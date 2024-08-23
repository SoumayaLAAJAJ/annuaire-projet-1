package fr.isika.cda27.projet1.Annuaire.front;

import fr.isika.cda27.projet1.Annuaire.back.Intern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class PageList extends BorderPane {

	public ObservableList<Intern> myObservableArrayList;
	public Intern selectedIntern;

	public PageList() {
		setPadding(new Insets(0, 50, 40, 50));

		// SEARCH BAR
		SearchBar searchBar = new SearchBar();
		searchBar.setPadding(new Insets(30, 0, 35, 0));
		setTop(searchBar);

		// Données test
		myObservableArrayList = FXCollections.observableArrayList(
				new Intern("Brachotte", "Faustine", "75", "2024", "CDA27"),
				new Intern("Laajaj", "Soumaya", "75", "2024", "CDA27"),
				new Intern("Costabello", "Florent", "13", "2024", "CDA27"),
				new Intern("Smaniotto", "Valentin", "13", "2024", "CDA27"));

		// TABLEVIEW
		TableView<Intern> tableView = new TableView<Intern>(myObservableArrayList);

		TableColumn<Intern, String> colNom = new TableColumn<Intern, String>("Nom");
		colNom.setCellValueFactory(new PropertyValueFactory<Intern, String>("name"));

		TableColumn<Intern, String> colPrenom = new TableColumn<Intern, String>("Prénom");
		colPrenom.setCellValueFactory(new PropertyValueFactory<Intern, String>("firstname"));

		TableColumn<Intern, String> colDepartment = new TableColumn<Intern, String>("Département");
		colDepartment.setCellValueFactory(new PropertyValueFactory<Intern, String>("department"));

		TableColumn<Intern, String> colYear = new TableColumn<Intern, String>("Année");
		colYear.setCellValueFactory(new PropertyValueFactory<Intern, String>("year"));

		TableColumn<Intern, String> colPromo = new TableColumn<Intern, String>("Promo");
		colPromo.setCellValueFactory(new PropertyValueFactory<Intern, String>("promo"));

		tableView.getColumns().addAll(colNom, colPrenom, colDepartment, colYear, colPromo);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Intern>() {

			@Override
			public void changed(ObservableValue<? extends Intern> observable, Intern oldvalue, Intern newValue) {
				selectedIntern = newValue;
			}

		});

		setCenter(tableView);

	}

}
