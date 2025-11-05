import java.util.*;
import java.io.*;

public class Generation {
    private GPTree[] trees;
    private DataSet dataSet;
    private NodeFactory factory;
    private int maxDepth;
    private Random rand;

    public Generation(int size, int maxDepth, String fileName) 
    {
        this.maxDepth = maxDepth;
        this.rand = new Random();
        this.dataSet = new DataSet(fileName);
        Binop[] ops = { new Plus(), new Minus(), new Mult(), new Divide() };
        this.factory = new NodeFactory(ops, dataSet.getNumIndependantVariables());
        this.trees = new GPTree[size];

        for (int i = 0; i < size; i++) {
            trees[i] = new GPTree(factory, maxDepth, rand);
        }
    }

    public void evalAll() 
    {
        for (GPTree tree : trees) {
            tree.evalFitness(dataSet);
        }
        Arrays.sort(trees); 
    }

    public ArrayList<GPTree> getTopTen() 
    {
        ArrayList<GPTree> topTen = new ArrayList<>();
        int limit = Math.min(10, trees.length);
        for (int i = 0; i < limit; i++) {
            topTen.add(trees[i]);
        }
        return topTen;
    }

    public void printBestFitness() 
    {
        if (trees.length > 0) {
            System.out.printf("Best Fitness: %.2f\n", trees[0].getFitness());
        }
    }

    public void printBestTree() 
    {
        if (trees.length > 0) {
            System.out.println("Best Tree: " + trees[0]);
        }
    }

    public void evolve() 
    {
        GPTree[] newGeneration = new GPTree[trees.length];
        for (int i = 0; i < trees.length; i += 2) {
            int parent1Index = rand.nextInt(trees.length / 2);
            int parent2Index = rand.nextInt(trees.length / 2);
            GPTree parent1 = (GPTree) trees[parent1Index].clone();
            GPTree parent2 = (GPTree) trees[parent2Index].clone();
            parent1.crossover(parent2, rand);
            newGeneration[i] = parent1;
            if (i + 1 < trees.length) {
                newGeneration[i + 1] = parent2;
            }
        }
        this.trees = newGeneration;
    }
}
