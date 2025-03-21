package com.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
  
    String url = "jdbc:mysql://localhost:3306/app"; 
    String utilisateur = "root";
    String motDePasse = "root";
    Connection connexion = null;

    public Connexion () {
        try {
            // Établir la connexion
            this.connexion = DriverManager.getConnection(this.url, this.utilisateur, this.motDePasse);
            System.out.println("Connected !");
        } catch (SQLException e) {
            System.out.println("Erreur de connexion  : " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return this.connexion;
    }

    public void close() {
        if (this.connexion != null) { 
            try { 
                this.connexion.close(); 
                System.out.println("Connexion fermée avec succès."); 
            } catch (SQLException e) { 
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage()); 
            } 
        } 
    }
}