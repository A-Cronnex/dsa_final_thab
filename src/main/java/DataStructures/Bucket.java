package DataStructures;

public class Bucket<K, V> {
    BucketNode<K,V> head;
    int size = 0;
    BucketNode<K, V> tail = null;
    public Bucket(){
    }

    public void appendNode(String key, String value){
        if (size == 0){
            this.head = new BucketNode<String, String>(key,value);
            this.tail = this.head;
            this.tail.next = null;
            size++;
            return;
        }

        BucketNode<String,String> node = new BucketNode<String,String>(key,value);

        BucketNode<String,String> temp = this.tail;

        temp.next = node;

        this.tail = node;

        this.tail.next = null;


        size++;
    }

    public BucketNode<String,String> findValueRecursive(BucketNode<String, String> element, String key){

        if(element.key.equals(key)){
            return element;
        } else if (element.next == null){
            return null;
        }

        return (findValueRecursive(element.next, key));
    }

    public BucketNode<String,String> getHead() {
        return  this.head;
    }

    public BucketNode<String,String> deleteNode(String key){

        if (this.head == null){
            return null;
        }

        if (this.getHead().getKey().equals(key)){

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

    /*public LinkedListNode<String, String> findNodeAndDelete(LinkedListNode<String,String> element, int index, int starting){

        if (element == null){
            return null;
        } else {
            if (starting == index -1){
                LinkedListNode<String,String> temp = element.next;
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
    }*/

    private BucketNode<String, String> findNodeAndDelete(BucketNode<String,String> node, String key) {


        if (node == null){
            return null;
        } else {
            if (node.next.getKey().equals(key)){
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

    public void printAllElementsWithReferences() {
        System.out.println("--- LinkedList Elements and References ---");
        if (this.head == null) {
            System.out.println("The list is empty (head is null).");
            System.out.println("Size: 0");
            return;
        }

        BucketNode<String, String> current = this.head;
        int count = 0;

        // Iterate through the list until 'current' becomes null
        while (current != null) {

            // Use System.identityHashCode() to get a consistent representation
            // of the object's memory address hash code.
            String currentNodeReference = Integer.toHexString(System.identityHashCode(current));

            // Get the reference of the next node for comparison
            String nextNodeReference = (current.next == null)
                    ? "null"
                    : Integer.toHexString(System.identityHashCode(current.next));

            System.out.printf("[%d] Key: %s, Value: %s\n", count, current.key, current.value);
            System.out.printf("    -> Current Node Ref: @%s\n", currentNodeReference);
            System.out.printf("    -> Next Node Ref:    @%s\n", nextNodeReference);

            // Move to the next node
            current = current.next;
            count++;
        }

        System.out.println("Total Size: " + this.size);
        System.out.println("----------------------------------------");
    }
}
