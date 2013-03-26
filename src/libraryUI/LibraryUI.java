package libraryUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Label;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LibraryUI extends JFrame {

	Connection connection;
	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField textField;
	private JFrame mainFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LibraryUI frame = new LibraryUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LibraryUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 499, 355);
		//mainFrame = new JFrame("Library Frame");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//mainFrame.setContentPane(contentPane);
		
		JLabel lblNewLabel;
		try {
			
			JButton btnLogin = new JButton("Login");
			btnLogin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						login();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			btnLogin.setBounds(182, 223, 117, 29);
			contentPane.add(btnLogin);
			
			passwordField = new JPasswordField();
			passwordField.setBounds(163, 172, 154, 39);
			contentPane.add(passwordField);
			
			textField = new JTextField();
			textField.setBounds(163, 104, 154, 41);
			contentPane.add(textField);
			textField.setColumns(10);
			
			JLabel lblUsername = DefaultComponentFactory.getInstance().createTitle("Username");
			lblUsername.setFont(new Font("Lucida Grande", Font.BOLD, 15));
			lblUsername.setForeground(Color.WHITE);
			lblUsername.setBounds(163, 85, 86, 16);
			contentPane.add(lblUsername);
			
			JLabel lblPassword = DefaultComponentFactory.getInstance().createTitle("Password");
			lblPassword.setFont(new Font("Lucida Grande", Font.BOLD, 15));
			lblPassword.setForeground(Color.WHITE);
			lblPassword.setBounds(163, 151, 120, 16);
			contentPane.add(lblPassword);
			lblNewLabel = new JLabel(new ImageIcon(ImageIO.read(new File("Images/library1.jpg"))));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(-2, -9, 502, 350);
			contentPane.add(lblNewLabel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//mainFrame.setVisible(true);
		try {
			// Load the Oracle JDBC driver
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			System.exit(-1);
		}
		
		
	}
	
	//Connect to Database
	public boolean connectToDb(String username, String password){
		
		String connectURL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug";
		
		
		try {
			connection = DriverManager.getConnection(connectURL, username, password);		
			System.out.println("\nConnected to Oracle!");
			return true;
		} catch (SQLException ex) {
			System.out.println("Message: " + ex.getMessage());
			return false;
		}
	}
	
	private void login() throws IOException{
		
		String username = textField.getText();
		String password = String.valueOf(passwordField.getPassword());
		
		if (connectToDb(username, password)){
			System.out.println("Success");
			this.dispose();
			MainPanel mp = new MainPanel();
			mp.setVisible(true);
			//showMainPanel();
		}else{
			System.out.println("Fail");
		}
	}
	
	
}
