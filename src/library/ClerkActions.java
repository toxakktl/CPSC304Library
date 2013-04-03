package library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ClerkActions extends UserActions {
	// OUTDATE IS DUE DATE!
	
	public ClerkActions(Connection c) {
		super(c);
	}

	public void addBorrower(String bid, String password, String username, String address, String phone,
			String email, String sin, java.util.Date expiry, String type) {
		try {
			PreparedStatement ps = con.prepareStatement("INSERT INTO Borrower VALUES (?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, Integer.parseInt(bid));
			ps.setString(2, password);
			ps.setString(3, username);
			ps.setString(4, address);
			ps.setString(5, phone);
			ps.setString(6, email);
			ps.setInt(7, Integer.parseInt(sin));
			ps.setDate(8, new java.sql.Date(expiry.getTime()));
			ps.setString(9, type);

			ps.executeUpdate();
			con.commit();
			ps.close();

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			try {
				// undo the insert
				con.rollback();
			} catch (SQLException ex2) {
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}

	}

	public void checkOut(String bid, String[] callNos) {
		try {
			// calculate check-out date and the due date
			// do this first because due date is the same for everything
			Statement timeLimitSearch = con.createStatement();
			ResultSet timeLimitResult = timeLimitSearch.executeQuery("SELECT bookTimeLimit FROM BorrowerType t, Borrower b WHERE b.bid = " + bid + " AND b.type = t.type");
			timeLimitResult.next();
			GregorianCalendar cal = new GregorianCalendar();
			cal.add(GregorianCalendar.DATE, timeLimitResult.getInt("bookTimeLimit"));
			java.sql.Date outDate = new java.sql.Date(System.currentTimeMillis());
			java.sql.Date dueDate = new java.sql.Date(cal.getTimeInMillis());

			String receipt = null;
			for (String callNumber : callNos) {
				// check if this call number is available for borrowing
				String copyNo = null;
				Statement statusSearch = con.createStatement();
				ResultSet statusResult = statusSearch.executeQuery("SELECT copyNo, status FROM BookCopy WHERE callNumber = '" + callNumber + "'");
				while (statusResult.next()) {
					String status = statusResult.getString("status");
					if (status.equals("in")) {
						copyNo = statusResult.getString("copyNo");
						System.out.println("Borrower successfully checked out book");
						break;
					} else if (status.equals("on-hold")) {
						// the book is on hold; check that the person who is borrowing has placed a hold on the book
						Statement holdSearch = con.createStatement();
						ResultSet holdResult = holdSearch.executeQuery("SELECT bid FROM HoldRequest WHERE callNumber = '" + callNumber + "' AND bid = " + bid);
						if (holdResult.next()) {
							// the person has placed a hold on this book
							copyNo = statusResult.getString("copyNo");
							System.out.println("Borrower successfully checked out a book he placed on hold");
							break;
						} else {
							System.out.println("Borrower " + bid + " is trying to borrow a held book with call number " + callNumber + " without placing a hold first. Skipping...");
							continue;
						}
					} else if (status.equals("out")) {
						System.out.println("Book with call number " + callNumber + " already has all copies out. Skipping...");
						continue;
					}
				}
				if (copyNo == null) {
					continue;
				}
				PreparedStatement checkOut = con.prepareStatement("UPDATE BookCopy SET status = 'out' WHERE callNumber = '" + callNumber + "' AND copyNo = '" + copyNo + "'");
				checkOut.executeUpdate();
				con.commit();
				checkOut.close();

				PreparedStatement ps = con.prepareStatement("INSERT INTO borrowing VALUES (borid_counter.nextval,?,?,?,?,?)");
				ps.setInt(1, Integer.parseInt(bid));
				ps.setString(2, callNumber);
				ps.setString(3, copyNo);
				ps.setDate(4, dueDate);
				ps.setDate(5, null);

				ps.executeUpdate();
				con.commit();
				ps.close();
				if (receipt == null)
					receipt = "Items checked out:\n";
				receipt += callNumber + "\n";
			}
			// print a note with the items and their due date
			if (receipt != null) {
				receipt += "Due: " + cal.getTime().toString() + "\n";
				JOptionPane.showMessageDialog(null, receipt, "Check-out receipt", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			try {
				// undo the insert
				con.rollback();
			} catch (SQLException ex2) {
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	public void checkIn(String callNumber, String copyNo) {
		try {
			// check if item is late
			PreparedStatement lateSearch = con.prepareStatement("SELECT bid, outDate, inDate FROM Borrowing WHERE callNumber = '" + callNumber + "' AND copyNo = '" + copyNo + "'");
			ResultSet lateResult = lateSearch.executeQuery();
			lateResult.next();
			java.util.Date dueDate = lateResult.getDate("outDate");
			if (new java.util.Date().after(dueDate)) {
				// today is after the due date; item is overdue. Fine the borrower
				PreparedStatement fine = con.prepareStatement("INSERT INTO fine VALUES (fineid_counter.nextval,?,?,NULL,?)");
				// calculate amount for fine ($1 per day late)
				java.util.Date late = new java.util.Date(System.currentTimeMillis() - dueDate.getTime());
				fine.setInt(1, late.getDate() * 1);
				fine.setDate(2, new java.sql.Date(System.currentTimeMillis()));
				fine.setInt(3, lateResult.getInt("bid"));

				fine.executeUpdate();
				con.commit();
				fine.close();

			}
			PreparedStatement checkIn = con.prepareStatement("UPDATE BookCopy SET status = ? WHERE callNumber = ? AND copyNo = ?");
			checkIn.setString(2, callNumber);
			checkIn.setString(3, copyNo);

			// check if hold for this item exists
			// TODO modify query to return the earliest placed hold for the book
			PreparedStatement holdSearch = con.prepareStatement("SELECT bid FROM HoldRequest WHERE callNumber = '" + callNumber + "'");
			ResultSet holdResult = holdSearch.executeQuery();
			if (holdResult.next()) {
				checkIn.setString(1, "on-hold");
			} else {
				checkIn.setString(1, "in");
			}

			checkIn.executeUpdate();
			con.commit();
			checkIn.close();

			PreparedStatement upDateBorrowing = con.prepareStatement("UPDATE Borrowing SET inDate = ? WHERE callNumber = '" + callNumber + "' AND copyNo = '" + copyNo + "'");
			upDateBorrowing.setDate(1, new java.sql.Date(System.currentTimeMillis()));
			upDateBorrowing.executeUpdate();
			con.commit();
			upDateBorrowing.close();

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			try {
				// undo the insert
				con.rollback();
			} catch (SQLException ex2) {
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	public void listOverdue() {
		try {
			PreparedStatement overdueSearch = con.prepareStatement("SELECT * FROM borrowing WHERE ? > outDate AND inDate IS NULL");
			overdueSearch.setDate(1, new java.sql.Date(System.currentTimeMillis()));
			ResultSet overdueResult = overdueSearch.executeQuery();

			// display data in a table
			JPanel panel = new JPanel();
			Vector columnNames = new Vector();
			Vector data = new Vector();

			ResultSetMetaData rsmd = overdueResult.getMetaData();
			//get number of columns
			int numCols = rsmd.getColumnCount();
			for (int i = 0; i < numCols; i++) {
				// get column name and print it
				columnNames.addElement(rsmd.getColumnName(i+1));
				System.out.printf("%-15s", rsmd.getColumnName(i + 1));
			}
			while (overdueResult.next()) {
				Vector row = new Vector(numCols);
				for (int i = 1; i <= numCols; i++) {
					row.addElement(overdueResult.getObject(i));
				}
				data.addElement(row);
			}
			JTable table = new JTable(data, columnNames);
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setMaxWidth(250);
			}
			JScrollPane scrollPane = new JScrollPane(table);
			panel.add(scrollPane);
			JFrame frame = new JFrame();
			frame.add(panel); //adding panel to the frame
			frame.setSize(460, 450); //setting frame size
			frame.setVisible(true);

			overdueSearch.close();
			overdueResult.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
