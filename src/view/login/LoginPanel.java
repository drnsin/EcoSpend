package view.login;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/*
 * This class is the login panel of the application that contains text fields and buttons that allow the user
 * to login or move to the sign up button. This is the panel that shows up when the application runs.
 */
@SuppressWarnings("serial")
public class LoginPanel extends JPanel {

	//Fields
	//Labels
	public static JLabel invalidID; //Error messages
	private JLabel loginBox; //Background
	
	//Buttons
	public static JButton loginButton;
	public static JButton signupButton;
	
	//Text fields
	public static JTextField userNameTextField;
	public static JPasswordField passwordField;
	
	//Constructor method
	public LoginPanel() {
		
		//Set the size and location of the panel
		setLayout(null);
		setBounds(0,0,1920,1080);
		
		//Add the objects onto the panel
		addButtons();
		addTextField();
		
		//Add the invalid id error message onto the panel
		invalidID = new JLabel();
		invalidID.setText("Invalid username/password! Try again!");
		invalidID.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		invalidID.setForeground(Color.RED);
		invalidID.setBounds(715,585,500,40);
		invalidID.setVisible(false);
		add(invalidID);
		
		//Add the background onto the panel
		loginBox = new JLabel(new ImageIcon("images/LoginBox.png"));
		loginBox.setBounds(0,0,1920,1080);
		add(loginBox);
		
	}
	
	//This method adds the login and sign up buttons onto the panel
	public void addButtons() {
		
		loginButton = new JButton();
		loginButton.setBounds(740,665,200,70);
		loginButton.setText("Login");
		
		signupButton = new JButton();
		signupButton.setBounds(950,665,200,70);
		signupButton.setText("Sign Up");
		
		add(loginButton);
		add(signupButton);
		
	}
	
	//This method adds the text fields onto the panel
	public void addTextField() {
		
		userNameTextField = new JTextField();
		userNameTextField.setBounds(865,445,380,40);
		userNameTextField.setFont(new Font("Arial", Font.PLAIN, 20));
		add(userNameTextField);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(865,525,380,40);
		passwordField.setFont(new Font("Arial", Font.PLAIN, 20));
		add(passwordField);
		
	}
	
}
