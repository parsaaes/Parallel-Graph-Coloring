package ir.ac.aut.ceit.alg;

import ir.ac.aut.ceit.alg.graph.Coloring;
import ir.ac.aut.ceit.alg.graph.Graph;
import ir.ac.aut.ceit.alg.graph.ParallelFirstFit;

public class Main {


    public static void main(String[] args) {
        Coloring coloring = new Coloring();
        coloring.doColoring("test.txt");
    }
}
