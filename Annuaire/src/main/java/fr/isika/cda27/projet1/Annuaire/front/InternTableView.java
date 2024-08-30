package fr.isika.cda27.projet1.Annuaire.front;

import fr.isika.cda27.projet1.Annuaire.back.Intern;
import fr.isika.cda27.projet1.Annuaire.back.Tree;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class InternTableView extends TableView<Intern> {

    private FilteredList<Intern> filteredList;

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
        colNom.setCellFactory(new Callback<TableColumn<Intern, String>, TableCell<Intern, String>>() {
            @Override
            public TableCell<Intern, String> call(TableColumn<Intern, String> param) {
                return new TableCell<Intern, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.toUpperCase());
                        }
                    }
                };
            }
        });

        TableColumn<Intern, String> colPrenom = new TableColumn<>("Prénom");
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        colPrenom.setCellFactory(column -> new TableCell<Intern, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.substring(0, 1).toUpperCase() + item.substring(1).toLowerCase());
                }
            }
        });

        TableColumn<Intern, String> colDepartment = new TableColumn<>("Département");
        colDepartment.setCellValueFactory(new PropertyValueFactory<>("department"));

        TableColumn<Intern, String> colYear = new TableColumn<>("Année");
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Intern, String> colPromo = new TableColumn<>("Promo");
        colPromo.setCellValueFactory(new PropertyValueFactory<>("promo"));
        colPromo.setCellFactory(new Callback<TableColumn<Intern, String>, TableCell<Intern, String>>() {
            @Override
            public TableCell<Intern, String> call(TableColumn<Intern, String> param) {
                return new TableCell<Intern, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.toUpperCase());
                        }
                    }
                };
            }
        });


        // Ajouter les colonnes à la TableView
        this.getColumns().addAll(colNom, colPrenom, colDepartment, colYear, colPromo);
        this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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
    
    // Métholde pour filter la table avec un seul critère
    public void filterOne(String field, String filterText) {
        filteredList.setPredicate(intern -> {
            // Si le texte est vide, afficher tous les éléments
            if (filterText == null || filterText.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = filterText.toLowerCase();

            // Comparaison avec le texte du filtre en fonction du champ envoyé
            if (field == "name" && intern.getName().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filtrer par nom
            } else if (field == "firstname" && intern.getFirstname().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filtrer par prénom
            } else if (field == "department" && intern.getDepartment().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filtrer par département
            } else if (field == "year" && intern.getYear().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filtrer par année
            } else if (field == "promo" && intern.getPromo().toLowerCase().contains(lowerCaseFilter)) {
                return true; // Filtrer par promo
            }
            return false;
        });
    }
}
