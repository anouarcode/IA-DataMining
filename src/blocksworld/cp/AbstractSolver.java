package blocksworld.cp;
import  blocksworld.modelling.*;
import java.util.*;


public class AbstractSolver implements Solver {
     Set<Variable> variables;
     Set<Constraint> contraintes;
     public AbstractSolver(Set<Variable> variables,Set<Constraint> contraintes){
        this.variables = variables;
        this.contraintes = contraintes;

     }
     public boolean isConsistent(Map<Variable,Object> instance){
          for(Constraint contrainte : this.contraintes){
            if(instance.keySet().containsAll(contrainte.getScope())){
               if(!contrainte.isSatisfiedBy(instance)){
                return false;
              }
            }
          }
          return true;

     }

    @Override
    public Map<Variable, Object> solve(){
        return null;
    }
    
}
