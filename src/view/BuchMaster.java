package view;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.ListModel;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JTabbedPane;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.AbstractListModel;

import domain.Book;
import domain.Library;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javax.swing.JScrollPane;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class BuchMaster extends Observable {

	private JFrame frame;
	private JPanel buecherTab;
	private JLabel lblAnzahlBuecher;
	private JLabel lblAnzahlExemplare;
	private JButton btnSelektierteAnzeigen;
	private JButton btnNeuesBuch;
	private List<Book> books;
	private Library library;
	private JScrollPane scrollPane;
	private JTable table;
	private JTextField txtSuche;
	private JCheckBox chckbxNurVerfgbare;
	private JLabel lblAlleBcherDer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BuchMaster window = new BuchMaster();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BuchMaster() {
		initialize();
		frame.setVisible(true);
	}

	public BuchMaster(Library library) {
		this.library = library;
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {


		frame = new JFrame();
		frame.setBounds(100, 100, 607, 516);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Swinging Library");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 545, 0 };
		gridBagLayout.rowHeights = new int[] { 237, 5, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);
		
		books = library.getBooks();
		
		JTabbedPane buchMasterTabs = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_buchMasterTabs = new GridBagConstraints();
		gbc_buchMasterTabs.gridheight = 2;
		gbc_buchMasterTabs.insets = new Insets(0, 5, 5, 0);
		gbc_buchMasterTabs.fill = GridBagConstraints.BOTH;
		gbc_buchMasterTabs.gridx = 0;
		gbc_buchMasterTabs.gridy = 0;
		frame.getContentPane().add(buchMasterTabs, gbc_buchMasterTabs);

		buecherTab = new JPanel();
		buchMasterTabs.addTab("B\u00FCcher", null, buecherTab, null);
		GridBagLayout gbl_buecherTab = new GridBagLayout();
		gbl_buecherTab.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_buecherTab.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_buecherTab.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_buecherTab.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		buecherTab.setLayout(gbl_buecherTab);

		JPanel inventarStatistikenPanel = new JPanel();
		inventarStatistikenPanel.setBorder(new TitledBorder(null,
				"Inventar Statistiken", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		GridBagConstraints gbc_inventarStatistikenPanel = new GridBagConstraints();
		gbc_inventarStatistikenPanel.gridwidth = 5;
		gbc_inventarStatistikenPanel.anchor = GridBagConstraints.NORTH;
		gbc_inventarStatistikenPanel.insets = new Insets(0, 5, 5, 0);
		gbc_inventarStatistikenPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_inventarStatistikenPanel.gridx = 0;
		gbc_inventarStatistikenPanel.gridy = 0;
		buecherTab.add(inventarStatistikenPanel, gbc_inventarStatistikenPanel);
		inventarStatistikenPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 50,
				5));

		lblAnzahlBuecher = new JLabel("Anzahl B\u00FCcher: 865");
		inventarStatistikenPanel.add(lblAnzahlBuecher);

		lblAnzahlExemplare = new JLabel("Anzahl Exemplare: 2200");
		inventarStatistikenPanel.add(lblAnzahlExemplare);

		lblAlleBcherDer = new JLabel(
				"Alle B\u00FCcher der Bibliothek sind in der untenstehenden Tabelle");
		GridBagConstraints gbc_lblAlleBcherDer = new GridBagConstraints();
		gbc_lblAlleBcherDer.anchor = GridBagConstraints.WEST;
		gbc_lblAlleBcherDer.insets = new Insets(0, 20, 5, 5);
		gbc_lblAlleBcherDer.gridx = 0;
		gbc_lblAlleBcherDer.gridy = 1;
		buecherTab.add(lblAlleBcherDer, gbc_lblAlleBcherDer);

		JPanel buchInventarPanel = new JPanel();
		buchInventarPanel.setBorder(new TitledBorder(null, "Buch Inventar",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_buchInventarPanel = new GridBagConstraints();
		gbc_buchInventarPanel.insets = new Insets(0, 5, 5, 5);
		gbc_buchInventarPanel.gridheight = 2;
		gbc_buchInventarPanel.gridwidth = 5;
		gbc_buchInventarPanel.fill = GridBagConstraints.BOTH;
		gbc_buchInventarPanel.gridx = 0;
		gbc_buchInventarPanel.gridy = 2;
		buecherTab.add(buchInventarPanel, gbc_buchInventarPanel);
		GridBagLayout gbl_buchInventarPanel = new GridBagLayout();
		gbl_buchInventarPanel.columnWidths = new int[] { 69, 0, 131, 145, 0, 0,
				0 };
		gbl_buchInventarPanel.rowHeights = new int[] { 23, 0, 0 };
		gbl_buchInventarPanel.columnWeights = new double[] { 1.0, 1.0, 0.0,
				0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_buchInventarPanel.rowWeights = new double[] { 0.0, 1.0,
				Double.MIN_VALUE };
		buchInventarPanel.setLayout(gbl_buchInventarPanel);

		txtSuche = new JTextField();
		txtSuche.setText("Suche");
		GridBagConstraints gbc_txtSuche = new GridBagConstraints();
		gbc_txtSuche.gridwidth = 2;
		gbc_txtSuche.insets = new Insets(0, 5, 5, 5);
		gbc_txtSuche.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSuche.gridx = 1;
		gbc_txtSuche.gridy = 0;
		buchInventarPanel.add(txtSuche, gbc_txtSuche);
		txtSuche.setColumns(10);

		btnSelektierteAnzeigen = new JButton("Selektierte Anzeigen");
		btnSelektierteAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selected[] = table.getSelectedRows();
				for (int i : selected) {
					Book book = library.findByBookTitle(table.getModel()
							.getValueAt(i, 1).toString());
					BuchDetail bookDetail = new BuchDetail(book, library);
				}
			}
		});

		chckbxNurVerfgbare = new JCheckBox("Nur Verf\u00FCgbare");
		chckbxNurVerfgbare.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				// 1 = Selected, 2 = Not Selected
				// remove all entries				
				for (int i = table.getRowCount() - 1; i >= 0; i--) {
					((DefaultTableModel) table.getModel()).removeRow(i);
				}
				if (arg0.getStateChange() == 1) { //If hook is set, add only entries with available Copies (Number of Copies - Number of Lent Copies)
					for (int x = 0; x < books.size(); x++) {
						if ((library.getCopiesOfBook(books.get(x)).size() - library
								.getLentCopiesOfBook(books.get(x)).size()) > 0) {
							String[] s = {
									""
											+ (library.getCopiesOfBook(
													books.get(x)).size() - library
													.getLentCopiesOfBook(
															books.get(x))
													.size()),
									books.get(x).getName(),
									books.get(x).getAuthor(),
									books.get(x).getPublisher() };
							((DefaultTableModel) table.getModel()).addRow(s);
						}
					}
				} else { //if hook is not set, add all Books
					for (int i = 0; i < books.size(); i++) {
						String[] s = {
								""
										+ (library
												.getCopiesOfBook(books.get(i))
												.size() - library
												.getLentCopiesOfBook(
														books.get(i)).size()),
								books.get(i).getName(),
								books.get(i).getAuthor(),
								books.get(i).getPublisher() };
						((DefaultTableModel) table.getModel()).addRow(s);
					}
				}
			}
		});

		GridBagConstraints gbc_chckbxNurVerfgbare = new GridBagConstraints();
		gbc_chckbxNurVerfgbare.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNurVerfgbare.gridx = 3;
		gbc_chckbxNurVerfgbare.gridy = 0;
		buchInventarPanel.add(chckbxNurVerfgbare, gbc_chckbxNurVerfgbare);
		GridBagConstraints gbc_btnSelektierteAnzeigen = new GridBagConstraints();
		gbc_btnSelektierteAnzeigen.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnSelektierteAnzeigen.insets = new Insets(0, 0, 5, 5);
		gbc_btnSelektierteAnzeigen.gridx = 4;
		gbc_btnSelektierteAnzeigen.gridy = 0;
		buchInventarPanel.add(btnSelektierteAnzeigen,
				gbc_btnSelektierteAnzeigen);

		btnNeuesBuch = new JButton("Neues Buch hinzuf\u00FCgen");
		GridBagConstraints gbc_btnNeuesBuch = new GridBagConstraints();
		gbc_btnNeuesBuch.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNeuesBuch.insets = new Insets(0, 0, 5, 0);
		gbc_btnNeuesBuch.gridx = 5;
		gbc_btnNeuesBuch.gridy = 0;
		buchInventarPanel.add(btnNeuesBuch, gbc_btnNeuesBuch);

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 6;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		buchInventarPanel.add(scrollPane, gbc_scrollPane);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setCellSelectionEnabled(true);

		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"Verf\u00FCgbar", "Name", "Autor", "Verlag" }));

		for (int i = 0; i < books.size(); i++) {
			String[] s = {
					""
							+ (library.getCopiesOfBook(books.get(i)).size() - library
									.getLentCopiesOfBook(books.get(i)).size()),
					books.get(i).getName(), books.get(i).getAuthor(),
					books.get(i).getPublisher() };
			((DefaultTableModel) table.getModel()).addRow(s);
		}
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setViewportView(table);

		JPanel ausleiheTab = new JPanel();
		buchMasterTabs.addTab("Ausleihe", null, ausleiheTab, null);
	}

}
