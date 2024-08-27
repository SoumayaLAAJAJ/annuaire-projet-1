package fr.isika.cda27.projet1.Annuaire.back;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;

public class Tree implements Serializable {
	// private static final long serialVersionUID = 1L;
	private Node root;
	private RandomAccessFile raf;

	public Tree(Node root) {
		this.root = root;
		try {
			raf = new RandomAccessFile("src/main/resources/arbre.bin", "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Tree() {
		try {
			raf = new RandomAccessFile("src/main/resources/arbre.bin", "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public RandomAccessFile getRaf() {
		return raf;
	}

	public void setRaf(RandomAccessFile raf) {
		this.raf = raf;
	}

	public void checkRootToAddNode(Intern intern) {

		// si le fichier est vide = pas d'arbre
		try {
			if (raf.length() == 0) {
				// créer un noeud
				this.root = new Node(intern);
				// ecrire le noeud dans le fichier binaire
				raf.seek(0);
				intern.writeToRandomAccessFile(raf);
				raf.writeInt(-1);
				raf.writeInt(-1);
				raf.writeInt(-1);
			} else {
				// ecrire le noeud à la suite dans le fichier binaire
				intern.writeToRandomAccessFile(raf);
				raf.writeInt(-1);
				raf.writeInt(-1);
				raf.writeInt(-1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}