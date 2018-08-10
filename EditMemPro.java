

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbconn.DBconnection;

/**
 * Servlet implementation class EditMemPro
 */
@WebServlet("/EditMemPro")
public class EditMemPro extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String query;
	Integer id1;
	Connection conn;
	Statement stmt;
	ResultSet rs;
	DBconnection db;

	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List lst = new ArrayList();
		try {
			HttpSession session = request.getSession(false);
			 if(session != null){
				 id1 = (Integer)session.getAttribute("memid");
				 //System.out.println(id1);
			 }
				
				//System.out.println(libId);
				if(id1 != 0){
					db = new DBconnection();
					conn = db.getConnection();
					stmt = conn.createStatement();
					query = "SELECT * FROM member WHERE id = "+id1;
					rs = db.getResult(query, conn);
					while(rs.next()){
						lst.add(rs.getString("image"));
						lst.add(rs.getString("username"));
						lst.add(rs.getString("password"));
						lst.add(rs.getString("phone"));
						lst.add(rs.getString("email"));
						lst.add(rs.getString("address"));
						
					}
					//System.out.println(Arrays.toString(lst.toArray()));
					rs.close();
				
			}
			else{
				RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally{
			request.setAttribute("LibInfo", lst);
			RequestDispatcher rd = request.getRequestDispatcher("/editMem.jsp");
			rd.forward(request, response);
			lst.clear();
		}
	}

}
