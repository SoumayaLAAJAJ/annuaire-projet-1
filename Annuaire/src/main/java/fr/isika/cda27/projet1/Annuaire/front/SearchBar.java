package fr.isika.cda27.projet1.Annuaire.front;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SearchBar extends VBox {

	public SearchBar() {

		setPadding(new Insets(30, 0, 35, 0));

		// Ajout de l'icône de recherche
		Image searchIcon = new Image(getClass().getResource("/searchIcon.png").toExternalForm());
		ImageView iconView = new ImageView(searchIcon);
		iconView.setFitWidth(30);
		iconView.setPreserveRatio(true);

		// Pane contenant le texte et l'icône
		HBox btnContent = new HBox();
		btnContent.setAlignment(Pos.CENTER_LEFT);
		btnContent.setSpacing(240);
		btnContent.getChildren().addAll(new Label("Rechercher"), iconView);

		// Création du bouton et ajout du contenu
		Button searchBtn = new Button();
		searchBtn.setGraphic(btnContent);
		searchBtn.setPrefWidth(400);

		// Ajout de la classe CSS et ajout à la scène
		searchBtn.getStyleClass().add("searchBtn");

		getChildren().addAll(searchBtn);

	}
}
