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
import domain.Shelf;

public class BookAdd implements Observer
{

	private JFrame frmBookAdd;
	private JLabel lblTitle;
	private JTextField txtTitle;
	private JLabel lblAuthor;
	private JTextField txtAuthor;
	private JLabel lblPublisher;
	private JTextField txtPublisher;
	private JLabel lblCount;
	private Book book = null;
	private Library library;
	private JLabel lblShelf;
	private JComboBox comboBoxShelf;
	private JTable table;
	private JButton btnAddCopy;
	private JButton btnRemoveCopy;
	private JButton btnAddBook;
	private TableModelBookDetail tableModel;
	private final String[] header = new String[] { "Inventar Nummer", "Verfügbarkeit" };
	private JLabel lblStatus;

	/**
	 * Create the application.
	 */
	public BookAdd(Library library)
	{
		this.library = library;
		initialize();
		updateFields();
		frmBookAdd.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmBookAdd = new JFrame();
		frmBookAdd.setBounds(100, 100, 450, 360);
		frmBookAdd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmBookAdd.getContentPane().setLayout(new BoxLayout(frmBookAdd.getContentPane(), BoxLayout.Y_AXIS));
		frmBookAdd.setTitle("Buch erfassen");
		Dimension d = new Dimension(450, 360);
		frmBookAdd.setMinimumSize(d);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Buch Informationen", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmBookAdd.getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 47, 77, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		lblTitle = new JLabel("Titel:");
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.anchor = GridBagConstraints.WEST;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		panel.add(lblTitle, gbc_lblTitle);

		txtTitle = new JTextField();
		GridBagConstraints gbc_txtTitle = new GridBagConstraints();
		gbc_txtTitle.gridwidth = 2;
		gbc_txtTitle.insets = new Insets(0, 0, 5, 0);
		gbc_txtTitle.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTitle.gridx = 1;
		gbc_txtTitle.gridy = 0;
		panel.add(txtTitle, gbc_txtTitle);
		txtTitle.setColumns(10);

		lblAuthor = new JLabel("Autor:");
		GridBagConstraints gbc_lblAuthor = new GridBagConstraints();
		gbc_lblAuthor.anchor = GridBagConstraints.WEST;
		gbc_lblAuthor.gridheight = 2;
		gbc_lblAuthor.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuthor.gridx = 0;
		gbc_lblAuthor.gridy = 1;
		panel.add(lblAuthor, gbc_lblAuthor);

		txtAuthor = new JTextField();
		GridBagConstraints gbc_txtAuthor = new GridBagConstraints();
		gbc_txtAuthor.gridwidth = 2;
		gbc_txtAuthor.gridheight = 2;
		gbc_txtAuthor.insets = new Insets(0, 0, 5, 0);
		gbc_txtAuthor.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAuthor.gridx = 1;
		gbc_txtAuthor.gridy = 1;
		panel.add(txtAuthor, gbc_txtAuthor);
		txtAuthor.setColumns(10);

		lblPublisher = new JLabel("Verlag:");
		GridBagConstraints gbc_lblPublisher = new GridBagConstraints();
		gbc_lblPublisher.anchor = GridBagConstraints.WEST;
		gbc_lblPublisher.gridheight = 2;
		gbc_lblPublisher.insets = new Insets(0, 0, 5, 5);
		gbc_lblPublisher.gridx = 0;
		gbc_lblPublisher.gridy = 3;
		panel.add(lblPublisher, gbc_lblPublisher);

		txtPublisher = new JTextField();
		GridBagConstraints gbc_txtPublisher = new GridBagConstraints();
		gbc_txtPublisher.gridwidth = 2;
		gbc_txtPublisher.gridheight = 2;
		gbc_txtPublisher.insets = new Insets(0, 0, 5, 0);
		gbc_txtPublisher.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPublisher.gridx = 1;
		gbc_txtPublisher.gridy = 3;
		panel.add(txtPublisher, gbc_txtPublisher);
		txtPublisher.setColumns(10);

		lblShelf = new JLabel("Regal:");
		GridBagConstraints gbc_lblShelf = new GridBagConstraints();
		gbc_lblShelf.anchor = GridBagConstraints.WEST;
		gbc_lblShelf.insets = new Insets(0, 0, 5, 5);
		gbc_lblShelf.gridx = 0;
		gbc_lblShelf.gridy = 5;
		panel.add(lblShelf, gbc_lblShelf);

		comboBoxShelf = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 2;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 5;
		panel.add(comboBoxShelf, gbc_comboBox);
		comboBoxShelf.setModel(new DefaultComboBoxModel(Shelf.values()));

		ImageIcon iconAddBook = new ImageIcon("icons/book_add.png");
		btnAddBook = new JButton("Buch erfassen", iconAddBook);
		btnAddBook.setToolTipText("Fügt der Bibliothek ein neues Buch hinzu (alle Felder müssen ausgefüllt sein!)");
		btnAddBook.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				addBook();
			}
		});

		lblStatus = new JLabel(" ");
		GridBagConstraints gbc_lblError = new GridBagConstraints();
		gbc_lblError.gridwidth = 2;
		gbc_lblError.anchor = GridBagConstraints.EAST;
		gbc_lblError.insets = new Insets(0, 0, 5, 5);
		gbc_lblError.gridx = 0;
		gbc_lblError.gridy = 6;
		panel.add(lblStatus, gbc_lblError);
		GridBagConstraints gbc_btnAddBook = new GridBagConstraints();
		gbc_btnAddBook.anchor = GridBagConstraints.EAST;
		gbc_btnAddBook.gridheight = 2;
		gbc_btnAddBook.gridx = 2;
		gbc_btnAddBook.gridy = 6;
		panel.add(btnAddBook, gbc_btnAddBook);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Exemplare", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmBookAdd.getContentPane().add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		lblCount = new JLabel("Anzahl: 0");
		if (book != null)
		{
			lblCount.setText("Anzahl: " + library.getCopiesOfBook(book).size());
		}

		GridBagConstraints gbc_lblAnzahl = new GridBagConstraints();
		gbc_lblAnzahl.anchor = GridBagConstraints.WEST;
		gbc_lblAnzahl.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnzahl.gridx = 0;
		gbc_lblAnzahl.gridy = 0;
		panel_1.add(lblCount, gbc_lblAnzahl);

		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
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
		tableModel = new TableModelBookDetail(library, book, header);
		table.setModel(tableModel);
		table.getColumnModel().getColumn(0).setPreferredWidth(105);
		table.getColumnModel().getColumn(0).setMinWidth(30);
		table.getColumnModel().getColumn(0).setMaxWidth(105);

		btnRemoveCopy = new JButton("Ausgewählte entfernen");
		btnRemoveCopy.setToolTipText("Entfernt das in der Tabelle markierte Buch, falls es nicht ausgeliehen ist");

		btnRemoveCopy.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int selected[] = table.getSelectedRows();
				for (int i = selected.length - 1; i >= 0; i--)
				{
					Copy copyToDelet = tableModel.getCopyAtRow(selected[i]);
					library.removeCopy(copyToDelet);
				}
				lblCount.setText("Anzahl: " + library.getCopiesOfBook(book).size());
			}
		});
		btnRemoveCopy.setEnabled(false);
		GridBagConstraints gbc_btnRemoveCopy = new GridBagConstraints();
		gbc_btnRemoveCopy.insets = new Insets(0, 0, 5, 5);
		gbc_btnRemoveCopy.gridx = 1;
		gbc_btnRemoveCopy.gridy = 0;
		panel_1.add(btnRemoveCopy, gbc_btnRemoveCopy);

		btnAddCopy = new JButton("Exemplar hinzufügen");
		btnAddCopy.setToolTipText("Fügt ein neues Exemplar des angezeigten Buches hinzu");
		btnAddCopy.setEnabled(false);
		btnAddCopy.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (book == null)
				{
					lblStatus.setForeground(Color.RED);
					lblStatus.setText("Es muss zuerst ein Buch erfasst werden!");
				} else
				{
					library.createAndAddCopy(book);
					lblCount.setText("Anzahl: " + library.getCopiesOfBook(book).size());
				}
			}
		});
		GridBagConstraints gbc_btnAddCopy = new GridBagConstraints();
		gbc_btnAddCopy.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddCopy.gridx = 2;
		gbc_btnAddCopy.gridy = 0;
		panel_1.add(btnAddCopy, gbc_btnAddCopy);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel_1.add(scrollPane, gbc_scrollPane);
		
		addKeyboardListeners(frmBookAdd);
		
	}

	private boolean verifyFields()
	{
		boolean ok = true;
		Color red = new Color(255, 0, 0);
		Color black = new Color(0, 0, 0);
		if (bookExists())
		{
			lblStatus.setText("Das Buch existiert bereits!");
			lblStatus.setForeground(red);
			return false;
		}
		if (txtTitle.getText().equals(""))
		{
			lblStatus.setForeground(red);
			lblTitle.setText("Titel*");
			lblTitle.setForeground(red);
			ok = false;
		} else if (lblTitle.getForeground().equals(red))
		{
			lblTitle.setForeground(black);
			lblTitle.setText("Titel");
		}
		if (txtAuthor.getText().equals(""))
		{
			lblAuthor.setText("Autor*");
			lblAuthor.setForeground(red);
			ok = false;
		} else if (lblAuthor.getForeground().equals(red))
		{
			lblAuthor.setForeground(black);
			lblAuthor.setText("Autor");
		}
		if (txtPublisher.getText().equals(""))
		{
			lblPublisher.setText("Verlag*");
			lblPublisher.setForeground(red);
			ok = false;
		} else if (lblPublisher.getForeground().equals(red))
		{
			lblPublisher.setForeground(black);
			lblPublisher.setText("Verlag");
		}
		if (comboBoxShelf.getSelectedIndex() == -1)
		{
			lblShelf.setText("Regal*");
			lblShelf.setForeground(red);
			ok = false;
		} else if (lblShelf.getForeground().equals(red))
		{
			lblShelf.setForeground(black);
			lblShelf.setText("Regal");
		}
		if (ok)
		{
			lblStatus.setText(" ");
		} else
		{
			lblStatus.setText("Bitte füllen Sie die Markierten Felder aus");
			lblStatus.setForeground(red);
		}
		return ok;
	}
	
	private void addBook(){
		if (verifyFields())
		{
			book = library.createAndAddBook(txtTitle.getText());
			book.setAuthor(txtAuthor.getText());
			book.setPublisher(txtPublisher.getText());
			book.setShelf((Shelf) comboBoxShelf.getSelectedItem());
			tableModel = new TableModelBookDetail(library, book, header);
			table.setModel(tableModel);
			tableModel.fireTableDataChanged();
			lblStatus.setText("Das Buch wurde erfolgreich erfasst");
			lblStatus.setForeground(Color.BLACK);
			btnAddCopy.setEnabled(true);
		}
	}

	private boolean bookExists()
	{
		List<Book> books = library.getBooks();
		for (Book b : books)
		{
			if (b.getName().equals(txtTitle.getText()))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * @param
	 * @author Simon Brouwer, Adrian Rieser
	 * @return void Updates the fields that have changed in another BuchDetail
	 */
	void updateFields()
	{
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
			comboBoxShelf.setSelectedItem((book.getShelf()));
			System.out.println(book.getShelf().toString());
		}
	}

	@Override
	public void update(Observable o, Object arg)
	{
		updateFields();
	}
	
	public void addKeyboardListeners(final JFrame frame) {
		ActionListener escListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		};

		ActionListener enterListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addBook();
			}
		};
		frame.getRootPane().registerKeyboardAction(escListener,
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		frame.getRootPane().registerKeyboardAction(enterListener,
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

	}

}
