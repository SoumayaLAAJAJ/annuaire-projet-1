package fr.isika.cda27.projet1.Annuaire.front;

import fr.isika.cda27.projet1.Annuaire.back.User;
import fr.isika.cda27.projet1.Annuaire.back.UserDataManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Form extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inscription Stagiaire");

        // Chemin de l'image relatif au répertoire des ressources
        String imagePath = "/images/gin2.png";
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
            return;
        }

        // Créer une ImageView pour afficher l'image
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(600); // Ajuster la largeur de l'image à la taille de la fenêtre
        imageView.setFitHeight(400); // Ajuster la hauteur de l'image à la taille de la fenêtre
        imageView.setPreserveRatio(true); // Maintenir le ratio de l'image

        // Créer une grille pour le formulaire
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setAlignment(Pos.CENTER);

        // Style du GridPane : 
        //TODO tester en enlevant le background color
        grid.setStyle("-fx-background-color: rgba(100, 100, 100, 0.8); " +//
                      "-fx-background-radius: 10; -fx-padding: 20;");//

        // Ajouter des labels et champs de texte avec styles
        Label usernameLabel = new Label("Nom du stagiaire:");
        usernameLabel.setFont(Font.font("Arial", 14));
        grid.add(usernameLabel, 0, 0);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Entrez votre nom");
        usernameField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
        grid.add(usernameField, 1, 0);

        Label passwordLabel = new Label("Mot de passe:");
        passwordLabel.setFont(Font.font("Arial", 14));
        grid.add(passwordLabel, 0, 1);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Entrez votre mot de passe");
        passwordField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
        grid.add(passwordField, 1, 1);

        // Ajouter le bouton d'inscription avec style
        Button signUpButton = new Button("S'inscrire");
        signUpButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; " +
                              "-fx-background-radius: 20; -fx-padding: 10 20;");
        signUpButton.setEffect(new DropShadow(10, Color.BLACK));
        grid.add(signUpButton, 1, 2);

        // Action à effectuer lors du clic sur le bouton
        signUpButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis !");
                return;
            }

            // Créer un nouvel objet User sans rôle admin
            User user = new User(username, password, false);
            enregistrerUtilisateur(user);

            // Ajouter l'utilisateur à la liste des utilisateurs et enregistrer
            UserDataManager manager = new UserDataManager();
            List<User> users = loadUsersFromFile(manager); // Charger les utilisateurs existants
            if (users == null) {
                users = new ArrayList<>();
            }
            users.add(user);
            manager.saveUsersToFile(users, "src/main/resources/users.dat");

            // Afficher un message de succès
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Utilisateur inscrit avec succès !");
        });

        // Créer un StackPane pour superposer le formulaire sur l'image
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(imageView, grid);

        // Créer la scène et l'ajouter à la fenêtre principale
        Scene scene = new Scene(stackPane, 600, 400); // Ajuster les dimensions si nécessaire
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void enregistrerUtilisateur(User user) {
        System.out.println("Enregistrement de l'utilisateur: " + user.getUsername());
    }

    private List<User> loadUsersFromFile(UserDataManager manager) {
        return manager.loadUsersFromFile("src/main/resources/users.dat");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}