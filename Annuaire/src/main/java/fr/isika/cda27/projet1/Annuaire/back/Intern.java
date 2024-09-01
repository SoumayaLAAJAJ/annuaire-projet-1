package fr.isika.cda27.projet1.Annuaire.back;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Cette classe représente un stagiaire avec ses informations personnelles.
 */
public class Intern implements Comparable<Intern> {

	public static final int NAME_LENGTH = 15;
	public static final int FIRSTNAME_LENGTH = 15;
	public static final int DEPARTMENT_LENGTH = 6;
	public static final int YEAR_LENGTH = 4;
	public static final int PROMO_LENGTH = 20;
	public static final int RECORD_LENGTH = NAME_LENGTH * 2 + FIRSTNAME_LENGTH * 2 + DEPARTMENT_LENGTH * 2
			+ YEAR_LENGTH * 2 + PROMO_LENGTH * 2 + 12;

	private String name;
	private String firstname;
	private String department;
	private String year;
	private String promo;

	/**
	 * Constructeur permettant de créer un stagiaire avec tous les paramètres
	 * nécessaires.
	 * 
	 * @param name       Le nom du stagiaire
	 * @param firstname  Le prénom du stagiaire
	 * @param department Le département du stagiaire
	 * @param promo      La promotion du stagiaire
	 * @param year       L'année d'entrée du stagiaire
	 */
	public Intern(String name, String firstname, String department, String promo, String year) {
		this.name = name;
		this.firstname = firstname;
		this.department = department;
		this.promo = promo;
		this.year = year;
	}

	/**
	 * Constructeur permettant de créer un stagiaire sans initialiser les attributs.
	 * 
	 */
	public Intern() {
	}

	/**
	 * Retourne le nom du stagiaire.
	 * 
	 * @return Le nom du stagiaire
	 */
	public String getName() {
		return name;
	}

	/**
	 * Permet de définir le nom du stagiaire.
	 * 
	 * @param name Le nom à définir.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retourne le prénom du stagiaire.
	 * 
	 * @return Le prénom du stagiaire
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Permet de définir le prénom du stagiaire.
	 * 
	 * @param name Le prénom à définir
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * Retourne le département du stagiaire.
	 * 
	 * @return Le département du stagiaire
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * Permet de définir le département du stagiaire.
	 * 
	 * @param name Le département à définir
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * Retourne l'année d'entrée du stagiaire.
	 * 
	 * @return L'année d'entrée du stagiaire
	 */
	public String getYear() {
		return year;
	}

	/**
	 * Permet de définir l'année d'entrée du stagiaire.
	 * 
	 * @param name L'année à définir
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * Retourne la promo du stagiaire.
	 * 
	 * @return La promo du stagiaire
	 */
	public String getPromo() {
		return promo;
	}

	/**
	 * Permet de définir la promo du stagiaire.
	 * 
	 * @param name La promo à définir
	 */
	public void setPromo(String promo) {
		this.promo = promo;
	}

	/**
	 * Compare cet objet Intern avec un autre objet Intern. La comparaison se fait
	 * d'abord par nom, puis par prénom, département, année et promotion.
	 * 
	 * @param o le stagiaire à comparer
	 * @return Un nombre négatif, zéro ou un nombre positif si cet objet est
	 *         respectivement inférieur, égal ou supérieur à l'objet spécifié.
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
	 * Ecrit les informations du stagiaire dans un fichier binaire.
	 * 
	 * @param raf Le fichier binaire dans lequel écrire les données
	 * @throws IOException Si une erreur d'entrée/sortie se produit
	 */
	public void writeToRandomAccessFile(RandomAccessFile raf) throws IOException {
		raf.writeChars(formatString(this.name, NAME_LENGTH));
		raf.writeChars(formatString(this.firstname, FIRSTNAME_LENGTH));
		raf.writeChars(formatString(this.department, DEPARTMENT_LENGTH));
		raf.writeChars(formatString(this.promo, PROMO_LENGTH));
		raf.writeChars(formatString(this.year, YEAR_LENGTH));
	}

	/**
	 * Formatte une chaîne de caractères pour qu'elle ait une longueur fixe en
	 * ajoutant des espaces à droite si nécessaire.
	 * 
	 * @param s      La chaîne à formater
	 * @param length La longueur fixe souhaitée pour la chaîne
	 * @return La chaîne formatée à la longueur spécifiée
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
}