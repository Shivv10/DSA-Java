//==============================================================
// Library.java

//  Create a Library of Books, and track loans.
//
// NOTE: Some debugging print statements have been left as comments,
//       to demonstrate how the program was tested as being developed.
//==============================================================
import java.util.*;
import java.io.*;


public class Library{

    //==========================================================
    // main
    //
    // PURPOSE: Run program, process input and produce output.
    //
    // ARGUMENTS: No arguments expected.
    //==========================================================
	public static void main(String[] args){
		simulateLibrary();
		System.out.println("\nProgram terminated normally.");
	}//end main

    //==========================================================
    // simulateLibrary
    //
    // PURPOSE: Create a Library, read input file one line at a
    //          time and perform requested operations.
    //
    // PARAMETERS: none
    //==========================================================
    public static void simulateLibrary(){
	    Scanner keyboard;
        String filename;
		BufferedReader inFile;
		String nextLine;

		Library myLibrary = new Library();

		// Allow user to choose file with keyboard input.
		keyboard = new Scanner( System.in );
		System.out.println( "\nPlease enter the input file name (.txt files only): " );
		filename = keyboard.nextLine();

		System.out.println("\nProcessing file " + filename + "...\n");

		try{
		    inFile = new BufferedReader(new FileReader(filename));
		    nextLine = inFile.readLine();

		    while (nextLine != null){

				processLine(myLibrary, nextLine);

				nextLine = inFile.readLine();
		    }
		} catch (IOException e){
		    System.out.println("\nError reading file: " + filename);
		}

    }//end simulateLibrary


    //==========================================================
    // processLine
    //
    // PURPOSE: Process one line of input, calling Library methods
    //          as appropriate.
    //
    // PARAMETERS: theLibrary - the library on which to operate
    //             inputLine - one line of input
    //==========================================================
	public static void processLine(Library theLibrary, String inputLine){
		int index=-1, index2=-1; //location of commas
		String command=null, params=null;  //to split input line into a command and parameters
		String param1=null, param2=null, param3=null; //to split parameters into name & title

		if (!inputLine.equals("")){
			//get command
			index = inputLine.indexOf(' ');
			if (index != -1){
				command = inputLine.substring(0,index); //up to but not including space
				params = inputLine.substring(index+1, inputLine.length()); //+1 to omit space

//System.out.println("The command is [" + command + "] and the parameters are [" + params + "].");

				//split params into author names & title
				index = params.indexOf(',');
				if (index != -1){ //there is at least one ,
					param1 = params.substring(0,index).trim();
					if (index < params.length()-1){ //there is something after the ,
						index2 = params.indexOf(',', index+1); //starts search at index+1
						if (index2!= -1){ //there is a second ,
							param2 = params.substring(index+1,index2).trim();
							if (index2 < params.length()-1) //there is something after the second ,
								param3 = params.substring(index2+1,params.length()).trim();
						}
						else //there is no second , but there is text after the first ,
							param2 = params.substring(index+1, params.length()).trim();
					}
				}
				else //no , found - only one parameter
					param1 = params;
			}//end if

//System.out.println("The first param is [" + param1 + "] the second is [" + param2 + "] and the third is [" + param3 + "].");

			if(command.equals("ADD")){
				//params could be blank - if so, add book with "unknown" field
				if (param1 == null || param1.equals(""))
					param1 = "unknown";
				if (param2 == null || param2.equals(""))
					param2 = "unknown";
				if (param3 == null || param3.equals(""))
					param3 = "unknown";

				theLibrary.addBook(param1, param2, param3);
//System.out.println("The updated library is:\n" + theLibrary);
			}
			else if (command.equals("SEARCHA") && param1 != null && !param1.equals("")){
				//must have one parameter (author last name) to search by author
				System.out.println("Books by " + param1 + ":\n" + theLibrary.listByAuthor(param1));
			}
			else if (command.equals("SEARCHT") && param1 != null && !param1.equals("")){
				//must have one parameter (title) to search by title
				System.out.println("Books named " + param1 + ":\n" + theLibrary.listByTitle(param1));

			}
			else if (command.equals("GETBOOK") && param1 != null && !param1.equals("")
			         && param2 != null && !param2.equals("") && param3 != null && !param3.equals("")){
				//needs 3 parameters (last, first, title)

				boolean bookAvail = theLibrary.getBook(param1, param2, param3);
				if (bookAvail)
					System.out.println("Book loaned:\n" + param1 + ", " + param2 + ", " + param3 + "\n");
				else
					System.out.println("Book not available:\n" + param1 + ", " + param2 + ", " + param3 + "\n");
			}
			else if (command.equals("RETURNBOOK") && param1 != null && !param1.equals("")
			         && param2 != null && !param2.equals("") && param3 != null && !param3.equals("")){
				//needs 3 parameters (last, first, title)
				boolean bookReturned = theLibrary.returnBook(param1, param2, param3);
				if (bookReturned)
					System.out.println("Book returned:\n" + param1 + ", " + param2 + ", " + param3 + "\n");
				else
					System.out.println("Book could not be returned:\n" + param1 + ", " + param2 + ", " + param3 + "\n");
			}
			else{
				System.out.println("\"" + inputLine + "\" is an invalid command.");
			}

		}//end if

	}//end processLine

}//end class A1Q1LibrarySolution


//==============================================================
// Library class
//
// PURPOSE: Store a list of books.
//==============================================================
class Library{
	static final int INITIAL_SIZE = 10;
	int arraySize;
	int numBooks;
	Book[] theBooks;

    //==========================================================
    // Library constructor
    //
    // PURPOSE: Create an empty Library.
    //==========================================================
	public Library(){
		arraySize = INITIAL_SIZE;
		theBooks = new Book[arraySize];
		numBooks = 0;
	}

    //==========================================================
    // addBook
    //
    // PURPOSE: Add a book with given author and title to the library.
    //
    // PARAMETERS: last - author last name
    //             first - author first name
    //             title - book title
    //==========================================================
	public void addBook(String last, String first, String title){
		//if no space left in array of books, enlarge it
		if (numBooks == arraySize)
			enlargeArray();

		//find where to insert book
		int index = searchByAuthor(last);

		//(if author not found, will insert at index)
		//if author found, test first name and title & update index, so that books are in order
		while (numBooks>0 && index < numBooks && theBooks[index].getLast().equals(last)
			   && theBooks[index].getFirst().compareTo(first) < 0){ //same last name, compare first name
			index++;
		}
		while (numBooks>0 && index < numBooks && theBooks[index].getLast().equals(last) && theBooks[index].getFirst().equals(first)
		       && theBooks[index].getTitle().compareTo(title) < 0){ //same author, compare title
			index++;
		}
		//NOTE: Could also implement a compare method in book class, similar to String compareTo

		//insert new book at index by shifting all later books up one position
		for (int i=numBooks; i>index; i--){
			theBooks[i] = theBooks[i-1];
		}
		theBooks[index] = new Book(last, first, title);
		numBooks++;

	}//end addBook

    //==========================================================
    // enlargeArray
    //
    // PURPOSE: Enlarge library, by doubling array size.
    //==========================================================
	private void enlargeArray(){
		Book[] temp = new Book[2*arraySize];
		arraySize = 2*arraySize;

		for (int i=0; i<numBooks; i++)
			temp[i] = theBooks[i];

		theBooks = temp;
	}//end enlargeArray

    //==========================================================
    // searchByAuthor
    //
    // PURPOSE: A modified binary search (non-recursive). Will return
    //          the index of the first book with matching author, or
    //          the index of the location where that author would be,
    //          if the author is not found.
    //
    // PARAMETERS: last - author to search for
    //
    // RETURN: index of first book with matching author, or index
    //         of location where that author would be
    //==========================================================
	private int searchByAuthor(String last){

		int lo = 0;
		int hi=numBooks-1;
		boolean found = false;
		int index=0; //return 0 if array is empty

		if (numBooks > 0){
			while (lo <= hi && !found){
				int mid = (lo+hi)/2;
				if (theBooks[mid].getLast().equals(last)){
					index = mid;
					found = true;
				}
				else if (theBooks[mid].getLast().compareTo(last) < 0){
					lo=mid+1;
				}
				else{
					hi=mid-1;
				}
			}
		}

		//either last name was found, or lo & hi crossed and lo points at first book that should come after given author

		if (found){
			//author was found - make sure you find FIRST book with that author - step backward to find it
			while (index > 0 && theBooks[index-1].getLast().equals(last)){
				//if previous author matches, step back
				index--;
			}
		}
		else{
			//lo & hi crossed - lo is first book that should come after given author
			index = lo;
		}

		return index;
	}//end searchByAuthor

    //==========================================================
    // listByAuthor
    //
    // PURPOSE: List all books with given author.
    //
    // PARAMETERS: last - last name of author
    //
    // RETURN: String containing books with matching author, one per line
    //==========================================================
	public String listByAuthor(String last){
		int index = searchByAuthor(last); //index is first book by that author, or first book after where that author would be
		String output = "";

		while(numBooks>0 && index<numBooks && theBooks[index].getLast().equals(last)){ //don't look at position 0 if the library is empty!
			output = output + theBooks[index] + "\n";
			index++;
		}

		return output;
	}

    //==========================================================
    // listbyTitle
    //
    // PURPOSE: List all books with given title.
    //
    // PARAMETERS: title - book title
    //
    // RETURN: String containing books with matching title, one per line
    //==========================================================
	public String listByTitle(String title){
		String output = "";

		//library not ordered by title - use linear search
		for (int i=0; i<numBooks; i++){
			if (theBooks[i].getTitle().equals(title))
				output = output + theBooks[i] + "\n";
		}
		return output;
	}

    //==========================================================
    // getBook
    //
    // PURPOSE: Test if book is available, and if so mark it as out on loan.
    //
    // PARAMETERS: last - author last name
    //             first - author first name
    //             title - book title
    //
    // RETURN: true if book was available and has been marked as loaned, false otherwise
    //==========================================================
	public boolean getBook(String last, String first, String title){
		boolean success = false;

		//find book - locate author using binary search
		int index = searchByAuthor(last);

		if (numBooks>0){
		//find book to loan
			while (index < numBooks && theBooks[index].getLast().equals(last) && !success){
				if(theBooks[index].getFirst().equals(first) && theBooks[index].getTitle().equals(title)
				   && theBooks[index].isAvailable()){
					theBooks[index].getBook(); //sets book as loaned
					success = true;
				}
				index++;
			}
		}

		//will exit loop with success==false if book was not found or all copies of the book were already loaned

		return success;

	}//end getBook

    //==========================================================
    // returnBook
    //
    // PURPOSE: Test if book is out on loan, and if so mark it as available (i.e. it has been returned).
    //
    // PARAMETERS: last - author last name
    //             first - author first name
    //             title - book title
    //
    // RETURN: true if book was successfully returned, false otherwise
    //==========================================================
	public boolean returnBook(String last, String first, String title){
		boolean success = false;

		//find book - locate author using binary search
		int index = searchByAuthor(last);

		if (numBooks>0){
		//find book to return
			while (index < numBooks && theBooks[index].getLast().equals(last) && !success){
				if(theBooks[index].getFirst().equals(first) && theBooks[index].getTitle().equals(title)
				   && !theBooks[index].isAvailable()){
					theBooks[index].returnBook(); //sets book as available
					success = true;
				}
				index++;
			}
		}

		//will exit loop with success==false if book was not found or all copies of the book were already available

		return success;

	}//end returnBook

    //==========================================================
    // toString
    //
    // PURPOSE: Output the library contents, one book per line.
    //
    // RETURN: String containing a list of all books in the library, one book per line
    //==========================================================
	public String toString(){
		String out = "";
		for (int i=0; i<numBooks; i++)
			out += theBooks[i] + "\n";
		return out;
	}

}//end class Library

//==============================================================
// Book class
//
// PURPOSE: Store information for one book.
//==============================================================
class Book{
	private String title;
	private String lastname;
	private String firstname;
	private boolean available;

	public Book(String l, String f, String t){
		title = t;
		lastname = l;
		firstname = f;
		available = true;
	}

	public boolean isAvailable(){
		return available;
	}

	public String getLast(){
		return lastname;
	}

	public String getFirst(){
		return firstname;
	}

	public String getTitle(){
		return title;
	}

	public void getBook(){
		available = false;
	}

	public void returnBook(){
		available = true;
	}

	public String toString(){
		return lastname + ", " + firstname + ", " + title;
	}

}//end class Book
