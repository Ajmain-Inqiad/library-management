package loginAuth;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbconn.DBconnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String pass, query;
	Connection conn;
	Statement stmt;
	ResultSet rs;
	DBconnection db;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userid ;
		String username = request.getParameter("username");
		String userpassword = request.getParameter("password");
		String type = request.getParameter("type");
		String result = "";

		if(type.equals("admin")){

			try {
				db = new DBconnection();
				conn = db.getConnection();
				stmt = conn.createStatement();
				query = "SELECT * FROM admin WHERE username = '"+username+"' and password = '"+userpassword+"'";
				rs = db.getResult(query, conn);
				if(rs.next()){
					HttpSession session = request.getSession();  
			        session.setAttribute("name",rs.getString("username"));
			        
			        session.setAttribute("type", "admin");
					response.sendRedirect("admin.jsp");
				}
				else{
					request.setAttribute("message", "Username/Password does not match");
					request.getRequestDispatcher("/index.jsp").forward(request, response);
				}
			} catch (Exception e) {
				request.setAttribute("message", "Connection Error");
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
		}
		else if(type.equals("librarian")){
			try {
				db = new DBconnection();
				conn = db.getConnection();
				stmt = conn.createStatement();
				query = "SELECT * FROM librarian WHERE username = '"+username+"' and password = '"+userpassword+"'";
				rs = db.getResult(query, conn);
				if(rs.next()){
					userid =rs.getInt("id");
					HttpSession session = request.getSession();  
			        session.setAttribute("name",rs.getString("username"));
			        session.setAttribute("libid", userid);
			        session.setAttribute("type", "librarian");
			        session.setAttribute("image", rs.getString("image"));
					response.sendRedirect("librarian.jsp");
				}
				else{
					request.setAttribute("message", "Username/Password does not match");
					request.getRequestDispatcher("/index.jsp").forward(request, response);
				}
			} catch (Exception e) {
				request.setAttribute("message", "Connection Error");
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
		}
		else if(type.equals("member")){
			try {
				db = new DBconnection();
				conn = db.getConnection();
				stmt = conn.createStatement();
				query = "SELECT * FROM member WHERE username = '"+username+"' and password = '"+userpassword+"'";
				rs = db.getResult(query, conn);
				if(rs.next()){
					userid =rs.getInt("id");
					HttpSession session = request.getSession();  
			        session.setAttribute("name",rs.getString("username"));
			        session.setAttribute("memid", rs.getInt("id"));
			        session.setAttribute("type", "member");
			        session.setAttribute("image", rs.getString("image"));
					response.sendRedirect("member.jsp");
				}
				else{
					request.setAttribute("message", "Username/Password does not match");
					request.getRequestDispatcher("/index.jsp").forward(request, response);
				}
			} catch (Exception e) {
				request.setAttribute("message", "Connection Error");
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
		}

	}

}
