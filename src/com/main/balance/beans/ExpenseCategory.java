/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main.balance.beans;

/**
 *
 * @author TURK
 */
public class ExpenseCategory {
    private int id;
    private String username;
    private String category;

    public ExpenseCategory() {
    }

    public ExpenseCategory(int id, String username, String category) {
        this.id = id;
        this.username = username;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    
    
}
