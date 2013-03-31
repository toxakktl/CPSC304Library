package library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class BorrowerActions {

	Connection con;
	Vector columnNames = new Vector();
	Vector data = new Vector();
	JPanel panel = new JPanel();
	JTable table;
	
	public BorrowerActions(Connection c) {
		con = c;
	}
	
	public void searchBooks(String searchField, String dropdown) {
		Statement stm;
		try{
			stm = con.createStatement();
			if (dropdown.equals("Title")){
				
				//TODO Later replace query to Book, and title				
				ResultSet resultSearch = stm.executeQuery("SELECT * FROM authors WHERE au_lname = '" + searchField + "'");
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
		        
			}else if (dropdown.equals("Author")){
				//TODO Later chnage the query to Book, authors
				ResultSet resultSearch = stm.executeQuery("SELECT * FROM publishers WHERE pub_name = '" + searchField + "'");
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
			}else{
				//TODO Later replace query to Book, and title				
				ResultSet resultSearch = stm.executeQuery("SELECT * FROM roysched WHERE title_id = '" + searchField + "'");
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
				
			}catch (SQLException ex){
				System.out.println("The SQL Exception has occured:" + ex);
			}
	}
	
	/**Check his/her account. The system will display the items the borrower has currently borrowed and not yet returned,
	  any outstanding fines and the hold requests that have been placed by the borrower.
	 */
	public void checkAccount(String bid) {
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


			//ANY OUTSTANDING FEES
			String outstandingFines = "SELECT f.fid, f.amount, f.issuedDate FROM Fine f, Borrowing b " +
					"WHERE b.bid = '" +bid+ "'" + "AND f.borid = b.borid AND f.paidDate IS NULL";
			ResultSet oFines = stmF.executeQuery(outstandingFines);

			//HOLD REQUESTS THAT HAVE BEEN PLACED BY THE BORROWER
			String holdR = "SELECT hr.hid, hr.bid, hr.callNumber, b.isbn, b.title FROM Book b, HoldRequest hr " +
					"WHERE hr.bid = '" +bid+ "'" + "AND b.callNumber = hr.callNumber";
			ResultSet holdRequests = stmH.executeQuery(holdR);

			stmB.close();
			stmF.close();
			stmH.close();
			System.out.println("Does it get here?");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/**
	 * Place a hold request for a book that is out. When the item is returned, the system sends an email 
	 * to the borrower and informs the library clerk to keep the book out of the shelves.
	 * @throws SQLException 
	 */
	public void placeHold(int bid, String callNumber) throws SQLException {
		
		Statement stm = con.createStatement();
		String str = "SELECT bc.callNumber, bc.status FROM BookCopy bc WHERE bc.callNumber = '" + callNumber + "' AND bc.status = 'in'";
		ResultSet rs = stm.executeQuery(str);
		if (rs == null){

			PreparedStatement ps = con.prepareStatement("INSERT INTO HoldRequest VALUES (hid_counter.next,"+bid+","+callNumber+", sysdate)");
			System.out.println("inserted");
			ps.executeUpdate();
			con.commit();
			ps.close();
		}
		else
			System.out.println("The item is available, you do not need to place a hold");
	}
	
	
	/**Pay a fine.
	 * @throws SQLException */
	public void payFines(int fid) throws SQLException {
		//Update the paidDate
		PreparedStatement ps = con.prepareStatement("UPDATE Fine SET paidDate=sysdate WHERE fid=" + fid);
		ps.executeUpdate();
		con.commit();
		ps.close();
	}

}
