package blocksworld.modelling;
import java.util.*;

/**
 * Extension de BlocksWorld avec gestion des contraintes.
 * Crée automatiquement toutes les contraintes nécessaires pour le problème des blocs.
 */
public class BwWithConstraints extends BlocksWorld {
    private final Set<Constraint> constraints;
    
    /**
     * Construit un monde de blocs avec contraintes.
     * 
     * @param numBlocks le nombre de blocs dans le monde
     * @param numPiles le nombre de piles disponibles
     */
    public BwWithConstraints(int numBlocks, int numPiles){
        super(numBlocks,numPiles);
        this.constraints = createAllConstraints();
    }
    
    /**
     * Crée l'ensemble de toutes les contraintes du problème.
     * Inclut les contraintes de différence, fixed et free.
     * 
     * @return l'ensemble de toutes les contraintes
     */
    private Set<Constraint> createAllConstraints() {
        Set<Constraint> constraints = new HashSet<>();
        
        // 1. Contraintes de différence pour les variables on_b != on_b'
        constraints.addAll(createDifferenceConstraints());
        
        // 2. Contraintes fixed : si on_b = b' alors fixed_b' = true
        constraints.addAll(createFixedConstraints());
        
        // 3. Contraintes free : si on_b = -(p+1) alors free_p = false
        constraints.addAll(createFreeConstraints());
        
        return constraints;
    }
    
    /**
     * Crée les contraintes de différence entre toutes les paires de variables on_b.
     * Assure que deux blocs différents ne peuvent pas être au même endroit.
     * 
     * @return l'ensemble des contraintes de différence
     */
    private Set<Constraint> createDifferenceConstraints() {
        Set<Constraint> constraints = new HashSet<>();
        
        for (int b1 = 0; b1 < this.getNumBlocks(); b1++) {
            for (int b2 = b1 + 1; b2 < this.getNumBlocks(); b2++) {
                Variable on1 = this.getVariable("on_" + b1);
                Variable on2 = this.getVariable("on_" + b2);
                constraints.add(new DifferenceConstraint(on1, on2));
            }
        }
        
        return constraints;
    }
    
    /**
     * Crée les contraintes fixed pour tous les blocs.
     * Si un bloc b est sur un bloc b', alors fixed_b' doit être vrai.
     * 
     * @return l'ensemble des contraintes fixed
     */
    private Set<Constraint> createFixedConstraints() {
        Set<Constraint> constraints = new HashSet<>();
        
        for (int b = 0; b < this.getNumBlocks(); b++) {
            for (int bPrime = 0; bPrime < this.getNumBlocks(); bPrime++) {
                if (b != bPrime) {
                    Variable onVar = this.getVariable("on_" + b);
                    Variable fixedVar = this.getVariable("fixed_" + bPrime);
                    constraints.add(new ImplicationOntoFixed(onVar, fixedVar, bPrime));
                }
            }
        }
        
        return constraints;
    }
    
    /**
     * Crée les contraintes free pour toutes les piles.
     * Si un bloc b est sur une pile p, alors free_p doit être faux.
     * 
     * @return l'ensemble des contraintes free
     */
    private Set<Constraint> createFreeConstraints() {
        Set<Constraint> constraints = new HashSet<>();
        
        for (int b = 0; b < this.getNumBlocks(); b++) {
            for (int p = 0; p < this.getNumPiles(); p++) {
                Variable onVar = getVariable("on_" + b);
                Variable freeVar = getVariable("free_" + p);
                //int pileValue = -(p + 1);
                constraints.add(new ImplicationOntoFree(onVar, freeVar, p));
            }
        }
        
        return constraints;
    }
    
    /**
     * Retourne l'ensemble de toutes les contraintes du problème.
     * 
     * @return l'ensemble des contraintes
     */
    public Set<Constraint> getAllConstraints() {
        return this.constraints;
    }
    
    @Override
    public String toString(){
        String res = "===BlocksWorld===\n Variables : " + super.toString() + "\n";
        for(Constraint constraint : this.getAllConstraints()){
            res += constraint.toString() + "\n";
        }
        return res;
    }
}