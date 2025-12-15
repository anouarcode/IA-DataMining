package blocksworld.planning;
import java.util.*;
import blocksworld.modelling.*;

/**
 * Heuristique pondérée pour le problème du monde des blocs.
 * Calcule une distance en attribuant des poids différents selon le type de variable.
 */
public class WeightedBlocksHeuristic implements Heuristic {
    Goal goal;
    
    /**
     * Constructeur de l'heuristique pondérée.
     * 
     * @param goal le but à atteindre
     */
    public WeightedBlocksHeuristic(Goal goal){
        this.goal = goal;
    }
    
    /**
     * Estime la distance entre un état donné et le but.
     * Les relations "on" ont un poids de 3, les blocs "fixed" un poids de 2,
     * et les piles "free" un poids de 1.
     * 
     * @param state l'état actuel à évaluer
     * @return la distance pondérée estimée jusqu'au but
     */
    @Override
    public float estimate(Map<Variable, Object> state) {
        if(goal.isSatisfiedBy(state)){
            return (float) 0;
        }
        int distance = 0;
        Map<Variable,Object> goalState = ((BasicGoal) goal).getGoal();
        for (Variable key : goalState.keySet()) {
            // Poids plus important pour les relations "on" (3 points)
            if (key.getName().startsWith("on_")) {
                if (!state.get(key).equals(goalState.get(key))) {
                    distance += 3;
                }
            }
            // Poids moyen pour les blocs fixed (2 points)  
            if (key.getName().startsWith("fixed_")) {
                boolean goalFixed = (Boolean) goalState.get(key);
                boolean currentFixed = (Boolean) state.get(key);
                if (goalFixed != currentFixed) {
                    distance += 2;
                }
            }
            // Poids faible pour les piles free (1 point)
            if (key.getName().startsWith("free_")) {
                if (!state.get(key).equals(goalState.get(key))) {
                    distance += 1;
                }
            }
        }
        return (float) distance;
    }
}