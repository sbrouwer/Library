package view;

import javax.swing.JPanel;
import java.awt.GridBagLayout;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import tablemodel.TableModelBookMaster;
import tablemodel.TableModelCustomerMaster;

import domain.Book;
import domain.Library;

public class BookMasterCustomerTab extends JPanel
{
	private Library library;
	
	private JButton btnChangeSelectedCustomer;
	private JButton btnNewCustomer;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public BookMasterCustomerTab(Library library)
	{
		this.library = library;
		initialize();
		

	}

	private void initialize()
	{
		setBorder(new TitledBorder(null, "Kundenverwaltung", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JTextField textField_search = new JTextField();
		GridBagConstraints gbc_textField_search = new GridBagConstraints();
		gbc_textField_search.insets = new Insets(0, 0, 5, 5);
		gbc_textField_search.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_search.gridx = 0;
		gbc_textField_search.gridy = 0;
		add(textField_search, gbc_textField_search);
		textField_search.setColumns(10);
		
		btnChangeSelectedCustomer = new JButton("Selektierter Kunde anpassen");
		GridBagConstraints gbc_btnChangeSelectedCustomer = new GridBagConstraints();
		gbc_btnChangeSelectedCustomer.insets = new Insets(0, 0, 5, 5);
		gbc_btnChangeSelectedCustomer.gridx = 1;
		gbc_btnChangeSelectedCustomer.gridy = 0;
		add(btnChangeSelectedCustomer, gbc_btnChangeSelectedCustomer);
		
		btnNewCustomer = new JButton("Neuer Kunde erfassen");
		GridBagConstraints gbc_btnNewCustomer = new GridBagConstraints();
		gbc_btnNewCustomer.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewCustomer.gridx = 2;
		gbc_btnNewCustomer.gridy = 0;
		add(btnNewCustomer, gbc_btnNewCustomer);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				if (table.getSelectedRows().length > 0) {
					btnChangeSelectedCustomer.setEnabled(true);
				} else {
					btnChangeSelectedCustomer.setEnabled(false);
				}
			}
		});
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);

		TableModelCustomerMaster tableModel = new TableModelCustomerMaster(library, new String[] { "Kunden-ID", "Name", "Vorname", "Strasse", "PLZ", "Ort" });
		table.setModel(tableModel);

//		table.getColumnModel().getColumn(0).setMinWidth(80);
//		table.getColumnModel().getColumn(0).setMaxWidth(80);

		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setViewportView(table);
	}

}
