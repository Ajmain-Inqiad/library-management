

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Bookdownload
 */
@WebServlet("/Bookdownload")
public class Bookdownload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	 private final String dir1 = "D:\\Study\\eclipse_jee_3\\LibraryManagement\\WebContent\\books\\";
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String fileName = request.getParameter("id");
		response.setContentType("APPLICATION/PDF");
		
		response.setHeader("Content-Disposition", "inline: fileName = \""+fileName+"\"");
		FileInputStream fi = new FileInputStream(dir1+fileName);
		int i;
		while((i=fi.read()) != -1){
			out.write(i);
		}
		fi.close();
		out.close();
		
	}

}
