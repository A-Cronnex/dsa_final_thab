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

    public Graph() {
    }

    public Graph(JSONGraph inputGraph) {
        initialiseNodes(inputGraph.getNodes());
        initialiseEdges(inputGraph.getEdges());
    }

    public void initialiseNodes(List<Node> nodesInput) {
        for (Node node : nodesInput) {
            int pos = indexToNode.size();
            idToIndex.insert(node.getId(), pos);
            indexToNode.add(node);
            adjacencyList.add(pos, new LinkedList<>());
        }
    }

    public void initialiseEdges(List<JSONEdge> edgesInput) {
        for (JSONEdge edge : edgesInput) {
            int from = idToIndex.get(edge.getFrom());
            int to = idToIndex.get(edge.getTo());
            // Create main direction edge
            adjacencyList.get(from).appendNode(new Edge(to, edge.getDistance(), edge.getCapacity(), edge.getEnergy(), edge.isRestricted(), edge.isBidirectional()));
            if (edge.isBidirectional()) {
                adjacencyList.get(to).appendNode(new Edge(from, edge.getDistance(), edge.getCapacity(), edge.getEnergy(), edge.isRestricted(), edge.isBidirectional()));
            }
        }
    }
    //  B2 NO-FLY ZONE
    // Gets the Edge object from one node to another (if it exists)
    public Edge getEdge(int from, int to) {
        if (from < 0 || from >= adjacencyList.size())
            return null;

        //this will find edges
        for (Edge e : adjacencyList.get(from)) {
            if (e.getTo() == to) {
                return e;
            }
        }
        return null;
    }
    //Marks a corridor (edge) as a no-fly zone by setting restricted = true
    public void setNoFlyZone(int from, int to) {
        Edge e = getEdge(from, to);
        if (e != null) {
            e.setRestricted(true);
        }
    }
    //Removes a no-fly zone restriction from an edge
    public void clearNoFlyZone(int from, int to) { // B2 ADDITION
        Edge e = getEdge(from, to);
        if (e != null) {
            e.setRestricted(false);
        }
    }

}
