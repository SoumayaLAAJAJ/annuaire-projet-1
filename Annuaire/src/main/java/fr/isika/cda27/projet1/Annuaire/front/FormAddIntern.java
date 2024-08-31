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

		// Ajout du menu de gauche
		LeftPane leftPane = new LeftPane(app, loggedInUser, "FormAddIntern");
		this.setLeft(leftPane);

		// Conteneur pour le formulaire
		VBox rightContainer = new VBox();
		rightContainer.setPadding(new Insets(80, 20, 0, 70));

		this.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
		add.getStyleClass().add("searchIconBtn");
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

		// Formulaire
		form.addRow(0, title);
		form.addRow(2, nameBox, firstnameBox, departmentBox);
		form.addRow(3, yearBox, promoBox);
		form.addRow(5, add);
		form.setVgap(30);

		container.getChildren().addAll(form);
		rightContainer.getChildren().addAll(container);

		// Ajouter le conteneur de droite au centre du BorderPane
		this.setCenter(rightContainer);

		// Ajoute le stagiaire dans l'arbre binaire
		add.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Intern newIntern = new Intern(nameTxtfield.getText(), firstnameTxtfield.getText(),
						departmentTxtfield.getText(), promoTxtField.getText(), yearChoiceBox.getValue());
				Tree tree = new Tree();
				try {
					tree.addNode(newIntern);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	public Scene createAddView(App app, User loggedInUser) {
		return new Scene(this, 1300, 700);
	}

}
