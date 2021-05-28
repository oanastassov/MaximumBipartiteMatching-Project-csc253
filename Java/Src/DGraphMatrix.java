/**
 * <h1>DGraphMatrix Class</h1>
 * <h2>Methods used by the adjacency matrix representation</h2>
 * <h2>Contains methods to run Maximum Bipartite Matching</h2>
 * @author Olivia Anastassov and Nikki Kirk
 * @version 1.0
 * @since 5/2/2021
 */

import java.util.*;

class DGraphMatrix{
    private int [][] graph;
    private int[][] weights;
    private int nrV;
    private int nrLeftVertices;
    private int nrRightVertices;

//------------------------------------- 
// Constructors 
//-------------------------------------
    public DGraphMatrix(int numVert, int nrLeftVertices, int nrRightVertices){
        graph = new int[numVert+2][numVert+2];//add 2 for source and sink
        weights = new int[numVert+2][numVert+2];
        this.nrV = numVert;
        this.nrLeftVertices = nrLeftVertices;
        this.nrRightVertices = nrRightVertices;
        for (int i = 0; i < numVert+2; i++) {
            for (int j = 0; j < numVert+2; j++) {
                weights[i][j] = 0;
            }
        }
    }

//------------------------------------- 
// Testers
//-------------------------------------
//      hasEdge
//-------------------------------------
    /**
     * Method used to check if the graph contains a edge
     * @param u - first vertex
     * @param v - second vertex
     * @return - returns true if graph contains the edge
     */
    public boolean hasEdge(int u, int v){
        if(graph[u][v] == 1)
            return true;
        else
            return false;
    }

//------------------------------------- 
// Modifiers
//-------------------------------------
//      addEdge
//-------------------------------------
    /**
     * Method used to add an edge to the matrix representation
     * @param u - first vertex
     * @param v - second vertex
     * @param weight - edge weight
     */
    public void addEdge(int u, int v, int weight){
        graph[u][v] = 1;
        weights[u][v] = weight;
    }

//------------------------------------- 
//Helper
//-------------------------------------
//      LeftHandSideNodes
//      ConnectToSourceSink
//-------------------------------------
    /**
     * Method used to compile a list of "applicants" for
     * maximum bipartite matching problem
     * @return - ArrayList of "applicant" vertices
     */
    public ArrayList<Integer> LeftHandSideNodes(){
        ArrayList<Integer> LHSN= new ArrayList<Integer>();
        for(int i = 0; i < nrV; i++){
            boolean isOnLeftSide = false;
            for(int j = 0; j < nrV; j++){
                if(graph[i][j] == 1){//gets number at the index
                    isOnLeftSide = true;
                }
            }
            if(isOnLeftSide){
                LHSN.add(i);
            }
        }
        return LHSN;
    }

    /**
     * Method used to connect the source and sink vertices
     * to the "applicant" and "job" vertices
     */
    public void ConnectToSourceSink(){
        ArrayList<Integer> LHSN = LeftHandSideNodes();
        for(int i = 0; i < nrV; i++){
            if(LHSN.contains(i)){
                addEdge(nrV, i, 1);
            }
            else{
                addEdge(i, nrV+1, 1);
            }
        }
    }
    
//------------------------------------- 
// Algorithms
//-------------------------------------
//      BFS
//      fordFulkerson
//-------------------------------------
    /**
     * Method used to confirm if there is a path from source to sink
     * while also updating parent[] and visited[] 
     * @param MBMDS - MBMDataStructure containing required information
     * @see https://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/
     * @return - returns true if there is a path between source and sink
     * @author Method derived from GeeksForGeeks
     */
    public MBMDataStructures BFS(MBMDataStructures MBMDS){
        for(int i = 0; i < graph.length; i++){
            MBMDS.visited[i] = false;
        }
        LinkedList<Integer> remaining = new LinkedList<Integer>(); // verts who's children need to be visited eventually 
        remaining.add(nrV);
        MBMDS.visited[nrV] = true;
        MBMDS.parent[nrV] = -1;
        
        // while there are verts who's children need to be visited eventually
        while(!remaining.isEmpty()){  
            // Remove the front vertex from remaining
            int u = remaining.poll(); 
            for(int i = 0; i < graph.length; i++){
                // residualGraph ensures the flow is moving from source to sink
                if(MBMDS.visited[i] == false && MBMDS.residualGraph[u][i] > 0){ 
                    if(i == nrV+1){
                        MBMDS.parent[i] = u;
                        MBMDS.foundPath = true; // found a path from the source to the sink
                        return MBMDS; 
                    }
                    remaining.add(i);
                    MBMDS.parent[i] = u; // parent of v is u and u is now the parent
                    MBMDS.visited[i] = true;
                } 
            }
        }
        MBMDS.foundPath = false; // didn't find anything
        return MBMDS;
    }

    /**
     * Method used to find the maximum flow from source to sink
     * @return  returns the updated MBMDataStructure
     * @author Method derived from GeeksForGeeks 
     * @see https://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/
     * {@link #BFS(MBMDataStructures)}
     * {@link #ConnectToSourceSink()}
     */
    public MBMDataStructures fordFulkerson(){
        //Connects the source and sink nodes to the graph
        ConnectToSourceSink();
        MBMDataStructures MBMDS = new MBMDataStructures(nrV, nrLeftVertices, nrRightVertices);

        // Initalize the residual graph to our current graph
        for(int i = 0; i < graph.length; i++){
            for(int v = 0; v < graph[i].length; v++){
                MBMDS.residualGraph[i][v] = graph[i][v];
            }
        }

        //Agument the flow while there is a path from source to sink
        MBMDS = BFS(MBMDS);
        while(MBMDS.foundPath){
            int pathFlow = Integer.MAX_VALUE; 
            for(int v = nrV + 1; v != nrV; v = MBMDS.parent[v]){
                int u = MBMDS.parent[v];
                // take smallest residual capacity and set it to be path flow
                pathFlow = Math.min(pathFlow, MBMDS.residualGraph[u][v]); 
            }

            //update the residual capacities and reverse the edges in the path
            for(int v = nrV + 1; v != nrV; v = MBMDS.parent[v]){
                int u = MBMDS.parent[v];
                MBMDS.residualGraph[u][v] -= pathFlow;
                MBMDS.residualGraph[v][u] += pathFlow;
            }

            //Add the path to the maxFlow
            MBMDS.maxFlow += pathFlow;

            // updates MBMDS so we don't find the same path again
            MBMDS = BFS(MBMDS); 
        }

        // only edges that will remain in the residual graph will be the final
		// edges - they will have 1 on them, therefore we can add them to the
		// output arraylist (pairs) to print out
        for(int i = 0; i < nrV; ++i){
        	for(int j = 0; j < i; ++j){
        		if(MBMDS.residualGraph[i][j] == 1){
			        MBMDS.pairs.add(new DEdge(j,i));
                }
            }
        }
        return MBMDS;
    }

//------------------------------------- 
// Serialize and Print
//-------------------------------------
//      toString
//      print
//-------------------------------------
    /**
     * Method used to convert matrix to a string representation
     * @return matrix as a string
     */
    public String toString(){
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < graph.length; i++) {
            //first row add labels
            if(i == 0){
                output.append("X ");
                for(int a = 0; a < graph.length; a++){
                    output.append(a + " ");
                }
                output.append("\n");
            }
            //print row label then row
            output.append(i + " ");
            for (int j = 0; j < graph.length; j++) {
                output.append(graph[i][j] + " ");
            }
            output.append("\n");
        }
        return output.toString();
    }

    /**
     * Method used to print the matrix to the console
     * {@link #toString()}
     */
    public void print(){
        System.out.print(toString());
    }
}