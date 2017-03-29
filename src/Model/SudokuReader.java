package Model;

import Model.Cell;
import Model.Puzzle;

import java.util.ArrayList;
/**
 * The Model.SudokuReader class takes the original puzzle
 * input and converts it into a cnf output so it can
 * be solved with SAT4J
 *
 * Created by Chris, Connor and Rex on 11/30/2016.
 */
public class SudokuReader {

    // ArrayList of Cells for known values
    private ArrayList<Cell> cells = new ArrayList<>();

    // The string that will become the
    // CNF file
    private StringBuffer output;

    // The puzzle object being worked on
    private Puzzle puzzle;

    // Number if default clauses for
    // EVERY 3x3 sudoku
    private int numOfClauses = 11988;

    /**
     * The constructor for a Model.SudokuReader
     *
     * @param output :the StringBuffer that will be filled
     *                with all of the clauses needed to
     *                correctly solve the puzzle, then made
     *                into a .cnf file at a later point
     */
    public SudokuReader(Puzzle puzzle, StringBuffer output) {
        this.output = output;
        this.puzzle = puzzle;
    }

    /**
     * Gets the already filled in values of the
     * sudoku puzzle and makes an array of cells
     * that contains the row location and column
     * location of the value as well as the value
     * itself
     */
    private void findGivenValues(){

        // The incomplete puzzle grid
        int[][] grid = puzzle.getGrid();

        // For each cell of the given puzzle,
        // check it to see if there is a value
        // other than zero (not blank)
        // If so add a cell object to an arrayList
        // with the row, column position of the value
        // and the value itself
        for(int r = 0; r < puzzle.getSize() ; r++){
            for(int c = 0; c < puzzle.getSize(); c++){
                if(grid[r][c] != 0){
                    cells.add(new Cell(r + 1,c + 1, grid[r][c]));
                } // End if
            } // End column value for loop
        } // End row value for loop

    } // End findGivenValues()

    /**
     * Starts the beginning of the CNF file with the needed
     * lines and info, then it adds the known value clauses
     */
    public void generateCNF(){

        // Calls the findGivenValues method
        findGivenValues();

        // Gets total number of clauses by adding the amount
        // of clauses about to be generated from the given
        // values to the default amount of clauses
        numOfClauses = numOfClauses + cells.size();

        // Generates the p line needed for SAT4J Solver
        output.append("p cnf 999 " + numOfClauses + "\n");

        // Loops through the array of given values and generates
        // the clauses that set the already known values in the
        // sudoku puzzle to true
        for(Cell cell : cells){
            output.append(cell.getRow() + "" + cell.getCol() + "" + cell.getValue() + " 0\n");
        } // End for each loop
    } // End generateCNF()

} // End Model.SudokuReader class
