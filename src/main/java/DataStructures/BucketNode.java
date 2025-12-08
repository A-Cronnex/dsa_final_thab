package DataStructures;

public class BucketNode<K,V> {
    public K key;
    public V value;
    public BucketNode<K,V> next;

    public BucketNode(K key, V value){

        this.key = key;
        this.value = value;
    }

    public BucketNode<K, V> getNextNode(){
        return this.next;
    }

    public K getKey(){
        return this.key;
    }

    public V getValue(){
        return this.value;
    }

}
