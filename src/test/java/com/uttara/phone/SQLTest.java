package test.java.com.uttara.phone;

import main.java.com.uttara.phone.MySqlService;

public class SQLTest {
    public static void main(String[] args) {
        MySqlService ms = new MySqlService();
        ms.createContactBook("mushi");
    }
}
