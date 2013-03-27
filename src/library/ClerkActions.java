package library;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClerkActions extends UserActions {
	
	public ClerkActions(Connection c) {
		super(c);
	}

	public void addBorrower() {
		
	}
	
	public void checkOut() {
		
	}
	
	public void checkIn() {
		
	}
	
	public void checkInOverdue() {
		
	}
	
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
}
