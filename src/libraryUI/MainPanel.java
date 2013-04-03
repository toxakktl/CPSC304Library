package libraryUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import library.BorrowerActions;
import library.ClerkActions;
import library.LibrarianActions;

import com.jgoodies.forms.factories.DefaultComponentFactory;

public class MainPanel extends JFrame {

	Connection con;

	private JPanel contentPane;
	private JLabel backgroundLabel;
	private JLabel lblBookBack;
	private JLabel lblWelcome;
	
	private JTextField searchField;
	private JComboBox comboBox;
	private JComboBox comboBox_1;

	private JTextField borrowerIDField;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField payFine;

	private final ClerkActions ca;
	private final JLabel lblBorrId = new JLabel("Borrower ID:");
	private final JTextField borIDField = new JTextField();
	private final JLabel lblPassword = new JLabel("Password:");
	private final JPasswordField passwordField = new JPasswordField();
	private final JLabel lblUsername = new JLabel("Name:");
	private final JLabel lblEmail = new JLabel("Email:");
	private final JLabel lblSin = new JLabel("SIN:");
	private final JLabel lblPhone;
	private final JLabel lblExpiryDate;
	private final JLabel lblUserType;
	private final JLabel lblAddress;
	private final JTextField addressField;
	private final JTextField emailField;
	private final JTextField sinField;
	private final JTextField usernameField;
	private final JTextField phoneField;
	private final JSpinner spinner = new JSpinner();
	private final JComboBox userTypeComboBox;
	private final JButton btnAddUser = new JButton("Submit");

	private final JLabel lblOnePerLine;
	private final JLabel lblCallNumbers;
	private final JLabel lblBorID2;
	private final JTextField borIDField2;
	private final JTextArea callNumbersField = new JTextArea();
	private final JButton btnCheckOut;

	private final JLabel lblCallNumbers2;
	private final JLabel lblCopyNumber;
	private final JTextField callNumberField;
	private final JTextField copyNumberField;
	private final JButton btnCheckIn;

	private JTextField AddBookcallNumber;
	private JTextField AddBookisbn;
	private JTextField AddBooktitle;
	private JTextField AddBookauthor;
	private JTextField AddBookpublisher;
	private JTextField AddBookyear;

	private JTextField GMPNum;
	private JTextField GMPYear;

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public MainPanel(final Connection con) throws IOException {
		ca = new ClerkActions(con);
		this.con = con;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 499, 355);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnClerk = new JMenu("Clerk");
		menuBar.add(mnClerk);

		JMenuItem mntmNewMenuItem = new JMenuItem("Add Borrower");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hideAll();
				lblBorrId.setVisible(true);
				borIDField.setVisible(true);
				lblPassword.setVisible(true);
				passwordField.setVisible(true);
				lblUsername.setVisible(true);
				lblEmail.setVisible(true);
				lblSin.setVisible(true);
				phoneField.setVisible(true);
				emailField.setVisible(true);
				sinField.setVisible(true);
				usernameField.setVisible(true);
				spinner.setVisible(true);
				userTypeComboBox.setVisible(true);
				btnAddUser.setVisible(true);
				lblPhone.setVisible(true);
				lblExpiryDate.setVisible(true);
				lblUserType.setVisible(true);
				lblAddress.setVisible(true);
				addressField.setVisible(true);
				lblBookBack.setVisible(true);
			}
		});
		mnClerk.add(mntmNewMenuItem);

		JMenuItem mntmCheckOutItems = new JMenuItem("Check out items");
		mntmCheckOutItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideAll();
				lblCallNumbers.setVisible(true);
				lblBorID2.setVisible(true);
				lblOnePerLine.setVisible(true);

				borIDField2.setVisible(true);
				callNumbersField.setVisible(true);
				btnCheckOut.setVisible(true);
				lblBookBack.setVisible(true);
			}
		});
		mnClerk.add(mntmCheckOutItems);

		JMenuItem mntmProcessAReturn = new JMenuItem("Process a return");
		mntmProcessAReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideAll();
				lblCallNumbers2.setVisible(true);
				lblCopyNumber.setVisible(true);

				callNumberField.setVisible(true);
				copyNumberField.setVisible(true);
				btnCheckIn.setVisible(true);
				lblBookBack.setVisible(true);
			}
		});
		mnClerk.add(mntmProcessAReturn);

		JMenuItem mntmCheckOverdueItems = new JMenuItem("Check overdue items");
		mntmCheckOverdueItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ca.listOverdue();
				hideAll();
				lblWelcome.setVisible(true);
				backgroundLabel.setVisible(true);
			}
		});
		mnClerk.add(mntmCheckOverdueItems);

		JMenu mnBorrower = new JMenu("Borrower");
		menuBar.add(mnBorrower);

		JMenuItem mntmSearch = new JMenuItem("Search");
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

		JMenuItem mntmReportBooks = new JMenuItem("Report checked out");
		mntmReportBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LibrarianActions la = new LibrarianActions(con);
				la.generateCheckedOutReport();
			}
		});
		mnLibrarian.add(mntmReportBooks);

		JMenuItem mntmPopularItems = new JMenuItem("Popular items");
		mnLibrarian.add(mntmPopularItems);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmInitialize = new JMenuItem("Initialize tables");
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

		JMenuItem mntmShowBookCopies = new JMenuItem("Show all books in library");
		mntmShowBookCopies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LibrarianActions la = new LibrarianActions(con);
				la.showAllBookCopies();
			}
		});
		mnHelp.add(mntmShowBookCopies);

		JMenuItem mntmShowBorrowers = new JMenuItem("Show all borrowers");
		mntmShowBorrowers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LibrarianActions la = new LibrarianActions(con);
				la.showAllBorrowers();
			}
		});
		mnHelp.add(mntmShowBorrowers);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 150, 150));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblWelcome = DefaultComponentFactory.getInstance().createTitle("WELCOME");
		lblWelcome.setForeground(Color.WHITE);
		lblWelcome.setFont(new Font("Arial", Font.BOLD, 63));
		lblWelcome.setBounds(84, 86, 397, 110);
		contentPane.add(lblWelcome);

		// Components for Search (Clerk)
		searchField = new JTextField();
		searchField.setBounds(104, 20, 269, 28);
		contentPane.add(searchField);
		searchField.setColumns(10);
		searchField.setVisible(false);


		final JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BorrowerActions bActions = new BorrowerActions(con);
				String dropDownItem = (String)comboBox.getSelectedItem();
				String searchInputText = searchField.getText();

				bActions.searchBooks(searchInputText, dropDownItem);	
				lblWelcome.setVisible(true);
				searchField.setVisible(false);
				btnSearch.setVisible(false);
				comboBox.setVisible(false);

			}
		});
		btnSearch.setVisible(false);

		btnSearch.setBounds(374, 21, 117, 29);
		contentPane.add(btnSearch);

		comboBox = new JComboBox();
		comboBox.addItem("Title");
		comboBox.addItem("Author");
		comboBox.addItem("Subject");
		//comboBox.getItemAt(0);
		comboBox.setBounds(6, 22, 96, 27);
		contentPane.add(comboBox);

		comboBox.setVisible(false);

		comboBox_1 = new JComboBox();
		comboBox_1.addItem("Not returned");
		comboBox_1.addItem("Fines");
		comboBox_1.addItem("Hold requests");
		comboBox_1.setBounds(16, 61, 103, 27);
		contentPane.add(comboBox_1);
		comboBox_1.setVisible(false);



		// Components for Add book function (Librarian)

		AddBookcallNumber = new JTextField();
		AddBookcallNumber.setBounds(240, 45, 134, 28);
		contentPane.add(AddBookcallNumber);
		AddBookcallNumber.setColumns(10);

		AddBookisbn = new JTextField();
		AddBookisbn.setBounds(240, 75, 134, 28);
		contentPane.add(AddBookisbn);
		AddBookisbn.setColumns(10);

		AddBooktitle = new JTextField();
		AddBooktitle.setBounds(240, 105, 134, 28);
		contentPane.add(AddBooktitle);
		AddBooktitle.setColumns(10);

		AddBookauthor = new JTextField();
		AddBookauthor.setBounds(240, 135, 134, 28);
		contentPane.add(AddBookauthor);
		AddBookauthor.setColumns(10);

		AddBookpublisher = new JTextField();
		AddBookpublisher.setBounds(240, 165, 134, 28);
		contentPane.add(AddBookpublisher);
		AddBookpublisher.setColumns(10);

		AddBookyear = new JTextField();
		AddBookyear.setBounds(240, 195, 134, 28);
		contentPane.add(AddBookyear);
		AddBookyear.setColumns(10);

		final JLabel lblABCallNumber = new JLabel("Call Number");
		lblABCallNumber.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblABCallNumber.setBounds(120, 45, 98, 19);
		contentPane.add(lblABCallNumber);

		final JLabel lblIsbn = new JLabel("ISBN");
		lblIsbn.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblIsbn.setBounds(120, 75, 78, 19);
		contentPane.add(lblIsbn);

		final JLabel lblBookTitle = new JLabel("Book Title");
		lblBookTitle.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblBookTitle.setBounds(120, 105, 78, 19);
		contentPane.add(lblBookTitle);

		final JLabel lblMainAuthor = new JLabel("Main Author");
		lblMainAuthor.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblMainAuthor.setBounds(120, 135, 97, 19);
		contentPane.add(lblMainAuthor);

		final JLabel lblPublisher = new JLabel("Publisher");
		lblPublisher.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblPublisher.setBounds(120, 165, 73, 19);
		contentPane.add(lblPublisher);

		final JLabel lblYearPublished = new JLabel("Year Published");
		lblYearPublished.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblYearPublished.setBounds(120, 195, 116, 19);
		contentPane.add(lblYearPublished);

		setAddBookVisible(false);
		lblABCallNumber.setVisible(false);
		lblIsbn.setVisible(false);
		lblBookTitle.setVisible(false);
		lblMainAuthor.setVisible(false);
		lblPublisher.setVisible(false);
		lblYearPublished.setVisible(false);

		final JButton btnAddBook = new JButton("Add Book");
		btnAddBook.setVisible(false);
		btnAddBook.setBounds(250, 230, 117, 29);
		contentPane.add(btnAddBook);
		// END ADD BOOK Components

		// Add Copy Components -- Uses from Add Book components
		final JButton btnAddCopy = new JButton("Add Copy");
		btnAddCopy.setVisible(false);
		btnAddCopy.setBounds(250, 230, 117, 29);
		contentPane.add(btnAddCopy);

		// END ADD COPY Components
		// Generate Most popular (GMP) books components
		final JLabel lblGMPNum = new JLabel("# of Books ");
		lblGMPNum.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblGMPNum.setBounds(120, 105, 110, 19);
		contentPane.add(lblGMPNum);

		final JLabel lblGMPYear = new JLabel("From Year: ");
		lblGMPYear.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblGMPYear.setBounds(120, 135, 110, 19);
		contentPane.add(lblGMPYear);

		GMPNum = new JTextField();
		GMPNum.setBounds(240, 105, 134, 28);
		contentPane.add(GMPNum);
		GMPNum.setColumns(10);

		GMPYear = new JTextField();
		GMPYear.setBounds(240, 135, 134, 28);
		contentPane.add(GMPYear);
		GMPYear.setColumns(10);

		final JButton btnGMP = new JButton("Get Top Books");
		btnGMP.setVisible(false);
		btnGMP.setBounds(250, 165, 117, 29);
		contentPane.add(btnGMP);

		lblGMPNum.setVisible(false);
		lblGMPYear.setVisible(false);
		GMPNum.setVisible(false);
		GMPYear.setVisible(false);
		btnGMP.setVisible(false);

		btnAddBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LibrarianActions la = new LibrarianActions(con);
				String cn = AddBookcallNumber.getText();
				String isbn = AddBookisbn.getText();
				String title = AddBooktitle.getText();
				String author = AddBookauthor.getText();
				String pub = AddBookpublisher.getText();
				String year = AddBookyear.getText();
				int y = Integer.parseInt(year);
				la.addBook(cn, isbn, title, author, pub, y);
				la.addCopy(cn, 0);
				
				hideAll();
				lblWelcome.setVisible(true);
				backgroundLabel.setVisible(true);
				
				
			}
		});
		// END OF ADD BOOK FUNCTIONS (LIBRARIAN)


		comboBox.setVisible(false);

		final JLabel lblEnterTheBorrower = DefaultComponentFactory.getInstance().createLabel("Enter the Borrower ID");
		lblEnterTheBorrower.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblEnterTheBorrower.setForeground(Color.WHITE);
		lblEnterTheBorrower.setBounds(17, 26, 205, 16);
		contentPane.add(lblEnterTheBorrower);
		lblEnterTheBorrower.setVisible(false);

		borrowerIDField = new JTextField();
		borrowerIDField.setBounds(120, 60, 134, 28);
		contentPane.add(borrowerIDField);
		borrowerIDField.setColumns(10);
		borrowerIDField.setVisible(false);

		final JButton btnSubmit = new JButton("Submit");

		btnSubmit.setBounds(251, 60, 117, 29);
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

		hold.setVisible(false);


		btnSubmit.setVisible(false);

		btnSearch.setVisible(false);

		//Search
		// Add Copy Button -- (Librarian)
		btnAddCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LibrarianActions la = new LibrarianActions(con);
				String book = AddBookcallNumber.getText();
				int x = la.countCopies(book);
				// System.out.println(x);
				la.addCopy(book, x);
				hideAll();
				lblWelcome.setVisible(true);
				backgroundLabel.setVisible(true);
				lblBookBack.setVisible(false);
				AddBookcallNumber.setVisible(false);
				lblABCallNumber.setVisible(false);
				btnAddCopy.setVisible(false);

			}
		});

		// Report N Most Popular for year Y (Components) -- (Librarian)
		btnGMP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				LibrarianActions la = new LibrarianActions(con);
				int n = Integer.parseInt(GMPNum.getText());
				int y = Integer.parseInt(GMPYear.getText());
				la.generateMostPopularReport(n, y);
				hideAll();
				lblWelcome.setVisible(true);
				backgroundLabel.setVisible(true);
				lblBookBack.setVisible(false);
				btnGMP.setVisible(false);
				lblGMPNum.setVisible(false);
				lblGMPYear.setVisible(false);
				GMPNum.setVisible(false);
				GMPYear.setVisible(false);
			}
		});


		// Changing window visibilities for functions
		/* Change window for Search */
		mntmSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hideAll();
				searchField.setVisible(true);
				btnSearch.setVisible(true);
				comboBox.setVisible(true);	
				backgroundLabel.setVisible(true);
			}
		});

		//Check account
		mntmCheckAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hideAll();
				comboBox_1.setVisible(true);
				lblEnterTheBorrower.setVisible(true);
				btnSubmit.setVisible(true);
				borrowerIDField.setVisible(true);
				backgroundLabel.setVisible(true);
			}
		});

		//on click Submit for checking account
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BorrowerActions bact = new BorrowerActions(con);
				String dropDownItem = (String)comboBox_1.getSelectedItem();
				bact.checkAccount(borrowerIDField.getText(), dropDownItem);

				lblWelcome.setVisible(true);

				comboBox_1.setVisible(false);
				lblEnterTheBorrower.setVisible(false);
				btnSubmit.setVisible(false);
				borrowerIDField.setVisible(false);
			}
		});

		//Place a hold
		mntmPlaceAHold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Add shit
				hideAll();
				lblBorrowerId.setVisible(true);
				lblCallNumber.setVisible(true);
				textField.setVisible(true);
				textField_1.setVisible(true);
				hold.setVisible(true);
				backgroundLabel.setVisible(true);
			}
		});

		//on click place a hold request
		hold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BorrowerActions baH = new BorrowerActions(con);
					baH.placeHold(Integer.parseInt(textField_1.getText()), textField.getText());
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} 
				lblWelcome.setVisible(true);

				lblBorrowerId.setVisible(false);
				lblCallNumber.setVisible(false);
				textField.setVisible(false);
				textField_1.setVisible(false);
				hold.setVisible(false);
			}
		});

		//On click menu item pay a Fine
		mntmPayAFine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hideAll();
				lblEnterTheFid.setVisible(true);
				payFine.setVisible(true);
				pay.setVisible(true);
				backgroundLabel.setVisible(true);
			}
		});

		//on click pay button
		pay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BorrowerActions baP = new BorrowerActions(con);
					baP.payFines(Integer.parseInt(payFine.getText()));
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				lblWelcome.setVisible(true);

				lblEnterTheFid.setVisible(false);
				payFine.setVisible(false);
				pay.setVisible(false);
			}
		});


		// Add Copy Window change
		mntmAddACopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hideAll();
				lblWelcome.setVisible(false);
				lblBookBack.setVisible(true);
				AddBookcallNumber.setVisible(true);
				lblABCallNumber.setVisible(true);
				btnAddCopy.setVisible(true);
			}
		});
		/* Change window for Add Book */

		mntmAddABook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hideAll();
				lblWelcome.setVisible(false);
				lblBookBack.setVisible(true);
				setAddBookVisible(true);
				btnAddBook.setVisible(true);
				lblABCallNumber.setVisible(true);
				lblIsbn.setVisible(true);
				lblBookTitle.setVisible(true);
				lblMainAuthor.setVisible(true);
				lblPublisher.setVisible(true);
				lblYearPublished.setVisible(true);
			}
		});

		mntmPopularItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				hideAll();
				lblWelcome.setVisible(false);
				lblBookBack.setVisible(true);
				btnGMP.setVisible(true);
				lblGMPNum.setVisible(true);
				lblGMPYear.setVisible(true);
				GMPNum.setVisible(true);
				GMPYear.setVisible(true);
			}
		});
		lblBorrId.setVisible(false);

		// CLERK ACTIONS STUFF

		lblBorrId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBorrId.setBounds(27, 31, 76, 14);
		contentPane.add(lblBorrId);
		borIDField.setVisible(false);

		borIDField.setBounds(115, 27, 116, 22);
		borIDField.setColumns(10);
		contentPane.add(borIDField);
		lblPassword.setVisible(false);

		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(244, 75, 76, 16);
		contentPane.add(lblPassword);
		passwordField.setVisible(false);

		passwordField.setBounds(332, 71, 116, 20);
		contentPane.add(passwordField);
		lblUsername.setVisible(false);

		lblUsername.setHorizontalAlignment(SwingConstants.TRAILING);
		lblUsername.setBounds(27, 75, 76, 16);
		contentPane.add(lblUsername);
		lblEmail.setVisible(false);

		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(27, 145, 76, 16);
		contentPane.add(lblEmail);
		lblSin.setVisible(false);

		lblSin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSin.setBounds(244, 145, 76, 16);
		contentPane.add(lblSin);

		emailField = new JTextField();
		emailField.setVisible(false);
		emailField.setBounds(116, 139, 116, 22);
		contentPane.add(emailField);
		emailField.setColumns(10);

		sinField = new JTextField();
		sinField.setVisible(false);
		sinField.setBounds(332, 139, 116, 22);
		contentPane.add(sinField);
		sinField.setColumns(10);

		lblExpiryDate = new JLabel("Expiry Date:");
		lblExpiryDate.setVisible(false);
		lblExpiryDate.setHorizontalAlignment(SwingConstants.TRAILING);
		lblExpiryDate.setBounds(27, 178, 76, 16);
		contentPane.add(lblExpiryDate);

		usernameField = new JTextField();
		usernameField.setVisible(false);
		usernameField.setBounds(116, 69, 116, 22);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		spinner.setVisible(false);
		spinner.setModel(new SpinnerDateModel(new Date(1364799600000L), null, null, Calendar.DAY_OF_YEAR));
		JSpinner.DateEditor de = new JSpinner.DateEditor(spinner, "dd/MM/yyyy");
		spinner.setEditor(de);
		spinner.setBounds(116, 174, 116, 20);

		contentPane.add(spinner);

		lblUserType = new JLabel("User Type:");
		lblUserType.setVisible(false);
		lblUserType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUserType.setBounds(244, 178, 76, 16);
		contentPane.add(lblUserType);

		userTypeComboBox = new JComboBox();
		userTypeComboBox.setVisible(false);
		userTypeComboBox.setModel(new DefaultComboBoxModel(new String[] {"Student", "Faculty", "Staff"}));
		userTypeComboBox.setBounds(332, 172, 116, 22);
		contentPane.add(userTypeComboBox);
		btnAddUser.setVisible(false);
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ca.addBorrower(borIDField.getText(), passwordField.getText(), usernameField.getText(), addressField.getText(), phoneField.getText(), emailField.getText(), sinField.getText(), (Date) spinner.getValue(), (String) userTypeComboBox.getSelectedItem());
				hideAll();
				lblWelcome.setVisible(true);
				backgroundLabel.setVisible(true);
			}
		});
		btnAddUser.setBounds(376, 254, 95, 25);

		contentPane.add(btnAddUser);

		lblPhone = new JLabel("Phone:");
		lblPhone.setVisible(false);
		lblPhone.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPhone.setBounds(244, 110, 76, 14);
		contentPane.add(lblPhone);

		phoneField = new JTextField();
		phoneField.setVisible(false);
		phoneField.setBounds(332, 104, 116, 20);
		contentPane.add(phoneField);
		phoneField.setColumns(10);

		lblAddress = new JLabel("Address:");
		lblAddress.setVisible(false);
		lblAddress.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAddress.setBounds(27, 110, 76, 14);
		contentPane.add(lblAddress);

		addressField = new JTextField();
		addressField.setVisible(false);
		addressField.setBounds(116, 104, 116, 20);
		contentPane.add(addressField);
		addressField.setColumns(10);

		// stuff for check out

		lblBorID2 = new JLabel("Borrower ID:");
		lblBorID2.setVisible(false);
		lblBorID2.setBounds(12, 13, 107, 14);
		contentPane.add(lblBorID2);

		borIDField2 = new JTextField();
		borIDField2.setVisible(false);
		borIDField2.setBounds(115, 11, 86, 20);
		contentPane.add(borIDField2);
		borIDField2.setColumns(10);

		lblCallNumbers = new JLabel("Call Numbers:");
		lblCallNumbers.setBounds(12, 40, 95, 14);
		lblCallNumbers.setVisible(false);
		contentPane.add(lblCallNumbers);
		
		callNumbersField.setVisible(false);
		callNumbersField.setBounds(115, 40, 356, 206);

		contentPane.add(callNumbersField);

		btnCheckOut = new JButton("Check Out");
		btnCheckOut.setVisible(false);
		btnCheckOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ca.checkOut(borIDField2.getText(), callNumbersField.getText().split("\n"));
				hideAll();
				lblWelcome.setVisible(true);
				backgroundLabel.setVisible(true);
			}
		});
		btnCheckOut.setBounds(376, 260, 95, 25);
		contentPane.add(btnCheckOut);

		lblOnePerLine = new JLabel("(One Per Line)");
		lblOnePerLine.setVisible(false);
		lblOnePerLine.setBounds(12, 55, 95, 14);
		contentPane.add(lblOnePerLine);

		// stuff for check in

		lblCallNumbers2 = new JLabel("Call Number:");
		lblCallNumbers2.setVisible(false);
		lblCallNumbers2.setBounds(105, 54, 86, 14);
		contentPane.add(lblCallNumbers2);

		callNumberField = new JTextField();
		callNumberField.setVisible(false);
		callNumberField.setBounds(203, 48, 171, 20);
		contentPane.add(callNumberField);
		callNumberField.setColumns(10);

		lblCopyNumber = new JLabel("Copy Number:");
		lblCopyNumber.setVisible(false);
		lblCopyNumber.setBounds(105, 84, 86, 14);
		contentPane.add(lblCopyNumber);

		copyNumberField = new JTextField();
		copyNumberField.setVisible(false);
		copyNumberField.setBounds(203, 78, 171, 20);
		contentPane.add(copyNumberField);
		copyNumberField.setColumns(10);

		btnCheckIn = new JButton("Check In");
		btnCheckIn.setVisible(false);
		btnCheckIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ca.checkIn(callNumberField.getText(), copyNumberField.getText());
				hideAll();
				lblWelcome.setVisible(true);
				backgroundLabel.setVisible(true);
			}
		});
		btnCheckIn.setBounds(203, 137, 95, 25);
		contentPane.add(btnCheckIn);
		// END OF GENERATE MOST POPULAR COMPONENTS

		// Sets Background for Add Book
		lblBookBack = new JLabel(new ImageIcon(ImageIO.read(new File("Images/book1.jpg"))));
		lblBookBack.setBounds(-2, -9, 502, 350);
		contentPane.add(lblBookBack);
		lblBookBack.setVisible(false);


		// Sets Background -- MUST BE LAST to be background
		backgroundLabel = new JLabel(new ImageIcon(ImageIO.read(new File("Images/library1.jpg"))));
		backgroundLabel.setBounds(-2, -9, 502, 350);
		contentPane.add(backgroundLabel);
	}

	private void setAddBookVisible(boolean b){
		hideAll();
		if (b){
			AddBookcallNumber.setVisible(true);
			AddBookisbn.setVisible(true);
			AddBooktitle.setVisible(true);
			AddBookauthor.setVisible(true);
			AddBookpublisher.setVisible(true);
			AddBookyear.setVisible(true);
			lblBookBack.setVisible(true);
		}
	}

	private void hideAll() {
		for (Component c : contentPane.getComponents()) {
			c.setVisible(false);
		}
		//lblBookBack.setVisible(true);
	}
}
