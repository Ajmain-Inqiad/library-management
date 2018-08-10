

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.*;
import java.time.temporal.ChronoUnit;

import dbconn.DBconnection;

/**
 * Servlet implementation class issuebook
 */
@WebServlet("/issuebook")
public class issuebook extends HttpServlet {
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
		try {
			String title = request.getParameter("title");
			title = title.replace("'", "@");
			String id = request.getParameter("mid"); 
			 LocalDate today = LocalDate.now();
			 LocalDate next2Week = today.plus(2, ChronoUnit.WEEKS);
			db = new DBconnection();
			conn = db.getConnection();
			stmt = conn.createStatement();
			query = "INSERT INTO issue_req_book (title, mem_id, start_date, due_date) VALUES ('"+title+"','"+id+"','"+today+"','"+next2Week+"')";
			//System.out.println(query);
			int i = stmt.executeUpdate(query);
			request.setAttribute("message", "Book request was successfull");
			request.getRequestDispatcher("/member.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("message", "Book request was not successfull");
			request.getRequestDispatcher("/member.jsp").forward(request, response);
		}
		
	}

}
