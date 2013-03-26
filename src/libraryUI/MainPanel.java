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

public class MainPanel extends JFrame {

	private JPanel contentPane;

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
		setBounds(100, 100, 450, 300);
		
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
		
		JLabel lblNewLabel = new JLabel(new ImageIcon(ImageIO.read(new File("Images/library1.jpg"))));
		lblNewLabel.setBounds(0, 0, 450, 256);
		contentPane.add(lblNewLabel);
	}
}
