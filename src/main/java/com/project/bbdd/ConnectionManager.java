package com.project.bbdd;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionManager {
    
    // Funcion que abre una conexión con la BBDD especificada
    public static Connection getMySQLConnection() {
        Connection conn = null;
        
        try {
            Properties configs = new Properties();
            configs.load(new FileInputStream("./configs/configs.properties"));

            // Get a connection
            String url = configs.getProperty("url") +  configs.getProperty("database");
            String user = configs.getProperty("user");
            String pass = configs.getProperty("pass");  
            
            System.out.println("URL: " + url + ", USER: " + user + ", PASS: " + pass);

            conn = DriverManager.getConnection(url, user, pass);                        
        } catch(SQLException sqle) {
            System.err.println("ConnectionManager - SQLException: " + sqle.getMessage());
        } catch(IOException ioe) {
            System.err.println("ConnectionManager - ClassNotFoundException: " + ioe.getMessage());
        } catch(Exception e) {
            System.err.println("ConnectionManager - ClassNotFoundException: " + e.getMessage());
        }
        
        return conn;
    }   
    
}
