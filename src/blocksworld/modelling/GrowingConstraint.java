package blocksworld.modelling;
import java.util.*;

/**
 * Contrainte de croissance imposant que les blocs empilés respectent un ordre croissant.
 * Si un bloc b est sur un bloc b', alors b doit être strictement supérieur à b'.
 */
public class GrowingConstraint implements Constraint {
    private Variable onVarB;
    private Variable onVarBPrime;
    private int b;
    private int bPrime;
    
    /**
     * Construit une contrainte de croissance entre deux blocs.
     * 
     * @param onVarB la variable représentant la position du bloc b
     * @param onVarBPrime la variable représentant la position du bloc b'
     * @param b l'identifiant du bloc b
     * @param bPrime l'identifiant du bloc b'
     */
    public GrowingConstraint(Variable onVarB, Variable onVarBPrime, int b, int bPrime) {
        this.onVarB = onVarB;
        this.onVarBPrime = onVarBPrime;
        this.b = b;
        this.bPrime = bPrime;
    }
    
    /**
     * Retourne l'ensemble des variables impliquées dans cette contrainte.
     * 
     * @return l'ensemble contenant les deux variables on_b et on_b'
     */
    @Override
    public Set<Variable> getScope() {
        return Set.of(onVarB, onVarBPrime);
    }
    
    /**
     * Vérifie si l'instance donnée satisfait la contrainte de croissance.
     * La contrainte est satisfaite si b est sur b' implique que b > b'.
     * 
     * @param instance l'affectation partielle ou complète des variables
     * @return true si la contrainte est satisfaite, false sinon
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instance) {
        Object onBValue = instance.get(onVarB);
        Object onBPrimeValue = instance.get(onVarBPrime);
        
        // Si b est sur bPrime ET bPrime est sur quelque chose d'autre que b
        // Alors on doit avoir b > bPrime (croissance de bas en haut)
        if (onBValue.equals(bPrime)) {
            return b > bPrime;  // Croissance : bloc du dessus > bloc du dessous
        }
        
        return true;
    }
    
    @Override
    public String toString(){
        return "Contrainte de croissance : b = "  + b + " b' = " + bPrime + " b > b' ? ---> " + (bPrime < b) + "\n"; 
    }
}