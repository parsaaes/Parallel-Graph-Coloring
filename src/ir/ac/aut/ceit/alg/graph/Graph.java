package ir.ac.aut.ceit.alg.graph;


import ir.ac.aut.ceit.alg.io.FileUtils;

import java.util.ArrayList;

public class Graph {
    private ListNode[] adjList;
    private int[] colors;
    private boolean[] isVisited;
    private int vertexNumber;

    public Graph(String filePath){
        createAdjList(filePath);
        System.out.println("Graph created. Max Edge: " + vertexNumber);
    }

    private void createAdjList(String path) {
        ArrayList<int[]> csv = FileUtils.readCSV(path);
        int maxEdge = 0;

        for (int[] edgeEndPoints : csv) {
            if(edgeEndPoints[0] > maxEdge){
                if(edgeEndPoints[1] > edgeEndPoints[0]){
                    maxEdge = edgeEndPoints[1];
                }
                else {
                    maxEdge = edgeEndPoints[0];
                }
            }
        }

        vertexNumber = maxEdge + 1;
        isVisited = new boolean[vertexNumber];
        adjList = new ListNode[vertexNumber];
        colors = new int[vertexNumber];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = -1; // means uncolored
        }

        for (int[] edgeEndPoints : csv) {
            insertEdge(edgeEndPoints[0],edgeEndPoints[1]);
        }
    }

    // a -> b
    public void insertEdge(int a, int b){
        adjList[a] = new ListNode(b, adjList[a]);
    }


    public void printAdjList(){
        for (int i = 0; i < adjList.length; i++) {
            ListNode listNode = adjList[i];
            System.out.print(i + ": ");
            if (listNode != null) {
                listNode.printFromHere();
                System.out.println(" ");
            }
            else {
                System.out.println("[ ]");
            }
        }
    }

    public void runDFS(){
        isVisited = new boolean[vertexNumber];
        runDFS(0);
    }
    private void runDFS(int startVertex) {
        isVisited[startVertex] = true;
        // System.out.println("visited " + intToChar.get(startVertex) + " ");
        ListNode adjVertex = adjList[startVertex];
        while (adjVertex != null) {
            if (isVisited[adjVertex.getData()] == false) {
                runDFS(adjVertex.getData());
            }
            adjVertex = adjVertex.getNext();
        }
    }

    public ListNode[] getAdjList() {
        return adjList;
    }

    public int[] getColors() {
        return colors;
    }

    public int getVertexNumber() {
        return vertexNumber;
    }

    public void doColoring(int vertex, int color){
        colors[vertex] = color;
    }

    public void printColoring(){
        for (int i = 0; i < colors.length; i++) {
            System.out.println(i + "=>" + colors[i]);
        }
    }
}
