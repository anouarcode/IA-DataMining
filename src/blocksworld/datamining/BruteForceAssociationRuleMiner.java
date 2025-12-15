package blocksworld.datamining;
import java.util.*;
import blocksworld.modelling.*;

public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner {

    public BruteForceAssociationRuleMiner(BooleanDatabase database){
        super(database);
    }


    public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> items) {
        Set<Set<BooleanVariable>> result = new HashSet<>();
        List<BooleanVariable> list = new ArrayList<>(items);
        
        
        result.add(new HashSet<>());
        
       
        for (BooleanVariable item : list) {
            Set<Set<BooleanVariable>> newSubsets = new HashSet<>();
            
            for (Set<BooleanVariable> subset : result) {
                Set<BooleanVariable> newSubset = new HashSet<>(subset);
                newSubset.add(item);
                newSubsets.add(newSubset);
            }
            
            result.addAll(newSubsets);
        }
        
        result.remove(new HashSet<>());
        result.remove(new HashSet<>(items));
        
        return result;
    }

    @Override
    public Set<AssociationRule> extract(float frMin, float confMin) {
        Set<AssociationRule> rules = new HashSet<>();
        Apriori apriori = new Apriori(database);
        
        Set<Itemset> frequentItemset = apriori.extract(frMin);
        System.out.println("Nombre de motifs :" +  frequentItemset.size());
        System.out.println("================== DEBUT EXTRACTION DES REGLES VEUILLEZ PATIENTER=====================");
        System.out.println("EXTRACTION EN COURS...");
        for (Itemset itemset : frequentItemset) {
            Set<BooleanVariable> items = itemset.getItems();
            Set<Set<BooleanVariable>> allPremises = allCandidatePremises(items);
            for (Set<BooleanVariable> premisse : allPremises) {
                Set<BooleanVariable> conclusion = new HashSet<>(items);
                conclusion.removeAll(premisse);
                if (!premisse.isEmpty() && !conclusion.isEmpty()) {
                    float conf = confidence(premisse, conclusion, frequentItemset);
                    float freq = frequency(items, frequentItemset);
                    if (conf >= confMin) {
                        rules.add(new AssociationRule(premisse, conclusion, freq, conf));
                    }
                }
            }
        }
        System.out.println("==================FIN EXTRACTION DES REGLES=====================");
        System.out.println("Nombre de règles trouvées :" +  rules.size());
        System.out.println("Règles : " + rules.toString());
        return rules;
    }
    
    


    
}
