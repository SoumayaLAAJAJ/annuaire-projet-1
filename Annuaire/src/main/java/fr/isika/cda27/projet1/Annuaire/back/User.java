package fr.isika.cda27.projet1.Annuaire.back;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Cette classe représente un utilisateur de l'application. Elle inclut les
 * informations de connexion, telles que le nom d'utilisateur, le mot de passe
 * et le rôle de l'utilisateur.
 */
public class User {
	private String username;
	private String password;
	private boolean roleAdmin;

	/**
	 * Constructeur permettant de créer un utilisateur avec tous les paramètres
	 * nécessaires.
	 * 
	 * @param username  Le nom d'utilisateur de l'utilisateur
	 * @param password  Le mot de passe de l'utilisateur
	 * @param roleAdmin Indique si l'utilisateur a le rôle d'administrateur
	 */
	public User(String username, String password, boolean roleAdmin) {
		this.username = username;
		this.password = password;
		this.roleAdmin = roleAdmin;
	}

	/**
	 * Constructeur permettant de créer un utilisateur sans initialiser les attributs.
	 */
	public User() {
	}

	/**
	 * Retourne le nom d'utilisateur.
	 * 
	 * @return le nom d'utilisateur
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Indique si l'utilisateur a le rôle d'administrateur.
	 * 
	 * @return true si l'utilisateur est administrateur, sinon false.
	 */
	public boolean isRoleAdmin() {
		return roleAdmin;
	}

	/**
	 * Créé un nouvel utilisateur en enregistrant ses informations dans un
	 * fichier.
	 * 
	 * @param filePath Le chemin du fichier où les informations de l'utilisateur
	 *                 seront enregistrées.
	 * @return true si l'inscription a réussi, sinon false.
	 */
	public boolean signUp(String filePath) {
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
			writer.write(this.username + ";" + this.password + ";" + this.roleAdmin + "\n");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Connecte un utilisateur en vérifiant les informations de connexion dans le fichier.
	 * 
	 * @param filePath Le chemin du fichier où les informations de l'utilisateur
	 *                 sont stockées
	 * @param username Le nom d'utilisateur pour la connexion
	 * @param password Le mot de passe pour la connexion
	 * @return un objet User si la connexion réussit, sinon null
	 */
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
