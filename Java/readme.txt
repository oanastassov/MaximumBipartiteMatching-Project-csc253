Olivia Anastassov and Nikki Kirk
CSC253 - Applied Algorithms
Final Project
5/1/2021

Updates:--------------------------------------------------------------------------------------
5/11/2021
	Olivia:
Handled the input data for the program
Created a framework for everything we would need for this program
Handled the processing of files needed for input
Implemented BFS to be used in the Ford-Fulkerson
Handled the output data for the program

	Nikki: 
Implemented the Ford-Fulkerson Algorithm 
Handled the processing of files needed for output
Handled the output visualization with Java Graphics
Handled documentation with Javadocs

Program Contents:-----------------------------------------------------------------------------
Data --> Folder containing the read/write data
	MBMTxt --> Input data
	MBMGraphicsTxt --> Graphics input data
	GrfMBM --> Outputed data as Mathematica files
	TxtLogs --> System output written to a text log


Java --> Contains Program Files
	UnitTests --> Contains instructions for running program
	Classes --> Folder containing the .class files 
	Src --> Contains all files needed for the program to run
		DEdge.java --> Structure to create a directed edge for Edge List Representation
		DGraphEdges.java --> Contains methods to create and manipulate a graph
		DGraphMatrix.java --> Functions for Adjacency Matrix Representation of the graph
		GraphicalGraph.java --> Contains methods to create and manipulate a graph using Java Graphics
		MBMMain.java --> Main program driver
		MBMDataStructures.java --> Contains data structures used for the Maximum Bipartite Matching problem
		ProjectUtils.java --> Contains various misc methods used throughout the program

javadocs --> Contains documentation for the entire program
	index.html --> Visit this link to bring up the complete webpage


What does this program do?:- ------------------------------------------------------------------
Given a input file containing a bipartite graph, the program finds the maximum number of 
matches possible between two sets of vertices.

How to Run: -----------------------------------------------------------------------------------
Please refer to run.txt located inside of FinalProject/Java/UnitTests

Known Bugs: ----------------------------------------------------------------------------------- 

We have a method under development to handle cases where the vertices inputted are "0". If this is 
the case, then we obviously cannot run the program effectivly. 