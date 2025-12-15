package blocksworld.cp;
import  blocksworld.modelling.*;
import java.util.List;
import java.util.Set;


public interface ValueHeuristic {
    public List<Object> ordering(Variable var, Set<Object> domain);
}
