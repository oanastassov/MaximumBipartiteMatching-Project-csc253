/**
 * <h1>MBMDataStructures Class</h1>
 * <h2>contains data structures used for Maximum Bipartite Matching</h2>
 * @author Olivia Anastassov and Nikki Kirk
 * @version 1.0
 * @since 5/2/2021
 */


import java.util.*;
import java.io.*;

public class MBMDataStructures{
    private int nrV;
    public int nrLeftVertices;
    public int nrRightVertices;
    public int [] parent;
    public boolean [] visited;
    public int maxFlow;
    public int [][] residualGraph;//residualGraph
    public boolean foundPath;
    public ArrayList<DEdge> pairs;
    
    final static boolean DEBUG = false;

//------------------------------------- 
// Constructors 
//-------------------------------------
public MBMDataStructures(int nrV, int nrLeftVertices, int nrRightVertices){
    this.nrV = nrV;
    this.nrLeftVertices = nrLeftVertices;
    this.nrRightVertices = nrRightVertices;
    this.parent = new int [nrV+2];
    this.visited = new boolean[nrV+2];
    this.maxFlow = 0;
    this.residualGraph = new int [nrV+2][nrV+2];
    this.foundPath = false;
    this.pairs = new ArrayList<DEdge>();
}

//-------------------------------------
// Getters
//-------------------------------------
//      getPairs
//-------------------------------------
    public ArrayList<DEdge> getPairs(){
        return this.pairs;
    }

//-------------------------------------
// Serialization and Print
//-------------------------------------
//      printResidual
//      MaxFlowToString
//-------------------------------------
    /**
     * Method used to print the residualGraph created
     * by running the fordFulkerson method
     */
    public void printResidual(){
        for(int i = 0; i< residualGraph.length; i++){
            for(int j = 0; j< residualGraph[i].length; j++){
                System.out.print(residualGraph[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Method used to convert int maxFlow to a string
     * @return - maxFlow as a string
     */
    public String maxFlowToString(){
        String output = "";
        output += "Maximum Matchings: " + maxFlow + "\n";
        return output;
    }

    /**
     * Method used to convert pairs to a string
     * @return - pairs as a string
     */
    public String pairsToString(){
        String output = "";
        for(int i = 0; i < pairs.size(); i++){
            output = output + pairs.get(i).getVertex1() + " " + pairs.get(i).getVertex2();
            output = output + "\n";
        }
        return output;
    }

    /**
     * Method used to print the max flow as a string
     * {@link #maxFlowToString()}
     */
    public void printMaxFlow(){
        System.out.print(maxFlowToString());
    }
}