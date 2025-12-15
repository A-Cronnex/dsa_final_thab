package org.example.JSON;

public class JSONEdge {
    private String from;
    private String to;
    private int energy;
    private int capacity;
    private int distance;
    private boolean bidirectional;
    private boolean restricted;

    public JSONEdge(){}

    public JSONEdge(String from, String to, int capacity, int energy){
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.energy = energy;
    }

    public JSONEdge(String from, String to, int distance, int capacity, int energy, boolean restricted, boolean bidirectional) {
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.capacity = capacity;
        this.energy = energy;
        this.restricted = restricted;
        this.bidirectional = bidirectional;
    }

    public void setEnergy(int energy) {this.energy = energy;}
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
    public int getEnergy() {return energy;}
    public String getTo() {return to;}

    @Override
    public String toString() {
        return "JSONEdge{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", energy=" + energy +
                ", capacity=" + capacity +
                ", distance=" + distance +
                ", bidirectional=" + bidirectional +
                ", restricted=" + restricted +
                '}';
    }
}
