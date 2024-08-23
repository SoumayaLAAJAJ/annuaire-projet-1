package fr.isika.cda27.projet1.Annuaire.front;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.isika.cda27.projet1.Annuaire.back.Intern;
import fr.isika.cda27.projet1.Annuaire.back.Tree;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

        Label label = new Label("Hello, JavaFX ");
        Scene scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
    	Tree abr = new Tree();
    	List<Intern> interns = new ArrayList<>();
    	
    	Collections.addAll(interns, 
    			new Intern("Laajaj", "Soumaya", "75", "2024", "CDA270"),
    			new Intern("Costabello", "Florent", "13", "2024", "CDA27"),
    			new Intern("Brachotte", "Faustine", "75", "2024", "CDA27"),
    			new Intern("Smaniotto", "Valentin", "95", "2024", "CDA27"),
    			new Intern("Laajaj", "Amelie", "75", "2024", "CDA279"), 
    			new Intern("Smaniotto", "Valentin", "95", "2024", "CDA27")
    			);
    	/**
    	 * Création de l'arbre
    	 */
    	for(Intern intern : interns) {
    		abr.checkRootToAddNode(intern);
    	}
    	
    	/**
    	 * Affichage de l'arbre
    	 */
    	abr.checkRootToDisplayNodes();
    	
    	/**
    	 * Recherche dans l'arbre
    	 */
    	List<Intern> laajajResults = abr.checkRootAndSearchIntern(new Intern("laajaj", null, null, null, null));
    	System.out.println("**correspondant à Laajaj : " + laajajResults);
    	
    	List<Intern> parisResults = abr.checkRootAndSearchIntern(new Intern(null, null, "75", null, null));
    	System.out.println("**résultats à Paris: " + parisResults);
    	
    	List<Intern> cda27Results = abr.checkRootAndSearchIntern(new Intern(null, null, null, null, "cda27"));
    	System.out.println("**resultats cda27" + cda27Results);
    	
        launch();
    }

}