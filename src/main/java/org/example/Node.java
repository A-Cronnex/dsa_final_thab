package org.example;

public class Node {
    private String id;
    private String name;
    private String type;
    private int x;
    private int y;
    private boolean charging;

    public Node(){}

    public Node(String id, String name, String type, int x, int y){
        this.id = id;
        this.name = name;
        this.type = type;
        this.x = x;
        this.y = y;
        this.charging = type.equals("charging");
    }

    public void setId(String id) { this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setType(String type) {
        this.type = type;
        this.charging = type.equals("charging");
    }
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public void setCharging(boolean c) {this.charging = c;}

    public String getId() {return id;}
    public String getName() {return name;}
    public String getType() {return type;}
    public int getX() {return x;}
    public int getY() {return y;}
    public boolean isCharging() {return charging;}

    @Override
    public String toString() {
        return "Node{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", isCharging=" + charging +
                '}';
    }
}
