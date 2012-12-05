package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableRowSorter;

import tablemodel.TableModelLoanMaster;
import domain.Book;
import domain.Library;
import domain.Loan;

public class BookMasterLoanTab extends JPanel implements Observer
{
	private Library library;
	private JLabel lblAmountOfOverdueLoans;
	private JLabel lblAmountOfLoans;
	private JLabel lblAmountOfLentLoans;
	private JTextField txtSearch;
	private JCheckBox chckbxOnlyOverdue;
	private TableModelLoanMaster tableModel;
	private JTable table;
	private TableRowSorter<TableModelLoanMaster> sorter;
	private JButton btnShowSelected;
	private JButton btnAddLoan;
	private final String[] header = new String[] { "Status", "Exemplar-ID", "Titel", "Ausgeliehen Bis", "Ausgeliehen An" };

	public BookMasterLoanTab(Library library)
	{
		this.library = library;
		library.addObserver(this);
		initialize();
		updateStatistics();
	}

	private void initialize()
	{
		GridBagLayout gbl_booksTab = new GridBagLayout();
		gbl_booksTab.columnWidths = new int[] { 0, 0 };
		gbl_booksTab.rowHeights = new int[] { 0, 0, 0 };
		gbl_booksTab.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_booksTab.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		this.setLayout(gbl_booksTab);

		JPanel panel_statistics = new JPanel();
		panel_statistics.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Statistiken", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		GridBagConstraints gbc_panel_statistics = new GridBagConstraints();
		gbc_panel_statistics.anchor = GridBagConstraints.NORTH;
		gbc_panel_statistics.insets = new Insets(0, 0, 5, 0);
		gbc_panel_statistics.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_statistics.gridx = 0;
		gbc_panel_statistics.gridy = 0;

		this.add(panel_statistics, gbc_panel_statistics);

		GridBagLayout gbl_panel_statistics = new GridBagLayout();
		gbl_panel_statistics.columnWidths = new int[] { 94, 106, 113, 0 };
		gbl_panel_statistics.rowHeights = new int[] { 14, 0 };
		gbl_panel_statistics.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_statistics.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_statistics.setLayout(gbl_panel_statistics);

		lblAmountOfLoans = new JLabel("Anzahl Ausleihen: " + library.getLoans().size());
		GridBagConstraints gbc_lblAmountOfLoans = new GridBagConstraints();
		gbc_lblAmountOfLoans.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAmountOfLoans.insets = new Insets(0, 0, 0, 5);
		gbc_lblAmountOfLoans.gridx = 0;
		gbc_lblAmountOfLoans.gridy = 0;
		panel_statistics.add(lblAmountOfLoans, gbc_lblAmountOfLoans);

		lblAmountOfLentLoans = new JLabel();
		GridBagConstraints gbc_lblAmountOfLentLoans = new GridBagConstraints();
		gbc_lblAmountOfLentLoans.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAmountOfLentLoans.insets = new Insets(0, 0, 0, 5);
		gbc_lblAmountOfLentLoans.gridx = 1;
		gbc_lblAmountOfLentLoans.gridy = 0;
		panel_statistics.add(lblAmountOfLentLoans, gbc_lblAmountOfLentLoans);

		lblAmountOfOverdueLoans = new JLabel();
		GridBagConstraints gbc_lblAmountOfOverdueLoans = new GridBagConstraints();
		gbc_lblAmountOfOverdueLoans.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAmountOfOverdueLoans.gridx = 2;
		gbc_lblAmountOfOverdueLoans.gridy = 0;
		panel_statistics.add(lblAmountOfOverdueLoans, gbc_lblAmountOfOverdueLoans);

		tableModel = new TableModelLoanMaster(library, header);

		JPanel panel_management = new JPanel();
		panel_management.setBorder(new TitledBorder(null, "Ausleihenverwaltung", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_management = new GridBagConstraints();
		gbc_panel_management.fill = GridBagConstraints.BOTH;
		gbc_panel_management.gridx = 0;
		gbc_panel_management.gridy = 1;
		add(panel_management, gbc_panel_management);
		GridBagLayout gbl_panel_management = new GridBagLayout();
		gbl_panel_management.columnWidths = new int[] { 38, 0, 0, 0, 0 };
		gbl_panel_management.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_management.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_management.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panel_management.setLayout(gbl_panel_management);

		txtSearch = new JTextField();
		txtSearch.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusGained(FocusEvent arg0)
			{
				if (txtSearch.getText().contains("Suche"))
				{
					txtSearch.setText("");
				}
			}
		});
		txtSearch.setToolTipText("Geben Sie hier die Exemplar Nummer, den Titel des Buches oder den Namen des Kundes ein, nach dem Sie suchen möchten");
		txtSearch.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent arg0)
			{
				search();
			}
		});
		txtSearch.setText("Suche");
		txtSearch.setColumns(10);
		GridBagConstraints gbc_txtSearch = new GridBagConstraints();
		gbc_txtSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSearch.anchor = GridBagConstraints.WEST;
		gbc_txtSearch.insets = new Insets(0, 0, 5, 5);
		gbc_txtSearch.gridx = 0;
		gbc_txtSearch.gridy = 0;
		panel_management.add(txtSearch, gbc_txtSearch);

		chckbxOnlyOverdue = new JCheckBox("Nur überfällige");
		chckbxOnlyOverdue.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent arg0)
			{
				// 1 = Selected, 2 = Not Selected
				// remove all entries
				// deleteTableRows(table);
				if (arg0.getStateChange() == 1)
				{ // If hook is set, add only
					addOverdueLoans(); // entries with available
					// Copies
					// (Number of Copies -
					// Number of Lent Copies)

				} else
				{ // if hook is not set, add all Books
					sorter.setRowFilter(null);
				}
			}

		});
		GridBagConstraints gbc_chckbxOnlyOverdue = new GridBagConstraints();
		gbc_chckbxOnlyOverdue.anchor = GridBagConstraints.EAST;
		gbc_chckbxOnlyOverdue.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxOnlyOverdue.gridx = 1;
		gbc_chckbxOnlyOverdue.gridy = 0;
		panel_management.add(chckbxOnlyOverdue, gbc_chckbxOnlyOverdue);
		chckbxOnlyOverdue.setToolTipText("Es werden nur überfällige Exemplare angezeigt");
		
		ImageIcon iconShowSelected = new ImageIcon("icons/book.png");
		btnShowSelected = new JButton("Selektiertes Anzeigen", iconShowSelected);
		btnShowSelected.setToolTipText("Zeigt das in der untenstehenden Tabelle ausgewählte Exemplar in einer Detailansicht an");
		btnShowSelected.setEnabled(false);
		btnShowSelected.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				int selected[] = table.getSelectedRows();
				for (int i : selected)
				{
					Loan loan = tableModel.getLoanAtRow(table.convertRowIndexToModel(i));
					new LoanDetail(library, loan);
				}
			}
		});
		GridBagConstraints gbc_btnShowSelected = new GridBagConstraints();
		gbc_btnShowSelected.insets = new Insets(0, 0, 5, 5);
		gbc_btnShowSelected.anchor = GridBagConstraints.EAST;
		gbc_btnShowSelected.gridx = 2;
		gbc_btnShowSelected.gridy = 0;
		panel_management.add(btnShowSelected, gbc_btnShowSelected);
		
		ImageIcon iconAddLoan = new ImageIcon("icons/book_go.png");
		btnAddLoan = new JButton("Neue Ausleihe erfassen", iconAddLoan);
		btnAddLoan.setToolTipText("Öffnet ein Fenster um eine neue Ausleihe zu tätigen");
		btnAddLoan.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new LoanDetail(library);
			}
		});
		GridBagConstraints gbc_btnAddLoan = new GridBagConstraints();
		gbc_btnAddLoan.anchor = GridBagConstraints.EAST;
		gbc_btnAddLoan.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddLoan.gridx = 3;
		gbc_btnAddLoan.gridy = 0;
		panel_management.add(btnAddLoan, gbc_btnAddLoan);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel_management.add(scrollPane, gbc_scrollPane);

		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				if (table.getSelectedRows().length > 0)
				{
					btnShowSelected.setEnabled(true);
				} else
				{
					btnShowSelected.setEnabled(false);
				}
			}
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2)
				{
					int selected[] = table.getSelectedRows();
					for (int i : selected)
					{
						Loan loan = tableModel.getLoanAtRow(table.convertRowIndexToModel(i));
						new LoanDetail(library, loan);
					}
				}
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);
		table.setModel(tableModel);
		table.getColumnModel().getColumn(0).setMinWidth(80);
		table.getColumnModel().getColumn(0).setMaxWidth(80);
		scrollPane.setViewportView(table);

	}

	private void search()
	{
		sorter = new TableRowSorter<TableModelLoanMaster>(tableModel);
		table.setRowSorter(sorter);
		RowFilter<TableModelLoanMaster, Object> rf = null;
		List<RowFilter<TableModelLoanMaster, Object>> filters = new ArrayList<RowFilter<TableModelLoanMaster, Object>>();
		// If current expression doesn't parse, don't update.
		try
		{
			RowFilter<TableModelLoanMaster, Object> rfID = RowFilter.regexFilter("(?i)^.*" + txtSearch.getText() + ".*", 1);
			RowFilter<TableModelLoanMaster, Object> rfTitle = RowFilter.regexFilter("(?i)^.*" + txtSearch.getText() + ".*", 2);
			RowFilter<TableModelLoanMaster, Object> rfCustomer = RowFilter.regexFilter("(?i)^.*" + txtSearch.getText() + ".*", 4);
			filters.add(rfID);
			filters.add(rfTitle);
			filters.add(rfCustomer);
			rf = RowFilter.orFilter(filters);
		} catch (java.util.regex.PatternSyntaxException e)
		{
			return;
		}
		sorter.setRowFilter(rf);
	}
	
	private void addOverdueLoans()
	{
		sorter = new TableRowSorter<TableModelLoanMaster>(tableModel);
		table.setRowSorter(sorter);
		RowFilter<TableModelLoanMaster, Object> rf = null;
		// If current expression doesn't parse, don't update.
		try
		{
			rf = RowFilter.regexFilter("(?i)^.*Fällig.*", 0);
		} catch (java.util.regex.PatternSyntaxException e)
		{
			return;
		}
		sorter.setRowFilter(rf);
	}

	@Override
	public void update(Observable o, Object arg)
	{
		updateFields();
	}

	private void updateFields()
	{
		updateStatistics();
	}
	
	private void updateStatistics()
	{
		lblAmountOfLoans.setText("Anzahl Bücher: " + library.getBooks().size());
		lblAmountOfLentLoans.setText("Anzahl Exemplare: " + library.getCopies().size());
	}

}
