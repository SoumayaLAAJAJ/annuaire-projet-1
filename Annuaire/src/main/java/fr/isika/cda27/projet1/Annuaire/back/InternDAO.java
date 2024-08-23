package model;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//import fr.isika.cda27.projet1.Annuaire.back.Intern;

//import fr.isika.cda27.projet1.Annuaire.back.Intern;

//import fr.isika.cda27.projet1.Annuaire.back.Intern;

public class InternDAO {
	// declaration des variables
	String path = "C:\\Users\\valsm\\Downloads\\STAGIAIRES.DON";
	public String name;
	public String firstname;
	public String department;
	public String year;
	public String promo;
	public int compteur;
	public int data1;
	public char chara1;
// declaration de la liste
	public List<Intern> maListe;

	// constructeur pour les stagieres
	public InternDAO() throws IOException {
		super();
		// initialisations des variables
		name = "";
		firstname = "";
		department = "";
		year = "";
		promo = "";
		// path pour la Bdd
		String path = "C:\\Users\\valsm\\Downloads\\STAGIAIRES.DON";
		// input du fichier binaire 
		FileInputStream inputstream = new FileInputStream(path);
		InputStreamReader Fileinputstream = new InputStreamReader(inputstream);
		// lecture des data
		int data = Fileinputstream.read();
		// conversion des bytes en characters
		char chara = (char) data;
		// initialisation de la liste
		maListe = new ArrayList<Intern>();
		compteur = 0;
		// tant que des données sont dispo on tourne la boucle
		while (data != -1) {
			// si le charactere est un retour a la ligne ou * et retour a la ligne
		if ( data== 10 || (data==42 && Fileinputstream.read()==10) ) {
					compteur = compteur + 1; //compteur pour les données
				}
		// si l'ensemble de charactere est une etoile et un retour a la ligne
		else if(data==42 && Fileinputstream.read()==10){
			// ajout du stagière
					maListe.add(new Intern(name,firstname,department, year, promo));		
					// reset des variables
					compteur=0;				
					name = "";
					firstname = "";
					department = "";
					year = "";
					promo = "";

		}

		else {
// switch pour proprement mettre chaque données
				switch (compteur) {
				case 0:
					name = name + chara;
					break;
				case 1:
					firstname = firstname + chara;
					break;
				case 2:
					department = department + chara;
					break;
				case 3:
					year = year + chara;
					break;
				case 4:
					promo = promo + chara;
					break;
				case 5:

					break;
				}
			}
// lecture du charactere suivant
			data = Fileinputstream.read();
			chara = (char) data;

		}

	}
// liste des stagières
	public List<Personne> getMaListe() {
		return maListe;
	}

	public void setMaListe(ArrayList<Personne> maListe) {
		this.maListe = maListe;
	}

	// Ici, on ajouterait le CRUD

}
