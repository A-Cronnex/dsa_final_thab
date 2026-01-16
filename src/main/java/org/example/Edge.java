package org.example;

public class Edge {
    private int to;
    private int energy_cost;
    private int capacity;
    private int distance;
    // does this really need to exist? Or should we simply store 2 edges..?
    private boolean bidirectional; // True if edge is two-way
    private boolean restricted; // True if the corridor is a no-fly zone

    public Edge(){}

    public Edge(int to, int capacity, int energyCost){
        this.to = to;
        this.capacity = capacity;
        this.energy_cost = energyCost;
        this.restricted = false;
        this.bidirectional = false;
    }

    public Edge(int to, int distance, int capacity, int energyCost, boolean restricted, boolean bidirectional) {
        this.to = to;
        this.distance = distance;
        this.capacity = capacity;
        this.energy_cost = energyCost;
        this.restricted = restricted; // no fly zone
        this.bidirectional = bidirectional;
    }
//setters
    public void setEnergyCost(int energyCost) {this.energy_cost = energyCost;}
    public void setCapacity(int capacity) {this.capacity = capacity;}
    public void setDistance(int distance) {this.distance = distance;}
    public void setBidirectional(boolean bidirectional) {this.bidirectional = bidirectional;}
    public void setRestricted(boolean restricted) {this.restricted = restricted;}
    public void setTo(int to) {this.to = to;}
//getters
    public boolean isRestricted() {return restricted;}
    public boolean isBidirectional() {return bidirectional;}
    public int getDistance() {return distance;}
    public int getCapacity() {return capacity;}
    public int getEnergyCost() {return energy_cost;}
    public int getTo() {return to;}




    @Override
    public String toString() {
        return "Edge{" +
                "to=" + to +
                ", energyCost=" + energy_cost +
                ", capacity=" + capacity +
                ", distance=" + distance +
                ", bidirectional=" + bidirectional +
                ", restricted=" + restricted +
                '}';
    }
}
