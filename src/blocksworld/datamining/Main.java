package blocksworld.datamining;
import blocksworld.modelling.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        // L'extraction de motif se fait déja dans le extract de la classe BruteForceAssociationRuleMiner, pas besoin de l'appeler ici
        BlocksWorldMiner bwMiner = new BlocksWorldMiner(5,5); //
        BooleanDatabase database = bwMiner.createDatabase(10000); // 10000 instance
        BruteForceAssociationRuleMiner brute = new BruteForceAssociationRuleMiner(database);
        float minFr = 2/3f;
        float minConf = 95/100f;
        Set<AssociationRule> rules = brute.extract(minFr,minConf);
        
    }
}