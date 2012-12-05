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
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableRowSorter;

import tablemodel.TableModelBookMaster;
import tablemodel.TableModelLoanMaster;

import domain.Book;
import domain.Copy;
import domain.Library;
import domain.Loan;
import javax.swing.UIManager;

public class BookMasterLoanTab extends JPanel implements Observer
{
	private JTable table;
	private JTextField txtSearch;
	private List<Book> books;
	private List<Loan> loans;
	private TableRowSorter<TableModelLoanMaster> sorter;
	private TableModelLoanMaster tableModel;
	private Library library;
	private JCheckBox chckbxNurUeberfaellige;
	private JScrollPane scrollPane;
	private JLabel lblAnzahlAusleihen;
	private JLabel lblAktuellAusgeliehen;
	private JButton btnSelektiertesAnzeigen;
	private JButton btnNeueAusleihe;
	private JLabel lblUeberfaelligeAusleihen;
	private final String[] header = new String[] { "Status", "Exemplar-ID", "Titel", "Ausgeliehen Bis", "Ausgeliehen An" };
	private JPanel panel_management;

	public BookMasterLoanTab(Library library)
	{
		this.library = library;
		library.addObserver(this);
		initialize();
	}

	private void initialize()
	{
		GridBagLayout gbl_buecherTab = new GridBagLayout();
		gbl_buecherTab.columnWidths = new int[] { 0, 0 };
		gbl_buecherTab.rowHeights = new int[] { 0, 0, 0 };
		gbl_buecherTab.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_buecherTab.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		this.setLayout(gbl_buecherTab);

		JPanel panel_statistics = new JPanel();
		panel_statistics.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Statistiken", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		GridBagConstraints gbc_panel_statistics = new GridBagConstraints();
		gbc_panel_statistics.anchor = GridBagConstraints.NORTH;
		gbc_panel_statistics.insets = new Insets(0, 0, 5, 0);
		gbc_panel_statistics.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_statistics.gridx = 0;
		gbc_panel_statistics.gridy = 0;

		this.add(panel_statistics, gbc_panel_statistics);

		loans = library.getLoans();
		GridBagLayout gbl_panel_statistics = new GridBagLayout();
		gbl_panel_statistics.columnWidths = new int[] { 94, 106, 113, 0 };
		gbl_panel_statistics.rowHeights = new int[] { 14, 0 };
		gbl_panel_statistics.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_statistics.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_statistics.setLayout(gbl_panel_statistics);

		lblAnzahlAusleihen = new JLabel("Anzahl Ausleihen: " + loans.size());
		GridBagConstraints gbc_lblAnzahlAusleihen = new GridBagConstraints();
		gbc_lblAnzahlAusleihen.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAnzahlAusleihen.insets = new Insets(0, 0, 0, 5);
		gbc_lblAnzahlAusleihen.gridx = 0;
		gbc_lblAnzahlAusleihen.gridy = 0;
		panel_statistics.add(lblAnzahlAusleihen, gbc_lblAnzahlAusleihen);

		lblAktuellAusgeliehen = new JLabel("Aktuell Ausgeliehen: " + library.getLentOutBooks().size());
		GridBagConstraints gbc_lblAktuellAusgeliehen = new GridBagConstraints();
		gbc_lblAktuellAusgeliehen.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAktuellAusgeliehen.insets = new Insets(0, 0, 0, 5);
		gbc_lblAktuellAusgeliehen.gridx = 1;
		gbc_lblAktuellAusgeliehen.gridy = 0;
		panel_statistics.add(lblAktuellAusgeliehen, gbc_lblAktuellAusgeliehen);

		lblUeberfaelligeAusleihen = new JLabel("Überfällige Ausleihen: " + library.getOverdueLoans().size());
		GridBagConstraints gbc_lblUeberfaelligeAusleihen = new GridBagConstraints();
		gbc_lblUeberfaelligeAusleihen.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblUeberfaelligeAusleihen.gridx = 2;
		gbc_lblUeberfaelligeAusleihen.gridy = 0;
		panel_statistics.add(lblUeberfaelligeAusleihen, gbc_lblUeberfaelligeAusleihen);

		// Ab hier "Selektiertes Anzeigen"
		ImageIcon iconSelektiertesAnzeigen = new ImageIcon("icons/book.png");

		// Ab hier "Neues Buch hinzuf�gen"
		ImageIcon iconNeueAusleihe = new ImageIcon("icons/book_go.png");

		books = library.getBooks();
		tableModel = new TableModelLoanMaster(library, header);
		
		panel_management = new JPanel();
		panel_management.setBorder(new TitledBorder(null, "Ausleihenverwaltung", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_management = new GridBagConstraints();
		gbc_panel_management.fill = GridBagConstraints.BOTH;
		gbc_panel_management.gridx = 0;
		gbc_panel_management.gridy = 1;
		add(panel_management, gbc_panel_management);
		GridBagLayout gbl_panel_management = new GridBagLayout();
		gbl_panel_management.columnWidths = new int[]{38, 0, 0, 0, 0};
		gbl_panel_management.rowHeights = new int[]{0, 0, 0};
		gbl_panel_management.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_management.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel_management.setLayout(gbl_panel_management);
		
				// Ab hier "Suche"
				txtSearch = new JTextField();
				GridBagConstraints gbc_txtSearch = new GridBagConstraints();
				gbc_txtSearch.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtSearch.anchor = GridBagConstraints.WEST;
				gbc_txtSearch.insets = new Insets(0, 0, 5, 5);
				gbc_txtSearch.gridx = 0;
				gbc_txtSearch.gridy = 0;
				panel_management.add(txtSearch, gbc_txtSearch);
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
				
						// Ab hier "Nur �berf�llige"
						chckbxNurUeberfaellige = new JCheckBox("Nur überfällige");
						GridBagConstraints gbc_chckbxNurUeberfaellige = new GridBagConstraints();
						gbc_chckbxNurUeberfaellige.anchor = GridBagConstraints.EAST;
						gbc_chckbxNurUeberfaellige.insets = new Insets(0, 0, 5, 5);
						gbc_chckbxNurUeberfaellige.gridx = 1;
						gbc_chckbxNurUeberfaellige.gridy = 0;
						panel_management.add(chckbxNurUeberfaellige, gbc_chckbxNurUeberfaellige);
						chckbxNurUeberfaellige.setToolTipText("Es werden nur überfällige Exemplare angezeigt");
						btnSelektiertesAnzeigen = new JButton("Selektiertes Anzeigen", iconSelektiertesAnzeigen);
						GridBagConstraints gbc_btnSelektiertesAnzeigen = new GridBagConstraints();
						gbc_btnSelektiertesAnzeigen.insets = new Insets(0, 0, 5, 5);
						gbc_btnSelektiertesAnzeigen.anchor = GridBagConstraints.EAST;
						gbc_btnSelektiertesAnzeigen.gridx = 2;
						gbc_btnSelektiertesAnzeigen.gridy = 0;
						panel_management.add(btnSelektiertesAnzeigen, gbc_btnSelektiertesAnzeigen);
						btnSelektiertesAnzeigen.setToolTipText("Zeigt das in der untenstehenden Tabelle ausgewählte Exemplar in einer Detailansicht an");
						btnSelektiertesAnzeigen.setEnabled(false);
						btnNeueAusleihe = new JButton("Neue Ausleihe erfassen", iconNeueAusleihe);
						GridBagConstraints gbc_btnNeueAusleihe = new GridBagConstraints();
						gbc_btnNeueAusleihe.anchor = GridBagConstraints.EAST;
						gbc_btnNeueAusleihe.insets = new Insets(0, 0, 5, 0);
						gbc_btnNeueAusleihe.gridx = 3;
						gbc_btnNeueAusleihe.gridy = 0;
						panel_management.add(btnNeueAusleihe, gbc_btnNeueAusleihe);
						btnNeueAusleihe.setToolTipText("Öffnet ein Fenster um eine neue Ausleihe zu tätigen");
						
								scrollPane = new JScrollPane();
								GridBagConstraints gbc_scrollPane = new GridBagConstraints();
								gbc_scrollPane.fill = GridBagConstraints.BOTH;
								gbc_scrollPane.gridwidth = 4;
								gbc_scrollPane.gridx = 0;
								gbc_scrollPane.gridy = 1;
								panel_management.add(scrollPane, gbc_scrollPane);
								
										// Ab hier JTable
										table = new JTable();
										table.getTableHeader().setReorderingAllowed(false);
										table.addMouseListener(new MouseAdapter()
										{
											@Override
											public void mouseReleased(MouseEvent arg0)
											{
												if (table.getSelectedRows().length > 0)
												{
													btnSelektiertesAnzeigen.setEnabled(true);
												} else
												{
													btnSelektiertesAnzeigen.setEnabled(false);
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
														LoanDetail loanDetail = new LoanDetail(library, loan);
													}
												}
											}
										});
										table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
										table.setAutoCreateRowSorter(true);
										table.setModel(tableModel);
										
												table.getColumnModel().getColumn(0).setMinWidth(80);
												table.getColumnModel().getColumn(0).setMaxWidth(80);
												
														table.setBorder(new LineBorder(new Color(0, 0, 0)));
														scrollPane.setViewportView(table);
						btnNeueAusleihe.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								LoanDetail loanDetail = new LoanDetail(library);
							}
						});
						btnSelektiertesAnzeigen.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent arg0)
							{
								int selected[] = table.getSelectedRows();
								for (int i : selected)
								{
									Loan loan = tableModel.getLoanAtRow(table.convertRowIndexToModel(i));
									LoanDetail loanDetail = new LoanDetail(library, loan);
								}
							}
						});
						chckbxNurUeberfaellige.addItemListener(new ItemListener()
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
			RowFilter<TableModelLoanMaster, Object> rfTitel = RowFilter.regexFilter("(?i)^.*" + txtSearch.getText() + ".*", 2);
			RowFilter<TableModelLoanMaster, Object> rfKunde = RowFilter.regexFilter("(?i)^.*" + txtSearch.getText() + ".*", 4);
			filters.add(rfID);
			filters.add(rfTitel);
			filters.add(rfKunde);
			rf = RowFilter.orFilter(filters);
		} catch (java.util.regex.PatternSyntaxException e)
		{
			return;
		}
		sorter.setRowFilter(rf);
	}

	private void updateStats()
	{
		lblAnzahlAusleihen.setText("Anzahl Bücher: " + books.size());
		lblAktuellAusgeliehen.setText("Anzahl Exemplare: " + library.getCopies().size());
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
		this.books = library.getBooks();
		updateStats();
	}

}
