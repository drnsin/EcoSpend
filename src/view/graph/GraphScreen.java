package view.graph;

import java.awt.Color;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;

import model.Money;

/*
 * This class is the screen that contains all of the graphs that display the user's spending information.
 * There are 2 line graphs that display monthly and daily spending, and a bar graph that displays monthly
 * spending by category.
 */
@SuppressWarnings("serial")
public class GraphScreen extends JFrame {
	
	//Fields
	//Combo boxes
	public static JComboBox<Month> monthCombo = new JComboBox<>(Month.values()); //Combo box for category spending chart
	public static JComboBox<Month> monthCombo2 = new JComboBox<>(Month.values()); //Combo box for the daily spending chart
	
	//Labels
	public static JPanel barPanel, monthlyPanel, dailyPanel;
	
	//Bar graphs
	public static CategoryChart chart = new CategoryChartBuilder().width(600).height(600).title("Category Spending").xAxisTitle("Category").yAxisTitle("Expenses ($)").build();
	
	//Line graphs
	public static XYChart chart1 = new XYChartBuilder().width(800).height(600).title("Monthly Spending").xAxisTitle("Months").yAxisTitle("Expenses ($)").build();
	public static XYChart chart2 = new XYChartBuilder().width(800).height(600).title("Daily Spending").xAxisTitle("Days in Month").yAxisTitle("Expenses ($)").build();
	
	//Buttons
	public static JButton backButton = new JButton(new ImageIcon("images/backToDash.png"));
	
	//Labels
	private JLabel bg = new JLabel(new ImageIcon("images/graphScreen.png"));
	
	//Constructor method
	public GraphScreen() {
		
		//Setup properties of frame
		setLayout(null);
		setSize(1920,1080);
		setVisible(true);
		setTitle("EcoSpend - Statistics");
		
		//Add the objects onto the screen
		addDailySpendings();
		addMonthlySpendings();
		addCategorySpendings();
		addComboBox();
		addButtons();
		addJLabels();
		
	}

	//This method initializes and creates a line graph that displays monthly payments
	private void addMonthlySpendings() {
		
		//Change the style of the chart (Show certain elements, change colour, transparent background)
	    chart1.getStyler().setLegendVisible(false);
	    chart1.getStyler().setAxisTitlesVisible(true);
	    chart1.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Area);
	    chart1.getStyler().setChartBackgroundColor(new Color(255,255, 255, 0));
	    chart1.getStyler().setAxisTickLabelsColor(Color.WHITE);
	    chart1.getStyler().setChartFontColor(Color.WHITE);
	    
	    //Add a series with the monthly spendings as the data set
	    chart1.addSeries("Monthly", Arrays.asList(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}), 
	    		Arrays.asList(new Double[] { Money.monthTotal[1], Money.monthTotal[2], Money.monthTotal[3], Money.monthTotal[4],
	    				Money.monthTotal[5], Money.monthTotal[6], Money.monthTotal[7], Money.monthTotal[8], Money.monthTotal[9],
	    				Money.monthTotal[10], Money.monthTotal[11], Money.monthTotal[12] }));
	    
	    //Change the labels of the x-axis
	    //https://github.com/knowm/XChart/issues/267
	    Map<Double, Object> xMarkMap = new TreeMap<Double, Object>();
	    
	    //Set each number in the x-axis to the respective month name
	    for (double x = 1.0; x <= 12.0; x++)  {
		    xMarkMap.put(x, Month.of((int) x).getDisplayName(TextStyle.SHORT_STANDALONE, Locale.CANADA));
	    }
	    
	    chart1.setXAxisLabelOverrideMap(xMarkMap); //Set the x-axis labels
	    
	    //Create new panel to contain the graph
	    monthlyPanel = new XChartPanel(chart1);
	    
	    //Set size and location of the panel
	    monthlyPanel.setLayout(null);
	    monthlyPanel.setBounds(10,400,600,600);
	    monthlyPanel.setOpaque(false);
	    monthlyPanel.setVisible(true);
		
		add(monthlyPanel); //Add panel to the frame
		
	}
	
	//This method initializes and creates a line graph that displays daily payments for the specified month
	private void addDailySpendings() {
		
		//Change the style of the chart (Show certain elements, change colour, transparent background)
	    chart2.getStyler().setLegendVisible(false);
	    chart2.getStyler().setAxisTitlesVisible(true);
	    chart2.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Area);
	    chart2.getStyler().setChartBackgroundColor(new Color(255,255, 255, 0));
	    chart2.getStyler().setAxisTickLabelsColor(Color.WHITE);
	    chart2.getStyler().setChartFontColor(Color.WHITE);
	    
	    //Add a series with initial values
	    chart2.addSeries("Daily", Arrays.asList(1), Arrays.asList(1.0));
	    
	    //Create new panel to contain the graph
	    dailyPanel = new XChartPanel(chart2);
	    
	    //Set size and location of the panel
	    dailyPanel.setLayout(null);
	    dailyPanel.setBounds(660,400,600,600);
	    dailyPanel.setOpaque(false);
	    dailyPanel.setVisible(true);
		
		add(dailyPanel); //Add panel to the frame
		
	}
	
	//This method initializes and creates a bar graph that displays the category spendings for each month
	private void addCategorySpendings() {
	    
		//Change the style of the chart (Show certain elements, change colour, transparent background)
	    chart.getStyler().setLegendVisible(false);
	    chart.getStyler().setChartBackgroundColor(new Color(255,255, 255, 0));
	    chart.getStyler().setAxisTickLabelsColor(Color.WHITE);
	    chart.getStyler().setChartFontColor(Color.WHITE);
	 
	    //Add a series with initial values
	    chart.addSeries("Category", Arrays.asList(new String[] { "Food", "Clothing", "Housing", "Transportation", "Medical", "Misc."}), 
	    		Arrays.asList(new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }));
	    
	    //Create new panel to contain the graph
		barPanel = new XChartPanel(chart);
		
		//Set size and location of the panel
		barPanel.setLayout(null);
		barPanel.setBounds(1310,400,600,600);
		barPanel.setOpaque(false);
		barPanel.setVisible(true);
		
		add(barPanel); //Add panel to the frame
		
	}
	
	//This method sets up and adds the combo boxes onto the frame
	private void addComboBox() {
		
		monthCombo.setBounds(1310,350,100,35);
		monthCombo.setBackground(Color.WHITE);
		monthCombo.setOpaque(true);
		
		add(monthCombo);
		
		monthCombo2.setBounds(660,350,100,35);
		monthCombo2.setBackground(Color.WHITE);
		monthCombo2.setOpaque(true);
		
		add(monthCombo2);
		
	}
	
	//This method sets up and adds the back button onto the frame
	private void addButtons() {
		
		backButton.setBounds(35,40,350,80);
		backButton.setOpaque(false);
		backButton.setContentAreaFilled(false);
		
		add(backButton);
		
	}
	
	//This method adds the background onto the frame
	private void addJLabels() {
		
		bg.setBounds(0,0,1920,1080);
		add(bg);
		
	}

}
