/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdcproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GerardPC
 * @note
 * embedded url
 * url = "jdbc:derby:WWTABDM;create=true"
 * online url
 * url = "jdbc:derby://localhost:1527/WWTBAMDB;create=true"
 * 
 */
public final class DBManager {
    
    private static final String USER_NAME = "pdc";
    private static final String PASSWORD = "pdc";
    private static final String URL = "jdbc:derby:WWTABDM;create=true";
    
    Connection conn;

    public DBManager() {
        establishConnection();
    }

    public Connection getConnection() {
        return this.conn;
    }

    //Establish connection
    public void establishConnection() {
        if (this.conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
                System.out.println(URL + " Connected");

            } catch (SQLException ex) {
                Logger.getLogger("Error starting database");

            }
        }
    }

    public void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger("Error closing database");            }
        }
    }
    
    public void createResultsTable() {
    
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("DROP TABLE RESULTS");
            String sqlCreateTable = "CREATE TABLE RESULTS (PLAYERS VARCHAR(20), WINNINGS INT)";
            String sqlInsertToTable = "";

            statement.executeUpdate(sqlCreateTable);
            statement.execute(sqlInsertToTable);
            System.out.println("Table successfully made!");

        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());

        }
    }

}
