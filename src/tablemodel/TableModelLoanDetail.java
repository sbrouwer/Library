package tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import domain.Copy;
import domain.Library;

public class TableModelLoanDetail extends AbstractTableModel {

	Library library;
	List<Copy> copies;
	String[] header;

	public TableModelLoanDetail(Library library, List<Copy> copies, String[] header) {
		this.library = library;
		this.copies = copies;
		this.header = header;

		fireTableDataChanged();
	}

	public Object getValueAt(int row, int colum) {
		Copy copy = copies.get(row);
		switch (colum) {
		case 0:
			return copy.getInventoryNumber();
		case 1:
			return copy.getTitle().getName();
		case 2:
			return copy.getTitle().getAuthor();
		default:
			return null;
		}
	}

	public Copy getCopyAtRow(int row) {
		return copies.get(row);
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		if (copies != null) {
			return copies.size();
		} else {
			return 0;
		}
	}

	@Override
	public String getColumnName(int column) {
		return header[column];
	}

	public void addRow(Copy copyToAdd) {
		if (copies == null) {
			this.copies = library.getCopiesOfBook(copyToAdd.getTitle());
		}
		this.copies.add(copyToAdd);
		fireTableDataChanged();
		fireTableRowsInserted(0, getRowCount());
	}

	public void removeRow(Copy copyToDelet) {
		this.copies.remove(copyToDelet);
		library.removeCopy(copyToDelet);
		fireTableDataChanged();
	}
}
