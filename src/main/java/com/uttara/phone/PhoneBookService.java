package main.java.com.uttara.phone;

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
	private IOService ioService = new SerializedTextIOService();


	/*
	 * BUSINESS LOGIC
	 */

	/**
	 * This method will open the file for the phone book given, read one line at a
	 * time, split it, inject it to a bean object add the bean to a List and finally
	 * return it.
	 * 
	 * @param phoneBook name
	 * @return ArrayList of contactBean type elements
	 */



	public void createContactsBook() {
		

	}

	// public void loadsContactsBook () {
	// 	phoneBookService.getUserStringInput("Enter name of an existing phone book"):
	// 	System.out.println();
	// 	phoneBookName = stringScanner.nextLine();
	// 	// input validations!
	// 	result = PhoneUtil.validateName(phoneBookName);
	// 	// until the input validations succeed, keep asking the user to give new input
	// 	// and show error msg

	// 	while (!result.equals(Constants.SUCCESS)) {
	// 		System.out
	// 				.println("Enter proper name which single word, no spl char and starts with letter...");
	// 		phoneBookName = stringScanner.nextLine();
	// 		result = PhoneUtil.validateName(phoneBookName);
	// 	}
	// 	if (phoneBookService.phoneBookExists(phoneBookName)) {
	// 		System.out.println("loading phone book.. " + phoneBookName);
	// 		showsContactsMenu();
	// 	} else {
	// 		System.out.println("Phone Book with name " + phoneBookName + " does not exist.");
	// 	}
	// }

	


	public List<ContactBean> listContacts(String phoneBook) {

		List<ContactBean> contactArray = new ArrayList<ContactBean>();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(phoneBook + ".pb"));
			String line;
			while ((line = br.readLine()) != null) {
				String[] splitFileData = line.split("[\\=|:]+");
				ContactBean bean = new ContactBean();
				Address address = new Address();
				bean.setName(splitFileData[0]);
				bean.setPetName(splitFileData[1]);
				bean.setPhoneNumber1(splitFileData[2]);
				bean.setPhoneNumber2(splitFileData[3]);
				bean.setDateOfBirth(splitFileData[4]);
				//address.setHomeNumber(splitFileData[5]);
				address.setStreetAddress(splitFileData[6]);
				address.setPincode(splitFileData[7]);
				address.setCity(splitFileData[8]);
				address.setState(splitFileData[9]);
				address.setCountry(splitFileData[10]);
				bean.setAddress(address);
				bean.setEmail1(splitFileData[11]);
				bean.setEmail2(splitFileData[12]);
				bean.setEmail3(splitFileData[13]);
				bean.setCreatedDate(splitFileData[14]);
				bean.setModifiedDate(splitFileData[15]);
				bean.setTag(splitFileData[16]);
				contactArray.add(bean);
			}
			return contactArray;
		} catch (IOException e) {
			e.printStackTrace();
			// we should throw a custom business exception here...for now I am returning
			// null!
			return null;
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

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
	 * Check if phoneBookName exists if yes,return true, if not return false and
	 * create a new file with phoneBookName
	 * 
	 * @param
	 * @return
	 * @exception
	 */
	public boolean phoneBookExists(String phoneBookName) {
		Logger.getInstance().log("Checking if " + phoneBookName + " exists");
		return new File(phoneBookName + ".pb").exists();
	}

	public boolean contactNameExists(String phoneBookName, String contactName) {
		Logger.getInstance().log("Checking if contact name " + contactName + " exists in " + phoneBookName);

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(phoneBookName + ".pb"));
			String line;
			while ((line = br.readLine()) != null) {
				String[] contactArray = line.split("[\\=]");
				Logger.getInstance().log(contactArray[0] + ".equalsIgnoreCase " + contactName);
				if (contactArray[0].equalsIgnoreCase(contactName)) {
					// contact name exists
					return true;
				}
			}
			// ContactName does not exist
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			// we should throw a custom business exception here...for now I am returning
			// null!
			return true;
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	/**
	 * Check if Contact name exits in that phone book, if yes return false, if no
	 * then return true
	 */
	public boolean isContactNameUnique(String phoneBookName, String contactName) {
		Logger.getInstance().log("Checking if contact name " + contactName + " exists in " + phoneBookName);

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(phoneBookName + ".pb"));
			String line;
			while ((line = br.readLine()) != null) {
				String[] contactArray = line.split("[\\=]+");
				Logger.getInstance().log(contactArray[0] + ".equalsIgnoreCase " + contactName);
				if (contactArray[0].equalsIgnoreCase(contactName)) {
					Logger.getInstance().log(contactArray[0] + " == " + contactName);
					// contact name is not unique
					return false;
				}
			}
			// ContactName is unique
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			// we should throw a custom business exception here...for now I am returning
			// null!
			return true;
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	public String getCurrentDate() {
		// "dd/MM/yyyy HH:mm:ss")
		date = new Date();
		simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return simpleDateFormat.format(date);
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
