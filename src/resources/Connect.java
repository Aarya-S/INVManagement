package resources;

import java.sql.*;

public class Connect {
    Connection con;

    public static Connection ConnectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/invmanagement", "root", "password");
            return con;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
