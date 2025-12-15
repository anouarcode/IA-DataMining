package blocksworld.planning;
import java.util.*;
import blocksworld.modelling.*;

public interface Action {
    public boolean isApplicable(Map<Variable,Object> instance);
    public Map<Variable, Object> successor(Map<Variable, Object> instance);
    public int getCost();
}