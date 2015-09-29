package tr.org.mmo.asansor.managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRException;

import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.component.fieldset.Fieldset;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.chart.PieChartModel;

import tr.org.mmo.asansor.beans_.AnaSoru;
import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.beans_.Istatistik;
import tr.org.mmo.asansor.beans_.RaporSource;
import tr.org.mmo.asansor.business.BasvuruBusiness;
import tr.org.mmo.asansor.business.BinaBusiness;
import tr.org.mmo.asansor.business.FirmaBusiness;
import tr.org.mmo.asansor.business.KontrolBusiness;
import tr.org.mmo.asansor.business.RandevuBusiness;
import tr.org.mmo.asansor.business.RaporBusiness;
import tr.org.mmo.asansor.business.TemsilcilikBusiness;
import tr.org.mmo.asansor.dto.AsansorKapsamDTO;
import tr.org.mmo.asansor.dto.BelediyeDTO;
import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.dto.BinaKisiDTO;
import tr.org.mmo.asansor.dto.CihazFirmaDTO;
import tr.org.mmo.asansor.dto.CihazTeknikDTO;
import tr.org.mmo.asansor.dto.KontrolDTO;
import tr.org.mmo.asansor.dto.KontrolFirmaKatilimDTO;
import tr.org.mmo.asansor.dto.MuayeneKurulusDTO;
import tr.org.mmo.asansor.dto.RandevuMuhendisDTO;
import tr.org.mmo.asansor.dto.RaporDTO;
import tr.org.mmo.asansor.dto.SoruDTO;
import tr.org.mmo.asansor.dto.SoruOnTanimliDTO;
import tr.org.mmo.asansor.dto.YapiKonusuDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.models.IstatistikDataBasvuruModel;
import tr.org.mmo.asansor.models.IstatistikDataBinaModel;
import tr.org.mmo.asansor.models.IstatistikDataModel;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.StringSort;

@ManagedBean
@ViewScoped
public class IstatistikBean implements Serializable{

	/**
	 * 
	 */
	private SoruTableBean soruTableBean=new SoruTableBean();
	private List<Istatistik> selected;
	private List<Istatistik> slctRaporlar;
	private static final long serialVersionUID = -1521550544354979558L;

	private PieChartModel pieModel=new PieChartModel();
	private PieChartModel pieModel1=new PieChartModel();
	private PieChartModel pieModel2=new PieChartModel();
	private PieChartModel pieModel3=new PieChartModel();
	private String accordionHeader;
	private List<Istatistik> istatistikRaporList=new ArrayList<Istatistik>();
	private IstatistikDataModel istatistikRaporModel=new IstatistikDataModel(istatistikRaporList);
	private IstatistikDataBasvuruModel istatistikBasvuruModel=new IstatistikDataBasvuruModel(istatistikRaporList);
	private IstatistikDataBasvuruModel istatistikKontrolModel=new IstatistikDataBasvuruModel(istatistikRaporList);
	private IstatistikDataBinaModel istatistikBinaModel=new IstatistikDataBinaModel(istatistikRaporList);
	private String istatistikListeHeader="";
	private Date tarih;
	DateFormat df=new SimpleDateFormat("dd.MM.yyyy");
	private String raporTarih;
	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;
	
	@ManagedProperty("#{binaBean}")
	private BinaBean binaBean;
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;
	
	public Date getTarih() {
		return tarih;
	}

	public void setTarih(Date tarih) {
		this.tarih = tarih;
	}
	@PostConstruct
	public void init(){
		Date raporTarih=null;
		raporTarih=(Date)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("raporTarih");
		if (raporTarih!=null){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("raporTarih");
		
		tarih=raporTarih;
			istatistikRaporList=new ArrayList<Istatistik>();
			istatistikRaporModel=new IstatistikDataModel(istatistikRaporList);
			istatistikBinaModel=new IstatistikDataBinaModel(istatistikRaporList);
			selectMenu="Y";
			accordionHeader="Yazdırılmamış Raporlar";
			AccordionPanel p=(AccordionPanel)FacesContext.getCurrentInstance().getViewRoot().findComponent(":formrapor:accordion");
			UIComponent panel=(UIComponent)FacesContext.getCurrentInstance().getViewRoot().findComponent(":formrapor:dummy");
			try{
				p.setRendered(true);
				panel.setRendered(false);
			}catch(Exception e){
				
			}
			
			try {
				
				istatistikRaporList=new ArrayList<Istatistik>();
				
			
			  getRapor();
				
				Fieldset f=(Fieldset)FacesContext.getCurrentInstance().getViewRoot().findComponent(":formrapor:fieldistatistik");
				
				if(istatistikRaporList.size()>0){
					if (f!=null){
						f.setRendered(true);
						
					}
				istatistikRaporModel=new IstatistikDataModel(istatistikRaporList);
				}
				else{
					if (f!=null){
						f.setRendered(false);
					}
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, Messages._SQL_506_.getMesaj(),""));
				}
				if (panel!=null){
					panel.setRendered(false);
					}
			} catch (CRUDException e) {
			 
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(e.getMessage()));
					
				
			}
			if (panel!=null){
				panel.setRendered(false);
				}
				
		
		}
		
	}
	private String selectMenu;
	public void menuSelectListener(ActionEvent event){
		selectedBina=new Istatistik();
		selectedCihaz=new Istatistik();
		tarih=new Date();
		Fieldset f=(Fieldset)FacesContext.getCurrentInstance().getViewRoot().findComponent(":formrapor:fieldistatistik");
		if (f!=null){
			f.setRendered(false);
		}
		istatistikRaporList=new ArrayList<Istatistik>();
		istatistikRaporModel=new IstatistikDataModel(istatistikRaporList);
		istatistikBinaModel=new IstatistikDataBinaModel(istatistikRaporList);
		
		
	String parameter=(String)	FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("parameter");
	if (parameter!=null){
		selectMenu=parameter;
		if (selectMenu.equals("O")){
			accordionHeader="Onaylanmış Raporlar";
		}
		if (selectMenu.equals("Y")){
			accordionHeader="Yazdırılmamış Raporlar";
		}
		if (selectMenu.equals("D")){
			accordionHeader="Onay Bekleyen Raporlar";
		}
		if (selectMenu.equals("C")){
			accordionHeader="Çalışılan Binalar";
		}
		
		AccordionPanel p=(AccordionPanel)FacesContext.getCurrentInstance().getViewRoot().findComponent(":formrapor:accordion");
		UIComponent panel=(UIComponent)FacesContext.getCurrentInstance().getViewRoot().findComponent(":formrapor:dummy");
		try{
			p.setRendered(true);
			panel.setRendered(false);
		}catch(Exception e){
			
		}
	}
	//UIMenuItem item = (UIMenuItem) event.getComponent();
		
	}
	public void getIstatistikRapor(SelectEvent event){
		selectedBina=new Istatistik();
		selectedCihaz=new Istatistik();
		UIComponent panel=(UIComponent)FacesContext.getCurrentInstance().getViewRoot().findComponent(":formrapor:dummy");
		if (panel!=null){
		panel.setRendered(false);
		}
		try {
			
			istatistikRaporList=new ArrayList<Istatistik>();
			
			if (selectMenu.equals("C"))
			getCalisilanBinalar();
			else getRapor();
			
			Fieldset f=(Fieldset)FacesContext.getCurrentInstance().getViewRoot().findComponent(":formrapor:fieldistatistik");
			
			if(istatistikRaporList.size()>0){
				if (f!=null){
					f.setRendered(true);
					
				}
			istatistikRaporModel=new IstatistikDataModel(istatistikRaporList);
			}
			else{
				if (f!=null){
					f.setRendered(false);
				}
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, Messages._SQL_506_.getMesaj(),""));
			}
			if (panel!=null){
				panel.setRendered(false);
				}
		} catch (CRUDException e) {
		 
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
				
			
		}
		if (panel!=null){
			panel.setRendered(false);
			}
			
		}
	public String getIstatistikListeHeader() {
		return istatistikListeHeader;
	}

	public void setIstatistikListeHeader(String istatistikListeHeader) {
		this.istatistikListeHeader = istatistikListeHeader;
	}

	public List<Istatistik> getIstatistikRaporList() {
		return istatistikRaporList;
	}

	public void setIstatistikRaporList(List<Istatistik> istatistikRaporList) {
		this.istatistikRaporList = istatistikRaporList;
	}

	public IstatistikDataModel getIstatistikRaporModel() {
		return istatistikRaporModel;
	}
	public void setIstatistikRaporModel(IstatistikDataModel istatistikRaporModel) {
		this.istatistikRaporModel = istatistikRaporModel;
	}

	public PieChartModel getPieModel() {
		
		return pieModel;
	}

	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}

	public String getSelectMenu() {
		return selectMenu;
	}

	public void setSelectMenu(String selectMenu) {
		this.selectMenu = selectMenu;
	}

	public String getRaporTarih() {
		return raporTarih;
	}

	public void setRaporTarih(String raporTarih) {
		this.raporTarih = raporTarih;
	}
	
	public void raporAc(Istatistik rapor){
		try{
		if (selectMenu.equals("Y")){
			prepareRapor(rapor);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("raporTarih",tarih);
		}else{
			listener(rapor);
		}
		}catch(CRUDException e){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(),e.getMessage()));
		}
	}
	private void listener(Istatistik rapor) throws CRUDException{
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		request.getSession().setAttribute("dosya", rapor.getDosya());
		request.getSession().setAttribute("dosyaAdi", rapor.getDosyaAdi());

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
			throw new CRUDException(e.getMessage(),null);
		}catch (Exception e) {
			throw new CRUDException(e.getMessage(),null);
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

	public void openSelected(ActionEvent event) {

		FacesContext context = FacesContext.getCurrentInstance();

		if (selected != null && selected.size() > 0) {

			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();

			request.getSession().setAttribute("raporlar", selected);

			try {
				context.getExternalContext().redirect(
						context.getExternalContext().getRequestContextPath()
								+ "/TumRaporlarIstatistikServlet");
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

	public List<Istatistik> getSelected() {
		return selected;
	}

	public void setSelected(List<Istatistik> selected) {
		this.selected = selected;
	}

	public List<Istatistik> getSlctRaporlar() {
		return slctRaporlar;
	}

	public void setSlctRaporlar(List<Istatistik> slctRaporlar) {
		this.slctRaporlar = slctRaporlar;
	}

	private void prepareRapor(Istatistik kontrol) throws CRUDException {
		
		try {
			
			
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
				soruTableBean.getAnaSoru(kontrol.getCihaz().getTip(), kapsamId, true,tarih);
				soruTableBean.cihazTestSonucGetir(kontrol.getKontrolId());
				kontrol.setTestSorular(soruTableBean.getSoruListSmall());
				for (BinaDTO b:loginBean.getTumBinalar()){
					if (b.getBinaId()==kontrol.getBinaId()){
						kontrol.setBina(b);
						break;
					}
				}
				if (kontrol.getKontrolBaslangicTarihi().compareTo(sessionBean.getBakanlikSoru().getTarih())>=0){
					yeniRapor(kontrol);
				}else{
				if (kontrol.getCihazTipi() == 66) {
					elektrikliAsansorRapor(kontrol);
				} else {
					pdfRapor(kontrol);
				}
				}
			
		} catch (Exception e) {
			throw new CRUDException(e.getMessage(),null);
		}
		
		
		
	}
	private void yeniRapor(Istatistik kontrol) {

		try {
			RaporBean rapor = new RaporBean();
			
				rapor.setRaporAdi("asansorkontrolraporu.jasper");
			
			String path = ((ServletContext) FacesContext.getCurrentInstance()
					.getExternalContext().getContext())
					.getRealPath("/resources/raporlar/");

			path.replace("\\", "/");
			path += "/";
			// System.out.println(path);
			rapor.setRaporPath(path);

			rapor.setFileName(kontrol.getBina().getBinaId() + "-"
					+ kontrol.getCihazId() + "-" + df.format(new Date())
					+ (kontrol.isRevizyonRapor() ? "(REV)" : "") + ".pdf");

			HashMap<Object, Object> parameter = new HashMap<Object, Object>();

			parameter = kontrol.getCihaz().getTip() == 66 ? getElektrikliAsansorParametreler(kontrol)
					: getHidrolikAsansorParametreler(kontrol);
			KontrolDTO knt=new KontrolDTO();
			try{
				knt.setKontrolFirmaKatilimDTO(new KontrolBusiness().getFirmaKatilim(kontrol.getKontrolId()));
			}catch(CRUDException e){
				knt.setKontrolFirmaKatilimDTO(new KontrolFirmaKatilimDTO());
			}
			parameter.put("firmaPersonelAdSoyad", knt.getKontrolFirmaKatilimDTO().getFirmaGorevli1());
			parameter.put("firmaPersonelGorevi", knt.getKontrolFirmaKatilimDTO().getFirmaGorevli1Gorev());
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
			parameter.put("logo",new KontrolBean().setAsansorRaporLogo());

			

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
										aciklamaList.add(s.getSorun() + "-" +sod.getAciklama());
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

			rapor.setParameter(parameter);
			rapor.setListt(list);

			RaporDTO raporDTO = new RaporDTO();
			raporDTO.setDosyaAdi(rapor.getFileName());

			raporDTO.setKontrolId(kontrol.getKontrolId());
			raporDTO.setRaporTarihi(new Date());
			
			
			parameter.put("raporNo", String.valueOf(0));
			parameter.put("raporTarihi", new Date());
			rapor.pdf(0, kontrol.getCihaz().getTip());
			
			
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
	private void elektrikliAsansorRapor(Istatistik kontrol) {

		try {
			RaporBean rapor = new RaporBean();
		
				rapor.setRaporAdi("periyodikKontrolRapor(ElektrikliAsansor).jasper");
		
			String path = ((ServletContext) FacesContext.getCurrentInstance()
					.getExternalContext().getContext())
					.getRealPath("/resources/raporlar/");

			path.replace("\\", "/");
			path += "/";
			// System.out.println(path);
			rapor.setRaporPath(path);

			rapor.setFileName(kontrol.getBinaAdi() + "-"
					+ kontrol.getCihazId() 
					+ (kontrol.isRevizyonRapor() ? "(REV)" : "") + ".pdf");

			HashMap<Object, Object> parameter = new HashMap<Object, Object>();

			parameter = getElektrikliAsansorParametreler(kontrol);
					

			parameter.put("pAciklamalar", kontrol.getAciklamalar() == null ? ""
					: kontrol.getAciklamalar().replace('İ', 'i').toLowerCase());

			parameter.put("logo",new KontrolBean().setAsansorRaporLogo());

			List<RaporSource> list = new ArrayList<RaporSource>();

			RaporSource r = new RaporSource();
			r.setSira("HEADER");
			list.add(r);

			List<String> aciklamalarList = new ArrayList<String>();

			for (AnaSoru as : kontrol.getTestSorular()) {
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

				list.add(r);
				// }

			}
			r = new RaporSource();
			r.setSira("RESULT");
			list.add(r);

			rapor.setParameter(parameter);
			rapor.setListt(list);

			RaporDTO raporDTO = new RaporDTO();
			raporDTO.setDosyaAdi(rapor.getFileName());

			raporDTO.setKontrolId(kontrol.getKontrolId());
			raporDTO.setRaporTarihi(new Date());
			
			
			parameter.put("raporNo", String.valueOf(0));
			parameter.put("raporTarihi", new Date());
			rapor.pdf(0, kontrol.getCihaz().getTip());

			
		} catch (CRUDException e) {
			System.out.println(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		} catch (JRException e) {
			System.out.println(e.getMessage());
			FacesContext.getCurrentInstance().responseComplete();
			if (e.getMessage().contains(
					"file because it is being used by another process")) {
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
	
	public String pdfRapor(Istatistik kontrol) {

		try {
			RaporBean rapor = new RaporBean();
		
				rapor.setRaporAdi("periyodikKontrolRapor(HidrolikAsansor).jasper");
		
			String path = ((ServletContext) FacesContext.getCurrentInstance()
					.getExternalContext().getContext())
					.getRealPath("/resources/raporlar/");

			path.replace("\\", "/");
			path += "/";
			// System.out.println(path);
			rapor.setRaporPath(path);

			rapor.setFileName(kontrol.getBina().getBinaId() + "-"
					+ kontrol.getCihazId() 
					+ (kontrol.isRevizyonRapor() ? "(REV)" : "") + ".pdf");

			HashMap<Object, Object> parameter = new HashMap<Object, Object>();

			parameter = getHidrolikAsansorParametreler(kontrol);

			parameter.put("pAciklamalar", kontrol.getAciklamalar() == null ? ""
					: kontrol.getAciklamalar().replace('İ', 'i').toLowerCase());

			parameter.put("logo", new KontrolBean().setAsansorRaporLogo());

			List<RaporSource> list = new ArrayList<RaporSource>();

			RaporSource r = new RaporSource();
			r.setSira("HEADER");
			list.add(r);

			List<String> aciklamalarList = new ArrayList<String>();

			for (AnaSoru as : kontrol.getTestSorular()) {
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

				list.add(r);
				// }

			}
			r = new RaporSource();
			r.setSira("RESULT");
			list.add(r);

			rapor.setParameter(parameter);
			rapor.setListt(list);

			RaporDTO raporDTO = new RaporDTO();
			raporDTO.setDosyaAdi(rapor.getFileName());

			raporDTO.setKontrolId(kontrol.getKontrolId());
			raporDTO.setRaporTarihi(new Date());
			
			
			parameter.put("raporNo", String.valueOf(0));
			parameter.put("raporTarihi", new Date());
			rapor.pdf(0, kontrol.getCihaz().getTip());

			
		} catch (CRUDException e) {
			System.out.println(e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		} catch (JRException e) {
			System.out.println(e.getMessage());
			FacesContext.getCurrentInstance().responseComplete();
			if (e.getMessage().contains(
					"file because it is being used by another process")) {
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
	
	private HashMap<Object, Object> getElektrikliAsansorParametreler(
			Istatistik kontrol) {
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
		for (BelediyeDTO b : loginBean.getBelediyeList()) {
			if (b.getKod() == kontrol.getBina().getBelediye()) {
				parameter.put("belediye", b.getAdi());
				break;
			}
		}

		

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

		parameter.put("onaylayanAdiSoyadi", kontrol.getOnaylayan());
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

	private HashMap<Object, Object> getHidrolikAsansorParametreler(
			Istatistik kontrol) {

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
		for (BelediyeDTO b : loginBean.getBelediyeList()) {
			if (b.getKod() == kontrol.getBina().getBelediye()) {
				parameter.put("belediye", b.getAdi());
				break;
			}
		}

		

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

		parameter.put("onaylayanAdiSoyadi", kontrol.getOnaylayan());
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
		String kapsam="";
		
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

	public IstatistikDataBinaModel getIstatistikBinaModel() {
		return istatistikBinaModel;
	}

	public void setIstatistikBinaModel(IstatistikDataBinaModel istatistikBinaModel) {
		this.istatistikBinaModel = istatistikBinaModel;
	}

	public PieChartModel getPieModel1() {
		return pieModel1;
	}

	public void setPieModel1(PieChartModel pieModel1) {
		this.pieModel1 = pieModel1;
	}

	public PieChartModel getPieModel2() {
		return pieModel2;
	}

	public void setPieModel2(PieChartModel pieModel2) {
		this.pieModel2 = pieModel2;
	}

	public PieChartModel getPieModel3() {
		return pieModel3;
	}

	public void setPieModel3(PieChartModel pieModel3) {
		this.pieModel3 = pieModel3;
	}

	public String getAccordionHeader() {
		return accordionHeader;
	}

	public void setAccordionHeader(String accordionHeader) {
		this.accordionHeader = accordionHeader;
	}
	
	private void getCalisilanBinalar() throws ReadException{
		List<Istatistik> list=new ArrayList<Istatistik>();
		list=new BinaBusiness().sonCalisilanBinalar(tarih);
		pieModel1=new PieChartModel();
		pieModel2=new PieChartModel();
		pieModel3=new PieChartModel();
		int yapilanBasvuru=0;
		int verilenRandevu=0;
		int kontrolAdet=0; 
		int asansorAdet=0;
		int normalKontrolAdet=0;
		int eksiklikKontroluAdet=0;
		HashMap<Integer,Istatistik> map=new HashMap<Integer, Istatistik>();
		for (Istatistik r:list){
			yapilanBasvuru++;
			verilenRandevu+=r.getRandevuAdet();
			asansorAdet+=r.getAsansorAdet();
			eksiklikKontroluAdet+=r.getEksiklikKontrolu();
			normalKontrolAdet+=r.getNormalKontrol();
			map.put(r.getBinaId(), r);
		}
		list = new RaporBusiness().getRaporlar(tarih);
		kontrolAdet=list.size();
		Set<Integer> key=map.keySet();
		Iterator<Integer> iter=key.iterator();
		while (iter.hasNext()){
			int k=iter.next();
			istatistikRaporList.add(map.get(k));
		}
		istatistikBinaModel=new IstatistikDataBinaModel(istatistikRaporList);
		istatistikListeHeader=new StringBuffer(df.format(tarih)).append(" TARİHİ İTİBARİYLE ÇALIŞILAN BİNALAR").toString();
		pieModel1.set("Başvuru",yapilanBasvuru);
		pieModel1.set("Randevu",verilenRandevu);
		
		pieModel2.set("Başvurulan Asansör",asansorAdet);
		pieModel2.set("Kontrol",kontrolAdet);
		
		pieModel3.set("Normal Kontrol",normalKontrolAdet);
		pieModel3.set("Eksiklik Kontrolü",eksiklikKontroluAdet);
		
	
	}
	
	private void getRapor() throws ReadException{
		List<Istatistik> list=new ArrayList<Istatistik>();
		pieModel=new PieChartModel();
		int onayRapor =0;
		int onaysizRapor=0;
		int rapor=0;
		int yazdirilmamisRapor=0;
		
		raporTarih=new StringBuffer(df.format(tarih)).append(" - ").append(df.format(Calendar.getInstance().getTime())).toString();
		istatistikListeHeader=new StringBuffer(df.format(tarih)).append(" TARİHİ İTİBARİYLE ").
				append(selectMenu.equals("O")?"ONAYLANAN":(selectMenu.equals("Y")?"YAZDIRILMAMIŞ":"ONAY BEKLEYEN")).append(" RAPORLAR").toString();
		
		if (selectMenu.equals("O") || selectMenu.equals("D")  ){
		list = new RaporBusiness().getRaporlar(tarih);
		for (Istatistik r:list){
			if (r.getOnayDurum().equals("O")){
				if (selectMenu.equals("O"))
				istatistikRaporList.add(r);
				onayRapor++;
			}else{
				if (!selectMenu.equals("O"))
					istatistikRaporList.add(r);
				onaysizRapor++;
			}
			rapor++;
		}
		pieModel.set("Onay Bekleyen Raporlar",onaysizRapor);
		pieModel.set("Tüm Raporlar",rapor);
		pieModel.set("Onaylanan Raporlar",onayRapor);
		}else{
			list = new RaporBusiness().getYazdirilmamisRaporlar(tarih);
			for (Istatistik r:list){
				if (r.getOnayDurum()==null ||  r.getOnayDurum().trim().equals("")){
					istatistikRaporList.add(r);
					yazdirilmamisRapor++;
				}
				if (r.getOnayDurum()!=null && r.getOnayDurum().equals("O")){
					onayRapor++;
				}
				if (r.getOnayDurum()!=null && !r.getOnayDurum().equals("O") && !r.getOnayDurum().trim().equals("") ){
					onaysizRapor++;
				}
				
				rapor++;
			}
			pieModel.set("Onay Bekleyen Raporlar",onaysizRapor);
			pieModel.set("Tüm Raporlar",rapor);
			pieModel.set("Onaylanan Raporlar",onayRapor);
			pieModel.set("Yazdırılmamış Raporlar",yazdirilmamisRapor);
		}
	
		
	}
	
	private Istatistik selectedBina=new Istatistik();
	private Istatistik selectedCihaz=new Istatistik();
	
	public void rowSelect(Istatistik basvuru){
		selectedBina=new Istatistik();
		selectedCihaz=new Istatistik();
		
		if (basvuru!=null && basvuru.getBinaId()>0){
			try {
				selectedBina=(Istatistik)basvuru.clone();
			} catch (CloneNotSupportedException e1) {
				selectedBina=new Istatistik();
			}
			List<Istatistik> list=new ArrayList<Istatistik>();
		try {
			list=	new BasvuruBusiness().getBasvurularByBinaIdAndTarih(selectedBina.getBinaId(),tarih);
			istatistikBasvuruModel=new IstatistikDataBasvuruModel(list);
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(),""));
		}
		}
	}
	
	public void onRowSelect(Istatistik cihaz){
		
		raporAcRender=false;
		
		if (cihaz!=null && cihaz.getBasvuruId()>0){
			try {
				selectedCihaz=(Istatistik)cihaz.clone();
			} catch (CloneNotSupportedException e1) {
				selectedCihaz=new Istatistik();
			}
			List<Istatistik> list=new ArrayList<Istatistik>();
		try {
			list=	new RandevuBusiness().getRandevuKontrol(cihaz.getCihazId(), cihaz.getBasvuruId());
			for (Istatistik i:list){
				String durum="";
				if (i.getOnayDurum()==null || i.getOnayDurum()==""){
					durum="Rapor yazdırılmadı";
				}if (i.getKontrolId()<=0){
					
					durum="Kontrol Yapılmadı";
				}
				if (i.getOnayDurum()!=null && i.getOnayDurum().equals("O")){
					durum="Rapor Onaylandı";
					raporAcRender=true;
				}
				if (i.getOnayDurum()!=null && !i.getOnayDurum().equals("O")){
					durum="Rapor Onay Bekliyor";
					raporAcRender=true;
				}
				StringBuffer sb=new StringBuffer(durum);
				if (i.getOdemeTutari()>0) {
					sb.append(" Ödemesi alındı");
				}
				i.setAciklamalar(sb.toString());
				if (i.getDosya()==null || i.getDosyaAdi().contains("ESKİ")){
					raporAcRender=false;
				}
			}
			istatistikKontrolModel=new IstatistikDataBasvuruModel(list);
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(),""));
		}
		}
	}
	private boolean raporAcRender=false;
	public IstatistikDataBasvuruModel getIstatistikBasvuruModel() {
		return istatistikBasvuruModel;
	}

	public void setIstatistikBasvuruModel(
			IstatistikDataBasvuruModel istatistikBasvuruModel) {
		this.istatistikBasvuruModel = istatistikBasvuruModel;
	}

	public Istatistik getSelectedBina() {
		return selectedBina;
	}

	public void setSelectedBina(Istatistik selectedBina) {
		this.selectedBina = selectedBina;
	}

	public Istatistik getSelectedCihaz() {
		return selectedCihaz;
	}

	public void setSelectedCihaz(Istatistik selectedCihaz) {
		this.selectedCihaz = selectedCihaz;
	}

	public boolean isRaporAcRender() {
		return raporAcRender;
	}

	public void setRaporAcRender(boolean raporAcRender) {
		this.raporAcRender = raporAcRender;
	}

	public IstatistikDataBasvuruModel getIstatistikKontrolModel() {
		return istatistikKontrolModel;
	}

	public void setIstatistikKontrolModel(
			IstatistikDataBasvuruModel istatistikKontrolModel) {
		this.istatistikKontrolModel = istatistikKontrolModel;
	}
	
	public void binaListener(Istatistik bina){
		
		 if (bina!=null && bina.getBinaId()>0){
			for (BinaDTO binaDTO : loginBean.getTumBinalar()) {
				if (binaDTO.getBinaId() == bina.getBinaId()) {
					binaBean.getBinaBilgileri(binaDTO);
					break;
				}
			}
		 }
		
	}

	public BinaBean getBinaBean() {
		return binaBean;
	}

	public void setBinaBean(BinaBean binaBean) {
		this.binaBean = binaBean;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}
	
	
}
