package blocksworld.datamining;
import blocksworld.modelling.*;

import java.util.Set;

public interface ItemsetMiner {
    public BooleanDatabase getDatabase();
    public Set<Itemset> extract(float frMin);   

}