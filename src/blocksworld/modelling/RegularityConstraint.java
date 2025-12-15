package blocksworld.modelling;
import java.util.*;

/**
 * Contrainte de régularité imposant que trois blocs consécutifs empilés
 * respectent une différence arithmétique constante.
 * Si b1 est sur b2 et b2 est sur b3, alors (b2 - b1) doit être égal à (b3 - b2).
 */
public class RegularityConstraint implements Constraint {
    private final Variable onB1, onB2;
    private final int b1, b2;
    private final BlocksWorld world;
    
    /**
     * Construit une contrainte de régularité entre deux blocs.
     * 
     * @param onB1 la variable représentant la position du bloc b1
     * @param onB2 la variable représentant la position du bloc b2
     * @param b1 l'identifiant du bloc b1
     * @param b2 l'identifiant du bloc b2
     * @param world le monde de blocs pour accéder aux autres variables
     */
    public RegularityConstraint(Variable onB1, Variable onB2, int b1, int b2, BlocksWorld world) {
        this.onB1 = onB1;
        this.onB2 = onB2;
        this.b1 = b1;
        this.b2 = b2;
        this.world = world;
    }
    
    /**
     * Retourne l'ensemble des variables impliquées directement dans cette contrainte.
     * 
     * @return l'ensemble contenant on_b1 et on_b2
     */
    @Override
    public Set<Variable> getScope() {
        return Set.of(onB1, onB2);
    }
    
    /**
     * Vérifie si l'instance donnée satisfait la contrainte de régularité.
     * Si b1 est sur b2 et b2 est sur b3, alors vérifie que (b2 - b1) = (b3 - b2).
     * 
     * @param instance l'affectation des variables à vérifier
     * @return true si la contrainte est satisfaite, false sinon
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instance) {
        // Vérifie si b1 est sur b2
        if (instance.get(onB1).equals(b2)) {
            // b1 est sur b2 alors chercher b3 tel que b2 est sur b3
            for (int b3 = 0; b3 < world.getNumBlocks(); b3++) {
                if (b3 != b1 && b3 != b2) {
                    Variable onB3 = world.getVariable("on_" + b3);
                    if (instance.get(onB2).equals(b3)) {
                        // On a b1->b2->b3, vérifier la régularité
                        int diff1 = b2 - b1;
                        int diff2 = b3 - b2;
                        return diff1 == diff2;
                    }
                }
            }
        }
        
        return true; // Pas de triplet détecté -> contrainte satisfaite
    }
    
    @Override
    public String toString(){
        return "RegularityConstraint: b1=" + b1 + ", b2=" + b2;
    }
}