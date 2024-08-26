package fr.isika.cda27.projet1.Annuaire.front;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import fr.isika.cda27.projet1.Annuaire.back.BinaryFileReader;
import fr.isika.cda27.projet1.Annuaire.back.BinaryFileWriter;
import fr.isika.cda27.projet1.Annuaire.back.Intern;
import fr.isika.cda27.projet1.Annuaire.back.InternDAO;
import fr.isika.cda27.projet1.Annuaire.back.Tree;
import fr.isika.cda27.projet1.Annuaire.back.TreeBuilder;
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
        try {
        	InternDAO intern = new InternDAO();
        	List<Intern> interns = intern.getMaListe();
        	
        	
        	TreeBuilder treeBuilder = new TreeBuilder();
        	treeBuilder.buildTreeFromInterns(interns);
        	
        	Tree tree = treeBuilder.getTree();
        	saveTreeToBinaryFile(tree, "src/main/resources/arbre.bin");

            System.out.println("creation OK");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("erreur: " + e.getMessage());
        }
        
        launch();
    }

    // Méthode pour sauvegarder l'arbre en fichier binaire
    private static void saveTreeToBinaryFile(Tree tree, String filePath) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(tree);
        }
    }
}
