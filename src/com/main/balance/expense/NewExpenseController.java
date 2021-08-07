/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main.balance.expense;

import com.main.balance.db.DB;
import com.main.balance.main.MainController;
import com.main.balance.util.UserInfo;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author TURK
 */
public class NewExpenseController implements Initializable {

    @FXML
    private Label expenseCategoryLabel;
    @FXML
    private TextField expenseNameTF;
    @FXML
    private DatePicker expenseDateDP;
    @FXML
    private TextField expenseAmountTF;
    @FXML
    private Label warningsLabel;
    @FXML
    private Button confirmButton;

    DB db = DB.getInstance();

    private MainController mainController;
    private Stage thisStage;

    public Stage getThisStage() {
        return thisStage;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public MainController getMainController() {
        return mainController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void confirmButtonPressed(ActionEvent event) {

        String expenseName = expenseNameTF.getText().trim();
        String expenseAmount = expenseAmountTF.getText().trim();
        LocalDate expenseDate = expenseDateDP.getValue();

        String category = expenseCategoryLabel.getText();

        if (expenseName.equals("")) {
            msg("Notification: Fill the fields");
        } else {
            if (expenseAmount.equals("")) {
                msg("Notification: Fill the fields");
            } else {

                if (expenseDate == null) {
                    msg("Notification: Select a date");
                } else {
                    db.iud("insert into expense (username,amount,register,category,note) values ('" + UserInfo.username + "','" + expenseAmount + "','" + expenseDate.toString() + "','" + category + "','" + expenseName + "') ");

                    db.iud("update balance set balance = balance - " + expenseAmount + " where username = '" + UserInfo.username + "'");
                    mainController.loadBalance();
                    msg("Notification: Succesfully registered");
                    thisStage.close();
                }
            }
        }

    }

    public void msg(String message) {
        warningsLabel.setText(message);
    }

    public void setExpenseCategoryLabelText(String s) {
        expenseCategoryLabel.setText(s);
    }
}
