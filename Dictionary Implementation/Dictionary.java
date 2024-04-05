//==============================================================
// Dictionary.java

// PURPOSE: Compare the performance of three dictionaries (ordered
//          array, open addressing hash table, separate chaining
//          hash table).
//==============================================================

import java.io.*;
import java.util.*;

//==========================================================
// Dictionary class (main)
//
// PURPOSE: Compare performance of three dictionaries. Given
//          a text file, time how long it takes to fill each
//          dictionary, one word at a time. Time how long it
//          takes to search each dictionary for a given set
//          of words.
//
// ARGUMENTS: None (could pass the filename as an argument)
//==========================================================

public class Dictionary {

    public static void main(String[] args){

		String inputFile = "GreatExpectations3.txt";
		String searchFile = "A3Q2TestWords.txt";

		DictionaryOrdered allWordsOrdered; //complete dictionary, using an ordered array
		DictionaryOpen allWordsOpen; //complete dictionary, using open addressing
		DictionaryChain allWordsChain; //complete dictionary, using separate chaining

		long startTime, endTime, elapsedTime;


		System.out.println("\nFilling ordered array dictionary...");
		startTime = System.nanoTime();
		allWordsOrdered = new DictionaryOrdered(100);
		buildOrdered(allWordsOrdered, inputFile);
		endTime = System.nanoTime();
		elapsedTime = endTime - startTime;
		System.out.println("The time to fill the ordered array dictionary with " + allWordsOrdered.getSize() + " words was: " + elapsedTime + " ns.");

		System.out.println("\nFilling open addressing dictionary...");
		allWordsOpen = new DictionaryOpen(100);
		startTime = System.nanoTime();
		buildOpen(allWordsOpen, inputFile);
		endTime = System.nanoTime();
		elapsedTime = endTime - startTime;
		System.out.println("The time to fill the open addressing dictionary with " + allWordsOpen.getSize() + " words was: " + elapsedTime + " ns.");

		System.out.println("\nFilling separate chaining dictionary...");
		allWordsChain = new DictionaryChain(100);
		startTime = System.nanoTime();
		buildChain(allWordsChain, inputFile);
		endTime = System.nanoTime();
		elapsedTime = endTime - startTime;
		System.out.println("The time to fill the separate chaining dictionary with " + allWordsChain.getSize() + " words was: " + elapsedTime + " ns.");

		System.out.println("\nSearching ordered array dictionary...");
		startTime = System.nanoTime();
		searchOrdered(allWordsOrdered, searchFile);
		endTime = System.nanoTime();
		elapsedTime = endTime - startTime;
		System.out.println("The time to search the ordered array dictionary was: " + elapsedTime + " ns.");

		System.out.println("\nSearching open addressing dictionary...");
		startTime = System.nanoTime();
		searchOpen(allWordsOpen, searchFile);
		endTime = System.nanoTime();
		elapsedTime = endTime - startTime;
		System.out.println("The time to search the open addressing dictionary was: " + elapsedTime + " ns.");

		System.out.println("\nSearching separate chaining dictionary...");
		startTime = System.nanoTime();
		searchChain(allWordsChain, searchFile);
		endTime = System.nanoTime();
		elapsedTime = endTime - startTime;
		System.out.println("The time to search the separate chaining dictionary was: " + elapsedTime + " ns.");

		System.out.println("\nEnd of Processing.");
    }//end main

    //==========================================================
    // buildOrdered
    //
    // PURPOSE: Fill the given dictionary with the given text.
    //
    // PARAMETERS:
    //   allWordsOrdered - the dictionary to be filled
    //   inputFile - the file containing words to add to dictionary
    //
    // RETURNS:
    //   none
    //==========================================================
    static void buildOrdered(DictionaryOrdered allWordsOrdered, String inputFile){
		String temp;
		String[] words;

		try{
		    BufferedReader buff = new BufferedReader(new FileReader(new File(inputFile)));

			temp = buff.readLine();
		    while(temp != null){
		    	words = temp.split("[^a-zA-Z�]+");

				for (int i=0; i<words.length; i++)
		    	   allWordsOrdered.insert(words[i]);

				temp = buff.readLine();
		    }

		}//end try
		catch (IOException e){
		    System.out.println("File I/O Error: " + inputFile);
		}

    }//end buildOrdered

    //==========================================================
    // buildOpen
    //
    // PURPOSE: Fill the given dictionary with the given text.
    //
    // PARAMETERS:
    //   allWordsOpen - the dictionary to be filled
    //   inputFile - the file containing words to add to dictionary
    //
    // RETURNS:
    //   none
    //==========================================================
    static void buildOpen(DictionaryOpen allWordsOpen, String inputFile){
		String temp;
		String[] words;

		try{
			BufferedReader buff = new BufferedReader(new FileReader(new File(inputFile)));

			temp = buff.readLine();
		    while(temp != null){
		    	words = temp.split("[^a-zA-Z�]+");

				for (int i=0; i<words.length; i++)
			       allWordsOpen.insert(words[i]);

				temp = buff.readLine();
		    }

		}//end try
		catch (IOException e){
		    System.out.println("File I/O Error: " + inputFile);
		}

    }//end buildOpen

    //==========================================================
    // buildChain
    //
    // PURPOSE: Fill the given dictionary with the given text.
    //
    // PARAMETERS:
    //   allWordsChain - the dictionary to be filled
    //   inputFile - the file containing words to add to dictionary
    //
    // RETURNS:
    //   none
    //==========================================================
    static void buildChain(DictionaryChain allWordsChain, String inputFile){
		String temp;
		String[] words;

		try{
			BufferedReader buff = new BufferedReader(new FileReader(new File(inputFile)));

			temp = buff.readLine();
		    while(temp != null){
		    	words = temp.split("[^a-zA-Z�]+");

				for (int i=0; i<words.length; i++)
			       allWordsChain.insert(words[i]);

				temp = buff.readLine();
		    }

		}//end try
		catch (IOException e){
		    System.out.println("File I/O Error: " + inputFile);
		}

    }//end buildChain

    //==========================================================
    // searchOrdered
    //
    // PURPOSE: Search the given dictionary for the given words.
    //
    // PARAMETERS:
    //   allWordsOrdered - the dictionary to be searched
    //   searchFile - the file containing words to look for
    //
    // RETURNS:
    //   none (prints number of words found)
    //==========================================================
    static void searchOrdered(DictionaryOrdered allWordsOrdered, String searchFile){
		String temp;
		int numFound=0;
		int numMissing=0;

		try{

		    Scanner scnner = new Scanner(new File(searchFile));
		    while(scnner.hasNext()){

				temp = scnner.next();
				if (allWordsOrdered.search(temp))
				    numFound++;
				else
				    numMissing++;
		    }
		    System.out.println("Number of words found = " + numFound + ". Number of words not found = " + numMissing + ".");

		}//end try
		catch (IOException e){
		    System.out.println("File I/O Error: " + searchFile);
		}

    }//end searchOrdered

    //==========================================================
    // searchOpen
    //
    // PURPOSE: Search the given dictionary for the given words.
    //
    // PARAMETERS:
    //   allWordsOpen - the dictionary to be searched
    //   searchFile - the file containing words to look for
    //
    // RETURNS:
    //   none (prints number of words found)
    //==========================================================
    static void searchOpen(DictionaryOpen allWordsOpen, String searchFile){
		String temp;
		int numFound=0;
		int numMissing=0;

		try{

		    Scanner scnner = new Scanner(new File(searchFile));
		    while(scnner.hasNext()){

				temp = scnner.next();
				if (allWordsOpen.search(temp))
				    numFound++;
				else
				    numMissing++;
		    }
		    System.out.println("Number of words found = " + numFound + ". Number of words not found = " + numMissing + ".");

		}//end try
		catch (IOException e){
		    System.out.println("File I/O Error: " + searchFile);
		}

    }//end searchOpen

    //==========================================================
    // searchChain
    //
    // PURPOSE: Search the given dictionary for the given words.
    //
    // PARAMETERS:
    //   allWordsChain - the dictionary to be searched
    //   searchFile - the file containing words to look for
    //
    // RETURNS:
    //   none (prints number of words found)
    //==========================================================
    static void searchChain(DictionaryChain allWordsChain, String searchFile){
		String temp;
		int numFound=0;
		int numMissing=0;

		try{

		    Scanner scnner = new Scanner(new File(searchFile));
		    while(scnner.hasNext()){

				temp = scnner.next();
				if (allWordsChain.search(temp))
				    numFound++;
				else
				    numMissing++;
		    }
		    System.out.println("Number of words found = " + numFound + ". Number of words not found = " + numMissing + ".");

		}//end try
		catch (IOException e){
		    System.out.println("File I/O Error: " + searchFile);
		}

    }//end searchChain

}//end class A3Q2DictionarySolution

//==============================================================
// DictionaryOrdered class
//
// PURPOSE: Store a list of words, in an ordered array.
//
// METHODS: - constructor: public Dictionary(int size)
//          - public int getSize() - return the current number of words
//          - public void insert(String newWord) - insert new word in list
//          - private boolean realInsert(String toInsert) -
//          - public boolean search(String wordToFind) - search for
//                given word and return true if found
// FOR TESTING: public void print() - print contents of dictionary
//
//==============================================================
class DictionaryOrdered{

    private int arraySize; //current size of array
    private String[] words;
    private int numWords; //current size of the dictionary

    //==========================================================
    // DictionaryOrdered constructor
    //
    // PURPOSE: Create an empty dictionary.  Dictionary is implemented
    //          using an array in alphabetical order.
    //
    // PARAMETERS:
    //   size  Expected number of words in the dictionary.
    //==========================================================
    public DictionaryOrdered(int size){
		arraySize = size;
		words = new String[arraySize];
		numWords = 0;
    }//end constructor

    //==========================================================
    // getSize
    //
    // PURPOSE: Return the number of words currently in the dictionary.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   int - the number of words in the dictionary
    //==========================================================
    public int getSize(){
		return numWords;
    }//end getSize

    //==========================================================
    // insert
    //
    // PURPOSE: Insert the given word in the dictionary.
    //
    // PARAMETERS:
    //   newWord  The word to add to the dictionary.
    //
    // RETURNS:
    //   None
    //==========================================================
    public void insert(String newWord){
		//if full, double size
		if (numWords >= arraySize){
		    String[] temp = new String[2*arraySize];
		    for (int i=0; i<numWords; i++)
				temp[i] = words[i];
		    arraySize = 2*arraySize;
		    words = temp;
		}

		//insert word
		boolean success = realInsert(newWord); //inserts word into array
		if (success)
		    numWords++;
		    //note: if duplicate, would have returned false
		    //      & don't want to increment numWords

    }//end insert

    //==========================================================
    // realInsert
    //
    // PURPOSE: Locate the appropriate location in the ordered
    //          array and insert the given word.
    //
    // PARAMETERS:
    //   toInsert  The word to add to the dictionary.
    //
    // RETURNS:
    //   boolean - true if word added, false if word was a duplicate
    //==========================================================
    private boolean realInsert(String toInsert){
		boolean added = false;
		String toInsertLower = toInsert.toLowerCase();

		int index = binSearch(toInsertLower); //find where to insert the word

		if (numWords==0){
		    words[0] = toInsertLower;
		    added = true;
		}
		else if (index==numWords){  //avoid calling compareTo in the case below if there is no string at index
			words[numWords] = toInsertLower;
			added = true;
		}
		else if (words[index].compareTo(toInsertLower)!=0){
		    //shift all items over
		    for (int i=numWords; i>index; i--){
				words[i] = words[i-1];
		    }
		    words[index] = toInsertLower;

		    added = true;
		}

		return added;

    }//end realInsert

    //==========================================================
    // search
    //
    // PURPOSE: Search dictionary for given word and return true if found.
    //
    // PARAMETERS:
    //   wordToFind  The word to search for.
    //
    // RETURNS:
    //   boolean - true if word is found in dictionary, false otherwise
    //==========================================================
    public boolean search(String wordToFind){
		String wordToFindLower = wordToFind.toLowerCase();
		boolean found = false;

		if (numWords>0 && words[binSearch(wordToFindLower)].compareTo(wordToFindLower) == 0)
		    found = true;

		return found;

    }//end search

    //==========================================================
    // binSearch
    //
    // PURPOSE: Search dictionary for given word and return array
    //          index if found, location where word should go if not found.
    //          Used by search and insert.
    //
    // PARAMETERS:
    //   wordToFind  The word to search for.
    //
    // RETURNS:
    //   int - index in the array if found, index where word should go if not found
    //==========================================================
    private int binSearch(String wordToFind){
		int lo = 0;
		int hi = numWords-1;
		boolean done = false;
		int index = -1; //location of word

		if (numWords>0){
		    while(!done){
				int mid = (lo + hi) / 2;
				if (lo > hi){
				    done = true; //not found, exit pointing to where word should go
				    index = lo;
				}
				else if (words[mid].compareTo(wordToFind)==0){
				    done = true; //found, exit
				    index = mid;
				}
				else if (words[mid].compareTo(wordToFind)<0){
					lo = mid + 1;
				}
				else{
					hi = mid - 1;
				}
		    }//end while
		}
		else
		    index = 0;

		return index;
    }//end binSearch

    //==========================================================
    // print
    //
    // PURPOSE: Print the contents of the dictionary.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   None
    //==========================================================
    public void print(){
		if (numWords > 0){
		    System.out.println("The dictionary contains " + numWords + " words:");
		    for (int i=0; i < arraySize; i++){
			if (words[i] != null)
			    System.out.println(words[i].toLowerCase());
		    }//end for

		}//end if
		else
		    System.out.println("The dictionary is currently empty.");
   	}//end print

}//end class DictionaryOrdered


//==============================================================
// DictionaryOpen class
//
// PURPOSE: Store a list of words, in a hash table using open addressing.
//
// METHODS: - constructor: public Dictionary(int size)
//          - private int primaryHash(String key) - calculate the primary
//                hash value for the given string
//          - private  int secondaryHash(String key) - calculate the
//                secondary hash value for the given string
//          - private  int getPrime(int min) - return the first prime
//                number greater than min
//          - private  boolean isPrime(int n) - test if n is prime
//          - public int getSize() - return the current number of words
//          - public void insert(String newWord) - insert new word in list
//          - private boolean realInsert(String toInsert) -
//          - private void rehash() - transfer contents to larger table
//          - public boolean search(String wordToFind) - search for
//                given word and return true if found
// FOR TESTING: public void print() - print contents of dictionary
//
//==============================================================
class DictionaryOpen{

    private int arraySize; //current size of hash table
    private String[] words;
    private int numWords; //current size of the dictionary

    private static final int HASH2CONST = 41; //must be smaller than array size

    //==========================================================
    // DictionaryOpen constructor
    //
    // PURPOSE: Create an empty dictionary.  Dictionary is implemented
    //          as a hash table, using open addressing.
    //
    // PARAMETERS:
    //   size  Expected number of words in the dictionary.
    //==========================================================
    public DictionaryOpen(int size){
		arraySize = getPrime(size); //find a prime number larger than requested size
		words = new String[arraySize];
		numWords = 0;
    }//end constructor


    //==========================================================
    // primaryHash
    //
    // PURPOSE: Calculate the primary hash value for the given string,
    //          using only letters (ignore punctuation).
    //
    // PARAMETERS:
    //   key - the string to be stored in the dictionary
    //
    // RETURNS:
    //   int - the hash value for the given string
    //==========================================================
    private int primaryHash(String key){
		int hashValue = 0;
		for (int j=0; j < key.length(); j++){
		    if (key.charAt(j) >= 97 && key.charAt(j) <= 122){
		        int letter = key.charAt(j) - 96;
		        hashValue = (hashValue * 27 + letter) % arraySize;
		    }
		}
		return hashValue;
    }//end primaryHash

    //==========================================================
    // secondaryHash
    //
    // PURPOSE: Calculate the secondary hash value for the given
    //          string, using only letters (e.g. ignore apostrophes).
    //
    // PARAMETERS:
    //   key - the string to be stored in the dictionary
    //
    // RETURNS:
    //   int - the hash value for the given string
    //==========================================================
    private  int secondaryHash(String key){
		int hashValue = 0;
		//sum of int value for characters that are a-z
		for (int j=0; j<key.length(); j++){
		    if (key.charAt(j) >= 97 && key.charAt(j) <= 122)
				hashValue += (key.charAt(j) - 96);
		}
		hashValue = HASH2CONST - (hashValue % HASH2CONST);
		return hashValue;
    }//end secondaryHash

    //==========================================================
    // getPrime
    //
    // PURPOSE: Returns first prime number greater than min.
    //
    // PARAMETERS:
    //   min - the lower limit for the search
    //
    // RETURNS:
    //   int - the prime number
    //==========================================================
    private  int getPrime(int min){
		for (int j = min+1; true; j++){
		    if (isPrime(j))
				return j;
		}
    }//end getPrime

    //==========================================================
    // isPrime
    //
    // PURPOSE: Returns true if given number is a prime number.
    //
    // PARAMETERS:
    //   n - the number to test
    //
    // RETURNS:
    //   boolean - true if n is prime, false otherwise
    //==========================================================
    private  boolean isPrime(int n){
		boolean isPrime = true;
		for (int j=2; (j*j <= n) && isPrime; j++)
		    if (n%j == 0)
				isPrime = false; //if divides evenly, is NOT prime
		return isPrime;
    }//end isPrime

    //==========================================================
    // getSize
    //
    // PURPOSE: Return the number of words currently in the dictionary.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   int - the number of words in the dictionary
    //==========================================================
    public int getSize(){
		return numWords;
    }//end getSize

    //==========================================================
    // insert
    //
    // PURPOSE: Insert the given word in the dictionary.
    //
    // PARAMETERS:
    //   newWord  The word to add to the dictionary.
    //
    // RETURNS:
    //   None
    //==========================================================
    public void insert(String newWord){
		//if hash table more than 60% full, rehash
		if (numWords > arraySize*.6){
		    rehash();
		}

		//insert word
		boolean success = realInsert(newWord); //inserts word into hash table
		if (success)
		    numWords++;
		    //note: if duplicate, would have returned false
		    //      & don't want to increment numWords

    }//end insert

    //==========================================================
    // realInsert
    //
    // PURPOSE: Locate the appropriate location and insert the given
    //          word in the hash table.
    //          Used as helper method for insert & rehash.
    //
    // PARAMETERS:
    //   toInsert  The word to add to the dictionary.
    //
    // RETURNS:
    //   boolean - true if word added, false if word was a duplicate
    //==========================================================
    private boolean realInsert(String toInsert){

		String toInsertLower = toInsert.toLowerCase();
		int hashVal = primaryHash(toInsertLower);
		int stepSize = secondaryHash(toInsertLower);
		boolean inserted = true;

		while (words[hashVal] != null && inserted){
		    if (words[hashVal].compareTo(toInsertLower)==0)
				inserted = false;  //found duplicate, do not add
		    hashVal += stepSize;
		    hashVal %= arraySize;
		}//end while

		if (inserted)
			words[hashVal] = toInsertLower;

   	   	return inserted;

    }//end realInsert

    //==========================================================
    // rehash
    //
    // PURPOSE: Transfer hash table contents to a larger table.
    //          Used as helper method for insert.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   None
    //==========================================================
    private void rehash(){
		String[] oldTable = words;    //save old words - allows you to use insert to put words in the new array
		int oldArraySize = arraySize;

		arraySize = getPrime(arraySize*2);  //next prime above twice the size
		//use new arraySize to insert into new table
		words = new String[arraySize];

		for (int i=0; i<oldArraySize; i++){
		    if (oldTable[i] != null)
				realInsert(oldTable[i]); //insert in new words array
		}
    }//end rehash

    //==========================================================
    // search
    //
    // PURPOSE: Search dictionary for given word and return true if found.
    //
    // PARAMETERS:
    //   wordToFind  The word to search for.
    //
    // RETURNS:
    //   boolean - true if word is found in dictionary, false otherwise
    //==========================================================
    public boolean search(String wordToFind){
		String wordToFindLower = wordToFind.toLowerCase();
		int hashVal = primaryHash(wordToFindLower);
		int stepSize = secondaryHash(wordToFindLower);
		boolean found = false;

		while (words[hashVal] != null && !found){
		    if (words[hashVal].compareTo(wordToFindLower)==0){
				found = true;  //found duplicate
		    }
		    hashVal += stepSize;
		    hashVal %= arraySize;
		}

		return found; //word was not found
    }//end search

    //==========================================================
    // print
    //
    // PURPOSE: Print the contents of the dictionary.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   None
    //==========================================================
    public void print(){
		if (numWords > 0){
		    System.out.println("The dictionary contains " + numWords + " words:");
		    for (int i=0; i < arraySize; i++){
				if (words[i] != null)
				    System.out.println(words[i].toLowerCase());
		    }//end for

		}//end if
		else
		    System.out.println("The dictionary is currently empty.");
  	}//end print

}//end class DictionaryOpen


//==============================================================
// StringNode class
//
// PURPOSE: Store information for one string in a linked list.
//
// **Could be nested inside the linked list class**
//
// METHODS: - constructors: public StringNode(String w)
//          - accessors: public String getWord()
//                       public StringNode getNext()
//          - mutators: public boolean setWord(String w)
//                      public boolean setNext(StringNode newNext)
//          - toString: public String toString()
//==============================================================
class StringNode{
    public String word;
    public StringNode next;

    //==========================================================
    // StringNode constructor
    //
    // PURPOSE: Create one StringNode object with given word.
    //
    // PARAMETERS:
    //   w  The word to store.
    //==========================================================
    public StringNode(String w){
		word = w;
		next = null;
    }//end constructor

    //==========================================================
    // getWord
    //
    // PURPOSE: Return the word in the node.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   String - the word
    //==========================================================
    public String getWord(){
		return word;
    }//end getWord

    //==========================================================
    // getNext
    //
    // PURPOSE: Return the reference to the next node in a list of nodes.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   StringNode - a reference/pointer to the next node
    //==========================================================
    public StringNode getNext(){
		return next;
    }//end getNext

    //==========================================================
    // setWord
    //
    // PURPOSE: Set the word in this node
    //
    // PARAMETERS:
    //   newWord  the new word
    //
    // RETURNS:
    //   boolean - true if word was stored successfully, false otherwise
    //==========================================================
    public boolean setWord(String newWord){
		if (newWord==null)
		    return false;
		else{
		    word = newWord;
		    return true;
		}
    }//end setWord

    //==========================================================
    // setNext
    //
    // PURPOSE: Set the reference to the next node in the list
    //
    // PARAMETERS:
    //   newNext  the new reference
    //
    // RETURNS:
    //   boolean - true if next was set successfully
    //==========================================================
    public boolean setNext(StringNode newNext){
		next = newNext;
		return true;
    }//end setNext

    //==========================================================
    // toString
    //
    // PURPOSE: Create a String containing the data for this node.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   String - contains the word stored in the node
    //==========================================================
    public String toString(){
		return word;
    }//end toString

}//end class StringNode

//==============================================================
// Chain class
//
// PURPOSE: A linked list to store the items in one chain of a
//          hash table using separate chaining.
//
// METHODS: - constructor: public Chain()
//          - public boolean isEmpty() - test if the list is empty
//          - public boolean insert(String toAdd) - insert
//                a new node with given data into the list, return true
//                if successful, false if given string is already in list
//          - public boolean search(String toFind) - return true
//                if given string is in the linked list
//          - public String toString() - Create a String containing
//                the data from all nodes in the list, one node per line
//==============================================================
class Chain{ //a linked list class
    public StringNode first;

    //==========================================================
    // Chain constructor
    //
    // PURPOSE: Create an empty linked list.
    //
    // PARAMETERS:
    //   None
    //==========================================================
    public Chain(){
		first = null;
    }//end constructor

    //==========================================================
    // isEmpty
    //
    // PURPOSE: Test whether there are any nodes in the list.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   boolean - true if the list is empty, false otherwise.
    //==========================================================
    public boolean isEmpty(){
		return (first == null);
    }//end isEmpty

    //==========================================================
    // insert
    //
    // PURPOSE: Create & insert a new node with given data into
    //          the list.  Do not add duplicate words.
    //
    // PARAMETERS:
    //   toAdd  the string to store in the new node
    //
    // RETURNS:
    //   boolean - true if successful, false if given string is already in list
    //==========================================================
    public boolean insert(String toAdd){
		StringNode newNode = new StringNode(toAdd);
		StringNode previous = null;
		StringNode current = first;
		boolean inserted = true;

		if (first==null){ //add first item
		    first = newNode;
		}
		else{ //test for duplicates... if reach end of list, add the word
		    while (current != null && inserted){
				if (current.word.compareTo(toAdd)==0)
				    inserted = false;  //do not add duplicate
				previous = current;
				current = current.next;
		    }
		    //at end of list and previous can't be null because there
		    //was at least one item in the list
		    if (inserted)
		  	   previous.next = newNode;
		}
		return inserted;
    }//end insert


    //no need for delete method


    //==========================================================
    // search
    //
    // PURPOSE: Find the given string in the list.
    //
    // PARAMETERS:
    //   toFind  the string to search for
    //
    // RETURNS:
    //   boolean - true if string is found, false otherwise
    //==========================================================
    public boolean search(String toFind){
		StringNode current = first;
		boolean found = false;

		while (current != null && !found){
		    if (current.word.compareTo(toFind)==0)
				found = true; //found
		    current = current.next;
		}
		return found;
    }//end search

    //==========================================================
    // toString
    //
    // PURPOSE: Create a String containing the data from all nodes
    //          in the list, one node per line.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   String - contains all words in the list, with
    //            data from each node on its own line
    //==========================================================
    public String toString(){
		String theList = "";
		StringNode current = first;

		while (current != null){
		    theList += current.toString() + "\n";
		    current = current.getNext();
		}

		return theList;
    }//end toString

}//end Chain class


//==============================================================
// DictionaryChain class
//
// PURPOSE: Store a list of words, in a hash table using separate
//          chaining. Words are converted to lowercase as inserted.
//
// METHODS: - constructor: public Dictionary(int size)
//          - private int hash(String key) - calculate the hash
//                value for the given string
//          - private  int getPrime(int min) - return the first prime
//                number greater than min
//          - private  boolean isPrime(int n) - test if n is prime
//          - public int getSize() - return the current number of words
//          - public void insert(String newWord) - insert new word in list
//          - private boolean realInsert(String toInsert) -
//          - private void rehash() - transfer contents to larger table
//          - public boolean search(String wordToFind) - search for
//                given word and return true if found
// FOR TESTING: public void print() - print contents of dictionary
//
//==============================================================
class DictionaryChain{

    private int arraySize; //current size of hash table
    private Chain[] hashArray; //array of linked lists
    private int numWords; //current size of the dictionary


    //==========================================================
    // DictionaryChain constructor
    //
    // PURPOSE: Create an empty dictionary.  Dictionary is implemented
    //          as a hash table, using separate chaining.  Create
    //          empty lists at each position in the hash array.
    //
    // PARAMETERS:
    //   size  Expected number of words in the dictionary.
    //==========================================================
    public DictionaryChain(int size){
		arraySize = getPrime(size);
		hashArray = new Chain[arraySize];

		for (int i=0; i < arraySize; i++)
		    hashArray[i] = new Chain();

		numWords = 0;
    }//end constructor


    //==========================================================
    // hash (Horner's method)
    //
    // PURPOSE: Calculate the hash value for the given string.
    //          Use only letters a-z (e.g. ignore apostrophes).
    //
    // PARAMETERS:
    //   key - the string to be stored in the dictionary
    //
    // RETURNS:
    //   int - the hash value for the given string
    //==========================================================
    private int hash(String key){

		int hashValue = 0;
		for (int j=0; j < key.length(); j++){
		    if (key.charAt(j) >= 97 && key.charAt(j) <= 122){
		        int letter = key.charAt(j) - 96;
		        hashValue = (hashValue * 27 + letter) % arraySize;
		    }
		}

		return hashValue;
    }

    //==========================================================
    // getPrime
    //
    // PURPOSE: Returns first prime number greater than min.
    //
    // PARAMETERS:
    //   min - the lower limit for the search
    //
    // RETURNS:
    //   int - the prime number
    //==========================================================
    private  int getPrime(int min){
		for (int j = min+1; true; j++){
		    if (isPrime(j))
				return j;
		}
    }//end getPrime

    //==========================================================
    // isPrime
    //
    // PURPOSE: Returns true if given number is a prime number.
    //
    // PARAMETERS:
    //   n - the number to test
    //
    // RETURNS:
    //   boolean - true if n is prime, false otherwise
    //==========================================================
    private  boolean isPrime(int n){
		boolean isPrime = true;
		for (int j=2; (j*j <= n) && isPrime; j++)
		    if (n%j == 0)
				isPrime = false; //if divides evenly, is NOT prime
		return isPrime;
    }//end isPrime


    //==========================================================
    // getSize
    //
    // PURPOSE: Return the number of words currently in the dictionary.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   int - the number of words in the dictionary
    //==========================================================
    public int getSize(){
		return numWords;
    }//end getSize

    //==========================================================
    // insert
    //
    // PURPOSE: Insert the given word in the dictionary.
    //
    // PARAMETERS:
    //   newWord  The word to add to the dictionary.
    //
    // RETURNS:
    //   None
    //==========================================================
    public void insert(String newWord){
		//if hash table > two times full, rehash
		if (numWords > arraySize*2){
		    rehash();
		}

		//insert word
		boolean success = realInsert(newWord);
		if (success)
		    numWords++; //if duplicate, would have returned false & don't want to increment numWords

    }//end insert

    //==========================================================
    // realInsert
    //
    // PURPOSE: Locate the appropriate chain and insert the given
    //          word in the hash table.
    //          Used as helper method for insert & rehash.
    //
    // PARAMETERS:
    //   toInsert  The word to add to the dictionary.
    //
    // RETURNS:
    //   boolean - true if word added, false if word was a duplicate
    //==========================================================
    private boolean realInsert(String toInsert){

		String toInsertLower = toInsert.toLowerCase();
		int hashVal = hash(toInsertLower);

		boolean success = hashArray[hashVal].insert(toInsertLower); //insert into linked list
		return success;

    }//end realInsert

    //==========================================================
    // rehash
    //
    // PURPOSE: Transfer hash table contents to a larger table.
    //          Used as helper method for insert.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   None
    //==========================================================
    private void rehash(){
		Chain[] oldArray = hashArray; //save old array
		int oldArraySize = arraySize;

		arraySize = getPrime(arraySize*2);  //next prime above twice the size
		//will use new arraySize to calc hash function & insert into new table

		hashArray = new Chain[arraySize];
		for (int i=0; i < arraySize; i++)
		    hashArray[i] = new Chain(); //create empty chains in larger table

		//copy all items into new hash table
		//Note: this method knows about the linked list structure.  This is
		//      ok because we know we are using separate chaining.  The lists
		//      are hidden from the user of the dictionary class.
		for (int i=0; i<oldArraySize; i++){ //for each chain in the old table
		    StringNode current = oldArray[i].first; //start at beginning of chain
		    while (current != null){ //for all items in a chain
				//take an item, rehash it, move to next item in list
				String temp = current.word;
				realInsert(temp); //insert into new array, which calculates new hash value using the larger array size
				current = current.next;
		    }//end while
		}//end for

    }//end rehash

    //==========================================================
    // search
    //
    // PURPOSE: Search dictionary for given word and return true if found.
    //
    // PARAMETERS:
    //   wordToFind  The word to search for.
    //
    // RETURNS:
    //   boolean - true if word is found in dictionary, false otherwise
    //==========================================================
    public boolean search(String wordToFind){
		String wordToFindLower = wordToFind.toLowerCase();
		int hashVal = hash(wordToFindLower);

		return hashArray[hashVal].search(wordToFindLower); //call linked list search

    }//end search


    //==========================================================
    // print
    //
    // PURPOSE: Print the contents of the dictionary.
    //
    // PARAMETERS:
    //   None
    //
    // RETURNS:
    //   None
    //==========================================================
    public void print(){
		StringNode current;

		if (numWords > 0){
		    System.out.println("The dictionary contains " + numWords + " words:");
		    for (int i=0; i < arraySize; i++){
				//if chain not empty, print it
				if (!hashArray[i].isEmpty())
				    System.out.print(hashArray[i]); //call list toString method
		    }//end for

		}//end if
		else
		    System.out.println("The dictionary is currently empty.");
    }//end print

}//end class DictionaryChain
