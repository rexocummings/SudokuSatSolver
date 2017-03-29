package Model;

/**
 * Model.Cell class
 * Created by Chris, Connor and Rex on 11/30/2016.
 *
 * Model.Cell class contains the entries for specific cell values.
 */
public class Cell {
	
	private int row = 0;
	private int col = 0;
	private int value = 0;

    /**
     * Model.Cell class constructor
     */
    public Cell( int row, int col, int value){
    	this.row = row;
    	this.col = col;
    	this.value = value;
    }

	public int getRow()
	{
		return row;
	}

	public void setRow(int row)
	{
		this.row = row;
	}

	public int getCol()
	{
		return col;
	}

	public void setCol(int col)
	{
		this.col = col;
	}

	public int getValue()
	{
		return value;
	}

	public void setValue(int value)
	{
		this.value = value;
	}
}