import java.util.*;
import java.io.*;

public class GPTree implements Collector, Comparable<GPTree>, Cloneable {

    private Node root;
    private ArrayList<Node> crossNodes;
    private double fitness;

    public GPTree() 
    {
        root = null;
    }

    public GPTree(NodeFactory factory, int maxDepth, Random rand) 
    {
        root = factory.getOperator(rand);
        root.addRandomKids(factory, maxDepth, rand);
    }

    @Override
    public void collect(Node node) 
    {
        if (!node.isLeaf()) {
            crossNodes.add(node);
        }
    }

    public void traverse() 
    {
        crossNodes = new ArrayList<>();
        root.traverse(this);
    }

    public String getCrossNodes() 
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < crossNodes.size(); i++) {
            sb.append(crossNodes.get(i).toString());
            if (i < crossNodes.size() - 1) sb.append(";");
        }
        return sb.toString();
    }

    public void crossover(GPTree other, Random rand) 
    {
        this.traverse();
        other.traverse();
        if (this.crossNodes.isEmpty() || other.crossNodes.isEmpty()) return;

        int thisPoint = rand.nextInt(this.crossNodes.size());
        int otherPoint = rand.nextInt(other.crossNodes.size());
        boolean left = rand.nextBoolean();

        Node thisTrunk = crossNodes.get(thisPoint);
        Node otherTrunk = other.crossNodes.get(otherPoint);

        if (left) {
            thisTrunk.swapLeft(otherTrunk);
        } else {
            thisTrunk.swapRight(otherTrunk);
        }
    }

    public void evalFitness(DataSet dataSet) {
        double totalError = 0.0;
        for (DataRow row : dataSet.getRows()) {
            double predicted = this.eval(row.getIndependentVariables());
            double actual = row.getDependentVariable();
            double diff = predicted - actual;
            totalError += diff * diff;
        }
        fitness = totalError;
    }

    public double getFitness() {
        return fitness;
    }

    @Override
    public int compareTo(GPTree other) {
        if (this.fitness < other.fitness) return -1;
        if (this.fitness > other.fitness) return 1;
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof GPTree)) return false;
        GPTree other = (GPTree) o;
        return this.compareTo(other) == 0;
    }

    @Override
    public Object clone() {
        try {
            GPTree copy = (GPTree) super.clone();
            if (this.root != null) {
                copy.root = (Node) this.root.clone();
            }
            return copy;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public double eval(double[] data) {
        return root.eval(data);
    }

    @Override
    public String toString() {
        return root.toString();
    }
}
