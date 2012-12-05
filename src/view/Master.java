package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import domain.Library;
import java.awt.Toolkit;

public class Master {

	private JFrame frmLibrary;
	private Library library;

	public Master(Library library) {
		this.library = library;
		initialize();
		frmLibrary.setVisible(true);
	}

	private void initialize() {

		frmLibrary = new JFrame();
		frmLibrary.setIconImage(Toolkit.getDefaultToolkit().getImage("icons/books_stack.png"));
		frmLibrary.setBounds(100, 100, 644, 516);
		frmLibrary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLibrary.setTitle("Bibliothek");
		Dimension d = new Dimension(900, 600);
		frmLibrary.setMinimumSize(d);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 545, 0 };
		gridBagLayout.rowHeights = new int[] { 237, 5, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		frmLibrary.getContentPane().setLayout(gridBagLayout);
		addKeyboardListeners(frmLibrary);

		JTabbedPane bookMasterTabs = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_bookMasterTabs = new GridBagConstraints();
		gbc_bookMasterTabs.gridheight = 2;
		gbc_bookMasterTabs.insets = new Insets(0, 5, 5, 0);
		gbc_bookMasterTabs.fill = GridBagConstraints.BOTH;
		gbc_bookMasterTabs.gridx = 0;
		gbc_bookMasterTabs.gridy = 0;
		frmLibrary.getContentPane().add(bookMasterTabs, gbc_bookMasterTabs);
			
		JPanel booksTab = new TabBook(library);
		ImageIcon iconBookTab = new ImageIcon("icons/book.png");
		bookMasterTabs.addTab("Bücher", iconBookTab, booksTab, "Übersicht der Bücher in der Bibliothek, möglichkeiten Bücher hinzuzufügen und zu entfernen");

		JPanel loanTab = new TabLoan(library);	
		ImageIcon iconLoanTab = new ImageIcon("icons/book_go.png");
		bookMasterTabs.addTab("Ausleihe", iconLoanTab, loanTab, null);
		
		JPanel customerTab = new TabCustomer(library);	
		ImageIcon iconCustomerTab = new ImageIcon("icons/customer.png");
		bookMasterTabs.addTab("Kunden", iconCustomerTab, customerTab, null);
	}
	
	private void addKeyboardListeners(final JFrame frame) {
	    ActionListener escListener = new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent e) {
	            frame.dispose();
	        }
	    };
	    
	    frame.getRootPane().registerKeyboardAction(escListener, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
	}
}
