

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
 * Servlet implementation class EditAdPass
 */
@WebServlet("/EditAdPass")
public class EditAdPass extends HttpServlet {
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
		try {
			pass = request.getParameter("adpass");
			db = new DBconnection();
			conn = db.getConnection();
			stmt = conn.createStatement();
			query = "UPDATE admin SET password = '"+pass+"' WHERE username = 'admin'";
			//System.out.println(query);
			int j = stmt.executeUpdate(query);
			//System.out.println(j);
			if(j ==1){
				request.setAttribute("message", "Password Change successfully");
				request.getRequestDispatcher("/admin.jsp").forward(request, response);
			} else{
				request.setAttribute("message", "Password Change successfully");
				request.getRequestDispatcher("/admin.jsp").forward(request, response);
			}
		}
		catch (Exception e) {
			request.setAttribute("message", "Password Change was not successfully");
			//System.out.println(e);
			request.getRequestDispatcher("/admin.jsp").forward(request, response);
		}

	}

}
