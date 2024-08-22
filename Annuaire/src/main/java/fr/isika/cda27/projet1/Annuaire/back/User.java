package fr.isika.cda27.projet1.Annuaire.back;

public class User {
	private String username;
	private String password;
	private boolean roleAdmin;
	
	public User(String username, String password, boolean roleAdmin) {
		this.username = username;
		this.password = password;
		this.roleAdmin = roleAdmin;
	}

	public User() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRoleAdmin() {
		return roleAdmin;
	}

	public void setRoleAdmin(boolean roleAdmin) {
		this.roleAdmin = roleAdmin;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", roleAdmin=" + roleAdmin + "]";
	}
	
	
	
	
	
}
