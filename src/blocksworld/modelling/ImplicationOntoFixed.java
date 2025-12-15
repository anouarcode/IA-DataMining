package blocksworld.modelling;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;

/**
 * Contrainte d'implication entre une variable on_b et une variable fixed_b'.
 * Si on_b = b', alors fixed_b' doit être true.
 * Exprime qu'un bloc avec un autre bloc dessus est fixé.
 */
public class ImplicationOntoFixed implements Constraint{
    Variable onVar; // onb
    Variable fixedVar; // fixedb'
    Object conditionValue; // b'
    
    /**
     * Construit une contrainte d'implication vers fixed.
     * 
     * @param onVar la variable on_b (position du bloc b)
     * @param fixedVar la variable fixed_b' (état fixé du bloc b')
     * @param conditionValue la valeur condition b' (si on_b = b')
     */
    public ImplicationOntoFixed(Variable onVar,Variable fixedVar,Object conditionValue){
        this.onVar = onVar;
        this.fixedVar = fixedVar;
        this.conditionValue = conditionValue;
    }
    
    /**
     * Retourne l'ensemble des variables impliquées dans cette contrainte.
     * 
     * @return l'ensemble contenant on_b et fixed_b'
     */
    @Override
    public Set<Variable> getScope(){
        Set<Variable> set = new HashSet<>();
        set.add(onVar);
        set.add(fixedVar);
        return set;
    }
    
    /**
     * Vérifie si l'instance donnée satisfait la contrainte d'implication.
     * Si on_b = b', alors fixed_b' doit être true.
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
        
        Object fixedValue = instance.get(fixedVar);                  
        Object onVarValue = instance.get(onVar);
        
        if(!onVarValue.equals(conditionValue)){
            return true;
        }
        
        return fixedValue.equals(Boolean.TRUE);
    }
    
    @Override
    public String toString() {
        return "Contrainte d'implication On to Fixed " + onVar.getName() + " = " + conditionValue + " alors normalement " + fixedVar.getName() + " = true";
    }
}