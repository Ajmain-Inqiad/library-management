

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
 * Servlet implementation class AllBooks
 */
@WebServlet("/AllBooks")
public class AllBooks extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String query;
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
			db = new DBconnection();
			conn = db.getConnection();
			stmt = conn.createStatement();
			query = "SELECT * FROM books";
			rs = db.getResult(query, conn);
			while(rs.next()){
				lst.add(rs.getString("book_img"));
				lst.add(rs.getString("isbn"));
				lst.add(rs.getString("title"));
				lst.add(rs.getString("author"));
				lst.add(rs.getString("description"));
				lst.add(rs.getString("publisher"));			
				lst.add(rs.getString("publishDate"));
				lst.add(rs.getString("page"));
				lst.add(rs.getString("type"));
				lst.add(rs.getString("edition"));
				lst.add(rs.getString("filename"));
			}
			rs.close();
		} catch (Exception e) {
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
		}finally{
			request.setAttribute("ReqBook", lst);
			RequestDispatcher rd = request.getRequestDispatcher("/booklist.jsp");
			rd.forward(request, response);
			lst.clear();
		}
	}

}
