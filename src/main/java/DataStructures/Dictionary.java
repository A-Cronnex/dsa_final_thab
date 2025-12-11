package DataStructures;

public class Dictionary <T, V> {
    // objects within dictionary need to have correct hashCode overrides <------------

    // if needed, implement a resizing function, but that's a bit of a hassle if unnecessary
    private static final int DICT_SIZE = 101;

    LinkedList<Pair<T, V>>[] hashmap;
    private int size;


    public Dictionary(){
        this.hashmap = new LinkedList[];
        this.size = 0;
    }

    // helper functions
    // so that everything has a corresponding bucket
    public int hash(T key){
        int hashNumber = key.hashCode();
        if (hashNumber < 0) hashNumber = -hashNumber;
        return hashNumber % DICT_SIZE;
    }

    // to look through a bucket - has iteration
    private Pair<T, V> searchBucket(int bucketIndex, T key) {
        // better iteration through would be nice
        LinkedList<Pair<T, V>> bucket = hashmap[bucketIndex];
        if (bucket != null) {
            LinkedListNode<Pair<T, V>> currentNode = bucket.getHead();
            while (currentNode != null) {
                if (currentNode.value.first.equals(key)) {
                    return currentNode.value;
                }
                currentNode = currentNode.next;
            }
        }
        return null;
    }

    // operations

    public boolean is_empty(){
        return (size == 0);
    }
    public int size() {return size;}

    public V search(T key){
        int index = hash(key);

        Pair<T, V> foundPair = searchBucket(index, key);
        if (foundPair == null) return null;
        return foundPair.second;
    }

    public void insert(T key, V value){
        int index = hash(key);
        if (hashmap[index] == null) {
            hashmap[index] = new LinkedList<>();
        } else if (searchBucket(index, key) != null) return;

        hashmap[index].appendNode(new Pair<>(key, value));
        size++;
    }

    public boolean delete(T key){
        int index = hash(key);
        // better iteration again
        LinkedList<Pair<T, V>> bucket = hashmap[index];
        if (bucket != null) {
            bucket.deleteNode(key);
            return true;
            }
        }
        return false;
    }

}
