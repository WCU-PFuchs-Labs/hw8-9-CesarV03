import java.util.Random;
public class NodeFactory {

    private int numIndep;
    private int numOps;

    public NodeFactory() 
    {
        this.numIndep = 3; 
        this.numOps = 4;
    }

    public int numIndep() 
    {
        return numIndep;
    }

    public int numOps() 
    {
        return numOps;
    }

    public Binop getRandomOp(Random rand) 
    {
        int choice = rand.nextInt(numOps);
        switch (choice) {
            case 0: return new Plus();
            case 1: return new Minus();
            case 2: return new Mult();
            default: return new Divide();
        }
    }

    public Node getRandomTerminal(Random rand) 
    {
        boolean chooseConst = rand.nextBoolean();
        if (chooseConst) {
            double value = rand.nextDouble() * 10 - 5;
            return new Node(new Const(value));
        } else {
            int index = rand.nextInt(numIndep);
            return new Node(new Variable(index));
        }
    }

    public Node getRandomNode(Random rand, int maxDepth) 
    {
        if (maxDepth <= 1) {
            return getRandomTerminal(rand);
        } else {
            Binop op = getRandomOp(rand);
            Node left = getRandomNode(rand, maxDepth - 1);
            Node right = getRandomNode(rand, maxDepth - 1);
            return new Node(op, left, right);
        }
    }
}
