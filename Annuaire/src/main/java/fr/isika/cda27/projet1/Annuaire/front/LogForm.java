package fr.isika.cda27.projet1.Annuaire.front;

import fr.isika.cda27.projet1.Annuaire.back.User;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

/**
 * Cette classe permet la création et la gestion de la scène de connexion de l'application.
 * Elle permet aux utilisateurs de se connecter ou de s'inscrire en utilisant un formulaire simple.
 */
public class LogForm {

	private App app;

    /**
     * Constructeur permettant d'initialiser la vue de connexion utilisateur
     *
     * @param app L'application
     */
	public LogForm(App app) {
		this.app = app;
	}

	private static final String FILE_PATH = "src/main/resources/users.txt";

    /**
     * Crée et retourne une scène contenant le formulaire de connexion.
     *
     * @return La scène contenant le formulaire de connexion
     */
	public Scene createLogFormScene() {
		String imagePath = "/gin-login.png";
		Image image = null;

		try {
			image = new Image(getClass().getResourceAsStream(imagePath));
		} catch (Exception e) {
			System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
			e.printStackTrace();
		}

		if (image == null) {
			System.err.println("L'image à l'emplacement " + imagePath + " n'a pas pu être trouvée.");
			return null;
		}

		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(700);
		imageView.setPreserveRatio(true);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setAlignment(Pos.CENTER);

		VBox usernameBox = new VBox();
		Label usernameLabel = new Label("Nom du stagiaire :");
		usernameLabel.setFont(Font.font("Arial", 14));
		usernameLabel.setStyle("-fx-text-fill: #ffffff");
		TextField usernameField = new TextField();
		usernameField.setPromptText("Entrez votre nom");
		usernameField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		usernameBox.getChildren().addAll(usernameLabel, usernameField);
		grid.add(usernameBox, 1, 0);

		VBox passwordBox = new VBox();
		Label passwordLabel = new Label("Mot de passe :");
		passwordLabel.setFont(Font.font("Arial", 14));
		passwordLabel.setStyle("-fx-text-fill: #ffffff");
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Entrez votre mot de passe");
		passwordField.setStyle("-fx-background-radius: 10; -fx-padding: 10;");
		passwordBox.getChildren().addAll(passwordLabel, passwordField);
		grid.add(passwordBox, 1, 1);

		Button signUpButton = new Button("S'inscrire");
		signUpButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; "
				+ "-fx-background-radius: 20; -fx-padding: 10 20;");
		signUpButton.setEffect(new DropShadow(10, Color.BLACK));

		Button loginButton = new Button("Se connecter");
		loginButton.setStyle("-fx-background-color: #008CBA; -fx-text-fill: white; "
				+ "-fx-background-radius: 20; -fx-padding: 10 20;");
		loginButton.setEffect(new DropShadow(10, Color.BLACK));

		HBox btnBox = new HBox(10); // 10px spacing between buttons
		btnBox.getChildren().addAll(signUpButton, loginButton);
		grid.add(btnBox, 1, 2);

		signUpButton.setOnAction(e -> {
			String username = usernameField.getText();
			String password = passwordField.getText();

			if (username == "" | password == "") {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Erreur de connexion");
				alert.setHeaderText(null);
				alert.setContentText("Veuillez compléter tous les champs");
				alert.showAndWait();
			} else {
				User newUser = new User(username, password, false);
				if (newUser.signUp(FILE_PATH)) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Inscription réussie");
					alert.setHeaderText(null);
					alert.setContentText("Inscription réussie !");
					alert.showAndWait();
				} else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Erreur");
					alert.setHeaderText(null);
					alert.setContentText("Une erreur s'est produite lors de l'inscription.");
					alert.showAndWait();
				}
			}

		});

		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				User user = new User();
				String username = usernameField.getText();
				String password = passwordField.getText();
				User loggedInUser = user.login(FILE_PATH, username, password);

				if (loggedInUser != null) {
					app.switchToPageList(app, loggedInUser);
				} else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Erreur de connexion");
					alert.setHeaderText(null);
					alert.setContentText("Nom d'utilisateur ou mot de passe incorrect");
					alert.showAndWait();
				}
			}
		});

		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(imageView, grid);

		return new Scene(stackPane, 600, 400);
	}
}
