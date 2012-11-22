package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
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
import domain.Library;
import domain.Loan;

public class LoanDetail
{

	private JFrame frmAusleiheDetail;
	private JTextField txtCustomerIdentifier;
	private JTextField txtCopyInventoryNumber;
	private JTextField txtReturnDate;
	private JTable table;
	private Library library;
	private Customer customer;
	private JComboBox customersComboBox;
	private JLabel lblAnzahlAusleihenAmount;
	private TableModelLoanDetail tableModel;

	// /**
	// * Launch the application.
	// */
	// public static void main(String[] args)
	// {
	// EventQueue.invokeLater(new Runnable()
	// {
	// public void run()
	// {
	// try
	// {
	// LoanDetail window = new LoanDetail();
	// window.frmAusleiheDetail.setVisible(true);
	// } catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the application.
	 */
	public LoanDetail(Library library)
	{
		this.library = library;
		initialize();

		updateForNewLoan();
		
		frmAusleiheDetail.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public LoanDetail(Library library, Loan loan)
	{
		this.library = library;
		this.customer = loan.getCustomer();
		initialize();
		
		updateWithExistingLoan(loan);

		frmAusleiheDetail.setVisible(true);
	}

	private void updateForNewLoan()
	{
		txtCustomerIdentifier.setText("");
		txtCustomerIdentifier.setEditable(false);
		
		customersComboBox.setModel(new DefaultComboBoxModel(library.getCustomers().toArray()));
		customersComboBox.setSelectedIndex(-1);
		
		txtCopyInventoryNumber.setText("");		
		GregorianCalendar returnDate = new GregorianCalendar();
		returnDate.add(GregorianCalendar.DAY_OF_YEAR, Loan.DAYS_TO_RETURN_BOOK);
		txtReturnDate.setText(String.valueOf(Loan.getFormattedDate(returnDate)));
		txtReturnDate.setEditable(false);
			
		lblAnzahlAusleihenAmount.setText("0");
	}
	
	private void updateWithExistingLoan(Loan loan)
	{
		txtCustomerIdentifier.setText(String.valueOf(loan.getCustomer().getIdentifier()));
		txtCustomerIdentifier.setEditable(false);
	
		customersComboBox.setModel(new DefaultComboBoxModel(library.getCustomers().toArray()));
		customersComboBox.setSelectedItem(loan.getCustomer());
		
		txtCopyInventoryNumber.setText("");
		txtReturnDate.setText("");
		txtReturnDate.setEditable(false);
		

		lblAnzahlAusleihenAmount.setText(String.valueOf(library.getCustomerLoans(customer).size()));
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmAusleiheDetail = new JFrame();
		frmAusleiheDetail.setTitle("Ausleihe Detail");
		frmAusleiheDetail.setBounds(100, 100, 450, 300);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		frmAusleiheDetail.getContentPane().setLayout(gridBagLayout);

		JPanel customerPanel = new JPanel();
		customerPanel.setBorder(new TitledBorder(null, "Kundenauswahl", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		customerPanel.add(lblKennung, gbc_lblNewLabel);

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
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		
		customerPanel.add(customersComboBox, gbc_comboBox);

		JPanel newCopyPanel = new JPanel();
		newCopyPanel.setBorder(new TitledBorder(null, "Neues Exemplar ausleihen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_newCopyPanel = new GridBagConstraints();
		gbc_newCopyPanel.insets = new Insets(0, 0, 5, 0);
		gbc_newCopyPanel.fill = GridBagConstraints.BOTH;
		gbc_newCopyPanel.gridx = 0;
		gbc_newCopyPanel.gridy = 1;
		frmAusleiheDetail.getContentPane().add(newCopyPanel, gbc_newCopyPanel);
		GridBagLayout gbl_newCopyPanel = new GridBagLayout();
		gbl_newCopyPanel.columnWidths = new int[] { 100, 0, 0, 0, 0 };
		gbl_newCopyPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_newCopyPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_newCopyPanel.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
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
				
			}
		});
		txtCopyInventoryNumber.setText("123123");
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 0;
		newCopyPanel.add(txtCopyInventoryNumber, gbc_textField_1);
		txtCopyInventoryNumber.setColumns(10);

		JLabel lblX = new JLabel("X");
		GridBagConstraints gbc_lblX = new GridBagConstraints();
		gbc_lblX.insets = new Insets(0, 0, 5, 5);
		gbc_lblX.gridx = 2;
		gbc_lblX.gridy = 0;
		newCopyPanel.add(lblX, gbc_lblX);

		JButton btnExemplarAusleihen = new JButton("Exemplar ausleihen");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.WEST;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 3;
		gbc_btnNewButton.gridy = 0;
		newCopyPanel.add(btnExemplarAusleihen, gbc_btnNewButton);

		JLabel lblZurueckAm = new JLabel("Zur\u00FCck am:");
		GridBagConstraints gbc_lblZurckAm = new GridBagConstraints();
		gbc_lblZurckAm.anchor = GridBagConstraints.WEST;
		gbc_lblZurckAm.insets = new Insets(0, 0, 0, 5);
		gbc_lblZurckAm.gridx = 0;
		gbc_lblZurckAm.gridy = 1;
		newCopyPanel.add(lblZurueckAm, gbc_lblZurckAm);

		txtReturnDate = new JTextField();
		txtReturnDate.setText("asdasd");
		GridBagConstraints gbc_txtAsdasd = new GridBagConstraints();
		gbc_txtAsdasd.gridwidth = 3;
		gbc_txtAsdasd.insets = new Insets(0, 0, 0, 5);
		gbc_txtAsdasd.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAsdasd.gridx = 1;
		gbc_txtAsdasd.gridy = 1;
		newCopyPanel.add(txtReturnDate, gbc_txtAsdasd);
		txtReturnDate.setColumns(10);

		JPanel loanByCustomerTablePanel = new JPanel();
		loanByCustomerTablePanel.setBorder(new TitledBorder(null, "Ausleihen von Kunde", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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

		lblAnzahlAusleihenAmount = new JLabel("2");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 1;
		gbc_label.gridy = 0;
		loanByCustomerTablePanel.add(lblAnzahlAusleihenAmount, gbc_label);

		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		
		String[] headers = {"Exemplar-ID", "Titel", "Autor"};
		tableModel = new TableModelLoanDetail(library, null, headers);
		table.setModel(tableModel);
		
//		TableColumn tableColumnCopyInventoryNumber = this.table.getColumn(headers[0]);
//		tableColumnCopyInventoryNumber.setMinWidth(100);
//		tableColumnCopyInventoryNumber.setMaxWidth(100);
		
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

}
