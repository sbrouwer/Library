package domain;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TableModelBookDetail extends AbstractTableModel {

	Library library;
	List<Copy> copies;
	
	public TableModelBookDetail(Library library, List<Copy> copies)
	{
		this.library = library;
		this.copies = copies;
	}
	
	public Object getValueAt(int row, int colum)
    {
		Copy copy = copies.get(row);
        switch (colum)
        {
            case 0: return copy.getInventoryNumber();
            case 1: return library.getLoanOfCopy(copy).g;
            default: return null;
        }
    }
	

	@Override
	public int getColumnCount()
	{
		return 2;
	}

	@Override
	public int getRowCount()
	{
		return copies.size();
	}
	
}
