

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbconn.DBconnection;
import mail.SendMail;


/**
 * Servlet implementation class EditMem
 */
@WebServlet("/EditMem")
public class EditMem extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String query, test_user;
	Connection conn;
	Statement stmt;
	ResultSet rs;
	DBconnection db;
	SendMail send;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//System.out.println("hig");

			String username = request.getParameter("username");
			String pass = request.getParameter("password");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			String id = request.getParameter("id");
			//String id = (String)request.getSession(true).getAttribute("libid");
			//System.out.println(id);
			db = new DBconnection();
			send = new SendMail();
			conn = db.getConnection();
			stmt = conn.createStatement();
			HttpSession session = request.getSession(false);
			if(session != null){
				 test_user = (String)session.getAttribute("name");
				 //System.out.println(id1);
			 }
			 if(!(test_user.equals(username))){
				query = "SELECT username FROM member WHERE username = '"+username+"'";
				rs = db.getResult(query, conn);
				if(rs.next()){
					request.setAttribute("message", "Duplicate username");
					request.getRequestDispatcher("member.jsp").forward(request, response);
				} else{
					query = "UPDATE member SET username = '"+username+"', password = '"+pass+"', phone = '"+phone+"', email = '"+email+"', address = '"+address+"' WHERE id = "+id;
					//System.out.println(query);
					int j = stmt.executeUpdate(query);
					String sub = "Member Profile Edition";
					String msg = "Your account has been modified. Now, your basic info are username: "+username+", password = "+pass+"";
					msg = msg + " email: "+email+"";
					boolean result = send.sendingMail(email, msg, sub);
					request.setAttribute("message", "Profile edition suucessful. Please login again to update");
					request.getRequestDispatcher("member.jsp").forward(request, response);
				}
			 }else{
				query = "UPDATE member SET username = '"+username+"', password = '"+pass+"', phone = '"+phone+"', email = '"+email+"', address = '"+address+"' WHERE id = "+id;
				//System.out.println(query);
				int j = stmt.executeUpdate(query);
				String sub = "Member Profile Edition";
				String msg = "Your account has been modified. Now, your basic info are username: "+username+", password = "+pass+"";
				msg = msg + " email: "+email+"";
				boolean result = send.sendingMail(email, msg, sub);
				request.setAttribute("message", "Profile edition suucessful. Please login again to update");
				request.getRequestDispatcher("member.jsp").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("message", "Profile edition failed");
			request.getRequestDispatcher("member.jsp").forward(request, response);
		}
	}

}
