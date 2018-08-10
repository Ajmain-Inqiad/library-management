

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
 * Servlet implementation class cv
 */
@WebServlet("/cv")
public class cv extends HttpServlet {
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
			String lib_name=request.getParameter("name");
			String lib_username=request.getParameter("username");
			String lib_password=request.getParameter("pass");
			String lib_gender=request.getParameter("gender");
			String lib_birthday=request.getParameter("dob");
			String lib_mobile=request.getParameter("mobile");
			String lib_address=request.getParameter("address");
			String lib_nid=request.getParameter("nid");
			String lib_education=request.getParameter("edu");
			String lib_exp=request.getParameter("experience");
			String lib_email=request.getParameter("email");  
			//String date=request.getParameter("date");
			String lib_image = request.getParameter("image");
			//String m_reg_day = request.getParameter("reg_date");
			conn = db.getConnection();
			stmt = conn.createStatement();
			//System.out.println(m_image);

			query = "SELECT username FROM req_lib WHERE username = '"+lib_username+"'";
			rs = db.getResult(query, conn);

			if(rs.next()){

				request.setAttribute("error", "please change your username");
				request.setAttribute("message", lib_image);
				request.getRequestDispatcher("/cv.jsp").forward(request, response);
			} else{
				query = "INSERT INTO req_lib (name, username, password, email, phone, nid, bday, address, gender, study, exp, image) VALUES('"+lib_name+"','"+lib_username+"','"+lib_password+"','"+lib_email+"','"+lib_mobile+"','"+lib_nid+"','"+lib_birthday+"','"+lib_address+"','"+lib_gender+"','"+lib_education+"','"+lib_exp+"','"+lib_image+"')";
				//System.out.println(query);
				int i = stmt.executeUpdate(query);
				if(i>=1){
					request.setAttribute("mail", lib_email);
					request.setAttribute("username", lib_username);
					request.setAttribute("pass", lib_password);
					request.setAttribute("type", "librarian");
					request.getRequestDispatcher("/reg_succ.jsp").forward(request, response);
				}
			}
		} catch (Exception e) {
			request.setAttribute("message", "Connection Error occured");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		} 


	}

}
