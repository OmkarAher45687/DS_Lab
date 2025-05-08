//TokenRing.cpp
#include <iostream>
#include <vector>
#include <string>

using namespace std;

int main() {
    int n; // Number of nodes
    cout << "Enter the number of nodes: ";
    cin >> n;

    int token = 0; // Initially, the token is with node 0

    // Display the ring structure: 0 1 2 ... n-1 0
    for (int i = 0; i < n; i++)
        cout << " " << i;
    cout << " " << 0 << endl;

    while (true) {
        int s, r; // Sender and receiver
        string data;

        cout << "Enter sender: ";
        cin >> s;

        cout << "Enter receiver: ";
        cin >> r;

        cout << "Enter Data: ";
        cin.ignore();               // Clears leftover newline character from cin
        getline(cin, data);        // Reads the actual message

        // Token passing from current token to sender
        cout << "Token passing:";
        for (int i = token; (i % n) != s; i = (i + 1) % n) {
            cout << " " << i << "->";
        }
        cout << " " << s << endl;

        // Sender sends data
        cout << "Sender " << s << " sending data: " << data << endl;

        // Forward data through the ring until it reaches the receiver
        for (int i = (s + 1) % n; i != r; i = (i + 1) % n) {
            cout << "Data " << data << " forwarded by " << i << endl;
        }

        // Receiver gets the data
        cout << "Receiver " << r << " received data: " << data << endl;

        // Token stays with sender
        token = s;
    }

    return 0;
}

/*
ubuntu@omkar:~/Desktop/LP_V_DS/Assign_5_Token_ring$ g++ TokenRing.cpp -o TokenRing
ubuntu@omkar:~/Desktop/LP_V_DS/Assign_5_Token_ring$ ./TokenRing
Enter the number of nodes: 5
 0 1 2 3 4 0
Enter sender: 2
Enter receiver: 1
Enter Data: Omkar Here
Token passing: 0-> 1-> 2
Sender 2 sending data: Omkar Here
Data Omkar Here forwarded by 3
Data Omkar Here forwarded by 4
Data Omkar Here forwarded by 0
Receiver 1 received data: Omkar Here
*/
