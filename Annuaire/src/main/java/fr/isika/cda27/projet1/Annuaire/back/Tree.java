package fr.isika.cda27.projet1.Annuaire.back;

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

	public ArrayList<Intern> getInterns() throws IOException {

		raf.seek(0);
		ArrayList<Intern> myList = new ArrayList<Intern>();
		getInternsInfix(myList, 0, this.getRaf());
		return myList;
	}

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
