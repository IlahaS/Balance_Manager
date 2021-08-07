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
public class Plan {
    private int id;
    private String username;
    private String name;
    private LocalDateTime beginDate;
    private LocalDateTime endDate;
    private double amount;
    private LocalDateTime register;

    public Plan() {
    }

    public Plan(int id, String username, String name, LocalDateTime beginDate, LocalDateTime endDate, double amount, LocalDateTime register) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.amount = amount;
        this.register = register;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getRegister() {
        return register;
    }

    public void setRegister(LocalDateTime register) {
        this.register = register;
    }
    
    
    
}
