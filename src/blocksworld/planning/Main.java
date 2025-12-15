package blocksworld.planning;

import blocksworld.modelling.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== CONFIGURATION RÉGULIÈRE ~4000 NŒUDS ===\n");
        
        // le problème avec 9 blocs et 4 piles
        BwWithConstraintsAndActions problem = new BwWithConstraintsAndActions(9, 4);
        RegularConfigurationConstraints regular = new RegularConfigurationConstraints(problem);
        
        // ETAT INITIAL - RÉGULIER
        Map<Variable, Object> initialState = new HashMap<>();
        
        ArrayList<Integer> pile1 = new ArrayList<>();
        pile1.add(0);
        pile1.add(1);
        pile1.add(2); 

        ArrayList<Integer> pile2 = new ArrayList<>();
        pile2.add(3);
        pile2.add(4);
        pile2.add(5); 

        ArrayList<Integer> pile3 = new ArrayList<>();
        pile3.add(6);
        pile3.add(7); 

        ArrayList<Integer> pile4 = new ArrayList<>();
        pile4.add(8); 

        List<List<Integer>> initialPiles = new ArrayList<>();
        initialPiles.add(pile1);
        initialPiles.add(pile2);
        initialPiles.add(pile3);
        initialPiles.add(pile4);
        
        initialState = createStateFromPiles(initialPiles, 4);
  
        // ETAT BUT -  REGULIER
        Map<Variable, Object> goalState = new HashMap<>();

        ArrayList<Integer> goalPile1 = new ArrayList<>();
        goalPile1.add(2);
        goalPile1.add(4);
        goalPile1.add(6); 

        ArrayList<Integer> goalPile2 = new ArrayList<>();
        goalPile2.add(3);
        goalPile2.add(5);
        goalPile2.add(7); 

        ArrayList<Integer> goalPile3 = new ArrayList<>();
        goalPile3.add(0);
        goalPile3.add(1); 

        ArrayList<Integer> goalPile4 = new ArrayList<>();
        goalPile4.add(8); // 8 seul

        List<List<Integer>> goalPiles = new ArrayList<>();
        goalPiles.add(goalPile1);
        goalPiles.add(goalPile2);
        goalPiles.add(goalPile3);
        goalPiles.add(goalPile4);

        goalState = createStateFromPiles(goalPiles, 4);
                
        Goal goal = new BasicGoal(goalState);
        
        // Créer les heuristiques
        Heuristic misplacedHeuristic = new MisplacedBlocksHeuristic(goal);
        Heuristic weight = new WeightedBlocksHeuristic(goal);
        
        // Créer les planificateurs
        AStarPlanner aStarMisplaced = new AStarPlanner(initialState, problem.getActions(), goal, weight);
        
        System.out.println("=== Test configuration régulière cible ~4000 nœuds ===\n");
        
        System.out.println("Test de la configuration initiale:");
        boolean initialIsRegular = regular.testConfig(initialState);
        System.out.println("Configuration initiale régulière: " + initialIsRegular);
        
        System.out.println("\nTest de la configuration but:");
        boolean goalIsRegular = regular.testConfig(goalState);
        System.out.println("Configuration but régulière: " + goalIsRegular);
        
        if(initialIsRegular && goalIsRegular){
            System.out.println("\n Les deux configurations sont régulières - Lancement du planificateur...");
            long startTime = System.currentTimeMillis();
            List<Action> plan = aStarMisplaced.plan();
            long endTime = System.currentTimeMillis();
            
            if (plan != null) {
                System.out.println("PLAN TROUVÉ (" + plan.size() + " actions)");
                System.out.println("Temps: " + (endTime - startTime) + " ms");
                System.out.println("Noeuds explorés: " + aStarMisplaced.getSonde());
                System.out.println("\nPlan complet:");
                for (int i = 0; i <  plan.size(); i++) {
                    System.out.println("  " + (i + 1) + ". " + plan.get(i));
                }
                // Visualisation du plan
                PlanVisualizer.visualizePlan(initialState, plan, "Plan Blocks World - Configuration Régulière");
            } else {
                System.out.println("AUCUN PLAN TROUVÉ");
                System.out.println("Temps: " + (endTime - startTime) + " ms");
                System.out.println("Noeuds explorés: " + aStarMisplaced.getSonde());
            }
        } else {
            System.out.println("\n Au moins une configuration n'est pas régulière !");
            System.out.println("La planification ne sera pas lancée.");
        }
    }

   public static Map<Variable, Object> createStateFromPiles(List<List<Integer>> piles, int totalPiles) {
        Map<Variable, Object> state = new HashMap<>();
        
        // Définir les domaines
        Set<Object> booleanDomain = Set.of(true, false);
        Set<Object> onDomain = createOnDomain(piles, totalPiles);
        
        for (int p = 0; p < totalPiles; p++) {
            state.put(new Variable("free_" + p, booleanDomain), true);
        }
        
        int totalBlocks = 0;
        for (List<Integer> pile : piles) {
            totalBlocks += pile.size();
        }
        for (int b = 0; b < totalBlocks; b++) {
            state.put(new Variable("fixed_" + b, booleanDomain), false);
        }
        
        for (int pileIndex = 0; pileIndex < piles.size(); pileIndex++) {
            List<Integer> pile = piles.get(pileIndex);
            int pileId = -(pileIndex + 1);
            
            if (!pile.isEmpty()) {
                state.put(new Variable("free_" + pileIndex, booleanDomain), false);
                
                int bottomBlock = pile.get(pile.size() - 1);
                state.put(new Variable("on_" + bottomBlock, onDomain), pileId);
                
                for (int i = 0; i < pile.size() - 1; i++) {
                    int currentBlock = pile.get(i);
                    int blockBelow = pile.get(i + 1);
                    state.put(new Variable("on_" + currentBlock, onDomain), blockBelow);
                    state.put(new Variable("fixed_" + blockBelow, booleanDomain), true);
                }
                
                int topBlock = pile.get(0);
                state.put(new Variable("fixed_" + topBlock, booleanDomain), false);
            }
        }
        
        return state;
    }

    // Méthode utilitaire pour créer le domaine des variables "on"
    private static Set<Object> createOnDomain(List<List<Integer>> piles, int totalPiles) {
        Set<Object> domain = new HashSet<>();
        
        // Ajouter les identifiants de piles (négatifs)
        for (int p = 0; p < totalPiles; p++) {
            domain.add(-(p + 1));
        }
        
        // Ajouter les identifiants de blocs
        int totalBlocks = 0;
        for (List<Integer> pile : piles) {
            totalBlocks += pile.size();
        }
        for (int b = 0; b < totalBlocks; b++) {
            domain.add(b);
        }
        
        // Ajouter -1 pour représenter "sur la table" ou aucune pile
        domain.add(-1);
        
        return domain;
    }
}