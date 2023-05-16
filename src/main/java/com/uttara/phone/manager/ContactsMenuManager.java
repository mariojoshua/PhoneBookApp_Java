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
					//showsEditContactMenu();
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
					System.out.println("Kindly enter choice between 1 to 6\n");
					break;
			}
		}


	}

    private void addContactController() {
		// take all inputs after validation
		Gender gender = getGenderInput(); 
		String fullName = getFullNameInput(phoneBookName);
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
		System.out.println("Contact addition status: " + message);
		Logger.getInstance().log("Contact addition status: " + message);
    }


	private LocalDateTime getDateInput() {
		String validity = Constants.FAILURE;
		String dateOfBirth = "";
		while (!validity.equals(Constants.SUCCESS)) {
			dateOfBirth = PhoneHelper
			.getUserStringInput("Enter the Date of Birth for the contact");
			// Input Validation
			validity = PhoneHelper.validateDate(dateOfBirth);
			Logger.getInstance().log(validity);
			System.out.println(validity);
		}
		return LocalDate.parse(dateOfBirth.trim()).atStartOfDay();
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
		return Gender.valueOf(gender);
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
	private String getFullNameInput(String phoneBookName) {
		String validity = Constants.FAILURE;
		String fullName = "";
		while (!validity.equals(Constants.SUCCESS)) {
			fullName = PhoneHelper
			.getUserStringInput("Enter the full name for the contact");
			// Input Validation
			validity = PhoneHelper.validateGender(fullName);
			// Business Validation
			if (phoneBookService.contactNameExists(phoneBookName, fullName)) {
				Logger.getInstance().log("Phone book " + phoneBookName + " exists.");
				validity = Constants.SUCCESS;
			}
			Logger.getInstance().log(validity);
			System.out.println(validity);
		}
		return fullName;
	}
}