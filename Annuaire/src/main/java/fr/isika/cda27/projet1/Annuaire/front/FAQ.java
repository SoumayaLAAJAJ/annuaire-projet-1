package fr.isika.cda27.projet1.Annuaire.front;

import fr.isika.cda27.projet1.Annuaire.back.User;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FAQ extends HBox {
    private Accordion faqAccordion = new Accordion();

    public FAQ(App app, User loggedInUser) {
        super();
        initializeUI(app, loggedInUser);
    }

    private void initializeUI(App app, User loggedInUser) {
        this.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        LeftPane leftPane = new LeftPane(app, loggedInUser, "FAQ");

        // Créez les questions
        createQuestion("Comment ajouter un stagiaire ?", "/addIntern.png", 800, "Appuyez sur le bouton 'Ajouter un stagiaire', remplir le formulaire et appuyez sur le bouton 'Ajouter'. Attention : tous les champs doivent être remplis !");
        createQuestion("Comment rechercher un stagiaire ?", "/seekIntern.png", 800, "Appuyez sur la barre de recherche et remplissez un ou plusieurs champs selon votre recherche. Cliquez sur le bouton de recherche (loupe) et le stagiaire recherché s'affiche dans la liste du dessous.");
        createQuestion("Comment s'inscrire, se connecter et se déconnecter ?", "/log.png", 800, "Entrez votre username et votre mot de passe. Pour s'inscrire, appuyez sur le bouton 'S'inscrire' (en vert). Pour se connecter, appuyez sur le bouton 'Se connecter' (en bleu). Pour se déconnecter, appuyez sur le bouton 'Se déconnecter' en bas du menu de gauche.");
        createQuestion("Puis-je modifier ou supprimer un stagiaire ?", "/updateAndDelete.png", 600, "Uniquement si vous avez un profil administrateur. Pour supprimer, sélectionnez le stagiaire et appuyez sur l'icone de suppression (corbeille). Pour modifier, sélectionnez le stagiaire, double-cliquez sur la zone à modifier et une fois la modification apportée, appuyez sur l'icone de modification (feuille/stylo) pour enregistrer les modifications.");
        createQuestion("Comment afficher la liste des stagiaires ?", "/displayInternList.png", 600, "Appuyez sur le bouton 'Liste des stagiaires'.");
        createQuestion("Comment imprimer ma liste des stagiaires ?", "/printListInterns.png", 600, "Appuyez sur l'icône d'impression (imprimante).");

        VBox faqContainer = new VBox(20);
        faqContainer.getChildren().add(faqAccordion);
        
        // Définir une taille maximale et préférée pour faqContainer
        faqContainer.setMaxWidth(1000);  // Limite la largeur
        faqContainer.setPrefWidth(1000);  // Largeur préférée

        // Désactiver le défilement horizontal et activer le défilement vertical
        ScrollPane scrollPane = new ScrollPane(faqContainer);
        scrollPane.setFitToWidth(true);  // Ajuste la largeur
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Pas de barre de défilement horizontale
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Barre de défilement verticale si nécessaire

        this.getChildren().addAll(leftPane, scrollPane);

        faqContainer.setPadding(new javafx.geometry.Insets(10, 50, 10, 50));
        VBox.setVgrow(faqContainer, Priority.ALWAYS);
    }

    private void createQuestion(String title, String imagePath, double imageWidth, String answerText) {
        VBox content = new VBox(10);
        content.setFillWidth(true);

        ImageView imageView = new ImageView(new Image(getClass().getResource(imagePath).toExternalForm()));
        imageView.setFitWidth(imageWidth);
        imageView.setPreserveRatio(true);
        imageView.getStyleClass().add("image-view");

        Label answerLabel = new Label(answerText);
        answerLabel.setWrapText(true);
        answerLabel.getStyleClass().add("label");

        content.getChildren().addAll(imageView, answerLabel);

        TitledPane titledPane = new TitledPane(title, content);
        titledPane.setAnimated(false);
        titledPane.getStyleClass().add("titled-pane");
        faqAccordion.getPanes().add(titledPane);

        titledPane.setMaxHeight(Double.MAX_VALUE);
    }

    public Scene createAddView(App app, User loggedInUser) {
        return new Scene(this, 1300, 700);
    }
}





