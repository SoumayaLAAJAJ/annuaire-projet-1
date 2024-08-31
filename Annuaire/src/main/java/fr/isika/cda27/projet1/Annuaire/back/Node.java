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
	public int IndexFather;
	public int IndexRemplacant;
	public int IndexChild;
	public int buffer1;
	public int bufferTDLC;
	public int bufferTDRC;

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

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

		// System.out.println("addNode de " + newIntern.getName()+ "depuis position " +
		// raf.getFilePointer() + " aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" ) ;

		Tree tree = new Tree();
		long position = raf.getFilePointer();
		Node node = tree.readNode(raf, position); // On se place à la racine de l'arbe
//System.out.println(newIntern);
		// System.out.println();

		if (node.intern.getName().compareToIgnoreCase(newIntern.getName()) > 0) {

			/**
			 * ...et que dans ce cas, le fils gauche du noeud racine (key) est null...
			 */
			if (node.leftChild == -1) {

				// on recule le curseur de 12 octet
				// raf.seek(raf.length());
				raf.seek(raf.getFilePointer() - 12);
				raf.writeInt((int) (raf.length() / Intern.RECORD_LENGTH));
				// System.out.println((int) (raf.length() / Intern.RECORD_LENGTH));// index du
				// noeud
				// je me remets à la fin du fichier
				raf.seek(raf.length());
				// j'écris le nouveau noeud
				newIntern.writeToRandomAccessFile(raf);
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
				addNode(newIntern, raf);
			}
			/**
			 * SI le stagiaire de la racine < celui qui est comparé...
			 */
		} // meme chose mais on remont de -8 octet
		/**
		 * ...et que dans ce cas le fils droit est null...
		 */
		else if (node.intern.getName().compareToIgnoreCase(newIntern.getName()) < 0) {
			if (node.rightChild == -1) {
				/**
				 * ...ALORS on ajoute le stagiaire dans ce noeud (condition d'arrêt)
				 */
				raf.seek(raf.getFilePointer() - 8);
				raf.writeInt((int) (raf.length() / Intern.RECORD_LENGTH)); // index du noeud
				// je me remets à la fin du fichier
				raf.seek(raf.length());
				// System.out.println((int) ((raf.length() / Intern.RECORD_LENGTH)));
				// j'écris le nouveau noeud
				newIntern.writeToRandomAccessFile(raf);
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
				addNode(newIntern, raf);
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
				// System.out.println((int) (raf.length() / Intern.RECORD_LENGTH));// index du
				// noeud
				// je me remets à la fin du fichier
				raf.seek(raf.length());
				// j'écris le nouveau noeud
				newIntern.writeToRandomAccessFile(raf);
				raf.writeInt(-1);
				raf.writeInt(-1);
				raf.writeInt(-1);

			}
			// demander a vincent pourquoi il imprime 10 et retourne a la ligne
		}
		// System.out.println(intern);

	}

	public void seekInterns(String name, String firstname) throws IOException {
		// System.out.println("getInterns
		// ****************************************************************************");
		IndexChild=-1;
		Tree tree = new Tree();
		tree.getRaf().seek(0);
		seekNode(name, firstname,tree.getRaf());

	}

	public void seekNode(String name, String firstname,RandomAccessFile raf) throws IOException {
		/**
		 * 
		 * SI le stagiaire de la racine > celui qui est comparé...
		 */

		Tree tree = new Tree();
		long position = raf.getFilePointer();
		Node node = tree.readNode(raf, position);
		// On lit le noeud courrant

		// Est ce que je m trouve dans le noeud que je cherhche ?

		// oui -> je retourne le Intern
		// non -> on fait ce qu'il y a dessous
		if ((node.intern.getName().compareToIgnoreCase(name) == 0 && node.intern.getFirstname().compareToIgnoreCase(firstname)==0) ) {
			IndexChild = (int) ((raf.getFilePointer() - Intern.RECORD_LENGTH) / Intern.RECORD_LENGTH);
//			System.out.println("Index Father = " + IndexFather);
//		System.out.println("Index Child = " + IndexChild);
//			System.out.println("trouvé");
		}

		if (node.intern.getName().compareToIgnoreCase(name) > 0) { // si valeur<noeudcourrant
		//	System.out.println("depuis position " + raf.getFilePointer() + "  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			// on part a gauche

			if (node.leftChild == -1) {

			//	System.out.println("Non trouvé");

			} else {
				// sinon reccursion à gauche
				IndexFather = (int) ((raf.getFilePointer() - Intern.RECORD_LENGTH) / Intern.RECORD_LENGTH);
				// System.out.println("Index Father = " + tree.IndexFather);
				raf.seek(node.leftChild * Intern.RECORD_LENGTH);
				seekNode(name,firstname, raf);
			}

		}

		else if (node.intern.getName().compareToIgnoreCase(name) < 0) {
			// System.out.println("depuis position " + raf.getFilePointer() + "
			// aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" ) ;
			if (node.rightChild == -1) {
				// la valeurn'existe pas
			//	System.out.println("Non trouvé");
			} else {
				// réccursion à droite
				IndexFather = (int) ((raf.getFilePointer() - Intern.RECORD_LENGTH) / Intern.RECORD_LENGTH);
				// System.out.println("Index Father = " + IndexFather);
				raf.seek(node.rightChild * Intern.RECORD_LENGTH);
				seekNode(name,firstname, raf);

			}
		}
		else if (node.intern.getName().compareToIgnoreCase(name) == 0 && node.next!=-1) {
			IndexFather = (int) ((raf.getFilePointer() - Intern.RECORD_LENGTH) / Intern.RECORD_LENGTH);
			// System.out.println("Index Father = " + IndexFather);
			raf.seek(node.next * Intern.RECORD_LENGTH);
	//	System.out.println(node.getIntern().getName());
			seekNode(name,firstname, raf);
		}

	}

	public void deleteIntern(String name,String firstname, RandomAccessFile raf) throws IOException {
		
		Tree tree = new Tree();
		
		seekInterns(name,firstname);// sort index du pere et fils
		// Verification si le stagiere a été trouvé
		if (IndexChild!=-1) {
		//System.out.println("indexFather " + IndexFather);
		Node nodeFather = tree.readNode(tree.getRaf(), IndexFather * Intern.RECORD_LENGTH); //IndexFather
		Node nodeToDelete = tree.readNode(tree.getRaf(), IndexChild * Intern.RECORD_LENGTH);// IndexChild
		
		if (nodeToDelete.next != -1){
			bufferTDLC=IndexChild;
			raf.seek(nodeToDelete.next*Intern.RECORD_LENGTH);
			Node node1 = tree.readNode(tree.getRaf(), nodeToDelete.next * Intern.RECORD_LENGTH);
			IndexRemplacant = (int) ((raf.getFilePointer()- Intern.RECORD_LENGTH) / Intern.RECORD_LENGTH);
			while (node1.next != -1) {
				IndexRemplacant = (int) ((raf.getFilePointer()- Intern.RECORD_LENGTH) / Intern.RECORD_LENGTH);
				// System.out.println("node" +node1.rightChild);
				node1 = tree.readNode(tree.getRaf(), node1.next* Intern.RECORD_LENGTH);
				//System.out.println(node1.getIntern().getName()	); 				
		}
			Intern internRemplacant = node1.getIntern();
			deleteIntern(node1.getIntern().getName(),node1.getIntern().getFirstname(), tree.getRaf());
			raf.seek(bufferTDLC*Intern.RECORD_LENGTH);
			internRemplacant.writeToRandomAccessFile(raf);	
		}
		else if (nodeToDelete.leftChild == -1 && nodeToDelete.rightChild == -1 ) {
			//System.out.println("enfant supprimé");
			buffer1=-1;
			if (nodeFather.leftChild == IndexChild) {
				raf.seek((IndexFather + 1) * Intern.RECORD_LENGTH - 12);
				raf.writeInt(buffer1);
			//	System.out.println("why " +IndexChild);
			} else if (nodeFather.rightChild == IndexChild) {
				//System.out.println("why " +IndexChild);
				//System.out.println(buffer1);
				raf.seek((IndexFather + 1) * Intern.RECORD_LENGTH - 8);
				raf.writeInt(buffer1);
			}
			else if (nodeFather.next==IndexChild) {
				raf.seek((IndexFather + 1) * Intern.RECORD_LENGTH - 4);
				raf.writeInt(buffer1);
			}
		}

		else if ((nodeToDelete.leftChild != -1 && nodeToDelete.rightChild == -1)
				|| (nodeToDelete.leftChild == -1 && nodeToDelete.rightChild != -1)) {
			// System.out.println("test : " + node1.leftChild * Intern.RECORD_LENGTH);
			if (nodeToDelete.leftChild != -1) {
				buffer1 = nodeToDelete.leftChild;
			} else {
				buffer1 = nodeToDelete.rightChild;
			}
			if (nodeFather.leftChild == IndexChild) {
				raf.seek((IndexFather + 1) * Intern.RECORD_LENGTH - 12);
				raf.writeInt(buffer1);
				//System.out.println("why =" +IndexChild);
			} else if (nodeFather.rightChild == IndexChild) {
				//System.out.println("why =" +IndexChild);
				//System.out.println(buffer1);
				raf.seek((IndexFather + 1) * Intern.RECORD_LENGTH - 8);
				raf.writeInt(buffer1);
			}
		}
// 2 enfants
		
		else if (nodeToDelete.leftChild != -1 && nodeToDelete.rightChild != -1) {
			// Stockage des Index des enfants
			bufferTDLC=IndexChild;


			// System.out.println("retnrer");
		
			raf.seek(nodeToDelete.rightChild*Intern.RECORD_LENGTH);
			Node node1 = tree.readNode(tree.getRaf(), nodeToDelete.rightChild * Intern.RECORD_LENGTH);
		
	
			IndexRemplacant = (int) (raf.getFilePointer() / Intern.RECORD_LENGTH);
			//System.out.println("rempl  " +IndexRemplacant);
		
		// recuperer remplacant
			while (node1.leftChild != -1) {
				IndexRemplacant = (int) ((raf.getFilePointer()- Intern.RECORD_LENGTH) / Intern.RECORD_LENGTH);
				// System.out.println("node" +node1.rightChild);
				node1 = tree.readNode(tree.getRaf(), node1.leftChild * Intern.RECORD_LENGTH);
				//System.out.println(node1.getIntern().getName()	); 
		}
			
		

			Intern internRemplacant = node1.getIntern();
			deleteIntern(node1.getIntern().getName(),node1.getIntern().getFirstname(), tree.getRaf());
			raf.seek(bufferTDLC*Intern.RECORD_LENGTH);
			internRemplacant.writeToRandomAccessFile(raf);	

			
			}		
		}
		else {
		//	System.out.println("Le stagière n'existe pas");
		}
	}
	
	
	
	public void updateIntern(RandomAccessFile raf, String name, String firstname,Intern newIntern) throws IOException {
		Tree tree = new Tree();
		raf=tree.getRaf();
		
		deleteIntern(name, firstname,raf);
		raf.seek(0);
		addNode(newIntern, raf);
		
	}
	
	
	
	
	
	public Node getInternfromseek(String name, String firstname) throws IOException {
		
		Tree tree = new Tree();
		seekInterns(name, firstname);
		Node nodeIntern=tree.readNode(tree.getRaf(), (int)(IndexChild*Intern.RECORD_LENGTH));
		return nodeIntern;
	}
	
	public ArrayList<Intern> getInterns(long position) throws IOException{
		//System.out.println("getInterns ****************************************************************************");
		Tree tree=new Tree();
		tree.getRaf().seek(position);
		ArrayList<Intern> myList = new ArrayList<Intern>();
		tree.getInternsInfix(myList, position, tree.getRaf());
		return myList;
	}
}
