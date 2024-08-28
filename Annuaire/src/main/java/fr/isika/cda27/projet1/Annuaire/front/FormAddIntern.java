package fr.isika.cda27.projet1.Annuaire.front;


import java.io.IOException;
import java.io.RandomAccessFile;

import fr.isika.cda27.projet1.Annuaire.back.Intern;
import fr.isika.cda27.projet1.Annuaire.back.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FormAddIntern extends HBox{
	
	Label title = new Label("Ajouter un stagiaire");
	VBox container = new VBox();
	
	Label name = new Label("Nom");	
	Label firstname = new Label("Prénom");
	Label department = new Label("Département");
	Label promo = new Label("Promotion");
	Label year = new Label("Année");
	public TextField nameTxtfield = new TextField();
	public TextField firstnameTxtfield = new TextField();
	public TextField departmentTxtfield = new TextField();
	public TextField promoTxtField = new TextField();
	public ChoiceBox<String> yearChoiceBox = new ChoiceBox<String>();
	Button add = new Button("Ajouter");

	
	VBox nameBox = new VBox();
	VBox firstnameBox= new VBox();
	VBox departmentBox = new VBox();
	VBox promoBox= new VBox();
	VBox yearBox= new VBox();

	GridPane form = new GridPane();
	private User loggedInUser;
	
	
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
        
        
        add.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			
			public void handle(ActionEvent event) {
				Intern jhonny = new Intern();// TODO Auto-generated method stub
				
				try {
					RandomAccessFile raf = new RandomAccessFile("src/main/resources/arbre.bin","rw");
					raf.seek(raf.length());
					raf.writeChars(jhonny.formatString(nameTxtfield.getText(), 20));
					raf.writeChars(jhonny.formatString(firstnameTxtfield.getText(), 20));
					raf.writeChars(jhonny.formatString(departmentTxtfield.getText(), 2));
					raf.writeChars(jhonny.formatString(yearChoiceBox.getValue(), 4));
					raf.writeChars(jhonny.formatString(promoTxtField.getText(),8));
					raf.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} );
		container.getChildren().addAll(searchBar, form);
		container.setPadding(new Insets(0, 50, 40, 50));
		
		LeftPane leftPane = new LeftPane(loggedInUser);
		this.getChildren().addAll(leftPane, container);
		
	}



}
