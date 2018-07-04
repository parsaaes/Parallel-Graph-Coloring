package ir.ac.aut.ceit.alg.graph;


import java.util.ArrayList;

public abstract class ParallelGraph {
    protected Graph graph;
    protected int startVertex;
    protected int endVertex;

    public ParallelGraph(Graph graph, int startVertex, int endVertex) {
        this.graph = graph;
        this.startVertex = startVertex;
        this.endVertex = endVertex;
    }

    /**
     * get the color with least index number
     * @param imPossibleList impossible list of colors
     * @return
     */
    protected int getLowestAvailableColor(ArrayList<Integer> imPossibleList) {
        int color = -2;
        for (int j = 0; j < graph.getVertexNumber(); j++) {
            if(!imPossibleList.contains(j)){
                color = j;
                break;
            }
        }
        return color;
    }
}
