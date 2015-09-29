package tr.org.mmo.asansor.managedbeans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;


















import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

import tr.org.mmo.asansor.beans_.AnaSoru;
import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.business.AsansorBusiness;
import tr.org.mmo.asansor.business.FirmaBusiness;
import tr.org.mmo.asansor.business.KontrolBusiness;
import tr.org.mmo.asansor.business.RandevuBusiness;
import tr.org.mmo.asansor.business.SoruListBusiness;
import tr.org.mmo.asansor.dto.AsansorKapsamDTO;
import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.dto.CihazDTO;
import tr.org.mmo.asansor.dto.CihazFirmaDTO;
import tr.org.mmo.asansor.dto.FirmaDTO;
import tr.org.mmo.asansor.dto.KontrolDTO;
import tr.org.mmo.asansor.dto.MuhendisCihazYetkiDTO;
import tr.org.mmo.asansor.dto.OnTanimliTestDTO;
import tr.org.mmo.asansor.dto.RandevuListeDTO;
import tr.org.mmo.asansor.dto.RandevuMuhendisDTO;
import tr.org.mmo.asansor.dto.SoruDTO;
import tr.org.mmo.asansor.dto.SoruOnTanimliDTO;
import tr.org.mmo.asansor.dto.TestDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.models.AnaSoruDataModel;
import tr.org.mmo.asansor.models.CihazDataModel;
import tr.org.mmo.asansor.models.FirmaDataModel;
import tr.org.mmo.asansor.models.SoruDataModel;
import tr.org.mmo.asansor.models.SoruOnTanimliModel;
import tr.org.mmo.asansor.util.DateUtils;
import tr.org.mmo.asansor.util.Messages;

@ManagedBean
@ViewScoped
public class SoruTableBean implements Serializable {

	/**
	 * 
	 */
	private boolean bakimFirmaDegButton=false;
	private boolean kaydetButtonTasiRender=true;
	private boolean kontrolYanitlariTasinsinDisable=false;
	private  int  altSoruParent=0;
	private boolean bakanlikSoru=false;
	private String activeIndex1="100";
	private String activeIndex2="100";
	private String activeIndex3="100";
	private boolean kontrolTasiRender=false;
	private boolean rrender=false;
	private boolean kaydetButtonRender=true;
	private boolean soruListesiRender=false;
	private int onTanimliSoruId = 0;
	private String genelDurum;
	List<KontrolDTO> oncekiKontrollerList = new ArrayList<KontrolDTO>();
	private KontrolDTO kontrolDTO = new KontrolDTO();
	private String oncekiKontrolOnaylayanKisi;
	private String oncekiKontrolMuhendisi;
	int kapsamId = 0;
	int cihazTip = 0;
	private boolean kontrolYanitlariTasinsin = false;
	private KontrolDTO randevuyaAitKontrol = new KontrolDTO();
	@ManagedProperty(value="#{cihazBean}")
	private CihazBean cihazBean;
	public String getOncekiKontrolOnaylayanKisi() {
		return oncekiKontrolOnaylayanKisi;
	}
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;
	
	@ManagedProperty("#{binaBean}")
	private BinaBean binaBean;

	public BinaBean getBinaBean() {
		return binaBean;
	}

	public void setBinaBean(BinaBean binaBean) {
		this.binaBean = binaBean;
	}

	private boolean revizyonRaporMu;

	public void setOncekiKontrolOnaylayanKisi(String oncekiKontrolOnaylayanKisi) {
		this.oncekiKontrolOnaylayanKisi = oncekiKontrolOnaylayanKisi;
	}

	private HashMap<String, Integer> cihazOncekiKontroller;

	private boolean cVisible = false;
	private String dialogHeader;

	private SoruOnTanimliDTO onTanimliDTO = new SoruOnTanimliDTO();
	private List<SoruOnTanimliDTO> onTanimliList = new ArrayList<SoruOnTanimliDTO>();
	private List<SoruOnTanimliDTO> selected;
	private List<SoruDTO> altSorularList = new ArrayList<SoruDTO>();
	private SoruDataModel altSorularModel = new SoruDataModel(
			new ArrayList<SoruDTO>());

	private CihazDTO secilen;
	private CihazDTO cihaz = new CihazDTO();
	private SoruDTO soruEkleDTO = new SoruDTO();
	private List<AsansorKapsamDTO> kapsamTurleri = new ArrayList<AsansorKapsamDTO>();

	public CihazDTO getSecilen() {
		return secilen;
	}

	@ManagedProperty(value="#{loginBean}")
	private LoginBean loginBean;

	@PostConstruct
	public void init() {
		rrender=false;
		soruListesiRender=false;
		if (loginBean.getKullanici().getKullaniciTuru() == 1) {
			int sicilNo = 0;
			try {
				sicilNo = Integer.parseInt(loginBean.getKullanici()
						.getSicilNo().trim());
			} catch (Exception e) {
				sicilNo = 0;

			}
			if (sicilNo > 0) {
				List<MuhendisCihazYetkiDTO> yetkiList = new ArrayList<MuhendisCihazYetkiDTO>();
				try {
					yetkiList = new RandevuBusiness()
							.getMuhendisCihazYetki(sicilNo);
					loginBean.getKullanici().setMuhendisCihazYetkiList(
							yetkiList);
				} catch (ReadException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		if (binaBean!=null){
			this.binaBean.setGenelAraBina(new BinaDTO());
			this.binaBean.setGenelBinalar(new ArrayList<BinaDTO>());
		}
		if (binaBean!=null && binaBean.getRandevuId()>0){
			
			
			
			soruListSmall=new ArrayList<AnaSoru>();
			optionalSoruListSmall=new ArrayList<AnaSoru>();
			secilen=new CihazDTO();
			cihazlar=new ArrayList<CihazDTO>();
			kontrolDTO=new KontrolDTO();
			cihazList=new CihazDataModel(new ArrayList<CihazDTO>());
			Integer randevuId=(Integer)	((HttpServletRequest) FacesContext.getCurrentInstance()
					.getExternalContext().getRequest()).getSession()
					.getAttribute("parameterFromRandevuListe");
			if (randevuId!=null && (int)randevuId>0){
				((HttpServletRequest) FacesContext.getCurrentInstance()
						.getExternalContext().getRequest()).getSession()
						.removeAttribute("parameterFromRandevuListe");
				RandevuListeDTO r=(RandevuListeDTO)((HttpServletRequest) FacesContext.getCurrentInstance()
						.getExternalContext().getRequest()).getSession()
						.getAttribute("parameterRandevu");
				((HttpServletRequest) FacesContext.getCurrentInstance()
						.getExternalContext().getRequest()).getSession()
						.removeAttribute("parameterRandevu");
				
				try {
					cihazlar=new AsansorBusiness()
					.getKontrolEdilecekCihazlar(
							binaBean.getSecilenBina().getBinaId(), binaBean.getRandevuId());
					if (r!=null){
						this.binaBean.setRandevuListe(r);
					}
					
					
				} catch (ReadException e) {
					cihazlar=new ArrayList<CihazDTO>();
				}	
			}else{
				cihazlar=new ArrayList<CihazDTO>();
				this.binaBean.setRandevuId(0);
				this.binaBean.setRandevuStr("");
				this.binaBean.setRandevuListe(new RandevuListeDTO());
				if (this.binaBean.getSessionBina()!=null && this.binaBean.getSessionBina().getBinaId()>0){
						this.binaBean.getRandevularForBina(this.binaBean.getSessionBina());
					
				}
				cihazList=new CihazDataModel(cihazlar);
				
			}
			
			
			cihazList=new CihazDataModel(cihazlar);
		}else{
			cihazlar=new ArrayList<CihazDTO>();
			this.binaBean.setRandevuId(0);
			this.binaBean.setRandevuStr("");
			this.binaBean.setRandevuListe(new RandevuListeDTO());
			
			cihazList=new CihazDataModel(cihazlar);
			if (this.binaBean.getSessionBina()!=null && this.binaBean.getSessionBina().getBinaId()>0){
				this.binaBean.getRandevularForBina(this.binaBean.getSessionBina());
				
			}
		
			
			
		}
		
		

		
		
	}

	public void setSecilen(CihazDTO secilen) {
		this.secilen = secilen;
	}

	private List<CihazDTO> cihazlar;

	public List<CihazDTO> getCihazlar() {
		return cihazlar;
	}

	public void setCihazlar(List<CihazDTO> cihazlar) {
		this.cihazlar = cihazlar;
	}

	public CihazDataModel getCihazList() {
		return cihazList;
	}

	public void setCihazList(CihazDataModel cihazList) {
		this.cihazList = cihazList;
	}

	private CihazDataModel cihazList;

	public SoruTableBean() {
		if (!FacesContext.getCurrentInstance().getViewRoot().getViewId()
				.equals("/asansorkontroller.xhtml")) {
			soruListSmall = new ArrayList<AnaSoru>();
			optionalSoruListSmall=new ArrayList<AnaSoru>();
		}

	}

	private static final long serialVersionUID = -2407724394228627851L;
	private BinaDTO secilenBina;
	private List<AnaSoru> soruListSmall = new ArrayList<AnaSoru>();
	private List<AnaSoru> optionalSoruListSmall = new ArrayList<AnaSoru>();
	private AnaSoruDataModel soruModel = new AnaSoruDataModel(
			new ArrayList<AnaSoru>());

	private SoruOnTanimliModel onTanimliModel;
	private String altSoruHeader;

	public SoruOnTanimliModel getOnTanimliModel() {
		return onTanimliModel;
	}

	public void setOnTanimliModel(SoruOnTanimliModel onTanimliModel) {
		this.onTanimliModel = onTanimliModel;
	}

	public List<SoruOnTanimliDTO> getOnTanimliList() {
		return onTanimliList;
	}

	public void setOnTanimliList(List<SoruOnTanimliDTO> onTanimliList) {
		this.onTanimliList = onTanimliList;
	}

	public BinaDTO getSecilenBina() {
		return secilenBina;
	}

	public void setSecilenBina(BinaDTO secilenBina) {
		this.secilenBina = secilenBina;
	}

	public List<AnaSoru> getSoruListSmall() {
		return soruListSmall;
	}

	public void setSoruListSmall(List<AnaSoru> soruListSmall) {
		this.soruListSmall = soruListSmall;
	}

	public String getCihazlar(int binaId) {

		FacesContext context = FacesContext.getCurrentInstance();
		try {

			CihazBean cihazBean = (CihazBean) context.getApplication()
					.evaluateExpressionGet(context, "#{cihazBean}",
							CihazBean.class);

			cihazBean.setCihazlar(new AsansorBusiness().getCihazlar(binaId));
			cihazBean.setCihazList(new CihazDataModel(cihazBean.getCihazlar()));

			cihazBean
					.setHeader("Asansör detay bilgilerini görebilmek için liste üzerine tıklayınız !!!");
			cihazBean.submit();

		} catch (ReadException e) {
			e.printStackTrace();
		}

		return "";

	}



	public void getAnaSoru(int cihazTipi, int kapsamId, boolean asansorTest,Date tarih) {

		SoruListBusiness slb = new SoruListBusiness();
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage("");
		try {
			soruListSmall = new ArrayList<AnaSoru>();
			optionalSoruListSmall = new ArrayList<AnaSoru>();
			List<AnaSoru> soruList = new ArrayList<AnaSoru>();
			
			soruList = slb.anaSoruListesiGetir(cihazTipi, asansorTest,tarih);
			
			if (soruList.size() <= 0) {
				context.addMessage(null, new FacesMessage(
						Messages._TESTSORUSUBULUNAMADI_.getMesaj()));
			} else {
				if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
						.contains("kontrolListesi.xhtml")) {

					for (AnaSoru a : soruList) {
						boolean kapsamSorusu = false;
						a = setKapsam(a);

						for (int i : a.getKapsam()) {

							if (kapsamId == i) {
								kapsamSorusu = true;
								break;
							}
						}
						if (!kapsamSorusu) {
							a.setKapsam(new short[] { -1 });
						}
						List<SoruDTO> altSoruList = new ArrayList<SoruDTO>();
						for (SoruDTO s : a.getAltSorular()) {
							boolean kapsamAltSoru = false;
							s = setKapsam(s);
							for (int i : s.getKapsam()) {

								if (kapsamId == i) {
									kapsamAltSoru = true;
									break;
								}
							}
							if (!kapsamAltSoru) {

								s.setKapsam(new short[] { -1 });
							}
							altSoruList.add(s);
						}

						List<SoruDTO> altSoru = new ArrayList<SoruDTO>();
						List<SoruDTO> optionalSoru = new ArrayList<SoruDTO>();

						for (SoruDTO sd : altSoruList) {

							altSoru.add(sd);

						}
						a.setAltSorular(altSoru);
						a.setOptionalAltSorular(optionalSoru);
						soruListSmall.add(a);

					}

				} else {
					for (AnaSoru a : soruList) {
						boolean kapsamSorusu = false;
						a = setKapsam(a);

						for (int i : a.getKapsam()) {

							if (kapsamId == i) {
								kapsamSorusu = true;
								break;
							}
						}
						if (kapsamSorusu) {

							List<SoruDTO> altSoruList = new ArrayList<SoruDTO>();
							for (SoruDTO s : a.getAltSorular()) {
								altSoruList.add(s);
							}

							List<SoruDTO> altSoru = new ArrayList<SoruDTO>();
							List<SoruDTO> optionalSoru = new ArrayList<SoruDTO>();

							for (SoruDTO sd : altSoruList) {
								boolean kapsamAltSoru = false;
								sd = setKapsam(sd);
								for (int i : sd.getKapsam()) {

									if (kapsamId == i) {
										kapsamAltSoru = true;
										break;
									}
								}
								if (!kapsamAltSoru) {
									optionalSoru.add(sd);

								} else {
									altSoru.add(sd);

								}

							}
							a.setAltSorular(altSoru);
							a.setOptionalAltSorular(optionalSoru);
							soruListSmall.add(a);

						} else {
							optionalSoruListSmall.add(a);
						}

					}
				}
			}
		} catch (ReadException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, msg);
		}
	}

	public void getTestAnaSoru(int cihazTipi) {
		cVisible = false;
		SoruListBusiness slb = new SoruListBusiness();
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage("");
		optionalSoruListSmall=new ArrayList<AnaSoru>();

		try {
			soruListSmall = slb.testAnaSoruListesiGetir(cihazTipi,bakanlikSoru);
			for (AnaSoru as : soruListSmall) {
				as = setKapsam(as);
				for (SoruDTO t : as.getAltSorular()) {
					t = setKapsam(t);
				}

			}

			if (soruListSmall.size() <= 0) {
				List<AnaSoru> l = new ArrayList<AnaSoru>();
				
				AnaSoru a = new AnaSoru();
				l.add(a);
				soruModel = new AnaSoruDataModel(l);
				cVisible = false;
				context.addMessage(null, new FacesMessage(
						Messages._TESTSORUSUBULUNAMADI_.getMesaj()));

			} else {
				soruModel = new AnaSoruDataModel(new ArrayList<AnaSoru>());
				soruModel = new AnaSoruDataModel(soruListSmall);
				cVisible = true;

			}
		} catch (ReadException e) {
			cVisible = false;
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, msg);
		}
	}
	
	private void tarihHataliMi(Date baslangic,Date bitis) throws CRUDException{
		
		Calendar cal=Calendar.getInstance();
		Calendar cal0=Calendar.getInstance();
		Calendar cal1=Calendar.getInstance();
		
			
				cal.setTime(baslangic);
				cal0.setTime(bitis);
				if (cal1.before(cal)){
					throw new CRUDException(Messages._KONTROLTARIHIGUNUNTARINDENBUYUKOLAMAZ_.getMesaj(),null);
				}
				
				if (cal1.before(cal0)){
					throw new CRUDException(Messages._KONTROLTARIHIGUNUNTARINDENBUYUKOLAMAZ_.getMesaj(),null);
				}
				
				
				
				if (cal.get(Calendar.HOUR_OF_DAY)<6){
					throw new CRUDException(Messages._KONTROLSAATIHATALI_.getMesaj(),null);
					
					
				}
				if (cal0.get(Calendar.HOUR_OF_DAY)<6){
					throw new CRUDException(Messages._KONTROLSAATIHATALI_.getMesaj(),null);
				}
				if (bitis.before(baslangic)){
					
					throw new CRUDException(Messages._KONTROLTARIHGIRISIHATALI_.getMesaj(),null);
				}
		
			
			
			
				
	}
	public void testKaydet(ActionEvent event) {

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage("");
		try {

		if (this.kontrolDTO.getKontrolTuru()==null || this.kontrolDTO.getKontrolTuru().trim().equals("")){
			msg = new FacesMessage(Messages._KONTROLTURUSECINIZ_.getMesaj());
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			
			context.addMessage(null, msg);
		}else{
			if (this.kontrolDTO.getKontrolBaslangicTarihi()==null || 
				
					this.kontrolDTO.getKontrolBitisTarihi()==null 
				 
					){
				msg = new FacesMessage(Messages._KONTROLZAMANIHATALI_.getMesaj());
				msg.setSeverity(FacesMessage.SEVERITY_INFO);

				context.addMessage(null, msg);
				
			}else{
				tarihHataliMi(this.kontrolDTO.getKontrolBaslangicTarihi(), this.kontrolDTO.getKontrolBitisTarihi());
				
				if (this.kontrolDTO.getOnaylayanSicilNo()<=0){
					msg = new FacesMessage(Messages._ONAYLAYANSECINIZ_.getMesaj());
					msg.setSeverity(FacesMessage.SEVERITY_INFO);

					context.addMessage(null, msg);
				}else{
					if(this.kontrolDTO.getKontrolFirmaKatilimDTO().isFirmaKontroleKatildi() && 
							(this.kontrolDTO.getKontrolFirmaKatilimDTO().getFirmaGorevli1()==null || this.kontrolDTO.getKontrolFirmaKatilimDTO().getFirmaGorevli1().trim().length()<=3)){
						msg = new FacesMessage(Messages._FIRMAGOREVLIGIRINIZ_.getMesaj());
						msg.setSeverity(FacesMessage.SEVERITY_INFO);

						context.addMessage(null, msg);
					}else{
						
					
		
			SoruListBusiness slb = new SoruListBusiness();
			for (AnaSoru a : optionalSoruListSmall) {
				soruListSmall.add(a);
			}
			String etiket = etiketBelirle(soruListSmall,this.kontrolDTO.getKontrolBaslangicTarihi());

			kontrolDTO.setCihaz(secilen);
			kontrolDTO.setCihazId(secilen.getCihazId());
			kontrolDTO.setRevizyonRapor(isRevizyonRaporMu());
			Calendar cal = Calendar.getInstance();
			cal.setTime(kontrolDTO.getKontrolBaslangicTarihi());

			kontrolDTO.setKontrolBaslangicSaati(cal.getTime());
			cal = Calendar.getInstance();
			cal.setTime(kontrolDTO.getKontrolBitisTarihi());

			kontrolDTO.setKontrolBitisSaati(cal.getTime());
			slb.kontrolKaydet(this.kontrolDTO, etiket, soruListSmall);

			String str = etiket.equals("Y") ? Messages._ETIKETYESIL_.getMesaj()
					: etiket.equals("S") ? Messages._ETIKETSARI_.getMesaj()
							:etiket.equals("M") ? Messages._ETIKETMAVI_.getMesaj(): Messages._ETIKETKIRMIZI_.getMesaj();
			msg = new FacesMessage(Messages._TESTKAYDEDILDI_.getMesaj() + " "
					+ str);
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			for (CihazDTO c : getCihazlar()) {
				if (c.getCihazId() == secilen.getCihazId()) {
					c.setKontrolTarihi(kontrolDTO.getKontrolBitisTarihi());
					break;
				}
			}
			
			// getCihazKontroller(secilen.getCihazId());
			setCihazList(new CihazDataModel(getCihazlar()));
			kontrolTasiRender=false;
			rrender=false;
			soruListSmall = new ArrayList<AnaSoru>();
			optionalSoruListSmall=new ArrayList<AnaSoru>();
			randevuyaAitKontrol = new KontrolDTO();
			secilen = new CihazDTO();
			kontrolYanitlariTasinsin = false;

			kontrolDTO = new KontrolDTO();
			soruListesiRender=false;
			

			context.addMessage(null, msg);
		}
		
					}
					
				}
						
						
				
			}

		} catch (CRUDException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);

			context.addMessage(null, msg);
		}


	}
	/*
	public void onTabChange(TabChangeEvent event) {
		TabView tView = (TabView) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(":formbina:tbbina");

		FacesContext context = FacesContext.getCurrentInstance();

		if (tView.getActiveIndex() == 2) {
			CihazBean cihazBean = (CihazBean) context
					.getApplication()
					.getExpressionFactory()
					.createValueExpression(context.getELContext(),
							"#{cihazBean}", CihazBean.class)

					.getValue(context.getELContext());
			cihazOncekiKontroller = new HashMap<String, Integer>();

			if (cihazBean != null && cihazBean.getSecilen() != null
					&& cihazBean.getSecilen().getTip() > 0) {
				getCihazKontroller(cihazBean.getSecilen().getCihazId());
				getAnaSoru(cihazBean.getSecilen().getTip(), cihazBean
						.getSecilen().getKapsamId(), false);

			}

		}
		
	}
	*/

	public void getOnTanimliSorular(SoruDTO soru) {
		onTanimliList = new ArrayList<SoruOnTanimliDTO>();
		for (SoruOnTanimliDTO sod : soru.getList()) {

			onTanimliList.add(sod);
		}
		onTanimliModel = new SoruOnTanimliModel(onTanimliList);

	}

	public void setOnTanimliSorular() {

		for (AnaSoru s : soruListSmall) {
			for (SoruDTO sd : s.getAltSorular()) {
				if (sd.getSoruId() == selected.get(0).getSoruId()) {

					sd.setSlctdList(selected);
					break;
				}
			}

		}

	}

	public List<SoruOnTanimliDTO> getSelected() {
		return selected;
	}

	public void setSelected(List<SoruOnTanimliDTO> selected) {
		this.selected = selected;
	}

	private String etiketBelirle(List<AnaSoru> soruListSmall,Date kontrolTarihi) {

		String etiket = "Y";
		if 
		(kontrolTarihi.compareTo(sessionBean.getBakanlikSoru().getTarih())<0){
		for (AnaSoru a : soruListSmall) {
			if (etiket.equals("K"))
				break;
			if (a.getDurum() != null
					&& a.getDurum().equalsIgnoreCase("Uygun Değil")) {
				List<SoruDTO> list = new ArrayList<SoruDTO>();
				for (SoruDTO alt : a.getAltSorular()) {
					list.add(alt);
				}
				for (SoruDTO alt : a.getOptionalAltSorular()) {
					list.add(alt);
				}
				for (SoruDTO s : list) {
					if (etiket.equals("K"))
						break;

					s.setSlctdList(new ArrayList<SoruOnTanimliDTO>());
					for (SoruOnTanimliDTO sod : s.getList()) {
						for (int i : s.getSelectedListId()) {
							if (i == sod.getId())
								s.getSlctdList().add(sod);
						}
					}
					for (SoruOnTanimliDTO sod : s.getSlctdList()) {
						if (etiket.equals("K"))
							break;
						if (sod.getAciklama().contains("**")) {
							etiket = "K";
							break;
						}
						if (sod.getAciklama().contains("*")) {
							etiket = "S";
						}
					}

				}
			}

		}
	}
		else{
			for (AnaSoru a : soruListSmall) {
				if (etiket.equals("K"))
					break;
				if (a.getDurum() != null
					&& a.getDurum().equalsIgnoreCase("Uygun Değil")){
					etiket=getEtiket(a.getYildiz().trim(), etiket);
					
					
				}
				if (etiket.equals("K"))
					break;
				for (SoruDTO s:a.getAltSorular()){
					if (etiket.equals("K"))
						break;
					if (s.getDurum() != null
							&& s.getDurum().equalsIgnoreCase("Uygun Değil")){
							etiket=getEtiket(s.getYildiz().trim(), etiket);
							
							
						}
				}
				for (SoruDTO s:a.getOptionalAltSorular()){
					if (etiket.equals("K"))
						break;
					if (s.getDurum() != null
							&& s.getDurum().equalsIgnoreCase("Uygun Değil")){
							etiket=getEtiket(s.getYildiz().trim(), etiket);
							
							
						}
				}
			}
		}

		return etiket;

	}
	private String getEtiket(String yildiz,String currentEtiket){
	String etiket="";
	if (yildiz.equals("**")){
		etiket="K";
	}
	if (yildiz.equals("*")){
		if (!currentEtiket.equals("K")){
		etiket="S";
		}else{
			etiket=currentEtiket;
		}
	}
	if (yildiz.equals("")){
		if (!currentEtiket.equals("K") && !currentEtiket.equals("S")){
		etiket="M";
		}
		else{
			etiket=currentEtiket;
		}
	}
	return etiket;
}
	public HashMap<String, Integer> getCihazOncekiKontroller() {
		return cihazOncekiKontroller;
	}

	public void setCihazOncekiKontroller(
			HashMap<String, Integer> cihazOncekiKontroller) {
		this.cihazOncekiKontroller = cihazOncekiKontroller;
	}

	public void getCihazKontroller(int cihazId) {
		cihazOncekiKontroller = new HashMap<String, Integer>();
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		try {
			List<KontrolDTO> l=new ArrayList<KontrolDTO>();
			oncekiKontrollerList=new ArrayList<KontrolDTO>();
			l = new KontrolBusiness()
					.getCihazKontroller(cihazId);
			
			for (KontrolDTO k : l) {
				if (!k.isRaporIptal()){
					oncekiKontrollerList.add(k);
				}
				
			}
			for (KontrolDTO k : oncekiKontrollerList) {
				

				String s = k.isRevizyonRapor() ? " - REV" : "";
				cihazOncekiKontroller.put(df.format(k.getKontrolBitisTarihi())
						+ s, k.getKontrolId());
				
			}

		} catch (CRUDException e) {

			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "",
					e.getMessage());
			FacesContext.getCurrentInstance().addMessage("Hata", m);
		}

	}

	public void cihazTestByKontrol(KontrolDTO kontrol) {
		if (kontrol != null) {
			try {
				soruListSmall = new ArrayList<AnaSoru>();
				optionalSoruListSmall = new ArrayList<AnaSoru>();
				List<AnaSoru> list = new ArrayList<AnaSoru>();
				Date tarih=new Date();
				try{
					tarih=kontrol.getKontrolBaslangicTarihi().compareTo(sessionBean.getBakanlikSoru().getTarih())>=0?sessionBean.getBakanlikSoru().getTarih():null;
				}catch (Exception e) {
					tarih=null;
					}
				list = new SoruListBusiness().anaSoruListesiGetir(kontrol
						.getCihaz().getTip(), false,tarih);
				

				for (AnaSoru a : list) {
					boolean kapsamSorusu = false;
					a = setKapsam(a);
					for (int i : a.getKapsam()) {

						if (kontrol.getCihaz().getKapsamId() == i) {
							kapsamSorusu = true;
							break;
						}
					}
					if (kapsamSorusu) {

						List<SoruDTO> altSoruList = new ArrayList<SoruDTO>();
						for (SoruDTO s : a.getAltSorular()) {
							altSoruList.add(s);
						}

						List<SoruDTO> altSoru = new ArrayList<SoruDTO>();
						List<SoruDTO> optionalSoru = new ArrayList<SoruDTO>();

						for (SoruDTO sd : altSoruList) {
							boolean kapsamAltSoru = false;
							sd = setKapsam(sd);
							for (int i : sd.getKapsam()) {

								if (kontrol.getCihaz().getKapsamId() == i) {
									kapsamAltSoru = true;
									break;
								}
							}
							if (!kapsamAltSoru) {
								optionalSoru.add(sd);

							} else {
								altSoru.add(sd);

							}

						}
						a.setAltSorular(altSoru);
						a.setOptionalAltSorular(optionalSoru);
						soruListSmall.add(a);

					} else {
						optionalSoruListSmall.add(a);
					}

				}

				cihazTestSonucGetir(kontrol.getKontrolId());

			} catch (ReadException e) {
				FacesMessage msg = new FacesMessage(e.getMessage());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

		}

	}

	public void cihazTestYanitlariByKontrolId(AjaxBehaviorEvent event) {
		String s = null;
		kaydetButtonRender=true;
		RequestContext rc = RequestContext.getCurrentInstance();
		rc.execute("PF('oncekikontrol').hide()");
		try {
			s = ((UIInput) event.getComponent()).getValue().toString();
		} catch (NullPointerException e) {

		}
		/*
		AccordionPanel acrd = (AccordionPanel) FacesContext
				.getCurrentInstance().getViewRoot()
				.findComponent(":formbina:accrdnsorular");
		acrd.setMultiple(false);
		*/
	//	kontrolDTO = new KontrolDTO();
		genelDurum = "";
		Date tarih=new Date();
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		if (s != null && !s.trim().equals("")) {
			int kontrolId = Integer.parseInt(s);
			KontrolDTO currentKontrol=new KontrolDTO();
			
			for (KontrolDTO kd : oncekiKontrollerList) {

				if (kontrolId == kd.getKontrolId()) {
					try {
						currentKontrol=(KontrolDTO)kd.clone();
					} catch (CloneNotSupportedException e1) {
						currentKontrol=new KontrolDTO();
					}
					setKontrol(kd);
				}
			}
			
			try{
			tarih=currentKontrol.getKontrolBaslangicTarihi().compareTo(sessionBean.getBakanlikSoru().getTarih())>=0?sessionBean.getBakanlikSoru().getTarih():null;
				currentKontrol=null;
			}catch (Exception e) {
				tarih=null;
				}
			getAnaSoru(cihazTip, kapsamId, false,tarih);
			showTest(kontrolId);

		} else {
			//acrd.setActiveIndex("0");
			tarih=Calendar.getInstance().getTime().compareTo(sessionBean.getBakanlikSoru().getTarih())>=0?sessionBean.getBakanlikSoru().getTarih():null;
			
				
			
			getAnaSoru(secilen.getTip(), secilen.getKapsamId(), false,tarih);
			kontrolDTO = new KontrolDTO();
			kontrolDTO.setKontrolTuru(getKontrolTuru());
		}

	}

	private void showTest(int kontrolId) {
		cihazTestSonucGetir(kontrolId);
		/*
		AccordionPanel acrd = (AccordionPanel) FacesContext
				.getCurrentInstance().getViewRoot()
				.findComponent(":formbina:accrdnsorular");
		cihazTestSonucGetir(kontrolId);
		acrd.setMultiple(true);
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < soruListSmall.size(); i++) {
			StringBuffer sbj = new StringBuffer();

			if (i == 0)
				sb.append(i);
			else
				sb.append("," + i);
		}
		acrd.setActiveIndex(sb.toString());
		*/
		soruListesiRender=true;
		kontrolYanitlariTasinsin = false;

	}

	public void cihazTestSonucGetir(int kontrolId) {

		try {

			List<TestDTO> list = new ArrayList<TestDTO>();

			list = new SoruListBusiness()
					.getTestYanitlariByKontrolId(kontrolId);

			if (list != null) {

				for (TestDTO t : list) {
					for (AnaSoru a : soruListSmall) {
						if (a.getSoruId() == t.getSoruId()) {
							a.setDurum(t.getCevap());

						}
						for (SoruDTO s:a.getAltSorular()){
							if (s.getSoruId() == t.getSoruId()) {
								s.setDurum(t.getCevap());

							}
						}
						for (SoruDTO s:a.getOptionalAltSorular()){
							if (s.getSoruId() == t.getSoruId()) {
								s.setDurum(t.getCevap());

							}
						}
					}

					for (AnaSoru a : optionalSoruListSmall) {
						if (a.getSoruId() == t.getSoruId()) {
							a.setDurum(t.getCevap());

						}
						for (SoruDTO s:a.getAltSorular()){
							if (s.getSoruId() == t.getSoruId()) {
								s.setDurum(t.getCevap());

							}
						}
					}
				}
				List<OnTanimliTestDTO> onTanimliTestList = new ArrayList<OnTanimliTestDTO>();

				onTanimliTestList = new SoruListBusiness()
						.getOnTanimliTestYanitlariByKontrolId(kontrolId);

				for (AnaSoru as : soruListSmall) {
					for (SoruDTO sd : as.getAltSorular()) {

						for (OnTanimliTestDTO o : onTanimliTestList) {

							if (sd.getSoruId() == o.getSoruId()) {
								SoruOnTanimliDTO so = new SoruOnTanimliDTO();
								so.setId(o.getOnTanimliId());
								sd.getSlctdList().add(so);
							}

						}
						sd.setSelectedListId(new int[sd.getSlctdList().size()]);
						
							if (sd.getSlctdList()!=null && sd.getSlctdList().size()>0){
								sd.setCheck(true);
							}else{
								sd.setCheck(false);
							}
							
						int i = 0;
						for (SoruOnTanimliDTO ss : sd.getSlctdList()) {
							sd.getSelectedListId()[i] = ss.getId();
							i++;
						}
					}

					for (SoruDTO sd : as.getOptionalAltSorular()) {

						for (OnTanimliTestDTO o : onTanimliTestList) {

							if (sd.getSoruId() == o.getSoruId()) {
								SoruOnTanimliDTO so = new SoruOnTanimliDTO();
								so.setId(o.getOnTanimliId());
								sd.getSlctdList().add(so);
							}

						}
						sd.setSelectedListId(new int[sd.getSlctdList().size()]);
						if (sd.getSlctdList()!=null && sd.getSlctdList().size()>0){
							sd.setCheck(true);
						}else{
							sd.setCheck(false);
						}
						int i = 0;
						for (SoruOnTanimliDTO ss : sd.getSlctdList()) {
							sd.getSelectedListId()[i] = ss.getId();
							i++;
						}
					}
				}

				for (AnaSoru as : optionalSoruListSmall) {
					for (SoruDTO sd : as.getAltSorular()) {

						for (OnTanimliTestDTO o : onTanimliTestList) {

							if (sd.getSoruId() == o.getSoruId()) {
								SoruOnTanimliDTO so = new SoruOnTanimliDTO();
								so.setId(o.getOnTanimliId());
								sd.getSlctdList().add(so);
							}

						}
						sd.setSelectedListId(new int[sd.getSlctdList().size()]);
						if (sd.getSlctdList()!=null && sd.getSlctdList().size()>0){
							sd.setCheck(true);
						}else{
							sd.setCheck(false);
						}
						int i = 0;
						for (SoruOnTanimliDTO ss : sd.getSlctdList()) {
							sd.getSelectedListId()[i] = ss.getId();
							i++;
						}
					}
				}

			}

		} catch (ReadException e) {

			FacesMessage msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public void cihazTestSonucGetir(Connection con, int kontrolId) {

		try {
			List<TestDTO> list = new ArrayList<TestDTO>();

			list = new SoruListBusiness()
					.getTestYanitlariByKontrolId(kontrolId);

			if (list != null) {

				for (TestDTO t : list) {
					for (AnaSoru a : soruListSmall) {
						if (a.getSoruId() == t.getSoruId()) {
							a.setDurum(t.getCevap());

						}
						for (SoruDTO s:a.getAltSorular()){
							if (s.getSoruId() == t.getSoruId()) {
								s.setDurum(t.getCevap());

							}
						}
						for (SoruDTO s:a.getOptionalAltSorular()){
							if (s.getSoruId() == t.getSoruId()) {
								s.setDurum(t.getCevap());

							}
						}
					}

					for (AnaSoru a : optionalSoruListSmall) {
						if (a.getSoruId() == t.getSoruId()) {
							a.setDurum(t.getCevap());

						}
						for (SoruDTO s:a.getAltSorular()){
							if (s.getSoruId() == t.getSoruId()) {
								s.setDurum(t.getCevap());

							}
						}
					}
				}
				List<OnTanimliTestDTO> onTanimliTestList = new ArrayList<OnTanimliTestDTO>();

				onTanimliTestList = new SoruListBusiness()
						.getOnTanimliTestYanitlariByKontrolId(kontrolId);

				for (AnaSoru as : soruListSmall) {
					for (SoruDTO sd : as.getAltSorular()) {

						for (OnTanimliTestDTO o : onTanimliTestList) {

							if (sd.getSoruId() == o.getSoruId()) {
								SoruOnTanimliDTO so = new SoruOnTanimliDTO();
								so.setId(o.getOnTanimliId());
								sd.getSlctdList().add(so);
							}

						}
						sd.setSelectedListId(new int[sd.getSlctdList().size()]);
						int i = 0;
						for (SoruOnTanimliDTO ss : sd.getSlctdList()) {
							sd.getSelectedListId()[i] = ss.getId();
							i++;
						}
					}
				}

				for (AnaSoru as : optionalSoruListSmall) {
					for (SoruDTO sd : as.getAltSorular()) {

						for (OnTanimliTestDTO o : onTanimliTestList) {

							if (sd.getSoruId() == o.getSoruId()) {
								SoruOnTanimliDTO so = new SoruOnTanimliDTO();
								so.setId(o.getOnTanimliId());
								sd.getSlctdList().add(so);
							}

						}
						sd.setSelectedListId(new int[sd.getSlctdList().size()]);
						int i = 0;
						for (SoruOnTanimliDTO ss : sd.getSlctdList()) {
							sd.getSelectedListId()[i] = ss.getId();
							i++;
						}
					}
				}

			}

		} catch (ReadException e) {

			FacesMessage msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public void onTanimliSil(SoruOnTanimliDTO s) {
		for (AnaSoru as : soruListSmall) {
			for (SoruDTO sd : as.getAltSorular()) {
				for (SoruOnTanimliDTO sod : sd.getSlctdList()) {
					if (sod.getAciklama().trim().equals(s.getAciklama().trim())) {
						sd.getSlctdList().remove(sod);

						if (sd.getSlctdList().size() <= 0)
							sd.setSlctdList(new ArrayList<SoruOnTanimliDTO>());

						break;
					}
				}
			}
		}
	}

	Calendar calBaslangic = Calendar.getInstance();
	Calendar calBitis = Calendar.getInstance();

	private boolean tarihKontrol(Object o, UIComponent uc) {
		boolean bool = true;

		if (uc.getId().equals("baslangictarih")) {

			calBaslangic.setTime((Date) o);
			kontrolDTO.setKontrolBaslangicTarihi(calBaslangic.getTime());

		}

		if (uc.getId().equals("bitistarih")) {

			calBitis.setTime((Date) o);
			kontrolDTO.setKontrolBitisTarihi(calBitis.getTime());
		}
		if (calBitis != null && calBaslangic != null
				&& calBitis.before(calBaslangic))
				 {
			bool = false;
		
		}
		return bool;

	}

	public void dateListener(SelectEvent event) {
		Object o = event.getObject();
		UIComponent uc = event.getComponent();

		if (!tarihKontrol(o, uc)) {

			FacesMessage msg = new FacesMessage(
					Messages._KONTROLTARIHIHATALI_.getMesaj());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public void onRowSelect(SelectEvent event) {

		try {
			
				secilen = (CihazDTO) event.getObject();
				kontrolTasiRender=false;
				kaydetButtonRender=true;
				kaydetButtonTasiRender=true;
				boolean yetkili = false;
				List<MuhendisCihazYetkiDTO> yetkiList = new ArrayList<MuhendisCihazYetkiDTO>();
				yetkiList = loginBean.getKullanici()
						.getMuhendisCihazYetkiList();
				for (MuhendisCihazYetkiDTO m : yetkiList) {

					if (m.getCihazTipi() == secilen.getTip()) {
						yetkili = true;
						break;
					}

				}

				if (!yetkili) {
					soruListSmall = new ArrayList<AnaSoru>();
					optionalSoruListSmall=new ArrayList<AnaSoru>();
					this.kontrolDTO = new KontrolDTO();
					soruListesiRender=false;
					FacesMessage msg = new FacesMessage(
							"Bu Cihaz Tipine kontrol yetkiniz bulunmamaktadır.");
					msg.setSeverity(FacesMessage.SEVERITY_INFO);
					FacesContext.getCurrentInstance().addMessage(null, msg);
				} else {
					randevuyaAitKontrol = new KontrolDTO();
					kontrolYanitlariTasinsin = false;

					SelectOneMenu kontrolTarihleri = (SelectOneMenu) FacesContext
							.getCurrentInstance().getViewRoot()
							.findComponent(":formbina:kontroltarihleri");
					try {
						kontrolTarihleri.setValue("");
					} catch (Exception e) {

					}
					
					getCihazKontroller(secilen.getCihazId());
					for (KontrolDTO k : oncekiKontrollerList) {
						if (k.getRandevuId() == binaBean.getRandevuId()) {
							try {
								randevuyaAitKontrol = (KontrolDTO) k.clone();
							} catch (CloneNotSupportedException e) {
								randevuyaAitKontrol = new KontrolDTO();
							}
						}
					}
					CihazBean cihazBean = (CihazBean) FacesContext
							.getCurrentInstance().getViewRoot().getViewMap()
							.get("cihazBean");
					if (cihazBean != null) {

						cihazBean.asansorTeknikOzellikleri(secilen);
						cihazBean.setCihaz(secilen);
						

					}
					Date raporTarih=new Date();
					Date tarih=new Date();
					DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
					if (randevuyaAitKontrol.getKontrolId()>0){
						raporTarih=randevuyaAitKontrol.getKontrolBaslangicTarihi();
					}
					tarih=raporTarih.compareTo(sessionBean.getBakanlikSoru().getTarih())>=0?sessionBean.getBakanlikSoru().getTarih():null;
					
						
					
					soruListSmall = new ArrayList<AnaSoru>();
					optionalSoruListSmall=new ArrayList<AnaSoru>();
					kapsamId = secilen.getKapsamId();
					cihazTip = secilen.getTip();
					getAnaSoru(secilen.getTip(), secilen.getKapsamId(), true,tarih);
					if (soruListSmall==null || soruListSmall.size()<=0){
						soruListesiRender=false;
						
						soruListSmall=new ArrayList<AnaSoru>();
						
						altSorularModel=new SoruDataModel(new ArrayList<SoruDTO>());
						onTanimliList=new ArrayList<SoruOnTanimliDTO>();
						onTanimliModel=new SoruOnTanimliModel(new ArrayList<SoruOnTanimliDTO>());
						optionalSoruListSmall=new ArrayList<AnaSoru>();
						throw new CRUDException(Messages._TESTSORUSUBULUNAMADI_.getMesaj(), null);
					}
					if (randevuyaAitKontrol.getKontrolId() > 0) {
						try {
							this.kontrolDTO = (KontrolDTO) randevuyaAitKontrol
									.clone();
							kontrolDTO.setBina(binaBean.getBinaDTO());
							kontrolDTO.setCihazId(secilen.getCihazId());

							setKontrol(kontrolDTO);
							showTest(kontrolDTO.getKontrolId());
						} catch (CloneNotSupportedException e) {
							this.kontrolDTO = new KontrolDTO();
							throw new CRUDException(
									Messages._SQL_514_.getMesaj(), null);
						}
					} else {
						this.kontrolDTO = new KontrolDTO();
						kontrolDTO.setKontrolTuru(getKontrolTuru());
						kontrolDTO.setCihazId(secilen.getCihazId());
						kontrolDTO.setRandevuId(binaBean.getRandevuId());
						kontrolDTO.setBina(binaBean.getBinaDTO());
						FirmaDTO firma = new FirmaDTO();
						firma = new FirmaBusiness()
								.getCihazKontrolFirma(
										kontrolDTO.getRandevuId(),
										secilen.getCihazId());
						firma = firma == null ? new FirmaDTO() : firma;
						kontrolDTO.setFirma(firma);
						soruListesiRender=false;
						
						TabView tw = (TabView) FacesContext
								.getCurrentInstance().getViewRoot()
								.findComponent(":formbina:tw");
						if (tw!=null)
							tw.setActiveIndex(1);
//						RequestContext.getCurrentInstance().execute(
//								" PF('asansorbilgi').show()");
					}
				}
			
		} catch (NumberFormatException e) {

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,  e
							.getMessage(),""));
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,  e
							.getMessage(),""));
		}

	}
	
	public void asansorGuncellendi(CihazDTO cihaz){


		try {
			this.secilen=cihaz;
				

				
					randevuyaAitKontrol = new KontrolDTO();
					kontrolYanitlariTasinsin = false;

					SelectOneMenu kontrolTarihleri = (SelectOneMenu) FacesContext
							.getCurrentInstance().getViewRoot()
							.findComponent(":formbina:kontroltarihleri");
					try {
						kontrolTarihleri.setValue("");
					} catch (Exception e) {

					}
					
					getCihazKontroller(secilen.getCihazId());
					for (KontrolDTO k : oncekiKontrollerList) {
						if (k.getRandevuId() == binaBean.getRandevuId()) {
							try {
								randevuyaAitKontrol = (KontrolDTO) k.clone();
							} catch (CloneNotSupportedException e) {
								randevuyaAitKontrol = new KontrolDTO();
							}
						}
					}
					CihazBean cihazBean = (CihazBean) FacesContext
							.getCurrentInstance().getViewRoot().getViewMap()
							.get("cihazBean");
					if (cihazBean != null) {

						cihazBean.asansorTeknikOzellikleri(secilen);
						cihazBean.setCihaz(secilen);

					}
					Date raporTarih=new Date();
					Date tarih=new Date();
					DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
					if (randevuyaAitKontrol.getKontrolId()>0){
						raporTarih=randevuyaAitKontrol.getKontrolBaslangicTarihi();
					}
				
					tarih=raporTarih.compareTo(sessionBean.getBakanlikSoru().getTarih())>=0?sessionBean.getBakanlikSoru().getTarih():null;
					
					
					soruListSmall = new ArrayList<AnaSoru>();
					optionalSoruListSmall=new ArrayList<AnaSoru>();
					kapsamId = secilen.getKapsamId();
					cihazTip = secilen.getTip();
					getAnaSoru(secilen.getTip(), secilen.getKapsamId(), true,tarih);
					if (soruListSmall==null || soruListSmall.size()<=0){
						soruListesiRender=false;
						soruListSmall=new ArrayList<AnaSoru>();
						altSorularModel=new SoruDataModel(new ArrayList<SoruDTO>());
						onTanimliList=new ArrayList<SoruOnTanimliDTO>();
						onTanimliModel=new SoruOnTanimliModel(new ArrayList<SoruOnTanimliDTO>());
						optionalSoruListSmall=new ArrayList<AnaSoru>();
						throw new CRUDException(Messages._TESTSORUSUBULUNAMADI_.getMesaj(), null);
					}
					if (randevuyaAitKontrol.getKontrolId() > 0) {
						try {
							this.kontrolDTO = (KontrolDTO) randevuyaAitKontrol
									.clone();
							kontrolDTO.setBina(binaBean.getBinaDTO());
							kontrolDTO.setCihazId(secilen.getCihazId());

							setKontrol(kontrolDTO);
							showTest(kontrolDTO.getKontrolId());
						} catch (CloneNotSupportedException e) {
							this.kontrolDTO = new KontrolDTO();
							throw new CRUDException(
									Messages._SQL_514_.getMesaj(), null);
						}
					} else {
						this.kontrolDTO = new KontrolDTO();
						kontrolDTO.setKontrolTuru(getKontrolTuru());
						kontrolDTO.setCihazId(secilen.getCihazId());
						kontrolDTO.setRandevuId(binaBean.getRandevuId());
						kontrolDTO.setBina(binaBean.getBinaDTO());
						FirmaDTO firma = new FirmaDTO();
						firma = new FirmaBusiness()
								.getCihazKontrolFirma(
										kontrolDTO.getRandevuId(),
										secilen.getCihazId());
						firma = firma == null ? new FirmaDTO() : firma;
						kontrolDTO.setFirma(firma);
						soruListesiRender=false;
						
						TabView tw = (TabView) FacesContext
								.getCurrentInstance().getViewRoot()
								.findComponent(":formbina:tw");
						if (tw!=null)
							tw.setActiveIndex(1);
//						RequestContext.getCurrentInstance().execute(
//								" PF('asansorbilgi').show()");
					}
				
			
		} catch (NumberFormatException e) {

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,  e
							.getMessage(),""));
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,  e
							.getMessage(),""));
		}

	
	}

	public void onRowUnSelect(ActionEvent event) {
		SelectOneMenu kontrolTarihleri = (SelectOneMenu) FacesContext
				.getCurrentInstance().getViewRoot()
				.findComponent(":formbina:kontroltarihleri");
		try {
			kontrolTarihleri.setValue("");
		} catch (Exception e) {

		}
		randevuyaAitKontrol = new KontrolDTO();
		kontrolYanitlariTasinsin = false;
		kaydetButtonTasiRender=true;
		secilen = new CihazDTO();
		rrender=false;
		kontrolTasiRender=false;
		soruListSmall = new ArrayList<AnaSoru>();
		optionalSoruListSmall=new ArrayList<AnaSoru>();
		this.kontrolDTO = new KontrolDTO();
		soruListesiRender=false;
		if (this.cihazBean!=null){
			this.cihazBean.setCihazTeknik(null);
		}
		soruListSmall = new ArrayList<AnaSoru>();
	}

	private String getKontrolTuru() {
		String kontrolTuru = "N";
		for (KontrolDTO k : oncekiKontrollerList) {
			if (k.getKontrolTuru().equals("N")) {
				long oncekiKontrolGecenGunSayisi = DateUtils
						.differenceBetweenTwoDates(k.getKontrolBitisTarihi(),
								new Date());
				kontrolTuru = oncekiKontrolGecenGunSayisi < 330 ? "E" : "N";
				break;
			}
		}
		return kontrolTuru;
	}

	public void testSorulariListener() {
		getTestAnaSoru(cihaz.getTip());

	}

	public CihazDTO getCihaz() {
		return cihaz;
	}

	public void setCihaz(CihazDTO cihaz) {
		this.cihaz = cihaz;
	}

	public List<AsansorKapsamDTO> getKapsamTurleri() {
		return kapsamTurleri;
	}

	public void setKapsamTurleri(List<AsansorKapsamDTO> kapsamTurleri) {
		this.kapsamTurleri = kapsamTurleri;
	}

	public void asansorTurListener(AjaxBehaviorEvent event) {
		soruListSmall=new ArrayList<AnaSoru>();
		soruModel=new AnaSoruDataModel(soruListSmall);
		List<AsansorKapsamDTO> l = new ArrayList<AsansorKapsamDTO>();
		kapsamTurleri = new ArrayList<AsansorKapsamDTO>();
		UIInput ui = (UIInput) event.getComponent();
		if (ui!=null && ui.getValue()!=null){
			String val=ui.getValue().toString();
			l = ((SessionBean) Common.findBean("sessionBean"))
					.getKapsamTurleri();
			for (AsansorKapsamDTO a : l) {
				if (Integer.parseInt(val.trim()) == a.getCihazTipi()) {
					kapsamTurleri.add(a);
				}
			}

			
		}
		
		
	}
	
	public void bakanlikSoruListener(AjaxBehaviorEvent event) {
		soruListSmall=new ArrayList<AnaSoru>();
		soruModel=new AnaSoruDataModel(soruListSmall);
		List<AsansorKapsamDTO> l = new ArrayList<AsansorKapsamDTO>();
		if (cihaz==null || cihaz.getTip()<=0){
		kapsamTurleri = new ArrayList<AsansorKapsamDTO>();
		}
		
		
		
		
	}

	public void kapsamListener(AjaxBehaviorEvent event) {
		UIInput i = (UIInput) event.getComponent();
		if (i != null && i.getValue() != null
				&& !i.getValue().toString().equals("") && cihaz.getTip() > 0) {
			// validOpenDialog=1;
		} else {// validOpenDialog=0;
		}

		// cVisible=false;
		// soruListSmall=new ArrayList<AnaSoru>();
		// soruModel=new AnaSoruDataModel(soruListSmall);
	}

	public void getAltSorular1Listener(SoruDTO s) {
		if (s != null) {
			altSorularList = new ArrayList<SoruDTO>();
			try {
				altSorularList = new SoruListBusiness().testAltSoruGetir(
						s.getSoruId(), s.getCihazTipi());
				altSorularModel = new SoruDataModel(new ArrayList<SoruDTO>());
				if (altSorularList.size() > 0) {
					altSoruHeader = s.getSoru();
					altSorularModel = new SoruDataModel(altSorularList);
					RequestContext rc = RequestContext.getCurrentInstance();
					rc.execute("PF('daltsorular').show()");
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(Messages._ALTSORUBULUNAMADI_
									.getMesaj()));
				}
			} catch (CRUDException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			}

		}

	}

	public String getAltSoruHeader() {
		return altSoruHeader;
	}

	public void setAltSoruHeader(String altSoruHeader) {
		this.altSoruHeader = altSoruHeader;
	}

	public SoruDataModel getAltSorularModel() {
		return altSorularModel;
	}

	public void setAltSorularModel(SoruDataModel altSorularModel) {
		this.altSorularModel = altSorularModel;
	}

	public AnaSoruDataModel getSoruModel() {
		return soruModel;
	}

	public void setSoruModel(AnaSoruDataModel soruModel) {
		this.soruModel = soruModel;
	}

	public List<SoruDTO> getAltSorularList() {
		return altSorularList;
	}

	public void setAltSorularList(List<SoruDTO> altSorularList) {
		this.altSorularList = altSorularList;
	}

	public void onRowEdit(RowEditEvent event) {
		SoruDTO s = (SoruDTO) event.getObject();
		try {
			new SoruListBusiness().testSorusuGuncelle(s);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							Messages._SQL_513_.getMesaj(), null));
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage("Hata",
					new FacesMessage(e.getMessage()));
		}
	}

	public void onRowEditOnTanimli(RowEditEvent event) {
		SoruOnTanimliDTO s = (SoruOnTanimliDTO) event.getObject();
		try {
			new SoruListBusiness().onTanimliGuncelle(s);
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage("Hata",
					new FacesMessage(e.getMessage()));
		}
	}

	public void onRowEditAnaSoru(RowEditEvent event) {
		AnaSoru soru = (AnaSoru) event.getObject();
		if (soru != null) {
			SoruDTO s = new SoruDTO();
			s.setAktif(soru.getAktif());
			s.setCihaz_Tipi(soru.getCihazTipi());
			s.setCihazTipi(soru.getCihazTipi());
			s.setDurum(soru.getAktif());
			s.setSiraNo(soru.getSiraNo());
			s.setSoruId(soru.getSoruId());
			s.setSoru_Id(soru.getSoruId());
			s.setYildiz(soru.getYildiz());
			s.setSoru(soru.getSoru());
			s.setKapsam(soru.getKapsam());
			s.setKapsamArr(soru.getKapsamArr());

			try {
				new SoruListBusiness().testSorusuGuncelle(s);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								Messages._SQL_513_.getMesaj(), null));
			} catch (CRUDException e) {
				FacesContext.getCurrentInstance().addMessage("Hata",
						new FacesMessage(e.getMessage()));
			}
		}
	}

	public void onRowCancel(RowEditEvent event) {
	}

	public SoruDTO getSoruEkleDTO() {
		return soruEkleDTO;
	}

	public void setSoruEkleDTO(SoruDTO soruEkleDTO) {
		this.soruEkleDTO = soruEkleDTO;
	}

	public void setAltSorular1Listener(SoruDTO s) {
		FacesContext fc = FacesContext.getCurrentInstance();
		soruEkleDTO = new SoruDTO();
		soruEkleDTO.setCihazTipi(s.getCihazTipi());
		soruEkleDTO.setCihaz_Tipi(s.getCihazTipi());

		soruEkleDTO.setParent(s.getSoruId());
		altSoruParent=s.getSoruId();
		try {
			Date tarih=new Date();
			
			tarih=bakanlikSoru?sessionBean.getBakanlikSoru().getTarih():null;
			
			soruEkleDTO.setSiraNo(new SoruListBusiness().getMaxSiraNo(
					s.getCihazTipi(), s.getSoruId(),tarih));
			
			
		} catch (CRUDException e) {
			RequestContext.getCurrentInstance().execute(
					"PF('altsoruekle').show()");
			fc.addMessage("Hata", new FacesMessage(e.getMessage()));

		}
	}

	public void setAltSorularListener(AnaSoru s) {
		FacesContext fc = FacesContext.getCurrentInstance();
		soruEkleDTO = new SoruDTO();
		soruEkleDTO.setCihazTipi(s.getCihazTipi());
		soruEkleDTO.setCihaz_Tipi(s.getCihazTipi());
		soruEkleDTO.setKapsam(s.getKapsam());
		soruEkleDTO.setKapsamArr(s.getKapsamArr());
		soruEkleDTO.setParent(s.getSoruId());
		altSoruParent=s.getSoruId();
		dialogHeader = "Alt Soru Ekle";
		try {
			Date tarih=new Date();
			tarih=bakanlikSoru?sessionBean.getBakanlikSoru().getTarih():null;
			
				
			
			soruEkleDTO.setSiraNo(new SoruListBusiness().getMaxSiraNo(
					s.getCihazTipi(), s.getSoruId(),tarih));
			
			
			
		} catch (CRUDException e) {
			RequestContext.getCurrentInstance().execute(
					"PF('altsoruekle').show()");
			fc.addMessage("Hata", new FacesMessage(e.getMessage()));

		}
	}

	public void setAnaSoruListener() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if (cihaz.getTip()>0){
		soruEkleDTO = new SoruDTO();
		soruEkleDTO.setCihazTipi(cihaz.getTip());
		soruEkleDTO.setCihaz_Tipi(cihaz.getTip());

		soruEkleDTO.setParent(null);

		dialogHeader = "Ana Soru Ekle";
	
		try {
			Date tarih=new Date();
			
			tarih=bakanlikSoru?sessionBean.getBakanlikSoru().getTarih():null;
			
			soruEkleDTO.setSiraNo(new SoruListBusiness().getMaxSiraNo(
					cihaz.getTip(), null,tarih));
			RequestContext.getCurrentInstance().execute(
					"PF('altsoruekle').show()");

		} catch (CRUDException e) {
			
			fc.addMessage("Hata", new FacesMessage(e.getMessage()));

		}
		}
	}

	public void setOnTanimliSorular(SoruDTO s) {
		FacesContext fc = FacesContext.getCurrentInstance();

		dialogHeader = s.getSoru();
		try {
			onTanimliSoruId = 0;
			onTanimliDTO.setSoruId(s.getSoruId());
			onTanimliSoruId = s.getSoruId();
			onTanimliList = new ArrayList<SoruOnTanimliDTO>();
			onTanimliList = new SoruListBusiness().getOnTanimliSorular(s
					.getSoruId());

		} catch (CRUDException e) {
			RequestContext.getCurrentInstance().execute(
					"PF('ontanimlisoruekle').show()");
			fc.addMessage("Hata", new FacesMessage(e.getMessage()));
		}
	}

	public void soruKaydet() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if (soruEkleDTO.getSoru() == null
				|| soruEkleDTO.getSoru().trim().length() <= 1) {
			
			fc.addMessage(null, new FacesMessage("Soru Giriniz !"));

		} else {
			try {
			DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			
		
				soruEkleDTO.setTarih(bakanlikSoru?sessionBean.getBakanlikSoru().getTarih():null);
			
		
				new SoruListBusiness().soruEkle(soruEkleDTO);
				
				
				getTestAnaSoru(cihaz.getTip());
			
				
				
				soruEkleDTO.setSiraNo(new SoruListBusiness().getMaxSiraNo(
						cihaz.getTip(),(soruEkleDTO.getParent()==null?null:altSoruParent),soruEkleDTO.getTarih()));
				soruEkleDTO.setSoru("");
				soruEkleDTO.setAktif("A");
				soruEkleDTO.setYildiz("");
				fc.addMessage(null,
						new FacesMessage(Messages._SQL_513_.getMesaj()));
			} catch (CRUDException e) {
				RequestContext.getCurrentInstance().execute(
						"PF('altsoruekle').show()");
				fc.addMessage("Hata", new FacesMessage(e.getMessage()));

			} 
		}
	}

	public void onTanimliKaydet() {
		FacesContext fc = FacesContext.getCurrentInstance();

		try {
			/*
			 * try{ onTanimliDTO.setSoruId(onTanimliList.get(0).getSoruId());
			 * }catch(NullPointerException e){ onTanimliDTO.setSoruId(1); }catch
			 * (IndexOutOfBoundsException e) { onTanimliDTO.setSoruId(1); }
			 */
			onTanimliDTO.setSoruId(onTanimliSoruId);
			int id = new SoruListBusiness().onTanimliEkle(onTanimliDTO);
			onTanimliDTO.setId(id);
			onTanimliList.add(onTanimliDTO);
			onTanimliDTO = new SoruOnTanimliDTO();
			fc.addMessage(null, new FacesMessage(Messages._SQL_513_.getMesaj()));

		} catch (CRUDException e) {
			RequestContext.getCurrentInstance().execute(
					"PF('ontanimlisoruekle').show()");
			fc.addMessage("Hata", new FacesMessage(e.getMessage()));

		}
	}

	public boolean iscVisible() {
		return cVisible;
	}

	public void setcVisible(boolean cVisible) {
		this.cVisible = cVisible;
	}

	public String getDialogHeader() {
		return dialogHeader;
	}

	public void setDialogHeader(String dialogHeader) {
		this.dialogHeader = dialogHeader;
	}

	public void hide(CloseEvent event) {
		onTanimliList = new ArrayList<SoruOnTanimliDTO>();
		dialogHeader = "";
	}

	public SoruOnTanimliDTO getOnTanimliDTO() {
		return onTanimliDTO;
	}

	public void setOnTanimliDTO(SoruOnTanimliDTO onTanimliDTO) {
		this.onTanimliDTO = onTanimliDTO;
	}

	public void dListener(AjaxBehaviorEvent event) {
		UIInput ui=(UIInput)event.getComponent();
		String clientId=ui.getClientId();
		
		String[] strArr=	 clientId.split(":");
		
		String[] sArr =new String[strArr.length];
		int i=0;
		for (String s:strArr){
			try {
				int i1=Integer.parseInt(s);
				sArr[i]=s;
				i++;
			} catch (NumberFormatException e) {
				
			}
		}
		if (sArr!=null){
			soruliste:
			for (int a=0;a<soruListSmall.size();a++) {
				if (a==Integer.parseInt(sArr[0])){
					for (SoruDTO s:soruListSmall.get(a).getAltSorular()){
						s.setDurum(soruListSmall.get(a).getDurum());
						if (s.getDurum()==null || !s.getDurum().equals("Uygun Değil")){
							s.setSelectedListId(null);
							s.setSlctdList(new ArrayList<SoruOnTanimliDTO>());
							s.setCheck(false);
						}
					}
					
					break soruliste;
					
				}
				

			}
		}
		

	}
	

	public void durumListener(AjaxBehaviorEvent event){
		UIInput ui=(UIInput)event.getComponent();
		String clientId=ui.getClientId();
		
		String[] strArr=	 clientId.split(":");
		
		String[] sArr =new String[strArr.length];
		int i=0;
		for (String s:strArr){
			try {
				int i1=Integer.parseInt(s);
				sArr[i]=s;
				i++;
			} catch (NumberFormatException e) {
				
			}
		}
		if (sArr!=null){
			soruliste:
			for (int a=0;a<soruListSmall.size();a++) {
				if (a==Integer.parseInt(sArr[0])){
					for (int b=0;b<soruListSmall.get(a).getAltSorular().size();b++){
						if (b==Integer.parseInt(sArr[1])){
						SoruDTO s=soruListSmall.get(a).getAltSorular().get(b);
						if (s.getDurum()==null || !s.getDurum().equals("Uygun Değil")){
						s.setSelectedListId(null);
						s.setSlctdList(new ArrayList<SoruOnTanimliDTO>());
						s.setCheck(false);
						
						}
						break soruliste;
						}
						
					}
				}
				

			}
		}
		
		if (activeIndex1.trim().length()>0){
			activeIndex1=activeIndex1+","+sArr[1];
		}else{
		activeIndex1=sArr[1];
		}
	}
	
	
	public void durum1Listener(AjaxBehaviorEvent event){
		UIInput ui=(UIInput)event.getComponent();
		String clientId=ui.getClientId();
		
		String[] strArr=	 clientId.split(":");
		
		String[] sArr =new String[strArr.length];
		int i=0;
		for (String s:strArr){
			try {
				int i1=Integer.parseInt(s);
				sArr[i]=s;
				i++;
			} catch (NumberFormatException e) {
				
			}
		}
		if (sArr!=null){
			soruliste:
			for (int a=0;a<soruListSmall.size();a++) {
				if (a==Integer.parseInt(sArr[0])){
					for (int b=0;b<soruListSmall.get(a).getOptionalAltSorular().size();b++){
						if (b==Integer.parseInt(sArr[1])){
						SoruDTO s=soruListSmall.get(a).getOptionalAltSorular().get(b);
						if (s.getDurum()==null || !s.getDurum().equals("Uygun Değil")){
						s.setSelectedListId(null);
						s.setSlctdList(new ArrayList<SoruOnTanimliDTO>());
						s.setCheck(false);
						
						}
						break soruliste;
						}
						
					}
				}
				

			}
		}
		
		if (activeIndex2.trim().length()>0){
			activeIndex2=activeIndex2+","+sArr[1];
		}else{
		activeIndex2=sArr[1];
		}
	}
	
	public void durum0Listener(AjaxBehaviorEvent event){
		UIInput ui=(UIInput)event.getComponent();
		String clientId=ui.getClientId();
		
		String[] strArr=	 clientId.split(":");
		
		String[] sArr =new String[strArr.length];
		int i=0;
		for (String s:strArr){
			try {
				int i1=Integer.parseInt(s);
				sArr[i]=s;
				i++;
			} catch (NumberFormatException e) {
				
			}
		}
		if (sArr!=null){
			soruliste0:
			for (int a=0;a<optionalSoruListSmall.size();a++) {
				if (a==Integer.parseInt(sArr[0])){
					for (int b=0;b<optionalSoruListSmall.get(a).getAltSorular().size();b++){
						if (b==Integer.parseInt(sArr[1])){
						SoruDTO s=optionalSoruListSmall.get(a).getAltSorular().get(b);
						if (s.getDurum()==null || !s.getDurum().equals("Uygun Değil")){
						s.setSelectedListId(null);
						s.setSlctdList(new ArrayList<SoruOnTanimliDTO>());
						s.setCheck(false);
						
						
						}
						break soruliste0;
						}
						
					}
				}
				

			}
		}
		if (activeIndex3.trim().length()>0){
			activeIndex3=activeIndex3+","+sArr[1];
		}else{
		activeIndex3=sArr[1];
		}
	}

	public void dOpListener(AjaxBehaviorEvent event) {
		try{
			String str=
					((UIInput) (event.getComponent())).getValue().toString();
							if (str!=null){
								for (AnaSoru a : optionalSoruListSmall) {
									for (SoruDTO s:a.getAltSorular()){
									
										s.setDurum(a.getDurum());
										if (a.getDurum()==null || !a.getDurum().equals("Uygun Değil")){
										s.setSelectedListId(null);
										s.setSlctdList(new ArrayList<SoruOnTanimliDTO>());
										s.setCheck(false);
										}
										
									}

								}
							}
						
		}catch
		(Exception e){
			
		}

	}

	public void genelDurumListener(AjaxBehaviorEvent event) {
		/*
		AccordionPanel acrd = (AccordionPanel) FacesContext
				.getCurrentInstance().getViewRoot()
				.findComponent(":formbina:accrdnsorular");

		acrd.setMultiple(false);
		*/
		for (AnaSoru a : soruListSmall) {
			for (SoruDTO s:a.getAltSorular()){
				s.setDurum(genelDurum);
				if (genelDurum==null || !genelDurum.equals("Uygun Değil")){
					s.setSelectedListId(null);
					s.setSlctdList(new ArrayList<SoruOnTanimliDTO>());
					s.setCheck(false);
					

			}
			}
			
				
					
		
			/*
			if (!genelDurum.equals("B")) {
				a.setChecked(true);
			} else {
				a.setChecked(false);

			}
			*/
			a.setDurum(genelDurum);
		}
		/*
		acrd.setMultiple(true);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < soruListSmall.size(); i++) {
			if (i == 0)
				sb.append(i);
			else
				sb.append("," + i);
		}
		acrd.setActiveIndex(sb.toString());
		*/

	}

	public String getGenelDurum() {
		return genelDurum;
	}

	public void setGenelDurum(String genelDurum) {
		this.genelDurum = genelDurum;
	}

	public void setRevizyonRapor(AjaxBehaviorEvent event) {

		boolean b = false;
		if (isRevizyonRaporMu()) {
			for (KontrolDTO k : oncekiKontrollerList) {
				if (kontrolDTO.getRandevuId() == k.getRandevuId()
						&& k.isRevizyonRapor()) {
					revizyonRaporMu = false;

					FacesMessage fm = new FacesMessage(
							"Bu rapor için düzenlenmiş bir revizyon rapor bulunmaktadır.Değişikliği revizyon rapor üzerinde yapınız!");
					fm.setSeverity(FacesMessage.SEVERITY_INFO);
					FacesContext.getCurrentInstance().addMessage(null, fm);
					b = true;
					RequestContext
							.getCurrentInstance()
							.execute(
									"alert('Bu rapor için düzenlenmiş bir revizyon rapor bulunmaktadır.Değişikliği revizyon rapor üzerinde yapınız!')");
					break;
				}
				if (!b) {
					if (kontrolDTO.getOnayDurum()==null ||   !kontrolDTO.getOnayDurum().equals("O")) {

						revizyonRaporMu = false;
						kontrolDTO.setRevizyonRapor(revizyonRaporMu);

						FacesMessage fm = new FacesMessage(
								"Onaylanmamış rapor üzerinde değişiklik yapabilirsiniz.Revizyon rapor alamazsınız!");
						fm.setSeverity(FacesMessage.SEVERITY_INFO);
						FacesContext.getCurrentInstance().addMessage(null, fm);
						b = true;
						RequestContext
								.getCurrentInstance()
								.execute(
										"alert('Onaylanmamış rapor üzerinde değişiklik yapabilirsiniz.Revizyon rapor alamazsınız!')");
					}
				}
			}
		} else {
			if (kontrolDTO.isRevizyonRapor()) {
				revizyonRaporMu = true;
				FacesMessage fm = new FacesMessage(
						"Bu rapor  revizyon rapor olarak düzenlenmiştir!");
				fm.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext.getCurrentInstance().addMessage(null, fm);
				b = true;
				RequestContext
						.getCurrentInstance()
						.execute(
								"alert('Bu rapor  revizyon rapor olarak düzenlenmiştir!')");
			}
		}
		CommandButton btn = (CommandButton) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(":formbina:testkaydet");
		if (!b) {

			if (isRevizyonRaporMu()) {

				if (btn != null) {
					btn.setDisabled(false);
				}
			} else {
				if (btn != null) {
					btn.setDisabled(true);
				}
			}
		}

	}

	public KontrolDTO getKontrolDTO() {
		return kontrolDTO;
	}

	public void setKontrolDTO(KontrolDTO kontrolDTO) {
		this.kontrolDTO = kontrolDTO;
	}

	public void kontrolSorulariListener(ActionEvent event) {
		soruListesiRender=true;
	}

	public boolean isRevizyonRaporMu() {
		return revizyonRaporMu;
	}

	public void setRevizyonRaporMu(boolean revizyonRaporMu) {
		this.revizyonRaporMu = revizyonRaporMu;
	}

	public String getOncekiKontrolMuhendisi() {
		return oncekiKontrolMuhendisi;
	}

	public void setOncekiKontrolMuhendisi(String oncekiKontrolMuhendisi) {
		this.oncekiKontrolMuhendisi = oncekiKontrolMuhendisi;
	}

	public List<AnaSoru> getOptionalSoruListSmall() {
		return optionalSoruListSmall;
	}

	public void setOptionalSoruListSmall(List<AnaSoru> optionalSoruListSmall) {
		this.optionalSoruListSmall = optionalSoruListSmall;
	}

	private SoruDTO setKapsam(SoruDTO s) {
		try {
			Integer[] i = (Integer[]) s.getKapsamArr().getArray();
			s.setKapsam(new short[i.length]);
			int j = 0;

			for (int i1 : i) {
				s.getKapsam()[j] = (short) i1;
				j++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	private AnaSoru setKapsam(AnaSoru s) {

		try {
			Integer[] i = (Integer[]) s.getKapsamArr().getArray();
			s.setKapsam(new short[i.length]);
			int j = 0;

			for (int i1 : i) {
				s.getKapsam()[j] = (short) i1;
				j++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	public boolean isKontrolYanitlariTasinsin() {
		return kontrolYanitlariTasinsin;
	}

	public void setKontrolYanitlariTasinsin(boolean kontrolYanitlariTasinsin) {
		this.kontrolYanitlariTasinsin = kontrolYanitlariTasinsin;
	}

	public void kontrolTasiListener(AjaxBehaviorEvent event) {
		
		
	

		boolean kullaniciRaporuMu = false;
		for (RandevuMuhendisDTO r : kontrolDTO.getKontrolMuhendisleri()) {
			try {
				if (Integer.parseInt(loginBean.getKullanici().getSicilNo()
						.trim()) == r.getSicilNo()) {
					kullaniciRaporuMu = true;
				}
			} catch (Exception e) {
				kullaniciRaporuMu = false;
			}
		}
		if (kontrolYanitlariTasinsin) {
			try {
				if (randevuyaAitKontrol.getKontrolId() > 0) {

					if (randevuyaAitKontrol.getOnayDurum().equals("O")) {
						RequestContext.getCurrentInstance().execute(
								"alert('Onaylı Rapor Değiştiremezsiniz!')");

						kontrolYanitlariTasinsin = false;
					} else {
						kontrolDTO = (KontrolDTO) randevuyaAitKontrol.clone();
						kontrolDTO.setBina(binaBean.getBinaDTO());
						kontrolDTO.setCihazId(secilen.getCihazId());
						kaydetButtonTasiRender=true;

					}
				} else {
					try {
						//kontrolDTO = new KontrolDTO();
						kontrolDTO.setBina(binaBean.getBinaDTO());
						kontrolDTO.setCihazId(secilen.getCihazId());
						kontrolDTO.setRandevuId(binaBean.getRandevuId());
						kaydetButtonTasiRender=true;
					} catch (Exception e) {
						throw new CRUDException(Messages._SQL_514_.getMesaj(),
								null);
					}

				}

			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								Messages._SQL_514_.getMesaj(), null));
			}

		}

		if (!kullaniciRaporuMu) {
			
				if (kontrolYanitlariTasinsin) {
					kaydetButtonRender=true;
				kaydetButtonTasiRender=true;
				}
					else
						kaydetButtonRender=false;
						
		
		}
		
		if (kontrolTasiRender && kontrolYanitlariTasinsin){
			kaydetButtonRender=true;
			kaydetButtonTasiRender=true;
		}else{
			kaydetButtonRender=false;
		}
		

	}

	private void setKontrol(KontrolDTO kd) {



		RequestContext rc = RequestContext.getCurrentInstance();
		boolean kullaniciRaporuMu = false;
		kontrolYanitlariTasinsinDisable=false;
		kaydetButtonTasiRender=true;
		for (RandevuMuhendisDTO r : kd.getKontrolMuhendisleri()) {
			try {
				if (Integer.parseInt(loginBean.getKullanici().getSicilNo()
						.trim()) == r.getSicilNo()) {
					kullaniciRaporuMu = true;
					break;
				}
			} catch (Exception e) {
				kullaniciRaporuMu = false;
			}

			if (r.getSicilNo() == kd.getKontrolMuhendisiSicilNo()) {
				oncekiKontrolMuhendisi = r.getAdiSoyadi();
				break;
			}
		}

		if (!kullaniciRaporuMu) {
			
			kaydetButtonRender=false;
			RequestContext
					.getCurrentInstance()
					.execute(
							"alert('Cihazın bu kontrolü size ait değil.Değişiklik yapamazsınız !')");

		}

		for (RandevuMuhendisDTO r : kd.getKontrolMuhendisleri()) {
			if (r.getSicilNo() == kd.getKontrolMuhendisiSicilNo()) {
				oncekiKontrolMuhendisi = r.getAdiSoyadi();
				break;
			}
		}
		for (Kullanici k : loginBean.getOnayciList()) {
			if (kd.getOnaylayanSicilNo() == Integer.parseInt(k.getSicilNo())) {
				oncekiKontrolOnaylayanKisi = k.getAdi() + " " + k.getSoyadi();
				break;
			}
		}
		try {
			this.revizyonRaporMu = kd.isRevizyonRapor();
			//this.kontrolDTO = (KontrolDTO) kd.clone();
			if (kd.getRandevuId()!=binaBean.getRandevuId()){
				kontrolTasiRender=true;
				if (kullaniciRaporuMu){
					kaydetButtonRender=false;
				}
			}
			else{
				kontrolTasiRender=false;}
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		rc.execute("PF('oncekikontrol').show()");
		if (kd.getOnayDurum().equals("O")) {
			if (kd.isRevizyonRapor()) {
				RequestContext.getCurrentInstance().execute(
						"alert('Seçilen Revizyon  ve  Onaylı Rapordur.')");
			} else
				RequestContext.getCurrentInstance().execute(
						"alert('Seçilen Rapor Onaylı Rapordur.')");
		}
		if ((this.kontrolDTO!=null && kd.getKontrolId()!=this.kontrolDTO.getKontrolId()) || this.kontrolDTO==null){
			kaydetButtonTasiRender=false;
		}
	
		boolean bool1=false;
		boolean bool2=false;
		
			
		try{
		bool1=kd.getKontrolBaslangicTarihi().compareTo(sessionBean.getBakanlikSoru().getTarih())>=0?true:false;
		}catch (Exception e) {
			bool1=false;
			}
			if (this.kontrolDTO.getKontrolBaslangicTarihi()!=null)
			bool2=this.kontrolDTO.getKontrolBaslangicTarihi().compareTo(sessionBean.getBakanlikSoru().getTarih())>=0?true:false;
			else
				bool2=Calendar.getInstance().getTime().compareTo(sessionBean.getBakanlikSoru().getTarih())>=0?true:false;	
				
			
		
		if (bool1!=bool2){
			kontrolYanitlariTasinsinDisable=true;
			RequestContext.getCurrentInstance().execute(
					"alert('Randevuya ait kontrol test soruları seçmiş olduğunuz kontrol test sorularından farklıdır.Test yanıtları taşınamaz !')");
			
		}
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public void firmaKontroleKatildiListener(AjaxBehaviorEvent event) {
		if (!this.kontrolDTO.getKontrolFirmaKatilimDTO()
				.isFirmaKontroleKatildi()) {
			this.kontrolDTO.getKontrolFirmaKatilimDTO().setFirmaGorevli1("");
			this.kontrolDTO.getKontrolFirmaKatilimDTO().setFirmaGorevli2("");
			this.kontrolDTO.getKontrolFirmaKatilimDTO().setFirmaGorevli3("");
			this.kontrolDTO.getKontrolFirmaKatilimDTO().setFirmaGorevli1Gorev("");
			this.kontrolDTO.getKontrolFirmaKatilimDTO().setFirmaGorevli2Gorev("");
			this.kontrolDTO.getKontrolFirmaKatilimDTO().setFirmaGorevli3Gorev("");
			
		}
	}
	
	public void tabChangeListener(TabChangeEvent event){
		 
if (event.getTab().getId().equals("tab03")){
	rrender=true;
}else{
	rrender=false;
}
		if (secilen==null || secilen.getCihazId()<=0){
			if (this.cihazBean!=null){
				this.cihazBean.setCihazTeknik(null);
			}
		}
		cihazBean.setBakimciFirmalar(new ArrayList<FirmaDTO>());
        cihazBean.setFirmaModel(new FirmaDataModel(cihazBean.getBakimciFirmalar()));
        cihazBean.setCihazFirma(new CihazFirmaDTO());
		cihazBean.setSozlesmeGiris(false);
		binaBean.setFirmaList(new ArrayList<FirmaDTO>());
		bakimFirmaDegButton=false;
		
	}

	public CihazBean getCihazBean() {
		return cihazBean;
	}

	public void setCihazBean(CihazBean cihazBean) {
		this.cihazBean = cihazBean;
	}
	
	public void binaSelect(ActionEvent event){
		soruListesiRender=false;
	if (binaBean!=null){
		binaBean.setIlceler(new TreeMap<String, Integer>());
		binaBean.setGenelAraBina(new BinaDTO());
		binaBean.setGenelBinalar(new ArrayList<BinaDTO>());
		soruListSmall=new ArrayList<AnaSoru>();
		optionalSoruListSmall=new ArrayList<AnaSoru>();
		onTanimliList=new ArrayList<SoruOnTanimliDTO>();
		secilen=new CihazDTO();
		cihazlar=new ArrayList<CihazDTO>();
		kontrolDTO=new KontrolDTO();
		cihazList=new CihazDataModel(new ArrayList<CihazDTO>());
		
		
	}
	
	    
	}
	
	public void randevuSelectListener(SelectEvent event) {

		try {

			String id = String.valueOf(((RandevuListeDTO) event.getObject())
					.getRandevuId());

			if (id != null) {

				this.binaBean.setRandevuId(Integer.parseInt(id));
				this.binaBean.setRandevuStr(id);
				cihazlar=new ArrayList<CihazDTO>();
				cihazList=new CihazDataModel(cihazlar);
				soruListSmall=new ArrayList<AnaSoru>();
				kontrolDTO=new KontrolDTO();
				secilen=new CihazDTO();
				altSorularModel=new SoruDataModel(new ArrayList<SoruDTO>());
				onTanimliList=new ArrayList<SoruOnTanimliDTO>();
				onTanimliModel=new SoruOnTanimliModel(onTanimliList);
				optionalSoruListSmall=new ArrayList<AnaSoru>();
				
				if (new RandevuBusiness().isRandevuMuhendis(this.binaBean.getRandevuId())) {
					
						
						try {
							cihazlar=new AsansorBusiness()
							.getKontrolEdilecekCihazlar(
									binaBean.getBinaDTO().getBinaId(), binaBean.getRandevuId());
							
						} catch (ReadException e) {
							cihazlar=new ArrayList<CihazDTO>();
						}
					cihazList=new CihazDataModel(cihazlar);
						
				
					
				} else {
					
				
					
					FacesContext.getCurrentInstance()
							.addMessage(
									null,
									new FacesMessage(
											FacesMessage.SEVERITY_INFO,
											Messages._RANDEVUSIZINDEGIL_
													.getMesaj(), ""));
				}
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}

	}

	
	public void check1(SoruDTO soru){
		
		
	

		
		StringBuffer sb = new StringBuffer();

		int i=0;
		
		for (AnaSoru a:soruListSmall){
			i=0;
			
			for (SoruDTO s:a.getAltSorular()){
			
				if (soru.getSoruId()==s.getSoruId()){
					
					if (i == 0)
						sb.append(i);
					else
						sb.append("," + i);
					
					if (soru.getSelectedListId().length>0){
					s.setCheck(true);
					}else{
						s.setCheck(false);
					}
				
					break;
				}
				i++;
				
			}
			
		}
		activeIndex1=sb.toString();
	}
	
	
	public void check3(SoruDTO soru){
		StringBuffer sb = new StringBuffer();

		int i=0;
		for (AnaSoru a:soruListSmall){
			i=0;
			for (SoruDTO s:a.getOptionalAltSorular()){
				
				if (soru.getSoruId()==s.getSoruId()){
					if (i == 0)
						sb.append(i);
					else
						sb.append("," + i);
					if (soru.getSelectedListId().length>0){
					s.setCheck(true);
					}else{
						s.setCheck(false);
					}
					
					break;
				}
				i++;
			}
		}
		activeIndex2=sb.toString();
	}
	public void check2(SoruDTO soru){
		StringBuffer sb = new StringBuffer();

		int i=0;
		for (AnaSoru a:optionalSoruListSmall){
			i=0;
			for (SoruDTO s:a.getAltSorular()){
			
				if (soru.getSoruId()==s.getSoruId()){
					if (i == 0)
						sb.append(i);
					else
						sb.append("," + i);
					if (soru.getSelectedListId().length>0){
					s.setCheck(true);
					}else{
						s.setCheck(false);
					}
					break;
				}
				i++;
			}
		}
		activeIndex3=sb.toString();
	}

	public boolean isRrender() {
		return rrender;
	}

	public void setRrender(boolean rrender) {
		this.rrender = rrender;
	}

	public String getActiveIndex1() {
		return activeIndex1;
	}

	public void setActiveIndex1(String activeIndex1) {
		this.activeIndex1 = activeIndex1;
	}

	public String getActiveIndex2() {
		return activeIndex2;
	}

	public void setActiveIndex2(String activeIndex2) {
		this.activeIndex2 = activeIndex2;
	}

	public String getActiveIndex3() {
		return activeIndex3;
	}

	public void setActiveIndex3(String activeIndex3) {
		this.activeIndex3 = activeIndex3;
	}

	public boolean isKontrolTasiRender() {
		return kontrolTasiRender;
	}

	public void setKontrolTasiRender(boolean kontrolTasiRender) {
		this.kontrolTasiRender = kontrolTasiRender;
	}

	public boolean isKaydetButtonRender() {
		return kaydetButtonRender;
	}

	public void setKaydetButtonRender(boolean kaydetButtonRender) {
		this.kaydetButtonRender = kaydetButtonRender;
	}

	public boolean isBakanlikSoru() {
		return bakanlikSoru;
	}

	public void setBakanlikSoru(boolean bakanlikSoru) {
		this.bakanlikSoru = bakanlikSoru;
	}
	
	 public void altSoruEkleClose(CloseEvent event) {
	        soruEkleDTO=new SoruDTO();
	        altSoruParent=0;
	        UIInput ui=(UIInput)FacesContext.getCurrentInstance().getViewRoot().findComponent("formtest:tk");
	        if (ui!=null){
	        	ui.setValue(false);
	        }
	    }

	public boolean isKontrolYanitlariTasinsinDisable() {
		return kontrolYanitlariTasinsinDisable;
	}

	public void setKontrolYanitlariTasinsinDisable(
			boolean kontrolYanitlariTasinsinDisable) {
		this.kontrolYanitlariTasinsinDisable = kontrolYanitlariTasinsinDisable;
	}

	public boolean isKaydetButtonTasiRender() {
		return kaydetButtonTasiRender;
	}

	public void setKaydetButtonTasiRender(boolean kaydetButtonTasiRender) {
		this.kaydetButtonTasiRender = kaydetButtonTasiRender;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}
	 
	public void kapsamSelectListener(AjaxBehaviorEvent event){
		UIInput ui=(UIInput)event.getComponent();
	
		if (ui!=null){
			if (ui.getValue()!= null ){
				if (ui.getValue().toString().equals("true")){
					List<AsansorKapsamDTO> l=new ArrayList<AsansorKapsamDTO>();
					for (AsansorKapsamDTO a: sessionBean.getKapsamTurleri()){
						if (a.getCihazTipi()==cihaz.getTip()){
						l.add(a);
						}
						
					}
					short[] kapsam = new short[l.size()];
					
					int i=0;
					for (AsansorKapsamDTO a: l){
						kapsam[i]=(short)a.getId();
						i++;
					}

					soruEkleDTO.setKapsam(kapsam);
				}else{
					soruEkleDTO.setKapsam(null);
					
				}
			}
		}
		
	}
	
	public void firmaBul(AjaxBehaviorEvent event) {
		UIInput ui=(UIInput)event.getComponent();
		Object o=ui.getValue();
		if (o!=null && o.toString().equalsIgnoreCase("true")){
		FacesMessage msg;
		cihazBean=cihazBean==null?new CihazBean():cihazBean;
		
		cihazBean.setFirmaModel(new FirmaDataModel(new ArrayList<FirmaDTO>()));
		if (this.secilen != null && this.secilen.getCihazId()>0) {
		
			try {
				cihazBean.setBakimciFirmalar( new FirmaBusiness()
				.getAsansorBakimciFirmalar(this.secilen.getCihazId()));
				cihazBean.setFirmaModel(new FirmaDataModel(cihazBean.getBakimciFirmalar()));
				
				cihazBean.setSozlesmeGiris(false);
				binaBean.setFirmaList(new RandevuBean().getFirmalar(this.binaBean.getSessionBina().getIl()));
				RequestContext.getCurrentInstance().execute("PF('dfirma').show()");

			} catch (CRUDException e) {
				msg = new FacesMessage(e.getMessage());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, msg);

			}
		}
		} else{
			   cihazBean.setBakimciFirmalar(new ArrayList<FirmaDTO>());
		        cihazBean.setFirmaModel(new FirmaDataModel(cihazBean.getBakimciFirmalar()));
		        cihazBean.setCihazFirma(new CihazFirmaDTO());
				cihazBean.setSozlesmeGiris(false);
				binaBean.setFirmaList(new ArrayList<FirmaDTO>());
				RequestContext.getCurrentInstance().execute("PF('dfirma').hide()");
				
				
		}

	}
	
	public void handleClose(CloseEvent event) {
        cihazBean.setBakimciFirmalar(new ArrayList<FirmaDTO>());
        cihazBean.setFirmaModel(new FirmaDataModel(cihazBean.getBakimciFirmalar()));
       cihazBean.setCihazFirma(new CihazFirmaDTO());
		cihazBean.setSozlesmeGiris(false);
		binaBean.setFirmaList(new ArrayList<FirmaDTO>());
		bakimFirmaDegButton=false;
    }

	public boolean isBakimFirmaDegButton() {
		return bakimFirmaDegButton;
	}

	public void setBakimFirmaDegButton(boolean bakimFirmaDegButton) {
		this.bakimFirmaDegButton = bakimFirmaDegButton;
	}
	
	
}

