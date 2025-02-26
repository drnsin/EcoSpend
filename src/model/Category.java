package model;

import java.util.Arrays;

/*
 * Model class for category. Category contains 7 different categories that contain 13 rows and
 * 32 columns for the month and days. This model class will primarily be used to store all of the
 * transactions, each in their respective category field.
 */
public class Category {
	
	//Fields
	//These fields are the category arrays of the living expenses
	//13 rows for each month (ignore 0), 32 columns for the maximum of 31 days per month (ignore 0)
	public static double[][] food = new double[13][32];
	public static double[][] clothing = new double[13][32];
	public static double[][] housing = new double[13][32];
	public static double[][] transportation = new double[13][32];
	public static double[][] medical = new double[13][32];
	public static double[][] misc = new double[13][32];
	public static double[][] pay = new double[13][32];
	
	//Utility methods
	//This method initially fills each array with 0 when the user logs in
	public static void fillArray() {
		
		for(double[] row: food)
			Arrays.fill(row, 0);
		for(double[] row: clothing)
			Arrays.fill(row, 0);
		for(double[] row: housing)
			Arrays.fill(row, 0);
		for(double[] row: transportation)
			Arrays.fill(row, 0);
		for(double[] row: medical)
			Arrays.fill(row, 0);
		for(double[] row: misc)
			Arrays.fill(row, 0);
		for(double[] row: pay)
			Arrays.fill(row, 0);
		
	}
	
}
