package blocksworld.modelling;
import java.util.*;

/**
 * Représente un monde de blocs avec des variables et des piles.
 * Gère la création et l'accès aux variables du problème.
 */
public class BlocksWorld {
    protected final int numBlocks;
    protected final int numPiles;
    protected final Map<Variable, Object> variables; 
    
    /**
     * Construit un monde de blocs avec le nombre spécifié de blocs et de piles.
     * 
     * @param numBlocks le nombre de blocs dans le monde
     * @param numPiles le nombre de piles disponibles
     */
    public BlocksWorld(int numBlocks, int numPiles) {
        this.numBlocks = numBlocks;
        this.numPiles = numPiles;
        this.variables = new HashMap<>();
        createVariables();
    }
    
    /**
     * Crée toutes les variables du problème (on_b, fixed_b, free_p).
     */
    private void createVariables() {
        // Création des variables on_b pour chaque bloc
        for (int b = 0; b < numBlocks; b++) {
            String varName = "on_" + b;
            Set<Object> domain = createDomainForBlock(b);
            Variable var = new Variable(varName, domain);
            variables.put(var, null);  
        }
        
        // Création des variables fixed_b pour chaque bloc
        for (int b = 0; b < numBlocks; b++) {
            String varName = "fixed_" + b;
            Variable var = new BooleanVariable(varName);
            variables.put(var, null);  
        }
        
        // Création des variables free_p pour chaque pile
        for (int p = 0; p < numPiles; p++) {
            String varName = "free_" + p;
            Variable var = new BooleanVariable(varName);
            variables.put(var, null);  
        }
    }
    
    /**
     * Crée le domaine de valeurs possibles pour un bloc donné.
     * Le domaine contient les piles (valeurs négatives) et les autres blocs.
     * 
     * @param blockId l'identifiant du bloc
     * @return l'ensemble des valeurs possibles pour ce bloc
     */
    private Set<Object> createDomainForBlock(int blockId) {
        Set<Object> domain = new HashSet<>();
        
        // Ajout des piles (valeurs négatives: -1, -2, ..., -numPiles)
        for (int p = 1; p <= numPiles; p++) {
            domain.add(-p);
        }
        
        // Ajout des autres blocs (0, 1,..., numBlocks-1) sauf le bloc lui-même
        for (int b = 0; b < numBlocks; b++) {
            if (b != blockId) {
                domain.add(b);
            }
        }
        
        return domain;
    }
    
    /**
     * Retourne l'ensemble des variables du problème.
     * 
     * @return un ensemble contenant toutes les variables
     */
    public Set<Variable> getVariables() {
        return new HashSet<>(variables.keySet()); 
    }
    
    /**
     * Recherche une variable par son nom.
     * 
     * @param name le nom de la variable recherchée
     * @return la variable correspondante, ou null si non trouvée
     */
    public Variable getVariable(String name) {
        for (Variable var : variables.keySet()) { 
            if (var.getName().equals(name)) {
                return var;
            }
        }
        return null;
    }
    
    /**
     * Retourne le nombre de blocs dans le monde.
     * 
     * @return le nombre de blocs
     */
    public int getNumBlocks() {
        return numBlocks;
    }
    
    /**
     * Retourne le nombre de piles disponibles.
     * 
     * @return le nombre de piles
     */
    public int getNumPiles() {
        return numPiles;
    }
    
    @Override
    public String toString(){
        return this.getVariables().toString();
    }
}