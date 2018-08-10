

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbconn.DBconnection;

/**
 * Servlet implementation class GetReqBook
 */
@WebServlet("/GetReqBook")
public class GetReqBook extends HttpServlet {
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
		LocalDate today = null;
		Date date = null;
		try {
			db = new DBconnection();
			conn = db.getConnection();
			stmt = conn.createStatement();
			query = "SELECT * FROM issued_book WHERE mem_id = '"+mem_id+"' AND title = '"+title+"'";
			rs = db.getResult(query, conn);
			//System.out.println(query);
			if(rs.next()){
				today = LocalDate.now();
				date = Date.valueOf(today);
			}else{
				request.setAttribute("message", "Did Not Get the requested Book");
				request.getRequestDispatcher("/librarian.jsp").forward(request, response);
			}
			query = "INSERT INTO history_book (title, mem_id, rec_date) VALUES ('"+title+"', '"+mem_id+"', '"+date+"')";
			//System.out.println(query);
			int i = stmt.executeUpdate(query);
			if(i>=1){
				query = "DELETE FROM issued_book WHERE mem_id = '"+mem_id+"' AND title = '"+title+"'";
				//System.out.println(query);
				int j = stmt.executeUpdate(query);
				request.setAttribute("message", "Book has been recieved");
				request.getRequestDispatcher("/librarian.jsp").forward(request, response);
			} else{
				request.setAttribute("message", "Did Not Get the requested Book");
				request.getRequestDispatcher("/librarian.jsp").forward(request, response);
			}
			
			
			rs.close();
		} catch (Exception e) {
			request.setAttribute("message", "Did Not Get the requested Book");
			request.getRequestDispatcher("/librarian.jsp").forward(request, response);
		}
		
	}

}
