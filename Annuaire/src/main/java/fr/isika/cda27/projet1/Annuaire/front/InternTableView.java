package fr.isika.cda27.projet1.Annuaire.front;

import fr.isika.cda27.projet1.Annuaire.back.Intern;
import fr.isika.cda27.projet1.Annuaire.back.Node;
import fr.isika.cda27.projet1.Annuaire.back.Tree;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.util.List;

/**
 * Cette classe représente une table d'affichage des stagiaires. Elle permet de
 * visualiser, modifier et filtrer les données des stagiaires.
 */
public class InternTableView extends TableView<Intern> {

	private FilteredList<Intern> filteredList;
	private boolean isEditable = false;

	/**
	 * Constructeur permettant d'initialiser la table avec les stagiaires présents
	 * dans le fichier binaire.
	 */
	public InternTableView() {

		try {
			Tree tree = new Tree();
			List<Intern> interns = tree.getInterns();
			ObservableList<Intern> myObservableArrayList = FXCollections.observableArrayList(interns);
			filteredList = new FilteredList<>(myObservableArrayList, p -> true);
			this.setItems(filteredList);
		} catch (IOException e) {
			e.printStackTrace();
			filteredList = new FilteredList<>(FXCollections.observableArrayList(), p -> true);
			this.setItems(filteredList);
		}

		TableColumn<Intern, String> colNom = new TableColumn<>("Nom");
		colNom.setCellValueFactory(new PropertyValueFactory<>("name"));
		colNom.setCellFactory(TextFieldTableCell.forTableColumn());
		colNom.setOnEditCommit(event -> {
			Intern intern = event.getRowValue();
			Intern oldIntern = new Intern(intern.getName(), intern.getFirstname(), intern.getDepartment(),
					intern.getPromo(), intern.getYear());
			intern.setName(event.getNewValue());
			updatebinFile(oldIntern, intern);
		});

		TableColumn<Intern, String> colPrenom = new TableColumn<>("Prénom");
		colPrenom.setCellValueFactory(new PropertyValueFactory<>("firstname"));
		colPrenom.setCellFactory(TextFieldTableCell.forTableColumn());
		colPrenom.setOnEditCommit(event -> {
			Intern intern = event.getRowValue();
			Intern oldIntern = new Intern(intern.getName(), intern.getFirstname(), intern.getDepartment(),
					intern.getYear(), intern.getPromo());
			intern.setFirstname(event.getNewValue());
			updatebinFile(oldIntern, intern);
		});

		TableColumn<Intern, String> colDepartment = new TableColumn<>("Département");
		colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
		colDepartment.setCellFactory(TextFieldTableCell.forTableColumn());
		colDepartment.setOnEditCommit(event -> {
			Intern intern = event.getRowValue();
			Intern oldIntern = new Intern(intern.getName(), intern.getFirstname(), intern.getDepartment(),
					intern.getYear(), intern.getPromo());
			intern.setDepartment(event.getNewValue());
			updatebinFile(oldIntern, intern);
		});

		TableColumn<Intern, String> colYear = new TableColumn<>("Année");
		colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
		colYear.setCellFactory(TextFieldTableCell.forTableColumn());
		colYear.setOnEditCommit(event -> {
			Intern intern = event.getRowValue();
			Intern oldIntern = new Intern(intern.getName(), intern.getFirstname(), intern.getDepartment(),
					intern.getYear(), intern.getPromo());
			intern.setYear(event.getNewValue());
			updatebinFile(oldIntern, intern);
		});

		TableColumn<Intern, String> colPromo = new TableColumn<>("Promo");
		colPromo.setCellValueFactory(new PropertyValueFactory<>("promo"));
		colPromo.setCellFactory(TextFieldTableCell.forTableColumn());
		colPromo.setOnEditCommit(event -> {
			Intern intern = event.getRowValue();
			Intern oldIntern = new Intern(intern.getName(), intern.getFirstname(), intern.getDepartment(),
					intern.getYear(), intern.getPromo());
			intern.setPromo(event.getNewValue());
			updatebinFile(oldIntern, intern);
		});

		this.getColumns().addAll(colNom, colPrenom, colDepartment, colYear, colPromo);
		this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		// Configuration de l'édition
		setEditable(isEditable);
		colNom.setEditable(isEditable);
		colPrenom.setEditable(isEditable);
		colDepartment.setEditable(isEditable);
		colYear.setEditable(isEditable);
		colPromo.setEditable(isEditable);
	}

	/**
	 * Met à jour les informations du stagiaire dans le fichier binaire.
	 *
	 * @param oldIntern Le stagiaire avant modification
	 * @param newIntern Le stagiaire après modification
	 */
	private void updatebinFile(Intern oldIntern, Intern newIntern) {

		Intern intern = new Intern();
		Node node = new Node(intern);
		Tree tree = new Tree();

		try {
			node.updateIntern(tree.getRaf(), oldIntern.getName(), oldIntern.getFirstname(), newIntern);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Bascule le mode d'édition de la table.
	 *
	 * @return true si la table est éditable, false sinon.
	 */
	public boolean toggleEditMode() {
		isEditable = !isEditable;
		setEditable(isEditable);
		for (TableColumn<Intern, ?> column : this.getColumns()) {
			column.setEditable(isEditable);
		}
		return isEditable;
	}

	/**
	 * Filtre les données de la table selon plusieurs critères.
	 *
	 * @param nameFilter       Le filtre appliqué au nom
	 * @param firstnameFilter  Le filtre appliqué au prénom
	 * @param departmentFilter Le filtre appliqué au département
	 * @param yearFilter       Le filtre appliqué à l'année d'entrée
	 * @param promoFilter      Le filtre appliqué à la promotion
	 */
	public void filterTable(String nameFilter, String firstnameFilter, String departmentFilter, String yearFilter,
			String promoFilter) {
		filteredList.setPredicate(intern -> {
			// Si tous les critères sont vides, afficher tous les éléments
			if (nameFilter == null && firstnameFilter == null && departmentFilter == null && yearFilter == null
					&& promoFilter == null
					|| nameFilter.isEmpty() && firstnameFilter.isEmpty() && departmentFilter.isEmpty()
							&& yearFilter.isEmpty() && promoFilter.isEmpty()) {
				return true;
			}

			// Comparaison du texte dans la table avec le texte du filtre
			String lowerCaseNameFilter = nameFilter != null ? nameFilter.toLowerCase() : "";
			String lowerCaseFirstnameFilter = firstnameFilter != null ? firstnameFilter.toLowerCase() : "";
			String lowerCaseDepartmentFilter = departmentFilter != null ? departmentFilter.toLowerCase() : "";
			String lowerCaseYearFilter = yearFilter != null ? yearFilter.toLowerCase() : "";
			String lowerCasePromoFilter = promoFilter != null ? promoFilter.toLowerCase() : "";

			if (intern.getName().toLowerCase().contains(lowerCaseNameFilter)
					&& intern.getFirstname().toLowerCase().contains(lowerCaseFirstnameFilter)
					&& intern.getDepartment().toLowerCase().contains(lowerCaseDepartmentFilter)
					&& intern.getYear().toLowerCase().contains(lowerCaseYearFilter)
					&& intern.getPromo().toLowerCase().contains(lowerCasePromoFilter)) {
				return true;
			}

			return false;
		});
	}

	/**
	 * Filtre les données de la table selon un seul critère.
	 *
	 * @param field      Le nom du champ sur lequel appliquer le filtre
	 * @param filterText Le texte à filtrer
	 */
	public void filterOne(String field, String filterText) {
		filteredList.setPredicate(intern -> {

			if (filterText == null || filterText.isEmpty()) {
				return true;
			}

			String lowerCaseFilter = filterText.toLowerCase();

			if (field.equals("name") && intern.getName().toLowerCase().contains(lowerCaseFilter)) {
				return true;
			} else if (field.equals("firstname") && intern.getFirstname().toLowerCase().contains(lowerCaseFilter)) {
				return true;
			} else if (field.equals("department") && intern.getDepartment().toLowerCase().contains(lowerCaseFilter)) {
				return true;
			} else if (field.equals("year") && intern.getYear().toLowerCase().contains(lowerCaseFilter)) {
				return true;
			} else if (field.equals("promo") && intern.getPromo().toLowerCase().contains(lowerCaseFilter)) {
				return true;
			}
			return false;
		});
	}
	
	public FilteredList<Intern> getFilteredList() {
	    return filteredList;
	}

}
