package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableRowSorter;

import tablemodel.TableModelTabCustomer;
import domain.Customer;
import domain.Library;

public class TabCustomer extends JPanel
{
	private Library library;
	private JTextField txtSearch;
	TableModelTabCustomer tableModel;
	private TableRowSorter<TableModelTabCustomer> sorter;
	JButton btnEditCustomer;
	JTable table;

	public TabCustomer(Library library)
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
		
		JLabel lblAmountOfCustomers = new JLabel(String.valueOf(library.getCustomers().size()));
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
		
		txtSearch = new JTextField();
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
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				search();
			}
		});
		txtSearch.setColumns(10);
		GridBagConstraints gbc_textField_search = new GridBagConstraints();
		gbc_textField_search.insets = new Insets(0, 0, 5, 5);
		gbc_textField_search.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_search.gridx = 0;
		gbc_textField_search.gridy = 0;
		panel_management.add(txtSearch, gbc_textField_search);
		
		ImageIcon iconCustomerEdit = new ImageIcon("icons/customer_edit.png");		
		btnEditCustomer = new JButton("Kunde editieren", iconCustomerEdit);
		btnEditCustomer.setToolTipText("Zeigt der in der untenstehende Tabelle ausgewählten Kunde zum editieren an");
		btnEditCustomer.setEnabled(false);
		btnEditCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selected[] = table.getSelectedRows();
				for (int i : selected) {
					Customer customer = tableModel.getCustomerAtRow(table.convertRowIndexToModel(i));
					CustomerEdit customerEdit = new CustomerEdit(customer, library);
				}
			}
		});
		
		GridBagConstraints gbc_btnChangeSelectedCustomer = new GridBagConstraints();
		gbc_btnChangeSelectedCustomer.insets = new Insets(0, 0, 5, 5);
		gbc_btnChangeSelectedCustomer.gridx = 1;
		gbc_btnChangeSelectedCustomer.gridy = 0;
		panel_management.add(btnEditCustomer, gbc_btnChangeSelectedCustomer);
		
		ImageIcon iconCustomerAdd = new ImageIcon("icons/customer_add.png");		
		JButton btnNewCustomer = new JButton("Neuer Kunde", iconCustomerAdd);
		btnNewCustomer.setToolTipText("Öffnet ein Fenster um einen neuen Kunden zu erfassen");
		btnNewCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CustomerAdd customerAdd = new CustomerAdd(library);
			}
		});

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
					btnEditCustomer.setEnabled(true);
				} else {
					btnEditCustomer.setEnabled(false);
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2){
					int selected[] = table.getSelectedRows();
					for (int i : selected) {
						Customer customer = tableModel.getCustomerAtRow(table.convertRowIndexToModel(i));
						CustomerEdit customerEdit = new CustomerEdit(customer,library);
					}
				}
			}
		});
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);

		tableModel = new TableModelTabCustomer(library, new String[] { "Kunden-ID", "Name", "Vorname", "Strasse", "PLZ", "Ort" });
		table.setModel(tableModel);

		table.getColumnModel().getColumn(0).setMinWidth(80);
		table.getColumnModel().getColumn(0).setMaxWidth(80);
		table.getColumnModel().getColumn(4).setMinWidth(50);
		table.getColumnModel().getColumn(4).setMaxWidth(50);
		//TODO UPDATE?

		scrollPane.setViewportView(table);
		
		sorter = new TableRowSorter<TableModelTabCustomer>(tableModel);
		table.setRowSorter(sorter);
		sorter.setSortsOnUpdates(true);
		sorter.toggleSortOrder(1);
	
		}
	
	private void search() {
		RowFilter<TableModelTabCustomer, Object> rf = null;
		List<RowFilter<TableModelTabCustomer, Object>> filters = new ArrayList<RowFilter<TableModelTabCustomer, Object>>();
		// If current expression doesn't parse, don't update.
		try {
			RowFilter<TableModelTabCustomer, Object> rfID = RowFilter.regexFilter(
					"(?i)^.*" + txtSearch.getText() + ".*", 0);
			RowFilter<TableModelTabCustomer, Object> rfName = RowFilter.regexFilter(
					"(?i)^.*" + txtSearch.getText() + ".*", 1);
			RowFilter<TableModelTabCustomer, Object> rfSurname = RowFilter.regexFilter(
					"(?i)^.*" + txtSearch.getText() + ".*", 2);
			RowFilter<TableModelTabCustomer, Object> rfStreet = RowFilter.regexFilter(
					"(?i)^.*" + txtSearch.getText() + ".*", 3);
			RowFilter<TableModelTabCustomer, Object> rfPLZ = RowFilter.regexFilter(
					"(?i)^.*" + txtSearch.getText() + ".*", 4);
			RowFilter<TableModelTabCustomer, Object> rfOrt = RowFilter.regexFilter(
					"(?i)^.*" + txtSearch.getText() + ".*", 5);
			
			filters.add(rfID);
			filters.add(rfSurname);
			filters.add(rfName);
			filters.add(rfStreet);
			filters.add(rfPLZ);
			filters.add(rfOrt);
			
			rf = RowFilter.orFilter(filters);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(rf);
	}

}
