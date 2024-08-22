package fr.isika.cda27.projet1.Annuaire.back;

public class Node {
	private Intern key;
	private Node leftChild;
	private Node rightChild;
	
	public Node(Intern key, Node leftChild, Node rightChild) {
		this.key = key;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	public Intern getKey() {
		return key;
	}

	public void setKey(Intern key) {
		this.key = key;
	}

	public Node getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}

	public Node getRightChild() {
		return rightChild;
	}

	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}
	
	
	
	
	

}
