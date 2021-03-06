package ir.ac.aut.ceit.alg.graph;

import java.util.ArrayList;

public class ParallelFindColorError extends ParallelGraph implements Runnable {
    private ArrayList<Integer> errorVertices = new ArrayList<>();

    public ParallelFindColorError(Graph graph, int startVertex, int endVertex) {
        super(graph, startVertex, endVertex);
    }

    /**
     * find coloring errors
     */
    @Override
    public void run() {
        for(int i = startVertex; i < endVertex; i++){
            ListNode neigh = graph.getAdjList()[i];
            while (neigh != null){
                int neighVertex = neigh.getData();
                int neighColor = graph.getColors()[neighVertex];
                int vertexColor = graph.getColors()[i];
                if((neighColor == vertexColor)){
                    //System.out.println("added " + i + " for " + neighVertex);
                    errorVertices.add(Math.min(neighVertex,i));
                    break;
                }
                neigh = neigh.getNext();
            }
        }
    }

    public ArrayList<Integer> getErrorVertices() {
        return errorVertices;
    }

}
