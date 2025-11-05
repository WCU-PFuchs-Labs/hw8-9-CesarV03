public class Variable {
    private int index;

    public Variable(int index) 
    {
        this.index = index;
    }

    public double eval(double[] data) 
    {
        return data[index];
    }

    public String toString() 
    {
        return "x" + index;
    }
}
