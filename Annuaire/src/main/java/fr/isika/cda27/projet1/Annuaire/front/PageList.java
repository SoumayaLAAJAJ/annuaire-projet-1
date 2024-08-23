package fr.isika.cda27.projet1.Annuaire.front;

import fr.isika.cda27.projet1.Annuaire.back.Intern;
import fr.isika.cda27.projet1.Annuaire.back.InternDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class PageList extends BorderPane {

    public ObservableList<Intern> myObservableArrayList;
    public Intern selectedIntern;

    public PageList() {
        setPadding(new Insets(0, 50, 40, 50));

        // SEARCH BAR
        SearchBar searchBar = new SearchBar();
        searchBar.setPadding(new Insets(30, 0, 35, 0));
        setTop(searchBar);

        // Initialiser la liste observable avec les données de InternDAO
        try {
            InternDAO internDAO = new InternDAO();
            myObservableArrayList = FXCollections.observableArrayList(internDAO.getMaListe());
        } catch (IOException e) {
            e.printStackTrace();
            myObservableArrayList = FXCollections.observableArrayList();
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

        setCenter(tableView);
    }
}

