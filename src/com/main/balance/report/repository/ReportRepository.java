/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main.balance.report.repository;

import com.main.balance.beans.Expense;
import com.main.balance.beans.Income;
import com.main.balance.db.DB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author TURK
 */
public class ReportRepository {
    
    DB db = DB.getInstance();
private String incomeReportFilter = "";
private String expenseReportFilter = "";

    public String getExpenseReportFilter() {
        return expenseReportFilter;
    }

    public void setExpenseReportFilter(String expenseReportFilter) {
        this.expenseReportFilter = expenseReportFilter;
    }
    public String getIncomeReportFilter() {
        return incomeReportFilter;
    }

    public void setIncomeReportFilter(String incomeReportFilter) {
        this.incomeReportFilter = incomeReportFilter;
    }




    // burda ne edeceyimi unutmusdum, sonra tezden baxilacaq :D
    public ObservableList<Income> getIncomes() throws SQLException {
        
        ObservableList<Income> incomes = FXCollections.observableArrayList();
                                
        ResultSet r = db.getResultSet("select * from income "+incomeReportFilter);
        /*birinci saxta sekilde melumat yaziram ki yoxlamama olsun*/
        //incomes.add(new Income(1, "u1", 122, LocalDateTime.now(), "k1", "gelir 1"));
        while (r.next()) {
            incomes.add(new Income(r.getInt("id"), r.getString("username"),
                    r.getDouble("amount"), r.getTimestamp("register").toLocalDateTime(), r.getString("category"), r.getString("note")));
        }
        db.closeRs(r);
        return incomes;
        
    }
    public ObservableList<Expense> getExpenses() throws SQLException {
        
        ObservableList<Expense> expenses = FXCollections.observableArrayList();
                                
        ResultSet r = db.getResultSet("select * from expense "+expenseReportFilter);
        /*birinci saxta sekilde melumat yaziram ki yoxlamama olsun*/
        //incomes.add(new Income(1, "u1", 122, LocalDateTime.now(), "k1", "gelir 1"));
        while (r.next()) {
            expenses.add(new Expense(r.getInt("id"), r.getString("username"),
                    r.getDouble("amount"), r.getTimestamp("register").toLocalDateTime(), r.getString("category"), r.getString("note")));
        }
        db.closeRs(r);
        return expenses;
        
    }
}
