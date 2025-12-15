package blocksworld.planning;

import java.util.*;
import blocksworld.modelling.*;
public class  BasicGoal implements Goal {
    
    Map<Variable,Object> but;
    public BasicGoal(Map<Variable,Object> but){
        this.but = but;
    }

    public Map<Variable,Object> getGoal(){
        return this.but;
    }
    
    
    @Override
    public boolean isSatisfiedBy(Map<Variable,Object> instance){
        for(Variable goal : but.keySet()){
             if(instance.containsKey(goal)){
                    if(!((but.get(goal)).equals(instance.get(goal)))){
                         return false;
                    }
             } else {
                return false;
             }
        }
        return true;
    
    }
}