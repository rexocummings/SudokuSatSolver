package Controller;

import Model.*;
import org.sat4j.specs.*;
import org.sat4j.minisat.*;
import org.sat4j.reader.*;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * Controller.SudokuDriver class
 * Created by Chris, Connor and Rex on 11/30/2016.
 *
 * Controller.SudokuDriver contains the main of the program of which to run
 */
public class SudokuDriver {

    /**
     * main is the main method in the driver class that
     * starts the entire program.
     */
    public static void main(String[] args) throws IOException{

        // Initializes space to store the time the program finished
        // and the total time the program ran from start of solving
        // the solution or rejection of the puzzle.
        long endTime;
        long totalTimeMS;
        long hours;
        long minutes;
        long seconds;
        long milli;

        ParseCmdLine parse = new ParseCmdLine();
        Puzzle puzzle1 = new Puzzle(parse.getArgs(args));

        // Fills a 2D array with inputs from a file
        // to create sudoku puzzle to solve
        puzzle1.populatePuzzle();

        System.out.println("This is the original Sudoku puzzle: ");
        puzzle1.displayPuzzle();
        System.out.println();

        // StringBuffer will be filled with all of the
        // clauses needed to solve the puzzle and
        // added to a CNF file
        StringBuffer output = new StringBuffer();

        // Reads the puzzle for already given values
        SudokuReader sr = new SudokuReader(puzzle1, output);

        // Initializes the StringBuffer with the first
        // set of CNFs needed for the file
        sr.generateCNF();

        // Creates an Model.Encoder object that will turn
        // the puzzle into a bunch of CNFs
        Encoder encoder = new Encoder(puzzle1, output);

        // Generates all of the CNFs that will give
        // constraints to solve the puzzle and adds
        // them to the StringBuffer
        encoder.generatePuzzleContraints();

        // Removes the last \n added to the
        // StringBuffer
        output.deleteCharAt(152319);

        // Generates the final version if the
        // CNF and makes it a file
        encoder.generateCNFFile();

        // Stores the time the program starts solving the Sudoku Model.Puzzle
        long startTime = System.currentTimeMillis();

        System.out.println("Attempting to Solve...");

        ISolver solver = SolverFactory.newDefault();
        solver.setTimeout(3600); // 1 hour timeout
        Reader reader = new DimacsReader(solver);
        // CNF filename is given on the command line
        try {
            IProblem problem = reader.parseInstance(System.getProperty("user.home") + "\\cnfPuzzle.cnf");
            if (problem.isSatisfiable()) {
                System.out.println("Satisfiable !");

                // Time the program finishes solving
                endTime = System.currentTimeMillis();

                // Creates a decoder object that will turn the SAT4J
                // solution into a readable solution
                Decoder decoder = new Decoder(puzzle1, problem.model());

                // Goes through the solution array returned
                // by SAT4J and finds the answers for
                // each cell and plugs them into the
                // puzzle grid
                decoder.findSolutionValues();

                // Displays the solved puzzle
                puzzle1.displayPuzzle();

                // Calculated total time it took to solve the sudoku
                totalTimeMS = endTime - startTime;
                milli = totalTimeMS%1000;
                seconds = (totalTimeMS/1000)%60;
                minutes = (totalTimeMS/(60000))%60;
                hours = (totalTimeMS/(36000000))%24;
                String totalTime = hours + "h " + minutes + "m " + seconds + "s " + milli + "ms";
                System.out.println("Solved in: " + totalTimeMS + "ms (" + totalTime + ")\n" );

                System.out.println("*******************************************************************************************");
                System.out.println("********** MAKE SURE TO GO TO " + System.getProperty("user.home") + " AND DELETE cnfPuzzle.cnf WHEN DONE! **********");
                System.out.println("*******************************************************************************************");
            } else {
                System.out.println("Unsatisfiable !");

                // Time the program finishes solving
                endTime = System.currentTimeMillis();

                // Calculated total time the program took to reject the sudoku
                totalTimeMS = endTime - startTime;
                milli = totalTimeMS%1000;
                seconds = (totalTimeMS/1000)%60;
                minutes = (totalTimeMS/(60000))%60;
                hours = (totalTimeMS/(36000000))%24;
                String totalTime = hours + "h " + minutes + "m " + seconds + "s " + milli + "ms";
                System.out.println("No Solution found after: " + totalTimeMS + "ms (" + totalTime + ")\n" );

                System.out.println("*******************************************************************************************");
                System.out.println("********** MAKE SURE TO GO TO " + System.getProperty("user.home") + " AND DELETE cnfPuzzle.cnf WHEN DONE! **********");
                System.out.println("*******************************************************************************************");
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ContradictionException e) {
            System.out.println("Unsatisfiable (trivial)!");
        } catch (TimeoutException e) {
            System.out.println("Timeout, sorry!");
        }
    }
}