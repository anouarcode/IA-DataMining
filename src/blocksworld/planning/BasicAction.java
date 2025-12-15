package blocksworld.planning;
import java.util.*;
import blocksworld.modelling.*;
public class BasicAction implements Action {
    Map<Variable, Object> pre;
    Map<Variable, Object> eff;
    int cout;

    public BasicAction(Map<Variable, Object> pre,Map<Variable, Object> eff,int cout){
        this.pre = pre;
        this.eff = eff;
        this.cout = cout;
    }
    @Override
    public boolean isApplicable(Map<Variable,Object> instance) {
       for(Variable v : this.pre.keySet()){
          if(!instance.containsKey(v) || !(this.pre.get(v).equals(instance.get(v)))){
            return false;
          }
       }
       return true;
    }

    @Override
    public Map<Variable, Object> successor(Map<Variable, Object> instance){
        if(isApplicable(instance)){
            Map<Variable, Object> newInstance = new HashMap<>();
            for(Map.Entry<Variable,Object> e : eff.entrySet()){
                newInstance.put(e.getKey(), e.getValue());
            }
            for(Variable e : instance.keySet()){
                if(!(newInstance.containsKey(e))){
                    newInstance.put(e,instance.get(e));
                }
            }
            return newInstance;

        } else {
            return instance;
        }
    }

    @Override
    public int getCost(){
        return this.cout;
    }

    @Override
    public String toString(){
        return "Precondition : " + pre.toString() + "\nEffets : " + eff.toString();
    }


    
}
