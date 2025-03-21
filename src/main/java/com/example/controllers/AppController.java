package com.example.controllers;

import com.example.models.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;

public class AppController {
    @FXML private TextField nomField;
    @FXML private TextField nouveauNomField;
    @FXML private TextField nouveauemailField;
    @FXML private TextField emailField;
    @FXML private TextField idField;
    @FXML private TextField searchField;
    @FXML private TextArea resultArea;

    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @FXML
    public void addUtilisateurs() {
        try {
            Utilisateur user = new Utilisateur(nomField.getText(), emailField.getText());

            if (!user.isValidNom() || !user.isValidEmail()) {
                showAlert("Erreur", "Nom ou email invalide !", Alert.AlertType.ERROR);
                return;
            }

            String sql = "INSERT INTO utilisateurs (nom, email, created_at, updated_at) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, user.getNom());
                pstmt.setString(2, user.getEmail());
                pstmt.setTimestamp(3, Timestamp.valueOf(user.getCreatedAt()));
                pstmt.setTimestamp(4, Timestamp.valueOf(user.getUpdatedAt()));
                pstmt.executeUpdate();
                showAlert("Succès", "Utilisateur ajouté avec succès !", Alert.AlertType.INFORMATION);
                clearFields();
            }
        } catch (SQLException e) {
            showAlert("Erreur SQL", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void listUtilisateurs() {
        try {
            resultArea.clear();
            String sql = "SELECT * FROM utilisateurs ORDER BY created_at DESC";
            //String sql = "SELECT * FROM utilisateurs";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Utilisateur user = new Utilisateur(
                            rs.getString("nom"),
                            rs.getString("email")
                    );
                    user.setId(rs.getInt("id"));
                    user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                    resultArea.appendText(user + "\n");
                }
            }
        } catch (SQLException e) {
            showAlert("Erreur SQL", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void modifierUtilisateur() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String nouveauNom = nouveauNomField.getText().trim();
            String nouvelEmail = nouveauemailField.getText().trim();

            if (nouveauNom.isEmpty() || nouvelEmail.isEmpty()) {
                showAlert("Erreur", "Le nom et l'email ne peuvent pas être vides !", Alert.AlertType.ERROR);
                return; 
            }

            String sql = "UPDATE utilisateurs SET nom = ?, email = ?, updated_at = ? WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, nouveauNom);
                pstmt.setString(2, nouvelEmail);
                pstmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                pstmt.setInt(4, id);
                int rowsUpdated = pstmt.executeUpdate();
                


                if (rowsUpdated > 0) {
                    showAlert("Succès", "Utilisateur modifié avec succès !", Alert.AlertType.INFORMATION);
                    clearFields();
                } else {
                    showAlert("Erreur", "Aucun utilisateur trouvé avec cet ID.", Alert.AlertType.ERROR);
                }


            }
        } catch (NumberFormatException e) {
            showAlert("Erreur", "ID invalide !", Alert.AlertType.ERROR);
        } catch (SQLException e) {
            showAlert("Erreur SQL", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void deleteUtilisateurs() {
        try {
            int id = Integer.parseInt(idField.getText().trim());

            String sql = "DELETE FROM utilisateurs WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                int rowsDeleted = pstmt.executeUpdate();

                if (rowsDeleted > 0) {
                    showAlert("Succès", "Utilisateur supprimé avec succès !", Alert.AlertType.INFORMATION);
                    clearFields();
                } else {
                    showAlert("Erreur", "Aucun utilisateur trouvé avec cet ID.", Alert.AlertType.ERROR);
                }
            }
        } catch (NumberFormatException e) {
            showAlert("Erreur", "ID invalide !", Alert.AlertType.ERROR);
        } catch (SQLException e) {
            showAlert("Erreur SQL", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void rechercherParEmailOuNom() {
        try {
            String terme = searchField.getText().trim();

            String sql = "SELECT * FROM utilisateurs WHERE email LIKE ? OR nom LIKE ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, "%" + terme + "%");
                pstmt.setString(2, "%" + terme + "%");
                try (ResultSet rs = pstmt.executeQuery()) {
                    resultArea.clear();
                    boolean found = false;
                    while (rs.next()) {
                        Utilisateur user = new Utilisateur(
                                rs.getString("nom"),
                                rs.getString("email")
                        );
                        user.setId(rs.getInt("id"));
                        user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                        resultArea.appendText(user + "\n");
                        found = true;
                    }
                    if (!found) {
                        resultArea.appendText("Aucun résultat pour : " + terme + "\n");
                    }
                }
            }
        } catch (SQLException e) {
            showAlert("Erreur SQL", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void exporterListeUtilisateurs() {
        try {
            String sql = "SELECT * FROM utilisateurs";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                File file = new File("utilisateurs_export.csv");
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("ID,Nom,Email,Créé le,Mis à jour le\n");

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String nom = rs.getString("nom");
                        String email = rs.getString("email");
                        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                        LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();

                        writer.write(String.format("%d,%s,%s,%s,%s\n", id, nom, email, createdAt, updatedAt));
                    }

                    showAlert("Exportation réussie", "La liste des utilisateurs a été exportée dans : " + file.getAbsolutePath(), Alert.AlertType.INFORMATION);
                }
            }
        } catch (SQLException e) {
            showAlert("Erreur SQL", "Erreur lors de la récupération des utilisateurs : " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (IOException e) {
            showAlert("Erreur d'exportation", "Erreur lors de l'écriture du fichier : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        nomField.clear();
        emailField.clear();
        idField.clear();
        searchField.clear();
        nouveauemailField.clear();
        nouveauNomField.clear();
        
    }
}