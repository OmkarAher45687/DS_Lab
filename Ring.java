//Ring.java

import java.util.*; // Importing utility classes like ArrayList, Scanner, Collections

// Main class for Ring Election Algorithm
public class Ring {

    int max_processes;    // Maximum number of processes
    int coordinator;      // Current coordinator process ID
    boolean processes[];  // Array to keep track of process status (up/down)
    ArrayList<Integer> pid; // List to hold participating process IDs during election

    // Constructor to initialize Ring
    public Ring(int max) {
        coordinator = max;    // Set initial coordinator to last process
        max_processes = max;
        pid = new ArrayList<>(); 
        processes = new boolean[max]; // Create boolean array for processes

        for (int i = 0; i < max; i++) {
            processes[i] = true; // Set all processes as "up"
            System.out.println("P" + (i + 1) + " created."); // Display created process
        }
        System.out.println("P" + coordinator + " is the coordinator."); // Show coordinator
    }

    // Display the status of all processes
    void displayProcesses() {
        for (int i = 0; i < max_processes; i++) {
            if (processes[i])
                System.out.println("P" + (i + 1) + " is up."); // Process is active
            else
                System.out.println("P" + (i + 1) + " is down."); // Process is inactive
        }

        // Check if the coordinator is still alive
        if (processes[coordinator - 1]) {
            System.out.println("P" + coordinator + " is the coordinator."); // Coordinator is active
        } else {
            System.out.println("Coordinator P" + coordinator + " is down! Please run election."); // Coordinator down
        }
    }

    // Bring a process back up
    void upProcess(int process_id) {
        if (!processes[process_id - 1]) {
            processes[process_id - 1] = true;
            System.out.println("Process P" + (process_id) + " is up."); // Process brought up
        } else {
            System.out.println("Process P" + (process_id) + " is already up."); // Already active
        }
    }

    // Bring a process down
    void downProcess(int process_id) {
        if (!processes[process_id - 1]) {
            System.out.println("Process P" + (process_id) + " is already down."); // Already inactive
        } else {
            processes[process_id - 1] = false;
            System.out.println("Process P" + (process_id) + " is down."); // Process brought down

            // If coordinator is brought down, start election automatically
            if (coordinator == process_id) {
                System.out.println("Coordinator P" + process_id + " is down. Starting election...");
                startElection();
            }
        }
    }

    // Helper method to print list of participant IDs
    void displayArrayList(ArrayList<Integer> pid) {
        System.out.print("[ ");
        for (Integer x : pid) {
            System.out.print(x + " ");
        }
        System.out.println("]");
    }

    // Election initiated by a particular process
    void initElection(int process_id) {
        if (!processes[process_id - 1]) {
            System.out.println("Process P" + process_id + " is down. Cannot initiate election.");
            return;
        }

        pid.clear(); // Clear the previous list
        int current = process_id - 1; // Start from the initiator

        System.out.println("Election started by process P" + (process_id));

        do {
            if (processes[current]) { // Only active processes participate
                pid.add(current + 1); // Add process ID to list
                System.out.print("Process P" + (current + 1) + " passing list: ");
                displayArrayList(pid); // Show current list
            }
            current = (current + 1) % max_processes; // Move to next process (circular)
        } while (current != (process_id - 1)); // Until list comes back to initiator

        coordinator = Collections.max(pid); // Highest PID becomes coordinator
        System.out.println("Process P" + process_id + " has declared P" + coordinator + " as the coordinator.");

        pid.clear(); // Clear list after election
    }

    // Method to automatically start election if coordinator goes down
    void startElection() {
        pid.clear(); // Clear previous list
        int starter = -1; // Variable to find first active process

        // Find the first active process
        for (int i = 0; i < max_processes; i++) {
            if (processes[i]) {
                starter = i + 1;
                break;
            }
        }

        if (starter == -1) { // No processes are active
            System.out.println("No processes are up! System is down.");
            return;
        }

        initElection(starter); // Start election with the first active process
    }

    // Main function
    public static void main(String[] args) {
        Ring ring = null; // Ring object
        int max_processes, process_id, choice; 
        Scanner sc = new Scanner(System.in); // Scanner for input

        while (true) {
            System.out.println("\n--- Ring Algorithm ---");
            System.out.println("1. Create processes");
            System.out.println("2. Display processes");
            System.out.println("3. Up a process");
            System.out.println("4. Down a process");
            System.out.println("5. Run election algorithm");
            System.out.println("6. Exit Program");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt(); // Read user choice

            switch (choice) {
                case 1:
                    System.out.print("Enter the total number of processes: ");
                    max_processes = sc.nextInt();
                    ring = new Ring(max_processes); // Create processes
                    break;
                case 2:
                    if (ring != null)
                        ring.displayProcesses(); // Show all processes
                    else
                        System.out.println("Please create processes first!");
                    break;
                case 3:
                    if (ring != null) {
                        System.out.print("Enter the process to up: ");
                        process_id = sc.nextInt();
                        ring.upProcess(process_id); // Bring process up
                    } else
                        System.out.println("Please create processes first!");
                    break;
                case 4:
                    if (ring != null) {
                        System.out.print("Enter the process to down: ");
                        process_id = sc.nextInt();
                        ring.downProcess(process_id); // Bring process down
                    } else
                        System.out.println("Please create processes first!");
                    break;
                case 5:
                    if (ring != null) {
                        System.out.print("Enter the process to initiate election: ");
                        process_id = sc.nextInt();
                        ring.initElection(process_id); // Start election manually
                    } else
                        System.out.println("Please create processes first!");
                    break;
                case 6:
                    System.out.println("Exiting Program...");
                    System.exit(0); // End program
                    break;
                default:
                    System.out.println("Invalid choice. Please try again."); // Wrong input
            }
        }
    }
}


/*
ubuntu@omkar:~/Desktop/LP_V_DS/Assign_6_Bully_Algo$ javac *.java
ubuntu@omkar:~/Desktop/LP_V_DS/Assign_6_Bully_Algo$ java Ring

--- Ring Algorithm ---
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 1
Enter the total number of processes: 5
P1 created.
P2 created.
P3 created.
P4 created.
P5 created.
P5 is the coordinator.

--- Ring Algorithm ---
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 2
P1 is up.
P2 is up.
P3 is up.
P4 is up.
P5 is up.
P5 is the coordinator.

--- Ring Algorithm ---
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 4
Enter the process to down: 5
Process P5 is down.
Coordinator P5 is down. Starting election...
Election started by process P1
Process P1 passing list: [ 1 ]
Process P2 passing list: [ 1 2 ]
Process P3 passing list: [ 1 2 3 ]
Process P4 passing list: [ 1 2 3 4 ]
Process P1 has declared P4 as the coordinator.

--- Ring Algorithm ---
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 5
Enter the process to initiate election: 2
Election started by process P2
Process P2 passing list: [ 2 ]
Process P3 passing list: [ 2 3 ]
Process P4 passing list: [ 2 3 4 ]
Process P1 passing list: [ 2 3 4 1 ]
Process P2 has declared P4 as the coordinator.

--- Ring Algorithm ---
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 5
Enter the process to initiate election: 3
Election started by process P3
Process P3 passing list: [ 3 ]
Process P4 passing list: [ 3 4 ]
Process P1 passing list: [ 3 4 1 ]
Process P2 passing list: [ 3 4 1 2 ]
Process P3 has declared P4 as the coordinator.

--- Ring Algorithm ---
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 4
Enter the process to down: 1
Process P1 is down.

--- Ring Algorithm ---
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 4
Enter the process to down: 2
Process P2 is down.

--- Ring Algorithm ---
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 4
Enter the process to down: 3
Process P3 is down.

--- Ring Algorithm ---
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 4
Enter the process to down: 4
Process P4 is down.
Coordinator P4 is down. Starting election...
No processes are up! System is down.

--- Ring Algorithm ---
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 5
Enter the process to initiate election: 2
Process P2 is down. Cannot initiate election.

--- Ring Algorithm ---
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 2
P1 is down.
P2 is down.
P3 is down.
P4 is down.
P5 is down.
Coordinator P4 is down! Please run election.

--- Ring Algorithm ---
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 3
Enter the process to up: 5
Process P5 is up.

--- Ring Algorithm ---
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 5
Enter the process to initiate election: 5
Election started by process P5
Process P5 passing list: [ 5 ]
Process P5 has declared P5 as the coordinator.

--- Ring Algorithm ---
1. Create processes
2. Display processes
3. Up a process
4. Down a process
5. Run election algorithm
6. Exit Program
Enter your choice: 

*/
