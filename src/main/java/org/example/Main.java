package org.example;

import DataStructures.LinkedList;

import java.lang.reflect.Type;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        LinkedList LL = new LinkedList();

        LL.appendNode(1);
        LL.appendNode(2);
        LL.appendNode(3);
        LL.appendNode(4);

        System.out.println(LL.findValueRecursive(LL.getHead(),-2));



    }


}