package tr.org.mmo.asansor.test;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class ConnectionTestServlet
 */
@WebServlet("/ConnectionTestServlet")
public class ConnectionTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnectionTestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Connection con=null;
		System.setProperty("java.net.preferIPv4Stack" , "true");
		ServletRequest sr=(ServletRequest)request;
		String ipAddress1 ;
		    ipAddress1 = sr.getRemoteAddr();
		
		System.out.println(ipAddress1);
		String ipAddress = request.getHeader( "X-FORWARDED-FOR" );
		if ( ipAddress == null ) {
		    ipAddress = request.getRemoteAddr();
		}
		System.out.println(ipAddress);
		/*
		try {
			//con=DAOBase.instance().getConnection();
			//System.out.println(con);
			// new ImageTest().insertPDF("d://document.pdf");
			 new ImageTest().getPDFData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}

}
