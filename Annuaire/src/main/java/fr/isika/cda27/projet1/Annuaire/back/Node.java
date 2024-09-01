package fr.isika.cda27.projet1.Annuaire.back;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Cette classe représente un nœud dans l'arbre binaire. Chaque nœud contient un
 * stagiaire de type {@link Intern} et des références à ses enfants gauche et
 * droit, ainsi qu'à un nœud suivant.
 * 
 */
public class Node {

	private Intern intern;
	private int leftChild;
	private int rightChild;
	private int next;
	public int IndexFather;
	public int IndexRemplacant;
	public int IndexChild;
	public int buffer1;
	public int bufferTDLC;
	public int bufferTDRC;

	/**
	 * Constructeur permettant d'iinitialiser un nœud avec un objet `Intern` et des
	 * enfants non définis.
	 * 
	 * @param intern le stagiaire contenu dans le nœud
	 */
	public Node(Intern intern) {
		this.intern = intern;
		this.leftChild = -1;
		this.rightChild = -1;
		this.next = -1;
	}

	/**
	 * Constructeur permettant d'initialiser un nœud avec un objet `Intern`, un
	 * enfant gauche, un enfant droit, et un nœud suivant.
	 * 
	 * @param intern     le stagiaire contenu dans le nœud
	 * @param leftChild  l'index du nœud enfant gauche
	 * @param rightChild l'index du nœud enfant droit
	 * @param next       l'index du nœud suivant
	 */
	public Node(Intern intern, int leftChild, int rightChild, int next) {
		this.intern = intern;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.next = next;
	}

	/**
	 * Retourne le stagiaire contenu dans ce nœud.
	 * 
	 * @return le stagiaire
	 */
	public Intern getIntern() {
		return intern;
	}

	/**
	 * Retourne l'index de l'enfant gauche du nœud.
	 * 
	 * @return l'index de l'enfant gauche
	 */
	public int getLeftChild() {
		return leftChild;
	}

	/**
	 * Retourne l'index de l'enfant droit du nœud.
	 * 
	 * @return l'index de l'enfant droit
	 */
	public int getRightChild() {
		return rightChild;
	}

	/**
	 * Retourne l'index du nœud suivant dans l'arbre.
	 * 
	 * @return l'index du nœud suivant
	 */
	public int getNext() {
		return next;
	}

	/**
	 * Ajoute un stagiaire dans l'arbre en utilisant le fichier binaire. La méthode
	 * place le nouvel objet à la position appropriée dans l'arbre.
	 * 
	 * @param newIntern Le stagiaire à ajouter
	 * @param raf       Le fichier dans lequel est stocké l'arbre
	 * @throws IOException si une erreur d'entrée/sortie se produit
	 */
	public void addNode(Intern newIntern, RandomAccessFile raf) throws IOException {

		Tree tree = new Tree();
		long position = raf.getFilePointer();
		Node node = tree.readNode(raf, position);

		if (node.intern.getName().compareToIgnoreCase(newIntern.getName()) > 0) {

			if (node.leftChild == -1) {

				raf.seek(raf.getFilePointer() - 12);
				raf.writeInt((int) (raf.length() / Intern.RECORD_LENGTH));

				raf.seek(raf.length());
				newIntern.writeToRandomAccessFile(raf);
				raf.writeInt(-1);
				raf.writeInt(-1);
				raf.writeInt(-1);

			} else {

				raf.seek(node.leftChild * Intern.RECORD_LENGTH);
				addNode(newIntern, raf);
			}

		}

		else if (node.intern.getName().compareToIgnoreCase(newIntern.getName()) < 0) {
			if (node.rightChild == -1) {

				raf.seek(raf.getFilePointer() - 8);
				raf.writeInt((int) (raf.length() / Intern.RECORD_LENGTH)); // index du noeud
				raf.seek(raf.length());

				newIntern.writeToRandomAccessFile(raf);
				raf.writeInt(-1);
				raf.writeInt(-1);
				raf.writeInt(-1);

			} else {
				raf.seek(node.rightChild * Intern.RECORD_LENGTH);
				addNode(newIntern, raf);
			}

		} else {
			if (node.next == -1) {

				raf.seek(raf.getFilePointer() - 4);

				raf.writeInt((int) (raf.length() / Intern.RECORD_LENGTH));

				raf.seek(raf.length());
				newIntern.writeToRandomAccessFile(raf);
				raf.writeInt(-1);
				raf.writeInt(-1);
				raf.writeInt(-1);
			}
		}
	}

	/**
	 * Recherche un noeud dans l'arbre en fonction du nom et du prénom du stagiaire.
	 * 
	 * @param name      Le nom du stagiaire à rechercher
	 * @param firstname Le prénom du stagiaire à rechercher
	 * @throws IOException si une erreur d'entrée/sortie se produit
	 */
	public void seekInterns(String name, String firstname) throws IOException {

		IndexChild = -1;
		Tree tree = new Tree();
		tree.getRaf().seek(0);
		seekNode(name, firstname, tree.getRaf());

	}

	/**
	 * Recherche un nœud dans l'arbre en fonction du nom et du prénom du stagiaire.
	 * 
	 * @param name      Le nom du stagiaire à rechercher
	 * @param firstname Le prénom du stagiaire à rechercher
	 * @param raf       Le fichier dans lequel est stocké l'arbre
	 * @throws IOException si une erreur d'entrée/sortie se produit
	 */
	public void seekNode(String name, String firstname, RandomAccessFile raf) throws IOException {

		Tree tree = new Tree();
		long position = raf.getFilePointer();
		Node node = tree.readNode(raf, position);

		if ((node.intern.getName().compareToIgnoreCase(name) == 0
				&& node.intern.getFirstname().compareToIgnoreCase(firstname) == 0)) {
			IndexChild = (int) ((raf.getFilePointer() - Intern.RECORD_LENGTH) / Intern.RECORD_LENGTH);

		}

		if (node.intern.getName().compareToIgnoreCase(name) > 0) {
			if (node.leftChild == -1) {

			} else {
				IndexFather = (int) ((raf.getFilePointer() - Intern.RECORD_LENGTH) / Intern.RECORD_LENGTH);
				raf.seek(node.leftChild * Intern.RECORD_LENGTH);
				seekNode(name, firstname, raf);
			}

		}

		else if (node.intern.getName().compareToIgnoreCase(name) < 0) {

			if (node.rightChild == -1) {

			} else {
				IndexFather = (int) ((raf.getFilePointer() - Intern.RECORD_LENGTH) / Intern.RECORD_LENGTH);
				raf.seek(node.rightChild * Intern.RECORD_LENGTH);
				seekNode(name, firstname, raf);

			}
		} else if (node.intern.getName().compareToIgnoreCase(name) == 0 && node.next != -1) {
			IndexFather = (int) ((raf.getFilePointer() - Intern.RECORD_LENGTH) / Intern.RECORD_LENGTH);
			raf.seek(node.next * Intern.RECORD_LENGTH);
			seekNode(name, firstname, raf);
		}

	}

	/**
	 * Supprime un stagiaire de l'arbre en fonction de son nom et prénom.
	 * 
	 * @param name      Le nom du stagiaire à supprimer
	 * @param firstname Le prénom du stagiaire à supprimer
	 * @param raf       Le fichier dans lequel est stocké l'arbre
	 * @throws IOException si une erreur d'entrée/sortie se produit
	 */
	public void deleteIntern(String name, String firstname, RandomAccessFile raf) throws IOException {

		Tree tree = new Tree();
		seekInterns(name, firstname);
		
		if (IndexChild != -1) {
			Node nodeFather = tree.readNode(tree.getRaf(), IndexFather * Intern.RECORD_LENGTH); // IndexFather
			Node nodeToDelete = tree.readNode(tree.getRaf(), IndexChild * Intern.RECORD_LENGTH);// IndexChild

			if (nodeToDelete.next != -1) {
				bufferTDLC = IndexChild;
				raf.seek(nodeToDelete.next * Intern.RECORD_LENGTH);
				Node node1 = tree.readNode(tree.getRaf(), nodeToDelete.next * Intern.RECORD_LENGTH);
				IndexRemplacant = (int) ((raf.getFilePointer() - Intern.RECORD_LENGTH) / Intern.RECORD_LENGTH);
				
				while (node1.next != -1) {
					IndexRemplacant = (int) ((raf.getFilePointer() - Intern.RECORD_LENGTH) / Intern.RECORD_LENGTH);
					node1 = tree.readNode(tree.getRaf(), node1.next * Intern.RECORD_LENGTH);
				}
				
				Intern internRemplacant = node1.getIntern();
				deleteIntern(node1.getIntern().getName(), node1.getIntern().getFirstname(), tree.getRaf());
				raf.seek(bufferTDLC * Intern.RECORD_LENGTH);
				internRemplacant.writeToRandomAccessFile(raf);
				
			} else if (nodeToDelete.leftChild == -1 && nodeToDelete.rightChild == -1) {
				buffer1 = -1;
				if (nodeFather.leftChild == IndexChild) {
					raf.seek((IndexFather + 1) * Intern.RECORD_LENGTH - 12);
					raf.writeInt(buffer1);
				} else if (nodeFather.rightChild == IndexChild) {

					raf.seek((IndexFather + 1) * Intern.RECORD_LENGTH - 8);
					raf.writeInt(buffer1);
				} else if (nodeFather.next == IndexChild) {
					raf.seek((IndexFather + 1) * Intern.RECORD_LENGTH - 4);
					raf.writeInt(buffer1);
				}
			}

			else if ((nodeToDelete.leftChild != -1 && nodeToDelete.rightChild == -1)
					|| (nodeToDelete.leftChild == -1 && nodeToDelete.rightChild != -1)) {
				if (nodeToDelete.leftChild != -1) {
					buffer1 = nodeToDelete.leftChild;
				} else {
					buffer1 = nodeToDelete.rightChild;
				}
				if (nodeFather.leftChild == IndexChild) {
					raf.seek((IndexFather + 1) * Intern.RECORD_LENGTH - 12);
					raf.writeInt(buffer1);
				} else if (nodeFather.rightChild == IndexChild) {

					raf.seek((IndexFather + 1) * Intern.RECORD_LENGTH - 8);
					raf.writeInt(buffer1);
				}
			}

			else if (nodeToDelete.leftChild != -1 && nodeToDelete.rightChild != -1) {
				bufferTDLC = IndexChild;

				raf.seek(nodeToDelete.rightChild * Intern.RECORD_LENGTH);
				Node node1 = tree.readNode(tree.getRaf(), nodeToDelete.rightChild * Intern.RECORD_LENGTH);

				IndexRemplacant = (int) (raf.getFilePointer() / Intern.RECORD_LENGTH);

				while (node1.leftChild != -1) {
					IndexRemplacant = (int) ((raf.getFilePointer() - Intern.RECORD_LENGTH) / Intern.RECORD_LENGTH);
					node1 = tree.readNode(tree.getRaf(), node1.leftChild * Intern.RECORD_LENGTH);
				}

				Intern internRemplacant = node1.getIntern();
				deleteIntern(node1.getIntern().getName(), node1.getIntern().getFirstname(), tree.getRaf());
				raf.seek(bufferTDLC * Intern.RECORD_LENGTH);
				internRemplacant.writeToRandomAccessFile(raf);

			}
		}
	}

	/**
	 * Remplace un stagiaire dans l'arbre
	 * 
	 * @param raf       Le fichier binaire dans lequel est stocké l'arbre
	 * @param name      Le nom du stagiaire à supprimer
	 * @param firstname Le prénom du stagiaire à supprimer
	 * @param newIntern Le stagiaire modifié à ajouter
	 * @throws IOException Si une erreur d'entrée/sortie se produit
	 */
	public void updateIntern(RandomAccessFile raf, String name, String firstname, Intern newIntern) throws IOException {
		Tree tree = new Tree();
		raf = tree.getRaf();

		deleteIntern(name, firstname, raf);
		raf.seek(0);
		addNode(newIntern, raf);
	}

}
