import java.util.*;
import java.io.*;

public class TestGP {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter data file name: ");
        String fileName = sc.nextLine();
        int generationSize = 500;  
        int maxDepth = 5;          
        int numGenerations = 50; 
        Generation gen = new Generation(generationSize, maxDepth, fileName);

        for (int g = 1; g <= numGenerations; g++) {
            gen.evalAll();
            System.out.println("Generation " + g + ":");
            gen.printBestTree();
            gen.printBestFitness();

            if (g < numGenerations) {
                gen.evolve();
            }

            System.out.println();
        }
    }
}
