

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
 * Servlet implementation class Editbookinfo
 */
@WebServlet("/Editbookinfo")
public class Editbookinfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String query;
	Connection conn;
	Statement stmt;
	ResultSet rs;
	DBconnection db;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String title=request.getParameter("title");
			if(title==null || title.equals("")){
				request.setAttribute("message", "Book edition failed");
				request.getRequestDispatcher("librarian.jsp").forward(request, response);
			}
			
			String au_name=request.getParameter("au_name");
			String edition=request.getParameter("edition");
			String description=request.getParameter("description");
			String page=request.getParameter("page");
			String isbn = request.getParameter("isbn");
			title = title.replaceAll("'", "!");
			description = description.replaceAll("'", "!");
			System.out.println(title);
			System.out.println(au_name);
			System.out.println(edition);
			System.out.println(description);
			System.out.println(page);
			db = new DBconnection();
			conn = db.getConnection();
			stmt = conn.createStatement();
			query = "UPDATE books SET title = '"+title+"', author = '"+au_name+"', description = '"+description+"', page = '"+page+"', edition = '"+edition+"' WHERE isbn = '"+isbn+"'";                       
			//System.out.println(query);
			int i= stmt.executeUpdate(query);
			if(i>=1){
				request.setAttribute("message", "Book edition suucessful");
				request.getRequestDispatcher("librarian.jsp").forward(request, response);
			}
		}
		catch(Exception e){

			System.out.println(e);
		}
	}

}
