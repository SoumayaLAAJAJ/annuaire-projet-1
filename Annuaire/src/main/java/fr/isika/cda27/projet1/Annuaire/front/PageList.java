package fr.isika.cda27.projet1.Annuaire.front;

import fr.isika.cda27.projet1.Annuaire.back.Intern;
import fr.isika.cda27.projet1.Annuaire.back.Tree;
import fr.isika.cda27.projet1.Annuaire.back.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class PageList extends BorderPane {

	public ObservableList<Intern> myObservableArrayList;
	public Intern selectedIntern;

	public PageList(User loggedInUser) {
		Tree tree = new Tree();
		tree.createBinfile();

		this.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

		// PANNEAU GAUCHE
		LeftPane leftPane = new LeftPane(loggedInUser);
		this.setLeft(leftPane);

		// Conteneur pour la barre de recherche et la table
		VBox rightContainer = new VBox();
		rightContainer.setPadding(new Insets(0, 40, 0, 40));
		rightContainer.setSpacing(10);

		// SEARCH BAR
		SearchBar searchBar = new SearchBar();
		searchBar.setPadding(new Insets(30, 0, 25, 0));
		setTop(searchBar);

		rightContainer.getChildren().add(searchBar);

		try {
            List<Intern> interns = tree.getInterns(); // Récupère la liste des stagiaires
            myObservableArrayList = FXCollections.observableArrayList(interns); // Initialise l'ObservableList
        } catch (IOException e) {
            e.printStackTrace();
            myObservableArrayList = FXCollections.observableArrayList(); // En cas d'erreur, initialise une liste vide
        }

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
        

		// Ajout de l'icône de suppression
		Image binIcon = new Image(getClass().getResource("/binIcon.png").toExternalForm());
		ImageView BinIconView = new ImageView(binIcon);
		BinIconView.setFitWidth(20);
		BinIconView.setPreserveRatio(true);

		// Ajout de l'icône d'édition
		Image editIcon = new Image(getClass().getResource("/editIcon.png").toExternalForm());
		ImageView editIconView = new ImageView(editIcon);
		editIconView.setFitWidth(20);
		editIconView.setPreserveRatio(true);

		// Création du bouton suppression et ajout de l'icône dans le bouton
		Button removeBtn = new Button();
		removeBtn.setGraphic(BinIconView);
		removeBtn.getStyleClass().add("searchIconBtn");
		removeBtn.setVisible(true);

		// Création du bouton d'édition et ajout de l'icône dans le bouton
		Button editBtn = new Button();
		editBtn.setGraphic(editIconView);
		editBtn.getStyleClass().add("searchIconBtn");
		editBtn.setVisible(true);

		VBox adminBtns = new VBox(removeBtn, editBtn);
		adminBtns.setSpacing(20);

		// Si user est admin :

		if (loggedInUser.isRoleAdmin()) {
			// Création de l'hbox contenant la table view et les boutons admin
			HBox tableViewBox = new HBox(tableView, adminBtns);
			tableViewBox.setSpacing(20);
			VBox.setVgrow(tableViewBox, Priority.ALWAYS);
			rightContainer.getChildren().add(tableViewBox);

		} else {

			// Si user est pas admin, ajout uniquement de tableview dans l'hbox
			rightContainer.getChildren().add(tableView);

		}

		// Permet à la tableview d'occuper tout l'espace disponible horizontalement
		HBox.setHgrow(tableView, Priority.ALWAYS);

		// Permettre au TableView d'occuper tout l'espace disponible verticalement
		VBox.setVgrow(tableView, Priority.ALWAYS);

		// Ajouter un espace en bas
		Region bottomPadding = new Region();
		bottomPadding.setMinHeight(20); // Ajuster la hauteur pour définir l'espace en bas
		rightContainer.getChildren().add(bottomPadding);

		// Ajouter le conteneur de droite au centre du BorderPane
		this.setCenter(rightContainer);
	}

}
