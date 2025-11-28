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

    public LinkedListNode<Integer> deleteNode(int index){

        if (this.head == null){
            return null;
        }

        if (index == 0){

            if (this.head.next == null) {
                this.head = null;
                this.tail = null;
                this.size--;
                return null;
            }

            this.head = this.head.next;
            this.size--;
            return this.head;
        }

        return findNodeAndDelete(this.getHead(),index,0);
    }

    private LinkedListNode<Integer> findNodeAndDelete(LinkedListNode<Integer> element, int index, int starting){

        if (element == null){
            return null;
        } else {
            if (starting == index -1){
                LinkedListNode<Integer> temp = element.next;
                if (temp.next == null){
                    this.tail = element;
                    this.tail.next = null;
                    this.size--;
                    return  this.tail;
                }
                element.next = temp.next;
                this.size--;
                return element;

            }
        }

        return findNodeAndDelete(element.next, index, starting + 1);
    }
}
