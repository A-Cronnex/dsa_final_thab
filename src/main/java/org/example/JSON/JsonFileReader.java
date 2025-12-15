package org.example.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonFileReader {

    public JSONGraph readFromFile(String filename){
        ObjectMapper objectMapper = new ObjectMapper();
        JSONGraph parsedGraph = null;
        try {
            parsedGraph = objectMapper.readValue(new File(filename), JSONGraph.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return parsedGraph;
    }
        /*
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(new File("src/sample.json"));
        JsonNode node = jsonNode.get("nodes");
        TypeReference<List<Node>> typeRef = new TypeReference<List<Node>>() {};

        List<Node> nodes = objectMapper.readValue(node.traverse(), typeRef);

        System.out.println(nodes.get(0).getId());
        System.out.println(nodes);
        */
}
