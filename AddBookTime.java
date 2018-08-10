

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.time.LocalDate;


import java.time.temporal.ChronoUnit;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbconn.DBconnection;

/**
 * Servlet implementation class AddBookTime
 */
@WebServlet("/AddBookTime")
public class AddBookTime extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String title, query, date;
	Integer id1;
	Connection conn;
	Statement stmt;
	ResultSet rs;
	DBconnection db;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		String due_date = null;
		date = null;
		title = request.getParameter("book");
		//System.out.println(title);
		try {
			HttpSession session = request.getSession(false);
			if(session != null){
				id1 = (Integer)session.getAttribute("memid");
			}
			db = new DBconnection();
			conn = db.getConnection();
			stmt = conn.createStatement();
			//String date;
			query = "SELECT start_date, due_date FROM issue_req_book WHERE mem_id = "+id1+" and title = '"+title+"'";
			rs = db.getResult(query, conn);
			while(rs.next()){

				date = rs.getString("start_date");
				due_date = rs.getString("due_date");
			}


			LocalDate dat = LocalDate.parse(date);

			LocalDate due_d = LocalDate.parse(due_date);

			long p2 = ChronoUnit.DAYS.between(dat, due_d);

			

			if(p2>21){
				request.setAttribute("message", "Max 4 weeks is allowed for a book");
				request.getRequestDispatcher("member.jsp").forward(request, response);
			}
			else{
				LocalDate today = LocalDate.now();
				LocalDate next1Week = today.plus(1, ChronoUnit.WEEKS);

				long p3 = ChronoUnit.DAYS.between(dat, next1Week);
				
				if(p3>21){

					request.setAttribute("message", "Max 4 weeks is allowed for a book");
					request.getRequestDispatcher("member.jsp").forward(request, response);
				}
				else{
					next1Week = due_d.plus(1, ChronoUnit.WEEKS);
					query = "UPDATE issue_req_book SET due_date = '"+next1Week+"' WHERE mem_id = "+id1+" and title= '"+title+"'";
					
					int j = stmt.executeUpdate(query);
					request.setAttribute("message", "Time has been extended");
					request.getRequestDispatcher("member.jsp").forward(request, response);
				}
			}
			rs.close();
		} catch (Exception e) {
			//response.sendRedirect("error.jsp");
			System.out.println(e);
		}
	}

}
