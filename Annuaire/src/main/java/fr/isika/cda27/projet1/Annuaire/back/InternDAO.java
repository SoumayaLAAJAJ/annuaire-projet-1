package fr.isika.cda27.projet1.Annuaire.back;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe permet l'accès et la lecture des données des stagiaires depuis
 * un fichier texte. Les données sont lues depuis un fichier DON et stockées
 * dans une liste d'objets Intern.
 */
public class InternDAO {

	public List<Intern> maListe;

	/**
	 * Constructeur permettant d'initialiser la liste des stagiaires en lisant les
	 * données depuis un fichier texte.
	 * 
	 * @throws IOException Si une erreur d'entrée/sortie se produit lors de la
	 *                     lecture du fichier
	 */
	public InternDAO() throws IOException {
		
		maListe = new ArrayList<>();
		String path = "src/main/resources/STAGIAIRES.DON";
		
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			String line;
			String name = "", firstname = "", department = "", year = "", promo = "";
			int compteur = 0;

			while ((line = reader.readLine()) != null) {
				if (line.isEmpty())
					continue;
				if (line.equals("*")) {
					maListe.add(new Intern(name, firstname, department, promo, year));
					name = firstname = department = year = promo = "";
					compteur = 0;
				} else {
					switch (compteur) {
					case 0:
						name = line;
						break;
					case 1:
						firstname = line;
						break;
					case 2:
						department = line;
						break;
					case 3:
						promo = line;
						break;
					case 4:
						year = line;
						break;
					}
					
					compteur++;
				}
			}
		}
	}

	/**
	 * Retourne la liste des stagiaires.
	 * 
	 * @return La liste des stagiaires
	 */
	public List<Intern> getMaListe() {
		return maListe;
	}

}
