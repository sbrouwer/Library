package domain;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TableModelBookDetail extends AbstractTableModel {

	Library library;
	List<Copy> copies;
	String[] header;

	public TableModelBookDetail(Library library, List<Copy> copies, String[] header) {
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
			Loan l = library.getLoanOfCopy(copy);
			if (l != null) {
				if (l.isLent()) { //Damit keine schon zur�ckgegebene Loans angezeigt werden
					if (!l.isOverdue()) {
						return l.getDueDateString() + " (Noch " + l.getDaysTilDue()
								+ " Tage bis zur R�ckgabe)";
					} else {
						return l.getDueDateString() + " (F�llig!)";
					}
				} else {
					return "Verf�gbar";
				}
			} else {
				return "Verf�gbar";
			}
		default:
			return null;
		}
	}

	public Copy getCopyAtRow(int row) {
		return copies.get(row);
	}

	@Override
	public int getColumnCount() {
		return 2;
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
			this.copies = library.getCopiesOfBook(copyToAdd.getBook());
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
