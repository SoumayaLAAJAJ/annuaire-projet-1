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

        
////        try {
////        	Tree tree = new Tree();
////        	// creation du random access file pour lire et se déplacer dans le fichier binaire
////            // lecture des données via le fichier binaire : on a crée la méthode un peu plus bas / cf explications
////            //List<Intern> internList = readAllInternsFromBinFile(raf);
////            // pour rappel, observableArrayList permet de créer une liste "dynamique' : JavaFX va surveiller les potentiels changements et MAJ automatiquement la liste en fonction
////           // myObservableArrayList = FXCollections.observableArrayList(internList);
////        } catch (IOException e) {
////            e.printStackTrace();
////            // si erreur, on crée une liste vide comme ça on a quand même le front qui s'affiche
////            myObservableArrayList = FXCollections.observableArrayList(); 
////        }
//
//        
//        // Initialiser la liste observable avec les données de l'arbre binaire
//        myObservableArrayList = FXCollections.observableArrayList(myObservableArrayList);
//
//        // TABLEVIEW
//        TableView<Intern> tableView = new TableView<>(myObservableArrayList);
//
//        TableColumn<Intern, String> colNom = new TableColumn<>("Nom");
//        colNom.setCellValueFactory(new PropertyValueFactory<>("name"));
//
//        TableColumn<Intern, String> colPrenom = new TableColumn<>("Prénom");
//        colPrenom.setCellValueFactory(new PropertyValueFactory<>("firstname"));
//
//        TableColumn<Intern, String> colDepartment = new TableColumn<>("Département");
//        colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
//
//        TableColumn<Intern, String> colYear = new TableColumn<>("Année");
//        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
//
//        TableColumn<Intern, String> colPromo = new TableColumn<>("Promo");
//        colPromo.setCellValueFactory(new PropertyValueFactory<>("promo"));
//
//        tableView.getColumns().addAll(colNom, colPrenom, colDepartment, colYear, colPromo);
//        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//
//        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldvalue, newValue) -> selectedIntern = newValue);
//        
//        // Permettre au TableView d'occuper tout l'espace disponible
//        VBox.setVgrow(tableView, Priority.ALWAYS);
//
//        // Ajouter la table au conteneur de droite
//        rightContainer.getChildren().add(tableView);

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
    
//    private List<Intern> readAllInternsFromBinFile(RandomAccessFile raf) throws IOException {
//        List<Intern> internList = new ArrayList<>();
//        raf.seek(0); // Commencer au début du fichier
//
//        while (raf.getFilePointer() + Intern.RECORD_LENGTH <= raf.length()) {
//            Intern intern = readInternFromFile(raf);
//            if (intern != null) {
//                internList.add(intern);
//            }
//        }
//        return internList;
//    }
//
//    private Intern readInternFromFile(RandomAccessFile raf) throws IOException {
//        // Vérification de s'il y a de la data à lire dans le fichier binaire
//        if (raf.getFilePointer() + Intern.RECORD_LENGTH > raf.length()) {
//        	// on retourne null s'il n'y a pas assez de data
//            return null;  
//        }
//
//        String name = "";
//        String firstname = "";
//        String department = "";
//        String year = "";
//        String promo = "";
//
//        // Lecture des champs du stagiaire
//        for (int j = 0; j < Intern.NAME_LENGTH; j++) {
//            name += raf.readChar();
//        }
//        for (int j = 0; j < Intern.FIRSTNAME_LENGTH; j++) {
//            firstname += raf.readChar();
//        }
//        for (int j = 0; j < Intern.DEPARTMENT_LENGTH; j++) {
//            department += raf.readChar();
//        }
//        for (int j = 0; j < Intern.YEAR_LENGTH; j++) {
//            year += raf.readChar();
//        }
//        for (int j = 0; j < Intern.PROMO_LENGTH; j++) {
//            promo += raf.readChar();
//        }
//
//        // Lecture des indices des enfants : 12 est la taille des 3 int 
//        if (raf.getFilePointer() + 12 > raf.length()) { 
//            return null;
//        }
//
//        int leftChild = raf.readInt();
//        int rightChild = raf.readInt();
//        int next = raf.readInt();
//
//        return new Intern(name.trim(), firstname.trim(), department.trim(), year.trim(), promo.trim());
//    }
//
//    
//    
//    private static void readNodeFromBinFile(RandomAccessFile raf, int index) {
//		String name = "";
//		String firstname = "";
//		String department = "";
//		String year = "";
//		String promo = "";
//		int leftChild = 0;
//		int rightChild = 0;
//		int next = 0;
//
//		try {
//			// read Nom
//			raf.seek(index);
//			System.out.println(raf.getFilePointer());
//
//			for (int j = 0; j < Intern.NAME_LENGTH; j++) {
//				name += raf.readChar();
//			}
//			name = name.trim();
//			System.out.println(name);
//
//			// read Prenom
//			for (int j = 0; j < (Intern.FIRSTNAME_LENGTH); j++) {
//				firstname += raf.readChar();
//			}
//			firstname = firstname.trim();
//			System.out.println(firstname);
//			
//			// read Department
//			for (int j = 0; j < (Intern.DEPARTMENT_LENGTH); j++) {
//				department += raf.readChar();
//			}
//			department = department.trim();
//			System.out.println(department);
//			
//			// read année
//			for (int j = 0; j < (Intern.YEAR_LENGTH); j++) {
//				year += raf.readChar();
//			}
//			year = year.trim();
//			System.out.println(year);
//			
//			// read promo
//			for (int j = 0; j < (Intern.PROMO_LENGTH); j++) {
//				promo += raf.readChar();
//			}
//			promo = promo.trim();
//			System.out.println(promo);
//			
//			// read left child
//			leftChild = raf.readInt();
//			System.out.println(leftChild);
//			
//			// read right child
//			rightChild = raf.readInt();
//			System.out.println(rightChild);
//
//			// read next child
//			next = raf.readInt();
//			System.out.println(next);
//
//			System.out.println(raf.getFilePointer());
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}
}



