Train Modelling with Linked List <br>
This project involves modelling a train using a doubly-linked list, where each node represents one train car. The program reads commands from an input file, processes them, and performs the requested operations on the train. The input file consists of lines of commands, each possibly followed by lines of data. <br>

Classes Overview: <br>
TrainCar Class: Represents a train car storing information about the type of cargo and its value. If the car is an engine, the value is set to $0. <br>

Node Class: Represents a node in the doubly-linked list, storing links to the previous and next nodes, as well as a TrainCar object. <br>

Train Class: The linked list class representing the entire train. It contains methods to add cars to the train, remove cars, and print the train. <br>

Application/Main Class: Responsible for processing the input file, executing operations on the train, and displaying results. <br>

Program Input: <br>
The input file contains commands that the program should execute. Possible commands include: <br>

PICKUP [num]: Adds the specified number of train cars to the train. Each car is listed separately on a following line, specifying the type of cargo and its value. <br>

PRINT: Displays the entire train, including engines. It prints the number of cars in the train and the total value of the cargo, followed by the list of cars from front to back. <br>

DROPLAST [num]: Removes the last specified number of cargo cars from the train. Engines are never dropped. If the specified number is larger than the number of cargo cars on the train, it prints the number actually dropped. <br>

DROPFIRST [num]: Removes the first specified number of cargo cars from the train, excluding engines. If the specified number is larger than the number of cargo cars on the train, it prints the number actually dropped. <br>

DROP [type] [num]: Removes the first specified number of cars that contain the specified type of cargo. If fewer than the specified number of cars of the specified type exist, it prints the number actually dropped. <br>

Usage: <br>
To use the train modelling system, provide an input file containing commands as specified above. Run the application/main class to process the commands and observe the results. <br>






