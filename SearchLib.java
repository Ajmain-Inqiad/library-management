

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

import dbconn.DBconnection;

/**
 * Servlet implementation class SearchLib
 */
@WebServlet("/SearchLib")
public class SearchLib extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String query;
	Connection conn;
	Statement stmt;
	ResultSet rs;
	DBconnection db;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("searchLib");
		List lst = new ArrayList();
		try {
			db = new DBconnection();
			conn = db.getConnection();
			stmt = conn.createStatement();
			query = "SELECT * FROM req_lib WHERE name like '%"+name+"%'";
			rs = db.getResult(query, conn);
			while(rs.next()){
				lst.add(rs.getString("id"));
				lst.add(rs.getString("name"));
				lst.add(rs.getString("username"));
				lst.add(rs.getString("email"));
				lst.add(rs.getString("phone"));			
				lst.add(rs.getString("nid"));
				lst.add(rs.getString("address"));
				lst.add(rs.getString("study"));
				lst.add(rs.getString("image"));
			}
			rs.close();
		} catch (Exception e) {
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
		}finally{
			request.setAttribute("ReqLib", lst);
			RequestDispatcher rd = request.getRequestDispatcher("/displayReqLib.jsp");
			rd.forward(request, response);
			lst.clear();
		}
	}

}
