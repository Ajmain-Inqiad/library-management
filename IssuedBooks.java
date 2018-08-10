

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
import javax.servlet.http.HttpSession;

import dbconn.DBconnection;

/**
 * Servlet implementation class IssuedBooks
 */
@WebServlet("/IssuedBooks")
public class IssuedBooks extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String query;
	Integer id1;
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
			HttpSession session = request.getSession(false);
			if(session != null){
				id1 = (Integer)session.getAttribute("memid");
			}
			db = new DBconnection();
			conn = db.getConnection();
			stmt = conn.createStatement();
			query = "SELECT * FROM history_book WHERE mem_id = "+id1;
			rs = db.getResult(query, conn);
			while(rs.next()){
				lst.add(rs.getString("id"));
				lst.add(rs.getString("title"));	
				lst.add(rs.getString("rec_date"));
			}
			rs.close();
		} catch (Exception e) {
			request.setAttribute("message", "Loading Error");
			request.getRequestDispatcher("/member.jsp").forward(request, response);
		}finally{
			request.setAttribute("BookHistory", lst);
			request.getRequestDispatcher("/disBookHistory.jsp").forward(request, response);
			lst.clear();
		}
	}

}
