package main.java.com.uttara.phone;

import java.util.List;

public class PlainTextIOService extends IOService {





    @Override
    public Boolean updateContacts(ContactBean contactBean) {
        // TODO Auto-generated method stub
        return null;
    }





    @Override
    public Boolean createContactBook(String phoneBookName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean deleteContactBook(String phoneBookName) {
        // TODO Auto-generated method stub
        return null;
    }

    	/**
	 * This method will open the file for the phone book given, read one line at a
	 * time, split it, inject it to a bean object add the bean to a List and finally
	 * return it.
	 * 
	 * @param phoneBook name
	 * @return ArrayList of contactBean type elements
	 */
    @Override
    public List<ContactBean> readContact(String phoneBookName) {
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
        throw new UnsupportedOperationException("Unimplemented method 'readContact'");
    }

    @Override
    public Boolean writeContacts(ContactBean contactBean, String phoneBookName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeContacts'");
    }

    @Override
    public Boolean deleteContact(String fullName, String phoneBookName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteContact'");
    }


    	/**
	 * Check if phoneBookName exists if yes,return true, if not return false and
	 * create a new file with phoneBookName
	 * 
	 * @param
	 * @return
	 * @exception
	 */
    @Override
    public Boolean contactBookExists(String phoneBookName) {
        return new File(phoneBookName + ".pb").exists();
        throw new UnsupportedOperationException("Unimplemented method 'contactBookExists'");
    }

    @Override
    public Boolean contactExists(String fullName, String phoneBookName) {
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
        throw new UnsupportedOperationException("Unimplemented method 'contactExists'");
    }
    
}
