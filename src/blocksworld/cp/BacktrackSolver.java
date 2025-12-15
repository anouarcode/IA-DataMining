package blocksworld.cp;
import  blocksworld.modelling.*;
import java.util.*;

public class BacktrackSolver extends AbstractSolver {
    public BacktrackSolver(Set<Variable> variables,Set<Constraint> contraintes){
        super(variables, contraintes);
    }

    @Override
    public Map<Variable, Object> solve(){
        Map<Variable,Object> partielSolution = new HashMap<>();
        LinkedList<Variable> variableNotI = new LinkedList<>();
        for(Variable v : variables){
            variableNotI.add(v);
        }
        return backTracking(partielSolution,variableNotI);

    }

    public Map<Variable, Object> backTracking(Map<Variable,Object> partielSolution,LinkedList<Variable> variableNotI){
        if(variableNotI.isEmpty()){
            return partielSolution;
        }
        Variable x = variableNotI.pop();
        for(Object value : x.getDomain()){
            partielSolution.put(x,value);
            if(isConsistent(partielSolution)){
                Map<Variable, Object> res = backTracking(partielSolution, variableNotI);
                if(res != null){
                    return res;
                }
            }
            partielSolution.remove(x);
        }
        variableNotI.add(x);
        return null;
    }
     


    
}
