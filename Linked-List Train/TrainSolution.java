//==============================================================
// TrainSolution.java
//
// PURPOSE: Model a train using a linked list.
//
//==============================================================
// Testing code has been left in this solution to give you an
// idea of how incremental development was used to code the
// solution, testing individual classes and methods as they were
// added.
//
// This version does not use dummy nodes. Use of dummy nodes is
// also a valid solution.
//==============================================================

import java.io.*;
import java.util.*;

//==========================================================
// Application (main) class
//
// PURPOSE: Read a file of commands and perform requested
//          operations on a train (doubly-linked list).
//
// ARGUMENTS: None (could also pass the filename as an argument)
//==========================================================

public class TrainSolution {

    public static void main(String[] args){
	    Scanner keyboard;
        String filename;

//	String filename = "trains.txt";

		// Allow user to choose file with keyboard input.
		keyboard = new Scanner( System.in );
		System.out.println( "\nPlease enter the input file name (.txt files only): " );
		filename = keyboard.nextLine();

/* code from testing */
/*	//testing Node class
	TrainNode p = new TrainNode("corn", 80000);
	System.out.println("Node is: " + p); //test toString
	System.out.println("or Node has cargo type: " + p.getCargoType() + " and value: " + p.getValue());

	//testing Train class
	Train testTrain = new Train();
	System.out.println("The new train...");
	testTrain.print();
	testTrain.addCar("corn",80000);
	testTrain.addCar("beans",70000);
	testTrain.addCar("Engine",0);
	System.out.println("Train with 2 engines, corn (80000), beans (70000)...");
	testTrain.print();
	testTrain.addCar("corn",60000);
	testTrain.addCar("corn",40000);
	testTrain.addCar("beans",20000);
	testTrain.addCar("corn",80000);
	testTrain.addCar("beans",50000);
	testTrain.addCar("corn",30000);
	testTrain.addCar("corn",10000);
	System.out.println("Train before dropping has 9 cars...");
	testTrain.print();

	testTrain.deleteFirst(1);
	System.out.println("Drop first 1...");
	testTrain.print();
	testTrain.deleteFirst(6);
	System.out.println("Drop first 6...");
	testTrain.print();
	testTrain.deleteFirst(4);
	System.out.println("Drop first 4...but only 2 to drop...");
	testTrain.print();

	testTrain.deleteLast(1);
	System.out.println("Drop last 1...");
	testTrain.print();
	testTrain.deleteLast(6);
	System.out.println("Drop last 6...");
	testTrain.print();
	testTrain.deleteLast(4);
	System.out.println("Drop last 4...but only 2 to drop...");
	testTrain.print();

	testTrain.deleteCars("corn",1);
	System.out.println("Drop 1 corn...");
	testTrain.print();
	testTrain.deleteCars("beans", 6);
	System.out.println("Drop 6 beans...");
	testTrain.print();
	testTrain.deleteCars("engine", 2);
	System.out.println("Drop 2 engines...");
	testTrain.print();
	testTrain.deleteCars("corn", 7);
	System.out.println("Drop 7 corn...");
	testTrain.print();
*/

		//read and process file
		processFile(filename);

		System.out.println("\nEnd of Processing.");
    }//end main

    //==========================================================
    // processFile
    //
    // PURPOSE: Create a Train, read input file one line at a time
    //          & perform requested operations.
    //
    // PARAMETERS: filename - input file
    //==========================================================
    public static void processFile(String filename){
		BufferedReader inFile;
		String nextLine;
		String[] inTokens;

		System.out.println("processing file....");

		Train theTrain = new Train();

		try{
		    inFile = new BufferedReader(new FileReader(filename));
		    nextLine = inFile.readLine();

		    while (nextLine != null){
				System.out.println("Processing command:  " + nextLine);

				inTokens = nextLine.split(" ");

				if (inTokens[0].equals("PICKUP")){
					int num = Integer.parseInt(inTokens[1]);
					int numCargo=0;
					int numEngine=0;

					for (int i=0; i<num; i++){ //for each car to be added to train
						nextLine = inFile.readLine();
						inTokens = nextLine.split(" ");
						if (inTokens[0].equalsIgnoreCase("engine")){
							theTrain.addCar(inTokens[0], 0);
							numEngine++;
						} else {
				    		theTrain.addCar(inTokens[0], Integer.parseInt(inTokens[1])); //input line has type (String) and value (int)
				    		numCargo++;
						}
					}
					System.out.println(numEngine + " engine and " + numCargo + " cars added to train");
				} else if (inTokens[0].equals("PRINT")){
					theTrain.print();
				} else if (inTokens[0].equals("DROPLAST")){
					int num = theTrain.deleteLast(Integer.parseInt(inTokens[1]));
					System.out.println(num + " car dropped from train");
				} else if (inTokens[0].equals("DROPFIRST")){
					int num = theTrain.deleteFirst(Integer.parseInt(inTokens[1]));
					System.out.println(num + " cars dropped from train");
				} else if (inTokens[0].equals("DROP")){
					int num = theTrain.deleteCars(inTokens[1], Integer.parseInt(inTokens[2]));
					System.out.println(num + " cars dropped from train");
				}

				//System.out.println("train is: "+ theTrain); //testing - print full details of train

				nextLine = inFile.readLine();
		    }
		} catch (IOException e){
		    System.out.println("Error reading file: " + filename);
		}

    }//end processFile

}//end class TrainSolution

//==============================================================
// TrainCar class
//
// PURPOSE: Store information for one train car.
//
// METHODS: - constructor: public TrainCar(String t, int v)
//          - accessors: public String getCargoType()
//                       public int getValue()
//          - toString: public String toString()
//==============================================================
class TrainCar{
	private String carType;
	private int value;

	public TrainCar(String t, int v){
		carType = t;
		if (v >= 0)
		    value = v;
		else
		    value = 0;
	}

	public String getCargoType(){
		return carType;
	}

	public int getValue(){
		return value;
	}

	public String toString(){
		return carType + ", " + value;
	}
}//end class TrainCar

//==============================================================
// TrainNode class
//
// PURPOSE: Store one train car and the links between nodes. To
//          be used in a doubly-linked list.
//
// **The Node class could be nested inside the linked list class**
//
// METHODS: - constructor: public TrainNode(String t, int v)
//          - accessors: public String getCargoType()
//                       public int getValue()
//                       public TrainNode getNext()
//                       public TrainNode getPrev()
//          - mutators: public boolean setNext(TrainNode newNext)
//                      public boolean setPrev(TrainNode newPrev)
//          - toString: public String toString()
//==============================================================
// Note that this would be a good place to use inheritance, with
// a parent class TrainCar and child classes Engine and CargoCar.
// The TrainNode should contain a TrainCar object, which would be
// either an Engine or a CargoCar. When traversing the list,
// polymorphism would be used to select the correct methods for
// the current car.
// (The focus of this question was implementing a linked list,
// and therefore the use of polymorphism was not required. Keep
// these ideas in mind for COMP 2150, Object Orientation.)
//==============================================================
class TrainNode {

	private TrainCar carInfo;
    private TrainNode next;
    private TrainNode prev;

    //==========================================================
    // TrainNode constructor
    //
    // PURPOSE: Create one TrainNode object with given type and value.
    //
    // PARAMETERS:
    //   t  type of cargo
    //   v  value of cargo (must be non-negative)
    //==========================================================
    public TrainNode(String t, int v){
		carInfo = new TrainCar(t, v);
		next = null;
		prev = null;
    }//end constructor

    //==========================================================
    // getCargoType
    //
    // PURPOSE: Return the type of cargo.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   String - the type of cargo
    //==========================================================
    public String getCargoType(){
		return carInfo.getCargoType();
    }//end getCargoType

    //==========================================================
    // getValue
    //
    // PURPOSE: Return the cargo value stored in this node.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   int - the value of cargo in this car
    //==========================================================
    public int getValue(){
		return carInfo.getValue();
    }//end getValue

    //==========================================================
    // getNext
    //
    // PURPOSE: Return the reference to the next Node in a list of Nodes.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   TrainNode - a reference/pointer to the next Node
    //==========================================================
    public TrainNode getNext(){
		return next;
    }//end getNext

    //==========================================================
    // getPrev
    //
    // PURPOSE: Return the reference to the previous Node in a list of Nodes.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   TrainNode - a reference/pointer to the previous Node
    //==========================================================
    public TrainNode getPrev(){
		return prev;
    }//end getPrev

    //==========================================================
    // setNext
    //
    // PURPOSE: Set the reference to the next Node in the list
    //
    // PARAMETERS:
    //   newNext  the new reference
    //
    // RETURNS:
    //   boolean - true if next was set successfully
    //==========================================================
    public boolean setNext(TrainNode newNext){
		next = newNext;
		return true;
    }//end setNext

    //==========================================================
    // setPrev
    //
    // PURPOSE: Set the reference to the previous Node in the list
    //
    // PARAMETERS:
    //   newPrev  the new reference
    //
    // RETURNS:
    //   boolean - true if next was set successfully
    //==========================================================
    public boolean setPrev(TrainNode newPrev){
		prev = newPrev;
		return true;
    }//end setPrev

    //==========================================================
    // toString
    //
    // PURPOSE: Create a String containing the data for this Node.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   String - contains Node data in the format "Type, Value" (e.g. "corn, 80000")
    //==========================================================
    public String toString(){
		return "" + carInfo;
    }//end toString

}//end class TrainNode


//==============================================================
// Train class
//
// PURPOSE: A linked list to store the cars on a train, where the list is doubly-linked.
//
// METHODS: - constructor: public Train()
//          - public void addCar(String t, int v) - add a car to the train
//          - public int deleteFirst(int numToDrop) - delete first numToDrop cargo cars
//          - public int deleteLast(int numToDrop) - delete last numToDrop cargo cars
//          - public int deleteCars(String type, int numToDrop) - delete first numToDrop cars with given type
//          - public int countEngines() - count number of engines in train
//          - public int countCargoCars() - count number of cargo cars in train
//          - public int sumValues() - get total value of all cargo
//          - public void print() - print the cars in the train, and total value
//==============================================================
class Train{

    private TrainNode first;
    private TrainNode last;

    //==========================================================
    // Train constructor
    //
    // PURPOSE: Create a new train, with a single engine.
    //
    // PARAMETERS:
    //   None
    //==========================================================
    public Train(){
		first = new TrainNode("engine",0);
		last = first;
    }//end constructor

    //==========================================================
    // addCar
    //
    // PURPOSE: Add a car with given type and value to the train.
    //          Add engines at front. Add cargo at end.
    //
    // PARAMETERS:
    //   t - the type of cargo
    //   v - value of the cargo
    //==========================================================
	public void addCar(String t, int v){
		TrainNode temp = new TrainNode(t,v);

		if (first == null){
			//add only car to train
			first = temp;
			last = first;
		}
		else if (t.equalsIgnoreCase("engine")){
			//add engines at front
			first.setPrev(temp);
			temp.setNext(first);
			first = temp;
		}
		else {
			//add cargo at end
			last.setNext(temp);
			temp.setPrev(last);
			last = temp;
		}

	}//end addCar

    //==========================================================
    // deleteFirst
    //
    // PURPOSE: Delete the first numToDrop cargo cars.
    //
    // PARAMETERS:
    //   numToDrop - the number of cars to delete from the train
    //
    // RETURN:
    //   int - the number of cars dropped (equal to numToDrop if
    //         at least that many cargo cars existed)
    //==========================================================
	public int deleteFirst(int numToDrop){
		int numDropped = 0;
		TrainNode current = first;
		TrainNode prev = null;

		if (first == null) //train empty
			return 0;
		else {
			//move past engines
			while (current != null && current.getCargoType().equalsIgnoreCase("engine")) {
				prev = current;
				current = current.getNext();
			}

			//delete until hit end of train or have dropped required number
			while (current != null && numDropped < numToDrop){
				if (prev == null){ //drop first car in train
					first.getNext().setPrev(null);
					first = first.getNext();

					//leave prev as null, in case need to delete another car at start of train
				} else if (current.getNext() == null){ //drop last car in train (can't be only car or would have been dropped above)
					last = current.getPrev();
					if (last != null) //should always be true
						last.setNext(null);
					//no need to change prev... current will be null next and end the loop
				} else { //drop a middle car
					current.getPrev().setNext(current.getNext());
					current.getNext().setPrev(current.getPrev());

					//should be no change to prev
				}
				numDropped++;

				current = current.getNext(); //haven't changed current so it is still linked to a car left on the train
			}
		}
		return numDropped;

	}//end deleteFirst

    //==========================================================
    // deleteLast
    //
    // PURPOSE: Delete the last numToDrop cargo cars.
    //
    // PARAMETERS:
    //   numToDrop - the number of cars to delete from the train
    //
    // RETURN:
    //   int - the number of cars dropped (equal to numToDrop if
    //         at least that many cargo cars existed)
    //==========================================================
	public int deleteLast(int numToDrop){
		int numDropped = 0;
		TrainNode current = last;
		TrainNode next = null;

		if (first == null) //train empty
			return 0;
		else {
			//delete until hit end of train or have dropped required number
			while (current != null && !(current.getCargoType().equalsIgnoreCase("engine")) && numDropped < numToDrop){
				if (current.getPrev() == null){ //drop first car in train - should only happen if this is the only car
					first.getNext().setPrev(null);
					first = first.getNext();

				} else if (current.getNext() == null){ //drop last car in train (can't be only car or would have been dropped above)
					last = current.getPrev();
					if (last != null) //should always be true
						last.setNext(null);

				} else { //drop a middle car - should never happen
					current.getPrev().setNext(current.getNext());
					current.getNext().setPrev(current.getPrev());

					//should be no change to next
				}
				numDropped++;

				current = current.getPrev(); //move backward through train - haven't changed current so it is still linked to a car left on the train
			}
		}
		return numDropped;

	}//end deleteLast

    //==========================================================
    // deleteCars
    //
    // PURPOSE: Delete the first numToDrop cars with the given type.
    //
    // PARAMETERS:
    //   type - the type of car to drop
    //   numToDrop - the number of cars to delete from the train
    //
    // RETURN:
    //   int - the number of cars dropped (equal to numToDrop if
    //         at least that many cargo cars of the given type existed)
    //==========================================================
	public int deleteCars(String type, int numToDrop){
		int numDropped = 0;
		TrainNode current = first;
		TrainNode prev = null;

		if (first == null) //train empty
			return 0;
		else {
			//delete until hit end of train or have dropped requested number
			while (current != null && numDropped < numToDrop){
				if (current.getCargoType().equalsIgnoreCase(type)){
					if (current == first && first == last) { //drop only car in train
						first = null;
						last = null;
					}
					else if (prev == null){ //drop first car in train
						first.getNext().setPrev(null);
						first = first.getNext();
						//leave prev as null, in case need to delete another car at start of train
					}
					else if (current.getNext() == null){ //drop last car in train (can't be only car or would have been dropped above)
						last = current.getPrev();
						if (last != null) //should always be true
							last.setNext(null);
						//no need to change prev... current will be null next and end the loop
					}
					else { //drop a middle car
						current.getPrev().setNext(current.getNext());
						current.getNext().setPrev(current.getPrev());

						//should be no change to prev
					}
					numDropped++;
				}
				else
					prev = current; //step ahead if don't delete anything

				current = current.getNext(); //haven't changed current so it is still linked to a car left on the train
			}
		}
		return numDropped;

	}//end deleteCars

    //==========================================================
    // countEngines
    //
    // PURPOSE: Return the number of engines on the train.
    //
    // PARAMETERS:
    //   none
    //
    // RETURN:
    //   int - the number of engines on the train
    //==========================================================
	public int countEngines(){
		int count = 0;
		TrainNode current = first;
		if (first == null)
			return 0;
		else {
			while (current != null && current.getCargoType().equalsIgnoreCase("engine")){ //all engines are added at start of train
				count++;
				current = current.getNext();
			}
		}
		return count;
	}

    //==========================================================
    // countCargoCars
    //
    // PURPOSE: Return the number of non-engines on the train.
    //
    // PARAMETERS:
    //   none
    //
    // RETURN:
    //   int - the number of non-engines on the train
    //==========================================================
	public int countCargoCars(){
		int count = 0;
		TrainNode current = first;
		if (first == null)
			return 0;
		else {
			while (current != null) {
				if (!(current.getCargoType().equalsIgnoreCase("engine")))
					count++;
				current = current.getNext();
			}
		}
		return count;
	}

    //==========================================================
    // sumValues
    //
    // PURPOSE: Calculate and return the total value of cargo on the train.
    //
    // PARAMETERS:
    //   none
    //
    // RETURN:
    //   int - the total value of the cargo on the train
    //==========================================================
	public int sumValues(){
		int total = 0;
		TrainNode current = first;
		if (first == null)
			return 0;
		else {
			while (current != null){
				total += current.getValue();
				current = current.getNext();
			}
		}
		return total;
	}//end sumValues

    //==========================================================
    // print
    //
    // PURPOSE: Print information about the train (summary of
    //          number of cars, total cargo value, list of all
    //          cars on train (type only).
    //
    // PARAMETERS:
    //   none
    //==========================================================
	public void print(){
		System.out.println("Total number of engines:  " + countEngines() + ", Total number of cargo cars:  " + countCargoCars() + ", Total value of cargo:  $" + sumValues());
		System.out.print("The cars on the train are:  ");
		TrainNode current = first;

		//print list of cars
		while (current != null){
			System.out.print(current.getCargoType());
			current = current.getNext();
			if (current != null)
				System.out.print(" - ");
		}
		System.out.println();
	}//end print

    //==========================================================
    // print
    //
    // PURPOSE: Get information about the train (summary of
    //          number of cars, total cargo value, list of all
    //          cars on train (type and value).
    //
    // RETURN:
    //   String - formatted for printing
    //==========================================================
	public String toString(){
		String output = "Total number of engines:  " + countEngines() + ", Total number of cargo cars:  " + countCargoCars() + ", Total value of cargo:  $" + sumValues();
		output += "\nThe cars on the train are:  ";
		TrainNode current = first;

		//print list of cars
		while (current != null){
			output += "(" + current + ")";
			current = current.getNext();
			if (current != null)
				output += " - ";
		}
		return output + "\n";
	}//end toString

}//end class Train


