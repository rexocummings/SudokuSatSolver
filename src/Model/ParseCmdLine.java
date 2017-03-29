package Model;

/**
 * SAT Solver
 *
 * Author: Rex Cummings
 *
 * Date 11/30/2016
 */
public class ParseCmdLine
{

	public String getArgs(String[] args)
	{
		String fn = "";

		if (args.length > 0)
		{
			if (args.length > 1)
			{
				System.out.println("Too many arguments.");
				System.out.println("Usage: java Controller.SudokuDriver filename");
				System.exit(0);
			}
			else
			{
				fn = args[0];
				System.out.println("File: " + fn + " was loaded successfully.");
			}
		} 
		else{
			System.out.println("No argument passed");
			System.out.println("Usage: java Controller.SudokuDriver filename");
			System.exit(0);
		}
		return fn;
	}
}