package fr.isika.cda27.projet1.Annuaire.front;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Cette classe crée un panneau de recherche qui permet à l'utilisateur de filtrer les stagiaires.
 */
public class SearchBar extends VBox {

    /**
     * Crée une instance de `SearchBar` pour filtrer les stagiaires dans la table spécifiée.
     *
     * @param internTableView La table des stagiaires à filtrer
     */
	public SearchBar(InternTableView internTableView) {

		setPadding(new Insets(30, 0, 20, 0));

		VBox searchPane = new VBox();
		searchPane.setSpacing(20);

		// Création du bouton de recherche
		Image searchIcon = new Image(getClass().getResource("/searchIcon.png").toExternalForm());
		ImageView iconView = new ImageView(searchIcon);
		iconView.setFitWidth(25);
		iconView.setPreserveRatio(true);

		ImageView iconView2 = new ImageView(searchIcon);
		iconView2.setFitWidth(25);
		iconView2.setPreserveRatio(true);

		HBox btnContent = new HBox();
		btnContent.setAlignment(Pos.CENTER_LEFT);
		btnContent.setSpacing(115);
		btnContent.getChildren().addAll(new Label("Rechercher par :"), iconView);

		Button searchBtn = new Button();
		searchBtn.setGraphic(btnContent);
		searchBtn.setPrefWidth(300);

		HBox searchBtns = new HBox();
		searchBtns.setSpacing(20);

        // Création des champs de texte pour la recherche
		TextField nameTextField = new TextField();
		nameTextField.setPromptText("Nom");
		nameTextField.setStyle("-fx-prompt-text-fill: black");
		nameTextField.setPrefWidth(340);
		nameTextField.setVisible(false);

		TextField firstnameTextField = new TextField();
		firstnameTextField.setPromptText("Prénom");
		firstnameTextField.setStyle("-fx-prompt-text-fill: black");
		firstnameTextField.setPrefWidth(340);
		firstnameTextField.setVisible(false);

		TextField departmentTextField = new TextField();
		departmentTextField.setPromptText("Département");
		departmentTextField.setStyle("-fx-prompt-text-fill: black");
		departmentTextField.setPrefWidth(340);
		departmentTextField.setVisible(false);

		TextField yearTextField = new TextField();
		yearTextField.setPromptText("Année");
		yearTextField.setStyle("-fx-prompt-text-fill: black");
		yearTextField.setPrefWidth(340);
		yearTextField.setVisible(false);

		TextField promoTextField = new TextField();
		promoTextField.setPromptText("Promo");
		promoTextField.setStyle("-fx-prompt-text-fill: black");
		promoTextField.setPrefWidth(340);
		promoTextField.setVisible(false);

        // Création du bouton de validation pour appliquer les filtres
		Button validateBtn = new Button();
		validateBtn.setGraphic(iconView2);
		validateBtn.getStyleClass().add("iconBtn");
		validateBtn.setVisible(false);

		searchBtns.getChildren().addAll(nameTextField, firstnameTextField, departmentTextField, yearTextField,
				promoTextField, validateBtn);

		searchPane.getChildren().addAll(searchBtn, searchBtns);
		searchBtn.getStyleClass().add("searchBtn");
		getChildren().addAll(searchPane);

		searchBtn.setOnAction(e -> {
			// Affiche les boutons lorsqu'on clique sur "rechercher"
			nameTextField.setVisible(!nameTextField.isVisible());
			firstnameTextField.setVisible(!firstnameTextField.isVisible());
			departmentTextField.setVisible(!departmentTextField.isVisible());
			yearTextField.setVisible(!yearTextField.isVisible());
			promoTextField.setVisible(!promoTextField.isVisible());
			validateBtn.setVisible(!validateBtn.isVisible());

		});

		// Appliquer le filtre en spécifiant le champ
		nameTextField.setOnAction(e -> {
			internTableView.filterOne("name", nameTextField.getText());
		});

		firstnameTextField.setOnAction(e -> {
			internTableView.filterOne("firstname", firstnameTextField.getText());
		});

		departmentTextField.setOnAction(e -> {
			internTableView.filterOne("department", departmentTextField.getText());
		});

		yearTextField.setOnAction(e -> {
			internTableView.filterOne("year", yearTextField.getText());
		});

		promoTextField.setOnAction(e -> {
			internTableView.filterOne("promo", promoTextField.getText());
		});

		// Applique le filtre multi-critères
		validateBtn.setOnAction(e -> {
			internTableView.filterTable(nameTextField.getText(), firstnameTextField.getText(),
					departmentTextField.getText(), yearTextField.getText(), promoTextField.getText());
		});
	}
}