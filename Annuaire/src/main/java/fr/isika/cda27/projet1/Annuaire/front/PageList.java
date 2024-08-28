package fr.isika.cda27.projet1.Annuaire.front;

import fr.isika.cda27.projet1.Annuaire.back.Intern;
import fr.isika.cda27.projet1.Annuaire.back.InternDAO;
import fr.isika.cda27.projet1.Annuaire.back.Tree;
import fr.isika.cda27.projet1.Annuaire.back.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class PageList extends BorderPane {

    public ObservableList<Intern> myObservableArrayList;
    public Intern selectedIntern;

    public PageList(User loggedInUser) {
    	createBinfile();
    	
        this.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        
        // PANNEAU GAUCHE
        LeftPane leftPane = new LeftPane(loggedInUser);
        this.setLeft(leftPane);

        // Conteneur pour la barre de recherche et la table
        VBox rightContainer = new VBox();
        rightContainer.setPadding(new Insets(0, 50, 0, 50)); 
        rightContainer.setSpacing(10); 

        // SEARCH BAR
        SearchBar searchBar = new SearchBar();
        searchBar.setPadding(new Insets(30, 0, 25, 0));
        setTop(searchBar);
       
        rightContainer.getChildren().add(searchBar);

        
        
        // APPEL READNODE
		//readNodeFromBinFile(raf, 312);


        


        // TABLEVIEW
        // Initialiser la liste observable avec les données de l'arbre binaire
        TableView<Intern> tableView = new TableView<>(myObservableArrayList);

        TableColumn<Intern, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Intern, String> colPrenom = new TableColumn<>("Prénom");
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("firstname"));

        TableColumn<Intern, String> colDepartment = new TableColumn<>("Département");
        colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));

        TableColumn<Intern, String> colYear = new TableColumn<>("Année");
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Intern, String> colPromo = new TableColumn<>("Promo");
        colPromo.setCellValueFactory(new PropertyValueFactory<>("promo"));

        tableView.getColumns().addAll(colNom, colPrenom, colDepartment, colYear, colPromo);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newValue) -> selectedIntern = newValue);
        
        // Permettre au TableView d'occuper tout l'espace disponible
        VBox.setVgrow(tableView, Priority.ALWAYS);

        // Ajouter la table au conteneur de droite
        rightContainer.getChildren().add(tableView);

        // Ajouter un espace en bas
        Region bottomPadding = new Region();
        bottomPadding.setMinHeight(30); // Ajuster la hauteur pour définir l'espace en bas
        rightContainer.getChildren().add(bottomPadding);

        // Ajouter le conteneur de droite au centre du BorderPane
        this.setCenter(rightContainer);
    }
    
    private void createBinfile() {
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

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    


}



