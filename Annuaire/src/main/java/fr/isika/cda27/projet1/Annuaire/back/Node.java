package fr.isika.cda27.projet1.Annuaire.back;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

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

	public void seekInterns(String name, String firstname) throws IOException {

		IndexChild = -1;
		Tree tree = new Tree();
		tree.getRaf().seek(0);
		seekNode(name, firstname, tree.getRaf());

	}

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
		} else {
		}
	}

	public void updateIntern(RandomAccessFile raf, String name, String firstname, Intern newIntern) throws IOException {
		Tree tree = new Tree();
		raf = tree.getRaf();

		deleteIntern(name, firstname, raf);
		raf.seek(0);
		addNode(newIntern, raf);

	}

	public Node getInternfromseek(String name, String firstname) throws IOException {

		Tree tree = new Tree();
		seekInterns(name, firstname);
		Node nodeIntern = tree.readNode(tree.getRaf(), (int) (IndexChild * Intern.RECORD_LENGTH));
		return nodeIntern;
	}

	public ArrayList<Intern> getInterns(long position) throws IOException {

		Tree tree = new Tree();
		tree.getRaf().seek(position);
		ArrayList<Intern> myList = new ArrayList<Intern>();
		tree.getInternsInfix(myList, position, tree.getRaf());
		return myList;
	}
}
