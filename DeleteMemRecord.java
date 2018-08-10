

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
import javax.servlet.http.HttpSession;

import dbconn.DBconnection;


/**
 * Servlet implementation class DeleteMemRecord
 */
@WebServlet("/DeleteMemRecord")
public class DeleteMemRecord extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;
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
			query = "DELETE FROM member WHERE id = "+id;
			int j = stmt.executeUpdate(query);
			//String type = (String)session.getAttribute("type");
			response.sendRedirect("showMember.jsp");
		} catch (Exception e) {
			request.setAttribute("message", "Connection Error occured");
			request.getRequestDispatcher("/showMember.jsp").forward(request, response);
		}

	}

}
