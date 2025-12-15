package blocksworld.cp;
import  blocksworld.modelling.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class RandomValueHeuristic implements ValueHeuristic {
    Random random;
    public RandomValueHeuristic(Random random){
        this.random = random;
    }

    public List<Object> ordering(Variable var, Set<Object> domain){
        List<Object> copyDomainList = new ArrayList<>();
        for(Object o : domain){
            copyDomainList.add(o);
        }
        Collections.shuffle(copyDomainList,random);
        return copyDomainList;
        

    }
    
}
