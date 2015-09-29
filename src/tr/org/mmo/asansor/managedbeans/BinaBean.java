package tr.org.mmo.asansor.managedbeans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import tr.org.mmo.asansor.beans_.AnaSoru;
import tr.org.mmo.asansor.beans_.Randevu;
import tr.org.mmo.asansor.beans_.UavtBina;
import tr.org.mmo.asansor.business.AsansorBusiness;
import tr.org.mmo.asansor.business.BelediyeBusiness;
import tr.org.mmo.asansor.business.BinaBusiness;
import tr.org.mmo.asansor.business.FirmaBusiness;
import tr.org.mmo.asansor.business.RandevuBusiness;
import tr.org.mmo.asansor.business.RaporBusiness;
import tr.org.mmo.asansor.business.WebServiceBusiness;
import tr.org.mmo.asansor.dao.ApplicationDAOImpl;
import tr.org.mmo.asansor.dto.BasvuruAsansorDTO;
import tr.org.mmo.asansor.dto.BasvuruListeDTO;
import tr.org.mmo.asansor.dto.BelediyeDTO;
import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.dto.BinaKisiDTO;
import tr.org.mmo.asansor.dto.BinaSorumlulukDTO;
import tr.org.mmo.asansor.dto.BucakKoyDTO;
import tr.org.mmo.asansor.dto.CihazDTO;
import tr.org.mmo.asansor.dto.CihazTeknikDTO;
import tr.org.mmo.asansor.dto.EskiRaporDTO;
import tr.org.mmo.asansor.dto.FirmaDTO;
import tr.org.mmo.asansor.dto.IlIlceDTO;
import tr.org.mmo.asansor.dto.KontrolDTO;
import tr.org.mmo.asansor.dto.KontrolHaberDTO;
import tr.org.mmo.asansor.dto.OdemeAlinmayacakBinalarDTO;
import tr.org.mmo.asansor.dto.OdemeDTO;
import tr.org.mmo.asansor.dto.RandevuListeDTO;
import tr.org.mmo.asansor.dto.SoruDTO;
import tr.org.mmo.asansor.dto.SoruOnTanimliDTO;
import tr.org.mmo.asansor.dto.UavtBelediyeDTO;
import tr.org.mmo.asansor.dto.YapiKonusuDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;
import tr.org.mmo.asansor.models.BasvuruAsansorDataModel;
import tr.org.mmo.asansor.models.BasvuruListeModel;
import tr.org.mmo.asansor.models.BasvuruRandevuModel;
import tr.org.mmo.asansor.models.BinaDataModel;
import tr.org.mmo.asansor.models.BinaKisiDataModel;
import tr.org.mmo.asansor.models.CihazDataModel;
import tr.org.mmo.asansor.models.OdemeDataModel;
import tr.org.mmo.asansor.models.RandevuListeDataModel;
import tr.org.mmo.asansor.models.SoruDataModel;
import tr.org.mmo.asansor.models.SoruOnTanimliModel;
import tr.org.mmo.asansor.models.UavtBinaDataModel;
import tr.org.mmo.asansor.util.BakimciFirmaComparator;
import tr.org.mmo.asansor.util.CaddeSokakComparator;
import tr.org.mmo.asansor.util.IlIlceComparator;
import tr.org.mmo.asansor.util.MahalleComparator;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.Util;
import tr.org.mmo.beans.CaddeSokak;
import tr.org.mmo.beans.Mahalle;
import btest.*;
@ManagedBean
@SessionScoped
public class BinaBean implements Serializable {

	/**
	 * 
	 * 
	 */
	private List<FirmaDTO> firmaList=new ArrayList<FirmaDTO>();
	private BinaDTO sessionBina=new BinaDTO();
	private int tabIndex=4;
	private UavtBina uavtBina;
	private UavtBinaDataModel uavtBinaList = new UavtBinaDataModel(
			new ArrayList<UavtBina>());
	private BinaDataModel binaGenelModel=new BinaDataModel(new ArrayList<BinaDTO>());
	private BinaDTO genelAraBina=new BinaDTO();
	private List<UavtBina> uavtBinaFilter;
	private List<BinaDTO> filteredSearch;
	private boolean searchType;
	private List<EskiRaporDTO> eskiRaporList = new ArrayList<EskiRaporDTO>();
	private RandevuListeDataModel randevuListeModel;
	private RandevuListeDTO randevuListe;
	private List<RandevuListeDTO> filteredRandevuList;
	private int belediyelerSize = 0;
	private boolean yeniBina = false;
	private List<FirmaDTO> bakimciFirmalar = new ArrayList<FirmaDTO>();
	private String mapCenter = "";
	private static final long serialVersionUID = 8055414672631507833L;
	private List<BelediyeDTO> belediyeList = new ArrayList<BelediyeDTO>();
	private BasvuruRandevuModel basvuruVeRandevular;
	private BinaDTO binaDTO = new BinaDTO();
	private BinaDTO secilenBina = new BinaDTO();
	private int binaId;
	private int randevuId;
	private List<BinaSorumlulukDTO> sTurList = new ArrayList<BinaSorumlulukDTO>();
	private List<Randevu> filteredVal;
	private BinaKisiDataModel kisiData;
	private List<BinaKisiDTO> kisiList = new ArrayList<BinaKisiDTO>();
	private BinaDTO secilen = new BinaDTO();
	private List<SelectItem> belediyeler = new ArrayList<SelectItem>();
	private List<BinaDTO> binaList = new ArrayList<BinaDTO>();
	private OdemeAlinmayacakBinalarDTO oabDTO = new OdemeAlinmayacakBinalarDTO();
	private List<OdemeAlinmayacakBinalarDTO> odemeAlinmazList = new ArrayList<OdemeAlinmayacakBinalarDTO>();
	private KontrolHaberDTO kontrolHaberDTO = new KontrolHaberDTO();
	private int tescilno;
	
	@ManagedProperty(value = "#{sessionBean}")
	private SessionBean sessionBean;
	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public KontrolHaberDTO getKontrolHaberDTO() {
		return kontrolHaberDTO;
	}

	public void setKontrolHaberDTO(KontrolHaberDTO kontrolHaberDTO) {
		this.kontrolHaberDTO = kontrolHaberDTO;
	}

	private List<Mahalle> mahalleList = new ArrayList<Mahalle>();
	private List<CaddeSokak> caddeSokakList = new ArrayList<CaddeSokak>();
	private BinaDataModel binaListSmall;

	@SuppressWarnings("unused")
	private boolean kisiEklensin = false;
	private List<YapiKonusuDTO> yapiTipler = new ArrayList<YapiKonusuDTO>();

	@ManagedProperty(value = "#{applicationBean.fileMap}")
	private HashMap<Integer, List<File>> fileMap;

	public List<YapiKonusuDTO> getYapiTipler() {
		return yapiTipler;
	}

	public void setYapiTipler(List<YapiKonusuDTO> yapiTipler) {
		this.yapiTipler = yapiTipler;
	}

	public BinaDataModel getBinaListSmall() {
		return binaListSmall;
	}

	public void setBinaListSmall(BinaDataModel binaListSmall) {
		this.binaListSmall = binaListSmall;
	}

	private List<CihazDTO> cihazList = new ArrayList<CihazDTO>();

	public List<CihazDTO> getCihazList() {
		return cihazList;
	}

	public void setCihazList(List<CihazDTO> cihazList) {
		this.cihazList = cihazList;
	}
	
	
	
	
	@PostConstruct
	public void init() {
		if (this.sessionBina.getBinaId()>0){
			try{
			this.binaDTO=(BinaDTO) sessionBina.clone();
			this.binalar=(BinaDTO) sessionBina.clone();
		} catch (CloneNotSupportedException e) {
			this.binalar=new BinaDTO();
			this.binaDTO=new BinaDTO();
		}
		}
		emptyModel = new DefaultMapModel();
		setMap();
		
		if (yapiTipler == null || yapiTipler.size() <= 0) {
			try {
				yapiTipler = new BinaBusiness().getYapiTipler();
			} catch (CRUDException e) {
				e.printStackTrace();
			}
		}

		if (sTurList == null || sTurList.size() <= 0) {
			try {

				sTurList = new BinaBusiness().getBinaSorumlulukTur();

			} catch (CRUDException e) {
				e.printStackTrace();
			}
		}
		basvuruVeRandevular = new BasvuruRandevuModel(new ArrayList<Randevu>());
		

		
	
		
	}

	public List<BinaSorumlulukDTO> getsTurList() {
		return sTurList;
	}

	public void setsTurList(List<BinaSorumlulukDTO> sTurList) {
		this.sTurList = sTurList;
	}

	public BinaDTO getBinaDTO() {
		return binaDTO;
	}

	public void setBinaDTO(BinaDTO binaDTO) {
		this.binaDTO = binaDTO;
	}

	public int getBinaId() {
		return binaId;
	}

	public void setBinaId(int binaId) {
		this.binaId = binaId;
	}

	public BinaDTO getSecilenBina() {
		return secilenBina;
	}

	public void setSecilenBina(BinaDTO secilenBina) {
		this.secilenBina = secilenBina;
	}

	private boolean visible = false;

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/*
	 * public String getCihazlar(){ visible=true; FacesContext
	 * context=FacesContext.getCurrentInstance(); try {
	 * 
	 * CihazBean cihazBean = (CihazBean)
	 * context.getApplication().evaluateExpressionGet(context, "#{cihazBean}",
	 * CihazBean.class);
	 * 
	 * cihazBean.setCihazlar( new AsansorBusiness().getCihazlar(binaId));
	 * cihazBean.setCihazList(new CihazDataModel(cihazBean.getCihazlar()));
	 * cihazBean.getSecilen().setCihaz(null); cihazBean.submit();
	 * 
	 * } catch (ReadException e) { e.printStackTrace(); }
	 * 
	 * 
	 * return "";
	 * 
	 * }
	 */

	public void binaGetir() {
		try {
			boolean binaVar = false;
			secilenBina = new BinaBusiness().binaBul(binaId);
			if (secilenBina != null) {
				if (secilenBina.getBinaId() > 0) {
					binaVar = true;
					binaSayfasinaGit();

				}

			}
			if (!binaVar) {
				this.binalar = new BinaDTO();
				this.binaDTO = new BinaDTO();
				FacesMessage msg = new FacesMessage(
						Messages._SQL_506_.getMesaj());
				msg.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext.getCurrentInstance().addMessage(null, msg);

			}
		} catch (CRUDException e) {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}
	}

	public void binaBulByIdListener(AjaxBehaviorEvent event) {
		try {
			this.binaId = Integer.parseInt(((UIInput) event.getComponent())
					.getValue().toString());
			CihazBean cihazBean = (CihazBean) FacesContext
					.getCurrentInstance()
					.getApplication()
					.evaluateExpressionGet(FacesContext.getCurrentInstance(),
							"#{cihazBean}", CihazBean.class);
			cihazBean.setCihazTeknik(new ArrayList<CihazTeknikDTO>());
			cihazBean.setVisible(false);
			try {
				cihazBean.setUavtAsansorSiraNo("");
				cihazBean.setCihazUavtKod("");

			} catch (Exception e) {

			}

			CihazDTO cihaz = new CihazDTO();
			cihaz.setDurum("A");
			cihazBean.setCihaz(cihaz);
			binaBulById();
			this.binalar = new BinaDTO();
			this.binalar = (BinaDTO) this.binaDTO.clone();

		} catch (Exception e) {

		}
	}

	public void binaBulByTescilNoListener(AjaxBehaviorEvent event) {
		try {
			this.tescilno = Integer.parseInt(((UIInput) event.getComponent())
					.getValue().toString());
			CihazBean cihazBean = (CihazBean) FacesContext
					.getCurrentInstance()
					.getApplication()
					.evaluateExpressionGet(FacesContext.getCurrentInstance(),
							"#{cihazBean}", CihazBean.class);
			cihazBean.setCihazTeknik(new ArrayList<CihazTeknikDTO>());
			cihazBean.setVisible(false);
			try {
				cihazBean.setUavtAsansorSiraNo("");
				cihazBean.setCihazUavtKod("");

			} catch (Exception e) {

			}

			CihazDTO cihaz = new CihazDTO();
			cihaz.setDurum("A");
			cihazBean.setCihaz(cihaz);

			binaBulByTescilno();
			this.binalar = new BinaDTO();
			this.binalar = (BinaDTO) this.binaDTO.clone();
			cihazBean
					.setBinaIdString(String
							.valueOf(this.binaDTO.getBinaId() == 0 ? ""
									: this.binaDTO.getBinaId()));

		} catch (Exception e) {

		}
	}

	public void binaBulById() {
		try {
			boolean binaVar = false;
			cihazList=new ArrayList<CihazDTO>();
			this.binalar = new BinaDTO();
			this.binaDTO = new BinaDTO();
			secilenBina = new BinaBusiness().binaBul(binaId);
			basvuruClear();
			if (secilenBina != null) {
				if (secilenBina.getBinaId() > 0) {
					binaVar = true;
					getBinaBilgileri(secilenBina);
					this.binalar = secilenBina;

				}

			}
			if (!binaVar) {
				
				FacesMessage msg = new FacesMessage(
						Messages._SQL_506_.getMesaj());
				msg.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		} catch (CRUDException e) {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}
		tabIndex=2;

	}

	public void binaBulByTescilno() {
		try {
			boolean binaVar = false;
			cihazList=new ArrayList<CihazDTO>();
			secilenBina = new BinaBusiness().binaBulTescilno(tescilno);
			if (secilenBina != null) {
				if (secilenBina.getTescilNo() > 0) {
					binaVar = true;
					getBinaBilgileri(secilenBina);
					this.binalar = secilenBina;
				}

			}
			if (!binaVar) {
				this.binalar = new BinaDTO();
				this.binaDTO = new BinaDTO();
				FacesMessage msg = new FacesMessage(
						Messages._SQL_506_.getMesaj());
				msg.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext.getCurrentInstance().addMessage(null, msg);

			}
		} catch (CRUDException e) {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}
		tabIndex=3;

	}

	public String binaGetirByRandevuId() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String view = context.getViewRoot().getViewId();
			
			if ((new RandevuBusiness().isRandevuMuhendis(randevuId) && view
					.equals("/kontrol.xhtml"))
					|| !view.equals("/kontrol.xhtml")) {
				secilenBina = new BinaBusiness().binaBulByRandevuId(randevuId);
				
				if (secilenBina != null && secilenBina.getBinaId() > 0) {
					
					
					
					if (view.equals("/kontrol.xhtml")) {
						
						((HttpServletRequest) FacesContext.getCurrentInstance()
								.getExternalContext().getRequest()).getSession()
								.setAttribute("randevu",randevuId);
						((HttpServletRequest) FacesContext.getCurrentInstance()
								.getExternalContext().getRequest()).getSession()
								.setAttribute("randevuBina",secilenBina.getBinaId());
					
					
						SoruTableBean soruTableBean = (SoruTableBean) FacesContext
								.getCurrentInstance()
								.getApplication()
								.evaluateExpressionGet(FacesContext.getCurrentInstance(),
										"#{soruTableBean}", SoruTableBean.class);
						if (soruTableBean!=null){
							soruTableBean.setCihazlar(new ArrayList<CihazDTO>());
							soruTableBean.setCihazList(new CihazDataModel(soruTableBean.getCihazlar()));
							soruTableBean.setSoruListSmall(new ArrayList<AnaSoru>());
							soruTableBean.setKontrolDTO(new KontrolDTO());
							soruTableBean.setSecilen(new CihazDTO());
							soruTableBean.setAltSorularModel(new SoruDataModel(new ArrayList<SoruDTO>()));
							soruTableBean.setOnTanimliList(new ArrayList<SoruOnTanimliDTO>());
							soruTableBean.setOnTanimliModel(new SoruOnTanimliModel(soruTableBean.getOnTanimliList()));
							soruTableBean.setOptionalSoruListSmall(new ArrayList<AnaSoru>());
							try {
								soruTableBean.setCihazlar(new AsansorBusiness()
								.getKontrolEdilecekCihazlar(
										secilenBina.getBinaId(), randevuId));
								
							} catch (ReadException e) {
								soruTableBean.setCihazlar(new ArrayList<CihazDTO>());
							}
							soruTableBean.setCihazList(new CihazDataModel(soruTableBean.getCihazlar()));
							
						}
						
						

						/*
						 * soruTableBean.getSecilen().setCihaz(null);
						 */
					}

					try {
						binaDTO = new BinaDTO();
						binaDTO = (BinaDTO) secilenBina.clone();
						this.sessionBina=(BinaDTO)secilenBina.clone();
						/*
						 * List<BelediyeDTO> l=new ArrayList<BelediyeDTO>();
						 * l=new
						 * BelediyeBusiness().getBelediyelerByIl(binaDTO.getIl
						 * ()); for (BelediyeDTO d:l){ belediyeler.add(new
						 * SelectItem(d.getKod(),d.getAdi()));
						 * 
						 * }
						 * 
						 * for (SelectItem i:belediyeler){ if
						 * (Integer.parseInt(i.getValue
						 * ().toString())==binaDTO.getBelediye()){
						 * binaDTO.setBelediyeAdi(i.getLabel().toString());
						 * break; } } for (YapiKonusuDTO y:yapiTipler){ if
						 * (y.getId()==binaDTO.getYapiTip()){
						 * binaDTO.setYapiKonusuStr(y.getTip()); break; } }
						 */
						binaId = secilenBina.getBinaId();
						getRandevularForBina(binaDTO);
						
						// binaSayfasinaGit();
					} catch (CloneNotSupportedException e) {

						e.printStackTrace();
					}

				}

				else {
				

					 randevuId=0;
					FacesMessage msg = new FacesMessage(
							Messages._SQL_506_.getMesaj());
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			} else {
				SoruTableBean soruTableBean = (SoruTableBean) FacesContext
						.getCurrentInstance()
						.getApplication()
						.evaluateExpressionGet(FacesContext.getCurrentInstance(),
								"#{soruTableBean}", SoruTableBean.class);
				if (soruTableBean!=null){
					soruTableBean.setCihazlar(new ArrayList<CihazDTO>());
					soruTableBean.setCihazList(new CihazDataModel(soruTableBean.getCihazlar()));
					soruTableBean.setSoruListSmall(new ArrayList<AnaSoru>());
					soruTableBean.setKontrolDTO(new KontrolDTO());
					soruTableBean.setSecilen(new CihazDTO());
					soruTableBean.setAltSorularModel(new SoruDataModel(new ArrayList<SoruDTO>()));
					soruTableBean.setOnTanimliList(new ArrayList<SoruOnTanimliDTO>());
					soruTableBean.setOnTanimliModel(new SoruOnTanimliModel(soruTableBean.getOnTanimliList()));
					soruTableBean.setOptionalSoruListSmall(new ArrayList<AnaSoru>());
					
					
				}
				
				randevuId=0;
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								Messages._RANDEVUSIZINDEGIL_.getMesaj(), ""));
			}
		} catch (ReadException e) {
			randevuId=0;
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage()));
		}

		return "";

	}
	
	public String asansorTest() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			String view = context.getViewRoot().getViewId();
			binaDTO = new BinaDTO();
			if ((new RandevuBusiness().isRandevuMuhendis(randevuId) && view
					.equals("/kontrol.xhtml"))
					|| !view.equals("/kontrol.xhtml")) {
				secilenBina = new BinaBusiness().binaBulByRandevuId(randevuId);
				if (secilenBina != null && secilenBina.getBinaId() > 0) {

					if (view.equals("/kontrol.xhtml")) {
						
					
						

						/*
						 * soruTableBean.getSecilen().setCihaz(null);
						 */
					}

					try {

						binaDTO = (BinaDTO) secilenBina.clone();
						/*
						 * List<BelediyeDTO> l=new ArrayList<BelediyeDTO>();
						 * l=new
						 * BelediyeBusiness().getBelediyelerByIl(binaDTO.getIl
						 * ()); for (BelediyeDTO d:l){ belediyeler.add(new
						 * SelectItem(d.getKod(),d.getAdi()));
						 * 
						 * }
						 * 
						 * for (SelectItem i:belediyeler){ if
						 * (Integer.parseInt(i.getValue
						 * ().toString())==binaDTO.getBelediye()){
						 * binaDTO.setBelediyeAdi(i.getLabel().toString());
						 * break; } } for (YapiKonusuDTO y:yapiTipler){ if
						 * (y.getId()==binaDTO.getYapiTip()){
						 * binaDTO.setYapiKonusuStr(y.getTip()); break; } }
						 */
						binaId = secilenBina.getBinaId();
						// binaSayfasinaGit();
					} catch (CloneNotSupportedException e) {

						e.printStackTrace();
					}

				}

				else {
				

					 
					FacesMessage msg = new FacesMessage(
							Messages._SQL_506_.getMesaj());
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			} else {
			
				SoruTableBean soruTableBean = (SoruTableBean) FacesContext
						.getCurrentInstance()
						.getApplication()
						.evaluateExpressionGet(FacesContext.getCurrentInstance(),
								"#{soruTableBean}", SoruTableBean.class);
				if (soruTableBean!=null){
					soruTableBean.setCihazlar(new ArrayList<CihazDTO>());
					soruTableBean.setCihazList(new CihazDataModel(soruTableBean.getCihazlar()));
					soruTableBean.setSoruListSmall(new ArrayList<AnaSoru>());
					soruTableBean.setKontrolDTO(new KontrolDTO());
					soruTableBean.setSecilen(new CihazDTO());
					soruTableBean.setAltSorularModel(new SoruDataModel(new ArrayList<SoruDTO>()));
					soruTableBean.setOnTanimliList(new ArrayList<SoruOnTanimliDTO>());
					soruTableBean.setOnTanimliModel(new SoruOnTanimliModel(soruTableBean.getOnTanimliList()));
					soruTableBean.setOptionalSoruListSmall(new ArrayList<AnaSoru>());
					
					
				}
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								Messages._RANDEVUSIZINDEGIL_.getMesaj(), ""));
			}
		} catch (ReadException e) {
			e.printStackTrace();
		}

		return "";

	}

	private String randevuStr;

	public String getRandevuStr() {
		return randevuStr;
	}

	public void setRandevuStr(String randevuStr) {
		this.randevuStr = randevuStr;
		if (randevuStr!=null && randevuStr.trim().length()>0)
		this.randevuId = Integer.parseInt(randevuStr.trim());
	}

	public int getRandevuId() {
		return randevuId;
	}

	public void setRandevuId(int randevuId) {
		this.randevuId = randevuId;
	}

	public void submit() {
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
				.put("binaBean", this);

	}

	private TreeMap<String, Integer> ilceler = new TreeMap<String, Integer>(
			new IlIlceComparator());
	private TreeMap<String, Integer> bucakKoyList = new TreeMap<String, Integer>(
			new IlIlceComparator());
	private String ada;
	private String tcKimlikNo;
	private String parsel;
	private String pafta;
	private BinaDTO binalar = new BinaDTO();

	public String getTcKimlikNo() {
		return tcKimlikNo;
	}

	public void setTcKimlikNo(String tcKimlikNo) {
		this.tcKimlikNo = tcKimlikNo;
	}

	public BinaDTO getBinalar() {
		return binalar;
	}

	public void setBinalar(BinaDTO binalar) {
		this.binalar = binalar;
	}

	public String getAda() {
		return ada;
	}

	public void setAda(String ada) {
		this.ada = ada;
	}

	public String getParsel() {
		return parsel;
	}

	public void setParsel(String parsel) {
		this.parsel = parsel;
	}

	public String getPafta() {
		return pafta;
	}

	public void setPafta(String pafta) {
		this.pafta = pafta;
	}

	public List<SelectItem> getBelediyeler() {
		return belediyeler;
	}

	public void setBelediyeler(List<SelectItem> belediyeler) {
		this.belediyeler = belediyeler;
	}

	public BinaDTO getSecilen() {
		return secilen;
	}

	public void setSecilen(BinaDTO secilen) {
		this.secilen = secilen;
	}

	public List<BinaDTO> getBinaList() {
		return binaList;
	}

	public void setBinaList(List<BinaDTO> binaList) {
		this.binaList = binaList;
	}

	public String binaByAdaParselPafta() {
		FacesMessage msg;
		FacesContext context = FacesContext.getCurrentInstance();
		binaList = new ArrayList<BinaDTO>();
		// try {
		for (BinaDTO bina : loginBean.getTumBinalar()) {
			if (bina.getAda().equals(ada) && bina.getParsel().equals(parsel)
					&& bina.getPafta().equals(pafta)) {
				binaList.add(bina);
			}
		}
		// binaList=new BinaBusiness().binaBul(ada,parsel,pafta);

		if (binaList.size() <= 0) {
			this.binalar = new BinaDTO();
			this.binaDTO = new BinaDTO();
			msg = new FacesMessage(Messages._SQL_506_.getMesaj());
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			context.addMessage(null, msg);
		}
		binaListSmall = new BinaDataModel(binaList);
		ada = "";
		parsel = "";
		pafta = "";
		/*
		 * } catch (CRUDException e) { msg=new FacesMessage(e.getMessage());
		 * msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		 * context.addMessage(null, msg); }
		 */
		return "";
	}

	public void binaBulByTcKimlik() {
		FacesMessage msg;
		FacesContext context = FacesContext.getCurrentInstance();
		this.binaDTO=new BinaDTO();
		this.binalar=new BinaDTO();
		cihazList=new ArrayList<CihazDTO>();
		basvuruClear();
		binaList=new ArrayList<BinaDTO>();
		binaListSmall=new BinaDataModel(new ArrayList<BinaDTO>());
		if (tcKimlikNo==null || tcKimlikNo.trim().equals("")){
			
			context.addMessage(null, new FacesMessage(Messages._GECERSIZTCKIMLIKNO_.getMesaj()));
		}else{
		try {
			
			binaList = new BinaBusiness().binaBulByTcKimlik(Long
					.parseLong(tcKimlikNo));

			if (binaList.size() <= 0) {
				
				msg = new FacesMessage(Messages._SQL_506_.getMesaj());
				msg.setSeverity(FacesMessage.SEVERITY_INFO);
				context.addMessage(null, msg);
			}
			binaListSmall = new BinaDataModel(binaList);

			
		} catch (CRUDException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, msg);
		}}
		tabIndex=1;
		
	}

	public void onRowSelect(SelectEvent event)
			throws CloneNotSupportedException {
		// FacesMessage msg;
		// FacesContext context=FacesContext.getCurrentInstance();

		String id = String.valueOf(((BinaDTO) event.getObject()).getBinaId());

		if (id != null && secilen != null) {
			getBinaBilgileri(secilen);
			binalar = secilen;
			this.binaId = secilen.getBinaId();
			this.tescilno = secilen.getTescilNo();
		}
	}

	public void onRowUnselect(UnselectEvent event) {
	}
	private List<FirmaDTO> bakimciFirmalarArama=new ArrayList<FirmaDTO>();
	

	public List<FirmaDTO> getBakimciFirmalarArama() {
		return bakimciFirmalarArama;
	}

	public void setBakimciFirmalarArama(List<FirmaDTO> bakimciFirmalarArama) {
		this.bakimciFirmalarArama = bakimciFirmalarArama;
	}

	@SuppressWarnings("unused")
	public void ilChange(AjaxBehaviorEvent event) {
		int ilKodu = Integer.parseInt(((UIInput) (event.getComponent()))
				.getValue().toString());
		if (ilKodu>0){
	
		if (((UIInput) (event.getComponent())).getId().equals("gil")){
			
			genelBinalar=new ArrayList<BinaDTO>();
			cihazList=new ArrayList<CihazDTO>();
			this.binaDTO=new BinaDTO();
			basvuruClear();
		}
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;
		ApplicationBean bean = (ApplicationBean) context.getExternalContext()
				.getApplicationMap().get("applicationBean");

		ilceler = new TreeMap<String, Integer>(new IlIlceComparator());
		bucakKoyList = new TreeMap<String, Integer>(new IlIlceComparator());
		ilceler.putAll(bean.getIlceler().get(ilKodu));
		this.binaDTO.setUavtKod(0);
		this.binaDTO.setBucakKoyKod(0);
		String viewId = FacesContext.getCurrentInstance().getViewRoot()
				.getViewId();
		int sozlesmeBinaTip = 0;
		if (viewId.equals("/binakayit.xhtml")) {
			sozlesmeBinaTip = binaDTO.getSozlesmeBinaTipId();
		} else {
			BasvuruBean basvuru = (BasvuruBean) FacesContext
					.getCurrentInstance()
					.getApplication()
					.evaluateExpressionGet(FacesContext.getCurrentInstance(),
							"#{basvuruBean}", BasvuruBean.class);
			try {
				basvuru.getBasvuru().getBina().setUavtKod(0);
				sozlesmeBinaTip = basvuru.getBasvuru().getBina()
						.getSozlesmeBinaTipId();
			} catch (NullPointerException e) {

			}
		}
		try {
			bakimciFirmalarArama=new RandevuBean().getFirmalar(ilKodu);
			belediyeList = new ArrayList<BelediyeDTO>();
			belediyelerSize = 0;
			if (((UIInput)event.getComponent())!=null && ((UIInput)event.getComponent()).getId().contains("gil")){
				belediyeList = new BelediyeBusiness().getBelediyelerByIl(ilKodu,
						genelAraBina.getSozlesmeBinaTipId());
			}else{
			belediyeList = new BelediyeBusiness().getBelediyelerByIl(ilKodu,
					sozlesmeBinaTip);
			}
			belediyeler = new ArrayList<SelectItem>();
			belediyelerSize = belediyeList.size();
			if (belediyelerSize > 0) {
				for (BelediyeDTO d : belediyeList) {
					belediyeler.add(new SelectItem(d.getKod(), d.getAdi()));
				}
			} else {
				belediyeler = new ArrayList<SelectItem>();
				belediyeler.add(new SelectItem("",
						"Sözleşmeli Belediye Bulunamadı"));
				// context.addMessage(null,new
				// FacesMessage("Sözleşmeli Belediye Bulunamadı!!!"));
			}
		} catch (ReadException e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));

		}

		mahalleList = new ArrayList<Mahalle>();
		caddeSokakList = new ArrayList<CaddeSokak>();
		
		}else{
			ilceler=new TreeMap<String, Integer>();
			bucakKoyList = new TreeMap<String, Integer>(new IlIlceComparator());
			belediyeler = new ArrayList<SelectItem>();
			mahalleList = new ArrayList<Mahalle>();
			caddeSokakList = new ArrayList<CaddeSokak>();
			genelBinalar=new ArrayList<BinaDTO>();
			cihazList=new ArrayList<CihazDTO>();
			this.binaDTO=new BinaDTO();
			bakimciFirmalarArama=new ArrayList<FirmaDTO>();
			belediyelerSize = 0;
			
			BasvuruBean basvuruBean = (BasvuruBean)
					 FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{basvuruBean}",
					 BasvuruBean.class);
			if (basvuruBean!=null){
				basvuruBean.setAsansorData(new BasvuruAsansorDataModel(new ArrayList<BasvuruAsansorDTO>()));
				basvuruBean.setFirmaList(new ArrayList<FirmaDTO>());
				basvuruBean.setAsansorList(new ArrayList<BasvuruAsansorDTO>());
				if (basvuruBean.getBasvuru()!=null)
				basvuruBean.getBasvuru().setBina(new BinaDTO());
				basvuruBean.setSelectedAsansor(new BasvuruAsansorDTO[]{});
			}
		}
	}

	public void bucakKoyChange(AjaxBehaviorEvent event) {

		int koyKodu = Integer.parseInt(((UIInput) (event.getComponent()))
				.getValue().toString());
		if (((UIInput) (event.getComponent())).getId().equals("gbucak")){
			
			
			genelBinalar=new ArrayList<BinaDTO>();
			cihazList=new ArrayList<CihazDTO>();
			this.binaDTO=new BinaDTO();
			basvuruClear();
		}

		if (koyKodu > 0) {
			this.binaDTO.setMahalle("");
			try {
				BasvuruBean basvuru = (BasvuruBean) FacesContext
						.getCurrentInstance()
						.getApplication()
						.evaluateExpressionGet(
								FacesContext.getCurrentInstance(),
								"#{basvuruBean}", BasvuruBean.class);

				String viewId = FacesContext.getCurrentInstance().getViewRoot()
						.getViewId();

				getMahalleList(koyKodu);
				if (viewId.equals("/basvuru.xhtml")
						|| viewId.equals("/tarama.xhtml")) {
					if (basvuru != null) {

						basvuru.getBasvuru().getBina().setUavtKod(0);

					}
				} else {
					if (binaDTO != null) {

						this.binaDTO.setUavtKod(0);

					}
				}

				caddeSokakList = new ArrayList<CaddeSokak>();

			} catch (CRUDException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			}
		}

	}

	public void ilceChange(AjaxBehaviorEvent event) {

		int ilceKodu = Integer.parseInt(((UIInput) (event.getComponent()))
				.getValue().toString());
		
		if (ilceKodu > 0) {
			if (((UIInput) (event.getComponent())).getId().equals("gilce")){
				
				
				genelBinalar=new ArrayList<BinaDTO>();
				cihazList=new ArrayList<CihazDTO>();
				this.binaDTO=new BinaDTO();
				basvuruClear();
			}
			
			BasvuruBean basvuru = (BasvuruBean) FacesContext
					.getCurrentInstance()
					.getApplication()
					.evaluateExpressionGet(FacesContext.getCurrentInstance(),
							"#{basvuruBean}", BasvuruBean.class);

			String viewId = FacesContext.getCurrentInstance().getViewRoot()
					.getViewId();
			// LoginBean loginBean=(LoginBean)Common.findBean("loginBean");
			// loginBean.setLoggedIn(true);
			try {
				getBucakKoyListesi((short)ilceKodu);
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			}

		
			if (viewId.equals("/basvuru.xhtml")
					|| viewId.equals("/tarama.xhtml")) {
				if (basvuru != null) {
				
						basvuru.getBasvuru().getBina().setUavtKod(0);
				}
			} else {
				if (binaDTO != null) {

					
						
						this.binaDTO.setUavtKod(0);
										}
			}

			caddeSokakList = new ArrayList<CaddeSokak>();
			mahalleList = new ArrayList<Mahalle>();

		}

	}

	public void getBucakKoyListesi(short ilce) throws CRUDException {
		List<BucakKoyDTO> bucakKoyListesi = new ArrayList<BucakKoyDTO>();
		bucakKoyListesi = ApplicationDAOImpl.getINSTANCE()
				.getBucakKoyByIlceKod(ilce);

		bucakKoyList = new TreeMap<String, Integer>(new IlIlceComparator());
		for (BucakKoyDTO b : bucakKoyListesi) {
			bucakKoyList.put(b.getBucakAdi() + "-" + b.getKoyAdi(),
					b.getKoyKodu());
		}

	}

	public void mahalleChange(AjaxBehaviorEvent event) {
		String viewId = FacesContext.getCurrentInstance().getViewRoot()
				.getViewId();

		int mahalleId = 0;
		if (((UIInput) (event.getComponent())).getId().equals("gmahalle")){
			
			
			genelBinalar=new ArrayList<BinaDTO>();
			cihazList=new ArrayList<CihazDTO>();
			this.binaDTO=new BinaDTO();
			basvuruClear();
		}
		for (Mahalle m : mahalleList) {
			if (m.getAd()
					.replace('i', 'İ')
					.toUpperCase()
					.equals(((UIInput) (event.getComponent())).getValue()
							.toString())) {
				mahalleId = m.getKod();
				break;
			}
		}

		try {

			caddeSokakList = ApplicationDAOImpl.getINSTANCE()
					.getCaddeSokakByMahalleId(mahalleId);
			if (caddeSokakList != null) {
				for (CaddeSokak cs : caddeSokakList) {
					if (cs.getAd() == null) {
						cs.setAd("Cadde/Sokak Adı Yok!");
					}
				}
			}
			Collections.sort(caddeSokakList, new CaddeSokakComparator());
		} catch (NullPointerException e) {

		} catch (Exception e) {

		}
		if (this.binaDTO != null)
			this.binaDTO.setUavtKod(0);
		if (viewId.contains("basvuru") || viewId.contains("tarama")) {
			BasvuruBean basvuru = (BasvuruBean) FacesContext
					.getCurrentInstance()
					.getApplication()
					.evaluateExpressionGet(FacesContext.getCurrentInstance(),
							"#{basvuruBean}", BasvuruBean.class);

			if (basvuru != null && basvuru.getBasvuru() != null
					&& basvuru.getBasvuru().getBina() != null) {
				basvuru.getBasvuru().getBina().setUavtKod(0);
			}
		}

	}

	public void caddeSokakChange(AjaxBehaviorEvent event) {
		/*
		 * Bina tablosundan sorgu ve seçim sonrası bina no,uavt kod ve varsa ada
		 * parsel ve pafta güncellenecek şimdilik servise gidiyor
		 */
		if (((UIInput) (event.getComponent())).getId().equals("gcaddesokak")){
			
			
			genelBinalar=new ArrayList<BinaDTO>();
			cihazList=new ArrayList<CihazDTO>();
			this.binaDTO=new BinaDTO();
			basvuruClear();
		}
		this.uavtBina = new UavtBina();
		BasvuruBean basvuru = (BasvuruBean) FacesContext
				.getCurrentInstance()
				.getApplication()
				.evaluateExpressionGet(FacesContext.getCurrentInstance(),
						"#{basvuruBean}", BasvuruBean.class);
		uavtBinaList = new UavtBinaDataModel(new ArrayList<UavtBina>());
		String viewId = FacesContext.getCurrentInstance().getViewRoot()
				.getViewId();
		if (this.binaDTO != null)
			this.binaDTO.setUavtKod(0);
		if (viewId.contains("basvuru") || viewId.contains("tarama")) {

			if (basvuru != null && basvuru.getBasvuru() != null
					&& basvuru.getBasvuru().getBina() != null) {
				basvuru.getBasvuru().getBina().setUavtKod(0);
			}
		}

		try {

			long csbmKod = 0;
			if (viewId.contains("basvuru") || viewId.contains("tarama")) {
				for (CaddeSokak cs : caddeSokakList) {
					if (cs.getAd().equals(
							basvuru.getBasvuru().getBina().getCaddeSokak())) {
						csbmKod = cs.getKod();
						break;
					}
				}
			} else {
				for (CaddeSokak cs : caddeSokakList) {
					if (cs.getAd().equals(this.binaDTO.getCaddeSokak())) {
						csbmKod = cs.getKod();
						break;
					}
				}
			}
			uavtBinaList = new UavtBinaDataModel(new ArrayList<UavtBina>());
			List<UavtBina> binaList = new ArrayList<UavtBina>();
			binaList = new BinaBusiness().getUavtBinaTableByCsbmKod(csbmKod);
			uavtBinaList = new UavtBinaDataModel(binaList);
//			RequestContext.getCurrentInstance()
//					.execute("PF('uavtBina').show()");
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}

	}

	public TreeMap<String, Integer> getIlceler() {
		return ilceler;
	}

	public void setIlceler(TreeMap<String, Integer> ilceler) {
		this.ilceler = ilceler;
	}

	public List<BinaDTO> complete(String prefix) {

		List<BinaDTO> eslesenler = new ArrayList<BinaDTO>();
		if (loginBean.isLoggedIn() || loginBean.isBelediyeloggedIn()) {
			for (BinaDTO b : loginBean.getTumBinalar()) {
				if (b.getBinaAdi().toLowerCase().contains(prefix.toLowerCase())) {
					eslesenler.add(b);
				}
			}
		} else {
			if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
					.contains("/basvuru.xhtml")) {
				for (BinaDTO b : sessionBean.getTumBinalar()) {
					if (b.getBinaAdi().toLowerCase()
							.contains(prefix.toLowerCase())) {
						eslesenler.add(b);
					}
				}
			}
		}
		if (eslesenler == null || eslesenler.size() <= 0) {
			eslesenler = new ArrayList<BinaDTO>();
			BinaDTO b = new BinaDTO();
			b.setBinaId(0);
			b.setBinaAdi("Kayıt Bulunamadı !");
			eslesenler.add(b);
		}
		return eslesenler;
	}

	public List<BinaDTO> completeWithAdres(String prefix) {

		List<BinaDTO> eslesenler = new ArrayList<BinaDTO>();
		if (loginBean.isLoggedIn() || loginBean.isBelediyeloggedIn()) {
			for (BinaDTO b : loginBean.getTumBinalar()) {
				StringBuilder sb = new StringBuilder();
				sb.append(b.getMahalle()).append(b.getCaddeSokak());

				if (sb.toString().toLowerCase().contains(prefix.toLowerCase())
						|| (b.getAcikAdres() == null ? "" : b.getAcikAdres())
								.toLowerCase().contains(prefix.toLowerCase())) {
					eslesenler.add(b);
				}
			}
		} else {
			if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
					.contains("/basvuru.xhtml")) {
				for (BinaDTO b : sessionBean.getTumBinalar()) {
					StringBuilder sb = new StringBuilder();
					sb.append(b.getMahalle()).append(b.getCaddeSokak());

					if (sb.toString().toLowerCase()
							.contains(prefix.toLowerCase())
							|| (b.getAcikAdres() == null ? "" : b
									.getAcikAdres()).toLowerCase().contains(
									prefix.toLowerCase())) {
						eslesenler.add(b);
					}

				}
			}
		}

		if (eslesenler == null || eslesenler.size() <= 0) {
			eslesenler = new ArrayList<BinaDTO>();
			BinaDTO b = new BinaDTO();
			b.setBinaId(0);
			b.setBinaAdi("Kayıt Bulunamadı !");
			eslesenler.add(b);
		}
		return eslesenler;
	}
	public void itemSelectListener(SelectEvent event){
		if (FacesContext.getCurrentInstance().getViewRoot().getViewId().contains("basvuru") || 
				FacesContext.getCurrentInstance().getViewRoot().getViewId().contains("tarama")){
			BasvuruBean basvuru = (BasvuruBean) FacesContext
					.getCurrentInstance()
					.getApplication()
					.evaluateExpressionGet(FacesContext.getCurrentInstance(),
							"#{basvuruBean}", BasvuruBean.class);
			basvuru.setAsansorList(new ArrayList<BasvuruAsansorDTO>());
			basvuru.setFirmaList(new ArrayList<FirmaDTO>());
			basvuru.setAsansorData(new BasvuruAsansorDataModel(new ArrayList<BasvuruAsansorDTO>()));
			basvuru.setSelectedAsansor(new BasvuruAsansorDTO[]{});
			basvuru.getBasvuru().setBina(new BinaDTO());
			
		}else{
			this.binaDTO=new BinaDTO();
			cihazList=new ArrayList<CihazDTO>();
		}
		
	}
	public void listener(SelectEvent event) throws CloneNotSupportedException {
		BinaDTO binaDto = (BinaDTO) event.getObject();
		binaDTO=new BinaDTO();
		if (binaDto != null) {
			getBinaBilgileri(binaDto);
			if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
					.equals("/asansor.xhtml")) {
				try {

					CihazBean cihazBean = (CihazBean) FacesContext
							.getCurrentInstance()
							.getApplication()
							.evaluateExpressionGet(
									FacesContext.getCurrentInstance(),
									"#{cihazBean}", CihazBean.class);
					cihazBean.setCihazTeknik(new ArrayList<CihazTeknikDTO>());
					cihazBean.setVisible(false);
					try {
						cihazBean.setUavtAsansorSiraNo("");
						cihazBean.setCihazUavtKod("");

					} catch (Exception e) {

					}

					CihazDTO cihaz = new CihazDTO();
					cihaz.setDurum("A");
					cihazBean.setCihaz(cihaz);

					cihazBean.setTanimlanacakAsansorAdet(1);

				} catch (Exception e) {

				}
			}
		}

	}

	public void listener() {

		if (binalar != null) {
			cihazList = new ArrayList<CihazDTO>();
			CihazBean cihazBean = (CihazBean) FacesContext
					.getCurrentInstance()
					.getApplication()
					.evaluateExpressionGet(FacesContext.getCurrentInstance(),
							"#{cihazBean}", CihazBean.class);
			cihazBean.setCihazTeknik(new ArrayList<CihazTeknikDTO>());
			cihazBean.setVisible(false);
			try {
				cihazBean.setUavtAsansorSiraNo("");
				cihazBean.setCihazUavtKod("");

			} catch (Exception e) {

			}

			CihazDTO cihaz = new CihazDTO();
			cihaz.setDurum("A");
			cihazBean.setCihaz(cihaz);
			getBinaBilgileri(binalar);
			cihazBean
					.setBinaIdString(String
							.valueOf(this.binaDTO.getBinaId() == 0 ? ""
									: this.binaDTO.getBinaId()));

		}
		tabIndex=0;

	}

	public void bListener(SelectEvent event) throws CloneNotSupportedException {
		BinaDTO binaDto = (BinaDTO) event.getObject();

		if (binaDto != null) {
			secilenBina = (BinaDTO) binaDto.clone();
			try {
				List<RandevuListeDTO> rList = new ArrayList<RandevuListeDTO>();
				rList = new RandevuBusiness().getRandevuByBinaId(binaDto
						.getBinaId());
				randevuListeModel = new RandevuListeDataModel(rList);
				if (rList.size() <= 0) {
					SoruTableBean soruTableBean = new SoruTableBean();
					soruTableBean.setSoruListSmall(new ArrayList<AnaSoru>());
					soruTableBean.setSecilen(new CihazDTO());
					soruTableBean.setCihazlar(new ArrayList<CihazDTO>());

					FacesContext
							.getCurrentInstance()
							.getApplication()
							.getExpressionFactory()
							.createValueExpression(
									FacesContext.getCurrentInstance()
											.getELContext(),
									"#{soruTableBean}", SoruTableBean.class)

							.setValue(
									FacesContext.getCurrentInstance()
											.getELContext(), soruTableBean);

				}
			} catch (CRUDException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			}
		}
	}

	public void bListener() throws CloneNotSupportedException {
		randevuListeModel = new RandevuListeDataModel(new ArrayList<RandevuListeDTO>());
		if (binalar != null) {
			secilenBina = (BinaDTO) binalar.clone();
			binaDTO = (BinaDTO) binalar.clone();
			try {
				List<RandevuListeDTO> rList = new ArrayList<RandevuListeDTO>();
				rList = new RandevuBusiness().getRandevuByBinaId(binalar
						.getBinaId());
				randevuListeModel = new RandevuListeDataModel(rList);
				if (rList.size() <= 0) {
					SoruTableBean soruTableBean = new SoruTableBean();

					soruTableBean.setSoruListSmall(new ArrayList<AnaSoru>());
					soruTableBean.setSecilen(new CihazDTO());
					soruTableBean.setCihazlar(new ArrayList<CihazDTO>());
					binaDTO = new BinaDTO();

					FacesContext
							.getCurrentInstance()
							.getApplication()
							.getExpressionFactory()
							.createValueExpression(
									FacesContext.getCurrentInstance()
											.getELContext(),
									"#{soruTableBean}", SoruTableBean.class)

							.setValue(
									FacesContext.getCurrentInstance()
											.getELContext(), soruTableBean);

				}
			} catch (CRUDException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			}
		}
	}
	
	public void getRandevularForBina(BinaDTO bina)  {
		randevuListeModel = new RandevuListeDataModel(new ArrayList<RandevuListeDTO>());
	
			
			try {
				secilenBina = (BinaDTO) bina.clone();
				binaDTO = (BinaDTO) bina.clone();
				List<RandevuListeDTO> rList = new ArrayList<RandevuListeDTO>();
				rList = new RandevuBusiness().getRandevuByBinaId(bina
						.getBinaId());
				if (randevuId>0){
					for (RandevuListeDTO r:rList){
						if (randevuId==r.getRandevuId()){
							randevuListe=r;
						break;
						}
					}
				}
				randevuListeModel = new RandevuListeDataModel(rList);
				
			} catch (CRUDException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage()));
			}catch (CloneNotSupportedException e) {
				secilenBina = new BinaDTO();
				binaDTO = new BinaDTO();
				randevuListeModel=new RandevuListeDataModel(new ArrayList<RandevuListeDTO>());
			}
		
	}

	public String binaKaydet() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		
		try {
			switch (binaDTO.getBinaId()) {
			case 0:
				int binaId = new BinaBusiness().binaEkle(binaDTO);
				binaDTO.setBinaId(binaId);
				break;
			default:
				new BinaBusiness().binaGuncelle(binaDTO);
				break;
			}
			try {
				this.sessionBina=(BinaDTO)this.binaDTO.clone();
			} catch (CloneNotSupportedException e) {
				this.sessionBina=new BinaDTO();
			}
			if(binaDTO.getBinaId() > 0){
			msg = new FacesMessage(Messages._BINAKAYDEDILDI_.getMesaj());
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			}else{
//				 msg= new FacesMessage(Messages._UAVTVAR_.getMesaj());
//				 FacesContext.getCurrentInstance().addMessage(null, msg);
			 }
			List<BinaDTO> binaList = new ArrayList<BinaDTO>();
			binaList = new BinaBusiness().binaBulByIlIlce();
			loginBean.setTumBinalar(binaList);
			/*
			 * binaDTO=new BinaDTO(); CihazBean cihazBean=(CihazBean)
			 * context.getViewRoot() .getViewMap().get("cihazBean");
			 * cihazList=new ArrayList<CihazDTO>(); if (cihazBean!=null){
			 * cihazBean.setCihazList(new CihazDataModel(cihazList)); }
			 */

		} catch (CRUDException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		if(binaDTO.getBinaId() > 0){
		context.addMessage(null, msg);
		}
		return "";
	}

	public void degisiklikListener(AjaxBehaviorEvent event) {
		String viewId = FacesContext.getCurrentInstance().getViewRoot()
				.getViewId();
		// LoginBean loginBean=(LoginBean)Common.findBean("loginBean");
		// loginBean.setLoggedIn(true);
		if (viewId.equals("/basvuru.xhtml") || viewId.equals("/tarama.xhtml")) {

			BasvuruBean basvuru = (BasvuruBean) FacesContext
					.getCurrentInstance()
					.getApplication()
					.evaluateExpressionGet(FacesContext.getCurrentInstance(),
							"#{basvuruBean}", BasvuruBean.class);
			if (((UIInput) (event.getComponent())).getId()
					.equals("txtbinatipi")) {
				int binaTip = Integer
						.parseInt(((UIInput) (event.getComponent())).getValue()
								.toString());
				FacesContext context = FacesContext.getCurrentInstance();

				try {
					belediyeList = new ArrayList<BelediyeDTO>();
					belediyeList = new BelediyeBusiness().getBelediyelerByIl(
							basvuru.getBasvuru().getBina().getIl(), binaTip);
					belediyelerSize = belediyeList.size();
					if (belediyeList.size() > 0) {
						belediyeler = new ArrayList<SelectItem>();
						for (BelediyeDTO d : belediyeList) {

							belediyeler.add(new SelectItem(d.getKod(), d
									.getAdi()));
						}
					} else {
						belediyeler = new ArrayList<SelectItem>();
						belediyeler.add(new SelectItem("",
								"Sözleşmeli Belediye Bulunamadı"));
						context.addMessage(null, new FacesMessage(
								"Sözleşmeli Belediye Bulunamadı!!!"));
					}
				} catch (ReadException e) {
					context.addMessage(null, new FacesMessage(e.getMessage()));

				}
			}
			basvuru.setBinaBilgisiDegisti(true);
			basvuru.getBasvuru().getBina().setUavtKod(0);
			if (((UIInput) event.getSource()).getId().contains("ddcaddesokak")) {
				caddeSokakChange(event);
			}
		}

	}

	public void binaKaydetBasvuru() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;
		BasvuruBean basvuru = (BasvuruBean) context.getApplication()
				.evaluateExpressionGet(context, "#{basvuruBean}",
						BasvuruBean.class);
		int binaId = 0;
		try {
			if (basvuru.getBinaTip().equals("Y")) {
				binaId = new BinaBusiness().binaEkle(basvuru.getBasvuru()
						.getBina());
				basvuru.getBasvuru().getBina().setBinaId(binaId);
				basvuru.setAsansorList(new ArrayList<BasvuruAsansorDTO>());
//				if(binaId == 0){
//					 msg= new FacesMessage(Messages._UAVTVAR_.getMesaj());
//					 FacesContext.getCurrentInstance().addMessage(null, msg);
//				}
			} else {
				new BinaBusiness().binaGuncelle(basvuru.getBasvuru().getBina());
			}
			basvuru.setFirmaList(new RandevuBean().getFirmalar(basvuru.getBasvuru().getBina().getIl()));
			
			List<BinaDTO> binaList = new ArrayList<BinaDTO>();
			binaList = new BinaBusiness().binaBulByIlIlce();
			loginBean.setTumBinalar(binaList);
			basvuru.setSkip(true);
			basvuru.setBinaBilgisiDegisti(false);
			basvuru.setCihazlarVisible(true);

			// basvuru.onFlowProcess(new
			// FlowEvent((UIComponent)FacesContext.getCurrentInstance().getViewRoot().findComponent(":formbasvuru:basvuruvizard"),
			// "bina", "asansor"));

		} catch (CRUDException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, msg);
		}

	}
	public void bakimciFirmaNav() {
	
			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.execute("window.open('cihazfirmalar.jsf')");

		
	}
	public void binaKisilerNav() {
		// String sayfa="binakisikayit?faces-redirect=true";
		if (binaId == 0) {
			FacesMessage msg = new FacesMessage(
					Messages._BINAKISILERIEKLENEMEZ_.getMesaj());
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			// sayfa="";
		} else {
			((HttpServletRequest) FacesContext.getCurrentInstance()
					.getExternalContext().getRequest()).getSession()
					.setAttribute("binaId", binaId);
			((HttpServletRequest) FacesContext.getCurrentInstance()
					.getExternalContext().getRequest()).getSession()
					.setAttribute("bina", binaDTO);
			/*
			 * kisiEklensin=true;
			 * 
			 * if (!kisiEklensin){
			 * 
			 * FacesMessage msg=new
			 * FacesMessage(Messages._BINAKISILERIEKLENEMEZ_.getMesaj());
			 * msg.setSeverity(FacesMessage.SEVERITY_INFO);
			 * FacesContext.getCurrentInstance().addMessage(null,msg);
			 * //sayfa="";
			 * 
			 * }else{
			 */

			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.execute("window.open('binakisikayit.jsf')");

		}
		// }

		// return sayfa;
		/*
		 * try {
		 * FacesContext.getCurrentInstance().getExternalContext().redirect(
		 * "binakisikayit.jsf"); } catch (IOException e) {
		 * 
		 * e.printStackTrace(); }
		 */
	}

	
	private void binaSayfasinaGit() {
		tabIndex=0;

	}

	public void getBinaBilgileri(BinaDTO binaDto) {
		FacesContext context = FacesContext.getCurrentInstance();
		binaId = binaDto.getBinaId();
		tescilno = binaDto.getTescilNo();
		FacesMessage msg;

		try {
			ArrayList<IlIlceDTO> list = ApplicationDAOImpl.getINSTANCE()
					.getIlceler(binaDto.getIl());
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			List<RandevuListeDTO> rList = new ArrayList<RandevuListeDTO>();
			randevuListeModel = new RandevuListeDataModel(rList);
			bakimciFirmalar = new RandevuBean().getFirmalar(binaDto.getIl());
		
			for (IlIlceDTO i : list) {
				map.put(i.getIlceAdi(), i.getIlceKodu());
			}
			ilceler = new TreeMap<String, Integer>(new IlIlceComparator());
			
			ilceler.putAll(map);
			
		
			List<BelediyeDTO> l = new ArrayList<BelediyeDTO>();
			l = new BelediyeBusiness().getBelediyelerByIl(binaDto.getIl(),
					binaDto.getSozlesmeBinaTipId());
			belediyeler.clear();
			for (BelediyeDTO d : l) {
				belediyeler.add(new SelectItem(d.getKod(), d.getAdi()));

			}
			
			belediyelerSize = l.size();
			
			
			bucakKoyList = new TreeMap<String, Integer>(new IlIlceComparator());
			getBucakKoyListesi(binaDto.getIlce());
			
			
			getMahalleList(binaDto.getBucakKoyKod());
			
			int mahalleId = 0;
			for (Mahalle m : mahalleList) {
				if (m.getAd().replace('i', 'İ').toUpperCase()
						.equals(binaDto.getMahalle())) {
					mahalleId = m.getKod();
				}
			}
			
			caddeSokakList = ApplicationDAOImpl.getINSTANCE()
					.getCaddeSokakByMahalleId(mahalleId);
			
			Collections.sort(caddeSokakList, new CaddeSokakComparator());
			
		} catch (CRUDException e) {

			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, msg);
			e.printStackTrace();
		}
		for (SelectItem i : belediyeler) {
			if (i.getValue()!=null && i.getValue().toString().trim().length()>0 && Integer.parseInt(i.getValue().toString()) == binaDto
					.getBelediye()) {
				binaDto.setBelediyeAdi(i.getLabel().toString());
				break;
			}
		}
		for (YapiKonusuDTO y : yapiTipler) {
			if (y.getId() == binaDto.getYapiTip()) {
				binaDto.setYapiKonusuStr(y.getTip());
				break;
			}
		}

		binaDTO = new BinaDTO();
		try {
			binaDTO = (BinaDTO) binaDto.clone();
			if (binaDTO.getEnlem() > 0 && binaDTO.getBoylam() > 0) {
				mapCenter = new StringBuilder().append(binaDTO.getEnlem())
						.append(",").append(binaDTO.getBoylam()).toString();
				emptyModel = new DefaultMapModel();

				LatLng coord1 = new LatLng(binaDTO.getEnlem(),
						binaDTO.getBoylam());

				emptyModel.addOverlay(new Marker(coord1, binaDTO.getBinaAdi()));

			} else {
				setMap();
			}

		} catch (CloneNotSupportedException e1) {

			e1.printStackTrace();
		}
		firmaList=new ArrayList<FirmaDTO>();
		firmaList=new RandevuBean().getFirmalar(binaDTO
				.getIl());
		try {
			if (cihazList==null || cihazList.size()<=0 || binaDTO.getBinaId()!=sessionBina.getBinaId()){
			cihazList = new AsansorBusiness().getCihazlar(binaDTO.getBinaId());
			}
		} catch (ReadException e) {
			e.printStackTrace();
		}
		CihazBean cihazBean = (CihazBean) context
				.getApplication()
				.evaluateExpressionGet(context, "#{cihazBean}", CihazBean.class);
		if (cihazBean != null) {
			cihazBean.setCihazlar(cihazList);
			cihazBean.setCihazList(new CihazDataModel(cihazBean.getCihazlar()));
			cihazBean.setIlKodu(binaDTO.getIl());
			cihazBean.setFirmaList(firmaList);
			cihazBean.setIlceler(ilceler);
			cihazBean.setIlceKodu(binaDTO.getIlce());
			cihazBean.getBinalar().add(binaDTO);
			cihazBean.setBinaId(binaDTO.getBinaId());

		}
		String viewId = FacesContext.getCurrentInstance().getViewRoot()
				.getViewId();
		// LoginBean loginBean=(LoginBean)Common.findBean("loginBean");
		// loginBean.setLoggedIn(true);
		if (viewId.equals("/binakayit.xhtml")) {

			Panel p = (Panel) FacesContext.getCurrentInstance().getViewRoot()
					.findComponent(":form1:pnlBina");
			p.setVisible(true);

		}
		if (viewId.equals("/basvuru.xhtml") || viewId.equals("/tarama.xhtml")) {

			BasvuruBean basvuru = (BasvuruBean) context.getApplication()
					.evaluateExpressionGet(context, "#{basvuruBean}",
							BasvuruBean.class);
			basvuru.getBasvuru().setBina(binaDTO);

			Panel p = (Panel) FacesContext.getCurrentInstance().getViewRoot()
					.findComponent(":formbasvuru:pnlBina");
			p.setVisible(true);
			if (tcKimlikNo == null || tcKimlikNo.length() < 11) {
				tcKimlikNo = String.valueOf(basvuru.getBasvuru().getBasvuru()
						.getBasvuruYapanTCKimlikNo());
			}

			List<BasvuruAsansorDTO> baList = new ArrayList<BasvuruAsansorDTO>();
			for (CihazDTO c : cihazList) {
				if (c.getDurum().equals("A")) {
					BasvuruAsansorDTO bd = new BasvuruAsansorDTO();
					bd.setSonKontrolEtiketi(c.getSonKontrolEtiketi());
					bd.setKontrolTarihi(c.getSonKontrolTarihi());
					bd.setCihazId(c.getCihazId());
					bd.setCihazTip(c.getTip());
					bd.setTipAciklama(c.getTipAciklama());
					bd.setKimlikNo(c.getKimlikNo());
					bd.setAsansorunYeri(c.getAsansorunYeri());
					List<CihazTeknikDTO> cihazDegerList = new ArrayList<CihazTeknikDTO>();
					try {
						cihazDegerList = new AsansorBusiness()
								.getCihazKapasite(c.getCihazId());
					} catch (ReadException e) {
						e.printStackTrace();
					}
					for (CihazTeknikDTO ct : cihazDegerList) {
						try {
							if (c.getTip() == 66) {
								if (ct.getDegerId() == 157) {
									bd.setKapasiteDurak(Integer.parseInt(ct
											.getCevap()));

								}
								if (ct.getDegerId() == 158) {
									bd.setKapasiteAgirlik(Integer.parseInt(ct
											.getCevap()));

								}

							}
							if (c.getTip() == 68) {
								if (ct.getDegerId() == 182) {
									bd.setKapasiteDurak(Integer.parseInt(ct
											.getCevap()));

								}
								if (ct.getDegerId() == 174) {
									bd.setKapasiteAgirlik(Integer.parseInt(ct
											.getCevap()));

								}
							}
						} catch (NumberFormatException e) {

						}
					}

					baList.add(bd);
				}

			}
			try {
				baList = new FirmaBusiness().getAsansorBakimci(baList,
						new ArrayList<BasvuruAsansorDTO>(), 0);
				basvuru.setAsansorList(baList);
				basvuru.setFirmaList(new RandevuBean().getFirmalar(basvuru.getBasvuru().getBina().getIl()));
				basvuru.setCihazlarVisible(true);
			} catch (ReadException e) {
				msg = new FacesMessage(e.getMessage());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				context.addMessage(null, msg);
				e.printStackTrace();
			}

		}
		
		try {
			this.sessionBina=(BinaDTO)this.binaDTO.clone();
		} catch (CloneNotSupportedException e) {
			this.sessionBina=new BinaDTO();
		}
		List<RandevuListeDTO> rList = new ArrayList<RandevuListeDTO>();
		try {
			rList = new RandevuBusiness().getRandevuByBinaId(binaDTO
					.getBinaId());
			randevuListeModel = new RandevuListeDataModel(rList);
		} catch (CRUDException e) {
			randevuListeModel = new RandevuListeDataModel(rList);
		}
		
		if (FacesContext.getCurrentInstance().getViewRoot().getViewId().contains("odemeGorme")){
			OdemeBean odemeBean = (OdemeBean)
					 FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{odemeBean}",
							 OdemeBean.class);
			if (odemeBean!=null){
				odemeBean.odemeListener(binaDTO);
			}
		}

	}

	public void basvuruVeRandevular(ActionEvent event) {
		try {
			List<Randevu> list = new ArrayList<Randevu>();
			list = new BinaBusiness().getBasvuruRandevu(this.binaDTO.getBinaId());
			basvuruVeRandevular = new BasvuruRandevuModel(list);
			
			
		} catch (CRUDException e) {
			FacesMessage msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);

		}
	}

	public BasvuruRandevuModel getBasvuruVeRandevular() {
		return basvuruVeRandevular;
	}

	public void setBasvuruVeRandevular(BasvuruRandevuModel basvuruVeRandevular) {
		this.basvuruVeRandevular = basvuruVeRandevular;
	}

	public List<Randevu> getFilteredVal() {
		return filteredVal;
	}

	public void setFilteredVal(List<Randevu> filteredVal) {
		this.filteredVal = filteredVal;
	}

	public void sorumluKisiler() {

		try {
			kisiList = new BinaBusiness().binaKisiler(this.binaDTO.getBinaId());
			kisiData = new BinaKisiDataModel(kisiList);
		
		} catch (CRUDException e) {

			e.printStackTrace();
		}

	}

	public List<Mahalle> getMahalleList() {
		return mahalleList;
	}

	public void setMahalleList(List<Mahalle> mahalleList) {
		this.mahalleList = mahalleList;
	}

	public List<CaddeSokak> getCaddeSokakList() {
		return caddeSokakList;
	}

	public void setCaddeSokakList(List<CaddeSokak> caddeSokakList) {
		this.caddeSokakList = caddeSokakList;
	}

	public boolean isYeniBina() {
		return yeniBina;
	}

	public void setYeniBina(boolean yeniBina) {
		this.yeniBina = yeniBina;
	}

	public void binaSelect(AjaxBehaviorEvent event) {
		binaDegistir(new ActionEvent(event.getComponent()));
		Panel panelBina = (Panel) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(":form1:pnlBina");

		Panel panelArama = (Panel) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(":form1:binaara");
		binaDTO = new BinaDTO();
		tcKimlikNo = "";
		binaId = 0;
		binalar = new BinaDTO();
		cihazList = new ArrayList<CihazDTO>();
		ilceler=new TreeMap<String, Integer>();
		bucakKoyList = new TreeMap<String, Integer>();
		mahalleList=new ArrayList<Mahalle>();
		caddeSokakList=new ArrayList<CaddeSokak>();
		((HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest()).getSession()
				.removeAttribute("binaid");
		CihazBean cihazBean = (CihazBean) FacesContext
				.getCurrentInstance()
				.getApplication()
				.evaluateExpressionGet(FacesContext.getCurrentInstance(),
						"#{cihazBean}", CihazBean.class);
		if (cihazBean != null) {
			cihazBean.setCihazlar(new ArrayList<CihazDTO>());
			cihazBean
					.setCihazList(new CihazDataModel(new ArrayList<CihazDTO>()));
			cihazBean.setIlKodu(0);
			cihazBean.setIlceler(new TreeMap<String, Integer>());
			cihazBean.setIlceKodu(0);
			cihazBean.setBinalar(new ArrayList<BinaDTO>());
			cihazBean.setBinaId(0);

		}
		if (panelArama!=null)
		panelArama.setVisible(false);
		if (panelBina!=null)
		panelBina.setVisible(false);
		if (yeniBina) {
			panelBina.setVisible(true);
			panelArama.setVisible(false);

		} else {

			panelArama.setVisible(true);
			panelBina.setVisible(false);
			cihazList = new ArrayList<CihazDTO>();

		}

	}

	public void changeListener(AjaxBehaviorEvent event) {
		this.binaDTO=new BinaDTO();
		cihazList = new ArrayList<CihazDTO>();

	}

	public void keyDownListener(AjaxBehaviorEvent event) {
		this.binaDTO=new BinaDTO();
		cihazList = new ArrayList<CihazDTO>();

	}

	public BinaKisiDataModel getKisiData() {
		return kisiData;
	}

	public void setKisiData(BinaKisiDataModel kisiData) {
		this.kisiData = kisiData;
	}

	public List<BinaKisiDTO> getKisiList() {
		return kisiList;
	}

	public void setKisiList(List<BinaKisiDTO> kisiList) {
		this.kisiList = kisiList;
	}

	@SuppressWarnings("unused")
	public void sozlesmeBinaTipListener(AjaxBehaviorEvent event) {

		int binaTip = Integer.parseInt(((UIInput) (event.getComponent()))
				.getValue().toString());
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;
		if (((UIInput) (event.getComponent())).getId().equals("gbinatipi")){
			
			
			genelBinalar=new ArrayList<BinaDTO>();
			cihazList=new ArrayList<CihazDTO>();
			this.binaDTO=new BinaDTO();
			basvuruClear();
				
		}

		try {
			belediyeList = new ArrayList<BelediyeDTO>();
			belediyelerSize=0;
			if (((UIInput)event.getComponent())!=null && ((UIInput)event.getComponent()).getId().contains("gbinatipi")){
				belediyeList = new BelediyeBusiness().getBelediyelerByIl(genelAraBina.getIl(),
						binaTip);
			}else{
				belediyeList = new BelediyeBusiness().getBelediyelerByIl(
						binaDTO.getIl(), binaTip);
			}
			
			belediyelerSize = belediyeList.size();
			if (belediyeList.size() > 0) {
				belediyeler = new ArrayList<SelectItem>();
				for (BelediyeDTO d : belediyeList) {

					belediyeler.add(new SelectItem(d.getKod(), d.getAdi()));
				}
			} else {
				belediyeler = new ArrayList<SelectItem>();
				belediyeler.add(new SelectItem("",
						"Sözleşmeli Belediye Bulunamadı"));
				context.addMessage(null, new FacesMessage(
						"Sözleşmeli Belediye Bulunamadı!!!"));
			}
		} catch (ReadException e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));

		}
	}

	public int getBelediyelerSize() {
		return belediyelerSize;
	}

	public void setBelediyelerSize(int belediyelerSize) {
		this.belediyelerSize = belediyelerSize;
	}

	public OdemeAlinmayacakBinalarDTO getOabDTO() {
		return oabDTO;
	}

	public void setOabDTO(OdemeAlinmayacakBinalarDTO oabDTO) {
		this.oabDTO = oabDTO;
	}

	public void odemeAlinmamaDurumuEkle() {
		FacesContext fc = FacesContext.getCurrentInstance();
		FacesMessage fm;
		try {
			new BinaBusiness().odemeAlinmamaDurumuEkle(binaDTO.getBinaId(),
					oabDTO.getAciklama());
			getOdemeAlinmamaDurumlari();
			fm = new FacesMessage(Messages._SQL_513_.getMesaj());
			fm.setSeverity(FacesMessage.SEVERITY_INFO);
		} catch (CRUDException e) {
			e.printStackTrace();
			fm = new FacesMessage(Messages._SQL_512_.getMesaj());
			fm.setSeverity(FacesMessage.SEVERITY_INFO);
		}
		fc.addMessage(null, fm);
	}

	public void odemeAlinmamaDurumIptal(OdemeAlinmayacakBinalarDTO oab) {
		FacesContext fc = FacesContext.getCurrentInstance();
		FacesMessage fm;
		try {
			new BinaBusiness().odemeAlinmamaDurumuIptal(oab.getId());
			getOdemeAlinmamaDurumlari();
			fm = new FacesMessage(Messages._SQL_513_.getMesaj());
			fm.setSeverity(FacesMessage.SEVERITY_INFO);
		} catch (CRUDException e) {
			e.printStackTrace();
			fm = new FacesMessage(Messages._SQL_512_.getMesaj());
			fm.setSeverity(FacesMessage.SEVERITY_INFO);
		}
		fc.addMessage(null, fm);
	}

	public void getOdemeAlinmamaDurumlari() {
		odemeAlinmazList = new ArrayList<OdemeAlinmayacakBinalarDTO>();
		try {
			odemeAlinmazList = new BinaBusiness()
					.getOdemeAlinmamaDurumu(binaDTO.getBinaId());
		} catch (CRUDException e) {
			e.printStackTrace();
		}
	}

	public List<OdemeAlinmayacakBinalarDTO> getOdemeAlinmazList() {
		return odemeAlinmazList;
	}

	public void setOdemeAlinmazList(
			List<OdemeAlinmayacakBinalarDTO> odemeAlinmazList) {
		this.odemeAlinmazList = odemeAlinmazList;
	}

	public RandevuListeDataModel getRandevuListeModel() {
		return randevuListeModel;
	}

	public void setRandevuListeModel(RandevuListeDataModel randevuListeModel) {
		this.randevuListeModel = randevuListeModel;
	}

	public RandevuListeDTO getRandevuListe() {
		return randevuListe;
	}

	public void setRandevuListe(RandevuListeDTO randevuListe) {
		this.randevuListe = randevuListe;
	}

	

	public List<EskiRaporDTO> getEskiRaporList() {
		return eskiRaporList;
	}

	public void setEskiRaporList(List<EskiRaporDTO> eskiRaporList) {
		this.eskiRaporList = eskiRaporList;
	}

	public void eskiRaporlarListener(ActionEvent event) {
		CihazBean cihazBean = (CihazBean) FacesContext
				.getCurrentInstance()
				.getApplication()
				.evaluateExpressionGet(FacesContext.getCurrentInstance(),
						"#{cihazBean}", CihazBean.class);
		FacesMessage msg = new FacesMessage();
		if (cihazBean.getSecilen() != null
				&& cihazBean.getSecilen().getCihazId() > 0) {
			try {
				if (this.binaDTO==null || this.binaDTO.getBinaId()<=0){
					try {
						this.binaDTO=(BinaDTO)sessionBina.clone();
					} catch (CloneNotSupportedException e) {
						this.binaDTO=new BinaDTO();
					}
				}
				eskiRaporList = new RaporBusiness().eskiRaporGetir(this.binaDTO
						.getBinaId(), cihazBean.getSecilen().getCihazId());
				
			} catch (CRUDException e) {
				msg = new FacesMessage(e.getMessage());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, msg);

			}
		} else {
			msg = new FacesMessage(Messages._CIHAZSECMEDINIZ_.getMesaj());
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void eskiRaporGetir(EskiRaporDTO eskiRaporDTO) {
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		try {
			boolean eskiRapor = false;
			eskiRapor = new RaporBusiness().isEskiRaporMu(eskiRaporDTO
					.getRaporId());
			// try {
			// if
			// (eskiRaporDTO.getRaporTarih().compareTo(df.parse("08.12.2014"))
			// == -1) {
			if (eskiRapor) {
				diger(eskiRaporDTO);

			} else {
				HttpServletRequest request = (HttpServletRequest) FacesContext
						.getCurrentInstance().getExternalContext().getRequest();
				request.getSession().setAttribute("dosya",
						eskiRaporDTO.getDosya());
				request.getSession().setAttribute("dosyaAdi",
						eskiRaporDTO.getDosyaAdi());

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
			 * 
			 * e.printStackTrace(); }
			 */
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}
	}

	@SuppressWarnings("unused")
	private void diger(EskiRaporDTO eskiRaporDTO) {

		String path = "//var//pdf//";

		String raporAdi = "";
		int binaTescilNo = 0;
		if (this.sessionBina.getBinaId()>0){
			try{
			this.binaDTO=(BinaDTO) sessionBina.clone();
			this.binalar=(BinaDTO) sessionBina.clone();
		} catch (CloneNotSupportedException e) {
			this.binalar=new BinaDTO();
			this.binaDTO=new BinaDTO();
		}
		}
		if(binaDTO != null ){
		for (EskiRaporDTO e : eskiRaporList) {
			e.setBinaTescilNo(binaDTO.getTescilNo());
			if (e.getRaporId() == eskiRaporDTO.getRaporId()) {
				binaTescilNo = e.getBinaTescilNo();
				raporAdi = e.getBinaTescilNo() + "__" + e.getBasvuruId() + "__"
						+ e.getEskiKontrolKodu() + ".pdf";

				break;
			}
		}
		}else{
			System.out.println("BinaBean @diger error ");
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

	public HashMap<Integer, List<File>> getFileMap() {
		return fileMap;
	}

	public void setFileMap(HashMap<Integer, List<File>> fileMap) {
		this.fileMap = fileMap;
	}

	public boolean isSearchType() {
		return searchType;
	}

	public void setSearchType(boolean searchType) {
		this.searchType = searchType;
	}

	public void searchTypeListener(AjaxBehaviorEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();

		String viewId = context.getViewRoot().getViewId();
		String formId;
		try {
			formId = viewId.equals("/bina.xhtml") ? ":formbina"
					: (viewId.equals("/belediyebina.xhtml")?"formbina": (viewId.equals("/binakayit.xhtml") ? ":form1:accordionSearch"
							: (viewId.equals("/raporteslim.xhtml") ? ":formrapor"
									: (viewId.equals("/odemeGiris.xhtml") ? "formodeme"
											: (viewId
													.equals("/odemeGorme.xhtml") ? ":formodeme"
													: (viewId
															.equals("/kontrol.xhtml") ? ":formbina"
															: (viewId
																	.equals("/asansor.xhtml") ? ":form1"
																	: "")))))));
			StringBuilder sb = new StringBuilder();
			sb.append(formId).append(":binacomplete");
			AutoComplete oc = (AutoComplete) context.getViewRoot()
					.findComponent(sb.toString());
			if (searchType) {

				oc.setPlaceholder("Mahalle ve/veya cadde ismi girerek arama yapabilirsiniz");
			} else {
				oc.setPlaceholder("Bina adının ilk üç harfini girerek arama yapabilirsiniz");
			}
		} catch (NullPointerException e) {

		}

	}

	public List<BinaDTO> searchBina(String prefix) {
		if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
				.equals("/kontrol.xhtml")) {
			try {
				SoruTableBean soruTableBean = (SoruTableBean) FacesContext
						.getCurrentInstance().getViewRoot().getViewMap()
						.get("soruTableBean");

				soruTableBean.setSoruListSmall(new ArrayList<AnaSoru>());
				soruTableBean.setSecilen(new CihazDTO());
				soruTableBean.setCihazlar(new ArrayList<CihazDTO>());

				soruTableBean.setKontrolDTO(new KontrolDTO());

				
			} catch (Exception e) {

			}

		}
		if (searchType)
			return completeWithAdres(prefix);
		else
			return complete(prefix);

	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	private double lat;
	private double lon;

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public void onPointSelect(PointSelectEvent event) {
		LatLng latlng = event.getLatLng();
		lat = latlng.getLat();
		lon = latlng.getLng();

		// FacesContext.getCurrentInstance().addMessage(
		// null,
		// new FacesMessage(FacesMessage.SEVERITY_INFO, "Point Selected",
		// "Lat:" + latlng.getLat() + ", Lng:" + latlng.getLng()));
	}

	public void setCoordinat(ActionEvent event) {
		try {
			new BinaBusiness().setCoordinat(binaDTO.getBinaId(), lat, lon);
			mapCenter = new StringBuilder().append(lat).append(",").append(lon)
					.toString();
			emptyModel = new DefaultMapModel();

			LatLng coord1 = new LatLng(lat, lon);

			emptyModel.addOverlay(new Marker(coord1, binaDTO.getBinaAdi()));

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"İşlem Tamam", Messages._SQL_513_.getMesaj()));
		} catch (UpdateException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Güncelleme Hatası", e.getMessage()));
		}

	}

	private MapModel emptyModel = new DefaultMapModel();

	public MapModel getEmptyModel() {
		return emptyModel;
	}

	public void setEmptyModel(MapModel emptyModel) {
		this.emptyModel = emptyModel;
	}

	public String getMapCenter() {
		return mapCenter;
	}

	public void setMapCenter(String mapCenter) {
		this.mapCenter = mapCenter;
	}

	private void setMap() {
		emptyModel = new DefaultMapModel();

		LatLng coord1 = new LatLng(38.35888785866677, 27.13201940059662);
		if (binaDTO != null && binaDTO.getIl() > 0) {
			IlIlceDTO i = new IlIlceDTO();
			try {
				i = ApplicationDAOImpl.getINSTANCE().getIlByIlKodu(
						binaDTO.getIl());
			} catch (ReadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			coord1 = new LatLng(i.getEnlem(), i.getBoylam());

		}
		mapCenter = new StringBuilder().append(coord1.getLat()).append(",")
				.append(coord1.getLng()).toString();
		// Basic marker
		emptyModel.addOverlay(new Marker(coord1, ""));
	}

	public List<BinaDTO> getFilteredSearch() {
		return filteredSearch;
	}

	public void setFilteredSearch(List<BinaDTO> filteredSearch) {
		this.filteredSearch = filteredSearch;
	}

	public char getBinaGroup(BinaDTO bina) {
		return bina.getBinaAdi().charAt(0);
	}

	public List<FirmaDTO> getBakimciFirmalar() {
		return bakimciFirmalar;
	}

	public void setBakimciFirmalar(List<FirmaDTO> bakimciFirmalar) {
		this.bakimciFirmalar = bakimciFirmalar;
	}

	public int getTescilno() {
		return tescilno;
	}

	public void setTescilno(int tescilno) {
		this.tescilno = tescilno;
	}

	public void getMahalleList(int koyKodu) throws CRUDException {
		// List<UavtBelediyeDTO> uavtBelediyeList = new
		// ArrayList<UavtBelediyeDTO>();
		mahalleList = new ArrayList<Mahalle>();
		// uavtBelediyeList =
		// ApplicationDAOImpl.getINSTANCE().getUavtBelediye(ilKodu, ilceKodu);

		// for (UavtBelediyeDTO uavt : uavtBelediyeList) {
		List<Mahalle> lst = new ArrayList<Mahalle>();
		// lst =
		// ApplicationDAOImpl.getINSTANCE().getMahalleByKoyKodu(uavt.getKoyKod());
		lst = ApplicationDAOImpl.getINSTANCE().getMahalleByKoyKodu(koyKodu);
		for (Mahalle m : lst) {
			mahalleList.add(m);
		}
		// }
		HashMap<Integer, Mahalle> mahalleMap = new HashMap<Integer, Mahalle>();
		for (Mahalle m : mahalleList) {
			mahalleMap.put(m.getKod(), m);
		}
		mahalleList = new ArrayList<Mahalle>();
		Set<Integer> set = mahalleMap.keySet();
		Iterator<Integer> iter = set.iterator();
		while (iter.hasNext()) {
			int i = iter.next();
			mahalleList.add(mahalleMap.get(i));
		}
		Collections.sort(mahalleList, new MahalleComparator());

	}

	public void csbmBagliBinalar(ActionEvent event) {
		try {

			int csbmKod = 0;
			String caddeSokakAdi;
			if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
					.contains("basvuru")
					|| FacesContext.getCurrentInstance().getViewRoot()
							.getViewId().contains("tarama")) {
				BasvuruBean basvuruBean = (BasvuruBean)
						 FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{basvuruBean}",
						 BasvuruBean.class);
				caddeSokakAdi = basvuruBean.getBasvuru().getBina()
						.getCaddeSokak();
			} else {
				caddeSokakAdi = this.binaDTO.getCaddeSokak();
			}
			for (CaddeSokak cs : caddeSokakList) {
				if (cs.getAd().equals(caddeSokakAdi)) {
					csbmKod = cs.getKod();
					break;
				}
			}
			uavtBinaList = new UavtBinaDataModel(new ArrayList<UavtBina>());
			ServisSonucOfArrayOfBina8Zb117HL servisSonucOfArrayOfBina = new WebServiceBusiness()
					.csbmBagliBinaSorgulama(csbmKod);
			if (!servisSonucOfArrayOfBina.isHata()) {
				List<Bina> list = new ArrayList<Bina>();
				list = servisSonucOfArrayOfBina.getSonuc().getValue().getBina();

				List<UavtBina> uavtdenEklenecek = new ArrayList<UavtBina>();
				List<UavtBina> binaList = new ArrayList<UavtBina>();
				binaList = new BinaBusiness()
						.getUavtBinaTableByCsbmKod(csbmKod);
				for (Bina b : list) {
					UavtBina uavtGelen = new UavtBina(b);
					boolean var = false;
					for (UavtBina u : binaList) {
						if (u.getKod() == uavtGelen.getKod()) {
							var = true;
						}
					}
					if (!var) {
						uavtdenEklenecek.add(uavtGelen);
					}
				}

				if (uavtdenEklenecek.size() > 0) {
					new BinaBusiness().setBinaFromUavt(uavtdenEklenecek);

					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(Messages._SQL_513_.getMesaj()
									+ " " + uavtdenEklenecek.size()
									+ " adet bina eklendi"));
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(Messages._EKLENECEKBINAYOK_
									.getMesaj()));
				}

			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(servisSonucOfArrayOfBina.getMesaj()
								.getValue()));
			}
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}

	}

	public void csbmBagliBinaSorgulama(ActionEvent event) {
		try {
			this.uavtBina = new UavtBina();
			int csbmKod = 0;
			String caddeSokakAdi;
			if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
					.contains("basvuru")
					|| FacesContext.getCurrentInstance().getViewRoot()
							.getViewId().contains("tarama")) {
				BasvuruBean basvuruBean = (BasvuruBean)
						 FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{basvuruBean}",
						 BasvuruBean.class);
				caddeSokakAdi = basvuruBean.getBasvuru().getBina()
						.getCaddeSokak();
			} else {
				caddeSokakAdi = this.binaDTO.getCaddeSokak();
			}
			for (CaddeSokak cs : caddeSokakList) {
				if (cs.getAd().equals(caddeSokakAdi)) {
					csbmKod = cs.getKod();
					break;
				}
			}
			uavtBinaList = new UavtBinaDataModel(new ArrayList<UavtBina>());
			List<UavtBina> binaList = new ArrayList<UavtBina>();
			binaList = new BinaBusiness().getUavtBinaTableByCsbmKod(csbmKod);
			uavtBinaList = new UavtBinaDataModel(binaList);
			RequestContext.getCurrentInstance()
					.execute("PF('uavtBina').show()");
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}
	}

	public UavtBinaDataModel getUavtBinaList() {
		return uavtBinaList;
	}

	public void setUavtBinaList(UavtBinaDataModel uavtBinaList) {
		this.uavtBinaList = uavtBinaList;
	}

	public List<UavtBina> getUavtBinaFilter() {
		return uavtBinaFilter;
	}

	public void setUavtBinaFilter(List<UavtBina> uavtBinaFilter) {
		this.uavtBinaFilter = uavtBinaFilter;
	}

	public UavtBina getUavtBina() {
		return uavtBina;
	}

	public void setUavtBina(UavtBina uavtBina) {
		this.uavtBina = uavtBina;
	}

	public void onUavtRowSelect(SelectEvent event)
			throws CloneNotSupportedException {
		UavtBina bina = (UavtBina) event.getObject();
		if (bina != null) {
			String uavtKod = String.valueOf(bina.getKod());
			if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
					.contains("basvuru")
					|| FacesContext.getCurrentInstance().getViewRoot()
							.getViewId().contains("tarama")) {
				BasvuruBean basvuruBean = (BasvuruBean)
						 FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{basvuruBean}",
						 BasvuruBean.class);
				if (uavtKod != null) {
					basvuruBean.getBasvuru().getBina()
							.setUavtKod(Integer.parseInt(uavtKod));
				}

				basvuruBean
						.getBasvuru()
						.getBina()
						.setBinaNo(
								bina.getDisKapiNo() == null ? "" : bina
										.getDisKapiNo());
				basvuruBean
						.getBasvuru()
						.getBina()
						.setBinaAdi(
								bina.getBlokAdi() == null ? (bina.getSiteAdi() == null ? ""
										: bina.getSiteAdi())
										: bina.getBlokAdi());
				basvuruBean.getBasvuru().getBina()
						.setAda(bina.getAda() == null ? "" : bina.getAda());
				basvuruBean
						.getBasvuru()
						.getBina()
						.setPafta(
								bina.getPafta() == null ? "" : bina.getPafta());
				basvuruBean
						.getBasvuru()
						.getBina()
						.setParsel(
								bina.getParsel() == null ? "" : bina
										.getParsel());

			} else {
				if (uavtKod != null) {
					this.binaDTO.setUavtKod(Integer.parseInt(uavtKod));
				}

				this.binaDTO.setBinaNo(bina.getDisKapiNo() == null ? "" : bina
						.getDisKapiNo());
				this.binaDTO.setBinaAdi(bina.getBlokAdi() == null ? (bina
						.getSiteAdi() == null ? "" : bina.getSiteAdi()) : bina
						.getBlokAdi());
				this.binaDTO.setAda(bina.getAda() == null ? "" : bina.getAda());
				this.binaDTO.setPafta(bina.getPafta() == null ? "" : bina
						.getPafta());
				this.binaDTO.setParsel(bina.getParsel() == null ? "" : bina
						.getParsel());
			}
		}

	}

	public void ilceyeBagliMahalleSorgulama(ActionEvent event) {

		try {
			int ilceKod;
			if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
					.contains("basvuru")
					|| FacesContext.getCurrentInstance().getViewRoot()
							.getViewId().contains("tarama")) {
				BasvuruBean basvuruBean = (BasvuruBean)
						 FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{basvuruBean}",
						 BasvuruBean.class);
				ilceKod = basvuruBean.getBasvuru().getBina().getIlce();
			} else {
				ilceKod = this.binaDTO.getIlce();
			}
			ServisSonucOfArrayOfMahalle8Zb117HL servisSonucOfArrayOfMahalle = new WebServiceBusiness()
					.getMahalleOfIlceKod(ilceKod);
			if (!servisSonucOfArrayOfMahalle.isHata()) {
				List<btest.Mahalle> list = new ArrayList<btest.Mahalle>();
				list = servisSonucOfArrayOfMahalle.getSonuc().getValue()
						.getMahalle();
				List<Mahalle> uavtdenEklenecek = new ArrayList<Mahalle>();

				for (btest.Mahalle m : list) {
					Mahalle uavtGelen = new Mahalle(m);

					boolean var = false;
					for (Mahalle m1 : mahalleList) {
						if (m1.getKod() == uavtGelen.getKod())
							var = true;
					}
					if (!var) {
						uavtdenEklenecek.add(uavtGelen);
					}
				}
				if (uavtdenEklenecek.size() > 0) {
					List<UavtBelediyeDTO> uavtBelediyeList = new ArrayList<UavtBelediyeDTO>();

					uavtBelediyeList = ApplicationDAOImpl.getINSTANCE()
							.getUavtBelediye(this.binaDTO.getIl(), ilceKod);
					for (UavtBelediyeDTO ub : uavtBelediyeList) {
						for (Mahalle m : uavtdenEklenecek) {
							m.setKoyKodu(ub.getKoyKod());
						}
						break;
					}
					new BinaBusiness().setMahalleFromUavt(uavtdenEklenecek);
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(Messages._SQL_513_.getMesaj()
									+ "Listeye " + uavtdenEklenecek.size()
									+ " adet mahalle eklendi."));
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(Messages._EKLENECEKMAHALLEYOK_
									.getMesaj()));
				}
				for (Mahalle m : uavtdenEklenecek) {
					mahalleList.add(m);
				}
				Collections.sort(mahalleList, new MahalleComparator());
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(servisSonucOfArrayOfMahalle.getMesaj()
								.getValue()));
			}
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}

	}

	public void mahalleyeBagliCsbmSorgulama(ActionEvent event) {

		try {
			int mahalleKod = 0;
			String mahalleAdi;

			if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
					.contains("basvuru")
					|| FacesContext.getCurrentInstance().getViewRoot()
							.getViewId().contains("tarama")) {
				BasvuruBean basvuruBean = (BasvuruBean)
						 FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{basvuruBean}",
						 BasvuruBean.class);
				mahalleAdi = basvuruBean.getBasvuru().getBina().getMahalle();
			} else {
				mahalleAdi = this.binaDTO.getMahalle();
			}
			for (Mahalle m : mahalleList) {
				if (m.getAd().equals(mahalleAdi)) {
					mahalleKod = m.getKod();
					break;
				}
			}
			ServisSonucOfArrayOfCsbm8Zb117HL servisSonucOfArrayOfCsbm = new WebServiceBusiness()
					.getCsbmKodOfMahalleKod(mahalleKod);
			if (!servisSonucOfArrayOfCsbm.isHata()) {
				List<Csbm> list = new ArrayList<Csbm>();
				list = servisSonucOfArrayOfCsbm.getSonuc().getValue().getCsbm();
				List<CaddeSokak> uavtdenEklenecek = new ArrayList<CaddeSokak>();

				for (Csbm m : list) {
					CaddeSokak uavtGelen = new CaddeSokak(m);
					boolean var = false;
					for (CaddeSokak m1 : caddeSokakList) {
						if (m1.getKod() == uavtGelen.getKod())
							var = true;
					}
					if (!var) {
						uavtdenEklenecek.add(uavtGelen);
					}
				}
				if (uavtdenEklenecek.size() > 0) {
					new BinaBusiness().setCsbmFromUavt(uavtdenEklenecek);
					for (CaddeSokak m : uavtdenEklenecek) {
						caddeSokakList.add(m);
					}
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(Messages._SQL_513_.getMesaj()));
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(Messages._EKLENECEKCADDESOKAKYOK_
									.getMesaj()));
				}

				Collections.sort(caddeSokakList, new CaddeSokakComparator());
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(servisSonucOfArrayOfCsbm.getMesaj()
								.getValue()));
			}
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}

	}

	

	public TreeMap<String, Integer> getBucakKoyList() {
		return bucakKoyList;
	}

	public void setBucakKoyList(TreeMap<String, Integer> bucakKoyList) {
		this.bucakKoyList = bucakKoyList;
	}

	public BinaDTO getGenelAraBina() {
		return genelAraBina;
	}

	public void setGenelAraBina(BinaDTO genelAraBina) {
		this.genelAraBina = genelAraBina;
	}
	private List<BinaDTO> genelBinalar=new ArrayList<BinaDTO>();
	
	public void genelAraListener(ActionEvent event){
		
		genelBinalar=new ArrayList<BinaDTO>();
		if (
				!Util.nullif(genelAraBina.getBinaAdi()).equals("")
				||
				genelAraBina.getIl()>0 
				||
				genelAraBina.getIlce()>0 
				||
				!Util.nullif(genelAraBina.getMahalle()).equals("")
				||
				!Util.nullif(genelAraBina.getCaddeSokak()).equals("")
				||
				genelAraBina.getSozlesmeBinaTipId()>0
				||
				genelAraBina.getBelediye()>0
				||
				!Util.nullif(genelAraBina.getAda()).equals("")
				||
				!Util.nullif(genelAraBina.getPafta()).equals("")
				||
				!Util.nullif(genelAraBina.getParsel()).equals("")
				||
				genelAraBina.getFirma()>0
				){
			
		for (BinaDTO b:loginBean.getTumBinalar()){
			if (
				(
					Util.nullif(genelAraBina.getBinaAdi()).equals("") || 	
					(!Util.nullif(genelAraBina.getBinaAdi()).equals("") &&
					 b.getBinaAdi().toLowerCase().startsWith(genelAraBina.getBinaAdi().trim().toLowerCase())
					)
					)
					&&
					(genelAraBina.getIl()==0 ||
					(genelAraBina.getIl()>0 && b.getIl()==genelAraBina.getIl())
					)
					&&
					(genelAraBina.getIlce()==0 ||
					(genelAraBina.getIlce()>0 && b.getIlce()==genelAraBina.getIlce())
					)
					&&
					(Util.nullif(genelAraBina.getMahalle()).equals("") ||
							(!Util.nullif(genelAraBina.getMahalle()).equals("") && 
									!Util.nullif(b.getMahalle()).equals("") &&		
									(b.getMahalle().trim().toLowerCase().contains(genelAraBina.getMahalle().trim().toLowerCase()) || 
											genelAraBina.getMahalle().trim().toLowerCase().contains(b.getMahalle().trim().toLowerCase()) )
									)
							
					)
					&&
					(Util.nullif(genelAraBina.getCaddeSokak()).equals("") ||
							(!Util.nullif(genelAraBina.getCaddeSokak()).equals("") && !Util.nullif(b.getCaddeSokak()).equals("") && 
									(b.getCaddeSokak().trim().toLowerCase().contains(genelAraBina.getCaddeSokak().trim().toLowerCase()) || 
											genelAraBina.getCaddeSokak().trim().toLowerCase().contains(b.getCaddeSokak().trim().toLowerCase()) )
									)
							
					)
					&&
					(genelAraBina.getSozlesmeBinaTipId()==0 ||
					(genelAraBina.getSozlesmeBinaTipId()>0 && b.getSozlesmeBinaTipId()==genelAraBina.getSozlesmeBinaTipId())
					)
					&&
					(genelAraBina.getBelediye()==0 ||
					(genelAraBina.getBelediye()>0 && b.getBelediye()==genelAraBina.getBelediye())
					)
					&&
					(Util.nullif(genelAraBina.getAda()).equals("") ||
							(!Util.nullif(genelAraBina.getAda()).equals("") && !Util.nullif(b.getAda()).equals("") && 
									(b.getAda().trim().toLowerCase().contains(genelAraBina.getAda().trim().toLowerCase()) || 
											genelAraBina.getAda().trim().toLowerCase().contains(b.getAda().trim().toLowerCase()) )
									)
							
					)
					&&
					(Util.nullif(genelAraBina.getPafta()).equals("") ||
							(!Util.nullif(genelAraBina.getPafta()).equals("") && !Util.nullif(b.getPafta()).equals("") && 
									(b.getPafta().trim().toLowerCase().contains(genelAraBina.getPafta().trim().toLowerCase()) || 
											genelAraBina.getPafta().trim().toLowerCase().contains(b.getPafta().trim().toLowerCase()) )
									)
							
					)
					&&
					(Util.nullif(genelAraBina.getParsel()).equals("") ||
							(!Util.nullif(genelAraBina.getParsel()).equals("") && !Util.nullif(b.getParsel()).equals("") && 
									(b.getParsel().trim().toLowerCase().contains(genelAraBina.getParsel().trim().toLowerCase()) || 
											genelAraBina.getParsel().trim().toLowerCase().contains(b.getParsel().trim().toLowerCase()) )
									)
							
					)
					){
				genelBinalar.add(b);
			}
			
			
		
		}
		if (genelAraBina.getFirma()>0 && genelBinalar.size()>0){
			
			
			
			try {
				List<BinaDTO> list=new ArrayList<BinaDTO>();
				list = new AsansorBusiness().getCihazlar(genelBinalar,genelAraBina.getFirma());
				genelBinalar=new ArrayList<BinaDTO>();
				for (BinaDTO gb:list){
					genelBinalar.add(gb);
				}
			} catch (CRUDException e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			}
			}
		
		}
		
	
	}
	
	public void genelArama(){
		if (
				Util.nullif(genelAraBina.getBinaAdi()).equals("")
				&&
				genelAraBina.getIl()==0 
				&&
				genelAraBina.getIlce()==0 
				&&
				Util.nullif(genelAraBina.getMahalle()).equals("")
				&&
				Util.nullif(genelAraBina.getCaddeSokak()).equals("")
				&&
				genelAraBina.getSozlesmeBinaTipId()==0
				&&
				genelAraBina.getBelediye()==0
				&&
				Util.nullif(genelAraBina.getAda()).equals("")
				&&
				Util.nullif(genelAraBina.getPafta()).equals("")
				&&
				Util.nullif(genelAraBina.getParsel()).equals("")
				&&
				genelAraBina.getFirma()==0
				){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(Messages._ARAMAKRITERIGIRINIZ_.getMesaj()));
		}else{
			if (genelBinalar.size()<=0){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(Messages._SQL_506_.getMesaj()));
			}else{
				binaGenelModel=new BinaDataModel(genelBinalar);
				
				
			}
		}
		tabIndex=4;
			
		}

	public List<BinaDTO> getGenelBinalar() {
		return genelBinalar;
	}

	public void setGenelBinalar(List<BinaDTO> genelBinalar) {
		this.genelBinalar = genelBinalar;
	}

	public BinaDataModel getBinaGenelModel() {
		return binaGenelModel;
	}

	public void setBinaGenelModel(BinaDataModel binaGenelModel) {
		this.binaGenelModel = binaGenelModel;
	}
	
	public void binaListRowSelectRadioListener(SelectEvent event)
			 {
		BinaDTO bina = (BinaDTO) event.getObject();
		if (bina != null) {
		
			cihazList=new ArrayList<CihazDTO>();
				
					
					getBinaBilgileri(bina);
					this.binalar = bina;
					if (FacesContext.getCurrentInstance().getViewRoot().getViewId().contains("kontrol.xhtml")){
						getRandevularForBina(bina);
					}

			}
		
		


	}
	
	public void binaListRowSelectRadioListener1(BinaDTO bina)
	 {

if (bina != null) {

	cihazList=new ArrayList<CihazDTO>();
			
			getBinaBilgileri(bina);
			this.binalar = bina;
			
				getRandevularForBina(bina);

	}

}
	

	public void genelAramaBlurListener(AjaxBehaviorEvent event){
		
			
			genelBinalar=new ArrayList<BinaDTO>();
			cihazList=new ArrayList<CihazDTO>();
			this.binaDTO=new BinaDTO();
			basvuruClear();
	
	}
	private void basvuruClear(){
		BasvuruBean basvuruBean = (BasvuruBean)
				 FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{basvuruBean}",
				 BasvuruBean.class);
		if (basvuruBean!=null){
			basvuruBean.setFirmaList(new ArrayList<FirmaDTO>());
			basvuruBean.setAsansorData(new BasvuruAsansorDataModel(new ArrayList<BasvuruAsansorDTO>()));
			basvuruBean.setAsansorList(new ArrayList<BasvuruAsansorDTO>());
			if (basvuruBean.getBasvuru()!=null)
			basvuruBean.getBasvuru().setBina(new BinaDTO());
			basvuruBean.setSelectedAsansor(new BasvuruAsansorDTO[]{});
		}
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}
	
	public void tabChangeListener(TabChangeEvent event) {
		TabView tw=(TabView)event.getComponent();
		if (tw!=null)
        tabIndex=tw.getActiveIndex();
    }


	
	public void binaDegistir(ActionEvent event){
		this.sessionBina=new BinaDTO();
		this.firmaList=new ArrayList<FirmaDTO>();
		this.binaDTO=new BinaDTO();
		this.binalar=new BinaDTO();
		this.tcKimlikNo="";
		this.binaList=new ArrayList<BinaDTO>();
		this.binaListSmall=new BinaDataModel(new ArrayList<BinaDTO>());
		this.binaId=0;
		this.tescilno=0;
		
		this.genelAraBina=new BinaDTO();
		this.genelBinalar=new ArrayList<BinaDTO>();
		this.cihazList=new ArrayList<CihazDTO>();
		
		CihazBean cihazBean = (CihazBean) FacesContext
				.getCurrentInstance()
				.getApplication()
				.evaluateExpressionGet(FacesContext.getCurrentInstance(),
						"#{cihazBean}", CihazBean.class);
		
		if (cihazBean!=null){
			cihazBean.setCihazlar(new ArrayList<CihazDTO>());
			cihazBean.setCihazList(new CihazDataModel(new ArrayList<CihazDTO>()));
			cihazBean.setCihazTeknik(new ArrayList<CihazTeknikDTO>());
		}
		OdemeBean odemeBean = (OdemeBean) FacesContext
				.getCurrentInstance()
				.getApplication()
				.evaluateExpressionGet(FacesContext.getCurrentInstance(),
						"#{odemeBean}", OdemeBean.class);
		if (odemeBean!=null){
			odemeBean.setOdemeDataModel(new OdemeDataModel(new ArrayList<OdemeDTO>()));
			odemeBean.setOdemeYapModel(new BasvuruListeModel(new ArrayList<BasvuruListeDTO>()));
			odemeBean.setOdenenDataModel(new OdemeDataModel(new ArrayList<OdemeDTO>()));
			odemeBean.setBasvuruListeModel(new BasvuruListeModel(new ArrayList<BasvuruListeDTO>()));
			odemeBean.setBasvuruList(new ArrayList<BasvuruListeDTO>());
			odemeBean.setOdemeList(new ArrayList<OdemeDTO>());
			odemeBean.setSelectedOdenecek(new BasvuruListeDTO());
			
			odemeBean.setSelectedVal(new OdemeDTO());
			odemeBean.setBasvuru(new BasvuruListeDTO());
			odemeBean.setHeaderText("");
			odemeBean.setBasvuruId("");
			odemeBean.setOdeme(new OdemeDTO());
			odemeBean.setOdemeReq(false);
			odemeBean.setOdenenList(new ArrayList<OdemeDTO>());
			odemeBean.setOdemeYapilacakListe(new ArrayList<BasvuruListeDTO>());
		}
		
		
	}

	public BinaDTO getSessionBina() {
		return sessionBina;
	}

	public void setSessionBina(BinaDTO sessionBina) {
		this.sessionBina = sessionBina;
	}

	public List<RandevuListeDTO> getFilteredRandevuList() {
		return filteredRandevuList;
	}

	public void setFilteredRandevuList(List<RandevuListeDTO> filteredRandevuList) {
		this.filteredRandevuList = filteredRandevuList;
	}
	public List<FirmaDTO> getFirmaList() {
		Collections.sort(firmaList, new BakimciFirmaComparator());
		return firmaList;
	}

	public void setFirmaList(List<FirmaDTO> firmaList) {
		this.firmaList = firmaList;
	}
	
}
