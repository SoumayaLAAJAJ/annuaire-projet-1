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

public class InternTableView extends TableView<Intern> {

    private FilteredList<Intern> filteredList;
    private boolean isEditable = false; // État d'édition

    public InternTableView() {
        // Récupération des données et initialisation de l'ObservableList
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

        // Configuration des colonnes
        TableColumn<Intern, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNom.setCellFactory(TextFieldTableCell.forTableColumn());
        colNom.setOnEditCommit(event -> {
            Intern intern = event.getRowValue();
            Intern oldIntern = new Intern(intern.getName(),intern.getFirstname(),intern.getDepartment(),intern.getPromo(),intern.getYear());
            intern.setName(event.getNewValue());
            updatebinFile(oldIntern, intern);
        });

        TableColumn<Intern, String> colPrenom = new TableColumn<>("Prénom");
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        colPrenom.setCellFactory(TextFieldTableCell.forTableColumn());
        colPrenom.setOnEditCommit(event -> {
            Intern intern = event.getRowValue();
            Intern oldIntern = new Intern(intern.getName(),intern.getFirstname(),intern.getDepartment(),intern.getYear(),intern.getPromo());
            intern.setFirstname(event.getNewValue());
            updatebinFile(oldIntern, intern);
        });

        TableColumn<Intern, String> colDepartment = new TableColumn<>("Département");
        colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        colDepartment.setCellFactory(TextFieldTableCell.forTableColumn());
        colDepartment.setOnEditCommit(event -> {
            Intern intern = event.getRowValue();
            Intern oldIntern = new Intern(intern.getName(),intern.getFirstname(),intern.getDepartment(),intern.getYear(),intern.getPromo());
            intern.setDepartment(event.getNewValue());
            updatebinFile(oldIntern, intern);
        });

        TableColumn<Intern, String> colYear = new TableColumn<>("Année");
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colYear.setCellFactory(TextFieldTableCell.forTableColumn());
        colYear.setOnEditCommit(event -> {
            Intern intern = event.getRowValue();
            Intern oldIntern = new Intern(intern.getName(),intern.getFirstname(),intern.getDepartment(),intern.getYear(),intern.getPromo());
            intern.setYear(event.getNewValue());
            updatebinFile(oldIntern, intern);
        });

        TableColumn<Intern, String> colPromo = new TableColumn<>("Promo");
        colPromo.setCellValueFactory(new PropertyValueFactory<>("promo"));
        colPromo.setCellFactory(TextFieldTableCell.forTableColumn());
        colPromo.setOnEditCommit(event -> {
            Intern intern = event.getRowValue();
            Intern oldIntern = new Intern(intern.getName(),intern.getFirstname(),intern.getDepartment(),intern.getYear(),intern.getPromo());
            intern.setPromo(event.getNewValue());
            updatebinFile(oldIntern, intern);
        });

        // Ajouter les colonnes à la TableView
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
    

    // Met à jour le stagiaire dans le fichier binaire
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

	// Méthode pour basculer le mode d'édition
    public boolean toggleEditMode() {
        isEditable = !isEditable;
        setEditable(isEditable);
        for (TableColumn<Intern, ?> column : this.getColumns()) {
            column.setEditable(isEditable);
        }
        return isEditable;
    }
    



    // Méthode pour filtrer la table en multi-critères
    public void filterTable(String nameFilter, String firstnameFilter, String departmentFilter, String yearFilter, String promoFilter) {
        filteredList.setPredicate(intern -> {
            // Si tous les critères sont vides, afficher tous les éléments
            if (nameFilter == null && firstnameFilter == null && departmentFilter == null && yearFilter == null && promoFilter == null ||
                nameFilter.isEmpty() && firstnameFilter.isEmpty() && departmentFilter.isEmpty() && yearFilter.isEmpty() && promoFilter.isEmpty()) {
                return true;
            }

            // Comparaison du texte dans la table avec le texte du filtre
            String lowerCaseNameFilter = nameFilter != null ? nameFilter.toLowerCase() : "";
            String lowerCaseFirstnameFilter = firstnameFilter != null ? firstnameFilter.toLowerCase() : "";
            String lowerCaseDepartmentFilter = departmentFilter != null ? departmentFilter.toLowerCase() : "";
            String lowerCaseYearFilter = yearFilter != null ? yearFilter.toLowerCase() : "";
            String lowerCasePromoFilter = promoFilter != null ? promoFilter.toLowerCase() : "";

            if (intern.getName().toLowerCase().contains(lowerCaseNameFilter) &&
                intern.getFirstname().toLowerCase().contains(lowerCaseFirstnameFilter) &&
                intern.getDepartment().toLowerCase().contains(lowerCaseDepartmentFilter) &&
                intern.getYear().toLowerCase().contains(lowerCaseYearFilter) &&
                intern.getPromo().toLowerCase().contains(lowerCasePromoFilter)) {
                return true;
            }

            return false;
        });
    }

    // Méthode pour filtrer la table avec un seul critère
    public void filterOne(String field, String filterText) {
        filteredList.setPredicate(intern -> {
            // Si le texte est vide, afficher tous les éléments
            if (filterText == null || filterText.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = filterText.toLowerCase();

            // Comparaison avec le texte du filtre en fonction du champ envoyé
            if (field.equals("name") && intern.getName().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filtrer par nom
            } else if (field.equals("firstname") && intern.getFirstname().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filtrer par prénom
            } else if (field.equals("department") && intern.getDepartment().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filtrer par département
            } else if (field.equals("year") && intern.getYear().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filtrer par année
            } else if (field.equals("promo") && intern.getPromo().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filtrer par promo
            }
            return false;
        });
    }
    
}
