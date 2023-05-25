package com.uttara.phone.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.uttara.phone.Constants;
import com.uttara.phone.Logger;

public class ValidationHelper {

	
	
	static String whiteSpacesPresent(String word) {
		Pattern pattern = Pattern.compile("\s");
		Matcher matcher = pattern.matcher(word);
		if (matcher.find()) {
			return "Name should not have whitespaces!";
		}
		return Constants.SUCCESS;
	}

	static String startsWithLetter(String word) {
		if (!Pattern.matches("^[a-zA-Z]+", word)) {
			return "Name should start with a letter";
		}
		return Constants.SUCCESS;
	}

	static String onlyAlphanumeric(String word) {
		if (!Pattern.matches("[a-zA-Z0-9]+", word)) {
			return "Name should only contain alphanumeric characters i.e A-Z, a-z and 0-9!";
		}
		return Constants.SUCCESS;
	}

	static String specialCharactersPresent(String word, String allowedTokens) {
		Pattern pattern = Pattern.compile("[" + allowedTokens +"]+");
		Matcher matcher = pattern.matcher(word);
		if (!matcher.find()) {
			return "Name should not contain special characters except for " + allowedTokens;
		}
		return Constants.SUCCESS;
	}


	static String wordCount(String name, int expectedLength) {
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
		System.out.println("Not yet Implemented");
		String message = Constants.FAILURE;
		if (Pattern.matches("[MmFfOo]", name)) {
			return message;
		}
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
//To convert String to date
		String message = Constants.SUCCESS;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");	
		try  {
			sdf.parse(date);
		} catch (ParseException pe) {
			Logger.getInstance().log(pe.getStackTrace().toString());
			message = "Date could not be parsed, " +
				"kindly enter the date in dd/MM/yyyy format";
		}
		return message;
		
	}
	public static String validateEmail(String email) {
		return Constants.SUCCESS;
	}
	public static String validatePhoneNumber(String num) {
		//Use Regex  or starts with to check if it begins with right numbers
		return Constants.SUCCESS;
	}
	public static String validateTags(List<String> tagsList) {
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
		// [] - matches any single characters inside square brackets
		if (Pattern.matches("[MmFfOo]", gender)) {
			return Constants.SUCCESS;
		}
		// for (String option: List.of("m","f","o")) {
		// 	if (option.equalsIgnoreCase(gender.trim())) {
		// 		return Constants.SUCCESS;
		// 	}
		// }
		return "Kindly enter only m, f or o as gender";
	}

	public static String validateAddress(String address) {
		System.out.println("Not yet Implemented");
		return Constants.SUCCESS;
	}
	
}
