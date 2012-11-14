package view;

import java.awt.Color;
import java.awt.FlowLayout;
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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableRowSorter;

import domain.Book;
import domain.Library;
import domain.Loan;
import domain.TableModelBookMaster;
import domain.TableModelLoanMaster;

public class BookMasterLoanTab extends JPanel implements Observer
{
	private JTable table;
	private JTextField txtSuche;
	private List<Book> books;
	private List<Loan> loans;
	private TableRowSorter<TableModelLoanMaster> sorter;
	private TableModelLoanMaster tableModel;
	private Library library;
	private JCheckBox chckbxNurUeberfaellige;
	private JLabel lblAlleBcherDer;
	private JScrollPane scrollPane;
	private JLabel lblAnzahlAusleihen;
	private JLabel lblAktuellAusgeliehen;
	private JButton btnSelektierteAnzeigen;
	private JButton btnNeueAusleihe;
	private JLabel lblUeberfaelligeAusleihen;
	private final String[] header =  new String[] { "Status", "Exemplar-ID", "Titel", "Ausgeliehen Bis", "Ausgeliehen An" };
	
	public BookMasterLoanTab(Library library)
	{
		this.library = library;
		library.addObserver(this);
		initialize();
	}

	private void initialize()
	{
		GridBagLayout gbl_buecherTab = new GridBagLayout();
		gbl_buecherTab.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_buecherTab.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_buecherTab.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_buecherTab.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		this.setLayout(gbl_buecherTab);

		JPanel inventarStatistikenPanel = new JPanel();
		inventarStatistikenPanel.setBorder(new TitledBorder(null, "Inventar Statistiken", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		GridBagConstraints gbc_inventarStatistikenPanel = new GridBagConstraints();
		gbc_inventarStatistikenPanel.gridwidth = 5;
		gbc_inventarStatistikenPanel.anchor = GridBagConstraints.NORTH;
		gbc_inventarStatistikenPanel.insets = new Insets(0, 5, 5, 0);
		gbc_inventarStatistikenPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_inventarStatistikenPanel.gridx = 0;
		gbc_inventarStatistikenPanel.gridy = 0;
		
		this.add(inventarStatistikenPanel, gbc_inventarStatistikenPanel);
		inventarStatistikenPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));

		loans = library.getLoans();
		
		lblAnzahlAusleihen = new JLabel("Anzahl Ausleihen: " + loans.size());
		inventarStatistikenPanel.add(lblAnzahlAusleihen);

		lblAktuellAusgeliehen = new JLabel("Aktuell Ausgeliehen: " + library.getLentOutBooks().size());
		inventarStatistikenPanel.add(lblAktuellAusgeliehen);
		
		lblUeberfaelligeAusleihen = new JLabel("\u00DCberf\u00E4llige Ausleihen: " + library.getOverdueLoans().size());
		inventarStatistikenPanel.add(lblUeberfaelligeAusleihen);

		JPanel buchInventarPanel = new JPanel();
		buchInventarPanel.setBorder(new TitledBorder(null, "Buch Inventar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_buchInventarPanel = new GridBagConstraints();
		gbc_buchInventarPanel.insets = new Insets(0, 5, 5, 5);
		gbc_buchInventarPanel.gridheight = 3;
		gbc_buchInventarPanel.gridwidth = 5;
		gbc_buchInventarPanel.fill = GridBagConstraints.BOTH;
		gbc_buchInventarPanel.gridx = 0;
		gbc_buchInventarPanel.gridy = 1;
		this.add(buchInventarPanel, gbc_buchInventarPanel);
		
		GridBagLayout gbl_buchInventarPanel = new GridBagLayout();
		gbl_buchInventarPanel.columnWidths = new int[] { 69, 0, 131, 145, 0, 0, 0 };
		gbl_buchInventarPanel.rowHeights = new int[] { 0, 23, 0, 0 };
		gbl_buchInventarPanel.columnWeights = new double[] { 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_buchInventarPanel.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		buchInventarPanel.setLayout(gbl_buchInventarPanel);

		//Ab hier "Suche"
		txtSuche = new JTextField();
		txtSuche.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if (txtSuche.getText().contains("Suche")) {
					txtSuche.setText("");
				}
			}
		});
		txtSuche.setToolTipText("Geben Sie hier den Namen, Author oder Verlag eines Buches ein, dass Sie suchen möchten");
		txtSuche.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				// search();
				if (txtSuche.getText().length() > 0) {
					search(txtSuche.getText());
				} else {
					addAllLoans();
				}
			}
		});
				lblAlleBcherDer = new JLabel("Alle B\u00FCcher der Bibliothek sind in der untenstehenden Tabelle");
				GridBagConstraints gbc_lblAlleBcherDer = new GridBagConstraints();
				gbc_lblAlleBcherDer.gridwidth = 2;
				gbc_lblAlleBcherDer.insets = new Insets(0, 0, 5, 5);
				gbc_lblAlleBcherDer.gridx = 2;
				gbc_lblAlleBcherDer.gridy = 0;
				buchInventarPanel.add(lblAlleBcherDer, gbc_lblAlleBcherDer);
		txtSuche.setText("Suche");
		GridBagConstraints gbc_txtSuche = new GridBagConstraints();
		gbc_txtSuche.gridwidth = 2;
		gbc_txtSuche.insets = new Insets(0, 5, 5, 5);
		gbc_txtSuche.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSuche.gridx = 1;
		gbc_txtSuche.gridy = 1;
		buchInventarPanel.add(txtSuche, gbc_txtSuche);
		txtSuche.setColumns(10);

		//Ab hier "Selektierte Anzeigen"
		btnSelektierteAnzeigen = new JButton("Selektierte Anzeigen");
		btnSelektierteAnzeigen.setEnabled(false);
		btnSelektierteAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selected[] = table.getSelectedRows();
				for (int i : selected) {
					Book book = library.findByBookTitle(table.getModel().getValueAt(i, 1).toString());
					BookDetail bookDetail = new BookDetail(book, library);
				}
			}
		});

		//Ab hier "Nur Verfügbare"
		chckbxNurUeberfaellige = new JCheckBox("Nur Nur \u00DCberf\u00E4llige");
		chckbxNurUeberfaellige.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				// 1 = Selected, 2 = Not Selected
				// remove all entries
				//deleteTableRows(table);

				if (arg0.getStateChange() == 1) { // If hook is set, add only
						addOverdueLoans();							// entries with available
													// Copies
													// (Number of Copies -
													// Number of Lent Copies)
					
				} else { // if hook is not set, add all Books
					addAllLoans();
				}
			}

		});

		GridBagConstraints gbc_chckbxNurUeberfaellige = new GridBagConstraints();
		gbc_chckbxNurUeberfaellige.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNurUeberfaellige.gridx = 3;
		gbc_chckbxNurUeberfaellige.gridy = 1;
		buchInventarPanel.add(chckbxNurUeberfaellige, gbc_chckbxNurUeberfaellige);
		GridBagConstraints gbc_btnSelektierteAnzeigen = new GridBagConstraints();
		gbc_btnSelektierteAnzeigen.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnSelektierteAnzeigen.insets = new Insets(0, 0, 5, 5);
		gbc_btnSelektierteAnzeigen.gridx = 4;
		gbc_btnSelektierteAnzeigen.gridy = 1;
		buchInventarPanel.add(btnSelektierteAnzeigen, gbc_btnSelektierteAnzeigen);

		//Ab hier "Neues Buch hinzufügen"
		btnNeueAusleihe = new JButton("Neues Ausleihe erfassen");
		btnNeueAusleihe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookAdd bookAdd = new BookAdd(library);
			}
		});		
		GridBagConstraints gbc_btnNeueAusleihe = new GridBagConstraints();
		gbc_btnNeueAusleihe.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNeueAusleihe.insets = new Insets(0, 0, 5, 0);
		gbc_btnNeueAusleihe.gridx = 5;
		gbc_btnNeueAusleihe.gridy = 1;
		buchInventarPanel.add(btnNeueAusleihe, gbc_btnNeueAusleihe);

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 6;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		buchInventarPanel.add(scrollPane, gbc_scrollPane);
		
		//Ab hier JTable
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (table.getSelectedRows().length > 0) {
					btnSelektierteAnzeigen.setEnabled(true);
				} else {
					btnSelektierteAnzeigen.setEnabled(false);
				}
			}
		});
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setAutoCreateRowSorter(true);
		
		books = library.getBooks();
		tableModel = new TableModelLoanMaster(library, loans, header);
		table.setModel(tableModel);

		table.getColumnModel().getColumn(0).setMinWidth(80);
		table.getColumnModel().getColumn(0).setMaxWidth(80);

		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setViewportView(table);

		sorter = new TableRowSorter<TableModelLoanMaster>(tableModel);
		table.setRowSorter(sorter);		
	}
	
//	private void search() {
//		RowFilter<TableModelBookMaster, Object> rf = null;
//		// If current expression doesn't parse, don't update.
//		try {
//			rf = RowFilter.regexFilter(txtSuche.getText(), 0);
//		} catch (java.util.regex.PatternSyntaxException e) {
//			return;
//		}
//		sorter.setRowFilter(rf);
//	}

//	/**
//	 * Deletes all rows from a Table
//	 * 
//	 * @param _table
//	 *            Table to delete the entries(rows) from
//	 */
//	private void deleteTableRows(JTable _table) {
//		for (int i = _table.getRowCount() - 1; i >= 0; i--) {
//			((DefaultTableModel) _table.getModel()).removeRow(i);
//		}
//	}

	/**
	 * Adds all Books from the Library to the Inventary Table
	 */
	private void addAllLoans() {
		tableModel = new TableModelLoanMaster(library, loans, header);
		table.setModel(tableModel);
		tableModel.fireTableDataChanged();
		/*for (int i = 0; i < books.size(); i++) {
			String[] s = {
					""
							+ (library.getCopiesOfBook(books.get(i)).size() - library.getLentCopiesOfBook(
									books.get(i)).size()), books.get(i).getName(), books.get(i).getAuthor(),
					books.get(i).getPublisher() };
			((DefaultTableModel) table.getModel()).addRow(s);

		}*/
	}

	/**
	 * Adds only Available Books from the Library to the Inventary Table
	 
	private void addAvailableBooks() {
		List<Loan> loansToDisplay = new ArrayList<Loan>();
		for (int x = 0; x < loans.size(); x++) {
			if ((library.getCopiesOfBook(loans.get(x)).size() - library.getLentCopiesOfBook(books.get(x)).size()) > 0) {
				loansToDisplay.add(loans.get(x));
				/*
				 * String[] s = { "" +
				 * (library.getCopiesOfBook(books.get(x)).size() - library
				 * .getLentCopiesOfBook(books.get(x)).size()),
				 * books.get(x).getName(), books.get(x).getAuthor(),
				 * books.get(x).getPublisher() }; ((DefaultTableModel)
				 * table.getModel()).addRow(s);
				 

			}
		}
		tableModel = new TableModelLoanMaster(library, loansToDisplay, header);
		table.setModel(tableModel);
		tableModel.fireTableDataChanged();
	}*/

//	private void addBooks(List<Book> l) {
//		deleteTableRows(table);
//		for (int i = 0; i < l.size(); i++) {
//			String[] s = {"" + (library.getCopiesOfBook(l.get(i)).size() - library.getLentCopiesOfBook(l.get(i)).size()), l.get(i).getName(), l.get(i).getAuthor(), l.get(i).getPublisher() };
//			((DefaultTableModel) table.getModel()).addRow(s);
//		}
//	}

	private void search(String s) {
		List<Loan> loansToDisplay = new ArrayList<Loan>();
		for (int i = 0; i < books.size(); i++) {
			if (books.get(i).getName().contains(s)) {
				loansToDisplay.add(loans.get(i));
			}
			if (books.get(i).getAuthor().contains(s)) {
				loansToDisplay.add(loans.get(i));
			}
			if (books.get(i).getPublisher().contains(s)) {
				loansToDisplay.add(loans.get(i));
			}
		}
		// addBooks(booksToDisplay);
		tableModel = new TableModelLoanMaster(library, loansToDisplay, header);
		table.setModel(tableModel);
		tableModel.fireTableDataChanged();
	}
	
	private void updateStats(){
		lblAnzahlAusleihen.setText("Anzahl Bücher: " + books.size());
		lblAktuellAusgeliehen.setText("Anzahl Exemplare: "
				+ (library.getBooks().size() + library.getCopies().size()));
	}
	
	private void addOverdueLoans() {
		List<Loan> overdueLoans = library.getOverdueLoans();
		tableModel = new TableModelLoanMaster(library, overdueLoans, header);
		table.setModel(tableModel);
		tableModel.fireTableDataChanged();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		updateFields();
	}

	private void updateFields() {
		this.books = library.getBooks();
		addAllLoans();
		updateStats();
	}

}
