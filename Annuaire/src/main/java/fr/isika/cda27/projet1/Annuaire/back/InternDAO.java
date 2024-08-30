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

    // Modif du code de Valentin avec utilisation du BufferedReader : lecture ligne par ligne en les assignant aux variables correspondantes et quand on rencontre un "*" on sait qu'on a terminé d'ajouter un stagiaire 
    // la même logique est gardée, c'est juste qu'à la place du FileInputStream  et InputStreamReader qui lisait chaque caractère, maintenant on lit la ligne complète 
    public InternDAO() throws IOException {
        maListe = new ArrayList<>();
        String path = "src/main/resources/STAGIAIRES-TEST.DON";
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            String name = "", firstname = "", department = "", year = "", promo = "";
            int compteur = 0;

            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) continue; 
                if (line.equals("*")) {
                    maListe.add(new Intern(name, firstname, department, promo, year));
                    name = firstname = department = year = promo = "";
                    compteur = 0;
                } else {
                    switch (compteur) {
                        case 0: name = line; break;
                        case 1: firstname = line; break;
                        case 2: department = line; break;
                        case 3: promo = line; break;
                        case 4: year = line; break;
                        
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
    
    public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getFirstname() {
		return firstname;
	}




	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}




	public String getDepartment() {
		return department;
	}




	public void setDepartment(String department) {
		this.department = department;
	}




	public String getYear() {
		return year;
	}




	public void setYear(String year) {
		this.year = year;
	}




	public String getPromo() {
		return promo;
	}




	public void setPromo(String promo) {
		this.promo = promo;
	}


}
