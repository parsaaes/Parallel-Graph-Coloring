package ir.ac.aut.ceit.alg.graph;

import ir.ac.aut.ceit.alg.io.FileUtils;

import java.util.ArrayList;
import java.util.HashSet;

public class Coloring {
    private int threadCount = 2;
    private ArrayList<Thread> threadArray = new ArrayList<>();

    /**
     * do a coloring
     * @param path graph path
     */
    public void doColoring(String path){
        Graph graph = new Graph(path);
        //System.out.println("is colored? " + ColoringValidator.validate(graph));
        //graph.printAdjList();
        int eachThreadVertex = graph.getVertexNumber() / threadCount;

        // do FF in parallel
        doFFInParallel(graph, eachThreadVertex);

        System.out.println("coloring: ===============");
        graph.printColoring();

        // find errors in parallel
        HashSet<Integer> allVerticesErrors = findErrorsInParallel(graph, eachThreadVertex);

        System.out.println("errors: ===============");
        System.out.println("Vertices with invalid coloring: " + allVerticesErrors);

        // fix errors sequential
        for (Integer invalidVertex : allVerticesErrors) {
            ArrayList<Integer> impossibleList = buildImpossibleList(graph, graph.getAdjList()[invalidVertex]);
            graph.doColoring(invalidVertex, getLowestAvailableColor(graph, impossibleList));
            System.out.println("new color of " + invalidVertex + " is " + graph.getColors()[invalidVertex]);
        }

        System.out.println("final coloring: ===============");
        graph.printColoring();

        saveColoring(graph, path.substring(0,path.lastIndexOf(".")) + "-colored.txt");
        //System.out.println("is colored? " + ColoringValidator.validate(graph));

        // improve color naming [optional]
//        int[] colors = graph.getColors();
//        int lastColor = colors[0];
//        int lastNewColor = 0;
//        for (int i = 0; i < colors.length; i++) {
//            if(lastColor != colors[i]) {
//                lastColor = colors[i];
//                lastNewColor++;
//            }
//                colors[i] = lastNewColor;
//        }
//
//        System.out.println("final coloring[+]: ===============");
//        graph.printColoring();
    }

    /**
     * make a list of impossible colors
     * @param graph
     * @param firstNeigh
     * @return
     */
    private ArrayList<Integer> buildImpossibleList(Graph graph, ListNode firstNeigh) {
        ArrayList<Integer> imPossibleList = new ArrayList<>();
        ListNode neigh = firstNeigh;
        while (neigh != null){
            imPossibleList.add(graph.getColors()[neigh.getData()]);
            neigh = neigh.getNext();
        }
        return imPossibleList;
    }

    /**
     * get the color with least index
     * @param graph given graph
     * @param imPossibleList impossible list
     * @return least available color
     */
    protected int getLowestAvailableColor(Graph graph, ArrayList<Integer> imPossibleList) {
        int color = -2;
        for (int j = 0; j < graph.getVertexNumber(); j++) {
            if(!imPossibleList.contains(j)){
                color = j;
                break;
            }
        }
        return color;
    }

    /**
     * find coloring errors in parallel
     * @param graph given graph
     * @param eachThreadVertex number of vertices for each thread to search in
     * @return errors list
     */
    private HashSet<Integer> findErrorsInParallel(Graph graph, int eachThreadVertex) {
        threadArray = new ArrayList<>();
        ArrayList<ParallelFindColorError> errorClassArray = new ArrayList<>();
        HashSet<Integer> allVerticesErrors = new HashSet<>();

        int i = 0;
        int vertexNumbers = graph.getVertexNumber();
        while (i < vertexNumbers){
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
        for (ParallelFindColorError parallelFindColorError : errorClassArray) {
            allVerticesErrors.addAll(parallelFindColorError.getErrorVertices());
        }
        return allVerticesErrors;
    }

    /**
     * do Parallel FF
     * @param graph given graph
     * @param eachThreadVertex number of vertices for each thread to search in
     */
    private void doFFInParallel(Graph graph, int eachThreadVertex) {
        threadArray = new ArrayList<>();
        int i = 0;
        int vertexNumbers = graph.getVertexNumber();
        while (i < vertexNumbers){
            int end = i + eachThreadVertex;
            if(end > graph.getVertexNumber()){
                end = graph.getVertexNumber();
            }
            // System.out.println(i + " to " + end);
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

    }

    /**
     * save coloring to file
     * @param graph given colored graph
     * @param path file address
     */
    public void saveColoring(Graph graph, String path){
        String result = "";
        int[] colorArr = graph.getColors();
        for (int i = 0; i < colorArr.length; i++) {
            result += i + " " + colorArr[i] + "\n";
        }
        FileUtils.write(result, path);
    }
}
