package blocksworld.cp;
import  blocksworld.modelling.*;
import java.util.Map;
import java.util.Set;


public class DomainSizeVariableHeuristic implements VariableHeuristic {
    boolean degree;
    public DomainSizeVariableHeuristic(boolean degree){
        this.degree = degree;
    }

    @Override
    public Variable best(Set<Variable> variables,Map<Variable, Set<Object>> domaine){
        Variable BestVarMax = null;
        Variable BestVarMin = null;
        int domainSizeMax = 0;
        int domainSizeMin = Integer.MAX_VALUE;
        for(Variable var : variables){
            if(domaine.get(var).size() > domainSizeMax){
                BestVarMax = var;
                domainSizeMax = domaine.get(var).size();
            }
            if(domainSizeMin > domaine.get(var).size()){
                BestVarMin = var;
                domainSizeMin = domaine.get(var).size();
            }
        }
        if(degree){
            return  BestVarMax;
        }
        return BestVarMin;

    }
    
}
