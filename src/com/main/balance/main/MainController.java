/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main.balance.main;

import com.main.balance.db.DB;
import com.main.balance.expense.NewExpenseController;
import com.main.balance.income.NewIncomeController;
import com.main.balance.plan.NewPlanController;
import com.main.balance.util.UserInfo;
import com.main.balance.util.Utility;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DateStringConverter;

/**
 * FXML Controller class
 *
 * @author Ilahe
 */
public class MainController implements Initializable {

    @FXML
    private ListView<String> incomeCategoriesLV;
    @FXML
    private TextField incomeCategoryTF;
    @FXML
    private Button newIncomeCategoryButton;
    @FXML
    private Button updateIncomeCategoryButton;
    @FXML
    private Button deleteIncomeCategoryButton;
    @FXML
    private Button newIncomeButton;
    @FXML
    private Button incomeReportButton;
    @FXML
    private ListView<String> expenseCategoriesLV;
    @FXML
    private TextField expenseCategoryTF;
    @FXML
    private Button newExpenseCategoryButton;
    @FXML
    private Button updateExpenseCategoryButton;
    @FXML
    private Button deleteExpenseCategoryButton;
    @FXML
    private Button newExpenseButton;
    @FXML
    private Button expenseReportButton;
    @FXML
    private Button newPlanButton;
    @FXML
    private Label currentBalanceLabel;
    @FXML
    private Label warningsLabel;

    DB db = DB.getInstance();
    @FXML
    private ListView<String> planLV;
    @FXML
    private ListView<HBox> planReportLV;
    @FXML
    private Label totalAmountLabel;
    @FXML
    private Label planBeginDateLabel;
    @FXML
    private Label planEndDateLabel;
    @FXML
    private PieChart reportPC;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadInformation();
        loadBalance();
        loadPlans();

    }

    @FXML
    private void incomeCategoriesLVPressed(MouseEvent event) {
        String selectedCategory = incomeCategoriesLV.getSelectionModel().getSelectedItem();
        incomeCategoryTF.setText(selectedCategory);

    }

    @FXML
    private void newIncomeCategoryButtonPressed(ActionEvent event) {

        String incomeCategory = incomeCategoryTF.getText().trim();
        if (incomeCategory.equals("")) {
            msg("Notification: Fill the catergory field");
        } else {
            if (db.hasRowInTableForThisCondition("income_categories", "where username='" + UserInfo.username + "' and category='" + incomeCategory + "'")) {
                msg("Notification: This category exists");
            } else {
                db.iud("insert into income_categories (username,category) values ('" + UserInfo.username + "','" + incomeCategory + "') ");
                msg("Notification: Successfully added");
                loadInformation();

            }
        }
    }

    @FXML
    private void updateIncomeCategoryButtonPressed(ActionEvent event) {

        String newCategory = incomeCategoryTF.getText().trim();
        String oldCategory = incomeCategoriesLV.getSelectionModel().getSelectedItem();
        if (newCategory.equals("")) {

            msg("Notification: Fill the catergory field");

        } else {
            if (newCategory.equals(oldCategory)) {
                msg("Notification: No any changes found");
            } else {
                if (db.hasRowInTableForThisCondition("income_categories", "where username='" + UserInfo.username + "' and category = '" + newCategory + "'")) {
                    msg("Notification: This category exists");

                } else {
                    db.iud("update income_categories set category = '" + newCategory + "' where username= '" + UserInfo.username + "' and category = '" + oldCategory + "'");
                    msg("Notification: Successfully updated");
                    loadInformation();
                }
            }
        }

    }

    @FXML
    private void deleteIncomeCategoryButtonPressed(ActionEvent event) {
        String selected = incomeCategoriesLV.getSelectionModel().getSelectedItem();
        if (selected == null) {
            msg("Notification: Select a category");
        } else {

                db.iud("delete from income_categories where username = '" + UserInfo.username + "' and category = '" + selected + "'");

                msg("Notification: Successfully deleted");
                loadInformation();


        }
    }

    @FXML
    private void newIncomeButtonPressed(ActionEvent event) throws IOException {
        String selectedCateogry = incomeCategoriesLV.getSelectionModel().getSelectedItem();
        if (selectedCateogry == null) {
            msg("Notification: Select a category");

        } else {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setTitle("New Imcome Stage");
            stage.getIcons().add(new Image("file:" + String.valueOf(Paths.get(System.getProperty("user.dir"), "files", "main.png"))));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/main/balance/income/newIncome.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            NewIncomeController controller = loader.getController();
            controller.setIncomeCategoryLabelText(selectedCateogry);
            controller.setMainController(this);
            controller.setThisStage(stage);

        }
    }

    @FXML
    private void incomeReportButtonPressed(ActionEvent event) {
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setTitle("Delails Info Stage");
            stage.getIcons().add(new Image("file:" + String.valueOf(Paths.get(System.getProperty("user.dir"), "files", "main.png"))));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/main/balance/report/incomeReport.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void expenseCategoriesLVPressed(MouseEvent event) {
        String selectedCategory = expenseCategoriesLV.getSelectionModel().getSelectedItem();
        expenseCategoryTF.setText(selectedCategory);
    }

    @FXML
    private void newExpenseCategoryButtonPressed(ActionEvent event) {
        String expenseCateogry = expenseCategoryTF.getText().trim();
        if (expenseCateogry.equals("")) {
            msg("Notification: Fill the catergory field");
        } else {
            if (db.hasRowInTableForThisCondition("expense_categories", "where username='" + UserInfo.username + "' and category='" + expenseCateogry + "'")) {
                msg("Notification: This category exists");
            } else {
                db.iud("insert into expense_categories (username,category) values ('" + UserInfo.username + "','" + expenseCateogry + "') ");
                msg("Notification: Successfully added");
                loadInformation();
            }
        }
    }

    @FXML
    private void updateExpenseCategoryButtonPressed(ActionEvent event) {

        String newCategory = expenseCategoryTF.getText().trim();
        String oldCategory = expenseCategoriesLV.getSelectionModel().getSelectedItem();
        if (newCategory.equals("")) {

            msg("Notification: Fill the catergory field");

        } else {
            if (newCategory.equals(oldCategory)) {
                msg("Notification: No any changes found");
            } else {
                if (db.hasRowInTableForThisCondition("expense_categories", "where username='" + UserInfo.username + "' and category = '" + newCategory + "'")) {
                    msg("Notification: This category exists");

                } else {
                    db.iud("update expense_categories set category = '" + newCategory + "' where username= '" + UserInfo.username + "' and category = '" + oldCategory + "'");
                    msg("Notification: Successfully updated");
                    loadInformation();
                }
            }
        }
    }

    @FXML
    private void deleteExpenseCategoryButtonPressed(ActionEvent event) {
        String selected = expenseCategoriesLV.getSelectionModel().getSelectedItem();
        if (selected == null) {
            msg("Notification: Select a category");
        } else {
            db.iud("delete from expense_categories where username = '" + UserInfo.username + "' and category = '" + selected + "'");

            msg("Notification: Successfully deleted");
            loadInformation();
        }

    }

    @FXML
    private void newExpenseButtonPressed(ActionEvent event) throws IOException {
        String selectedCateogry = expenseCategoriesLV.getSelectionModel().getSelectedItem();
        if (selectedCateogry == null) {
            msg("Notification: Select a category");

        } else {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setTitle("New Expence Stage");
            stage.getIcons().add(new Image("file:" + String.valueOf(Paths.get(System.getProperty("user.dir"), "files", "main.png"))));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/main/balance/expense/newExpense.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            NewExpenseController controller = loader.getController();
            controller.setExpenseCategoryLabelText(selectedCateogry);
            controller.setMainController(this);
            controller.setThisStage(stage);

        }
    }

    @FXML
    private void expenseReportButtonPressed(ActionEvent event) {
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Expence Details Info");
            stage.getIcons().add(new Image("file:" + String.valueOf(Paths.get(System.getProperty("user.dir"), "files", "main.png"))));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/main/balance/report/expenseReport.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void planLVPressed(MouseEvent event) {

        String selected = planLV.getSelectionModel().getSelectedItem();
        if (selected == null) {
            msg("Choose an item");
        } else {
            String totalAmount = db.getColumnSingleRowsFromTableAsList("plans", "amount", "where username = '" + UserInfo.username + "' and name = '" + selected + "'");

            totalAmountLabel.setText(totalAmount);

            String planBeginDate = db.getColumnSingleRowsFromTableAsList("plans", "begin_date", "where username = '" + UserInfo.username + "' and name = '" + selected + "'");

            planBeginDate = planBeginDate.substring(0, 10);

            planBeginDateLabel.setText(planBeginDate);

            String planEndDate = db.getColumnSingleRowsFromTableAsList("plans", "end_date", "where username = '" + UserInfo.username + "' and name = '" + selected + "'");

            planEndDate = planEndDate.substring(0, 10);

            planEndDateLabel.setText(planEndDate);

            //------------
            String planId = db.getColumnSingleRowsFromTableAsList("plans", "id", "where username = '" + UserInfo.username + "' and name = '" + selected + "'");

            List<String> planGategories = db.getColumnRowsFromTableAsList("plan_details", "category", "where username = '" + UserInfo.username + "' and plan_id = '" + planId + "'");
            List<String> planAmounts = db.getColumnRowsFromTableAsList("plan_details", "amount", "where username = '" + UserInfo.username + "' and plan_id = '" + planId + "'");
            planReportLV.getItems().clear();

            for (int i = 0; i < planGategories.size(); i++) {
                HBox hBox = new HBox();
                String category = planGategories.get(i);

                Label planNameLabel = new Label(category + " : ");
                planNameLabel.setFont(new Font("Arial", 16));
                planNameLabel.setTextFill(Color.web("#ff0000"));
                hBox.getChildren().add(planNameLabel);

                double totalAmountForCategory = db.getColumnSumFromTable("expense", "amount", "where username = '" + UserInfo.username + "' and category='" + category + "' and register between '" + planBeginDate + "' and '" + planEndDate + "' ");

                double categoryAmount = Double.parseDouble(planAmounts.get(i));

                Label planAmountLabel = new Label("Planned amount : " + categoryAmount);

                planAmountLabel.setFont(new Font("Arial", 12));
                planAmountLabel.setTextFill(Color.web("#00ff00"));

                hBox.getChildren().add(planAmountLabel);

                double progress = totalAmountForCategory / categoryAmount;
                if (progress < 0) {
                    progress = 0;
                }
                if (progress > 1) {
                    progress = 1;
                }
                ProgressBar progressBar = new ProgressBar(progress);
                hBox.getChildren().add(progressBar);

                Label spendAmountLabel = new Label("Spent amount : " + totalAmountForCategory);

                spendAmountLabel.setFont(new Font("Arial", 12));
                spendAmountLabel.setTextFill(Color.web("#0000ff"));

                hBox.getChildren().add(spendAmountLabel);

                Label percentLabel = new Label("  Percentage : " + (int) ((progress) * 100) + " %");

                percentLabel.setFont(new Font("Arial", 12));
                percentLabel.setTextFill(Color.web("#ff0000"));

                hBox.getChildren().add(percentLabel);
                /*-----------------------------------------------------------------*/

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate beginDate = LocalDate.parse(planBeginDate, formatter);
                LocalDate endDate = LocalDate.parse(planEndDate, formatter);
                boolean tempBoolean = false;
                String temp = "Yaxşı";

                int planDay = endDate.compareTo(beginDate);

                int planLastDay = LocalDate.now().compareTo(beginDate);

                double normalPul = categoryAmount / planDay * planLastDay;
                if (totalAmountForCategory > normalPul) {
                    tempBoolean = false;
                } else {
                    tempBoolean = true;

                }
                if (tempBoolean == true) {

                } else {
                    temp = "Pis";
                }

                Label tempLabel = new Label("  Temp : " + temp);

                tempLabel.setFont(new Font("Arial", 12));
                tempLabel.setTextFill(Color.web("#ff0000"));

                hBox.getChildren().add(tempLabel);

                planReportLV.getItems().add(hBox);

            }
        }

    }

    @FXML
    private void newPlanButtonPressed(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Plan Details Info Stage");
        stage.getIcons().add(new Image("file:" + String.valueOf(Paths.get(System.getProperty("user.dir"), "files", "main.png"))));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/main/balance/plan/newPlan.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        NewPlanController controller = loader.getController();
        controller.setMainController(this);
        controller.setThisStage(stage);
    }

    public void msg(String message) {
        warningsLabel.setText(message);
    }

    private void loadInformation() {
        incomeCategoriesLV.getItems().clear();
        List<String> incomeCategoriesArrayList = db.getColumnRowsFromTableAsList("income_categories", "category", "where username = '" + UserInfo.username + "'");
        incomeCategoriesLV.getItems().addAll(incomeCategoriesArrayList);
        //-------------
        expenseCategoriesLV.getItems().clear();
        List<String> expenseCategoriesArrayList = db.getColumnRowsFromTableAsList("expense_categories", "category", "where username = '" + UserInfo.username + "'");
        expenseCategoriesLV.getItems().addAll(expenseCategoriesArrayList);

    }

    public void loadBalance() {
        currentBalanceLabel.setText(db.getColumnSingleRowsFromTableAsList("balance", "balance", "where username = '" + UserInfo.username + "'"));
    }

    public void loadPlans() {
        planLV.getItems().clear();
        LocalDate now = LocalDate.now();
        planLV.getItems().addAll(db.getColumnRowsFromTableAsList("plans", "name", "where username = '" + UserInfo.username + "' and end_date>'" + now + "' "));
    }

    @FXML
    private void incomeTabPressed(MouseEvent event) throws SQLException {

        ResultSet r = db.getResultSet("Select category,sum(amount) as amount From income where username = '" + UserInfo.username + "' group by category");
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();

        while (r.next()) {
            PieChart.Data data = new PieChart.Data(r.getString("category"), r.getDouble("amount"));
            list.add(data);
        }
        reportPC.setData(list);
        db.closeRs(r);
    }

    @FXML
    private void expenseTabPressed(MouseEvent event) throws SQLException {
        ResultSet r = db.getResultSet("Select category,sum(amount) as amount From expense where username = '" + UserInfo.username + "' group by category");
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();

        while (r.next()) {
            PieChart.Data data = new PieChart.Data(r.getString("category"), r.getDouble("amount"));
            list.add(data);
        }
        reportPC.setData(list);
        db.closeRs(r);
    }
}
