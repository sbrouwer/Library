package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JToolBar;
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
import java.awt.Font;

public class BuchMaster {

	private JFrame frame;

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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 561, 518);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{545, 0};
		gridBagLayout.rowHeights = new int[]{237, 5, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JTabbedPane buchMasterTabs = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_buchMasterTabs = new GridBagConstraints();
		gbc_buchMasterTabs.gridheight = 2;
		gbc_buchMasterTabs.insets = new Insets(0, 0, 5, 0);
		gbc_buchMasterTabs.fill = GridBagConstraints.BOTH;
		gbc_buchMasterTabs.gridx = 0;
		gbc_buchMasterTabs.gridy = 0;
		frame.getContentPane().add(buchMasterTabs, gbc_buchMasterTabs);
		
		JPanel buecherTab = new JPanel();
		buchMasterTabs.addTab("B\u00FCcher", null, buecherTab, null);
		GridBagLayout gbl_buecherTab = new GridBagLayout();
		gbl_buecherTab.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_buecherTab.rowHeights = new int[]{0, 0, 0, 0};
		gbl_buecherTab.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_buecherTab.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		buecherTab.setLayout(gbl_buecherTab);
		
		JPanel inventarStatistikenPanel = new JPanel();
		inventarStatistikenPanel.setBorder(new TitledBorder(null, "Inventar Statistiken", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_inventarStatistikenPanel = new GridBagConstraints();
		gbc_inventarStatistikenPanel.gridwidth = 5;
		gbc_inventarStatistikenPanel.anchor = GridBagConstraints.NORTH;
		gbc_inventarStatistikenPanel.insets = new Insets(0, 0, 5, 0);
		gbc_inventarStatistikenPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_inventarStatistikenPanel.gridx = 0;
		gbc_inventarStatistikenPanel.gridy = 0;
		buecherTab.add(inventarStatistikenPanel, gbc_inventarStatistikenPanel);
		inventarStatistikenPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 5));
		
		JLabel lblAnzahlBcher = new JLabel("Anzahl B\u00FCcher: 865");
		inventarStatistikenPanel.add(lblAnzahlBcher);
		
		JLabel lblAnzahlExemplare = new JLabel("Anzahl Exemplare: 2200");
		inventarStatistikenPanel.add(lblAnzahlExemplare);
		
		JPanel buchInventarPanel = new JPanel();
		buchInventarPanel.setBorder(new TitledBorder(null, "Buch Inventar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_buchInventarPanel = new GridBagConstraints();
		gbc_buchInventarPanel.gridheight = 2;
		gbc_buchInventarPanel.insets = new Insets(0, 0, 5, 0);
		gbc_buchInventarPanel.gridwidth = 5;
		gbc_buchInventarPanel.fill = GridBagConstraints.BOTH;
		gbc_buchInventarPanel.gridx = 0;
		gbc_buchInventarPanel.gridy = 1;
		buecherTab.add(buchInventarPanel, gbc_buchInventarPanel);
		GridBagLayout gbl_buchInventarPanel = new GridBagLayout();
		gbl_buchInventarPanel.columnWidths = new int[]{69, 0, 131, 145, 0, 0, 0};
		gbl_buchInventarPanel.rowHeights = new int[]{23, 50, 0};
		gbl_buchInventarPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_buchInventarPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		buchInventarPanel.setLayout(gbl_buchInventarPanel);
		
		JButton btn_selektierteAnzeigen = new JButton("Selektierte Anzeigen");
		btn_selektierteAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JLabel lblAusgewhlt = new JLabel("Ausgew\u00E4hlt: 1");
		GridBagConstraints gbc_lblAusgewhlt = new GridBagConstraints();
		gbc_lblAusgewhlt.gridwidth = 3;
		gbc_lblAusgewhlt.anchor = GridBagConstraints.WEST;
		gbc_lblAusgewhlt.insets = new Insets(0, 0, 5, 5);
		gbc_lblAusgewhlt.gridx = 0;
		gbc_lblAusgewhlt.gridy = 0;
		buchInventarPanel.add(lblAusgewhlt, gbc_lblAusgewhlt);
		GridBagConstraints gbc_btn_selektierteAnzeigen = new GridBagConstraints();
		gbc_btn_selektierteAnzeigen.anchor = GridBagConstraints.NORTHWEST;
		gbc_btn_selektierteAnzeigen.insets = new Insets(0, 0, 5, 5);
		gbc_btn_selektierteAnzeigen.gridx = 3;
		gbc_btn_selektierteAnzeigen.gridy = 0;
		buchInventarPanel.add(btn_selektierteAnzeigen, gbc_btn_selektierteAnzeigen);
		
		JButton btnNewButton = new JButton("Neues Buch hinzuf\u00FCgen");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 5;
		gbc_btnNewButton.gridy = 0;
		buchInventarPanel.add(btnNewButton, gbc_btnNewButton);
		
		JList list = new JList();
		list.setFont(new Font("Tahoma", Font.PLAIN, 14));
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Lord of the rings: The fellowship of the ring", "Lord of the rings: The tow towers", "Lord of the rings: The return of the King"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 6;
		gbc_list.insets = new Insets(0, 0, 0, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 1;
		buchInventarPanel.add(list, gbc_list);
		
		JPanel ausleiheTab = new JPanel();
		buchMasterTabs.addTab("Ausleihe", null, ausleiheTab, null);
	}

}
