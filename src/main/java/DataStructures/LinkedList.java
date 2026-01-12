package DataStructures;

public class LinkedList<T> implements Iterable<T> {
    private LinkedListNode<T> head = new LinkedListNode<T>(null);
    private LinkedListNode<T> tail = head;
    private int size = 0;


    public LinkedList() {
    }

    // does not actually return the dummy head, but the first actual element
    public LinkedListNode<T> getHead(){
        return this.head.next;
    }

    public int getSize(){
        return size;
    }

    public void appendNode(T value) {
        LinkedListNode<T> toAdd = new LinkedListNode<>(value);

        tail.next = toAdd;
        tail = toAdd;
        size++;
    }

    public boolean findValue(T value) {
        return findValue(head, value);
    }

    public boolean findValue(LinkedListNode<T> node, T value) {

        while (node != null) {
            if (node.value.equals(value)) return true;
            node = node.next;
        }

        return false;
    }

    public T get(int atIndex){
        if (atIndex >= size) return null;

        LinkedListNode<T> desiredNode = this.head;
        for (int i = 0; i <= atIndex; ++i) desiredNode = desiredNode.next;
        return desiredNode.value;
    }

    public LinkedListNode<T> removeChild(LinkedListNode<T> deletionPoint) {
        if (deletionPoint.next.equals(this.tail)) this.tail = deletionPoint;
        LinkedListNode<T> deleted = deletionPoint.next;
        deletionPoint.next = deleted.next;
        size--;
        return deleted;
    }

    public LinkedListNode<T> deleteNode(T value) {

        LinkedListNode linkingNode = this.head;
        while (linkingNode.next != null) {
            if (linkingNode.next.value.equals(value)) {
                return removeChild(linkingNode);
            }
        }

        return null;
    }

    // deletion by Node Reference
    public boolean deleteNode(LinkedListNode<T> reference) {

        LinkedListNode linkingNode = this.head;
        while (linkingNode.next != null) {
            if (linkingNode.next.equals(reference)) {
                removeChild(linkingNode);
                return true;
            }
        }

        return false;
    }

    // deletion by index
    public boolean deleteAt(int atIndex){
        if (atIndex >= size) return false;

        LinkedListNode<T> desiredNode = this.head;
        for (int i = 0; i < atIndex; ++i) desiredNode = desiredNode.next;
        removeChild(desiredNode);
        return true;
    }


    // iteration would be nice

    public void printList(){
        LinkedListNode<T> currentNode = this.getHead();
        while (currentNode != null){
            System.out.print(currentNode.value.toString() + "  ");
            currentNode = currentNode.next;
        }
        System.out.println();
    }
    @Override
    public java.util.Iterator<T> iterator() {
        return new java.util.Iterator<T>() {

            LinkedListNode<T> current = head.next;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T value = current.value;
                current = current.next;
                return value;
            }
        };
    }
}