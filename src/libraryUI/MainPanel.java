package libraryUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import library.Library;
import library.Library.BORROWER_ACTIONS;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class MainPanel extends JFrame {

	private JPanel contentPane;
	private JTextField searchField;
	private JComboBox comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPanel frame = new MainPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public MainPanel() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 499, 355);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnClerk = new JMenu("Clerk");
		menuBar.add(mnClerk);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Add Borrower");
		mnClerk.add(mntmNewMenuItem);
		
		JMenuItem mntmCheckOutItems = new JMenuItem("Check out items");
		mnClerk.add(mntmCheckOutItems);
		
		JMenuItem mntmProcessAReturn = new JMenuItem("Process a return");
		mnClerk.add(mntmProcessAReturn);
		
		JMenuItem mntmCheckOverdueItems = new JMenuItem("Check overdue items");
		mnClerk.add(mntmCheckOverdueItems);
		
		JMenu mnBorrower = new JMenu("Borrower");
		menuBar.add(mnBorrower);
		
		JMenuItem mntmSearch = new JMenuItem("Search");
//		mntmSearch.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				Library lib = new Library();
//				lib.borrowerActions(BORROWER_ACTIONS.SEARCH_BOOKS);
//			}
//		});
		mnBorrower.add(mntmSearch);
		
		JMenuItem mntmCheckAccount = new JMenuItem("Check account");
		mnBorrower.add(mntmCheckAccount);
		
		JMenuItem mntmPlaceAHold = new JMenuItem("Place a hold");
		mnBorrower.add(mntmPlaceAHold);
		
		JMenuItem mntmPayAFine = new JMenuItem("Pay a fine");
		mnBorrower.add(mntmPayAFine);
		
		JMenu mnLibrarian = new JMenu("Librarian");
		menuBar.add(mnLibrarian);
		
		JMenuItem mntmAddABook = new JMenuItem("Add a book");
		mnLibrarian.add(mntmAddABook);
		
		JMenuItem mntmAddACopy = new JMenuItem("Add a copy");
		mnLibrarian.add(mntmAddACopy);
		
		JMenuItem mntmReportBooks = new JMenuItem("Report books");
		mnLibrarian.add(mntmReportBooks);
		
		JMenuItem mntmPopularItems = new JMenuItem("Popular items");
		mnLibrarian.add(mntmPopularItems);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 150, 150));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JLabel lblWelcome = DefaultComponentFactory.getInstance().createTitle("WELCOME");
		lblWelcome.setForeground(Color.WHITE);
		lblWelcome.setFont(new Font("Arial", Font.BOLD, 63));
		lblWelcome.setBounds(84, 86, 397, 110);
		contentPane.add(lblWelcome);
		
		searchField = new JTextField();
		searchField.setBounds(104, 20, 269, 28);
		contentPane.add(searchField);
		searchField.setColumns(10);
		searchField.setVisible(false);
		
		final JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Library lib = new Library();
				String dropDownItem = (String)comboBox.getSelectedItem();
				String searchInputText = searchField.getText();
				lib.borrowerActions(BORROWER_ACTIONS.SEARCH_BOOKS, searchInputText, dropDownItem);
				
			}
		});
		
		btnSearch.setBounds(374, 21, 117, 29);
		contentPane.add(btnSearch);
		
		comboBox = new JComboBox();
		comboBox.addItem("Title");
		comboBox.addItem("Author");
		comboBox.addItem("Subject");
		comboBox.getItemAt(0);
		comboBox.setBounds(6, 22, 96, 27);
		contentPane.add(comboBox);
		comboBox.setVisible(false);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon(ImageIO.read(new File("Images/library1.jpg"))));
		lblNewLabel.setBounds(-2, -9, 502, 350);
		contentPane.add(lblNewLabel);
		btnSearch.setVisible(false);
		
		mntmSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblWelcome.setVisible(false);
				searchField.setVisible(true);
				btnSearch.setVisible(true);
				comboBox.setVisible(true);
				
			}
		});
	}
}
