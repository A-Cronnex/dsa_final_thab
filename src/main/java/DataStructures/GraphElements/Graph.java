package DataStructures.GraphElements;

import DataStructures.MyHashMap;

import java.util.ArrayList;

public class Graph {
    private ArrayList<LinkedList> adjacencyList = new ArrayList<LinkedList>();

    public Graph(GraphNode[] nodes){
        for (int i = 0; i < nodes.length; i++){
            LinkedList list = new LinkedList();
            adjacencyList.add(list);
            adjacencyList.get(i).appendNode(nodes[i].id, nodes[i].type,nodes[i].name, nodes[i].x, nodes[i].y,nodes[i].arrayPosition);

        }
    }

    public void appendEdge(Object[] edgeList){
        for (int i = 0; i < edgeList.length; i++){

            if (true){

            }
        }

    }
}
