package library;

//We need to import the java.sql package to use JDBC
import java.sql.*;

//for reading from the command line
import java.io.*;

//for the login window
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
 * This class implements a graphical login window and a simple text
 * interface for interacting with the branch table 
 */
public class Library implements ActionListener {
	// notes: no hold requests will be allowed unless all books with that call number are checked out
	
	// command line reader
	private BufferedReader in = new BufferedReader(new InputStreamReader(
			System.in));

	private Connection con;

	// user is allowed 3 login attempts
	private int loginAttempts = 0;

	// components of the login window
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JFrame mainFrame;

	private enum CLERK_ACTIONS {
		ADD_BORROWER, CHECK_OUT, CHECK_IN, CHECK_IN_OVERDUE;
	}

	private void clerkActions(CLERK_ACTIONS ca) {
		try {
			PreparedStatement ps = null;
			switch (ca) {
			case ADD_BORROWER:
				// add new borrower
				ps = con.prepareStatement("INSERT INTO borrowers VALUES (?,?,?,?,?,?,?,?)");

				System.out.println("User ID");
				ps.setString(1, in.readLine());

				System.out.println("User name: ");
				String name = in.readLine();
				ps.setString(2, name);

				System.out.println("User password: ");
				String pass = in.readLine();
				ps.setString(1, pass);

				System.out.println("User Address: ");
				ps.setString(3, in.readLine());

				System.out.println("User Phone: ");
				ps.setString(4, in.readLine());

				System.out.println("User Email Address: ");
				ps.setString(5, in.readLine());

				System.out.println("User SIN or Student #: ");
				ps.setString(6, in.readLine());

				System.out.println("User Expiry Date: ");
				ps.setString(7, in.readLine());

				System.out.println("User Type: ");
				ps.setString(8, in.readLine());
				break;
			case CHECK_OUT:
				System.out.println("Borrower ID: ");
				String borid = in.readLine();
				
				System.out.println("List of call numbers:");
				String callnosString = in.readLine();
				
				String[] callnos = callnosString.split(",|\\s+");
				for (String cn : callnos) {
					// TODO check if this call number is available for borrowing
					Statement statusSearch = con.createStatement();
					ResultSet statusResult = statusSearch.executeQuery("SELECT copyNo, status FROM bookcopy WHERE callNumber = " + cn);
					String status = statusResult.getString("status");
					String copyNo = null;
					if (status.equals("in")) {
						copyNo = statusResult.getString("copyNo");
					} else if (status.equals("on-hold")) {
						// the book is on hold; check that the person who is borrowing has placed a hold on the book
						 Statement holdSearch = con.createStatement();
						 ResultSet holdResult = holdSearch.executeQuery("SELECT bid FROM HoldRequest WHERE callNumber = " + cn);
						 if (holdResult.getString("bid").equals(borid)) {
							 copyNo = statusResult.getString("copyNo");
						 } else {
							 System.out.println("Borrower " + borid + " is trying to borrow a held book with call number " + cn + " without placing a hold first. Skipping...");
							 continue;
						 }
					} else if (status.equals("out")) {
						System.out.println("Book with call number " + cn + " already has all copies out. Skipping...");
						continue;
					}
				}
				// TODO calculate outDate and inDate
				
				ps = con.prepareStatement("INSERT INTO borrowing VALUES (?,?,?,?,?,?)");
				
				break;
			case CHECK_IN:

				break;
			case CHECK_IN_OVERDUE:

				break;
			}
			if (ps != null) {
				// update the database
				ps.executeUpdate();
				// commit work
				con.commit();
				ps.close();
			}
		} catch (IOException e) {
			System.out.println("IOException!");
			e.printStackTrace();
		} catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			try {
				// undo the insert
				con.rollback();
			} catch (SQLException ex2) {
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}
	
	public enum BORROWER_ACTIONS {
		SEARCH_BOOKS, CHECK_ACCOUNT, PLACE_HOLD, PAY_FINE;
	}
	
	public void borrowerActions(BORROWER_ACTIONS ba, String searchField, String dropdown) {
		//Table fields
		String title_id;
		String title;
		String type;
		String pub_id;
		int price;
		String advance;
		int ytd_sales;
		String contract; 
		String notes;
		String pubdate;
		Statement stm;
		
		try {
			PreparedStatement ps = null;
			switch (ba) {
			case SEARCH_BOOKS:

				System.out.println(searchField);
	//			try{
	//			stm = con.createStatement();
				if (dropdown.equals("Title")){
					System.out.println("Title Item was selected from DropDown list");
//					ResultSet resultSearch = stm.executeQuery("SELECT * FROM titles WHERE title = " + searchField );
//					ResultSetMetaData rsmd = resultSearch.getMetaData();
//
//					// get number of columns
//					int numCols = rsmd.getColumnCount();
//					for (int i = 0; i < numCols; i++) {
//						// get column name and print it
//
//						System.out.printf("%-15s", rsmd.getColumnName(i + 1));
//					}
//					
//					stm.close();
					
					
				}else if (dropdown.equals("Author")){
					System.out.println("Author Item was selected from DropDown list");
				}else
					System.out.println("Subject Item was selected from DropDown list");
				//}catch (SQLException ex){
					//System.out.println("The SQL Exception has occured:" + ex);
				//}
				break;
			case CHECK_ACCOUNT:
				//TODO Tokhtar
				break;
			case PLACE_HOLD:
				ps = con.prepareStatement("");
				//TODO Tokhtar 
				break;
			case PAY_FINE:
				//TODO Tokhtar
				break;
			}
			if (ps != null) {
				ps.executeUpdate();
				// commit work
				con.commit();
				ps.close();
			}
		} catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			try {
				// undo the insert
				con.rollback();
			} catch (SQLException ex2) {
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}
	
	private enum LIBRARIAN_ACTIONS {
		ADD_BOOK, GENERATE_CHECKED_OUT_REPORT, GENERATE_POPULAR_REPORT; 
	}
	
	private void librarianActions(LIBRARIAN_ACTIONS la) {
		try {
			PreparedStatement ps = null;
			switch (la) {
			case ADD_BOOK:
				ps = con.prepareStatement("");
				break;
			case GENERATE_CHECKED_OUT_REPORT:
				
				break;
			case GENERATE_POPULAR_REPORT:
				
				break;
			}
			if (ps != null) {
				ps.executeUpdate();
				// commit work
				con.commit();
				ps.close();
			}
		} catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			try {
				// undo the insert
				con.rollback();
			} catch (SQLException ex2) {
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	/*
	 * constructs login window and loads JDBC driver
	 */
	public Library() {
//		mainFrame = new JFrame("User Login");
//
//		JLabel usernameLabel = new JLabel("Enter username: ");
//		JLabel passwordLabel = new JLabel("Enter password: ");
//
//		usernameField = new JTextField(10);
//		passwordField = new JPasswordField(10);
//		passwordField.setEchoChar('*');
//
//		JButton loginButton = new JButton("Log In");
//
//		JPanel contentPane = new JPanel();
//		mainFrame.setContentPane(contentPane);
//
//		// layout components using the GridBag layout manager
//
//		GridBagLayout gb = new GridBagLayout();
//		GridBagConstraints c = new GridBagConstraints();
//
//		contentPane.setLayout(gb);
//		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//
//		// place the username label
//		c.gridwidth = GridBagConstraints.RELATIVE;
//		c.insets = new Insets(10, 10, 5, 0);
//		gb.setConstraints(usernameLabel, c);
//		contentPane.add(usernameLabel);
//
//		// place the text field for the username
//		c.gridwidth = GridBagConstraints.REMAINDER;
//		c.insets = new Insets(10, 0, 5, 10);
//		gb.setConstraints(usernameField, c);
//		contentPane.add(usernameField);
//
//		// place password label
//		c.gridwidth = GridBagConstraints.RELATIVE;
//		c.insets = new Insets(0, 10, 10, 0);
//		gb.setConstraints(passwordLabel, c);
//		contentPane.add(passwordLabel);
//
//		// place the password field
//		c.gridwidth = GridBagConstraints.REMAINDER;
//		c.insets = new Insets(0, 0, 10, 10);
//		gb.setConstraints(passwordField, c);
//		contentPane.add(passwordField);
//
//		// place the login button
//		c.gridwidth = GridBagConstraints.REMAINDER;
//		c.insets = new Insets(5, 10, 10, 10);
//		c.anchor = GridBagConstraints.CENTER;
//		gb.setConstraints(loginButton, c);
//		contentPane.add(loginButton);
//
//		// register password field and OK button with action event handler
//		passwordField.addActionListener(this);
//		loginButton.addActionListener(this);
//
//		// anonymous inner class for closing the window
//		mainFrame.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				System.exit(0);
//			}
//		});
//
//		// size the window to obtain a best fit for the components
//		mainFrame.pack();
//
//		// center the frame
//		Dimension d = mainFrame.getToolkit().getScreenSize();
//		Rectangle r = mainFrame.getBounds();
//		mainFrame.setLocation((d.width - r.width) / 2,
//				(d.height - r.height) / 2);
//
//		// make the window visible
//		mainFrame.setVisible(true);
//
//		// place the cursor in the text field for the username
//		usernameField.requestFocus();
//
//		try {
//			// Load the Oracle JDBC driver
//			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
//		} catch (SQLException ex) {
//			System.out.println("Message: " + ex.getMessage());
//			System.exit(-1);
//		}
	}

	/*
	 * connects to Oracle database named ug using user supplied username and
	 * password
	 */
	private boolean connect(String username, String password) {
		String connectURL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug";

		try {
			con = DriverManager.getConnection(connectURL, username, password);

			System.out.println("\nConnected to Oracle!");
			return true;
		} catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			return false;
		}
	}

	/*
	 * event handler for login window
	 */
	public void actionPerformed(ActionEvent e) {
		if (connect(usernameField.getText(),
				String.valueOf(passwordField.getPassword()))) {
			// if the username and password are valid,
			// remove the login window and display a text menu
			mainFrame.dispose();
			showMenu();
		} else {
			loginAttempts++;

			if (loginAttempts >= 3) {
				mainFrame.dispose();
				System.exit(-1);
			} else {
				// clear the password
				passwordField.setText("");
			}
		}

	}

	/*
	 * displays simple text interface
	 */
	private void showMenu() {
		int choice;
		boolean quit;

		quit = false;

		try {
			// disable auto commit mode
			con.setAutoCommit(false);

			while (!quit) {
				System.out.print("\n\nPlease choose one of the following: \n");
				System.out.print("1.  Insert branch\n");
				System.out.print("2.  Delete branch\n");
				System.out.print("3.  Update branch\n");
				System.out.print("4.  Show branch\n");
				System.out.print("5.  Quit\n>> ");

				choice = Integer.parseInt(in.readLine());

				System.out.println(" ");

				switch (choice) {
				case 1:
					insertBranch();
					break;
				case 2:
					deleteBranch();
					break;
				case 3:
					updateBranch();
					break;
				case 4:
					showBranch();
					break;
				case 5:
					quit = true;
				}
			}

			con.close();
			in.close();
			System.out.println("\nGood Bye!\n\n");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("IOException!");

			try {
				con.close();
				System.exit(-1);
			} catch (SQLException ex) {
				System.out.println("Message: " + ex.getMessage());
			}
		} catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
		}
	}


	/*
	 * inserts a branch
	 */
	private void insertBranch() {
		int bid;
		String bname;
		String baddr;
		String bcity;
		int bphone;
		PreparedStatement ps;

		try {
			try {
				// TODO create table
				ps = con.prepareStatement("DROP TABLE branch");
				ps.executeUpdate();

				ps = con.prepareStatement("DROP SEQUENCE branch_counter");
				ps.execute();

				con.commit();

				ps = con.prepareStatement("CREATE TABLE branch "
						+ "(branch_id number(38) not null PRIMARY KEY,"
						+ "branch_name varchar(20) not null,"
						+ "branch_addr varchar(50),"
						+ "branch_city varchar(20), not null,"
						+ "branch_phone number(38))");
				ps.executeUpdate();
				con.commit();

				ps = con.prepareStatement("CREATE SEQUENCE branch_counter START WITH 1000");
				ps.execute();

				ps.close();
			} catch (SQLException e1) { e1.printStackTrace(); }

			ps = con.prepareStatement("INSERT INTO branch VALUES (branch_counter.nextval,?,?,?,?)");

			//System.out.print("\nBranch ID: ");
			//bid = Integer.parseInt(in.readLine());
			//ps.setInt(1, bid);

			System.out.print("\nBranch Name: ");
			bname = in.readLine();
			ps.setString(1, bname);

			System.out.print("\nBranch Address: ");
			baddr = in.readLine();

			if (baddr.length() == 0) {
				ps.setString(2, null);
			} else {
				ps.setString(2, baddr);
			}

			System.out.print("\nBranch City: ");
			bcity = in.readLine();
			ps.setString(3, bcity);

			System.out.print("\nBranch Phone: ");
			String phoneTemp = in.readLine();
			if (phoneTemp.length() == 0) {
				ps.setNull(4, java.sql.Types.INTEGER);
			} else {
				bphone = Integer.parseInt(phoneTemp);
				ps.setInt(4, bphone);
			}

			ps.executeUpdate();

			// commit work
			con.commit();

			ps.close();
		} catch (IOException e) {
			System.out.println("IOException!");
		} catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			try {
				// undo the insert
				con.rollback();
			} catch (SQLException ex2) {
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	/*
	 * deletes a branch
	 */
	private void deleteBranch() {
		int bid;
		PreparedStatement ps;

		try {
			ps = con.prepareStatement("DELETE FROM branch WHERE branch_id = ?");

			System.out.print("\nBranch ID: ");
			bid = Integer.parseInt(in.readLine());
			ps.setInt(1, bid);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0) {
				System.out.println("\nBranch " + bid + " does not exist!");
			}

			con.commit();

			ps.close();
		} catch (IOException e) {
			System.out.println("IOException!");
		} catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());

			try {
				con.rollback();
			} catch (SQLException ex2) {
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	/*
	 * updates the name of a branch
	 */
	private void updateBranch() {
		int bid;
		String bname;
		String bcity;
		PreparedStatement ps;

		try {
			ps = con.prepareStatement("UPDATE branch SET branch_name = ?, branch_city = ? WHERE branch_id = ?");

			System.out.print("\nBranch ID: ");
			bid = Integer.parseInt(in.readLine());
			ps.setInt(3, bid);

			System.out.print("\nBranch Name: ");
			bname = in.readLine();
			ps.setString(1, bname);

			System.out.print("\nBranch City: ");
			bcity = in.readLine();
			ps.setString(2, bcity);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println("\nBranch " + bid + " does not exist!");
			}

			con.commit();

			ps.close();
		} catch (IOException e) {
			System.out.println("IOException!");
		} catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());

			try {
				con.rollback();
			} catch (SQLException ex2) {
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	/*
	 * display information about branches
	 */
	private void showBranch() {
		String bid;
		String bname;
		String baddr;
		String bcity;
		String bphone;
		Statement stmt;
		ResultSet rs;

		try {
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT * FROM branch");

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();

			System.out.println(" ");

			// display column names;
			for (int i = 0; i < numCols; i++) {
				// get column name and print it

				System.out.printf("%-15s", rsmd.getColumnName(i + 1));
			}

			System.out.println(" ");

			while (rs.next()) {
				// for display purposes get everything from Oracle
				// as a string

				// simplified output formatting; truncation may occur

				bid = rs.getString("branch_id");
				System.out.printf("%-10.10s", bid);

				bname = rs.getString("branch_name");
				System.out.printf("%-20.20s", bname);

				baddr = rs.getString("branch_addr");
				if (rs.wasNull()) {
					System.out.printf("%-20.20s", " ");
				} else {
					System.out.printf("%-20.20s", baddr);
				}

				bcity = rs.getString("branch_city");
				System.out.printf("%-15.15s", bcity);

				bphone = rs.getString("branch_phone");
				if (rs.wasNull()) {
					System.out.printf("%-15.15s\n", " ");
				} else {
					System.out.printf("%-15.15s\n", bphone);
				}
			}

			// close the statement;
			// the ResultSet will also be closed
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
		}
	}

	public static void main(String args[]) {
		Library b = new Library();
	}
}

