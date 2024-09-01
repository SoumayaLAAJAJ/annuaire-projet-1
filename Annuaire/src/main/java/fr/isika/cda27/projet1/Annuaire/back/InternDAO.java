package fr.isika.cda27.projet1.Annuaire.back;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InternDAO {
	private String name;
	private String firstname;
	private String department;
	private String year;
	private String promo;
	public List<Intern> maListe;

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

	@Override
	public String toString() {
		return "InternDAO [name=" + name + ", firstname=" + firstname + ", department=" + department + ", year=" + year
				+ ", promo=" + promo + ", maListe=" + maListe + "]";
	}

	public void setMaListe(List<Intern> maListe) {
		this.maListe = maListe;
	}

	public List<Intern> getMaListe() {
		return maListe;
	}

}
