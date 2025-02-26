	package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Month;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;

import model.Category;
import model.Money;
import view.dashboard.DashboardScreen;
import view.dashboard.SpendingsPanel;
import view.dashboard.TransactionPanel;

/*
 * This class is responsible for handling all of the login for the transactions. This
 * includes writing new transactions into the user file, determining which values to
 * write, as well as adding the loaded transactions into arrays.
 */
public class TransactionController implements ActionListener {

	//Fields
	public static int costTypeSelected = 0; //The cost type selected (Income or expense)
	public static int categorySelected = 0; //The category of the current transaction
	
	public static SpendingsPanel spendingsPanel = new SpendingsPanel(); //Instantiate spendings panel
	public static TransactionPanel transactionPanel = new TransactionPanel(); //Instantiate transaction panel
	
	public TransactionController() {
		
		addActionListener(); //Add action listener to objects
		
	}
	
	@SuppressWarnings("unchecked")
	//This method adds action listeners to the transaction panel objects
	private void addActionListener() {
		
		//Action listeners to buttons
		TransactionPanel.backButton.addActionListener(this);		
		TransactionPanel.save.addActionListener(this);
		
		//Action listeners to cost type radio buttons
		TransactionPanel.expendituresButton.addActionListener(this);
		TransactionPanel.incomeButton.addActionListener(this);
		
		//Action listeners to category type radio buttons
		TransactionPanel.foodButton.addActionListener(this);
		TransactionPanel.clothingButton.addActionListener(this);
		TransactionPanel.housingButton.addActionListener(this);
		TransactionPanel.medicalButton.addActionListener(this);
		TransactionPanel.transportationButton.addActionListener(this);
		TransactionPanel.miscButton.addActionListener(this);
		
		//Key listeners to the cost text fields to limit the inputs to digits
		TransactionPanel.cost.addKeyListener(new KeyAdapter() {
	         public void keyTyped(KeyEvent e) {
	             char c = e.getKeyChar();
	             if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
	                  e.consume();  // if it's not a number, ignore the event
	             }
	             
	         }
	         
	      });
		
		TransactionPanel.costDecimal.addKeyListener(new KeyAdapter() {
	         public void keyTyped(KeyEvent e) {
	             char c = e.getKeyChar();
	             if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
	                  e.consume();  // if it's not a number, ignore the event
	             }
	             
	         }
	         
	      });
		
		//Action listener to month combo box that checks the month selected and updates the day combo box to the number of days in the selected month
		TransactionPanel.monthCombo.addActionListener(e -> {
	        Month selMonth = (Month) ((JComboBox<Month>) e.getSource()).getSelectedItem();
	        TransactionPanel.dayCombo.removeAllItems();
	        for (int x = 1; x <= LocalDate.of(AppController.months.getCurrentYear(), selMonth, 1).lengthOfMonth(); x++)
	        	TransactionPanel.dayCombo.addItem(x);
	    });
		
		//Add action listeners to the next and previous month buttons
		DashboardScreen.previousMonth.addActionListener(this);
		DashboardScreen.nextMonth.addActionListener(this);
		
	}

	@Override
	//This method is the action performed method
	public void actionPerformed(ActionEvent event) {
		
		//Determine the cost type selected
		if (event.getSource() == TransactionPanel.expendituresButton) {
			costTypeSelected = -1;
		}
		
		if (event.getSource() == TransactionPanel.incomeButton) {
			costTypeSelected = 1;
		}
		
		//Determine the category type selected
		if (event.getSource() == TransactionPanel.foodButton) {
			categorySelected = 1;
		}
		
		if (event.getSource() == TransactionPanel.clothingButton) {
			categorySelected = 2;
		}
		
		if (event.getSource() == TransactionPanel.housingButton) {
			categorySelected = 3;
		}
		
		if (event.getSource() == TransactionPanel.transportationButton) {
			categorySelected = 4;
		}
		
		if (event.getSource() == TransactionPanel.medicalButton) {
			categorySelected = 5;
		}
		
		if (event.getSource() == TransactionPanel.miscButton) {	
			categorySelected = 6;	
		}
		
		//If the user presses the save button
		if (event.getSource() == TransactionPanel.save) {
			saveTransaction(); //Save the new transaction
		}
		
		//If the user presses the next month button
		if (event.getSource() == DashboardScreen.nextMonth) {
			
			SpendingsPanel.spendingsPanelMonth++; //Increment the current month displayed on the scroll pane
			
			//If the month is December (lest month)
			if (SpendingsPanel.spendingsPanelMonth >= 12) {
				DashboardScreen.nextMonth.setEnabled(false); //Disable the button
			}
			
			//Otherwise, keep both buttons enabled
			else {
				DashboardScreen.nextMonth.setEnabled(true);
				DashboardScreen.previousMonth.setEnabled(true);
			}
			
			//Update the dashboard screen labels to the current month
			DashboardScreen.setPercentColor();
			DashboardScreen.monthLabel.setIcon(new ImageIcon("images/"+ (Month.of(SpendingsPanel.spendingsPanelMonth).name()) + ".png"));
			DashboardScreen.totalMonthLabel.setText("Total Month Expense: $" + SpendingsPanel.df.format(Money.monthTotal[SpendingsPanel.spendingsPanelMonth]));
			
			AppController.pieChartValues(AppController.money.getUserBudget(), Money.monthTotal[SpendingsPanel.spendingsPanelMonth]); //Update the pie chart to the current month
			
			spendingsPanel.removeAll(); //Remove all of the previous transaction labels in the scroll panel
			spendingsPanel.showLabels(); //Show the transaction labels of the current month
			
			//De-select all of the transaction panel radio buttons
			TransactionPanel.expendituresButton.setSelected(false);
			TransactionPanel.incomeButton.setSelected(false);
			TransactionPanel.foodButton.setSelected(false);
			TransactionPanel.clothingButton.setSelected(false);
			TransactionPanel.housingButton.setSelected(false);
			TransactionPanel.transportationButton.setSelected(false);
			TransactionPanel.medicalButton.setSelected(false);
			TransactionPanel.miscButton.setSelected(false);
			
		}
		
		//If the user presses the previous month button
		if (event.getSource() == DashboardScreen.previousMonth) {
			
			SpendingsPanel.spendingsPanelMonth--; //Decrement the current month displayed on the scroll pane
			
			//If the month is January (first month)
			if (SpendingsPanel.spendingsPanelMonth <= 1) {
				DashboardScreen.previousMonth.setEnabled(false); //Disable the button
			}
			
			//Otherwise, keep both buttons enabled
			else {
				DashboardScreen.previousMonth.setEnabled(true);
				DashboardScreen.nextMonth.setEnabled(true);
			}
			
			//Update the dashboard screen labels to the current month
			DashboardScreen.setPercentColor();
			DashboardScreen.monthLabel.setIcon(new ImageIcon("images/"+ (Month.of(SpendingsPanel.spendingsPanelMonth).name()) + ".png"));
			DashboardScreen.totalMonthLabel.setText("Total Month Expense: $" + SpendingsPanel.df.format(Money.monthTotal[SpendingsPanel.spendingsPanelMonth]));
			
			AppController.pieChartValues(AppController.money.getUserBudget(), Money.monthTotal[SpendingsPanel.spendingsPanelMonth]); //Update the pie chart to the current month
			
			spendingsPanel.removeAll(); //Remove all of the previous transaction labels in the scroll panel
			spendingsPanel.showLabels(); //Show the transaction labels of the current month
			
		}
		
	}
	
	//This mehtod saves the transaction into the account file and current session arrays
	private void saveTransaction() {

		//If the text field is empty, display error message
		if (TransactionPanel.cost.getText().equals("") || TransactionPanel.costDecimal.getText().equals("") ||
				costTypeSelected == 0 || categorySelected == 0) {	
			TransactionPanel.invalid.setVisible(true);		
		}
		
		//If the cost is less than 0, display error message
		else if (Integer.parseInt(TransactionPanel.cost.getText()) == 0 && Integer.parseInt(TransactionPanel.costDecimal.getText()) == 0) {		
			TransactionPanel.invalid.setVisible(true);			
		}
		
		//Otherwise
		else {
			
			double cost = (Integer.parseInt(TransactionPanel.cost.getText()) + ((double)Integer.parseInt(TransactionPanel.costDecimal.getText()) / 100)); //Get the cost
			
			int monthSpent = TransactionPanel.monthCombo.getSelectedIndex() + 1; //Get the integer value of the month that the transaction was made
			int daySpent = (int) TransactionPanel.dayCombo.getSelectedItem(); //Get the integer value of the day that the transaction was made
			//int recurringType = TransactionPanel.recurringCombo.getSelectedIndex();
			
			TransactionPanel.invalid.setVisible(false); //Hide the error message
			
			writeTransaction(AppController.user.getUsername(), costTypeSelected, categorySelected, monthSpent, daySpent, cost); //Write the transaction details into the user's csv file
			
			addToArray(categorySelected, monthSpent, daySpent, cost * costTypeSelected); //Add the transaction to the respective category array
			
			DashboardScreen.totalMonthLabel.setText("Total Month Expense: $" + SpendingsPanel.df.format(Money.monthTotal[SpendingsPanel.spendingsPanelMonth])); //Update the total month expense label
			DashboardScreen.setPercentColor(); //Update the percentage label
			
			transactionPanel.setVisible(false); //Close the transaction panel
	        DashboardScreen.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //Enable the scroll bar on the scroll pane
	        
	        //Enable all of the buttons on the dashboard
			DashboardScreen.nextDayButton.setEnabled(true);
			DashboardScreen.newTransaction.setEnabled(true);
			DashboardScreen.userButton.setEnabled(true);
			DashboardScreen.logOutButton.setEnabled(true);
			DashboardScreen.graphScreenButton.setEnabled(true);
	        
			//If month is January or December, enable and disable respective buttons
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
		
	}
	
	//This method writes the transaction into the user's account file
	public static void writeTransaction(String username, int costType, int categoryType, int month, int day, double cost) {
		
		try {
				
				//Set up writers
				FileWriter fileWriter = new FileWriter("accounts/" + username + ".csv", true);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				PrintWriter printWriter = new PrintWriter(bufferedWriter);
				
				//Write the user credentials into the new file
				printWriter.println(categoryType + "," + month + "," + day + "," + (cost * costType) + ",");
				printWriter.flush();
				printWriter.close();
				
				//Display message
				System.out.println("Saved transaction!");
		
		} catch (Exception E) {
		
		}
		
	}
	
	//This method adds the cost of the new transaction into its correct category, month, and day
	public static void addToArray (int categoryType, int month, int day, double cost) {
		
		//Depending on which category type the transaction is, add the cost to its array
		switch(categoryType) {
		
			case 1:
				Category.food[month][day] += cost;
				System.out.println(Category.food[month][day]);
				break;
				
			case 2:
				Category.clothing[month][day] += cost;
				System.out.println(Category.clothing[month][day]);
				break;
				
			case 3:
				Category.housing[month][day] += cost;
				System.out.println(Category.housing[month][day]);
				break;
				
			case 4:
				Category.transportation[month][day] += cost;
				System.out.println(Category.transportation[month][day]);
				break;
				
			case 5:
				Category.medical[month][day] += cost;
				System.out.println(Category.medical[month][day]);
				break;
				
			case 6:
				Category.misc[month][day] += cost;
				System.out.println(Category.misc[month][day]);
				break;
				
			case 7:
				Category.pay[month][day] += cost;
				System.out.println(Category.pay[month][day]);
				break;
				
		}
		
		//Add the cost to the day and month total
		Money.dayTotal[month][day] += cost;
		Money.monthTotal[month] += cost;
		
		spendingsPanel.removeAll(); //Remove all of the transaction labels for that month
		spendingsPanel.showLabels(); //Add the updated transactions
		
		if (SpendingsPanel.spendingsPanelMonth == month)
			AppController.pieChartValues(AppController.money.getUserBudget(), Money.monthTotal[month]); //Update the pie chart
		
		DashboardScreen.setPercentColor(); //Update the percentage label
		
	}

}
