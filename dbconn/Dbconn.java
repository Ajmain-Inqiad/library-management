package dbconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public interface Dbconn {

	public Connection getConnection();
	public void setConnection();
	public ResultSet getResult(String sql, Connection conn);
}
