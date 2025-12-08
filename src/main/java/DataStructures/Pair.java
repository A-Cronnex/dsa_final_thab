package DataStructures;

import java.util.Objects;

public class Pair <T, V>{
    public T first;
    public V second;

    public Pair(T first, V second){
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair)) return false;
        Pair pr = (Pair)obj;
        return this.first.equals(pr.first) && this.second.equals(pr.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
