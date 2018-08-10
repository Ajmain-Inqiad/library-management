

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
 * Servlet implementation class Registration
 */
@WebServlet("/Registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection conn;
	Statement stmt;
	ResultSet rs;
	DBconnection db;
	String query;


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			db = new DBconnection();
			String m_full_name = request.getParameter("name");
			String m_u_name = request.getParameter("username");
			String m_pass = request.getParameter("pass");
			String m_email = request.getParameter("email");
			String m_phn = request.getParameter("mobile");
			String m_nid = request.getParameter("nid");
			String m_bday = request.getParameter("dob");
			String m_add = request.getParameter("address");
			String m_gender = request.getParameter("gender");
			String m_edu = request.getParameter("edu");
			String m_image = request.getParameter("image");
			//String m_reg_day = request.getParameter("reg_date");
			conn = db.getConnection();
			stmt = conn.createStatement();
			//System.out.println(m_image);

			query = "SELECT username FROM req_mem WHERE username = '"+m_u_name+"'";
			rs = db.getResult(query, conn);
			
			if(rs.next()){
				
				request.setAttribute("error", "please change your username");
				request.setAttribute("message", m_image);
				request.getRequestDispatcher("/registration.jsp").forward(request, response);
			}else{
				query = "INSERT INTO req_mem (name, username, password, email, phone, nid, bday, address, gender, study, image) VALUES('"+m_full_name+"','"+m_u_name+"','"+m_pass+"','"+m_email+"','"+m_phn+"','"+m_nid+"','"+m_bday+"','"+m_add+"','"+m_gender+"','"+m_edu+"','"+m_image+"')";
				//System.out.println(query);
				int i = stmt.executeUpdate(query);
				if(i>=1){
					request.setAttribute("mail", m_email);
					request.setAttribute("username", m_u_name);
					request.setAttribute("pass", m_pass);
					request.setAttribute("type", "member");
					request.getRequestDispatcher("/reg_succ.jsp").forward(request, response);
				}
			}
		} catch (Exception e) {
			request.setAttribute("message", "Connection Error occured");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		} 

	}

}
