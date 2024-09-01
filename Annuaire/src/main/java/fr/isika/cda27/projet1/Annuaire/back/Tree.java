package fr.isika.cda27.projet1.Annuaire.back;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe représente un arbre binaire permettant de gérer des stagiaires de type {@link Intern} stockés dans un fichier binaire.
 */
public class Tree {

	private Node root;
	private RandomAccessFile raf;
	public int IndexChild;
	public int IndexFather;
	public String filePath = "src/main/resources/arbre.bin";

	/**
	 * Constructeur permettant d'initialiser le fichier binaire pour l'arbre.
	 */
	public Tree() {
		try {
			raf = new RandomAccessFile(this.filePath, "rwd");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retourne la racine de l'arbre.
	 * 
	 * @return La racine de l'arbre
	 */
	public Node getRoot() {
		return root;
	}

	/**
	 * Retourne le fichier binaire associé à l'arbre.
	 * 
	 * @return le fichier binaire associé à l'arbre
	 */
	public RandomAccessFile getRaf() {
		return raf;
	}

	/**
	 * Crée le fichier binaire de l'arbre à partir d'une liste existante de stagiaires.
	 */
	public void createBinfile() {

		try {
			if (this.getRaf().length() == 0) {
				InternDAO intern;
				List<Intern> interns;

				try {
					intern = new InternDAO();
					interns = intern.getMaListe();

					for (int i = 0; i < interns.size(); i++) {
						this.checkRootToAddNode(interns.get(i));
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Vérifie si la racine de l'arbre est définie pour la création du premier noeud
	 * Appele la méthode addNode() pour les noeuds suivants
	 * 
	 * @param intern Le stagiaire à ajouter dans l'arbre
	 */
	public void checkRootToAddNode(Intern intern) {
		try {
			if (raf.length() == 0) {
				this.root = new Node(intern);
				raf.seek(0);
				intern.writeToRandomAccessFile(raf);
				raf.writeInt(-1);
				raf.writeInt(-1);
				raf.writeInt(-1);
			} else {
				Node node = new Node(intern);

				raf.seek(0);
				node.addNode(intern, raf);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ajoute un nœud dans l'arbre pour le stagiaire spécifié.
	 * 
	 * @param intern Le stagiaire ajouter à l'arbre
	 * @throws IOException Si une erreur d'entrée/sortie survient lors de l'ajout
	 */
	public void addNode(Intern intern) throws IOException {
		Node node = new Node(intern);
		this.raf.seek(0);
		node.addNode(intern, raf);
	}

	/**
	 * Lit un nœud à partir du fichier binaire à la position spécifiée.
	 * 
	 * @param raf Le fichier binaire à lire
	 * @param position La position du nœud dans le fichier
	 * @return Le nœud lu à partir du fichier
	 */
	public Node readNode(RandomAccessFile raf, long position) {
		String name = "";
		String firstname = "";
		String department = "";
		String year = "";
		String promo = "";
		int leftChild = 0;
		int rightChild = 0;
		int next = 0;

		try {
			raf.seek(position);

			for (int j = 0; j < Intern.NAME_LENGTH; j++) {
				name += raf.readChar();
			}
			name = name.trim();
			for (int j = 0; j < (Intern.FIRSTNAME_LENGTH); j++) {
				firstname += raf.readChar();
			}
			firstname = firstname.trim();
			for (int j = 0; j < (Intern.DEPARTMENT_LENGTH); j++) {
				department += raf.readChar();
			}
			department = department.trim();

			for (int j = 0; j < (Intern.PROMO_LENGTH); j++) {
				promo += raf.readChar();
			}
			promo = promo.trim();
			for (int j = 0; j < (Intern.YEAR_LENGTH); j++) {
				year += raf.readChar();
			}
			year = year.trim();
			leftChild = raf.readInt();
			rightChild = raf.readInt();
			next = raf.readInt();

		} catch (IOException e) {
			e.printStackTrace();
		}

		Intern intern = new Intern(name, firstname, department, promo, year);
		Node node = new Node(intern, leftChild, rightChild, next);

		return node;
	}

	/**
	 * Retourne une liste de tous les stagiaires de l'arbre en utilisant le parcours infixe
	 * 
	 * @return Une liste ordonnée de stagiaires
	 * @throws IOException Si une erreur d'entrée/sortie survient lors de la lecture
	 */
	public ArrayList<Intern> getInterns() throws IOException {

		raf.seek(0);
		ArrayList<Intern> myList = new ArrayList<Intern>();
		getInternsInfix(myList, 0, this.getRaf());
		return myList;
	}

	/**
	 * Effectue un parcours infixe de l'arbre pour récupérer les stagiaires dans
	 * l'ordre.
	 * 
	 * @param myList La liste des stagiaires à compléter
	 * @param position La position actuelle dans le fichier binaire
	 * @param raf Le fichier binaire à lire
	 * @throws IOException Si une erreur d'entrée/sortie survient lors de la lecture
	 */
	public void getInternsInfix(ArrayList<Intern> myList, long position, RandomAccessFile raf) throws IOException {

		Node currentNode = readNode(raf, position);

		if (currentNode.getLeftChild() != -1) {
			getInternsInfix(myList, currentNode.getLeftChild() * Intern.RECORD_LENGTH, raf);
		}

		if (currentNode.getNext() != -1) {
			getInternsInfix(myList, currentNode.getNext() * Intern.RECORD_LENGTH, raf);
		}

		myList.add(currentNode.getIntern());

		if (currentNode.getRightChild() != -1) {
			getInternsInfix(myList, currentNode.getRightChild() * Intern.RECORD_LENGTH, raf);
		}
	}
}
