package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;

import model.*;
import view.dashboard.*;
import view.graph.*;
import view.login.*;
import view.user.UserPanel;
import view.user.UserScreen;
/*
 * This class contains the basic logic of the application. It is responsible handling logging in and out, account
 * creation, instantiating the dash board, moving the user from each screen, setting up the default values for each
 * of the screens, and reading and writing the user's credentials
 */
public class AppController implements ActionListener {
	
	//Fields
	//Instantiate model classes
	public static Months months = new Months();
	public static User user = new User();
	public static Money money = new Money();
	
	//Instantiate login screen
	private LoginScreen loginScreen = new LoginScreen();
	private LoginPanel loginPanel = new LoginPanel();
	private SignupPanel signupPanel = new SignupPanel();
	
	//Instantiate setup panel in dashboard screen
	public static DashboardScreen dashboardScreen;
	public static SetupPanel setupPanel = new SetupPanel();
	
	//Instantiate user panel in user screen
	private static UserScreen userScreen;
	private static UserPanel userPanel = new UserPanel();
	
	private String filepath = "userInfo.csv"; //File path for the master file for all account login credentials
	public static boolean userFound = false; //Boolean value for if an account in the file matches user login credentials
	public static boolean userExists = false; //Flag for if the user in the file exists already to stop users from using same username
	private Boolean hasIncome = null; //Flag for if the user selected that they had a job

	//Constructor method
	public AppController() {

		//Add the login screen panels
		loginScreen.add(loginPanel);
		loginScreen.add(signupPanel);
		
		//Show the login panel when user runs application
		loginScreen.setVisible(true);
		loginPanel.setVisible(true);
		signupPanel.setVisible(false);
		
		//Adding action listeners to objects in each screen
		loginActionListener();
		
		dashboardActionListener();
		
		setupPanelActionListener();
		
		userPanelActionListener();
		
	}
	
	//This method adds an action listener to login screen buttons
	private void loginActionListener() {
			
		SignupPanel.loginButton.addActionListener(this);
		SignupPanel.signupButton.addActionListener(this);
		
		LoginPanel.loginButton.addActionListener(this);
		LoginPanel.signupButton.addActionListener(this);
		
	}
	
	//This method adds an action listener to dashboard screen buttons
	private void dashboardActionListener() {
		
		DashboardScreen.nextDayButton.addActionListener(this);
		DashboardScreen.newTransaction.addActionListener(this);
		DashboardScreen.nextMonth.addActionListener(this);
		DashboardScreen.previousMonth.addActionListener(this);
		DashboardScreen.userButton.addActionListener(this);
		DashboardScreen.logOutButton.addActionListener(this);
		
		TransactionPanel.backButton.addActionListener(this);
		
		GraphScreen.backButton.addActionListener(this);
		
	}
	
	//This method adds action listeners to setup panel objects
	private void setupPanelActionListener() {
		
		//Key listener to each text field in the setup panel
		//Restrict the user to only inputting digits
		//https://stackoverflow.com/questions/20541230/allow-only-numbers-in-jtextfield
		SetupPanel.budget.addKeyListener(new KeyAdapter() {
	         public void keyTyped(KeyEvent e) {
	             char c = e.getKeyChar();
	             if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
	                  e.consume();  // if it's not a number, ignore the event
	             }
	             
	         }
	         
	      });
		
		SetupPanel.balance.addKeyListener(new KeyAdapter() {
	         public void keyTyped(KeyEvent e) {
	        	 
	             char c = e.getKeyChar();
	             if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) { 
	                  e.consume();  // if it's not a number, ignore the event
	             }
	             
	         }
	         
	      });
		
		SetupPanel.income.addKeyListener(new KeyAdapter() {
	         public void keyTyped(KeyEvent e) {
	             char c = e.getKeyChar();
	             if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
	            		 e.consume();  // if it's not a number, ignore the event
	             }

	         }
	         
	      });
		
		SetupPanel.budgetDecimal.addKeyListener(new KeyAdapter() {
	         public void keyTyped(KeyEvent e) {
	             char c = e.getKeyChar();
	             if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
	            		 e.consume();  // if it's not a number, ignore the event
	             }
	          
	         }
	         
	      });
		
		SetupPanel.balanceDecimal.addKeyListener(new KeyAdapter() {
	         public void keyTyped(KeyEvent e) {
	             char c = e.getKeyChar();
	             if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
	            		 e.consume();  // if it's not a number, ignore the event
	             }
	          
	         }
	         
	      });
		
		SetupPanel.incomeDecimal.addKeyListener(new KeyAdapter() {
	         public void keyTyped(KeyEvent e) {
	             char c = e.getKeyChar();
	             if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
	            		 e.consume();  // if it's not a number, ignore the event
	             }
	          
	         }
	         
	      });
		
		//Action listeners to JRadioButtons and confirm button
		SetupPanel.yesIncome.addActionListener(this);
		SetupPanel.noIncome.addActionListener(this);
		SetupPanel.confirm.addActionListener(this);
		
		//This action listener looks for the selected month in the month combo box and fills the day combo box with the month's number of days
		//https://stackoverflow.com/questions/45718098/how-to-get-the-month-from-a-combobox-and-make-it-a-month-in-a-date?noredirect=1&lq=1
		SetupPanel.monthCombo.addActionListener(e -> {
	        @SuppressWarnings("unchecked")
			Month selMonth = (Month) ((JComboBox<Month>) e.getSource()).getSelectedItem();
	        SetupPanel.dayCombo.removeAllItems();
	        for (int x = 1; x <= LocalDate.of(months.getCurrentYear(), selMonth, 1).lengthOfMonth(); x++)
	        	SetupPanel.dayCombo.addItem(x);
	    });
		
	}
	
	//This method adds action listeners to setup panel objects
	private void userPanelActionListener() {
		
		//Key listener to each text field in the user panel
		//Restrict the user to only inputting digits
		//https://stackoverflow.com/questions/20541230/allow-only-numbers-in-jtextfield
		UserPanel.budget.addKeyListener(new KeyAdapter() {
	         public void keyTyped(KeyEvent e) {
	             char c = e.getKeyChar();
	             if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
	                  e.consume();  // if it's not a number, ignore the event
	             }
	             
	         }
	         
	      });
		
		UserPanel.balance.addKeyListener(new KeyAdapter() {
	         public void keyTyped(KeyEvent e) {
	        	 
	             char c = e.getKeyChar();
	             if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) { 
	                  e.consume();  // if it's not a number, ignore the event
	             }
	             
	         }
	         
	      });
		
		UserPanel.income.addKeyListener(new KeyAdapter() {
	         public void keyTyped(KeyEvent e) {
	             char c = e.getKeyChar();
	             if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
	            		 e.consume();  // if it's not a number, ignore the event
	             }

	         }
	         
	      });
		
		UserPanel.budgetDecimal.addKeyListener(new KeyAdapter() {
	         public void keyTyped(KeyEvent e) {
	             char c = e.getKeyChar();
	             if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
	            		 e.consume();  // if it's not a number, ignore the event
	             }
	          
	         }
	         
	      });
		
		UserPanel.balanceDecimal.addKeyListener(new KeyAdapter() {
	         public void keyTyped(KeyEvent e) {
	             char c = e.getKeyChar();
	             if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
	            		 e.consume();  // if it's not a number, ignore the event
	             }
	          
	         }
	         
	      });
		
		UserPanel.incomeDecimal.addKeyListener(new KeyAdapter() {
	         public void keyTyped(KeyEvent e) {
	             char c = e.getKeyChar();
	             if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
	            		 e.consume();  // if it's not a number, ignore the event
	             }
	          
	         }
	         
	      });
		
		//This action listener looks for the selected month in the month combo box and fills the day combo box with the month's number of days
		//https://stackoverflow.com/questions/45718098/how-to-get-the-month-from-a-combobox-and-make-it-a-month-in-a-date?noredirect=1&lq=1
		UserPanel.monthCombo.addActionListener(e -> {
	        @SuppressWarnings("unchecked")
			Month selMonth = (Month) ((JComboBox<Month>) e.getSource()).getSelectedItem();
	        UserPanel.dayCombo.removeAllItems();
	        for (int x = 1; x <= LocalDate.of(months.getCurrentYear(), selMonth, 1).lengthOfMonth(); x++)
	        	UserPanel.dayCombo.addItem(x);
	    });
		
		//Add action listeners to buttons
		UserPanel.yesIncome.addActionListener(this);
		UserPanel.noIncome.addActionListener(this);
		UserPanel.confirm.addActionListener(this);
		UserPanel.backButton.addActionListener(this);
		
	}
	
	//This method is the action performed method
	public void actionPerformed(ActionEvent event) {
		
		//If the sign up button is pressed
		if (event.getSource() == SignupPanel.signupButton) {
			signUp(); //Create a new account for the user
		}

		//Sends user to login panel from sign up panel
		if (event.getSource() == SignupPanel.loginButton) {
			loginPanel.setVisible(true); //Set the login panel to visible
			signupPanel.setVisible(false); //Set the sign up panel to not visible
		}

		//Logs in the user if all conditions are met
		if (event.getSource() == LoginPanel.loginButton) {
			checkValidID();
		}

		//Sends user to sign up panel from login panel
		if (event.getSource() == LoginPanel.signupButton) {
			signupPanel.setVisible(true); //Set the sign up panel to visible
			loginPanel.setVisible(false); //Set the login panel to not visible
		}
		
		//If the next day button is pressed
		if (event.getSource() == DashboardScreen.nextDayButton) {
			
			months.nextMonth(months.getCurrentMonth(), months.getCurrentDay()); //Move to the next day (Check if next day is next month)
			
			String data = months.getCurrentMonth() + "," + months.getCurrentDay() + "," + months.getCurrentYear() + ","; //String value of the current date
			
			overwrite(user.getUsername(), data, 1); //Overwrite the date to the current date in the csv file
			
			System.out.printf("%d-%d-%d\n", months.getCurrentMonth(), months.getCurrentDay(), months.getCurrentYear()); //Display the new date into the console
		
			//If user has a job/income, check if the current day is their pay day
			if (hasIncome)
				money.checkPayDay();
			
			SpendingsPanel.spendingsPanelMonth = months.getCurrentMonth(); //Set the month of the scroll panel to the current month
			DashboardScreen.monthLabel.setIcon(new ImageIcon("images/"+ (Month.of(SpendingsPanel.spendingsPanelMonth).name()) + ".png")); //Set month label to the current month
			DashboardScreen.totalMonthLabel.setText("Total Month Expense: $" + SpendingsPanel.df.format(Money.monthTotal[SpendingsPanel.spendingsPanelMonth])); //Update the total month expense to the current month
			TransactionController.spendingsPanel.removeAll(); //Remove the labels from the scroll pane
			TransactionController.spendingsPanel.showLabels(); //Update the transactions label
			pieChartValues(AppController.money.getUserBudget(), Money.monthTotal[AppController.months.getCurrentMonth()]); //Update the pie chart values of the current month
			DashboardScreen.setPercentColor(); //Update the percentage label
			DashboardScreen.dateLabel.setText("Current Day: " + Month.of(AppController.months.getCurrentMonth()).toString() + " " + 
					AppController.months.getCurrentDay() + ", " + AppController.months.getCurrentYear()); //Update the current date label
			
			//If the current month is January or December
			if (months.getCurrentMonth() == 12 && months.getCurrentDay() == 31) {
				
				DashboardScreen.nextDayButton.setEnabled(false); //Disable the next day button
				
			}
			
		}
		
		//If the user selected that they have income, show the text field and combo boxes
		if (event.getSource() ==  SetupPanel.yesIncome) {
			showIncome(true);
		}
		
		//If the user selected that they don't have income, hide the text field and combo boxes
		if (event.getSource() ==  SetupPanel.noIncome) {
			showIncome(false);
		}
		
		//User confirms their setup values
		if (event.getSource() == SetupPanel.confirm) {
			setupValues(); //Write the values into the account file and open up dashboard
		}
		
		//If the user presses on the new transaction button
		if (event.getSource() == DashboardScreen.newTransaction) {
			setupTransactionPanel(); //Open up the transaction panel
		}
		
		//If the selects the log out button
		if (event.getSource() == DashboardScreen.logOutButton) {
			loggedOut(); //Log the user out
		}
		
		//User presses the back button in the transaction panel
		if (event.getSource() == TransactionPanel.backButton) {
			
			TransactionController.transactionPanel.setVisible(false); //Hide the transaction panel
	        DashboardScreen.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //Enable the scroll bar in the scroll pane
			
	        TransactionPanel.invalid.setVisible(false); //Hide the error message
	        
	        //Enable all of the buttons on the dashboard panel
			DashboardScreen.nextDayButton.setEnabled(true);
			DashboardScreen.newTransaction.setEnabled(true);
			DashboardScreen.userButton.setEnabled(true);
			DashboardScreen.logOutButton.setEnabled(true);
			DashboardScreen.graphScreenButton.setEnabled(true);
	        
			//Check to see if the month is January or December to enable the right buttons
	        if (SpendingsPanel.spendingsPanelMonth >= 12) {	
				DashboardScreen.nextMonth.setEnabled(false);
				DashboardScreen.previousMonth.setEnabled(true);
			}
	        
	        else if (SpendingsPanel.spendingsPanelMonth <= 1) {	
				DashboardScreen.previousMonth.setEnabled(false);
				DashboardScreen.nextMonth.setEnabled(true);
	        }
				
			else {
				DashboardScreen.nextMonth.setEnabled(true);
				DashboardScreen.previousMonth.setEnabled(true);
			}
			
		}
		
		//If user presses the user button
		if (event.getSource() == DashboardScreen.userButton) {
			
			//Remove the series for the pie chart
			DashboardScreen.chart.removeSeries("BUDGET");
			DashboardScreen.chart.removeSeries("SPENT");
			
			dashboardScreen.dispose(); //Dispose the dashboard screen
			userScreen = new UserScreen(); //Instantiate the user screen
			userScreen.add(userPanel); //Add the user panel to the user screen
			
			//Set the text fields to the current user's saved values
			UserPanel.budget.setText(String.valueOf((int) money.getUserBudget()));
			UserPanel.budgetDecimal.setText(String.valueOf(money.getUserBudget())
					.substring(String.valueOf(money.getUserBudget()).indexOf (".") + 1));
			UserPanel.balance.setText(String.valueOf((int) money.getUserBalance()));
			UserPanel.balanceDecimal.setText(String.valueOf(money.getUserBalance())
					.substring(String.valueOf(money.getUserBalance()).indexOf (".") + 1));
			
			//Display the first name and last name of the current user
			UserPanel.firstName.setText(user.getName());
			UserPanel.lastName.setText(user.getLastName());
			
			//If the user has income
			if (hasIncome) {
				UserPanel.yesIncome.setSelected(true); //Select the yes income button
				showIncomeUserPanel(true); //Show the income text fields and combo boxes
				
				//Set the combo boxes to the current user's saved values
				UserPanel.monthCombo.setSelectedIndex(money.getUserPayMonth()-1);
				UserPanel.dayCombo.setSelectedIndex(money.getUserPayDay()-1);
				UserPanel.paymentCombo.setSelectedItem(money.getUserPayTime());
				UserPanel.income.setText(String.valueOf((int) money.getUserPay()));
				UserPanel.incomeDecimal.setText(String.valueOf(money.getUserPay())
						.substring(String.valueOf(money.getUserPay()).indexOf (".") + 1));
			}
			
			//Otherwise
			else {
				UserPanel.noIncome.setSelected(true); //Select the no income button
				showIncomeUserPanel(false); //Hide the income objects
			}
		
		}
		
		//If the user selects yes income
		if (event.getSource() ==  UserPanel.yesIncome) {
			showIncomeUserPanel(true); //Display the income objects
		}
		
		//If the user selects no income
		if (event.getSource() ==  UserPanel.noIncome) {
			showIncomeUserPanel(false); //Hide the income objects
		}
		
		//If the user presses the confirm button
		if (event.getSource() == UserPanel.confirm) {
			updateUserValues(); //Overwrite the existing values with the new ones
		}
		
		//If the user presses the back button in the user panel
		if (event.getSource() == UserPanel.backButton) {
			
			//Hide the error messages
			UserPanel.invalid.setVisible(false);
			UserPanel.saved.setVisible(false);
			
			userScreen.dispose(); //Dispose the user screen
			dashboardScreen = new DashboardScreen(); //Instantiate the dashboard screen
			DashboardScreen.totalMonthLabel.setText("Total Month Expense: $" + SpendingsPanel.df.format(Money.monthTotal[SpendingsPanel.spendingsPanelMonth])); //Update the current month expense
			pieChartValues(money.getUserBudget(), Money.monthTotal[SpendingsPanel.spendingsPanelMonth]); //Update the pie chart
			
		}
		
		//If the user selects the back button in the graph screen
		if (event.getSource() == GraphScreen.backButton) {
			GraphController.graphScreen.dispose(); //Dispose graph screen
			
			GraphScreen.chart.removeSeries("Category");
			GraphScreen.chart1.removeSeries("Monthly");
			GraphScreen.chart2.removeSeries("Daily");
			dashboardScreen = new DashboardScreen();
			DashboardScreen.totalMonthLabel.setText("Total Month Expense: $" + SpendingsPanel.df.format(Money.monthTotal[SpendingsPanel.spendingsPanelMonth]));
			pieChartValues(money.getUserBudget(), Money.monthTotal[SpendingsPanel.spendingsPanelMonth]);
			
		}
		
	}
	
	//This method validates the user login information and checks if it matches any of the existing account credentials
	//Taken from my login code from SDP #2 - Added new code
	private void checkValidID() {
		
		//Goes through the file to check if the username and password match
		user.currentAccount(LoginPanel.userNameTextField.getText(),	String.valueOf(LoginPanel.passwordField.getPassword()));
		
		//If there is an existing user that matches, set the current fields to the user's information and send user to dashboard
		if (userFound) {
			loginScreen.dispose(); //Close the login screen
			loadCurrentAccountFile(user.getUsername());
			dashboardScreen = new DashboardScreen(); //Instantiate the dashboard screen
			
			//Update the labels in the dashboard screen
			DashboardScreen.monthLabel.setIcon(new ImageIcon("images/"+ (Month.of(months.getCurrentMonth()).name()) + ".png"));
			DashboardScreen.totalMonthLabel.setText("Total Month Expense: $" + SpendingsPanel.df.format(money.monthTotal[months.getCurrentMonth()]));
			DashboardScreen.dateLabel.setVisible(true);
			DashboardScreen.dateLabel.setText("Current Day: " + Month.of(AppController.months.getCurrentMonth()).toString() + " " + 
					AppController.months.getCurrentDay() + ", " + AppController.months.getCurrentYear());
			
			SpendingsPanel.spendingsPanelMonth = months.getCurrentMonth();
			
			TransactionController.spendingsPanel.showLabels(); //Shows the existing transaction in the scroll pane
			TransactionPanel.monthCombo.setSelectedIndex(AppController.months.getCurrentMonth() - 1); //Changes the month combo box to the user's current date
			TransactionPanel.dayCombo.setSelectedIndex(AppController.months.getCurrentDay() - 1); //Changes the day combo box to the user's current date
			
			pieChartValues(money.getUserBudget(), money.monthTotal[months.getCurrentMonth()]); //Sets the pie chart in the dashboard to the user's budget status in the current month
			
			//Hides login screen error labels
			LoginPanel.invalidID.setVisible(false);
			SignupPanel.fillFields.setVisible(false);
			SignupPanel.passwordLength.setVisible(false);
			
			//Checks if user has not setup their values yet
			if (months.getCurrentMonth() == 0 || money.getUserBudget() == 0) {
				
				//Show the setup panel to the user and hide the rest of the dashboard
				dashboardScreen.add(setupPanel);
				DashboardScreen.layeredPane.setVisible(false);
				DashboardScreen.bg.setVisible(false);
				DashboardScreen.dateLabel.setVisible(false);
				
			}
			
			//Otherwise, update the percentage label with the user's values
			else
				DashboardScreen.setPercentColor();
		}
		
		//Otherwise, if there is a blank space or username and password do not match, display an invalid message
		else if (LoginPanel.userNameTextField.getText().equals("") || LoginPanel.userNameTextField.getText().equals("") || !userFound) {
			LoginPanel.invalidID.setVisible(true);
		}
		
	}
	
	//This method creates a new account when the user attempts to sign up. It creates a separate file for the user when
	//they sign up. Values that will be written are name, last name, username, password, and the the current day
	//Taken from my login code from SDP #2 - Added new code
	private void signUp() {
	
		//Get the text from the text fields and set it to a variable
		String name = SignupPanel.nameTextField.getText();
		String lastName = SignupPanel.lastNameTextField.getText();
		String userName = SignupPanel.userNameTextField.getText();
		String password = String.valueOf(SignupPanel.passwordField.getPassword());
		
		//Check if the username that the user wants to sign up with is taken
		user.currentAccount(userName, password);
		
		//If any of the fields are blank, or there already exists an account with the username. display invalid message
		if (name.equals("") || lastName.equals("") || userName.equals("") || password.equals("") || userExists) {
			SignupPanel.fillFields.setVisible(true);
			SignupPanel.passwordLength.setVisible(false);
		}	
		
		//Restrictions for the password - Users must sign up with a password that is longer than 4 characters
		else if (password.length() < 4) {
			SignupPanel.passwordLength.setVisible(true);
			SignupPanel.fillFields.setVisible(false);
		}
		
		//Otherwise, write and save the user credentials into the account file
		else {
			
			//Write and save the user information
			saveProfile(name,lastName,userName,password,filepath);
			
			//Set the current information for the profile
			user.setName(name);
			user.setLastName(lastName);
			user.setUsername(userName);
			user.setPassword(password);
			
			//Get the real time date and set each variable
			LocalDate localDate = LocalDate.now();
			months.setCurrentYear(localDate.getYear());
			months.setCurrentMonth(localDate.getMonthValue());
			months.setCurrentDay(localDate.getDayOfMonth());
			
			//String value for the current date
			String data = months.getCurrentMonth() + "," + months.getCurrentDay() + "," + months.getCurrentYear() + ",";
			
			//Overwrite the date to the new date
			overwrite(user.getUsername(), data, 1);
			
			//Set user exists to false for next user
			userExists = false;
			
			//Set the invalid message labels to not visible
			SignupPanel.fillFields.setVisible(false);
			SignupPanel.passwordLength.setVisible(false);
			LoginPanel.invalidID.setVisible(false);
			
			//Closes the login screen and sends the user to the dashboard
			loginScreen.dispose(); //Dispose login screen
			dashboardScreen = new DashboardScreen(); //Instantiate dashboard screen
			
			//Show the setup panel and hide the other dashboard objects
			DashboardScreen.layeredPane.setVisible(false);
			DashboardScreen.bg.setVisible(false);
			dashboardScreen.add(setupPanel);
			setupPanel.setVisible(true);
			SetupPanel.yesIncome.setSelected(false);
			SetupPanel.noIncome.setSelected(false);
			
		}
	
	}

	//This method gets the user credentials that they signed up with and writes it into the master account file
	//Take from my login code from SDP #2
	private static void saveProfile(String name, String lastName, String userName, String password, String filepath) {
	
		try {
			
			//Create new writers
			FileWriter fileWriter = new FileWriter(filepath, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			PrintWriter printWriter = new PrintWriter(bufferedWriter);
			
			//Writer the new profile information into the account file
			printWriter.println();
			printWriter.print(name + "," + lastName + "," + userName + "," + password + ",");
			printWriter.flush();
			printWriter.close();
			
			//Display saved message into the csv
			System.out.println("saved");
			
			//Create new profile file for user
			newProfile(name, lastName, userName, password);
			
			userFound = true; //Set user found to true
			
		} catch (Exception E) {
			
		}

	}
	
	//This method creates a new separate account file for the user and writes their own personal data
	private static void newProfile(String name, String lastName, String username, String password) {
		
		try {
			
			File file = new File("accounts/"+ username + ".csv");
			
			//If the user file does not exist, create a new file
			if (!file.exists()) {
				file.createNewFile();
				
				//Set up writers
				FileWriter fileWriter = new FileWriter(file, true);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				PrintWriter printWriter = new PrintWriter(bufferedWriter);
				
				//Write the user credentials into the new file and filler values (to be overwritten when user sets up their values)
				printWriter.println(name + "," + lastName + "," + username + "," + password + ",");
				printWriter.println(0 + "," + 0 + "," + 0 + ","); //date
				printWriter.println(0 + ","); //budget
				printWriter.println(0 + ","); //balance
				printWriter.println(false + ","); //income flag
				printWriter.println(0 + ","); //income
				printWriter.println("null" + "," + 0 + "," + 0 + ","); //payment day
				printWriter.flush();
				printWriter.close();
				
				//Display message
				System.out.println("Created new account profile!");
			}
			
			//Otherwise, display error message
			else
				System.out.println("Account already exists!");
			
		} catch (Exception E) {
			
		}
		
	}
	
	//This method logs the user out and returns all of the screen objects back to default for the next user
	private void loggedOut() {
		
		//Create new login screen
		dashboardScreen.dispose();
		loginScreen = new LoginScreen();
		
		//Add all of the panels back
		loginScreen.add(signupPanel);
		loginScreen.add(loginPanel);
		
		//Set the visibility
        loginScreen.setVisible(true);
        loginPanel.setVisible(true);
        signupPanel.setVisible(false);
		
		//Set all of the text fields to blank
		LoginPanel.userNameTextField.setText("");
		LoginPanel.passwordField.setText("");
		
		SignupPanel.nameTextField.setText("");
		SignupPanel.lastNameTextField.setText("");
		SignupPanel.userNameTextField.setText("");
		SignupPanel.passwordField.setText("");
		
		//Set previous user fields to false
		user.setName("");
		user.setLastName("");
		user.setUsername("");
		user.setPassword("");
		
		//Fill all the transaction labels array to null
		for (int x = 0; x < Months.months.length; x++) {
			Arrays.fill(Months.months[x], null);
		}
		
		//Fill all of the day totals to 0
		for (int x = 0; x < Money.dayTotal.length; x++) {
			Arrays.fill(Money.dayTotal[x], 0);
		}
		
		//Fill each month total to 0
		Arrays.fill(Money.monthTotal, 0);
		
		//Remove the pie chart series
		DashboardScreen.chart.removeSeries("BUDGET");
		DashboardScreen.chart.removeSeries("SPENT");
		
		//Set the scroll pane month buttons to true
		DashboardScreen.nextMonth.setEnabled(true);
		DashboardScreen.previousMonth.setEnabled(true);
		
		//Default the scroll pane month to January
		SpendingsPanel.spendingsPanelMonth = 1;
		
		//Set all of the buttons and text fields to default
		TransactionPanel.expendituresButton.setSelected(false);
		TransactionPanel.incomeButton.setSelected(false);
		TransactionPanel.foodButton.setSelected(false);
		TransactionPanel.clothingButton.setSelected(false);
		TransactionPanel.housingButton.setSelected(false);
		TransactionPanel.transportationButton.setSelected(false);
		TransactionPanel.medicalButton.setSelected(false);
		TransactionPanel.miscButton.setSelected(false);
		TransactionPanel.cost.setText("0");
		TransactionPanel.costDecimal.setText("00");
		TransactionPanel.monthCombo.setSelectedIndex(0);
		TransactionPanel.dayCombo.setSelectedIndex(0);
		
		//Set the cost type and category selected to 0
		TransactionController.costTypeSelected = 0;
		TransactionController.categorySelected = 0;
		
		//Remove the charts from the graph screen
		GraphScreen.chart.removeSeries("Category");
		GraphScreen.chart1.removeSeries("Monthly");
		GraphScreen.chart2.removeSeries("Daily");
		
		//Set all of the buttons and text fields in the set up panel to default
		SetupPanel.balance.setText("0");
		SetupPanel.balanceDecimal.setText("00");
		SetupPanel.budget.setText("0");
		SetupPanel.budgetDecimal.setText("00");
		SetupPanel.income.setText("0");
		SetupPanel.incomeDecimal.setText("00");
		SetupPanel.incomeMessage.setVisible(false);
		SetupPanel.yesIncome.setSelected(false);
		SetupPanel.noIncome.setSelected(false);
		SetupPanel.paymentCombo.setSelectedIndex(0);
		SetupPanel.monthCombo.setSelectedIndex(0);
		SetupPanel.dayCombo.setSelectedIndex(0);
		
		userFound = false; //Set userfound to false for next user
		userExists = false; //Set userExists to false for next user
		hasIncome = null; //Set has income to false for next user
		
	}
	
	//This method reads the user's account file and loads it into the applications variables
	//Taken from my login code from SDP #2 - Added new code
	private void loadCurrentAccountFile(String username) {
		
		boolean loaded = false;
		
		try {
			
			//Read the csv(comma seperated values) file
			Scanner input = new Scanner (new File("accounts/" + username + ".csv"));
			input.useDelimiter(",|\\n");
			
			//Goes through csv file until there is no next input
			while (!loaded) {
				
				//Set the current name and login credentials
				user.setName(input.next());
				user.setLastName(input.next());
				user.setUsername(input.next());
				user.setPassword(input.next());
				
				input.nextLine();
				
				//Set the current date
				months.setCurrentMonth(input.nextInt());
				months.setCurrentDay(input.nextInt());
				months.setCurrentYear(input.nextInt());
				
				input.nextLine();
				
				money.setUserBudget(input.nextDouble()); //Set the user's budget
				
				input.nextLine();
				
				money.setUserBalance(input.nextDouble()); //Set the user's balance
				
				input.nextLine();
				
				hasIncome = input.nextBoolean(); //Set if the user has income
						
				input.nextLine();
				
				money.setUserPay(input.nextDouble()); //Set the user's pay value
				
				input.nextLine();
				
				money.setUserPayTime(input.next()); //Set the time interval in which the user gets paid
				money.setUserPayMonth(input.nextInt()); //Set the month the user gets paid
				money.setUserPayDay(input.nextInt()); //Set the day that the user gets paid
				
				input.nextLine();
				
				//Display the user information into the console
				System.out.println("USER INFORMATION:");
				System.out.printf("Account Credentials: %s, %s, %s, %s\n", user.getName(), user.getLastName(), user.getUsername(), user.getPassword());
				System.out.printf("Current Day: %d-%d-%d\n", months.getCurrentMonth(), months.getCurrentDay(), months.getCurrentYear());
				System.out.printf("Your budget: $%.2f\n", money.getUserBudget());
				System.out.printf("Your balance: $%.2f\n", money.getUserBalance());
				System.out.printf("Job Status: %s\n", hasIncome);
				
				//If the user has income, diplay the income information into the console
				if (hasIncome) {
					
					System.out.printf("Your pay: $%.2f\n", money.getUserPay());
					System.out.printf("Next time to get paid: %s - %d-%d", money.getUserPayTime(), money.getUserPayMonth(), money.getUserPayDay());
					
				}
				
				loaded = true; //An account has been loaded
				
			}
		
			input.close(); //Closes the input
			
			//Fill the category array to default value (0)
			Category.fillArray();
			
			//Load all of the user's transactions and adds it into the cateogry array
			LoadTransactions.loadCurrentAccountTransactions(user.getUsername());
			
			//If the month is January or December, disable the respective buttons
			if (months.getCurrentMonth() == 12) {
				
				if (months.getCurrentDay() == 31)
					DashboardScreen.nextDayButton.setEnabled(false);
				
				DashboardScreen.nextMonth.setEnabled(false);
				
			}
			
			if (months.getCurrentMonth() == 1)
				DashboardScreen.previousMonth.setEnabled(false);
				
			
		} catch(FileNotFoundException error) {
			
			//Display the error to the console
			System.out.println("Sorry file not loading - please check the name/location");
		}
		
	}
	
	//This method overwrites the specific line of data in the user's account file
	//Taken from my login code from SDP #2 - Added new code
	public static void overwrite(String username, String data, int lineNum) {
		
		//Overwrite the location of the user to the new location
		//https://stackoverflow.com/questions/31375972/how-to-replace-a-specific-line-in-a-file-using-java
		try {
			
		    Path path = Paths.get("accounts/" + username + ".csv"); //Set the path to the user's personal account fule
		    List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8); //Create array list for the line that is going to be removed
		    lines.set(lineNum,data); //Set the desired line to overwrite
		    Files.write(path, lines, StandardCharsets.UTF_8); //Overwrite the old location
		
		} catch (Exception E) {
			
			System.out.println("File not found!"); //Display error message
			
		}
		
	}
	
	//This method shows or hides the income options in the setup panel
	private void showIncome(boolean flag) {
		
		SetupPanel.income.setVisible(flag);
		SetupPanel.incomeDecimal.setVisible(flag);
		SetupPanel.incomeMessage.setVisible(flag);
		SetupPanel.paymentCombo.setVisible(flag);
		SetupPanel.monthCombo.setVisible(flag);
		SetupPanel.dayCombo.setVisible(flag);
		hasIncome = flag;
		
		//If the user does not have income, set default values to the income text field
		if (!flag) {
			
			SetupPanel.income.setText("0");
			SetupPanel.incomeDecimal.setText("00");
			
		}
	
	}
	
	//This method shows or hides the income options in the user panel
	private void showIncomeUserPanel(boolean flag) {
		
		UserPanel.income.setVisible(flag);
		UserPanel.incomeDecimal.setVisible(flag);
		UserPanel.incomeMessage.setVisible(flag);
		UserPanel.paymentCombo.setVisible(flag);
		UserPanel.monthCombo.setVisible(flag);
		UserPanel.dayCombo.setVisible(flag);
		hasIncome = flag;
		
		//If the user does not have income, set default values to the income text field
		if (!flag) {
			
			UserPanel.income.setText("0");
			UserPanel.incomeDecimal.setText("00");
			
		}
	
	}
	
	//This method checks the values that the user entered in the setup panel and overwrites the new values into the user's account file
	private void setupValues() {
		
		//If the fields are empty, display error message
		if (SetupPanel.budget.getText().equals("") || SetupPanel.budgetDecimal.getText().equals("") || 
			SetupPanel.balance.getText().equals("") || SetupPanel.balanceDecimal.getText().equals("") || 
			SetupPanel.income.getText().equals("") || SetupPanel.incomeDecimal.getText().equals("") ||
			hasIncome == null) {
					
					SetupPanel.invalid.setVisible(true);
					
		}
		
		//If the values for the budget, balance, or income is equal to 0, display error message
		else if (Integer.parseInt(SetupPanel.budget.getText()) == 0 || Integer.parseInt(SetupPanel.balance.getText()) == 0 ||
				 (hasIncome && Integer.parseInt(SetupPanel.income.getText()) == 0)) {
					
					SetupPanel.invalid.setVisible(true);
					
		}
		
		//Otherwise
		else {
			
			double budget = (Integer.parseInt(SetupPanel.budget.getText()) + ((double) Integer.parseInt(SetupPanel.budgetDecimal.getText()) / 100)); //Get the budget value
			double balance = (Integer.parseInt(SetupPanel.balance.getText()) + ((double) Integer.parseInt(SetupPanel.balanceDecimal.getText()) / 100)); //Get the balance value
			double income = 0.00; //Set the income value to default (0)
			
			//If the user has income
			if (hasIncome) {
				income = (Integer.parseInt(SetupPanel.income.getText()) + ((double) Integer.parseInt(SetupPanel.incomeDecimal.getText()) / 100)); //Get the income value
				String payTime = SetupPanel.paymentCombo.getSelectedItem().toString(); //Get the pay interval
				int payMonth = SetupPanel.monthCombo.getSelectedIndex() + 1; //Get the month that the user gets paid
				int payDay = (int) SetupPanel.dayCombo.getSelectedItem(); //Get the day that the user gets paid
				overwrite(user.getUsername(), payTime + "," + payMonth + "," + payDay + ",", 6); //Overwrite the income values
			}
			
			//Overwrite the current values with the new values in the csv file
			overwrite(user.getUsername(),budget + ",", 2);
			overwrite(user.getUsername(),balance + ",", 3);
			overwrite(user.getUsername(), hasIncome + ",", 4);
			overwrite(user.getUsername(), income + ",", 5);
			
			//Reload the current account file to set the model variables
			loadCurrentAccountFile(user.getUsername());
			
			//Hide the setup panel
			SetupPanel.invalid.setVisible(false);
			setupPanel.setVisible(false);
			
			//Show the dashboard screen
			DashboardScreen.layeredPane.setVisible(true);
			DashboardScreen.bg.setVisible(true);
			
			//Update the current date and month labels
			DashboardScreen.monthLabel.setIcon(new ImageIcon("images/"+ (Month.of(months.getCurrentMonth()).name()) + ".png"));
			DashboardScreen.dateLabel.setVisible(true);
			DashboardScreen.dateLabel.setText("Current Day: " + Month.of(AppController.months.getCurrentMonth()).toString() + " " + 
					AppController.months.getCurrentDay() + ", " + AppController.months.getCurrentYear());
			DashboardScreen.totalMonthLabel.setText("Total Month Expense: $" + SpendingsPanel.df.format(money.monthTotal[months.getCurrentMonth()]));
			
			SpendingsPanel.spendingsPanelMonth = months.getCurrentMonth(); //Set the scroll pane month to the current month
			
			TransactionController.spendingsPanel.showLabels(); //Show the transaction labels for the scroll pane
			
			//Set the date combo boxes in the transaction panel to the current date
			TransactionPanel.monthCombo.setSelectedIndex(AppController.months.getCurrentMonth() - 1);
			TransactionPanel.dayCombo.setSelectedIndex(AppController.months.getCurrentDay() - 1);
			
			pieChartValues(money.getUserBudget(), Money.monthTotal[SpendingsPanel.spendingsPanelMonth]); //Update the pie chart to current month
			
			DashboardScreen.setPercentColor(); //Update the percentage label
			
		}
		
	}
	
	//This method updates the user's information in the user panel
	private void updateUserValues() {
		
		//If fields are empty, display error message
		if (UserPanel.budget.getText().equals("") || UserPanel.budgetDecimal.getText().equals("") || 
				UserPanel.balance.getText().equals("") || UserPanel.balanceDecimal.getText().equals("") || 
				UserPanel.income.getText().equals("") || UserPanel.incomeDecimal.getText().equals("")) {
					
					UserPanel.invalid.setVisible(true);
					
				}
		
		//If budget, balance, or income equals 0, display error message
		else if (Integer.parseInt(UserPanel.budget.getText()) == 0 || Integer.parseInt(UserPanel.balance.getText()) == 0 ||
				 (hasIncome && Integer.parseInt(UserPanel.income.getText()) == 0)) {
					
					UserPanel.invalid.setVisible(true);
					
				}
		
		else {
			
			double budget = (Integer.parseInt(UserPanel.budget.getText()) + ((double) Integer.parseInt(UserPanel.budgetDecimal.getText()) / 100)); //Get budget value
			double balance = (Integer.parseInt(UserPanel.balance.getText()) + ((double) Integer.parseInt(UserPanel.balanceDecimal.getText()) / 100)); //Get balance value
			double income = 0.00; //Set income to default (0)
			
			//If user has income
			if (hasIncome) {
				income = (Integer.parseInt(UserPanel.income.getText()) + ((double) Integer.parseInt(UserPanel.incomeDecimal.getText()) / 100)); //Get income value
				String payTime = UserPanel.paymentCombo.getSelectedItem().toString(); //Get pay interval
				int payMonth = UserPanel.monthCombo.getSelectedIndex() + 1; //Get month of pay
				int payDay = (int) UserPanel.dayCombo.getSelectedItem(); //Get day of pay
				overwrite(user.getUsername(), payTime + "," + payMonth + "," + payDay + ",", 6); //Overwrite current income values with new ones
			}
			
			//Overwrite the current values with the new values in the csv file
			overwrite(user.getUsername(),budget + ",", 2);
			overwrite(user.getUsername(),balance + ",", 3);
			overwrite(user.getUsername(), hasIncome + ",", 4);
			overwrite(user.getUsername(), income + ",", 5);
			
			//Display saved message
			UserPanel.invalid.setVisible(false);
			UserPanel.saved.setVisible(true);
			
			//Reload the user file
			loadCurrentAccountFile(user.getUsername());
			
		}
		
	}
	
	//This method sets all of the objects of the transaction panel back to default for the next time
	//Disables dashboard screen buttons to prevent any errors from happening
	private void setupTransactionPanel() {
		
        DashboardScreen.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); //Disable scroll bar in scroll pane
        
		TransactionController.transactionPanel.setVisible(true); //Show the transaction panel
		
		//Set all of the radio buttons, text fields, and combo boxes to default
		TransactionPanel.expendituresButton.setSelected(false);
		TransactionPanel.incomeButton.setSelected(false);
		TransactionPanel.foodButton.setSelected(false);
		TransactionPanel.clothingButton.setSelected(false);
		TransactionPanel.housingButton.setSelected(false);
		TransactionPanel.transportationButton.setSelected(false);
		TransactionPanel.medicalButton.setSelected(false);
		TransactionPanel.miscButton.setSelected(false);
		TransactionPanel.cost.setText("0");
		TransactionPanel.costDecimal.setText("00");
		TransactionPanel.monthCombo.setSelectedIndex(months.getCurrentMonth() - 1);
		TransactionPanel.dayCombo.setSelectedIndex(months.getCurrentDay() - 1);
		
		//Set cost type and category selected to 0
		TransactionController.costTypeSelected = 0;
		TransactionController.categorySelected = 0;
		
		//Disable dashboard screen buttons
		DashboardScreen.nextDayButton.setEnabled(false);
		DashboardScreen.newTransaction.setEnabled(false);
		DashboardScreen.userButton.setEnabled(false);
		DashboardScreen.logOutButton.setEnabled(false);
		DashboardScreen.graphScreenButton.setEnabled(false);
		
		DashboardScreen.nextMonth.setEnabled(false);
		DashboardScreen.previousMonth.setEnabled(false);
		
	}
	
	//This method sets the pie chart values
	public static void pieChartValues(double monthlyBudget, double totalMonthSpent) {
		
		//If the total money spent is less than 0
		if (monthlyBudget + totalMonthSpent > monthlyBudget) {
			DashboardScreen.chart.updatePieSeries("BUDGET", 100); //Set budget to 100
			DashboardScreen.chart.updatePieSeries("SPENT", 0); //Set spent to 0
		}
		
		//If the total money spent is more than budget
		else if (totalMonthSpent * -1 > monthlyBudget) {
			DashboardScreen.chart.updatePieSeries("BUDGET", 0); //Set budget to 0
			DashboardScreen.chart.updatePieSeries("SPENT", 100); //Set spent to 100
		}
		
		//Otherwise, set the budget and spent ratio to its respective values
		else {
			DashboardScreen.chart.updatePieSeries("BUDGET", monthlyBudget + totalMonthSpent);
			DashboardScreen.chart.updatePieSeries("SPENT", totalMonthSpent * -1);
		}
		
		//Repaint and revalidate the pie chart
		DashboardScreen.piePanel.repaint();
		DashboardScreen.piePanel.revalidate();
		
	}

}
