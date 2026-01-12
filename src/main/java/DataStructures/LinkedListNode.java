package DataStructures;

public class LinkedListNode<T> {
    public T value;
    public LinkedListNode<T> next;

    public LinkedListNode(T e){
        this.value = e;
    }

    public T getElementNextNode(){
        return this.next.value;
    }

}
