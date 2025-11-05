import java.util.*;
import java.io.*;
import java.util.Arrays;

public class Generation {

    private GPTree[] trees;
    private DataSet dataSet;
    private Random rand;

    public Generation(int size, int maxDepth, String fileName) 
  {
        this.dataSet = new DataSet(fileName);
        this.rand = new Random();
        this.trees = new GPTree[size];

        NodeFactory factory = new NodeFactory();

        for (int i = 0; i < size; i++) {
            Node root = factory.getRandomNode(rand, maxDepth);
            trees[i] = new GPTree(root);
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
        ArrayList<GPTree> top = new ArrayList<>();
        int count = Math.min(10, trees.length);
        for (int i = 0; i < count; i++) {
            top.add(trees[i]);
        }
        return top;
    }

    public void printBestFitness() 
    {
        System.out.printf("Best Fitness: %.2f%n", trees[0].getFitness());
    }

    public void printBestTree() 
    {
        System.out.println("Best Tree: " + trees[0]);
    }

    public void evolve() 
    {
        GPTree[] newGen = new GPTree[trees.length];
        int half = trees.length / 2;

        for (int i = 0; i < half; i++) {
            GPTree parent1 = trees[rand.nextInt(half)];
            GPTree parent2 = trees[rand.nextInt(half)];
            GPTree child1 = (GPTree) parent1.clone();
            GPTree child2 = (GPTree) parent2.clone();
            newGen[i * 2] = child1;
            newGen[i * 2 + 1] = child2;
        }

        this.trees = newGen;
    }
}
