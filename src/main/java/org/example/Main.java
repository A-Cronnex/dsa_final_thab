package org.example;

import DataStructures.Dictionary;
import DataStructures.LinkedList;
import DataStructures.LinkedListNode;
import DataStructures.Pair;

import java.lang.reflect.Type;

public class Main {
    public static void main(String[] args) {
        Dictionary<String, Integer> test = new Dictionary<>();

        test.insert("ABBA", 4);
        test.insert("Me", 1);
        test.insert("HUB", 55);

        LinkedList<String> keys = test.keys();
        LinkedList<Integer> values = test.values();

        LinkedListNode<String> node = keys.getHead();
        while (node != null){
            System.out.println(node.value);
            node = node.next;
        }
        System.out.println();
        LinkedListNode<Integer> node2 = values.getHead();
        while (node2 != null){
            System.out.println(node2.value);
            node2 = node2.next;
        }
        System.out.println();

        System.out.println(test.is_empty());
        System.out.println(test.get("HUB"));
        System.out.println(test.delete("ABBA"));
        System.out.println(test.get("ABBA"));
    }


}