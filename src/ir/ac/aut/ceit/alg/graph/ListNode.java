package ir.ac.aut.ceit.alg.graph;


public class ListNode {
    private int data = -1;
    private ListNode next = null;

    public ListNode(int data , ListNode next) {
        this.data = data;
        this.next = next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    public ListNode getNext() {
        return next;
    }

    public int getData() {
        return data;
    }

    public void printFromHere(){
        ListNode tmp = this;
        System.out.print("[");
        while (tmp != null){
            System.out.print(" " + tmp.getData() + " ");
            tmp = tmp.getNext();
        }
        System.out.print("]");
    }

    public boolean containsInForward(int vertex){
        boolean result = false;
        ListNode tmp = this;
        while (tmp != null){
            if(tmp.getData() == vertex) {
                result = true;
                break;
            }
            tmp = tmp.getNext();
        }
        return result;
    }

    public int getDegree(){
        int result = 0;
        ListNode tmp = this;
        while (tmp != null){
            result++;
            tmp = tmp.getNext();
        }
        return result;
    }

    //if working on undirected graph you need to use this twice
    public void removeEdgeInForward(int toBeReomved){
        if(containsInForward(toBeReomved) == false){
            return;
        }
        ListNode tmp = this;
        while (tmp.getNext() != null){
            if(tmp.getNext().getData() == toBeReomved) {
                tmp.setNext(tmp.getNext().getNext());
                break;
            }
            tmp = tmp.getNext();
        }
    }
}
