package blocksworld.datamining;
import blocksworld.modelling.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;


public abstract class AbstractItemsetMiner implements ItemsetMiner {
    protected BooleanDatabase transactions;
    public static final Comparator<BooleanVariable> COMPARATOR = 
        (var1, var2) -> var1.getName().compareTo(var2.getName());

    public AbstractItemsetMiner(BooleanDatabase transactions){
        this.transactions = transactions;
    }


    public BooleanDatabase getTransactions(){
        return this.transactions;
    }

    public float frequency(Set<BooleanVariable> items){
        float count = 0f;
        for(Set<BooleanVariable> transaction : transactions.getTransactions()){
                 
                 if(transaction.containsAll(items)){
                    count++;
                 }
        }
        return (float) count/transactions.getTransactions().size();
        
    }

    @Override
    public BooleanDatabase getDatabase(){
        return this.transactions;
    }

    @Override
    public Set<Itemset> extract(float frMin){
        Set<Itemset> set = new HashSet<>();
         for(Set<BooleanVariable> transaction : transactions.getTransactions()){
            if(frequency(transaction) >= frMin){
                set.add(new Itemset(transaction,frequency(transaction)));
            }
        }
        return set;
    }
}
