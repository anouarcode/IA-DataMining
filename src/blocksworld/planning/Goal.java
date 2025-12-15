package blocksworld.planning;
import java.util.Map;
import blocksworld.modelling.*;

public interface Goal {
    public boolean isSatisfiedBy(Map<Variable,Object> instance);
}
