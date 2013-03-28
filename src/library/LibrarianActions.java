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
	
	//	Generate report with all books that have been checked out
	//	shows also dates checked out and due dates
	//	flags overdue items << In java code?
	//			SELECT *
	//			FROM BookCopy JOIN Borrowing
	//			ON (BookCopy.callNumber = Borrowing.callNumber) AND (BookCopy.copyNo = Borrowing.copyNo)
	//			WHERE BookCopy.status LIKE 'OUT'
	public void generateCheckedOutReport() {
		PreparedStatement ps = null;
		try{
			ps = con.prepareStatement("SELECT * " +
					"FROM BookCopy JOIN Borrowing " +
					"ON (BookCopy.callNumber = Borrowing.callNumber) " +
					"AND (BookCopy.copyNo = Borrowing.copyNo) " +
					"WHERE BookCopy.status LIKE 'OUT'");
			
			// THIS SQL QUERY MUST BE TESTED FIRST TODO

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

	//	Generate report with "N" most popular items for a given year
	//	n = top N, y = year
	//			SELECT *
	//			FROM
	//				(SELECT id, count(id) as count
	//				FROM
	//					(SELECT *
	//					FROM Borrowing
	//					WHERE outdate LIKE [Y] + '%')
	//				GROUP BY id)
	//			WHERE rownum <= [N]
	//			ORDER BY count DESC;
	public void generateMostPopularReport(int n, int y) {
		PreparedStatement ps = null;
		try{
			ps = con.prepareStatement("SELECT * FROM " +
					"(SELECT bid, count(bid) as count FROM " +
					"(SELECT * FROM Borrowing WHERE outdate LIKE '?%') " +
					"GROUP BY bid) " +
					"WHERE rownum <= ? " +
					"ORDER BY count DESC");

			// TODO THIS SQL QUERY ONLY RETURNS BID AND COUNT OF BID! -- at least it should

			ps.setInt(1, y);
			ps.setInt(2, 2);

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
}


