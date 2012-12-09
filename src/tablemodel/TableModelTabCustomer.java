package tablemodel;

import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import domain.Customer;
import domain.Library;

public class TableModelTabCustomer extends AbstractTableModel implements Observer
{
    private static final long serialVersionUID = -8401554389454934712L;
    
    private Library library;
    private String[] headers;

	public TableModelTabCustomer(Library library, String[] headers)
	{
		this.library = library;
		this.headers = headers;
		
		this.library.addObserver(this);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex)
	{
		switch (columnIndex)
		{
		case 0:
			return Long.class;
		case 1:
			return String.class;
		case 2:
			return String.class;
		case 3:
			return String.class;
		case 4:
			return Integer.class;
		case 5:
			return String.class;
		default:
			return null;
		}
	}

	@Override
	public Object getValueAt(int row, int colum)
	{
		Customer customer = library.getCustomers().get(row);
		switch (colum)
		{
		case 0:
			return customer.getIdentifier();
		case 1:
			return customer.getName();
		case 2:
			return customer.getSurname();
		case 3:
			return customer.getStreet();
		case 4:
			return customer.getZip();
		case 5:
			return customer.getCity();
		default:
			return null;
		}
	}

	@Override
	public int getColumnCount()
	{
		return headers.length;
	}

	@Override
	public int getRowCount()
	{
		return library.getCustomers().size();
	}

	@Override
	public String getColumnName(int column)
	{
		return headers[column];
	}

	@Override
	public void update(Observable o, Object arg)
	{
		fireTableDataChanged();
	}

	public Customer getCustomerAtRow(int convertRowIndexToModel)
	{
		return library.getCustomers().get(convertRowIndexToModel);
	}

}
