package fr.isika.cda27.projet1.Annuaire.back;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.Objects;

public class Intern implements Serializable, Comparable<Intern>{
	//private static final long serialVersionUID = 1L;
    public static final int NAME_LENGTH = 20;
    public static final int FIRSTNAME_LENGTH = 20;
    public static final int DEPARTMENT_LENGTH = 10;
    public static final int YEAR_LENGTH = 4;
    public static final int PROMO_LENGTH = 4;
    public static final int RECORD_LENGTH = NAME_LENGTH + FIRSTNAME_LENGTH + DEPARTMENT_LENGTH + YEAR_LENGTH + PROMO_LENGTH + 20;
    
	private String name;
	private String firstname;
	private String department;
	private String year;
	private String promo;

	
	public Intern(String name, String firstname, String department, String year, String promo) {
		this.name = name;
		this.firstname = firstname;
		this.department = department;
		this.year = year;
		this.promo = promo;
	}
	

	public Intern() {
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


	/**
	 * Modification de la méthode compareTo appartenant à l'interface Comparable
	 * On compare d'abord le nom.
	 * - Si c'est le même nom, alors on compare le prénom
	 * - Si c'est le même prénom, alors on compare le département
	 * - Si c'est le même département, on compare l'année
	 * - Si c'est la même année, on compare la promo
	 * NB: la gestion de doublon se fait directement dans la méthode addNode(); 
	 *
	 */
	@Override
	public int compareTo(Intern o) {
		int result = this.name.compareTo(o.name);
		
		if(result == 0) {
			result = this.firstname.compareTo(o.firstname);
			if(result == 0) {
				result = this.department.compareTo(o.department);
				if(result == 0) {
					result = this.year.compareTo(o.year);
					if(result == 0) {
						result = this.promo.compareTo(o.promo);
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * Stockage des données dans le fichier binaire 
	 * 
	 * @param raf
	 * @throws IOException
	 */
	public void writeToRandomAccessFile(RandomAccessFile raf) throws IOException {
        raf.writeChars(formatString(name, NAME_LENGTH));
        raf.writeChars(formatString(firstname, FIRSTNAME_LENGTH));
        raf.writeChars(formatString(department, DEPARTMENT_LENGTH));
        raf.writeChars(formatString(year, YEAR_LENGTH));
        raf.writeChars(formatString(promo, PROMO_LENGTH));
    }

	/**
	 * Formatte les String pour faire en sorte qu'elles aient une longueur fixe en ajoutant des espaces à droite si nécessaire
	 * @param s
	 * @param length
	 * @return
	 */
    private String formatString(String s, int length) {
        if (s.length() >= length) {
            return s.substring(0, length);
        } else {
        	// le symbole %- est définii comme un marqueur de format et il permet l'alignement à gauche
        	// si s = abc et que length et 5 alors on aura "abc  " avec 2 espaces
            return String.format("%-" + length + "s", s);
        }
    }
	


}
