/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main.balance.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TURK
 */
public class DB {

    private Connection connection;
    private static DB db = new DB();
    private static DB instance = new DB();

    private DB() {

        connectToDB();

    }

    public static DB getInstance() {
        return instance;
    }

    private void connectToDB() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/balance_manager_4501", "root", "Semedova0810");
            System.out.println("Successfully connected...");
        } catch (Exception e) {
            System.out.println("Can not connect...");
        }

    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Dissconected...");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public List<String> getColumnRowsFromTableAsList(String table, String column, String condition) {
        List<String> list = new ArrayList<String>();
        try {
            Statement s = connection.createStatement();
            ResultSet r = s.executeQuery("select " + column + " from " + table + " " + condition);
            while (r.next()) {
                list.add(r.getString(column));
            }
            close(null, s, r);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void close(Connection c, Statement s, ResultSet r) {
        try {
            if (c != null) {
                s.close();
            }
            if (s != null) {
                s.close();
            }
            if (r != null) {
                r.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean iud(String query) {
        boolean result = false;
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
            close(null, statement, null);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public boolean hasRowInTableForThisCondition(String table, String condition) {
        boolean has = false;

        try {
            Statement s = connection.createStatement();
            ResultSet r = s.executeQuery("select * from " + table + " " + condition);
            while (r.next()) {
                has = true;
                break;

            }
            close(null, s, r);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return has;
    }

    public String getColumnSingleRowsFromTableAsList(String table, String column, String condition) {
        String row = "";
        try {
            Statement s = connection.createStatement();
            ResultSet r = s.executeQuery("select " + column + " from " + table + " " + condition);
            r.next();
            row = r.getString(column);

            close(null, s, r);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    //sorgunun cavabini istifaade etmek meqsedle yaratmisham
    public ResultSet getResultSet(String query) {
        ResultSet resultSet = null;
        try {
            Statement s = connection.createStatement();
            resultSet = s.executeQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
        }//bu kodu sile bilerem menasiz oldu (SONRA YENIDEN BAXILACAQ)
        return resultSet;
    }

    public void closeRs(ResultSet r) throws SQLException{
        if(r == null){
            System.out.println("OK");
        }else{
            r.getStatement().close();
            r.close();
        }
    }
    public double getColumnSumFromTable(String table, String column,String condition){
        double sum = 0;
         try {
            Statement s = connection.createStatement();
            ResultSet r = s.executeQuery("select sum(" + column + ") from " + table + " " + condition);
            r.next();
            sum = r.getDouble(1);

            close(null, s, r);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }
}
