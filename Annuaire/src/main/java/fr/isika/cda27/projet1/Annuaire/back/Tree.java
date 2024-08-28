package fr.isika.cda27.projet1.Annuaire.back;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Tree  {
	
	private Node root;
	private RandomAccessFile raf;

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
	
	public void removeNode(Intern intern) {
		//node.removeNode();
	}
	
	public void addNode(Intern intern) throws IOException {
		Node node = new Node(intern);
		node.addNode(intern, raf);
	}

	public void checkRootToAddNode(Intern intern) {

		// si le fichier est vide on va créer un premier noeud avec des -1
		try {
			if (raf.length() == 0) {
				// créer un noeud
				this.root = new Node(intern);
				// ecrire le noeud dans le fichier binaire
				raf.seek(0);
				//node.writeToRandomAccessFile(raf);
				intern.writeToRandomAccessFile(raf);
				raf.writeInt(-1);
				raf.writeInt(-1);
				raf.writeInt(-1);
			} else {
				// sinon on créé un nouveau noeud à la suite dans le fichier binaire
				Node node = new Node(intern);
				// lire le premier noeud du fichier binaire et stock dans noeud et appelle add avec noeud
				node.readNodeFromFile(raf, raf.getFilePointer());
				
				node.addNode(intern, raf);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

