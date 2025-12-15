package blocksworld.modelling;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;

public class DifferenceConstraint implements Constraint {
    Variable v1;
    Variable v2;
    public DifferenceConstraint(Variable v1, Variable v2){
        this.v1 = v1;
        this.v2 = v2;
    }
    @Override
    public Set<Variable> getScope(){
        Set<Variable> set = new HashSet<>();
        set.add(v1);
        set.add(v2);
        return set;
    }
    @Override
    public boolean isSatisfiedBy(Map<Variable,Object> instance){
        for(Variable v : this.getScope()){
            if(!(instance.containsKey(v)) || (instance.get(v) == null)){
               throw new IllegalArgumentException("Instanciation Invalide");

            }
        }
    return !(instance.get(v1).equals(instance.get(v2)));

    }

    @Override
    public String toString() {
        return "Contrainte de différence " + v1.getName() + " != " + v2.getName();
    }
}