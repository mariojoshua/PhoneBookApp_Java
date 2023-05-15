package com.uttara.phone.manager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.uttara.phone.Constants;
import com.uttara.phone.ContactBean;
import com.uttara.phone.Logger;
import com.uttara.phone.Name;
import com.uttara.phone.PhoneBookService;
import com.uttara.phone.PhoneHelper;
import com.uttara.phone.Name.Gender;

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

	public void run() {
		contactsMenuController(phoneBookName);	
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

	public void contactsMenuController(String phoneBookName) {
		int choice = 0;
		while (choice != 6) {
			int menuSize = contactsMenuView();
			choice = PhoneHelper.choiceInputandValidation(choice, menuSize);
			switch (choice) {
				case 1: // Adding a contact menu 
					System.out.println("\f");
					Logger.getInstance().log("Adding contact");
					addContactController(phoneBookName);
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

    private void addContactController(String phoneBookName) {
		// take all inputs after validation
		Gender gender = getGenderInput(); 
		String fullName = getFullNameInput();
		String petName = getPetNameInput();
		Name name = new Name(gender, fullName, petName);
		List<String> phoneNumbers = null;
		String address = null;
		List<String> tags = null;
		List<String> email = null;
		Map<String, LocalDateTime> dates = null;
		// add to contactbean
		ContactBean contactBean = new ContactBean
			(phoneBookName, name, phoneNumbers, address,tags,email,dates);
		// invoke service to add to io
		String message = phoneBookService.createContact(phoneBookName);
		System.out.println(message);
		Logger.getInstance().log(message);
    }


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

	private String getPetNameInput() {
		return null;
	}

	private String getFullNameInput() {
		return null;
	}
}