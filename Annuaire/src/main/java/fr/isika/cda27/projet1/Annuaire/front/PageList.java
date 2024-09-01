package fr.isika.cda27.projet1.Annuaire.front;

import fr.isika.cda27.projet1.Annuaire.back.Intern;
import fr.isika.cda27.projet1.Annuaire.back.Node;
import fr.isika.cda27.projet1.Annuaire.back.Tree;
import fr.isika.cda27.projet1.Annuaire.back.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

/**
 * Cette classe est responsable de l'affichage de la liste des stagiaires dans l'application.
 * Elle fournit une interface utilisateur pour afficher, rechercher, imprimer, supprimer et modifier les informations des stagiaires.
 */
public class PageList extends BorderPane {

	public ObservableList<Intern> myObservableArrayList;
	public Intern selectedIntern;

    /**
     * Constructeur permettant d'initialiser la vue de la liste des stagiaires.
     *
     * @param app L'application
     * @param loggedInUser L'utilisateur actuellement connecté
     * @return La scène contenant la liste des stagiaires
     */
	public Scene createListView(App app, User loggedInUser) {

		this.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

		LeftPane leftPane = new LeftPane(app, loggedInUser, "PageList");
		this.setLeft(leftPane);

		VBox rightContainer = new VBox();
		rightContainer.setPadding(new Insets(0, 40, 0, 40));
		rightContainer.setSpacing(10);

		InternTableView tableView = new InternTableView();

		SearchBar searchBar = new SearchBar(tableView);
		searchBar.setPadding(new Insets(30, 0, 25, 0));
		setTop(searchBar);

		// Création des icônes
		Image printIcon = new Image(getClass().getResource("/printIcon.png").toExternalForm());
		ImageView printIconView = new ImageView(printIcon);
		printIconView.setFitWidth(20);
		printIconView.setPreserveRatio(true);

		Image binIcon = new Image(getClass().getResource("/binIcon.png").toExternalForm());
		ImageView binIconView = new ImageView(binIcon);
		binIconView.setFitWidth(20);
		binIconView.setPreserveRatio(true);

		Image editIcon = new Image(getClass().getResource("/editIcon.png").toExternalForm());
		ImageView editIconView = new ImageView(editIcon);
		editIconView.setFitWidth(20);
		editIconView.setPreserveRatio(true);

		// Création des boutons
		Button printBtn = new Button();
		printBtn.setGraphic(printIconView);
		printBtn.getStyleClass().add("iconBtn");
		printBtn.setVisible(true);
		printBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				PdfGenerator pdfGenerator = new PdfGenerator(tableView);
				String filePath = "liste_stagiaires.pdf";
				pdfGenerator.generatePdf(filePath);
			}
		});

		Button removeBtn = new Button();
		removeBtn.setGraphic(binIconView);
		removeBtn.getStyleClass().add("iconBtn");
		removeBtn.setVisible(true);
		removeBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Intern selectedIntern = tableView.getSelectionModel().getSelectedItem();

				if (selectedIntern != null) {
					try {
						Node node = new Node(selectedIntern);

						Tree tree = new Tree();
						node.deleteIntern(selectedIntern.getName(), selectedIntern.getFirstname(), tree.getRaf());

						List<Intern> updatedInterns = tree.getInterns();
						ObservableList<Intern> updatedObservableList = FXCollections
								.observableArrayList(updatedInterns);
						tableView.setItems(updatedObservableList);

					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setContentText("Veuillez sélectionner un stagiaire à supprimer");
					alert.showAndWait();
				}
			}
		});

		Button editBtn = new Button();
		editBtn.setGraphic(editIconView);
		editBtn.getStyleClass().add("iconBtn");
		editBtn.setVisible(true);

        // Création des tooltips
		Tooltip editTooltipActivate = new Tooltip("Activer le mode édition");
		Tooltip editTooltipDeactivate = new Tooltip("Désactiver le mode édition");
		Tooltip printTooltip = new Tooltip("Imprimer la liste des stagiaires");
		Tooltip removeTooltip = new Tooltip("Supprimer un stagiaire");

		editBtn.setTooltip(editTooltipActivate);
		printBtn.setTooltip(printTooltip);
		removeBtn.setTooltip(removeTooltip);

		VBox buttons = new VBox();
		buttons.setSpacing(20);
		buttons.getChildren().add(printBtn);

		HBox tableViewBox = new HBox(tableView, buttons);
		tableViewBox.setSpacing(20);

		VBox.setVgrow(tableViewBox, Priority.ALWAYS);
		HBox.setHgrow(tableView, Priority.ALWAYS);

        // Ajout des boutons de suppression et d'édition si l'utilisateur est administrateur
		if (loggedInUser.isRoleAdmin()) {
			buttons.getChildren().addAll(removeBtn, editBtn);

			editBtn.setOnAction(event -> {
                // Bascule l'état d'édition et récupère le nouvel état
				boolean isEditMode = tableView.toggleEditMode();

				// Change le style du bouton en fonction de l'état d'édition
				if (isEditMode) {
					editBtn.getStyleClass().remove("iconBtn");
					editBtn.getStyleClass().add("editIconBtn");
					editBtn.setTooltip(editTooltipDeactivate);

				} else {
					editBtn.getStyleClass().remove("editIconBtn");
					editBtn.getStyleClass().add("iconBtn");
					editBtn.setTooltip(editTooltipActivate);
				}
			});
		}

		Region bottomPadding = new Region();
		bottomPadding.setMinHeight(20);

		rightContainer.getChildren().addAll(searchBar, tableViewBox, bottomPadding);
		this.setCenter(rightContainer);

		return new Scene(this, 1300, 700);
	}
}
