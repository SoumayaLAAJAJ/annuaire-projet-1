package fr.isika.cda27.projet1.Annuaire.back;

import java.io.IOException;
import java.util.ArrayList;

public class Tests {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		Tree tree = new Tree();
		tree.createBinfile();
		Intern internd = new Intern();
		Node node = new Node(internd);
		Intern newIntern = new Intern("GIN", "Hello", "95", "1998", "CDAVIEUX");
		//node.updateIntern(tree.getRaf(),"LACROIX", "Pascale" , newIntern);

	//	System.out.println(node.getInternfromseek("CHONE", "Martin").getNext());
		node.deleteIntern("BRACHOTTE","Faustine", tree.getRaf());
	


	//	for (int i = 0; i < 1704; i++) {
	//		System.out.println((tree.readNode(tree.getRaf(), i*Intern.RECORD_LENGTH).getIntern().getName()));
	//}
		// System.out.println("OOOOOOOOOOOOOOOOOOOOO" + tree.getInterns().size() +
		// "OOOOOOOOOOOOOOOOOOOOO");
		for (Intern intern : tree.getInterns()) {
			System.out.println("\n" + intern.getName());

		}

}
}
