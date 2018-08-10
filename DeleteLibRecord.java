

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbconn.DBconnection;


/**
 * Servlet implementation class DeleteLibRecord
 */
@WebServlet("/DeleteLibRecord")
public class DeleteLibRecord extends HttpServlet {
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
		try {
			id = request.getParameter("id");
			db = new DBconnection();
			conn = db.getConnection();
			stmt = conn.createStatement();
			query = "DELETE FROM librarian WHERE id = "+id;
			int j = stmt.executeUpdate(query);
			response.sendRedirect("showLibrarian.jsp");
		} catch (Exception e) {
			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
		}

	}

}
