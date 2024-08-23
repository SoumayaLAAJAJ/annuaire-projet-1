package fr.isika.cda27.projet1.Annuaire.back;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private Intern key;
	private Node leftChild;
	private Node rightChild;

	public Node(Intern key, Node leftChild, Node rightChild) {
		this.key = key;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	public Node(Intern key) {
		this.key = key;
	}

	public Intern getKey() {
		return key;
	}

	public void setKey(Intern key) {
		this.key = key;
	}

	public Node getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}

	public Node getRightChild() {
		return rightChild;
	}

	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}

	/**
	 * **************AJOUT D'UN NOEUD DANS L'ARBRE : *************** 
	 * - 1ère étape : définir la méthode d'ajout de noeud dans la classe Node 
	 * - 2ème étape : voir
	 * dans la classe Tree
	 * 
	 * @param intern
	 */
	public void addNode(Intern intern) {
		/**
		 * SI le stagiaire de la racine > celui qui est comparé...
		 */
		if (this.key.compareTo(intern) > 0) {
			/**
			 * ...et que dans ce cas, le fils gauche du noeud racine (key) est null...
			 */
			if (this.leftChild == null) {
				/**
				 * ... ALORS on ajoute le stagiaire au fils gauche (condition d'arrêt)
				 */
				this.leftChild = new Node(intern);
				/**
				 * Mais si le stagiaire de la racine est toujours > celui qui est comparé MAIS
				 * que le fils gauche est déjà rempli
				 */
			} else {
				/**
				 * alors on continue le traitement sur le noeud intermédiaire jusqu'à trouver un
				 * noeud null ( => récursivité)
				 */
				this.leftChild.addNode(intern);
			}
			/**
			 * SI le stagiaire de la racine < celui qui est comparé...
			 */
		} else if (this.key.compareTo(intern) < 0) {
			/**
			 * ...et que dans ce cas le fils droit est null...
			 */
			if (this.rightChild == null) {
				/**
				 * ...ALORS on ajoute le stagiaire dans ce noeud (condition d'arrêt)
				 */
				this.rightChild = new Node(intern);
				/**
				 * si le fils droit est déjà rempli, alors récursivité
				 */
			} else {
				this.rightChild.addNode(intern);
			}
			/**
			 * Dans le cas où il y a un doublon, on envoie une erreur pour stopper le
			 * processus (VOIR AVEC LA TEAM S'ILS PENSENT QU'IL EST PLUS PERTINENT DE METTRE
			 * UN TRY/CATCH)
			 */
		} else {
			throw new IllegalArgumentException("Le stagiaire suivant existe déjà: " + this.key.toString());
		}
	}

	/**
	 * **************AFFICHAGE PAR ORDRE ALPHABETIQUE SELON PARCOURS INFIXE*************
	 *  - 1ere étape : définir la méthode d'affichage d'un Node selon son emplacement dans l'arbre 
	 *  - 2eme étape : voir dans la classe Tree
	 * 
	 * infixe : leftChild - Node - rightChild
	 * 
	 */
	public void displayNode() {
		/**
		 * Si le fils gauche est rempli, alors recursivité : On continue d'afficher les
		 * fils gauches jusqu'à ce qu'on tombe sur une feuille
		 */
		if (this.leftChild != null) {
			this.leftChild.displayNode();
		}

		/**
		 * Affichage de la racine
		 */
		System.out.println(this.key.toString());

		/**
		 * Si le fils droit est rempli, alors recursivité
		 */
		if (this.rightChild != null) {
			this.rightChild.displayNode();
		}
	}

	/**
	 * **********RECHERCHE DANS L'ARBRE BINAIRE*******
	 * 
	 *  @return liste de resultats
	 */
	public List<Intern> searchIntern(Intern internToLookFor) {
		
		List<Intern> results = new ArrayList<>();
		
		/**
		 * On fait la récursive tant qu'on a pas trouvé dans les sous arbres
		 */
		if (this.leftChild != null) {
			results.addAll(this.leftChild.searchIntern(internToLookFor));
		}

		/**
		 * Si un Intern "match" (méthode crée dans Intern), alors on ajoute cet Intern
		 * dans le tableau result : condition d'arrêt de la récursive
		 */
		if (this.key.match(internToLookFor)) {
			results.add(this.key);
		}

		if (this.rightChild != null) {
			results.addAll(this.rightChild.searchIntern(internToLookFor));
		}

		return results;
	}
}
