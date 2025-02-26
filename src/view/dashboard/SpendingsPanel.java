package view.dashboard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DecimalFormat;
import java.time.Month;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Category;
import model.Money;
import model.Months;

/*
 * This class is the panel that displays all of the user's transactions. The panel is display through
 * the months of the year and the user is able to move through each of the months to look at transactions.
 */
@SuppressWarnings("serial")
public class SpendingsPanel extends JPanel {
	
	//Fields
	//2D JLabel array that contains the labels for each category
	//These labels will used to keep track of the daily spendings for each day
	//Added onto the scroll pane displaying the transaction
	private JLabel[][] foodLabel = new JLabel[13][32];
	private JLabel[][] clothingLabel = new JLabel[13][32];
	private JLabel[][] housingLabel = new JLabel[13][32];
	private JLabel[][] transportationLabel = new JLabel[13][32];
	private JLabel[][] medicalLabel = new JLabel[13][32];
	private JLabel[][] miscLabel = new JLabel[13][32];
	private JLabel[][] payLabel = new JLabel[13][32];
	
	private JLabel[][] totalDaySpending = new JLabel[13][32]; //Total day spending
	
	public static final DecimalFormat df = new DecimalFormat("0.00"); //Decimal format that formats double values to the nearest hundredth
	public static int spendingsPanelMonth; //The current month that the spendings panel is on
	
	//Constructor method
	public SpendingsPanel() {
		
		//Set boxlayout for panel
		setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		setVisible(true);
		
		
	}
	
	//This method updates the labels of the specified month
	public void showLabels() {
		
		removeAll(); //Remove all current labels on the panel
		
		//Adds the transaction labels for each day
		for (int i = 1; i < Months.months[spendingsPanelMonth].length; i++) {
			addLabels(spendingsPanelMonth,i);
		}
		
		//Repaint and revalidate the panel
		repaint();
		revalidate();
		
	}
	
	//This method adds a transaction label by category for each day that contains a transaction
	public void addLabels(int monthNum, int dayNum) {
		
		//If the day has a transaction
		if (Category.food[monthNum][dayNum] != 0 || Category.clothing[monthNum][dayNum] != 0 || Category.housing[monthNum][dayNum] != 0
			|| Category.transportation[monthNum][dayNum] != 0 || Category.medical[monthNum][dayNum] != 0  || Category.misc[monthNum][dayNum] != 0
			|| Category.pay[monthNum][dayNum] != 0) {
			
			//Add the date of the transaction onto the panel
			add(Box.createRigidArea(new Dimension(0, 20))); //Add spacing in between days
			Months.months[monthNum][dayNum] = new JLabel(); //Create a new label for the day
			Months.months[monthNum][dayNum].setPreferredSize(new Dimension(610,100)); //Set size of label
			Months.months[monthNum][dayNum].setText(Month.of(monthNum).name() + " " + dayNum); //Set the text to the current day
			Months.months[monthNum][dayNum].setFont(new Font("Arial", Font.BOLD, 30)); //Set font of label
			Months.months[monthNum][dayNum].setForeground(Color.RED); //Set the color of the label
			
			add(Months.months[monthNum][dayNum]); //Add the date label to the panel
			
			//Checks which category(s) have a transaction
			//If the category has a transaction for that day, create a new label
			//Set the text to display the amount spent for that category that day
			//Add the label onto the panel
			if (Category.food[monthNum][dayNum] != 0) {
				
				foodLabel[monthNum][dayNum] = new JLabel();
				foodLabel[monthNum][dayNum].setPreferredSize(new Dimension(610,100));
				foodLabel[monthNum][dayNum].setText("Food: $" + String.valueOf(df.format(Category.food[monthNum][dayNum])));
				foodLabel[monthNum][dayNum].setFont(new Font("Arial", Font.PLAIN, 20));
				add(foodLabel[monthNum][dayNum]);
				
			}
			
			if (Category.clothing[monthNum][dayNum] != 0) {
				
				clothingLabel[monthNum][dayNum] = new JLabel();
				clothingLabel[monthNum][dayNum].setPreferredSize(new Dimension(610,100));
				clothingLabel[monthNum][dayNum].setText("Clothing: $" + String.valueOf(df.format(Category.clothing[monthNum][dayNum])));
				clothingLabel[monthNum][dayNum].setFont(new Font("Arial", Font.PLAIN, 20));
				add(clothingLabel[monthNum][dayNum]);
				
			}
			
			if (Category.housing[monthNum][dayNum] != 0) {
				
				housingLabel[monthNum][dayNum] = new JLabel();
				housingLabel[monthNum][dayNum].setPreferredSize(new Dimension(610,80));
				housingLabel[monthNum][dayNum].setText("Housing: $" + String.valueOf(df.format(Category.housing[monthNum][dayNum])));
				housingLabel[monthNum][dayNum].setFont(new Font("Arial", Font.PLAIN, 20));
				add(housingLabel[monthNum][dayNum]);
				
			}
			
			if (Category.transportation[monthNum][dayNum] != 0) {
				
				transportationLabel[monthNum][dayNum] = new JLabel();
				transportationLabel[monthNum][dayNum].setPreferredSize(new Dimension(610,80));
				transportationLabel[monthNum][dayNum].setText("Transportation: $" + String.valueOf(df.format(Category.transportation[monthNum][dayNum])));
				transportationLabel[monthNum][dayNum].setFont(new Font("Arial", Font.PLAIN, 20));
				add(transportationLabel[monthNum][dayNum]);
				
			}
			
			if (Category.medical[monthNum][dayNum] != 0) {
				
				medicalLabel[monthNum][dayNum] = new JLabel();
				medicalLabel[monthNum][dayNum].setPreferredSize(new Dimension(610,80));
				medicalLabel[monthNum][dayNum].setText("Medical: $" + String.valueOf(df.format(Category.medical[monthNum][dayNum])));
				medicalLabel[monthNum][dayNum].setFont(new Font("Arial", Font.PLAIN, 20));
				add(medicalLabel[monthNum][dayNum]);
				
			}
			
			if (Category.misc[monthNum][dayNum] != 0) {
				
				miscLabel[monthNum][dayNum] = new JLabel();
				miscLabel[monthNum][dayNum].setPreferredSize(new Dimension(610,80));
				miscLabel[monthNum][dayNum].setText("Miscellaneous: $" + String.valueOf(df.format(Category.misc[monthNum][dayNum])));
				miscLabel[monthNum][dayNum].setFont(new Font("Arial", Font.PLAIN, 20));
				add(miscLabel[monthNum][dayNum]);
				
			}
			
			if (Category.pay[monthNum][dayNum] != 0) {
				
				payLabel[monthNum][dayNum] = new JLabel();
				payLabel[monthNum][dayNum].setPreferredSize(new Dimension(610,80));
				payLabel[monthNum][dayNum].setText("Job Income: $" + String.valueOf(df.format(Category.pay[monthNum][dayNum])));
				payLabel[monthNum][dayNum].setFont(new Font("Arial", Font.PLAIN, 20));
				payLabel[monthNum][dayNum].setForeground(Color.green);
				add(payLabel[monthNum][dayNum]);
				
			}
			
			//Display the total spendings for that day
			totalDaySpending[monthNum][dayNum] = new JLabel();
			totalDaySpending[monthNum][dayNum].setPreferredSize(new Dimension(610,80));
			totalDaySpending[monthNum][dayNum].setText("Total Expense: $" + df.format(Money.dayTotal[monthNum][dayNum]));
			totalDaySpending[monthNum][dayNum].setFont(new Font("Arial", Font.BOLD, 20));
			
			add(totalDaySpending[monthNum][dayNum]); //Add the total day spending label onto the panel
			
		}
		
	}

}
