package com.uttara.phone;

import com.uttara.phone.ioServices.MySqlContactWriter;
import com.uttara.phone.ioServices.MySqlService;

public class PhoneBookApp {
    public static void main(String[] args) {
        PhoneBookManager phoneBookManager = new PhoneBookManager();
        phoneBookManager.run();
        new MySqlContactWriter().
    }
}
