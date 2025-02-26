package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.Category;
import model.Money;
import view.dashboard.DashboardScreen;
import view.dashboard.SpendingsPanel;

/*
 * This class is responsible for reading the user's transaction and storing it into the
 * application's fields. With the use of the scanner, it goes through each line in the
 * user's file to find details of the transaction such as cost, category type, and date of
 * transaction and loads it into fields depending on the category.
 */
public class LoadTransactions {
	
	//This method takes the user's username and locates their file in order to read its transactions
	public static void loadCurrentAccountTransactions(String username) {
		
		try {
			
			//Read the csv(comma seperated values) file
			Scanner input = new Scanner (new File("accounts/" + username + ".csv"));
			input.useDelimiter(",|\\n");
			
			skipLines(input, 6); //Skip the first 6 lines
			
			input.nextLine();
			
			//Goes through csv file until there is no next input
			while (input.hasNextLine() && input.hasNext()) {
				
				int categoryType = input.nextInt(); //Get the category type
				
				int month = input.nextInt(); //Get the month spent
				
				int day = input.nextInt(); //Get the day spent
				
				double cost = input.nextDouble(); //Get the cost of the transaction
				
				//Add the cost to the respective cateogry total
				switch(categoryType) {
				
					case 1:
						Category.food[month][day] += cost;
						break;
						
					case 2:
						Category.clothing[month][day] += cost;
						break;
						
					case 3:
						Category.housing[month][day] += cost;
						break;
						
					case 4:
						Category.transportation[month][day] += cost;
						break;
						
					case 5:
						Category.medical[month][day] += cost;
						break;
						
					case 6:
						Category.misc[month][day] += cost;
						break;
						
					case 7:
						Category.pay[month][day] += cost;
				
				}
				
				//Add the cost to the month and day total
				Money.dayTotal[month][day] += cost;
				Money.monthTotal[month] += cost;
				
				DashboardScreen.totalMonthLabel.setText("Total Month Expense: $" + SpendingsPanel.df.format(Money.monthTotal[AppController.months.getCurrentMonth()])); //Update the total monthly expense labal
				
				input.nextLine();
				
			}	
		
			input.close(); //Close the scanner
			
		} catch(FileNotFoundException error) {
			
			//Display the error to the console
			System.out.println("Sorry file not loading - please check the name/location");
		}
		
	}
	
	//This method skips to the transaction data when reading from the file
	//https://stackoverflow.com/questions/38816578/start-reading-from-a-specific-line-using-scanner
	private static void skipLines(Scanner input, int lineNum) {
		
		for (int i = 0; i < lineNum; i++) {
			
			if(input.hasNextLine())
				input.nextLine();
			
		}
		
	}

}
