package DataStructures;

public class Dictionary <T, V> {
    // objects within dictionary need to have correct hashCode overrides <------------

    // if needed, implement a resizing function, but that's a bit of a hassle if unnecessary
    private static final int DICT_SIZE = 101;

    LinkedList<Pair<T, V>>[] hashmap;
    private int size;


    public Dictionary(){
        this.hashmap = new LinkedList[DICT_SIZE];
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

    // Added 10.01.2026
    private boolean updateBucket(int bucketIndex, T key, V newValue) {
        // better iteration through would be nice
        LinkedList<Pair<T, V>> bucket = hashmap[bucketIndex];
        if (bucket != null) {
            LinkedListNode<Pair<T, V>> currentNode = bucket.getHead();
            while (currentNode != null) {
                if (currentNode.value.first.equals(key)) {
                    currentNode.value.second = newValue;
                    return true;
                }
                currentNode = currentNode.next;
            }
        }
        return false;
    }

    // operations

    public boolean is_empty(){
        return (size == 0);
    }
    public int size() {return size;}

    public V get(T key){
        int index = hash(key);

        Pair<T, V> foundPair = searchBucket(index, key);
        if (foundPair == null) return null;
        return foundPair.second;
    }

    public void insert(T key, V value){
        int index = hash(key);
        if (hashmap[index] == null) {
            hashmap[index] = new LinkedList<>();
        }
        if (!updateBucket(index, key, value)){
            hashmap[index].appendNode(new Pair<>(key, value));
            size++;
        }
    }

    public boolean delete(T key){
        int index = hash(key);
        // better deletion, since this takes double iteration
        LinkedList<Pair<T, V>> bucket = hashmap[index];
        if (bucket != null) {
            LinkedListNode<Pair<T, V>> deletedNode = bucket.deleteNode(searchBucket(index, key));
            if (deletedNode != null) size--;
            return deletedNode != null;
        }
        return false;
    }

    public LinkedList<T> keys(){
        LinkedList<T> keyList = new LinkedList<>();
        for (int i = 0; i < DICT_SIZE; ++i){
            if (hashmap[i] != null){
                LinkedListNode<Pair<T, V>> currentNode = hashmap[i].getHead();
                while (currentNode != null){
                    keyList.appendNode(currentNode.value.first);
                    currentNode = currentNode.next;
                }
            }
        }
        return keyList;
    }

    public LinkedList<V> values(){
        LinkedList<V> valueList = new LinkedList<>();
        for (int i = 0; i < DICT_SIZE; ++i){
            if (hashmap[i] != null){
                LinkedListNode<Pair<T, V>> currentNode = hashmap[i].getHead();
                while (currentNode != null){
                    valueList.appendNode(currentNode.value.second);
                    currentNode = currentNode.next;
                }
            }
        }
        return valueList;


    }

}
