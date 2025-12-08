package DataStructures;

public class Dictionary <T, V> {
    // objects within dictionary need to have correct hashCode overrides <------------
    /*
    replace with a big prime number later, based on likely number of inputs
    i.e.    16381
     */
    private static final int DICT_SIZE = 101;

    LinkedList<Pair<T, V>>[] hashmap;
    int size = 0;

    public int hash(T obj){
        return obj.hashCode() % DICT_SIZE;
    }

    public boolean is_empty(){
        return (size == 0);
    }

    // templates

    public V search(T key){
        int pos = hash(key);
        LinkedList<Pair<T, V>> bucket = hashmap[pos];

        // if (hashmap[pos] == null) return null;
        while ()
    }


}
