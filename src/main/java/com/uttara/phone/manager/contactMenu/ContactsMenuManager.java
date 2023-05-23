package com.uttara.phone.manager.contactMenu;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.uttara.phone.Constants;
import com.uttara.phone.ContactBean;
import com.uttara.phone.Logger;
import com.uttara.phone.Name;
import com.uttara.phone.Name.Gender;
import com.uttara.phone.PhoneBookService;
import com.uttara.phone.helper.PhoneHelper;

public class ContactsMenuManager {
    private PhoneBookService phoneBookService = null;
	private String phoneBookName;
	private EditContactMenuManager editContactMenuManager;
	private AddContactMenuManager addContactMenuManager;

	/**
	 * These methods forms the View of the application these method will display the
	 * menu, accept the inputs, display success/error messages to the user and
	 * invoke the methods of model!
	 */

    public ContactsMenuManager() {
        Logger.getInstance().log("In ContactsMenuManager constructor");
		this.phoneBookService = new PhoneBookService();
		this.editContactMenuManager = new EditContactMenuManager();
		this.addContactMenuManager = new AddContactMenuManager();
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
					editContactMenuManager.run(phoneBookName);
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
	 * ADD CONTACT METHODS
	 */
	private void addContactController() {
		// take all inputs after validation
		Gender gender = PhoneHelper.getGenderInput(); 
		String fullName = PhoneHelper.getFullNameInput(phoneBookName, Constants.ABSENT);
		String petName = PhoneHelper.getPetNameInput();
		Name name = new Name(gender, fullName, petName);
		String phoneNumber1 = PhoneHelper.getPhoneNumberInput("first");
		String phoneNumber2 = PhoneHelper.getPhoneNumberInput("second");
		List<String> phoneNumbers = List.of(phoneNumber1, phoneNumber2);
		String address = PhoneHelper.getAddressInput();
		List<String> tags = PhoneHelper.getTagsInputs();
		String email1 = PhoneHelper.getEmailInput("first");
		String email2 = PhoneHelper.getEmailInput("second");
		String email3 = PhoneHelper.getEmailInput("third");
		List<String> email = List.of(email1, email2, email3);
		Map<String, LocalDateTime> dates = 
		Map.of("dateOfBirth", PhoneHelper.getDateInput());
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

}