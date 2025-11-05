import java.util.*;
import java.io.*;

public class GPTree implements Collector, Comparable<GPTree>, Cloneable {

    private Node root;
    private ArrayList<Node> crossNodes;
    private double fitness;

    public GPTree(NodeFactory factory, int maxDepth, Random rand) 
    {
        root = factory.getOperator(rand);
        root.addRandomKids(factory, maxDepth, rand);
    }

    public GPTree(Node root) 
    {
        this.root = root;
    }

    public void collect(Node node) 
    {
        if (!node.isLeaf()) {
            crossNodes.add(node);
        }
    }

    public void traverse() 
    {
        crossNodes = new ArrayList<Node>();
        root.traverse(this);
    }

    public String getCrossNodes() 
    {
        StringBuilder sb = new StringBuilder();
        int last = crossNodes.size() - 1;
        for (int i = 0; i < last; i++) {
            sb.append(crossNodes.get(i).toString()).append(";");
        }
        if (last >= 0) sb.append(crossNodes.get(last).toString());
        return sb.toString();
    }

    public void crossover(GPTree tree, Random rand) 
    {
        this.traverse();
        tree.traverse();
        if (this.crossNodes.isEmpty() || tree.crossNodes.isEmpty()) return;
        int thisPoint = rand.nextInt(this.crossNodes.size());
        int treePoint = rand.nextInt(tree.crossNodes.size());
        boolean left = rand.nextBoolean();

        Node thisTrunk = crossNodes.get(thisPoint);
        Node treeTrunk = tree.crossNodes.get(treePoint);

        if (left) {
            thisTrunk.swapLeft(treeTrunk);
        } else {
            thisTrunk.swapRight(treeTrunk);
        }
    }

    public void evalFitness(DataSet dataSet) 
    {
        double total = 0.0;
        for (DataRow row : dataSet.getRows()) {
            double[] x = row.getIndependentVariables();
            double y = row.getDependentVariable();
            double pred = root.eval(x);
            double diff = pred - y;
            total += diff * diff;
        }
        this.fitness = total;
    }

    public double getFitness() { return fitness; }

    @Override
    public int compareTo(GPTree t) 
    {
        return Double.compare(this.fitness, t.fitness);
    }

    @Override
    public boolean equals(Object o) 
    {
        if (o == null || !(o instanceof GPTree)) return false;
        return this.compareTo((GPTree) o) == 0;
    }

    @Override
    public Object clone() 
    {
        try {
            GPTree g = (GPTree) super.clone();
            if (this.root != null) g.root = (Node) this.root.clone();
            return g;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public double eval(double[] data) 
    {
        return root.eval(data);
    }

    public String toString() 
    {
        return root.toString();
    }
}
