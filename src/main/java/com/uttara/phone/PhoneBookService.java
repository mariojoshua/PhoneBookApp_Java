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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * This class controls the business logic and business validations, Also called
 * the Model Layer
 * 
 * @author mariojoshuaaugustine
 */
public class PhoneBookService {

	private static Date date;
	private static String modifiedDate;
	private static SimpleDateFormat simpleDateFormat;
	private static PhoneBookService phoneBookService;

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

	public List<String> readContactsFromFile(String phoneBook) {
		List<String> contactFileArray = new ArrayList<String>();

		try(BufferedReader br = new BufferedReader(new FileReader(phoneBook + ".pb"))) {
			String line;
			while ((line = br.readLine()) != null) {
				contactFileArray.add(line);
			}
			return contactFileArray;
		} catch (IOException e) {
			e.printStackTrace();
			// we should throw a custom business exception here...for now I am returning
			// null!
			return null;
		}
	}

	public String writeContactsToFile(String phoneBookName, String contactLine) {
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(phoneBookName + ".pb", true))) {
			bw.write(contactLine);
			bw.newLine();
			return Constants.SUCCESS;
		} catch (IOException e) {
			e.printStackTrace();
			return "Adding Contact Failed! " + e.getMessage();

		}
	}

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
				address.setHomeNumber(splitFileData[5]);
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

	/**
	 * This method will open the file if it exists or will create a new one with the
	 * phoneBook name given and will write a line with the format name:phnum using
	 * the beans data
	 * 
	 * @param-Contact bean dataholder reference is the first parameter
	 * @param-phone book name is the second parameter.
	 * @return-returns "SUCCESS" else return error message to be shown to user!
	 */
	public String addContact(ContactBean contactBean, String phoneBookName) {

		phoneBookService = new PhoneBookService();
		Logger.getInstance().log("model->addContact() " + contactBean + " phB " + phoneBookName);
		/*
		 * Perform business validations - check if name is already used by another as
		 * file, if its successful, apply business logic only if B.L succeeds,
		 */
		// Set Created and Modified Date
		String createdDate = phoneBookService.getCurrentDate();
		contactBean.setCreatedDate(createdDate);
		contactBean.setModifiedDate(createdDate);
		Logger.getInstance().log("createdDate=" + createdDate);
		createdDate = null;

		String line = contactBean.getName() + "=" + contactBean.getPetName() + ":" + contactBean.getPhoneNumber1() + ":"
				+ contactBean.getPhoneNumber2() + ":" + contactBean.getDateOfBirth() + ":"
				+ contactBean.getAddress().getHomeNumber() + ":" + contactBean.getAddress().getStreetAddress() + ":"
				+ contactBean.getAddress().getPincode() + ":" + contactBean.getAddress().getCity() + ":"
				+ contactBean.getAddress().getState() + ":" + contactBean.getAddress().getCountry() + ":"
				+ contactBean.getEmail1() + ":" + contactBean.getEmail2() + ":" + contactBean.getEmail3() + ":"
				+ contactBean.getCreatedDate() + ":" + contactBean.getModifiedDate() + ":" + contactBean.getTag();
		System.out.println("model->addContact()->line = " + line);
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(phoneBookName + ".pb", true));
			bw.write(line);
			bw.newLine();
			return Constants.SUCCESS;
		} catch (IOException e) {
			e.printStackTrace();
			return "Adding Contact Failed! " + e.getMessage();

		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (date != null) {
				date = null;
			}
			if (simpleDateFormat != null) {
				simpleDateFormat = null;
			}
			if (phoneBookService != null) {
				phoneBookService = null;
			}

		}

		// System.out.println("returning success");

	}

	/*
	 * For removing a line (a contact), you will have to copy the contents of the
	 * file temporarily to memory except the one line and then write back to the
	 * same file, overwriting the contents.
	 * 
	 */
	public String removeContact(String phoneBookName, String contactName) {

		LinkedHashMap<String, String> contactMap = new LinkedHashMap<String, String>();

		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			// Read contacts except Remove that specific contact
			br = new BufferedReader(new FileReader(phoneBookName + ".pb"));
			// split contact by = operator and put name as key and remaining as value in
			// a linkedhashmap
			String line;
			while ((line = br.readLine()) != null) {
				String[] splitFileData = line.split("[\\=]");
				if (!splitFileData[0].equalsIgnoreCase(contactName)) {
					contactMap.put(splitFileData[0], splitFileData[1]);
				}

			}
			// Write linked hashmap contacts to file by overwriting file and adding an =
			// operator between key and value
			bw = new BufferedWriter(new FileWriter(phoneBookName + ".pb", false));
			Set<String> keys = contactMap.keySet();
			for (String key : keys) {
				line = key + "=" + contactMap.get(key);
				bw.write(line);
				bw.newLine();
			}
			return Constants.SUCCESS;
		} catch (IOException e) {
			e.printStackTrace();
			// we should throw a custom business exception here...for now I am returning
			// null!
			return null;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

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
