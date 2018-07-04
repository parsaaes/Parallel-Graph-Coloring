package ir.ac.aut.ceit.alg;

import ir.ac.aut.ceit.alg.graph.Coloring;

public class Main {


    public static void main(String[] args) {
        // instantiate a Coloring class and do coloring
        Coloring coloring = new Coloring();
        coloring.doColoring(System.getProperty("user.dir") + "/test1.txt");
    }
}
