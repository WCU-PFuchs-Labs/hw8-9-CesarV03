import java.util.*;

public class TestGP {
    public static void main(String[] args) {
        double[] data = {3.14, 2.78, 1.0};
        Binop[] ops = { new Plus(), new Minus(), new Mult(), new Divide() };
        int numIndepVars = 3;
        int maxDepth = 5;
        Random rand = new Random();
        NodeFactory n = new NodeFactory(ops, numIndepVars);
        GPTree gpt1 = new GPTree(n, maxDepth, rand);
        System.out.println("Tree 1: " + gpt1 + " = " + gpt1.eval(data));
        GPTree gpt2 = new GPTree(n, maxDepth, rand);
        System.out.println("Tree 2: " + gpt2 + " = " + gpt2.eval(data));

        gpt1.crossover(gpt2, rand);
        System.out.println("\nAfter crossover:");
        System.out.println("Tree 1: " + gpt1 + " = " + gpt1.eval(data));
        System.out.println("Tree 2: " + gpt2 + " = " + gpt2.eval(data));
    }
}
