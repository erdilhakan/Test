package tr.org.mmo.asansor.servlets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.util.PDFMergerUtility;

import tr.org.mmo.asansor.dto.EskiRaporDTO;
import tr.org.mmo.asansor.dto.RaporDTO;

/**
 * Servlet implementation class TumRaporlarServlet
 */
@WebServlet("/TumRaporlarServlet")
public class TumRaporlarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TumRaporlarServlet() {
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
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {

			List<RaporDTO> raporlar = new ArrayList<RaporDTO>();
			List<EskiRaporDTO> teslimRaporlar = new ArrayList<EskiRaporDTO>();
			boolean bool = (boolean) request.getSession().getAttribute(
					"raporTeslim");
			List<byte[]> l = new ArrayList<byte[]>();
			if (bool){
				teslimRaporlar = (List<EskiRaporDTO>) request.getSession().getAttribute(
						"raporlar");
				for (EskiRaporDTO r : teslimRaporlar) {
					if (r.getDosya()!=null ){
				l.add((byte[]) r.getDosya());
					}
				}
				
			}else{
				raporlar = (List<RaporDTO>) request.getSession().getAttribute(
						"raporlar");
				for (RaporDTO r : raporlar) {
						if (r.getDosya()!=null ){
					l.add((byte[]) r.getDosya());
						}

				}	
			}
			
			
			

			request.getSession().removeAttribute("raporlar");
			request.getSession().removeAttribute("raporTeslim");
			response.setHeader("Content-disposition", "attachment;filename=\""
					+ "Raporlar.pdf" + "\"" + ";");
			
			byte[] pdf = mergePDFData(l);
			response.setContentType("application/pdf");
			response.getOutputStream().write(pdf);

			response.getOutputStream().close();
			response.flushBuffer();

		} catch (Exception e) {
			PrintWriter pw=response.getWriter();
			pw.println("<h1>"+e.getMessage()+"</h1>");
			pw.close();
			try{
			response.getWriter().write(e.getMessage());
			
			
			response.getWriter().close();
			}catch(Exception e1){
				
			}
		}

	}

	private void mergePDFStreams(OutputStream oStream,
			List<InputStream> inputStreams) throws IOException {
		if (oStream != null && inputStreams != null && !inputStreams.isEmpty()) {
			PDFMergerUtility mergerUtility = new PDFMergerUtility();
			mergerUtility.addSources(inputStreams);
			mergerUtility.setDestinationStream(oStream);

			try {
				mergerUtility.mergeDocuments();
			} catch (COSVisitorException cosve) {
				throw new IOException(cosve);
			}
		}
	}

	private byte[] mergePDFData(List<byte[]> inputData) throws IOException {
		byte[] retData = new byte[0];

		if (inputData != null && !inputData.isEmpty()) {
			List<InputStream> inputs = new ArrayList<InputStream>();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			try {
				for (byte[] tmp : inputData) {
					if (tmp != null && tmp.length > 0) {
						ByteArrayInputStream bis = new ByteArrayInputStream(tmp);
						inputs.add(bis);
					}
				}

				mergePDFStreams(bos, inputs);
				retData = bos.toByteArray();
			} finally {
				// close the streams
				if (bos != null)
					bos.close();
				if (inputs != null && inputs.size() > 0) {
					for (InputStream is : inputs) {
						if (is != null)
							is.close();
					}
				}
			}
		}

		return retData;
	}

}
