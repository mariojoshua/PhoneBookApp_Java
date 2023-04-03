package com.uttara.phone;

/**
 * This class is used to start the app , 
 * it contains the main method of the app.
 * 
 * @author mariojoshuaaugustine
 * @version 1.0
 * @since 2021-12-01
 * 
 */

public class PhoneBookApp {
    public static void main(String[] args) {
        PhoneBookManager phoneBookManager = new PhoneBookManager();
        phoneBookManager.run();
    }
}
