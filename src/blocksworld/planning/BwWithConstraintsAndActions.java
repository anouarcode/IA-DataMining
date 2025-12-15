package blocksworld.planning;
import blocksworld.modelling.*;
import java.util.*;

/**
 * Extension de BwWithConstraints avec gestion des actions de planification.
 * Crée automatiquement toutes les actions possibles pour manipuler les blocs.
 */
public class BwWithConstraintsAndActions extends BwWithConstraints{
    private final Set<Action> actions;
    
    /**
     * Construit un monde de blocs avec contraintes et actions.
     * 
     * @param numBlocks le nombre de blocs dans le monde
     * @param numPiles le nombre de piles disponibles
     */
    public BwWithConstraintsAndActions(int numBlocks, int numPiles){
        super(numBlocks,numPiles);
        this.actions = createAllActions();
    }

    /**
     * Crée l'ensemble de toutes les actions possibles.
     * Inclut les quatre types d'actions : déplacer un bloc sur un autre bloc,
     * déplacer un bloc vers une pile vide, déplacer un bloc d'une pile vers un autre bloc,
     * et déplacer un bloc d'une pile vers une autre pile vide.
     * 
     * @return l'ensemble de toutes les actions
     */
    private Set<Action> createAllActions() {
        Set<Action> actions = new HashSet<>();
        
        // 1
        actions.addAll(createMoveBlockOntoBlockAction());
        
        // 2
        actions.addAll(createMoveBlockToEmptyPileAction());
        
        // 3
        actions.addAll(createMoveBlockUnderPileOntoBlockAction());

        // 4
        actions.addAll(createMoveBlockUnderPileToEmptyPileAction());
        
        return actions;
    }

    /**
     * Crée les actions pour déplacer un bloc b d'un bloc b' vers un autre bloc b''.
     * Préconditions : b est sur b', b n'est pas fixé, b' est fixé, b'' n'est pas fixé.
     * Postconditions : b est sur b'', b'' devient fixé, b' n'est plus fixé, b reste non fixé.
     * 
     * @return l'ensemble des actions de type "déplacer bloc sur bloc"
     */
    private Set<Action> createMoveBlockOntoBlockAction() {
        Set<Action> actions = new HashSet<>();
        for (int b = 0; b < this.getNumBlocks(); b++) {
            for (int bPrime = 0; bPrime < this.getNumBlocks(); bPrime++) {
                if(b != bPrime){
                    for(int bSecond = 0; bSecond < this.getNumBlocks(); bSecond++){
                        if(bPrime != bSecond && b != bSecond){
                            Variable onB = this.getVariable("on_" + b);
                            Variable fixedB = this.getVariable("fixed_" + b);
                            Variable fixedBSecond = this.getVariable("fixed_" + bSecond);
                            Variable fixedBPrime = this.getVariable("fixed_" + bPrime);
                            Map<Variable,Object> pre = new HashMap<>();
                            Map<Variable,Object> post = new HashMap<>();
                            // precondition
                            pre.put(onB,bPrime);
                            pre.put(fixedB,Boolean.FALSE);
                            pre.put(fixedBPrime,Boolean.TRUE);
                            pre.put(fixedBSecond,Boolean.FALSE);
                            // post-condition
                            post.put(onB,bSecond);
                            post.put(fixedBSecond,Boolean.TRUE);
                            post.put(fixedBPrime,Boolean.FALSE);
                            post.put(fixedB,Boolean.FALSE);
                            actions.add(new BasicAction(pre,post,1));
                        }
                    }
                }
            }
        }
        return actions;
    }

    /**
     * Crée les actions pour déplacer un bloc b d'un bloc b' vers une pile vide p.
     * Préconditions : b est sur b', b n'est pas fixé, b' est fixé, p est libre.
     * Postconditions : b est sur la pile p, p n'est plus libre, b' n'est plus fixé, b reste non fixé.
     * 
     * @return l'ensemble des actions de type "déplacer bloc vers pile vide"
     */
    private Set<Action> createMoveBlockToEmptyPileAction() {
        Set<Action> actions = new HashSet<>();
        for (int b = 0; b < this.getNumBlocks(); b++) {
            for (int bPrime = 0; bPrime < this.getNumBlocks(); bPrime++) {
                if(b != bPrime){
                    for (int p = 0; p < this.getNumPiles(); p++) {
                        Variable onB = this.getVariable("on_" + b);
                        Variable fixedB = this.getVariable("fixed_" + b);
                        Variable freeP = this.getVariable("free_" + p);
                        Variable fixedBPrime = this.getVariable("fixed_" + bPrime);
                        Map<Variable,Object> pre = new HashMap<>();
                        Map<Variable,Object> post = new HashMap<>();
                        // precondition
                        pre.put(onB,bPrime);
                        pre.put(fixedB,Boolean.FALSE);
                        pre.put(fixedBPrime,Boolean.TRUE);
                        pre.put(freeP,Boolean.TRUE);
                        // post-condition
                        post.put(onB,-(p+1));
                        post.put(freeP,Boolean.FALSE);
                        post.put(fixedBPrime,Boolean.FALSE);
                        post.put(fixedB,Boolean.FALSE);
                        actions.add(new BasicAction(pre,post,1));
                    }
                }
            }
        }
        return actions;
    }

    /**
     * Crée les actions pour déplacer un bloc b d'une pile p vers un bloc b'.
     * Préconditions : b est sur la pile p, b n'est pas fixé, b' n'est pas fixé, p n'est pas libre.
     * Postconditions : b est sur b', p devient libre, b' devient fixé, b reste non fixé.
     * 
     * @return l'ensemble des actions de type "déplacer bloc de pile vers bloc"
     */
    private Set<Action> createMoveBlockUnderPileOntoBlockAction() {
        Set<Action> actions = new HashSet<>();
        
        for (int b = 0; b < this.getNumBlocks(); b++) {
            for (int p = 0; p < this.getNumPiles(); p++) {
                for (int bPrime = 0; bPrime < this.getNumBlocks(); bPrime++) {
                    if (b != bPrime) {
                        Variable onB = this.getVariable("on_" + b);
                        Variable fixedB = this.getVariable("fixed_" + b);
                        Variable fixedBPrime = this.getVariable("fixed_" + bPrime);
                        Variable freeP = this.getVariable("free_" + p);
                        
                        Map<Variable, Object> pre = new HashMap<>();
                        Map<Variable, Object> post = new HashMap<>();
                        
                        pre.put(onB, -(p + 1));
                        pre.put(fixedB, Boolean.FALSE);
                        pre.put(fixedBPrime, Boolean.FALSE);
                        pre.put(freeP, Boolean.FALSE);
                        
                        post.put(onB, bPrime);
                        post.put(freeP, Boolean.TRUE);
                        post.put(fixedBPrime, Boolean.TRUE);
                        post.put(fixedB, Boolean.FALSE);
                        
                        actions.add(new BasicAction(pre, post,1));
                    }
                }
            }
        }
        return actions;
    }

    /**
     * Crée les actions pour déplacer un bloc b d'une pile p vers une autre pile vide p'.
     * Préconditions : b est sur la pile p, b n'est pas fixé, p n'est pas libre, p' est libre.
     * Postconditions : b est sur la pile p', p devient libre, b reste non fixé, p' n'est plus libre.
     * 
     * @return l'ensemble des actions de type "déplacer bloc de pile vers pile vide"
     */
    private Set<Action> createMoveBlockUnderPileToEmptyPileAction() {
        Set<Action> actions = new HashSet<>();
        for (int b = 0; b < this.getNumBlocks(); b++) {
            for (int p = 0; p < this.getNumPiles(); p++) {
                for(int pPrime = 0; pPrime < this.getNumPiles(); pPrime++){
                    if(p != pPrime){
                        Variable onB = this.getVariable("on_" + b);
                        Variable fixedB = this.getVariable("fixed_" + b);
                        Variable freeP = this.getVariable("free_" + p);
                        Variable freePrime = this.getVariable("free_" + pPrime);
                        Map<Variable,Object> pre = new HashMap<>();
                        Map<Variable,Object> post = new HashMap<>();
                        // precondition
                        pre.put(onB,-(p+1));
                        pre.put(fixedB,Boolean.FALSE);
                        pre.put(freeP,Boolean.FALSE);
                        pre.put(freePrime,Boolean.TRUE);
                        // post-condition
                        post.put(onB,-(pPrime+1));
                        post.put(freeP,Boolean.TRUE);
                        post.put(fixedB,Boolean.FALSE);
                        post.put(freePrime,Boolean.FALSE);
                        actions.add(new BasicAction(pre,post,1));
                    }
                }
            }
        }
        return actions;
    }

    /**
     * Retourne l'ensemble de toutes les actions disponibles.
     * 
     * @return l'ensemble des actions
     */
    public Set<Action> getActions() {
        return this.actions;
    }
}