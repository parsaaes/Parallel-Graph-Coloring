package ir.ac.aut.ceit.alg;

import ir.ac.aut.ceit.alg.graph.Coloring;

public class Main {


    public static void main(String[] args) {
        Long time1 = System.currentTimeMillis();
        Coloring coloring = new Coloring();
        coloring.doColoring(System.getProperty("user.dir") + "/test1.txt");
        Long time2 = System.currentTimeMillis();

        System.out.println("took: " + (time2-time1));
    }
}
