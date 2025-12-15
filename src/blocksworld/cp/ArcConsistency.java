package blocksworld.cp;
import  blocksworld.modelling.*;
import java.util.*;
public class ArcConsistency {
    Set<Constraint> constraintes;
    
    public ArcConsistency(Set<Constraint> constraintes){
        this.constraintes = constraintes;
        for(Constraint constraint : this.constraintes){
            Set<Variable> scope = constraint.getScope();
            if(scope.size() > 2){
                throw new IllegalArgumentException("Ni unaire ni binaire");
            }

        }
    }
    
    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> EDo){
        Map<Variable, Set<Object>> ED = new HashMap<>();
        
        for(Variable v : EDo.keySet()){
            ED.put(v,new HashSet<>((EDo.get(v))));
        }
        for(Variable x : ED.keySet()){
            for(Object v : ED.get(x)){
               for(Constraint c : this.constraintes){
                    Set<Variable> scope = c.getScope();
                    if(scope.size() == 1 ){
                        Map<Variable,Object> instance = new HashMap<>();
                        instance.put(x,v);
                        if(instance.keySet().containsAll(c.getScope())){
                            if(!c.isSatisfiedBy(instance)){
                                EDo.get(x).remove(v);
                            }
                        }
                    }
                }
            }
        }
        for(Variable x : EDo.keySet()){
            if(EDo.get(x).isEmpty()){
                return false;
            }
        }
        return true;
    }


    public boolean revise(Variable v1,Set<Object> D1,Variable v2,Set<Object> D2){
        boolean del=false;
        Set<Object> D1copie = new HashSet<>();
        for(Object value : D1){
            D1copie.add(value);

        }
        for(Object vi : D1copie){
            boolean viable = false;
            for(Object vj : D2){
                boolean tousSatisfait = true;
                for(Constraint c : this.constraintes){
                    Set<Variable> scope = c.getScope();
                    if(scope.size()== 2 && scope.contains(v1) && scope.contains(v2)){
                         Map<Variable,Object> instance = new HashMap<>();
                         instance.put(v1,vi);
                         instance.put(v2,vj);
                         if(!c.isSatisfiedBy(instance)){
                            tousSatisfait = false;
                            break;
                         }
                    }
                }
                if(tousSatisfait){
                    viable = true;
                    break;
                }    
            }
            if(!viable){
                D1.remove(vi);
                del = true;
            }
        }
        return del;
    }

    public boolean ac1(Map<Variable, Set<Object>> ED){
        if(!enforceNodeConsistency(ED)){
            return false;
        }
        boolean change ;
        do {
            change = false;
            for(Variable xi : ED.keySet()){
                    for(Variable xj : ED.keySet()){
                        if(!xi.equals(xj)){
                            if(revise(xi,ED.get(xi),xj,ED.get(xj))){
                                change = true;
                            }
                        }
                    }
            }
        }while(change == true);
        for(Variable x : ED.keySet()){
            if(ED.get(x).isEmpty()){
                return false;
            }
        }
        return true;
    }
}