/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main.balance.income;

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
public class NewIncomeController implements Initializable {

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
    @FXML
    private Label incomeCategoryLabel;
    @FXML
    private TextField incomeNameTF;
    @FXML
    private DatePicker incomeDateDP;
    @FXML
    private TextField incomeAmountTF;
    @FXML
    private Label warningsLabel;
    @FXML
    private Button confirmButton;

    DB db = DB.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void confirmButtonPressed(ActionEvent event) {

        String incomeName = incomeNameTF.getText().trim();
        String incomAmount = incomeAmountTF.getText().trim();
        LocalDate incomeDate = incomeDateDP.getValue();

        String category = incomeCategoryLabel.getText();

        if (incomeName.equals("")) {
            msg("Notification: Fill the fields");
        } else {
            if (incomAmount.equals("")) {
                msg("Notification: Fill the fields");
            } else {

                if (incomeDate == null) {
                    msg("Notification: Select a date");
                } else {
                    db.iud("insert into income (username,amount,register,category,note) values ('" + UserInfo.username + "','" + incomAmount + "','" + incomeDate.toString() + "','" + category + "','" + incomeName + "') ");

                    db.iud("update balance set balance = balance + " + incomAmount + " where username = '" + UserInfo.username + "'");
                    mainController.loadBalance();
                    msg("Successfully added");
                    thisStage.close();
                }
            }
        }

    }

    public void setIncomeCategoryLabelText(String s) {
        incomeCategoryLabel.setText(s);
    }

    public void msg(String message) {
        warningsLabel.setText(message);
    }

}
