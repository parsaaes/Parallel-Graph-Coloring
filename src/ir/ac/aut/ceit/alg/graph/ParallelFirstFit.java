package ir.ac.aut.ceit.alg.graph;


import java.util.ArrayList;

public class ParallelFirstFit extends ParallelGraph implements Runnable {


    public ParallelFirstFit(Graph graph, int startVertex, int endVertex) {
        super(graph,startVertex,endVertex);
    }

    @Override
    public void run() {
        for (int i = startVertex; i < endVertex ; i++) {
            ArrayList<Integer> unPossibleList = buildImpossibleList(graph.getAdjList()[i]);
            int color = getLowestAvailableColor(unPossibleList);
            graph.doColoring(i,color);
        }
        // graph.printColoring();
    }


    private ArrayList<Integer> buildImpossibleList(ListNode firstNeigh) {
        ArrayList<Integer> unPossibleList = new ArrayList<>();
        ListNode neigh = firstNeigh;
        while (neigh != null){
            if(graph.getColors()[neigh.getData()] != -1){
                unPossibleList.add(graph.getColors()[neigh.getData()]);
            }
            neigh = neigh.getNext();
        }
        return unPossibleList;
    }

}
