Library Management System
This project involves the implementation of a library management system in Java. The system allows users to manage a collection of books, search for books by author or title, loan books, and return them. The program reads commands from an input file, executes the corresponding operations, and outputs the results accordingly.


Classes Overview:
Application Class: The main class responsible for reading commands from the input file, executing operations, and displaying results. It serves as the entry point for the program.
Book Class: Represents a book entity with attributes such as title, author's first name/initials, author's last name, and a boolean flag indicating whether the book is currently on loan.
Library Class: Manages a collection of books and provides methods to perform operations like adding books, listing books by author or title, loaning books, and returning books.


Library Class Methods:
Constructor: Initializes an empty library.
addBook: Adds a new book to the library, ensuring that the books are stored in order by author's last name, first name, and title.
listByAuthor: Returns a formatted string containing a list of books written by a given author.
listByTitle: Returns a formatted string containing a list of books with a specified title.
loanBook: Attempts to loan a book from the library based on the provided author's name and title. Returns a boolean indicating whether the operation was successful.
returnBook: Marks a book as returned in the library based on the provided author's name and title. Returns a boolean indicating whether the operation was successful.


Input Format:
The program reads commands from a user-provided input file, with each command on a separate line.


Valid commands include:
ADD: Adds a new book to the library.
SEARCHA: Searches for books by author's last name.
SEARCHT: Searches for books by title.
GETBOOK: Attempts to borrow a book from the library.
RETURNBOOK: Returns a book to the library.


Usage:
To use the library management system, provide an input file containing commands as specified above. Run the application class to process the commands and observe the results.
