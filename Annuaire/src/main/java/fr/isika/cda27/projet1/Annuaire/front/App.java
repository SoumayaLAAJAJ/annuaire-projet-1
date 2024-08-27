package fr.isika.cda27.projet1.Annuaire.front;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import fr.isika.cda27.projet1.Annuaire.back.BinaryFileReader;
import fr.isika.cda27.projet1.Annuaire.back.BinaryFileWriter;
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

        

        // PAGE LISTE
        BorderPane listPage = new PageList();
        root.setCenter(listPage);

        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try {
        	InternDAO intern = new InternDAO();
        	List<Intern> interns = intern.getMaListe();
        	

        	RandomAccessFile raf = new RandomAccessFile("src/main/resources/arbre.bin","rw");
			interns = intern.getMaListe();			
			System.out.println(interns);			// Parcours de chaque stagiare pour créer un Node et l'écrire dans le fichier
			for (int i = 0; i < interns.size(); i++) {				// Crée un noeud avec le stagiaire
				Node node = new Node(interns.get(i));				// Écrit le noeud dans le fichier binaire
				node.addNode(interns.get(i), raf);
			}
            System.out.println("creation OK");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erreur: " + e.getMessage());
        }
        
        launch();
    }


}
