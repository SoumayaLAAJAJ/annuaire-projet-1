package fr.isika.cda27.projet1.Annuaire.back;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class Tree {

	private Node root;
	private RandomAccessFile raf;
	public int IndexChild;
	public int IndexFather;
	public String filePath = "src/main/resources/arbre.bin";


	public Tree() {
		try {
			raf = new RandomAccessFile(this.filePath, "rwd");
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
		// node.removeNode();
	}

	public void addNode(Intern intern) throws IOException {
		Node node = new Node(intern);
		this.raf.seek(0);
		node.addNode(intern, raf);
	}

	public Node readNode(RandomAccessFile raf, long position) {
		String name = "";
		String firstname = "";
		String department = "";
		String year = "";
		String promo = "";
		int leftChild = 0;
		int rightChild = 0;
		int next = 0;

	//	System.out.println("début lecture readnode pointeur: " + position);
		try {
			// read Nom
			raf.seek(position);
			//System.out.println("raf.getFilePointer() *++++++++++++++++++++++++++++++++++++++++++++"+raf.getFilePointer());
//			if (!(raf.getFilePointer() < 88)) {
//				raf.seek(position  - 88);
//			}
		//	System.out.println(raf.getFilePointer());
			for (int j = 0; j < Intern.NAME_LENGTH; j++) {
				name += raf.readChar();
			}
			name = name.trim();
		//	System.out.println(name); // read Prenom
			for (int j = 0; j < (Intern.FIRSTNAME_LENGTH); j++) {
				firstname += raf.readChar();
			}
			firstname = firstname.trim();
			//System.out.println(firstname); // read Department
			for (int j = 0; j < (Intern.DEPARTMENT_LENGTH); j++) {
				department += raf.readChar();
			}
			department = department.trim();
			//System.out.println(department); // read année
		
			//System.out.println(year); // read promo
			for (int j = 0; j < (Intern.PROMO_LENGTH); j++) {
				promo += raf.readChar();
			}
			promo = promo.trim();	
			for (int j = 0; j < (Intern.YEAR_LENGTH); j++) {
				year += raf.readChar();
			}
			year = year.trim();
			//System.out.println(promo); // read left child
			leftChild = raf.readInt();
			//System.out.println(leftChild); // read right child
			rightChild = raf.readInt();
			//System.out.println(rightChild); // read next child
			next = raf.readInt();
			//System.out.println(next);
			//System.out.println("pointeur fin de readnode: " + raf.getFilePointer() + "\n\n\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

		Intern intern = new Intern(name, firstname, department,  promo,year);
		Node node = new Node(intern, leftChild, rightChild, next);

		return node;
	}

	public void checkRootToAddNode(Intern intern) { // si le fichier est vide on va créer un premier noeud avec des -1
		try {
			if (raf.length() == 0) {
				// créer un noeud
				this.root = new Node(intern);
				// ecrire le noeud dans le fichier binaire
				raf.seek(0);
				// System.out.println(raf.getFilePointer());
				intern.writeToRandomAccessFile(raf);
				raf.writeInt(-1);
				raf.writeInt(-1);
				raf.writeInt(-1);
				// System.out.println(intern);
			} else {
				// sinon on créé un nouveau noeud à la suite dans le fichier binaire
				Node node = new Node(intern);
				// lire le premier noeud du fichier binaire et stocke dans noeud et appelle add
				// avec noeud
				// System.out.println(raf.getFilePointer());
				//root = readNode(raf, raf.getFilePointer() - 88);
				// System.out.println(root);
				raf.seek(0);
				node.addNode(intern, raf);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createBinfile()  {

		try {
			if (this.getRaf().length()==0){
			InternDAO intern;
			List<Intern> interns;

			try {
				// Initialise la liste de stagiaire depuis le fichier don
				intern = new InternDAO();
				interns = intern.getMaListe();

				// Parcours de chaque stagiare pour créer un Node et l'écrire dans le fichier
				for (int i = 0; i < interns.size(); i++) {
					// Crée un noeud avec le stagiaire
					this.checkRootToAddNode(interns.get(i));
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
	
	public ArrayList<Intern> getInterns() throws IOException{
		//System.out.println("getInterns ****************************************************************************");
		raf.seek(0);
		ArrayList<Intern> myList = new ArrayList<Intern>();
		getInternsInfix(myList, 0, this.getRaf());
		return myList;
	}
	
	public void getInternsInfix(ArrayList<Intern> myList, long position, RandomAccessFile raf) throws IOException{
		
		//System.out.println("getInternsInfix ----------------------------------------------------------" + position);
		Node currentNode = readNode(raf,position);

		//affiche filsGauche filsGauche.getInternsInfix(maListe)
		if (currentNode.getLeftChild()!=-1) {
		//	System.out.println("appel recc fils gauche " + currentNode.getIntern().getName());
			 getInternsInfix( myList, currentNode.getLeftChild()*Intern.RECORD_LENGTH ,raf);
		}
				
		if (currentNode.getNext()!=-1) {
			//	System.out.println("appel recc fils gauche " + currentNode.getIntern().getName());
				 getInternsInfix( myList, currentNode.getNext()*Intern.RECORD_LENGTH ,raf);
			}
		
		//affiche noeud maList.add(noeud.intern)
		//System.out.println("Ajout List " + currentNode.getIntern().getName());
		myList.add(currentNode.getIntern());
		
		//affiche filsDroit filsDroit.getInternsInfix
		if (currentNode.getRightChild()!=-1) {
			//System.out.println("appel recc fils droit " + currentNode.getIntern().getName());
			 getInternsInfix( myList, currentNode.getRightChild()*Intern.RECORD_LENGTH ,raf);
		}
	
	}
}
