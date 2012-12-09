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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableRowSorter;

import renderer.IconAndDescriptionRenderer;
import tablemodel.TableModelTabLoan;
import domain.Library;
import domain.Loan;

public class TabLoan extends JPanel implements Observer {

    private static final long serialVersionUID = -7205105883833687794L;
    
    private Library library;
	private JLabel lblAmountOfLoans;
	private JLabel lblAmountOfLentLoans;
	private JLabel lblAmountOfOverdueLoans;
	private JTextField txtSearch;
	private JCheckBox chckbxOnlyOverdue;
	private TableModelTabLoan tableModel;
	private JTable table;
	private TableRowSorter<TableModelTabLoan> sorter;
	private JButton btnShowSelected;
	private JButton btnAddLoan;
	private final String[] header = new String[] { "Status", "Exemplar-ID", "Titel", "Ausgeliehen Bis", "Ausgeliehen An" };
	private JLabel lblLoans;
	private JLabel lblLentLoans;
	private JLabel lblOverdueLoans;
	private int selectedRow = 0;

	public TabLoan(Library library) {
		this.library = library;
		library.addObserver(this);
		initialize();
		updateStatistics();
	}

	private void initialize() {
		GridBagLayout gbl_booksTab = new GridBagLayout();
		gbl_booksTab.columnWidths = new int[] { 0, 0 };
		gbl_booksTab.rowHeights = new int[] { 0, 0, 0 };
		gbl_booksTab.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_booksTab.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		this.setLayout(gbl_booksTab);

		JPanel panel_statistics = new JPanel();
		panel_statistics.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Statistiken", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		GridBagConstraints gbc_panel_statistics = new GridBagConstraints();
		gbc_panel_statistics.anchor = GridBagConstraints.NORTH;
		gbc_panel_statistics.insets = new Insets(0, 0, 5, 0);
		gbc_panel_statistics.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_statistics.gridx = 0;
		gbc_panel_statistics.gridy = 0;

		this.add(panel_statistics, gbc_panel_statistics);

		GridBagLayout gbl_panel_statistics = new GridBagLayout();
		gbl_panel_statistics.columnWidths = new int[] { 0, 0, 0, 0, 0, 113, 0 };
		gbl_panel_statistics.rowHeights = new int[] { 14, 0 };
		gbl_panel_statistics.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_statistics.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_statistics.setLayout(gbl_panel_statistics);

		lblLoans = new JLabel("Anzahl Ausleihen:");
		GridBagConstraints gbc_lblLoans = new GridBagConstraints();
		gbc_lblLoans.insets = new Insets(0, 0, 0, 5);
		gbc_lblLoans.gridx = 0;
		gbc_lblLoans.gridy = 0;
		panel_statistics.add(lblLoans, gbc_lblLoans);

		lblAmountOfLoans = new JLabel("Anzahl Ausleihen: " + library.getLoans().size());
		GridBagConstraints gbc_lblAmountOfLoans = new GridBagConstraints();
		gbc_lblAmountOfLoans.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAmountOfLoans.insets = new Insets(0, 0, 0, 20);
		gbc_lblAmountOfLoans.gridx = 1;
		gbc_lblAmountOfLoans.gridy = 0;
		panel_statistics.add(lblAmountOfLoans, gbc_lblAmountOfLoans);

		lblLentLoans = new JLabel("Aktuelle Ausleihen:");
		GridBagConstraints gbc_lblLentLoans = new GridBagConstraints();
		gbc_lblLentLoans.anchor = GridBagConstraints.WEST;
		gbc_lblLentLoans.insets = new Insets(0, 0, 0, 5);
		gbc_lblLentLoans.gridx = 2;
		gbc_lblLentLoans.gridy = 0;
		panel_statistics.add(lblLentLoans, gbc_lblLentLoans);

		lblAmountOfLentLoans = new JLabel();
		GridBagConstraints gbc_lblAmountOfLentLoans = new GridBagConstraints();
		gbc_lblAmountOfLentLoans.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAmountOfLentLoans.insets = new Insets(0, 0, 0, 20);
		gbc_lblAmountOfLentLoans.gridx = 3;
		gbc_lblAmountOfLentLoans.gridy = 0;
		panel_statistics.add(lblAmountOfLentLoans, gbc_lblAmountOfLentLoans);

		lblOverdueLoans = new JLabel("Überfällige Ausleihen:");
		GridBagConstraints gbc_lblOverdueLoans = new GridBagConstraints();
		gbc_lblOverdueLoans.anchor = GridBagConstraints.WEST;
		gbc_lblOverdueLoans.insets = new Insets(0, 0, 0, 5);
		gbc_lblOverdueLoans.gridx = 4;
		gbc_lblOverdueLoans.gridy = 0;
		panel_statistics.add(lblOverdueLoans, gbc_lblOverdueLoans);

		lblAmountOfOverdueLoans = new JLabel();
		GridBagConstraints gbc_lblAmountOfOverdueLoans = new GridBagConstraints();
		gbc_lblAmountOfOverdueLoans.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAmountOfOverdueLoans.gridx = 5;
		gbc_lblAmountOfOverdueLoans.gridy = 0;
		panel_statistics.add(lblAmountOfOverdueLoans, gbc_lblAmountOfOverdueLoans);

		tableModel = new TableModelTabLoan(library, header);

		JPanel panel_management = new JPanel();
		panel_management.setBorder(new TitledBorder(null, "Ausleihenverwaltung", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
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
		txtSearch.setText("Suche");
		txtSearch.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if (txtSearch.getText().contains("Suche")) {
					txtSearch.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if (txtSearch.getText().contains("")) {
					txtSearch.setText("Suche");
				}
			}
		});
		txtSearch.setToolTipText("Geben Sie hier die Exemplar Nummer, " +
				"den Titel des Buches oder den Namen des Kundes ein, nach dem Sie suchen möchten");
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				search();
			}
		});
		txtSearch.setColumns(10);
		GridBagConstraints gbc_txtSearch = new GridBagConstraints();
		gbc_txtSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSearch.anchor = GridBagConstraints.WEST;
		gbc_txtSearch.insets = new Insets(0, 0, 5, 5);
		gbc_txtSearch.gridx = 0;
		gbc_txtSearch.gridy = 0;
		panel_management.add(txtSearch, gbc_txtSearch);

		chckbxOnlyOverdue = new JCheckBox("Nur überfällige");
		chckbxOnlyOverdue.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				// 1 = Selected, 2 = Not Selected
				// remove all entries
				// deleteTableRows(table);
				if (arg0.getStateChange() == 1) { // If hook is set, add only
					addOverdueLoans();
					btnShowSelected.setEnabled(false);// entries with available
					// Copies
					// (Number of Copies -
					// Number of Lent Copies)

				} else { // if hook is not set, add all Books
					sorter.setRowFilter(null);
					btnShowSelected.setEnabled(false);
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
		btnShowSelected
				.setToolTipText("Zeigt das in der untenstehenden Tabelle ausgewählte Exemplar in einer Detailansicht an");
		btnShowSelected.setEnabled(false);
		btnShowSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selected[] = table.getSelectedRows();
				for (int i : selected) {
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
		btnAddLoan = new JButton("Neue Ausleihe", iconAddLoan);
		btnAddLoan.setToolTipText("Öffnet ein Fenster um eine neue Ausleihe zu tätigen");
		btnAddLoan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (table.getSelectedRows().length > 0) {
					btnShowSelected.setEnabled(true);
				} else {
					btnShowSelected.setEnabled(false);
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int selected[] = table.getSelectedRows();
					for (int i : selected) {
						Loan loan = tableModel.getLoanAtRow(table.convertRowIndexToModel(i));
						new LoanDetail(library, loan);
					}
				}
			}
		});

		table.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.getKeyCode() == 10) {
					if (table.getSelectedRow() != -1) {
						table.setRowSelectionInterval(table.getSelectedRow() - 1, table.getSelectedRow() - 1);
						Loan loan = tableModel.getLoanAtRow(table.convertRowIndexToModel(table.getSelectedRow()));
						new LoanDetail(library, loan);
					}
				}
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);
		table.setModel(tableModel);
		table.getColumnModel().getColumn(0).setCellRenderer(new IconAndDescriptionRenderer());
		table.getColumnModel().getColumn(0).setMinWidth(70);
		table.getColumnModel().getColumn(0).setMaxWidth(70);
		table.getColumnModel().getColumn(1).setMinWidth(80);
		table.getColumnModel().getColumn(1).setMaxWidth(80);
		table.getColumnModel().getColumn(2).setMinWidth(350);
		table.getColumnModel().getColumn(2).setMaxWidth(350);
		scrollPane.setViewportView(table);

		sorter = new TableRowSorter<TableModelTabLoan>(tableModel);
		table.setRowSorter(sorter);
		sorter.setSortsOnUpdates(true);
		sorter.toggleSortOrder(2);

		
		// Save selected row table
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent e) {
		        selectedRow = e.getFirstIndex();
		    }
		});

		// Restore selected raw table
		tableModel.addTableModelListener(new TableModelListener() {      
		    @Override
		    public void tableChanged(TableModelEvent e) {
		        SwingUtilities.invokeLater(new Runnable() {
		            @Override
		            public void run() {
		                if (selectedRow >= 0) {
		                            table.setRowSelectionInterval(selectedRow, selectedRow);
		                }
		             }
		        });
		    }
		});
	}

	private void search() {
		sorter = new TableRowSorter<TableModelTabLoan>(tableModel);
		table.setRowSorter(sorter);
		RowFilter<TableModelTabLoan, Object> rf = null;
		List<RowFilter<TableModelTabLoan, Object>> filters = new ArrayList<RowFilter<TableModelTabLoan, Object>>();
		// If current expression doesn't parse, don't update.
		try {
			RowFilter<TableModelTabLoan, Object> rfID = RowFilter.regexFilter("(?i)^.*" + txtSearch.getText()
					+ ".*", 1);
			RowFilter<TableModelTabLoan, Object> rfTitle = RowFilter.regexFilter(
					"(?i)^.*" + txtSearch.getText() + ".*", 2);
			RowFilter<TableModelTabLoan, Object> rfCustomer = RowFilter.regexFilter(
					"(?i)^.*" + txtSearch.getText() + ".*", 4);
			filters.add(rfID);
			filters.add(rfTitle);
			filters.add(rfCustomer);
			rf = RowFilter.orFilter(filters);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(rf);
	}

	private void addOverdueLoans() {
		sorter = new TableRowSorter<TableModelTabLoan>(tableModel);
		table.setRowSorter(sorter);
		RowFilter<TableModelTabLoan, Object> rf = null;
		// If current expression doesn't parse, don't update.
		try {
			rf = RowFilter.regexFilter("(?i)^.*Fällig.*", 0);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(rf);
	}

	@Override
	public void update(Observable o, Object arg) {
		updateFields();
	}

	private void updateFields() {
		updateStatistics();
	}

	private void updateStatistics() {
		lblAmountOfLoans.setText(String.valueOf(library.getLoans().size()));
		lblAmountOfLentLoans.setText(String.valueOf(library.getLentOutCopies().size()));
		lblAmountOfOverdueLoans.setText(String.valueOf(library.getOverdueLoans().size()));
	}

}
