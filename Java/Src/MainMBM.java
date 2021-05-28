/**
 * <h1>MainMBM Class</h1>
 * <h2>Driver code for the program</h2>
 * @author Olivia Anastassov and Nikki Kirk
 * @version 1.0
 * @since 5/9/2021
 */

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics2D;

public class MainMBM extends Frame{
	private static GraphicalGraph gGraph;
    private static final long serialVersionUID = 1L;
	private static int IMAGESIZEX = 400;
	private static int IMAGESIZEY = 500;
	private static int PLOTRANGE = 100;
	private static int SCALE = IMAGESIZEX/PLOTRANGE;
	private static ArrayList<DEdge> pairs;

//------------------------------------- 
// Constructors 
//-------------------------------------
	public MainMBM(){
		super("Graph Demonstration");
		prepareGUI();
	}

	public void prepareGUI(){
		setSize(IMAGESIZEX,IMAGESIZEY);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}        
		});
	}

//------------------------------------- 
// Drawing Method
//-------------------------------------
	@Override
	public void paint(Graphics g) {	
		// in order to use the Point2D object
		Graphics2D g2 = (Graphics2D) g;	
		gGraph.draw(g2);	
	}

//------------------------------------- 
// Main Driver
//-------------------------------------
    public static void main(String[] args) {
		//-----------------------------
		// Input and output files
		// taken from the command line
		//-----------------------------
		String inputFilePath = args[0];
		String inputGraphicsFilePath = args[1];
		String outputFileFolder = args[2];
		StringBuilder output = new StringBuilder();
        
		if (args.length < 3){	
			return;
		}


        String absOutputTreFilePath = ProjectUtils.makeAbsoluteOutputFilePathWithExt(inputFilePath,outputFileFolder,"mbm");
		String testest  = ProjectUtils.makeAbsoluteOutLogFilePath(outputFileFolder);
		
		

		output.append("======  STEP 1: READ GRAPH FROM TXT FILE\n");
		DGraphEdges dGraph = new DGraphEdges();
		dGraph.readFromTxtFile(inputFilePath);

		output.append("\n======  STEP 2: PRINT GRAPH \n");
		output.append(dGraph.toString() + "\n");

		output.append("\n======  STEP 3: CONVERT TO AN ADJACENCY MATRIX \n");
		DGraphMatrix dGraphMatrix = dGraph.toMatrix();

		output.append("\n======  STEP 4: PRINT ADJACENCY MATRIX \n");
		output.append(dGraphMatrix.toString());

		output.append("\n======  STEP 5: COMPUTE MAXIMUM BIPARTITE MATCHING\n");
		MBMDataStructures MBMDS = dGraphMatrix.fordFulkerson();

		output.append("\n======  STEP 6: PRINT MAXIMUM BIPARTITE MATCHING WITH EDGES\n");
        output.append(MBMDS.maxFlowToString());
		output.append(MBMDS.pairsToString());
        pairs = MBMDS.getPairs();

		output.append("\n======  STEP 7: CREATE GEOMETRIC GRAPH FROM TXT FILE \n");		
        gGraph = new GraphicalGraph(inputGraphicsFilePath);	
		gGraph.setPairs(pairs);
	
		output.append("\n======  STEP 8: RESCALE AND PRINT GRAPH THROUGH JAVA GRAPHICS\n ");	
		gGraph.rescalePoints(SCALE);
		MainMBM frame = new MainMBM();
		frame.setVisible(true);	

		output.append("\n======  STEP 9: SEND OUTPUT TO TXTLOG FOLDER\n ");
		String log = output.toString();
		ProjectUtils.writeToTextFile(testest, log);
	}
}
