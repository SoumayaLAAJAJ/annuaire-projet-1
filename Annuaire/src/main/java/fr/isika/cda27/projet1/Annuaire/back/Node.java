package fr.isika.cda27.projet1.Annuaire.back;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

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
		raf.writeInt(-1);
		raf.writeInt(-1);
		raf.writeInt(-1);
	}

	public Intern readNodeFromFile(RandomAccessFile raf, long position) throws IOException {

		Intern intern = new Intern();
		intern.setName(readFixedLengthString(raf, Intern.NAME_LENGTH));
		intern.setFirstname(readFixedLengthString(raf, Intern.FIRSTNAME_LENGTH));
		intern.setDepartment(readFixedLengthString(raf, Intern.DEPARTMENT_LENGTH));
		intern.setYear(readFixedLengthString(raf, Intern.YEAR_LENGTH));
		intern.setPromo(readFixedLengthString(raf, Intern.PROMO_LENGTH));
		// System.out.println("ICI" + intern);

		return intern;
	}

	private String readFixedLengthString(RandomAccessFile raf, int length) throws IOException {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			// System.out.println(raf.readChar());
			sb.append(raf.readChar());
		}
		return sb.toString().trim();
	}

	/**
	 * **************AJOUT D'UN NOEUD DANS L'ARBRE : *************** - 1ère étape :
	 * définir la méthode d'ajout de noeud dans la classe Node - 2ème étape : voir
	 * dans la classe Tree
	 * 
	 * @param intern
	 * @return
	 * @throws IOException
	 */

	public void addNode(Intern newIntern, RandomAccessFile raf) throws IOException {
		/**
		 * 
		 * SI le stagiaire de la racine > celui qui est comparé...
		 */
		
		System.out.println("addNode de " + newIntern.getName()+ "depuis position " + raf.getFilePointer() + "  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" ) ;
		
		Tree tree = new Tree();
		long position = raf.getFilePointer();
		Node node = tree.readNode(raf, position); //On se place à la racine de l'arbe

		// System.out.println();

		if (node.intern.getName().compareTo(newIntern.getName()) > 0) {

			/**
			 * ...et que dans ce cas, le fils gauche du noeud racine (key) est null...
			 */
			if (node.leftChild == -1) {

				// on recule le curseur de 12 octet
				// raf.seek(raf.length());
				raf.seek(raf.getFilePointer() - 12);
				raf.writeInt((int) (raf.length() / Intern.RECORD_LENGTH));
				System.out.println((int) (raf.length() / Intern.RECORD_LENGTH));// index du noeud
				// je me remets à la fin du fichier
				raf.seek(raf.length());
				// j'écris le nouveau noeud
				intern.writeToRandomAccessFile(raf);
				raf.writeInt(-1);
				raf.writeInt(-1);
				raf.writeInt(-1);

				/**
				 * ... ALORS on ajoute le stagiaire au fils gauche (condition d'arrêt)
				 */

				// this.leftChild = new Node(intern);
				/**
				 * Mais si le stagiaire de la racine est toujours > celui qui est comparé MAIS
				 * que le fils gauche est déjà rempli
				 */
			} else { // fils gauche non vide
				/**
				 * alors on continue le traitement sur le noeud intermédiaire jusqu'à trouver un
				 * noeud null ( => récursivité)
				 */
				// on se déplace à la position fils gauche
				raf.seek(node.leftChild * Intern.RECORD_LENGTH);
				// Node leftNode = leftNode.ReadNode();//lire un noeud
				addNode(intern, raf);
			}
			/**
			 * SI le stagiaire de la racine < celui qui est comparé...
			 */
		} // meme chose mais on remont de -8 octet
		/**
		 * ...et que dans ce cas le fils droit est null...
		 */
		else if (node.intern.getName().compareTo(newIntern.getName()) < 0) {
			if (node.rightChild == -1) {
				/**
				 * ...ALORS on ajoute le stagiaire dans ce noeud (condition d'arrêt)
				 */
				raf.seek(raf.getFilePointer() - 8);
				raf.writeInt((int) (raf.length() / Intern.RECORD_LENGTH)); // index du noeud
				// je me remets à la fin du fichier
				raf.seek(raf.length());
				System.out.println((int) ((raf.length() / Intern.RECORD_LENGTH)));
				// j'écris le nouveau noeud
				intern.writeToRandomAccessFile(raf);
				raf.writeInt(-1);
				raf.writeInt(-1);
				raf.writeInt(-1);

				// this.rightChild = new Node(intern);
				/**
				 * si le fils droit est déjà rempli, alors récursivité
				 */
			} else {
				// this.rightChild.addNode(intern);
				raf.seek(node.rightChild * Intern.RECORD_LENGTH);
				// Node rightNode= rightNode.ReadNode();//lire un noeud
				addNode(intern, raf);
			}
			/**
			 * Dans le cas où il y a un doublon, on envoie une erreur pour stopper le
			 * processus
			 */
		} else {
			if (node.next == -1) {

				/// meme chose maios on remonte de 4
				raf.seek(raf.length());
				raf.seek(raf.getFilePointer() - 4);
				raf.writeInt((int) (raf.length() / Intern.RECORD_LENGTH));
				System.out.println((int) (raf.length() / Intern.RECORD_LENGTH));// index du noeud
				// je me remets à la fin du fichier
				raf.seek(raf.length());
				// j'écris le nouveau noeud
				intern.writeToRandomAccessFile(raf);
				raf.writeInt(-1);
				raf.writeInt(-1);
				raf.writeInt(-1);

			}
			// demander a vincent pourquoi il imprime 10 et retourne a la ligne
		}
		//System.out.println(intern);
		
	}
	
	public void seekNode(Intern newIntern, RandomAccessFile raf) throws IOException {
		/**
		 * 
		 * SI le stagiaire de la racine > celui qui est comparé...
		 */
		

		Tree tree = new Tree();
		long position = raf.getFilePointer();
		Node node = tree.readNode(raf, position); //On lit le noeud courrant

			// Est ce que je m trouve dans le noeud que je cherhche ?
		
		//oui -> je retourne le Intern
		//non -> on fait ce qu'il y a dessous

		if (node.intern.getName().compareTo(newIntern.getName()) > 0) { //si valeur<noeudcourrant
			
			//on part a gauche
		
			if (node.leftChild == -1) {

			//la vlaur n'existe pas 
			
			} else { 
				//sinon reccursion à gauche
				raf.seek(node.leftChild * Intern.RECORD_LENGTH);
				
				seekNode(intern, raf);
			}
			
		}

		else if (node.intern.getName().compareTo(newIntern.getName()) < 0) {
			if (node.rightChild == -1) {
				// la valeurn'existe pas 
			} else {
				// réccursion à droite
				raf.seek(node.leftChild * Intern.RECORD_LENGTH);
				
				seekNode(intern, raf);
			}
		} else {
			if (node.next == -1) {
		}
			// demander a vincent pourquoi il imprime 10 et retourne a la ligne
		}
		//System.out.println(intern);
		
	}

	public void deleteIntern(Intern newIntern, RandomAccessFile raf) throws IOException {
		Tree tree = new Tree();
		long position = raf.getFilePointer();
		Node node = tree.readNode(raf, position);
		if (node.next != -1) {

		} else if (node.leftChild == -1) {
			if (node.rightChild == -1) {

			}
		} else if (node.rightChild == -1) {

		}
	}
}
