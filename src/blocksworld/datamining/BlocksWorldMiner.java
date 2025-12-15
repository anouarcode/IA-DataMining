package blocksworld.datamining;
import bwgenerator.*;
import bwgeneratordemo.*;
import java.util.*;
import blocksworld.modelling.*;

/**
 * Classe permettant de générer une base de données d'instances du monde des blocs
 * pour le data mining.
 */
public class BlocksWorldMiner extends BwWithConstraints {
    private final Set<BooleanVariable> variables;
    
    /**
     * Constructeur du mineur du monde des blocs.
     * 
     * @param numBlocks le nombre de blocs
     * @param numPiles le nombre de piles
     */
    public BlocksWorldMiner(int numBlocks, int numPiles){
        super(numBlocks,numPiles);
        this.variables = this.createVariables();
    }

    /**
     * Retourne l'ensemble des variables booléennes.
     * 
     * @return l'ensemble des variables booléennes
     */
    public Set<BooleanVariable> getBooleanVariables(){
        return this.variables;
    }
    
    /**
     * Crée l'ensemble de toutes les variables booléennes possibles
     * (fixed, free, on, on-table).
     * 
     * @return l'ensemble des variables booléennes créées
     */
    public Set<BooleanVariable> createVariables(){
        Set<BooleanVariable> set = new HashSet<>();
        for (int b = 0; b < numBlocks; b++) {
            String varName = "fixed_" + b;
            BooleanVariable var = new BooleanVariable(varName);
            set.add(var);  
        }
        
        for (int p = 0; p < numPiles; p++) {
            String varName = "free_" + p;
            BooleanVariable var = new BooleanVariable(varName);
            set.add(var);  
        }

        for (int b = 0; b < numBlocks; b++) {
            for (int bPrime = 0; bPrime < numBlocks; bPrime++) {
                if(b != bPrime){
                    String varName = "on_" + b + "," + bPrime;
                    BooleanVariable var = new BooleanVariable(varName);
                    set.add(var);
                }
            }
        }

        for (int b = 0; b < numBlocks; b++) {
            for (int p = 0; p < numPiles; p++) {
                String varName = "on-table_" + b + "," + p;
                BooleanVariable var = new BooleanVariable(varName);
                set.add(var);
            }
        }
        return set;
    }

    /**
     * Crée un ensemble de variables booléennes à partir d'une configuration de piles.
     * Seules les variables ayant la valeur true sont ajoutées à l'ensemble.
     * 
     * @param piles la liste des piles de blocs
     * @return l'ensemble des variables booléennes vraies dans cette configuration
     */
    public Set<BooleanVariable> createSetFromPile(List<List<Integer>> piles){
        Set<Integer> onTopBlocks = new HashSet<>(); 
        Set<Integer> onbottomBlocks = new HashSet<>();
        Set<BooleanVariable> set = new HashSet<>();
        
        for(int p = 0; p < numPiles; p++){
            List<Integer> pile = piles.get(p);
            
            if(!pile.isEmpty()){
                onTopBlocks.add(pile.get(0)); 
                onbottomBlocks.add(pile.get(pile.size()-1)); 
                
                for(int i = 0; i < pile.size() - 1; i++){
                    int currentBlock = pile.get(i);
                    int blockBelow = pile.get(i + 1);
                    
                    String onVarName = "on_" + currentBlock + "," + blockBelow;
                    BooleanVariable onVar = new BooleanVariable(onVarName);
                    onVar.setValue(true);
                    set.add(onVar); // Toujours true, on l'ajoute
                }
            }
            
            if(pile.isEmpty()) {
                String freeVarName = "free_"+ p;
                BooleanVariable freeVar = new BooleanVariable(freeVarName);
                freeVar.setValue(true);
                set.add(freeVar);
            }
        }

        for(int b = 0; b < numBlocks; b++){
            if(!onTopBlocks.contains(b)) {
                String fixedVarName = "fixed_"+ b;
                BooleanVariable fixedVar = new BooleanVariable(fixedVarName);
                fixedVar.setValue(true);
                set.add(fixedVar);
            }
        }
        for(int b = 0; b < numBlocks; b++){
            for(int p = 0; p < numPiles; p++){
                List<Integer> pile = piles.get(p);
                boolean isOnTable = !pile.isEmpty() && pile.get(pile.size()-1) == b;
                if(isOnTable) {
                    String onTableVarName = "on-table_"+ b + "," + p;
                    BooleanVariable onTableVar = new BooleanVariable(onTableVarName);
                    onTableVar.setValue(true);
                    set.add(onTableVar);
                }
            }
        }

        return set;
    }

    /**
     * Crée une base de données booléenne contenant n instances aléatoires
     * du monde des blocs.
     * 
     * @param n le nombre d'instances à générer
     * @return la base de données booléenne créée
     */
    public BooleanDatabase createDatabase(int n){
        BooleanDatabase db = new BooleanDatabase(this.variables);
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            List<List<Integer>> state = Demo.getState(random);
            Set<BooleanVariable> instance = createSetFromPile(state);
            db.add(instance);
        }
        return db;
    }
}