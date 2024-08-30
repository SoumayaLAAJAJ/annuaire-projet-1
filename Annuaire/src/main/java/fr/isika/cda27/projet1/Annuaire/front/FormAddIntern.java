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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FormAddIntern extends BorderPane {

	public FormAddIntern(App app, User loggedInUser) {
		super();
		initializeUI(app, loggedInUser);
	}

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
	ChoiceBox<String> yearChoiceBox = new ChoiceBox<>();
	Button add = new Button("Ajouter");

	VBox nameBox = new VBox();
	VBox firstnameBox = new VBox();
	VBox departmentBox = new VBox();
	VBox promoBox = new VBox();
	VBox yearBox = new VBox();

	GridPane form = new GridPane();
	Label feedbackLabel = new Label(); // Label pour afficher les messages de feedback

	private void initializeUI(App app, User loggedInUser) {

		// Ajout du menu de gauche
		LeftPane leftPane = new LeftPane(app, loggedInUser);
		this.setLeft(leftPane);

		// Conteneur pour le formulaire
		VBox rightContainer = new VBox();
		rightContainer.setPadding(new Insets(120, 40, 0, 40));
		rightContainer.setSpacing(10);

		this.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
		add.getStyleClass().add("specific-button");
		title.setStyle("-fx-font-weight: bold; -fx-font-family: 'Inter'; -fx-font-size: 28px;");

		nameBox.getChildren().addAll(name, nameTxtfield);
		firstnameBox.getChildren().addAll(firstname, firstnameTxtfield);
		departmentBox.getChildren().addAll(department, departmentTxtfield);
		promoBox.getChildren().addAll(promo, promoTxtField);

		for (int year = 2002; year <= 2024; year++) {
			yearChoiceBox.getItems().add(String.valueOf(year));
		}

		yearBox.getChildren().addAll(year, yearChoiceBox);

		HBox buttonRow = new HBox();
		HBox space = new HBox();
		buttonRow.getChildren().addAll(space, add);

		// Formulaire
		form.addRow(0, title);
		form.addRow(1, nameBox, firstnameBox, departmentBox);
		form.addRow(2, yearBox, promoBox);
		form.addRow(3, buttonRow);
		form.addRow(4, feedbackLabel); // Ajout du feedbackLabel sous le bouton
		form.setVgap(30);
		form.setHgap(100);

		container.getChildren().addAll(form);
		form.setPadding(new Insets(0, 0, 0, 50));

		rightContainer.getChildren().addAll(container);

		// Ajouter le conteneur de droite au centre du BorderPane
		this.setCenter(rightContainer);

		// Ajoute le stagiaire dans l'arbre binaire
		add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Vérifier si tous les champs sont remplis
				if (nameTxtfield.getText().isEmpty() || firstnameTxtfield.getText().isEmpty()
						|| departmentTxtfield.getText().isEmpty() || promoTxtField.getText().isEmpty()
						|| yearChoiceBox.getValue() == null) {

					// Afficher le message d'erreur en rouge
					feedbackLabel.setText("Merci de remplir tous les champs");
					feedbackLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

				} else {
					Intern newIntern = new Intern(nameTxtfield.getText(), firstnameTxtfield.getText(),
							departmentTxtfield.getText(), promoTxtField.getText(), yearChoiceBox.getValue());
					Tree tree = new Tree();
					try {
						tree.addNode(newIntern);

						// Afficher le message de succès en vert
						feedbackLabel.setText("Le stagiaire a bien été ajouté");
						feedbackLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");

						// Réinitialiser les champs après ajout
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

	public Scene createAddView(App app, User loggedInUser) {
		return new Scene(this, 1300, 700);
	}
}

