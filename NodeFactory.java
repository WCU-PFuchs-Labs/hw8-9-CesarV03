import java.util.Random;
// Used help with clone here https://www.geeksforgeeks.org/java/clone-method-in-java-2/
public class NodeFactory {
    private int numIndepVars;
    private Binop[] currentOps;

    public NodeFactory(Binop[] b, int numVars) 
    {
        currentOps = b;
        numIndepVars = numVars;
    }

    public Node getOperator(Random rand) 
    {
        int r = rand.nextInt(currentOps.length);
        Binop op = (Binop) currentOps[r].clone();
        Node node = new Node(op);
        return node;
    }

    public int getNumOps() 
    {
        return currentOps.length;
    }

    public Node getTerminal(Random rand) 
    {
        int r = rand.nextInt(numIndepVars + 1);
        if (r < numIndepVars) {
            Variable v = new Variable(r);
            Node n = new Node(v);
            return n;
        } else {
            double val = rand.nextDouble();
            Const c = new Const(val);
            Node n = new Node(c);
            return n;
        }
    }

    public int getNumIndepVars() 
    {
        return numIndepVars;
    }
}
