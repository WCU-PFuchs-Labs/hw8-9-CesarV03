import java.util.Random;

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
        Binop chosen = currentOps[r];
        if (chosen instanceof Plus) return new Node(new Plus());
        if (chosen instanceof Minus) return new Node(new Minus());
        if (chosen instanceof Mult) return new Node(new Mult());
        return new Node(new Divide());
    }

    public int getNumOps() 
    {
        return currentOps.length;
    }

    public Node getTerminal(Random rand) 
    {
        int r = rand.nextInt(numIndepVars + 1);
        if (r < numIndepVars) {
            return new Node(new Variable(r));
        } else {
            return new Node(new Const(rand.nextDouble()));
        }
    }

    public int getNumIndepVars() 
    {
        return numIndepVars;
    }

    public Node getRandomNode(Random rand, int maxDepth) 
    {
        if (maxDepth <= 1) {
            return getTerminal(rand);
        } else {
            Binop op = getRandomOp(rand);
            Node left = getRandomNode(rand, maxDepth - 1);
            Node right = getRandomNode(rand, maxDepth - 1);
            return new Node(op, left, right);
        }
    }

    public Binop getRandomOp(Random rand) 
    {
        int idx = rand.nextInt(currentOps.length);
        Binop chosen = currentOps[idx];
        if (chosen instanceof Plus) return new Plus();
        if (chosen instanceof Minus) return new Minus();
        if (chosen instanceof Mult) return new Mult();
        return new Divide();
    }
}
