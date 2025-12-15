package blocksworld.modelling;

import java.util.*;

public class Main {
    private static final Set<Object> ON_DOMAIN = Set.of(-2, -1, 0, 1, 2, 3);
    private static final Set<Object> BOOLEAN_DOMAIN = Set.of(true, false);
    
    public static void main(String[] args) {
        System.out.println("=== EXERCICE 5 - DÉMONSTRATION ===\n");
        
        // Création du monde
        BwWithConstraints world = new BwWithConstraints(3, 1);
        RegularConfigurationConstraints regular = new RegularConfigurationConstraints(world);
        GrowingConfigurationConstraint growing = new GrowingConfigurationConstraint(world);
        System.out.println("Monde créé : " + world);
        
        // Test de quelques configurations
        testConfig("Config valide", createValidConfig(), world,regular,growing);
        testConfig("Config invalide", createInvalidConfig(), world,regular,growing);
    }
    
    private static void testConfig(String nom, Map<Variable, Object> config, BwWithConstraints world,RegularConfigurationConstraints regular,GrowingConfigurationConstraint growing) {
        System.out.println("\n--- " + nom + " ---");
        System.out.println("Configuration : " + config);
        
        boolean toutesOk = true;
        for (Constraint c : world.getAllConstraints()) {
            if (!c.isSatisfiedBy(config)) {
                System.out.println("X " + c);
                toutesOk = false;
            }
        }
        for (Constraint c :  regular.getRegularityConstraints()) {
            if (!c.isSatisfiedBy(config)) {
                System.out.println("X " + c);
                toutesOk = false;
            }
        }

        for (Constraint c :  growing.getGrowingConstraint()) {
            if (!c.isSatisfiedBy(config)) {
                System.out.println("X " + c);
                toutesOk = false;
            }
        }
        
        if (toutesOk) {
            System.out.println("All test ok :  Toutes les contraintes sont satisfaites");
        }
    }
    
    // Configuration valide
    private static Map<Variable, Object> createValidConfig() {
        Map<Variable, Object> config = new HashMap<>();
        
        // Variables "on" avec leur domaine
        config.put(new Variable("on_0", ON_DOMAIN), -1);
        config.put(new Variable("on_1", ON_DOMAIN), 0);
        config.put(new Variable("on_2", ON_DOMAIN), 1);
        //config.put(new Variable("on_3", ON_DOMAIN), 2);
        
        // Variables "fixed" avec domaine booléen
        config.put(new Variable("fixed_0", BOOLEAN_DOMAIN), true);
        config.put(new Variable("fixed_1", BOOLEAN_DOMAIN), true); // car Bloc 2 est sur Bloc 1
        config.put(new Variable("fixed_2", BOOLEAN_DOMAIN), false);
        //config.put(new Variable("fixed_3", BOOLEAN_DOMAIN), false);
        
        // Variables "free" avec domaine booléen
        config.put(new Variable("free_0", BOOLEAN_DOMAIN), false);
        //config.put(new Variable("free_1", BOOLEAN_DOMAIN), false);
        
        return config;
    }
    
    // Configuration invalide (deux blocs sur le même)
    private static Map<Variable, Object> createInvalidConfig() {
        Map<Variable, Object> config = new HashMap<>();
        
        // Variables "on" avec leur domaine
        config.put(new Variable("on_0", ON_DOMAIN), 2);
        config.put(new Variable("on_1", ON_DOMAIN), -1);
        config.put(new Variable("on_2", ON_DOMAIN), 0); // Deux blocs sur le même!
        // config.put(new Variable("on_3", ON_DOMAIN), -2); 
        
        // Variables "fixed" avec domaine booléen
        config.put(new Variable("fixed_0", BOOLEAN_DOMAIN), true);
        config.put(new Variable("fixed_1", BOOLEAN_DOMAIN), false);
        config.put(new Variable("fixed_2", BOOLEAN_DOMAIN), true);
        // config.put(new Variable("fixed_3", BOOLEAN_DOMAIN), true);
        
        // Variables "free" avec domaine booléen
        config.put(new Variable("free_0", BOOLEAN_DOMAIN), false);
        // config.put(new Variable("free_1", BOOLEAN_DOMAIN), false);
        
        return config;
    }

}