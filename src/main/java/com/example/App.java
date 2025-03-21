package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import com.example.utils.Connexion;
import com.example.controllers.AppController;

public class App extends Application {
    private Connection connection;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/App.fxml"));
        Parent root = loader.load();
        
        // Initialisation de la connexion
        Connexion link = new Connexion();
        connection = link.getConnection();
        
        // Passer la connexion au contrôleur
        AppController controller = loader.getController();
        controller.setConnection(connection);
        
        Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
        primaryStage.setTitle("Gestion des Utilisateurs");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}