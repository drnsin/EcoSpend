package view.user;

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
 * This class contains the user's information. This includes the user's budget, balance, income
 * when they get paid, and how frequently. When the user opens this screen, it will display its
 * current information. The user is able to modify this information from this screen.
 */

@SuppressWarnings("serial")
public class UserPanel extends JPanel {
	
	//Fields
	//Labels
	private JLabel background;
	public static JLabel firstName = new JLabel();
	public static JLabel lastName = new JLabel();
	public static JLabel incomeMessage = new JLabel("How much do you get paid and when are you expecting your next deposit?");
	public static JLabel invalid = new JLabel("Invalid Values/Fill all Fields!");
	public static JLabel saved = new JLabel("Saved!");
	
	//Text fields
	public static JTextField budget = new JTextField();
	public static JTextField balance = new JTextField();
	public static JTextField income = new JTextField();
	public static JTextField budgetDecimal = new JTextField();
	public static JTextField balanceDecimal = new JTextField();
	public static JTextField incomeDecimal = new JTextField();
	
	//Radio buttons
	public static JRadioButton yesIncome,noIncome;
	
	//String data set for the payment options
	public static String[] paymentOptions = {"DAILY", "WEEKLY", "BI-WEEKLY", "MONTHLY", "ANNUALY"};
	
	//Combo box
	public static JComboBox<Month> monthCombo = new JComboBox<>(Month.values());
	public static JComboBox<Integer> dayCombo = new JComboBox<>();
	public static JComboBox<String> paymentCombo = new JComboBox<>(paymentOptions);
	
	//Buttons
	public static JButton confirm = new JButton("Confirm");
	public static JButton backButton = new JButton(new ImageIcon("images/backToDash.png"));
	
	//Create instance of max length
	private MaxLengthTextDocument maxLength1 = new MaxLengthTextDocument();
	private MaxLengthTextDocument maxLength2 = new MaxLengthTextDocument();
	private MaxLengthTextDocument maxLength3 = new MaxLengthTextDocument();
	
	//Constructor method
	public UserPanel() {
		
		//Set size and location of the panel
		setLayout(null);
		setBounds(0,0,1920,1080);
		
		//Add all of the objects onto the panel
		addRadioButtons();
		addTextFields();
		addComboBox();
		addJButton();
		addJLabel();
	
	}
	
	//This method adds the JLabels onto the panel
	private void addJLabel() {
		
		firstName.setFont(new Font("Times New Roman", Font.PLAIN, 56));
		firstName.setForeground(Color.WHITE);
		firstName.setBounds(865,140,400,75);
		add(firstName);
		
		lastName.setFont(new Font("Times New Roman", Font.PLAIN, 36));
		lastName.setForeground(Color.WHITE);
		lastName.setBounds(865,215,400,50);
		add(lastName);
	
		invalid.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		invalid.setForeground(Color.RED);
		invalid.setBounds(775,895,400,40);
		invalid.setVisible(false);
		add(invalid);

		saved.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		saved.setForeground(Color.GREEN);
		saved.setBounds(1030,895,200,40);
		saved.setVisible(false);
		add(saved);
		
		incomeMessage.setForeground(Color.WHITE);
		incomeMessage.setBounds(700,755,600,35);
		incomeMessage.setFont(new Font("Arial", Font.PLAIN, 15));
		incomeMessage.setVisible(false);
		add(incomeMessage);
		
		background = new JLabel(new ImageIcon("images/userPanel.png"));
		background.setBounds(0,0,1920,1080);
		add(background);
		
	}
	
	//This method adds the text fields onto the panel
	private void addTextFields() {
		
		budget.setBounds(700,410,200,35);
		budget.setFont(new Font("Arial", Font.PLAIN, 20));
		budget.setText("0");
		
		balance.setBounds(700,570,200,35);
		balance.setFont(new Font("Arial", Font.PLAIN, 20));
		balance.setText("0");
		
		income.setBounds(700,790,200,35);
		income.setFont(new Font("Arial", Font.PLAIN, 20));
		income.setText("0");
		income.setVisible(false);
		
		maxLength1.setMaxChars(3);
		maxLength2.setMaxChars(3);
		maxLength3.setMaxChars(3);
		
		budgetDecimal.setBounds(915,410,80,35);
		budgetDecimal.setFont(new Font("Arial", Font.PLAIN, 20));
		budgetDecimal.setDocument(maxLength1);
		budgetDecimal.setText("00");
		
		balanceDecimal.setBounds(915,570,80,35);
		balanceDecimal.setFont(new Font("Arial", Font.PLAIN, 20));
		balanceDecimal.setDocument(maxLength2);
		balanceDecimal.setText("00");
		
		incomeDecimal.setBounds(915,790,80,35);
		incomeDecimal.setFont(new Font("Arial", Font.PLAIN, 20));
		incomeDecimal.setVisible(false);
		incomeDecimal.setDocument(maxLength3);
		incomeDecimal.setText("00");
		
		add(budget);
		add(balance);
		add(income);
		add(budgetDecimal);
		add(balanceDecimal);
		add(incomeDecimal);
		
	}
	
	//This method adds the radio buttons onto the panel
	private void addRadioButtons() {
		
		yesIncome = new JRadioButton("Yes");
		yesIncome.setBounds(700,730,100,30);
		yesIncome.setFont(new Font("Arial", Font.PLAIN, 20));
		yesIncome.setOpaque(false);
		yesIncome.setForeground(Color.WHITE);
		
		noIncome = new JRadioButton("No");
		noIncome.setBounds(810,730,100,30);
		noIncome.setFont(new Font("Arial", Font.PLAIN, 20));
		noIncome.setOpaque(false);
		noIncome.setForeground(Color.WHITE);
		
		//Create new button group for radio buttons
		ButtonGroup bg = new ButtonGroup();
		bg.add(yesIncome);
		bg.add(noIncome);
		
		add(yesIncome);
		add(noIncome);
		
	}
	
	//This method adds the combo boxes onto the panel
	private void addComboBox() {
		
		paymentCombo.setBounds(700,830,90,30);
		paymentCombo.setBackground(Color.WHITE);
		paymentCombo.setOpaque(true);
		paymentCombo.setVisible(false);
		
		monthCombo.setBounds(800,830,100,30);
		monthCombo.setBackground(Color.WHITE);
		monthCombo.setOpaque(true);
		monthCombo.setVisible(false);
		
		dayCombo.setBounds(910,830,90,30);
		dayCombo.setBackground(Color.WHITE);
		dayCombo.setOpaque(true);
		dayCombo.setVisible(false);
		
		//Initially fill the day combo with 31 days
		for (int x = 1; x <= 31; x++)
			dayCombo.addItem(x);
		
		add(paymentCombo);
		add(monthCombo);
		add(dayCombo);
		
	}
	
	//This method adds the buttons onto the panel
	private void addJButton() {
		
		confirm.setBounds(1150,900,80,30);
		backButton.setBounds(35,40,350,80);
		backButton.setOpaque(false);
		backButton.setContentAreaFilled(false);
		
		add(confirm);
		add(backButton);
		
	}
	
}