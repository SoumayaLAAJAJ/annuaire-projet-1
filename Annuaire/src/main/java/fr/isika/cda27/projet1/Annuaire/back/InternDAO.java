package fr.isika.cda27.projet1.Annuaire.back;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class InternDAO implements Comparable<InternDAO> {
    String path = "src/main/resources/STAGIAIRES.DON";
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
			String path = "src/main/resources/STAGIAIRES.DON";
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
		public List<Intern> getMaListe() {
			return maListe;
		}

		public void setMaListe(ArrayList<Intern> maListe) {
			this.maListe = maListe;
		}

		// Ici, on ajouterait le CRUD

	

    // Implémentation de la méthode compareTo pour comparer deux InternDAO
    @Override
    public int compareTo(InternDAO other) {
        // Supposons que vous vouliez comparer les InternDAO en fonction du nom du premier stagiaire
        // ou vous pouvez choisir un autre attribut pour la comparaison
        if (this.maListe.isEmpty() || other.getMaListe().isEmpty()) {
            return 0;
        }
        // Comparer les noms du premier stagiaire dans chaque liste
        return this.maListe.get(0).getName().compareTo(other.getMaListe().get(0).getName());
    }

    // Implémentation de la méthode match pour vérifier si un InternDAO correspond à un autre
    public boolean match(InternDAO other) {
        // Vérifie si les listes sont identiques (vous pouvez affiner cette logique en fonction de vos besoins)
        if (this.maListe.size() != other.getMaListe().size()) {
            return false;
        }
        for (int i = 0; i < this.maListe.size(); i++) {
            if (!this.maListe.get(i).equals(other.getMaListe().get(i))) {
                return false;
            }
        }
        return true;
    }
}


