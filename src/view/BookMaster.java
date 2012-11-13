package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import domain.Library;

public class BookMaster {

	private JFrame frmBibliothek;
	private JPanel buecherTab;

	private Library library;

	/**
	 * Create the application.
	 */
	public BookMaster(Library library) {
		this.library = library;
		initialize();
		frmBibliothek.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmBibliothek = new JFrame();
		frmBibliothek.setBounds(100, 100, 644, 516);
		frmBibliothek.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBibliothek.setTitle("Bibliothek");
		Dimension d = new Dimension(900, 600);
		frmBibliothek.setMinimumSize(d);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 545, 0 };
		gridBagLayout.rowHeights = new int[] { 237, 5, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		frmBibliothek.getContentPane().setLayout(gridBagLayout);

		JTabbedPane buchMasterTabs = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_buchMasterTabs = new GridBagConstraints();
		gbc_buchMasterTabs.gridheight = 2;
		gbc_buchMasterTabs.insets = new Insets(0, 5, 5, 0);
		gbc_buchMasterTabs.fill = GridBagConstraints.BOTH;
		gbc_buchMasterTabs.gridx = 0;
		gbc_buchMasterTabs.gridy = 0;
		frmBibliothek.getContentPane().add(buchMasterTabs, gbc_buchMasterTabs);
		
		
		JPanel buecherTab = new BookMasterBooksTab(library);
		buchMasterTabs.addTab("B\u00FCcher", null, buecherTab, null);
		
		JPanel ausleiheTab = new JPanel();
		buchMasterTabs.addTab("Ausleihe", null, ausleiheTab, null);
	}

}
