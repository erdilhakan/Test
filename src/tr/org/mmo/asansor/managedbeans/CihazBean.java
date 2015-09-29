package tr.org.mmo.asansor.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.panel.Panel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleSelectEvent;
import org.primefaces.event.UnselectEvent;

import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.business.AsansorBusiness;
import tr.org.mmo.asansor.business.BasvuruBusiness;
import tr.org.mmo.asansor.business.BinaBusiness;
import tr.org.mmo.asansor.business.FirmaBusiness;
import tr.org.mmo.asansor.business.KontrolBusiness;
import tr.org.mmo.asansor.business.WebServiceBusiness;
import tr.org.mmo.asansor.dto.AsansorKapsamDTO;
import tr.org.mmo.asansor.dto.BasvuruAsansorDTO;
import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.dto.CihazDTO;
import tr.org.mmo.asansor.dto.CihazFirmaDTO;
import tr.org.mmo.asansor.dto.CihazTeknikDTO;
import tr.org.mmo.asansor.dto.FirmaDTO;
import tr.org.mmo.asansor.dto.KontrolDTO;
import tr.org.mmo.asansor.dto.UavtCihaz;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;
import tr.org.mmo.asansor.models.CihazDataModel;
import tr.org.mmo.asansor.models.FirmaDataModel;
import tr.org.mmo.asansor.models.KontrolDataModel;
import tr.org.mmo.asansor.models.UavtCihazDataModel;
import tr.org.mmo.asansor.util.BakimciFirmaComparator;
import tr.org.mmo.asansor.util.BinaComparator;
import tr.org.mmo.asansor.util.IlIlceComparator;
import tr.org.mmo.asansor.util.Messages;
import btest.*;

@ManagedBean(name = "cihazBean")
@ViewScoped
public class CihazBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2748727297290767728L;

	/*
	 * private InputText input; private InputMask intInput; public InputMask
	 * getIntInput() { return intInput; } public void setIntInput(InputMask
	 * intInput) { this.intInput = intInput; } private CommandButton button;
	 * private OutputLabel label; private SelectOneMenu selectMenu; private
	 * UISelectItems selectItems; private HtmlPanelGrid grid;
	 */
	private boolean sozlesmeGiris=false;
	
	private List<CihazDTO> filterCihaz;
	private List<FirmaDTO> filterFirma;
	private  FirmaDataModel firmaModel=new FirmaDataModel(new ArrayList<FirmaDTO>());
	private String binaIdString;
	private UavtCihazDataModel uavtCihazList = new UavtCihazDataModel(
			new ArrayList<UavtCihaz>());
	private List<UavtCihaz> uavtCihazFilter;
	private boolean uavtCihazEkle = false;
	private String cihazUavtKod;
	private String uavtAsansorSiraNo;
	private CihazFirmaDTO cihazFirma = new CihazFirmaDTO();
	private List<FirmaDTO> bakimciFirmalar = new ArrayList<FirmaDTO>();
	private List<Integer> tanimlanacakAsansorAdetList = new ArrayList<Integer>();
	private int tanimlanacakAsansorAdet;
	private boolean cihazUpdate = false;
	private KontrolDataModel kontroller;
	private List<KontrolDTO> filteredKontrol;
	private boolean cihazTeknikBilgilerVisible = false;
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;
	private List<FirmaDTO> firmaList = new ArrayList<FirmaDTO>();
	private boolean hizDiger=false;
	public boolean isCihazTeknikBilgilerVisible() {
		return cihazTeknikBilgilerVisible;
	}

	public void setCihazTeknikBilgilerVisible(boolean cihazTeknikBilgilerVisible) {
		this.cihazTeknikBilgilerVisible = cihazTeknikBilgilerVisible;
	}

	private int cihazId;

	public int getCihazId() {
		return cihazId;
	}

	public void setCihazId(int cihazId) {
		this.cihazId = cihazId;
	}

	private TreeMap<String, Integer> ilceler;

	public TreeMap<String, Integer> getIlceler() {
		return ilceler;
	}

	public void setIlceler(TreeMap<String, Integer> ilceler) {
		this.ilceler = ilceler;
	}

	private CihazDTO secilen;

	public CihazDTO getSecilen() {
		return secilen;
	}
	
	private List<CihazDTO> selList;
	
	public List<CihazDTO> getSelList() {
		return selList;
	}

	public void setSelList(List<CihazDTO> selList) {
		this.selList = selList;
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
	// private HtmlForm form;
	private boolean visible = false;
	private String header;

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	private int ilKodu;
	private int ilceKodu;

	public int getIlceKodu() {
		return ilceKodu;
	}

	public void setIlceKodu(int ilceKodu) {
		this.ilceKodu = ilceKodu;
	}

	private List<BinaDTO> binalar;

	@ManagedProperty("#{binaBean.binaId}")
	private int binaId;

	@ManagedProperty("#{binaBean}")
	private BinaBean binaBean;
	private List<CihazTeknikDTO> cihazTeknik;

	private boolean buttonVisibled = false;

	public int getBinaId() {
		return binaId;
	}

	public void setBinaId(int binaId) {
		this.binaId = binaId;
	}

	public List<BinaDTO> getBinalar() {
		return binalar;
	}

	public void setBinalar(List<BinaDTO> binalar) {
		this.binalar = binalar;
	}

	private CihazDTO cihaz = new CihazDTO();

	public CihazDTO getCihaz() {
		return cihaz;
	}

	public void setCihaz(CihazDTO cihaz) {
		this.cihaz = cihaz;
	}

	private boolean tanimlanacakAsansorListDisable = false;

	@PostConstruct
	public void init() {

		/*
		 * form=(HtmlForm)FacesContext.getCurrentInstance().getViewRoot().
		 * findComponent(":form2"); if (form==null){ form=new HtmlForm();
		 * form.setId("form2"); }
		 * 
		 * input=new InputText(); intInput=new InputMask();
		 * 
		 * button=new CommandButton(); label=new OutputLabel(); selectMenu=new
		 * SelectOneMenu(); selectItems=new UISelectItems();
		 */
		for (int i = 1; i < 11; i++) {
			tanimlanacakAsansorAdetList.add(i);
		}
		kontroller = new KontrolDataModel(new ArrayList<KontrolDTO>());

		visible = false;
		header = "";
		cihazTeknik = new ArrayList<CihazTeknikDTO>();

		cihazId = 0;
		cihaz = new CihazDTO();
		ilceler = new TreeMap<String, Integer>(new IlIlceComparator());
		binalar = new ArrayList<BinaDTO>();
		cihazlar = new ArrayList<CihazDTO>();
		if (this.binaBean.getSessionBina()!=null && this.binaBean.getSessionBina().getBinaId()>0) {
			binaId=this.binaBean.getSessionBina().getBinaId();
			try {
				cihazlar = new AsansorBusiness().getCihazlar(binaId);

			} catch (ReadException e) {

				e.printStackTrace();
			}
		}
		cihazList = new CihazDataModel(cihazlar);
		secilen = new CihazDTO();

		BinaDTO bina = (BinaDTO) ((HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest())
				.getSession().getAttribute("bina");
		this.cihaz = (CihazDTO) ((HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest())
				.getSession().getAttribute("cihaz");
		this.cihaz = this.cihaz == null ? new CihazDTO() : this.cihaz;
		if (bina != null && cihaz != null) {
			tanimlanacakAsansorListDisable = true;
			

			binaBean.setBinalar(bina);
			binaId = bina.getBinaId();

			cihazId = cihaz.getCihazId();

			uavtAsansorSiraNo = String.valueOf(cihaz.getUavtSiraNo());
			cihazUavtKod = String.valueOf(cihaz.getUavtKod());
			binaIdString = String.valueOf(bina.getBinaId());
			cihazUpdate = true;
			this.secilen=cihaz;
			formDuzenle();
		} else {
			tanimlanacakAsansorListDisable = false;
			tanimlanacakAsansorAdet = 1;
		}

		((HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest()).getSession()
				.removeAttribute("bina");
		((HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest()).getSession()
				.removeAttribute("cihaz");
		if (binaBean!=null && binaBean.getSessionBina()!=null && binaBean.getSessionBina().getBinaId()>0){
			binaIdString=String.valueOf(binaBean.getSessionBina().getBinaId());
		}
	}

	public String formDuzenle() {
		header = "";
		visible = false;
		hizDiger=false;
		kapasiteKisi=false;
		try {

			cihazTeknik = new AsansorBusiness().getTeknikOzellikler(
					cihaz.getTip(), cihazId);
			
			header = (cihaz.getTip() == 66 ? "ELEKTRİKLİ" : "HİDROLİK")
					+ " ASANSÖR TEKNİK BİLGİLERİ";
			if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
					.equals("/basvuru.xhtml")
					&& !((LoginBean) Common.findBean("loginBean")).isLoggedIn()) {
				List<CihazTeknikDTO> cTeknikList = new ArrayList<CihazTeknikDTO>();

				for (CihazTeknikDTO c : cihazTeknik) {
					if (c.getBaslik().indexOf("DURAK SAYISI") > -1
							|| c.getBaslik().indexOf("KAPASİTE") > -1
							|| c.getBaslik().indexOf("ASANSÖRÜN YERİ") > -1
							|| c.getBaslik().indexOf("ASANSÖRÜN CİNSİ") > -1) {
						cTeknikList.add(c);
					}
				}
				cihazTeknik = new ArrayList<CihazTeknikDTO>();
				for (CihazTeknikDTO c : cTeknikList) {
					cihazTeknik.add(c);
				}
			}
			cihazTeknik = cihazTeknik == null ? new ArrayList<CihazTeknikDTO>()
					: cihazTeknik;
			for (CihazTeknikDTO c : cihazTeknik) {
				
				if (c.getBaslik().indexOf("DURAK SAYISI") > -1
						|| c.getBaslik().indexOf("KAPASİTE(KG)") > -1 
						|| c.getBaslik().indexOf("ASANSÖRÜN YERİ") > -1 
						|| c.getBaslik().indexOf("ASANSÖRÜN CİNSİ") > -1) {
					c.setZorunluAlan(true);
				}
				if (c.getBaslik().indexOf("ASANSÖRÜN CİNSİ")>-1){
					this.kapasiteKisi=c.getCevap().trim().equalsIgnoreCase("İNSAN")?true:false;
					
					
				}
			
				if (c.getTipi().equals("coklu")) {
					HashMap<String, String> m = new HashMap<String, String>();
					if (c.getBaslik().indexOf("ÖZEL") > -1){
						if (c.getCevap()!=null && c.getCevap().trim().length()>0){
							c.setOzelAsansorSartlar(c.getCevap().split(";"));						}
						
					}
					if (c.getDegerId()==160 || c.getDegerId()==179){
						for (String d : c.getDeger().split(";")) {
							m.put(d, d);

						}
						m.put(c.getCevap(),c.getCevap());
					}else{
					if (c.getDegerId()!=166 && c.getDegerId()!=178){
					

					for (String d : c.getDeger().split(";")) {
						m.put(d, d);

					}

					
					}else{
						
						for (AsansorKapsamDTO a:sessionBean.getKapsamTurleri()){
							if (a.getCihazTipi()==cihaz.getTip()){
								m.put(a.getKapsamAdi().trim(),a.getKapsamAdi().trim());
							}
						}
					}
					}
					c.getItem().putAll(m);
				
			}
			
				
			}
			visible = cihazTeknik.size() > 0 ? true : false;

		} catch (NumberFormatException e) {

			e.printStackTrace();
		} catch (CRUDException e) {
			e.printStackTrace();
		}

		return "";
	}

	public void ilChange(AjaxBehaviorEvent event) {
		visible = false;
		int ilKodu = Integer.parseInt(((UIInput) (event.getComponent()))
				.getValue().toString());

		ilceler = new TreeMap<String, Integer>(new IlIlceComparator());

		if (ilKodu != 0) {
			ApplicationBean bean = (ApplicationBean) FacesContext
					.getCurrentInstance().getExternalContext()
					.getApplicationMap().get("applicationBean");

			ilceler.putAll(bean.getIlceler().get(ilKodu));

		}

	}

	public void ilceChange(AjaxBehaviorEvent event) {

		FacesContext context = FacesContext.getCurrentInstance();
		int ilceKodu = Integer.parseInt(((UIInput) (event.getComponent()))
				.getValue().toString());

		try {
			binalar = ilceKodu == 0 ? new ArrayList<BinaDTO>()
					: new BinaBusiness().binaBul(ilKodu, ilceKodu);

			Collections.sort(binalar, new BinaComparator());

		} catch (ReadException e) {
			FacesMessage msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			context.addMessage(null, msg);
		}

	}

	public void binaChange(AjaxBehaviorEvent event) {

	}

	public List<CihazTeknikDTO> getCihazTeknik() {
		return cihazTeknik;
	}

	public void setCihazTeknik(List<CihazTeknikDTO> cihazTeknik) {
		this.cihazTeknik = cihazTeknik;
	}

	public int getIlKodu() {
		return ilKodu;
	}

	public void setIlKodu(int ilKodu) {
		this.ilKodu = ilKodu;
	}

	public String cihazSil() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;

		try {
			List<KontrolDTO> cihazKontroller = new ArrayList<KontrolDTO>();
			cihazKontroller = new KontrolBusiness().getCihazKontroller(cihazId);
			if (cihazKontroller != null && cihazKontroller.size() > 0) {
				msg = new FacesMessage(
						Messages._CIHAZAAITKONTROLVAR_.getMesaj());
			} else {
				BasvuruAsansorDTO basvuruAsansor = new BasvuruAsansorDTO();
				basvuruAsansor = new BasvuruBusiness().basvuruVarmi(cihazId);
				if (basvuruAsansor.getBasvuruId() > 0) {
					msg = new FacesMessage(
							Messages._CIHAZAAITBASVURUVAR_.getMesaj());
				} else {
					new AsansorBusiness().cihazSil(cihazId);
					try {
						cihazlar = new AsansorBusiness().getCihazlar(binaId);

					} catch (ReadException e) {

					
					}
					cihazList=new CihazDataModel(cihazlar);
					secilen=new CihazDTO();
					msg = new FacesMessage(Messages._CIHAZSILINDI_.getMesaj());
					cihazUpdate = false;
					cihazTeknik = new ArrayList<CihazTeknikDTO>();
					ilceler = new TreeMap<String, Integer>(
							new IlIlceComparator());
					binalar = new ArrayList<BinaDTO>();
					cihaz = new CihazDTO();
					ilKodu = 0;
					binaId = 0;
					cihazId = 0;
					tanimlanacakAsansorAdet = 1;
					visible = false;
					tanimlanacakAsansorListDisable = false;
					uavtAsansorSiraNo = "";
					cihazUavtKod = "";
					binaIdString = "";
				}
			}

			msg.setSeverity(FacesMessage.SEVERITY_INFO);

		} catch (CRUDException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);

		}
		context.addMessage(null, msg);

		return "";

	}

	public String kaydet() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;

		try {
			List<CihazTeknikDTO> l = new ArrayList<CihazTeknikDTO>();

			for (CihazTeknikDTO c : cihazTeknik) {
				
				if ( c.getDegerId() == 156 || c.getDegerId()==189 ){
					c.setCevap("");
					if (c.getOzelAsansorSartlar()!=null){
					StringBuilder sb=new StringBuilder();
					int sw=0;
					for (String s:c.getOzelAsansorSartlar()){
						if (sw==0){
							sb.append(s);
							sw++;
						}else{
							sb.append(";").append(s);
						}
						
					}
					c.setCevap(sb.toString());
				}
				}
				
				if (c.getCevap() != null && !c.getCevap().trim().equals("")) {
					l.add(c);
				}
				
			}
			

			binaId = binaId == 0 ? binaBean.getBinalar().getBinaId() : binaId;
			ilKodu = ilKodu == 0 ? binaBean.getBinalar().getIl() : ilKodu;
			ilceKodu = ilceKodu == 0 ? binaBean.getBinalar().getIlce()
					: ilceKodu;
			String ilceAdi = "";

			ApplicationBean bean = (ApplicationBean) FacesContext
					.getCurrentInstance().getExternalContext()
					.getApplicationMap().get("applicationBean");
			HashMap<String, Integer> ilceMap = bean.getIlceler().get(ilKodu);

			Set<String> key = ilceMap.keySet();
			Iterator<String> iter = key.iterator();
			while (iter.hasNext()) {
				String s = iter.next();
				if (ilceMap.get(s) == ilceKodu) {
					ilceAdi = s;
					break;
				}
			}
			if (l.size() > 0) {

				if (cihazUpdate) {
					long uavtKod = 0;
					try {
						uavtKod = Long.parseLong(cihazUavtKod==null?"0":(cihazUavtKod.trim().equals("")?"0":cihazUavtKod.trim()));
					
							this.cihaz.setUavtKod(uavtKod);
							this.cihaz.setUavtSiraNo(Integer
									.parseInt(uavtAsansorSiraNo==null?"0":(uavtAsansorSiraNo.trim()==""?"0":uavtAsansorSiraNo.trim())));
							new AsansorBusiness().cihazGuncelle(this.cihaz, l,
									uavtCihazEkle, binaBean.getBinalar()
											.getUavtKod());
							cihazUpdate = false;
							binaBean
									.setBinalar(new BinaDTO());
						
					} catch (Exception e) {
						if (e.getCause()
								.getMessage()
								.contains(
										"duplicate key value violates unique constraint")) {
							throw new UpdateException(
									Messages._SQL_515_.getMesaj(), e);
						} else
							throw new CRUDException(
									Messages._UAVTKODBOSOLAMAZ_.getMesaj(),
									null);
					}

				} else {
					int asansorSiraNo = 1;
					int sw = 0;
					for (CihazDTO c : cihazlar) {
						if (sw == 0) {
							asansorSiraNo = c.getUavtSiraNo();
							sw++;
						} else {
							if (asansorSiraNo < c.getUavtSiraNo()) {
								asansorSiraNo = c.getUavtSiraNo();
							}
						}

					}
					asansorSiraNo++;

					new AsansorBusiness().cihazEkle(tanimlanacakAsansorAdet,
							this.cihaz, binaId, ilKodu, ilceKodu, ilceAdi
									.trim(), l, binaBean.getBinalar()
									.getUavtKod(), asansorSiraNo);
				}
				cihazlar=new ArrayList<CihazDTO>();
				try {
					cihazlar = new AsansorBusiness().getCihazlar(binaId);

				} catch (ReadException e) {

				
				}
				cihazList=new CihazDataModel(cihazlar);
				secilen=new CihazDTO();
				msg = new FacesMessage(Messages._CIHAZKAYDEDILDI_.getMesaj());
				
				cihazUpdate = false;
				cihazTeknik = new ArrayList<CihazTeknikDTO>();
				ilceler = new TreeMap<String, Integer>(new IlIlceComparator());
				cihaz = new CihazDTO();
				binalar = new ArrayList<BinaDTO>();
				ilKodu = 0;
				cihazId = 0;
				binaId = 0;
				uavtAsansorSiraNo = "";
				cihazUavtKod = "";
				tanimlanacakAsansorAdet = 1;
				visible = false;
				tanimlanacakAsansorListDisable = false;

			} else {
				msg = new FacesMessage(Messages._CIHAZDEGERGIRINIZ.getMesaj());
			}

			msg.setSeverity(FacesMessage.SEVERITY_INFO);

		} catch (CRUDException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);

		} catch (Exception e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);

		}
		context.addMessage(null, msg);

		return "";

	}

	public static ValueExpression createValueExpression(String valueExpression,
			Class<?> valueType) {
		FacesContext context = FacesContext.getCurrentInstance();
		return context
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(context.getELContext(), valueExpression,
						valueType);

	}

	public void submit() {
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
				.put("cihazBean", this);

	}

	public String onRowSelect(SelectEvent event) {

		FacesContext context = FacesContext.getCurrentInstance();
		String cihazId = String.valueOf(((CihazDTO) event.getObject())
				.getCihazId());
		String cihazTuru = String.valueOf(((CihazDTO) event.getObject())
				.getTip());

		if (cihazId != null && cihazTuru != null) {
			try {

				secilen.setCihaz(new AsansorBusiness().getCihaz(
						Integer.parseInt(cihazId), Integer.parseInt(cihazTuru)));
				/*
				StringBuilder sb = new StringBuilder();
				sb.append(secilen.getCihazId());
				sb.append(" NO' LU ");
				sb.append(secilen.getTipAciklama());

				header = sb.toString();
				*/
				buttonVisibled = secilen.getCihaz().size() > 0 ? true : false;

			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (CRUDException e) {

				FacesMessage msg = new FacesMessage(e.getMessage());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				context.addMessage(null, msg);
			}
		}
		return "";

	}

	public void onRowUnselect(UnselectEvent event) {

	}

	public boolean isButtonVisibled() {
		return buttonVisibled;
	}

	public void setButtonVisibled(boolean buttonVisibled) {
		this.buttonVisibled = buttonVisibled;
	}

	public void asansorGuncelle(CihazDTO cihaz) {
		FacesContext context = FacesContext.getCurrentInstance();

		
		if (binaBean != null) {
			((HttpServletRequest) context.getExternalContext().getRequest())
					.getSession().setAttribute("bina", binaBean.getBinaDTO());
			((HttpServletRequest) context.getExternalContext().getRequest())
					.getSession().setAttribute("cihaz", cihaz);
		}

		RequestContext requestContext = RequestContext.getCurrentInstance();
		/*
		try {
			context.getExternalContext().redirect("asansor.jsf?faces-redirect=true");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		requestContext.execute("window.open('asansor.jsf')");

	}
	
	
	
	
	public void asansorGuncelle(SelectEvent event) {
		CihazDTO cihaz=(CihazDTO)event.getObject();
		if (cihaz!=null){

			tanimlanacakAsansorListDisable = true;
			this.cihaz=cihaz;
			header="";
			cihazTeknik=new ArrayList<CihazTeknikDTO>();
			binaBean.setBinalar(binaBean.getBinaDTO());
			binaId = binaBean.getBinaDTO().getBinaId();

			cihazId = cihaz.getCihazId();
			
		
			cihazList = new CihazDataModel(cihazlar);
			uavtAsansorSiraNo = String.valueOf(cihaz.getUavtSiraNo());
			cihazUavtKod = String.valueOf(cihaz.getUavtKod());
			binaIdString = String.valueOf(binaBean.getBinaDTO().getBinaId());
			cihazUpdate = true;
			formDuzenle();
		
		}

	}
	private int getKapsam(String kapsam){
		List<AsansorKapsamDTO> l = new ArrayList<AsansorKapsamDTO>();
		int kapsamID=0;
		l = ((SessionBean) Common.findBean("sessionBean"))
				.getKapsamTurleri();
		for (AsansorKapsamDTO a : l) {
				if (a.getKapsamAdi().trim().toLowerCase().equals(kapsam.trim().toLowerCase())){
					kapsamID=a.getId();
					break;
				}
				
			}
		return kapsamID;
		}
	public void asansorGuncelle(ActionEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;
		SoruTableBean soruTableBean = (SoruTableBean) context.getApplication()
				.evaluateExpressionGet(FacesContext.getCurrentInstance(),
						"#{soruTableBean}", SoruTableBean.class);
		if (this.cihazTeknik != null && this.cihazTeknik.size() > 0
				&& soruTableBean != null && soruTableBean.getSecilen() != null
				&& soruTableBean.getSecilen().getCihazId() > 0) {

			cihazId = soruTableBean.getSecilen().getCihazId();
			this.cihaz.setCihazId(cihazId);
			try {
				List<CihazTeknikDTO> l = new ArrayList<CihazTeknikDTO>();

				for (CihazTeknikDTO c : cihazTeknik) {
					if (c.isZorunluAlan() && (c.getCevap()==null || c.getCevap().trim().length()<=0 || c.getCevap().trim().equals("0"))){
						throw new CRUDException(Messages._ZORUNLUALANLARIDOLDURUNUZ_.getMesaj(), null);
					}
					if (c.getDegerId()==156 || c.getDegerId()==189){
						c.setCevap("");
					if (c.getOzelAsansorSartlar()!=null){
						StringBuilder sb=new StringBuilder();
						int sw=0;
						for (String s:c.getOzelAsansorSartlar()){
							if (sw==0){
								sb.append(s);
								sw++;
							}else{
								sb.append(";").append(s);
							}
							
						}
						c.setCevap(sb.toString());
					}
					}
					if (c.getCevap() != null && !c.getCevap().trim().equals("")) {
						switch (c.getKod()) {
						case 1602: //Elektrikli Asansör Asansörün Yeri
							this.cihaz.setAsansorunYeri(c.getCevap().trim());
							break;
						case 1622: //Hidrolik Asansör Asansörün Yeri
							this.cihaz.setAsansorunYeri(c.getCevap().trim());
							break;
						case 1617: //Elektrikli Asansör Kapsam
							this.cihaz.setKapsamId(getKapsam(c.getCevap()));
							break;
						case 1629: //Hidrolik Asansör Kapsam
							this.cihaz.setKapsamId(getKapsam(c.getCevap()));
							break;
						default:
							break;
						}
						l.add(c);
					}
				}
				if (l.size() > 0) {
					new AsansorBusiness().cihazGuncelleKontrolde(this.cihaz, l);
					try {
						soruTableBean.setCihazlar(new AsansorBusiness()
						.getKontrolEdilecekCihazlar(
								this.cihaz.getBinaId(), binaBean.getRandevuId()));
						soruTableBean.setCihazList(new CihazDataModel(soruTableBean.getCihazlar()));
						cihazTeknik = new AsansorBusiness().getTeknikOzellikler(
								this.cihaz.getTip(), this.cihaz.getCihazId());
						for (CihazDTO c:soruTableBean.getCihazlar()){
							if (c.getCihazId()==this.cihaz.getCihazId()){
								this.cihaz=c;
								soruTableBean.setSecilen(c);
								soruTableBean.asansorGuncellendi(c);
								break;
							}
						}
						
						
					} catch (ReadException e) {
						soruTableBean.setCihazlar(new ArrayList<CihazDTO>());
					}
					msg = new FacesMessage(
							Messages._CIHAZKAYDEDILDI_.getMesaj());

				} else {
					msg = new FacesMessage(
							Messages._CIHAZDEGERGIRINIZ.getMesaj());
				}

				msg.setSeverity(FacesMessage.SEVERITY_INFO);

			} catch (CRUDException e) {
				msg = new FacesMessage(e.getMessage());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);

			}
			context.addMessage(null, msg);

		}
	}

	public boolean isCihazUpdate() {
		return cihazUpdate;
	}

	public void setCihazUpdate(boolean cihazUpdate) {
		this.cihazUpdate = cihazUpdate;
	}

	public void asansorTeknikOzellikleri(CihazDTO cihaz)
			throws NumberFormatException, CRUDException {
		kapasiteKisi=false;
		if (cihaz != null && cihaz.getCihazId() > 0) {
			header = "KİMLİK NO :" + cihaz.getKimlikNo() + "  "
					+ (cihaz.getTip() == 66 ? "ELEKTRİKLİ" : "HİDROLİK")
					+ " ASANSÖR TEKNİK BİLGİLERİ";
			
			cihazTeknik = new AsansorBusiness().getTeknikOzellikler(
					cihaz.getTip(), cihaz.getCihazId());

			for (CihazTeknikDTO c : cihazTeknik) {
				if (FacesContext.getCurrentInstance().getViewRoot().getViewId().contains("kontrol")){
					if (c.getBaslik().indexOf("KAPSAM")>-1){
						c.setZorunluAlan(true);
					}
				}
				if (c.getBaslik().indexOf("DURAK SAYISI") > -1
						|| c.getBaslik().indexOf("KAPASİTE(KG)") > -1
						|| c.getBaslik().indexOf("ASANSÖRÜN YERİ") > -1
						|| c.getBaslik().indexOf("ASANSÖRÜN CİNSİ") > -1) {
					c.setZorunluAlan(true);
				}
				if (c.getBaslik().indexOf("ASANSÖRÜN CİNSİ")>-1){
					this.kapasiteKisi=c.getCevap().trim().equalsIgnoreCase("İNSAN")?true:false;
					
					
				}
				if (c.getTipi().equals("coklu")) {
					if (c.getBaslik().indexOf("ÖZEL") > -1){
						if (c.getCevap()!=null && c.getCevap().trim().length()>0){
							c.setOzelAsansorSartlar(c.getCevap().split(";"));						}
						
					}
					HashMap<String, String> m = new HashMap<String, String>();
if (c.getDegerId()==160 || c.getDegerId()==179){
	for (String d : c.getDeger().split(";")) {
		m.put(d, d);

	}
	m.put(c.getCevap(),c.getCevap());
}else{
					if (c.getDegerId()!=166 && c.getDegerId()!=178){
					

					for (String d : c.getDeger().split(";")) {
						m.put(d, d);

					}

					
					}else{
						for (AsansorKapsamDTO a:sessionBean.getKapsamTurleri()){
							if (a.getCihazTipi()==cihaz.getTip()){
								m.put(a.getKapsamAdi().trim(),a.getKapsamAdi().trim());
							}
						}
					}
}
					c.getItem().putAll(m);
				
				}
			}
		}
	}

	public void getAsansorTeknikOzellikleri() {

		FacesMessage msg;
		cihazTeknikBilgilerVisible = false;
		if (secilen != null && secilen.getCihazId() > 0) {
			header = "KİMLİK NO :" + secilen.getKimlikNo() + "  "
					+ (secilen.getTip() == 66 ? "ELEKTRİKLİ" : "HİDROLİK")
					+ " ASANSÖR TEKNİK BİLGİLERİ";

			try {

				cihazTeknik = new AsansorBusiness().getTeknikOzellikler(
						secilen.getTip(), secilen.getCihazId());
				
				for (CihazTeknikDTO c : cihazTeknik) {
					if (c.getBaslik().indexOf("ÖZEL") > -1){
						HashMap<String,String> m=new HashMap<String, String>();
						for (String d : c.getDeger().split(";")) {
							m.put(d, d);

						}
						if (m!=null) {
							c.getItem().putAll(m);
						}
						if (c.getCevap()!=null && c.getCevap().trim().length()>0){
							c.setOzelAsansorSartlar(c.getCevap().split(";"));						}
						
					}
				}

			} catch (NumberFormatException e) {
				msg = new FacesMessage(e.getMessage());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} catch (CRUDException e) {
				msg = new FacesMessage(e.getMessage());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

		}

		else {
			msg = new FacesMessage(Messages._CIHAZSECMEDINIZ_.getMesaj());
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public void getAsansorKontroller(ActionEvent event) {
		FacesMessage msg;

		if (secilen != null && secilen.getCihazId() > 0) {
			try {
				List<KontrolDTO> list = new ArrayList<KontrolDTO>();
				list = new KontrolBusiness().getCihazKontroller(secilen
						.getCihazId());
				for (KontrolDTO k : list) {
					k.setCihaz(secilen);

				}
				kontroller = new KontrolDataModel(list);

				
			
			} catch (CRUDException e) {
				msg = new FacesMessage(e.getMessage());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, msg);

			}
		}

		else {
			msg = new FacesMessage(Messages._CIHAZSECMEDINIZ_.getMesaj());
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public KontrolDataModel getKontroller() {
		return kontroller;
	}

	public void setKontroller(KontrolDataModel kontroller) {
		this.kontroller = kontroller;
	}

	public List<KontrolDTO> getFilteredKontrol() {
		return filteredKontrol;
	}

	public void setFilteredKontrol(List<KontrolDTO> filteredKontrol) {
		this.filteredKontrol = filteredKontrol;
	}

	public void asansorEkleListener(AjaxBehaviorEvent event) {

		formDuzenle();

	}

	public String asansorKaydetBasvurudan() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;
		BasvuruBean basvuruBean = (BasvuruBean) FacesContext
				.getCurrentInstance()
				.getApplication()
				.evaluateExpressionGet(FacesContext.getCurrentInstance(),
						"#{basvuruBean}", BasvuruBean.class);

		try {
			List<CihazTeknikDTO> l = new ArrayList<CihazTeknikDTO>();

			for (CihazTeknikDTO c : cihazTeknik) {
				if (c.getDegerId()==156 || c.getDegerId()==189){
					c.setCevap("");
				if (c.getOzelAsansorSartlar()!=null){
					StringBuilder sb=new StringBuilder();
					int sw=0;
					for (String s:c.getOzelAsansorSartlar()){
						if (sw==0){
							sb.append(s);
							sw++;
						}else{
							sb.append(";").append(s);
						}
						
					}
					c.setCevap(sb.toString());
				}
				}
				if (c.getCevap() != null && !c.getCevap().trim().equals("")) {
					l.add(c);
				}
			}

			binaId = basvuruBean.getBasvuru().getBina().getBinaId();
			ilKodu = basvuruBean.getBasvuru().getBina().getIl();
			ilceKodu = basvuruBean.getBasvuru().getBina().getIlce();
			String ilceAdi = "";

			ApplicationBean bean = (ApplicationBean) FacesContext
					.getCurrentInstance().getExternalContext()
					.getApplicationMap().get("applicationBean");
			HashMap<String, Integer> ilceMap = bean.getIlceler().get(ilKodu);

			Set<String> key = ilceMap.keySet();
			Iterator<String> iter = key.iterator();
			while (iter.hasNext()) {
				String s = iter.next();
				if (ilceMap.get(s) == ilceKodu) {
					ilceAdi = s;
					break;
				}
			}

			if (l.size() > 0) {
				int asansorSiraNo = 1;
				int sw = 0;
				for (CihazDTO c : cihazlar) {
					if (sw == 0) {
						asansorSiraNo = c.getUavtSiraNo();
						sw++;
					} else {
						if (asansorSiraNo < c.getUavtSiraNo()) {
							asansorSiraNo = c.getUavtSiraNo();
						}
					}

				}
				asansorSiraNo++;
				new AsansorBusiness().cihazEkleBasvurudan(this.cihaz, binaId,
						ilKodu, ilceKodu, ilceAdi.trim(), l, basvuruBean
								.getBasvuru().getBina().getUavtKod(),
						asansorSiraNo);

				cihazTeknik = new ArrayList<CihazTeknikDTO>();

				visible = false;
				List<BasvuruAsansorDTO> baList = new ArrayList<BasvuruAsansorDTO>();
				try {
					List<CihazDTO> cihazlar = new ArrayList<CihazDTO>();
					cihazlar = new AsansorBusiness().getCihazlar(binaId);

					for (CihazDTO c : cihazlar) {
						if (c.getDurum().equals("A")){
						BasvuruAsansorDTO bd = new BasvuruAsansorDTO();
						bd.setCihazId(c.getCihazId());
						bd.setCihazTip(c.getTip());
						bd.setTipAciklama(c.getTipAciklama());
						bd.setSonKontrolEtiketi(c.getSonKontrolEtiketi());
						bd.setKontrolTarihi(c.getSonKontrolTarihi());
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
										bd.setKapasiteAgirlik(Integer
												.parseInt(ct.getCevap()));

									}

								}
								if (c.getTip() == 68) {
									if (ct.getDegerId() == 182) {
										bd.setKapasiteDurak(Integer.parseInt(ct
												.getCevap()));

									}
									if (ct.getDegerId() == 174) {
										bd.setKapasiteAgirlik(Integer
												.parseInt(ct.getCevap()));

									}
								}
							} catch (NumberFormatException e) {

							}
						}

						baList.add(bd);

					}
					}

				} catch (ReadException e) {
					e.printStackTrace();
				}
				try {
					List<BasvuruAsansorDTO> lst = new ArrayList<BasvuruAsansorDTO>();
					try {
						for (BasvuruAsansorDTO b : basvuruBean
								.getSelectedAsansor()) {
							lst.add(b);
						}
					} catch (Exception e) {

					}
					baList = new FirmaBusiness().getAsansorBakimci(baList, lst,
							basvuruBean.getBasvuru().getBasvuru()
									.getBasvuruId());

				} catch (ReadException e) {
					msg = new FacesMessage(e.getMessage());
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					context.addMessage(null, msg);
					e.printStackTrace();
				}

				basvuruBean.setAsansorList(baList);
				Panel p = (Panel) FacesContext.getCurrentInstance()
						.getViewRoot()
						.findComponent(":formbasvuru:panelasansorekle");
				p.setVisible(false);

			} else {
				msg = new FacesMessage(Messages._CIHAZDEGERGIRINIZ.getMesaj());
				context.addMessage(null, msg);
			}

		} catch (CRUDException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, msg);
		} catch (Exception e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, msg);
		}

		return "";

	}

	public int getTanimlanacakAsansorAdet() {
		return tanimlanacakAsansorAdet;
	}

	public void setTanimlanacakAsansorAdet(int tanimlanacakAsansorAdet) {
		this.tanimlanacakAsansorAdet = tanimlanacakAsansorAdet;
	}

	public List<Integer> getTanimlanacakAsansorAdetList() {
		return tanimlanacakAsansorAdetList;
	}

	public void setTanimlanacakAsansorAdetList(
			List<Integer> tanimlanacakAsansorAdetList) {
		this.tanimlanacakAsansorAdetList = tanimlanacakAsansorAdetList;
	}

	public void test(ActionEvent event) {
		System.out.println("test");
	}
	
	public void firmaBul_(CihazDTO cihazDTO) {

		FacesMessage msg;
		firmaModel=new FirmaDataModel(new ArrayList<FirmaDTO>());
		if (cihazDTO != null && cihazDTO.getCihazId()>0) {
		
			try {
				bakimciFirmalar = new FirmaBusiness()
				.getAsansorBakimciFirmalar(cihazDTO.getCihazId());

				firmaModel=new FirmaDataModel(bakimciFirmalar);
				

			} catch (CRUDException e) {
				msg = new FacesMessage(e.getMessage());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, msg);

			}
		} 

	}
	
	public void firmaBul(ActionEvent event) {

		FacesMessage msg;
		if (secilen != null && secilen.getCihazId() > 0) {
			cihazFirma = new CihazFirmaDTO();
			try {
				bakimciFirmalar = new FirmaBusiness()
						.getAsansorBakimciFirmalar(secilen.getCihazId());

				
				

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

	public List<FirmaDTO> getBakimciFirmalar() {
		return bakimciFirmalar;
	}

	public void setBakimciFirmalar(List<FirmaDTO> bakimciFirmalar) {
		this.bakimciFirmalar = bakimciFirmalar;
	}

	public boolean isTanimlanacakAsansorListDisable() {
		return tanimlanacakAsansorListDisable;
	}

	public void setTanimlanacakAsansorListDisable(
			boolean tanimlanacakAsansorListDisable) {
		this.tanimlanacakAsansorListDisable = tanimlanacakAsansorListDisable;
	}

	public void cihazTipiChange(AjaxBehaviorEvent event) {
		cihazTeknik = new ArrayList<CihazTeknikDTO>();
		cihaz=new CihazDTO();
		visible = false;
		tanimlanacakAsansorAdet = 1;
		cihaz.setDurum("A");
		this.secilen=null;
	}

	public List<FirmaDTO> getFirmaList() {
		Collections.sort(firmaList, new BakimciFirmaComparator());
		return firmaList;
	}

	public void setFirmaList(List<FirmaDTO> firmaList) {
		this.firmaList = firmaList;
	}

	public CihazFirmaDTO getCihazFirma() {
		return cihazFirma;
	}

	public void setCihazFirma(CihazFirmaDTO cihazFirma) {
		this.cihazFirma = cihazFirma;
	}

	public void bakimciFirmaKaydet() {
		cihazFirma.setCihazId(secilen.getCihazId());
		FacesMessage msg;
		try {
			new FirmaBusiness().setAsansorBakimciFirma(cihazFirma);
			bakimciFirmalar = new FirmaBusiness()
					.getAsansorBakimciFirmalar(secilen.getCihazId());
			cihazFirma = new CihazFirmaDTO();
			msg = new FacesMessage(Messages._SQL_513_.getMesaj());
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (CRUDException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void firmaSozlesmeTarihListener(SelectEvent event){
		Object o = event.getObject();
		UIComponent uc = event.getComponent();

		if (this.cihazFirma.getSozlesmeTarih() != null && this.cihazFirma.getSozlesmeBitisTarih() != null
				&& this.cihazFirma.getSozlesmeBitisTarih().before(this.cihazFirma.getSozlesmeTarih()))
				 {
			

			FacesMessage msg = new FacesMessage(
					Messages._KONTROLTARIHIHATALI_.getMesaj());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	
	public void bakimciFirmaKaydet_() {
		
		FacesMessage msg;

		if (selList != null && selList.size()>0) {
		try {
			if (this.cihazFirma.getSozlesmeTarih() != null && this.cihazFirma.getSozlesmeBitisTarih() != null
					&& this.cihazFirma.getSozlesmeBitisTarih().before(this.cihazFirma.getSozlesmeTarih()))
					 {
				

				 msg = new FacesMessage(
						Messages._KONTROLTARIHIHATALI_.getMesaj());
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}else{
			new FirmaBusiness().setAsansorBakimciFirma(cihazFirma,selList);
			
			cihazFirma = new CihazFirmaDTO();
			msg = new FacesMessage(Messages._SQL_513_.getMesaj());
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			}
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
public void bakimciFirmaSozlesmeKaydetBasvuruFirmaDegistir() {
		
		FacesMessage msg=new FacesMessage();
		FacesContext context=FacesContext.getCurrentInstance();
		try {
		
		
		
			if (this.cihazFirma.getFirmaId()>0 ){
				SoruTableBean soruTableBean = (SoruTableBean) FacesContext.getCurrentInstance().getApplication()
						.evaluateExpressionGet(FacesContext.getCurrentInstance(),
								"#{soruTableBean}", SoruTableBean.class);
				if (sozlesmeGiris){
				List<CihazDTO> cihazList=new ArrayList<CihazDTO>();
				
				cihazList.add(soruTableBean.getSecilen());
				if(this.cihazFirma.getSozlesmeTarih() == null || this.cihazFirma.getSozlesmeBitisTarih()==null){
					throw new CRUDException(Messages._SOZLESMETARIHLERIHATALI_.getMesaj(),null);
					
					
				}else{
			if ( this.cihazFirma.getSozlesmeBitisTarih().before(this.cihazFirma.getSozlesmeTarih()))
					 {
				
				throw new CRUDException(Messages._SOZLESMETARIHLERIHATALI_.getMesaj(),null);
				
				
			}else{
			new FirmaBusiness().setAsansorBakimciFirma(cihazFirma,cihazList);
			
			
			
			
			}
			}}
			
				new BasvuruBusiness().basvuruBakimciFirmaUpdate(soruTableBean.getKontrolDTO().getRandevuId(), this.cihazFirma.getFirmaId(), soruTableBean.getSecilen().getCihazId());
				FirmaDTO f=new FirmaDTO();
				f=new FirmaBusiness().getCihazKontrolFirma(soruTableBean.getKontrolDTO().getRandevuId(),  soruTableBean.getSecilen().getCihazId());
				soruTableBean.getKontrolDTO().setFirma(f);
				setBakimciFirmalar( new FirmaBusiness()
				.getAsansorBakimciFirmalar(soruTableBean.getSecilen().getCihazId()));
				setFirmaModel(new FirmaDataModel(getBakimciFirmalar()));
				cihazFirma = new CihazFirmaDTO();
				throw new CRUDException(Messages._SQL_513_.getMesaj(),new Throwable(""));
			
		}else{
				throw new CRUDException(Messages._FIRMASECILMEDI_.getMesaj(),null);
			
		}
		
		
	
		} catch (CRUDException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(e.getCause()==null?FacesMessage.SEVERITY_ERROR:e.getCause().getMessage()==null?FacesMessage.SEVERITY_ERROR:e.getCause().getMessage().equals("")?FacesMessage.SEVERITY_INFO:FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, msg);
		}
		
		
	}
	public void asansorKimlikKodSorgula(ActionEvent event) {
		if (uavtAsansorSiraNo!=null && !uavtAsansorSiraNo.trim().equals("")){
		try {
			uavtCihazEkle = false;
			
			int uavtBinaKod = binaBean.getBinalar().getUavtKod();
			if (uavtBinaKod > 0) {
				ServisSonucOfAsansorKimlikKodlar8Zb117HL servisSonucOfAsansorKimlikKodlar = new WebServiceBusiness()
						.asansorKimlikKodSorgula(uavtBinaKod,
								Integer.parseInt(uavtAsansorSiraNo.trim()));
				if (!servisSonucOfAsansorKimlikKodlar.isHata()) {
					if (servisSonucOfAsansorKimlikKodlar.getMesaj().getValue()
							.equals("")) {
						AsansorKimlikKodlar asansorKimlikKodlar = new AsansorKimlikKodlar();
						asansorKimlikKodlar = servisSonucOfAsansorKimlikKodlar
								.getSonuc().getValue();
						uavtAsansorSiraNo = String.valueOf(asansorKimlikKodlar
								.getAsansorSiraNo());
						cihazUavtKod = String.valueOf(asansorKimlikKodlar
								.getAsansorNo());
						this.cihaz.setUavtEtiket(asansorKimlikKodlar
								.getAsansorEtiket().getValue());
					} else {

						StringBuilder sb = new StringBuilder(
								servisSonucOfAsansorKimlikKodlar.getMesaj()
										.getValue());
						sb.append(" ");
						if (servisSonucOfAsansorKimlikKodlar.getMesaj()
								.getValue().toUpperCase().contains("BULUNMADI")) {
							uavtCihazEkle = true;
							cihazUavtKod = String.valueOf("9999999");
							this.cihaz.setUavtEtiket("");
							sb.append(Messages._UAVTYEASANSOREKLENECEK_
									.getMesaj());
						}
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(sb.toString()));
					}

				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(servisSonucOfAsansorKimlikKodlar
									.getMesaj().getValue()));
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(Messages._BINAUAVTKODBOS_.getMesaj()));
			}
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}}else{
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Sıra No Giriniz!"));
		}

	}

	public String getCihazUavtKod() {
		return cihazUavtKod;
	}

	public void setCihazUavtKod(String cihazUavtKod) {
		this.cihazUavtKod = cihazUavtKod;
	}

	public String getUavtAsansorSiraNo() {
		return uavtAsansorSiraNo;
	}

	public void setUavtAsansorSiraNo(String uavtAsansorSiraNo) {
		this.uavtAsansorSiraNo = uavtAsansorSiraNo;
	}

	public UavtCihazDataModel getUavtCihazList() {
		return uavtCihazList;
	}

	public void setUavtCihazList(UavtCihazDataModel uavtCihazList) {
		this.uavtCihazList = uavtCihazList;
	}
	
	public int binaBagliAsansorSorgulama(int uavtBinaKod) throws Exception{
		
		int asansorSiraNo = 1;
		try {

			boolean asansorSorguBitti = false;

				do {
					ServisSonucOfAsansorKimlikKodlar8Zb117HL servisSonucOfAsansorKimlikKodlar = new WebServiceBusiness()
							.asansorKimlikKodSorgula(uavtBinaKod, asansorSiraNo);
					if (!servisSonucOfAsansorKimlikKodlar.isHata()) {
						if (servisSonucOfAsansorKimlikKodlar.getMesaj()
								.getValue().equals("")) {
							
							asansorSiraNo++;
						} else {
							asansorSorguBitti = true;
						}
					} else {
						asansorSorguBitti = true;
					}

				} while (!asansorSorguBitti);
				
			
		} catch (CRUDException e) {
			throw new Exception(e.getMessage());
		} 
		return asansorSiraNo;

	}
	
	
	public void binaBagliAsansorSorgulama(ActionEvent event) {

		try {

			
			int uavtBinaKod = binaBean.getBinaDTO().getUavtKod();
			boolean asansorSorguBitti = false;

			int asansorSiraNo = 1;
			if (uavtBinaKod > 0) {
				List<UavtCihaz> uavtCihazList = new ArrayList<UavtCihaz>();
				do {
					ServisSonucOfAsansorKimlikKodlar8Zb117HL servisSonucOfAsansorKimlikKodlar = new WebServiceBusiness()
							.asansorKimlikKodSorgula(uavtBinaKod, asansorSiraNo);
					if (!servisSonucOfAsansorKimlikKodlar.isHata()) {
						if (servisSonucOfAsansorKimlikKodlar.getMesaj()
								.getValue().equals("")) {
							AsansorKimlikKodlar asansorKimlikKodlar = new AsansorKimlikKodlar();
							asansorKimlikKodlar = servisSonucOfAsansorKimlikKodlar
									.getSonuc().getValue();
							UavtCihaz uavtCihaz = new UavtCihaz();
							try {
								uavtCihaz.setAktif(asansorKimlikKodlar.getAktif().getValue()
										);
								uavtCihaz.setAsansorEtiket(asansorKimlikKodlar
										.getAsansorEtiket().getValue());
								uavtCihaz.setAsansorNo(asansorKimlikKodlar
										.getAsansorNo());
								uavtCihaz.setAsansorSiraNo(asansorKimlikKodlar
										.getAsansorSiraNo().getValue());
								uavtCihaz.setBinaKod(asansorKimlikKodlar
										.getBinaKod());
								uavtCihaz.setEtiketYil(asansorKimlikKodlar
										.getEtiketYil().getValue().intValue());
								uavtCihaz
										.setGecerlilikTarih(asansorKimlikKodlar
												.getGecerlilikTarih().getValue()
												.toGregorianCalendar()
												.getTime());
							} catch (Exception e) {

							}
							uavtCihazList.add(uavtCihaz);
							asansorSiraNo++;
						} else {
							asansorSorguBitti = true;
						}
					} else {
						asansorSorguBitti = true;
					}

				} while (!asansorSorguBitti);
				this.uavtCihazList = new UavtCihazDataModel(uavtCihazList);
				RequestContext.getCurrentInstance().execute(
						"PF('uavtCihaz').show()");
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(Messages._BINAUAVTKODBOS_.getMesaj()));
			}
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}

	}

	public List<UavtCihaz> getUavtCihazFilter() {
		return uavtCihazFilter;
	}

	public void setUavtCihazFilter(List<UavtCihaz> uavtCihazFilter) {
		this.uavtCihazFilter = uavtCihazFilter;
	}

	public String getBinaIdString() {
		return binaIdString;
	}

	public void setBinaIdString(String binaIdString) {
		this.binaIdString = binaIdString;
	}

	public void uavtSiraNoKeyDownListener(AjaxBehaviorEvent event) {
		UIInput ui=(UIInput)event.getComponent();
		try{
			int uiVal=Integer.parseInt(ui.getValue().toString().trim());
			cihazUavtKod = "";
			this.cihaz.setUavtEtiket("");
			
		}catch(NumberFormatException e){
			uavtAsansorSiraNo="0";
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Hatalı Sıra No !"));
		}
		catch(Exception e){
			
		}
		
		
	}

	public void uavtKodOnBlurListener(AjaxBehaviorEvent event) {
		cihazUavtKod = ((UIInput) event.getComponent()).getValue().toString()
				.trim();
	}

	public BinaBean getBinaBean() {
		return binaBean;
	}

	public void setBinaBean(BinaBean binaBean) {
		this.binaBean = binaBean;
	}
	
	
	public void binaDegistir(ActionEvent event){
		cihazlar=new ArrayList<CihazDTO>();
		cihazList=new CihazDataModel(cihazlar);
		secilen=new CihazDTO();
		kontroller = new KontrolDataModel(new ArrayList<KontrolDTO>());

		visible = false;
		header = "";
		cihazTeknik = new ArrayList<CihazTeknikDTO>();
		binaIdString="";
		cihazId = 0;
		cihaz = new CihazDTO();
		ilceler = new TreeMap<String, Integer>(new IlIlceComparator());
		binalar = new ArrayList<BinaDTO>();
		this.binaBean.setSessionBina(new BinaDTO());
		this.binaBean.setBinaDTO(new BinaDTO());
		this.binaBean.setBinalar(new BinaDTO());
		this.binaBean.setCihazList(new ArrayList<CihazDTO>());
		cihazUpdate=false;
	}
	
	public void yeniAsansor(ActionEvent event){
		if (cihaz.getTip()>0){
			this.secilen=null;
		cihazUpdate=false;
		formDuzenle();
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(Messages._ASANSORTIPISECINIZ_.getMesaj()));
		}
		
	}

	public FirmaDataModel getFirmaModel() {
		return firmaModel;
	}

	public void setFirmaModel(FirmaDataModel firmaModel) {
		this.firmaModel = firmaModel;
	}

	public List<FirmaDTO> getFilterFirma() {
		return filterFirma;
	}

	public void setFilterFirma(List<FirmaDTO> filterFirma) {
		this.filterFirma = filterFirma;
	}

	public List<CihazDTO> getFilterCihaz() {
		return filterCihaz;
	}

	public void setFilterCihaz(List<CihazDTO> filterCihaz) {
		this.filterCihaz = filterCihaz;
	}
	
	
	public void bakimciFirmaListener(ActionEvent event){
		selList=new ArrayList<CihazDTO>();
		cihazFirma=new CihazFirmaDTO();
		
		
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}
	
	public void hizListener(AjaxBehaviorEvent event){
		hizDiger=false;
		UIInput ui=(UIInput)event.getComponent();
		if (ui!=null && ui.getValue().toString().equalsIgnoreCase("DİĞER")){
			hizDiger=true;
		}
		
	}

	public boolean isHizDiger() {
		return hizDiger;
	}

	public void setHizDiger(boolean hizDiger) {
		this.hizDiger = hizDiger;
	}
	
	
	private boolean kapasiteKisi=false;
	
	
	public void asansorCinsiListener(AjaxBehaviorEvent event){
		UIInput ui=(UIInput)event.getComponent();
		kapasiteKisi=false;
		if (ui!=null){
			String str=ui.getValue().toString();
			this.kapasiteKisi=str.equalsIgnoreCase("İNSAN")?true:false;
			
		}
	}

	public boolean isKapasiteKisi() {
		return kapasiteKisi;
	}

	public void setKapasiteKisi(boolean kapasiteKisi) {
		this.kapasiteKisi = kapasiteKisi;
	}



	public boolean isSozlesmeGiris() {
		return sozlesmeGiris;
	}

	public void setSozlesmeGiris(boolean sozlesmeGiris) {
		this.sozlesmeGiris = sozlesmeGiris;
	}
	

	public void numberListener(AjaxBehaviorEvent event){
		UIInput ui=(UIInput)event.getComponent();
		if (ui!=null){
			try{
			if (Integer.parseInt(ui.getValue().toString().trim())<0){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(Messages._NEGATIFDEGER_.getMesaj()));
				((UIInput)event.getComponent()).setValue("");
			}
			}catch (Exception e) {
				
			}
		}
	}
	
	public void yeniAsansorListener(AjaxBehaviorEvent event){
		this.secilen=null;
		visible=false;
		tanimlanacakAsansorListDisable = false;
		this.cihaz=new CihazDTO();
		header="";
		cihazTeknik=new ArrayList<CihazTeknikDTO>();
		binaBean.setBinalar(binaBean.getBinaDTO());
		binaId = binaBean.getBinaDTO().getBinaId();

		cihazId = 0;
		
	
		cihazList = new CihazDataModel(cihazlar);
		uavtAsansorSiraNo = "";
		cihazUavtKod = String.valueOf(cihaz.getUavtKod());
		binaIdString = String.valueOf(binaBean.getBinaDTO().getBinaId());
		cihazUpdate = false;
	}
}
