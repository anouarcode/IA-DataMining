package blocksworld.planning;
import java.util.*;
import blocksworld.modelling.*;

public interface Heuristic {
    public float estimate(Map<Variable, Object> instantiation);
}
