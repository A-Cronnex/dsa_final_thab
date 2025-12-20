package org.example;

import DataStructures.Dictionary;
import DataStructures.LinkedList;
import DataStructures.LinkedListNode;
import DataStructures.Pair;
import org.example.JSON.JsonFileReader;

import java.lang.reflect.Type;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // subject to change!!!!
        // by aaron: I re-added the code of the menu in the main program because the Prof. Barbara said that in order to access each of the options...
        //we had to make a menu

        Scanner myObj = new Scanner(System.in);
        boolean hasFilledJsonFile = false;
        boolean isLoopActive = true;
        int option = -1;
        JsonFileReader JSONParser = new JsonFileReader();
        while (isLoopActive){

            System.out.println("Welcome to the drone network program! Please select an option");
            System.out.println("1. Input drone network from JSON file \n 2. Define up a fly-no-zone \n " +
                            " 3. Extend and modify a drone network. \n 4. Check reachability \n" +
                            " 5. Determine efficient flight routes \n 6. Calculate delivery capacity \n 7. Assess and improve network resilience " +
                            " 8. Optimize Charge Station Placement for Large-Scale Coverage \n 9. Communication Infraestructure for Drones \n 10. Exit");
            option = myObj.nextInt();

                    if (option == 10){
                        isLoopActive = false;
                    }

                    if (option == 1){
                        //Code for adding the JSON fill
                        System.out.println("Please ingress the name of the file below");
                        try{
                            String filename = myObj.next();
                            Graph NovaSchilda = new Graph(JSONParser.readFromFile(filename));
                            System.out.println(NovaSchilda.indexToNode);
                            NovaSchilda.idToIndex.keys().printList();
                            NovaSchilda.idToIndex.values().printList();
                            for (LinkedList<Edge> row : NovaSchilda.adjacencyList){
                                row.printList();
                            }
                            hasFilledJsonFile = true;

                        }catch (Exception e){
                            System.out.println("File not found!");
                        }

                    } else if ((option > 1 && option <= 10) && !hasFilledJsonFile ) {

                        System.out.println("You haven't ingressed any JSON file yet!");
                    } else if ( hasFilledJsonFile && (option > 1 && option <= 9)){
                        switch (option){
                            case 2:
                                //
                                break;
                            case 3:
                                //
                                break;
                            case 4:
                                //
                                break;
                            case 5:
                                //
                                break;
                            case 6:
                                //
                                break;
                            case 7:
                                //
                                break;
                            case 8:
                                //
                                break;
                            case 9:
                                //
                                break;

                        }
                    } else {
                        System.out.println("The command provided is not in the option list.");
                    }


                }
    }
}