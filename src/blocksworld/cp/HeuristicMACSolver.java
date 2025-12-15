package blocksworld.cp;
import  blocksworld.modelling.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class HeuristicMACSolver extends AbstractSolver {
    VariableHeuristic heuristicVar;
    ValueHeuristic heuristicVal;
    public HeuristicMACSolver(Set<Variable> variables,Set<Constraint> contraintes,VariableHeuristic heuristicVar,ValueHeuristic heuristicVal){
        super(variables,contraintes);
        this.heuristicVar = heuristicVar;
        this.heuristicVal = heuristicVal;
        
    }

     public Map<Variable, Object> solve(){
        Map<Variable,Object> partielSolution = new HashMap<>();
        LinkedList<Variable> variableNotI = new LinkedList<>();
        Map<Variable,Set<Object>> ED = new HashMap<>();
        for(Variable v : variables){
            variableNotI.add(v);
        }
        return Mac(partielSolution,variableNotI,ED);

    }

    public Map<Variable, Object> Mac(Map<Variable,Object> partielSolution,LinkedList<Variable> variableNotI, Map<Variable,Set<Object>> ED){
        if(variableNotI.isEmpty()){
            return partielSolution;
        }
        ArcConsistency arc = new ArcConsistency(contraintes);
        if(!arc.ac1(ED)){
            return null;
        }
        Set<Variable> setInstance = new HashSet<>();
        for(Variable x : variableNotI){
            setInstance.add(x);
        }
        Variable x = heuristicVar.best(setInstance,ED);
        if(x == null){
            return partielSolution;
        }
        variableNotI.remove(x);
        List<Object> listVal = heuristicVal.ordering(x,x.getDomain());

        for(Object value : listVal){
            partielSolution.put(x,value);
            if(isConsistent(partielSolution)){
                Map<Variable, Object> res = Mac(partielSolution, variableNotI,ED);
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
