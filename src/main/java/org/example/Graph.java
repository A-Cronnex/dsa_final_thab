package org.example;

import DataStructures.BinaryHeap;
import DataStructures.Dictionary;
import DataStructures.LinkedList;
import DataStructures.Pair;
import org.example.JSON.JSONEdge;
import org.example.JSON.JSONGraph;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;
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
            System.out.println("Corridor from " + from + " to " + to + " is now restricted (No-Fly Zone).");
        }
        else {
            System.out.println("Such edge does not exist in the network!");
        }
    }
    //Removes a no-fly zone restriction from an edge
    public void clearNoFlyZone(int from, int to) { // B2 ADDITION
        Edge e = getEdge(from, to);
        if (e != null) {
            e.setRestricted(false);
        }
    }

    //B3
    public void createNewNode(String id, String name, String type, int x, int y){
        System.out.println("Add a new node to the network");
        Node node = new Node(id,name,type,x,y);
        int pos = indexToNode.size();
        idToIndex.insert(node.getId(), pos);
        indexToNode.add(node);
        adjacencyList.add(pos, new LinkedList<>());
    }

    public void modifyAndExtendNetwork(){

        System.out.println("1. add node 2. Extend corridor");
        Scanner myObj = new Scanner(System.in);
        int option = myObj.nextInt();
        if (option == 1){
            System.out.println("Please insert the attributes of the node: id, name, type, x (int), y (int), respectively");
            String id = myObj.nextLine();
            String name = myObj.nextLine();
            String type = myObj.nextLine();
            int x = myObj.nextInt();
            int y = myObj.nextInt();
            createNewNode(id,name,type,x,y);
        }
        if (option == 2){
            System.out.print("Create corridor");
            String idFrom = myObj.nextLine();
            String idTo = myObj.nextLine();

            int from = idToIndex.get(idFrom);
            int to = idToIndex.get(idTo);

            Node fromNode = indexToNode.get(from);
            Node toNode = indexToNode.get(to);

            if (fromNode == null || toNode == null){
                return;
            } else {
                int capacity = myObj.nextInt();
                int distance = myObj.nextInt();
                int energy = myObj.nextInt();
                boolean restricted = myObj.nextBoolean();
                boolean bidirectional = myObj.nextBoolean();
                addNewCorridor(from,to,capacity,distance,energy,restricted,bidirectional);
            }
        }

    }

    public void addNewCorridor(int from, int to, int capacity, int distance,int energy, boolean restricted, boolean bidirectional){


        adjacencyList.get(from).appendNode(new Edge(to, distance, capacity, energy, restricted, bidirectional));
        if (bidirectional) {
            adjacencyList.get(to).appendNode(new Edge(from, distance, capacity, energy, restricted, bidirectional));
        }
    }

    // F1: Check if all delivery nodes are reachable from a given hub
    public boolean allDeliveriesReachable(String hubId) {

        // Convert HUB ID to index
        Integer startIndex = idToIndex.get(hubId);
        if (startIndex == null) {
            System.out.println("Hub ID does not exist.");
            return false;
        }

        // BFS setup
        boolean[] visited = new boolean[adjacencyList.size()];
        java.util.Queue<Integer> queue = new java.util.LinkedList<>();

        queue.add(startIndex);
        visited[startIndex] = true;

        // BFS traversal (skip restricted edges)
        while (!queue.isEmpty()) {
            int current = queue.poll(); //poll means areads a value and dlt the elements

            for (Edge edge : adjacencyList.get(current)) {

                if (edge.isRestricted()) continue; // skip blocked edges

                int next = edge.getTo();

                if (!visited[next]) {
                    visited[next] = true;
                    queue.add(next);
                }
            }
        }

        boolean allReachable = true;
        // Check if all delivery nodes were reached
        for (int i = 0; i < indexToNode.size(); i++) {
            Node n = indexToNode.get(i);

            if (n.getType().equals("delivery")) {
                if (!visited[i]){
                    System.out.println("Unreachable delivery node: " + n.getId());
                    allReachable = false;
                } else {
                    System.out.println("Reachable delivery node: " + n.getId());
                }
            }
        }

        return allReachable;
    }
    //F3: Calculate Delivery Capacity
    public int calculateDeliveryCapacity(String hubId, ArrayList<String> urbanArea) {

        Integer hubIndex = idToIndex.get(hubId);
        if (hubIndex == null) {
            System.out.println("Hub does not exist.");
            return 0;
        }

        int totalCapacity = 0;

        // For every edge starting at the hub
        for (Edge e : adjacencyList.get(hubIndex)) {

            // Skip no-fly zone edges
            if (e.isRestricted()) continue;

            // If the edge leads to a delivery point in the urban area
            String targetId = indexToNode.get(e.getTo()).getId();

            if (urbanArea.contains(targetId)) {
                totalCapacity += e.getCapacity();
            }
        }

        return totalCapacity;
    }

    //F4: Find the minimum set of corridors, by closing which the network can be disconnected
    // Stoer-Wagner algorithm
    // 11.01.2026 assuming undirected graph
    public void minCut(){
        int n = indexToNode.size();
        int[][] adjacencyCopy = new int[n][n];
        for (int i = 0; i < n; ++i){
            for (Edge edge : adjacencyList.get(i)){
                adjacencyCopy[i][edge.getTo()] = 1;
                adjacencyCopy[edge.getTo()][i] = 1;
            }
        }

        ArrayList<Integer> best_cut = new ArrayList<>();
        int best_cost = Integer.MAX_VALUE;


        ArrayList<Integer>[] vertexMerges = new ArrayList[n];
        for (int i = 0; i < n; ++i){
            vertexMerges[i] = new ArrayList<>();
        }

        BinaryHeap weightQueue = new BinaryHeap();
        for (int i = 0; i < n; ++i){
            vertexMerges[i].add(i);
            weightQueue.insertKey(i, 0);
        }

        for (int phase = 0; phase < n - 1; ++phase){
            for (int i = 0; i < n - phase; ++i){
                weightQueue.changeKey(i, 0);
            }
            int prev = -1;
            for (int it = 0; it < n - phase; ++it){
                Pair<Integer, Integer> sel = weightQueue.getMin();
                //System.out.println(sel.second);
                weightQueue.changeKey(sel.first, 1);
                //System.out.println(sel.second);
                if (it == n - phase - 1){
                    if (-sel.second < best_cost){
                        best_cost = -sel.second;
                        best_cut = vertexMerges[sel.first];
                    }
                    vertexMerges[prev].addAll(vertexMerges[sel.first]);
                    for (int i = 0; i < n; ++i){
                        adjacencyCopy[prev][i] = adjacencyCopy[i][prev] + adjacencyCopy[sel.first][i];
                        adjacencyCopy[i][prev] = adjacencyCopy[prev][i];
                    }
                    weightQueue.deleteKey(sel.first);
                } else {
                    for (int i = 0; i < n; ++i){
                        weightQueue.addKey(i, -adjacencyCopy[sel.first][i]);
                    }
                    prev = sel.first;
                }
            }
        }

        // create a proper return
        System.out.println(best_cut);
        System.out.println(best_cost);

    }

}