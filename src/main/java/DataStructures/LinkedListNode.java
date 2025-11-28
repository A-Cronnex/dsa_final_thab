package DataStructures;

public class LinkedListNode<Integer> {
    public Integer element;
    public LinkedListNode<Integer> next;

    public LinkedListNode(Integer e){
        this.element = e;
    }

    public Integer getElementNextNode(){
        return this.next.element;
    }

}
