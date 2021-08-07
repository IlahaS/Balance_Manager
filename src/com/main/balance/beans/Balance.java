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
public class Balance {
    private int id;
    private String username;
    private double balance;

    public Balance(int id, String username, double balance) {
        this.id = id;
        this.username = username;
        this.balance = balance;
    }

    public Balance() {
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    
}
