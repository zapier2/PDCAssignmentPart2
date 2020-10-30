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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gerard Gomez Vicotr Feng
 * @note embedded url url = "jdbc:derby:WWTBAMDB;create=true" online url url =
 * "jdbc:derby://localhost:1527/WWTBAMDB;create=true"
 *
 */
public final class DBManager {

    private static final String USER_NAME = "pdc";
    private static final String PASSWORD = "pdc";
    private static final String URL = "jdbc:derby:WWTBAMDB;create=true";

    Connection conn = null;

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
                // Use this function ONCE IF database folder does not exist
//                this.createResultsTable();
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
                Logger.getLogger("Error closing database");
            }
            System.out.println("Closing connection to DB");
        }

    }

    // Creating results table
    public void createResultsTable() {

        try {

            conn.createStatement().execute("CREATE TABLE RESULTS (PLAYERS VARCHAR(20), WINNINGS INT)");
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());

        }
    }

    //Insert results to table
    public void insertToTable(Player player) {
        try {
            String name = player.getPlayerName();
            int winnings = player.getWinnings();
            String sqlInsert = "INSERT INTO RESULTS VALUES('" + name + "', " + winnings + ")";
            conn.createStatement().execute(sqlInsert);

            System.out.println("Insert created");

        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }

    }
    //Display query
    public ArrayList<Player> getQuery() {
        ArrayList<Player> playerResults = new ArrayList<>();
        ResultSet rs = null;

        try {
            System.out.println(" getting query....");
            Statement statement = conn.createStatement();

            String sqlQuery = "SELECT * from RESULTS";

            rs = statement.executeQuery(sqlQuery);
            while (rs.next()) {
                Player player = new Player();
                player.setPlayerName(rs.getString(1));
                player.setWinnings(rs.getInt(2));
                playerResults.add(player);
            }

        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }

        return playerResults;
    }
    
    public boolean playerExist(String name) {
        ResultSet rs = null;
        boolean player = false;
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM RESULTS WHERE PLAYERS = " + "'" + name+ "'";
            rs = statement.executeQuery(query);
            if(rs.next() != false) {
                player = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return player;
    }
    
    public void updatePlayer(Player player) {
        try {
            Statement statement = conn.createStatement();
            String query = "UPDATE RESULTS SET WINNINGS = " + player.getWinnings() + " WHERE PLAYERS = " + "'" + player.playerName + "'";
            conn.createStatement().execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
