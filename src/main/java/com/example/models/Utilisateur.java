package com.example.models;

import java.time.LocalDateTime;

public class Utilisateur {

    private int id;
    private String nom;
    private String email;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    

    

    public Utilisateur(String nom, String email) {
        this.nom = nom;
        this.email = email;
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    
    }

  

    public boolean isValidEmail() {
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$";
        return this.email != null && this.email.matches(regex);
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return this.nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public LocalDateTime getCreatedAt() {
        return this.created_at;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updated_at;
    }

    public void setUpdatedAt(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public boolean isValidNom() {
        if (!this.nom.equals("")) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Nom: %s | Email: %s", id, nom, email);
    }
}