/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main.balance.test;

import com.main.balance.db.DB;
import java.util.List;

/**
 *
 * @author TURK
 */
public class TestClass {
    public static void printDatabaseTestInformation() {
        DB db = DB.getInstance();
        List<String> usernames = db.getColumnRowsFromTableAsList("users", "username", "");
        System.out.println(usernames);
        db.disconnect();
    }
}
