package fr.isika.cda27.projet1.Annuaire.back;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

public class Tree implements Serializable {
	//private static final long serialVersionUID = 1L;
	private Node root;
	public RandomAccessFile raf;

	public Tree(Node root) {
		
		this.root = root;
		try {
			raf = new RandomAccessFile("src/main/resources/arbre.bin","rw");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Tree() {
		try {
			raf = new RandomAccessFile("src/main/resources/arbre.bin","rw");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}


	/**
	 * AJOUT D'UN NOEUD DANS L'ARBRE : - 1ère étape : voir dans la classe Node -
	 * 2ème étape : -> Si l'arbre est vide, on ajoute un noeud racine avant de
	 * mettre en place la méthode addNode(); -> Sinon, on applique la méthode
	 * addNode(); directement * @param intern
	 */
	public void checkRootToAddNode(Intern intern) {
        try {
        	//si le fichier est vide = pas d'arbre
            if (raf.length() == 0) {
                this.root = new Node(intern);
                //ecrire le noeud dans le fichier binaire
                raf.seek(0);
                intern.writeToRandomAccessFile(raf);
                raf.writeInt(-1);
                raf.writeInt(-1);
                raf.writeInt(-1);
         //   } else {
            	//raf.seek(0);
            	//this.root.ReadNode() ;// =lire racine dans le fichier binaire
            //    this.root.ReadNode();
            }
        } catch (IOException e) {
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

	
	/**
	 * Retourne une liste de tous les Intern dans l'arbre en ordre infixe
	 * @return internList
	 */
	public List<Intern> getAllInterns() {
	    List<Intern> internList = new ArrayList<>();
	    if (this.root == null) {
	        root.collectInterns(internList);
	    }
	    return internList;
	}


	

}
