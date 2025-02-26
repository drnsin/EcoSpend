package controller;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/*
 * This class is responsible for limiting the number of characters that can be entered into
 * a text field
 */
//https://stackoverflow.com/questions/30027582/limit-the-number-of-characters-of-a-jtextfield
@SuppressWarnings("serial")
public class MaxLengthTextDocument extends PlainDocument {
	
	//Fields
	private int maxChars; //The max amount of numbers
	
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		
		if (str != null && (getLength() + str.length() < maxChars)) {
			super.insertString(offset, str, attr);
		}
		
	}

	public int getMaxChars() {
		return maxChars;
	}

	public void setMaxChars(int maxChars) {
		this.maxChars = maxChars;
	}

}
