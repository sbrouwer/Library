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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
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

import tablemodel.TableModelTabBook;
import domain.Book;
import domain.Library;

public class TabBook extends JPanel implements Observer
{
    private static final long serialVersionUID = 3516134185601989079L;
    
    private JTextField txtSearch;
	private Library library;
	private JLabel lblAmountOfBooks;
	private JLabel lblAmountOfCopies;
	private JTable table;
	private TableModelTabBook tableModel;
	private TableRowSorter<TableModelTabBook> sorter;
	private JButton btnBookDetail;
	private HashMap<Book, BookDetail> openOnce;
	private int selectedRow = 0;
	
	public TabBook(Library library)
	{
		this.library = library;
		library.addObserver(this);
		openOnce = new HashMap<Book, BookDetail>();
		initialize();
		updateStatistics();
	}

	private void initialize()
	{
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panel_statistics = new JPanel();
		panel_statistics.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), 
		        "Statistiken", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_statistics = new GridBagConstraints();
		gbc_panel_statistics.insets = new Insets(0, 0, 5, 0);
		gbc_panel_statistics.fill = GridBagConstraints.BOTH;
		gbc_panel_statistics.gridx = 0;
		gbc_panel_statistics.gridy = 0;
		add(panel_statistics, gbc_panel_statistics);
		GridBagLayout gbl_panel_statistics = new GridBagLayout();
		gbl_panel_statistics.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel_statistics.rowHeights = new int[]{0, 0};
		gbl_panel_statistics.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_statistics.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_statistics.setLayout(gbl_panel_statistics);
		
		JLabel lblBooks = new JLabel("Anzahl Bücher:");
		GridBagConstraints gbc_lblBooks = new GridBagConstraints();
		gbc_lblBooks.anchor = GridBagConstraints.WEST;
		gbc_lblBooks.insets = new Insets(0, 0, 0, 5);
		gbc_lblBooks.gridx = 0;
		gbc_lblBooks.gridy = 0;
		panel_statistics.add(lblBooks, gbc_lblBooks);
		
		lblAmountOfBooks = new JLabel();
		GridBagConstraints gbc_lblAmountOfBooks = new GridBagConstraints();
		gbc_lblAmountOfBooks.insets = new Insets(0, 0, 0, 20);
		gbc_lblAmountOfBooks.gridx = 1;
		gbc_lblAmountOfBooks.gridy = 0;
		panel_statistics.add(lblAmountOfBooks, gbc_lblAmountOfBooks);
		
		JLabel lblCopies = new JLabel("Anzahl Exemplare:");
		GridBagConstraints gbc_lblCopies = new GridBagConstraints();
		gbc_lblCopies.anchor = GridBagConstraints.WEST;
		gbc_lblCopies.insets = new Insets(0, 0, 0, 5);
		gbc_lblCopies.gridx = 2;
		gbc_lblCopies.gridy = 0;
		panel_statistics.add(lblCopies, gbc_lblCopies);
		
		lblAmountOfCopies = new JLabel();
		GridBagConstraints gbc_lblAmountOfCopies = new GridBagConstraints();
		gbc_lblAmountOfCopies.anchor = GridBagConstraints.WEST;
		gbc_lblAmountOfCopies.gridx = 3;
		gbc_lblAmountOfCopies.gridy = 0;
		panel_statistics.add(lblAmountOfCopies, gbc_lblAmountOfCopies);
		
		JPanel panel_management = new JPanel();
		panel_management.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), 
		        "B\u00FCcherverwaltung", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_management = new GridBagConstraints();
		gbc_panel_management.fill = GridBagConstraints.BOTH;
		gbc_panel_management.gridx = 0;
		gbc_panel_management.gridy = 1;
		add(panel_management, gbc_panel_management);
		GridBagLayout gbl_panel_management = new GridBagLayout();
		gbl_panel_management.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel_management.rowHeights = new int[]{0, 0, 0};
		gbl_panel_management.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_management.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel_management.setLayout(gbl_panel_management);
		
		txtSearch = new JTextField();
		txtSearch.setToolTipText("Geben Sie hier den Namen, Author oder Verlag eines Buches ein, dass Sie suchen möchten");
		txtSearch.setText("Suche");
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
			@Override
			public void focusLost(FocusEvent arg0)
			{
				if (txtSearch.getText().contains(""))
				{
					txtSearch.setText("Suche");
				}
			}
		});
		txtSearch.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent arg0)
			{
				search();
			}
		});
		txtSearch.setColumns(10);
		GridBagConstraints gbc_txtSearch = new GridBagConstraints();
		gbc_txtSearch.insets = new Insets(0, 0, 5, 5);
		gbc_txtSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSearch.gridx = 0;
		gbc_txtSearch.gridy = 0;
		panel_management.add(txtSearch, gbc_txtSearch);
		
		JCheckBox chckbxOnlyAvailable = new JCheckBox("Nur verfügbare");
		chckbxOnlyAvailable.setToolTipText("Falls markiert, werden nur Bücher mit verfügbaren Exemplaren angezeigt");
		chckbxOnlyAvailable.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent arg0)
			{
				if (arg0.getStateChange() == 1)
				{ 
					addAvailableBooks();
					btnBookDetail.setEnabled(false);
				} else
				{ 
					sorter.setRowFilter(null);
					btnBookDetail.setEnabled(false);
				}
			}
		});
		GridBagConstraints gbc_chckbxOnlyAvailable = new GridBagConstraints();
		gbc_chckbxOnlyAvailable.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxOnlyAvailable.gridx = 1;
		gbc_chckbxOnlyAvailable.gridy = 0;
		panel_management.add(chckbxOnlyAvailable, gbc_chckbxOnlyAvailable);
		
		ImageIcon iconBookDetail = new ImageIcon("icons/book.png");
		btnBookDetail = new JButton("Selektiertes anzeigen", iconBookDetail);
		btnBookDetail.setToolTipText("Zeigt das in der untenstehenden Tabelle ausgewählte Buch in einer Detailansicht an");
		btnBookDetail.setEnabled(false);
		btnBookDetail.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				openBookDetail();			
			}
		});
		GridBagConstraints gbc_btnBookDetail = new GridBagConstraints();
		gbc_btnBookDetail.insets = new Insets(0, 0, 5, 5);
		gbc_btnBookDetail.gridx = 2;
		gbc_btnBookDetail.gridy = 0;
		panel_management.add(btnBookDetail, gbc_btnBookDetail);
		
		ImageIcon iconBookAdd = new ImageIcon("icons/book_add.png");
		JButton btnNewBook = new JButton("Neues Buch", iconBookAdd);
		btnNewBook.setToolTipText("Öffnet ein Fenster um ein neues Buch zu erfassen");
		btnNewBook.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new BookAdd(library);
			}
		});
		GridBagConstraints gbc_btnNewBook = new GridBagConstraints();
		gbc_btnNewBook.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewBook.gridx = 3;
		gbc_btnNewBook.gridy = 0;
		panel_management.add(btnNewBook, gbc_btnNewBook);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel_management.add(scrollPane, gbc_scrollPane);

		table = new JTable();
		table.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(arg0.getKeyCode() == 10){
					if(table.getSelectedRow() != -1){
						table.setRowSelectionInterval(table.getSelectedRow() -1, table.getSelectedRow() -1);
						openBookDetail();
					}
				}
			}
		});
		
		table.getTableHeader().setReorderingAllowed(false);
		table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				if (table.getSelectedRows().length > 0)
				{
					btnBookDetail.setEnabled(true);
				} else
				{
					btnBookDetail.setEnabled(false);
				}
			}
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2)
				{
					openBookDetail();
					
				}
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);
		
		tableModel = new TableModelTabBook(library, new String[] { "Verfügbar", "Name", "Autor", "Verlag" });
		table.setModel(tableModel);
		table.getColumnModel().getColumn(0).setMinWidth(80);
		table.getColumnModel().getColumn(0).setMaxWidth(80);
		
		scrollPane.setViewportView(table);	

		sorter = new TableRowSorter<TableModelTabBook>(tableModel);
		table.setRowSorter(sorter);
		sorter.setSortsOnUpdates(true);
		sorter.toggleSortOrder(1);
		
		
		
		
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
	
	private void openBookDetail(){
		int selected[] = table.getSelectedRows();
		for (int i : selected)
		{
			Book book = tableModel.getBookAtRow(table.convertRowIndexToModel(i));
			if (openOnce.containsKey(book))
			{
				openOnce.get(book).setVisible(true);
				openOnce.get(book).toFront();
			}
			else {		
				final Book bookToRemove = book;
				BookDetail bookDetail = new BookDetail(book, library);
				bookDetail.addWindowListener(new WindowAdapter(){							 
					public void windowClosing(WindowEvent e)
					{
						openOnce.remove(bookToRemove);
					}				
				});
				openOnce.put(book, bookDetail);
			}
		}
	}

	private void addAvailableBooks()
	{
		sorter = new TableRowSorter<TableModelTabBook>(tableModel);
		table.setRowSorter(sorter);
		RowFilter<TableModelTabBook, Object> rf = null;
		try
		{
			rf = RowFilter.regexFilter("[^0]", 0);
		} catch (java.util.regex.PatternSyntaxException e)
		{
			return;
		}
		sorter.setRowFilter(rf);
	}

	private void search()
	{
		RowFilter<TableModelTabBook, Object> rf = null;
		List<RowFilter<TableModelTabBook, Object>> filters = new ArrayList<RowFilter<TableModelTabBook, Object>>();
		try
		{
			RowFilter<TableModelTabBook, Object> rfTitle = RowFilter.regexFilter("(?i)^.*" + txtSearch.getText() + ".*", 1);
			RowFilter<TableModelTabBook, Object> rfAuthor = RowFilter.regexFilter("(?i)^.*" + txtSearch.getText() + ".*", 2);
			RowFilter<TableModelTabBook, Object> rfPublisher = RowFilter.regexFilter("(?i)^.*" + txtSearch.getText() + ".*", 3);
			filters.add(rfAuthor);
			filters.add(rfTitle);
			filters.add(rfPublisher);
			rf = RowFilter.orFilter(filters);
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
	
	private void updateFields() {
		updateStatistics();
	}
	
	private void updateStatistics()
	{
		lblAmountOfBooks.setText(String.valueOf(library.getBooks().size()));
		lblAmountOfCopies.setText(String.valueOf(library.getCopies().size()));
	}
	
}
