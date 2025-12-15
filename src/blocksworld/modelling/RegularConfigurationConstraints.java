package blocksworld.modelling;
import java.util.*;

/**
 * Gère la création et la vérification des contraintes de régularité pour le monde de blocs.
 * Les contraintes de régularité assurent que les configurations respectent certaines règles structurelles (même écart entre les blocs).
 */
public class RegularConfigurationConstraints {
    private BlocksWorld world;
    
    /**
     * Construit un gestionnaire de contraintes de régularité.
     * 
     * @param world le monde de blocs sur lequel appliquer les contraintes
     */
    public RegularConfigurationConstraints(BlocksWorld world) {
        this.world = world;
    }
    
    /**
     * Génère toutes les contraintes de régularité pour toutes les paires de blocs.
     * 
     * @return l'ensemble de toutes les contraintes de régularité
     */
    public Set<Constraint> getRegularityConstraints() {
        Set<Constraint> constraints = new HashSet<>();
        
        // Pour chaque paire de blocs distincts
        for (int b1 = 0; b1 < world.getNumBlocks(); b1++) {
            for (int b2 = 0; b2 < world.getNumBlocks(); b2++) {
                if (b1 != b2) {
                    Constraint constraint = createRegularityConstraint(b1, b2);
                    constraints.add(constraint);
                }
            }
        }
        
        return constraints;
    }
    
    /**
     * Crée une contrainte de régularité spécifique entre deux blocs.
     * 
     * @param b1 l'identifiant du premier bloc
     * @param b2 l'identifiant du second bloc
     * @return la contrainte de régularité entre les deux blocs
     */
    private Constraint createRegularityConstraint(int b1, int b2) {
        return new RegularityConstraint(
            world.getVariable("on_" + b1),
            world.getVariable("on_" + b2), 
            b1, b2,
            world
        );
    }
    
    /**
     * Teste si une configuration donnée satisfait toutes les contraintes de régularité.
     * Affiche les contraintes non satisfaites s'il y en a.
     * 
     * @param config la configuration à tester (affectation des variables)
     * @return true si toutes les contraintes sont satisfaites, false sinon
     */
    public boolean testConfig(Map<Variable, Object> config) {
        boolean toutesOk = true;
        
        for (Constraint c : this.getRegularityConstraints()) {
            if (!c.isSatisfiedBy(config)) {
                System.out.println("X " + c);
                toutesOk = false;
                break;
            }
        }   
        
        return toutesOk;
    }
}