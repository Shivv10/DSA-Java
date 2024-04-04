/**
 * In this file, I have implemented a priority queue which is used to stimulate an emergency room
 * situation in a Hospital. The program reads input from the file and uses a heap to arrange patients
 * according to the highest priority. So the doctor treats patient with the highest priority first.
 * Priority is given to ones with more urgency on the scale of 1 - 9. If there is a conflict between 2
 * patients for same urgency, then the one with less arrival time is given more priority.
 */


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class HospitalEmergencyRoom {
    // Emergency room simulation
    public static void main(String[] args) {
        // Read input from file and create patients
        PriorityQueue priorityQueue = new PriorityQueue(100); // Adjust size as needed

        //Variables that will work as 'clock' for the emergency room
        int currentTime = 0; // Initialize current time to 0
        int doctorFreeTime = 0; // Initialize doctor free time to 0
        int patientNum = 0; // Initialize patient number to 0
        try {
            Scanner scanner = new Scanner(new FileReader("patients.txt")); // Create scanner to read from input file
            String nextLine;
            if (scanner.hasNextLine()) { // Check if there is a next line in the input file
                nextLine = scanner.nextLine(); // Read the next line from the input file
            } else {
                nextLine = null; // Set nextLine to null if there are no more lines in the input file
            }

            while (nextLine != null || !priorityQueue.isEmpty()) { // Continue simulation while there are more patients or the priority queue is not empty
                // Check if there is a new patient arriving
                if (nextLine != null) { // Check if there is a next line in the input file
                    String[] data = nextLine.split(" "); // Split the line into data fields
                    int arrivalTime = Integer.parseInt(data[0]); // Parse arrival time from data field
                    int urgency = Integer.parseInt(data[1]); // Parse urgency from data field
                    int treatmentTime = Integer.parseInt(data[2]); // Parse treatment time from data field

                    // Check if it's time for the patient to arrive
                    if (arrivalTime <= currentTime) { // Check if the patient's arrival time is less than or equal to the current time
                        patientNum++; // Increment patient number by 1
                        Patient patient = new Patient(patientNum, arrivalTime, urgency, treatmentTime); // Create a new patient object with the parsed data
                        priorityQueue.insert(patient); // Insert the patient into the priority queue
                        System.out.println(patient.toString()); // Print the patient's information
                        if (scanner.hasNextLine()) { // Check if there is a next line in the input file
                            nextLine = scanner.nextLine(); // Read the next line from the input file
                        } else {
                            nextLine = null; // Set nextLine to null if there are no more lines in the input file
                        }
                    }
                }

                // Check if doctor is free and there is a patient waiting
                if (doctorFreeTime <= currentTime && !priorityQueue.isEmpty()) { // Check if the doctor is free and there is a patient waiting in the priority queue
                    System.out.println("Doctor is available at time = " + currentTime); // Print that the doctor is available at the current time
                    Patient nextPatient = priorityQueue.peek(); // Get the highest priority patient from the priority queue
                    System.out.println("Patient " + nextPatient.getPatientNum() + " in for treatment at time = " + currentTime + " with urgency = " + nextPatient.getUrgency() + " and treatment time = " + nextPatient.getTime()); // Print information about the patient being treated
                    doctorFreeTime = currentTime + nextPatient.getTime(); // Update doctor free time to current time plus treatment time of the patient being treated
                    priorityQueue.deleteMax(); // Remove the highest priority patient from the priority queue
                } else if (doctorFreeTime == currentTime) { // Check if treatment has finished and doctor is available again at current time
                    System.out.println("Doctor is available at time = " + doctorFreeTime);
                }
                currentTime++;
            }
            System.out.println("Doctor is available at time = " + doctorFreeTime);
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist. Try a different file name !!");
        }
    }
}

class Patient {

    int patientNum;
    int arrivalTime;
    int urgency;
    int time;

    public Patient(int patientNum, int arrivalTime, int urgency, int time) {
        this.patientNum = patientNum;
        this.arrivalTime = arrivalTime;
        this.urgency = urgency;
        this.time = time;
    }

    public int getPatientNum() {
        return patientNum;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getUrgency() {
        return urgency;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Patient " + getPatientNum() + " arrived at time = " + getArrivalTime() + " with urgency = " + getUrgency()
                + " and treatment time = " + getTime();
    }
}

class PriorityQueue {
    Patient[] heap;
    int heapSize;

    public PriorityQueue(int size) {
        heap = new Patient[size];
        heapSize = 0;
    }

    public void insert(Patient priority) {
        if (heapSize < heap.length) {
            heap[heapSize] = priority;
            heapSize++;
            siftUp(heapSize - 1);
        }
    }

    private void siftUp(int index) {
        int i = index;
        while (i > 0 && heap[(i - 1) / 2].getUrgency() < heap[i].getUrgency()) { //While not at root or parent has lower priority
            swap(heap, i, (i - 1) / 2);
            i = (i - 1) / 2; //change index to compare to parent and grandparent
        }

        if (heap[(i - 1) / 2].getUrgency() == heap[i].getUrgency()) { //if parent and child has same urgency, then compare their arrival times
            while (i > 0 && heap[(i - 1) / 2].getArrivalTime() > heap[i].getArrivalTime()) {
                swap(heap, i, (i - 1) / 2);
                i = (i - 1) / 2; //change index to compare to parent and grandparent (according to arrival times)
            }
        }
    }

    public void deleteMax() {
        Patient priority = heap[0];
        heap[0] = heap[heapSize - 1];
        heapSize--;
        siftDown(0);
    }

    private void siftDown(int index) {
        int i = index;

        while (i < heapSize && (2 * i + 2) < heapSize) {
            if (heap[2 * i + 1].getUrgency() > heap[2 * i + 2].getUrgency() && heap[i].getUrgency() < heap[2 * i + 1].getUrgency()) { //key has less priority (urgency) than left child and left child is > right child
                swap(heap, i, 2 * i + 1);
                i = 2 * i + 1;
            } else if (heap[i].getUrgency() < heap[2 * i + 2].getUrgency()) { //right child > left child
                swap(heap, i, 2 * i + 2);
                i = 2 * i + 2;
            } else if (heap[2 * i + 1].getUrgency() == heap[2 * i + 2].getUrgency() || heap[i].getUrgency() == heap[2 * i + 1].getUrgency()
                    || heap[i].getUrgency() == heap[2 * i + 2].getUrgency()) { //key and left child have same urgency or key and right child have same urgency or right and left child have same urgency
                if (heap[2 * i + 1].getArrivalTime() < heap[2 * i + 2].getArrivalTime() && heap[i].getArrivalTime() > heap[2 * i + 1].getArrivalTime()) {//check for their arrival time and less arrival time means more priority
                    swap(heap, i, 2 * i + 1);
                    i = 2 * i + 1;
                } else if (heap[i].getArrivalTime() > heap[2 * i + 2].getArrivalTime()) { //key has more arrival time (less priority) than right child
                    swap(heap, i, 2 * i + 2);
                    i = 2 * i + 2;
                }
            }
        }
    }

    public Patient peek() {
        return heap[0];
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    private void swap(Patient[] arr, int idx1, int idx2) {
        Patient temp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = temp;
    }
}
