package org.example;

import DataStructures.BinaryHeap;
import DataStructures.Dictionary;
import DataStructures.LinkedList;
import DataStructures.Pair;
import org.example.JSON.JSONEdge;
import org.example.JSON.JSONGraph;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

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
    // BFS to find an augmenting path (Edmonds–Karp requirement)
    private boolean bfs(int[][] capacity, int[][] flow, int source, int sink, int[] parent) {
        boolean[] visited = new boolean[capacity.length];
        java.util.Queue<Integer> queue = new java.util.LinkedList<>();

        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < capacity.length; v++) {
                if (!visited[v] && capacity[u][v] - flow[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                    if (v == sink) return true;
                }
            }
        }
        return false;
        }
        // Full Edmonds–Karp Maximum Flow
        private int edmondsKarp ( int[][] capacity, int source, int sink) {
            int n = capacity.length;
            int[][] flow = new int[n][n];
            int[] parent = new int[n];
            int maxFlow = 0;

            while (bfs(capacity, flow, source, sink, parent)) {

                int pathFlow = Integer.MAX_VALUE;

                // find bottleneck of the found path
                for (int v = sink; v != source; v = parent[v]) {
                    int u = parent[v];
                    pathFlow = Math.min(pathFlow, capacity[u][v] - flow[u][v]);
                }

                // update residual flow
                for (int v = sink; v != source; v = parent[v]) {
                    int u = parent[v];
                    flow[u][v] += pathFlow;
                    flow[v][u] -= pathFlow; // reverse edge
                }

                maxFlow += pathFlow;
            }

            return maxFlow;
        }
            public int calculateDeliveryCapacityEdmondsKarp (String hubId, ArrayList < String > deliveryArea){

                Integer hubIndex = idToIndex.get(hubId);
                if (hubIndex == null) {
                    System.out.println("Hub does not exist.");
                    return 0;
                }

                int n = indexToNode.size();
                int superSink = n; // create additional node for super-sink

                // capacity matrix of size n+1 (extra for supersink)
                int[][] capacity = new int[n + 1][n + 1];

                // build capacity matrix from adjacency list
                for (int u = 0; u < n; u++) {
                    for (Edge e : adjacencyList.get(u)) {

                        // Skip restricted (no-fly) edges
                        if (e.isRestricted()) continue;

                        capacity[u][e.getTo()] = e.getCapacity();
                    }
                }
                // connect all delivery points to a single super-sink
                for (String id : deliveryArea) {
                    Integer idx = idToIndex.get(id);
                    if (idx == null) {
                        System.out.println("Delivery node " + id + " does not exist.");
                        continue;
                    }

                    if (!indexToNode.get(idx).getType().equals("delivery")) {
                        System.out.println(id + " is not a delivery node.");
                        continue;
                    }

                    // connect delivery node → super sink with unlimited capacity
                    capacity[idx][superSink] = Integer.MAX_VALUE;
                }

                // compute maximum flow from hub to superSink
                return edmondsKarp(capacity, hubIndex, superSink);
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

    //F2

    public void findEfficientFlightRoutes(String startId){
        //Application of Djstrka here
        //convert stringId to index for iterating inside the array
        int start = idToIndex.get(startId);
        Node startNode = indexToNode.get(start);

        //Note: There's no .json in this branch that can let me see the format of the data inside the type attribute, so I left it as DISTRIBUTION
        if (startNode == null || !startNode.getType().equals("DISTRIBUTION")){
            return;
        }



        boolean[] intree = new boolean[adjacencyList.size()];
        Arrays.fill(intree,false);
        int[] mostEnergyEfficient = new int[adjacencyList.size()];
        Arrays.fill(mostEnergyEfficient,Integer.MAX_VALUE);
        int[] parent = new int [adjacencyList.size()];
        Arrays.fill(parent,-1);

        DjkstraResult result = djkstra(intree,mostEnergyEfficient,parent,start);

        System.out.println(result);

    }

    private record DjkstraResult(int[] mostEnergyEfficient, int[] parent){}

    private DjkstraResult djkstra(boolean[] intree, int[] mostEnergyEfficient, int[] parent, int start){
        mostEnergyEfficient[start] = 0;


        int v = start;

        while(!intree[v]){
            intree[v] = true;//already visited

            if (indexToNode.get(v).getType().equals("DISTRIBUTION")){
                break; //end when reached a distribution node. this is the end.
            }


            for (Edge e : adjacencyList.get(v)) {

                if (e.isRestricted()) continue;

                int index_vertex = e.getTo();

                if (!intree[index_vertex] && mostEnergyEfficient[index_vertex] > mostEnergyEfficient[v] + e.getEnergyCost()) {
                    mostEnergyEfficient[index_vertex] = mostEnergyEfficient[v] + e.getEnergyCost();
                    parent[index_vertex] = v;
                }
            }


            //selecting the next vertex

            int dist = Integer.MAX_VALUE;

            for (int i = 0; i < adjacencyList.size(); i++){

                if (!intree[i] && mostEnergyEfficient[i] < dist){

                    dist = mostEnergyEfficient[i];
                    v = i;

                }
            }
        }
        return new DjkstraResult(mostEnergyEfficient,parent);
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
//comm