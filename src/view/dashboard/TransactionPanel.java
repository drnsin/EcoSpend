package view.dashboard;

import java.awt.Color;
import java.awt.Font;
import java.time.Month;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import controller.MaxLengthTextDocument;

/*
 * This class contains the panel that is responsible for allowing the user to add new transactions
 */
@SuppressWarnings("serial")
public class TransactionPanel extends JPanel {
	
	//Fields
	//Labels
	public static JLabel invalid = new JLabel("Invalid Values/Fill all Fields!"); //Error message
	public static JLabel saved = new JLabel("Saved!"); //Saved message
	
	//Radio buttons
	//Cost type
	public static JRadioButton expendituresButton = new JRadioButton("Expenditures");
	public static JRadioButton incomeButton = new JRadioButton("Income");
	
	//Categories
	public static JRadioButton foodButton = new JRadioButton("Food");
	public static JRadioButton clothingButton = new JRadioButton("Clothing");
	public static JRadioButton housingButton = new JRadioButton("Housing");
	public static JRadioButton transportationButton = new JRadioButton("Transportation");
	public static JRadioButton medicalButton = new JRadioButton("Medical");
	public static JRadioButton miscButton = new JRadioButton("Miscellaneous");
	
	//Buttons
	public static JButton backButton = new JButton(new ImageIcon("images/backButton.png")); //Back to dashboard button
	public static JButton save = new JButton("Save"); //Save transaction button
	
	//Text fields
	public static JTextField cost = new JTextField();
	public static JTextField costDecimal = new JTextField();
	
	//Combo boxes
	public static JComboBox<Month> monthCombo = new JComboBox<>(Month.values()); //Combo box for the month
	public static JComboBox<Integer> dayCombo = new JComboBox<>(); //Combo box for the day
	private static String[] recurringOptions = {"NOT RECURRING", "DAILY", "WEEKLY", "BI-WEEKLY", "MONTHLY", "BI-MONTHLY", "QUARTERLY", "SEMIANNUAL", "ANNUALLY"}; //String data set for the payment options
	public static JComboBox<String> recurringCombo = new JComboBox<>(recurringOptions); //Combo box for payement options
	
	private MaxLengthTextDocument maxLength = new MaxLengthTextDocument();
	
	//Constructor method
	public TransactionPanel() {
		
		//Setup the properties of the panel
		setLayout(null);
		setBounds(1400,0,520,1080);
		setBackground(Color.DARK_GRAY);
		setVisible(false);
		
		//Add all of the objects onto the panel
		addJLabels();
		addButtons();
		costTypeButtons();
		categoryButtons();
		addTextFields();
		addComboBox();
		
	}
	
	//This panel sets up and adds all of the labels onto the panel
	private void addJLabels() {
		
		JLabel title = new JLabel("New Transaction");
		title.setBounds(180,5,220,20);
		title.setFont(new Font("Arial", Font.BOLD, 15));
		title.setForeground(Color.WHITE);
		
		JLabel categoryHeading = new JLabel("Type of Transactions");
		categoryHeading.setBounds(165,185,220,20);
		categoryHeading.setFont(new Font("Arial", Font.BOLD, 15));
		categoryHeading.setForeground(Color.WHITE);
		
		JLabel costHeading = new JLabel("Cost: $");
		costHeading.setBounds(70,505,120,20);
		costHeading.setFont(new Font("Arial", Font.BOLD, 20));
		costHeading.setForeground(Color.WHITE);
		
		JLabel dateHeading = new JLabel("Date:");
		dateHeading.setBounds(70,585,100,20);
		dateHeading.setFont(new Font("Arial", Font.BOLD, 20));
		dateHeading.setForeground(Color.WHITE);
		
		JLabel reccuringHeading = new JLabel("Recurring:");
		reccuringHeading.setBounds(70,665,200,25);
		reccuringHeading.setFont(new Font("Arial", Font.BOLD, 20));
		reccuringHeading.setForeground(Color.WHITE);
		
		invalid.setBounds(90,850,400,40);
		invalid.setForeground(Color.RED);
		invalid.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		invalid.setVisible(false);
		
//		saved.setBounds(220,850,150,40);
//		saved.setForeground(Color.GREEN);
//		saved.setFont(new Font("Times New Roman", Font.PLAIN, 30));
//		saved.setVisible(false);
		
		add(title);
		add(categoryHeading);
		add(costHeading);
		add(dateHeading);
		add(invalid);
		//add(saved);		
		//add(reccuringHeading);
		
	}
	
	//This panel sets up and adds all of the buttons onto the panel
	private void addButtons() {
		
		backButton.setBounds(20,20,30,30);
		backButton.setOpaque(false);
		backButton.setContentAreaFilled(false);
		
		save.setBounds(92,920,335,80);
		
		add(backButton);
		add(save);
		
	}
	
	//This panel sets up and adds all of the cost type radio buttons onto the panel
	private void costTypeButtons() {
		
		expendituresButton.setBounds(45,70,220,30);
		expendituresButton.setFont(new Font("Arial", Font.PLAIN, 20));
		expendituresButton.setForeground(Color.WHITE);
		expendituresButton.setOpaque(false);
		
		incomeButton.setBounds(340,70,150,30);
		incomeButton.setFont(new Font("Arial", Font.PLAIN, 20));
		incomeButton.setForeground(Color.WHITE);
		incomeButton.setOpaque(false);
		
		//Create a new button group for the cost type buttons
		ButtonGroup bg = new ButtonGroup();
		bg.add(expendituresButton);
		bg.add(incomeButton);
		
		add(expendituresButton);
		add(incomeButton);
		
	}
	
	//This panel sets up and adds all of the cateogry buttons onto the panel
	private void categoryButtons() {
		
		foodButton.setBounds(40,230,100,30);
		foodButton.setFont(new Font("Arial", Font.PLAIN, 20));
		foodButton.setForeground(Color.WHITE);
		foodButton.setOpaque(false);
		
		clothingButton.setBounds(40,310,120,30);
		clothingButton.setFont(new Font("Arial", Font.PLAIN, 20));
		clothingButton.setForeground(Color.WHITE);
		clothingButton.setOpaque(false);
		
		housingButton.setBounds(40,390,120,30);
		housingButton.setFont(new Font("Arial", Font.PLAIN, 20));
		housingButton.setForeground(Color.WHITE);
		housingButton.setOpaque(false);
		
		transportationButton.setBounds(265,230,200,30);
		transportationButton.setFont(new Font("Arial", Font.PLAIN, 20));
		transportationButton.setForeground(Color.WHITE);
		transportationButton.setOpaque(false);
		
		medicalButton.setBounds(265,310,120,30);
		medicalButton.setFont(new Font("Arial", Font.PLAIN, 20));
		medicalButton.setForeground(Color.WHITE);
		medicalButton.setOpaque(false);
		
		miscButton.setBounds(265,390,200,30);
		miscButton.setFont(new Font("Arial", Font.PLAIN, 20));
		miscButton.setForeground(Color.WHITE);
		miscButton.setOpaque(false);		
		
		//Create a new button group for the category buttons
		ButtonGroup bg = new ButtonGroup();
		bg.add(foodButton);
		bg.add(clothingButton);
		bg.add(housingButton);
		bg.add(transportationButton);
		bg.add(medicalButton);
		bg.add(miscButton);
		
		add(foodButton);
		add(clothingButton);
		add(housingButton);
		add(transportationButton);
		add(medicalButton);
		add(miscButton);
		
	}
	
	//This panel sets up and adds all of the text fields onto the panel
	private void addTextFields() {
		
		cost.setBounds(160,495,180,35);
		cost.setFont(new Font("Arial", Font.PLAIN, 20));
		cost.setText("0");
		
		maxLength.setMaxChars(3); //Set the max characters allowed in the costDecimal field to 2
		
		costDecimal.setBounds(355,495,80,35);
		costDecimal.setFont(new Font("Arial", Font.PLAIN, 20));
		costDecimal.setDocument(maxLength);
		costDecimal.setText("00");
		
		add(cost);
		add(costDecimal);
		
	}
	
	//This panel sets up and adds all of the combo boxes onto the panel
	private void addComboBox() {
		
//		recurringCombo.setBounds(190,660,150,35);
//		recurringCombo.setBackground(Color.WHITE);
//		recurringCombo.setOpaque(true);
		
		monthCombo.setBounds(145,580,100,35);
		monthCombo.setBackground(Color.WHITE);
		monthCombo.setOpaque(true);
		
		dayCombo.setBounds(260,580,90,35);
		dayCombo.setBackground(Color.WHITE);
		dayCombo.setOpaque(true);
		
		for (int x = 1; x <= 31; x++)
			dayCombo.addItem(x);
		
//		add(recurringCombo);
		add(monthCombo);
		add(dayCombo);
		
	}
	
}
