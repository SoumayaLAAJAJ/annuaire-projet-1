package fr.isika.cda27.projet1.Annuaire.front;


import fr.isika.cda27.projet1.Annuaire.back.User;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FormAddIntern extends HBox{
	
	private User loggedInUser;

	Label title = new Label("Ajouter un stagiaire");
	VBox container = new VBox();
	
	Label name = new Label("Nom");	
	Label firstname = new Label("Prénom");
	Label department = new Label("Département");
	Label promo = new Label("Promotion");
	Label year = new Label("Année");
	TextField nameTxtfield = new TextField();
    TextField firstnameTxtfield = new TextField();
    TextField departmentTxtfield = new TextField();
    TextField promoTxtField = new TextField();
	ChoiceBox<String> yearChoiceBox = new ChoiceBox<String>();
	Button add = new Button("Ajouter");

	
	VBox nameBox = new VBox();
	VBox firstnameBox= new VBox();
	VBox departmentBox = new VBox();
	VBox promoBox= new VBox();
	VBox yearBox= new VBox();

	GridPane form = new GridPane();
	
	
	public FormAddIntern(User loggedInUser) {
		super();
		this.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
		add.getStyleClass().add("specific-button");
		title.setStyle("-fx-font-weight: bold; -fx-font-family: 'Inter'; -fx-font-size: 28px;");
		
		// SEARCH BAR
        SearchBar searchBar = new SearchBar();
        
        nameBox.getChildren().addAll(name, nameTxtfield);
        firstnameBox.getChildren().addAll(firstname, firstnameTxtfield);
        departmentBox.getChildren().addAll(department, departmentTxtfield);
        promoBox.getChildren().addAll(promo, promoTxtField);
        
        for (int year = 2002; year <= 2024 ; year++) {
        	yearChoiceBox.getItems().addAll(String.valueOf(year));
        }
        
        yearBox.getChildren().addAll(year, yearChoiceBox);
        
        HBox buttonRow = new HBox();
        HBox space = new HBox();
        buttonRow.getChildren().addAll(space, add); 
        
        
        // Formulaire 
        form.addRow(0, title);
        form.addRow(1, nameBox, firstnameBox, departmentBox);
        form.addRow(2, yearBox, promoBox);
        form.addRow(3, buttonRow);
        
        form.setVgap(30);
  	  	form.setHgap(100);
        
        
        
		container.getChildren().addAll(searchBar, form);
		container.setPadding(new Insets(0, 50, 40, 50));
		
		LeftPane leftPane = new LeftPane(loggedInUser);
		this.getChildren().addAll(leftPane, container);
		
	}

}
