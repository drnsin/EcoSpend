package application;

import controller.AppController;

import controller.GraphController;
import controller.TransactionController;

/*    Project Header
 * Authors: Darren Sin
 * Date: 01/18/2022
 * Course Code: ICS4U1-01
 * Teacher: Mr. Fernandes
 * Title: EcoSpend
 * Description: This application runs the personal budgeting application (EcoSpend). This application allows the user to sign up
 * for their own account in which they are able to log into and log out of. It stores the user's first name, last name, username,
 * password, as well as their budgeting preferences which they are able to set up after signing up. These preferences include
 * their preferred budget, balance, job status, income, payment date, and payment intervals. Progressing through the account creation/
 * login leads to an interaction menu in which user's can add new transactions that are stored, go to the user screen where they can
 * modify their budgeting preferences, open up the graph screen that contains visualizations of their spendings through various charts,
 * proceed to the next day (replacement for real time tracking), or log out allowing the next user to log in with a fresh new screen.
 * Features:
 *  - Sign up screen (Creates a new file for each user to store their personal information)
 *  - Login screen (Allows the user to log back into their own account and load their information back up)
 *  - Dashboard screen (Main screen of the application that the user can interact with to do the main functions)
 *  - Spendings panel (A scrollable panel that displays all of the user's transaction chronologically by month.
 *  					the user can move through each month to look at all of their added transactions)
 *  - Income (User can set if they have an income, when they will be paid, and the time interval for the next payment
 *  			day i.e. daily, weekly, bi-weekly, monthly. The money will be deposited into their account when it is
 *  			their pay day)
 *  - Pie chart (A pie chart on the main dashboard hows the user how much they have used of their budget. As more transactions
 *  			occur, the red ring will circle the chart. The percentage of the budget used is displayed on top of the chart
 *  			that details how much is used)
 *  - User screen (Able to edit and overwrite previous budgeting preferences)
 *  - Transaction panel (Add transaction by category and date)
 *  - Graph screen (3 graphs that contain monthly, daily, and monthly spending by category)
 * Major Skills: File reading and writing, using algorithms, recursion, object-oriented programming
 * Areas of Concern:  
 * - Did not have the time to fully implement a balance and recurring payment function
 * - The radio buttons on the transaction panel do not reset even after setting the selection to false
 * - The application only tracks up to the end of 2022 and does not go beyond 1 year. User's cannot progress after December 31, 2022
 * - The vertical scroll bar for the spendings panel goes lower than the amount of labels
 * Requirements: XChart Library
 * External Sources:
 * https://stackoverflow.com/questions/20541230/allow-only-numbers-in-jtextfield
 * https://stackoverflow.com/questions/45718098/how-to-get-the-month-from-a-combobox-and-make-it-a-month-in-a-date?noredirect=1&lq=1
 * https://stackoverflow.com/questions/38816578/start-reading-from-a-specific-line-using-scanner 
 * https://stackoverflow.com/questions/30027582/limit-the-number-of-characters-of-a-jtextfield
 * https://github.com/knowm/XChart/issues/267
 * //https://stackoverflow.com/questions/31375972/how-to-replace-a-specific-line-in-a-file-using-java
 */

public class EcoSpend {
	
	public static void main(String[] args) {
		
		new AppController();
		new TransactionController();
		new GraphController();
		
	}

}
