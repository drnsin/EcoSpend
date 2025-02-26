package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

import javax.swing.JComboBox;

import view.dashboard.DashboardScreen;
import view.graph.GraphScreen;
import model.Category;
import model.Money;

/*
 * This class houses the main logic for the graphs that are displayed within the graph
 * screen. It utilizes the XChart library by Knowm to create and display the user's
 * spendings information on various graphs.
 */
public class GraphController implements ActionListener {
	
	//Fields
	public static GraphScreen graphScreen; //Graph screen
	private int currentMonth; //The current month
	
	//Constructor
	public GraphController() {
		
		addActionListeners(); //Add the action listeners
		
	}
	
	//This methods adds the action listeners to the objects on the graph screen
	private void addActionListeners() {
	
		DashboardScreen.graphScreenButton.addActionListener(this); //Add action listener to the graph screen button on the dashboard
		
		//Add action listener to the month combo box. The action listener will change the category spendings whenever the user selects a different month
		//https://stackoverflow.com/questions/45718098/how-to-get-the-month-from-a-combobox-and-make-it-a-month-in-a-date?noredirect=1&lq=1
		GraphScreen.monthCombo.addActionListener(e -> {
	        @SuppressWarnings("unchecked")
			Month selMonth = (Month) ((JComboBox<Month>) e.getSource()).getSelectedItem();
	        GraphScreen.chart.setTitle("Category Spendings in: " + Month.of(selMonth.getValue()).getDisplayName(TextStyle.FULL_STANDALONE, Locale.CANADA)); //Rename the title of the chart to the current month
	        
	        //Get the monthly category spending from each category for the selected month
			double foodSeries = monthlyCategorySpending(Category.food, Category.food[selMonth.getValue()].length - 1, selMonth.getValue());
			double clothingSeries = monthlyCategorySpending(Category.clothing, Category.clothing[selMonth.getValue()].length - 1, selMonth.getValue());
			double housingSeries = monthlyCategorySpending(Category.housing, Category.housing[selMonth.getValue()].length - 1, selMonth.getValue());
			double transportationSeries = monthlyCategorySpending(Category.transportation, Category.transportation[selMonth.getValue()].length - 1, selMonth.getValue());
			double medicalSeries = monthlyCategorySpending(Category.medical, Category.medical[selMonth.getValue()].length - 1, selMonth.getValue());
			double miscSeries = monthlyCategorySpending(Category.misc, Category.misc[selMonth.getValue()].length - 1, selMonth.getValue());
			
			//Remove the current series
			GraphScreen.chart.removeSeries("Category");
			
			//Add the updated series to the chart with the new monthly values
			GraphScreen.chart.addSeries("Category", Arrays.asList(new String[] { "Food", "Clothing", "Housing", "Transportation", "Medical", "Misc."}), 
		    		Arrays.asList(new Double[] { foodSeries, clothingSeries, housingSeries, transportationSeries, medicalSeries, miscSeries }));
			
			//Repaint the panel
			GraphScreen.barPanel.repaint();
			
		});
		
		//Add action listener to the month combo box. The action listener will change the category spendings whenever the user selects a different month
		//https://stackoverflow.com/questions/45718098/how-to-get-the-month-from-a-combobox-and-make-it-a-month-in-a-date?noredirect=1&lq=1
		GraphScreen.monthCombo2.addActionListener(e -> {
	        @SuppressWarnings("unchecked")
			Month selMonth = (Month) ((JComboBox<Month>) e.getSource()).getSelectedItem();
	        
			GraphScreen.chart2.setTitle("Daily Spendings in: " + Month.of(selMonth.getValue()).getDisplayName(TextStyle.FULL_STANDALONE, Locale.CANADA)); //Rename the title of the chart of the current month
	        
	        YearMonth ym = YearMonth.of(2021, selMonth.getValue()); //Get the number of days in the selected month
	        
	        int daysInMonth = ym.lengthOfMonth() + 1; //Set the number of days in the month
	        
	        double[] daysArr = new double[daysInMonth]; //Create a double array for the month. This array will be the data set for the x-axis labels of the graph
			
	        //Add the day value into its respective element
	        for (int x = 1; x <= LocalDate.of(AppController.months.getCurrentYear(), selMonth, 1).lengthOfMonth(); x++)
	        	daysArr[x] = x;
	        
			GraphScreen.chart2.removeSeries("Daily"); //Remove the series
			
			double[] spentArr = new double[daysInMonth]; //Create new array that contains the money spent in each day
			
			//Set each day of the array to the respective day total
			for (int x = 1; x < daysArr.length; x++) {
				spentArr[x] = Money.dayTotal[selMonth.getValue()][x];
			}
			
			GraphScreen.chart2.removeSeries("Daily Spendings"); //Remove the current series
			
			GraphScreen.chart2.addSeries("Daily Spendings", daysArr, spentArr); //Add the updated series
			
			GraphScreen.dailyPanel.repaint(); //Repaint the panel
			
		});
		
	}

	@Override
	//This method is the action performed method
	public void actionPerformed(ActionEvent event) {

		//If the graph screen button is pressed
		if (event.getSource() == DashboardScreen.graphScreenButton) {
			
			currentMonth = AppController.months.getCurrentMonth(); //Set the current month
			setupGraphScreenValues(); //Set up the graphs in graph screen and instantiate it
		
		}
		
	}
	
	//This method sets up the values of each graph in the graph screen with the the user's transaction history and opens up the graph screen
	private void setupGraphScreenValues() {
		
		//Remove the pie chart series
		DashboardScreen.chart.removeSeries("BUDGET");
		DashboardScreen.chart.removeSeries("SPENT");
		
		AppController.dashboardScreen.dispose(); //Dispose the dashboard screen
		graphScreen = new GraphScreen(); //Instantiate the graph screen
		
		GraphScreen.monthCombo.setSelectedIndex(currentMonth - 1); //Set the selected item of the combo box to the current month
		
		//Get the monthly spending for the current month for each category
		double foodSeries = monthlyCategorySpending(Category.food, Category.food[currentMonth].length - 1, currentMonth);
		double clothingSeries = monthlyCategorySpending(Category.clothing, Category.clothing[currentMonth].length - 1, currentMonth);
		double housingSeries = monthlyCategorySpending(Category.housing, Category.housing[currentMonth].length - 1, currentMonth);
		double transportationSeries = monthlyCategorySpending(Category.transportation, Category.transportation[currentMonth].length - 1, currentMonth);
		double medicalSeries = monthlyCategorySpending(Category.medical, Category.medical[currentMonth].length - 1, currentMonth);
		double miscSeries = monthlyCategorySpending(Category.misc, Category.misc[currentMonth].length - 1, currentMonth);
		
		GraphScreen.chart.removeSeries("Category"); //Remove the series
		
		//Add the updated series
		GraphScreen.chart.addSeries("Category", Arrays.asList(new String[] { "Food", "Clothing", "Housing", "Transportation", "Medical", "Misc."}), 
	    		Arrays.asList(new Double[] { foodSeries, clothingSeries, housingSeries, transportationSeries, medicalSeries, miscSeries }));
		
		GraphScreen.chart.setTitle("Category Spendings in: " + Month.of(currentMonth).getDisplayName(TextStyle.FULL_STANDALONE, Locale.CANADA)); //Set the title of the chart to the current month
		
		GraphScreen.monthCombo2.setSelectedIndex(currentMonth - 1); //Set the selected item of the combo box to the current month
		
		YearMonth ym = YearMonth.of(2021, currentMonth); //Get the number of days in the current month
	        
		int daysInMonth = ym.lengthOfMonth() + 1; //Set the number of days
		
		double[] daysArr = new double[daysInMonth]; //Create an array to hold the number of days in the respective element for the x-axis labels
		
		//Add the day number in the respective index
		for (int x = 1; x <= LocalDate.of(AppController.months.getCurrentYear(), currentMonth, 1).lengthOfMonth(); x++)
			daysArr[x] = x;
		
		GraphScreen.chart2.removeSeries("Daily"); //Remove the series
		
		double[] spentArr = new double[daysInMonth]; //Create an array to hold the day total for each day
		
		//Set each index in the array to the respective day total
		for (int x = 1; x < daysArr.length; x++) {
			
			spentArr[x] = Money.dayTotal[currentMonth][x];
			
		}
		
		GraphScreen.chart2.removeSeries("Daily Spendings"); //Remove series
		
		GraphScreen.chart2.addSeries("Daily Spendings", daysArr, spentArr); //Add updated series
		
		GraphScreen.chart2.setTitle("Daily Spendings in: " + Month.of(currentMonth).getDisplayName(TextStyle.FULL_STANDALONE, Locale.CANADA)); //Set the title of the graph to the current month
		
		GraphScreen.dailyPanel.repaint(); //Repaint the panel
		
	}
	
	//This method recursive adds up the total monthly spending of the specified category
	public static double monthlyCategorySpending(double category[][], int n, int month) {
		
		//Base case
		if (n <= 0)
			return 0;
		
		return category[month][n] + monthlyCategorySpending(category,n-1,month);
		
	}
	
}
