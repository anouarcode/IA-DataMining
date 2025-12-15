package blocksworld.cp;
import  blocksworld.modelling.*;
import java.util.Map;
import java.util.Set;

public class NbConstraintsVariableHeuristic implements VariableHeuristic {
    Set<Constraint> contraintes;
    boolean degree;
    public NbConstraintsVariableHeuristic(Set<Constraint> contraintes,boolean degree){
        this.contraintes = contraintes;
        this.degree = degree;

    }

    @Override
    public Variable best(Set<Variable> variables,Map<Variable, Set<Object>> domaine){
        int maxCount = 0;
        int minCount = Integer.MAX_VALUE;
        Variable varMin = null;
        Variable varMax = null;
        for(Variable var : variables){
            int count = 0;
            for(Constraint contrainte : contraintes){
                if(contrainte.getScope().contains(var)){
                        count++;
                }
            }
            if(count > maxCount){
                maxCount = count;
                varMax = var;
            }

            if(count < minCount){
                minCount = count;
                varMin = var;
            }



        }
        if(degree){
            return varMax;
        }
        return varMin;

    }
    
}
