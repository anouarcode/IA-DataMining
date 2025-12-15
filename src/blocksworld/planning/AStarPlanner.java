package blocksworld.planning;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import blocksworld.modelling.*;

public class AStarPlanner implements Planner {
    Map<Variable,Object> initialState;
    Set<Action> actions;
    Goal but;
    Heuristic heuristic;
    public  int sonde;
    public  boolean activate;

    public AStarPlanner(Map<Variable,Object> initialState,Set<Action> actions, Goal but,Heuristic heuristic){
        this.initialState = initialState;
        this.actions =  actions;
        this.but = but;
        this.heuristic = heuristic;
        this.sonde = 1;
        this.activate = true;
    }
    public int getSonde(){
        return this.sonde;
    }
    public void activateNodeCount(boolean active){
        activate = active;
    }
    @Override
    public Goal getGoal(){
        return this.but;
    }
    
    @Override
    public Map<Variable,Object> getInitialState(){
        return this.initialState;
    }

    @Override
    public Set<Action> getActions(){
        return this.actions;
    }

    public Heuristic getHeuristic(){
        return this.heuristic;
    }

     @Override
    public List<Action> plan(){
      Map<Map<Variable,Object>,Map<Variable,Object>> father = new HashMap<>();
      Map<Map<Variable,Object>,Action> plan = new HashMap<>();
      Map<Map<Variable,Object>,Float> distance = new HashMap<>();
      Map<Map<Variable,Object>,Float> value = new HashMap<>();
      value.put(initialState,heuristic.estimate(initialState));
      father.put(getInitialState(),null);
      distance.put(getInitialState(),0.f);
      PriorityQueue<Map<Variable,Object>> open = new PriorityQueue<>(Comparator.comparingDouble(state -> value.get(state)));
      open.add(getInitialState());
      while(!open.isEmpty()){
        Map<Variable,Object> instantiation = open.peek();
        if(activate){
            sonde++;
        }
        open.remove(instantiation);
        if(getGoal().isSatisfiedBy(instantiation)){
            return getAStarPlan(father,plan,instantiation);
        }
        for(Action action : getActions()){
            if(action.isApplicable(instantiation)){
                Map<Variable,Object> next = action.successor(instantiation);
                if(!distance.containsKey(next)){
                    distance.put(next,Float.POSITIVE_INFINITY);
                }
                if(distance.get(next) > (distance.get(instantiation) + action.getCost())){
                    distance.put(next,(distance.get(instantiation)+ action.getCost()));
                    value.put(next,(distance.get(next) + heuristic.estimate(next)));
                    father.put(next,instantiation);
                    plan.put(next,action);
                    open.add(next);
                }
        }
    }
    }
    return null;
}

    public List<Action> getAStarPlan(Map<Map<Variable,Object>,Map<Variable,Object>> father,Map<Map<Variable,Object>,Action> plan,Map<Variable,Object> but){
        List<Action> AStar_plan = new ArrayList<>();
        while(father.get(but) != null){
            AStar_plan.add(plan.get(but));
            but = father.get(but);
        }
        return AStar_plan.reversed();
    }

}
