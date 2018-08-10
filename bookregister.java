

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

//import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbconn.DBconnection;

/**
 * Servlet implementation class bookregister
 */
@WebServlet("/bookregister")
public class bookregister extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String id, query;
	Connection conn;
	Statement stmt;
	ResultSet rs;
	DBconnection db;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String isbn=request.getParameter("isbn");
			String title=request.getParameter("title");
			String type=request.getParameter("type");
			String au_name=request.getParameter("au_name");
			String edition=request.getParameter("edition");
			String description=request.getParameter("description");
			String page=request.getParameter("page");
			String publisher=request.getParameter("publisher");
			String pDate=request.getParameter("pDate");
			String filename=request.getParameter("filename");
			String fileimg=request.getParameter("fileimage");
			String dummyFile = filename.replace(".pdf", "");
			if(!(dummyFile.equals(title))){
				request.setAttribute("message", filename);
				request.setAttribute("error", "Filename does not match with title");
				request.getRequestDispatcher("/uploadBooks.jsp").forward(request, response);
			}
			title = title.replaceAll("'", "!");
			description = description.replaceAll("'", "!");
			db = new DBconnection();
			conn = db.getConnection();
			stmt = conn.createStatement();
			query = "INSERT INTO books (isbn, title, author, description, publisher, publishDate, type, edition, page, filename, book_img) VALUES('"+isbn+"','"+title+"','"+au_name+"','"+description+"','"+publisher+"','"+pDate+"','"+type+"','"+edition+"','"+page+"','"+filename+"', '"+fileimg+"')";
			int i = stmt.executeUpdate(query);
			if(i>=1){
				request.setAttribute("message", "Book Has Been Uploaded Successfully");
				request.getRequestDispatcher("/librarian.jsp").forward(request, response);
			}else{
				request.setAttribute("message", "Book Uploaded failed");
				request.getRequestDispatcher("/librarian.jsp").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("message", "Book Uploaded failed");
			request.getRequestDispatcher("/librarian.jsp").forward(request, response);
		}
	}

}
