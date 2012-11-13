package domain;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TableModelBookMaster extends AbstractTableModel {

	Library library;
	List<Book> books;
	String[] header;

	public TableModelBookMaster(Library library, List<Book> books, String[] header) {
		this.library = library;
		this.books = books;
		this.header = header;

		fireTableDataChanged();
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
		return 4;
	}
	
	@Override
	public String getColumnName(int column) {
		return header[column];
	}
	
	@Override
	public int getRowCount() {
		return books.size();
	}
	
	public void addRow(Book bookToAdd){
		this.books.add(bookToAdd);
		fireTableDataChanged();
	}
	
	public void removeRow(Book bookToDelet){
		/*this.books.remove(bookToDelet);
		library.removeBook(bookToDelet);
		library.
		fireTableDataChanged();*/
	}
}
