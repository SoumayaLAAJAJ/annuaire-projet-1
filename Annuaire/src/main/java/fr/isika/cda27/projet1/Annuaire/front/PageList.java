package fr.isika.cda27.projet1.Annuaire.front;

import fr.isika.cda27.projet1.Annuaire.back.Intern;
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
import java.util.ArrayList;
import java.util.List;

public class PageList extends BorderPane {

    public ObservableList<Intern> myObservableArrayList;
    public Intern selectedIntern;

    public PageList(User loggedInUser) {
    	
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
        rightContainer.getChildren().add(searchBar);

        // Charger l'arbre depuis le fichier binaire
        List<Intern> internList = new ArrayList<>();
        try {
            Tree tree = Tree.loadTreeFromBinaryFile("src/main/resources/arbre.bin");
            //internList = tree.getAllInterns();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Initialiser la liste observable avec les données de l'arbre binaire
        myObservableArrayList = FXCollections.observableArrayList(internList);

        // TABLEVIEW
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
}



