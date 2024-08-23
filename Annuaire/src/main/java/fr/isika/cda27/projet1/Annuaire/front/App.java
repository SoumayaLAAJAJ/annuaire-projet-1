package fr.isika.cda27.projet1.Annuaire.front;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	@Override
	public void start(Stage stage) {

		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 1280, 700);
		scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
		stage.setTitle("Annuaire de Gin");

		// PANNEAU GAUCHE
		LeftPane leftPane = new LeftPane();
		root.setLeft(leftPane);

		// PAGE LISTE
		BorderPane listPage = new PageList();
		root.setCenter(listPage);

		stage.setMaximized(true);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}

}