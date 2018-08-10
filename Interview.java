

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mail.SendMail;
import java.time.LocalDate;


import java.time.temporal.ChronoUnit;

/**
 * Servlet implementation class Interview
 */
@WebServlet("/Interview")
public class Interview extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mail = request.getParameter("mail");
		String sub = "Interview";
		LocalDate today = LocalDate.now();
		LocalDate next1Week = today.plus(1, ChronoUnit.WEEKS);
		String msg = "Your interview will be held on "+next1Week;
		SendMail send = new SendMail();
		send.sendingMail(mail, msg, sub);
		request.setAttribute("message", "Mail Sent");
		request.getRequestDispatcher("admin.jsp").forward(request, response);
		
	}

}
