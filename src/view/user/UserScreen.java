package view.user;

import javax.swing.JFrame;

/*
 * This screen is responsible for housing the user panel that contains the user's budgeting
 * information
 */
@SuppressWarnings("serial")
public class UserScreen extends JFrame {
	
	//Constructor method
	public UserScreen() {
		
		//Setup the properties of the frame
		setLayout(null);
		setSize(1920,1080);
		setVisible(true);
		setTitle("EcoSpend - User Profile");
		
	}

}
