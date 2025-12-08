package DataStructures.GraphElements;

public class LinkedList {
    protected GraphNode head;
    protected int size = 0;
    protected GraphNode tail = null;

    public LinkedList(){
    }

    public void appendNode(String id, String type, String name, Integer x, Integer y, Integer arrayPosition){
        if (size == 0){
            this.head = new GraphNode(id,type, name, x, y, arrayPosition);
            this.tail = this.head;
            this.tail.next = null;
            size++;
            return;
        }

        GraphNode node = new GraphNode(id,type, name, x, y, arrayPosition);

        GraphNode temp = this.tail;

        temp.next = node;

        this.tail = node;

        this.tail.next = null;

        size++;
        }

        public GraphNode findValueRecursive(GraphNode element, String key){

            if(element.id.equals(key)){
                return element;
            } else if (element.next == null){
                return null;
            }

            return (findValueRecursive(element.next, key));
        }

        public GraphNode getHead() {
            return  this.head;
        }

        public GraphNode deleteNode(String key){

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

    private GraphNode findNodeAndDelete(GraphNode node, String key) {
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

