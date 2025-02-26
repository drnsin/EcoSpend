package view.dashboard;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.time.Month;

import javax.swing.*;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;

import controller.AppController;
import controller.TransactionController;
import model.Money;

/*
 * This class is the main screen of the application. It comprises of a pie chart that displays
 * the percentage of the budget used, a panel that displays all of the transactions, and the
 * main buttons that take the user to all of the other screens.
 */
@SuppressWarnings("serial")
public class DashboardScreen extends JFrame {
	
	//Fields
	//The main buttons on the dashboard
	public static JButton nextDayButton = new JButton(new ImageIcon("images/nextDay.png"));
	public static JButton newTransaction = new JButton(new ImageIcon("images/newTransaction.png"));
	public static JButton userButton = new JButton(new ImageIcon("images/userProfile.png"));
	public static JButton graphScreenButton = new JButton(new ImageIcon("images/graphButton.png"));
	public static JButton logOutButton = new JButton(new ImageIcon("images/logOut.png"));
	
	//The buttons that switch the display month for the scroll pane
	public static JButton nextMonth = new JButton(new ImageIcon("images/foward.png"));
	public static JButton previousMonth = new JButton(new ImageIcon("images/back.png"));
	
	//The scroll pane that will contain all of the transaction labels
	public static JScrollPane scrollPane = new JScrollPane(TransactionController.spendingsPanel);
	
	public static JLayeredPane layeredPane = new JLayeredPane(); 	//Layered pane to determine location of objects
	
	//The pie chart that displays a visualization of how much the user has used of their monthly budget
	public static JPanel piePanel;
	public static PieChart chart = new PieChartBuilder().width(800).height(800).build();
	
	//JLabels
	public static JLabel monthLabel = new JLabel(); //The month title for the current month that the scroll pane is on
	public static JLabel totalMonthLabel = new JLabel(); //The total expense for that month
	public static JLabel bg = new JLabel(new ImageIcon("images/DashboardBG.png")); //Background image
	private JLabel pieLabel = new JLabel(new ImageIcon("images/pieLabel.png")); //Label on top of the pie chart
	public static JLabel percent = new JLabel(); //Percentage label that displays the percentage of the budget that the user has used
	private JLabel logo = new JLabel(new ImageIcon("images/logo.png")); //Logo
	public static JLabel dateLabel = new JLabel(); //The current date
	
	public static final DecimalFormat df = new DecimalFormat("0.0"); //Decimal format formats double values to the nearest tenth
	
	//Constructor
	public DashboardScreen() {
		
		//Set the size of the frame
		setLayout(null);
		setSize(1920,1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("EcoSpend - Dashboard");
        
        //Add all of the objects onto the frame
        addPieChart();
        addButtons();
        addJScrollPane();
        addLayeredPane();
        addLabels();
        add(bg);
        
        setVisible(true); //Set the visibility to true
		
	}
	
	//This method adds and initializes the pie chart displayed on the dashboard
	//https://knowm.org/open-source/xchart/xchart-example-code/
	private void addPieChart() {
		
		//Change the style of the chart (Show certain elements, change colour, transparent background)
		Color[] sliceColors = new Color[] { new Color(0, 147, 94), new Color(234, 104, 94) };
		chart.getStyler().setSeriesColors(sliceColors);
	    chart.getStyler().setLegendVisible(false);
	    chart.getStyler().setPlotContentSize(.7);
	    chart.getStyler().setStartAngleInDegrees(0);
	    chart.getStyler().setChartBackgroundColor(new Color(255,255, 255, 0));
        chart.getStyler().setPlotBackgroundColor(new Color(255,255, 255, 0));
        chart.getStyler().setPlotBorderVisible(false);
        chart.getStyler().setHasAnnotations(false);
        
	    //Add the initial series
	    chart.addSeries("BUDGET", 100);;
	    chart.addSeries("SPENT", 0);
		
	    //Create new panel to contain the chart
		piePanel = new XChartPanel(chart);
		
		//Set properties of the panel
		piePanel.setLayout(null);
		piePanel.setBounds(-80,-80,800,800);
		piePanel.setOpaque(false);
		piePanel.setVisible(true);
		
	}

	//This methods sets up all of the buttons on the dashboard screen
	private void addButtons() {
		
		nextMonth.setBounds(1820,60,60,60);
		nextMonth.setOpaque(false);
		nextMonth.setContentAreaFilled(false);
		
		previousMonth.setBounds(1260,60,60,60);
		previousMonth.setOpaque(false);
		previousMonth.setContentAreaFilled(false);
		
		nextDayButton.setBounds(645,95,600,250);
		nextDayButton.setOpaque(false);
		nextDayButton.setContentAreaFilled(false);
		
		newTransaction.setBounds(645,355,600,250);
		newTransaction.setOpaque(false);
		newTransaction.setContentAreaFilled(false);
		
		logOutButton.setBounds(15,620,400,385);
		logOutButton.setOpaque(false);
		logOutButton.setContentAreaFilled(false);
		
		userButton.setBounds(430,620,400,385);
		userButton.setOpaque(false);
		userButton.setContentAreaFilled(false);
		
		graphScreenButton.setBounds(845,620,400,385);
		graphScreenButton.setOpaque(false);
		graphScreenButton.setContentAreaFilled(false);
		
	}
	
	//This method sets up the labels on the dashboard screen
	private void addLabels() {
		
		monthLabel.setBounds(1350,50,450,50);
		monthLabel.setFont(new Font("Arial", Font.BOLD, 40));
		
		totalMonthLabel.setBounds(1485,105,260,15);
		totalMonthLabel.setFont(new Font("Arial", Font.BOLD, 12));
		totalMonthLabel.setText("Total Month Expense: $");
		totalMonthLabel.setForeground(Color.WHITE);
		
		pieLabel.setBounds(70,70,502,502);
		
		percent.setBounds(110,180,480,100);
		percent.setFont(new Font("Times New Roman", Font.BOLD, 96));
		
		pieLabel.add(percent);
		
		setPercentColor(); //Set the value and colour of the percentage text
		
		logo.setBounds(1260,835,620,170);
		
		bg.setBounds(0,0,1920,1080);
		
		dateLabel.setBounds(690,60,516,34);
		dateLabel.setText("Current Day: " + Month.of(AppController.months.getCurrentMonth()).toString() + " " + //Set the label to the current day
						AppController.months.getCurrentDay() + ", " + AppController.months.getCurrentYear());
		dateLabel.setFont(new Font("Times New Roman", Font.PLAIN, 28));
		dateLabel.setForeground(Color.WHITE);
		
		//Add the date label and logo onto the background label
		bg.add(dateLabel);
		bg.add(logo);
		
	}
	
	//This method sets up the scroll pane
	private void addJScrollPane() {
		
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(1260,150,620,670);
		
	}
	
	//This method adds all of the objects onto a layered pane
	private void addLayeredPane() {
		
		layeredPane.setBounds(0,0,1920,1080);
		layeredPane.add(scrollPane,1);
		layeredPane.add(newTransaction,1);
		layeredPane.add(nextDayButton,1);
		layeredPane.add(nextMonth,1);
		layeredPane.add(graphScreenButton,1);
		layeredPane.add(userButton,1);
		layeredPane.add(previousMonth,1);
		layeredPane.add(monthLabel,1);
		layeredPane.add(totalMonthLabel,1);
		layeredPane.add(logOutButton,1);
		layeredPane.add(TransactionController.transactionPanel,0);
		layeredPane.add(pieLabel,2);
		layeredPane.add(piePanel);
		layeredPane.setOpaque(false);
		
		layeredPane.setVisible(true);
		
		add(layeredPane);
		
	}
	
	//This method sets the color and text value of the percentage label
	public static void setPercentColor() {
		
		//Get the percentage spent of the budget for the month displayed on the scroll pane
		double percentage = (Money.monthTotal[SpendingsPanel.spendingsPanelMonth] / AppController.money.getUserBudget() * -100);
		
		//If the percentage is more than 0
		if (percentage > 0) {
			
			//If the percentage is less than 50, set color to green
			if (percentage < 50) {
				percent.setForeground(Color.GREEN);
			}
			
			//If the percentage is less than 100, set color to orange
			else if (percentage < 100) {
				percent.setForeground(Color.ORANGE);
			}
			
			//Otherwise, set the color to red
			else {
				percent.setForeground(Color.RED);
			}
			
			percent.setText(df.format(percentage) + "%"); //Set the text of the label to the percentage value
			
		}
		
		//If the percentage is 0, remove the negative sign
		else if (percentage == 0) {
			percent.setText(df.format(percentage * -1) + "%");
			percent.setForeground(Color.GREEN);
		}
		
		else {
			percent.setText(df.format(percentage) + "%");
			percent.setForeground(Color.GREEN);
		}
		
	}

}
