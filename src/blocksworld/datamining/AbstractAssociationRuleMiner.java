package blocksworld.datamining;
import blocksworld.modelling.*;

import java.util.HashSet;
import java.util.Set;


public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner {
    BooleanDatabase database;
    public AbstractAssociationRuleMiner(BooleanDatabase database){
        this.database = database;
    }


    @Override
    public BooleanDatabase getDatabase(){
        return this.database;
    }

    public static float frequency(Set<BooleanVariable> item, Set<Itemset> Itemset){
        for(Itemset itemset : Itemset){
            if(itemset.getItems().equals(item)){
                return itemset.getFrequency();
            }
        }
        throw new IllegalCallerException("item not found in Itemset");
        
    }

    public static float confidence(Set<BooleanVariable> premisse, Set<BooleanVariable> conclusion,Set<Itemset> Itemset){
        Set<BooleanVariable> union = new HashSet<>();
        union.addAll(premisse);
        union.addAll(conclusion);
        float frUnion = frequency(union, Itemset);
        float frprem  = frequency(premisse, Itemset);
        return (float) frUnion/frprem;
        
    
    }

}