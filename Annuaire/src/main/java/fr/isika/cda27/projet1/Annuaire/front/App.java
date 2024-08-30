package fr.isika.cda27.projet1.Annuaire.front;

import fr.isika.cda27.projet1.Annuaire.back.Tree;
import fr.isika.cda27.projet1.Annuaire.back.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	private Stage stage;
	
	@Override
	public void init() {
		Tree tree = new Tree();
		tree.createBinfile();
	}

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		showLoginScene();
	}

	// Méthode pour appeler la page de connexion
	public void showLoginScene() {
		LogForm logForm = new LogForm(this);
		Scene loginScene = logForm.createLogFormScene();

		if (loginScene != null) {
			stage.setScene(loginScene);
			stage.setTitle("Connexion à l'annuaire de Gin");
			stage.centerOnScreen();
			stage.show();
		} else {
			// Gérer le cas où la scène n'a pas pu être créée (par exemple, si l'image n'est pas trouvée)
			System.err.println("La scène de connexion n'a pas pu être initialisée.");
		}
	}

	// Méthode pour appeler la liste des stagiaires
	public void switchToPageList(App app, User loggedInUser) {
		PageList pageList = new PageList();
		Scene pageListScene = pageList.createListView(app, loggedInUser);
		stage.setScene(pageListScene);
		stage.centerOnScreen();
		stage.setTitle("Bienvenue dans l'annuaire de Gin !");
	}

	// Méthode pour appeler le formulaire d'ajout de stagiaire
	public void switchToAddInternPage(App app, User loggedInUser) {
		FormAddIntern addPage = new FormAddIntern(app, loggedInUser);
		Scene addInternScene = addPage.createAddView(app, loggedInUser);
		stage.setScene(addInternScene);
	}
	
    // Méthode pour appeler la page FAQ
    public void switchToFAQPage(App app, User loggedInUser) {
        FAQ faqPage = new FAQ(app, loggedInUser);
        Scene faqScene = faqPage.createAddView(app, loggedInUser);
        stage.setScene(faqScene);
        stage.centerOnScreen();
        stage.setTitle("FAQ - Annuaire de Gin");
    }
	


	public static void main(String[] args) {
		launch(args);
	}
	
}
