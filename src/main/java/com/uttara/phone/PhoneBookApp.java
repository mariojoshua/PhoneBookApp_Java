package com.uttara.phone;

import com.uttara.phone.manager.MainMenuManager;

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
        MainMenuManager mainMenuManager = new MainMenuManager();
        mainMenuManager.run();
    }
}
