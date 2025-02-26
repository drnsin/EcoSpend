package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import controller.AppController;
import controller.TransactionController;
import view.dashboard.DashboardScreen;
import view.dashboard.SpendingsPanel;

/*
 * Model class money contains the user's budget, balance, pay, payment date, and payment interval. It
 * also contains the spending totals for each month and day. This model class will be the main part of
 * the application as it contains the user's budget.
 */
public class Money {
	
	//Fields
	private double userBudget; //The user's budget
	private double userBalance; //The user's balance
	private double userPay; //The user's pay
	private int userPayMonth; //What month the user is next paid
	private int userPayDay; //What day the user is next paid
	private String userPayTime; //The time interval between each pay
	
	public static double[][] dayTotal = new double[13][32]; //Array that has elements for each day in the year. Holds the total spent for each day
	public static double[] monthTotal = new double[13]; //Array for total spending for each month
	
	private double totalSpent; //Total money spent

	//Getter and setters
	public double getUserBudget() {
		return userBudget;
	}

	public void setUserBudget(double userBudget) {
		this.userBudget = userBudget;
	}

	public double getUserBalance() {
		return userBalance;
	}

	public void setUserBalance(double userBalance) {
		this.userBalance = userBalance;
	}

	public double getUserPay() {
		return userPay;
	}

	public void setUserPay(double userPay) {
		this.userPay = userPay;
	}

	public int getUserPayMonth() {
		return userPayMonth;
	}

	public void setUserPayMonth(int userPayMonth) {
		this.userPayMonth = userPayMonth;
	}

	public int getUserPayDay() {
		return userPayDay;
	}

	public void setUserPayDay(int userPayDay) {
		this.userPayDay = userPayDay;
	}

	public String getUserPayTime() {
		return userPayTime;
	}

	public void setUserPayTime(String userPayTime) {
		this.userPayTime = userPayTime;
	}

	public double getTotalSpent() {
		return totalSpent;
	}

	public void setTotalSpent(double totalSpent) {
		this.totalSpent = totalSpent;
	}
	
	//Utility methods
	//This method writes the current date into the user's account file
	public void writeCurrentDate(String username) {
		
		try {
			
			//Set the file to the user's personal account file
			File file = new File(username + ".csv");
			
			FileWriter fileWriter = new FileWriter(file, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			PrintWriter printWriter = new PrintWriter(bufferedWriter);
			
			//Write the location into the file
			printWriter.println();
			printWriter.print("");
			printWriter.println();
			printWriter.flush();
			printWriter.close();
			
			//Display message
			System.out.println("saved date");
			
		} catch (Exception E) {
			
		}
		
	}
	
	//This method checks if the current date is a pay day for the user
	public void checkPayDay() {
		
		//If the current date in is equal to the user's pay date
		if(AppController.months.getCurrentMonth() == getUserPayMonth() && AppController.months.getCurrentDay() == getUserPayDay()) {
			
			System.out.println("IT IS PAY DAY!"); //Display message into the console
			
			TransactionController.writeTransaction(AppController.user.getUsername(), 1, 7, getUserPayMonth(), getUserPayDay(), getUserPay()); //Add the pay transaction into the user's file
			
			TransactionController.addToArray(7, getUserPayMonth(), getUserPayDay(), getUserPay()); //Add the transaction into the pay array
			
			DashboardScreen.totalMonthLabel.setText("Total Month Expense: $" + SpendingsPanel.df.format(Money.monthTotal[SpendingsPanel.spendingsPanelMonth])); //Update the total month expense label
			
			//Determine the next time the user will be paid
			switch(getUserPayTime()) {
			
			//If it is a daily payment, set next pay date to the next day and write into user account file
			case "DAILY":
				nextMonth(getUserPayMonth(),getUserPayDay()); //Check if next day is in the next month
				AppController.overwrite(AppController.user.getUsername(), "DAILY" + "," + getUserPayMonth() + "," + getUserPayDay() + ",", 6);
				break;
			
			//If it is a weekly payment, set the pay date to the next 7th day and write into user account file
			case "WEEKLY":
				for (int i = 0; i < 7; i++) {
					nextMonth(getUserPayMonth(),getUserPayDay()); //Check if the next 7 days are in the next month
				}
				AppController.overwrite(AppController.user.getUsername(), "WEEKLY" + "," + getUserPayMonth() + "," + getUserPayDay() + ",", 6);
				break;
			
			//If it is a bi-weekly payment, set the pay date to the next 14th day and write into user account file
			case "BI-WEEKLY":
				
				for (int i = 0; i < 14; i++) {
					nextMonth(getUserPayMonth(),getUserPayDay()); //Check if the next 14 days are in the next month
				}
				
				AppController.overwrite(AppController.user.getUsername(), "BI-WEEKLY" + "," + getUserPayMonth() + "," + getUserPayDay() + ",", 6);
				break;
				
			//If it is a monthly payment, set the next pay date to 1 month after
			case "MONTHLY":	
				
				//If the next pay month is the next year
				if (getUserPayMonth() + 1 == 13) {
					setUserPayMonth(1);
					AppController.overwrite(AppController.user.getUsername(), "MONTHLY" + "," + getUserPayMonth() + "," + getUserPayDay() + ",", 6);
				}
				
				else {
					setUserPayMonth(getUserPayMonth() + 1);
					AppController.overwrite(AppController.user.getUsername(), "MONTHLY" + "," + getUserPayMonth() + "," + getUserPayDay() + ",", 6);
					
				}
				break;
			
			//UNIMPLEMENTED
			case "ANNUALLY":
				break;
				
			}
			
		}
		
	}
	
	//This method checks if the next day goes into the month
	private void nextMonth(int month, int day) {
		
		//If the month is one of the months that contain 31 days
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			
			//If the day is 31 and the month is not 12
			if (day == 31 && month != 12) {
				setUserPayMonth(++month); //Preincrement the month
				setUserPayDay(1); //Set the pay day to 1
			}
			
			//If the day is the last day of the year
			else if (month == 12 && day == 31) {
				setUserPayMonth(1); //Set month to January
				setUserPayDay(1); //Set day to 1
			}
			
			//Otherwise
			else
				setUserPayDay(++day); //Preincrement the day
			
		}
		
		//If the month is one of the months that contain 30 days
		else if (month == 4 || month == 6 || month == 9 || month == 11) {
			
			//If the day is 30
			if (day == 30) {
				setUserPayMonth(++month); //Preincrement the month
				setUserPayDay(1); //Set the pay day to 1
			}
			
			//Otherwise
			else
				setUserPayDay(++day); //Preincrement the day
			
		}
		
		//If the month is February
		else if (month == 2) {
			
			//Last day of the month
			if (day == 28) {
				setUserPayMonth(++month); //Preincrement the month
				setUserPayDay(1); //Set the pay day to 1
			}
			
			//Otherwise
			else
				setUserPayDay(++day); //Preincrement the day
		
		}
			
	}

}
