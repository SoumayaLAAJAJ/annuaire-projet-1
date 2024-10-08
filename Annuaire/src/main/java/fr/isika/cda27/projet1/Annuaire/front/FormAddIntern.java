package fr.isika.cda27.projet1.Annuaire.front;

import java.io.IOException;

import fr.isika.cda27.projet1.Annuaire.back.Intern;
import fr.isika.cda27.projet1.Annuaire.back.Tree;
import fr.isika.cda27.projet1.Annuaire.back.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Cette classe représente un formulaire permettant d'ajouter un stagiaire dans
 * l'annuaire.
 */
public class FormAddIntern extends BorderPane {

	/**
	 * Constructeur permettant d'initialiser l'interface pour ajouter un stagiaire.
	 *
	 * @param app          L'application
	 * @param loggedInUser L'utilisateur actuellement connecté
	 */
	public FormAddIntern(App app, User loggedInUser) {

		Label title = new Label("Ajouter un stagiaire");
		VBox container = new VBox();
		Label name = new Label("Nom");
		Label firstname = new Label("Prénom");
		Label department = new Label("Département");
		Label promo = new Label("Promotion");
		Label year = new Label("Année");
		TextField nameTxtfield = new TextField();
		TextField firstnameTxtfield = new TextField();
		TextField departmentTxtfield = new TextField();
		TextField promoTxtField = new TextField();
		ChoiceBox<String> yearChoiceBox = new ChoiceBox<String>();
		Button add = new Button("Ajouter");
		VBox nameBox = new VBox();
		VBox firstnameBox = new VBox();
		VBox departmentBox = new VBox();
		VBox promoBox = new VBox();
		VBox yearBox = new VBox();
		GridPane form = new GridPane();
		Label feedbackLabel = new Label();

		LeftPane leftPane = new LeftPane(app, loggedInUser, "FormAddIntern");
		this.setLeft(leftPane);

		VBox rightContainer = new VBox();
		rightContainer.setPadding(new Insets(80, 20, 0, 70));

		this.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
		this.setStyle("-fx-background-color: #ffffff");
		add.getStyleClass().add("specific-button");
		add.setStyle("-fx-padding: 12px 20px;");
		title.setStyle("-fx-font-weight: bold; -fx-font-family: 'Inter'; -fx-font-size: 28px;");

		nameBox.getChildren().addAll(name, nameTxtfield);
		nameBox.setSpacing(10);
		firstnameBox.getChildren().addAll(firstname, firstnameTxtfield);
		firstnameBox.setSpacing(10);
		departmentBox.getChildren().addAll(department, departmentTxtfield);
		departmentBox.setSpacing(10);
		departmentBox.setPadding(new Insets(0, 0, 0, 65));
		promoBox.getChildren().addAll(promo, promoTxtField);
		promoBox.setSpacing(10);

		for (int yearChoice = 2002; yearChoice <= 2024; yearChoice++) {
			yearChoiceBox.getItems().addAll(String.valueOf(yearChoice));
		}

		yearBox.getChildren().addAll(year, yearChoiceBox);
		yearBox.setSpacing(10);

		form.addRow(0, title);
		form.addRow(2, nameBox, firstnameBox, departmentBox);
		form.addRow(3, yearBox, promoBox);
		form.addRow(5, add);
		form.addRow(4, feedbackLabel);
		form.setVgap(30);

		container.getChildren().addAll(form);
		rightContainer.getChildren().addAll(container);

		this.setCenter(rightContainer);

		add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (nameTxtfield.getText().isEmpty() || firstnameTxtfield.getText().isEmpty()
						|| departmentTxtfield.getText().isEmpty() || promoTxtField.getText().isEmpty()
						|| yearChoiceBox.getValue() == null) {

					feedbackLabel.setText("Merci de remplir tous les champs");
					feedbackLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

				} else {
					String name = nameTxtfield.getText().toUpperCase();
					String firstname = capitalizeFirstLetter(firstnameTxtfield.getText());
					String department = departmentTxtfield.getText().toUpperCase();
					String promo = promoTxtField.getText().toUpperCase();

					Intern newIntern = new Intern(name, firstname, department, promo, yearChoiceBox.getValue());
					Tree tree = new Tree();
					try {
						tree.addNode(newIntern);

						feedbackLabel.setText("Le stagiaire a bien été ajouté");
						feedbackLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");

						nameTxtfield.clear();
						firstnameTxtfield.clear();
						departmentTxtfield.clear();
						promoTxtField.clear();
						yearChoiceBox.setValue(null);

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

	}

	/**
	 * Capitalise la première lettre d'une chaîne de caractères.
	 *
	 * @param text La chaîne de caractères à capitaliser
	 * @return La chaîne de caractères avec la première lettre en majuscule
	 */
	private String capitalizeFirstLetter(String text) {
		if (text == null || text.isEmpty()) {
			return text;
		}
		return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
	}

	/**
	 * Crée une scène pour le formulaire d'ajout de stagiaire.
	 *
	 * @param app          L'application
	 * @param loggedInUser L'utilisateur actuellement connecté
	 * @return Une nouvelle scène contenant la vue du formulaire d'ajout de
	 *         stagiaire
	 */
	public Scene createAddView(App app, User loggedInUser) {
		return new Scene(this, 1300, 700);
	}
}
