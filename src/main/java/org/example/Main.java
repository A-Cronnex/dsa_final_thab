package org.example;

import DataStructures.Dictionary;
import DataStructures.LinkedList;
import DataStructures.LinkedListNode;
import DataStructures.Pair;
import org.example.JSON.JsonFileReader;

import java.lang.reflect.Type;

public class Main {
    public static void main(String[] args) {
        // subject to change!!!!
        String filename = "src/sample.json";
        JsonFileReader JSONParser = new JsonFileReader();
        Graph NovaSchilda = new Graph(JSONParser.readFromFile(filename));
        System.out.println(NovaSchilda.indexToNode);
        NovaSchilda.idToIndex.keys().printList();
        NovaSchilda.idToIndex.values().printList();
        for (LinkedList<Edge> row : NovaSchilda.adjacencyList){
            row.printList();
        }
    }


}