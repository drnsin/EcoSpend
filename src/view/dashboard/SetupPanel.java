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
 * This class is the setup panel and is responsible for displaying a screen in which the user can
 * enter their information in regards to their budget. The setup panel will show for users that have
 * just signed up or have not setup their budget information
 */
@SuppressWarnings("serial")
public class SetupPanel extends JPanel {
	
	//Fields
	//Labels
	private JLabel background = new JLabel(new ImageIcon("images/setupBG.png"));
	public static JLabel invalid = new JLabel("Invalid Values/Fill all Fields!");
	private JLabel setupBox = new JLabel(new ImageIcon("images/SetupBox.png"));
	public static JLabel incomeMessage = new JLabel("How much do you get paid and when are you expecting your next deposit?");
	
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
	public static JComboBox<Month> monthCombo = new JComboBox<>(Month.values()); //Month combo box
	public static JComboBox<Integer> dayCombo = new JComboBox<>(); //Day combo box
	public static JComboBox<String> paymentCombo = new JComboBox<>(paymentOptions); //Payment option combo box
	
	//Buttons
	public static JButton confirm = new JButton("Confirm"); //Confirm button
	
	//Create instance of max length
	private MaxLengthTextDocument maxLength1 = new MaxLengthTextDocument();
	private MaxLengthTextDocument maxLength2 = new MaxLengthTextDocument();
	private MaxLengthTextDocument maxLength3 = new MaxLengthTextDocument();

	//Constructor method
	public SetupPanel() {
		
		//Set size and dimension of panel
		setLayout(null);
		setBounds(0,0,1920,1080);
		
		//Add objects onto the panel
		addRadioButtons();
		addTextFields();
		addComboBox();
		addJButton();
		addJLabel();
	
	}
	
	//This method adds the JLabels onto the panel
	private void addJLabel() {
		
		invalid.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		invalid.setForeground(Color.RED);
		invalid.setBounds(250,650,400,35);
		invalid.setVisible(false);
		setupBox.add(invalid);
		
		incomeMessage.setForeground(Color.WHITE);
		incomeMessage.setBounds(35,547,600,35);
		incomeMessage.setFont(new Font("Arial", Font.PLAIN, 15));
		incomeMessage.setVisible(false);
		setupBox.add(incomeMessage);
		
		setupBox.setBounds(645,178,629,726);
		add(setupBox);
		
		background.setBounds(0,0,1920,1080);
		add(background);
		
	}
	
	//This method adds the text fields onto the panel
	private void addTextFields() {
		
		budget.setBounds(35,167,200,35);
		budget.setFont(new Font("Arial", Font.PLAIN, 20));
		budget.setText("0");
		
		balance.setBounds(35,362,200,35);
		balance.setFont(new Font("Arial", Font.PLAIN, 20));
		balance.setText("0");
		
		income.setBounds(35,577,200,35);
		income.setFont(new Font("Arial", Font.PLAIN, 20));
		income.setText("0");
		income.setVisible(false);
		
		maxLength1.setMaxChars(3);
		maxLength2.setMaxChars(3);
		maxLength3.setMaxChars(3);
		
		budgetDecimal.setBounds(250,167,80,35);
		budgetDecimal.setFont(new Font("Arial", Font.PLAIN, 20));
		budgetDecimal.setDocument(maxLength1);
		budgetDecimal.setText("00");
		
		balanceDecimal.setBounds(250,362,80,35);
		balanceDecimal.setFont(new Font("Arial", Font.PLAIN, 20));
		balanceDecimal.setDocument(maxLength2);
		balanceDecimal.setText("00");
		
		incomeDecimal.setBounds(250,577,80,35);
		incomeDecimal.setFont(new Font("Arial", Font.PLAIN, 20));
		incomeDecimal.setVisible(false);
		incomeDecimal.setDocument(maxLength3);
		incomeDecimal.setText("00");
		
		setupBox.add(budget);
		setupBox.add(balance);
		setupBox.add(income);
		setupBox.add(budgetDecimal);
		setupBox.add(balanceDecimal);
		setupBox.add(incomeDecimal);
		
	}
	
	//This method adds the radio buttons onto the panel
	private void addRadioButtons() {
		
		yesIncome = new JRadioButton("Yes");
		yesIncome.setBounds(50,519,100,30);
		yesIncome.setFont(new Font("Arial", Font.PLAIN, 20));
		yesIncome.setForeground(Color.WHITE);
		yesIncome.setOpaque(false);
		
		noIncome = new JRadioButton("No");
		noIncome.setBounds(160,519,100,30);
		noIncome.setFont(new Font("Arial", Font.PLAIN, 20));
		noIncome.setForeground(Color.WHITE);
		noIncome.setOpaque(false);
		
		//Create new button group for radio buttons
		ButtonGroup bg = new ButtonGroup();
		bg.add(yesIncome);
		bg.add(noIncome);
		
		setupBox.add(yesIncome);
		setupBox.add(noIncome);
		
	}
	
	//This method adds the combo boxes onto the panel
	private void addComboBox() {
		
		paymentCombo.setBounds(35,617,90,30);
		paymentCombo.setBackground(Color.WHITE);
		paymentCombo.setOpaque(true);
		paymentCombo.setVisible(false);
		
		monthCombo.setBounds(135,617,100,30);
		monthCombo.setBackground(Color.WHITE);
		monthCombo.setOpaque(true);
		monthCombo.setVisible(false);
		
		dayCombo.setBounds(245,617,90,30);
		dayCombo.setBackground(Color.WHITE);
		dayCombo.setOpaque(true);
		dayCombo.setVisible(false);
		
		//Initially fill the day combo with 31 days
		for (int x = 1; x <= 31; x++)
			dayCombo.addItem(x);
		
		setupBox.add(paymentCombo);
		setupBox.add(monthCombo);
		setupBox.add(dayCombo);
		
	}

	//This method adds the buttons onto the panel
	private void addJButton() {
		
		confirm.setBounds(520,650,80,30);
		
		setupBox.add(confirm);
		
	}
	
}
