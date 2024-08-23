package fr.isika.cda27.projet1.Annuaire.back;

import java.util.ArrayList;
import java.util.List;

public class Tree {
	private Node root;

	public Tree(Node root) {
		this.root = root;
	}

	public Tree() {
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public boolean isEmpty() {
		return (this.root == null);
	}

	/**
	 * AJOUT D'UN NOEUD DANS L'ARBRE : - 1ère étape : voir dans la classe Node -
	 * 2ème étape : -> Si l'arbre est vide, on ajoute un noeud racine avant de
	 * mettre en place la méthode addNode(); -> Sinon, on applique la méthode
	 * addNode(); directement * @param intern
	 */
	public void checkRootToAddNode(InternDAO intern) {
		try {
			if (isEmpty()) {
				this.root = new Node(intern);
			} else {
				this.root.addNode(intern);
			}
		} catch (IllegalArgumentException e) {
			System.err.println("Erreur: " + e.getMessage());
		}
		
	}

	/**
	 * AFFICHAGE PAR ORDRE ALPHABETIQUE SELON PARCOURS INFIXE - 1ere étape : Voir
	 * dans la classe Node - 2eme étape : -> SI l'arbre est vide, afficher un
	 * message dans la console -> SINON, afficher chaque noeud de l'arbre selon la
	 * méthode displayNode(); définie dans la classe Node
	 * 
	 */
	public void checkRootToDisplayNodes() {
		if (isEmpty()) {
			System.out.println("L'arbre est vide");
		} else {
			// mettre en arg la liste qu'on a initialiser dans l'arbre 
			this.root.displayNode();
		}
	}
	
	/**
	 * **********RECHERCHE DANS L'ARBRE BINAIRE*******
	 */
	public List<InternDAO> checkRootAndSearchIntern(InternDAO internToLookFor){
		/**
		 * Si l'arbre est vide, retourner une liste vide
		 */
		if(isEmpty()) {
			return new ArrayList<>();	
		}
		/**
		 * Sinon, faire la récursive jusqu'à trouver un match
		 */
		return root.searchIntern(internToLookFor);
	}
	

}
