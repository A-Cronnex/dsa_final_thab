package org.example;

import DataStructures.*;
import DataStructures.Dictionary;
import DataStructures.LinkedList;
import org.example.JSON.JSONEdge;
import org.example.JSON.JSONGraph;
import java.util.*;

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

    //B3 Modifying the network
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

    public void createNewNode(String id, String name, String type, int x, int y){
        System.out.println("Add a new node to the network");
        Node node = new Node(id,name,type,x,y);
        int pos = indexToNode.size();
        idToIndex.insert(node.getId(), pos);
        indexToNode.add(node);
        adjacencyList.add(pos, new LinkedList<>());
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

    // F2 - find efficient flight route from A to B
    public void findEfficientFlightRoutes(String startId, String endId){
        //Application of Dijkstra here
        //convert stringId to index for iterating inside the array

        Integer start = idToIndex.get(startId);
        Integer end = idToIndex.get(endId);
        if (start == null || end == null) return;

        Node startNode = indexToNode.get(start);
        Node endNode = indexToNode.get(end);

        if (startNode.getType().equals("delivery")){
            return;
        }

        if (!endNode.getType().equals("delivery")){
            System.out.println("End point is not a delivery point");
            return;
        }

        if (!startNode.getType().equals("hub")){
            System.out.println("Must insert a hub");
            return;
        }


        DijkstraResult result = dijkstra(start);

        int mostEnergyEfficientResult = result.mostEnergyEfficient[end];


        System.out.println("The shortest path is " + formattedPathDijkstra(result.parent,end));
        System.out.println("The energy cost of this past is: " + mostEnergyEfficientResult);

    }

    private record DijkstraResult(int[] mostEnergyEfficient, int[] parent){}

    private DijkstraResult dijkstra(int start){
        int[] mostEnergyEfficient = new int[adjacencyList.size()];
        Arrays.fill(mostEnergyEfficient,Integer.MAX_VALUE);
        int[] parent = new int [adjacencyList.size()];
        Arrays.fill(parent,-1);

        mostEnergyEfficient[start] = 0;

        BinaryHeap priorityQ = new BinaryHeap();
        priorityQ.insertKey(start,0);

        while(!priorityQ.isEmpty()){

            Pair<Integer, Integer> top = priorityQ.extractMin();
            int vertex = top.first;
            int energyCost = top.second;

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
        return new DijkstraResult(mostEnergyEfficient,parent);
    }

    public String formattedPathDijkstra(int[] parent, int end){
        ArrayList<Integer> path = new ArrayList<>();
        while (end != -1){
            path.add(end);
            end = parent[end];
        }
        String format = "";

        for (int i = path.size() - 1; i>=0 ;  i--){
            format = i > 0? format + indexToNode.get(path.get(i)).getId() + "-> " : format + indexToNode.get(path.get(i)).getId() ;
        }

        return format;
    }

    /*
    public String formattedPathDjkstra(int[] parent,int start, int end){

        ArrayList<Integer> path = new ArrayList<Integer>();
        ArrayList<Integer> reMadePath = createPathRecursively(parent,end,parent[end],path);
        reMadePath.add(start);
        String format = "";

        for (int i = reMadePath.size() - 1; i>=0 ;  i--){
            format = i > 0? format + indexToNode.get(reMadePath.get(i)).getId() + "-> " : format + indexToNode.get(reMadePath.get(i)).getId() ;
        }

        return format;
    }
    */

    /*

    private ArrayList<Integer> createPathRecursively(int[] parent,int vertex, int getParent,ArrayList<Integer> path){
        if (vertex != -1){
            path.add(vertex);
        }
        if (parent[getParent] == -1){
            return path;
        } else {
            getParent = parent[vertex];
            vertex = getParent;
            return (createPathRecursively(parent,getParent,vertex,path));
        }
    }

    why check grandparent for -1, instead of parent? And then no need to add start outside of the recursion?
    in the else, getParent and vertex end up with the same value, it's useless

     */


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


    //F4: Find the minimum set of corridors, by closing which the network can be disconnected
    // Stoer-Wagner algorithm
    // 11.01.2026 assuming undirected graph
    public void minCut(){
        int n = indexToNode.size();
        int[][] adjacencyCopy = new int[n][n];
        for (int i = 0; i < n; ++i){
            for (Edge edge : adjacencyList.get(i)){
                if (edge.isRestricted()) continue;
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
                weightQueue.changeKey(sel.first, 1);
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

    // F5 - optimizing charge station placement
    // Modelling as Uncapacitated Facility Location Problem
    // Solving with greedy k-centers heuristic with 2x optimization ratio
    // returns the min-max distance and the list of placed chargers
    public Pair<Integer, ArrayList<Integer>> placeChargeStations(int k){
        int n = indexToNode.size();
        int[] dist = new int[n];
        ArrayList<Integer> centers = new ArrayList<>();
        for (int i = 0; i < n; i++){
            dist[i] = Integer.MAX_VALUE;
        }


        int initCharge = 0;
        // find out values considering existing charge stations
        for (int i = 0; i < n; ++i){
            if (indexToNode.get(i).isCharging()){
                initCharge++;
                int[] shortestPaths = dijkstra(i).mostEnergyEfficient;
                for (int j = 0; j < n; j++){
                    dist[j] = Math.min(dist[j], shortestPaths[j]);
                }
            }
        }
        k = Math.min(k, n - initCharge);

        int max = 0;
        if (initCharge > 0) max = maxIndex(dist, n);

        for (int i = 0; i < k; ++i){
            centers.add(max);
            int[] shortestPaths = dijkstra(max).mostEnergyEfficient;
            for (int j = 0; j < n; ++j){
                dist[j] = Math.min(dist[j], shortestPaths[j]);
            }

            max = maxIndex(dist, n);
        }

        for(int i = 0; i < centers.size(); i++)
        {
            indexToNode.get(centers.get(i)).setCharging(true);
        }
        return new Pair<>(dist[max], centers);
    }

    static int maxIndex(int[] dist, int n){
        int maxI = 0;
        for (int i = 0; i < n; ++i){
            if (dist[i] > dist[maxI]) maxI = i;
        }
        return maxI;
    }


    //F6

    public void communicationInfrastructureForDrones(String startId){
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

        primResult result = primsAlgorithm(distance,parent,start);

        int totalDistance = result.distance;
        int[] parentArray = result.network;

        for (int i = 0; i<parentArray.length; i++){
            System.out.println("parent of index " + i + " is " + parentArray[i]);
        }

        boolean isMST = isMST(parentArray);

        if (isMST){
            System.out.println(createVisualizationPrim(result.network,start));
        } else {
            System.out.println("Given this vertex and the computed typology, it's not possible to create a communication infraestructure which connect all");
        }

    }

    private record primResult(int distance, int[] network){}

    private primResult primsAlgorithm(int[] distance, int[]parent, int start){

        System.out.println(start);
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
                    distance[index_vertex] = e.getDistance();
                    priorityQ.insertKey(index_vertex,distance[index_vertex]);
                    parent[index_vertex] = vertex;
                }
            }

        }
        return new primResult(totalDistance,parent);
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



    private String createVisualizationPrim(int[]parent, int start){
        String format = "";
        for (int i = 0; i < parent.length ; i++){
            if (i != start){
                format = format + "[" + indexToNode.get(parent[i]).getId() + "]" + " " +  "[" + indexToNode.get(i).getId() + "] \n";
            }
        }
        return format;
    }

    private boolean isMST(int[] parents){
        int counter = 0;

        for (int i : parents){
            if (i == -1){
                counter++;
            }
        }
        return counter == 1;
    }


}