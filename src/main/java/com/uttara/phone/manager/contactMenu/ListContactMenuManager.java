package com.uttara.phone.manager.contactMenu;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.uttara.phone.ContactBean;
import com.uttara.phone.Logger;
import com.uttara.phone.PhoneBookService;
import com.uttara.phone.helper.PhoneHelper;

public class ListContactMenuManager {
    private PhoneBookService phoneBookService = null;
	private String phoneBookName;

	/**
	 * These methods forms the View of the application these method will display the
	 * menu, accept the inputs, display success/error messages to the user and
	 * invoke the methods of model!
	 */

     public ListContactMenuManager() {
        Logger.getInstance().log("In ContactsMenuManager constructor");
		phoneBookService = new PhoneBookService();
    }

	public void run(String phoneBookName) {
		this.phoneBookName = phoneBookName;
		listContactController();	
	} 

    /*
	 * LIST CONTACT METHODS
	 */
	int listContactsView() {
		int menuSize = 5;
		System.out.println("""
			\n\tList Contact Menu
			*********************************\n
			Press [1] to list contacts by alphabetical listing by name
            Press [2] to list contacts by alphabetical ordering of tags
            Press [3] to list contacts by created date
            Press [4] to list contacts by string length
			Press [5] to return to Contacts Menu	
			\n*********************************
			Enter choice\f""");		
		return menuSize;	
	}
    
    void listContactController() {
		int choice = 0;
        //int menuSize = 0;
        Comparator<ContactBean> comparator = null;
        List<ContactBean> allContacts = phoneBookService.listContacts(phoneBookName);
		System.out.println(allContacts);
		while (choice != 5) {
			int menuSize = listContactsView();
            if (allContacts.size() == 0) {
                choice = 5;
                System.out.println("No contacts are present in the contacts book to list");
            } else {
                choice = PhoneHelper.choiceInputandValidation(choice, menuSize);
            }
			switch (choice) {
				case 1: // Sort by alphabetical listing of name
				    comparator = (c1,c2) -> c1.name().getFullName().compareTo(c2.name().getFullName());	
                    sortAndPrint(allContacts, comparator);
                    System.out.println("Contacts listed successfully in Alphabetical Order");      
					choice = 0;
					break;
				case 2: // Sort by alphabetical ordering of tags
                    comparator = (c1, c2) -> c1.tags().stream().sorted().collect(Collectors.joining())
                                    .compareTo(c2.tags().stream().sorted().collect(Collectors.joining()));	
                    sortAndPrint(allContacts, comparator);
					System.out.println("Contacts listed successfully in Alphabetical Order of tags");
					choice = 0;
					break;
				case 3: // Sort Based on created Date
                    comparator = (c1,c2) -> c1.dates().get("createdDate").compareTo(c2.dates().get("createdDate"));	
                    sortAndPrint(allContacts, comparator);
					System.out.println("Contacts listed successfully by created Date order");
					choice = 0;
					break;
                case 4: // sort based on String Length
                    comparator = (c1,c2) -> c1.toString().compareTo(c2.toString());	
                    sortAndPrint(allContacts, comparator);
					System.out.println("Contacts listed successfully based on ascending String length");
					choice = 0;
					break;
                case 5:
					System.out.println("returning to Contacts Menu...");
					break;
				default:
					System.out.println("Kindly enter choice between 1 to "+ menuSize+"\n");
					break;

			}
		}
		
	}

    private void sortAndPrint(List<ContactBean> allContacts, Comparator<ContactBean> comparator) {
        allContacts.stream()
                    .sorted(comparator)
                    .forEach(System.out::println);  
    }
    
}
