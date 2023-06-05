package com.uttara.phone.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import com.uttara.phone.Constants;
import com.uttara.phone.Logger;
import com.uttara.phone.PhoneBookService;
import com.uttara.phone.Name.Gender;

/**
 * This class contains all the static utility methods used by 
 * multiple classes in the app by the app
 */

public class PhoneHelper { 

	// early instantiation
	private static BufferedReader bufferedReader 
	  = new BufferedReader(new InputStreamReader(System.in));
	private static PhoneBookService phoneBookService =
		new PhoneBookService();
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
			choice = PhoneHelper.getUserNumberInput("Enter a choice between 1 and " + upperLimit);
			Logger.getInstance().log("mainMenu choice = " + choice);
		}
		return choice;	
	}

	public static void closeResources() {
		bufferedReaderCloser();
	}

	// put format in message
	// check validity
	public static LocalDateTime getDateInput() {
		String validity = Constants.FAILURE;
		String dateOfBirth = "";
		while (!validity.equals(Constants.SUCCESS)) {
			dateOfBirth = PhoneHelper
			.getUserStringInput("Enter the Date of Birth for the " 
			+ "contact as dd/MM/yyyy eg. 27/05/1989");
			// Input Validation
			validity = ValidationHelper.validateDate(dateOfBirth);
			Logger.getInstance().log(validity);
			System.out.println(validity);
		}
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDate.parse(dateOfBirth, dateTimeFormatter).atStartOfDay();
	}

	// 3 emails input
	public static String getEmailInput(String index) {
		String validity = Constants.FAILURE;
		String email = "";
		while (!validity.equals(Constants.SUCCESS)) {
			email = PhoneHelper
			.getUserStringInput("Enter the "+ index + " email id for the contact");
			// Input Validation
			validity = ValidationHelper.validateEmail(email);
			Logger.getInstance().log(validity);
			System.out.println(validity);
		}
		return email;
	}

	public static String getAnyContactInput() {
		String validity = Constants.FAILURE;
		String input = "";
		while (!validity.equals(Constants.SUCCESS)) {
			input = PhoneHelper
			.getUserStringInput("Enter the contact detail to search for");
			// Input Validation
			validity = ValidationHelper.validateAnyContactDetail(input);
			Logger.getInstance().log(validity);
			System.out.println(validity);
		}
		return input;
	}

	// Input validation - alphanumeric, comma allowed
	public static List<String> getTagsInputs() {
		String validity = Constants.FAILURE;
		String tags = "";
		List<String> tagsList = new LinkedList<>();
		while (!validity.equals(Constants.SUCCESS)) {
			tags = PhoneHelper
			.getUserStringInput("""
				Enter comma seperated tags for the contact
				eg. home,walkingGroup,club""");
			// validate tags
			validity = ValidationHelper.validateTags(tagsList);
			Logger.getInstance().log(validity);
			System.out.println(validity);
		}
		// separate tags
		tagsList = separateTags(tags);
		return tagsList;
	}

	private static List<String> separateTags(String tags) {
		// split and trim
		String[] tagArray = tags.split(",");
		for (int i = 0; i < tagArray.length; i++) {
			tagArray[i] = tagArray[i];
		}
		return Arrays.asList(tagArray);
	}

	public static String getAddressInput() {
		String validity = Constants.FAILURE;
		String address = "";
		while (!validity.equals(Constants.SUCCESS)) {
			address = PhoneHelper
			.getUserStringInput("Enter the Address for the contact");
			// Input Validation
			validity = ValidationHelper.validateAddress(address);
			Logger.getInstance().log(validity);
			System.out.println(validity);
		}
		return address;
	}

	// 2 phoneinputs
	public static String getPhoneNumberInput(String index) {
		String validity = Constants.FAILURE;
		String phoneNumber = "";
		while (!validity.equals(Constants.SUCCESS)) {
			phoneNumber = PhoneHelper
			.getUserStringInput("Enter the "+ index + " phone number for the contact");
			// Input Validation
			validity = ValidationHelper.validatePhoneNumber(phoneNumber);
			Logger.getInstance().log(validity);
			System.out.println(validity);
		}
		return phoneNumber;
	}

	/*
	 * Input Validation
	 1. equaltoIgnoreCase m,f,o
	 */
	public static Gender getGenderInput() {
		String validity = Constants.FAILURE;
		String gender = "";
		while (!validity.equals(Constants.SUCCESS)) {
			gender = PhoneHelper.getUserStringInput("""
				Enter a gender for the contact
					m for Male
					f for Female
					o for others """);
			// Input Validation
			validity = ValidationHelper.validateGender(gender);
			Logger.getInstance().log(validity);
			System.out.println(validity);
		}
		return Gender.valueOf(gender.toUpperCase());
	}

	/*
	 * Input Validation:
    1. spl characters allowed ',-
    2. start with letter
    3. alphanumeric
    4. spl character not allowed =:
	 */
	public static String getPetNameInput() {
		String validity = Constants.FAILURE;
		String petName = "";
		while (!validity.equals(Constants.SUCCESS)) {
			petName = PhoneHelper
			.getUserStringInput("Enter a petname for the contact");
			// Input Validation
			validity = ValidationHelper.validateName(petName);
			Logger.getInstance().log(validity);
			System.out.println(validity);
		}
		return petName;
	}

	/*
	 * Input Validation:
    1. spl characters allowed',-
    2. start with letter
    3. alphanumeric
    4. spl character not allowed =:
	* Business Validation:
    1. Unique fullname
	 */
	/**
	 * Checks if the name exists or doesnt exist in a particular phonebook
	 * exists == true takes input When name is present prints msg , validity success
	 * exists == false takes input name is not present prints msg , validity success
	 * @param exists
	 * @return
	 */
	public static String getFullNameInput(String phoneBookName, String presence) {
		String validity = Constants.FAILURE;
		String fullName = "";
		while (!validity.equals(Constants.SUCCESS)) {
			fullName = PhoneHelper
			.getUserStringInput("Enter the full name for the contact");
			// Input Validation
			validity = ValidationHelper.validateName(fullName);
			// Business Validation
			validity = validateNameExistence(phoneBookName, fullName, presence);
			Logger.getInstance().log("checking for " + presence + "message: " + validity);
			System.out.println(validity);
		}
	return fullName;
	}
	
	// check if name exists
	// if checking for existence print one message
	// if checking for non existence print another message
	private static String validateNameExistence(String phoneBookName, String fullName, String presence) {
		String message = Constants.FAILURE;
		Boolean contactExists = phoneBookService.contactNameExists(phoneBookName, fullName);
		if (presence.equals(Constants.PRESENT)) {
			if (contactExists) {
				message = Constants.SUCCESS;
			} else { // contact doesnt exist
				message = fullName + " could not be found in " + phoneBookName 
				+ " try a different name or go to the list option to see all names.";
			}	
		} else if (presence.equals(Constants.ABSENT)) {
			if (contactExists) {
				message = fullName + " exists in " + phoneBookName 
				+ " , enter a different name";
			} else { // contact doesnt exist
				message = Constants.SUCCESS;
			}
		}
		return message;
	}

	// get name after input validation
	// check if present
	// check if absent


// Validation methods

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
		// no spaces ~ 1 word
		message = ValidationHelper.whiteSpacesPresent(word);
		if (!message.equals(Constants.SUCCESS)) {
			return message;
		}
		message = ValidationHelper.onlyAlphanumeric(word);
		if (!message.equals(Constants.SUCCESS)) {
			return message;
		}
		message = ValidationHelper.startsWithLetter(word);
		if (!message.equals(Constants.SUCCESS)) {
			return message;
		}
		return Constants.SUCCESS;
	}
}


