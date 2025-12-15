package org.example;

public class JSONEdge {
    private String from;
    private String to;
    private int energyCost;
    private int capacity;
    private int distance;
    private boolean bidirectional;
    private boolean restricted;

    public JSONEdge(){}

    public JSONEdge(String from, String to, int capacity, int energyCost){
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.energyCost = energyCost;
    }

    public JSONEdge(String from, String to, int distance, int capacity, int energyCost, boolean restricted, boolean bidirectional) {
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.capacity = capacity;
        this.energyCost = energyCost;
        this.restricted = restricted;
        this.bidirectional = bidirectional;
    }

    public void setEnergyCost(int energyCost) {this.energyCost = energyCost;}
    public void setCapacity(int capacity) {this.capacity = capacity;}
    public void setDistance(int distance) {this.distance = distance;}
    public void setBidirectional(boolean bidirectional) {this.bidirectional = bidirectional;}
    public void setRestricted(boolean restricted) {this.restricted = restricted;}
    public void setFrom(String from) {this.from = from;}
    public void setTo(String to) {this.to = to;}

    public String getFrom() {return from;}
    public boolean isRestricted() {return restricted;}
    public boolean isBidirectional() {return bidirectional;}
    public int getDistance() {return distance;}
    public int getCapacity() {return capacity;}
    public int getEnergyCost() {return energyCost;}
    public String getTo() {return to;}
}
