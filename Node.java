import java.util.Random;

public class Node {

    protected Node lChild;
    protected Node rChild;
    protected Binop op;        
    protected Const c;         
    protected Variable v;     

    public Node(Const c) 
    {
        this.c = c;
        this.v = null;
        this.op = null;
        this.lChild = null;
        this.rChild = null;
    }

    public Node(Variable v) 
    {
        this.v = v;
        this.c = null;
        this.op = null;
        this.lChild = null;
        this.rChild = null;
    }

    public Node(Binop op, Node left, Node right) 
    {
        this.op = op;
        this.lChild = left;
        this.rChild = right;
        this.c = null;
        this.v = null;
    }

    public Node(Binop op) 
    {
        this.op = op;
        this.lChild = null;
        this.rChild = null;
        this.c = null;
        this.v = null;
    }


    public double eval(double[] data) 
    {
        if (c != null) return c.eval(data);
        if (v != null) return v.eval(data);
        double leftVal = lChild.eval(data);
        double rightVal = rChild.eval(data);
        return op.eval(leftVal, rightVal);
    }

    public String toString() 
    {
        if (c != null) return c.toString();
        if (v != null) return v.toString();
        return "(" + lChild.toString() + " " + op.toString() + " " + rChild.toString() + ")";
    }


    public void traverse(Collector collector) 
    {
        collector.collect(this);
        if (lChild != null) lChild.traverse(collector);
        if (rChild != null) rChild.traverse(collector);
    }

    public void swapLeft(Node trunk) 
    {
        Node temp = this.lChild;
        this.lChild = trunk.lChild;
        trunk.lChild = temp;
    }
    
    public void swapRight(Node trunk) 
    {
        Node temp = this.rChild;
        this.rChild = trunk.rChild;
        trunk.rChild = temp;
    }

    public boolean isLeaf() 
    {
        return (op == null);
    }

    public void addRandomKids(NodeFactory nf, int maxDepth, Random rand) 
    {
        if (isLeaf() || maxDepth <= 0) return;
        int numOps = nf.getNumOps();
        int numVars = nf.getNumIndepVars();
        int choices = numOps + numVars + 1;

        if (lChild == null) {
            if (maxDepth == 1) 
            {
                lChild = nf.getTerminal(rand);
            } else {
                int choice = rand.nextInt(choices);
                if (choice < numOps) {
                    lChild = nf.getOperator(rand);
                    lChild.addRandomKids(nf, maxDepth - 1, rand);
                } else {
                    lChild = nf.getTerminal(rand);
                }
            }
        }

        if (rChild == null) {
            if (maxDepth == 1) {
                rChild = nf.getTerminal(rand);
            } else {
                int choice = rand.nextInt(choices);
                if (choice < numOps) {
                    rChild = nf.getOperator(rand);
                    rChild.addRandomKids(nf, maxDepth - 1, rand);
                } else {
                    rChild = nf.getTerminal(rand);
                }
            }
        }
    }
}
