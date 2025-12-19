package DataStructures.GraphElements;

public class LinkedList<T> {

    //Changed to generics, for better usability
    protected T head;
    protected int size = 0;
    protected T tail = null;

    public LinkedList(){
    }


    public void appendNode(T node){
        if (size == 0){
            this.head = node;
            this.tail = this.head;
            this.tail.next = null;
            size++;
            return;
        }


        T temp = this.tail;

        temp.next = node;

        this.tail = node;

        this.tail.next = null;

        size++;
        }

        public GraphNode findValueRecursive(GraphNode element, String key){

            if(element.getId().equals(key)){
                return element;
            } else if (element.next == null){
                return null;
            }

            return (findValueRecursive(element.next, key));
        }

        public T getHead() {
            return this.head;
        }

        public T deleteNode(String key){

            if (this.head == null){
                return null;
            }

            if (this.getHead().getId().equals(key)){

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

            return findNodeAndDelete(this.getHead(),key);
        }

    private T findNodeAndDelete(T node, String key) {
        if (node == null){
            return null;
        } else {
            if (node.next.getId().equals(key)){
                if(node.next.next == null){
                    this.tail = node;
                    this.tail.next = null;
                    this.size--;
                    return this.tail;
                }
                    node.next = node.next.next;
                    this.size--;
                    return node;
                }
            }

            return findNodeAndDelete(node.next, key);
        }
}

