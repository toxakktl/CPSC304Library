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
		
		JMenu mnBorrower = new JMenu("Borrower");
		menuBar.add(mnBorrower);
		
		JMenu mnLibrarian = new JMenu("Librarian");
		menuBar.add(mnLibrarian);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 150, 150));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon(ImageIO.read(new File("Images/library1.jpg"))));
		lblNewLabel.setBounds(0, 0, 450, 256);
		contentPane.add(lblNewLabel);
	}
}
