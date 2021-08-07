/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main.balance.plan;

import com.main.balance.db.DB;
import com.main.balance.main.MainController;
import com.main.balance.util.UserInfo;
import com.main.balance.util.Utility;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class NewPlanController implements Initializable {

    private MainController mainController;
    private Stage thisStage;

    DB db = DB.getInstance();
    @FXML
    private TextField planNameTF;
    @FXML
    private DatePicker beginDateDP;
    @FXML
    private DatePicker endDateDP;
    @FXML
    private ListView<String> categoriesLV;
    @FXML
    private ListView<Double> amountsLV;
    @FXML
    private Label currentBalanceLabel;
    @FXML
    private TextField amountTF;
    @FXML
    private Button okButton;
    @FXML
    private Label warningsLabel;
    @FXML
    private Label totalAmountLabel;
    public MainController getMainController() {
        return mainController;
    }
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public Stage getThisStage() {
        return thisStage;
    }
    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCategories();
        loadBalances();
        beginDateDP.setValue(LocalDate.now());
        endDateDP.setValue(LocalDate.now());

    }

    @FXML
    private void categoriesLVPressed(MouseEvent event) {
        int index = categoriesLV.getSelectionModel().getSelectedIndex();
        amountsLV.getSelectionModel().select(index);

    }

    @FXML
    private void okButtonPressed(ActionEvent event) {

        String planName = planNameTF.getText().trim();
        if (planName.equals("")) {
            msg("Notification: Give the name for plan");
        } else {

            if (db.hasRowInTableForThisCondition("plans", "where username = '" + UserInfo.username + "' and name = '" + planName + "'")) {
                msg("Notification: This plan exists");
            } else {
                LocalDate beginDate = beginDateDP.getValue();
                LocalDate endDate = endDateDP.getValue();
                if (beginDate.equals(endDate)) {
                    msg("Notification: Start and end date are equal");
                } else {
                    Double totalAmount = Double.parseDouble(totalAmountLabel.getText());
                    if (totalAmount > 0) {

                        if (db.iud("insert into plans (username,name,begin_date,end_date,amount,register) values ('" + UserInfo.username + "','" + planName + "','" + beginDate.toString() + "','" + endDate.toString() + "','" + totalAmount + "','" + LocalDateTime.now() + "') ")) {

                            int planId = Integer.parseInt(db.getColumnSingleRowsFromTableAsList("plans", "id", "where username = '" + UserInfo.username + "' order by id desc"));
                            for (int i = 0; i < categoriesLV.getItems().size(); i++) {
                                db.iud("insert into plan_details (username, plan_id,category,amount) values ('" + UserInfo.username + "','"
                                        + planId + "','" + categoriesLV.getItems().get(i) + "','" + amountsLV.getItems().get(i) + "')");

                            }
                            msg("Notification: Successfully added");
                            mainController.loadPlans();

                            thisStage.close();

                        } else {
                            msg("Notification: Error eccoured");
                        }

                    } else {
                        msg("Notification: Enter valid amount");
                    }
                }
            }

        }

    }
    private void loadCategories() {
        categoriesLV.getItems().clear();
        categoriesLV.getItems().addAll(db.getColumnRowsFromTableAsList("expense_categories", "category", "where username = '" + UserInfo.username + "'"));

        for (int i = 0; i < categoriesLV.getItems().size(); i++) {
            amountsLV.getItems().add(0.0);

        }

    }
    private void loadBalances() {
        currentBalanceLabel.setText(db.getColumnSingleRowsFromTableAsList("balance", "balance", "where username = '" + UserInfo.username + "'"));
    }

    @FXML
    private void amountTFKeyReleased(KeyEvent event) {
        String amount = amountTF.getText().trim();
        if (amount.equals("")) {
            int index = categoriesLV.getSelectionModel().getSelectedIndex();
            amountsLV.getItems().set(index, Double.parseDouble("0.00"));
        } else {
            int index = categoriesLV.getSelectionModel().getSelectedIndex();
            amountsLV.getItems().set(index, Double.parseDouble(amount));

        }
        double totalAmount = 0;
        for (int i = 0; i < amountsLV.getItems().size(); i++) {
            totalAmount += amountsLV.getItems().get(i);
        }
        totalAmountLabel.setText(String.valueOf(totalAmount));
    }
    public void msg(String message) {
        warningsLabel.setText(message);
    }
}
