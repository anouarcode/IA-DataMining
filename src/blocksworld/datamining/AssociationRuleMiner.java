package blocksworld.datamining;
import blocksworld.modelling.*;
import java.util.*;

public interface AssociationRuleMiner {
    public BooleanDatabase getDatabase();
    public Set<AssociationRule> extract(float frMin, float confMin);
    
}
