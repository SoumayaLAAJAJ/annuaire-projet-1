package fr.isika.cda27.projet1.Annuaire.back;

import java.io.IOException;
import java.util.ArrayList;

public class Tests {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Tree tree = new Tree();
		tree.createBinfile();
//		for (int i = 0; i < 1704; i++) {
//
//			System.out.println((tree.readNode(tree.getRaf(), i*88).getIntern().getName()));
//		}

		System.out.println("OOOOOOOOOOOOOOOOOOOOO" + tree.getInterns().size() + "OOOOOOOOOOOOOOOOOOOOO");
		for (Intern intern : tree.getInterns()) {
			System.out.println("\n" + intern.getName());
		}

	}

}
