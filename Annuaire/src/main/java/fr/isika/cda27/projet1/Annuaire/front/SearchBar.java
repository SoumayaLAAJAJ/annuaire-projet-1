package fr.isika.cda27.projet1.Annuaire.front;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.RandomAccessFile;

import fr.isika.cda27.projet1.Annuaire.back.Intern;
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

		setPadding(new Insets(30, 0, 20, 0));

		VBox searchPane = new VBox();
		searchPane.setSpacing(20);

		// Ajout de l'icône de recherche
		Image searchIcon = new Image(getClass().getResource("/searchIcon.png").toExternalForm());
		ImageView iconView = new ImageView(searchIcon);
		iconView.setFitWidth(25);
		iconView.setPreserveRatio(true);

		ImageView iconView2 = new ImageView(searchIcon);
		iconView2.setFitWidth(25);
		iconView2.setPreserveRatio(true);

		// Pane contenant le texte et l'icône
		HBox btnContent = new HBox();
		btnContent.setAlignment(Pos.CENTER_LEFT);
		btnContent.setSpacing(115);
		btnContent.getChildren().addAll(new Label("Rechercher par :"), iconView);

		// Création du bouton et ajout du contenu
		Button searchBtn = new Button();
		searchBtn.setGraphic(btnContent);
		searchBtn.setPrefWidth(300);

		HBox searchBtns = new HBox();
		searchBtns.setSpacing(20);

		// Création de l'input pour la recherche du nom
		TextField nameTextField = new TextField();
		nameTextField.setPromptText("Nom");
		nameTextField.setStyle("-fx-prompt-text-fill: black");
		nameTextField.setPrefWidth(340);
		nameTextField.setVisible(false);

		// Création de l'input pour la recherche du prénom
		TextField firstnameTextField = new TextField();
		firstnameTextField.setPromptText("Prénom");
		firstnameTextField.setStyle("-fx-prompt-text-fill: black");
		firstnameTextField.setPrefWidth(340);
		firstnameTextField.setVisible(false);

		// Création de l'input pour la recherche du département
		TextField departmentTextField = new TextField();
		departmentTextField.setPromptText("Département");
		departmentTextField.setStyle("-fx-prompt-text-fill: black");
		departmentTextField.setPrefWidth(340);
		departmentTextField.setVisible(false);

		// Création de l'input pour la recherche de l'année
		TextField yearTextField = new TextField();
		yearTextField.setPromptText("Année");
		yearTextField.setStyle("-fx-prompt-text-fill: black");
		yearTextField.setPrefWidth(340);
		yearTextField.setVisible(false);

		// Création de l'input pour la recherche de la promo
		TextField promoTextField = new TextField();
		promoTextField.setPromptText("Promo");
		promoTextField.setStyle("-fx-prompt-text-fill: black");
		promoTextField.setPrefWidth(340);
		promoTextField.setVisible(false);

		// Création du bouton et ajout de l'icône dans le bouton
		Button validateBtn = new Button();
		validateBtn.setGraphic(iconView2);
		validateBtn.getStyleClass().add("searchIconBtn");
		validateBtn.setVisible(false);

		searchBtns.getChildren().addAll(nameTextField, firstnameTextField, departmentTextField, yearTextField,
				promoTextField, validateBtn);

		// Ajout de tous les boutons à la searchPane
		searchPane.getChildren().addAll(searchBtn, searchBtns);

		// Ajout de la classe CSS et ajout à la scène
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

		// Appel de la méthode pour récupérér l'input quand on presse entrée sur les différents champs
		nameTextField.setOnAction(e -> handleSearchOne("name", nameTextField));
		firstnameTextField.setOnAction(e -> handleSearchOne("firstname", firstnameTextField));
		departmentTextField.setOnAction(e -> handleSearchOne("department", departmentTextField));
		yearTextField.setOnAction(e -> handleSearchOne("year", yearTextField));
		promoTextField.setOnAction(e -> handleSearchOne("promo", promoTextField));

		// Recherche sur plusieurs champs
		validateBtn.setOnAction(e -> handleSearchMany(nameTextField, firstnameTextField, departmentTextField,
				yearTextField, promoTextField));

	}

	// Récupération de l'input
	private void handleSearchOne(String label, TextField textField) {
		System.out.println(label + " " + textField.getText());
	
		try {

			RandomAccessFile raf = new RandomAccessFile("src/main/resources/arbre.bin", "r");

			String nameSearched = textField.getText();
			nameSearched = nameSearched.toUpperCase();
			Boolean nameFound = false;
			int position = 0;
			
			while (position < raf.length() && !nameFound) {

				String nomLu = "";
				raf.seek(position);
				System.out.println(raf.getFilePointer());

				for (int j = 0; j < Intern.NAME_LENGTH; j++) {
					nomLu += raf.readChar();
				}
				nomLu = nomLu.trim();

				System.out.println(nomLu);

				if (nomLu.equals(nameSearched)) {
					System.out.println("J'ai trouvé le nom : " + nameSearched + " en position " + position);
					nameFound = true;
				} else {
					position += Intern.RECORD_LENGTH;
				}
			}

			if (!nameFound) {
				System.out.println("Je n'ai pas trouvé le nom " + nameSearched + " dans le fichier");
			}

			raf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Récupération des valeurs de tous les inputs
	private void handleSearchMany(TextField name, TextField firstname, TextField department, TextField year,
			TextField promo) {

		// Appel getTextOrNull pour afficher null si pas de valeur entrée dans le champ
		String nameValue = getTextOrNull(name);
		String firstnameValue = getTextOrNull(firstname);
		String departmentValue = getTextOrNull(department);
		String yearValue = getTextOrNull(year);
		String promoValue = getTextOrNull(promo);

		System.out.println("name=" + nameValue + ", firstname=" + firstnameValue + ", department=" + departmentValue
				+ ", year=" + yearValue + ", promo=" + promoValue);
	}

	String getTextOrNull(TextField textField) {
		String text = textField.getText();
		return text == null || text.isEmpty() ? "null" : text;
	}

}