package fr.isika.cda27.projet1.Annuaire.back;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Node {

	private Intern intern;
	private int leftChild;
	private int rightChild;
	private int next;

	public Node(Intern intern) {
		this.intern = intern;
		this.leftChild = -1;
		this.rightChild = -1;
		this.next = -1;
	}

	public Node(Intern intern, int leftChild, int rightChild) {
		this.intern = intern;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.next = -1;
	}

	public Node(Intern intern, int leftChild, int rightChild, int next) {
		this.intern = intern;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.next = next;
	}

	public Intern getIntern() {
		return intern;
	}

	public void setIntern(Intern intern) {
		this.intern = intern;
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

	public void writeToRandomAccessFile(RandomAccessFile raf) throws IOException {
		intern.writeToRandomAccessFile(raf);
		raf.writeInt(leftChild);
		raf.writeInt(rightChild);
		raf.writeInt(next);
}
}
//=======
//	/**
//	 * **************AJOUT D'UN NOEUD DANS L'ARBRE : *************** - 1ère étape :
//	 * définir la méthode d'ajout de noeud dans la classe Node - 2ème étape : voir
//	 * dans la classe Tree
//	 * 
//	 * @param intern
//	 * @return 
//	 * @throws IOException 
//	 */
//	
//	public void addNode(Intern intern, RandomAccessFile raf) throws IOException {
//		/**
//		 * SI le stagiaire de la racine > celui qui est comparé...
//		 */
//		if (this.key.getName().compareTo(intern.getName()) > 0) {
//			/**
//			 * ...et que dans ce cas, le fils gauche du noeud racine (key) est null...
//			 */
//			if (this.leftChild== -1) {
//				/**
//				 * ... ALORS on ajoute le stagiaire au fils gauche (condition d'arrêt)
//				 */
//				//on recule le curseur de 12 octet
//				raf.seek(raf.getFilePointer() - 12);
//				raf.writeLong((raf.length() / Intern.RECORD_LENGTH)); //index du noeud
//				//je me remets à la fin du fichier
//				raf.seek(raf.length());
//				//j'écris le nouveau noeud
//				intern.writeToRandomAccessFile(raf);
//				raf.writeInt(-1);
//				raf.writeInt(-1);
//				raf.writeInt(-1);
//				
//			//	this.leftChild = new Node(intern);
//				/**
//				 * Mais si le stagiaire de la racine est toujours > celui qui est comparé MAIS
//				 * que le fils gauche est déjà rempli
//				 */
//			} else { //fils gauche non vide
//				/**
//				 * alors on continue le traitement sur le noeud intermédiaire jusqu'à trouver un
//				 * noeud null ( => récursivité)
//				 */
//				// on se déplace à la position fils gauche
//				raf.seek(this.leftChild *  Intern.RECORD_LENGTH);
//				//Node leftNode = leftNode.ReadNode();//lire un noeud
//				//leftNode.addNode(intern, raf);
//			}
//			/**
//			 * SI le stagiaire de la racine < celui qui est comparé...
//			 */
//		}  //meme chose mais on remont de -8 octet
//			/**
//			 * ...et que dans ce cas le fils droit est null...
//			 */
//		else if (this.key.getName().compareTo(intern.getName()) < 0) {
//			if (this.rightChild == -1) {
//				/**
//				 * ...ALORS on ajoute le stagiaire dans ce noeud (condition d'arrêt)
//				 */
//				raf.seek(raf.getFilePointer() - 8);
//				raf.writeLong(raf.length()  / Intern.RECORD_LENGTH); //index du noeud
//				//je me remets à la fin du fichier
//				raf.seek(raf.length());
//				//j'écris le nouveau noeud
//				intern.writeToRandomAccessFile(raf);
//				raf.writeInt(-1);
//				raf.writeInt(-1);
//				raf.writeInt(-1);
//				
//			//	this.rightChild = new Node(intern);
//				/**
//				 * si le fils droit est déjà rempli, alors récursivité
//				 */
//			} else {
//				//this.rightChild.addNode(intern);
//				raf.seek(this.rightChild *  Intern.RECORD_LENGTH);
//			//	Node rightNode= rightNode.ReadNode();//lire un noeud
//		//		 rightChild.addNode(intern, raf);
//			}
//			/**
//			 * Dans le cas où il y a un doublon, on envoie une erreur pour stopper le
//			 * processus (VOIR AVEC LA TEAM S'ILS PENSENT QU'IL EST PLUS PERTINENT DE METTRE
//			 * UN TRY/CATCH)
//			 */
//		}
//		else {
//		 if (this.doublon == -1) { 
//			
//			/// meme chose maios on remonte de 4
//			raf.seek(raf.getFilePointer() - 4);
//			raf.writeLong(raf.length()  / Intern.RECORD_LENGTH); //index du noeud
//			//je me remets à la fin du fichier
//			raf.seek(raf.length());
//			//j'écris le nouveau noeud
//			intern.writeToRandomAccessFile(raf);
//			raf.writeInt(-1);
//			raf.writeInt(-1);
//			raf.writeInt(-1);
//			
//		//	this.rightChild = new Node(intern);
//			/**
//			 * si le fils droit est déjà rempli, alors récursivité
//			 */
//			} else {
//				//this.rightChild.addNode(intern);
//				raf.seek(this.doublon *  Intern.RECORD_LENGTH);
//				//Node doublonNode= doublonNode.ReadNode();//lire un noeud
//				//rightNode.addNode(intern, raf);
//		} 
//		System.out.println(intern);
//		}
//		}
//	
//
//	/**
//	 * **************AFFICHAGE PAR ORDRE ALPHABETIQUE SELON PARCOURS
//	 * INFIXE************* - 1ere étape : définir la méthode d'affichage d'un Node
//	 * selon son emplacement dans l'arbre - 2eme étape : voir dans la classe Tree
//	 * 
//	 * infixe : leftChild - Node - rightChild
//	 * 
//	 */
//	public void displayNode() {
//		/**
//		 * Si le fils gauche est rempli, alors recursivité : On continue d'afficher les
//		 * fils gauches jusqu'à ce qu'on tombe sur une feuille
//		 */
//		if (this.leftChild != -1) {
//			this.leftNode.displayNode();
//		}
//
//		/**
//		 * Affichage de la racine
//		 */
//		System.out.println(this.key.toString());
//
//		/**
//		 * Si le fils droit est rempli, alors recursivité
//		 */
//		if (this.rightChild != -1) {
//			this.rightNode.displayNode();
//		}
//	}
//
//	/**
//	 * Parcours de l'arbre en ordre infixe et ajout les Interns d'une liste fournie en paramètre
//	 * @param internList
//	 */
//	public void collectInterns(List<Intern> internList) {
//		if (leftChild != -1) {
//			leftNode.collectInterns(internList);
//		}
//		internList.add(key);
//		if (rightChild != -1) {
//			rightNode.collectInterns(internList);
//		}
//>>>>>>> 5acb57c39fb1ce1a60497805374c546d64131387
//	}
//
//	//TODO methode lire un noeud qui retourne le noeud lu
//	//public Node ReadNode() {
//		//return null;
//	//}
//	//TODO ecrire un noeud dans un fichier binaire (write to random access file)
//}
