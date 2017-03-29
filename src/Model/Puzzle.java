package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Model.Puzzle class deals with creating a puzzle
 * based on the text file read by the parser
 * and solving the puzzle by brute force and
 * checks to verify correctness.
 *
 * Created by Chris, Connor and Rex on 9/29/2016.
 */
public class Puzzle {

    protected String fileName = "";
    protected String comment = "";
    protected int width = 0;
    protected int height = 0;
    protected int size = width*height;
    protected int grid[][];

    /**
     * Default Constructor for puzzle object
     */
    public Puzzle(){
        
    }
    
    /**
     * Constructor for puzzle object
     */
    public Puzzle(String fileName){
        this.fileName = fileName;
    }

    /**
     * Model.Puzzle - Reads the input file and generates a 2D-Array of ints
     * that contains all of the numbers that make up the sudoku
     */
    public void populatePuzzle() {
        try {

            BufferedReader br = new BufferedReader(new FileReader(fileName));

            String line = "";

            if((line = br.readLine()) != null){
                if(line.startsWith("c")){
                	do{
                		
                		comment = comment + "\n" + line;
                		line = br.readLine();
                		
                	}while (line.startsWith("c"));
                		
                    try {
                        width = Integer.parseInt(line);
                        height = Integer.parseInt(br.readLine());
                        if ((width < 0) || (height < 0)) {
                            System.out.println("width and height need to be positive ints");
                            System.exit(0);
                        }
                    }
                    catch(NumberFormatException e) {
                        System.out.println("File needs row and column to be an integer.");
                        System.exit(0);
                    }

                    System.out.println("\nFile comment:" + comment.substring(1));

                }else{
                    try {
                        width = Integer.parseInt(line);
                        height = Integer.parseInt(br.readLine());
                        if ((width < 0) || (height < 0)) {
                            System.out.println("width and height need to be positive ints");
                            System.exit(0);
                        }
                    }
                    catch(NumberFormatException e){
                        System.out.println("File needs row and column to be an integer.");
                        System.exit(0);
                    }
                }
            }else{
                System.err.println("The file you are trying to ready is empty! Please try again with a different file.");
                System.exit(0);
            }

            System.out.println("The width is: " + width);
            System.out.println("The height is: " + height + "\n");
                        
            size = width * height;

            grid = new int[size][size];
                       
            for(int i = 0; i < grid.length; i++) {
                String[] rowOfNums = br.readLine().trim().split("\\s+");   // ("\\s+") if " " doesn't work
                for(int j = 0; j < grid.length; j++) {
                    grid[i][j] = Integer.parseInt(rowOfNums[j]);
                }
            }
            br.close();

        } catch(FileNotFoundException e) {
            System.err.println("Can not open the file: "+ fileName);
        } catch(IOException e) {
            System.err.println("Error reading the file: " + fileName);
        } catch(ArrayIndexOutOfBoundsException e) {
            System.err.println("The grid in the .txt file does not match dimensions.");
            System.exit(-1);
        }
    }

    /**
     * printRow is a helper function used to display the finished puzzle
     *
     * @param row a one dimensional array of ints that is an interior
     * array in the 2d array grid
     */
    public static void printRow(int[] row) {
        for (int i : row) {
            System.out.print(i);
            System.out.print("\t");
        }
        System.out.println();
    }

    /**
     * displayPuzzle prints out the puzzle in the console for the user to see.
     * Uses helper function printRow(int[] row)
     */
    public void displayPuzzle() {
        for(int[] row : grid)
            printRow(row);
    }

    /**
     * Getter and setter methods
     */
    public String getFileName() {
        return fileName;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSize() {
        return size;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
    }
}