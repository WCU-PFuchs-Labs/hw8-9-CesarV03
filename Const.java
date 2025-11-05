public class Const {
    private double value;

    public Const(double value) 
    {
        this.value = value;
    }

    public double eval(double[] data) 
    {
        return value;
    }

    public String toString() 
    {
        return String.format("%.2f", value);
    }
}
