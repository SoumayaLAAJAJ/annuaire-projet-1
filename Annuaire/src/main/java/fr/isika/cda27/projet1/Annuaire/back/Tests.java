package fr.isika.cda27.projet1.Annuaire.back;

import java.io.IOException;
import java.util.ArrayList;

public class Tests {

	public static void main(String[] args) throws IOException {
		Tree tree = new Tree();
		tree.createBinfile();
		Intern internd = new Intern();
		Node node = new Node(internd);
		
		System.out.println(node.getInternfromseek("UNG").getIntern().getName());
	}
}

