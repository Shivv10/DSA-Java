~~~
Dictionary Implementation
In this project, we will develop three different implementations of a dictionary and compare their performance in terms of filling and searching. The first implementation will utilize an ordered array, while the second and third implementations will employ hash tables. The second dictionary will use open addressing with double hashing to resolve collisions, while the third will utilize separate chaining.

Application (Main) Class:

The application class orchestrates the creation, filling, and searching of dictionaries.
It reads input from "GreatExpectations.txt" and builds dictionaries with the goal of storing all words from the file.
Each line in the input file is split into tokens, and each token is added to the dictionary.
Punctuation is stripped, and all words are converted to lowercase to ensure uniqueness.
Initially, empty dictionaries of each type are created with an initial size of 100, which is adjusted as needed during execution.
The output includes the size of each dictionary and the number of words found during the search.
Dictionary Classes:

Three dictionary classes are implemented: DictionaryOrdered, DictionaryOpen, and DictionaryChain.
Each class has the following public methods:
Constructor: Accepts an integer indicating the initial size of the dictionary.
getSize(): Returns the number of words in the dictionary.
insert(String newWord): Inserts a word into the dictionary, preventing duplicates.
search(String wordToFind): Searches for a word in the dictionary and returns true if found, false otherwise.
Implementation details are hidden from the user, and access to dictionary contents is only via these public methods.
Details Specific to Each Implementation:

Ordered Array Dictionary:

Constructor accepts the initial size of the array.
Doubles the size of the array when it becomes full.
Utilizes non-recursive binary search for efficient search operations.
Open Addressing Dictionary:

Constructor accepts the initial size, ensuring the hash table size is a prime number.
Utilizes Horner's method for hashing with double hashing to resolve collisions.
Enlarges the array and rehashes words when the hash table is more than 60% full.
Separate Chaining Dictionary:

Constructor ensures the hash table size is a prime number.
Utilizes Horner's method for hashing.
Enlarges the hash array when the load factor exceeds 2.
By implementing these specifications, we aim to analyze the performance of different dictionary implementations and their suitability for various applications, considering factors such as fill time, search time, and memory efficiency.
~~~
