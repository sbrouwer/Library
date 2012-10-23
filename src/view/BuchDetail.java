package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import domain.Book;
import domain.Copy;
import domain.Library;
import domain.Loan;
import domain.Shelf;

public class BuchDetail implements Observer
{

	private JFrame frame;
	private JTextField txtTitel;
	private JTextField txtAutor;
	private JTextField txtVerlag;
	private JLabel lblAnzahl;
	private Book book;
	private Library library;
	private List<Copy> copies;
	private List<Loan> lent;
	private Shelf shelf;
	JComboBox regalComboBox;
	private JTable table;
	JButton btnExemplarHinzufuegen;
	JButton btnAusgewaehlteEntfernen;

	/**
	 * Create the application.
	 */
	public BuchDetail(Book book, Library library)
	{
		this.book = book;
		this.library = library;
		initialize();
		updateFields();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 360);
		// frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setTitle("Buch Detail Ansicht");

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Buch Informationen", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		frame.getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, 1.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblTitel = new JLabel("Titel");
		GridBagConstraints gbc_lblTitel = new GridBagConstraints();
		gbc_lblTitel.gridwidth = 2;
		gbc_lblTitel.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitel.gridx = 0;
		gbc_lblTitel.gridy = 0;
		panel.add(lblTitel, gbc_lblTitel);

		txtTitel = new JTextField();
		txtTitel.setEditable(false);
		GridBagConstraints gbc_txtTitel = new GridBagConstraints();
		gbc_txtTitel.insets = new Insets(0, 0, 5, 0);
		gbc_txtTitel.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTitel.gridx = 3;
		gbc_txtTitel.gridy = 0;
		panel.add(txtTitel, gbc_txtTitel);
		txtTitel.setColumns(10);

		JLabel lblAutor = new JLabel("Autor");
		GridBagConstraints gbc_lblAutor = new GridBagConstraints();
		gbc_lblAutor.gridwidth = 2;
		gbc_lblAutor.insets = new Insets(0, 0, 5, 5);
		gbc_lblAutor.gridx = 0;
		gbc_lblAutor.gridy = 2;
		panel.add(lblAutor, gbc_lblAutor);

		txtAutor = new JTextField();
		txtAutor.setEditable(false);
		GridBagConstraints gbc_txtAutor = new GridBagConstraints();
		gbc_txtAutor.gridheight = 2;
		gbc_txtAutor.insets = new Insets(0, 0, 5, 0);
		gbc_txtAutor.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAutor.gridx = 3;
		gbc_txtAutor.gridy = 1;
		panel.add(txtAutor, gbc_txtAutor);
		txtAutor.setColumns(10);

		JLabel lblVerlag = new JLabel("Verlag");
		GridBagConstraints gbc_lblVerlag = new GridBagConstraints();
		gbc_lblVerlag.gridheight = 2;
		gbc_lblVerlag.gridwidth = 2;
		gbc_lblVerlag.insets = new Insets(0, 0, 5, 5);
		gbc_lblVerlag.gridx = 0;
		gbc_lblVerlag.gridy = 3;
		panel.add(lblVerlag, gbc_lblVerlag);

		txtVerlag = new JTextField();
		txtVerlag.setEditable(false);
		GridBagConstraints gbc_txtVerlag = new GridBagConstraints();
		gbc_txtVerlag.gridheight = 2;
		gbc_txtVerlag.insets = new Insets(0, 0, 5, 0);
		gbc_txtVerlag.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtVerlag.gridx = 3;
		gbc_txtVerlag.gridy = 3;
		panel.add(txtVerlag, gbc_txtVerlag);
		txtVerlag.setColumns(10);

		JLabel lblRegal = new JLabel("Regal");
		GridBagConstraints gbc_lblRegal = new GridBagConstraints();
		gbc_lblRegal.gridheight = 2;
		gbc_lblRegal.gridwidth = 2;
		gbc_lblRegal.insets = new Insets(0, 0, 0, 5);
		gbc_lblRegal.gridx = 0;
		gbc_lblRegal.gridy = 5;
		panel.add(lblRegal, gbc_lblRegal);

		regalComboBox = new JComboBox();
		regalComboBox.setEnabled(false);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 5;
		panel.add(regalComboBox, gbc_comboBox);
		Shelf[] s = shelf.values();
		regalComboBox.setModel(new DefaultComboBoxModel(shelf.values()));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Exemplare", TitledBorder.LEADING, TitledBorder.TOP, null,
				null));
		frame.getContentPane().add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		lblAnzahl = new JLabel("Anzahl: 1");
		lblAnzahl.setText("Anzahl: " + library.getCopiesOfBook(book).size());
		GridBagConstraints gbc_lblAnzahl = new GridBagConstraints();
		gbc_lblAnzahl.gridwidth = 4;
		gbc_lblAnzahl.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnzahl.gridx = 0;
		gbc_lblAnzahl.gridy = 0;
		panel_1.add(lblAnzahl, gbc_lblAnzahl);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Inventar Nummer",
				"Verf\u00FCgbarkeit" })
		{
			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column)
			{
				return columnEditables[column];
			}
		});

		table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				if (table.getSelectedRows().length > 0)
				{
					btnAusgewaehlteEntfernen.setEnabled(true);
				} else
				{
					btnAusgewaehlteEntfernen.setEnabled(false);
				}
			}
		});

		table.getColumnModel().getColumn(0).setPreferredWidth(105);
		table.getColumnModel().getColumn(0).setMinWidth(30);
		table.getColumnModel().getColumn(0).setMaxWidth(105);

		btnAusgewaehlteEntfernen = new JButton("Ausgew\u00E4hlte Entfernen");

		btnAusgewaehlteEntfernen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int selected[] = table.getSelectedRows();
				for (int i : selected)
				{
					long inventoryNumberToDelete = Long.valueOf((String) ((DefaultTableModel) table.getModel()).getValueAt(i, 0));
					Copy copyToDelete = null;
					List<Copy> copies = library.getCopies();
					for (Copy copy : copies)
					{
						if (copy.getInventoryNumber() == inventoryNumberToDelete)
						{
							copyToDelete = copy;
						}
					}
					library.removeCopy(copyToDelete);
					((DefaultTableModel) table.getModel()).removeRow(i);
				}
			}
		});
		btnAusgewaehlteEntfernen.setEnabled(false);
		GridBagConstraints gbc_btnAusgewaehlteEntfernen = new GridBagConstraints();
		gbc_btnAusgewaehlteEntfernen.insets = new Insets(0, 0, 5, 5);
		gbc_btnAusgewaehlteEntfernen.gridx = 4;
		gbc_btnAusgewaehlteEntfernen.gridy = 0;
		panel_1.add(btnAusgewaehlteEntfernen, gbc_btnAusgewaehlteEntfernen);

		btnExemplarHinzufuegen = new JButton(" Exemplar hinzuf\u00FCgen");
		btnExemplarHinzufuegen.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Copy c = library.createAndAddCopy(book);
				String[] stringTableModel = { "" + c.getInventoryNumber(), "Verfügbar" };
				((DefaultTableModel) table.getModel()).addRow(stringTableModel);
			}
		});
		GridBagConstraints gbc_btnExemplarHinzufuegen = new GridBagConstraints();
		gbc_btnExemplarHinzufuegen.insets = new Insets(0, 0, 5, 0);
		gbc_btnExemplarHinzufuegen.gridx = 7;
		gbc_btnExemplarHinzufuegen.gridy = 0;
		panel_1.add(btnExemplarHinzufuegen, gbc_btnExemplarHinzufuegen);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.gridwidth = 8;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel_1.add(scrollPane, gbc_scrollPane);
		copies = library.getCopiesOfBook(book); // Bücherliste holen
		lent = library.getLentCopiesOfBook(book);
		DefaultListModel<String> listBuchDetailModel = new DefaultListModel<String>();
		// Liste füllen

		for (Copy c : copies)
		{
			if (library.isCopyLent(c))
			{
				for (Loan l : lent)
				{
					if (l.getCopy().equals(c))
					{ // TODO Datum muss noch formatiert werden!!!
						String[] stringTableModel = {
								"" + l.getCopy().getInventoryNumber(),
								l.getPickupDate().DAY_OF_MONTH + "." + l.getPickupDate().MONTH + "."
										+ l.getPickupDate().YEAR + " - " + l.getReturnDate().DAY_OF_MONTH
										+ "." + l.getReturnDate().MONTH + "." + l.getReturnDate().YEAR };
						((DefaultTableModel) table.getModel()).addRow(stringTableModel);

					}
				}
			} else
			{
				String[] stringTableModel = { "" + c.getInventoryNumber(), "Verfügbar" };
				((DefaultTableModel) table.getModel()).addRow(stringTableModel);
			}
		}
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
			txtTitel.setText("");
			txtAutor.setText("");
			txtVerlag.setText("");
			regalComboBox.setSelectedIndex(-1);
		} else
		{
			txtTitel.setText(book.getName());
			txtAutor.setText(book.getAuthor());
			txtVerlag.setText(book.getPublisher());
			regalComboBox.setSelectedItem((book.getShelf().toString()));
			System.out.println(book.getShelf().toString());
		}
	}

	@Override
	public void update(Observable o, Object arg)
	{
		updateFields();
	}

}
