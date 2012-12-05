package tablemodel;

import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

import domain.Customer;
import domain.Library;
import domain.Loan;

public class TableModelLoanDetail extends AbstractTableModel implements Observer
{
	Library library;
	Customer customer;
	String[] headers;
	ImageIcon iconLent = new ImageIcon("icons/book_go.png", "Ausgeliehen");
	ImageIcon iconOverdue = new ImageIcon("icons/warning.png", "Überfällig!");
	ImageIcon iconReturned = new ImageIcon("icons/arrow-return.png", "Zurückgegeben");

	public TableModelLoanDetail(Library library, Customer customer, String[] headers)
	{
		this.library = library;
		this.customer = customer;
		this.headers = headers;
		this.library.addObserver(this);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex)
	{
		switch (columnIndex)
		{
		case 0:
			return ImageIcon.class;
		case 1:
			return Long.class;
		case 2:
			return String.class;
		case 3:
			return String.class;
		default:
			return null;
		}
	}

	@Override
	public Object getValueAt(int row, int colum)
	{
		Loan loan = library.getCustomerLoans(customer).get(row);
		switch (colum)
		{
		case 0:
			return getLoanStatus(loan);
		case 1:
			return loan.getCopy().getInventoryNumber();
		case 2:
			return loan.getCopy().getTitle().getName();
		case 3:
			return loan.getCopy().getTitle().getAuthor();
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
		if (library.getCustomerLoans(customer) != null)
		{
			return library.getCustomerLoans(customer).size();
		} else
		{
			return 0;
		}
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

	private ImageIcon getLoanStatus(Loan l)
	{
		if (l.isLent())
		{
			if (l.isOverdue())
			{
				return iconOverdue;
			} else
			{
				return iconLent;
			}
		} else
		{
			return iconReturned;
		}
	}

}
