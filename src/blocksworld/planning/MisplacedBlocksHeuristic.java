package blocksworld.planning;
import blocksworld.modelling.*;
import java.util.*;

/**
 * Heuristique basée sur le comptage des blocs mal placés.
 * Compte le nombre de variables "on_b" dont la valeur dans l'état courant
 * diffère de la valeur dans l'état but.
 */
public class MisplacedBlocksHeuristic implements Heuristic{
    Goal goal;
    
    /**
     * Construit une heuristique de blocs mal placés.
     * 
     * @param goal l'état but à atteindre
     */
    public MisplacedBlocksHeuristic(Goal goal){
        this.goal = goal;
    }
    
    /**
     * Estime le coût restant pour atteindre le but depuis l'instanciation donnée.
     * Retourne le nombre de blocs qui ne sont pas à leur position finale.
     * 
     * @param instantiation l'état courant (affectation des variables)
     * @return le nombre de blocs mal placés, ou 0 si le but est atteint
     */
    @Override 
    public float estimate(Map<Variable, Object> instantiation){
        if(goal.isSatisfiedBy(instantiation)){
            return (float) 0;
        }
        
        int count = 0;
        if(goal instanceof BasicGoal){
            Map<Variable,Object> but = ((BasicGoal) goal).getGoal();
            for(Map.Entry<Variable,Object> goalEntry : but.entrySet()){
                Variable key = goalEntry.getKey();
                Object value = goalEntry.getValue();
                if(key.getName().startsWith("on_")){
                    Object instanceValue = instantiation.get(key);
                    if(instanceValue != null && !instanceValue.equals(value)){
                        count++;
                    }
                }
            }
        }
        
        return (float)count;
    }
}
