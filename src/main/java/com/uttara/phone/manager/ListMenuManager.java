package com.uttara.phone.manager;

import com.uttara.phone.Logger;
import com.uttara.phone.PhoneBookService;
import com.uttara.phone.helper.PhoneHelper;

public class ListMenuManager {
    private PhoneBookService phoneBookService = null;
	private String result = null;
	private String phoneBookName;

	/**
	 * These methods forms the View of the application these method will display the
	 * menu, accept the inputs, display success/error messages to the user and
	 * invoke the methods of model!
	 */
    public void run(String phoneBookName) {
		this.phoneBookName = phoneBookName;
		listContactsController();	
	} 

    public ListMenuManager() {
        Logger.getInstance().log("In ContactsMenuManager constructor");
		this.phoneBookService = new PhoneBookService();
    }

    private int listContactsMenuView() {
		int menuSize = 5;
		System.out.println("""
			\n\tList Contacts Menu
			*********************************\n
			Press [1] to list contacts by alphabetical listing by name
			Press [2] to list contacts by alphabetical ordering of tags
			Press [3] to list contacts by created date
			Press [4] to list contacts by string length 
			Press [5] to go back
			\n*********************************
			Enter choice\f""");		
		return menuSize;				
	}

	void listContactsController() {
		int choice = 0;
		while (choice != 5) {
			int menuSize = listContactsMenuView();
			choice = PhoneHelper.choiceInputandValidation(choice, menuSize);
			switch (choice) {
				case 1: // Adding a contact menu 
					System.out.println("\f");
					Logger.getInstance().log("Adding contact");
					
					choice = 0;
					break;
				case 2:
					// Edit contact Menu
					Logger.getInstance().log("Editing contact");
					//showsEditContactMenu();
					choice = 0;
					break;
				case 3:
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
					System.out.println("Going back to Main Menu");
					break;
				default:
					System.out.println("Kindly enter choice between 1 to "+ menuSize+"\n");
					break;
			}
		}
	}
}
