package fr.isika.cda27.projet1.Annuaire.front;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import fr.isika.cda27.projet1.Annuaire.back.Intern;
import fr.isika.cda27.projet1.Annuaire.back.InternDAO;
import fr.isika.cda27.projet1.Annuaire.back.Node;
import fr.isika.cda27.projet1.Annuaire.back.Tree;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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

		createBinfile();
		launch();
	}

	private static void createBinfile() {
		InternDAO intern;
		List<Intern> interns;

		try {
			// Initialise la liste de stagiaire depuis le fichier don
			intern = new InternDAO();
			interns = intern.getMaListe();

			// créé l'arbre
			Tree tree = new Tree();

			// Parcours de chaque stagiare pour créer un Node et l'écrire dans le fichier
			for (int i = 0; i < interns.size(); i++) {

				// Crée un noeud avec le stagiaire
				tree.checkRootToAddNode(interns.get(i));
			}

			//readNodeFromBinFile(raf, 312);


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void readNodeFromBinFile(RandomAccessFile raf, int index) {
		String name = "";
		String firstname = "";
		String department = "";
		String year = "";
		String promo = "";
		int leftChild = 0;
		int rightChild = 0;
		int next = 0;

		try {
			// read Nom
			raf.seek(index);
			System.out.println(raf.getFilePointer());

			for (int j = 0; j < Intern.NAME_LENGTH; j++) {
				name += raf.readChar();
			}
			name = name.trim();
			System.out.println(name);

			// read Prenom
			for (int j = 0; j < (Intern.FIRSTNAME_LENGTH); j++) {
				firstname += raf.readChar();
			}
			firstname = firstname.trim();
			System.out.println(firstname);
			
			// read Department
			for (int j = 0; j < (Intern.DEPARTMENT_LENGTH); j++) {
				department += raf.readChar();
			}
			department = department.trim();
			System.out.println(department);
			
			// read année
			for (int j = 0; j < (Intern.YEAR_LENGTH); j++) {
				year += raf.readChar();
			}
			year = year.trim();
			System.out.println(year);
			
			// read promo
			for (int j = 0; j < (Intern.PROMO_LENGTH); j++) {
				promo += raf.readChar();
			}
			promo = promo.trim();
			System.out.println(promo);
			
			// read left child
			leftChild = raf.readInt();
			System.out.println(leftChild);
			
			// read right child
			rightChild = raf.readInt();
			System.out.println(rightChild);

			// read next child
			next = raf.readInt();
			System.out.println(next);

			System.out.println(raf.getFilePointer());

		} catch (IOException e) {
			e.printStackTrace();
		}
		


	}
}
