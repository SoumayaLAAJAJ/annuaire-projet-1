package fr.isika.cda27.projet1.Annuaire.front;

import fr.isika.cda27.projet1.Annuaire.back.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

	private App app;

	public LeftPane(App app, User loggedInUser, String scene) {

		this.app = app;
		setStyle("-fx-background-color:#D9D9D9");
		setPrefWidth(270);

		HBox userTitle = new HBox();
		userTitle.setPadding(new Insets(20));

		Image userImg = new Image(getClass().getResource("/gin.jpg").toExternalForm());
		ImageView imageView = new ImageView(userImg);
		imageView.setFitWidth(130);
		imageView.setPreserveRatio(true);

		Circle clip = new Circle(60, 60, 60);
		imageView.setClip(clip);

		VBox userGreetings = new VBox();
		userGreetings.setPadding(new Insets(35, 0, 0, 10));
		Label greetings = new Label("Bonjour");
		Label username = new Label(loggedInUser.getUsername().toUpperCase());
		userGreetings.getChildren().addAll(greetings, username);

		userTitle.getChildren().addAll(imageView, userGreetings);
		setTop(userTitle);

		VBox menu = new VBox();
		menu.setSpacing(15);
		menu.setPadding(new Insets(30, 0, 0, 0));
		Button internListBtn = new Button("Liste des stagiaires");
		internListBtn.setPrefWidth(270);
		internListBtn.getStyleClass().add("labelBold");
		internListBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				app.switchToPageList(app, loggedInUser);
			}
		});

		Button internAddBtn = new Button("Ajouter un stagiaire");
		internAddBtn.setPrefWidth(270);
		menu.getChildren().addAll(internListBtn, internAddBtn);
		setCenter(menu);

		internAddBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				app.switchToAddInternPage(app, loggedInUser);
			}

		});

		BorderPane menuFooter = new BorderPane();
		Button FAQBtn = new Button("FAQ");
		Button logOutBtn = new Button("Se déconnecter");
		menuFooter.setLeft(FAQBtn);
		menuFooter.setRight(logOutBtn);

		if ("FormAddIntern".equals(scene)) {
			internAddBtn.getStyleClass().add("selected-button");
			internListBtn.getStyleClass().remove("selected-button");
			FAQBtn.getStyleClass().remove("selected-button");
		}
		if ("PageList".equals(scene)) {
			internAddBtn.getStyleClass().remove("selected-button");
			internListBtn.getStyleClass().add("selected-button");
			FAQBtn.getStyleClass().remove("selected-button");
		}
		if ("FAQ".equals(scene)) {
			internAddBtn.getStyleClass().remove("selected-button");
			internListBtn.getStyleClass().remove("selected-button");
			FAQBtn.getStyleClass().add("selected-button");
		}

		FAQBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				app.switchToFAQPage(app, loggedInUser);

			}
		});

		logOutBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				app.showLoginScene();
			}

		});

		setBottom(menuFooter);
	}
}