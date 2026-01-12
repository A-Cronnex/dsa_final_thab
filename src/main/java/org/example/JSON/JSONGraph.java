package org.example.JSON;

import org.example.Node;

import java.util.List;

public class JSONGraph {
    private List<Node> nodes;
    private List<JSONEdge> edges;

    public JSONGraph() {
    }

    public List<Node> getNodes() {
        return nodes;
    }
    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
    public List<JSONEdge> getEdges() {
        return edges;
    }
    public void setEdges(List<JSONEdge> edges) {
        this.edges = edges;
    }
}
