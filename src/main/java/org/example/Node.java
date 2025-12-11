package org.example;

public class Node {
    private String id;
    private String name;
    private String type;
    private int x;
    private int y;

    public Node(){}

    public Node(String id, String name, String type, int x, int y){
        this.id = id;
        this.name = name;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public void setId(String id) { this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setType(String type) {this.type = type;}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}

    public String getId() {return id;}
    public String getName() {return name;}
    public String getType() {return type;}
    public int getX() {return x;}
    public int getY() {return y;}
}
