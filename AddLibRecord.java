

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbconn.DBconnection;


/**
 * Servlet implementation class AddLibRecord
 */
@WebServlet("/AddLibRecord")
public class AddLibRecord extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String id, query;
	Connection conn;
	Statement stmt;
	ResultSet rs;
	DBconnection db;


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			id = request.getParameter("id");
			db = new DBconnection();
			conn = db.getConnection();
			stmt = conn.createStatement();
			query = "INSERT INTO librarian (name, username, password, email, phone, nid, bday, address, gender, study, exp, image) SELECT name, username, password, email, phone, nid, bday, address, gender, study, exp, image FROM req_lib WHERE id = "+id;
			int i = stmt.executeUpdate(query);
			if(i >= 1){
				query = "DELETE FROM req_lib WHERE id = "+id;
				int j = stmt.executeUpdate(query);
			}
			response.sendRedirect("ShowReqLib");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
