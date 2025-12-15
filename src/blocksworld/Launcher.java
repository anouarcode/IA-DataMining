package blocksworld;

import blocksworld.datamining.*;
import blocksworld.planning.*;
import blocksworld.cp.Main.*;
import blocksworld.modelling.*;
import java.util.Scanner;

public class Launcher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== BlocksWorld Launcher ===");
        System.out.println("1. modelling.Main");
        System.out.println("2.planning.Main");
        System.out.println("3. cp.Main");
        System.out.println("4. datamining.Main");
        System.out.print("Choix: ");
        
        int choice = scanner.nextInt();
        
        switch (choice) {
            case 4:
                blocksworld.datamining.Main.main(args);
                break;
            case 2:
                blocksworld.planning.Main.main(args);
                break;
            case 3:
                blocksworld.cp.Main.main(args);
                break;
            case 1:
                blocksworld.modelling.Main.main(args);
                break;
            default:
                System.out.println("Option invalide");
                break;
        }
        
        scanner.close();
    }
}