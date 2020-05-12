package ru.pstu.vmm.sample;

import java.sql.*;

public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection()
            throws ClassCastException, SQLException {
      //  String connectionString = "jdbc:mysql://" + dbHost + ":"
      //          + dbPort + "/" + dbName + "?" + "useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

      //  dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        dbConnection = DriverManager.getConnection("jdbc:mysql://"+ dbHost +"/" + dbName, dbUser, dbPass);

        return dbConnection;
    }

    public void addFileName(String filename) {
        String insert = "INSERT INTO FileNames (Names) VALUES ('" + filename + "')";
        connectDB(insert);
    }

    public void addWordFreq(String word, String freq) {
        String insert = "INSERT INTO WordsFrequency (word, frequency) VALUES ('" + word + "','" + freq + "')";
        connectDB(insert);
    }

    private void connectDB(String command) {
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(command);
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
