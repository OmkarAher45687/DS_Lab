//Bully.java

import java.util.*; // Importing Scanner and other utilities

public class Bully {
    int coordinator; // Holds the current coordinator process number
    int max_processes; // Total number of processes
    boolean processes[]; // Array to store if a process is up (true) or down (false)

    // Constructor to create processes
    public Bully(int max) {
        max_processes = max;
        processes = new boolean[max_processes];
        coordinator = max; // Initially, last process is the coordinator

        System.out.println("Creating processes..");
        for (int i = 0; i < max_processes; i++) {
            processes[i] = true; // Set all processes as up
            System.out.println("P" + (i + 1) + " created");
        }
        System.out.println("Process P" + coordinator + " is the coordinator");
    }

    // Display the status of all processes
    void displayProcesses() {
        boolean allDown = true; // Flag to check if all processes are down

        for (int i = 0; i < max_processes; i++) {
            if (processes[i]) {
                allDown = false; // If any process is up, set allDown to false
                System.out.println("P" + (i + 1) + " is up");
            } else {
                System.out.println("P" + (i + 1) + " is down");
            }
        }

        // If all processes are down
        if (allDown) {
            System.out.println("All processes are down. No active coordinator.");
        } else {
            // If the coordinator is still up
            if (processes[coordinator - 1]) {
                System.out.println("Process P" + coordinator + " is the coordinator");
            } else {
                System.out.println("Coordinator P" + coordinator + " is down. Please run election.");
            }
        }
    }

    // Bring a process up
    void upProcess(int process_id) {
        if (!processes[process_id - 1]) { // If process is down
            processes[process_id - 1] = true; // Make it up
            System.out.println("Process " + process_id + " is now up.");
        } else {
            System.out.println("Process " + process_id + " is already up.");
        }
    }

    // Bring a process down
    void downProcess(int process_id) {
        if (!processes[process_id - 1]) { // If process is already down
            System.out.println("Process " + process_id + " is already down.");
        } else {
            processes[process_id - 1] = false; // Make it down
            System.out.println("Process " + process_id + " is down.");
        }
    }

    // Run the election algorithm
    void runElection(int process_id) {
        
        int newCoordinator = process_id; // Start election from the initiator

        for (int i = process_id; i < max_processes; i++) {
            if (i != (process_id - 1)) { // Don't send message to self
                System.out.println("Election message sent from process " + process_id + " to process " + (i + 1));
            }
            if (processes[i]) { // If process is up
                newCoordinator = i + 1; // Update new coordinator
            }
        }

        coordinator = newCoordinator; // Set the new coordinator
        System.out.println("Process P" + coordinator + " is elected as coordinator.");
    }

    // Main function
    public static void main(String args[]) {
        Bully bully = null; // Object of Bully
        int max_processes = 0, process_id = 0; // Variables
        int choice = 0;
        Scanner sc = new Scanner(System.in); // Scanner for user input

        while (true) { // Infinite loop for menu
            System.out.println("\nBully Algorithm");
            System.out.println("1. Create processes");
            System.out.println("2. Display processes");
            System.out.println("3. Up a process");
            System.out.println("4. Down a process");
            System.out.println("5. Run election algorithm");
            System.out.println("6. Exit Program");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt(); // Take user choice

            switch (choice) {
                case 1:
                    System.out.print("Enter the number of processes: ");
                    max_processes = sc.nextInt(); // Read number of processes
                    bully = new Bully(max_processes); // Create Bully object
                    break;
                case 2:
                    if (bully != null)
                        bully.displayProcesses(); // Display processes
                    else
                        System.out.println("Please create processes first!");
                    break;
                case 3:
                    if (bully != null) {
                        System.out.print("Enter the process number to up: ");
                        process_id = sc.nextInt(); // Read process number
                        bully.upProcess(process_id); // Bring process up
                    } else {
                        System.out.println("Please create processes first!");
                    }
                    break;
                case 4:
                    if (bully != null) {
                        System.out.print("Enter the process number to down: ");
                        process_id = sc.nextInt(); // Read process number
                        bully.downProcess(process_id); // Bring process down
                    } else {
                        System.out.println("Please create processes first!");
                    }
                    break;
                case 5:
                    if (bully != null) {
                        System.out.print("Enter the process number which will perform election: ");
                        process_id = sc.nextInt(); // Read initiator process number
                        if (!bully.processes[process_id - 1]) { // If process is down
                            System.out.println("This process is down. Cannot initiate election.");
                        } else {
                            bully.runElection(process_id); // Run election
                            bully.displayProcesses(); // Display updated processes
                        }
                    } else {
                        System.out.println("Please create processes first!");
                    }
                    break;
                case 6:
                    System.out.println("Exiting Program...");
                    System.exit(0); // Exit program
                    break;
                default:
                    System.out.println("Error in choice. Please try again."); // Invalid choice
                    break;
            }
        }
    }
}



/*
ubuntu@omkar:~/Desktop/LP_V_DS/Assign_6_Bully_Algo$ javac *.java
ubuntu@omkar:~/Desktop/LP_V_DS/Assign_6_Bully_Algo$ java Bully

Bully Algorithm
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 1
Enter the number of processes: 5
Creating processes..
P1 created
P2 created
P3 created
P4 created
P5 created
Process P5 is the coordinator

Bully Algorithm
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 2
P1 is up
P2 is up
P3 is up
P4 is up
P5 is up
Process P5 is the coordinator

Bully Algorithm
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 4
Enter the process number to down: 3
Process 3 is down.

Bully Algorithm
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 2
P1 is up
P2 is up
P3 is down
P4 is up
P5 is up
Process P5 is the coordinator

Bully Algorithm
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 4
Enter the process number to down: 5
Process 5 is down.

Bully Algorithm
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 5
Enter the process number which will perform election: 2
Election message sent from process 2 to process 3
Election message sent from process 2 to process 4
Election message sent from process 2 to process 5
Process P4 is elected as coordinator.
P1 is up
P2 is up
P3 is down
P4 is up
P5 is down
Process P4 is the coordinator

Bully Algorithm
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 4
Enter the process number to down: 4
Process 4 is down.

Bully Algorithm
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 2
P1 is up
P2 is up
P3 is down
P4 is down
P5 is down
Coordinator P4 is down. Please run election.

Bully Algorithm
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 5
Enter the process number which will perform election: 2
Election message sent from process 2 to process 3
Election message sent from process 2 to process 4
Election message sent from process 2 to process 5
Process P2 is elected as coordinator.
P1 is up
P2 is up
P3 is down
P4 is down
P5 is down
Process P2 is the coordinator

Bully Algorithm
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 6 
Exiting Program...
ubuntu@omkar:~/Desktop/LP_V_DS/Assign_6_Bully_Algo$ 
*/
