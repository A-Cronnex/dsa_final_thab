package org.example;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonFileReader {
    private List<String> NodeNames;
    private List<String> Connections;
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File("src/sample.json"));
        JsonNode node = jsonNode.get("nodes");
        TypeReference<List<Node>> typeRef = new TypeReference<List<Node>>() {};

        List<Node> nodes = objectMapper.readValue(node.traverse(), typeRef);

        System.out.println(nodes.get(0).getId());
        System.out.println(nodes);

    }
}
