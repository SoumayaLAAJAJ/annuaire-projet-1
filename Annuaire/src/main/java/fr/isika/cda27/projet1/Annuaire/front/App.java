package fr.isika.cda27.projet1.Annuaire.front;

import fr.isika.cda27.projet1.Annuaire.back.Tree;
import fr.isika.cda27.projet1.Annuaire.back.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Classe principale de l'application qui gère les différentes vues de
 * l'annuaire.
 */
public class App extends Application {

	private Stage stage;

	/**
	 * Cette méthode est appelée lors de l'initialisation de l'application. Elle
	 * initialise un arbre binaire et crée un fichier binaire pour stocker les
	 * données des stagiaires.
	 */
	@Override
	public void init() {
		Tree tree = new Tree();
		tree.createBinfile();
	}

	/**
	 * Permet l'affichage de la scène de connexion à l'ouverture de l'application.
	 *
	 * @param stage Le stage principal sur lequel les scènes seront affichées
	 */
	@Override
	public void start(Stage stage) {
		this.stage = stage;
		showLoginScene();
	}

	/**
	 * Permet d'afficher la scène de connexion utilisateur
	 */
	public void showLoginScene() {
		LogForm logForm = new LogForm(this);
		Scene loginScene = logForm.createLogFormScene();
		stage.setScene(loginScene);
		stage.setTitle("Connexion à l'annuaire de Gin");
		stage.centerOnScreen();
		stage.show();
	}

	/**
	 * Change la scène actuelle pour afficher la liste des stagiaires.
	 *
	 * @param app          L'instance de l'application
	 * @param loggedInUser L'utilisateur actuellement connecté
	 */
	public void switchToPageList(App app, User loggedInUser) {
		PageList pageList = new PageList();
		Scene pageListScene = pageList.createListView(app, loggedInUser);
		stage.setScene(pageListScene);
		stage.centerOnScreen();
		stage.setTitle("Bienvenue dans l'annuaire de Gin");
	}

	/**
	 * Change la scène actuelle pour afficher le formulaire d'ajout de stagiaire.
	 *
	 * @param app          L'instance de l'application
	 * @param loggedInUser L'utilisateur actuellement connecté
	 */
	public void switchToAddInternPage(App app, User loggedInUser) {
		FormAddIntern addPage = new FormAddIntern(app, loggedInUser);
		Scene addInternScene = addPage.createAddView(app, loggedInUser);
		stage.setScene(addInternScene);
	}

	/**
	 * Change la scène actuelle pour afficher la FAQ.
	 *
	 * @param app          L'instance de l'application
	 * @param loggedInUser L'utilisateur actuellement connecté
	 */
	public void switchToFAQPage(App app, User loggedInUser) {
		FAQ faqPage = new FAQ(app, loggedInUser);
		Scene faqScene = faqPage.createAddView(app, loggedInUser);
		stage.setScene(faqScene);
		stage.centerOnScreen();
		stage.setTitle("FAQ - Annuaire de Gin");
	}

	/**
	 * Méthode main de l'application, point d'entrée du programme.
	 *
	 * @param args Les arguments de la ligne de commande
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
