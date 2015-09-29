package tr.org.mmo.asansor.managedbeans;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.datatype.DatatypeConfigurationException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;













import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import tr.org.mmo.asansor.beans_.AnaSoru;
import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.beans_.RaporDataSource;
import tr.org.mmo.asansor.beans_.RaporSource;
import tr.org.mmo.asansor.beans_.ReferansDenetimKayitKontrolSorularListesi;
import tr.org.mmo.asansor.business.AsansorBusiness;
import tr.org.mmo.asansor.business.BasvuruBusiness;
import tr.org.mmo.asansor.business.BelediyeBusiness;
import tr.org.mmo.asansor.business.FirmaBusiness;
import tr.org.mmo.asansor.business.KontrolBusiness;
import tr.org.mmo.asansor.business.RandevuBusiness;
import tr.org.mmo.asansor.business.RaporBusiness;
import tr.org.mmo.asansor.business.SoruListBusiness;
import tr.org.mmo.asansor.business.TemsilcilikBusiness;
import tr.org.mmo.asansor.business.WebServiceBusiness;
import tr.org.mmo.asansor.dto.AsansorKapsamDTO;
import tr.org.mmo.asansor.dto.BasvuruAsansorDTO;
import tr.org.mmo.asansor.dto.BasvuruDTO;
import tr.org.mmo.asansor.dto.BelediyeDTO;
import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.dto.BinaKisiDTO;
import tr.org.mmo.asansor.dto.CihazDTO;
import tr.org.mmo.asansor.dto.CihazFirmaDTO;
import tr.org.mmo.asansor.dto.CihazTeknikDTO;
import tr.org.mmo.asansor.dto.DenetimKayitDTO;
import tr.org.mmo.asansor.dto.EskiRaporDTO;
import tr.org.mmo.asansor.dto.FirmaDTO;
import tr.org.mmo.asansor.dto.KontrolDTO;
import tr.org.mmo.asansor.dto.KontrolFirmaKatilimDTO;
import tr.org.mmo.asansor.dto.KontrolTestDTO;
import tr.org.mmo.asansor.dto.MuayeneKurulusDTO;
import tr.org.mmo.asansor.dto.OnTanimliTestDTO;
import tr.org.mmo.asansor.dto.RandevuMuhendisDTO;
import tr.org.mmo.asansor.dto.RaporDTO;
import tr.org.mmo.asansor.dto.RaporKayitDTO;
import tr.org.mmo.asansor.dto.RaporTeslimDTO;
import tr.org.mmo.asansor.dto.ReferansDenetimSorularEslestirmeDTO;
import tr.org.mmo.asansor.dto.SoruDTO;
import tr.org.mmo.asansor.dto.SoruOnTanimliDTO;
import tr.org.mmo.asansor.dto.UavtCihaz;
import tr.org.mmo.asansor.dto.YapiKonusuDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;
import tr.org.mmo.asansor.models.EskiRaporDataModel;
import tr.org.mmo.asansor.models.RaporDataModel;
import tr.org.mmo.asansor.util.DateUtils;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.StringSort;
import tr.org.mmo.asansor.util.Util;
import btest.*;

import com.lowagie.text.pdf.PdfReader;

@ManagedBean
@ViewScoped
public class RaporBean implements Serializable {
	/**
	 * 
	 */
	private int rapor = 0;

	@ManagedProperty(value = "#{sessionBean}")
	private SessionBean sessionBean;
	
	@ManagedProperty("#{binaBean}")
	private BinaBean binaBean;
	private int onayliRaporIptalKod=-1;
	DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
	private static final long serialVersionUID = 3840924595554512119L;
	private Date secilenTarih;
	
	private JasperPrint jasperPrint;
	private String raporAdi;
	private String ay = DateUtils.getMonth(Calendar.getInstance().get(
			Calendar.MONTH))
			+ " ayı ";
	private String raporPath;
	private String fileName;
	private RaporTeslimDTO slctRapor = new RaporTeslimDTO();
	private List<RaporDTO> raporlar;
	private List<EskiRaporDTO> raporTeslimList;
	private List<RaporDTO> onaysizRaporlar;
	private List<RaporDTO> onayliRaporlar;
	private List<RaporDTO> slctRaporlar;
	private List<RaporDTO> filteredRaporlar;
	private List<EskiRaporDTO> slctRaporlarTeslim;
	private List<EskiRaporDTO> filteredRaporlarTeslim;
	String path = ((ServletContext) FacesContext.getCurrentInstance()
			.getExternalContext().getContext())
			.getRealPath("/resources/raporlar/");
	@ManagedProperty(value = "#{applicationBean.fileMap}")
	private HashMap<Integer, List<File>> fileMap;
	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;
	private int binaId;
	private RaporDTO secilen;
	private List<RaporDTO> selected;
	FileOutputStream fos = null;
	private String pdf;
	private String mediaPdf = "rapor.pdf";
	private RaporDataModel raporModel;
	private RaporDataModel raporOnayModel;
	private EskiRaporDataModel raporTeslimModel;
	private String raporAction;

	public RaporDataModel getRaporModel() {
		return raporModel;
	}

	public void setRaporModel(RaporDataModel raporModel) {
		this.raporModel = raporModel;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRaporPath() {
		return raporPath;
	}

	public void setRaporPath(String raporPath) {
		this.raporPath = raporPath;
	}

	public String getRaporAdi() {
		return raporAdi;
	}

	public void setRaporAdi(String raporAdi) {
		this.raporAdi = raporAdi;
	}

	private List<RaporDataSource> list;
	private List<RaporSource> listt;
	private HashMap<Object, Object> parameter = new HashMap<Object, Object>();

	private void init(int cihazTipi) throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = null;
		beanCollectionDataSource = new JRBeanCollectionDataSource(listt);
		// if (cihazTipi == 66)
		// beanCollectionDataSource = new JRBeanCollectionDataSource(listt);
		// else
		// beanCollectionDataSource = new JRBeanCollectionDataSource(list);

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		jasperPrint = JasperFillManager
				.fillReport(
						request.getSession()
								.getServletContext()
								.getRealPath(
										"/periyodikkontrolraporlari/" + "/"
												+ raporAdi), parameter,
						beanCollectionDataSource);
		// jasperPrint=JasperFillManager.fillReport(ctx.getRealPath("\\periyodikkontrolraporlari\\"+raporAdi),parameter,beanCollectionDataSource);

	}

	public void pdf(int raporNo, int cihazTipi) throws JRException,
			IOException, UpdateException {
		init(cihazTipi);

		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();

		// httpServletResponse.addHeader("Content-disposition","attachment; filename="+fileName);

		// httpServletResponse.setLocale(arg0)
		httpServletResponse.setLocale(new Locale("tr"));

		ServletOutputStream servletOutputStream = httpServletResponse
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
				fileName);

		pdfExporter.exportReport();
		if (raporNo > 0) {
			PdfReader pr = new PdfReader(fileName);

			FileInputStream fis = new FileInputStream(fileName);

			new RaporBusiness().raporKaydet(raporNo, fis, pr.getFileLength());
		}

		servletOutputStream.close();
		FacesContext.getCurrentInstance().responseComplete();

		// servletOutputStream.flush();
		// servletOutputStream.close();

		// Raporu Tabloya Yaz

	}

	public void pdfOnay(int raporNo, int cihazTipi) throws JRException,
			IOException, UpdateException {
		init(cihazTipi);

		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();

		// httpServletResponse.addHeader("Content-disposition","attachment; filename="+fileName);

		// httpServletResponse.setLocale(arg0)
		httpServletResponse.setLocale(new Locale("tr"));

		ServletOutputStream servletOutputStream = httpServletResponse
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
				fileName);

		pdfExporter.exportReport();
		if (raporNo > 0) {
			PdfReader pr = new PdfReader(fileName);

			FileInputStream fis = new FileInputStream(fileName);

			new RaporBusiness().raporKaydet(raporNo, fis, pr.getFileLength());
		}

		servletOutputStream.close();
		FacesContext.getCurrentInstance().responseComplete();

		// servletOutputStream.flush();
		// servletOutputStream.close();

		// Raporu Tabloya Yaz

	}

	public List<RaporDataSource> getList() {
		return list;
	}

	public void setList(List<RaporDataSource> list) {
		this.list = list;
	}

	public HashMap<Object, Object> getParameter() {
		return parameter;
	}

	public void setParameter(HashMap<Object, Object> parameter) {
		this.parameter = parameter;
	}

	public List<RaporDTO> getRaporlar() {
		return raporlar;
	}

	public void setRaporlar(List<RaporDTO> raporlar) {
		this.raporlar = raporlar;
	}

	public RaporDTO getSecilen() {
		return secilen;
	}

	public void setSecilen(RaporDTO secilen) {
		this.secilen = secilen;
	}
private boolean raporTeslimGoster=true;


	public boolean isRaporTeslimGoster() {
	return raporTeslimGoster;
}

public void setRaporTeslimGoster(boolean raporTeslimGoster) {
	this.raporTeslimGoster = raporTeslimGoster;
}

	@PostConstruct
	public void postConstruct() {
		raporlar = new ArrayList<RaporDTO>();
		raporModel = new RaporDataModel(raporlar);
		raporOnayModel = new RaporDataModel(raporlar);
		raporTeslimModel = new EskiRaporDataModel(new ArrayList<EskiRaporDTO>());
		onaysizRaporlar = new ArrayList<RaporDTO>();
		onayliRaporlar = new ArrayList<RaporDTO>();
		raporTeslimList = new ArrayList<EskiRaporDTO>();
		path.replace("\\", "/");
		path += "/";
		try {
			Calendar now = Calendar.getInstance();
			now.set(Calendar.DAY_OF_MONTH, 1);
			getTumRaporlar(now.getTime());
		} catch (ReadException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			e.printStackTrace();
		}
		if (FacesContext.getCurrentInstance().getViewRoot().getViewId().contains("raporteslim")){
			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			
			Object o=request.getSession().getAttribute("raporTeslimGor");
			request.getSession().removeAttribute("raporTeslimGor");
			if (o!=null && o.toString().equals("E")){
				raporTeslimGoster=false;
				try {
					raporTeslimList = new RaporBusiness().getRaporlarTeslimEdilmeyenRaporlar();
				} catch (ReadException e) {
					raporTeslimList=new ArrayList<EskiRaporDTO>();
				}

				raporTeslimModel = new EskiRaporDataModel(raporTeslimList);
			}
			
		}
	}

	private void getTumRaporlar(Date raporTarihi) throws ReadException {

		raporlar = new RaporBusiness().getRaporlarOnay(raporTarihi);

		for (RaporDTO r : raporlar) {

			if (r.getOnayDurum() == null) {
				onaysizRaporlar.add(r);

			} else if (r.getOnayDurum().equals("O")) {
				onayliRaporlar.add(r);
			} else {
				onaysizRaporlar.add(r);
			}

		}
		if (onayliRaporlar.size() > 0) {
			try {
				onayliRaporlar = new RaporBusiness()
						.getRaporKayit(onayliRaporlar);
			} catch (CRUDException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			}
		}
		raporModel = new RaporDataModel(onaysizRaporlar);
		raporOnayModel = new RaporDataModel(onayliRaporlar);

	}

	public String raporAc() {
		/*
		 * try {
		 * 
		 * if ((new File(pdf)).exists()) {
		 * 
		 * Process p = Runtime .getRuntime()
		 * .exec("rundll32 url.dll,FileProtocolHandler "+mediaPdf);
		 * 
		 * p.waitFor();
		 * 
		 * } else {
		 * 
		 * FacesContext.getCurrentInstance().addMessage(null,new
		 * FacesMessage(Messages._DOSYABULUNAMADI_.getMesaj()));
		 * 
		 * }
		 * 
		 * 
		 * 
		 * } catch (Exception ex) { ex.printStackTrace(); }
		 */
		try {

			File pdfFile = new File(pdf);
			if (pdfFile.exists()) {

				if (Desktop.isDesktopSupported()) {
					Desktop.getDesktop().open(pdfFile);
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(Messages._DOSYABULUNAMADI_
									.getMesaj()));
				}

			} else {
				FacesContext.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(Messages._DOSYABULUNAMADI_
										.getMesaj()));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";

	}

	public String getMediaPdf() {
		return mediaPdf;
	}

	public void setMediaPdf(String mediaPdf) {
		this.mediaPdf = mediaPdf;
	}

	public String getRaporAction() {
		return raporAction;
	}

	public void setRaporAction(String raporAction) {
		this.raporAction = raporAction;
	}

	public void onayListener(RaporDTO rapor) {
		rapor.setRaporOnayDisabled(false);
		ArrayList<RaporDTO> l = new ArrayList<RaporDTO>();
		for (RaporDTO r : onaysizRaporlar) {
			if (r.getRaporId() == rapor.getRaporId()) {
				l.add(rapor);

			} else {
				l.add(r);
			}
		}
		raporModel = new RaporDataModel(l);
	}

	public void asansorDenetimKayitSil(RaporDTO rapor) {
	
		ServisSonuc servisSonuc;
		try {
			servisSonuc = new WebServiceBusiness()
					.asansorDenetimKayitSilme(rapor.getRaporKayit()
							.getDenetimKayitId());
			if (!servisSonuc.isHata()) {
				new RaporBusiness().raporKayitSil(rapor.getRaporKayit());
				for (RaporDTO r : onayliRaporlar) {
					if (r.getRaporId() == rapor.getRaporId()) {
						r.setRaporKayit(new RaporKayitDTO());
						break;
					}
				}
				raporOnayModel = new RaporDataModel(onayliRaporlar);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(servisSonuc.getMesaj().getValue()));

			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(servisSonuc.getMesaj().getValue()));
			}

		} catch (CRUDException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		TabView tabView=(TabView)FacesContext.getCurrentInstance().getViewRoot().findComponent("formrapor:tabview");
		if (tabView!=null){
			tabView.setActiveIndex(1);
		}

	}

	public void asansorDenetimKayit(RaporDTO rapor) {

		DenetimKayit denetimKayit = new DenetimKayit();
		TabView tabView=(TabView)FacesContext.getCurrentInstance().getViewRoot().findComponent("formrapor:tabview");
		try {

			KontrolDTO kontrol = new KontrolBusiness()
					.getCihazKontrolByKontrolId(rapor.getKontrolId());
			RaporKayitDTO kayitRapor=new RaporKayitDTO();
			kayitRapor= new KontrolBusiness().checkDenetimKayit(kontrol.getKontrolId(),kontrol.getRandevuId(),kontrol.getCihazId(),kontrol.isRevizyonRapor());
			if (kontrol.getCihaz().getUavtKod()<= 0) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								Messages._SECILENCIHAZUAVTYEKAYITLIDEGIL_
										.getMesaj(), null));
				
				
				if (tabView!=null){
					tabView.setActiveIndex(1);
				}
			} else {
				BelediyeDTO belediye = new BelediyeDTO();
				try {
					belediye = new BelediyeBusiness().getBelediyeByKod(kontrol
							.getBina().getBelediye());
					
				} catch (CRUDException e) {
					belediye = new BelediyeDTO();
				}
				if (belediye.getUavtKod()<=0){
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									Messages._PROTOKOLIMZALAYANBELEDIYEUAVTKODYOK_
											.getMesaj(), null));
					if (tabView!=null){
						tabView.setActiveIndex(1);
					}
				}else{
			ObjectFactory factory = new ObjectFactory();

			ObjectFactory objectFactory = new ObjectFactory();
			List<KontrolTestDTO> testYanitList = new ArrayList<KontrolTestDTO>();
			testYanitList = new SoruListBusiness().getTestYanitlari(rapor
					.getKontrolId());
			denetimKayit.setAsansorNo(kontrol.getCihaz().getUavtKod());
			
			
			/*******************yeni eklendi * (16.09.2015)****************************/
			
			denetimKayit.setPeriyodikKontrolTipi(objectFactory.createDenetimKayitPeriyodikKontrolTipi(kontrol.getKontrolTuru().equals("N")?1:2));
			Calendar kontrolTarihi = Calendar.getInstance();
			kontrolTarihi.setTime(kontrol.getKontrolBaslangicTarihi());
			Calendar kontrolSaat=Calendar.getInstance();
			kontrolSaat.setTime(kontrol.getKontrolBaslangicSaati());
			kontrolTarihi.set(Calendar.HOUR,kontrolSaat.get(Calendar.HOUR));
			kontrolTarihi.set(Calendar.HOUR_OF_DAY,kontrolSaat.get(Calendar.HOUR_OF_DAY));
			kontrolTarihi.set(Calendar.MINUTE,kontrolSaat.get(Calendar.MINUTE));
			kontrolTarihi.set(Calendar.SECOND,kontrolSaat.get(Calendar.SECOND));
			denetimKayit.setDenetimTarih(DateUtils
					.CalendarToXMLGregorianCalendar(kontrolTarihi));
			
			
			kontrolTarihi.setTime(kontrol.getKontrolBitisTarihi());
			
			kontrolSaat.setTime(kontrol.getKontrolBitisSaati());
			kontrolTarihi.set(Calendar.HOUR,kontrolSaat.get(Calendar.HOUR));
			kontrolTarihi.set(Calendar.HOUR_OF_DAY,kontrolSaat.get(Calendar.HOUR_OF_DAY));
			kontrolTarihi.set(Calendar.MINUTE,kontrolSaat.get(Calendar.MINUTE));
			kontrolTarihi.set(Calendar.SECOND,kontrolSaat.get(Calendar.SECOND));
			denetimKayit.setDenetimBitis(objectFactory.createDenetimKayitDenetimBitis(DateUtils
					.CalendarToXMLGregorianCalendar(kontrolTarihi)));
			/***************************************************************************/
			
			
			 kontrolTarihi = Calendar.getInstance();
			kontrolTarihi.setTime(kontrol.getKontrolBitisTarihi());
			denetimKayit.setDenetimYil(kontrolTarihi.get(Calendar.YEAR));
			/******************************** Time Eklendi yukarıda yeni eklenenler içerisinde (16.09.2015) *******************
			
			denetimKayit.setDenetimTarih(DateUtils
					.CalendarToXMLGregorianCalendar(kontrolTarihi));
			***************************************************************************/
			DenetimKayitDetayAsansorBilgi denetimKayitDetayAsansorBilgi = new DenetimKayitDetayAsansorBilgi();
			denetimKayitDetayAsansorBilgi = setDenetimKayitAsansorBilgi(
					kontrol.getCihaz(), kontrol.getCihazTeknikBilgiler(),
					kontrol.getBina());
			ArrayOfDenetimKayitDetayAsansorBilgi arrayOfDenetimKayitDetayAsansorBilgi = new ArrayOfDenetimKayitDetayAsansorBilgi();
			arrayOfDenetimKayitDetayAsansorBilgi
					.getDenetimKayitDetayAsansorBilgi().add(
							denetimKayitDetayAsansorBilgi);

			denetimKayit
					.setDenetimKayitDetayAsansorBilgi(objectFactory.createArrayOfDenetimKayitDetayAsansorBilgi(arrayOfDenetimKayitDetayAsansorBilgi));
//			denetimKayit
//			.setDenetimKayitDetayAsansorBilgi(factory
//					.createArrayOfDenetimKayitDetayAsansorBilgi(arrayOfDenetimKayitDetayAsansorBilgi));
			DenetimKayitDetayBakimSozlesme denetimKayitDetayBakimSozlesme = new DenetimKayitDetayBakimSozlesme();
			denetimKayitDetayBakimSozlesme = setDenetimKayitDetayBakimSozlesme(
					kontrol.getFirma(), kontrol.getCihaz());
			ArrayOfDenetimKayitDetayBakimSozlesme arrayOfDenetimKayitDetayBakimSozlesme = new ArrayOfDenetimKayitDetayBakimSozlesme();
			arrayOfDenetimKayitDetayBakimSozlesme
					.getDenetimKayitDetayBakimSozlesme().add(
							denetimKayitDetayBakimSozlesme);
		//	denetimKayit
		//			.setDenetimKayitDetayBakimSozlesme(arrayOfDenetimKayitDetayBakimSozlesme);
			
 			denetimKayit
 			.setDenetimKayitDetayBakimSozlesme(factory
 					.createArrayOfDenetimKayitDetayBakimSozlesme(arrayOfDenetimKayitDetayBakimSozlesme));

			DenetimKayitDetayBinaSorumlusuBilgileri denetimKayitDetayBinaSorumluBilgileri = new DenetimKayitDetayBinaSorumlusuBilgileri();
			denetimKayitDetayBinaSorumluBilgileri = setDenetimKayitDetayBinaSorumlusuBilgileri(
					kontrol.getBinaKisiList().size() > 0 ? kontrol
							.getBinaKisiList().get(0) : new BinaKisiDTO(),
					kontrol.getBina());
			ArrayOfDenetimKayitDetayBinaSorumlusuBilgileri arrayOfDenetimKayitDetayBinaSorumlusuBilgileri = new ArrayOfDenetimKayitDetayBinaSorumlusuBilgileri();
	
 			denetimKayit
 			.setDenetimKayitDetayBinaSorumlusuBilgileri(factory
 					.createArrayOfDenetimKayitDetayBinaSorumlusuBilgileri(arrayOfDenetimKayitDetayBinaSorumlusuBilgileri));

			DenetimKayitDetayFirmaBilgi denetimKayitDetayFirmaBilgi = new DenetimKayitDetayFirmaBilgi();
			denetimKayitDetayFirmaBilgi = setDenetimKayitDetayFirmaBilgi(kontrol
					.getFirma(),kontrol.getCihaz().getCihazId(),kontrol.getKontrolFirmaKatilimDTO()==null?false:kontrol.getKontrolFirmaKatilimDTO().isFirmaKontroleKatildi());
			ArrayOfDenetimKayitDetayFirmaBilgi arrayOfDenetimKayitDetayFirmaBilgi = new ArrayOfDenetimKayitDetayFirmaBilgi();
			arrayOfDenetimKayitDetayFirmaBilgi.getDenetimKayitDetayFirmaBilgi()
					.add(denetimKayitDetayFirmaBilgi);

 			denetimKayit
 			.setDenetimKayitDetayFirmaBilgi(factory
 					.createArrayOfDenetimKayitDetayFirmaBilgi(arrayOfDenetimKayitDetayFirmaBilgi));

			DenetimKayitDetayTechizatBilgi denetimKayitDetayTechizatBilgi = new DenetimKayitDetayTechizatBilgi();
			denetimKayit.setDenetimKayitDetayTechizatBilgi(null);
			List<DenetimKayitDetaySoru> denetimKayitDetaySoru = new ArrayList<DenetimKayitDetaySoru>();
			/* 15.09.2015 tarihi itibari ile bakanlık servisinden çıkarıldı.*/
		//	denetimKayit.setMuayeneEsasAlinacakStandard(new AsansorBusiness().getCihazKapsamId(kontrol.getCihaz()));
			boolean yeniRaporMu=false;
			yeniRaporMu=kontrol.getKontrolBaslangicTarihi().compareTo(sessionBean.getBakanlikSoru().getTarih())>=0?true:false;
			
			
			denetimKayitDetaySoru = setDenetimKayitDetaySoru(testYanitList,
					kontrol.getCihaz().getTip(),new AsansorBusiness().getCihazKapsamId(kontrol.getCihaz()),yeniRaporMu);
			ArrayOfDenetimKayitDetaySoru arrayOfDenetimKayitDetaySoru = new ArrayOfDenetimKayitDetaySoru();
			for (DenetimKayitDetaySoru d : denetimKayitDetaySoru) {
				arrayOfDenetimKayitDetaySoru.getDenetimKayitDetaySoru().add(d);
			}
//			denetimKayit
//					.setDenetimKayitDetaySoru(arrayOfDenetimKayitDetaySoru);
//			
 			denetimKayit
 			.setDenetimKayitDetaySoru(factory
 					.createArrayOfDenetimKayitDetaySoru(arrayOfDenetimKayitDetaySoru));

//			denetimKayit.setBasvuruKayitNo(kontrol
//					.getRandevuId());
			

 
  	denetimKayit.setBasvuruKayitNo(objectFactory.createDenetimKayitBasvuruKayitNo(kontrol
 			.getRandevuId()));
			DenetimKayitSonuc denetimKayitSonuc = new DenetimKayitSonuc();
			denetimKayitSonuc = setDenetimKayitSonuc(kontrol);
			if (kayitRapor.getDenetimKayitId()>0){
			denetimKayitSonuc.setDenetimKayitId(kayitRapor.getDenetimKayitId());
			}
			ArrayOfDenetimKayitSonuc arrayOfDenetimKayitSonuc = new ArrayOfDenetimKayitSonuc();
			arrayOfDenetimKayitSonuc.getDenetimKayitSonuc().add(
					denetimKayitSonuc);
	//		denetimKayit.setDenetimKayitSonuc(arrayOfDenetimKayitSonuc);
 			denetimKayit.setDenetimKayitSonuc(factory
 					.createArrayOfDenetimKayitSonuc(arrayOfDenetimKayitSonuc));
			Calendar denetimTarih = Calendar.getInstance();
			denetimTarih.setTime(kontrol.getKontrolBitisTarihi());
			denetimKayit.setDenetimTarih(DateUtils
					.CalendarToXMLGregorianCalendar(denetimTarih));
			denetimKayit.setDurum(true);
			

			
			String str=kontrol
					.getKontrolMuhendisleri().size() > 0 ? kontrol
					.getKontrolMuhendisleri().get(0).getAdiSoyadi()
					: " ";
			denetimKayit
					.setMuayeneKisiAdSoyad(factory.createDenetimKayitMuayeneKisiAdSoyad(str));
			denetimKayit.setMuayeneKisiUnvan(factory.createDenetimKayitMuayeneKisiUnvan("Mühendis"));
			
			denetimKayit.setProtokolImzalayanBelediye(factory.createDenetimKayitProtokolImzalayanBelediye(belediye.getUavtKod()));
			

			
			ServisSonucOfDenetimKayitSonuc8Zb117HL servisSonucOfDenetimKayitSonuc = new ServisSonucOfDenetimKayitSonuc8Zb117HL();
			if (kayitRapor.getDenetimKayitId()>0){
			
				servisSonucOfDenetimKayitSonuc=new WebServiceBusiness().asansorDenetimKayitGuncelle(denetimKayit);
				
			}else{
			servisSonucOfDenetimKayitSonuc = new WebServiceBusiness()
					.asansorDenetimKayit(denetimKayit);
			}

			if (!servisSonucOfDenetimKayitSonuc.isHata()) {
				if (kayitRapor.getDenetimKayitId()>0){
					new RaporBusiness().raporKayitSil(kayitRapor);
				}
				RaporKayitDTO raporKayit = new RaporKayitDTO();
				raporKayit.setDenetimDurumId(servisSonucOfDenetimKayitSonuc
						.getSonuc().getValue().getDenetimDurumId());
				raporKayit.setDenetimKayitId(servisSonucOfDenetimKayitSonuc
						.getSonuc().getValue().getDenetimKayitId());
				raporKayit.setCihazId(kontrol.getCihazId());
				raporKayit.setRaporId(rapor.getRaporId());
				new RaporBusiness().raporKayitKaydet(raporKayit);
				for (RaporDTO r : onayliRaporlar) {
					if (r.getRaporId() == rapor.getRaporId()) {
						r.setRaporKayit(raporKayit);
						break;
					}
				}
				raporOnayModel = new RaporDataModel(onayliRaporlar);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(new StringBuffer(Messages._ASANSORDENETIMKAYDIYAPILDI_.getMesaj()).append(String
								.valueOf(servisSonucOfDenetimKayitSonuc
										.getSonuc().getValue()
										.getDenetimKayitId())).toString()));

			} else {
				
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(servisSonucOfDenetimKayitSonuc
								.getMesaj().getValue().toString()));
			}
			if (tabView!=null){
				tabView.setActiveIndex(1);
			}
		}
			}
		} catch (CRUDException e) {
			
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}
		
		
	}

	private DenetimKayitSonuc setDenetimKayitSonuc(KontrolDTO kontrol) {
		
		DenetimKayitSonuc denetimKayitSonuc = new DenetimKayitSonuc();
		if (kontrol!=null){
		ObjectFactory objectFactory = new ObjectFactory();
		if (kontrol.getEtiket()!=null){
		if (kontrol.getEtiket().equals("K"))
		denetimKayitSonuc.setDenetimDurumId(new Integer(1));
		if (kontrol.getEtiket().equals("S"))
			denetimKayitSonuc.setDenetimDurumId(new Integer(2));
		if (kontrol.getEtiket().equals("Y"))
			denetimKayitSonuc.setDenetimDurumId(new Integer(3));
		if (kontrol.getEtiket().equals("M"))
			denetimKayitSonuc.setDenetimDurumId(new Integer(4));
		}else{
			denetimKayitSonuc.setDenetimDurumId(new Integer(0));
		}
		denetimKayitSonuc.setDenetimKayitId(0);
		
		}
		return denetimKayitSonuc;
	}
	/* Hatalı olduğu için Değiştirildi. Metod sonuna Eski ibaresi eklendi (31.07.2015)
	 * 
	 * */
	
	private List<DenetimKayitDetaySoru> setDenetimKayitDetaySoru (
			List<KontrolTestDTO> list, int cihazTipi,int tseStandart,boolean yeniRaporMu) throws Exception{
		List<DenetimKayitDetaySoru> denetimDetaySoru = new ArrayList<DenetimKayitDetaySoru>();
		if (list!=null){
		sessionBean.referansDenetimDoldur();
			List<ReferansDenetimKayitKontrolSorularListesi> referansSorularList = new ArrayList<ReferansDenetimKayitKontrolSorularListesi>();
			int asansorTip=0;
			asansorTip=cihazTipi==66?2:1;
		List<ReferansDenetimSorularEslestirmeDTO> referansEslestirmeList = new ArrayList<ReferansDenetimSorularEslestirmeDTO>();
		if (yeniRaporMu){
			
			for (ReferansDenetimKayitKontrolSorularListesi r : sessionBean
					.getReferansDenetimKayitKontrolSorularListesi15092015Sonrasi()) {
				if (r.getCihazTipi()==asansorTip) {
					r.setSoruId(r.getId());
					referansSorularList.add(r);
				}

			}
			
		}
		else{
		for (ReferansDenetimKayitKontrolSorularListesi r : sessionBean
				.getReferansDenetimKayitKontrolSorularListesi()) {
			if (r.getCihazTipi() == cihazTipi) {
				referansSorularList.add(r);
			}

		}
		}
		
		
		if (yeniRaporMu){
			/* 
			 * Yeni Rapor da rapor gönderirken eşleştirme listesi kullanılmıyor.
			 * 
			 * 
			for (ReferansDenetimSorularEslestirmeDTO r : sessionBean.getReferansDenetimSorularEslestirmeListesi15092015Sonrasi()) {
				if (r.getAsansorTipi()==asansorTip) {
					referansEslestirmeList.add(r);
				}

			}
			*/
		}
		else{
		for (ReferansDenetimSorularEslestirmeDTO r : sessionBean.getReferansDenetimSorularEslestirmeListesi()) {
			if (r.getAsansorTipi()==asansorTip && r.getTseStandartId()==tseStandart) {
				referansEslestirmeList.add(r);
			}

		}
		}
		
		Map<Integer, DenetimKayitDetaySoru> denetimDetaySoruMap=new HashMap<Integer, DenetimKayitDetaySoru>();
		if (yeniRaporMu){
			for (ReferansDenetimKayitKontrolSorularListesi refSoru : referansSorularList) {
				DenetimKayitDetaySoru denetimKayitDetaySoru = new DenetimKayitDetaySoru();
				denetimKayitDetaySoru.setDenetimKayitSoruId(refSoru.getSoruId());
				
				ObjectFactory factory=new ObjectFactory();
				denetimKayitDetaySoru.setSoruSkala(factory.createDenetimKayitDetaySoruSoruSkala(Integer.parseInt(sessionBean
						.getReferansSoruSkalaMap().get("Uygun"))));
				denetimDetaySoruMap.put(refSoru.getSoruId(),denetimKayitDetaySoru);
				
			}
		}
		else{
		for (ReferansDenetimSorularEslestirmeDTO eslestirme : referansEslestirmeList) {
			DenetimKayitDetaySoru denetimKayitDetaySoru = new DenetimKayitDetaySoru();
			denetimKayitDetaySoru.setDenetimKayitSoruId(eslestirme.getSoruId());
			
			ObjectFactory factory=new ObjectFactory();
			denetimKayitDetaySoru.setSoruSkala(factory.createDenetimKayitDetaySoruSoruSkala(Integer.parseInt(sessionBean
					.getReferansSoruSkalaMap().get("Uygun"))));
			denetimDetaySoruMap.put(eslestirme.getSoruId(),denetimKayitDetaySoru);
			
		}
		}
		
		Map<Integer, DenetimKayitDetaySoru> denetimDetaySoruMap1=new HashMap<Integer, DenetimKayitDetaySoru>();
		if (yeniRaporMu){
			for (KontrolTestDTO test : list) {
				break1:
				for (ReferansDenetimKayitKontrolSorularListesi refSoru : referansSorularList) {
					if (test.getBakanlikSoruId()==refSoru.getSoruId()){
						
						test.setCevap(test.getCevap().replaceAll("İ", "i").replaceAll("Ğ", "g").toLowerCase());
									DenetimKayitDetaySoru denetimKayitDetaySoru = new DenetimKayitDetaySoru();
									denetimKayitDetaySoru.setDenetimKayitSoruId(refSoru.getSoruId());
									
									String str = test.getCevap();
									if (str.contains("uygulanmaz"))
										str="Uygulanamaz";
									if (str.contains("degil") || str.contains("değil"))
										str="Uygun Degil";
									else{
										str="Uygun";
									}
									ObjectFactory factory=new ObjectFactory();
									denetimKayitDetaySoru
											.setSoruSkala(factory.createDenetimKayitDetaySoruSoruSkala(Integer
													.parseInt(sessionBean
															.getReferansSoruSkalaMap()
															.get(str))));
									denetimDetaySoruMap1.put(refSoru.getSoruId(), denetimKayitDetaySoru);
									break break1;
									
				}
						
							
						}
					
					
				}
		}
		else{
			boolean altsorulardaUygunlukDurumuVar=false;
			for (KontrolTestDTO test : list) {
				if (test.getParent()!=null && !test.getParent().trim().equals("") && Integer.parseInt(test.getParent().trim())>0){
					altsorulardaUygunlukDurumuVar=true;
					break;
				}
			}
			
		
				
			
					for (KontrolTestDTO test : list) {
					test.setCevap(test.getCevap().replace("İ", "i").replace("Ğ", "g").toLowerCase());
					if (altsorulardaUygunlukDurumuVar){
						for (ReferansDenetimKayitKontrolSorularListesi refSoru : referansSorularList) {
							if (test.getSoruId()==refSoru.getAkmSoruId()){
								DenetimKayitDetaySoru denetimKayitDetaySoru = new DenetimKayitDetaySoru();
								denetimKayitDetaySoru.setDenetimKayitSoruId(refSoru.getSoruId());
								
								String str = test.getCevap();
								if (str.contains("uygulanmaz"))
									str="Uygulanamaz";
								if (str.contains("degil") || str.contains("değil"))
									str="Uygun Degil";
								else{
									str="Uygun";
								}
								ObjectFactory factory=new ObjectFactory();
								denetimKayitDetaySoru
										.setSoruSkala(factory.createDenetimKayitDetaySoruSoruSkala(Integer
												.parseInt(sessionBean
														.getReferansSoruSkalaMap()
														.get(str))));
								denetimDetaySoruMap1.put(refSoru.getSoruId(), denetimKayitDetaySoru);
								
							}
						}
					}
					else{
						for (ReferansDenetimKayitKontrolSorularListesi refSoru : referansSorularList) {
						if (test.getSoruId()==refSoru.getParent()){
						for (OnTanimliTestDTO ott:test.getAltSorular()){
							if (ott.getSoruId()==refSoru.getAkmSoruId()){
								DenetimKayitDetaySoru denetimKayitDetaySoru = new DenetimKayitDetaySoru();
								denetimKayitDetaySoru.setDenetimKayitSoruId(refSoru.getSoruId());
								
								String str = test.getCevap();
								if (str.contains("uygulanmaz"))
									str="Uygulanamaz";
								if (str.contains("degil") || str.contains("değil"))
									str="Uygun Degil";
								else{
									str="Uygun";
								}
								ObjectFactory factory=new ObjectFactory();
								denetimKayitDetaySoru
										.setSoruSkala(factory.createDenetimKayitDetaySoruSoruSkala(Integer
												.parseInt(sessionBean
														.getReferansSoruSkalaMap()
														.get(str))));
								denetimDetaySoruMap1.put(refSoru.getSoruId(), denetimKayitDetaySoru);
								
							}
						}
						}
					}
					}
					}
				
				
			
		}
		
		
		Set<Integer> set1=denetimDetaySoruMap1.keySet();
		Iterator<Integer> it1=set1.iterator();
		while (it1.hasNext()){
			int i=it1.next();
			if (denetimDetaySoruMap.get(i)!=null)
			denetimDetaySoruMap.get(i).setSoruSkala(denetimDetaySoruMap1.get(i).getSoruSkala());
		}
		
		
		Set<Integer> set=denetimDetaySoruMap.keySet();
		Iterator<Integer> it=set.iterator();
		while (it.hasNext()){
			int i=it.next();
			denetimDetaySoru.add(denetimDetaySoruMap.get(i));
		}
		
		
			
		
		/*
		 * UAVT SORU SKALA <ReferansSoruSkalaListesiGetir> <MethodParameters>
		 * <ServisSonucOfArrayOfReferansSoruSkala8Zb117hL> <Sonuc
		 * attr0="ReferansSoruSkalaArray" isNull="false">
		 * <ReferansSoruSkalaArray0> <Aktif>True</Aktif>
		 * <DurumAciklama>Uygun</DurumAciklama> <Id>1</Id>
		 * </ReferansSoruSkalaArray0> <ReferansSoruSkalaArray1>
		 * <Aktif>True</Aktif> <DurumAciklama>Uygun Degil</DurumAciklama>
		 * <Id>2</Id> </ReferansSoruSkalaArray1> <ReferansSoruSkalaArray2>
		 * <Aktif>True</Aktif> <DurumAciklama>Uygulanamaz</DurumAciklama>
		 * <Id>3</Id> </ReferansSoruSkalaArray2> </Sonuc> <Hata>False</Hata>
		 * <HataKodu>-1</HataKodu> <Mesaj isNull="false" />
		 * </ServisSonucOfArrayOfReferansSoruSkala8Zb117hL> </MethodParameters>
		 * </ReferansSoruSkalaListesiGetir>
		 */
		}
		
		return denetimDetaySoru;
	}
	private DenetimKayitDetayFirmaBilgi setDenetimKayitDetayFirmaBilgi(
			FirmaDTO firma,int cihazId,boolean firmaKatilimDurum) {
		DateFormat df=new SimpleDateFormat("dd.MM.yyyy");
		DenetimKayitDetayFirmaBilgi denetimKayitDetayFirmaBilgi = new DenetimKayitDetayFirmaBilgi();
		ObjectFactory objectFactory = new ObjectFactory();
		
		if (firma!=null){
			denetimKayitDetayFirmaBilgi.setBakimFirmaAd(objectFactory.createDenetimKayitDetayFirmaBilgiBakimFirmaAd(Util.nullif(firma.getUnvan())));
			denetimKayitDetayFirmaBilgi.setBakimFirmaAdresi(objectFactory.createDenetimKayitDetayFirmaBilgiBakimFirmaAdresi(Util.nullif(firma.getAdres())));
			denetimKayitDetayFirmaBilgi.setBakimFirmaTelefon(objectFactory.createDenetimKayitDetayFirmaBilgiBakimFirmaTelefon(String.valueOf(firma.getTelefonNo())));
			denetimKayitDetayFirmaBilgi.setBakimFirmaUnvan(objectFactory.createDenetimKayitDetayFirmaBilgiBakimFirmaUnvan(Util.nullif(firma.getUnvan())));
	
		int firmaYapi = 0;
		if (Util.nullif(firma.getMonteEden()).equals("E")) {
			firmaYapi = 1;
		}
		if (Util.nullif(firma.getYetkiliServis()).equals("E")) {
			firmaYapi = 2;
		}
		denetimKayitDetayFirmaBilgi.setBakimFirmaYapi(objectFactory.createDenetimKayitDetayFirmaBilgiBakimFirmaYapi(firmaYapi));
		denetimKayitDetayFirmaBilgi.setBakimFirmaYetkiliAdSoyad(objectFactory.createDenetimKayitDetayFirmaBilgiBakimFirmaYetkiliAdSoyad(Util.nullif(firma.getAdSoyad())));

		
		Date cihazFirmaSozlesmeTarih=null;
		try {
			cihazFirmaSozlesmeTarih=new FirmaBusiness().getCihazAnlasmaliFirmaTarih(cihazId, firma.getKod());
		} catch (CRUDException e2) {
			cihazFirmaSozlesmeTarih=null;
		}
		if (cihazFirmaSozlesmeTarih!=null){
			Calendar cal = Calendar.getInstance();
						
						
						cal.setTime(cihazFirmaSozlesmeTarih);
						try {
							denetimKayitDetayFirmaBilgi
									.setFirmaSozlesmeTarihi(objectFactory.createDenetimKayitDetayFirmaBilgiFirmaSozlesmeTarihi(DateUtils
													.CalendarToXMLGregorianCalendar(cal)));

						} catch (DatatypeConfigurationException e) {
							Calendar cal1=Calendar.getInstance();
							cal1.set(Calendar.YEAR, 1);
							cal1.set(Calendar.MONTH, 1);
							cal1.set(Calendar.DAY_OF_YEAR, 1);
							try {
								denetimKayitDetayFirmaBilgi
										.setFirmaSozlesmeTarihi(objectFactory.createDenetimKayitDetayFirmaBilgiFirmaSozlesmeTarihi(DateUtils
												.CalendarToXMLGregorianCalendar(cal1)));
							} catch (DatatypeConfigurationException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						
				

					}else{
						Calendar cal1=Calendar.getInstance();
						cal1.set(Calendar.YEAR, 1);
						cal1.set(Calendar.MONTH, 1);
						cal1.set(Calendar.DAY_OF_YEAR, 1);
						try {
							denetimKayitDetayFirmaBilgi
									.setFirmaSozlesmeTarihi(objectFactory.createDenetimKayitDetayFirmaBilgiFirmaSozlesmeTarihi(DateUtils
											.CalendarToXMLGregorianCalendar(cal1)));
						} catch (DatatypeConfigurationException e1) {
							
							e1.printStackTrace();
						}
					}
		
		
	
		if (firma.getGecerlilikSuresi()!=null){
Calendar cal = Calendar.getInstance();
			
			
			cal.setTime(firma.getGecerlilikSuresi());
			try {
				denetimKayitDetayFirmaBilgi
						.setHizmetYeterlilikBelgesiGecerlilikTarihi(objectFactory.createDenetimKayitDetayFirmaBilgiHizmetYeterlilikBelgesiGecerlilikTarihi(DateUtils
										.CalendarToXMLGregorianCalendar(cal)));

			} catch (DatatypeConfigurationException e) {
				Calendar cal1=Calendar.getInstance();
				cal1.set(Calendar.YEAR, 1);
				cal1.set(Calendar.MONTH, 1);
				cal1.set(Calendar.DAY_OF_YEAR, 1);
				try {
					denetimKayitDetayFirmaBilgi
							.setHizmetYeterlilikBelgesiGecerlilikTarihi(objectFactory.createDenetimKayitDetayFirmaBilgiHizmetYeterlilikBelgesiGecerlilikTarihi(DateUtils
									.CalendarToXMLGregorianCalendar(cal1)));
				} catch (DatatypeConfigurationException e1) {
				
					e1.printStackTrace();
				}
			}
			
		}else{
			Calendar cal1=Calendar.getInstance();
			cal1.set(Calendar.YEAR, 1);
			cal1.set(Calendar.MONTH, 1);
			cal1.set(Calendar.DAY_OF_YEAR, 1);
			try {
				denetimKayitDetayFirmaBilgi
						.setHizmetYeterlilikBelgesiGecerlilikTarihi(objectFactory.createDenetimKayitDetayFirmaBilgiHizmetYeterlilikBelgesiGecerlilikTarihi(DateUtils
								.CalendarToXMLGregorianCalendar(cal1)));
			} catch (DatatypeConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		denetimKayitDetayFirmaBilgi.setKontrolNezaretEtmeDurum(objectFactory.createDenetimKayitDetayFirmaBilgiKontrolNezaretEtmeDurum(firmaKatilimDurum));
		

		if (firma.getSozlesmeTarihi() != null) {
			Calendar cal = Calendar.getInstance();
			
			
			cal.setTime(firma
					.getSozlesmeTarihi());
			try {
				denetimKayitDetayFirmaBilgi
						.setYetkiliServisSozlesmeTarihi(objectFactory.createDenetimKayitDetayFirmaBilgiYetkiliServisSozlesmeTarihi(DateUtils
										.CalendarToXMLGregorianCalendar(cal)));

			} catch (DatatypeConfigurationException e) {
				Calendar cal1=Calendar.getInstance();
				cal1.set(Calendar.YEAR, 1);
				cal1.set(Calendar.MONTH, 1);
				cal1.set(Calendar.DAY_OF_YEAR, 1);
				try {
					denetimKayitDetayFirmaBilgi
							.setYetkiliServisSozlesmeTarihi(objectFactory.createDenetimKayitDetayFirmaBilgiYetkiliServisSozlesmeTarihi(DateUtils
									.CalendarToXMLGregorianCalendar(cal1)));
				} catch (DatatypeConfigurationException e1) {
					
					e1.printStackTrace();
				}
			}
		} else {
			Calendar cal1=Calendar.getInstance();
			cal1.set(Calendar.YEAR, 1);
			cal1.set(Calendar.MONTH, 1);
			cal1.set(Calendar.DAY_OF_YEAR, 1);
			try {
				denetimKayitDetayFirmaBilgi
						.setYetkiliServisSozlesmeTarihi(objectFactory.createDenetimKayitDetayFirmaBilgiYetkiliServisSozlesmeTarihi(DateUtils
								.CalendarToXMLGregorianCalendar(cal1)));
			} catch (DatatypeConfigurationException e1) {
				
				e1.printStackTrace();
			}
			
		}
		}
		return denetimKayitDetayFirmaBilgi;
	}
	
	
	private DenetimKayitDetayBinaSorumlusuBilgileri setDenetimKayitDetayBinaSorumlusuBilgileri(
			BinaKisiDTO kisi, BinaDTO bina) {
		DenetimKayitDetayBinaSorumlusuBilgileri denetimKayitDetayBinaSorumlusuBilgileri = new DenetimKayitDetayBinaSorumlusuBilgileri();
		if (bina!=null && kisi!=null){
		ObjectFactory objectFactory = new ObjectFactory();
		String adres;
		try {
			adres =  bina.getMahalle().trim() + " MAH. "
					+ bina.getCaddeSokak().trim()
					+ " NO:" + bina.getBinaNo().trim()+" "+bina.getIlceAdi().trim()+" "+bina.getIlAdi().trim();
				
		} catch (Exception e) {
			adres = bina.getAcikAdres();
		}
		denetimKayitDetayBinaSorumlusuBilgileri.setYapiAdres(objectFactory.createDenetimKayitDetayBinaSorumlusuBilgileriYapiAdres(adres));
		denetimKayitDetayBinaSorumlusuBilgileri
				.setBinaSorumluAdSoyad(objectFactory.createDenetimKayitDetayBinaSorumlusuBilgileriBinaSorumluAdSoyad(Util.nullif(kisi.getAdi())
						+ " " + Util.nullif(kisi.getSoyadi())));
		denetimKayitDetayBinaSorumlusuBilgileri
				.setBinaSorumluIletisimEPosta(objectFactory.createDenetimKayitDetayBinaSorumlusuBilgileriBinaSorumluIletisimEPosta(Util.nullif(kisi
						.getePosta())));
		denetimKayitDetayBinaSorumlusuBilgileri
				.setBinaSorumluIletisimTelNo(objectFactory.createDenetimKayitDetayBinaSorumlusuBilgileriBinaSorumluIletisimTelNo(String
						.valueOf(kisi.getTelefonNo())));
		denetimKayitDetayBinaSorumlusuBilgileri.setYapiAdres(objectFactory.createDenetimKayitDetayBinaSorumlusuBilgileriYapiAdres(Util.nullif(bina.getAcikAdres())));
		
		/*******************yeni eklendi * (16.09.2015)****************************/
		denetimKayitDetayBinaSorumlusuBilgileri.setAdaNo(objectFactory.createDenetimKayitDetayBinaSorumlusuBilgileriAdaNo(bina.getAda()));
		denetimKayitDetayBinaSorumlusuBilgileri.setParselNo(objectFactory.createDenetimKayitDetayBinaSorumlusuBilgileriParselNo(bina.getParsel()));
		/***************************************************************************/
		int yapiTip = 0;

		switch (bina.getYapiTip()) {
		case 1:
			yapiTip = 4; // İŞYERİ
			break;
		case 2:
			yapiTip = 4; // İŞYERİ
			break;
		case 3:
			yapiTip = 1; // APARTMAN
			break;
		case 4:
			yapiTip = 1; // APARTMAN
			break;
		case 5:
			yapiTip = 4; // İŞYERİ
			break;
		case 6:
			yapiTip = 5; // DİĞER
			break;
		case 7:
			yapiTip = 5; // DİĞER
			break;
		case 8:
			yapiTip = 4; // İŞYERİ
			break;
		case 9:
			yapiTip = 4; // İŞYERİ
			break;
		case 10:
			yapiTip = 5; // DİĞER
			break;
		case 11:
			yapiTip = 4; // İŞYERİ
			break;
		case 12:
			yapiTip = 2; // KAMU
			break;
		case 13:
			yapiTip = 5; // DİĞER
			break;
		case 14:
			yapiTip = 3; // MÜSTAKİL KONUT
			break;
		case 15:
			yapiTip = 3; // MÜSTAKİL KONUT
			break;
		case 16:
			yapiTip = 3; // MÜSTAKİL KONUT
			break;
		default:
			yapiTip = 5; // DİĞER
			break;
		}
		denetimKayitDetayBinaSorumlusuBilgileri.setYapininSinifi(objectFactory.createDenetimKayitDetayBinaSorumlusuBilgileriYapininSinifi(yapiTip));
		

	}
		return denetimKayitDetayBinaSorumlusuBilgileri;
	}
	
	private DenetimKayitDetayBakimSozlesme setDenetimKayitDetayBakimSozlesme(
			FirmaDTO firma, CihazDTO cihaz) {
		DenetimKayitDetayBakimSozlesme denetimKayitDetayBakimSozlesme = new DenetimKayitDetayBakimSozlesme();
		ObjectFactory objectFactory = new ObjectFactory();
		CihazFirmaDTO cihazFirma = new CihazFirmaDTO();
		try {
			cihazFirma = new FirmaBusiness().getCihazAnlasmaliFirma(
					cihaz.getCihazId(), firma.getKod());
			denetimKayitDetayBakimSozlesme.setBakimSozlesmesiDurum(objectFactory.createDenetimKayitDetayBakimSozlesmeBakimSozlesmesiDurum(cihazFirma
					.getFirmaId() > 0 ? 1 : 0));
			
		} catch (ReadException e) {
			cihazFirma = new CihazFirmaDTO();
			denetimKayitDetayBakimSozlesme.setBakimSozlesmesiDurum(objectFactory.createDenetimKayitDetayBakimSozlesmeBakimSozlesmesiDurum(0));
		}
		catch (Exception e) {
			cihazFirma = new CihazFirmaDTO();
			denetimKayitDetayBakimSozlesme.setBakimSozlesmesiDurum(objectFactory.createDenetimKayitDetayBakimSozlesmeBakimSozlesmesiDurum(0));
		}
		
		

		
		try{
			denetimKayitDetayBakimSozlesme.setTseBelgeDurum(objectFactory.createDenetimKayitDetayBakimSozlesmeTseBelgeDurum(Util.nullif(firma.getTseBelgesi()).equals("E")?1:0));
			denetimKayitDetayBakimSozlesme
					.setYetkiliServisSozlesmeDurum(objectFactory.createDenetimKayitDetayBakimSozlesmeYetkiliServisSozlesmeDurum(Util.nullif(firma.getServisSozlesme()).equals("E")?1:0));
		}catch(NullPointerException e){
			denetimKayitDetayBakimSozlesme.setTseBelgeDurum(objectFactory.createDenetimKayitDetayBakimSozlesmeTseBelgeDurum(0));
			denetimKayitDetayBakimSozlesme
					.setYetkiliServisSozlesmeDurum(objectFactory.createDenetimKayitDetayBakimSozlesmeYetkiliServisSozlesmeDurum(0));
		}
		
		/************************** yeni eklendi (16.09.2015) ***************************/
		denetimKayitDetayBakimSozlesme.setBakimKlavuzu(objectFactory.createDenetimKayitDetayBakimSozlesmeBakimKlavuzu(false));
		/*********************************************************************************/

		return denetimKayitDetayBakimSozlesme;
	}
	

	private DenetimKayitDetayAsansorBilgi setDenetimKayitAsansorBilgi(
			CihazDTO cihaz, List<CihazTeknikDTO> cihazTeknikBilgiler,
			BinaDTO bina) {
		DenetimKayitDetayAsansorBilgi denetimKayitDetayAsansorBilgi = new DenetimKayitDetayAsansorBilgi();
		
		ObjectFactory objectFactory = new ObjectFactory();
		denetimKayitDetayAsansorBilgi.setKimlikNumarasi(objectFactory.createDenetimKayitDetayAsansorBilgiKimlikNumarasi(String.valueOf(cihaz.getUavtKod())));
		//denetimKayitDetayAsansorBilgi.setKimlikNumarasi(objectFactory.createAnyURI(String.valueOf(cihaz.getUavtKod())));
		System.out.println(cihaz.getUavtEtiket());
		denetimKayitDetayAsansorBilgi.setAsansorTuru(cihaz.getTip() == 66 ? 2
				: 1);
		/***********************************(16.09.2015) Yeni Eklendi *************************/
		
		denetimKayitDetayAsansorBilgi.setAsansorDurumu(objectFactory.createDenetimKayitDetayAsansorBilgiAsansorDurumu(false));
		
		
		/****************************************************************************************/
		String makinaMotorSeriNo="";
		for (CihazTeknikDTO ctd : cihazTeknikBilgiler) {
			if (cihaz.getTip() == 66) {
				switch (ctd.getDegerId()) {
				case 1: // makinaMarkaTip
					denetimKayitDetayAsansorBilgi
							.setMarkaTipModel(objectFactory.createDenetimKayitDetayAsansorBilgiMarkaTipModel( ctd
									.getCevap()));
//					denetimKayitDetayAsansorBilgi
//					.setMarkaTipModel(objectFactory.createString(ctd
//							.getCevap()));
					break;
					
				case 164: //Makina Seri Numarası
					makinaMotorSeriNo=makinaMotorSeriNo+" "+ctd.getCevap()==null?"":ctd.getCevap().trim();
					
					break;
				case 2: // yonetmelikAdiTarihi

					break;
				case 151: // asansörün yeri

					break;
				case 152: // kapasite kisi

					break;
				case 153: // seri no

					denetimKayitDetayAsansorBilgi.setSeriNo(objectFactory.createDenetimKayitDetayAsansorBilgiSeriNo(ctd.getCevap()));

					break;
				case 158:// kapasiteAgirlik
					try {
						denetimKayitDetayAsansorBilgi.setBeyanYuku(Integer
								.parseInt(ctd.getCevap() == null ? "0" : ctd
										.getCevap()));
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setBeyanYuku(0);
					}
					break;
				case 155: // sinifi

					break;
				case 154: // imal yılı
					try {
						denetimKayitDetayAsansorBilgi.setMontajYil(Integer
								.parseInt(ctd.getCevap() == null ? "0" : ctd
										.getCevap()));
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setMontajYil(0);
					}
					break;
				case 160:
					try {
						denetimKayitDetayAsansorBilgi.setBeyanHiz(new BigDecimal(ctd.getCevap() == null ? "0" : ctd
								.getCevap()));
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setBeyanHiz(new BigDecimal(0));
					}
					break;
				case 161: // motor gücü

					break;
				case 190: // motor gücü birimi

					break;
				case 162: // motor seri no
					makinaMotorSeriNo=makinaMotorSeriNo+" "+ctd.getCevap()==null?"":ctd.getCevap().trim();
					
					break;
				
				case 194: // ce tse işaret
					break;
				case 167: // askı tipi

					break;
				case 168: // halat sayısı

					break;
				case 169: // halat çapı

					break;
				case 165: // onaylanmış kuruluş no

					break;
				case 157:// durakSayisi
					try {
						denetimKayitDetayAsansorBilgi.setDurakSayi(Integer
								.parseInt(ctd.getCevap() == null ? "0" : ctd
										.getCevap()));
						denetimKayitDetayAsansorBilgi.setKatSayisi(Integer
								.parseInt(ctd.getCevap() == null ? "0" : ctd
										.getCevap()));
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setDurakSayi(0);
						denetimKayitDetayAsansorBilgi.setKatSayisi(0);
					}
					break;
				case 195: // seyir mesafesi
					try {
						denetimKayitDetayAsansorBilgi.setSeyirMesafesi(objectFactory.createDenetimKayitDetayAsansorBilgiSeyirMesafesi(ctd.getCevap() == null ?new BigDecimal(0) : new BigDecimal(ctd.getCevap().trim())));
								
						
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setSeyirMesafesi(objectFactory.createDenetimKayitDetayAsansorBilgiSeyirMesafesi(new BigDecimal(0)));
					}
					break;
					

				case 209: // tanimi
					try {
						
						denetimKayitDetayAsansorBilgi.setMakinaMakaraDaireDurumu(objectFactory.createDenetimKayitDetayAsansorBilgiMakinaMakaraDaireDurumu(ctd.getCevap()==null?false:ctd.getCevap().trim().equalsIgnoreCase("MDRL")?true:false));
								
						
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setMakinaMakaraDaireDurumu(objectFactory.createDenetimKayitDetayAsansorBilgiMakinaMakaraDaireDurumu(false));
					}
					break;
				case 163: // fren tipi

					break;
				case 170: // kumanda tipi

					break;
				case 159: // montaj firması

					break;
				case 201: // kabin alanı

					break;
				case 166: // kapsam

					break;
				case 156: // özel şartlar
					break;
				case 191: // ruhsat tescil belgesi
					denetimKayitDetayAsansorBilgi.setTescilBelgesi(ctd
							.getCevap().equals("VAR") ? true : false);

					break;
				case 193: // ruhsat tescil belgesi tarihi
					Calendar cal = Calendar.getInstance();
					try {
						DateFormat df=new SimpleDateFormat("dd.MM.yyyy");
						cal.setTime(df
								.parse(ctd.getCevap() == null ? "01.01.0001" : ctd
										.getCevap().trim()));
						try {
							denetimKayitDetayAsansorBilgi
									.setTescilTarihi(DateUtils
											.CalendarToXMLGregorianCalendar(cal));
							denetimKayitDetayAsansorBilgi
							.setRuhsatSicilTarih(objectFactory.createDenetimKayitDetayAsansorBilgiRuhsatSicilTarih(DateUtils
									.CalendarToXMLGregorianCalendar(cal)));
						} catch (DatatypeConfigurationException e) {
							Calendar cal1=Calendar.getInstance();
							cal1.set(Calendar.YEAR, 1);
							cal1.set(Calendar.MONTH, 1);
							cal1.set(Calendar.DAY_OF_YEAR, 1);
							try {
								denetimKayitDetayAsansorBilgi.setTescilTarihi(DateUtils
										.CalendarToXMLGregorianCalendar(cal1));
								denetimKayitDetayAsansorBilgi
								.setRuhsatSicilTarih(objectFactory.createDenetimKayitDetayAsansorBilgiRuhsatSicilTarih(DateUtils
										.CalendarToXMLGregorianCalendar(cal1)));
							} catch (DatatypeConfigurationException e1) {
							
								e1.printStackTrace();
							}
						}
					} catch (ParseException e) {
						Calendar cal1=Calendar.getInstance();
					cal1.set(Calendar.YEAR, 1);
					cal1.set(Calendar.MONTH, 1);
					cal1.set(Calendar.DAY_OF_YEAR, 1);
					try {
						denetimKayitDetayAsansorBilgi.setTescilTarihi(DateUtils
								.CalendarToXMLGregorianCalendar(cal1));
						denetimKayitDetayAsansorBilgi
						.setRuhsatSicilTarih(objectFactory.createDenetimKayitDetayAsansorBilgiRuhsatSicilTarih(DateUtils
								.CalendarToXMLGregorianCalendar(cal1)));
					} catch (DatatypeConfigurationException e1) {
						
						e1.printStackTrace();
					}
					}

					break;

				case 192: // ruhsat tescil belgesi no
					break;
				default:
					break;
				}

			}

			if (cihaz.getTip() == 68) {
				switch (ctd.getDegerId()) {
				case 171: // asansorunYeri
					break;
				case 173: // montajFirmasi
					break;
				case 172: // kapasiteKisi
					break;
				case 174:// kapasiteAgirlik
					try {
						denetimKayitDetayAsansorBilgi.setBeyanYuku(Integer
								.parseInt(ctd.getCevap() == null ? "0" : ctd
										.getCevap()));
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setBeyanYuku(0);

					}
					break;
				case 175: // sinifi
					break;
				case 176: // imalYili
					try {
						denetimKayitDetayAsansorBilgi.setMontajYil(Integer
								.parseInt(ctd.getCevap() == null ? "0" : ctd
										.getCevap()));
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setMontajYil(0);

					}
					break;
				case 177: // seriNo
					denetimKayitDetayAsansorBilgi.setSeriNo(objectFactory.createDenetimKayitDetayAsansorBilgiSeriNo(ctd.getCevap()));
					//denetimKayitDetayAsansorBilgi.setMakinaMotorSeriNo(objectFactory.createDenetimKayitDetayAsansorBilgiMakinaMotorSeriNo(ctd.getCevap()));
//					denetimKayitDetayAsansorBilgi.setSeriNo(objectFactory
//							.createString(ctd.getCevap()));
					break;
				case 178: // kapsam
					break;
				case 179: // hiz
					try {
						denetimKayitDetayAsansorBilgi.setBeyanHiz(new BigDecimal(ctd.getCevap() == null ? "0" : ctd
										.getCevap()));
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setBeyanHiz(new BigDecimal(0));

					}
					break;
				case 180: // frenTipi
					break;
				case 181: // askiTipi
					break;
				case 182: // durakSayisi
					try {
						denetimKayitDetayAsansorBilgi.setDurakSayi(Integer
								.parseInt(ctd.getCevap() == null ? "0" : ctd
										.getCevap()));
						denetimKayitDetayAsansorBilgi.setKatSayisi(Integer
								.parseInt(ctd.getCevap() == null ? "0" : ctd
										.getCevap()));
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setDurakSayi(0);
						denetimKayitDetayAsansorBilgi.setKatSayisi(0);
					}
					break;

				case 196: // seyirMesafesi
					try {
						denetimKayitDetayAsansorBilgi.setSeyirMesafesi(objectFactory.createDenetimKayitDetayAsansorBilgiSeyirMesafesi(ctd.getCevap() == null ?new BigDecimal(0) : new BigDecimal(ctd.getCevap().trim())));
								
						
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setSeyirMesafesi(objectFactory.createDenetimKayitDetayAsansorBilgiSeyirMesafesi(new BigDecimal(0)));
					}
					break;
					

				case 210: // tanimi
					try {
						
						denetimKayitDetayAsansorBilgi.setMakinaMakaraDaireDurumu(objectFactory.createDenetimKayitDetayAsansorBilgiMakinaMakaraDaireDurumu(ctd.getCevap()==null?false:ctd.getCevap().trim().equalsIgnoreCase("MDRL")?true:false));
								
						
					} catch (Exception e) {
						denetimKayitDetayAsansorBilgi.setMakinaMakaraDaireDurumu(objectFactory.createDenetimKayitDetayAsansorBilgiMakinaMakaraDaireDurumu(false));
					}
					break;
					
				case 202: // kabinAlani
					break;
				case 183: // kumandaTipi
					break;

				case 4: // yonetmelikAdiTarihi

					break;

				case 184: // onKurNo

					break;

				case 197:// ceTseIsaret
					break;
				case 185: // halatSayisi
					break;
				case 186: // halatCapi
					break;
				case 187: // pompaMotorGucu
					break;
				case 188: // pompaMotorSeriNo
					break;

				case 3: // hidrolikGrubuMarkaModel
					denetimKayitDetayAsansorBilgi
							.setMarkaTipModel(objectFactory.createDenetimKayitDetayAsansorBilgiMarkaTipModel(ctd
									.getCevap()));
//					denetimKayitDetayAsansorBilgi
//					.setMarkaTipModel(objectFactory.createString(ctd
//							.getCevap()));
					break;
				case 189: // ozelSartlar
					break;
				case 200: // ruhsatTescilBelgesi
					denetimKayitDetayAsansorBilgi.setTescilBelgesi(ctd
							.getCevap().equals("VAR") ? true : false);
					break;

				case 199: // ruhsatTescilBelgesiTarihi
					
					Calendar cal = Calendar.getInstance();
					try {
						DateFormat df=new SimpleDateFormat("dd.MM.yyyy");
						cal.setTime(df
								.parse(ctd.getCevap() == null ?"01.01.0001" : ctd
										.getCevap().trim()));
						try {
							denetimKayitDetayAsansorBilgi
									.setTescilTarihi(DateUtils
											.CalendarToXMLGregorianCalendar(cal));
							denetimKayitDetayAsansorBilgi
							.setRuhsatSicilTarih(objectFactory.createDenetimKayitDetayAsansorBilgiRuhsatSicilTarih(DateUtils
									.CalendarToXMLGregorianCalendar(cal)));
						} catch (DatatypeConfigurationException e) {
							Calendar cal1=Calendar.getInstance();
							cal1.set(Calendar.YEAR, 1);
							cal1.set(Calendar.MONTH, 1);
							cal1.set(Calendar.DAY_OF_YEAR, 1);
							
							try {
								denetimKayitDetayAsansorBilgi.setTescilTarihi(DateUtils
										.CalendarToXMLGregorianCalendar(cal1));
								denetimKayitDetayAsansorBilgi
								.setRuhsatSicilTarih(objectFactory.createDenetimKayitDetayAsansorBilgiRuhsatSicilTarih(DateUtils
										.CalendarToXMLGregorianCalendar(cal1)));
							} catch (DatatypeConfigurationException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					} catch (ParseException e) {
						Calendar cal1=Calendar.getInstance();
						cal1.set(Calendar.YEAR, 1);
						cal1.set(Calendar.MONTH, 1);
						cal1.set(Calendar.DAY_OF_YEAR, 1);
						
						try {
							denetimKayitDetayAsansorBilgi.setTescilTarihi(DateUtils
									.CalendarToXMLGregorianCalendar(cal1));
							denetimKayitDetayAsansorBilgi
							.setRuhsatSicilTarih(objectFactory.createDenetimKayitDetayAsansorBilgiRuhsatSicilTarih(DateUtils
									.CalendarToXMLGregorianCalendar(cal1)));
						} catch (DatatypeConfigurationException e1) {
						
							e1.printStackTrace();
						}
					}

					break;

				case 198:// ruhsatTescilBelgesiNo
					break;

				default:
					break;
				}
			}
		}
		if (cihaz.getTip()==66){
		denetimKayitDetayAsansorBilgi.setMakinaMotorSeriNo(objectFactory.createDenetimKayitDetayAsansorBilgiMakinaMotorSeriNo(makinaMotorSeriNo));
		}
		denetimKayitDetayAsansorBilgi
				.setBinaAdresKodu((long) bina.getUavtKod());
		denetimKayitDetayAsansorBilgi.setKimlikNumarasi(objectFactory.createDenetimKayitDetayAsansorBilgiKimlikNumarasi(String.valueOf(cihaz.getUavtKod())));

	
		return denetimKayitDetayAsansorBilgi;
	}

	public void listener(RaporDTO rapor) {
		try {

			boolean eskiRapor = false;
			eskiRapor = new RaporBusiness().isEskiRaporMu(rapor.getRaporId());

			if (eskiRapor) {
				diger(rapor);

			} else {
				HttpServletRequest request = (HttpServletRequest) FacesContext
						.getCurrentInstance().getExternalContext().getRequest();
				request.getSession().setAttribute("dosya", rapor.getDosya());
				request.getSession().setAttribute("dosyaAdi",
						rapor.getDosyaAdi());

				try {
					FacesContext
							.getCurrentInstance()
							.getExternalContext()
							.redirect(
									FacesContext.getCurrentInstance()
											.getExternalContext()
											.getRequestContextPath()
											+ "/PDFServlet");

				} catch (IOException e) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(e.getMessage()));
					e.printStackTrace();
				}
			}
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}
		/*
		 * try { //new RaporDAOImpl().getPDFData(rapor.getRaporId()); new
		 * RaporDAOImpl().setPDFData(rapor.getDosya(),rapor.getDosyaAdi());
		 * 
		 * 
		 * mediaPdf="/resources/raporlar/"+rapor.getDosyaAdi().trim();
		 * pdf=path+rapor.getDosyaAdi().trim(); raporAc();
		 */
		/*
		 * try { media=new DefaultStreamedContent(new FileInputStream(new
		 * File(mediaPdf)),"Application/pdf"); } catch (FileNotFoundException e)
		 * { // TODO Auto-generated catch block e.printStackTrace(); }
		 * //generatePDF();
		 */
		/*
		 * TabView
		 * tView=(TabView)FacesContext.getCurrentInstance().getViewRoot()
		 * .findComponent(":formrapor:tabview"); tView.setActiveIndex(0); }
		 * catch (CRUDException e) {
		 * FacesContext.getCurrentInstance().addMessage(null, new
		 * FacesMessage(e.getMessage())); }
		 */
	}

	public void listenerTumRaporlar() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		request.getSession().setAttribute("raporlar", slctRaporlar);
		request.getSession().setAttribute("raporTeslim",false);
		try {
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							FacesContext.getCurrentInstance()
									.getExternalContext()
									.getRequestContextPath()
									+ "/TumRaporlarServlet");
		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			e.printStackTrace();
		}
	}

	public void listenerOnay(EskiRaporDTO rapor) {

		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		try {
			boolean eskiRapor = false;
			eskiRapor = new RaporBusiness().isEskiRaporMu(rapor.getRaporId());
			// try {
			// if (rapor.getRaporTarih().compareTo(df.parse("08.12.2014")) ==
			// -1) {
			if (eskiRapor) {

				diger(rapor);

			} else {
				HttpServletRequest request = (HttpServletRequest) FacesContext
						.getCurrentInstance().getExternalContext().getRequest();
				request.getSession().setAttribute("dosya", rapor.getDosya());
				request.getSession().setAttribute("dosyaAdi",
						rapor.getDosyaAdi());

				try {
					FacesContext
							.getCurrentInstance()
							.getExternalContext()
							.redirect(
									FacesContext.getCurrentInstance()
											.getExternalContext()
											.getRequestContextPath()
											+ "/PDFServlet");
				} catch (IOException e) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(e.getMessage()));
					e.printStackTrace();
				}
			}
			// }
			/*
			 * catch (ParseException e) {
			 * FacesContext.getCurrentInstance().addMessage(null, new
			 * FacesMessage(e.getMessage())); }
			 */
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}
		/*
		 * try{ new
		 * RaporDAOImpl().setPDFData(rapor.getDosya(),rapor.getDosyaAdi());
		 */
		// fos=new ImageTest().getPDFData();
		// mediaPdf="/resources/raporlar/"+rapor.getDosyaAdi().trim();
		// pdf=rapor.getDosyaYeri().trim()+rapor.getDosyaAdi().trim();
		/*
		 * mediaPdf="/resources/raporlar/"+rapor.getDosyaAdi().trim();
		 * pdf=path+rapor.getDosyaAdi().trim(); raporAc();
		 */
		/*
		 * try { media=new DefaultStreamedContent(new FileInputStream(new
		 * File(mediaPdf)),"Application/pdf"); } catch (FileNotFoundException e)
		 * { // TODO Auto-generated catch block e.printStackTrace(); }
		 * //generatePDF();
		 */
		/*
		 * TabView
		 * tView=(TabView)FacesContext.getCurrentInstance().getViewRoot()
		 * .findComponent(":formrapor:tabview"); tView.setActiveIndex(1); }
		 * catch (Exception e) {
		 * FacesContext.getCurrentInstance().addMessage(null, new
		 * FacesMessage(e.getMessage())); }
		 */

	}

	public void raporSil(RaporDTO rapor) {

		// File deleteFile = new
		// File(rapor.getDosyaYeri().trim()+rapor.getDosyaAdi().trim()) ;
		try {
			new RaporBusiness().raporSil(rapor);
			for (RaporDTO r : onaysizRaporlar) {
				if (r.getRaporId() == rapor.getRaporId()) {
					onaysizRaporlar.remove(r);
					raporModel = new RaporDataModel(onaysizRaporlar);
					break;
				}
			}
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(Messages._DOSYASILINDI_.getMesaj()));
			/*
			 * if( deleteFile.exists() ){ deleteFile.delete() ;
			 * FacesContext.getCurrentInstance().addMessage(null,new
			 * FacesMessage(Messages._DOSYASILINDI_.getMesaj())); }else{
			 * FacesContext.getCurrentInstance().addMessage(null,new
			 * FacesMessage(Messages._DOSYABULUNAMADI_.getMesaj())); }
			 */
		} catch (CRUDException e) {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}

	}
	private RaporDTO selectedOnayliRapor=new RaporDTO();
	
	
	public RaporDTO getSelectedOnayliRapor() {
		return selectedOnayliRapor;
	}

	public void setSelectedOnayliRapor(RaporDTO selectedOnayliRapor) {
		this.selectedOnayliRapor = selectedOnayliRapor;
	}

	public void onayliRaporIptal(RaporDTO rapor) {
		boolean revizyonRaporDuzenlendiMi=false;
		selectedOnayliRapor=new RaporDTO();
		if (rapor.getRaporKayit().getDenetimKayitId()<=0){
		try {
			RaporTeslimDTO raporTeslim=new RaporTeslimDTO();
			raporTeslim=new RaporBusiness().raporTeslimGetir(rapor.getRaporId());
			if (raporTeslim.getId()<=0){
			revizyonRaporDuzenlendiMi=new RaporBusiness().isRevizyonRaporVarMi(rapor.getKontrolId());
			if (!revizyonRaporDuzenlendiMi){
				onayliRaporIptalKod=-1;
				selectedOnayliRapor=rapor;
				odemesiIptalEdilecekCihaz=new BasvuruAsansorDTO();
				RequestContext.getCurrentInstance().execute("PF('confirmiptal').show()");
				
			}else{
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(Messages._RAPORICINREVIZYONDUZENLENMIS_.getMesaj()));
			}
			}else{
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(Messages._RAPORTESLIMEDILMIS_.getMesaj()));
			}
		} catch (CRUDException e) {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}
		}else{
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(Messages._RAPORBAKANLIGAKAYITLI_.getMesaj()));
		}

	}
	
	public void onayliRaporIptal(ActionEvent event) throws CRUDException{
	
		
		
		if (selectedOnayliRapor!=null && selectedOnayliRapor.getRaporId()>0){
		try{
			KontrolDTO kontrol = new KontrolDTO();
			kontrol = new KontrolBusiness().getCihazKontrolByKontrolId(selectedOnayliRapor
					.getKontrolId());

			if (kontrol != null) {

				SoruTableBean soruTableBean = new SoruTableBean();
				int kapsamId = 0;
				for (CihazTeknikDTO c : kontrol.getCihazTeknikBilgiler()) {
					for (AsansorKapsamDTO akd : ((SessionBean) Common
							.findBean("sessionBean")).getKapsamTurleri()) {
						if (c.getCevap().equals(akd.getKapsamAdi())) {
							kapsamId = akd.getId();
							break;
						}

					}
				}
				Date tarih=new Date();
				try{
				tarih=kontrol.getKontrolBaslangicTarihi().compareTo(sessionBean.getBakanlikSoru().getTarih())>=0?sessionBean.getBakanlikSoru().getTarih():null;
				}catch (Exception e) {
					tarih=null;
					}
				soruTableBean.getAnaSoru(kontrol.getCihaz().getTip(), kapsamId,
						true,tarih);
				soruTableBean.cihazTestSonucGetir(kontrol.getKontrolId());
				kontrol.setTestSorular(soruTableBean.getSoruListSmall());

				kontrol.setOnayDurum("O");
				kontrol.setRaporId(selectedOnayliRapor.getRaporId());
				prepareRapor(kontrol,"I");
				new RaporBusiness().onayliRaporIptal(selectedOnayliRapor,onayliRaporIptalKod,odemesiIptalEdilecekCihaz);
				for (RaporDTO r : onayliRaporlar) {
					if (r.getRaporId() == selectedOnayliRapor.getRaporId()) {
						r.setRaporIptal(true);
						break;
					}
				}
				raporOnayModel=new RaporDataModel(onayliRaporlar);
				selectedOnayliRapor=new RaporDTO();
				onayliRaporIptalKod=-1;
				
				odemesiIptalEdilecekCihaz=new BasvuruAsansorDTO();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(Messages._RAPORIPTALEDILDI_.getMesaj()));
				onayliRaporIptalKod=-1;
				selectedOnayliRapor=new RaporDTO();
				odemesiIptalEdilecekCihaz=new BasvuruAsansorDTO();
				RequestContext.getCurrentInstance().execute("PF('confirmiptal').hide()");
			}else{
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(Messages._RAPORIPTALEDILEMEDI_.getMesaj()));
			}
			
		
		}
		catch (CRUDException e) {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}
		}else{
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(Messages._GENEL_.getMesaj()));
		}
	}
	
	public void onayliRaporIptalVazgec(ActionEvent event) throws CRUDException{
		selectedOnayliRapor=new RaporDTO();
		onayliRaporIptalKod=-1;
		
		odemesiIptalEdilecekCihaz=new BasvuruAsansorDTO();
	}
	public void onayla(RaporDTO rapor) {

		try {
			KontrolDTO kontrol = new KontrolDTO();
			kontrol = new KontrolBusiness().getCihazKontrolByKontrolId(rapor
					.getKontrolId());

			if (kontrol != null) {

				SoruTableBean soruTableBean = new SoruTableBean();
				int kapsamId = 0;
				for (CihazTeknikDTO c : kontrol.getCihazTeknikBilgiler()) {
					for (AsansorKapsamDTO akd : ((SessionBean) Common
							.findBean("sessionBean")).getKapsamTurleri()) {
						if (c.getCevap().equals(akd.getKapsamAdi())) {
							kapsamId = akd.getId();
							break;
						}

					}
				}
				Date tarih=new Date();
				try{
				tarih=kontrol.getKontrolBaslangicTarihi().compareTo(sessionBean.getBakanlikSoru().getTarih())>=0?sessionBean.getBakanlikSoru().getTarih():null;
				}
				catch (Exception e) {
					tarih=null;
					}
				soruTableBean.getAnaSoru(kontrol.getCihaz().getTip(), kapsamId,
						true,tarih);
				soruTableBean.cihazTestSonucGetir(kontrol.getKontrolId());
				kontrol.setTestSorular(soruTableBean.getSoruListSmall());

				kontrol.setOnayDurum("O");
				kontrol.setRaporId(rapor.getRaporId());
				prepareRapor(kontrol,"");

				new RaporBusiness().raporOnayla(rapor.getKontrolId());
				for (RaporDTO r : onaysizRaporlar) {
					if (r.getRaporId() == rapor.getRaporId()) {
						onaysizRaporlar.remove(r);
						r.setOnayDurum("O");
						r.setOnayTarihi(new Date());
						onayliRaporlar.add(r);
						raporModel = new RaporDataModel(onaysizRaporlar);
						raporOnayModel = new RaporDataModel(onayliRaporlar);
						break;
					}
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(Messages._SQL_514_.getMesaj()));
			}
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}

	}

	public void onayciKontrol(RaporDTO rapor) {

		try {
			new KontrolBusiness().kontrolDurumUpdate(rapor.getKontrolId(), "T");
			for (RaporDTO r : onaysizRaporlar) {
				if (r.getRaporId() == rapor.getRaporId()) {
					r.setOnayDurum("T");
					break;
				}
			}
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}

	}

	public void muhendisOnay(RaporDTO rapor) {

		try {
			new KontrolBusiness().kontrolDurumUpdate(rapor.getKontrolId(), "M");
			for (RaporDTO r : onaysizRaporlar) {
				if (r.getRaporId() == rapor.getRaporId()) {
					r.setOnayDurum("M");
					break;
				}
			}
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}

	}

	public RaporDataModel getRaporOnayModel() {
		return raporOnayModel;
	}

	public void setRaporOnayModel(RaporDataModel raporOnayModel) {
		this.raporOnayModel = raporOnayModel;
	}

	public EskiRaporDataModel getRaporTeslimModel() {
		return raporTeslimModel;
	}

	public void setRaporTeslimModel(EskiRaporDataModel raporTeslimModel) {
		this.raporTeslimModel = raporTeslimModel;
	}

	public void raporListener(SelectEvent event)
			throws CloneNotSupportedException {
		BinaDTO binaDto = (BinaDTO) event.getObject();

		if (binaDto != null) {
			raporListele(binaDto.getBinaId());

		}

	}

	public void raporListener() {
		

		if (binaBean != null && binaBean.getBinalar() != null) {
			raporListele(binaBean.getBinalar().getBinaId());

		}

	}

	private void raporListele(int binaId) {
		try {
			raporTeslimList = new RaporBusiness().getRaporlarForBina(binaId);

			raporTeslimModel = new EskiRaporDataModel(raporTeslimList);
			if (raporTeslimList.size() <= 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(Messages._SQL_506_.getMesaj()));
			}
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}

	}

	public void raporBulById() {

		raporListele(binaId);

	}

	public int getBinaId() {
		return binaId;
	}

	public void setBinaId(int binaId) {
		this.binaId = binaId;
	}

	public List<EskiRaporDTO> getRaporTeslimList() {
		return raporTeslimList;
	}

	public void setRaporTeslimList(List<EskiRaporDTO> raporTeslimList) {
		this.raporTeslimList = raporTeslimList;
	}

	public RaporTeslimDTO getSlctRapor() {
		return slctRapor;
	}

	public void setSlctRapor(RaporTeslimDTO slctRapor) {
		this.slctRapor = slctRapor;
	}

	public void raporTeslimKaydet() {
		FacesContext fc = FacesContext.getCurrentInstance();
		FacesMessage fm;
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		
		try {
		Object obj=request.getSession().getAttribute("RaporTeslimOnay");
		request.getSession().removeAttribute("RaporTeslimOnay");
		if (obj!=null && obj.toString()!=null && obj.toString().equals("E")){
			//boolean odemeKontrol=false;
			RaporTeslimDTO teslimRaporBilgisi=new RaporTeslimDTO();
			
			try {
				teslimRaporBilgisi=(RaporTeslimDTO)slctRapor.clone();
			} catch (CloneNotSupportedException e) {
				teslimRaporBilgisi=new RaporTeslimDTO();
			}
			
			for (EskiRaporDTO r:slctRaporlarTeslim){
				slctRapor=new RaporTeslimDTO();
				slctRapor.setRaporId(r.getRaporId());
				slctRapor.setAciklama(teslimRaporBilgisi.getAciklama());
				slctRapor.setTcKimlikNo(teslimRaporBilgisi.getTcKimlikNo());
				slctRapor.setTelefonNo(teslimRaporBilgisi.getTelefonNo());
				slctRapor.setTelefonNoStr(teslimRaporBilgisi.getTelefonNoStr());
				slctRapor.setTeslimEdilenKisi(teslimRaporBilgisi.getTeslimEdilenKisi());
				slctRapor.setTeslimTarihi(teslimRaporBilgisi.getTeslimTarihi());
					new RaporBusiness().raporTeslimKaydet(slctRapor);
				
			
			}
			fm = new FacesMessage(Messages._SQL_513_.getMesaj());
			fm.setSeverity(FacesMessage.SEVERITY_INFO);
			fc.addMessage(null, fm);
		}else{
			//boolean odemeKontrol=false;
			//BasvuruDTO b = new BasvuruDTO();
			//b.setBasvuruId(new RaporBusiness().getBasvuruId(slctRapor.getRaporId()));
			//odemeKontrol=new RaporBusiness().odemeAlindiMi(b.getBasvuruId());
			//if(odemeKontrol==true){
				new RaporBusiness().raporTeslimKaydet(slctRapor);
				fm = new FacesMessage(Messages._SQL_513_.getMesaj());
				fm.setSeverity(FacesMessage.SEVERITY_INFO);
				fc.addMessage(null, fm);
			/*}else if(odemeKontrol==false){
				fm = new FacesMessage(Messages._ODEMEUYARI_.getMesaj());
				fm.setSeverity(FacesMessage.SEVERITY_ERROR);
				fc.addMessage(null, fm);
			}*/
		}
			
			
		} catch (CRUDException e) {
			fm = new FacesMessage(e.getMessage());
			fm.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(null, fm);
		}
	}

	public void raporTeslimListener(EskiRaporDTO r) {
		if (r != null) {
			try {
				slctRapor = new RaporBusiness()
						.raporTeslimGetir(r.getRaporId());

			} catch (CRUDException e) {

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			}

		}
	}
	
	public void selectedRaporTeslimListener(ActionEvent event) {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		request.getSession().removeAttribute("RaporTeslimOnay");
		if (slctRaporlarTeslim!=null && slctRaporlarTeslim.size()>0){
			request.getSession().setAttribute("RaporTeslimOnay","E");
			RequestContext.getCurrentInstance().execute("PF('dialog').show()");
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Rapor seçiniz !", ""));
		}
		
	}

	public List<RaporDTO> getSelected() {
		return selected;
	}

	public void setSelected(List<RaporDTO> selected) {
		this.selected = selected;
	}

	public List<RaporDTO> getSlctRaporlar() {
		return slctRaporlar;
	}

	public void setSlctRaporlar(List<RaporDTO> slctRaporlar) {
		this.slctRaporlar = slctRaporlar;
	}

	public void openSelected(ActionEvent event) {

		FacesContext context = FacesContext.getCurrentInstance();

		if (selected != null && selected.size() > 0) {

			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();

			request.getSession().setAttribute("raporlar", selected);
			
			request.getSession().setAttribute("raporTeslim",false);
			try {
				context.getExternalContext().redirect(
						context.getExternalContext().getRequestContextPath()
								+ "/TumRaporlarServlet");
			} catch (IOException e) {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
				e.printStackTrace();
			}

		} else {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Rapor seçiniz !", ""));
		}
	}
	public void openSelectedTeslim(ActionEvent event) {

		FacesContext context = FacesContext.getCurrentInstance();

		

			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();

			request.getSession().setAttribute("raporlar", slctRaporlarTeslim);
			request.getSession().setAttribute("raporTeslim",true);

			try {
				context.getExternalContext().redirect(
						context.getExternalContext().getRequestContextPath()
								+ "/TumRaporlarServlet");
			} catch (IOException e) {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
				e.printStackTrace();
			}

		
	}
	public List<RaporSource> getListt() {
		return listt;
	}

	public void setListt(List<RaporSource> listt) {
		this.listt = listt;
	}

	@SuppressWarnings("unused")
	private void diger(EskiRaporDTO eskiRaporDTO) {

		String path = "//var//pdf//";

		String raporAdi = "";
		int binaTescilNo = 0;
		for (EskiRaporDTO e : raporTeslimList) {
			e.setBinaTescilNo(e.getBinaTescilNo());
			if (e.getRaporId() == eskiRaporDTO.getRaporId()) {
				binaTescilNo = e.getBinaTescilNo();
				raporAdi = e.getBinaTescilNo() + "__" + e.getBasvuruId() + "__"
						+ e.getEskiKontrolKodu() + ".pdf";

				break;
			}
		}
		File file = null;

		if (!raporAdi.equals("")) {

			file = new File(path + raporAdi);

			if (file != null) {
				try {
					raporGonder(file);
				} catch (CRUDException e1) {
					try {
						raporFromMap(raporAdi, binaTescilNo);
					} catch (CRUDException e2) {
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(e1.getMessage()));
						e2.printStackTrace();
					}

				}
			} else {
				try {
					raporFromMap(raporAdi, binaTescilNo);
				} catch (CRUDException e2) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(e2.getMessage()));
					e2.printStackTrace();
				}
			}

		} else {
			addMessage();
		}

	}

	@SuppressWarnings("unused")
	private void diger(RaporDTO rapor) {

		String path = "//var//pdf//";

		String raporAdi = "";
		int binaTescilNo = 0;
		for (RaporDTO e : raporlar) {

			if (e.getRaporId() == rapor.getRaporId()) {
				binaTescilNo = e.getBinaTescilNo();
				raporAdi = e.getBinaTescilNo() + "__" + e.getBasvuruId() + "__"
						+ e.getEskiKontrolKodu() + ".pdf";

				break;
			}
		}
		File file = null;

		if (!raporAdi.equals("")) {

			file = new File(path + raporAdi);

			if (file != null) {
				try {
					raporGonder(file);
				} catch (CRUDException e1) {
					try {
						raporFromMap(raporAdi, binaTescilNo);
					} catch (CRUDException e2) {
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(e1.getMessage()));
						e2.printStackTrace();
					}

				}
			} else {
				try {
					raporFromMap(raporAdi, binaTescilNo);
				} catch (CRUDException e2) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(e2.getMessage()));
					e2.printStackTrace();
				}
			}

		} else {
			addMessage();
		}

	}

	public void raporGonder(File file) throws CRUDException {
		byte[] fileBytes;

		fileBytes = raporToByteArray(file);
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		request.getSession().setAttribute("dosya", fileBytes);
		request.getSession().setAttribute("dosyaAdi", file.getName());

		try {
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							FacesContext.getCurrentInstance()
									.getExternalContext()
									.getRequestContextPath()
									+ "/PDFServlet");

		} catch (IOException e) {

			e.printStackTrace();
			throw new CRUDException("Rapor bulunamadı", null);

		}

	}

	public void readFileFromMap(List<File> files, String raporAdi)
			throws CRUDException {
		byte[] fileBytes;
		for (File file : files) {
			if (file.getName().equals(raporAdi)) {

				fileBytes = raporToByteArray(file);
				HttpServletRequest request = (HttpServletRequest) FacesContext
						.getCurrentInstance().getExternalContext().getRequest();
				request.getSession().setAttribute("dosya", fileBytes);
				request.getSession().setAttribute("dosyaAdi", file.getName());

				try {
					FacesContext
							.getCurrentInstance()
							.getExternalContext()
							.redirect(
									FacesContext.getCurrentInstance()
											.getExternalContext()
											.getRequestContextPath()
											+ "/PDFServlet");

				} catch (IOException e) {
					e.printStackTrace();
					throw new CRUDException("Rapor bulunamadı", null);
				}

				break;
			}

		}

	}

	private void raporFromMap(String raporAdi, int binaTescilNo)
			throws CRUDException {

		int j = 0;
		j = (binaTescilNo / 1000) % 1000;
		List<File> fileList = new ArrayList<File>();
		try {
			fileList = fileMap.get(j);
			if (fileList.size() > 0)
				readFileFromMap(fileList, raporAdi);
			else
				throw new CRUDException("Dosya Bulunamadı", null);
		} catch (NullPointerException e) {
			throw new CRUDException(Messages._NULLPOINTER_.getMesaj(), null);
		}

	}

	private void addMessage() {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Dosya Bulunamadı"));
	}

	private byte[] raporToByteArray(File f) throws CRUDException {
		FileInputStream fileInputStream = null;
		byte[] bFile = new byte[(int) f.length()];

		try {
			// convert file into array of bytes
			fileInputStream = new FileInputStream(f);
			fileInputStream.read(bFile);
			fileInputStream.close();
			/*
			 * for (int i = 0; i < bFile.length; i++) {
			 * System.out.print((char)bFile[i]); }
			 */
			// System.out.println("Done");
		} catch (FileNotFoundException e) {
			throw new CRUDException("Rapor Bulunamadı !", null);
		} catch (Exception e) {
			throw new CRUDException(Messages._SQL_507_.getMesaj(), null);
		}
		return bFile;

	}

	public HashMap<Integer, List<File>> getFileMap() {
		return fileMap;
	}

	public void setFileMap(HashMap<Integer, List<File>> fileMap) {
		this.fileMap = fileMap;
	}

	public List<RaporDTO> getFilteredRaporlar() {
		return filteredRaporlar;
	}

	public void setFilteredRaporlar(List<RaporDTO> filteredRaporlar) {
		this.filteredRaporlar = filteredRaporlar;
	}

	public int getRapor() {
		return rapor;
	}

	public void setRapor(int rapor) {
		this.rapor = rapor;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public Date getSecilenTarih() {
		return secilenTarih;
	}

	public void setSecilenTarih(Date secilenTarih) {
		this.secilenTarih = secilenTarih;
	}

	public String getAy() {
		return ay;
	}

	public void setAy(String ay) {
		this.ay = ay;
	}

	public void raporByTarihListener(ActionEvent event) {
		if (secilenTarih.compareTo(new Date()) > 0) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(Messages._HATALITARIH_.getMesaj()));

		} else {
			DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
			raporlar = new ArrayList<RaporDTO>();
			onayliRaporlar = new ArrayList<RaporDTO>();
			onaysizRaporlar = new ArrayList<RaporDTO>();
			try {
				ay = df.format(secilenTarih) + " - " + df.format(new Date())
						+ " ";
				getTumRaporlar(secilenTarih);
			} catch (ReadException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			}
		}

	}

	public void prepareRapor(KontrolDTO kontrol,String raporIptalDurum) {
		/*
		 * raporun değişmesi ile birlikte bu method hazırlandı. hidrolik asansör
		 * raporu değişirse pdfrapor methodu silinecek. 28.11.2014
		 */
		if (kontrol.getKontrolBaslangicTarihi().compareTo(sessionBean.getBakanlikSoru().getTarih())>=0){
			yeniRapor(kontrol,raporIptalDurum);
		}else{
			if (kontrol.getCihaz().getTip() == 66) {
				elektrikliAsansorRapor(kontrol,raporIptalDurum);
			} else {
				pdfRapor(kontrol,raporIptalDurum);
			}
		}
		
	}
	private void yeniRapor(KontrolDTO kontrol,String raporIptalDurum) {

		try {
			
			
				
				
					this.setRaporAdi("asansorkontrolraporu.jasper");
				
			
			String path = ((ServletContext) FacesContext.getCurrentInstance()
					.getExternalContext().getContext())
					.getRealPath("/resources/raporlar/");

			path.replace("\\", "/");
			path += "/";
			// System.out.println(path);
			this.setRaporPath(path);

			this.setFileName(kontrol.getBina().getBinaId() + "-"
					+ kontrol.getCihazId() + "-" + df.format(new Date())
					+ (kontrol.isRevizyonRapor() ? "(REV)" : "") + ".pdf");

			HashMap<Object, Object> parameter = new HashMap<Object, Object>();

			parameter = kontrol.getCihaz().getTip() == 66 ? getElektrikliAsansorParametreler(kontrol)
					: getHidrolikAsansorParametreler(kontrol);
			try{
				kontrol.setKontrolFirmaKatilimDTO(new KontrolBusiness().getFirmaKatilim(kontrol.getKontrolId()));
			}catch(CRUDException e){
				kontrol.setKontrolFirmaKatilimDTO(new KontrolFirmaKatilimDTO());
			}
			parameter.put("firmaPersonelAdSoyad", kontrol.getKontrolFirmaKatilimDTO().getFirmaGorevli1());
			parameter.put("firmaPersonelGorevi", kontrol.getKontrolFirmaKatilimDTO().getFirmaGorevli1Gorev());
			MuayeneKurulusDTO muayeneKurulus=new MuayeneKurulusDTO();
			try{
				if (kontrol.getBina().getIl()>0 && kontrol.getBina().getIlce()>0){
					muayeneKurulus=new TemsilcilikBusiness().getTemsilcilik(kontrol.getBina().getIl(), kontrol.getBina().getIlce());
					if (muayeneKurulus.getKod()<=0){
						muayeneKurulus=new TemsilcilikBusiness().getSube(kontrol.getBina().getIl());
					}
				}else{
					if (kontrol.getBina().getIl()>0){
						muayeneKurulus=new TemsilcilikBusiness().getSube(kontrol.getBina().getIl());
					}
				}
				
			}catch(CRUDException e){
				muayeneKurulus=new MuayeneKurulusDTO();
			}
			BelediyeDTO belediye = new BelediyeDTO();
			try {
				belediye = new BelediyeBusiness().getBelediyeByKod(kontrol
						.getBina().getBelediye());
				
			} catch (CRUDException e) {
				belediye = new BelediyeDTO();
			}
			parameter.put("belediye",belediye.getAdi());
			parameter.put("aTipiMuayeneKurulusuAdres", muayeneKurulus.getAdres());
			parameter.put("aTipiMuayeneKurulusuTelefon", muayeneKurulus.getTelefonNo());
			parameter.put("aTipiMuayeneKurulusuFaks",muayeneKurulus.getFaks());
			parameter.put("aTipiMuayeneKurulusuEPosta", muayeneKurulus.getePosta());
			
			parameter.put("raporOnayTarihi", kontrol.getOnayTarihi());
			parameter.put("etiket",kontrol.getEtiket());
			parameter.put("pAciklamalar", kontrol.getAciklamalar() == null ? ""
					: kontrol.getAciklamalar().replace('İ', 'i').toLowerCase());
			parameter.put(
					"raporOnayDurum",
					kontrol.getOnayDurum() == null ? "R" : (kontrol
							.getOnayDurum().equals("O") ? kontrol
							.getOnayDurum() : "R"));
			parameter.put("logo", new KontrolBean().setAsansorRaporLogo());

			

			Map<Integer, List<String>> map=new HashMap<Integer, List<String>>();
		
			
			for (AnaSoru as : kontrol.getTestSorular()) {
				int uygunsuzlukKisim=0;
				 List<String> aciklamaList=new ArrayList<String>();
				for (SoruDTO s:as.getAltSorular()){
					aciklamaList=new ArrayList<String>();
					uygunsuzlukKisim=s.getYildiz().trim().equalsIgnoreCase("**")?1:s.getYildiz().trim().equalsIgnoreCase("*")?2:s.getYildiz().trim().equalsIgnoreCase("")?3:0;
					String kapsam=(String)parameter.get("kapsam");
					if (!kapsam.contains("TS EN 81-1") && !kapsam.contains("TS EN 81-2") && !kapsam.contains("TS EN 81-1+A3") && !kapsam.contains("TS EN 81-2+A3") && !kapsam.contains("TS EN 81-20")){
					
					if (uygunsuzlukKisim==3){
						if (s.getSinif()==4){
							uygunsuzlukKisim=4;
						}
					}
					}
					
					 for (int i : s.getSelectedListId()) {
							if (i > 0) {
								for (SoruOnTanimliDTO sod : s.getList()) {
									if (i == sod.getId()) {
										aciklamaList.add(s.getSorun() + "-" + sod.getAciklama());
									}
								}
							}
						}
					 
					 List<String> strList=new ArrayList<String>();
						int j=0;
						if (map.get(uygunsuzlukKisim)!=null && map.get(uygunsuzlukKisim).size()>0){
							strList=map.get(uygunsuzlukKisim);
							j=strList.size();
						}
						for (String s1:aciklamaList){
							strList.add(new StringBuffer().append(++j).append(")").append(s1).toString());
						}
						map.put(uygunsuzlukKisim, strList);
					
				}
				
				
				for (SoruDTO s:as.getOptionalAltSorular()){
					 aciklamaList=new ArrayList<String>();
					uygunsuzlukKisim=s.getYildiz().trim().equalsIgnoreCase("**")?1:s.getYildiz().trim().equalsIgnoreCase("*")?2:s.getYildiz().trim().equalsIgnoreCase("")?3:0;
					String kapsam=(String)parameter.get("kapsam");
					if (!kapsam.contains("TS EN 81-1") && !kapsam.contains("TS EN 81-2") && !kapsam.contains("TS EN 81-1+A3") && !kapsam.contains("TS EN 81-2+A3") && !kapsam.contains("TS EN 81-20")){
					
					if (uygunsuzlukKisim==3){
						if (s.getSinif()==4){
							uygunsuzlukKisim=4;
						}
					}
					}
					 
					 for (int i : s.getSelectedListId()) {
							if (i > 0) {
								for (SoruOnTanimliDTO sod : s.getList()) {
									if (i == sod.getId()) {
										aciklamaList.add(sod.getAciklama());
									}
								}
							}
						}
				List<String> strList=new ArrayList<String>();
						int j=0;
						if (map.get(uygunsuzlukKisim)!=null && map.get(uygunsuzlukKisim).size()>0){
							strList=map.get(uygunsuzlukKisim);
							j=strList.size();
						}
						for (String s1:aciklamaList){
							strList.add(new StringBuffer().append(++j).append(")").append(s1).toString());
						}
						map.put(uygunsuzlukKisim, strList);
					
				}
				
				
				
				
				
				
			}
			Set<Integer> set=map.keySet();
			Iterator<Integer> it=set.iterator();
			List<RaporSource> list = new ArrayList<RaporSource>();
			int mapSize=map.size();
			int mapESize=0;
			while (it.hasNext()){
			int k=it.next();
			if (map.get(k)!=null && map.get(k).size()>0){
				mapESize=map.get(k).size();
				break;
			}
			}
			if (map!=null && mapSize>0 && mapESize>0){
				
				RaporSource r = new RaporSource();
				r.setfKriter("HEADER");
				list.add(r);
				
				
			}
		
			//while (it.hasNext()){
				
				
		//		int i=it.next();
			if (map!=null && mapSize>0 && mapESize>0){
				int q=0;
			for (int i=1;i<5;i++){
				if (map.get(i)!=null && map.get(i).size()>0){
				int j=0;
				q++; /*Uygunsuzluk bölümlerini taşımak için i yerine kondu. */
				int arrSize=map.get(i).size();
				for (String s:map.get(i)){
					RaporSource r = new RaporSource();
					 if (j==arrSize-1){
						 r.setfKriter("DETAIL1");
					 }else{
						r.setfKriter("DETAIL");
					 }
						if (j==0){
							r.setSira(q+".");
							
						}
						else{
							r.setSira("");
						}
						r.setfAciklama(s);
					
								
					 	list.add(r);
						j++;
					}
				}/* UYGUNSUZLUK OLMAYAN BÖLÜMLER ÇIKMASIN
				else{
					RaporSource r = new RaporSource();	
					 r.setfKriter("DETAIL1");
					 r.setfAciklama("");
					 r.setSira(i+".");
					 list.add(r);
				}*/
				
				}
			}
				
				
			
			
		
			if (map!=null && mapSize>0 && mapESize>0){
				RaporSource r = new RaporSource();
				r.setfKriter("FOOTER0");
				list.add(r);
				 r = new RaporSource();
				
				r.setfKriter("FOOTER1");
				r.setfAciklama("Notlar :");
				list.add(r);
				//if (map.get(1)!=null && map.get(1).size()>0){
				int q=0;
				Set<Integer> set1=map.keySet();
				Iterator<Integer> it1=set1.iterator();
				while (it1.hasNext()){
				int ii=it1.next();
				if (map.get(ii).size()>0){
				r = new RaporSource();
				r.setfKriter("FOOTER1");
				q++;
				switch (ii) {
				case 1:
					r.setfAciklama(" "+q+".Kısımdaki uygunsuzluklar GÜVENSİZ olarak ifade edilmektedir."+(kontrol.getKontrolTuru().equals("N") ?"Düzeltme süresi 1 aydır.":""));
					break;
				case 2:
					r.setfAciklama(" "+q+".Kısımdaki uygunsuzluklar KUSURLU olarak ifade edilmektedir."+(kontrol.getKontrolTuru().equals("N") ?" Düzeltme süresi 2 aydır.":""));
					break;
				case 3:
					r.setfAciklama(" "+q+".Kısımdaki uygunsuzluklar HAFİF KUSURLU olarak ifade edilmektedir. Düzeltme süresi 12 aydır. ");
					break;
				case 4:
					r.setfAciklama(" "+q+".Kısımdaki Uygunsuzluklar HAFİF KUSURLU olarak ifade edilmektedir. Düzeltme süresi 48 aydır. ");
					break;
				default:
					break;
				}
				list.add(r);
				}
				}
					
				
				
				if (kontrol.getKontrolTuru().equals("E") && (kontrol.getEtiket().equals("S") || kontrol.getEtiket().equals("K") )) {
					r = new RaporSource();
					r.setfKriter("FOOTER1");
					r.setfAciklama("Not:Asansör İşletme,Bakım ve Periyodik Kontrol Yönetmeliği Madde 24 gereği takip kontrolü sonucu güvensiz/kusurlu olan asansörünüz güvenli hale getirilinceye kadar ilgili idare tarafından mühürlenir. ");
					list.add(r);
					}
				
				
				
				
				r = new RaporSource();
				
				r.setfKriter("FOOTER2");
				
				list.add(r);
				
			}

			this.setParameter(parameter);
			this.setListt(list);

			RaporDTO raporDTO = new RaporDTO();
			raporDTO.setDosyaAdi(this.getFileName());

			raporDTO.setKontrolId(kontrol.getKontrolId());
			raporDTO.setRaporTarihi(new Date());
			int raporNo = 0;
			raporNo = kontrol.getRaporId();

			parameter.put("raporNo", String.valueOf(raporNo));
			parameter.put("raporTarihi", new Date());

			pdfOnay(raporNo, kontrol.getCihaz().getTip());

					} catch (CRUDException e) {
			System.out.println(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		} catch (JRException e) {
			System.out.println(e.getMessage());
			FacesContext.getCurrentInstance().responseComplete();
			if (e.getMessage().contains(
					"Dosya başka bir uygulama tarafından kullanılıyor")) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			}

		} catch (IOException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().responseComplete();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}

	}
	private void elektrikliAsansorRapor(KontrolDTO kontrol,String raporIptalDurum) {

		try {

			if (kontrol.getCihaz().getTip() == 66) {
				this.setRaporAdi("periyodikKontrolRapor(ElektrikliAsansor).jasper");
			} else {
				this.setRaporAdi("periyodikKontrolRapor(HidrolikAsansor).jasper");
			}

			String path = ((ServletContext) FacesContext.getCurrentInstance()
					.getExternalContext().getContext())
					.getRealPath("/resources/raporlar/");

			path.replace("\\", "/");
			path += "/";
			// System.out.println(path);
			this.setRaporPath(path);

			this.setFileName(kontrol.getBina().getBinaId() + "-"
					+ kontrol.getCihazId() + "-" + df.format(new Date())
					+ (kontrol.isRevizyonRapor() ? "(REV)" : "") + ".pdf");

			HashMap<Object, Object> parameter = new HashMap<Object, Object>();

			parameter = kontrol.getCihaz().getTip() == 66 ? getElektrikliAsansorParametreler(kontrol): getHidrolikAsansorParametreler(kontrol);
			parameter.put("raporIptalDurum",raporIptalDurum);
			parameter.put("pAciklamalar", kontrol.getAciklamalar() == null ? "": kontrol.getAciklamalar().replace('İ', 'i').toLowerCase());
			parameter.put("raporOnayDurum",kontrol.getOnayDurum() == null ? "R" : (kontrol.getOnayDurum().equals("O") ? kontrol.getOnayDurum() : "R"));
			parameter.put("logo", new KontrolBean().setAsansorRaporLogo());

			List<RaporSource> list = new ArrayList<RaporSource>();

			RaporSource r = new RaporSource();
			r.setSira("HEADER");
			list.add(r);

			List<String> aciklamalarList = new ArrayList<String>();

			for (AnaSoru as : kontrol.getTestSorular()) {
				boolean raporaAl = true;
				as.setDurum(as.getDurum() == null ? "" : as.getDurum());
				// if (as.getDurum() != null && as.getDurum().contains("U")) {
				r = new RaporSource();
				r.setSira("DETAIL");
				aciklamalarList = new ArrayList<String>();
				r.setfKriter(as.getSorun() + " " + as.getSoru());
				r.setfSonuc(as.getDurum().replace('i', 'İ').toUpperCase());
				for (SoruDTO s : as.getAltSorular()) {
					for (int i : s.getSelectedListId()) {
						if (i > 0) {
							for (SoruOnTanimliDTO sod : s.getList()) {
								if (i == sod.getId()) {
									aciklamalarList.add(sod.getAciklama());
								}
							}
						}
					}
				}
				Collections.sort(aciklamalarList, new StringSort());
				StringBuilder sb = new StringBuilder();
				int k = 0;
				for (String s : aciklamalarList) {

					sb.append(++k).append(" ) ").append(s).append(" ");
				}

				r.setfAciklama(sb.toString().trim());
				if (as.getKapsam()[0] == (short) -1 && !as.isChecked()) {
					raporaAl = false;
				}
				if (raporaAl) {
					list.add(r);
				}
				// }

			}
			r = new RaporSource();
			r.setSira("RESULT");
			list.add(r);

			this.setParameter(parameter);
			this.setListt(list);

			RaporDTO raporDTO = new RaporDTO();
			raporDTO.setDosyaAdi(this.getFileName());

			raporDTO.setKontrolId(kontrol.getKontrolId());
			raporDTO.setRaporTarihi(new Date());
			int raporNo = 0;

			raporNo = kontrol.getRaporId();

			parameter.put("raporNo", String.valueOf(raporNo));
			parameter.put("raporTarihi", new Date());

			pdfOnay(raporNo, kontrol.getCihaz().getTip());

		} catch (CRUDException e) {
			System.out.println(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		} catch (JRException e) {
			System.out.println(e.getMessage());
			FacesContext.getCurrentInstance().responseComplete();
			if (e.getMessage().contains(
					"Dosya başka bir uygulama tarafından kullanılıyor")) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			}

		} catch (IOException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().responseComplete();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}

	}

	public String pdfRapor(KontrolDTO kontrol,String raporIptalDurum) {

		try {

			if (kontrol.getCihaz().getTip() == 66) {
				this.setRaporAdi("periyodikKontrolRapor(ElektrikliAsansor).jasper");
			} else {
				this.setRaporAdi("periyodikKontrolRapor(HidrolikAsansor).jasper");
			}

			String path = ((ServletContext) FacesContext.getCurrentInstance()
					.getExternalContext().getContext())
					.getRealPath("/resources/raporlar/");

			path.replace("\\", "/");
			path += "/";
			// System.out.println(path);
			this.setRaporPath(path);

			this.setFileName(kontrol.getBina().getBinaId() + "-"
					+ kontrol.getCihazId() + "-" + df.format(new Date())
					+ (kontrol.isRevizyonRapor() ? "(REV)" : "") + ".pdf");

			HashMap<Object, Object> parameter = new HashMap<Object, Object>();

			parameter = kontrol.getCihaz().getTip() == 66 ? getElektrikliAsansorParametreler(kontrol)
					: getHidrolikAsansorParametreler(kontrol);
			parameter.put("raporIptalDurum",raporIptalDurum);
			parameter.put("pAciklamalar", kontrol.getAciklamalar() == null ? ""
					: kontrol.getAciklamalar().replace('İ', 'i').toLowerCase());
			parameter.put(
					"raporOnayDurum",
					kontrol.getOnayDurum() == null ? "R" : (kontrol
							.getOnayDurum().equals("O") ? kontrol
							.getOnayDurum() : "R"));
			parameter.put("logo", new KontrolBean().setAsansorRaporLogo());

			List<RaporSource> list = new ArrayList<RaporSource>();

			RaporSource r = new RaporSource();
			r.setSira("HEADER");
			list.add(r);

			List<String> aciklamalarList = new ArrayList<String>();

			for (AnaSoru as : kontrol.getTestSorular()) {
				boolean raporaAl = true;
				as.setDurum(as.getDurum() == null ? "" : as.getDurum());
				// if (as.getDurum() != null && as.getDurum().contains("U")) {
				r = new RaporSource();
				r.setSira("DETAIL");
				aciklamalarList = new ArrayList<String>();
				r.setfKriter(as.getSorun() + " " + as.getSoru());
				r.setfSonuc(as.getDurum().replace('i', 'İ').toUpperCase());
				for (SoruDTO s : as.getAltSorular()) {
					for (int i : s.getSelectedListId()) {
						if (i > 0) {
							for (SoruOnTanimliDTO sod : s.getList()) {
								if (i == sod.getId()) {
									aciklamalarList.add(sod.getAciklama());
								}
							}
						}
					}
				}
				Collections.sort(aciklamalarList, new StringSort());
				StringBuilder sb = new StringBuilder();
				int k = 0;
				for (String s : aciklamalarList) {

					sb.append(++k).append(" ) ").append(s).append(" ");
				}

				r.setfAciklama(sb.toString().trim());
				if (as.getKapsam()[0] == (short) -1 && !as.isChecked()) {
					raporaAl = false;
				}
				if (raporaAl) {
					list.add(r);
				}

				// }

			}
			r = new RaporSource();
			r.setSira("RESULT");
			list.add(r);

			this.setParameter(parameter);
			this.setListt(list);

			RaporDTO raporDTO = new RaporDTO();
			raporDTO.setDosyaAdi(this.getFileName());

			raporDTO.setKontrolId(kontrol.getKontrolId());
			raporDTO.setRaporTarihi(new Date());
			int raporNo = 0;

			raporNo = kontrol.getRaporId();

			parameter.put("raporNo", String.valueOf(raporNo));
			parameter.put("raporTarihi", new Date());

			pdfOnay(raporNo, kontrol.getCihaz().getTip());
		} catch (CRUDException e) {
			System.out.println(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		} catch (JRException e) {
			System.out.println(e.getMessage());
			FacesContext.getCurrentInstance().responseComplete();
			if (e.getMessage().contains(
					"Dosya başka bir uygulama tarafından kullanılıyor")) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			}

		} catch (IOException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().responseComplete();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}

		return "";

	}

	

	public HashMap<Object, Object> getElektrikliAsansorParametreler(
			KontrolDTO kontrol) {
		HashMap<Object, Object> parameter = new HashMap<Object, Object>();
		// parameter.put("asansorTipi",kontrol.getCihaz().getTipAciklama().toUpperCase());
		parameter.put("asansorTip", kontrol.getCihaz().getTip());
		parameter.put("binaAdi", kontrol.getBina().getBinaAdi());
		parameter.put("asansorCinsi", "");
		parameter.put("asansorKimlikNo", kontrol.getCihaz().getUavtEtiket());
		
		String adres;
		try {
			adres =  kontrol.getBina().getMahalle().trim() + " MAHALLESİ "
					+ kontrol.getBina().getCaddeSokak().trim()
					+ " NO:" + kontrol.getBina().getBinaNo().trim()+" "+kontrol.getBina().getIlceAdi()+" "+kontrol.getBina().getIlAdi();
					
		} catch (Exception e) {
			adres = kontrol.getBina().getAcikAdres();
		}
		boolean yeniRapor=false;
		yeniRapor=kontrol.getKontrolBaslangicTarihi().compareTo(sessionBean.getBakanlikSoru().getTarih())>=0?true:false; 
		if (yeniRapor){
			adres=kontrol.getBina().getBinaAdi()+" "+adres;
		}
		parameter.put("binaAdresi", adres);
		parameter.put("binaKod", String.valueOf(kontrol.getBina().getBinaId()));
		

		

		parameter.put("ilce", kontrol.getBina().getIlceAdi());
		parameter.put("il", kontrol.getBina().getIlAdi());
		for (YapiKonusuDTO y : binaBean.getYapiTipler()) {
			if (y.getId() == kontrol.getBina().getYapiTip()) {
				parameter.put("yapiKonusu", y.getTip());
				break;
			}

		}
		parameter.put("kontrolTuru",
				kontrol.getKontrolTuru().equals("E") ? "Eksiklik Kontrolü"
						: "Normal Kontrol");
		parameter.put("pafta", kontrol.getBina().getPafta());
		parameter.put("ada", kontrol.getBina().getAda());
		parameter.put("parsel", kontrol.getBina().getParsel());
		List<KontrolDTO> oncekiKontrolList = new ArrayList<KontrolDTO>();
		try {
			oncekiKontrolList = new KontrolBusiness()
					.getCihazKontrollerForRapor(kontrol.getCihazId(),
							kontrol.getKontrolId());
		} catch (ReadException e) {
			oncekiKontrolList = new ArrayList<KontrolDTO>();
		}
		Calendar cal = Calendar.getInstance();

		if (kontrol.getKontrolTuru().equals("N")) {
			parameter.put("kontrolTarihi1", kontrol.getKontrolBitisTarihi());
			cal.setTime(kontrol.getKontrolBitisTarihi());
			parameter.put("kontrolTarihi2", null);
			parameter.put("kontrolTarihi3", null);
		} else {

			boolean eksiklikYazildi = false;
			for (KontrolDTO k : oncekiKontrolList) {
				if (k.getKontrolTuru().equals("E") && !eksiklikYazildi) {

					parameter.put("kontrolTarihi2", k.getKontrolBitisTarihi());
					parameter.put("kontrolTarihi3",
							kontrol.getKontrolBitisTarihi());
					eksiklikYazildi = true;
				}
				if (k.getKontrolTuru().equals("N")) {

					parameter.put("kontrolTarihi1", k.getKontrolBitisTarihi());
					cal.setTime(k.getKontrolBitisTarihi());

					if (!eksiklikYazildi) {
						parameter.put("kontrolTarihi2",
								kontrol.getKontrolBitisTarihi());
					}
					break;
				}
			}

		}
		for (BinaKisiDTO kisi : kontrol.getBinaKisiList()) {
			String binaSorumlusu = new StringBuffer(kisi.getAdi() == null ? ""
					: kisi.getAdi().trim())
					.append(" ")
					.append(kisi.getSoyadi() == null ? "" : kisi.getSoyadi()
							.trim()).toString();
			String irtibatTelefon="";
			if (kisi.getTelefonNoGsmStr()!=null && kisi.getTelefonNoGsmStr().trim().length()>5){
				irtibatTelefon=kisi.getTelefonNoGsmStr();
			}else{
				if (kisi.getTelefonNoStr()!=null && kisi.getTelefonNoStr().trim().length()>5){
					irtibatTelefon=kisi.getTelefonNoStr();
				}
			}
			
			parameter.put("binaSorumlusu", binaSorumlusu);
			parameter.put("irtibatTelefon", irtibatTelefon);

			if (kisi.getSorumlulukTuru() == 1)
				break;
		}

		if (kontrol.getFirma() != null) {
			CihazFirmaDTO cihazFirma = new CihazFirmaDTO();
			String bakimciFirmaPersonel = null;
			try {
				cihazFirma = new FirmaBusiness().getCihazAnlasmaliFirma(kontrol
						.getCihaz().getCihazId(), kontrol.getFirma().getKod());
			} catch (Exception e) {
				cihazFirma = new CihazFirmaDTO();
			}
			try {
				int kontrolId = kontrol.getKontrolId();
				bakimciFirmaPersonel = new KontrolBusiness()
						.getBakimciFirmaPersonel(kontrolId);
			} catch (Exception e1) {
				e1.getMessage();
			}

			parameter.put("binaBakimSozlesmesi", cihazFirma.getId() > 0 ? "E"
					: "H");
			parameter.put(
					"binaBakimSozlesmesiTarihi",
					cihazFirma.getSozlesmeTarih() == null ? "" : df.format(cihazFirma
							.getSozlesmeTarih()));
			try{
				String sure="";
			int sozlesmeSure=	Integer.parseInt(cihazFirma.getSozlesmeSure().trim());
			int yil = sozlesmeSure / 365;       
			sure=yil>0?yil+" YIL":"";
			int ay = (sozlesmeSure - (yil*365)) / 30; 
			sure=ay>0?sure+" "+ay+" AY":sure;
			int gun = (sozlesmeSure - ((yil*365) + (ay*30))); 
			sure=gun>0?sure+" "+gun+" GÜN":sure;
				parameter.put(
						"binaBakimSozlesmeSure",sure);
			}catch(Exception e){
				parameter.put(
						"binaBakimSozlesmeSure","");
			}

			parameter.put("bakimciFirma", kontrol.getFirma().getUnvan());
			parameter.put("bakimciFirmaAdres", kontrol.getFirma().getAdres());

			parameter.put("bakimciFirmaIlce", kontrol.getFirma().getIlceAdi());
			parameter.put("bakimciFirmaIl", kontrol.getFirma().getIlAdi());
			kontrol.getFirma().setUygunlukBelgesi(
					kontrol.getFirma().getUygunlukBelgesi() == null ? ""
							: kontrol.getFirma().getUygunlukBelgesi());
			parameter.put("ceUygunlukBelgesi", kontrol.getFirma()
					.getUygunlukBelgesi());
			parameter.put(
					"ceBelgeTarih",
					kontrol.getFirma().getGecerlilikSuresi() == null ? "" : df
							.format(kontrol.getFirma().getGecerlilikSuresi()));
			kontrol.getFirma().setMonteEden(
					kontrol.getFirma().getMonteEden() == null ? "" : kontrol
							.getFirma().getMonteEden());
			parameter.put("monteEden", kontrol.getFirma().getMonteEden());
			kontrol.getFirma().setYetkiliServis(
					kontrol.getFirma().getYetkiliServis() == null ? ""
							: kontrol.getFirma().getYetkiliServis());
			parameter.put("yetkiliServis", kontrol.getFirma()
					.getYetkiliServis());
			kontrol.getFirma().setServisSozlesme(
					kontrol.getFirma().getServisSozlesme() == null ? ""
							: kontrol.getFirma().getServisSozlesme());
			parameter.put("yetkiliServisSozlesme", kontrol.getFirma()
					.getServisSozlesme());
			parameter.put(
					"yetkiliServisSozlesmeTarih",
					kontrol.getFirma().getSozlesmeTarihi() == null ? "" : df
							.format(kontrol.getFirma().getSozlesmeTarihi()));
			kontrol.getFirma().setTseBelgeNo(
					kontrol.getFirma().getTseBelgeNo() == null ? "" : kontrol
							.getFirma().getTseBelgeNo().trim());
			parameter.put("tseHybBelgesi", kontrol.getFirma().getTseBelgesi());
			parameter.put("bakimciFirmaTelefon", kontrol.getFirma()
					.getTelefonNoStr());
			parameter.put("bakimciFirmaFaks", kontrol.getFirma()
					.getFaksStr());
			parameter.put("bakimciFirmaEPosta", kontrol.getFirma().getEposta());
			parameter
					.put("tseHybBelgesiNo", kontrol.getFirma().getTseBelgeNo());
			parameter.put("firmaPersonelAdSoyad", bakimciFirmaPersonel);
		}
		int sm = 0;
		for (RandevuMuhendisDTO rm : kontrol.getKontrolMuhendisleri()) {
			if (rm.getSorumlu().equals("E")) {
				parameter.put("muhendisSicilNo", rm.getSicilNo());
				parameter.put("muhendisAdiSoyadi", rm.getAdiSoyadi());
				parameter.put("muhendisUnvani",rm.getUnvan());
			}
			if (rm.getSorumlu().equals("H") || rm.getSorumlu().equals("O")) {
				switch (sm) {
				case 0:
					parameter.put("muhendisSicilNo1", rm.getSicilNo());
					parameter.put("muhendisAdiSoyadi1", rm.getAdiSoyadi());
					parameter.put("muhendisUnvani1",rm.getUnvan());
					sm++;
					break;
				case 1:
					parameter.put("muhendisSicilNo2", rm.getSicilNo());
					parameter.put("muhendisAdiSoyadi2", rm.getAdiSoyadi());
					parameter.put("muhendisUnvani2",rm.getUnvan());
					sm++;
					break;

				default:
					break;
				}
			}
		}

		parameter.put("onaylayanAdiSoyadi", kontrol.getOnaylayanAdiSoyadi());
		parameter.put("onaylayanSicilNo",
				String.valueOf(kontrol.getOnaylayanSicilNo()));

		String str = "";
		str = kontrol.getEtiket().equals("S") ? Messages._ETIKETSARI_
				.getMesaj() : str;
		str = kontrol.getEtiket().equals("Y") ? Messages._ETIKETYESIL_
				.getMesaj() : str;
		str = kontrol.getEtiket().equals("K") ? Messages._ETIKETKIRMIZI_
				.getMesaj() : str;

		Date birSonrakiKontrolTarihi = null;
		/*
		 * if (kontrol.getEtiket().equals("K")) { cal.set(Calendar.MONTH,
		 * cal.get(Calendar.MONTH) + 1);
		 * 
		 * } if (kontrol.getEtiket().equals("S")) { cal.set(Calendar.MONTH,
		 * cal.get(Calendar.MONTH) + 6);
		 * 
		 * } if (kontrol.getEtiket().equals("Y")) { cal.set(Calendar.MONTH,
		 * cal.get(Calendar.MONTH) + 12);
		 * 
		 * }
		 * 
		 * birSonrakiKontrolTarihi = cal.getTime();
		 */
		if (!kontrol.getEtiket().equals("K")) {
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 12);
			birSonrakiKontrolTarihi = cal.getTime();

		}
		parameter.put("birSonrakiKontrolTarihi", birSonrakiKontrolTarihi);
		parameter.put("sonuc", str);
		/*
		 * Elektrikli Asansör Periyodik Kontrol Rapor Sorgusu ile yazıldı Bknz.
		 * Sayfa Sonu
		 */
		String kapsam="";
		for (CihazTeknikDTO ctd : kontrol.getCihazTeknikBilgiler()) {
			ctd.setCevap(ctd.getCevap() == null ? "" : ctd.getCevap());
			switch (ctd.getDegerId()) {
			case 1:
				parameter.put("makinaMarkaTip", ctd.getCevap());
				break;
			case 2:
				parameter.put("yonetmelikAdiTarihi", ctd.getCevap());
				break;
			case 151:
				parameter.put("asansorunYeri", ctd.getCevap());
				break;
			case 152:
				parameter.put("kapasiteKisi", ctd.getCevap());
				break;
			case 153:
				parameter.put("seriNo", ctd.getCevap());
				break;
			case 158:
				parameter.put("kapasiteAgirlik", ctd.getCevap());
				break;
			case 155:
				parameter.put("sinifi", ctd.getCevap());
				break;
			case 154:
				parameter.put("imalYili", ctd.getCevap());
				break;
			case 160:
				
				if (yeniRapor){
					double hizd=0.00;
					try{
					hizd=Double.parseDouble(ctd.getCevap().trim());
					}catch(Exception e){
						hizd=0.00;
					}
					parameter.put("hiz", hizd);
				}else
				parameter.put("hiz", ctd.getCevap());
				break;
			case 161:
				parameter.put("motorGucu", ctd.getCevap());
				break;
			case 190:
				parameter.put("motorGucuBirimi", ctd.getCevap());
				break;
			case 162:
				parameter.put("motorSeriNo", ctd.getCevap());
				break;
			case 164:
				parameter.put("makinaSeriNo", ctd.getCevap());
				break;
			case 194:
				parameter.put("ceTseIsaret", ctd.getCevap());
				parameter.put("tseIsaret", ctd.getCevap());
				break;
			case 167:
				parameter.put("askiTipi", ctd.getCevap());
				break;
			case 168:
				parameter.put("halatSayisi", ctd.getCevap());
				break;
			case 169:
				parameter.put("halatCapi", ctd.getCevap());
				break;
			case 165:
				parameter.put("onKurNo", ctd.getCevap());
				break;
			case 157:
				parameter.put("durakSayisi", ctd.getCevap());
				break;
			case 207:
				parameter.put("katSayisi", ctd.getCevap());
				break;
			case 195:
				parameter.put("seyirMesafesi", ctd.getCevap());
				break;
			case 163:
				parameter.put("frenTipi", ctd.getCevap());
				break;
			case 170:
				parameter.put("kumandaTipi", ctd.getCevap());
				break;
			case 159:
				parameter.put("montajFirmasi", ctd.getCevap());
				break;
			case 201:
				parameter.put("kabinAlani", ctd.getCevap());
				break;
			case 166:
				if (!yeniRapor)
				parameter.put("kapsam", ctd.getCevap());
				else
					kapsam+=kapsam.trim().length()>0?";"+ctd.getCevap():ctd.getCevap();
				break;
			case 156:
				String[] s=ctd.getCevap().split(";");
				String kapsam_="";
				if (s!=null && s.length>0){
					for (String s1:s){
						if (s1.indexOf("(")>=0){
						String ss=s1.substring(s1.indexOf("(")+1,s1.indexOf(")"));
						kapsam_+=kapsam_.trim().length()>0?";"+ss:ss;
						}
					}
				}
				if (!yeniRapor)
				parameter.put("ozelSartlar",kapsam_);
				else{
					kapsam+=kapsam.trim().length()>0?";"+kapsam_:kapsam_;
				}
				break;
				
				
			case 191:
				parameter.put("ruhsatTescilBelgesi", ctd.getCevap());
				break;
			case 192:
				parameter.put("ruhsatTescilBelgesiNo", ctd.getCevap());
				break;
			case 193:
				parameter.put("ruhsatTescilBelgesiTarihi", ctd.getCevap()
						.trim());
				break;
			case 205:
				parameter.put("asansorCinsi", ctd.getCevap()
						.trim().equalsIgnoreCase("İNSAN")?"I":"Y");
				break;
			default:
				break;
			}

		}
		if (yeniRapor){
			parameter.put("kapsam", kapsam);
		}

		return parameter;

	}

	public HashMap<Object, Object> getHidrolikAsansorParametreler(
			KontrolDTO kontrol) {

		HashMap<Object, Object> parameter = new HashMap<Object, Object>();
		String kapsam="";
		// parameter.put("asansorTipi",kontrol.getCihaz().getTipAciklama().toUpperCase());
		parameter.put("asansorTip", kontrol.getCihaz().getTip());
		parameter.put("binaAdi", kontrol.getBina().getBinaAdi());
		parameter.put("asansorCinsi", "");
		parameter.put("asansorKimlikNo", kontrol.getCihaz().getUavtEtiket());
		String adres;
		try {
			adres =  kontrol.getBina().getMahalle().trim() + " MAHALLESİ "
					+ kontrol.getBina().getCaddeSokak().trim()
					+ " NO:" + kontrol.getBina().getBinaNo().trim()+" "+kontrol.getBina().getIlceAdi()+" "+kontrol.getBina().getIlAdi();
				
		} catch (Exception e) {
			adres = kontrol.getBina().getAcikAdres();
		}
		boolean yeniRapor=false;
		yeniRapor=kontrol.getKontrolBaslangicTarihi().compareTo(sessionBean.getBakanlikSoru().getTarih())>=0?true:false;
		if (yeniRapor){
			adres=kontrol.getBina().getBinaAdi()+" "+adres;
		}
		parameter.put("binaAdresi", adres);
		parameter.put("binaKod", String.valueOf(kontrol.getBina().getBinaId()));
		

		

		parameter.put("ilce", kontrol.getBina().getIlceAdi());
		parameter.put("il", kontrol.getBina().getIlAdi());
		for (YapiKonusuDTO y : binaBean.getYapiTipler()) {
			if (y.getId() == kontrol.getBina().getYapiTip()) {
				parameter.put("yapiKonusu", y.getTip());
				break;
			}

		}
		parameter.put("kontrolTuru",
				kontrol.getKontrolTuru().equals("E") ? "Eksiklik Kontrolü"
						: "Normal Kontrol");
		parameter.put("pafta", kontrol.getBina().getPafta());
		parameter.put("ada", kontrol.getBina().getAda());
		parameter.put("parsel", kontrol.getBina().getParsel());

		List<KontrolDTO> oncekiKontrolList = new ArrayList<KontrolDTO>();
		Calendar cal = Calendar.getInstance();
		try {

			oncekiKontrolList = new KontrolBusiness()
					.getCihazKontrollerForRapor(kontrol.getCihazId(),
							kontrol.getKontrolId());
		} catch (ReadException e) {
			oncekiKontrolList = new ArrayList<KontrolDTO>();
		}

		if (kontrol.getKontrolTuru().equals("N")) {
			parameter.put("kontrolTarihi1", kontrol.getKontrolBitisTarihi());

			cal.setTime(kontrol.getKontrolBitisTarihi());
			parameter.put("kontrolTarihi2", null);
			parameter.put("kontrolTarihi3", null);
		} else {

			boolean eksiklikYazildi = false;
			for (KontrolDTO k : oncekiKontrolList) {
				if (k.getKontrolTuru().equals("E") && !eksiklikYazildi) {

					parameter.put("kontrolTarihi2", k.getKontrolBitisTarihi());
					parameter.put("kontrolTarihi3",
							kontrol.getKontrolBitisTarihi());
					eksiklikYazildi = true;
				}
				if (k.getKontrolTuru().equals("N")) {

					parameter.put("kontrolTarihi1", k.getKontrolBitisTarihi());

					cal.setTime(k.getKontrolBitisTarihi());
					if (!eksiklikYazildi) {
						parameter.put("kontrolTarihi2",
								kontrol.getKontrolBitisTarihi());
					}
					break;
				}
			}

		}

		for (BinaKisiDTO kisi : kontrol.getBinaKisiList()) {
			String binaSorumlusu = new StringBuffer(kisi.getAdi() == null ? ""
					: kisi.getAdi().trim())
					.append(" ")
					.append(kisi.getSoyadi() == null ? "" : kisi.getSoyadi()
							.trim()).toString();
			String irtibatTelefon="";
			if (kisi.getTelefonNoGsmStr()!=null && kisi.getTelefonNoGsmStr().trim().length()>5){
				irtibatTelefon=kisi.getTelefonNoGsmStr();
			}else{
				if (kisi.getTelefonNoStr()!=null && kisi.getTelefonNoStr().trim().length()>5){
					irtibatTelefon=kisi.getTelefonNoStr();
				}
			}
			parameter.put("binaSorumlusu", binaSorumlusu);
			parameter.put("irtibatTelefon", irtibatTelefon);

			if (kisi.getSorumlulukTuru() == 1)
				break;

		}

		if (kontrol.getFirma() != null) {
			CihazFirmaDTO cihazFirma = new CihazFirmaDTO();
			String bakimciFirmaPersonel = null;

			try {
				cihazFirma = new FirmaBusiness().getCihazAnlasmaliFirma(kontrol
						.getCihaz().getCihazId(), kontrol.getFirma().getKod());
			} catch (Exception e) {
				cihazFirma = new CihazFirmaDTO();
			}
			try {
				int kontrolId = kontrol.getKontrolId();
				bakimciFirmaPersonel = new KontrolBusiness()
						.getBakimciFirmaPersonel(kontrolId);
			} catch (Exception e1) {
				e1.getMessage();
			}

			parameter.put("binaBakimSozlesmesi", cihazFirma.getId() > 0 ? "E"
					: "H");
			parameter.put(
					"binaBakimSozlesmesiTarihi",
					cihazFirma.getSozlesmeTarih() == null ? "" : df.format(cihazFirma
							.getSozlesmeTarih()));
			try{
				String sure="";
			int sozlesmeSure=	Integer.parseInt(cihazFirma.getSozlesmeSure().trim());
			int yil = sozlesmeSure / 365;       
			sure=yil>0?yil+" YIL":"";
			int ay = (sozlesmeSure - (yil*365)) / 30; 
			sure=ay>0?sure+" "+ay+" AY":sure;
			int gun = (sozlesmeSure - ((yil*365) + (ay*30))); 
			sure=gun>0?sure+" "+gun+" GÜN":sure;
				parameter.put(
						"binaBakimSozlesmeSure",sure);
			}catch(Exception e){
				parameter.put(
						"binaBakimSozlesmeSure","");
			}
			parameter.put("bakimciFirma", kontrol.getFirma().getUnvan());
			parameter.put("bakimciFirmaAdres", kontrol.getFirma().getAdres());

			parameter.put("bakimciFirmaIlce", kontrol.getFirma().getIlceAdi());
			parameter.put("bakimciFirmaIl", kontrol.getFirma().getIlAdi());
			kontrol.getFirma().setUygunlukBelgesi(
					kontrol.getFirma().getUygunlukBelgesi() == null ? ""
							: kontrol.getFirma().getUygunlukBelgesi());
			parameter.put("ceUygunlukBelgesi", kontrol.getFirma()
					.getUygunlukBelgesi());
			parameter.put(
					"ceBelgeTarih",
					kontrol.getFirma().getGecerlilikSuresi() == null ? "" : df
							.format(kontrol.getFirma().getGecerlilikSuresi()));
			kontrol.getFirma().setMonteEden(
					kontrol.getFirma().getMonteEden() == null ? "" : kontrol
							.getFirma().getMonteEden());
			parameter.put("monteEden", kontrol.getFirma().getMonteEden());
			kontrol.getFirma().setYetkiliServis(
					kontrol.getFirma().getYetkiliServis() == null ? ""
							: kontrol.getFirma().getYetkiliServis());
			parameter.put("yetkiliServis", kontrol.getFirma()
					.getYetkiliServis());
			kontrol.getFirma().setServisSozlesme(
					kontrol.getFirma().getServisSozlesme() == null ? ""
							: kontrol.getFirma().getServisSozlesme());
			parameter.put("yetkiliServisSozlesme", kontrol.getFirma()
					.getServisSozlesme());
			parameter.put(
					"yetkiliServisSozlesmeTarih",
					kontrol.getFirma().getSozlesmeTarihi() == null ? "" : df
							.format(kontrol.getFirma().getSozlesmeTarihi()));
			kontrol.getFirma().setTseBelgeNo(
					kontrol.getFirma().getTseBelgeNo() == null ? "" : kontrol
							.getFirma().getTseBelgeNo().trim());
			parameter.put("tseHybBelgesi", kontrol.getFirma().getTseBelgesi());
			parameter.put("bakimciFirmaTelefon", kontrol.getFirma()
					.getTelefonNoStr());
			parameter.put("bakimciFirmaFaks", kontrol.getFirma()
					.getFaksStr());
			parameter.put("bakimciFirmaEPosta", kontrol.getFirma().getEposta());
			parameter
					.put("tseHybBelgesiNo", kontrol.getFirma().getTseBelgeNo());
			parameter.put("firmaPersonelAdSoyad", bakimciFirmaPersonel);
		}
		int sm = 0;
		for (RandevuMuhendisDTO rm : kontrol.getKontrolMuhendisleri()) {
			if (rm.getSorumlu().equals("E")) {
				parameter.put("muhendisSicilNo", rm.getSicilNo());
				parameter.put("muhendisAdiSoyadi", rm.getAdiSoyadi());
				parameter.put("muhendisUnvani",rm.getUnvan());
			}
			if (rm.getSorumlu().equals("H")) {
				switch (sm) {
				case 0:
					parameter.put("muhendisSicilNo1", rm.getSicilNo());
					parameter.put("muhendisAdiSoyadi1", rm.getAdiSoyadi());
					parameter.put("muhendisUnvani1",rm.getUnvan());
					sm++;
					break;
				case 1:
					parameter.put("muhendisSicilNo2", rm.getSicilNo());
					parameter.put("muhendisAdiSoyadi2", rm.getAdiSoyadi());
					parameter.put("muhendisUnvani2",rm.getUnvan());
					sm++;
					break;

				default:
					break;
				}
			}
		}

		parameter.put("onaylayanAdiSoyadi", kontrol.getOnaylayanAdiSoyadi());
		parameter.put("onaylayanSicilNo",
				String.valueOf(kontrol.getOnaylayanSicilNo()));

		String str = "";
		str = kontrol.getEtiket().equals("S") ? Messages._ETIKETSARI_
				.getMesaj() : str;
		str = kontrol.getEtiket().equals("Y") ? Messages._ETIKETYESIL_
				.getMesaj() : str;
		str = kontrol.getEtiket().equals("K") ? Messages._ETIKETKIRMIZI_
				.getMesaj() : str;

		Date birSonrakiKontrolTarihi = null;
		/*
		 * if (kontrol.getEtiket().equals("K")) { cal.set(Calendar.MONTH,
		 * cal.get(Calendar.MONTH) + 1);
		 * 
		 * } if (kontrol.getEtiket().equals("S")) { cal.set(Calendar.MONTH,
		 * cal.get(Calendar.MONTH) + 6);
		 * 
		 * } if (kontrol.getEtiket().equals("Y")) { cal.set(Calendar.MONTH,
		 * cal.get(Calendar.MONTH) + 12);
		 * 
		 * } birSonrakiKontrolTarihi = cal.getTime();
		 */
		if (!kontrol.getEtiket().equals("K")) {
			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 12);
			birSonrakiKontrolTarihi = cal.getTime();

		}
		parameter.put("birSonrakiKontrolTarihi", birSonrakiKontrolTarihi);
		parameter.put("sonuc", str);

		/*
		 * Hidrolik Asansör Periyodik Kontrol Rapor Sorgusu ile yazıldı Bknz.
		 * Sayfa Sonu
		 */
		parameter.put("seriNo", "-");
		
		for (CihazTeknikDTO ctd : kontrol.getCihazTeknikBilgiler()) {
			ctd.setCevap(ctd.getCevap() == null ? "" : ctd.getCevap());
			switch (ctd.getDegerId()) {
			case 171:
				parameter.put("asansorunYeri", ctd.getCevap());
				break;
			case 173:
				parameter.put("montajFirmasi", ctd.getCevap());
				break;
			case 172:
				parameter.put("kapasiteKisi", ctd.getCevap());
				break;
			case 174:
				parameter.put("kapasiteAgirlik", ctd.getCevap());
				break;
			case 175:
				parameter.put("sinifi", ctd.getCevap());
				break;
			case 176:
				parameter.put("imalYili", ctd.getCevap());
				break;
			case 177:
				parameter.put("seriNo", ctd.getCevap());
				break;
			
			case 179:
				
				if (yeniRapor){
					double hizd=0.00;
					try{
					hizd=Double.parseDouble(ctd.getCevap().trim());
					}catch(Exception e){
						hizd=0.00;
					}
					parameter.put("hiz", hizd);
				}else
				parameter.put("hiz", ctd.getCevap());
				break;
				
			case 180:
				parameter.put("frenTipi", ctd.getCevap());
				break;
			case 181:
				parameter.put("askiTipi", ctd.getCevap());
				break;
			case 182:
				parameter.put("durakSayisi", ctd.getCevap());
				break;
			case 208:
				parameter.put("katSayisi", ctd.getCevap());
				break;
			case 196:
				parameter.put("seyirMesafesi", ctd.getCevap());
				break;
			case 202:
				parameter.put("kabinAlani", ctd.getCevap());
				break;
			case 183:
				parameter.put("kumandaTipi", ctd.getCevap());
				break;

			case 4:
				parameter.put("yonetmelikAdiTarihi", ctd.getCevap());
				break;

			case 184:
				parameter.put("onKurNo", ctd.getCevap());
				break;

			case 197:
				parameter.put("ceTseIsaret", ctd.getCevap());
				parameter.put("tseIsaret", ctd.getCevap());
				break;
			case 185:
				parameter.put("halatSayisi", ctd.getCevap());
				break;
			case 186:
				parameter.put("halatCapi", ctd.getCevap());
				break;
			case 187:
				parameter.put("pompaMotorGucu", ctd.getCevap());
				break;
			case 188:
				parameter.put("pompaMotorSeriNo", ctd.getCevap());
				break;

			case 3:
				parameter.put("hidrolikGrubuMarkaModel", ctd.getCevap());
				break;
			
				
			case 178:
				if (!yeniRapor)
				parameter.put("kapsam", ctd.getCevap());
				else
					kapsam+=kapsam.trim().length()>0?";"+ctd.getCevap():ctd.getCevap();
				break;
			
			case 189:
				String[] s=ctd.getCevap().split(";");
				String kapsam_="";
				if (s!=null && s.length>0){
					for (String s1:s){
						if (s1.indexOf("(")>=0){
						String ss=s1.substring(s1.indexOf("(")+1,s1.indexOf(")"));
						kapsam_+=kapsam_.trim().length()>0?";"+ss:ss;
						}
					}
				}
				if (!yeniRapor)
				parameter.put("ozelSartlar",kapsam_);
				else{
					kapsam+=kapsam.trim().length()>0?";"+kapsam_:kapsam_;
				}
				break;	
				
			case 200:
				parameter.put("ruhsatTescilBelgesi", ctd.getCevap());
				break;

			case 198:
				parameter.put("ruhsatTescilBelgesiTarihi", ctd.getCevap()
						.trim());
				break;

			case 199:
				parameter.put("ruhsatTescilBelgesiNo", ctd.getCevap());
				break;
			case 206:
				parameter.put("asansorCinsi", ctd.getCevap()
						.trim().equalsIgnoreCase("İNSAN")?"I":"Y");
				break;
			default:
				break;
			}

		}

if (yeniRapor){
			parameter.put("kapsam", kapsam);
		}

		return parameter;

	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	

	public BinaBean getBinaBean() {
		return binaBean;
	}

	public void setBinaBean(BinaBean binaBean) {
		this.binaBean = binaBean;
	}

	public int getOnayliRaporIptalKod() {
		return onayliRaporIptalKod;
	}

	public void setOnayliRaporIptalKod(int onayliRaporIptalKod) {
		this.onayliRaporIptalKod = onayliRaporIptalKod;
	}
	private BasvuruAsansorDTO  odemesiIptalEdilecekCihaz=new BasvuruAsansorDTO();
	public void onayliRaporIptalKodListener(AjaxBehaviorEvent event){
		randevuAsansorListe=new ArrayList<BasvuruAsansorDTO>();
		if (selectedOnayliRapor!=null){
			try {
				randevuAsansorListe=	new BasvuruBusiness().getBasvuruAsansor(selectedOnayliRapor);
				for (BasvuruAsansorDTO b:randevuAsansorListe){
					if (b.getCihazId()==selectedOnayliRapor.getCihazId()){
						odemesiIptalEdilecekCihaz=b;
						if (b.getKontrolTutari()>0 && onayliRaporIptalKod>0){
							b.setKontrolIptal(true);
							break;
						}
					}
				}
			} catch (ReadException e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage()));
			}
		}
		
	}
	
	private List<BasvuruAsansorDTO> randevuAsansorListe=new ArrayList<BasvuruAsansorDTO>();

	public List<BasvuruAsansorDTO> getRandevuAsansorListe() {
		return randevuAsansorListe;
	}

	public void setRandevuAsansorListe(List<BasvuruAsansorDTO> randevuAsansorListe) {
		this.randevuAsansorListe = randevuAsansorListe;
	}

	public List<EskiRaporDTO> getSlctRaporlarTeslim() {
		return slctRaporlarTeslim;
	}

	public void setSlctRaporlarTeslim(List<EskiRaporDTO> slctRaporlarTeslim) {
		this.slctRaporlarTeslim = slctRaporlarTeslim;
	}

	public List<EskiRaporDTO> getFilteredRaporlarTeslim() {
		return filteredRaporlarTeslim;
	}

	public void setFilteredRaporlarTeslim(List<EskiRaporDTO> filteredRaporlarTeslim) {
		this.filteredRaporlarTeslim = filteredRaporlarTeslim;
	}
	

	
	
	
	
	
}
