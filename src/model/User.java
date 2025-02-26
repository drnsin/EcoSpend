package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import controller.AppController;

/*
 * This model class contains the user's login information. This model class will mainly be utilize to log
 * the user in and load all of their data for the application.
 */
public class User {

	//Fields
	private String name;
	private String lastName;
	private String username;
	private String password;
	
	//Setter and getters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	//This method determines if the credentials that the user entered during login matches any account
	//from the master account file
	//Taken from my login code from SDP #2
	public void currentAccount(String username, String password) {
		
		try {
			
			//Read the csv(comma seperated values) file
			Scanner input = new Scanner (new File("userInfo.csv"));
			input.useDelimiter(",|\\n");
			
			//As long as there is another line of information, keep doing the following
			while (input.hasNext()) {
				
				input.nextLine();
				
				String currentName = input.next();
				String currentLM = input.next();
				String currentUN = input.next();
				String currentPW = input.next();
				
				System.out.printf("%s, %s, %s, %s\n", currentName, currentLM, currentUN, currentPW);
				
				//If the user's username and password matches the currently read username and password from the file
				if (username.equals(currentUN) && password.equals(currentPW)) {
				
					//Set the fields to the user's information
					setName(currentName);
					setLastName(currentLM);
					setUsername(currentUN);
					setPassword(currentPW);
					
					AppController.userFound = true; //user has been found
				
				}
				
				//If the username matches one in the file
				if (username.equals(currentUN))
					AppController.userExists = true; //an account exists with the username
				
			}
		
			input.close();
			
			//If the user is able to successfully log in, display message in console
			if (AppController.userFound)
				System.out.println("LOGIN");
			
		} catch(FileNotFoundException error) {
			
			//Display the error to the console
			System.out.println("Sorry file not loading - please check the name/location");
		}
		
	}
	
}
