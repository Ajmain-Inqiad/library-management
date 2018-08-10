

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//		import javax.servlet.http.Part;
import java.io.*;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class BookUpload
 */
@WebServlet("/BookUpload")
public class BookUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String UPLOAD_DIRECTORY = "D:\\Study\\eclipse_jee_3\\LibraryManagement\\WebContent\\books\\";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(ServletFileUpload.isMultipartContent(request)){
			try {

				int count = 0;
				List<FileItem> multiparts = new ServletFileUpload(
						new DiskFileItemFactory()).parseRequest(request);

				for(FileItem item : multiparts){
					if(!item.isFormField()){
						String name = new File(item.getName()).getName();
						File file = new File(UPLOAD_DIRECTORY+"/"+name);
						//System.out.println(file.exists());
						if(!file.exists()){
							String type= item.getContentType();
							//System.out.println(type);
							if(type.equals("application/pdf")){
								//System.out.println("name "+name);
								item.write(
										new File(UPLOAD_DIRECTORY + File.separator + name));
								request.setAttribute("message", name);
								count++;
							} else if(type.equals("image/jpeg") || type.equals("image/jpg")){
								item.write(
										new File(UPLOAD_DIRECTORY + File.separator + name));
								request.setAttribute("file", name);
								count++;
							}
							else{
								request.setAttribute("message", "File Upload Failed due to file format mismatch");
								request.getRequestDispatcher("/bookfile.jsp").forward(request, response);
							}
							if(count == 2){
								request.getRequestDispatcher("/uploadBooks.jsp").forward(request, response);
							}
						}
						else{
							request.setAttribute("message", "File name already exists");
							request.getRequestDispatcher("/bookfile.jsp").forward(request, response);
						}
					}
				}

				//File uploaded successfully

			} catch (Exception ex) {
				request.setAttribute("message", "File Upload Failed due to " + ex);
				request.getRequestDispatcher("/bookfile.jsp").forward(request, response);
			}

		}else{
			request.setAttribute("message",
					"Sorry this Servlet only handles file upload request");
			request.getRequestDispatcher("/bookfile.jsp").forward(request, response);
		}
	}

}
