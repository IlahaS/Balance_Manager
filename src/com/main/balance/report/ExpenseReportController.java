/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main.balance.report;

import com.main.balance.beans.Expense;
import com.main.balance.beans.Income;
import com.main.balance.report.repository.ReportRepository;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author TURK
 */
public class ExpenseReportController implements Initializable {
    
    
    
 private ReportRepository reportRepository;
    @FXML
    private DatePicker beginDateDP;
    @FXML
    private Button filterButton;
    @FXML
    private TableView<Expense> reportTV;
    @FXML
    private TableColumn<Expense, Integer> idCol;
    @FXML
    private TableColumn<Expense, String> noteCol;
    @FXML
    private TableColumn<Expense, Double> amountCol;
    @FXML
    private TableColumn<Expense, String> categoryCol;
    @FXML
    private TableColumn<Expense, LocalDateTime> registerCol;
    @FXML
    private DatePicker endDateDP;
    @FXML
    private Label rowCountLabel;
    @FXML
    private Label totalAmountLabel;
    @FXML
    private Label warningsLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        beginDateDP.setValue(LocalDate.now());
        
        endDateDP.setValue(LocalDate.now());
        
        reportRepository = new ReportRepository();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        registerCol.setCellValueFactory(new PropertyValueFactory<>("register"));
        
        try {
            loadRows();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    
    @FXML
    private void filterButtonPressed(ActionEvent event) throws SQLException {
        
        LocalDate beginDate = beginDateDP.getValue();
        LocalDate endDate = endDateDP.getValue();
        
        reportRepository.setExpenseReportFilter("where register between '" + beginDate + "' and '" + endDate + "'");
        
        loadRows();
        
    }
    
    private void loadRows() throws SQLException {
        
        reportTV.setItems(reportRepository.getExpenses());//expenseden melumatlari goturmek ucun

        rowCountLabel.setText(String.valueOf(reportTV.getItems().size()));
        double totalamount = 0;
        
        for (int i = 0; i < reportTV.getItems().size(); i++) {
            totalamount += reportTV.getItems().get(i).getAmount();
        }
        totalAmountLabel.setText(String.valueOf(totalamount));
        
    }
    
     
    
}
