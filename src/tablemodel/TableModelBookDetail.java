package tablemodel;

import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import domain.Book;
import domain.Copy;
import domain.Copy.Condition;
import domain.Library;
import domain.Loan;

public class TableModelBookDetail extends AbstractTableModel implements Observer {

	Library library;
	String[] headers;
	Book book;

	public TableModelBookDetail(Library library, Book book, String[] headers) {
		this.library = library;
		this.book = book;
		this.headers = headers;
		this.library.addObserver(this);
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex)
	{
		switch (columnIndex) {
		case 0:
			return Long.class;
		case 1:
			return String.class;
		case 2: 
			return Condition.class;
		default:
			return null;
		}
	}

	public Object getValueAt(int row, int colum) {
		Copy copy = library.getCopiesOfBook(book).get(row);
		switch (colum) {
		case 0:
			return copy.getInventoryNumber();
		case 1:
			Loan l = library.getLoanOfCopy(copy);
			if (l != null) {
				if (l.isLent()) { //Damit keine schon zurückgegebene Loans angezeigt werden
					if (!l.isOverdue()) {
						return l.getDueDateString() + " (Noch " + l.getDaysTilDue()
								+ " Tage bis zur Rückgabe)";
					} else {
						return l.getDueDateString() + " (Fällig!)";
					}
				} else {
					return "Verfügbar";
				}
			} else {
				return "Verfügbar";
			}
		case 2:
			return copy.getCondition();
		default:
			return null;
		}
	}

	public Copy getCopyAtRow(int row) {
		return library.getCopiesOfBook(book).get(row);
	}

	@Override
	public int getColumnCount() {
		return headers.length;
	}

	@Override
	public int getRowCount() {
		if (library.getCopiesOfBook(book) != null) {
			return library.getCopiesOfBook(book).size();
		} else {
			return 0;
		}
	}

	@Override
	public String getColumnName(int column) {
		return headers[column];
	}

	@Override
	public void update(Observable o, Object arg)
	{	
		fireTableDataChanged();
	}
}
