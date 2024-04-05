```
Project Description: Maze Pathfinding<br>
In this project, we aim to develop a program that reads a maze from a file and attempts to find a path through it using two different search algorithms: one using a stack and the other using a queue. The primary goal is to implement depth-first and breadth-first searches, akin to real-world maze navigation scenarios.<br>
<br>
Maze Representation:<br>
The maze is represented as a grid, where each cell can either be a path (denoted by a '.'), a wall (denoted by a '#'), the starting point (denoted by 'S'), or the finish line (denoted by 'F'). When a path is found, it will be marked with an asterisk ('*') from the start to the finish line.<br>
<br>
Approach to Path Finding:<br>
Both search algorithms involve traversing the maze while maintaining a data structure (stack or queue) containing positions to explore. The process includes marking visited positions and updating the maze object accordingly. The pseudocode for both searches is provided below:<br>

Add start position to data structure<br>
Mark start position as visited<br>
while (data structure is not empty):<br>
    current = position removed from data structure<br>
    if (current is the finish):<br>
        exit search
    for (each neighbour of current that is an unvisited path):
        Mark neighbour as visited
        Record neighbour as visited from current
        Add neighbour to the data structure
Solution Components:
The solution must include several classes, each serving a specific purpose:

Position Class: Represents a location in the maze, storing information such as row and column numbers, type of square, and whether it has been visited.

Stack & Queue Classes: Implement data structures for storing positions using either an array or linked list implementation.

Maze Class: Represents the maze itself, consisting of a 2D array of positions. It includes methods for reading the input file, initializing the maze, and implementing the search algorithms.

Application (Main) Class: Responsible for orchestrating the overall process, including creating the maze, attempting to solve it using both stack and queue methods, and printing the results.

Conclusion:
By implementing this maze pathfinding project, we aim to develop a deeper understanding of search algorithms, data structures, and object-oriented programming principles. The provided pseudocode serves as a guide for implementing the search algorithms effectively and efficiently.
```
