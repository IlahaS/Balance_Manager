/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main.balance;

import com.main.balance.test.TestClass;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author TURK
 */
public class MainClass extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Sign In");
        Parent root = FXMLLoader.load(getClass().getResource("login/login.fxml"));
        primaryStage.getIcons().add(new Image("file:"+String.valueOf(Paths.get(System.getProperty("user.dir"),"files","login.png"))));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
      //  TestClass.printDatabaseTestInformation();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
