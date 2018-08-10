package delete;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class deletion {
	private static final String username = "root";
	private static final String password = "";
	private static final String m_con_string = "jdbc:mysql://localhost/library";
	
	
	public static boolean libGetRow(int libId) throws SQLException, ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		String sql = "DELETE FROM librarian WHERE id = ?";
		ResultSet rs = null;
		try (
				Connection conn = DriverManager.getConnection(m_con_string, username, password);
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			stmt.setInt(1, libId);
			rs = stmt.executeQuery();
			
			if(rs.next()){
				return true;
			}else{
				
				return false;
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			if(rs != null){
				rs.close();
			}
		}
		return false;
	}
	
	public static boolean memGetRow(int memId) throws SQLException, ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		String sql = "DELETE FROM member WHERE id = "+memId;
		ResultSet rs = null;
		try (
				Connection conn = DriverManager.getConnection(m_con_string, username, password);
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			//System.out.println("try er moddhe");
			//stmt.setInt(1, memId);
			
			conn.createStatement().execute(sql);
			//System.out.println("execute er por");
			return true;
			//System.out.println(rs + " id : "+memId);
//			if(rs.next()){
//				return true;
//			}else{
//				
//				return false;
//			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			if(rs != null){
				rs.close();
			}
		}
		return false;
	}

}
