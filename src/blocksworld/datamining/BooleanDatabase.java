package blocksworld.datamining;
import blocksworld.modelling.*;
import java.util.*;

public class BooleanDatabase {
    private Set<BooleanVariable> items;
    private List<Set<BooleanVariable>> transactions;
    public BooleanDatabase(Set<BooleanVariable> items){
        this.transactions = new ArrayList<>();
        this.items = items;
    }

    public void add(Set<BooleanVariable> set){
        this.transactions.add(set);
        
    }

    public Set<BooleanVariable> getItems(){
        return this.items;
    }

    public List<Set<BooleanVariable>> getTransactions(){
        return this.transactions;
    } 

    public String toString(){
        return "Les items : "  + this.items.toString() + "\n" + "Les transactions : " + this.transactions.toString() + "\n";
    }

    
}
