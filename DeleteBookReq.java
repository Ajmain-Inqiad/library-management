

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbconn.DBconnection;

/**
 * Servlet implementation class DeleteBookReq
 */
@WebServlet("/DeleteBookReq")
public class DeleteBookReq extends HttpServlet {
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
		String mem_id = request.getParameter("id");
		String title = request.getParameter("title");
		try {
			db = new DBconnection();
			conn = db.getConnection();
			stmt = conn.createStatement();
			query = "DELETE FROM issue_req_book WHERE mem_id = '"+mem_id+"' AND title = '"+title+"'";
			int j = stmt.executeUpdate(query);
			request.setAttribute("message", "Book Request is ignored");
			request.getRequestDispatcher("/librarian.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("message", "Book Request is failed to ignore");
			request.getRequestDispatcher("/librarian.jsp").forward(request, response);
		}
	}

}
