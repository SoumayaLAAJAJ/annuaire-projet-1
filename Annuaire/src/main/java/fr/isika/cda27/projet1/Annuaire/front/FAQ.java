package fr.isika.cda27.projet1.Annuaire.front;

import fr.isika.cda27.projet1.Annuaire.back.User;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FAQ extends HBox {
	Accordion faqAccordion = new Accordion();
	VBox question1Content = new VBox(10);
	VBox question2Content = new VBox(10);

	public FAQ(App app, User loggedInUser) {
		super();
		initializeUI(app, loggedInUser);
	}

	private void initializeUI(App app, User loggedInUser) {
		this.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
		LeftPane leftPane = new LeftPane(app, loggedInUser);
		ImageView question1Image = new ImageView(new Image(getClass().getResource("/log.png").toExternalForm()));
		question1Image.setFitWidth(800);
		question1Image.setPreserveRatio(true);
		question1Content.getChildren().add(question1Image);
		question1Content.getChildren().add(new Label("Voici la réponse à la question 1.azertyuioklpqsdfghjklmwxcvbn,ezrtyuiosdfghjklxwcvbn, sdfghjklxwcvbn, zqsezdjkilmsdfghjkl sdfghjklsdfghjklsdfcgvhbj"));
		TitledPane question1Pane = new TitledPane("Question 1 : Comment faire ceci ?", question1Content);

		VBox question2Content = new VBox(10);
		ImageView question2Image = new ImageView(new Image(getClass().getResource("/printListInterns.png").toExternalForm()));
		question2Image.setFitWidth(300);
		question2Image.setPreserveRatio(true);
		question2Content.getChildren().add(question2Image);
		question2Content.getChildren().add(new Label("Voici la réponse à la question 2."));
		TitledPane question2Pane = new TitledPane("Question 2 : Comment faire cela ?", question2Content);

		faqAccordion.getPanes().addAll(question1Pane, question2Pane);

		VBox faqContainer = new VBox(20);
		faqContainer.getChildren().add(faqAccordion);

		this.getChildren().addAll(leftPane, faqContainer);

		faqContainer.setPadding(new javafx.geometry.Insets(10, 50, 10, 50));

	}

	public Scene createAddView(App app, User loggedInUser) {
		return new Scene(this, 1300, 700);
	}
}
