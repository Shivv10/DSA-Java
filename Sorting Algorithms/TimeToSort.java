import java.util.*;

/**

 This program times the execution of seven sorting algorithms (insertion sort, bubble sort,
 selection sort, merge sort, quick sort, a hybrid quick sort, and a shell sort) and reports
 on the run time and whether the algorithms actually sorted the list.

 */

public class TimeToSort {
    // Control the testing
    private static final int ARRAY_SIZE = 10000;
    private static final int SAMPLE_SIZE = 300; // The number of trials in each experiment.

    // Control the randomness
    private static final int NUM_SWAPS = ARRAY_SIZE / 2;
    private static Random generator = new Random( System.nanoTime() );

    // Control the base cases for hybrid quick sort:
    private static final int BREAKPOINT = 50;

    // Controls which sort is tried.
    private static final int INSERTION_SORT = 0;
    private static final int BUBBLE_SORT = 1;
    private static final int SELECTION_SORT = 2;
    private static final int MERGE_SORT = 3;
    private static final int QUICK_SORT = 4;
    private static final int HYBRID_QUICK_SORT = 5;
    private static final int SHELL_SORT = 6;

/*********** main and the method it calls *************************/

    /*******************************************************************
     * main
     *
     * Purpose: Print out "bookend" messages and call the method that
     *          does all the testing of the sorting algorithms.
     *
     ******************************************************************/
    public static void main( String[] args ) {
        System.out.println( "\n\n Sorting Test \n" );

        testSorts();

        System.out.println( "\nProcessing ends normally\n" );
    } // end main


    /*******************************************************************
     * testSorts
     *
     * Purpose: Run each sorting algorithm SAMPLE_SIZE times,
     *          on an array of size ARRAY_SIZE with NUM_SWAPS
     *          random swaps performed on it.
     *          Compute the arithmetic mean of the timings for each sorting algorithm.
     *
     *          Print the results.
     *
     ******************************************************************/
    private static void testSorts() {

        // Arrays used in timing experiments (create these arrays once)
        int[] array = new int[ARRAY_SIZE]; // array to be sorted
        long[] sortTime = new long[ SAMPLE_SIZE ]; // store timings for multiple runs
        // of a single sorting method
        // Fill array to be sorted with the numbers 0 to ARRAY_SIZE.
        // (The numbers will be randomly swapped before each sort.)
        fillArray( array );

        // Now run the experiments on all the sorts
        System.out.println("Array size: " + ARRAY_SIZE + "\nNumber of swaps: " + NUM_SWAPS);
        System.out.println("Number of trials of each sort: " + SAMPLE_SIZE );

        // Stats for each run
        System.out.println("\nInsertion sort mean: "
                + tryOneSort( array, sortTime, INSERTION_SORT )
                + " ns" );
        System.out.println("Bubble sort mean: "
                + tryOneSort( array, sortTime, BUBBLE_SORT )
                + " ns" );
        System.out.println("Selection sort mean: "
                + tryOneSort( array, sortTime, SELECTION_SORT )
                + " ns" );
        System.out.println("Merge sort mean: "
                + tryOneSort( array, sortTime, MERGE_SORT )
                + " ns" );
        System.out.println("Quick sort mean: "
                + tryOneSort( array, sortTime, QUICK_SORT )
                + " ns" );
        System.out.println("Hybrid quick sort mean: "
                + tryOneSort( array, sortTime, HYBRID_QUICK_SORT )
                + " ns" );
        System.out.println("Shell sort mean: "
                + tryOneSort( array, sortTime, SHELL_SORT )
                + " ns" );

    } // end testSorts

/*********** methods called by testSorts *************************/

    /*******************************************************************
     * tryOneSort:
     *
     * Purpose: Get an average run time for a sorting algorithm.
     *
     * Methodology: Run the chosen sorting algorithm SAMPLE_SIZE times,
     *          on an array of size ARRAY_SIZE with NUM_SWAPS
     *          random swaps performed on it.
     *          Return the arithmetic mean of the timings.
     *
     ******************************************************************/
    private static double tryOneSort( int[] array, long[] sortTime, int whichSort ) {

        long start, stop, elapsedTime;  // Time how long each sort takes.

        start = stop = 0; // because the compiler complains that they might
        // not have been initialized inside the for-loop

        for ( int i = 0; i < SAMPLE_SIZE; i++ ) {
            randomizeArray( array, NUM_SWAPS );
            if ( whichSort == INSERTION_SORT ) {
                start = System.nanoTime();
                insertionSort( array );
                stop = System.nanoTime();
                checkArray(array, "Insertion sort");
            } else if ( whichSort == BUBBLE_SORT ) {
                start = System.nanoTime();
                bubbleSort(array);
                stop = System.nanoTime();
                checkArray(array, "Bubble sort");

            } else if ( whichSort == SELECTION_SORT ) {
                start = System.nanoTime();
                selectionSort( array );
                stop = System.nanoTime();
                checkArray(array, "Selection sort");

            } else if ( whichSort == MERGE_SORT ) {
                start = System.nanoTime();
                mergeSort(array);
                stop = System.nanoTime();
                checkArray(array, "Merge sort");

            } else if ( whichSort == QUICK_SORT ) {
                start = System.nanoTime();
                quickSort( array );
                stop = System.nanoTime();
                checkArray(array, "Quick sort");

            } else if ( whichSort == HYBRID_QUICK_SORT ) {
                start = System.nanoTime();
                hybridQuickSort( array );
                stop = System.nanoTime();
                checkArray(array, "Hybrid quick sort");
            } else if ( whichSort == SHELL_SORT ) {
                start = System.nanoTime();
                shellSort( array );
                stop = System.nanoTime();
                checkArray(array, "Shell sort");
            }
            elapsedTime = stop - start;
            sortTime[i] = elapsedTime;
        } // end for

        return arithmeticMean( sortTime );
    } // end tryOneSort


/********** sort methods here ********************/

// Insertion sort
private static void insertionSort(int[] array, int start, int end) {
    for (int i = start + 1; i < end; i++) {
        int current = array[i];
        int j = i - 1;
        while (j >= start && array[j] > current) {
            array[j + 1] = array[j]; // Shift elements to the right
            j--;
        }
        array[j + 1] = current; // Insert the current element in the correct position
    }
}

    public static void insertionSort(int[] array) {
        insertionSort(array, 0, array.length);
    }

    // Bubble sort
    public static void bubbleSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1); // Swap adjacent elements if they are in the wrong order
                }
            }
        }
    }

    // Selection sort
    public static void selectionSort(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = findMin(array, i, n); // Find the index of the minimum element in the unsorted part
            swap(array, i, minIndex); // Swap the minimum element with the first element of the unsorted part
        }
    }

    private static int findMin(int[] array, int start, int end) {
        int minIndex = start;
        for (int i = start + 1; i < end; i++) {
            if (array[i] < array[minIndex]) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    // Merge Sort
    public static void mergeSort(int[] array) {
        mergeSort(array, 0, array.length, new int[array.length]);
    }

    private static void mergeSort(int[] array, int start, int end, int[] tempArray) {
        if (end - start < 2) {
            return; // Base case: array of size 1 or empty
        }

        int middle = (start + end) / 2;
        mergeSort(array, start, middle, tempArray); // Sort the left half
        mergeSort(array, middle, end, tempArray); // Sort the right half
        merge(array, start, middle, end, tempArray); // Merge the sorted halves
    }

    private static void merge(int[] array, int start, int middle, int end, int[] tempArray) {
        int i = start; // Index for the left subarray
        int j = middle; // Index for the right subarray
        int k = start; // Index for the merged array

        while (i < middle && j < end) {
            if (array[i] <= array[j]) {
                tempArray[k++] = array[i++]; // Copy the smaller element from the left subarray
            } else {
                tempArray[k++] = array[j++]; // Copy the smaller element from the right subarray
            }
        }

        // Copy the remaining elements from the left subarray
        while (i < middle) {
            tempArray[k++] = array[i++];
        }

        // Copy the remaining elements from the right subarray
        while (j < end) {
            tempArray[k++] = array[j++];
        }

        // Copy the merged elements back to the original array
        for (i = start; i < end; i++) {
            array[i] = tempArray[i];
        }
    }

    // Quick Sort
    public static void quickSort(int[] array) {
        quickSort(array, 0, array.length);
    }

    private static void quickSort(int[] array, int start, int end) {
        if (end - start < 2) {
            return; // Base case: array of size 1 or empty
        } else if (end - start == 2) {
            // Base case: array of size 2, swap if necessary
            if (array[start] > array[start + 1]) {
                swap(array, start, start + 1);
            }
            return;
        }

        int pivotIndex = medianOfThree(array, start, end);
        int pivotPosition = partition(array, start, end, pivotIndex);

        quickSort(array, start, pivotPosition); // Sort the elements before the pivot
        quickSort(array, pivotPosition + 1, end); // Sort the elements after the pivot
    }

    private static int medianOfThree(int[] array, int start, int end) {
        int mid = (start + end) / 2;

        if (array[start] > array[mid]) {
            swap(array, start, mid);
        }

        if (array[start] > array[end - 1]) {
            swap(array, start, end - 1);
        }

        if (array[mid] > array[end - 1]) {
            swap(array, mid, end - 1);
        }

        return mid; // Return the index of the median value
    }

    private static int partition(int[] array, int start, int end, int pivotIndex) {
        int pivotValue = array[pivotIndex];
        swap(array, start, pivotIndex); // Move the pivot value to the beginning

        int i = start + 1;
        for (int j = start + 1; j < end; j++) {
            if (array[j] < pivotValue) {
                swap(array, i, j); // Swap elements smaller than the pivot to the left side
                i++;
            }
        }

        swap(array, start, i - 1); // Place the pivot value at its correct position
        return i - 1; // Return the index of the pivot value
    }

    // Hybrid Quick Sort
    public static void hybridQuickSort(int[] array) {
        hybridQuickSort(array, 0, array.length);
    }

    private static void hybridQuickSort(int[] array, int start, int end) {
        if (end - start < 2) {
            return; // Base case: array of size 1 or empty
        }

        if (end - start < BREAKPOINT) {
            insertionSort(array, start, end); // Use insertion sort for small subarrays
            return;
        }

        int pivotIndex = medianOfThree(array, start, end);
        int pivotPosition = partition(array, start, end, pivotIndex);

        hybridQuickSort(array, start, pivotPosition); // Sort the elements before the pivot
        hybridQuickSort(array, pivotPosition + 1, end); // Sort the elements after the pivot
    }

    // Shell Sort
    public static void shellSort(int[] array) {
        int h = 1;
        while (h < array.length / 3) {
            h = 3 * h + 1;
        }

        while (h >= 1) {
            for (int i = h; i < array.length; i++) {
                int current = array[i];
                int j = i - h;
                while (j >= 0 && array[j] > current) {
                    array[j + h] = array[j]; // Shift elements to the right with a gap of h
                    j -= h;
                }
                array[j + h] = current; // Insert the current element in the correct position
            }
            h /= 3; // Reduce the gap size
        }
    }



/****************** Other miscellaneous methods ********************/

    /*******************************************************************
     * swap
     *
     * Purpose: Swap the items stored in positions i and j in array.
     *
     ******************************************************************/
    private static void swap( int[] array, int i, int j ) {
        int temp = array[ i ];
        array[ i ] = array[ j ];
        array[ j ] = temp;
    } // end swap


    /*******************************************************************
     * isSorted
     *
     * Purpose: Return true if the input array is sorted into
     *          ascending order; return false otherwise.
     *
     * Idea: If every item is <= to the item immediately after it,
     *       then the whole list is sorted.
     *
     ******************************************************************/
    public static boolean isSorted( int[] array ) {
        boolean sorted = true;

        // Loop through all adjacent pairs in the
        // array and check if they are in proper order.
        // Stops at first problem found.
        for ( int i = 1; sorted && (i < array.length); i++ )
            sorted = array[i-1] <=  array[i];
        return sorted;
    } // end method isSorted

    /*******************************************************************
     * checkArray
     *
     * Purpose: Print an error message if array is not
     *          correctly sorted into ascending order.
     *          (If the array is correctly sorted, checkArray does nothing.)
     *
     ******************************************************************/
    private static void checkArray(int[] array, String sortType) {
        if ( !isSorted( array ) )
            System.out.println( sortType + " DID NOT SORT CORRECTLY *** ERROR!!" );
    }

    /*******************************************************************
     * fillArray
     *
     * Purpose: Fills the given array with the numbers 0 to array.length-1.
     *
     ******************************************************************/
    public static void fillArray( int[] array ) {

        for ( int i = 0; i < array.length; i++ ) {
            array[i] = i;
        } // end for

    } // end fillArray

    /*******************************************************************
     * randomizeArray
     *
     * Purpose: Does numberOfSwaps swaps of randomly-chosen positions
     *          in the given array.
     *
     ******************************************************************/
    public static void randomizeArray( int[] array, int numberOfSwaps ) {
        for ( int count = 0; count < numberOfSwaps; count++ ) {
            int i = generator.nextInt( array.length );
            int j = generator.nextInt( array.length );
            swap( array, i, j );
        }
    } // end randomizeArray


    /*******************************************************************
     * arithmeticMean
     *
     * Purpose: Compute the average of long values.
     *          To avoid long overflow, use type double in the computation.
     *
     ******************************************************************/
    public static double arithmeticMean(long data[]) {
        double sum = 0;
        for (int i = 0; i < data.length; i++)
            sum += (double)data[i];
        return sum / (double)data.length;
    } // end arithmeticMean

} // end class
