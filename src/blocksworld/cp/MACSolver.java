package blocksworld.cp;
import  blocksworld.modelling.*;
import java.util.*;


public class MACSolver extends AbstractSolver{
    //constructeur

    public MACSolver(Set<Variable> variables,Set<Constraint> contraintes){
        super(variables, contraintes);
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

        Variable x = variableNotI.pop();
        for(Object value : x.getDomain()){
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
