package fr.isika.cda27.projet1.Annuaire.front;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class LeftPane extends BorderPane {

	public LeftPane() {
		// Couleur de fond et largueur du panneau
		setStyle("-fx-background-color:#D9D9D9");
		setPrefWidth(270);

		// En-tête avec image de Gin et nom du user
		HBox userTitle = new HBox();
		userTitle.setPadding(new Insets(20));

		// Ajout de la photo de super Gin
		Image userImg = new Image(getClass().getResource("/gin.jpg").toExternalForm());
		ImageView imageView = new ImageView(userImg);
		imageView.setFitWidth(130);
		imageView.setPreserveRatio(true);

		// Création du cercle pour la photo de super Gin
		Circle clip = new Circle(60, 60, 60);
		imageView.setClip(clip);

		// Ajout du texte de bienvenue
		VBox userGreetings = new VBox();
		userGreetings.setPadding(new Insets(35, 0, 0, 10));
		Label greetings = new Label("Bonjour");
		Label username = new Label("Gin");
		userGreetings.getChildren().addAll(greetings, username);

		userTitle.getChildren().addAll(imageView, userGreetings);
		setTop(userTitle);

		// Navigation principale
		VBox menu = new VBox();
		menu.setSpacing(15);
		menu.setPadding(new Insets(30, 0, 0, 0));
		Button internListBtn = new Button("Liste des stagiaires");
		internListBtn.setPrefWidth(270);
		internListBtn.getStyleClass().add("labelBold");

		Button internAddBtn = new Button("Ajouter un stagiaire");
		internAddBtn.setPrefWidth(270);
		menu.getChildren().addAll(internListBtn, internAddBtn);
		setCenter(menu);

		// Footer
		BorderPane menuFooter = new BorderPane();
		Button FAQBtn = new Button("FAQ");
		Button logOutBtn = new Button("Se déconnecter");
		menuFooter.setLeft(FAQBtn);
		menuFooter.setRight(logOutBtn);

		setBottom(menuFooter);
	}
}