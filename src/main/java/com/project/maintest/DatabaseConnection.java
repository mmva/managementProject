package com.project.maintest;

import com.project.bbdd.ConnectionManager;
import java.sql.Connection;


public class DatabaseConnection {

    public static void main(String[] args) {
        Connection conn = ConnectionManager.getMySQLConnection();
    }
}
