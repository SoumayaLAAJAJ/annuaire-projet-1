package fr.isika.cda27.projet1.Annuaire.back;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tree implements Serializable {
	//private static final long serialVersionUID = 1L;
	private Node root;

	public Tree(Node root) {
		this.root = root;
	}

	public Tree() {
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public boolean isEmpty() {
		return (this.root == null);
	}

	/**
	 * AJOUT D'UN NOEUD DANS L'ARBRE : - 1ère étape : voir dans la classe Node -
	 * 2ème étape : -> Si l'arbre est vide, on ajoute un noeud racine avant de
	 * mettre en place la méthode addNode(); -> Sinon, on applique la méthode
	 * addNode(); directement * @param intern
	 */
	public void checkRootToAddNode(Intern intern) {
        try {
            if (isEmpty()) {
                this.root = new Node(intern);
            } else {
                this.root.addNode(intern);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }

	/**
	 * AFFICHAGE PAR ORDRE ALPHABETIQUE SELON PARCOURS INFIXE - 1ere étape : Voir
	 * dans la classe Node - 2eme étape : -> SI l'arbre est vide, afficher un
	 * message dans la console -> SINON, afficher chaque noeud de l'arbre selon la
	 * méthode displayNode(); définie dans la classe Node
	 * 
	 */
	public void checkRootToDisplayNodes() {
		if (isEmpty()) {
			System.out.println("L'arbre est vide");
		} else {
			this.root.displayNode();
		}
	}
	
	
	/**
	 * Charge un arbre depuis le fichier binaire
	 * Utile pour la persistance de l'arbre (c'est à dire le fait de sauvegarder l'état actuel) et récupérer les données pour plus tard
	 * IOException, ClassNotFoundException : gèrent les erreurs dans la lecture du fichier ; par exemple s'il n'est pas trouvé ou si le fichier contient des données qui ne sont pas interprétées comme un Tree (comme ce qu'il y a tout au dessus du fichier binaire)
	 * ObjectInputStream : utilisé pour lire des objets à partir d'un flux binaire 
	 *
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	//TODO: PAS DE STATIC
	// class Tree avec les méthodes de CRUD de l'arbre - dans chacune de ces méthodes, on vérifie si fichier binaire est vide, si non null, on lit le premier noeud comme racine et méthode récursive à partir de là 
	public static Tree loadTreeFromBinaryFile(String filePath) throws IOException, ClassNotFoundException {
	    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
	    	// Ici je fais un cast car readObject retourne un objet générique et je veux que ce soit traité comme un Tree
	        return (Tree) in.readObject();
	    }
	}
	
	/**
	 * Retourne une liste de tous les Intern dans l'arbre en ordre infixe
	 * @return internList
	 */
	public List<Intern> getAllInterns() {
	    List<Intern> internList = new ArrayList<>();
	    if (!isEmpty()) {
	        root.collectInterns(internList);
	    }
	    return internList;
	}


	

}
