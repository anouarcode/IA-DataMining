package blocksworld.cp;
import blocksworld.modelling.*;
import blocksworld.planning.*;
import java.util.*;

/**
 * Classe principale pour tester différents solveurs CSP sur le problème du monde des blocs
 * avec différentes combinaisons de contraintes.
 */
public class Main {
   /**
    * Point d'entrée du programme.
    * Compare les performances de BacktrackSolver, MACSolver et HeuristicMACSolver
    * sur 4 cas de contraintes différents.
    * 
    * @param args arguments de la ligne de commande (non utilisés)
    */
   public static void main(String[] args) {

         BwWithConstraintsAndActions blocksworld = new BwWithConstraintsAndActions(10,2);   
         RegularConfigurationConstraints regular = new RegularConfigurationConstraints(blocksworld);
         GrowingConfigurationConstraint grow = new GrowingConfigurationConstraint(blocksworld);
      
         // Cas 1: Régularité seule
         System.out.println("=== CAS 1: Régularité seule ===");
         Set<Constraint> allConstraints = new HashSet<>();
         allConstraints.addAll(regular.getRegularityConstraints());
         BacktrackSolver solver1 = new BacktrackSolver(blocksworld.getVariables(), allConstraints);
         MACSolver solver2 = new MACSolver(blocksworld.getVariables(), allConstraints);
         HeuristicMACSolver solver3 = new HeuristicMACSolver(blocksworld.getVariables(), allConstraints, 
         new NbConstraintsVariableHeuristic(allConstraints, true), new RandomValueHeuristic(new Random()));
         
         long start = System.currentTimeMillis();
         Map<Variable,Object> sol1 = solver1.solve();
         long time1 = System.currentTimeMillis() - start;
         
         start = System.currentTimeMillis();
         Map<Variable,Object> sol2 = solver2.solve();
         long time2 = System.currentTimeMillis() - start;
         
         start = System.currentTimeMillis();
         Map<Variable,Object> sol3 = solver3.solve();
         long time3 = System.currentTimeMillis() - start;
         
         System.out.println("Backtrack: " + time1 + "ms - " + (sol1 != null ? sol1 : "Aucune"));
         System.out.println("MAC: " + time2 + "ms - " + (sol2 != null ? sol2 : "Aucune"));
         System.out.println("HeuristicMAC: " + time3 + "ms - " + (sol3 != null ? sol3 : "Aucune"));
         
         // Cas 2: Croissante seule
         System.out.println("\n=== CAS 2: Croissante seule ===");
         allConstraints.clear();
         allConstraints.addAll(grow.getGrowingConstraint());
         
         BacktrackSolver solver4 = new BacktrackSolver(blocksworld.getVariables(), allConstraints);
         MACSolver solver5 = new MACSolver(blocksworld.getVariables(), allConstraints);
         HeuristicMACSolver solver6 = new HeuristicMACSolver(blocksworld.getVariables(), allConstraints, 
         new NbConstraintsVariableHeuristic(allConstraints, true), new RandomValueHeuristic(new Random()));
         
         start = System.currentTimeMillis();
         Map<Variable,Object> sol4 = solver4.solve();
         long time4 = System.currentTimeMillis() - start;
         
         start = System.currentTimeMillis();
         Map<Variable,Object> sol5 = solver5.solve();
         long time5 = System.currentTimeMillis() - start;
         
         start = System.currentTimeMillis();
         Map<Variable,Object> sol6 = solver6.solve();
         long time6 = System.currentTimeMillis() - start;
         
         System.out.println("Backtrack: " + time4 + "ms - " + (sol4 != null ? sol4 : "Aucune"));
         System.out.println("MAC: " + time5 + "ms - " + (sol5 != null ? sol5 : "Aucune"));
         System.out.println("HeuristicMAC: " + time6 + "ms - " + (sol6 != null ? sol6 : "Aucune"));
         
         // Cas 3: Régularité + Croissante
         System.out.println("\n=== CAS 3: Régularité + Croissante ===");
         allConstraints.clear();
         allConstraints.addAll(regular.getRegularityConstraints());
         allConstraints.addAll(grow.getGrowingConstraint());
         
         BacktrackSolver solver7 = new BacktrackSolver(blocksworld.getVariables(), allConstraints);
         MACSolver solver8 = new MACSolver(blocksworld.getVariables(), allConstraints);
         HeuristicMACSolver solver9 = new HeuristicMACSolver(blocksworld.getVariables(), allConstraints, 
             new NbConstraintsVariableHeuristic(allConstraints, true), new RandomValueHeuristic(new Random()));
         
         start = System.currentTimeMillis();
         Map<Variable,Object> sol7 = solver7.solve();
         long time7 = System.currentTimeMillis() - start;
         
         start = System.currentTimeMillis();
         Map<Variable,Object> sol8 = solver8.solve();
         long time8 = System.currentTimeMillis() - start;
         
         start = System.currentTimeMillis();
         Map<Variable,Object> sol9 = solver9.solve();
         long time9 = System.currentTimeMillis() - start;
         
         System.out.println("Backtrack: " + time7 + "ms - " + (sol7 != null ? sol7 : "Aucune"));
         System.out.println("MAC: " + time8 + "ms - " + (sol8 != null ? sol8 : "Aucune"));
         System.out.println("HeuristicMAC: " + time9 + "ms - " + (sol9 != null ? sol9 : "Aucune"));
         
         // Cas 4: Toutes les contraintes
         System.out.println("\n=== CAS 4: Toutes les contraintes (Résulat valide)===");
         allConstraints.clear();
         allConstraints.addAll(regular.getRegularityConstraints());
         allConstraints.addAll(grow.getGrowingConstraint());
         allConstraints.addAll(blocksworld.getAllConstraints());
         
         BacktrackSolver solver10 = new BacktrackSolver(blocksworld.getVariables(), allConstraints);
         MACSolver solver11 = new MACSolver(blocksworld.getVariables(), allConstraints);
         HeuristicMACSolver solver12 = new HeuristicMACSolver(blocksworld.getVariables(), allConstraints, 
             new NbConstraintsVariableHeuristic(allConstraints, true), new RandomValueHeuristic(new Random()));
         
         start = System.currentTimeMillis();
         //Map<Variable,Object> sol10 = solver10.solve();
         long time10 = System.currentTimeMillis() - start;
         
         start = System.currentTimeMillis();
         //Map<Variable,Object> sol11 = solver11.solve();
         long time11 = System.currentTimeMillis() - start;
         
         start = System.currentTimeMillis();
         Map<Variable,Object> sol12 = solver12.solve();
         long time12 = System.currentTimeMillis() - start;
         //System.out.println("Backtrack: " + time10 + "ms - " + (sol10 != null ? sol10 : "Aucune"));
         //System.out.println("MAC: " + time11 + "ms - " + (sol11 != null ? sol11 : "Aucune"));
         System.out.println("HeuristicMAC: " + time12 + "ms - " + (sol12 != null ? sol12 : "Aucune"));
         if(sol12 != null){
            PlanVisualizer.visualizePlan(sol12,new ArrayList<>(), "Solution satisfaisant de toute les contraintes");
         }
  
   }
}