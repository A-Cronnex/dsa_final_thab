package DataStructures.GraphElements;

import DataStructures.MyHashMap;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    private ArrayList<LinkedList> adjacencyList = new ArrayList<LinkedList>();
    private Map<String, Integer> dictionary = new HashMap<String, Integer>();
    public Graph(GraphNode[] nodes){
        for (int i = 0; i < nodes.length; i++){
            LinkedList list = new LinkedList();
            adjacencyList.add(list);
            GraphNode node = new GraphNode(nodes[i].getId(), nodes[i].getType(),nodes[i].getName(), nodes[i].getX(), nodes[i].getY());
            adjacencyList.get(i).appendNode(node);
            dictionary.put(nodes[i].getId(),i);

        }
    }

    public void appendEdge(Object[] edgeList){
        for (int i = 0; i < edgeList.length; i++){
            //Ṕrovisional coding structure. Must be adapted to fit the structure of the JSON file
            int index = dictionary.get(edgeList.from);
            adjacencyList.get(index).appendNode();
        }

    }
}
