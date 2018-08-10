package delete;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

/**
 * Servlet implementation class deleteMem
 */
@WebServlet("/deleteMem")
public class deleteMem extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("deleteId"));
		System.out.println(id);
		boolean result = false;
		try {
//			if(deletion.libGetRow(id)){
//				result = true;
//			}
//			else 
			if(deletion.memGetRow(id)){
				result = true;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result);
		if(result){
			String message = "Deletion Complete";
//			request.setAttribute("message", message);
//			request.getRequestDispatcher("/deleteMember.jsp").forward(request, response);
			response.sendRedirect("deleteMember.jsp");
		}else{
			String message = "Deletion Failed";
			request.setAttribute("message", message);
//			request.getRequestDispatcher("/deleteMember.jsp").forward(request, response);
			response.sendRedirect("deleteMember.jsp");
		}

	}

}
