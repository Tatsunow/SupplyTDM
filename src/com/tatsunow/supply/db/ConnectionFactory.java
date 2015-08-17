package com.tatsunow.supply.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {
	
	private String host = "187.45.189.100";
	private String db_name = "u510039948_tdm";
	private String user = "u510039948_tdm";
	private String pass = "tat180569";
	
    public Connection getConnection() {
    	try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
            return DriverManager.getConnection("jdbc:mysql://"+ host + "/" + db_name, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
}
