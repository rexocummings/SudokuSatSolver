package Model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The Model.Encoder class generates the default clauses
 * needed to be a solvable sudoku and adds the clauses
 * to the cnf file.
 *
 * Created by Chris, Connor and Rex on 11/30/2016.
 */
public class Encoder {

    // Model.Puzzle we are working with
    Puzzle puzzle;

    // The overall size of the sudoku puzzle based
    // on the smaller box sizes 3x3 = 9
    private final int PUZZLE_SIZE = 9;

    // The size of an individual box
    private final int BOX_SIZE = 3;

    // CNF output string for "file"
    private StringBuffer output;


    /**
     * The file that will contain all
     *  of the clauses for the puzzle
     *
     * @param output : the string that will serve
     *               as the "file" with the cnf info
     */
    public Encoder(Puzzle puzzle, StringBuffer output){
        this.output = output;
        this.puzzle = puzzle;
    }

    /**
     * Generates all of the row constraints needed
     * by EVERY sudoku puzzle to be solvable
     */
    private void generateRowConstraints(){

        // Constraint 1.1.A etc
        // Each number must occur at least once in a row
        for(int k = 1; k <= PUZZLE_SIZE; k++){
            for(int i = 1; i <= PUZZLE_SIZE; i++){
                for(int j = 1; j <= PUZZLE_SIZE; j++){

                    // Adds clause for value ijk to
                    // the output file
                    output.append(i + "" + j + "" + k + " ");

                } // End j value for loop

                // Marks the end of clause line with
                // terminator symbol '0' and starts
                // a new line
                output.append("0\n");

            } // End i value for loop
        } // End k value for loop

        // Constraint 1.1.B etc
        // Each number must occur only once in a row
        for(int k = 1; k <= PUZZLE_SIZE; k++){
            for(int i = 1; i <= PUZZLE_SIZE; i++){
                for(int j = 1; j <= PUZZLE_SIZE; j++){

                    // Counter used to generate
                    // all possible combos for
                    // the clauses
                    int counter = 1;

                    for(int h = j; h < PUZZLE_SIZE; h++){

                        // Adds clauses for values -ijk or
                        // -i(j + ctr)k to output file
                        output.append("-" + i + "" + j + "" + k + " ");
                        output.append("-" + i + "" + (j + counter) + "" + k + " 0\n");
                        counter++;

                    } // End h value for loop
                } // End j value for loop
            } // End i value for loop
        } // End k value for loop

    } // End generateRowConstraints()

    /**
     * Generates all of the column constraints
     * needed by EVERY sudoku puzzle to
     * be solvable
     */
    private void generateColConstraints(){

        // Constraint 2.1.A etc
        // Each number must occur at least once in a col
        for(int k = 1; k <= PUZZLE_SIZE; k++){
            for(int j = 1; j <= PUZZLE_SIZE; j++){
                for(int i = 1; i <= PUZZLE_SIZE; i++){

                    // Adds clause for value ijk to
                    // the output file
                    output.append(i + "" + j + "" + k + " ");

                } // End i value for loop

                // Marks the end of clause line with
                // terminator symbol '0' and starts
                // a new line
                output.append("0\n");

            } // End j value for loop
        } // End k value for loop


        // Constraint 2.1.B etc
        // Each number must occur only once in a col
        for(int k = 1; k <= PUZZLE_SIZE; k++){
            for(int j = 1; j <= PUZZLE_SIZE; j++){
                for(int i = 1; i <= PUZZLE_SIZE; i++){

                    // Counter used to generate
                    // all possible combos for
                    // the clauses
                    int ctr = 1;

                    for(int h = i; h < PUZZLE_SIZE; h++){

                        // Adds clauses for values -ijk
                        // -hjk to output file
                        output.append("-" + i + "" + j + "" + k + " ");
                        output.append("-" + (i + ctr) + "" + j + "" + k + " 0\n");
                        ctr++;

                    } // End h value for loop
                } // End i value for loop
            } // End j value for loop
        } // End k value for loop

    } // End generateColConstraints()

    /**
     * Generates all of the box constraints needed
     * by EVERY sudoku puzzle to be solvable
     */
    private void generateBoxConstraints(){

        // Constraint 3.1.A etc & Constraint 3.1.B etc
        // Each number must occur at least once in a box
        // &
        // Each number must occur only once in a box

        // These two for loops go through the 3x3
        // boxes in a grid format
        for(int rowBox = 1; rowBox <= BOX_SIZE; rowBox++){
            for(int colBox = 1; colBox <= BOX_SIZE; colBox++){

                // Temporary array to hold the coord
                // values for the current cells
                // of the current box
                String[] temp = new String[PUZZLE_SIZE];

                // Max row value for current box
                int cellRowMax = rowBox  * BOX_SIZE;

                // Min row value for current box
                int cellRowMin = (rowBox - 1) * BOX_SIZE + 1;

                // Max column value for current box
                int cellColMax = colBox * BOX_SIZE;

                // Min column value for current box
                int cellColMin = (colBox - 1) * BOX_SIZE + 1;

                // Index pointer for the array entry
                // selection
                int index = 0;

                // Creates coord values and puts them into
                // the temporary array for the current box
                for(int i = cellRowMin; i <= cellRowMax; i++){
                    for(int j = cellColMin; j <= cellColMax; j++){
                        temp[index] = i + "" + j;
                        index++;
                    }
                }

                // Helper that generates the clauses using
                // the generated array
                boxConstraintHelper(temp);

            } // End colBox for loop
        } // End rowBox for loop

    } // End generateBoxConstraints

    /**
     * Generates all of the value constraints
     * needed  by EVERY sudoku puzzle to
     * be solvable
     */
    private void generateValueConstraints(){

        // Constraint 4.1
        // Each cell must contain at least one number
        for(int i = 1; i <= PUZZLE_SIZE; i++){
            for( int j = 1; j <= PUZZLE_SIZE; j++){
                for(int k = 1; k <= PUZZLE_SIZE; k++){

                    // Adds clause for value ijk to
                    // the output file
                    output.append(i + "" + j + "" + k + " ");

                } // End k value for loop

                // Marks the end of clause line with
                // terminator symbol '0' and starts
                // a new line
                output.append("0\n");

            } // End j value for loop
        } // End i value for loop


        // Constraint 4.2
        // Each cell must contain only one number
        for(int i = 1; i <= PUZZLE_SIZE; i++){
            for(int j = 1; j <= PUZZLE_SIZE; j++){
                for(int k = 1; k < PUZZLE_SIZE; k++){

                    for(int h = k + 1; h <= PUZZLE_SIZE; h++){

                        // Adds clauses for values -ijk or
                        // -ijh to output file
                        output.append("-" + i + "" + j + "" + k + " ");
                        output.append("-" + i + "" + j + "" + h + " 0\n");

                    } // End h value for loop
                } // End k value for loop
            } // End j value for loop
        } // End i value for loop

    } // End generateValueConstraints()

    /**
     * Generates all of the clause constraints that
     * are needed to be a solvable Sudoku Model.Puzzle
     * and adds these constraints to an output file
     */
    public void generatePuzzleContraints(){
        generateRowConstraints();
        generateColConstraints();
        generateBoxConstraints();
        generateValueConstraints();

    } // End generatePuzzleConstraints()

    /**
     * Takes the array of coords for a box and
     * appends the current value (1-9) being
     * used to generate the clause
     *
     * @param array : the array with the row column
     *                coords for current box
     */
    private void boxConstraintHelper(String[] array){

        for(int i = 1; i <= PUZZLE_SIZE; i++){
            for(int j = 0; j < PUZZLE_SIZE; j++){
                output.append(array[j] + "" + i + " ");
            } // End j value for loop

            // Marks the end of clause line with
            // terminator symbol '0' and starts
            // a new line
            output.append("0\n");

            for(int j = 0; j < PUZZLE_SIZE - 1; j++){
                for(int k = j + 1; k <= PUZZLE_SIZE - 1; k++){

                    output.append("-" + array[j] + i + " -" + array[k] + i + " 0\n");

                } // End k value for loop
            } // End j value for loop
        } // End i value for loop

    } // End boxConstraintHelper()

    /**
     * Generates a stored CNF file of all of the clauses
     * that is input into SAT4J to be solved
     *
     * @throws IOException : file couldn't be created
     */
    public void generateCNFFile() throws IOException{

        // Creates the cnfPuzzle.cnf file in the users home directory
        BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(System.getProperty("user.home"), "cnfPuzzle.cnf")));

        // Writes the contents of output to a file
        bwr.write(output.toString());

        // Flushes the stream
        bwr.flush();

        // Closes the stream
        bwr.close();

    } // End generateCNFFile()

} // End Model.Encoder class