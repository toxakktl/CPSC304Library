package libraryUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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

import library.BorrowerActions;
import library.Library;
import library.Library.BORROWER_ACTIONS;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class MainPanel extends JFrame {

	private JPanel contentPane;
	private JTextField searchField;
	private JComboBox comboBox;
	Connection con;
	private JTextField borrowerIDField;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField payFine;
	


	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MainPanel frame = new MainPanel();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public MainPanel(final Connection con) throws IOException {
		this.con = con;
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
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmInitialize = new JMenuItem("Initiliaze tables");
		mntmInitialize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ScriptRunner runner = new ScriptRunner(con, false, false);
				try {
				runner.runScript(new BufferedReader(new FileReader("SQL/something.sql")));
				} catch (FileNotFoundException e) {
					System.out.println("Message: " + e.getMessage());
				} catch (IOException e) {
					System.out.println("Message: " + e.getMessage());
				} catch (SQLException e) {
					System.out.println("Message: " + e.getMessage());
				}
			}
		});
		mnHelp.add(mntmInitialize);
		
		JMenuItem mntmInsert = new JMenuItem("Insert default values");
		mntmInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ScriptRunner runner = new ScriptRunner(con, false, false);
				try {
				runner.runScript(new BufferedReader(new FileReader("SQL/Insert.sql")));
				} catch (FileNotFoundException e) {
					System.out.println("Message: " + e.getMessage());
				} catch (IOException e) {
					System.out.println("Message: " + e.getMessage());
				} catch (SQLException e) {
					System.out.println("Message: " + e.getMessage());
				}
			}
		});
		mnHelp.add(mntmInsert);
		
		JMenuItem mntmDelete = new JMenuItem("Delete all values");
		mntmDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ScriptRunner runner = new ScriptRunner(con, false, false);
				try {
				runner.runScript(new BufferedReader(new FileReader("SQL/Delete.sql")));
				} catch (FileNotFoundException e) {
					System.out.println("Message: " + e.getMessage());
				} catch (IOException e) {
					System.out.println("Message: " + e.getMessage());
				} catch (SQLException e) {
					System.out.println("Message: " + e.getMessage());
				}
			}
		});
		mnHelp.add(mntmDelete);
		
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
				//Library lib = new Library(con);
				BorrowerActions bActions = new BorrowerActions(con);
				String dropDownItem = (String)comboBox.getSelectedItem();
				String searchInputText = searchField.getText();
				bActions.searchBooks(searchInputText, dropDownItem);	
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
		
		final JLabel lblEnterTheBorrower = DefaultComponentFactory.getInstance().createLabel("Enter the Borrower ID");
		lblEnterTheBorrower.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblEnterTheBorrower.setForeground(Color.WHITE);
		lblEnterTheBorrower.setBounds(17, 26, 205, 16);
		contentPane.add(lblEnterTheBorrower);
		lblEnterTheBorrower.setVisible(false);
		
		borrowerIDField = new JTextField();
		borrowerIDField.setBounds(17, 60, 134, 28);
		contentPane.add(borrowerIDField);
		borrowerIDField.setColumns(10);
		borrowerIDField.setVisible(false);
		
		final JButton btnSubmit = new JButton("Submit");

		btnSubmit.setBounds(151, 60, 117, 29);
		contentPane.add(btnSubmit);
		
		
		
		final JLabel lblBorrowerId = DefaultComponentFactory.getInstance().createLabel("Borrower ID");
		lblBorrowerId.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblBorrowerId.setForeground(Color.WHITE);
		lblBorrowerId.setBounds(19, 25, 120, 16);
		contentPane.add(lblBorrowerId);
		lblBorrowerId.setVisible(false);
		
		final JLabel lblCallNumber = DefaultComponentFactory.getInstance().createLabel("Call Number");
		lblCallNumber.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblCallNumber.setForeground(Color.WHITE);
		lblCallNumber.setBounds(152, 25, 120, 16);
		contentPane.add(lblCallNumber);
		lblCallNumber.setVisible(false);
		
		textField = new JTextField();
		textField.setBounds(151, 54, 134, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setVisible(false);
		
		textField_1 = new JTextField();
		textField_1.setBounds(16, 54, 134, 28);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setVisible(false);
		
		final JButton hold = new JButton("Submit");
	
		hold.setBounds(291, 55, 117, 29);
		contentPane.add(hold);
		
		final JLabel lblEnterTheFid = DefaultComponentFactory.getInstance().createLabel("Enter the FID");
		lblEnterTheFid.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblEnterTheFid.setForeground(Color.WHITE);
		lblEnterTheFid.setBounds(29, 26, 120, 16);
		contentPane.add(lblEnterTheFid);
		lblEnterTheFid.setVisible(false);
		
		payFine = new JTextField();
		payFine.setBounds(27, 60, 134, 28);
		contentPane.add(payFine);
		payFine.setColumns(10);
		payFine.setVisible(false);
		
		final JButton pay = new JButton("Pay");
	
		pay.setBounds(173, 60, 117, 29);
		contentPane.add(pay);
		pay.setVisible(false);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon(ImageIO.read(new File("Images/library1.jpg"))));
		lblNewLabel.setBounds(-2, -9, 502, 350);
		contentPane.add(lblNewLabel);
		hold.setVisible(false);
	
		
		btnSubmit.setVisible(false);
		
		btnSearch.setVisible(false);
		
		//Search
		mntmSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (borrowerIDField.isVisible() && btnSubmit.isVisible() && lblEnterTheBorrower.isVisible())
				{
					borrowerIDField.setVisible(false);
					btnSubmit.setVisible(false);
					lblEnterTheBorrower.setVisible(false);
				}
				if (lblBorrowerId.isVisible() && lblCallNumber.isVisible() && textField.isVisible()&&textField_1.isVisible()&hold.isVisible())
				{
					lblBorrowerId.setVisible(false);
					lblCallNumber.setVisible(false);
					textField.setVisible(false);
					textField_1.setVisible(false);
					hold.setVisible(false);
				}
				if (lblEnterTheFid.isVisible()&&pay.isVisible()&&payFine.isVisible()){
					lblEnterTheFid.setVisible(false);
					pay.setVisible(false);
					payFine.setVisible(false);
				}
				lblWelcome.setVisible(false);
				searchField.setVisible(true);
				btnSearch.setVisible(true);
				comboBox.setVisible(true);		
			}
		});
		
		//Check account
		mntmCheckAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblWelcome.setVisible(false);
				if (searchField.isVisible())
					searchField.setVisible(false);
				if (btnSearch.isVisible())
					btnSearch.setVisible(false);
				if (comboBox.isVisible())
					comboBox.setVisible(false);
				if (lblBorrowerId.isVisible() && lblCallNumber.isVisible() && textField.isVisible()&&textField_1.isVisible()&hold.isVisible())
				{
					lblBorrowerId.setVisible(false);
					lblCallNumber.setVisible(false);
					textField.setVisible(false);
					textField_1.setVisible(false);
					hold.setVisible(false);
				}
				if (lblEnterTheFid.isVisible()&&pay.isVisible()&&payFine.isVisible()){
					lblEnterTheFid.setVisible(false);
					pay.setVisible(false);
					payFine.setVisible(false);
				}
				lblEnterTheBorrower.setVisible(true);
				btnSubmit.setVisible(true);
				borrowerIDField.setVisible(true);
			}
		});
		
		//on click Submit for checking account
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BorrowerActions bact = new BorrowerActions(con);
				bact.checkAccount(borrowerIDField.getText());
			}
		});
		
		//Place a hold
		mntmPlaceAHold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Add shit
				lblWelcome.setVisible(false);
				
				if (searchField.isVisible())
					searchField.setVisible(false);
				if (btnSearch.isVisible())
					btnSearch.setVisible(false);
				if (comboBox.isVisible())
					comboBox.setVisible(false);
				if (borrowerIDField.isVisible() && btnSubmit.isVisible() && lblEnterTheBorrower.isVisible())
				{
					borrowerIDField.setVisible(false);
					btnSubmit.setVisible(false);
					lblEnterTheBorrower.setVisible(false);
				}
				if (lblEnterTheFid.isVisible()&&pay.isVisible()&&payFine.isVisible()){
					lblEnterTheFid.setVisible(false);
					pay.setVisible(false);
					payFine.setVisible(false);
				}
				lblBorrowerId.setVisible(true);
				lblCallNumber.setVisible(true);
				textField.setVisible(true);
				textField_1.setVisible(true);
				hold.setVisible(true);
			
				
				
			}
		});
		
		//on click place a hold request
		hold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					BorrowerActions baH = new BorrowerActions(con);
					baH.placeHold(Integer.parseInt(textField_1.getText()), textField.getText());
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		//On click menu item pay a Fine
		mntmPayAFine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(lblWelcome.isVisible())
					lblWelcome.setVisible(false);
				
				if (borrowerIDField.isVisible() && btnSubmit.isVisible() && lblEnterTheBorrower.isVisible())
				{
					borrowerIDField.setVisible(false);
					btnSubmit.setVisible(false);
					lblEnterTheBorrower.setVisible(false);
				}
				if (lblBorrowerId.isVisible() && lblCallNumber.isVisible() && textField.isVisible()&&textField_1.isVisible()&hold.isVisible())
				{
					lblBorrowerId.setVisible(false);
					lblCallNumber.setVisible(false);
					textField.setVisible(false);
					textField_1.setVisible(false);
					hold.setVisible(false);
				}
				if (searchField.isVisible())
					searchField.setVisible(false);
				if (btnSearch.isVisible())
					btnSearch.setVisible(false);
				if (comboBox.isVisible())
					comboBox.setVisible(false);
				
				lblEnterTheFid.setVisible(true);
				payFine.setVisible(true);
				pay.setVisible(true);
				
				
			}
		});
		
		//on click pay button
		pay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BorrowerActions baP = new BorrowerActions(con);
					baP.payFines(Integer.parseInt(payFine.getText()));
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
}
