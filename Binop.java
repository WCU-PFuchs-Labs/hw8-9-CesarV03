public abstract class Binop {
    public abstract double eval(double left, double right);
    public abstract String getOp();
    public String toString() 
    {
        return getOp();
    }
}
