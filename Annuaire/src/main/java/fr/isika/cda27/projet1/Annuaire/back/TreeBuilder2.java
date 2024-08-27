package fr.isika.cda27.projet1.Annuaire.back;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreeBuilder2 {
    private Tree tree;

    public TreeBuilder2() {
        this.tree = new Tree();
    }

    public Tree getTree() {
        return tree;
    }
    
    /**
     * Ajoute chaque Stagiaire à l'arbre 
     * @param interns
     */
    public void buildTreeFromInterns(List<Intern> interns) {
        Set<Intern> seenInterns = new HashSet<>();
        	for (Intern intern : interns) {
                if (seenInterns.add(intern)) {
                    tree.checkRootToAddNode(intern);
                }
            }
    } 
    
}






