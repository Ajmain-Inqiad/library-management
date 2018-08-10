

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

import dbconn.DBconnection;

/**
 * Servlet implementation class GivenBook
 */
@WebServlet("/GivenBook")
public class GivenBook extends HttpServlet {
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
			query = "SELECT * FROM issued_book";
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
			request.setAttribute("message", "DataBase Error");
			request.getRequestDispatcher("/member.jsp").forward(request, response);
		}finally{
			request.setAttribute("GiveBook", lst);
			RequestDispatcher rd = request.getRequestDispatcher("/disGivenBooks.jsp");
			rd.forward(request, response);
			lst.clear();
		}
	}

}
