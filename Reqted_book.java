

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
import javax.servlet.http.HttpSession;

import dbconn.DBconnection;
/**
 * Servlet implementation class Reqted_book
 */
@WebServlet("/Reqted_book")
public class Reqted_book extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String id, query;
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
				 //System.out.println(id1);
			 }


			db = new DBconnection();
			conn = db.getConnection();
			stmt = conn.createStatement();
			
			query = "SELECT * FROM issue_req_book WHERE mem_id = "+id1;
			rs = db.getResult(query, conn);
			while(rs.next()){
				lst.add(rs.getString("title"));
				lst.add(rs.getString("start_date"));
				lst.add(rs.getString("due_date"));
			}
			rs.close();
		} catch (Exception e) {
			request.setAttribute("message", "Loading Error, try after sometime");
			request.getRequestDispatcher("/member.jsp").forward(request, response);
		} finally{
			request.setAttribute("ReqBook", lst);
			request.getRequestDispatcher("/displayReqBook.jsp").forward(request, response);
			lst.clear();
		}
	}

}
