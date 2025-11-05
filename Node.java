public class Node implements Cloneable {
    private Const constant;
    private Variable variable;
    private Binop op;
    private Node lChild, rChild;

    public Node(Const c) 
    {
        this.constant = c;
    }

    public Node(Variable v) 
    {
        this.variable = v;
    }

    public Node(Binop op, Node left, Node right) 
    {
        this.op = op;
        this.lChild = left;
        this.rChild = right;
    }

    public boolean isLeaf() 
    {
        return op == null;
    }

    public double eval(double[] data) 
    {
        if (constant != null) return constant.eval(data);
        if (variable != null) return variable.eval(data);
        double left = lChild.eval(data);
        double right = rChild.eval(data);
        return op.eval(left, right);
    }

    public String toString() 
    {
        if (constant != null) return constant.toString();
        if (variable != null) return variable.toString();
        return "(" + lChild.toString() + " " + op.getOp() + " " + rChild.toString() + ")";
    }

    public Object clone() 
    {
        try {
            Node copy = (Node) super.clone();
            if (lChild != null) copy.lChild = (Node) lChild.clone();
            if (rChild != null) copy.rChild = (Node) rChild.clone();
            return copy;
        } catch (CloneNotSupportedException e) 
        {
            return null;
        }
    }
}
