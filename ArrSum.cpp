//ArrSum.cpp

#include <iostream>     // For input/output
#include <mpi.h>        // For MPI functions

using namespace std;
int main(int argc, char *argv[]) {
    int rank, size;
    int N = 100;  // Total number of elements
    int local_sum = 0, global_sum = 0;

    // Initialize MPI
    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank); // Get rank of this processor
    MPI_Comm_size(MPI_COMM_WORLD, &size); // Get total number of processors

    // Array initialization (only by rank 0)
    int* arr = nullptr;
    if (rank == 0) {
        arr = new int[N];
        for (int i = 0; i < N; ++i) {
            arr[i] = i + 1; // Fill array with 1 to 100
        }
    }

    // Divide the array among processors
    int local_n = N / size;
    int* local_arr = new int[local_n];

    // Scatter parts of array to each processor
    MPI_Scatter(arr, local_n, MPI_INT, local_arr, local_n, MPI_INT, 0, MPI_COMM_WORLD);

    // Each processor computes sum of its portion
    for (int i = 0; i < local_n; ++i) {
        local_sum += local_arr[i];
    }

    // Show intermediate local sums
    cout << "Processor " << rank << " calculated local sum: " << local_sum << std::endl;

    // Collect local sums at root processor and compute global sum
    MPI_Reduce(&local_sum, &global_sum, 1, MPI_INT, MPI_SUM, 0, MPI_COMM_WORLD);

    // Display total sum only from rank 0
    if (rank == 0) {
        cout << "The total sum is: " << global_sum << std::endl;
        delete[] arr; // Clean up memory
    }

    // Clean up local memory
    delete[] local_arr;

    // Finalize MPI
    MPI_Finalize();
    return 0;
}


/*OUT[PUT
ubuntu@omkar:~/Desktop/LP_V_DS/Assign_3_MPI_Arr$ mpic++ -o ArrSum ArrSum.cpp
ubuntu@omkar:~/Desktop/LP_V_DS/Assign_3_MPI_Arr$ mpirun -np 5 ./ArrSum
Processor 0 calculated local sum: 210
Processor 1 calculated local sum: 610
Processor 2 calculated local sum: 1010
Processor 3 calculated local sum: 1410
Processor 4 calculated local sum: 1810
The total sum is: 5050
ubuntu@omkar:~/Desktop/LP_V_DS/Assign_3_MPI_Arr$ 

*/
