package blocksworld.modelling;
import java.util.*;

/**
 * Gère la création de contraintes de croissance pour le monde de blocs.
 * Les contraintes de croissance imposent des restrictions sur l'empilement des blocs.
 */
public class GrowingConfigurationConstraint {
    private BlocksWorld world;
    
    /**
     * Construit un gestionnaire de contraintes de croissance.
     * 
     * @param world le monde de blocs sur lequel appliquer les contraintes
     */
    public GrowingConfigurationConstraint(BlocksWorld world) {
        this.world = world;
    }
    
    /**
     * Génère toutes les contraintes de croissance pour toutes les paires de blocs.
     * 
     * @return l'ensemble de toutes les contraintes de croissance
     */
    public Set<Constraint> getGrowingConstraint() {
        Set<Constraint> constraints = new HashSet<>();
        
        for (int b = 0; b < world.getNumBlocks(); b++) {
            for (int bPrime = 0; bPrime < world.getNumBlocks(); bPrime++) {
                if (b != bPrime) {
                    Variable onVarB = world.getVariable("on_" + b);
                    Variable onVarBPrime = world.getVariable("on_" + bPrime);
                    Constraint constraint = new GrowingConstraint(onVarB, onVarBPrime, b, bPrime);
                    constraints.add(constraint);
                }
            }
        }
        
        return constraints;
    }
    
    /**
     * Crée une contrainte de croissance spécifique entre deux blocs.
     * 
     * @param b l'identifiant du premier bloc
     * @param bPrime l'identifiant du second bloc
     * @return la contrainte de croissance entre les deux blocs
     */
    public Constraint createGrowingConstraint(int b, int bPrime) {
        Variable onVarB = world.getVariable("on_"+ b);
        Variable onVarBPrime = world.getVariable("on_"+ bPrime); 
        return new GrowingConstraint(onVarB, onVarBPrime, b, bPrime);  
    }
}