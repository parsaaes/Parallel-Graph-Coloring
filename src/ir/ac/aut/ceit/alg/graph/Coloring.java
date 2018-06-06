package ir.ac.aut.ceit.alg.graph;

import java.util.ArrayList;
import java.util.HashSet;

public class Coloring {
    private int threadCount = 2;
    private ArrayList<Thread> threadArray = new ArrayList<>();
    private HashSet<Integer> allVerticesErrors = new HashSet<>();

    public void doColoring(String path){
        Graph graph = new Graph(path);
        //graph.printAdjList();
        int eachThreadVertex = graph.getVertexNumber() / threadCount;

        // do FF in parallel
        int i = 0;
        while (i < graph.getVertexNumber()){
            int end = i + eachThreadVertex;
            if(end > graph.getVertexNumber()){
                end = graph.getVertexNumber();
            }
            System.out.println(i + " to " + end);
            threadArray.add(new Thread(new ParallelFirstFit(graph,i,end)));
            i += eachThreadVertex;
        }
        for (Thread thread : threadArray) {
            thread.start();
        }
        try {
            for (Thread thread : threadArray) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("===============");
        graph.printColoring();

        // find errors in parallel
        threadArray = new ArrayList<>();
        ArrayList<ParallelFindColorError> errorClassArray = new ArrayList<>();

        i = 0;
        while (i < graph.getVertexNumber()){
            int end = i + eachThreadVertex;
            if(end > graph.getVertexNumber()){
                end = graph.getVertexNumber();
            }
            System.out.println(i + " to " + end);
            ParallelFindColorError errorThread = new ParallelFindColorError(graph,i,end);
            errorClassArray.add(errorThread);
            threadArray.add(new Thread(errorThread));
            i += eachThreadVertex;
        }
        for (Thread thread : threadArray) {
            thread.start();
        }
        try {
            for (Thread thread : threadArray) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("===============");
        for (ParallelFindColorError parallelFindColorError : errorClassArray) {
            allVerticesErrors.addAll(parallelFindColorError.getErrorVertices());
        }
        System.out.println("Vertices with invalid coloring: " + allVerticesErrors);
    }
}
