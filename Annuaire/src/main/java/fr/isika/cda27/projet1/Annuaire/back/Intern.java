package fr.isika.cda27.projet1.Annuaire.back;

public class Intern {
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
	
	
	
	
	
	

}
