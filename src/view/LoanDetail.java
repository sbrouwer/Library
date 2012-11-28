package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import tablemodel.TableModelLoanDetail;
import domain.Customer;
import domain.IllegalLoanOperationException;
import domain.Library;
import domain.Loan;

public class LoanDetail implements Observer {

	private JFrame frmAusleiheDetail;
	private JTextField txtCustomerIdentifier;
	private JTextField txtCopyInventoryNumber;
	private JTextField txtReturnDate;
	private JTable table;
	private Library library;
	private Customer customer;
	private JComboBox<Customer> customersComboBox;
	private JLabel lblAnzahlAusleihenAmount;
	private JLabel lblX;
	private JButton btnExemplarAusleihen;
	private JButton btnExemplarZurueckgeben;
	private JLabel lblZurueckAm;
	private JLabel lblError;
	private Color red = new Color(255,0,0);
	private Color black = new Color(0,0,0);

	public LoanDetail(Library library) {
		this.library = library;
		customer = null;
		library.addObserver(this);
		initialize();
		updateForNewLoan();
		frmAusleiheDetail.setVisible(true);

	}

	public LoanDetail(Library library, Loan loan) {
		this.library = library;
		customer = loan.getCustomer();
		library.addObserver(this);
		initialize();
		updateWithExistingLoan(loan);
		frmAusleiheDetail.setVisible(true);
	}

	private void updateWithExistingLoan(Loan loan) {
		txtCustomerIdentifier.setText(String.valueOf(loan.getCustomer().getIdentifier()));
		txtCustomerIdentifier.setEditable(false);

		customersComboBox.setModel(new DefaultComboBoxModel(library.getCustomers().toArray()));
		customersComboBox.setSelectedItem(loan.getCustomer());

		btnExemplarZurueckgeben.setEnabled(true);
		txtCopyInventoryNumber.setText("" + loan.getCopy().getInventoryNumber());
		lblX.setText("\u2713");

		txtCopyInventoryNumber.setText(Long.toString(loan.getCopy().getInventoryNumber()));
		if (!loan.isOverdue()) {
			txtReturnDate.setText(loan.getDueDateString() + " (Noch " + loan.getDaysTilDue() + " Tage)");
		} else {
			txtReturnDate.setText(loan.getDueDateString() + " (Fällig!)");
		}

		txtReturnDate.setEditable(false);

		lblAnzahlAusleihenAmount.setText(String.valueOf(library.getCustomerLoans(customer).size()));
	}

	private void updateForNewLoan() {
		txtCustomerIdentifier.setText("");
		txtCustomerIdentifier.setEditable(false);

		customersComboBox.setModel(new DefaultComboBoxModel(library.getCustomers().toArray()));
		customersComboBox.setSelectedIndex(-1);

		btnExemplarAusleihen.setEnabled(false);
		btnExemplarZurueckgeben.setEnabled(false);

		txtCopyInventoryNumber.setText("");
		GregorianCalendar returnDate = new GregorianCalendar();
		returnDate.add(GregorianCalendar.DAY_OF_YEAR, Loan.DAYS_TO_RETURN_BOOK);
		txtReturnDate.setText(String.valueOf(Loan.getFormattedDate(returnDate)));
		txtReturnDate.setEditable(false);

		lblAnzahlAusleihenAmount.setText("0");
	}

	private boolean checkIfInventoryNumberExists(String potentialInventoryNumber) {
		try {
			if (Long.parseLong(potentialInventoryNumber) < 0) {
				// Falls Zahl kleiner als 0
				return false;
			}
			if (library.getCopyByInventoryNumber(Long.parseLong(txtCopyInventoryNumber.getText())) == null) {
				// Falls keine Copy mit dieser InventoryNumber vorhanden
				return false;
			}
		} catch (NumberFormatException nfe) {
			// Falls keine Zahl
			return false;
		}
		return true;

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		final String[] headers = { "Exemplar-ID", "Titel", "Autor" };

		frmAusleiheDetail = new JFrame();
		frmAusleiheDetail.setTitle("Ausleihe Detail");
		frmAusleiheDetail.setBounds(100, 100, 550, 380);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		frmAusleiheDetail.getContentPane().setLayout(gridBagLayout);
		Dimension d = new Dimension(550, 380);
		frmAusleiheDetail.setMinimumSize(d);

		JPanel customerPanel = new JPanel();
		customerPanel.setBorder(new TitledBorder(null, "Kundenauswahl", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		GridBagConstraints gbc_customerPanel = new GridBagConstraints();
		gbc_customerPanel.insets = new Insets(0, 0, 5, 0);
		gbc_customerPanel.fill = GridBagConstraints.BOTH;
		gbc_customerPanel.gridx = 0;
		gbc_customerPanel.gridy = 0;
		frmAusleiheDetail.getContentPane().add(customerPanel, gbc_customerPanel);
		GridBagLayout gbl_customerPanel = new GridBagLayout();
		gbl_customerPanel.columnWidths = new int[] { 100, 0, 0 };
		gbl_customerPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_customerPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_customerPanel.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		customerPanel.setLayout(gbl_customerPanel);

		JLabel lblKennung = new JLabel("Kennung:");
		GridBagConstraints gbc_lblError = new GridBagConstraints();
		gbc_lblError.anchor = GridBagConstraints.WEST;
		gbc_lblError.insets = new Insets(0, 0, 5, 5);
		gbc_lblError.gridx = 0;
		gbc_lblError.gridy = 0;
		customerPanel.add(lblKennung, gbc_lblError);

		txtCustomerIdentifier = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		customerPanel.add(txtCustomerIdentifier, gbc_textField);
		txtCustomerIdentifier.setColumns(10);

		JLabel lblKunde = new JLabel("Kunde:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		customerPanel.add(lblKunde, gbc_lblNewLabel_1);

		customersComboBox = new JComboBox<Customer>();
		customersComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (customersComboBox.getSelectedIndex() != -1) {
					// Falls Customer ausgewÃ¤hlt...
					customer = library.getCustomers().get(customersComboBox.getSelectedIndex());
					txtCustomerIdentifier.setText(String.valueOf(customer.getIdentifier()));
					table.setModel(new TableModelLoanDetail(library, customer, headers));
					lblAnzahlAusleihenAmount.setText(String
							.valueOf(library.getCustomerLoans(customer).size()));

				} else {
					// Falls kein Customer ausgewÃ¤hlt...
					txtCustomerIdentifier.setText("");
					table.setModel(new TableModelLoanDetail(library, null, headers));
					lblAnzahlAusleihenAmount.setText("0");
				}
			}
		});

		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;

		customerPanel.add(customersComboBox, gbc_comboBox);

		JPanel newCopyPanel = new JPanel();
		newCopyPanel.setBorder(new TitledBorder(null, "Neues Exemplar ausleihen", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		GridBagConstraints gbc_newCopyPanel = new GridBagConstraints();
		gbc_newCopyPanel.insets = new Insets(0, 0, 5, 0);
		gbc_newCopyPanel.fill = GridBagConstraints.BOTH;
		gbc_newCopyPanel.gridx = 0;
		gbc_newCopyPanel.gridy = 1;
		frmAusleiheDetail.getContentPane().add(newCopyPanel, gbc_newCopyPanel);
		GridBagLayout gbl_newCopyPanel = new GridBagLayout();
		gbl_newCopyPanel.columnWidths = new int[] { 100, 0, 0, 0, 0, 0 };
		gbl_newCopyPanel.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_newCopyPanel.columnWeights = new double[] { 1.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_newCopyPanel.rowWeights = new double[] { 1.0, 1.0, 0.0, Double.MIN_VALUE };
		newCopyPanel.setLayout(gbl_newCopyPanel);

		JLabel lblExemplarID = new JLabel("Exemplar-ID:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 0;
		newCopyPanel.add(lblExemplarID, gbc_lblNewLabel_2);

		txtCopyInventoryNumber = new JTextField();
		txtCopyInventoryNumber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if (checkIfInventoryNumberExists(txtCopyInventoryNumber.getText())) {
					lblX.setText("\u2713");
					if (library.isCopyLent(library.getCopyByInventoryNumber(Long
							.parseLong(txtCopyInventoryNumber.getText())))) {
						btnExemplarAusleihen.setEnabled(false);
						btnExemplarZurueckgeben.setEnabled(true);
						txtReturnDate.setText(String.valueOf(library.getLoanOfCopy(
								library.getCopyByInventoryNumber(Long.parseLong(txtCopyInventoryNumber
										.getText()))).getDueDateString()));
					} else {
						btnExemplarAusleihen.setEnabled(true);
						btnExemplarZurueckgeben.setEnabled(false);
						GregorianCalendar returnDate = new GregorianCalendar();
						returnDate.add(GregorianCalendar.DAY_OF_YEAR, Loan.DAYS_TO_RETURN_BOOK);
						txtReturnDate.setText(Loan.getFormattedDate(returnDate) + " noch 30 Tage");
					}
				} else {
					lblX.setText("X");
					btnExemplarAusleihen.setEnabled(false);
					btnExemplarZurueckgeben.setEnabled(false);
				}
			}
		});
		txtCopyInventoryNumber.setText("42");
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 0;
		newCopyPanel.add(txtCopyInventoryNumber, gbc_textField_1);
		txtCopyInventoryNumber.setColumns(10);

		lblX = new JLabel("X");
		GridBagConstraints gbc_lblX = new GridBagConstraints();
		gbc_lblX.insets = new Insets(0, 0, 5, 5);
		gbc_lblX.gridx = 2;
		gbc_lblX.gridy = 0;
		newCopyPanel.add(lblX, gbc_lblX);

		ImageIcon iconExemplarAusleihen = new ImageIcon("icons/book_go.png");
		btnExemplarAusleihen = new JButton("Exemplar ausleihen", iconExemplarAusleihen);
		btnExemplarAusleihen.setEnabled(false);
		btnExemplarAusleihen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (customersComboBox.getSelectedIndex() > -1) {
					if (checkIfInventoryNumberExists(txtCopyInventoryNumber.getText())) { // check
																							// InventoryNr
						if (library.isCopyLent(library.getCopyByInventoryNumber(Long
								.parseLong(txtCopyInventoryNumber.getText())))) { // check
																					// if
																					// is
																					// Lent
							System.out.println("Kopie ist bereits Ausgeliehen!");
							lblError.setForeground(red);
							lblError.setText("Dieses Exemplar ist bereits ausgeliehen!");
						} else {
							Loan l = library.createAndAddLoan(customer, library.getCopyByInventoryNumber(Long
									.parseLong(txtCopyInventoryNumber.getText()))); // add
																					// loan
							if (l != null) {
								lblAnzahlAusleihenAmount.setText(String.valueOf(library.getCustomerLoans(
										customer).size()));
								btnExemplarAusleihen.setEnabled(false);
								btnExemplarZurueckgeben.setEnabled(true);// update
								lblError.setForeground(black);
								lblError.setText("Exemplar wurde erfolgreich ausgeliehen");											// textfields
							} else {
								System.out.println("Fehler!");
							}
						}
					}
				}else{
					lblError.setForeground(red);
					lblError.setText("Es Muss ein Kunde asgewählt sein bevor ein Buch ausgeliehen werden kann!");
				}
			}
		});

		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 3;
		gbc_btnNewButton.gridy = 0;
		newCopyPanel.add(btnExemplarAusleihen, gbc_btnNewButton);

		ImageIcon iconExemplarZurueckgeben = new ImageIcon("icons/arrow-return.png");
		btnExemplarZurueckgeben = new JButton("Exemplar zur\u00FCckgeben", iconExemplarZurueckgeben);
		GridBagConstraints gbc_btnExemplarZurckgeben = new GridBagConstraints();
		gbc_btnExemplarZurckgeben.insets = new Insets(0, 0, 5, 0);
		gbc_btnExemplarZurckgeben.gridx = 4;
		gbc_btnExemplarZurckgeben.gridy = 0;
		newCopyPanel.add(btnExemplarZurueckgeben, gbc_btnExemplarZurckgeben);
		btnExemplarZurueckgeben.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (checkIfInventoryNumberExists(txtCopyInventoryNumber.getText())) {
					try {
						Loan l = library.getLoanOfCopy(library.getCopyByInventoryNumber(Long
								.valueOf(txtCopyInventoryNumber.getText())));
						if (l != null) {
							if (l.isLent()) {
								if (l.isOverdue()) {

									l.returnCopy(new GregorianCalendar());
									lblError.setForeground(black);
									lblError.setText("Exemplar wurde zurückgegeben, Ausleihe war Überfällig!");
									btnExemplarAusleihen.setEnabled(true);
									btnExemplarZurueckgeben.setEnabled(false);
								} else {
									l.returnCopy(new GregorianCalendar());
									lblError.setForeground(black);
									lblError.setText("Exemplar wurde zurückgegeben");
									btnExemplarAusleihen.setEnabled(true);
									btnExemplarZurueckgeben.setEnabled(false);
								}
							}
						} else {
							System.out.println("Loan war null");
						}

					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (IllegalLoanOperationException e) {
						e.printStackTrace();
					}
				}
			}
		});

		lblZurueckAm = new JLabel("Zur\u00FCck am:");
		GridBagConstraints gbc_lblZurckAm = new GridBagConstraints();
		gbc_lblZurckAm.anchor = GridBagConstraints.WEST;
		gbc_lblZurckAm.insets = new Insets(0, 0, 5, 5);
		gbc_lblZurckAm.gridx = 0;
		gbc_lblZurckAm.gridy = 1;
		newCopyPanel.add(lblZurueckAm, gbc_lblZurckAm);

		txtReturnDate = new JTextField();
		txtReturnDate.setText("");
		GridBagConstraints gbc_txtAsdasd = new GridBagConstraints();
		gbc_txtAsdasd.insets = new Insets(0, 0, 5, 0);
		gbc_txtAsdasd.gridwidth = 4;
		gbc_txtAsdasd.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAsdasd.gridx = 1;
		gbc_txtAsdasd.gridy = 1;
		newCopyPanel.add(txtReturnDate, gbc_txtAsdasd);
		txtReturnDate.setColumns(10);

		lblError = new JLabel("");
		GridBagConstraints gbc_lblError2 = new GridBagConstraints();
		gbc_lblError2.anchor = GridBagConstraints.EAST;
		gbc_lblError2.gridwidth = 5;
		gbc_lblError2.insets = new Insets(0, 0, 0, 5);
		gbc_lblError2.gridx = 0;
		gbc_lblError2.gridy = 2;
		newCopyPanel.add(lblError, gbc_lblError2);
		GridBagConstraints gbc_textFieldError;

		JPanel loanByCustomerTablePanel = new JPanel();
		loanByCustomerTablePanel.setBorder(new TitledBorder(null, "Ausleihen von Kunde",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_loanByCustomerTablePanel = new GridBagConstraints();
		gbc_loanByCustomerTablePanel.fill = GridBagConstraints.BOTH;
		gbc_loanByCustomerTablePanel.gridx = 0;
		gbc_loanByCustomerTablePanel.gridy = 2;
		frmAusleiheDetail.getContentPane().add(loanByCustomerTablePanel, gbc_loanByCustomerTablePanel);
		GridBagLayout gbl_loanByCustomerTablePanel = new GridBagLayout();
		gbl_loanByCustomerTablePanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_loanByCustomerTablePanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_loanByCustomerTablePanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_loanByCustomerTablePanel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		loanByCustomerTablePanel.setLayout(gbl_loanByCustomerTablePanel);

		JLabel lblAnzahlAusleihen = new JLabel("Anzahl Ausleihen:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 0;
		loanByCustomerTablePanel.add(lblAnzahlAusleihen, gbc_lblNewLabel_3);

		lblAnzahlAusleihenAmount = new JLabel("42");
		GridBagConstraints gbc_lblAnzahlAusleihenAmount = new GridBagConstraints();
		gbc_lblAnzahlAusleihenAmount.anchor = GridBagConstraints.WEST;
		gbc_lblAnzahlAusleihenAmount.insets = new Insets(0, 0, 5, 0);
		gbc_lblAnzahlAusleihenAmount.gridx = 1;
		gbc_lblAnzahlAusleihenAmount.gridy = 0;
		loanByCustomerTablePanel.add(lblAnzahlAusleihenAmount, gbc_lblAnzahlAusleihenAmount);

		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);

		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.insets = new Insets(0, 0, 0, 5);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 2;
		loanByCustomerTablePanel.add(table, gbc_table);

		JScrollPane scrollPane = new JScrollPane(table);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		loanByCustomerTablePanel.add(scrollPane, gbc_scrollPane);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		lblAnzahlAusleihenAmount.setText(String.valueOf(library.getCustomerLoans(customer).size()));
	}
}
