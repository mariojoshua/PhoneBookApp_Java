package com.uttara.phone.manager.contactMenu;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.uttara.phone.ContactBean;
import com.uttara.phone.Logger;
import com.uttara.phone.PhoneBookService;
import com.uttara.phone.helper.PhoneHelper;

public class SearchContactMenuManager {
    private PhoneBookService phoneBookService = null;
	private String phoneBookName;

    public SearchContactMenuManager() {
        Logger.getInstance().log("In ContactsMenuManager constructor");
		phoneBookService = new PhoneBookService();
    }

    public void run(String phoneBookName) {
        this.phoneBookName = phoneBookName;
		searchContactController();
    }

    private void searchContactController() {
        // get input to search
        String searchInput = PhoneHelper.getAnyContactInput();
        // get all contact details
        List<ContactBean> contactsList = phoneBookService.listContacts(phoneBookName);
        // find occurences everywhere
        // List<String>totalList = contactsList.stream()
        //                         .map(cb -> cb.toString())
        //                         .filter(line -> line.contains(searchInput))
        //                         .collect(Collectors.toList());
        // find occurences in email
        List<String> emailList = findEmailOccurences(contactsList, searchInput);
        // find occurences in phonenumber
        List<String> phoneList = findPhoneOccurences(contactsList, searchInput);
        // find occurences in tags
        List<String> tagsList = contactsList.stream()
                                .filter(cb -> cb.tags().toString().contains(searchInput))
                                .map(cb ->cb.name().getFullName() + "\t" + cb.tags().toString())
                                .collect(Collectors.toList());
        //menu
        Map<String,List<String>> occurenceMap = Map.of("email", emailList,
                                                "phoneNumber", phoneList,
                                                "tags", tagsList);
        searchContactView(occurenceMap);
    }

    private List<String> findPhoneOccurences(List<ContactBean> contactsList, String searchInput) {
        Stream<String> stream1 = contactsList.stream()
                                .filter(cb -> cb.phoneNumbers().get(0).contains(searchInput))
                                .map(cb -> cb.name().getFullName() + "\t" + cb.phoneNumbers().get(0));
        Stream<String> stream2 = contactsList.stream()
                                .filter(cb -> cb.phoneNumbers().get(1).contains(searchInput))
                                .map(cb -> cb.name().getFullName() + "\t" + cb.phoneNumbers().get(1));
        List<String> phoneList = Stream.concat(stream1, stream2)
                                .collect(Collectors.toList());
        return phoneList;
    }

    private List<String> findEmailOccurences(List<ContactBean> contactsList, String searchInput) {
        Stream<String> stream1 = contactsList.stream()
                                .filter(cb -> cb.email().get(0).contains(searchInput))
                                .map(cb -> cb.name().getFullName() + "\t" + cb.email().get(0));
        Stream<String> stream2 = contactsList.stream()
                                .filter(cb -> cb.email().get(1).contains(searchInput))
                                .map(cb -> cb.name().getFullName() + "\t" + cb.email().get(1));
        // Stream<String> stream3 = contactsList.stream()
        //                         .filter(cb -> cb.email().get(2).contains(searchInput))
        //                         .map(cb -> cb.name().getFullName() + " " + cb.email().get(2));
        List<String> emailList = Stream.concat(stream1,stream2)
                                .collect(Collectors.toList());
        return emailList;
    }

    private void searchContactView(Map<String, List<String>> occurenceMap) {
        System.out.println("\n\n==========================");
        System.out.println("\033[1mTotal number of occurences:\033[0m" + (occurenceMap.get("email").size() 
                                                          + occurenceMap.get("phoneNumber").size() 
                                                          + occurenceMap.get("tags").size()));
        System.out.println("==========================");
        System.out.println("Number of occurences in email: " + occurenceMap.get("email").size());
        System.out.println("Matches found:");
        occurenceMap.get("email").forEach(System.out::println);
        System.out.println("--------------------------");
        System.out.println("Number of occurences in Phone number: " + occurenceMap.get("phoneNumber").size());
        System.out.println("Matches found:");
        occurenceMap.get("phoneNumber").forEach(System.out::println);
        System.out.println("--------------------------");
        System.out.println("Number of occurences in tags: " + occurenceMap.get("tags").size());
        System.out.println("Matches found:");
        occurenceMap.get("tags").forEach(System.out::println);
        System.out.println("x------------------------x");
    }

}
