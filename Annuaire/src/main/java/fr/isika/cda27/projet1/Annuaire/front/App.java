package fr.isika.cda27.projet1.Annuaire.front;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        LogForm logForm = new LogForm();
        Scene loginScene = logForm.createLogFormScene(stage); // Obtenir la scène de connexion

        if (loginScene != null) {
            stage.setScene(loginScene);
            stage.setTitle("Connexion au Annuaire de Gin");
            stage.show();
        } else {
            // Gérer le cas où la scène n'a pas pu être créée (par exemple, si l'image n'est pas trouvée)
            System.err.println("La scène de connexion n'a pas pu être initialisée.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

