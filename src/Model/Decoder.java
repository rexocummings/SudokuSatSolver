package Model;

/**
 * The Model.Decoder class takes the CNF file and converts
 * it into a readable solution
 *
 * Created by Chris, Connor and Rex on 11/30/2016.
 */
public class Decoder {

    // The solution array returned by SAT4J
    private int[] array;
    private Puzzle puzzle;

    /**
     * Model.Decoder Constructor that takes the solution
     * array given by SAT4J
     *
     * @param puzzle :  The puzzle that is currently
     *                  being worked on
     * @param array :   The solution array output by SAT4J
     */
    public Decoder(Puzzle puzzle, int[] array){
        this.array = array;
        this.puzzle = puzzle;
    } // End Deconder constructor

    /**
     * Searches the array solution generated by SAT4J
     * for the values that answer the given sudoku
     * puzzle and adds the answers to a readable
     * version of the puzzle
     */
    public void findSolutionValues(){

        int row;
        int col;
        int value;
        int[][] grid = puzzle.getGrid();

        // Takes the non-negative ints from the
        //  solution array and breaks them into
        // row, column, value then adds the
        // value to the puzzle grid
        // at grid[row][col]
        for(int i = 0; i < array.length; i++){
            if(array[i] > 0){
                value = array[i] % 10;
                col = array[i]/10 % 10;
                row = array[i]/100 % 10;
                grid[row-1][col-1] = value;
            } // End if
        } // End i value for loop

        // Set the puzzle's grid to the now
        // fully solved grid
        puzzle.setGrid(grid);

    } // End findSolutionValues()
} // End Model.Decoder class
