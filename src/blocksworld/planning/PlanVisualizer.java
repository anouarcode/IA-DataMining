package blocksworld.planning;
import bwmodel.BWState;
import bwmodel.BWStateBuilder;
import bwui.BWComponent;
import bwui.BWIntegerGUI;
import blocksworld.modelling.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;

/**
 * Classe permettant de visualiser l'exécution d'un plan dans le monde des blocs.
 */
public class PlanVisualizer {
    
    /**
     * Visualise l'exécution d'un plan en affichant chaque étape dans une fenêtre graphique.
     * 
     * @param initialState l'état initial du monde des blocs
     * @param plan la liste des actions à exécuter
     * @param title le titre de la fenêtre
     */
    public static void visualizePlan(Map<Variable, Object> initialState, java.util.List<Action> plan, String title) {
        int n = countBlocks(initialState);
        // Building state
        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(n);
        for (int b = 0; b < n; b++) {
            Object underObj = initialState.get(new Variable("on_" + b, null)); // Le domaine n'est pas important pour la récupération
            if (underObj instanceof Integer) {
                int under = (Integer) underObj;
                if (under >= 0) {
                    builder.setOn(b, under);
                }
            }
        }
        BWState<Integer> state = builder.getState();
        // Displaying
        BWIntegerGUI gui = new BWIntegerGUI(n);
        JFrame frame = new JFrame(title);
        BWComponent<Integer> component = gui.getComponent(state);
        frame.add(component);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(600,600));
        frame.setVisible(true);
        System.out.println("Début de la simulation du plan (" + plan.size() + " actions)");
        // Playing plan
        Map<Variable, Object> currentState = new HashMap<>(initialState);
        for (int i = 0; i < plan.size(); i++) {
            Action action = plan.get(i);
            try { 
                Thread.sleep(1000); 
            } catch (InterruptedException e) { 
                e.printStackTrace(); 
            }
            // Appliquer l'action
            currentState = action.successor(currentState);
            BWState<Integer> newBWState = createBWState(currentState, n);
            component.setState(newBWState);
            System.out.println("Action " + (i + 1) + " terminée");
        }
        System.out.println("Simulation du plan terminée !");
    }
    
    /**
     * Crée un état BWState à partir d'un état sous forme de map.
     * 
     * @param state l'état sous forme de map de variables
     * @param n le nombre de blocs
     * @return l'état BWState correspondant
     */
    private static BWState<Integer> createBWState(Map<Variable, Object> state, int n) {
        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(n);
        for (int b = 0; b < n; b++) {
            Object underObj = state.get(new Variable("on_" + b, null)); // Le domaine n'est pas important pour la récupération
            if (underObj instanceof Integer) {
                int under = (Integer) underObj;
                if (under >= 0) {
                    builder.setOn(b, under);
                }
            }
        }
        return builder.getState();
    }
    
    /**
     * Compte le nombre de blocs dans un état donné.
     * 
     * @param state l'état du monde des blocs
     * @return le nombre de blocs présents
     */
    private static int countBlocks(Map<Variable, Object> state) {
        int maxBlock = -1;
        for (Variable var : state.keySet()) {
            if (var.getName().startsWith("on_")) {
                String blockStr = var.getName().substring(3);
                try {
                    int block = Integer.parseInt(blockStr);
                    maxBlock = Math.max(maxBlock, block);
                } catch (NumberFormatException e) {
                    // Ignorer
                }
            }
        }
        return maxBlock + 1;
    }
}