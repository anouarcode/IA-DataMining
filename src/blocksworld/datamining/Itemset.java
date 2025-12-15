package blocksworld.datamining;
import blocksworld.modelling.*;
import java.util.*;


public class Itemset {
    Set<BooleanVariable> items;
    float frequency ;

    //constructeur
    public Itemset(Set<BooleanVariable> items,float frequency){
        this.items = items;
        this.frequency = frequency;
    }
    public Set<BooleanVariable> getItems(){
        return this.items;
    }

    public float getFrequency(){
        return this.frequency;
    }

    public String toString(){
        return items.toString() + " frequence : " + frequency ; 
    }
}
