package com.uttara.phone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;
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
				result = bufferedReader.readLine().trim();
				Logger.getInstance().log(prompt + " : " + result);
			}	
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return result;
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

	public static int choiceInputandValidation(int choice, int upperLimit) {
		while ( choice < 1 || choice > upperLimit) {
			choice = PhoneHelper.getUserNumberInput("Enter a choice between 1 and 6");
			Logger.getInstance().log("mainMenu choice = " + choice);
		}
		return choice;	
	}

	public static void closeResources() {
		bufferedReaderCloser();
	}

	
	/*
	 * Input Validation:
    1. 1 word
	2. no spaces
    3. no spl character ~ only alphanumeric
    4. start with letter
Business Validation:
    1. No duplicates
	 */
	public static String validateContactsBookName(String word) {
		String message = Constants.SUCCESS;
		message = wordCount(word, 1);
		if (!message.equals(Constants.SUCCESS)) {
			return message;
		}
		message = specialCharactersPresent(word);
		if (!message.equals(Constants.SUCCESS)) {
			return message;
		}
		message = startsWithLetter(word);
		if (!message.equals(Constants.SUCCESS)) {
			return message;
		}
		message = whiteSpacesPresent(word);
		if (!message.equals(Constants.SUCCESS)) {
			return message;
		}
		return Constants.SUCCESS;
	}
	
	private static String whiteSpacesPresent(String word) {
		if (Pattern.matches("\\s+", word)) {
			return "Name should not have whitespaces!";
		}
		return Constants.SUCCESS;
	}

	private static String startsWithLetter(String word) {
		if (!Character.isLetter(word.charAt(0))) {
			return "Name should start with a letter";
		}
		return Constants.SUCCESS;
	}

	private static String specialCharactersPresent(String word) {
		if (!Pattern.matches("^[a-zA-Z0-9]+$", word)) {
			return "Name should not contain special characters!";
		}
		return Constants.SUCCESS;
	}

	private static String specialCharactersPresent(String word, String characterTokens) {
		if (!Pattern.matches("[" + characterTokens +"]", word)) {
			return "Name should not contain special characters except for " + characterTokens;
		}
		return Constants.SUCCESS;
	}


	private static String wordCount(String name, int expectedLength) {
		String[] splitName = name.split(name);
		if (splitName.length > expectedLength) {
			return "Name length permitted " + expectedLength;
		}
		return Constants.SUCCESS;
	}


	/**
	 * This method will validate the string given to check if it contains multiple
	 * words or special chars. If7 yes, it returns an error message else it returns constant
	 * string success!
	 */
	/*
	Input Validation:
    1. spl characters ',-
    2. start with letter
    3. alphanumeric
    4. spl character not allowed =:
	*/
	public static String validateName(String name) {
		String message = Constants.SUCCESS;
		message = specialCharactersPresent(name, "'/-")
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

	// Validation equal to m/M, f/F, o/O
	public static String validateGender(String gender) {
		for (String option: List.of("m","f","o")) {
			if (option.equalsIgnoreCase(gender.trim())) {
				return Constants.SUCCESS;
			}
		}
		return "Kindly enter only m, f or o as gender";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
