This program simulates an emergency room in a hospital using a priority queue to manage patient arrivals and treatments. Patients are prioritized based on their urgency and treated accordingly by a doctor.

Patient Class:
Each patient has the following attributes:
Unique patient number (integer)
Urgency (integer between 1 and 10)
Treatment time (integer representing minutes)

Public methods include:
getPatientNum(): Returns the patient number.
getUrgency(): Returns the urgency.
getTime(): Returns the treatment time.
toString(): Returns a string representation of the patient information.

Priority Queue Class
Implemented using a heap of Patient objects.
Public methods:
insert(patient): Adds a new patient to the queue.
deleteMax(): Removes and returns the highest priority patient.
peek(): Returns the highest priority patient without removing it.
isEmpty(): Returns true if the queue is empty.
Patients are organized so that the highest priority is at the front of the queue.


Emergency Room Simulation
Patients arrive and are placed in the priority queue based on their urgency.
The doctor treats patients in order of priority.
Input data is read from a file named patients.txt, containing arrival time, urgency, and treatment time for each patient.
A simulation clock tracks the elapsed time in the ER.
Patients are entered into the queue only when the clock time matches their arrival time.
When the doctor is free, the next patient in the queue is called in for treatment.
Statements are printed for each event (patient arrival, patient called in, doctor availability).
Output is ordered by event time.
