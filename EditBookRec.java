

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
 * Servlet implementation class EditBookRec
 */
@WebServlet("/EditBookRec")
public class EditBookRec extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String id, query;
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
			
			id = request.getParameter("id");
			db = new DBconnection();
			conn = db.getConnection();
			stmt = conn.createStatement();
			query = "SELECT * FROM books WHERE isbn = "+id;
			rs = db.getResult(query, conn);
			while(rs.next()){
				lst.add(rs.getString("title"));
				lst.add(rs.getString("author"));
				lst.add(rs.getString("edition"));
				lst.add(rs.getString("description"));
				lst.add(rs.getString("page"));
				lst.add(rs.getString("isbn"));
			}
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
		}finally{
			request.setAttribute("ReqBook", lst);
			RequestDispatcher rd = request.getRequestDispatcher("/editbook.jsp");
			rd.forward(request, response);
			lst.clear();
		}
	}

}
