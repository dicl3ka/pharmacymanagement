package org.mavensample;

import java.sql.Connection;

import org.mavensample.database.DBConnection;

public class MavenDemo {

    public static void main(String[] args) {

        Connection conn = DBConnection.getConnection();

        if (conn != null) {
            System.out.println("Connection successful!");
        } else {
            System.out.println("Connection failed!");
        }
    }
}