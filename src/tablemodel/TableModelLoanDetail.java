package tablemodel;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import domain.Library;
import domain.Loan;

public class TableModelLoanDetail extends AbstractTableModel {

	Library library;
	List<Loan> loans;
	String[] header;

	public TableModelLoanDetail(Library library, String[] header) {
		this.library = library;
		this.header = header;

		fireTableDataChanged();
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
			return String.class;
		default:
			return null;
		}
	}

	@Override
	public Object getValueAt(int row, int colum) {
		Loan loan = loans.get(row);
		switch (colum) {
		case 0:
			return loan.getCopy().getInventoryNumber();
		case 1:
			return loan.getCopy().getTitle().getName();
		case 2:
			return loan.getCopy().getTitle().getAuthor();
		default:
			return null;
		}
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		if (loans != null) {
			return loans.size();
		} else {
			return 0;
		}
	}

	@Override
	public String getColumnName(int column) {
		return header[column];
	}
	
	public void setLoans(List<Loan> loans){
		this.loans = loans;
	}

}
