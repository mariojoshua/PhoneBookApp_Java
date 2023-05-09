package com.uttara.phone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * This class contains all the static utility methods used by 
 * multiple classes in the app by the app
 */

public class PhoneHelper { 

	// early instantiation
	private static BufferedReader bufferedReader 
	  = new BufferedReader(new InputStreamReader(System.in));
	// constructor set to private so that it cannot be instantiated
	// and only a single instance will be used
	private PhoneHelper() {
	}

	/**
	 * This method will validate the string given to check if it contains multiple
	 * words or special chars. If7 yes, it returns an error message else it returns constant
	 * string success!
	 */
	public static String validateName(String n) {
		// if (n == null || n.trim().equals(""))
		// 	throw new IllegalArgumentException("name cannot be null");

		// String[] sa = n.split(" ");
		// if (sa.length > 1)
		// 	return "Name cannot contain multiple words! Enter a single worded string as input!";

		// for (int i = 0; i < n.length(); i++) {
		// 	char c = n.charAt(i);

		// 	if (!(Character.isDigit(c) || Character.isLetter(c)))
		// 		return "Name should not contain special chars!";
		// }
		return Constants.SUCCESS;
	}

	public static int choiceInputandValidation(int choice, int upperLimit) {
		while ( choice < 1 || choice > upperLimit) {
			choice = PhoneHelper.getUserNumberInput("Enter a choice between 1 and 6");
			Logger.getInstance().log("mainMenu choice = " + choice);
		}
		return choice;	
	}
	
	public static int getUserNumberInput(String prompt) {
		int choice = 0;
		try {
			choice = Integer.parseInt(getUserStringInput(prompt));
		} catch (NumberFormatException ne) {
			getUserNumberInput(prompt);
		}
		return choice;
	}
	
	public static String getUserStringInput(String prompt) {
		String result = "";
		try {
			while (result.isBlank()) {
				System.out.print(prompt +  ": ");
				result = bufferedReader.readLine();
				Logger.getInstance().log(prompt + " : " + result);
			}	
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return result;
	}
	public static String validateDate(String date) {
		//Check if input date is before current date
		//To validate current date and time
//		Date d = new Date;
//		d.validateTime();
//
//		//To convert String to date
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		Date dt = sdf.parse(String date);
//
//		//to convert data to String
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy")
//		String plDt = sdf.format(d);
		return Constants.SUCCESS;
		
	}
	public static String validateEmail(String email) {
		return Constants.SUCCESS;
	}
	public static String validatePhoneNumber(String num) {
		//Use Regex  or starts with to check if it begins with right numbers
		return Constants.SUCCESS;
	}
	public static String validateTags(String tag) {
		return Constants.SUCCESS;
	}
	
	static String validateHomeNumber(String homeNumber) {
		return Constants.SUCCESS;
	}


	static String validateStreetAddress(String streetAddress) {
		return Constants.SUCCESS;
	}


	static String validatePincode(String pincode) {
		return Constants.SUCCESS;
	}


	static String validateCity(String city) {
		return Constants.SUCCESS;
	}


	static String validateState(String state) {
		return Constants.SUCCESS;
	}


	static String validateCountry(String country) {
		return Constants.SUCCESS;
	}

    public static void closeResources() {
		bufferedReaderCloser();
	}

	public static void bufferedReaderCloser() {
		if (bufferedReader != null) {
			try {
				bufferedReader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
