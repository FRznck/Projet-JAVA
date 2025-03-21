```--------------------------------------------------------------------------------------------------------------------------```

                                --Gestion des Utilisateurs - Application JavaFX---


Description du projet

Cette application est une application de gestion des utilisateurs développée en Java avec JavaFX. Elle permet de gérer une liste d'utilisateurs en effectuant des opérations CRUD (Create, Read, Update, Delete) et en exportant les données au format CSV.

L'application utilise une base de données MySQL pour stocker les informations des utilisateurs et offre une interface graphique conviviale pour interagir avec les données.

```--------------------------------------------------------------------------------------------------------------------------```

Fonctionnalités

Ajouter un utilisateur :

Saisir le nom et l'email d'un utilisateur.

Valider l'ajout avec un message de succès ou d'erreur.

Lister tous les utilisateurs :

Afficher la liste complète des utilisateurs dans une zone de texte.

Modifier un utilisateur :

Saisir l'ID de l'utilisateur à modifier.

Mettre à jour le nom et/ou l'email.

Supprimer un utilisateur :

Saisir l'ID de l'utilisateur à supprimer.

Confirmer la suppression avec un message de succès ou d'erreur.

Rechercher un utilisateur :

Rechercher par ID ou par nom/email.

Afficher les résultats dans la zone de texte.

Exporter la liste des utilisateurs :

Exporter la liste des utilisateurs dans un fichier CSV.

```--------------------------------------------------------------------------------------------------------------------------```
Structure du projet

Fichiers principaux

App.java : Point d'entrée de l'application. Initialise la connexion à la base de données et lance l'interface graphique.

AppController.java : Contrôleur de l'interface graphique. Gère les interactions utilisateur et les opérations sur la base de données.

Utilisateur.java : Modèle représentant un utilisateur avec ses propriétés (ID, nom, email) et des méthodes de validation.

Connexion.java : Gère la connexion à la base de données MySQL.

main.fxml : Fichier FXML définissant la structure de l'interface graphique.

styles.css : Feuille de style pour personnaliser l'apparence de l'interface.

src/
├── main/
│   ├── java/
│   │   └── com/example/
│   │       ├── controllers/ (contient AppController pour gérer l'implémentations)
│   │       ├── models/ (contient classe Utilisateur)
│   │       ├── utils/ (contient Connexion à la BDD)
│   │       └── App.java (menu principal)
│   └── resources/
│       ├── css/
│       │   └── styles.css (pour le style de l'application)
│       └── fxml/
│           └── App.fxml (gestion des boutons,label)


Auteurs

FRznck
```--------------------------------------------------------------------------------------------------------------------------```

