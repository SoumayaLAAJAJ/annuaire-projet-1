package fr.isika.cda27.projet1.Annuaire.back;

import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

public class Node implements Serializable {
	// private static final long serialVersionUID = 1L;
	private Intern key;
	private int leftChild;
	private int rightChild;
	private int doublon;

	public Node(Intern key, int leftChild, int rightChild) {
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

	public int getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(int leftChild) {
		this.leftChild = leftChild;
	}

	public int getRightChild() {
		return rightChild;
	}

	public void setRightChild(int rightChild) {
		this.rightChild = rightChild;
	}

	/**
	 * **************AJOUT D'UN NOEUD DANS L'ARBRE : *************** - 1ère étape :
	 * définir la méthode d'ajout de noeud dans la classe Node - 2ème étape : voir
	 * dans la classe Tree
	 * 
	 * @param intern
	 */
	public void addNode(Intern intern, RandomAccessFile raf) {
		/**
		 * SI le stagiaire de la racine > celui qui est comparé...
		 */
		if (this.key.getName().compareTo(intern.getName()) > 0) {
			/**
			 * ...et que dans ce cas, le fils gauche du noeud racine (key) est null...
			 */
			if (this.leftChild== -1) {
				/**
				 * ... ALORS on ajoute le stagiaire au fils gauche (condition d'arrêt)
				 */
				//on recule le curseur de 12 octet
				//raf.seek(raf.getFilePointer() - 12);
				//raf.writeInt(raf.length() / Intern.RECORD_LENGTH); //index du noeud
				//je me remets à la fin du fichier
				//raf.seek(raf.length());
				//j'écris le nouveau noeud
				//intern.writeToRandomAccessFile(raf);
				//raf.writeInt(-1);
				//raf.writeInt(-1);
				//raf.writeInt(-1);
				
			//	this.leftChild = new Node(intern);
				/**
				 * Mais si le stagiaire de la racine est toujours > celui qui est comparé MAIS
				 * que le fils gauche est déjà rempli
				 */
			} else { //fils gauche non vide
				/**
				 * alors on continue le traitement sur le noeud intermédiaire jusqu'à trouver un
				 * noeud null ( => récursivité)
				 */
				// on se déplace à la position fils gauche
				//raf.seek(this.leftChild *  Intern.RECORD_LENGTH);
				//Node leftNode = //lire un noeud
				//leftNode.addNode(intern, raf);
			}
			/**
			 * SI le stagiaire de la racine < celui qui est comparé...
			 */
		} else if (this.key.compareTo(intern) < 0) { //meme chose mais on remont de -8 octet
			/**
			 * ...et que dans ce cas le fils droit est null...
			 */
			//if (this.rightChild == ) {
				/**
				 * ...ALORS on ajoute le stagiaire dans ce noeud (condition d'arrêt)
				 */
				//this.rightChild = new Node(intern);
				/**
				 * si le fils droit est déjà rempli, alors récursivité
				 */
			} else {
				//this.rightChild.addNode(intern);
			}
			/**
			 * Dans le cas où il y a un doublon, on envoie une erreur pour stopper le
			 * processus (VOIR AVEC LA TEAM S'ILS PENSENT QU'IL EST PLUS PERTINENT DE METTRE
			 * UN TRY/CATCH)
			 */
		} /*else {
			/// meme chose maios on remonte de 4
		}*/
//}

	/**
	 * **************AFFICHAGE PAR ORDRE ALPHABETIQUE SELON PARCOURS
	 * INFIXE************* - 1ere étape : définir la méthode d'affichage d'un Node
	 * selon son emplacement dans l'arbre - 2eme étape : voir dans la classe Tree
	 * 
	 * infixe : leftChild - Node - rightChild
	 * 
	 */
	public void displayNode() {
		/**
		 * Si le fils gauche est rempli, alors recursivité : On continue d'afficher les
		 * fils gauches jusqu'à ce qu'on tombe sur une feuille
		 */
		//if (this.leftChild != null) {
			//this.leftChild.displayNode();
		}

		/**
		 * Affichage de la racine
		 */
		//System.out.println(this.key.toString());

		/**
		 * Si le fils droit est rempli, alors recursivité
		 */
		//if (this.rightChild != null) {
			//this.rightChild.displayNode();
		//}
	//}

	/**
	 * Parcours de l'arbre en ordre infixe et ajout les Interns d'une liste fournie en paramètre
	 * @param internList
	 */
	/*public void collectInterns(List<Intern> internList) {
		if (leftChild != null) {
			leftChild.collectInterns(internList);
		}
		internList.add(key);
		if (rightChild != null) {
			rightChild.collectInterns(internList);
		}
	}*/

	public int getDoublon() {
		return doublon;
	}

	public void setDoublon(int doublon) {
		this.doublon = doublon;
	}

	//TODO methode lire un noeud qui retourne le noeud lu
	
	//TODO ecrire un noeud dans un fichier binaire
}
