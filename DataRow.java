public class DataRow {
    private double y;
    private double[] x;

    public DataRow(double y, double[] x) {
        this.y = y;
        this.x = x;
    }

    public double getDependentVariable() {
        return y;
    }

    public double[] getIndependentVariables() {
        return x;
    }
}
