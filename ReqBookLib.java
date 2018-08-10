

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
 * Servlet implementation class ReqBookLib
 */
@WebServlet("/ReqBookLib")
public class ReqBookLib extends HttpServlet {
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
			query = "SELECT * FROM issue_req_book";
			rs = db.getResult(query, conn);
			while(rs.next()){
				lst.add(rs.getString("id"));
				lst.add(rs.getString("title"));
				lst.add(rs.getString("mem_id"));	
				lst.add(rs.getString("start_date"));
				lst.add(rs.getString("due_date"));
			}
			rs.close();
		} catch (Exception e) {
			request.setAttribute("message", "Loading Error");
			request.getRequestDispatcher("/librarian.jsp").forward(request, response);
		}finally{
			request.setAttribute("ReqBookList", lst);
			request.getRequestDispatcher("/disReqBookList.jsp").forward(request, response);
			lst.clear();
		}
	}

}
