package tr.org.mmo.asansor.servlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.pdf.PdfReader;

import tr.org.mmo.asansor.business.RaporBusiness;
import tr.org.mmo.asansor.dto.RandevuListeDTO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

/**
 * Servlet implementation class EtiketServlet
 */
@WebServlet("/EtiketServlet")
public class EtiketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JasperPrint jasperPrint;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EtiketServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
   
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doPost(request, response);
    	
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			init(request);
			
			// httpServletResponse.addHeader("Content-disposition","attachment; filename="+fileName);

			// httpServletResponse.setLocale(arg0)
			response.setLocale(new Locale("tr"));

			ServletOutputStream servletOutputStream = response
					.getOutputStream();

			// Raporu çık
			JasperExportManager.exportReportToPdfStream(jasperPrint,
					servletOutputStream);

			// Raporu diske kaydet
			//
			JRPdfExporter pdfExporter = new JRPdfExporter();
			pdfExporter.setParameter(JRPdfExporterParameter.JASPER_PRINT,
					jasperPrint);

			pdfExporter.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME,
					"etiket.pdf");

			pdfExporter.exportReport();
		

			servletOutputStream.close();
			

			// servletOutputStream.flush();
			// servletOutputStream.close();

			

		} catch (JRException e) {
			
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void init(HttpServletRequest request) throws JRException {
		List<RandevuListeDTO> list=new ArrayList<RandevuListeDTO>();
		list=(List<RandevuListeDTO>)request.getSession().getAttribute("etiketList");
		JRBeanCollectionDataSource beanCollectionDataSource = null;
		beanCollectionDataSource = new JRBeanCollectionDataSource(list);
		
		
		jasperPrint = JasperFillManager
				.fillReport(
						request.getSession()
								.getServletContext()
								.getRealPath(
										"/asansoretiket/" + "/"
												+ "asansorEtiket.jasper"), null,
						beanCollectionDataSource);
		

	}

}
