/**
 * <h1>ProjectUtils Class</h1>
 * <h2>Utility methods used throughout the package</h2>
 * @author Olivia Anastassov and Nikki Kirk
 * @version 1.0
 * @since 5/9/2021
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.geom.*;

public class ProjectUtils{
    static boolean debug = false; 

	/**
	 * Method used to print debugging statement to the console
	 * @param statement debugging statement to be printed to the console
	 */
	public static void printDebug(String statement){
		if(debug){
			System.out.println(statement);
		}
	}

	/**
     * Method used to convert to indexing beginning with 1
     * @param n - int to convert
     * @return - returns int + 1
     */
	public static int toMathId(int n){
        return n+1;
    }

	/**
	 * Method used to convert integer to javaID (0-based indexing)
	 * @param n the integer to convert
	 * @return integer - 1
	 */
	public static int toJavaId(int n){
        return n-1;
    }

	/**
	 * Method used to generate a random point for java graphics
	 * @param range max value
	 * @return point
	 */
	public static Point2D makeRandomPoint2D(int range){
		int x = (int)(Math.random() * (range+1)); 
		int y = (int)(Math.random() * (range+1)); 
		Point2D pt = new Point2D.Double(x,y);
		
		return pt;
	}

	/**
	 * Method used to generate random points for java graphics
	 * @param range max value
	 * @return list of random points
	 * {@link #makeRandomPoint2D(int)}
	 */
	public static Point2D[] makeRandomPoints2D(int range, int numberOfPoints){
		Point2D[] pts = new Point2D[numberOfPoints];

		for (int i=0; i < numberOfPoints; i++){
			pts[i] = makeRandomPoint2D(range);
		}
		return pts;
	}

	/**
	 * Method used to create the input file path
	 * @param inputFilePath path for the input file
	 * @return input file path as a string
	 */
	public static String makeAbsoluteInputFilePath(String inputFilePath){
		File input = new File(inputFilePath);
		String absInputFilePath = "";
		try{
			absInputFilePath= input.getCanonicalPath();
		} catch(IOException e) {
			System.out.println("ERROR: cound not run getCanonicalPath on file "+inputFilePath);
		}
		return absInputFilePath;
	}

	/**
	 * Method used to direct output log to the Logtxt folder
	 * @param outputPath current outputPath
	 * @return new outputPath as a string
	 */
	public static String makeAbsoluteOutLogFilePath(String outputPath){
		String output = outputPath.substring(0, 10);
		output += "/TxtLogs/";
		return output;
	}

	/**
	 * Method used to create the input file path with a specific extension
	 * @param inputFilePath path for the input file
	 * @param ext specific extension to add to the file path
	 * @return input file path with specified extension as a string
	 */
	public static String makeAbsoluteInputFilePathWithExt(String inputFilePath, String ext){
		File input = new File(inputFilePath);
		String inputFileFolder = input.getParent();
		String fileName = input.getName();
		String name = fileName.split("\\.")[0];
			
		// make output file path with extension
		String newInputFilePath = inputFileFolder + "/" + name + "." + ext;
		File newInput= new File(newInputFilePath);
		String absNewInputFilePath = "";
		try{
			absNewInputFilePath = newInput.getCanonicalPath();
		} 
		catch(IOException e){
			System.out.println("ERROR: cound not run getCanonicalPath on file "+newInputFilePath);
		}
		
		return absNewInputFilePath;
	}	

	/**
	 * Method used to create the output file path with a specific extention
	 * @param inputFilePath path for the input file
	 * @param outputFileFolder path for the output folder
	 * @param ext specific extension to be added to the file path
	 * @return output file path with the specified extension as a string
	 */
	public static String makeAbsoluteOutputFilePathWithExt(String inputFilePath, String outputFileFolder, String ext){
		File input = new File(inputFilePath);
		String fileName = input.getName();
		String name = fileName.split("\\.")[0];
		// make output file path with extension
		String outputFilePath = outputFileFolder + "/" + name + "." + ext;
		File output= new File(outputFilePath);
		String absOutputFilePath = "";
		try{
			absOutputFilePath = output.getCanonicalPath();
		} 
		catch(IOException e){
			System.out.println("ERROR: cound not run getCanonicalPath on file "+outputFilePath);
		}
		return absOutputFilePath;
	}
	
	/**
	 * Method used to copy a file
	 * @param inputFilePath path for the input file
	 * @param outputFilePath path for the output file
	 * @throws IOException
	 */
	public static void copyFileUsingStream(String inputFilePath, String outputFilePath) throws IOException {
		File source = new File(inputFilePath);
		File dest = new File(outputFilePath);
		InputStream is = null;
	    OutputStream os = null;
	    try{
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	    	}
	    } 
		finally{
	        is.close();
	        os.close();
	    }
	}

	/**
     * Method to check if a file exisits within a given directory
     * @return Returns true if it exisits
     */
    public static Boolean fileExisits(String fileName, String directory){
        //Set the new location to the directory + file name
        String location = directory + fileName;
        Path path = Paths.get(location);

        if(Files.exists(path)){
            return true;
        }
        return false;
    }

	 /**
     * Method used to write data to a text file
     * @param filename  name of the file to write to
	 * @param output string to be written to the file
	 * {@link #fileExisits(String, String)}
     */
    public static void writeToTextFile(String outputDirectory, String output){
        try{
			String fileName = "output.txt";
			int i = 0;
            int index = fileName.lastIndexOf(".");
			//Check to see if file exists
            while(fileExisits(fileName, outputDirectory)){
                i++;
                if(i == 1){
                    //Warn user that file exisits
                    System.out.println("NOTE: " + fileName +  " already exisits.");
                }
                fileName = fileName.substring(0, index);
                //Rename file with trailing int
                fileName = fileName + i + ".txt";
            }

			File file = new File(outputDirectory, fileName);
            FileWriter myWriter = new FileWriter(file); 
            myWriter.write(output);
            myWriter.close();
        } 
        catch(IOException e) { 
            System.out.println("An error occurred."); 
            e.printStackTrace();
        } 
    }
	
}
