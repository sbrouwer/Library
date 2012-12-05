package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import tablemodel.TableModelBookDetail;

import domain.Book;
import domain.Copy;
import domain.Library;
import domain.Loan;
import domain.Shelf;

public class BookDetail implements Observer
{
	private JFrame frame;
	private JTextField txtTitle;
	private JTextField txtAuthor;
	private JTextField txtPublisher;
	private JLabel lblCount;
	private Book book;
	private Library library;
	private List<Copy> copies;
	JComboBox comboBoxShelf;
	private JTable table;
	JButton btnAddCopy;
	JButton btnRemoveCopy;
	private JLabel lblError;
	private Color red = new Color(255,0,0);
	private Color black = new Color(0,0,0);

	/**
	 * Create the application.
	 */
	public BookDetail(Book book, Library library)
	{
		this.book = book;
		this.library = library;
		initialize();
		updateFields();
		book.addObserver(this); //TODO: Observer wieder abmelden!
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 360);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setTitle("Buch Detail Ansicht");
		Dimension d = new Dimension(450, 360);
		frame.setMinimumSize(d);
		
		addKeyboardListeners(frame);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Buch Informationen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblTitel = new JLabel("Titel");
		GridBagConstraints gbc_lblTitel = new GridBagConstraints();
		gbc_lblTitel.gridwidth = 2;
		gbc_lblTitel.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitel.gridx = 0;
		gbc_lblTitel.gridy = 0;
		panel.add(lblTitel, gbc_lblTitel);

		txtTitle = new JTextField();
		txtTitle.setEditable(false);
		GridBagConstraints gbc_txtTitel = new GridBagConstraints();
		gbc_txtTitel.insets = new Insets(0, 0, 5, 0);
		gbc_txtTitel.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTitel.gridx = 2;
		gbc_txtTitel.gridy = 0;
		panel.add(txtTitle, gbc_txtTitel);
		txtTitle.setColumns(10);

		JLabel lblAutor = new JLabel("Autor");
		GridBagConstraints gbc_lblAutor = new GridBagConstraints();
		gbc_lblAutor.gridheight = 2;
		gbc_lblAutor.gridwidth = 2;
		gbc_lblAutor.insets = new Insets(0, 0, 5, 5);
		gbc_lblAutor.gridx = 0;
		gbc_lblAutor.gridy = 1;
		panel.add(lblAutor, gbc_lblAutor);

		txtAuthor = new JTextField();
		txtAuthor.setEditable(false);
		GridBagConstraints gbc_txtAutor = new GridBagConstraints();
		gbc_txtAutor.gridheight = 2;
		gbc_txtAutor.insets = new Insets(0, 0, 5, 0);
		gbc_txtAutor.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAutor.gridx = 2;
		gbc_txtAutor.gridy = 1;
		panel.add(txtAuthor, gbc_txtAutor);
		txtAuthor.setColumns(10);

		JLabel lblVerlag = new JLabel("Verlag");
		GridBagConstraints gbc_lblVerlag = new GridBagConstraints();
		gbc_lblVerlag.gridheight = 2;
		gbc_lblVerlag.gridwidth = 2;
		gbc_lblVerlag.insets = new Insets(0, 0, 5, 5);
		gbc_lblVerlag.gridx = 0;
		gbc_lblVerlag.gridy = 3;
		panel.add(lblVerlag, gbc_lblVerlag);

		txtPublisher = new JTextField();
		txtPublisher.setEditable(false);
		GridBagConstraints gbc_txtVerlag = new GridBagConstraints();
		gbc_txtVerlag.gridheight = 2;
		gbc_txtVerlag.insets = new Insets(0, 0, 5, 0);
		gbc_txtVerlag.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtVerlag.gridx = 2;
		gbc_txtVerlag.gridy = 3;
		panel.add(txtPublisher, gbc_txtVerlag);
		txtPublisher.setColumns(10);

		JLabel lblRegal = new JLabel("Regal");
		GridBagConstraints gbc_lblRegal = new GridBagConstraints();
		gbc_lblRegal.gridheight = 2;
		gbc_lblRegal.gridwidth = 2;
		gbc_lblRegal.insets = new Insets(0, 0, 0, 5);
		gbc_lblRegal.gridx = 0;
		gbc_lblRegal.gridy = 5;
		panel.add(lblRegal, gbc_lblRegal);

		comboBoxShelf = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 5;
		comboBoxShelf.setModel(new DefaultComboBoxModel<Shelf>(Shelf.values()));		
		panel.add(comboBoxShelf, gbc_comboBox);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Exemplare", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frame.getContentPane().add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		lblCount = new JLabel("Anzahl: 1");
		lblCount.setText("Anzahl: " + library.getCopiesOfBook(book).size());
		GridBagConstraints gbc_lblAnzahl = new GridBagConstraints();
		gbc_lblAnzahl.anchor = GridBagConstraints.WEST;
		gbc_lblAnzahl.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnzahl.gridx = 0;
		gbc_lblAnzahl.gridy = 0;
		panel_1.add(lblCount, gbc_lblAnzahl);
		
		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		table.setAutoCreateRowSorter(true);

		String[] header = {"Inventar Nummer","Verfügbarkeit","Zustand"};
		copies = library.getCopiesOfBook(book);
		final TableModelBookDetail tableModel = new TableModelBookDetail(library, book, header);
		table.setModel(tableModel);
		
		table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				if (table.getSelectedRows().length > 0)
				{
					btnRemoveCopy.setEnabled(true);
				} else
				{
					btnRemoveCopy.setEnabled(false);
				}
			}
		});

		table.getColumnModel().getColumn(0).setPreferredWidth(105);
		table.getColumnModel().getColumn(0).setMinWidth(30);
		table.getColumnModel().getColumn(0).setMaxWidth(105);
		
		ImageIcon iconRemoveCopy = new ImageIcon("icons/book_delete.png");
		btnRemoveCopy = new JButton("Ausgew\u00E4hlte entfernen",iconRemoveCopy);
		btnRemoveCopy.setToolTipText("Entfernt das in der Tabelle markierte Buch, falls es nicht ausgeliehen ist");
		btnRemoveCopy.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int selected[] = table.getSelectedRows();
				for (int i = selected.length - 1; i >= 0; i--) //Von hinten nach vorne die Elemente entfernen, ansosnten index out of bounds exeception!
				{
					Copy copyToDelet = tableModel.getCopyAtRow(table.convertRowIndexToModel(selected[i]));
					if(!library.isCopyLent(copyToDelet)){
						library.removeCopy(copyToDelet);
					}else{
						lblError.setForeground(red);
						lblError.setText("Das Exemplar ist noch ausgeliehen und kann deshalb nicht entfernt werden!");
					}
					
				}
				lblCount.setText("Anzahl: " + library.getCopiesOfBook(book).size()); //Label Anzahl Kopien updaten
			}
		});
		btnRemoveCopy.setEnabled(false);
		GridBagConstraints gbc_btnRemoveCopy = new GridBagConstraints();
		gbc_btnRemoveCopy.insets = new Insets(0, 0, 5, 5);
		gbc_btnRemoveCopy.gridx = 1;
		gbc_btnRemoveCopy.gridy = 0;
		panel_1.add(btnRemoveCopy, gbc_btnRemoveCopy);

		
		ImageIcon iconAddCopy = new ImageIcon("icons/book_add.png");
		btnAddCopy = new JButton(" Exemplar hinzuf\u00FCgen",iconAddCopy);
		btnAddCopy.setToolTipText("F\u00FCgt der Bibliothek ein neues Exemplar des angezeigten Buches hinzu");
		btnAddCopy.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				library.createAndAddCopy(book);
			}
		});
		GridBagConstraints gbc_btnAddCopy = new GridBagConstraints();
		gbc_btnAddCopy.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddCopy.gridx = 2;
		gbc_btnAddCopy.gridy = 0;
		panel_1.add(btnAddCopy, gbc_btnAddCopy);
		
		lblError = new JLabel("");
		GridBagConstraints gbc_lblError = new GridBagConstraints();
		gbc_lblError.anchor = GridBagConstraints.EAST;
		gbc_lblError.gridwidth = 3;
		gbc_lblError.insets = new Insets(0, 0, 5, 0);
		gbc_lblError.gridx = 0;
		gbc_lblError.gridy = 1;
		panel_1.add(lblError, gbc_lblError);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		panel_1.add(scrollPane, gbc_scrollPane);

		frame.setVisible(true);
	}

	/**
	 * @param
	 * @author Simon Brouwer, Adrian Rieser
	 * @return void Updates the fields that have changed in another BuchDetail
	 */
	void updateFields()
	{
		lblCount.setText("Anzahl: " + library.getCopiesOfBook(book).size()); //Label Anzahl Kopien updaten
		if (book == null)
		{
			txtTitle.setText("");
			txtAuthor.setText("");
			txtPublisher.setText("");
			comboBoxShelf.setSelectedIndex(-1);
		} else
		{
			txtTitle.setText(book.getName());
			txtAuthor.setText(book.getAuthor());
			txtPublisher.setText(book.getPublisher());
			comboBoxShelf.setSelectedItem((book.getShelf().toString()));
		}
	}
	public void addKeyboardListeners(final JFrame frame) {
	    ActionListener escListener = new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent e) {
	            frame.dispose();
	        }
	    };
	    

	    frame.getRootPane().registerKeyboardAction(escListener,
	            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
	            JComponent.WHEN_IN_FOCUSED_WINDOW);
	}

	@Override
	public void update(Observable o, Object arg)
	{
		updateFields();
	}

}
