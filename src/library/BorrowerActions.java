package library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class BorrowerActions extends UserActions {

	//Fields for Borrower
	Vector columnNames = new Vector();
	Vector data = new Vector();
	JPanel panel = new JPanel();
	JTable table;

	public BorrowerActions(Connection c) {
		super(c);
	}

	public void searchBooks(String searchField, String dropdown) {
		Statement stm;
		try {
			stm = con.createStatement();
			if (dropdown.equals("Title")){

				//TODO Later replace query to Book, and title				
				ResultSet resultSearch = stm.executeQuery("SELECT * FROM Book WHERE title = '" + searchField + "'");
				ResultSetMetaData rsmd = resultSearch.getMetaData();
				//get number of columns

				int numCols = rsmd.getColumnCount();
				for (int i = 0; i < numCols; i++) {
					// get column name and print it
					columnNames.addElement(rsmd.getColumnName(i+1));
					System.out.printf("%-15s", rsmd.getColumnName(i + 1));
				}

				while (resultSearch.next()) {
					Vector row = new Vector(numCols);
					for (int i = 1; i <= numCols; i++) {
						row.addElement(resultSearch.getObject(i));
					}
					data.addElement(row);
				}

				resultSearch.close();
				stm.close();
				//New JFrame window for result table 
				table = new JTable(data, columnNames);

				TableColumn column;
				for (int i = 0; i < table.getColumnCount(); i++) {
					column = table.getColumnModel().getColumn(i);
					column.setMaxWidth(250);
				}
				JScrollPane scrollPane = new JScrollPane(table);
				panel.add(scrollPane);               
				JFrame frame = new JFrame();
				frame.add(panel);         //adding panel to the frame
				frame.setSize(460, 450); //setting frame size
				frame.setVisible(true);


			} else if (dropdown.equals("Author")){
				//TODO Later chnage the query to Book, authors
				ResultSet resultSearch = stm.executeQuery("SELECT * FROM Book WHERE mainAuthor = '" + searchField + "'");
				ResultSetMetaData rsmd = resultSearch.getMetaData();
				int numCols = rsmd.getColumnCount();
				for (int i = 0; i < numCols; i++) {
					// get column name and print it
					columnNames.addElement(rsmd.getColumnName(i+1));
					System.out.printf("%-15s", rsmd.getColumnName(i + 1));
				}

				while (resultSearch.next()) {
					Vector row = new Vector(numCols);
					for (int i = 1; i <= numCols; i++) {
						row.addElement(resultSearch.getObject(i));
					}
					data.addElement(row);
				}

				resultSearch.close();
				stm.close();
				//New JFrame window for result table 
				table = new JTable(data, columnNames);			
				TableColumn column;
				for (int i = 0; i < table.getColumnCount(); i++) {
					column = table.getColumnModel().getColumn(i);
					column.setMaxWidth(250);
				}
				JScrollPane scrollPane = new JScrollPane(table);
				panel.add(scrollPane);               
				JFrame frame = new JFrame();
				frame.add(panel);         //adding panel to the frame
				frame.setSize(460, 450); //setting frame size
				frame.setVisible(true);
			} else{
				//TODO Later replace query to Book, and subject	
				String sub = "SELECT hs.callNumber FROM HasSubject hs WHERE subject = '" +searchField+ "'";
				ResultSet resultSearch = stm.executeQuery("SELECT b.callNumber, b.isbn, b.title, b.mainAuthor, b.publisher, b.year " +
						"FROM ("+sub+") sub, Book b WHERE b.callNumber = sub.callNumber");
				ResultSetMetaData rsmd = resultSearch.getMetaData();
				//get number of columns
				int numCols = rsmd.getColumnCount();
				for (int i = 0; i < numCols; i++) {
					// get column name and print it
					columnNames.addElement(rsmd.getColumnName(i+1));
					System.out.printf("%-15s", rsmd.getColumnName(i + 1));
				}

				while (resultSearch.next()) {
					Vector row = new Vector(numCols);
					for (int i = 1; i <= numCols; i++) {
						row.addElement(resultSearch.getObject(i));
					}
					data.addElement(row);
				}

				resultSearch.close();
				stm.close();
				//New JFrame window for result table 
				table = new JTable(data, columnNames);

				TableColumn column;
				for (int i = 0; i < table.getColumnCount(); i++) {
					column = table.getColumnModel().getColumn(i);
					column.setMaxWidth(250);
				}
				JScrollPane scrollPane = new JScrollPane(table);
				panel.add(scrollPane);               
				JFrame frame = new JFrame();
				frame.add(panel);         //adding panel to the frame
				frame.setSize(460, 450); //setting frame size
				frame.setVisible(true);
			}

		} catch (SQLException ex){
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**Check his/her account. The system will display the items the borrower has currently borrowed and not yet returned,
	  any outstanding fines and the hold requests that have been placed by the borrower.
	 */
	public void checkAccount(String bid, String ddi) {
		Statement stmB;
		Statement stmF;
		Statement stmH;
		try {
			stmB = con.createStatement();
			stmF = con.createStatement();
			stmH = con.createStatement();

			//ITEMS THAT ARE BORROWERD BUT NOT YET RETURNED

			//All borrowings of particular borrower
			String allBorrowings = "SELECT * FROM Borrowing WHERE bid = '"+ bid + "'";
			//Borrowings with no return date (i.e not returned items)
			String notReturnedBorrowings = "SELECT * FROM ("+allBorrowings+") bor WHERE bor.inDate IS NULL ";
			//Result info
			String result = "SELECT bor.callNumber, b.isbn, b.title, b.mainAuthor, bor.outDate FROM ("+notReturnedBorrowings+ 
					") bor, Book b WHERE bor.callNumber = b.callNumber";
			ResultSet notReturnedItems = stmB.executeQuery(result);
			ResultSetMetaData rsmdB = notReturnedItems.getMetaData();


			//ANY OUTSTANDING FEES
			String outstandingFines = "SELECT f.fid, f.amount, f.issuedDate FROM Fine f, Borrowing b " +
					"WHERE b.bid = '" +bid+ "'" + "AND f.borid = b.borid AND f.paidDate IS NULL";
			ResultSet oFines = stmF.executeQuery(outstandingFines);
			ResultSetMetaData rsmdF = oFines.getMetaData();

			//HOLD REQUESTS THAT HAVE BEEN PLACED BY THE BORROWER
			String holdR = "SELECT hr.hid, hr.bid, hr.callNumber, b.isbn, b.title FROM Book b, HoldRequest hr " +
					"WHERE hr.bid = '" +bid+ "'" + "AND b.callNumber = hr.callNumber";
			ResultSet holdRequests = stmH.executeQuery(holdR);
			ResultSetMetaData rsmdH = holdRequests.getMetaData();

			if (ddi.equals("Not returned")){
				int numCols = rsmdB.getColumnCount();
				for (int i = 0; i < numCols; i++) {
					// get column name and print it
					columnNames.addElement(rsmdB.getColumnName(i+1));
					System.out.printf("%-15s", rsmdB.getColumnName(i + 1));
				}

				while (notReturnedItems.next()) {
					Vector row = new Vector(numCols);
					for (int i = 1; i <= numCols; i++) {
						row.addElement(notReturnedItems.getObject(i));
					}
					data.addElement(row);
				}

				notReturnedItems.close();
				stmB.close();
				//New JFrame window for result table 
				table = new JTable(data, columnNames);			
				TableColumn column;
				for (int i = 0; i < table.getColumnCount(); i++) {
					column = table.getColumnModel().getColumn(i);
					column.setMaxWidth(250);
				}
				JScrollPane scrollPane = new JScrollPane(table);
				panel.add(scrollPane);               
				JFrame frame = new JFrame();
				frame.add(panel);         //adding panel to the frame
				frame.setSize(460, 450); //setting frame size
				frame.setVisible(true);
			} else if (ddi.equals("Fines")){
				int numCols = rsmdF.getColumnCount();
				for (int i = 0; i < numCols; i++) {
					// get column name and print it
					columnNames.addElement(rsmdF.getColumnName(i+1));
					System.out.printf("%-15s", rsmdF.getColumnName(i + 1));
				}

				while (oFines.next()) {
					Vector row = new Vector(numCols);
					for (int i = 1; i <= numCols; i++) {
						row.addElement(oFines.getObject(i));
					}
					data.addElement(row);
				}

				oFines.close();
				stmF.close();
				//New JFrame window for result table 
				table = new JTable(data, columnNames);			
				TableColumn column;
				for (int i = 0; i < table.getColumnCount(); i++) {
					column = table.getColumnModel().getColumn(i);
					column.setMaxWidth(250);
				}
				JScrollPane scrollPane = new JScrollPane(table);
				panel.add(scrollPane);               
				JFrame frame = new JFrame();
				frame.add(panel);         //adding panel to the frame
				frame.setSize(460, 450); //setting frame size
				frame.setVisible(true);
			} else if (ddi.equals("Hold requests")){
				int numCols = rsmdH.getColumnCount();
				for (int i = 0; i < numCols; i++) {
					// get column name and print it
					columnNames.addElement(rsmdH.getColumnName(i+1));
					System.out.printf("%-15s", rsmdH.getColumnName(i + 1));
				}

				while (holdRequests.next()) {
					Vector row = new Vector(numCols);
					for (int i = 1; i <= numCols; i++) {
						row.addElement(holdRequests.getObject(i));
					}
					data.addElement(row);
				}

				holdRequests.close();
				stmH.close();
				//New JFrame window for result table 
				table = new JTable(data, columnNames);			
				TableColumn column;
				for (int i = 0; i < table.getColumnCount(); i++) {
					column = table.getColumnModel().getColumn(i);
					column.setMaxWidth(250);
				}
				JScrollPane scrollPane = new JScrollPane(table);
				panel.add(scrollPane);               
				JFrame frame = new JFrame();
				frame.add(panel);         //adding panel to the frame
				frame.setSize(460, 450); //setting frame size
				frame.setVisible(true);
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}	
	}

	/**
	 * Place a hold request for a book that is out. When the item is returned, the system sends an email 
	 * to the borrower and informs the library clerk to keep the book out of the shelves.
	 * @throws SQLException 
	 */
	public void placeHold(int bid, String callNumber) {
		try {
			Statement stm = con.createStatement();
			String str = "SELECT bc.callNumber, bc.status FROM BookCopy bc WHERE bc.callNumber = '" + callNumber + "' AND bc.status = 'in'";
			ResultSet rs = stm.executeQuery(str);
			if (!rs.next()){

				PreparedStatement ps = con.prepareStatement("INSERT INTO HoldRequest VALUES (hid_counter.nextval,"+bid+", '"+callNumber+"', sysdate)");
				ps.executeUpdate();
				con.commit();	
				ps.close();

				Statement stm1  = con.createStatement();
				ResultSet result = stm1.executeQuery("SELECT * FROM HoldRequest");
				ResultSetMetaData rsmd = result.getMetaData();

				int numCols = rsmd.getColumnCount();
				for (int i = 0; i < numCols; i++) {
					// get column name and print it
					columnNames.addElement(rsmd.getColumnName(i+1));
					System.out.printf("%-15s", rsmd.getColumnName(i + 1));
				}

				while (result.next()) {
					Vector row = new Vector(numCols);
					for (int i = 1; i <= numCols; i++) {
						row.addElement(result.getObject(i));
					}
					data.addElement(row);
				}

				result.close();
				stm1.close();
				//New JFrame window for result table 
				table = new JTable(data, columnNames);			
				TableColumn column;
				for (int i = 0; i < table.getColumnCount(); i++) {
					column = table.getColumnModel().getColumn(i);
					column.setMaxWidth(250);
				}
				JScrollPane scrollPane = new JScrollPane(table);
				panel.add(scrollPane);               
				JFrame frame = new JFrame();
				frame.add(panel);         //adding panel to the frame
				frame.setSize(460, 450); //setting frame size
				frame.setVisible(true);

			}
			else {
				JOptionPane.showMessageDialog(null,"The item is available, you do not need to place a hold", "Information", JOptionPane.INFORMATION_MESSAGE);
			} 
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}


	/**Pay a fine.
	 * @throws SQLException */
	public void payFines(int fid) {
		try {
			//Update the paidDate
			PreparedStatement ps = con.prepareStatement("UPDATE Fine SET paidDate=sysdate WHERE fid=" + fid);
			ps.executeUpdate();
			con.commit();
			ps.close();

			Statement stm1  = con.createStatement();
			ResultSet result = stm1.executeQuery("SELECT * FROM Fine");
			ResultSetMetaData rsmd = result.getMetaData();

			int numCols = rsmd.getColumnCount();
			for (int i = 0; i < numCols; i++) {
				// get column name and print it
				columnNames.addElement(rsmd.getColumnName(i+1));
				System.out.printf("%-15s", rsmd.getColumnName(i + 1));
			}

			while (result.next()) {
				Vector row = new Vector(numCols);
				for (int i = 1; i <= numCols; i++) {
					row.addElement(result.getObject(i));
				}
				data.addElement(row);
			}

			result.close();
			stm1.close();
			//New JFrame window for result table 
			table = new JTable(data, columnNames);			
			TableColumn column;
			for (int i = 0; i < table.getColumnCount(); i++) {
				column = table.getColumnModel().getColumn(i);
				column.setMaxWidth(250);
			}
			JScrollPane scrollPane = new JScrollPane(table);
			panel.add(scrollPane);               
			JFrame frame = new JFrame();
			frame.add(panel);         //adding panel to the frame
			frame.setSize(460, 450); //setting frame size
			frame.setVisible(true);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
