package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import tablemodel.TableModelCustomerMaster;
import domain.Library;
import javax.swing.JLabel;

public class BookMasterCustomerTab extends JPanel
{
	private Library library;
	JButton btnChangeSelectedCustomer;
	JTable table;

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
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panel_statistics = new JPanel();
		panel_statistics.setBorder(new TitledBorder(null, "Statistiken", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_statistics = new GridBagConstraints();
		gbc_panel_statistics.insets = new Insets(0, 0, 5, 0);
		gbc_panel_statistics.fill = GridBagConstraints.BOTH;
		gbc_panel_statistics.gridx = 0;
		gbc_panel_statistics.gridy = 0;
		add(panel_statistics, gbc_panel_statistics);
		GridBagLayout gbl_panel_statistics = new GridBagLayout();
		gbl_panel_statistics.columnWidths = new int[]{0, 0, 0};
		gbl_panel_statistics.rowHeights = new int[]{0, 0};
		gbl_panel_statistics.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_statistics.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_statistics.setLayout(gbl_panel_statistics);
		
		JLabel lblCustomers = new JLabel("Anzahl Kunden:");
		GridBagConstraints gbc_lblCustomers = new GridBagConstraints();
		gbc_lblCustomers.insets = new Insets(0, 0, 0, 5);
		gbc_lblCustomers.gridx = 0;
		gbc_lblCustomers.gridy = 0;
		panel_statistics.add(lblCustomers, gbc_lblCustomers);
		
		JLabel lblAmountOfCustomers = new JLabel("42");
		GridBagConstraints gbc_lblAmountOfCustomers = new GridBagConstraints();
		gbc_lblAmountOfCustomers.anchor = GridBagConstraints.WEST;
		gbc_lblAmountOfCustomers.gridx = 1;
		gbc_lblAmountOfCustomers.gridy = 0;
		panel_statistics.add(lblAmountOfCustomers, gbc_lblAmountOfCustomers);
		
		JPanel panel_management = new JPanel();
		GridBagConstraints gbc_panel_management = new GridBagConstraints();
		gbc_panel_management.fill = GridBagConstraints.BOTH;
		gbc_panel_management.gridx = 0;
		gbc_panel_management.gridy = 1;
		add(panel_management, gbc_panel_management);
		GridBagLayout gbl_panel_management = new GridBagLayout();
		gbl_panel_management.columnWidths = new int[]{0};
		gbl_panel_management.rowHeights = new int[]{0, 0};
		gbl_panel_management.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_panel_management.rowWeights = new double[]{0.0, 1.0};
		panel_management.setLayout(gbl_panel_management);
			
		panel_management.setBorder(new TitledBorder(null, "Kundenverwaltung", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JTextField textField_search = new JTextField();
		GridBagConstraints gbc_textField_search = new GridBagConstraints();
		gbc_textField_search.insets = new Insets(0, 0, 5, 5);
		gbc_textField_search.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_search.gridx = 0;
		gbc_textField_search.gridy = 0;
		panel_management.add(textField_search, gbc_textField_search);
		textField_search.setColumns(10);
		
		btnChangeSelectedCustomer = new JButton("Selektierter Kunde anpassen");
		btnChangeSelectedCustomer.setEnabled(false);
		GridBagConstraints gbc_btnChangeSelectedCustomer = new GridBagConstraints();
		gbc_btnChangeSelectedCustomer.insets = new Insets(0, 0, 5, 5);
		gbc_btnChangeSelectedCustomer.gridx = 1;
		gbc_btnChangeSelectedCustomer.gridy = 0;
		panel_management.add(btnChangeSelectedCustomer, gbc_btnChangeSelectedCustomer);
		
		JButton btnNewCustomer = new JButton("Neuer Kunde erfassen");
		GridBagConstraints gbc_btnNewCustomer = new GridBagConstraints();
		gbc_btnNewCustomer.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewCustomer.gridx = 2;
		gbc_btnNewCustomer.gridy = 0;
		panel_management.add(btnNewCustomer, gbc_btnNewCustomer);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel_management.add(scrollPane, gbc_scrollPane);
		
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
