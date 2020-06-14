package ru.pstu.vmm.sample;

import java.sql.*;

public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection()
            throws ClassCastException, SQLException {
        dbConnection = DriverManager.getConnection("jdbc:mysql://"+ dbHost +"/" + dbName, dbUser, dbPass);

        return dbConnection;
    }

    // сохранение файлов и путей в таблицу FileNames
    public void addFileName(String filename) {
        String insert = "INSERT INTO FileNames (file_path) VALUES ('" + filename + "')";
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

    public void addFileWords(Integer file_id, Integer word_id)
    {
        String insert = "INSERT INTO FileWords (file_id, word_id) VALUES ('" + file_id + "','" + word_id + "')";
        connectDB(insert);
    }
}
