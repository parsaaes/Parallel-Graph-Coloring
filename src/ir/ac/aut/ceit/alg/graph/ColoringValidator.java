package ir.ac.aut.ceit.alg.graph;

public class ColoringValidator {
    /**
     * check if coloring is correct
     * @param graph given graph
     * @return true if coloring is correct
     */
    public static boolean validate(Graph graph){
        int vertexNum = graph.getVertexNumber();
        int[] colorArr = graph.getColors();
        for (int i = 0; i < vertexNum ; i++) {
            ListNode neigh = graph.getAdjList()[i];
            while (neigh != null){
                if(colorArr[i] == -1){
                    return false;
                }
                else if(colorArr[neigh.getData()] == colorArr[i]){
                    return false;
                }
                neigh = neigh.getNext();
            }
        }
        return true;
    }
}
