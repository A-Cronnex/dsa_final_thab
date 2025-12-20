package org.example;

import DataStructures.Dictionary;
import DataStructures.LinkedList;
import org.example.JSON.JSONEdge;
import org.example.JSON.JSONGraph;
import org.example.Edge;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

// THIS SHOULD BE THE MAIN CLASS FOR THIS PROJECT IN FACT
public class Graph {
    // is this fine? I don't know
    public ArrayList<Node> indexToNode = new ArrayList<>();
    public Dictionary<String, Integer> idToIndex = new Dictionary<>();
    public ArrayList<LinkedList<Edge>> adjacencyList = new ArrayList<>();

    public Graph(){
    }
    public Graph(JSONGraph inputGraph){
        initialiseNodes(inputGraph.getNodes());
        initialiseEdges(inputGraph.getEdges());
    }
    public void initialiseNodes(List<Node> nodesInput){
        for (Node node : nodesInput){
            int pos = indexToNode.size();
            idToIndex.insert(node.getId(), pos);
            indexToNode.add(node);
            adjacencyList.add(pos, new LinkedList<>());
        }
    }
    public void initialiseEdges(List<JSONEdge> edgesInput){
        for (JSONEdge edge : edgesInput){
            int from = idToIndex.get(edge.getFrom());
            int to = idToIndex.get(edge.getTo());
            adjacencyList.get(from).appendNode(new Edge(to, edge.getDistance(), edge.getCapacity(), edge.getEnergy(), edge.isRestricted(), edge.isBidirectional()));
            if (edge.isBidirectional()){
                adjacencyList.get(to).appendNode(new Edge(from, edge.getDistance(), edge.getCapacity(), edge.getEnergy(), edge.isRestricted(), edge.isBidirectional()));
            }
        }
    }

}
