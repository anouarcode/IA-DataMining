package blocksworld.modelling;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;

/**
 * Contrainte d'implication entre une variable on_b et une variable free_p.
 * Si on_b = -(p+1) (le bloc b est sur la pile p), alors free_p doit être false.
 * Exprime qu'une pile occupée par un bloc n'est pas libre.
 */
public class ImplicationOntoFree implements Constraint{
    Variable onVar; // onb
    Variable freeVar; // freep
    int pileValue;
    
    /**
     * Construit une contrainte d'implication vers free.
     * 
     * @param onVar la variable on_b (position du bloc b)
     * @param freeVar la variable free_p (disponibilité de la pile p)
     * @param pileValue l'indice p de la pile (la valeur dans on_b sera -(p+1))
     */
    public ImplicationOntoFree(Variable onVar,Variable freeVar,int pileValue){
        this.onVar = onVar;
        this.freeVar = freeVar;
        this.pileValue = pileValue;
    }
    
    /**
     * Retourne l'ensemble des variables impliquées dans cette contrainte.
     * 
     * @return l'ensemble contenant on_b et free_p
     */
    @Override
    public Set<Variable> getScope(){
        Set<Variable> set = new HashSet<>();
        set.add(onVar);
        set.add(freeVar);
        return set;
    }
    
    /**
     * Vérifie si l'instance donnée satisfait la contrainte d'implication.
     * Si on_b = -(p+1), alors free_p doit être false.
     * 
     * @param instance l'affectation des variables à vérifier
     * @return true si la contrainte est satisfaite, false sinon
     * @throws IllegalArgumentException si une variable du scope n'est pas instanciée
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable,Object> instance){
        for(Variable v : this.getScope()){
            if(!(instance.containsKey(v)) || (instance.get(v) == null)){
                throw new IllegalArgumentException("Instanciation Invalide");
            }
        };                   
        
        Object onVarValue = instance.get(onVar);
        Object freeVarValue = instance.get(freeVar);
        
        if(!onVarValue.equals(-(pileValue + 1))){
            return true;
        }
        
        return freeVarValue.equals(Boolean.FALSE);
    }
    
    @Override
    public String toString() {
        return "Contrainte d'implication On to Free " + onVar.getName() + " = " + (-(pileValue + 1)) + " alors " + freeVar.getName() + " = false";
    }
}