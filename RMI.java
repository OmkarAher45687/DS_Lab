//ServerIntf.java

import java.rmi.*;

// This is the remote interface.
// It declares the method(s) that can be called remotely by clients.
public interface ServerIntf extends Remote {

    // This method performs addition.
    // It can be called by a client from a different JVM or machine.
    // It must throw RemoteException as it's a remote method.
    double add(double d1, double d2) throws RemoteException;
}


//ServerImpl.java

import java.rmi.*;
import java.rmi.server.*;

// ServerImpl is the actual class that provides the functionality of the remote method.
// It must extend UnicastRemoteObject to make it remotely accessible.
public class ServerImpl extends UnicastRemoteObject implements ServerIntf {

    // Constructor must throw RemoteException
    public ServerImpl() throws RemoteException {
        super(); // Calls the parent constructor of UnicastRemoteObject
    }

    // Implementation of the add method defined in the interface
    public double add(double d1, double d2) throws RemoteException {
        return d1 + d2; // Return the sum of two numbers
    }
}


//Server.java

import java.rmi.*;

// Server class starts the server application and binds the remote object to the RMI registry
public class Server {
    public static void main(String args[]) {
        try {
            // Step 1: Create an instance of the implementation class
            ServerImpl serverImpl = new ServerImpl();

            // Step 2: Server – Binds the serverImpl object to a name  "Server" in the RMI Registry:
            Naming.rebind("Server", serverImpl);

            // Step 3: Confirmation message
            System.out.println("Server is ready...");

        } catch (Exception e) {
            // If any exception occurs, it will be printed here
            System.out.println("Exception: " + e);
        }
    }
}


//Client.java

import java.rmi.*;

// Client connects to the server and calls the remote method
public class Client {
    public static void main(String args[]) {
        try {
            // Step 1: Check if all required arguments are passed (server address, number1, number2)
            if (args.length < 3) {
                System.out.println("Usage: java Client <server> <num1> <num2>");
                return; // Stop execution if arguments are missing
            }

            // Step 2: Build the RMI URL using the server address passed in args[0]
            String ServerURL = "rmi://" + args[0] + "/Server";

            // Step 3: Look up the remote object using the RMI URL
            //Client – Connects to the server using the hostname and performs addition:
            ServerIntf serverIntf = (ServerIntf) Naming.lookup(ServerURL);

            // Step 4: Parse the two numbers from command-line arguments
            double d1 = Double.parseDouble(args[1]);
            double d2 = Double.parseDouble(args[2]);

            // Step 5: Call the remote add method
            double result = serverIntf.add(d1, d2);

            // Step 6: Print the result
            System.out.println("The first number is: " + d1);
            System.out.println("The second number is: " + d2);
            System.out.println("The sum is: " + result);

        } catch (Exception e) {
            // Catch and display any exception that occurs
            System.out.println("Exception: " + e);
        }
    }
}
