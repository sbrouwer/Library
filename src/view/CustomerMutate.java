package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;

public class CustomerMutate extends JFrame
{

	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtSurename;
	private JTextField txtStreet;
	private JTextField txtZip;
	private JLabel lblCity;
	private JTextField txtCity;
	private JButton btnMutateCustomer;

	/**
	 * Create the frame.
	 */
	public CustomerMutate()
	{
		setTitle("Kundenangaben \u00E4ndern");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "Kundendaten", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblName = new JLabel("Name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		contentPane.add(lblName, gbc_lblName);
		
		txtName = new JTextField();
		GridBagConstraints gbc_txtName = new GridBagConstraints();
		gbc_txtName.insets = new Insets(0, 0, 5, 0);
		gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtName.gridx = 1;
		gbc_txtName.gridy = 0;
		contentPane.add(txtName, gbc_txtName);
		txtName.setColumns(10);
		
		JLabel lblSurename = new JLabel("Vorname");
		GridBagConstraints gbc_lblSurename = new GridBagConstraints();
		gbc_lblSurename.insets = new Insets(0, 0, 5, 5);
		gbc_lblSurename.gridx = 0;
		gbc_lblSurename.gridy = 1;
		contentPane.add(lblSurename, gbc_lblSurename);
		
		txtSurename = new JTextField();
		GridBagConstraints gbc_txtSurename = new GridBagConstraints();
		gbc_txtSurename.insets = new Insets(0, 0, 5, 0);
		gbc_txtSurename.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSurename.gridx = 1;
		gbc_txtSurename.gridy = 1;
		contentPane.add(txtSurename, gbc_txtSurename);
		txtSurename.setColumns(10);
		
		JLabel lblStreet = new JLabel("Strasse");
		GridBagConstraints gbc_lblStreet = new GridBagConstraints();
		gbc_lblStreet.insets = new Insets(0, 0, 5, 5);
		gbc_lblStreet.gridx = 0;
		gbc_lblStreet.gridy = 2;
		contentPane.add(lblStreet, gbc_lblStreet);
		
		txtStreet = new JTextField();
		GridBagConstraints gbc_txtStreet = new GridBagConstraints();
		gbc_txtStreet.insets = new Insets(0, 0, 5, 0);
		gbc_txtStreet.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStreet.gridx = 1;
		gbc_txtStreet.gridy = 2;
		contentPane.add(txtStreet, gbc_txtStreet);
		txtStreet.setColumns(10);
		
		JLabel lblZip = new JLabel("PLZ");
		GridBagConstraints gbc_lblZip = new GridBagConstraints();
		gbc_lblZip.insets = new Insets(0, 0, 5, 5);
		gbc_lblZip.gridx = 0;
		gbc_lblZip.gridy = 3;
		contentPane.add(lblZip, gbc_lblZip);
		
		txtZip = new JTextField();
		GridBagConstraints gbc_txtZip = new GridBagConstraints();
		gbc_txtZip.insets = new Insets(0, 0, 5, 0);
		gbc_txtZip.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtZip.gridx = 1;
		gbc_txtZip.gridy = 3;
		contentPane.add(txtZip, gbc_txtZip);
		txtZip.setColumns(10);
		
		lblCity = new JLabel("Ort");
		GridBagConstraints gbc_lblCity = new GridBagConstraints();
		gbc_lblCity.insets = new Insets(0, 0, 5, 5);
		gbc_lblCity.gridx = 0;
		gbc_lblCity.gridy = 4;
		contentPane.add(lblCity, gbc_lblCity);
		
		txtCity = new JTextField();
		GridBagConstraints gbc_txtCity = new GridBagConstraints();
		gbc_txtCity.insets = new Insets(0, 0, 5, 0);
		gbc_txtCity.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCity.gridx = 1;
		gbc_txtCity.gridy = 4;
		contentPane.add(txtCity, gbc_txtCity);
		txtCity.setColumns(10);
		
		btnMutateCustomer = new JButton("Kundenangaben \u00FCbernehmen");
		GridBagConstraints gbc_btnMutateCustomer = new GridBagConstraints();
		gbc_btnMutateCustomer.anchor = GridBagConstraints.EAST;
		gbc_btnMutateCustomer.gridwidth = 2;
		gbc_btnMutateCustomer.insets = new Insets(0, 0, 0, 5);
		gbc_btnMutateCustomer.gridx = 0;
		gbc_btnMutateCustomer.gridy = 5;
		contentPane.add(btnMutateCustomer, gbc_btnMutateCustomer);
	}

}
