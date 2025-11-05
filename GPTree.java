import java.util.*;
import java.io.*;

public class GPTree implements Comparable<GPTree>, Cloneable {

    private Node root;
    private double fitness;

  public GPTree(Node root) 
    {
        this.root = root;
    }

    public GPTree(GPTree other) 
    {
        this.root = (Node) other.root.clone();
        this.fitness = other.fitness;
    }

    public void evalFitness(DataSet dataSet) 
{
        double sum = 0.0;
        for (DataRow row : dataSet.getRows()) {
            double y = row.getDependentVariable();
            double[] x = row.getIndependentVariables();
            double prediction = root.eval(x);
            sum += Math.pow((prediction - y), 2);
        }
        this.fitness = sum;
    }

    public double getFitness() 
    {
        return fitness;
    }

    @Override
    public int compareTo(GPTree t) 
    {
        if (this.fitness < t.fitness) return -1;
        else if (this.fitness > t.fitness) return 1;
        else return 0;
    }

    @Override
    public boolean equals(Object o) 
    {
        if (o == null || !(o instanceof GPTree)) return false;
        GPTree other = (GPTree) o;
        return this.compareTo(other) == 0;
    }

    @Override
    public Object clone() 
    {
        try {
            GPTree copy = (GPTree) super.clone();
            copy.root = (Node) this.root.clone();
            return copy;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public Node getRoot() 
    {
        return root;
    }

    public String toString() 
    {
        return root.toString();
    }
}
