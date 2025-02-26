package model;

import javax.swing.JLabel;

/*
 * This model class contains the current date of the user, as well as the field that contains all of the
 * labels for the transactions.
 */
public class Months {
	
	//Fields
	public static JLabel[][] months = new JLabel[13][32]; //JLabel array that contains the labels for the transactions for each day of the year

	private int currentYear; //The current year
	private int currentMonth; //The current month
	private int currentDay; //The current day
	
	//Setter and getters
	public int getCurrentYear() {
		return currentYear;
	}
	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}
	public int getCurrentMonth() {
		return currentMonth;
	}
	public void setCurrentMonth(int currentMonth) {
		this.currentMonth = currentMonth;
	}
	public int getCurrentDay() {
		return currentDay;
	}
	public void setCurrentDay(int currentDay) {
		this.currentDay = currentDay;
	}
	
	//This method checks if the next day goes into the next month
	public void nextMonth(int month, int day) {
		
		//If the month is one of the months that contain 31 days
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			
			//If the day is 31 and the month is not 12
			if (day == 31 && month != 12) {
				setCurrentMonth(++month); //Preincrement the month
				setCurrentDay(1); //Set the current day to 1
			}
			
			//If the day is the last day of the year
			else if (month == 12 && day == 31) {
				
				setCurrentYear(getCurrentYear() + 1);
				setCurrentMonth(1); //Set the current month to January
				setCurrentDay(1); //Set the current day to 1
				
			}

			//Otherwise
			else
				setCurrentDay(++day); //Preincrement the day
			
		}
		
		//If the month is one of the months that contain 30 days
		else if (month == 4 || month == 6 || month == 9 || month == 11) {

			//If the day is 30
			if (day == 30) {
				setCurrentMonth(++month); //Preincrement the month
				setCurrentDay(1); //Set the current day to 1
			}
			
			//Otherwise
			else
				setCurrentDay(++day); //Preincrement the day
			
		}
		
		//If the month is February
		else if (month == 2) {
			
			//Last day of the month
			if (day == 28) {
				setCurrentMonth(++month); //Preincrement the month
				setCurrentDay(1); //Set the current day to 1
			}
			
			//Otherwise
			else
				setCurrentDay(++day); //Preincrement the day
		
		}
			
	}
	
}
