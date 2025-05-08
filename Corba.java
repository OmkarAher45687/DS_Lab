//sudo update-alternatives --config java
/*
idlj -fall Calculator.idl
javac *.java CalculatorApp/*.java
java CalculatorServer
# In another terminal:
java CalculatorClient
*/


//Calculator.idl
module CalculatorApp {
  interface Calculator {
    float add(in float a, in float b);
    float subtract(in float a, in float b);
    float multiply(in float a, in float b);
    float divide(in float a, in float b);
  };
};

//CalculatorImpl.java
import CalculatorApp.*;
import org.omg.CORBA.*;

public class CalculatorImpl extends CalculatorPOA {
    public float add(float a, float b) {
        return a + b;
    }

    public float subtract(float a, float b) {
        return a - b;
    }

    public float multiply(float a, float b) {
        return a * b;
    }

    public float divide(float a, float b) {
        if (b == 0) throw new ArithmeticException("Divide by zero");
        return a / b;
    }
}


//CalculatorServer.java
// Import all the classes generated from the IDL file in CalculatorApp package
import CalculatorApp.*;

// Import necessary CORBA classes
import org.omg.CORBA.*;
import org.omg.PortableServer.*;

public class CalculatorServer {
    public static void main(String args[]) {
        try {
            // Initialize the ORB (Object Request Broker) - used for communication
            ORB orb = ORB.init(args, null);

            // Get reference to Root POA (Portable Object Adapter)
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));

            // Activate the POA manager - makes POA ready to handle requests
            rootpoa.the_POAManager().activate();

            // Create object of the Calculator implementation class
            CalculatorImpl calcImpl = new CalculatorImpl();

            // Register the CalculatorImpl object with the ORB
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(calcImpl);

            // Narrow the CORBA object reference to a Calculator type
            Calculator href = CalculatorHelper.narrow(ref);

            // Create a file to save the object reference string
            java.io.FileOutputStream file = new java.io.FileOutputStream("Calculator.ref");

            // Create a writer to write the object reference into the file
            java.io.PrintWriter writer = new java.io.PrintWriter(file);

            // Convert the object reference to string and write it in the file
            writer.println(orb.object_to_string(href));

            // Close the writer
            writer.close();

            // Print that the server is ready
            System.out.println("CalculatorServer ready and waiting...");

            // Run the ORB so it keeps listening for client requests
            orb.run();

        } catch (Exception e) {
            // Print any error that occurs during the process
            System.err.println("ERROR: " + e);
            e.printStackTrace();
        }
    }
}



//CalculatorClient.java
// Import necessary classes generated from the IDL file in CalculatorApp package
import CalculatorApp.*;

// Import necessary CORBA classes for ORB (Object Request Broker)
import org.omg.CORBA.*;

// Import classes for reading files
import java.io.*;

public class CalculatorClient {
    public static void main(String args[]) {
        try {
            // Initialize the ORB (Object Request Broker) for client communication
            ORB orb = ORB.init(args, null);

            // Read the IOR (Interoperable Object Reference) from the file 'Calculator.ref'
            BufferedReader br = new BufferedReader(new FileReader("Calculator.ref"));

            // Read the first line from the file which contains the IOR string
            String ior = br.readLine();

            // Close the file after reading
            br.close();

            // Convert the IOR string into a CORBA object reference
            org.omg.CORBA.Object obj = orb.string_to_object(ior);

            // Narrow the generic CORBA object to a specific Calculator object
            Calculator calc = CalculatorHelper.narrow(obj);

            // Perform addition and print the result
            System.out.println("Addition: " + calc.add(10, 5));

            // Perform subtraction and print the result
            System.out.println("Subtraction: " + calc.subtract(10, 5));

            // Perform multiplication and print the result
            System.out.println("Multiplication: " + calc.multiply(10, 5));

            // Perform division and print the result
            System.out.println("Division: " + calc.divide(10, 5));

        } catch (Exception e) {
            // If an error occurs, print it
            System.err.println("ERROR : " + e);
            e.printStackTrace(System.out);  // Print the stack trace for more detailed error information
        }
    }
}


