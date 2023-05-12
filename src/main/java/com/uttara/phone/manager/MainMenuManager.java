package com.uttara.phone.manager;

import java.util.List;

import com.uttara.phone.Constants;
import com.uttara.phone.Logger;
import com.uttara.phone.PhoneBookService;
import com.uttara.phone.PhoneHelper;

/**
 * This class will hold the view and the controller in flavor 1 of MVC(Desktop
 * App) design pattern
 * 
 * @author mariojoshuaaugustine
 * @version 1.0
 * @since 2021-12-01
 * 
 */

public class MainMenuManager {

	private PhoneBookService phoneBookService = null;
	private String result = null;
	private String phoneBookName;
	private ContactsMenuManager contactsMenuManager;
	private ListMenuManager listMenuManager;

	public MainMenuManager() {
		this.contactsMenuManager = new ContactsMenuManager();
		this.listMenuManager = new ListMenuManager();
	}

	/**
	 * These methods forms the View of the application these method will display the
	 * menu, accept the inputs, display success/error messages to the user and
	 * invoke the methods of model!
	 */

	public void run() {
		System.out.println("In run method");
		this.phoneBookService = new PhoneBookService();
		//System.out.println(this.phoneBookService);
		mainMenuController();	
	} 

	private int mainMenuView() {
		int menuSize = 6;
		System.out.println("""
			\n\tContacts Book Menu
			*********************************\n
			Press [1] to Create Contacts Book
			Press [2] to Load Contact Books
			Press [3] to Search contacts
			Press [4] to List contacts
			Press [5] for Birthday reminders
			Press [6] to Exit
			\n*********************************
			Enter choice\f""");		
		return menuSize;				
	}

	private void mainMenuController () {
		int choice = 0;
		while (choice != 6) {
			String phoneBookName = "";
			int menuSize = mainMenuView();
			choice = PhoneHelper.choiceInputandValidation(choice, menuSize);
			switch (choice) {
				case 1: // Creates a Contacts Book file/entry on disk/sql 
					phoneBookName = createContactsBookController();
					contactsMenuManager.contactsMenuController(phoneBookName);
					choice = 0;
					break;
				case 2: // Loads Contacts Book Name from file
					System.out.println("Contact book loaded");
					phoneBookName = loadContactsController();
					contactsMenuManager.contactsMenuController(phoneBookName);
					choice = 0;
					break;
				case 3:
					System.out.println("searching phone book...");
					choice = 0;
					break;
				case 4:
					System.out.println("listing phone book...");
				//	System.out.println(phoneBookService.listContacts(phoneBookName));
					listContactsController();
					choice = 0;
					break;
				case 5:
					System.out.println("listing birthday reminders...");
					choice = 0;
					break;
				case 6:
					System.out.println("Exiting...Have a great day");
					PhoneHelper.closeResources();
					break;
				default:
					System.out.println("Kindly enter choice between 1 to 6\n");
					break;

			}
		}
	}

	

	private String loadContactsController() {
		//check if phonebook exists
		String phoneBookName = "";
		String validity = Constants.FAILURE;
		while (!validity.equals(Constants.SUCCESS)) {
			phoneBookName = PhoneHelper
			.getUserStringInput("Enter a name for the phoneBook");
			// Input Validation
			validity = PhoneHelper.validateContactsBookName(phoneBookName);
			// Business Validation - no duplicates
			if (phoneBookService.phoneBookExists(phoneBookName)) {
				Logger.getInstance().log("Phone book " + phoneBookName + " exists.");
				validity = Constants.SUCCESS;
			}
			System.out.println("Opening PhoneBook" + phoneBookName);
		}
		return phoneBookName;
	}


	String createContactsBookController() {
		String phoneBookName = "";
		String validity = Constants.FAILURE;
		while (!validity.equals(Constants.SUCCESS)) {
			phoneBookName = PhoneHelper
			.getUserStringInput("Enter a name for the phoneBook");
			// Input Validation
			validity = PhoneHelper.validateContactsBookName(phoneBookName);
			// Business Validation - no duplicates
			if (phoneBookService.phoneBookExists(phoneBookName)) {
				validity = "Phone book " + phoneBookName + " already exists, use a different name ";
			}
			System.out.println(validity);
		}
		Logger.getInstance().log("Validation Success, Creating phonebook " + phoneBookName);
		String message = phoneBookService.createContactsBook(phoneBookName);
		System.out.println(message);
		Logger.getInstance().log(message);
		return phoneBookName;
	}

	// private void showsSearchContactMenu() {
	// 	/*
	// 	 * When 5 is selected (Search), you should ask the user to input a string to
	// 	 * search. This string has be searched in the entire contents of the contacts
	// 	 * book(name, email,phone num, tags, etc) and the following should be displayed:
	// 	 */

	// 	System.out.println("Total number of occurances : <num>");
	// 	System.out.println("Number of occurances in email : <num>");
	// 	System.out.println("Matches found: ");
	// 	// <contact name1 - email1 text1>
	// 	// <contact name2 - email2 text2>

	// 	System.out.println("Number of occurances in Phone num : <num>");
	// 	// <contact name1 - ph - email>
	// 	// <contact name2 - ph - email>

	// 	System.out.println("Number of occurances in tags : <num>");
	// 	// <contact name - ph - email - tag>
	// 	// <contact name - ph - email - tag>

	// }

	// private void showsListContactMenu() {
	// 	choice = 0;
	// 	System.out.println("""
	// 		Press 1 to list contacts by alphabetical listing by name
	// 		Press 2 to list contacts by alphabetical ordering of tags
	// 		Press 3 to list contacts by created date
	// 		Press 4 to list contacts by string length (length of entire line info on contact)""");

	// 	while ( choice < 1 || choice > 4) {
	// 		choice = PhoneHelper.getUserNumberInput("Enter a choice between 1 and 6");
	// 		Logger.getInstance().log("mainMenu choice = " + choice);
	// 	}
	// 	/*
	// 	 * To list all contact in the current phone book by String length
	// 	 */

	// 	System.out.println("");
	// 	// invoke method of model to get the collection of beans from file
	// 	List<ContactBean> contactsArray = phoneBookService.listContacts(phoneBookName);
	// 	// if null is returned, it means there was a problem
	// 	if (contactsArray == null) {
	// 		System.out.println("Oops,There's a problem during listing! Contact Admin! (look at console stacktrace)");
	// 	} else {
	// 		switch (choice) {
	// 		case 1:
	// 			// Sort by alphabetical listing of name
	// 			Collections.sort(contactsArray);
	// 			// loop over the list and invoke getter methods on bean to display to user
	// 			// for (ContactBean contactBean : contactsArray) {
	// 			// 	System.out.println(contactBean);
	// 			// }
	// 			contactsArray.forEach(contactBean -> System.out.println(contactBean));
	// 			break;
	// 		case 2:
	// 			// Sort by alphabetical ordering of tags
	// 			TagsComparator tagsComparator = ((o1,o2) -> o1.tag compareTo(o2)))
	// 			Collections.sort(contactsArray, tagsComparator);
	// 			// loop over the list and invoke getter methods on bean to display to user
	// 			contactsArray.forEach(contactBean -> System.out.println(contactBean));
	// 			break;
	// 		case 3:
	// 			// Sort Based on created Date
	// 			Comparator<ContactBean> stringLengthComparator = (o1, o2) -> 
	// 								o1.dateOfBirth() - o2.toString().length();
	// 			CreatedDateComparator cdc = new CreatedDateComparator();
	// 			Collections.sort(contactsArray, cdc);
	// 			// loop over the list and invoke getter methods on bean to display to user
	// 			contactsArray.forEach(contactBean -> System.out.println(contactBean));
	// 			break;
	// 		case 4:
	// 			// sort arraylist based on String Length
	// 			Comparator<Object> stringLengthComparator = (o1, o2) -> 
	// 								o1.toString().length() - o2.toString().length();
	// 			Collections.sort(contactsArray, stringLengthComparator);
	// 			// loop over the list and invoke getter methods on bean to display to user
	// 			contactsArray.forEach(contactBean -> System.out.println(contactBean));
	// 			break;

	// 		default:
	// 			break;
	// 		}

	// 	}

	// }

	// private void showsRemoveContactMenu() {
	// 	/*
	// 	 * Take input of contact name to remove
	// 	 */
	// 	System.out.println("Enter Contact Name to remove");
	// 	contactName = PhoneHelper.getUserStringInput(phoneBookName);
	// 	/*
	// 	 * Check if contact exists, if yes then proceed to remove contact, if success //
	// 	 * message is returned print success message, if not give failure message
	// 	 */
	// 	if (phoneBookService.contactNameExists(phoneBookName, contactName)) {
	// 		if (phoneBookService.removeContact(phoneBookName, contactName).equals(Constants.SUCCESS)) {
	// 			System.out.println(contactName + " successfully removed from " + phoneBookName);
	// 		} else {
	// 			System.out.println("Removing contact failed, try again or contact administator");
	// 		}

	// 	} else {
	// 		System.out.println("Unable to remove as contact name could not be found");
	// 	}

	// }

	// private void showsEditContactMenu() {
	// 	int choice = 0;

	// 	// enter contact name to edit
	// 	System.out.println("Enter name of contact");
	// 	contactName = stringScanner.nextLine();
	// 	// check if contact exists
	// 	while (!phoneBookService.contactNameExists(phoneBookName, contactName)) {
	// 		System.out.println(
	// 				contactName + " does not exist in " + phoneBookName + ", Kindly enter a contact name that exists");
	// 		contactName = stringScanner.nextLine();

	// 	}

	// 	// List contact details
	// 	List<ContactBean> contactsArray = phoneBookService.listContacts(phoneBookName);
	// 	// if null is returned, it means there was a problem
	// 	if (contactsArray == null) {
	// 		System.out.println("There's a problem during listing! Contact Admin! (look at console stacktrace)");
	// 	} else {
	// 		for (ContactBean contactBean : contactsArray) {
	// 			if (contactBean.getName().equalsIgnoreCase(contactName)) {
	// 				System.out.println("Contact Name: " + contactBean.getName());
	// 				System.out.println("Primary phone number: " + contactBean.getPhoneNumber1());
	// 				System.out.println("Secondary phone number: " + contactBean.getPhoneNumber2());
	// 				System.out.println("Primary email id: " + contactBean.getEmail1());
	// 				System.out.println("Secondary email id: " + contactBean.getEmail2());
	// 				System.out.println("Tertiary email id: " + contactBean.getEmail3() + "\n");

	// 			}
	// 		}

	// 	}

	// 	while (choice != 7) {
	// 		System.out.println("""
	// 			Press [1] to edit phone number
	// 			Press [2] to remove phone number
	// 			Press [3] to add new phone number
	// 			Press [4] to edit email id
	// 			Press [5] to remove email id
	// 			Press [6] to add new email id
	// 			Press [7] to go back\n
	// 			Enter a choice to proceed""");

	// 		//
	// 		while (!numberScanner.hasNextInt()) {
	// 			System.out.println("Kindly give numbers between 1 to 6 only\n\f");
	// 			numberScanner.next();
	// 		}

	// 	}

	// 	// read the valid token integer input from scanner
	// 	choice = numberScanner.nextInt();
	// 	Logger.getInstance().log("choice = " + choice);

	// 	switch (choice)

	// 	{
	// 	case 1:
	// 		System.out.println("Press [1] to edit primary phone number");
	// 		System.out.println("Press [2] to edit secondary phone number\n");

	// 		System.out.println("Type the new/corrected phone number");
	// 		phoneNumber1 = stringScanner.nextLine();
	// 		result = PhoneUtil.validatePhoneNumber(phoneNumber1);
	// 		while (!result.equals(Constants.SUCCESS)) {
	// 			System.out.println("Enter a valid phone number");
	// 			phoneNumber1 = stringScanner.nextLine();
	// 			result = PhoneUtil.validatePhoneNumber(phoneNumber1);
	// 		}

	// 		if (phoneBookService.editPhoneNumber(phoneBookName, contactName, phoneNumber1, choice)
	// 				.equals(Constants.SUCCESS)) {
	// 			System.out.println("Phone number successfully changed");
	// 		} else {
	// 			System.out
	// 					.println("There was an error with changing the phone number, try again or else contact admin");
	// 		}
	// 		break;
	// 	case 2:
	// 		System.out.println("Press [1] to remove primary phone number");
	// 		System.out.println("Press [2] to remove secondary phone number");
	// 		if (phoneBookService.removePhoneNumber(phoneBookName, contactName, choice).equals(Constants.SUCCESS)) {
	// 			System.out.println("Phone number successfully removed");
	// 		} else {
	// 			System.out.println("There was an error removing the phone number, try again or else contact admin");

	// 		}
	// 		break;
	// 	case 3:
	// 		// Check if primary or secondary phone numbers are empty
	// 		// if yes then put new phone number as there
	// 		// if not, then ask which phone number to overwrite
	// 		System.out.println("Type the new/corrected phone number");
	// 		phoneNumber1 = stringScanner.nextLine();
	// 		result = PhoneUtil.validatePhoneNumber(phoneNumber1);
	// 		while (!result.equals(Constants.SUCCESS)) {
	// 			System.out.println("Enter a valid phone number");
	// 			phoneNumber1 = stringScanner.nextLine();
	// 			result = PhoneUtil.validatePhoneNumber(phoneNumber1);
	// 		}
	// 		if (phoneBookService.addPhoneNumber(phoneBookName, contactName, phoneNumber1, choice)
	// 				.equals(Constants.SUCCESS)) {
	// 			System.out.println("Phone number successfully added");
	// 		} else {
	// 			System.out.println("There was an error adding the phone number, try again or else contact admin");

	// 		}
	// 		break;
	// 	case 4:
	// 		System.out.println("Press [1] to edit primary email id");
	// 		System.out.println("Press [2] to edit secondary email id");
	// 		System.out.println("Press [3] to edit tertiary email id");

	// 		System.out.println("Type the new/corrected email ID");
	// 		email1 = stringScanner.nextLine();
	// 		result = PhoneUtil.validatePhoneNumber(email1);
	// 		while (!result.equals(Constants.SUCCESS)) {
	// 			System.out.println("Enter a valid phone number");
	// 			email1 = stringScanner.nextLine();
	// 			result = PhoneUtil.validatePhoneNumber(email1);
	// 		}
	// 		if (phoneBookService.editEmailid(phoneBookName, contactName, email1, choice).equals(Constants.SUCCESS)) {
	// 			System.out.println("Email id successfully changed");
	// 		} else {
	// 			System.out.println("There was an error with changing the email id, try again or else contact admin");

	// 		}
	// 		break;
	// 	case 5:
	// 		System.out.println("Press [1] to remove primary email id");
	// 		System.out.println("Press [2] to remove secondary email id");
	// 		System.out.println("Press [3] to remove tertiary email id");
	// 		if (phoneBookService.removeEmailId(phoneBookName, contactName, choice).equals(Constants.SUCCESS)) {
	// 			System.out.println("Email id successfully removed");
	// 		} else {
	// 			System.out.println("There was an error removing the email id, try again or else contact admin");

	// 		}
	// 		break;
	// 	case 6:
	// 		// Check if primary or secondary or tertiary email id's are empty
	// 		// if yes then put new email id there
	// 		// if not, then ask which phone number to overwrite
	// 		System.out.println("Type the new/corrected email ID");
	// 		email1 = stringScanner.nextLine();
	// 		result = PhoneUtil.validatePhoneNumber(email1);
	// 		while (!result.equals(Constants.SUCCESS)) {
	// 			System.out.println("Enter a valid phone number");
	// 			email1 = stringScanner.nextLine();
	// 			result = PhoneUtil.validatePhoneNumber(email1);
	// 		}

	// 		if (phoneBookService.addEmailId(phoneBookName, contactName, email1, choice).equals(Constants.SUCCESS)) {
	// 			System.out.println("Email id successfully added");
	// 		} else {
	// 			System.out.println("There was an error adding the email id, try again or else contact admin");

	// 		}
	// 		break;

	// 	default:
	// 		System.out.println("Not Yet Implemented");
	// 		break;
	// 	}

	// }

	// public void showsAddContactMenu() {
	// 	/*
	// 	 * Input Contact name and validate if its unique, if its unique then move
	// 	 * forward or else ask user to enter a unique name
	// 	 */

	// 	contactName = PhoneHelper.getUserStringInput("Enter full name of contact");

	// 	while (!phoneBookService.isContactNameUnique(phoneBookName, contactName)) {
	// 		System.out.println("Name Already Exists, Kindly Enter a unique name.");
	// 		contactName = stringScanner.nextLine();
	// 		result = PhoneUtil.validateName(contactName);
	// 	}

	// 	/*
	// 	 * Input PetName
	// 	 */
	// 	System.out.println("Enter pet name of contact");
	// 	petName = stringScanner.nextLine();

	// 	while (!phoneBookService.isContactNameUnique(phoneBookName, contactName)) {
	// 		System.out.println("Name Already Exists, Kindly Enter a unique name.");
	// 		contactName = stringScanner.nextLine();
	// 		result = PhoneUtil.validateName(contactName);
	// 	}

	// 	/*
	// 	 * Input Contact number and validate else ask user to enter a valid number for
	// 	 * phoneNumber1 and phoneNumber2
	// 	 */
	// 	System.out.println("Enter primary phone number of contact " + contactName);
	// 	phoneNumber1 = stringScanner.nextLine();
	// 	result = PhoneUtil.validatePhoneNumber(phoneNumber1);
	// 	while (!result.equals(Constants.SUCCESS)) {
	// 		System.out.println("Enter a valid phone number");
	// 		phoneNumber1 = stringScanner.nextLine();
	// 		result = PhoneUtil.validatePhoneNumber(phoneNumber1);
	// 	}

	// 	System.out.println("Enter secondary phone number of contact " + contactName);
	// 	phoneNumber2 = stringScanner.nextLine();
	// 	result = PhoneUtil.validatePhoneNumber(phoneNumber2);
	// 	while (!result.equals(Constants.SUCCESS)) {
	// 		System.out.println("Enter a valid phone number");
	// 		phoneNumber2 = stringScanner.nextLine();
	// 		result = PhoneUtil.validatePhoneNumber(phoneNumber2);
	// 	}

	// 	/*
	// 	 * Input Date of Birth and validate if its before current date and a valid date
	// 	 */
	// 	System.out.println("Enter Date of birth of contact in dd/MM/yyyy format " + contactName);
	// 	dateOfBirth = stringScanner.nextLine();
	// 	result = PhoneUtil.validateDate(dateOfBirth);
	// 	while (!result.equals(Constants.SUCCESS)) {
	// 		System.out.println("Enter a valid Date of Birth");
	// 		dateOfBirth = stringScanner.nextLine();
	// 		result = PhoneUtil.validateDate(dateOfBirth);
	// 	}

	// 	/*
	// 	 * Input address and validate each field of the address private String
	// 	 * homeNumber; private String streetAddress; private String pincode; private
	// 	 * String city; private String state; private String country;
	// 	 */
	// 	System.out.println("Enter house number of contact " + contactName);
	// 	homeNumber = stringScanner.nextLine();
	// 	result = PhoneUtil.validateHomeNumber(homeNumber);
	// 	while (!result.equals(Constants.SUCCESS)) {
	// 		System.out.println("Enter a valid Date of Birth");
	// 		homeNumber = stringScanner.nextLine();
	// 		result = PhoneUtil.validateHomeNumber(homeNumber);
	// 	}

	// 	System.out.println("Enter street address of contact " + contactName);
	// 	streetAddress = stringScanner.nextLine();
	// 	result = PhoneUtil.validateStreetAddress(streetAddress);
	// 	while (!result.equals(Constants.SUCCESS)) {
	// 		System.out.println("Enter a valid Date of Birth");
	// 		streetAddress = stringScanner.nextLine();
	// 		result = PhoneUtil.validateStreetAddress(streetAddress);
	// 	}

	// 	System.out.println("Enter pincode of contact " + contactName);
	// 	pincode = stringScanner.nextLine();
	// 	result = PhoneUtil.validatePincode(pincode);
	// 	while (!result.equals(Constants.SUCCESS)) {
	// 		System.out.println("Enter a valid Date of Birth");
	// 		pincode = stringScanner.nextLine();
	// 		result = PhoneUtil.validatePincode(pincode);
	// 	}

	// 	System.out.println("Enter city of Contact " + contactName);
	// 	city = stringScanner.nextLine();
	// 	result = PhoneUtil.validateCity(city);
	// 	while (!result.equals(Constants.SUCCESS)) {
	// 		System.out.println("Enter a valid Date of Birth");
	// 		city = stringScanner.nextLine();
	// 		result = PhoneUtil.validateCity(city);
	// 	}

	// 	System.out.println("Enter state of contact " + contactName);
	// 	state = stringScanner.nextLine();
	// 	result = PhoneUtil.validateState(state);
	// 	while (!result.equals(Constants.SUCCESS)) {
	// 		System.out.println("Enter a valid Date of Birth");
	// 		state = stringScanner.nextLine();
	// 		result = PhoneUtil.validateState(state);
	// 	}

	// 	System.out.println("Enter country of contact " + contactName);
	// 	country = stringScanner.nextLine();
	// 	result = PhoneUtil.validateCountry(country);
	// 	while (!result.equals(Constants.SUCCESS)) {
	// 		System.out.println("Enter a valid Date of Birth");
	// 		country = stringScanner.nextLine();
	// 		result = PhoneUtil.validateCountry(country);
	// 	}

	// 	address = new Address(homeNumber, streetAddress, pincode, city, state, country);

	// 	/*
	// 	 * Input email1,email2,email3
	// 	 */
	// 	System.out.println("Enter first email address of contact " + contactName);
	// 	email1 = stringScanner.nextLine();
	// 	result = PhoneUtil.validateEmail(phoneNumber2);
	// 	while (!result.equals(Constants.SUCCESS)) {
	// 		System.out.println("Enter a valid phone number");
	// 		email1 = stringScanner.nextLine();
	// 		result = PhoneUtil.validateEmail(phoneNumber2);
	// 	}
	// 	System.out.println("Enter second email address of contact " + contactName);
	// 	email2 = stringScanner.nextLine();
	// 	result = PhoneUtil.validateEmail(phoneNumber2);
	// 	while (!result.equals(Constants.SUCCESS)) {
	// 		System.out.println("Enter a valid phone number");
	// 		email2 = stringScanner.nextLine();
	// 		result = PhoneUtil.validateEmail(phoneNumber2);
	// 	}

	// 	System.out.println("Enter third email address of contact " + contactName);
	// 	email3 = stringScanner.nextLine();
	// 	result = PhoneUtil.validateEmail(phoneNumber2);
	// 	while (!result.equals(Constants.SUCCESS)) {
	// 		System.out.println("Enter a valid phone number");
	// 		email3 = stringScanner.nextLine();
	// 		result = PhoneUtil.validateEmail(phoneNumber2);
	// 	}

	// 	/*
	// 	 * Input comma-separated tags and validate if they start with a hash
	// 	 */
	// 	System.out.println("Enter  comma seperated tags for contact eg. #family,#friends,#work  " + contactName);
	// 	tag = stringScanner.nextLine();
	// 	result = PhoneUtil.validateTags(tag);
	// 	while (!result.equals(Constants.SUCCESS)) {
	// 		System.out.println("Enter a valid Date of Birth");
	// 		tag = stringScanner.nextLine();
	// 		result = PhoneUtil.validateTags(tag);
	// 	}

	// 	// Create bean object to hold user inputs
	// 	ContactBean bean = new ContactBean();
	// 	bean.setName(contactName);
	// 	bean.setPhoneNumber1(phoneNumber1);
	// 	bean.setPhoneNumber2(phoneNumber2);
	// 	bean.setDateOfBirth(dateOfBirth);
	// 	bean.setPetName(petName);
	// 	bean.setAddress(address);
	// 	bean.setEmail1(email1);
	// 	bean.setEmail2(email2);
	// 	bean.setEmail3(email3);
	// 	bean.setTag(tag);

	// 	// invoke method of model to add the contact to file
	// 	Logger.getInstance().log("main()->invoking models addContact()");
	// 	result = phoneBookService.addContact(bean, phoneBookName);

	// 	/*
	// 	 * if model method returned Constants.SUCCESS, it means that addition was
	// 	 * successful,else it is returning error message to display to user
	// 	 */
	// 	Logger.getInstance().log("main()-> result from addContact() " + result);
	// 	if (result.equals(Constants.SUCCESS)) {
	// 		System.out
	// 				.println("Contact " + contactName + " has been added successfully to phone book " + phoneBookName);
	// 	} else {
	// 		System.out.println("There is a problem in adding " + result + " ,Kindly try again");
	// 	}
	// }

}
