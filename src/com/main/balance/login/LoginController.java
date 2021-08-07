
package com.main.balance.login;

import com.main.balance.db.DB;
import com.main.balance.util.UserInfo;
import com.main.balance.util.Utility;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author TURK
 */
public class LoginController implements Initializable {

    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField passwordPF;
    @FXML
    private Button enterButton;
    @FXML
    private Button createAccountButton;
    @FXML
    private Button deleteAccountButton;
    @FXML
    private Label warningsLabel;
    String username, password;
    DB db = DB.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void enterButtonPressed(ActionEvent event) throws IOException {

        getUserInfo();
        if (db.hasRowInTableForThisCondition("users", "where username = '" + username + "' and password = '" + password + "'")) {
                      UserInfo.username = username;

            msg("Notification: User found");
            Stage stage = new Stage();
            stage.setTitle("Main Stage");
                        stage.initModality(Modality.APPLICATION_MODAL);

           stage.getIcons().add(new Image("file:"+String.valueOf(Paths.get(System.getProperty("user.dir"),"files","main.png"))));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/main/balance/main/main.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            msg("Notification: User not found");
        }
    }

    @FXML
    private void createAccountButtonPressed(ActionEvent event) {
        getUserInfo();
        if (username.equals("") || password.equals("")) {
            msg("Notification: Invalid field");
        } else {
            if (db.hasRowInTableForThisCondition("users", "where username = '" + username + "'")) {
                msg("Notification: Username exists");
            } else {
                if (db.iud("insert into users (username, password,reigster,name,surname) values ('" + username + "','" + password + "','" + Utility.getNowDateTime() + "','Name','Surname')")) {
                  
                    db.iud("insert into balance (username,balance) values ('"+username+"','0')");
                    
                    msg("Notification: Registered succesfully");
                } else {
                    msg("Notification: Issue occured");
                }
            }
        }
    }

    @FXML
    private void deleteAccountButtonPressed(ActionEvent event) {
        getUserInfo();

        if (db.hasRowInTableForThisCondition("users", "where username = '" + username + "' and password = '" + password + "'")) {
           
            db.iud("delete from balance where username = '" + username + "'");
            db.iud("delete from expense where username = '" + username + "'");
            db.iud("delete from expense_categories where username = '" + username + "'");
            db.iud("delete from income where username = '" + username + "'");
            db.iud("delete from income_categories where username = '" + username + "'");
            db.iud("delete from plan_details where username = '" + username + "'");
            db.iud("delete from plans where username = '" + username + "'");
            db.iud("delete from users where username = '" + username + "'");
           
            msg("Notification: Acount is deleted");

        } else {
            msg("Notification: Not found");
        }
    }

    public void msg(String message) {
        warningsLabel.setText(message);
    }

    private void getUserInfo() {
        username = usernameTF.getText().trim();
        password = passwordPF.getText().trim();
    }
}
