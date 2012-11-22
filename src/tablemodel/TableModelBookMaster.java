package tablemodel;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import domain.Book;
import domain.Library;

public class TableModelBookMaster extends AbstractTableModel implements Observer {

	Library library;
	List<Book> books;
	String[] headers;

	public TableModelBookMaster(Library library, String[] headers) {
		this.library = library;
		this.books = library.getBooks();
		this.headers = headers;
		this.library.addObserver(this);
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex)
	{
		switch (columnIndex) {
		case 0:
			return Integer.class;
		case 1:
			return String.class;
		case 2:
			return String.class;
		case 3:
			return String.class;
		default:
			return null;
		}
	}

	public Object getValueAt(int row, int colum) {	
		
		Book book = books.get(row);
		switch (colum) {
		case 0:
			return library.getCopiesOfBook(book).size() - library.getLentCopiesOfBook(book).size();
		case 1:
			return book.getName();
		case 2:
			return book.getAuthor();
		case 3:
			return book.getPublisher();
		default:
			return null;
		}
	}

	public Book getBookAtRow(int row){
		return books.get(row);
	}
	
	@Override
	public int getColumnCount() {
		return headers.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return headers[column];
	}
	
	@Override
	public int getRowCount() {
		return books.size();
	}

	@Override
	public void update(Observable o, Object arg)
	{
		books = library.getBooks();
		fireTableDataChanged();
	}
	
}
