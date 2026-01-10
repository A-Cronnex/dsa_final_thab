package DataStructures;

import java.util.ArrayList;

public class BinaryHeap {
    // Min Heap implementation
    // you're supposed to implement a comparator actually
    ArrayList<Pair<Integer, Integer>> heap = new ArrayList<>();
    // Pair<ElementID, Priority>

    public boolean isEmpty(){
        return heap.size() == 0;
    }

    private void swap(int a, int b) {
        Pair<Integer, Integer> temp = heap.get(a);
        heap.set(a, heap.get(b));
        heap.set(b, temp);
    }

    private int parent(int pos){
        return (pos - 1) / 2;
    }
    private int left(int pos) {
        return 2 * pos + 1;
    }
    private int right(int pos){
        return 2 * pos + 2;
    }

    public void insertKey(Pair<Integer, Integer> key) {
        int pos = heap.size();
        heap.add(key);

        while (pos != 0 && heap.get(pos).second < heap.get(parent(pos)).second) {
            swap(pos, parent(pos));
            pos = parent(pos);
        }
    }

    public void decreasePriority(int pos, int new_val){
        heap.get(pos).second = new_val;

        while (pos != 0 && heap.get(pos).second < heap.get(parent(pos)).second) {
            swap(pos, parent(pos));
            pos = parent(pos);
        }
    }

    public Pair<Integer, Integer> getMin() {
        return heap.get(0);
    }

    public Pair<Integer, Integer> extractMin(){
        if (heap.size() == 0){
            return null;
        }

        if (heap.size() == 1){
            Pair<Integer, Integer> root = getMin();
            heap.remove(0);
            return root;
        }

        Pair<Integer, Integer> root = getMin();

        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size()-1);
        MinHeapify(0);

        return root;
    }

    public void deleteKey(int pos) {
        decreasePriority(pos, Integer.MIN_VALUE);
        extractMin();
    }

    private void MinHeapify(int pos){
        int l = left(pos);
        int r = right(pos);

        int smallest = pos;
        if (l < heap.size() && heap.get(l).second < heap.get(smallest).second){
            smallest = l;
        }
        if (r < heap.size() && heap.get(r).second < heap.get(smallest).second){
            smallest = r;
        }

        if (smallest != pos){
            swap(pos, smallest);
            MinHeapify(smallest);
        }
    }

    public void increasePriority(int pos, int new_val) {
        heap.get(pos).second = new_val;
        MinHeapify(pos);
    }

    public void changePriority(int pos, int new_val){
        if (heap.get(pos).second == new_val) {
            return;
        }
        if (heap.get(pos).second < new_val){
            increasePriority(pos, new_val);
        } else {
            decreasePriority(pos, new_val);
        }
    }
}
