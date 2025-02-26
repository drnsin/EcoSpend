package view.login;

import javax.swing.*;

/*
 * This class is the screen that contains the sign up and login panels for the application. It
 * is the first screen that displays when the application runs.
 */
@SuppressWarnings("serial")
public class LoginScreen extends JFrame {
	
	//Constructor method
	public LoginScreen() {
		
		//Setup the properties of the login screen
		setLayout(null);
		setSize(1920,1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("EcoSpend - Login");
        
	}

}
