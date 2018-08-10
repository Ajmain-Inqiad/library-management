

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbconn.DBconnection;

/**
 * Servlet implementation class AddBookReq
 */
@WebServlet("/AddBookReq")
public class AddBookReq extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String start, due, query;
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
			query = "SELECT * FROM issue_req_book WHERE mem_id = '"+mem_id+"' AND title = '"+title+"'";
			rs = db.getResult(query, conn);
			//System.out.println(query);
			if(rs.next()){
				start = rs.getString("start_date");
				due = rs.getString("due_date");
			}else{
				//System.out.println("rs khali");
			}
			query = "INSERT INTO issued_book (title, mem_id, start_date, due_date) VALUES ('"+title+"', '"+mem_id+"', '"+start+"', '"+due+"')";
			//System.out.println(query);
			int i = stmt.executeUpdate(query);
			if(i>=1){
				query = "DELETE FROM issue_req_book WHERE mem_id = '"+mem_id+"' AND title = '"+title+"'";
				System.out.println(query);
				int j = stmt.executeUpdate(query);
				request.setAttribute("message", "Book Request has been accepted");
				request.getRequestDispatcher("/librarian.jsp").forward(request, response);
			} else{
				request.setAttribute("message", "Insertion Failed");
				request.getRequestDispatcher("/librarian.jsp").forward(request, response);
			}
			
			
			rs.close();
		} catch (Exception e) {
			request.setAttribute("message", "Can not accept the request");
			request.getRequestDispatcher("/librarian.jsp").forward(request, response);
		}
		
		
	}

}
