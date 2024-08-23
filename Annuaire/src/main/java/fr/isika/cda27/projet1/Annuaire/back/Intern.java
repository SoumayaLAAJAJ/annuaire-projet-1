package fr.isika.cda27.projet1.Annuaire.back;

public class Intern implements Comparable<Intern>{
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

	@Override
	public String toString() {
		return "Intern [name=" + name + ", firstname=" + firstname + ", department=" + department + ", year=" + year
				+ ", promo=" + promo + "]";
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
	 * Méthode vérifiant si un attribut correspond à celui de la liste
	 * - SI la case du nom est rempli ET qu'il ne correspond pas à un nom de la liste, ALORS on retourne false
	 * - idem pour chaque argument (prenom, departement, année, promo)
	 * - Si au contraire, il est vide ou que les attributs correspondent : return true
	 * 
	 * @param o
	 * @return boolean 
	 */
	public boolean match(Intern o) {
		if(o.name != null && !this.name.equalsIgnoreCase(o.name)) {
			return false;
		}
		if(o.name != null && !this.name.equalsIgnoreCase(o.name)) {
			return false;
		}
		if(o.department != null && !this.department.equalsIgnoreCase(o.department)) {
			return false;
		}
		if(o.year != null && !this.year.equalsIgnoreCase(o.year)) {
			return false;
		}
		if(o.department != null && !this.department.equalsIgnoreCase(o.department)) {
			return false;
		}
		if(o.promo != null && !this.promo.equalsIgnoreCase(o.promo)) {
			return false;
		}
		return true;
	}
	


}
