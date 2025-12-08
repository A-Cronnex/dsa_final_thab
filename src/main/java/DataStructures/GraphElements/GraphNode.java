package DataStructures.GraphElements;

import DataStructures.MyHashMap;

public class GraphNode {
    public String id;
    public String type;
    public String name;
    public Integer x;
    public Integer y;
    public GraphNode next;
    public Integer arrayPosition;

    public MyHashMap weights;

    public GraphNode(String id, String type, String name, Integer x, Integer y, Integer arrayPosition){
        this.setId(id);
        this.setType(type);
        this.setName(name);
        this.setX(x);
        this.setY(y);
    }

    public void setId(String id) { this.id = id;}
    public void setType(String type) {this.type = type;}
    public void setName(String name) {this.name = name;}
    public void setX(Integer x){this.x = x;}
    public void setY(Integer y){this.y = y;}
    public void setArrayPosition(Integer arrayPosition){this.arrayPosition = arrayPosition;}

    public String getId() {return this.id;}
    public String getType() {return this.type;}
    public Integer getX() {return  this.x;}
    public Integer getY() {return  this.y;}

    public String getNextNodeValue(){
        return this.next.id;
    }
    public GraphNode getNextNode(){
        return this.next;
    }
}
