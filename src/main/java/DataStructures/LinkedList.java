package DataStructures;

import java.lang.reflect.Type;

public class LinkedList {
    LinkedListNode<Integer> head;
    int size = 0;
    LinkedListNode<Integer> tail = null;
    public LinkedList(){
    }

    public void appendNode(int value){
        if (size == 0){
            this.head = new LinkedListNode<Integer>(value);
            this.tail = this.head;
            this.tail.next = null;
            size++;
            return;
        }

        LinkedListNode<Integer> node = new LinkedListNode<Integer>(value);

        LinkedListNode<Integer> temp = this.tail;

        temp.next = node;

        this.tail = node;

        this.tail.next = null;

        size++;
    }



    public boolean findValueRecursive(LinkedListNode<Integer> element, int value){

        if(element.element == value){
            return true;
        } else if (element.next == null){
            return false;
        }

        return (findValueRecursive(element.next, value));
    }

    public LinkedListNode<Integer> getHead() {
        return  this.head;
    }
}
