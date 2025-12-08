package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);
        boolean hasFilledJsonFile = false;
        boolean isLoopActive = true;
        int option = -1;
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
                //Code for adding the JSON file
                continue;
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