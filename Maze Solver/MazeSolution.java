//==============================================================
// MazeSolution.java
//
//
// PURPOSE: Given a maze, use two searches to find a path from
//          start to finish. Print the maze and a list of the
//          positions along a found path.
//==============================================================
import java.util.*;
import java.io.*;

enum SquareType{START, FINISH, PATH, WALL;}

public class MazeSolution {

    public static void main( String[] args ) {

		solveAMaze();
		System.out.println("\nProgram terminated normally.");

    } // end main

	public static void solveAMaze(){
	    Scanner keyboard;
        String filename;

		Maze myMaze;

		// Allow user to choose file with keyboard input.
		keyboard = new Scanner( System.in );
		System.out.println( "\nPlease enter the input file name (.txt files only): " );
		filename = keyboard.nextLine();

		System.out.println("\nProcessing " + filename + "...\n");

		//build maze
		myMaze = new Maze(filename);

		System.out.println("The initial maze is:");
		System.out.println(myMaze);

		//search for path using a depth-first search
		boolean stackSolved = myMaze.solveWithStack();

		//print result of search
		if (stackSolved){
			System.out.println("The path found using a stack is:");
			System.out.println(myMaze.getMaze());
			System.out.print("Path from start to finish is: ");
			System.out.println(myMaze.getPath());
		}
		else{
			System.out.println("No path found when searching with stack.");
		}

		//reset maze to try another search
		myMaze.resetMaze();

		//search for path using a breadth-first search
		boolean queueSolved = myMaze.solveWithQueue();

		//print result of search
		if (queueSolved){
			System.out.println("\nThe path found using a queue is:");
			System.out.println(myMaze.getMaze());
			System.out.print("Path from start to finish is: ");
			System.out.println(myMaze.getPath());
		}
		else{
			System.out.println("No path found when searching with queue.");
		}

	}//end solveAMaze

}//end MazeSolution class


//==============================================================
// Position class
//
// PURPOSE: Store the properties for one position in a maze.
//
// METHODS: - constructor: public Position(int r, int c, SquareType t)
//          - public void setVisited(boolean v)
//          - public boolean isVisited() - returns true if position has been visited
//          - public int getRow()
//          - public int getColumn()
//          - public void setPrevious(Position p)
//          - public Position getPrevious()
//          - public SquareType getType()
//          - public String getCoords() - gives coordinates, as "(r, c)"
//          - public String getSymbol() - gives * if position is marked as visited
//          - public String toString() - gives . for path, whether visited or not
//
//==============================================================
class Position{
	private int row; //coordinates in maze
	private int col;
	private SquareType typeOfSquare; //type - path, wall, start, or finish
	private boolean visited; //has the position been explored?
	private Position previous; //the position from which we arrived at this position

	public Position(int r, int c, SquareType t){
		row = r;
		col = c;
		typeOfSquare = t;
		visited = false;
		previous = null;
	}//end constructor

	public void setVisited(boolean v){
		visited = v;
	}

	public boolean isVisited(){
		return visited;
	}

	public int getRow(){
		return row;
	}

	public int getColumn(){
		return col;
	}

	public void setPrevious(Position p){
		previous = p;
	}

	public Position getPrevious(){
		return previous;
	}

	public SquareType getType(){
		return typeOfSquare;
	}

	public String getCoords(){
		return "(" + row + ", " + col + ")";
	}

	//gives * for path if visited
	public String getSymbol(){
		String output = "";
		if (typeOfSquare == SquareType.START)
			output += "S";
		else if (typeOfSquare == SquareType.FINISH)
			output += "F";
		else if (typeOfSquare == SquareType.PATH && !visited)
			output += ".";
		else if (typeOfSquare == SquareType.PATH && visited)
			output += "*";
		else if (typeOfSquare == SquareType.WALL)
			output += "#";
		else
			output += "?";  //should never see a ?
		return output;
	}

	//gives . for path, whether visited or not
	public String toString(){
		String output = "";
		if (typeOfSquare == SquareType.START)
			output += "S";
		else if (typeOfSquare == SquareType.FINISH)
			output += "F";
		else if (typeOfSquare == SquareType.PATH)
			output += ".";
		else if (typeOfSquare == SquareType.WALL)
			output += "#";
		else
			output += "?";  //should never see a ?
		return output;
	}
}//end class Position

//=====================================================================
//=====================================================================
// NOTE: This solution implements the stack with an array and the queue
//       with a linked list. Alternate solution: implement stack with
//       linked list and queue with array.
//=====================================================================
//=====================================================================

//==============================================================
// Stack class
//
// PURPOSE: ADT Stack implemented using an array and storing Positions.
//
// METHODS: - constructor: public Stack(int stackSize) - create an empty stack with given array size
//          - public boolean isEmpty()
//          - public boolean isFull()
//          - public void push( Position newItem )
//          - public Position pop()
//          - public Position top() - sometimes called peek
//          - public String toString()
//
//==============================================================
class Stack {
    private Position[] stackArray;
    private int top; // Position of top item in the stack.
                     // top is -1 if the stack is empty.

    // Create a new empty stack
    public Stack( int stackSize ) {
		stackArray = new Position[ stackSize ];
		top = -1;
    } // end Stack constructor

    public boolean isEmpty() {
		return top == -1;
    } // isEmpty

    public boolean isFull() {
		return top == stackArray.length - 1;
    } // end isFull

    public void push( Position newItem ) {
		if ( !isFull() ) {
		    top++;
		    stackArray[top] = newItem;
		} else {
		    System.out.println( "***ERROR in Stack push(): stack is full" );
		}
    } // end push

    public Position pop() {
		Position result = null;

		if ( !isEmpty() ) {
		    result = stackArray[top];
		    top--;
		} else {
		    System.out.println( "***ERROR in Stack pop(): stack is empty" );
		}

		return result;
    } // end pop

    public Position top() {
		Position result = null;

		if ( !isEmpty() ) {
		    result = stackArray[top];
		} else {
		    System.out.println( "***ERROR in Stack top(): "
					+ "stack is empty" );
		}

		return result;
    } // end top

    public String toString() {
		String result = "[";
		for ( int i = 0; i < top; i++ ) {
		    result += " " + stackArray[i] + "\n";
		} // end for
		if ( top != -1 ) {
		    result += " " + stackArray[top];
		}
		result += " >";
		return result;
    } // end toString

} // end class Stack


//==============================================================
// Queue class
//
// PURPOSE: ADT Queue implemented using a circular linked list
//          with no dummy node and only a pointer (called end)
//          that points to the last node (the most recently-added node).
//          The queue stores Positions, one per node.
//
// METHODS: - constructor: public Queue() - create an empty queue
//          - public boolean isEmpty()
//          - public void enqueue( Position newItem )
//          - public Position dequeue()
//          - public Position front() - sometimes called peek
//          - public String toString()
//
//==============================================================
class Queue {

    //=======================================================
    // A node in the circular linked list.
    //=======================================================
    private class Node {
		public Position item;
		public Node next;

		public Node( Position newItem, Node newNext ) {
		    item = newItem;
		    next = newNext;
		} // end Node constructor

    } // end class Node

    //=======================================================
    // Back to the Queue class
    //=======================================================

    private Node end; // A pointer to the newest (last) node in the queue

    public Queue() {
		end = null;
    } // end Queue constructor

    /***************************************************************
     * isEmpty
     ***************************************************************/
    public boolean isEmpty() {
		return end == null;
    } // end isEmpty

    /***************************************************************
     * enqueue
     *
     * PURPOSE: Add a new item to the end of the queue.
     *
     * In a circular linked list with an end pointer,
     * we need to add a new node such that
     * (1) the old last node's next pointer (if there were any nodes)
     *     points at the new node,
     * (2) the new node points at the first node with its next pointer
     *     (which means that the new node must point at itself,
     *     if there weren't any nodes before), and
     * (3) end points at the new node.
     *
     ***************************************************************/
    public void enqueue( Position newItem ) {
		if ( !isEmpty() ) {
		    // Create a new node with a pointer to first node in
		    // queue and make the old last node's next pointer
		    // point to the new node.
		    end.next = new Node( newItem, end.next );
		    // end should now point to the new node.
		    end = end.next;
		} else {
		    // New node is the only node, so it should point to itself.
		    // Can't create the new node with a pointer to itself.
		    // So, first create the new node with a null next pointer
		    // and make end point at the new node.
		    end = new Node( newItem, null );
		    // Now make the new node point at itself.
		    end.next = end;
		}
    } // end enqueue

    /***************************************************************
     * dequeue
     *
     * PURPOSE: Return the value in the first (oldest) node in
     *          the list and remove that node from the list.
     *
     * In a circular linked list with an end pointer,
     * leave should return the node that is "after"
     * the node pointed at by end. End remains pointing
     * at the last node, except in the special case below.
     *
     * Special case: There might be only ONE node in
     * the list, in which case it has to be returned by
     * leave and end must be made null since the list is
     * now empty.
     *
     ***************************************************************/
    public Position dequeue() {
		Position result = null;

		if ( !isEmpty() ) {
		    result = end.next.item;
		    if ( end.next != end ) {
			// We are not deleting the only node,
			// so point the last node at the second node
			// to delete the first node
			end.next = end.next.next;
		    } else {
			// We are deleting the only node.
			end = null;
		    }
		} else {
		    System.out.println( "***ERROR in Queue leave(): queue is empty" );
		}

		return result;
    } // end dequeue

    /***************************************************************
     * front
     ***************************************************************/
    public Position front() {
		Position result = null;

		if ( !isEmpty() ) {
		    result = end.next.item;
		} else {
		    System.out.println( "***ERROR in Queue front(): queue is empty" );
		}

		return result;
    } // end front

    /***************************************************************
     * toString
     ***************************************************************/
    public String toString() {
		String result = "<";
		Node curr;

		if ( end != null ) {
		    curr = end.next; //first node
		    while ( curr != end ) {
				result += " " + curr.item + "\n";
				curr = curr.next;
		    } // end while
		    if ( end != null )
				result += " " + curr.item;
		}
		result += " <";

		return result;
    } // end toString

} // end class Queue

//==============================================================
// Maze class
//
// PURPOSE: Store a maze consisting of paths and walls, with a
//          start and finish.
//
// METHODS: - constructor: public Maze(String filename) - build a maze from a text file
//          - public String toString() - return String representation of the maze (without * marking the path)
//          - public String getMaze() - return String representation of the maze (with * marking the path)
//          - public Position getFinish()
//          - public Position getStart()
//          - public void resetMaze() - reset all positions to unvisited and set the maze as unsolved
//          - public boolean solveWithStack()
//          - public boolean solveWithQueue()
//          - private void cleanUpMaze() - reset path positions as unvisited if they are not on the path from start to finish
//          - public String getPath() - return coordinates along path from start to finish
//
// ASSUMPTION:  There is exactly one start and exactly one finish.
//
//==============================================================
class Maze{
	int numRows;
	int numCols;
	Position[][] theMaze;
	boolean solved;  //true if a path has been found, false otherwise

    //==========================================================
    // constructor
    //
    // PURPOSE: Create a maze from a text file, where the first row
    //          contains the number of rows and number of columns
    //          (separated by a space), and following rows contain
    //          a row in the maze.
    //
    //==========================================================
	public Maze(String filename){
		BufferedReader inFile;
		String nextLine;
		String[] tokens;

		try{
		    inFile = new BufferedReader(new FileReader(filename));

		    nextLine = inFile.readLine();
			tokens = nextLine.split(" ");
			numRows = Integer.parseInt(tokens[0].trim());
			numCols = Integer.parseInt(tokens[1].trim());

			theMaze = new Position[numRows][numCols];

		    for (int r=0; r<numRows; r++){
				nextLine = inFile.readLine(); //read a row of the maze
				for (int c=0; c<numCols; c++){
					char pos = nextLine.charAt(c);
					if (pos == '.'){
						theMaze[r][c] = new Position(r, c, SquareType.PATH);
					}
					else if (pos == '#'){
						theMaze[r][c] = new Position(r, c, SquareType.WALL);
					}
					else if (pos == 'S'){
						theMaze[r][c] = new Position(r, c, SquareType.START);
					}
					else if (pos == 'F'){
						theMaze[r][c] = new Position(r, c, SquareType.FINISH);
					}
					else
						theMaze[r][c] = null; //invalid symbol - the position should already be null anyway
				}
		    }
		} catch (IOException e){
		    System.out.println("\nError reading file: " + filename);
		}

		solved = false;

	}//end constructor

    //==========================================================
    // toString
    //
    // PURPOSE: Get a text representation of the maze, with all
    //          path positions (visited or not) marked as '.'.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   String - text representation of the maze
    //==========================================================
	public String toString(){
		String output = "";

		for (int r=0; r<numRows; r++){

			for (int c=0; c<numCols; c++){
				output += theMaze[r][c];  //toString gives a single char
			}
			output += "\n";
		}

		return output;
	}//end toString

    //==========================================================
    // getMaze
    //
    // PURPOSE: Get a text representation of the maze, with '*'
    //          marking the path.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   String - text representation of the maze
    //==========================================================
	public String getMaze(){
		String output = "";

		for (int r=0; r<numRows; r++){

			for (int c=0; c<numCols; c++){
				output += theMaze[r][c].getSymbol();
			}
			output += "\n";
		}

		return output;
	}//end toString

    //==========================================================
    // getFinish
    //
    // PURPOSE: Get a pointer to the finish position.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   Position - a pointer to the finish (we assumed there was only one finish)
    //==========================================================
	public Position getFinish(){
		Position theEnd = null;
		boolean found = false;
		for (int r=0; r<numRows && !found; r++){
			for (int c=0; c<numCols && !found; c++){
				if(theMaze[r][c].getType() == SquareType.FINISH){
					theEnd = theMaze[r][c];
					found = true;
				}
			}
		}
		return theEnd;
	}//end getFinish

    //==========================================================
    // getStart
    //
    // PURPOSE: Get a pointer to the start position.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   Position - a pointer to the start (we assumed there was only one start)
    //==========================================================
	public Position getStart(){
		Position theStart = null;
		boolean found = false;
		for (int r=0; r<numRows && !found; r++){
			for (int c=0; c<numCols && !found; c++){
				if(theMaze[r][c].getType() == SquareType.START){
					theStart = theMaze[r][c];
					found = true;
				}
			}
		}
		return theStart;
	}//end getStart

    //==========================================================
    // resetMaze
    //
    // PURPOSE: Set all positions to unvisited and set the maze
    //          to unsolved.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   None
    //==========================================================
	public void resetMaze(){
		for (int r=0; r<numRows; r++){
			for (int c=0; c<numCols; c++){
				theMaze[r][c].setVisited(false);
				theMaze[r][c].setPrevious(null);
			}
		}
		solved = false;
	}//end resetMaze

    //==========================================================
    // solveWithStack
    //
    // PURPOSE: Search for a path from start to finish, using a
    //          depth-first search. After this method runs,
    //          previous pointers can be used to find the path
    //          from finish to start. After this method runs,
    //          only positions on the path will be marked as
    //          visited.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   boolean - true if a path is found
    //==========================================================
	public boolean solveWithStack(){
		Position current = null;
		boolean done = false;

		Stack toTest = new Stack(numRows*numCols);

		Position s = getStart();
		Position f = getFinish();

		toTest.push(s);
		s.setVisited(true);

		while (!done && !toTest.isEmpty()){
			current = toTest.pop();  //get a position from which to search
			if (current == f){
				done = true;
			}
			else{
				int c = current.getColumn();
				int r = current.getRow();
				//try going left
				if (c>0 && theMaze[r][c-1].getType() != SquareType.WALL && !theMaze[r][c-1].isVisited()){
					theMaze[r][c-1].setVisited(true);
					theMaze[r][c-1].setPrevious(current);
					toTest.push(theMaze[r][c-1]);
				}
				//try going up
				if (r>0 && theMaze[r-1][c].getType() != SquareType.WALL && !theMaze[r-1][c].isVisited()){
					theMaze[r-1][c].setVisited(true);
					theMaze[r-1][c].setPrevious(current);
					toTest.push(theMaze[r-1][c]);
				}

				//try going right
				if (c<numCols-1 && theMaze[r][c+1].getType() != SquareType.WALL && !theMaze[r][c+1].isVisited()){
					theMaze[r][c+1].setVisited(true);
					theMaze[r][c+1].setPrevious(current);
					toTest.push(theMaze[r][c+1]);
				}

				//try going down
				if (r<numRows-1 && theMaze[r+1][c].getType() != SquareType.WALL && !theMaze[r+1][c].isVisited()){
					theMaze[r+1][c].setVisited(true);
					theMaze[r+1][c].setPrevious(current);
					toTest.push(theMaze[r+1][c]);
				}
			}
		}//end while

		if (current == f)
			solved = true;

		//reset path positions as unvisited if not on path from start to finish
		cleanUpMaze();

		return solved;

	}//end solveWithStack


    //==========================================================
    // solveWithQueue
    //
    // PURPOSE: Search for a path from start to finish, using a
    //          breadth-first search. After this method runs,
    //          previous pointers can be used to find the path
    //          from finish to start.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   boolean - true if a path is found
    //==========================================================
	public boolean solveWithQueue(){
		Position current = null;
		boolean done = false;

		Queue toTest = new Queue();

		Position s = getStart();
		Position f = getFinish();

		toTest.enqueue(s);
		s.setVisited(true);

		while (!done && !toTest.isEmpty()){
			current = toTest.dequeue();  //get a position from which to search
			if (current == f){
				done = true;
			}
			else{
				int c = current.getColumn();
				int r = current.getRow();
				//try going left
				if (c>0 && theMaze[r][c-1].getType() != SquareType.WALL && !theMaze[r][c-1].isVisited()){
					theMaze[r][c-1].setVisited(true);
					theMaze[r][c-1].setPrevious(current);
					toTest.enqueue(theMaze[r][c-1]);
				}
				//try going up
				if (r>0 && theMaze[r-1][c].getType() != SquareType.WALL && !theMaze[r-1][c].isVisited()){
					theMaze[r-1][c].setVisited(true);
					theMaze[r-1][c].setPrevious(current);
					toTest.enqueue(theMaze[r-1][c]);
				}

				//try going right
				if (c<numCols-1 && theMaze[r][c+1].getType() != SquareType.WALL && !theMaze[r][c+1].isVisited()){
					theMaze[r][c+1].setVisited(true);
					theMaze[r][c+1].setPrevious(current);
					toTest.enqueue(theMaze[r][c+1]);
				}

				//try going down
				if (r<numRows-1 && theMaze[r+1][c].getType() != SquareType.WALL && !theMaze[r+1][c].isVisited()){
					theMaze[r+1][c].setVisited(true);
					theMaze[r+1][c].setPrevious(current);
					toTest.enqueue(theMaze[r+1][c]);
				}
			}
		}//end while

		if (current == f)
			solved = true;

		//reset path positions as unvisited if not on path from start to finish
		cleanUpMaze();

		return solved;

	}//end solveWithQueue


    //==========================================================
    // cleanUpMaze
    //
    // PURPOSE: Reset path positions as unvisited if they are not
    //          on the path from start to finish.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   None
    //==========================================================
	private void cleanUpMaze(){
		Stack path = new Stack(numRows*numCols);

		if (solved){
			Position finish = getFinish();
			Position start = getStart();

			Position current = finish;

			//push path from finish to start
			while (current != null && current != start){ //null shouldn't happen!
				path.push(current);
				current = current.getPrevious();
			}
			if (current == start)
				path.push(start);
			else
				System.out.println("ERROR: Maze marked as solved but no path from start to finish!!!");

			//set entire maze to unvisited
			for (int r=0; r<numRows; r++){
				for (int c=0; c<numCols; c++){
					theMaze[r][c].setVisited(false);
				}
			}

			//set path back to visited
			while (!path.isEmpty()){
				Position onPath = path.pop();
				onPath.setVisited(true);
			}
		}
		else{
			//no path found - reset all
			resetMaze();
		}
	}//end cleanUpMaze


    //==========================================================
    // getPath
    //
    // PURPOSE: Return coordinates on path from start to finish.
    //
    // METHOD: Use a stack.  Locate the finish. Push finish on to
    //         stack. Follow previous pointer to a position earlier
    //         on the path. Continue pushing positions and following
    //         previous pointer until start is reached. To print path,
    //         add to string as positions are popped from stack,
    //         until stack is empty.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   String - contains coordinates from start to finish, in format "(r,c)".
    //==========================================================
	public String getPath(){
		Stack path = new Stack(numRows*numCols);
		String output = "";

		if (solved){
			Position finish = getFinish();
			Position start = getStart();

			Position current = finish;

			//push path from finish to start
			while (current != null && current != start){ //null shouldn't happen!
				path.push(current);
				current = current.getPrevious();
			}
			if (current == start)
				path.push(start);
			//else PROBLEM! Maze can't be solved and not have path from start to finish
			else
				System.out.println("ERROR: Maze marked as solved but no path from start to finish!!!");

			while (!path.isEmpty()){
				Position onPath = path.pop();
				output += onPath.getCoords() + " ";
			}
		}
		//else no path found - return empty string

		return output;
	}//end getPath

}//end class Maze
