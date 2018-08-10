package dbconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBconnection implements Dbconn{

	Connection conn;
	Statement stmt;
	ResultSet rs;
	public DBconnection(){
		
	}
	
	public Connection getConnection(){
		setConnection();
		return conn;
	}
	
	public void setConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/library", "root", "");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	public ResultSet getResult(String sql, Connection conn){
		this.conn = conn;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (Exception e) {
			System.out.println("rs error");
		}
		return rs;
	}

}
