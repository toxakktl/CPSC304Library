package library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;


public class LibrarianActions extends UserActions {

	public LibrarianActions(Connection c) {
		super(c);
	}

	//	Add a new book to the library
	//			INSERT INTO Book (callNumber, isbn, title, mainAuthor, publisher, year )
	//				VALUES(1, 2, 3, 4, 5, 6);
	//
	//	1 = callNumber
	//	2 = isbn
	//	3 = title
	//	4 = mainAuthor
	//	5 = publisher
	//	6 = year
	public void addBook(String callNumber, String isbn, String title, String mainAuthor,
			String publisher, int year) {
		PreparedStatement ps = null;
		try{
			ps = con.prepareStatement("INSERT INTO Book VALUES (?,?,?,?,?,?)");

			ps.setString(1, callNumber);
			ps.setString(2, isbn);
			ps.setString(3, title);
			ps.setString(4, mainAuthor);
			ps.setString(5, publisher);
			ps.setInt(6, year);

			// update the database
			ps.executeUpdate();
			// commit work
			con.commit();
			ps.close();

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
	
	// 	Add a new copy of an existing book to the library
	//			INSERT INTO BookCopy (callNumber, copyNo, status)
	//				VALUES(1, 2, 3);
	//
	//	1 = callNumber (MUST ALREADY EXIST)
	//	2 = isbn
	//	3 = status
	public void addCopy(String callNumber, String copyNo) {
		PreparedStatement ps = null;
		String status = "IN";
		try{
			ps = con.prepareStatement("INSERT INTO BookCopy VALUES (?,?,?)");

			ps.setString(1, callNumber);
			ps.setString(2, copyNo); // Should Increment TODO ex. C1, C2, C3... etc.
			ps.setString(3, status); // All Book Copies are set to "IN" status

			// update the database
			ps.executeUpdate();
			// commit work
			con.commit();
			ps.close();

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
	public void generateCheckedOutReport() {

	}

	public void generateMostPopularReport() {

	}

}
