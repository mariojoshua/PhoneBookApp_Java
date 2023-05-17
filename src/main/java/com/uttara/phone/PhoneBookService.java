package com.uttara.phone;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.uttara.phone.ioServices.IOService;
import com.uttara.phone.ioServices.IOServiceFactory;
import com.uttara.phone.ioServices.IOServiceFactory.IOServiceName;

/**
 * This class controls the business logic and business validations, Also called
 * the Model Layer
 * 
 * @author mariojoshuaaugustine
 */
public class PhoneBookService {

	private Date date;
	private String modifiedDate;
	private SimpleDateFormat simpleDateFormat;
	private String phoneBookName;
	private IOService ioService = null;


	/*
	 * BUSINESS LOGIC
	 */
	public IOService getIOService() {
		/* 
		 * Change Persistence method here if needed, options
		 * PLAIN_TEXT,
         * SERIALIZED_TEXT,
         * MYSQL_DATABASE;
		*/
		return IOServiceFactory.getIoService(IOServiceName.MYSQL_DATABASE);
	}


	public PhoneBookService() {
		ioService = getIOService();
	}


	public String createContactsBook(String phoneBookName) {
		// check if contact exists, if no then create
		//if yes return message that it already exists	
		if (ioService.createContactBook(phoneBookName) == true) {
			return phoneBookName + " phone book successfully created";
		} else {
			return phoneBookName + " phone book could not be created";
		}		
	}

	public boolean phoneBookExists(String phoneBookName) {
		Logger.getInstance().log("Checking if " + phoneBookName + " exists");
		return ioService.contactBookExists(phoneBookName);
	}

	public boolean contactNameExists(String phoneBookName, String fullName) {
		Logger.getInstance().log("Checking if contact name " + fullName + " exists in " + phoneBookName);
		return ioService.contactExists(new ContactBean(phoneBookName, new Name(fullName))) ;
	}

	public List<ContactBean> listContacts(String phoneBook) {

		List<ContactBean> contactArray = new ArrayList<ContactBean>();

		return contactArray;

	}

	public String createContact(ContactBean contactBean) {
		String message = Constants.FAILURE;
		Logger.getInstance().log("Writing contact " 
			+ contactBean.name().getFullName() 
			+ " to " + contactBean.phoneBookName());
		if (ioService.writeContacts(contactBean)) {
			message = Constants.SUCCESS;
		}
		return message;
	}

	

	/*
	 * For removing a line (a contact), you will have to copy the contents of the
	 * file temporarily to memory except the one line and then write back to the
	 * same file, overwriting the contents.
	 * 
	 */
	

	/*
	 * BUSINESS VALIDATION
	 */




	/**
	 * Check if Contact name exits in that phone book, if yes return false, if no
	 * then return true
	 */
	public boolean isContactNameUnique(String phoneBookName, String contactName) {
		Logger.getInstance().log("Checking if contact name " + contactName + " exists in " + phoneBookName);
		return true;
	}

	public String getCurrentDate() {
		// "dd/MM/yyyy HH:mm:ss")
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	public String editPhoneNumber(String phoneBookName, String contactName, String phoneNumber, int choice) {
		Logger.getInstance().log("In Edit Phone Number method");
		phoneBookService = new PhoneBookService();
		List<ContactBean> contactsArray = phoneBookService.listContacts(phoneBookName);

		// Iterate over contactBean elements until we get our match
		for (ContactBean contactBean : contactsArray) {
			// match contact name to edit
			if (contactBean.getName().equalsIgnoreCase(contactName)) {
				// change modified date
				modifiedDate = phoneBookService.getCurrentDate();
				contactBean.setModifiedDate(modifiedDate);
				Logger.getInstance().log("modifiedDate=" + modifiedDate);
				modifiedDate = null;

				// Edit PhoneNumber 1 and
				if (choice == 1) {
					contactBean.setPhoneNumber1(phoneNumber);
					break;
				}
				// Edit phoneNumber 2
				if (choice == 2) {
					contactBean.setPhoneNumber2(phoneNumber);
					break;
				}

			}
		}

		// Write Back to File
		phoneBookService = null;
		return Constants.SUCCESS;
	}

	public String removePhoneNumber(String phoneBookName, String contactName, int choice) {
		Logger.getInstance().log(" In remove Phone Number method");
		phoneBookService = new PhoneBookService();
		List<ContactBean> contactsArray = phoneBookService.listContacts(phoneBookName);

		// Iterate over contactBean elements until we get our match
		for (ContactBean contactBean : contactsArray) {
			// match contact name to edit
			if (contactBean.getName().equalsIgnoreCase(contactName)) {
				// Make phoneNumber 1 empty
				if (choice == 1) {
					contactBean.setPhoneNumber1("");
					break;
				}
				// Or else Make phoneNumber 2 empty
				if (choice == 2) {
					contactBean.setPhoneNumber2("");
					break;
				}

			}
		}
		// Write Back to File
		phoneBookService = null;
		return Constants.SUCCESS;

	}

	public String addPhoneNumber(String phoneBookName, String contactName, String phoneNumber, int choice) {
		Logger.getInstance().log(" In add Phone Number method");
		phoneBookService = new PhoneBookService();
		List<ContactBean> contactsArray = phoneBookService.listContacts(phoneBookName);

		// Iterate over contactBean elements until we get our match
		for (ContactBean contactBean : contactsArray) {
			// match contact name to edit
			if (contactBean.getName().equalsIgnoreCase(contactName)) {
				// change modified date
				modifiedDate = phoneBookService.getCurrentDate();
				contactBean.setModifiedDate(modifiedDate);
				
				Logger.getInstance().log("modifiedDate=" + modifiedDate);
				modifiedDate = null;
				// Check if phoneNumber 1 is empty, then put new phone number there
				if (contactBean.getPhoneNumber1().isEmpty()) {
					contactBean.setPhoneNumber1(phoneNumber);
					break;
				}
				// Or else check if phoneNumber 2 is empty, then put new phone number there
				if (contactBean.getPhoneNumber1().isEmpty()) {
					contactBean.setPhoneNumber2(phoneNumber);
					break;
				}

			}
		}

		// Write Back to File
		phoneBookService = null;
		return Constants.SUCCESS;

	}

	public String editEmailid(String phoneBookName, String contactName, String email, int choice) {
		Logger.getInstance().log("In edit EmaidID method");
		phoneBookService = new PhoneBookService();
		List<ContactBean> contactsArray = phoneBookService.listContacts(phoneBookName);

		// Iterate over contactBean elements until we get our match
		for (ContactBean contactBean : contactsArray) {
			// match contact name to edit
			if (contactBean.getName().equalsIgnoreCase(contactName)) {
				// change modified date
				modifiedDate = phoneBookService.getCurrentDate();
				contactBean.setModifiedDate(modifiedDate);
				Logger.getInstance().log("modifiedDate=" + modifiedDate);
				modifiedDate = null;
				// Edit PhoneNumber 1
				if (choice == 1) {
					contactBean.setEmail1(email);
					break;
				}
				// Edit phoneNumber 2
				if (choice == 2) {
					contactBean.setEmail2(email);
					break;
				}
				if (choice == 3) {
					contactBean.setEmail3(email);
					break;
				}

			}
		}

		// Write Back to File
		phoneBookService = null;
		return Constants.SUCCESS;

	}

	public String removeEmailId(String phoneBookName, String contactName, int choice) {
		Logger.getInstance().log("In remove EmaidID method");
		phoneBookService = new PhoneBookService();
		List<ContactBean> contactsArray = phoneBookService.listContacts(phoneBookName);

		// Iterate over contactBean elements until we get our match
		for (ContactBean contactBean : contactsArray) {
			// match contact name to edit
			if (contactBean.getName().equalsIgnoreCase(contactName)) {
				// Make phoneNumber 1 empty
				if (choice == 1) {
					contactBean.setEmail1("");
					break;
				}
				// Or else Make phoneNumber 2 empty
				if (choice == 2) {
					contactBean.setEmail2("");
					break;
				}
				if (choice == 3) {
					contactBean.setEmail3("");
					break;
				}

			}
		}
		// Write Back to File
		phoneBookService = null;
		return Constants.SUCCESS;

	}

	public String addEmailId(String phoneBookName, String contactName, String email, int choice) {
		Logger.getInstance().log("In add EmaidID method");
		phoneBookService = new PhoneBookService();
		List<ContactBean> contactsArray = phoneBookService.listContacts(phoneBookName);

		// Iterate over contactBean elements until we get our match
		for (ContactBean contactBean : contactsArray) {
			// match contact name to edit
			if (contactBean.getName().equalsIgnoreCase(contactName)) {
				// change modified date
				modifiedDate = phoneBookService.getCurrentDate();
				contactBean.setModifiedDate(modifiedDate);
				Logger.getInstance().log("modifiedDate=" + modifiedDate);
				modifiedDate = null;
				// Check if phoneNumber 1 is empty, then put new phone number there
				if (contactBean.getEmail1().isEmpty()) {
					contactBean.setEmail1(email);
					break;
				}
				// Or else check if phoneNumber 2 is empty, then put new phone number there
				if (contactBean.getEmail2().isEmpty()) {
					contactBean.setEmail2(email);
					break;
				}
				if (contactBean.getEmail3().isEmpty()) {
					contactBean.setEmail3(email);
					break;
				} else {
					return Constants.FAILURE;
				}

			}
		}

		// Write Back to File
		phoneBookService = null;
		return Constants.SUCCESS;

	}

	public String editContact(String phoneBookName, String contactName, int choice) {
		// same as remove contact, but modify the value of map and then put back into
		// text
		switch (choice) {
		case 1: {
			/*
			 * Edit Phone Number
			 */
			return Constants.SUCCESS;
		}
		case 2: {
			/*
			 * Remove phone number
			 */
			return Constants.SUCCESS;
		}
		case 3: {
			/*
			 * Add new phone number
			 */
			return Constants.SUCCESS;
		}
		case 4: {
			/*
			 * Edit email id
			 */
			return Constants.SUCCESS;
		}
		case 5: {
			/*
			 * Remove email id
			 */
			return Constants.SUCCESS;
		}
		case 6: {
			/*
			 * Add new email id
			 */
			return Constants.SUCCESS;
		}
		default:

			throw new IllegalArgumentException("Unexpected value: " + choice);

		}

	}

}
