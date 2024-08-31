package fr.isika.cda27.projet1.Annuaire.front;

import fr.isika.cda27.projet1.Annuaire.back.Intern;
import fr.isika.cda27.projet1.Annuaire.back.User;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class PageList extends BorderPane {

    public ObservableList<Intern> myObservableArrayList;
    public Intern selectedIntern;

    public Scene createListView(App app, User loggedInUser) {

        this.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // Ajout du menu de gauche
        LeftPane leftPane = new LeftPane(app, loggedInUser, "PageList");
        this.setLeft(leftPane);

        // Conteneur pour la partie droite de la page
        VBox rightContainer = new VBox();
        rightContainer.setPadding(new Insets(0, 40, 0, 40));
        rightContainer.setSpacing(10);

        // Ajout de la table view
        InternTableView tableView = new InternTableView();
        
        // Ajout de la barre de recherche
        SearchBar searchBar = new SearchBar(tableView);
        searchBar.setPadding(new Insets(30, 0, 25, 0));
        setTop(searchBar);

        // Ajout de l'icône d'impression
        Image printIcon = new Image(getClass().getResource("/printIcon.png").toExternalForm());
        ImageView printIconView = new ImageView(printIcon);
        printIconView.setFitWidth(20);
        printIconView.setPreserveRatio(true);

        // Ajout de l'icône de suppression
        Image binIcon = new Image(getClass().getResource("/binIcon.png").toExternalForm());
        ImageView binIconView = new ImageView(binIcon);
        binIconView.setFitWidth(20);
        binIconView.setPreserveRatio(true);

        // Ajout de l'icône d'édition
        Image editIcon = new Image(getClass().getResource("/editIcon.png").toExternalForm());
        ImageView editIconView = new ImageView(editIcon);
        editIconView.setFitWidth(20);
        editIconView.setPreserveRatio(true);

        // Création du bouton suppression et ajout de l'icône dans le bouton
        Button printBtn = new Button();
        printBtn.setGraphic(printIconView);
        printBtn.getStyleClass().add("searchIconBtn");
        printBtn.setVisible(true);
        
        // Création du bouton suppression et ajout de l'icône dans le bouton
        Button removeBtn = new Button();
        removeBtn.setGraphic(binIconView);
        removeBtn.getStyleClass().add("searchIconBtn");
        removeBtn.setVisible(true);

        // Création du bouton d'édition et ajout de l'icône dans le bouton
        Button editBtn = new Button();
        editBtn.setGraphic(editIconView);
        editBtn.getStyleClass().add("searchIconBtn");
        editBtn.setVisible(true);
        
        // Ajout de tooltips pour les boutons
        Tooltip editTooltipActivate = new Tooltip("Activer le mode édition");
        Tooltip editTooltipDeactivate = new Tooltip("Désactiver le mode édition");
        Tooltip printTooltip = new Tooltip("Imprimer la liste des stagiaires");
        Tooltip removeTooltip = new Tooltip("Supprimer un stagiaire");

        editBtn.setTooltip(editTooltipActivate);
        printBtn.setTooltip(printTooltip);
        removeBtn.setTooltip(removeTooltip);

        // Création d'un VBox pour les boutons
        VBox buttons = new VBox();
        buttons.setSpacing(20);
        buttons.getChildren().add(printBtn);
                
        // Création d'un HBOX pour la TableView et les boutons
        HBox tableViewBox = new HBox(tableView, buttons);
        tableViewBox.setSpacing(20);
        
        VBox.setVgrow(tableViewBox, Priority.ALWAYS);
        HBox.setHgrow(tableView, Priority.ALWAYS);

        // Si l'utilisateur est admin, on ajoute les boutons de suppression et d'édition
        if (loggedInUser.isRoleAdmin()) {
            buttons.getChildren().addAll(removeBtn, editBtn);
            
            // Gestion de l'action du bouton d'édition
            editBtn.setOnAction(event -> {
                // Bascule l'état d'édition et récupère le nouvel état
                boolean isEditMode = tableView.toggleEditMode();

                // Change le style du bouton en fonction de l'état d'édition
                if (isEditMode) {
                    editBtn.getStyleClass().remove("searchIconBtn");
                    editBtn.getStyleClass().add("editIconBtn");
                    editBtn.setTooltip(editTooltipDeactivate);

                } else {
                    editBtn.getStyleClass().remove("editIconBtn");
                    editBtn.getStyleClass().add("searchIconBtn");
                    editBtn.setTooltip(editTooltipActivate);

                }
            });
        }

        // Ajoute un espace en bas
        Region bottomPadding = new Region();
        bottomPadding.setMinHeight(20);
        
        rightContainer.getChildren().addAll(searchBar, tableViewBox, bottomPadding);

        // Ajoute le conteneur de droite au centre du BorderPane
        this.setCenter(rightContainer);

        return new Scene(this, 1300, 700);
    }
}
