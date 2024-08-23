package fr.isika.cda27.projet1.Annuaire.front;

import fr.isika.cda27.projet1.Annuaire.back.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Form extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inscription Stagiaire");

        // Créer une grille pour le formulaire
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Chemin de l'image relatif au répertoire des ressources
        String imagePath = "/images/gin.png";
        Image image = null;

        try {
            // Charger l'image à partir du chemin fourni
            image = new Image(getClass().getResourceAsStream(imagePath));
        } catch (Exception e) {
            // Gestion des erreurs si l'image ne peut pas être chargée
            System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
            e.printStackTrace();
        }

        if (image == null) {
            System.err.println("L'image à l'emplacement " + imagePath + " n'a pas pu être trouvée.");
        } else {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(100); // Ajuster la largeur de l'image si nécessaire
            imageView.setPreserveRatio(true); // Maintenir le ratio de l'image

            grid.add(imageView, 0, 0, 2, 1); // Ajouter l'ImageView dans la grille (occupant deux colonnes en haut)
        }

        // Ajouter des labels et champs de texte
        Label usernameLabel = new Label("Nom du stagiaire:");
        grid.add(usernameLabel, 0, 1);
        TextField usernameField = new TextField();
        grid.add(usernameField, 1, 1);

        Label passwordLabel = new Label("Mot de passe:");
        grid.add(passwordLabel, 0, 2);
        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 2);

        // Ajouter le bouton d'inscription
        Button signUpButton = new Button("S'inscrire");
        grid.add(signUpButton, 1, 3);

        // Action à effectuer lors du clic sur le bouton
        signUpButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Créer un nouvel objet User sans rôle admin
            User user = new User(username, password, false);
            enregistrerUtilisateur(user);

            // Afficher un message de succès
            System.out.println("Utilisateur inscrit: " + user);
        });

        // Créer la scène et l'ajouter à la fenêtre principale
        Scene scene = new Scene(grid, 320, 250); // Ajuster les dimensions si nécessaire
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void enregistrerUtilisateur(User user) {
        
        System.out.println("Enregistrement de l'utilisateur: " + user.getUsername());
    }

    public static void main(String[] args) {
        launch(args);
    }
}