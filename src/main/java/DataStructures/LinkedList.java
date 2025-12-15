package DataStructures;

public class LinkedList<T> {
    private LinkedListNode<T> head = new LinkedListNode<T>(null);
    private LinkedListNode<T> tail = head;
    private int size = 0;


    public LinkedList() {
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

    // does not actually return the dummy head, but the first actual element
    public LinkedListNode<T> getHead(){
        return this.head.next;
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
}