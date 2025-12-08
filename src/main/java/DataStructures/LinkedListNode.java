package DataStructures;

public class LinkedListNode<T> {
    public T element;
    public LinkedListNode<T> next;

    public LinkedListNode(T e){
        this.element = e;
    }

    public T getElementNextNode(){
        return this.next.element;
    }

}
