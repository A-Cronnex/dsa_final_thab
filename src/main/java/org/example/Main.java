package org.example;

import DataStructures.LinkedList;
import DataStructures.Pair;

import java.lang.reflect.Type;

public class Main {
    public static void main(String[] args) {
        Pair<String, Integer> one = new Pair<>("Papa", 5);
        Pair<String, Integer> two = new Pair<>("Papa", 5);

        System.out.println(one.hashCode() == two.hashCode());

        LinkedList LL = new LinkedList<Node>();

        LL.appendNode(1);
        LL.appendNode(2);
        LL.appendNode(3);
        LL.appendNode(4);

        System.out.println(LL.findValue(LL.getHead(),-2));



    }


}