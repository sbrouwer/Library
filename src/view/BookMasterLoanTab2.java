package view;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableRowSorter;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import tablemodel.TableModelBookMaster;
import tablemodel.TableModelLoanMaster;
import domain.Book;
import domain.Library;
import domain.Loan;

public class BookMasterLoanTab2 extends JPanel
{
	private Library library;
	private JTextField textFieldSearch;
	private JTable table;
	private final String[] header = new String[] { "Status", "Exemplar-ID", "Titel", "Ausgeliehen Bis", "Ausgeliehen An" };
	private TableModelLoanMaster tableModel;
	private JCheckBox chckbxOnlyOverdues;


	public BookMasterLoanTab2(Library library)
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
		gbl_panel_statistics.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel_statistics.rowHeights = new int[]{14, 0};
		gbl_panel_statistics.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_statistics.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_statistics.setLayout(gbl_panel_statistics);
		
		JLabel lblCopies = new JLabel("Anzahl Ausleihen:");
		GridBagConstraints gbc_lblCopies = new GridBagConstraints();
		gbc_lblCopies.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblCopies.insets = new Insets(0, 0, 0, 5);
		gbc_lblCopies.gridx = 0;
		gbc_lblCopies.gridy = 0;
		panel_statistics.add(lblCopies, gbc_lblCopies);
		
		JLabel lblAmountOfCopies = new JLabel("0");
		GridBagConstraints gbc_lblAmountOfCopies = new GridBagConstraints();
		gbc_lblAmountOfCopies.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAmountOfCopies.insets = new Insets(0, 0, 0, 5);
		gbc_lblAmountOfCopies.gridx = 1;
		gbc_lblAmountOfCopies.gridy = 0;
		panel_statistics.add(lblAmountOfCopies, gbc_lblAmountOfCopies);
		
		JLabel lblActualLoans = new JLabel("Aktuelle Ausleihen");
		GridBagConstraints gbc_lblActualLoans = new GridBagConstraints();
		gbc_lblActualLoans.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblActualLoans.insets = new Insets(0, 0, 0, 5);
		gbc_lblActualLoans.gridx = 2;
		gbc_lblActualLoans.gridy = 0;
		panel_statistics.add(lblActualLoans, gbc_lblActualLoans);
		
		JLabel lblAmoutOfActualLoans = new JLabel("0");
		GridBagConstraints gbc_lblAmoutOfActualLoans = new GridBagConstraints();
		gbc_lblAmoutOfActualLoans.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAmoutOfActualLoans.insets = new Insets(0, 0, 0, 5);
		gbc_lblAmoutOfActualLoans.gridx = 3;
		gbc_lblAmoutOfActualLoans.gridy = 0;
		panel_statistics.add(lblAmoutOfActualLoans, gbc_lblAmoutOfActualLoans);
		
		JLabel lblOverdueLoans = new JLabel("Überfällige Ausleihen");
		GridBagConstraints gbc_lblOverdueLoans = new GridBagConstraints();
		gbc_lblOverdueLoans.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblOverdueLoans.insets = new Insets(0, 0, 0, 5);
		gbc_lblOverdueLoans.gridx = 4;
		gbc_lblOverdueLoans.gridy = 0;
		panel_statistics.add(lblOverdueLoans, gbc_lblOverdueLoans);
		
		JLabel lblAmountOfOverdueLoans = new JLabel("0");
		GridBagConstraints gbc_lblAmountOfOverdueLoans = new GridBagConstraints();
		gbc_lblAmountOfOverdueLoans.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAmountOfOverdueLoans.gridx = 5;
		gbc_lblAmountOfOverdueLoans.gridy = 0;
		panel_statistics.add(lblAmountOfOverdueLoans, gbc_lblAmountOfOverdueLoans);
		
		JPanel panel_management = new JPanel();
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
		
		textFieldSearch = new JTextField();
		textFieldSearch.setText("Suche");
		GridBagConstraints gbc_textFieldSearch = new GridBagConstraints();
		gbc_textFieldSearch.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSearch.gridx = 0;
		gbc_textFieldSearch.gridy = 0;
		panel_management.add(textFieldSearch, gbc_textFieldSearch);
		textFieldSearch.setColumns(10);
		
		chckbxOnlyOverdues = new JCheckBox("Nur Überfällige");
		GridBagConstraints gbc_chckbxOnlyOverdues = new GridBagConstraints();
		gbc_chckbxOnlyOverdues.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxOnlyOverdues.gridx = 1;
		gbc_chckbxOnlyOverdues.gridy = 0;
		panel_management.add(chckbxOnlyOverdues, gbc_chckbxOnlyOverdues);
		
		JButton btnLoanDetail = new JButton("Ausleihedetail anzeigen");
		GridBagConstraints gbc_btnLoanDetail = new GridBagConstraints();
		gbc_btnLoanDetail.insets = new Insets(0, 0, 5, 5);
		gbc_btnLoanDetail.gridx = 2;
		gbc_btnLoanDetail.gridy = 0;
		panel_management.add(btnLoanDetail, gbc_btnLoanDetail);
		
		JButton btnNewLoan = new JButton("Neue Ausleihe");
		GridBagConstraints gbc_btnNewLoan = new GridBagConstraints();
		gbc_btnNewLoan.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewLoan.gridx = 3;
		gbc_btnNewLoan.gridy = 0;
		panel_management.add(btnNewLoan, gbc_btnNewLoan);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel_management.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
				if (table.getSelectedRows().length > 0)
				{
					chckbxOnlyOverdues.setEnabled(true);
				} else
				{
					chckbxOnlyOverdues.setEnabled(false);
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
						new LoanDetail(library, loan);
					}
				}
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoCreateRowSorter(true);

		tableModel = new TableModelLoanMaster(library, header);
		table.setModel(tableModel);

		table.getColumnModel().getColumn(0).setMinWidth(80);
		table.getColumnModel().getColumn(0).setMaxWidth(80);

		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setViewportView(table);
	}

}
