// There are many ways to solve a problem. This file contains one
// solution. It is not the only one.
//==============================================================

//
// PURPOSE: Given a patially-filled game board, attempt to find
//          a solution, and output the solution if found.

// INPUT FORMAT: Board listed row by row, items separated by spaces,
//               empty cells marked with "-".
//
//==============================================================

import java.util.Scanner;

public class SudokuSolution {

    //==========================================================
    // main
    //
    // PURPOSE: Run program & process input.
    //
    // ARGUMENTS: No arguments expected.
    //==========================================================
    public static void main(String[] args){

		playGame();

		System.out.println("Program terminated normally.");

    }//end main

    //==========================================================
    // playGame
    //
    // PURPOSE: Read a Sudoku board from the user & try to solve it.
    //==========================================================
    public static void playGame(){
		Scanner keyboard;
		String originalBoard;

		Sudoku theGame;

		keyboard = new Scanner(System.in);
		System.out.println("\nPlease enter a game board, row by row, with - to represent empty cells and spaces between each cell:");
		originalBoard = keyboard.nextLine();

		theGame = new Sudoku(originalBoard);

		System.out.println("The original board is:\n" + theGame);

		boolean result = theGame.fillBoard();
		if (result){
			System.out.println("The solution is:\n" + theGame);
		}
		else{
			System.out.println("No solution found!");
		}

		keyboard.close();
    }//end playGame

}//end class A1Q2SudokuSolution

//==============================================================
// Sudoku class
//
// Holds one Sudoku game board
//
// Note: Could store in either 1D or 2D array.
//       Using char takes less space, but maximum board size is 9x9,
//       unless you use letters or other characters.
//       int allows any legal board size
//==============================================================
class Sudoku{
	private int[][] grid;
	private int gridWidth;
	static private final int EMPTY = 0;
	static private final char EMPTYCHAR = '-';

    //==========================================================
    // Constructor - no parameters
    //
    // PURPOSE: Construct an empty 9x9 grid.
    //==========================================================
	    public Sudoku() {
	        this(9);
	    }

    //==========================================================
    // Constructor - int parameter
    //
    // PURPOSE: Construct an empty grid of given size.
    //
    // PARAMETERS: gridSize - the length and width of the grid
    //==========================================================
    public Sudoku(int gridSize) {
		this.gridWidth = gridSize;
        this.grid = new int[gridWidth][gridWidth];
        for (int r = 0; r < gridWidth; r++) {
            for (int c = 0; c < gridWidth; c++) {
                this.grid[r][c] = EMPTY;
            }
        }
    }

    //==========================================================
    // Constructor - String parameter
    //
    // PURPOSE: Construct a partially-filled Sudoku grid from input.
    //          If valid size is not given, or given board contains
    //          errors, create an empty 9x9 board.
    //          Valid sizes are n^2 by n^2 where n>1 (e.g. 4x4, 9x9, 16x16, 25x25
    //
    // PARAMETERS: str - the contents of the grid, row by row, using
    //                   '-' for blank, and each item separated by ' '
    //==========================================================
    public Sudoku(String str) {
		boolean valid;
        String[] tokens = str.split("\\s+");

//System.out.println("Number of tokens: " + tokens.length);

	    if (Math.pow((int)Math.sqrt(tokens.length),2) != tokens.length){
			//invalid number of entries - does not correspond to allowed board size
			valid = false;
		}
		else{
			//create board from input
	       	gridWidth = (int)Math.sqrt(tokens.length);

	       	this.grid = new int[gridWidth][gridWidth];
	       	for (int i = 0; i < tokens.length; i++) {
				if (tokens[i].charAt(0) == EMPTYCHAR)
					this.grid[i/gridWidth][i%gridWidth] = EMPTY;
				else
	           		this.grid[i/gridWidth][i%gridWidth] = Integer.parseInt(tokens[i]);
	       	}
	       	valid = validateBoard();
		}

		if (!valid){
			//problem with input - invalid board
			//create empty 9x9 board
			gridWidth = 9;
			this.grid = new int[gridWidth][gridWidth];
	       	for (int r = 0; r < gridWidth; r++) {
	       	    for (int c = 0; c < gridWidth; c++) {
	       	        this.grid[r][c] = EMPTY;
	       	    }
	       	}
		}

    }//end constructor

    //==========================================================
    // fillBoard
    //
    // PURPOSE: Try to fill the board with a valid solution.
    //          Uses recursive backtracking.
    //
    // RETURN: true if board was successfully filled, false otherwise
    //==========================================================
	public boolean fillBoard() {

		boolean solved = false;

		int i=0, previ=0;
		while (!solved && emptyExists() && i<gridWidth*gridWidth){

			i = getEmpty(previ); //starting at index previ, find next empty cell in grid
			if (i<gridWidth*gridWidth){   //getEmpty returns length*length if no empty spot found
				//choose a number
				for (int j=1; j<=gridWidth && !solved; j++){

					//test if j would satisfy the rules
					if (itemAllowed(i, j)){

						//if yes, put it in and keep searching
						grid[i/gridWidth][i%gridWidth] = j;

						solved = fillBoard();

						if (!solved) //backtrack by removing number
							grid[i/gridWidth][i%gridWidth] = EMPTY;
					}//end if

				}//end for
			}//end if
			previ=i+1; //start next search after the index tested
		}

		if (!emptyExists())
			solved = true;

       	return solved;
    }//end fillBoard

    //==========================================================
    // itemAllowed
    //
    // PURPOSE: Test if item "num" can be legally placed in position "index".
    //
    // PARAMETERS: index - position on grid (=row*gridSize+col)
    //             num - the character to place at the specified position
    //
    // RETURN: true if item can be legally placed, false otherwise
    //==========================================================
	private boolean itemAllowed(int index, int num){
		int row = index/gridWidth;
		int col = index%gridWidth;

		boolean allowed = true;
		//test the row
		for (int i=0; i<gridWidth && allowed; i++)
			if (i!=col && grid[row][i]==num) //not looking at index but numbers match
				allowed = false;

		//test the col
		for (int i=0; i<gridWidth && allowed; i++)
			if (i!=row && grid[i][col]==num) //not looking at index but numbers match
				allowed = false;

		//test the sqrt(gridSize) x sqrt(gridSize) area
		int blockSize = (int)Math.sqrt(gridWidth);
		int blockRow = row/blockSize; //(int)Math.sqrt(gridWidth);
		int blockCol = col/blockSize; //(int)Math.sqrt(gridWidth);

		for (int i=0; i<blockSize; i++){
			for (int j=0; j<blockSize; j++){
				//i,j is position within the block to be tested

				int toTest = (blockRow*blockSize+i)*gridWidth + blockCol*blockSize+j; //row in grid * num cols  + col in grid = overall index

				if (toTest != index && grid[blockRow*blockSize+i][blockCol*blockSize+j]==num)
					allowed = false;

			}
		}

		return allowed;
	} //end itemAllowed

    //==========================================================
    // validateBoard
    //
    // PURPOSE: Test if the board contains any errors/inconsistencies
    //          (e.g. two of the same number in a row/col/block).
    //
    // RETURN: true if board is legal
    //==========================================================
    private boolean validateBoard(){
		boolean valid = true;

        for (int r = 0; r < gridWidth && valid; r++) {
            for (int c = 0; c < gridWidth && valid; c++) {
                if (grid[r][c] != EMPTY){
					valid = itemAllowed(r*gridWidth+c, grid[r][c]);
				}
            }
        }

		return valid;
	}//end validateBoard

    //==========================================================
    // getEmpty
    //
    // PURPOSE: Locate an empty cell on the grid.
    //
    // PARAMETERS: start - begin search for empty spot at this index
    //
    // RETURN: index of empty cell, or gridSize*gridSize if no empty cell exists
    //==========================================================
	private int getEmpty(int start){
		//overall index = row*gridSize+col
		int toReturn = gridWidth*gridWidth; //to return if empty not found
		boolean emptyExists = false;
		for (int i = start; i<gridWidth*gridWidth; i++){   //exit when find first empty cell with && !emptyExists -- but longer run time
			if (grid[i/gridWidth][i%gridWidth] == EMPTY){
				emptyExists = true;
				toReturn = i;
			}
		}

		return toReturn;
	}//end getEmpty

    //==========================================================
    // emptyExists
    //
    // PURPOSE: Test if there is an empty cell anywhere on the grid.
    //
    // RETURN: true if an empty cell exists, false otherwise
    //==========================================================
	private boolean emptyExists(){
		boolean foundEmpty = false;
		for (int i=0; i<gridWidth && !foundEmpty; i++)
			for (int j=0; j<gridWidth && !foundEmpty; j++)
				if (grid[i][j] == EMPTY)
					foundEmpty = true;

		return foundEmpty;
	}//emptyExists

    //==========================================================
    // toString
    //
    // PURPOSE: Construct a String representation of the grid.
    //
    // RETURN: the grid in a displayable format - one grid row per line
    //==========================================================
    public String toString() {
        String output = "";
        for (int r = 0; r < this.gridWidth; r++) {
            for (int c = 0; c < this.gridWidth; c++) {
                output += grid[r][c] + " ";
            }
            if (r < this.gridWidth-1) {
                output += "\n";
            }
        }
        return output;
    }//end toString

}//end class Sudoku

