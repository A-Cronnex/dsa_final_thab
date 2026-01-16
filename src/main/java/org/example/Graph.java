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

        System.out.println("1. add node 2. Extend corridor 3. Modify energy cost");
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

                System.out.println("One of the nodes doesn't exist");
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

        if (option == 3){
            System.out.print("Modify Energy Cost");
            String idFrom = myObj.nextLine();
            String idTo = myObj.nextLine();

            int from = idToIndex.get(idFrom);
            int to = idToIndex.get(idTo);

            Node fromNode = indexToNode.get(from);
            Node toNode = indexToNode.get(to);
            if (fromNode == null || toNode == null){

                System.out.println("One of the nodes doesn't exist");
                return;
            } else {
                int newEnergyCost = myObj.nextInt();
                adjacencyList.get(from).get(to).setEnergyCost(newEnergyCost);
                if (adjacencyList.get(from).get(to).isBidirectional()) {
                    adjacencyList.get(to).get(from).setEnergyCost(newEnergyCost);
                }

            }
        }

    }

    public void addNewCorridor(int from, int to, int capacity, int distance,int energy, boolean restricted, boolean bidirectional){


        adjacencyList.get(from).appendNode(new Edge(to, distance, capacity, energy, restricted, bidirectional));
        if (bidirectional) {
            adjacencyList.get(to).appendNode(new Edge(from, distance, capacity, energy, restricted, bidirectional));
        }
    }

    public void editTopography(String idFrom, String idTo) {
        int from = idToIndex.get(idFrom);
        int to = idToIndex.get(idTo);

        Node fromNode = indexToNode.get(from);
        Node toNode = indexToNode.get(to);

        if (fromNode == null || toNode == null) {
            System.out.println("Neither vertex exists");
            return;
        }

        deleteEdge(from,to);


    }

    private Edge findEdge(int from, int to){
        Edge edge = null;
        int index = 0;
        for (Edge e: adjacencyList.get(from)){
            int toNode = e.getTo();
            if (to == toNode){
                edge = e;
            }
            if (index == adjacencyList.get(from).getSize() - 1) {
                System.out.println("The other vertex is not neighboring the starter vertex (idFrom)");

            }
            index++;
        }
        return edge;
    }

    private void deleteEdge(int from, int to){
       Edge edgeToFind = findEdge(from,to);

       if(edgeToFind != null){
           if (edgeToFind.isBidirectional()){
               Edge bidirectionalEdge = findEdge(to, from);
               adjacencyList.get(to).deleteNode(bidirectionalEdge);
           }
           adjacencyList.get(from).deleteNode(edgeToFind);
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


        int[] mostEnergyEfficient = new int[adjacencyList.size()];
        Arrays.fill(mostEnergyEfficient,Integer.MAX_VALUE);
        int[] parent = new int [adjacencyList.size()];
        Arrays.fill(parent,-1);

        DjkstraResult result = djkstra(mostEnergyEfficient,parent,start);

        System.out.println(formattedPath(result.parent));

    }

    //F6

    public void communicationInfraestructureForDrones(String startId){
        int start = idToIndex.get(startId);
        Node startNode = indexToNode.get(start);

        //Note: There's no .json in this branch that can let me see the format of the data inside the type attribute, so I left it as DISTRIBUTION
        if (startNode == null){
            return;
        }

        int[] distance = new int[adjacencyList.size()];
        Arrays.fill(distance,Integer.MAX_VALUE);
        int[] parent = new int [adjacencyList.size()];
        Arrays.fill(parent,-1);

        primsAlgorithm(distance,parent,start);
    }

    private record DjkstraResult(int[] mostEnergyEfficient, int[] parent){}

    private DjkstraResult djkstra(int[] mostEnergyEfficient, int[] parent, int start){
        mostEnergyEfficient[start] = 0;

        BinaryHeap priorityQ = new BinaryHeap();
        priorityQ.insertKey(start,0);

        while(!priorityQ.isEmpty()){

            Pair<Integer, Integer> top = priorityQ.extractMin();
            int vertex = top.first;
            int energyCost = top.second;


            if (indexToNode.get(vertex).getType().equals("DISTRIBUTION")){
                break; //end when reached a distribution node. this is the end.
            }

            if(energyCost > mostEnergyEfficient[vertex]) continue;


            for (Edge e : adjacencyList.get(vertex)) {

                if (e.isRestricted()) continue;

                int index_vertex = e.getTo();

                if (mostEnergyEfficient[index_vertex] > mostEnergyEfficient[vertex] + e.getEnergyCost()) {
                    mostEnergyEfficient[index_vertex] = mostEnergyEfficient[vertex] + e.getEnergyCost();
                    priorityQ.insertKey(index_vertex,mostEnergyEfficient[index_vertex]);
                    parent[index_vertex] = vertex;
                }
            }


            //selecting the next vertex

        }
        return new DjkstraResult(mostEnergyEfficient,parent);
    }

    private record primResult(int distance, int[] network){}

    private primResult primsAlgorithm(int[] distance, int[]parent, int start){

        boolean[] inTree = new boolean[adjacencyList.size()];
        Arrays.fill(inTree,false);

        distance[start] = 0;
        int totalDistance = 0;
        BinaryHeap priorityQ = new BinaryHeap();
        priorityQ.insertKey(start,0);

        while(!priorityQ.isEmpty()){

            Pair<Integer, Integer> top = priorityQ.extractMin();
            int vertex = top.first;
            int distanceValue = top.second;

            if(inTree[vertex] || distanceValue > distance[vertex]) continue;
            inTree[vertex] = true;

            if (parent[vertex] != -1){
                totalDistance += distance[vertex];
            }


            for (Edge e : adjacencyList.get(vertex)) {

                if (e.isRestricted()) continue;

                int index_vertex = e.getTo();

                if (!inTree[index_vertex] && distance[index_vertex] > e.getDistance()) {
                    distance[index_vertex] = e.getEnergyCost();
                    priorityQ.insertKey(index_vertex,distance[index_vertex]);
                    parent[index_vertex] = vertex;
                }
            }

        }
        return new primResult(totalDistance,parent);
    }

    public String formattedPath(int[] array){

        int index = 0;
        String path = "";
        for (int vertex : array) {
            index++;

            path = index != array.length - 1? path + indexToNode.get(vertex).getId() + "->" : path + indexToNode.get(vertex).getId();
        }

        return path;
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