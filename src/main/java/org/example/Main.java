package org.example;

import DataStructures.Dictionary;
import DataStructures.LinkedList;
import DataStructures.LinkedListNode;
import DataStructures.Pair;
import org.example.JSON.JsonFileReader;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);
        boolean hasFilledJsonFile = false;
        boolean isLoopActive = true;
        Graph NovaSchilda = null;
        JsonFileReader JSONParser = new JsonFileReader();

        while (isLoopActive) {

            System.out.println("\nWelcome to the drone network program! Please select an option");
            System.out.println("1. Input drone network from JSON file");
            System.out.println("2. Define a no-fly zone");
            System.out.println("3. Extend and modify a drone network");
            System.out.println("4. Check reachability");
            System.out.println("5. Determine efficient flight routes");
            System.out.println("6. Calculate delivery capacity");
            System.out.println("7. Assess and improve network resilience");
            System.out.println("8. Optimize charge station placement");
            System.out.println("9. Drone communication infrastructure");
            System.out.println("10. Exit");
            System.out.print("Choose option: ");

            //  FIX: Read menu input safely without crashing
            String input = myObj.nextLine();
            int option;
            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 10.\n");
                continue;
            }

            // EXIT
            if (option == 10) {
                isLoopActive = false;
                break;
            }

            // OPTION 1: LOAD JSON FILE
            if (option == 1) {
                System.out.println("Please write the name of the file below:");
                String filename = myObj.nextLine(); // FIXED

                System.out.println(filename);

                try {
                    NovaSchilda = new Graph(JSONParser.readFromFile(filename));

                    // Print graph summary
                    System.out.println(NovaSchilda.indexToNode);
                    NovaSchilda.idToIndex.keys().printList();
                    NovaSchilda.idToIndex.values().printList();
                    for (//LinkedList<Edge> row : NovaSchilda.adjacencyList
                        int i = 0; i < NovaSchilda.adjacencyList.size(); i++) {
                        System.out.println("From " + NovaSchilda.indexToNode.get(i).getId() + " " + i);
                        NovaSchilda.adjacencyList.get(i).printList();
                    }

                    hasFilledJsonFile = true;
                } catch (Exception e) {
                    System.out.println(e);
                }

                continue;
            }

            // If user hasn't loaded JSON yet but tries other options
            if (!hasFilledJsonFile) {
                System.out.println("You haven't loaded any JSON network yet!");
                continue;
            }

            // OTHER OPTIONS AFTER JSON LOADED
            switch (option) {
                //B2
                case 2:
                    System.out.println("Define a No-Fly Zone");

                    System.out.print("Enter FROM node ID: ");
                    String fromId = myObj.nextLine();

                    System.out.print("Enter TO node ID: ");
                    String toId = myObj.nextLine();

                    Integer fromIndex = NovaSchilda.idToIndex.get(fromId);
                    Integer toIndex = NovaSchilda.idToIndex.get(toId);

                    if (fromIndex == null || toIndex == null) {
                        System.out.println("Invalid node ID(s). Please check your JSON file.");
                        break;
                    }

                    NovaSchilda.setNoFlyZone(fromIndex, toIndex);
                    System.out.println("Updated edges from " + fromId + ":");
                    NovaSchilda.adjacencyList.get(fromIndex).printList();
                    break;

                case 3:
                    NovaSchilda.modifyAndExtendNetwork();
                    break;
                // F1 --> reachibility
                case 4:
                    System.out.println("Check Reachability");
                    System.out.print("Enter HUB node ID: ");
                    String hubId = myObj.nextLine();

                    boolean reachable = NovaSchilda.allDeliveriesReachable(hubId);

                    if (reachable) {
                        System.out.println("All delivery points are reachable from " + hubId);
                    } else {
                        System.out.println("Some delivery points are NOT reachable from " + hubId);
                    }
                    break;

                case 5:
                    System.out.println("=== Determine Efficient Flight Route ===");
                    System.out.println("FInd efficient Flight Route");
                    String startNode = myObj.nextLine();
                    NovaSchilda.findEfficientFlightRoutes(startNode);
                    break;

                case 6:
                    System.out.println("Calculate Delivery Capacity");

                    System.out.print("Enter HUB ID: ");
                    String hubIdCap = myObj.nextLine();

                    System.out.print("Enter number of delivery points in the urban area: ");
                    int numPoints = Integer.parseInt(myObj.nextLine());

                    ArrayList<String> area = new ArrayList<>();
                    for (int i = 0; i < numPoints; i++) {
                        System.out.print("Enter delivery point ID: ");
                        area.add(myObj.nextLine());
                    }

                    int capacity = NovaSchilda.calculateDeliveryCapacity(hubIdCap, area);
                    System.out.println("Max simultaneous deliveries: " + capacity);
                    break;

                case 7:
                    System.out.println("Assess and improve network resilience");
                    NovaSchilda.minCut();
                    break;

                case 9:
                    System.out.println("Create network");
                    //todo - creating topology
                    String startingNode = myObj.nextLine();

                    Graph NovaShildaCopy = new Graph();

                    for (int i = 0; i < NovaSchilda.indexToNode.size(); i++){
                        NovaShildaCopy.indexToNode.add(NovaSchilda.indexToNode.get(i));
                    }

                    for (int i = 0; i < NovaSchilda.idToIndex.size(); i++){
                        NovaShildaCopy.adjacencyList.add(NovaSchilda.adjacencyList.get(i));
                    }

                    for (int i = 0; i < NovaSchilda.idToIndex.size(); i++){
                        NovaShildaCopy.idToIndex.insert(NovaShildaCopy.indexToNode.get(i).getId(),i);
                    }

                    System.out.println("Do you want to temporarily change the network? If yes, press Y, otherwise press N");

                    String optNew = myObj.nextLine();

                    if(optNew.equals("Y")){
                        System.out.println("Write first id");
                        fromId = myObj.nextLine();
                        System.out.println("Write second id");
                        toId = myObj.nextLine();

                        NovaShildaCopy.editTopography(fromId,toId);
                    } else if (optNew.equals("N")){
                        NovaShildaCopy.communicationInfraestructureForDrones(startingNode);
                    } else {
                        System.out.println("Not an option");
                    }


                default:
                    System.out.println("Feature not implemented yet.");
                    break;


            }

        }

        System.out.println("Program terminated.");
    }
}







///Users/ifrahsanaullah/SEMESTER 3/ALGORITHM & DS/dsa_final_sdi2024/src/main/resources/sample.json