package tr.org.mmo.asansor.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PDFServlet
 */
@WebServlet("/PDFServlet")
public class PDFServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PDFServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			byte[] pdf = (byte[]) request.getSession().getAttribute("dosya");
			String dosyaAdi = (String) request.getSession().getAttribute(
					"dosyaAdi");
			request.getSession().removeAttribute("dosya");
			request.getSession().removeAttribute("dosyaAdi");
			response.setHeader("Content-disposition", "attachment;filename=\""
					+ dosyaAdi + "\"" + ";");

			response.setContentType("application/pdf");
			response.getOutputStream().write(pdf);
			response.getOutputStream().close();
			response.flushBuffer();

		} catch (Exception e) {
			response.getWriter().write(e.getMessage());
			response.getWriter().close();
		}

	}

}
