/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main.balance.beans;

import java.time.LocalDateTime;

/**
 *
 * @author TURK
 */
public class Income {
    private int id;
    private String username;
    private double amount;
    private LocalDateTime reigster;
    private String category;
    private String note;

    public Income(int id, String username, double amount, LocalDateTime reigster, String category, String note) {
        this.id = id;
        this.username = username;
        this.amount = amount;
        this.reigster = reigster;
        this.category = category;
        this.note = note;
    }

    public Income() {
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getReigster() {
        return reigster;
    }

    public void setReigster(LocalDateTime reigster) {
        this.reigster = reigster;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    
}
