package com.uttara.phone.manager.contactMenu;

import com.uttara.phone.Constants;
import com.uttara.phone.Logger;
import com.uttara.phone.PhoneBookService;
import com.uttara.phone.helper.PhoneHelper;

public class EditContactMenuManager {
    private PhoneBookService phoneBookService = null;
	private String phoneBookName;

	/**
	 * These methods forms the View of the application these method will display the
	 * menu, accept the inputs, display success/error messages to the user and
	 * invoke the methods of model!
	 */

     public EditContactMenuManager() {
        Logger.getInstance().log("In ContactsMenuManager constructor");
		phoneBookService = new PhoneBookService();
    }

	public void run(String phoneBookName) {
		this.phoneBookName = phoneBookName;
		editContactController();	
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
		// get fullname and check if contact exists in order to edit
		String fullName = PhoneHelper.getFullNameInput(phoneBookName,Constants.PRESENT);
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

	private int addEmailPhoneMenuView() {
		int menuSize = 3;
		System.out.println("""
			\n\tAdd Email/Phone number Menu
			*********************************\n
			Press [1] to add new email
			Press [2] to add new phone number
			Press [3] to return to edit Contacts Menu	
			\n*********************************
			Enter choice\f""");		
		return menuSize;
	}

	private void addEmailPhoneController(String fullName) {
		int choice = 0;
		while (choice != 3) {
			addEmailPhoneMenuView();
			int menuSize = editContactsView();
			choice = PhoneHelper.choiceInputandValidation(choice, menuSize);
			switch (choice) {
				case 1: // Creates a Contacts Book file/entry on disk/sql 
					addNewEmail(fullName);
					choice = 0;
					break;
				case 2: // Loads Contacts Book Name from file
					System.out.println("Contact book loaded");
					addNewPhoneNumber(fullName);
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

	private void addNewPhoneNumber(String fullName) {
		// Take phone num input after validation
		// if < 2 phonenumbers are present
		// add phonenumber
		// if 2 phonenumbers are present,  display phonenumbers
		// and give option to overwrite any num
		System.out.println("Not yet implemented");

	}

	private void addNewEmail(String fullName) {
		// Take email input after validation
		// if < 3 emails are present
		// add email
		// if 3 emails are present, display email id's and
		// give option to overwrite any email
		System.out.println("Not yet implemented");
	}

	private int editContactInfoView() {
		int menuSize = 3;
		System.out.println("""
			\n\tAdd Email/Phone number Menu
			*********************************\n
			Press [1] to remove contact Info
			Press [2] to change contact Info
			Press [3] to return to edit Contacts Menu	
			\n*********************************
			Enter choice\f""");		
		return menuSize;
	}

	private void editContactInfoController(String fullName) {
		int choice = 0;
		while (choice != 3) {
			int menuSize = editContactInfoView();
			choice = PhoneHelper.choiceInputandValidation(choice, menuSize);
			switch (choice) {
				case 1: // Creates a Contacts Book file/entry on disk/sql 
					removeContactInfoController(fullName);
					choice = 0;
					break;
				case 2: // Loads Contacts Book Name from file
					System.out.println();
					changeContactInfoController(fullName);
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

	private int chooseContactInfoMenuView(String action) {
		int menuSize = 5;
		System.out.println("\n\tContact Info "+ action +" Menu"
		 + "\n*********************************" 
		 +	"\nChoose contact info to " + action);
		System.out.println("""
				Press [1] for gender
				Press [2] for petname
				Press [3] for dateOfBirth
				Press [4] for Address
				Press [5] to go back to previous menu
			\n*********************************
			Enter choice\f""");		
		return menuSize;
	}

	private void changeContactInfoController(String fullName) {
		int choice = 0;
		while (choice != 3) {
			int menuSize = chooseContactInfoMenuView("Change");
			choice = PhoneHelper.choiceInputandValidation(choice, menuSize);
			switch (choice) {
				case 1: // Creates a Contacts Book file/entry on disk/sql 
					removeContactInfoController(fullName);
					choice = 0;
					break;
				case 2: // Loads Contacts Book Name from file
					System.out.println();
					changeContactInfoController(fullName);
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

	private void removeContactInfoController(String fullName) {
		int choice = 0;
		while (choice != 3) {
			int menuSize = chooseContactInfoMenuView("Remove");
			choice = PhoneHelper.choiceInputandValidation(choice, menuSize);
			switch (choice) {
				case 1: // Creates a Contacts Book file/entry on disk/sql 
					removeContactInfoController(fullName);
					choice = 0;
					break;
				case 2: // Loads Contacts Book Name from file
					System.out.println();
					changeContactInfoController(fullName);
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
}
