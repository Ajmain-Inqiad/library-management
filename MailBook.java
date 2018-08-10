

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import dbconn.DBconnection;
import mail.SendMail;
/**
 * Servlet implementation class MailBook
 */
@WebServlet("/MailBook")
public class MailBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String query;
	Connection conn;
	Statement stmt;
	ResultSet rs;
	DBconnection db;
	SendMail send;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mem_id = request.getParameter("id");
		String name = request.getParameter("title");
		name = name.replace("@", "'");
		String time = request.getParameter("time");
		int remain = Integer.parseInt(time);
		remain = -1*remain;
		String to = "";
		String msg = "";
		String sub = "Book Reminder";
		try {
			db = new DBconnection();
			send = new SendMail();
			conn = db.getConnection();
			stmt = conn.createStatement();
			query = "SELECT * FROM member WHERE id = "+mem_id;
			rs = db.getResult(query, conn);
			if(rs.next()){
				to = rs.getString("email");
			} else{
				System.out.println("result nai");
			}
			if(remain>0){
				msg = "You have "+remain+" days to return the book titled: "+name+"";
			} else{
				msg = "Your time has been passed to return book titled: "+name+". If you do not return the book, your membership will be canceled";
			}
			boolean result = send.sendingMail(to, msg, sub);
			if(result){
			    request.setAttribute("message", "Mail has sent successfully");
				request.getRequestDispatcher("librarian.jsp").forward(request, response);
			}else{
				request.setAttribute("message", "Mail has not sent");
				request.getRequestDispatcher("librarian.jsp").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("message", "Mail has not sent");
			request.getRequestDispatcher("librarian.jsp").forward(request, response);
		}
	}

}
