package DataStructures;

import java.util.ArrayList;

public class BinaryHeap {
    // Min Heap implementation
    // you're supposed to implement a comparator actually
    ArrayList<Pair<Integer, Integer>> heap = new ArrayList<>();
    // Pair<ElementID, Priority>
    Dictionary<Integer, Integer> pos = new Dictionary<>();
    // Pair<ElementID, Index>


    public boolean isEmpty(){
        return heap.size() == 0;
    }

    private void swap(int a, int b) {
        Pair<Integer, Integer> tempa = heap.get(a), tempb = heap.get(b);
        heap.set(a, tempb);
        pos.insert(tempb.first, a);
        heap.set(b, tempa);
        pos.insert(tempa.first, b);
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

    public void insertKey(int first, int priority) {
        int id = heap.size();
        heap.add(new Pair<>(first, priority));
        pos.insert(first, id);

        while (id != 0 && heap.get(id).second < heap.get(parent(id)).second) {
            swap(id, parent(id));
            id = parent(id);
        }
    }

    private void decreaseKey(int id, int new_val){
        heap.get(id).second = new_val;

        while (id != 0 && heap.get(id).second < heap.get(parent(id)).second) {
            swap(id, parent(id));
            id = parent(id);
        }
    }

    public Pair<Integer, Integer> getMin() {
        return heap.get(0).clone();
    }

    public Pair<Integer, Integer> extractMin(){
        if (heap.size() == 0){
            return null;
        }

        if (heap.size() == 1){
            Pair<Integer, Integer> root = getMin();
            heap.remove(0);
            pos.delete(root.first);
            return root;
        }

        Pair<Integer, Integer> root = getMin();

        Pair<Integer, Integer> temp = heap.get(heap.size() - 1);
        heap.set(0, temp);
        pos.insert(temp.first, 0);

        heap.remove(heap.size()-1);
        MinHeapify(0);

        pos.delete(root.first);
        return root;
    }

    public void deleteKey(int first) {
        changeKey(first, Integer.MIN_VALUE);
        extractMin();
    }

    private void MinHeapify(int id){
        int l = left(id);
        int r = right(id);

        int smallest = id;
        if (l < heap.size() && heap.get(l).second < heap.get(smallest).second){
            smallest = l;
        }
        if (r < heap.size() && heap.get(r).second < heap.get(smallest).second){
            smallest = r;
        }

        if (smallest != id){
            swap(id, smallest);
            MinHeapify(smallest);
        }
    }

    private void increaseKey(int id, int new_val) {
        heap.get(id).second = new_val;
        MinHeapify(id);
    }

    public void changeKey(int first, int new_val){
        Integer idPTR = pos.get(first);
        if (idPTR == null) return;
        int id = idPTR;

        int old_val = heap.get(id).second;

        if (old_val == new_val) {
            return;
        }
        if (old_val < new_val){
            increaseKey(id, new_val);
        } else {
            decreaseKey(id, new_val);
        }
    }

    public void addKey(int first, int change){
        Integer idPTR = pos.get(first);
        if (idPTR == null) return;
        int id = idPTR;

        int new_val = heap.get(id).second + change;

        if (change == 0) {
            return;
        }
        if (change > 0){
            increaseKey(id, new_val);
        } else {
            decreaseKey(id, new_val);
        }
    }
}
