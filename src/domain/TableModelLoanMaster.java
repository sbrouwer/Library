package domain;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TableModelLoanMaster extends AbstractTableModel {

	Library library;
	List<Loan> loans;
	String[] header;
	List<Copy> copies;

	public TableModelLoanMaster(Library library, List<Loan> loans, String[] header) {
		this.library = library;
		this.header = header;
		this.copies = library.getCopies();
		this.loans = new ArrayList<Loan>();
		List<Loan> lent = loans;
		for(Loan l : lent){
			if(l.isLent()){
				this.loans.add(l);
			}
		}
		

		fireTableDataChanged();
	}

	public Object getValueAt(int row, int colum) {
		Loan loan = loans.get(row);

		switch (colum) {
		case 0:
			return getLoanStatus(loan);
		case 1:
			return loan.getCopy().getInventoryNumber();
		case 2:
			return loan.getCopy().getTitle();
		case 3:
				if (!loan.isOverdue()) {
					return loan.getDueDateString() + " (Noch " + loan.getDaysTilDue() + " Tage)";
				} else {
					return loan.getDueDateString() + " (Fällig!)";
				}

		case 4:
			return loan.getCustomer().getName();
		default:
			return null;
		}
	}

	public Loan getCopyAtRow(int row) {
		return loans.get(row);
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return header[column];
	}

	@Override
	public int getRowCount() {
		return loans.size();
	}

	public void addRow(Loan loanToAdd) {
		this.loans.add(loanToAdd);
		fireTableDataChanged();
	}

	public void removeRow(Book bookToDelet) {
		/*
		 * this.books.remove(bookToDelet); library.removeBook(bookToDelet);
		 * library. fireTableDataChanged();
		 */
	}

	private String getLoanStatus(Loan l) {
		if (l.isOverdue()) {
			return "\u26A0 Fällig";
		} else {
			return "\u2713 Ok";
		}
	}

}
