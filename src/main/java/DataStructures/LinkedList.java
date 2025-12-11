package DataStructures;

public class LinkedList<T> {
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail = null;
    private int size = 0;


    public LinkedList(){
    }

    public void appendNode(T value){
        if (size == 0){
            this.head = new LinkedListNode<T>(value);
            this.tail = this.head;
            this.tail.next = null;
            size++;
            return;
        }

        this.tail.next = new LinkedListNode<T>(value);
        this.tail = this.tail.next;

        //  doesn't java initialize field variables as null already?
        //  this.tail.next = null;

        size++;
    }

    public boolean findValue(T value){
        return findValue(head, value);
    }

    public boolean findValue(LinkedListNode<T> node, T value){

        while (node != null){
            if(node.value.equals(value)) return true;
            node = node.next;
        }

        return false;

        /* why use recursive function, when you can just do a loop?

        if(node.element.equals(value)){
            return true;
        } else if (node.next == null){
            return false;
        }

        return (findValueRecursive(node.next, value));*/
    }

    public LinkedListNode<T> getHead() {
        return this.head;
    }

    public LinkedListNode<T> indexDeleteNode(int index){

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

    //deletion by value
    // needs attention, is a mess
    public LinkedListNode<T> deleteNode(T value){
        LinkedListNode<T> popNode = null;
        if (this.head == null){
            return null;
        }

        LinkedListNode checkedNode = this.head;
        if (head.value == value){
            popNode = head;
            this.head = head.next;
            size--;
        } else {
            while (checkedNode != null){
                if (checkedNode.next.value == value){
                    popNode = checkedNode.next
                    checkedNode.next = popNode.next;
                    size--;
                    break;
                }
            }
        }
        if (size == 0){
            this.head = null;
            this.tail = null;
        }
    }

    private LinkedListNode<T> findNodeAndDelete(LinkedListNode<T> node, int index, int starting){

        if (node == null){
            return null;
        } else {
            if (starting == index - 1){
                LinkedListNode<T> temp = node.next;
                if (temp.next == null){
                    this.tail = node;
                    this.tail.next = null;
                    this.size--;
                    return this.tail;
                }
                node.next = temp.next;
                this.size--;
                return node;

            }
        }

        return findNodeAndDelete(node.next, index, starting + 1);
    }
}
