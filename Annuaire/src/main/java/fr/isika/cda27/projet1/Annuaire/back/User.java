package fr.isika.cda27.projet1.Annuaire.back;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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

	public boolean signUp(String filePath) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
			writer.write(this.username + ";" + this.password + ";" + this.roleAdmin + "\n");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public User login(String filePath, String username, String password) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(";");
				if (parts.length == 3 && parts[0].equals(username) && parts[1].equals(password)) {
					boolean roleAdmin = Boolean.parseBoolean(parts[2]);
					return new User(username, password, roleAdmin);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
