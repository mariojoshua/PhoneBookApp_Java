package com.uttara.phone.manager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.uttara.phone.Constants;
import com.uttara.phone.ContactBean;
import com.uttara.phone.Logger;
import com.uttara.phone.Name;
import com.uttara.phone.Name.Gender;
import com.uttara.phone.PhoneBookService;
import com.uttara.phone.PhoneHelper;

public class ContactsMenuManager {
    private PhoneBookService phoneBookService = null;
	private String result = null;
	private String phoneBookName;

	/**
	 * These methods forms the View of the application these method will display the
	 * menu, accept the inputs, display success/error messages to the user and
	 * invoke the methods of model!
	 */

    public ContactsMenuManager() {
        Logger.getInstance().log("In ContactsMenuManager constructor");
		this.phoneBookService = new PhoneBookService();
    }

	public void run(String phoneBookName) {
		this.phoneBookName = phoneBookName;
		contactsMenuController();	
	} 

    private int contactsMenuView() {
		int menuSize = 6;
		System.out.println("""
			\n\tContacts Menu
			*********************************\n
			Press [1] to Add contact
			Press [2] to Edit a contact
			Press [3] to Remove a contact
			Press [4] to List contacts
			Press [5] to Search contact
			Press [6] to go back
			\n*********************************
			Enter choice\f""");		
		return menuSize;		
	}

	private void contactsMenuController() {
		int choice = 0;
		while (choice != 6) {
			int menuSize = contactsMenuView();
			choice = PhoneHelper.choiceInputandValidation(choice, menuSize);
			switch (choice) {
				case 1: // Adding a contact menu 
					System.out.println("\f");
					Logger.getInstance().log("Adding contact");
					addContactController();
					choice = 0;
					break;
				case 2:
					// Edit contact Menu
					Logger.getInstance().log("Editing contact");
					editContactController();
					choice = 0;
					break;
				case 3:
					/*
					* To Remove a contact validation - Check if contact exits IF contact is removed
					* return success message or else Display"Unable to remove"
					*/
					Logger.getInstance().log("removing contact");
					//showsRemoveContactMenu();
					choice = 0;
					break;
				case 4:
					// List Contact Menu 
					//showsListContactMenu();
					choice = 0;
					break;
				case 5:
					//showsSearchContactMenu();
					choice = 0;
					break;
				case 6:
					System.out.println("Going back to Main Menu");
					break;
				default:
					System.out.println("Kindly enter choice between 1 to " + menuSize + "\n");
					break;
			}
		}


	}

	/*
	 * EDIT CONTACT METHODS
	 */
	int editContactsView() {
		int menuSize = 3;
		System.out.println("""
			\n\tContacts Book Menu
			*********************************\n
			Press [1] to remove/change contact info
			Press [2] to add new email/phone number
			Press [3] to return to Contacts Menu	
			\n*********************************
			Enter choice\f""");		
		return menuSize;	
	}
    private void editContactController() {
		int choice = 0;
		String fullName = getFullNameInput(phoneBookName);
		while (choice != 3) {
			int menuSize = editContactsView();
			choice = PhoneHelper.choiceInputandValidation(choice, menuSize);
			switch (choice) {
				case 1: // Creates a Contacts Book file/entry on disk/sql 
					editContactInfoController(fullName);
					choice = 0;
					break;
				case 2: // Loads Contacts Book Name from file
					System.out.println("Contact book loaded");
					addEmailPhoneController(fullName);
					choice = 0;
					break;
				case 3:
					System.out.println("returning to Contacts Menu...");
					choice = 0;
					break;
				default:
					System.out.println("Kindly enter choice between 1 to "+ menuSize+"\n");
					break;

			}
		}
		
	}

	private void addEmailPhoneController(String fullName) {
		System.out.println("Not yet Implemented");
	}

	private void editContactInfoController(String fullName) {
		System.out.println("Not yet Implemented");
	}

	/*
	 * ADD CONTACT METHODS
	 */
	private void addContactController() {
		// take all inputs after validation
		Gender gender = getGenderInput(); 
		String fullName = getFullNameInput(Constants.ABSENT);
		String petName = getPetNameInput();
		Name name = new Name(gender, fullName, petName);
		String phoneNumber1 = getPhoneNumberInput("first");
		String phoneNumber2 = getPhoneNumberInput("second");
		List<String> phoneNumbers = List.of(phoneNumber1, phoneNumber2);
		String address = getAddressInput();
		List<String> tags = getTagsInputs();
		String email1 = getEmailInput("first");
		String email2 = getEmailInput("second");
		String email3 = getEmailInput("third");
		List<String> email = List.of(email1, email2, email3);
		Map<String, LocalDateTime> dates = 
		Map.of("dateOfBirth", getDateInput());
		// add to contactbean
		ContactBean contactBean = new ContactBean
			(phoneBookName, name, phoneNumbers, address,tags,email,dates);
		// invoke service to add to io
		String message = phoneBookService.createContact(contactBean);
		System.out.println(message.equals(Constants.SUCCESS)
					? "Contact " + fullName + " added Succesfully"
					: "Failed to add contact "+ fullName + ", try Again");
		Logger.getInstance().log("Contact addition status: " + message);
    }

	// put format in message
	// check validity
	private LocalDateTime getDateInput() {
		String validity = Constants.FAILURE;
		String dateOfBirth = "";
		while (!validity.equals(Constants.SUCCESS)) {
			dateOfBirth = PhoneHelper
			.getUserStringInput("Enter the Date of Birth for the " 
			+ "contact as dd/MM/yyyy eg. 27/05/1989");
			// Input Validation
			validity = PhoneHelper.validateDate(dateOfBirth);
			Logger.getInstance().log(validity);
			System.out.println(validity);
		}
		return LocalDate.parse(dateOfBirth).atStartOfDay();
	}

	// 3 emails input
	private String getEmailInput(String index) {
		String validity = Constants.FAILURE;
		String email = "";
		while (!validity.equals(Constants.SUCCESS)) {
			email = PhoneHelper
			.getUserStringInput("Enter the "+ index + " email id for the contact");
			// Input Validation
			validity = PhoneHelper.validateEmail(email);
			Logger.getInstance().log(validity);
			System.out.println(validity);
		}
		return email;
	}

	// Input validation - alphanumeric, comma allowed
	private List<String> getTagsInputs() {
		String validity = Constants.FAILURE;
		String tags = "";
		List<String> tagsList = new LinkedList<>();
		while (!validity.equals(Constants.SUCCESS)) {
			tags = PhoneHelper
			.getUserStringInput("""
				Enter comma seperated tags for the contact
				eg. home,walkingGroup,club""");
			// validate tags
			validity = PhoneHelper.validateTags(tagsList);
			Logger.getInstance().log(validity);
			System.out.println(validity);
		}
		// separate tags
		tagsList = separateTags(tags);
		return tagsList;
	}

	private List<String> separateTags(String tags) {
		// split and trim
		String[] tagArray = tags.split(",");
		for (int i = 0; i < tagArray.length; i++) {
			tagArray[i] = tagArray[i].trim();
		}
		return Arrays.asList(tagArray);
	}

	private String getAddressInput() {
		String validity = Constants.FAILURE;
		String address = "";
		while (!validity.equals(Constants.SUCCESS)) {
			address = PhoneHelper
			.getUserStringInput("Enter the Address for the contact");
			// Input Validation
			validity = PhoneHelper.validateAddress(address);
			Logger.getInstance().log(validity);
			System.out.println(validity);
		}
		return address;
	}

	// 2 phoneinputs
	private String getPhoneNumberInput(String index) {
		String validity = Constants.FAILURE;
		String phoneNumber = "";
		while (!validity.equals(Constants.SUCCESS)) {
			phoneNumber = PhoneHelper
			.getUserStringInput("Enter the "+ index + " phone number for the contact");
			// Input Validation
			validity = PhoneHelper.validatePhoneNumber(phoneNumber);
			Logger.getInstance().log(validity);
			System.out.println(validity);
		}
		return phoneNumber;
	}

	/*
	 * Input Validation
	 1. equaltoIgnoreCase m,f,o
	 */
	private Gender getGenderInput() {
		String validity = Constants.FAILURE;
		String gender = "";
		while (!validity.equals(Constants.SUCCESS)) {
			gender = PhoneHelper.getUserStringInput("""
				Enter a gender for the contact
					m for Male
					f for Female
					o for others """);
			// Input Validation
			validity = PhoneHelper.validateGender(gender);
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
	private String getPetNameInput() {
		String validity = Constants.FAILURE;
		String petName = "";
		while (!validity.equals(Constants.SUCCESS)) {
			petName = PhoneHelper
			.getUserStringInput("Enter a petname for the contact");
			// Input Validation
			validity = PhoneHelper.validateName(petName);
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
	private String getFullNameInput(String presence) {
		String validity = Constants.FAILURE;
		String fullName = "";
		while (!validity.equals(Constants.SUCCESS)) {
			fullName = PhoneHelper
			.getUserStringInput("Enter the full name for the contact");
			// Input Validation
			validity = PhoneHelper.validateName(fullName);
			// Business Validation
			validity = validateNameExistence(fullName, presence);
			Logger.getInstance().log("checking for " + presence + "message: " + validity);
			System.out.println(validity);
		}
	return fullName;
	}
	// check if name exists
	// if checking for existence print one message
	// if checking for non existence print another message
	private String validateNameExistence(String fullName, String presence) {
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
}