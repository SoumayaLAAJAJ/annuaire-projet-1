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

	public Intern getInter() {
		return intern;
	}

	public void setKey(Intern intern) {
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
