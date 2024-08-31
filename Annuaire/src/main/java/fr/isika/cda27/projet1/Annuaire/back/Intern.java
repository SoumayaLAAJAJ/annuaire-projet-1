package fr.isika.cda27.projet1.Annuaire.back;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Intern implements Comparable<Intern> {

	public static final int NAME_LENGTH = 15;
	public static final int FIRSTNAME_LENGTH = 15;
	public static final int DEPARTMENT_LENGTH = 6;
	public static final int YEAR_LENGTH = 4;
	public static final int PROMO_LENGTH = 20;
	public static final int RECORD_LENGTH = NAME_LENGTH*2 + FIRSTNAME_LENGTH*2 + DEPARTMENT_LENGTH*2 + YEAR_LENGTH*2+ PROMO_LENGTH*2 + 12;

	private String name;
	private String firstname;
	private String department;
	private String year;
	private String promo;

	public Intern(String name, String firstname, String department, String promo, String year) {
		this.name = name;
		this.firstname = firstname;
		this.department = department;
		this.promo = promo;
		this.year = year;

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
	 * Modification de la méthode compareTo appartenant à l'interface Comparable On
	 * compare d'abord le nom. - Si c'est le même nom, alors on compare le prénom -
	 * Si c'est le même prénom, alors on compare le département - Si c'est le même
	 * département, on compare l'année - Si c'est la même année, on compare la promo
	 * NB: la gestion de doublon se fait directement dans la méthode addNode();
	 *
	 */
	@Override
	public int compareTo(Intern o) {
		int result = this.name.compareTo(o.name);

		if (result == 0) {
			result = this.firstname.compareTo(o.firstname);
			if (result == 0) {
				result = this.department.compareTo(o.department);
				if (result == 0) {
					result = this.year.compareTo(o.year);
					if (result == 0) {
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
	// Méthode d'écriture dans un RandomAccessFile
	public void writeToRandomAccessFile(RandomAccessFile raf) throws IOException {
		raf.writeChars(formatString(this.name, NAME_LENGTH));
		raf.writeChars(formatString(this.firstname, FIRSTNAME_LENGTH));
		raf.writeChars(formatString(this.department, DEPARTMENT_LENGTH));
		raf.writeChars(formatString(this.promo, PROMO_LENGTH));
		raf.writeChars(formatString(this.year, YEAR_LENGTH));
	}

	/**
	 * Formatte les String pour faire en sorte qu'elles aient une longueur fixe en
	 * ajoutant des espaces à droite si nécessaire
	 * 
	 * @param s
	 * @param length
	 * @return String
	 */
	private String formatString(String s, int length) {
		String sLong = s;
		if (sLong.length() < length) {
			for (int i = s.length(); i < length; i++) {
				sLong += " ";
			}
		} else {
			sLong = sLong.substring(0, length);
		}

		return sLong;
	}

	@Override
	public String toString() {
		return "Intern [name=" + name + ", firstname=" + firstname + ", department=" + department + ", year=" + year
				+ ", promo=" + promo + "]";
	}

}