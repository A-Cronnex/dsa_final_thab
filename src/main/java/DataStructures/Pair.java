package DataStructures;

import java.util.Objects;

public class Pair <T, V> implements Cloneable{
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

    @Override
    public String toString() {
        return first.toString() + " : " + second.toString();
    }

    @Override
    protected Pair<T, V> clone(){
        return new Pair<>(first, second);
    }
}
