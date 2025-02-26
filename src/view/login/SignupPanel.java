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
 * This class deals with the sign up function of the application. The panel contains text fields
 * in which they can enter their first name, last name, username, and password to create a new
 * account.
 */
@SuppressWarnings("serial")
public class SignupPanel extends JPanel {
	
	//Fields
	//JLabels
	private JLabel signupBox;
	private JLabel background;
	public static JLabel fillFields;
	public static JLabel passwordLength;
	
	//JButtons
	public static JButton loginButton = new JButton();
	public static JButton signupButton = new JButton();
	
	//JTextFields
	public static JTextField nameTextField;
	public static JTextField lastNameTextField;
	public static JTextField userNameTextField;
	public static JPasswordField passwordField;

	//Constructor method
	public SignupPanel() {
		
		//Set dimensions and location of the panel
		setLayout(null);
		setBounds(0,0,1920,1080);
		
		//Add the objects onto the panel
		addTextField();
		addButtons();
		
		//Add the error message onto the panel
		fillFields = new JLabel();
		fillFields.setText("Fill out all fields/User Exists!");
		fillFields.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		fillFields.setForeground(Color.RED);
		fillFields.setBounds(790,660,400,40);
		fillFields.setVisible(false);
		add(fillFields);
		
		//Add the password length error message onto the panel
		//Password must be more than 4 characters long
		passwordLength = new JLabel();
		passwordLength.setText("Password must be greater than 4 characters!");
		passwordLength.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		passwordLength.setForeground(Color.RED);
		passwordLength.setBounds(690,660,600,40);
		passwordLength.setVisible(false);
		add(passwordLength);
		
		//Sign up box
		signupBox = new JLabel(new ImageIcon("images/SignupPanel.png"));
		signupBox.setBounds(0,0,1920,1080);
		add(signupBox);
		
		//Background
		background = new JLabel(new ImageIcon("images/green.png"));
		background.setBounds(0,0,1920,1080);
		add(background);
		
	}
	
	//This method adds the buttons onto the panel
	public void addButtons() {
		
		loginButton.setBounds(985,750,200,70);
		loginButton.setText("Login");
		
		signupButton.setBounds(735,750,200,70);
		signupButton.setText("Sign Up");
		
		add(loginButton);
		add(signupButton);
		
	}
	
	//This method adds the text fields onto the panel
	public void addTextField() {
		
		nameTextField = new JTextField();
		nameTextField.setBounds(865,355,380,40);
		nameTextField.setFont(new Font("Arial", Font.PLAIN, 20));
		add(nameTextField);
		
		lastNameTextField = new JTextField();
		lastNameTextField.setBounds(865,435,380,40);
		lastNameTextField.setFont(new Font("Arial", Font.PLAIN, 20));
		add(lastNameTextField);
		
		userNameTextField = new JTextField(3);
		userNameTextField.setBounds(865,515,380,40);
		userNameTextField.setFont(new Font("Arial", Font.PLAIN, 20));
		add(userNameTextField);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(865,595,380,40);
		passwordField.setFont(new Font("Arial", Font.PLAIN, 20));
		add(passwordField);
		
	}


}
