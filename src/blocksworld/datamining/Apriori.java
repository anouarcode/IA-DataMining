package blocksworld.datamining;
import blocksworld.modelling.*;

import java.util.*;


public class Apriori extends AbstractItemsetMiner implements ItemsetMiner {
    // cache
    private Map<Itemset, Float> frequencyCache = new HashMap<>();
    
    public Apriori(BooleanDatabase transactions){
        super(transactions);
    }

    // Utilisation optimisée du cache
    private float getFrequency(Set<BooleanVariable> items) {
        // Chercher dans le cache avec la clé
        for (Map.Entry<Itemset, Float> entry : frequencyCache.entrySet()) {
            if (entry.getKey().getItems().equals(items)) {
                return entry.getValue();
            }
        }

        // Calculer et mémoriser
        float freq = super.frequency(items);
        frequencyCache.put(new Itemset(items, freq), freq);
        return freq;
    }

    public Set<Itemset> frequentSingletons(float frequence){
        Set<Itemset> set = new HashSet<>();

        for(BooleanVariable item : transactions.getItems()){
            Set<BooleanVariable> s = new HashSet<>();
            s.add(item);
            float freq = getFrequency(s);
            if(freq >= frequence){
                set.add(new Itemset(s, freq));
            }
        }
        return set;
    }

    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> set1, SortedSet<BooleanVariable> set2) {
        if (set1.size() != set2.size() || set1.isEmpty() || set2.isEmpty()) {
            return null;
        }

        Iterator<BooleanVariable> it1 = set1.iterator();
        Iterator<BooleanVariable> it2 = set2.iterator();

        int k = set1.size();

        // Vérifier les k-1 premiers items
        for (int i = 0; i < k - 1; i++) {
            BooleanVariable v1 = it1.next();
            BooleanVariable v2 = it2.next();
            if (!v1.equals(v2)) {
                return null; // les premiers k-1 items ne sont pas identiques
            }
        }

        // Vérifier le dernier item
        BooleanVariable last1 = it1.next();
        BooleanVariable last2 = it2.next();
        if (last1.equals(last2)) {
            return null; // le dernier item doit être différent
        }

        SortedSet<BooleanVariable> result = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
        result.addAll(set1);
        result.add(last2); // ajouter le dernier élément différent
        return result;
    }

   
    public static boolean allSubsetsFrequent(Set<BooleanVariable> items, Collection<SortedSet<BooleanVariable>> itemSet){
        Set<Set<BooleanVariable>> fastLookup = new HashSet<>();
        for(SortedSet<BooleanVariable> s : itemSet) {
            fastLookup.add(new HashSet<>(s));
        }
        
        for(BooleanVariable item1 : items){
            Set<BooleanVariable> subSetItem = new HashSet<>();
            for(BooleanVariable item2 : items){
                if(!item1.equals(item2)){
                    subSetItem.add(item2);
                }
            }

            if(!fastLookup.contains(subSetItem)){
                return false;
            }
        }
        return true;
    }
    

    @Override
    public Set<Itemset> extract(float frMin) {
        System.out.println("==================DEBUT EXTRACTION DES MOTIFS VEUILLEZ PATIENTER !!!=====================");
        
        // Effacer le cache au début
        frequencyCache.clear();
        
        Set<Itemset> res = new HashSet<>();
        res.addAll(this.frequentSingletons(frMin));
        
        List<SortedSet<BooleanVariable>> itemsFrequent = new ArrayList<>();
        for(Itemset item : res) {
            SortedSet<BooleanVariable> tmp = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            tmp.addAll(item.getItems());
            itemsFrequent.add(tmp);
        }
        
        System.out.println("EXTRACTION EN COURS...");
        int iteration = 1;
        while(!itemsFrequent.isEmpty()) {
            System.out.println("Itération " + iteration + " - Candidats: " + itemsFrequent.size());
            List<SortedSet<BooleanVariable>> tmp = new ArrayList<>();
            tmp.addAll(itemsFrequent);
            itemsFrequent.clear();
            
            for(int i = 0; i < tmp.size(); i++) {
                for(int j = i + 1; j < tmp.size(); j++) {
                    SortedSet<BooleanVariable> combinaison = this.combine(tmp.get(i), tmp.get(j));
                    if(combinaison != null) {
                        if(allSubsetsFrequent(combinaison, tmp)) {
                            float freq = getFrequency(combinaison);
                            if(freq >= frMin) {
                                Itemset frequent = new Itemset(combinaison, freq);
                                res.add(frequent);
                                itemsFrequent.add(combinaison);
                            }
                        }
                    }
                }
            }
            iteration++;
        }
        
        System.out.println("==================FIN EXTRACTION DES MOTIFS=====================");
        System.out.println("motifs : " + res);
        System.out.println("Nombre de motifs trouvés: " + res.size());
        return res;
    }
}