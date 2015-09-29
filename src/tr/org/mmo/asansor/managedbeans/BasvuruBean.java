package tr.org.mmo.asansor.managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.Position.Bias;

import org.primefaces.component.dialog.Dialog;
import org.primefaces.component.growl.Growl;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.wizard.Wizard;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleSelectEvent;
import org.primefaces.event.UnselectEvent;

import tr.org.mmo.asansor.beans_.Basvuru;
import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.beans_.TaramaYapilmayanBinalar;
import tr.org.mmo.asansor.business.AsansorBusiness;
import tr.org.mmo.asansor.business.BasvuruBusiness;
import tr.org.mmo.asansor.business.BelediyeBusiness;
import tr.org.mmo.asansor.business.BinaBusiness;
import tr.org.mmo.asansor.business.FirmaBusiness;
import tr.org.mmo.asansor.business.MailBusiness;
import tr.org.mmo.asansor.business.OdemeBusiness;
import tr.org.mmo.asansor.dao.ApplicationDAOImpl;
import tr.org.mmo.asansor.dto.BasvuruAsansorDTO;
import tr.org.mmo.asansor.dto.BasvuruDTO;
import tr.org.mmo.asansor.dto.BasvuruListeDTO;
import tr.org.mmo.asansor.dto.BelediyeDTO;
import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.dto.BinaKisiDTO;
import tr.org.mmo.asansor.dto.CihazDTO;
import tr.org.mmo.asansor.dto.CihazFirmaDTO;
import tr.org.mmo.asansor.dto.CihazTeknikDTO;
import tr.org.mmo.asansor.dto.FirmaDTO;
import tr.org.mmo.asansor.dto.IlIlceDTO;
import tr.org.mmo.asansor.dto.KontrolDTO;
import tr.org.mmo.asansor.dto.OdemeDTO;
import tr.org.mmo.asansor.dto.TaramaDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.models.BasvuruAsansorDataModel;
import tr.org.mmo.asansor.models.BasvuruListeModel;
import tr.org.mmo.asansor.models.CihazDataModel;
import tr.org.mmo.asansor.util.CaddeSokakComparator;
import tr.org.mmo.asansor.util.DateUtils;
import tr.org.mmo.asansor.util.IlIlceComparator;
import tr.org.mmo.asansor.util.MahalleComparator;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.beans.CaddeSokak;
import tr.org.mmo.beans.Mahalle;

@ManagedBean(name = "basvuruBean")
@ViewScoped
public class BasvuruBean implements Serializable {
	boolean dialogGoster = true;
	private boolean kontrolSekli;
	private boolean islemiTamamla = false;
	private String kontrolYapilmamaNeden;
	private List<TaramaYapilmayanBinalar> taramaYapilmayanBinaListe;
	private List<BasvuruAsansorDTO> basvuruYapilanAsansorler = new ArrayList<BasvuruAsansorDTO>();
	private boolean bbVal = false;
	private TreeMap<String, Integer> firmalar = new TreeMap<String, Integer>();
	@ManagedProperty(value = "#{loginBean.tumBinalar}")
	private List<BinaDTO> tumBinalar;
	boolean binadanOdemeAlinacak = true;
	
	@ManagedProperty("#{sessionBean}")
	private SessionBean sessionBean;
	@ManagedProperty("#{binaBean}")
	private BinaBean binaBean;
	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	/**
	 * 
	 */

	private static final long serialVersionUID = -2184239654660439122L;

	private String binaTip;
	private boolean binaTipSelected = false;

	public String getBinaTip() {
		if (binaTip != null && !binaTip.equals("")) {
			binaTipSelected = true;
		}
		return binaTip;
	}

	public void setBinaTip(String binaTip) {
		this.binaTip = binaTip;
	}

	private boolean skip;
	private boolean valueTCCheck = false;
	private Basvuru basvuru;

	private boolean basvuruUpdate = false;
	private BasvuruAsansorDataModel asansorData;
	private BinaKisiDTO binaKisi = new BinaKisiDTO();

	public BasvuruAsansorDataModel getAsansorData() {
		return asansorData;
	}

	public void setAsansorData(BasvuruAsansorDataModel asansorData) {
		this.asansorData = asansorData;
	}

	private List<BasvuruAsansorDTO> asansorList;
	private List<BasvuruAsansorDTO> tutarHesaplanmayanAsansorler;
	private BasvuruAsansorDTO[] selectedAsansor;
	private BasvuruAsansorDTO[] selectedFirmaAsansor;
	private boolean cihazlarVisible = false;

	public boolean isCihazlarVisible() {
		return cihazlarVisible;
	}

	public void setCihazlarVisible(boolean cihazlarVisible) {
		this.cihazlarVisible = cihazlarVisible;
	}

	private List<BasvuruListeDTO> basvuruListesi;
	private BasvuruListeModel basvuruListe;
	private BasvuruListeDTO selectedBasvuru;
	private List<BasvuruListeDTO> filteredVal;
	private List<TaramaYapilmayanBinalar> filteredTaramaVal;

	public List<BasvuruListeDTO> getFilteredVal() {
		return filteredVal;
	}

	public void setFilteredVal(List<BasvuruListeDTO> filteredVal) {
		this.filteredVal = filteredVal;
	}

	private boolean binaBilgisiDegisti = false;
	List<BasvuruListeDTO> listt = new ArrayList<BasvuruListeDTO>();

	@PostConstruct
	public void init() {

		String viewId = FacesContext.getCurrentInstance().getViewRoot()
				.getViewId();
		this.binaBean.setBinalar(new BinaDTO());
		this.binaBean.setTcKimlikNo("");
		this.binaBean.setBinaId(0);
		this.binaBean.setTescilno(0);
		if (viewId.equals("/basvuru.xhtml")) {
			if (sessionBean.getTumBinalar() == null
					|| sessionBean.getTumBinalar().size() == 0) {
				try {
					sessionBean.setTumBinalar(new BinaBusiness()
							.binaBulByIlIlce());
				} catch (CRUDException e) {

					e.printStackTrace();
				}
			}
		}
		if (viewId.equals("/basvuru.xhtml") || viewId.equals("/tarama.xhtml")) {
			kontrolSekli = viewId.equals("/basvuru.xhtml") ? false : true;
			basvuru = new Basvuru();
			tutarHesaplanmayanAsansorler = new ArrayList<BasvuruAsansorDTO>();
			BasvuruListeDTO basvuruListeDTO = (BasvuruListeDTO) ((HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest())
					.getSession().getAttribute("basvurulisteparam");
			((HttpServletRequest) FacesContext.getCurrentInstance()
					.getExternalContext().getRequest()).getSession()
					.removeAttribute("basvurulisteparam");
			if (basvuruListeDTO != null && basvuruListeDTO.getBasvuruId() > 0) {
				Panel p = (Panel) FacesContext.getCurrentInstance()
						.getViewRoot().findComponent(":formbasvuru:pnlBina");
				p.setVisible(true);
				Panel p1 = (Panel) FacesContext.getCurrentInstance()
						.getViewRoot().findComponent(":formbasvuru:binaara");
				p1.setVisible(true);
				binaTip = "K";
				basvuruUpdate = true;

				BasvuruDTO basvuruDTO = new BasvuruDTO();
				tutarHesaplanmayanAsansorler = new ArrayList<BasvuruAsansorDTO>();
				for (BasvuruAsansorDTO b : basvuruListeDTO
						.getAsansorKontrolFiyatlari()) {
					if (b.getKontrolTutari() <= 0)
						tutarHesaplanmayanAsansorler.add(b);
				}
				basvuruDTO.setBasvuruYapanSorumlulukTuru(basvuruListeDTO
						.getBasvuruYapanSorumlulukTur());
				binaKisi.setSorumlulukTuru((short) basvuruListeDTO
						.getBasvuruYapanSorumlulukTur());
				basvuruDTO.setBasvuruId(basvuruListeDTO.getBasvuruId());
				basvuruDTO.setBasvuruTarihi(basvuruListeDTO.getBasvuruTarihi());
				basvuruDTO.setBasvuruYapanAdi(basvuruListeDTO.getAdi());
				basvuruDTO.setBasvuruYapanSoyadi(basvuruListeDTO.getSoyadi());
				basvuruDTO.setBasvuruYapanTCKimlikNo(basvuruListeDTO
						.getTcKimlikNo());
				basvuruDTO.setBinaId(basvuruListeDTO.getBinaId());
				basvuruDTO.setTelefonNo(Long.parseLong(basvuruListeDTO
						.getTelefonNo().replace("(", "").replace(")", "")
						.replace(" ", "").trim()));
				basvuruDTO.setTelefonNoStr(basvuruListeDTO.getTelefonNo());
				basvuruDTO.setTelefonNoDahili(basvuruListeDTO.getTelefonNoDahili());
				basvuru.setBasvuru(basvuruDTO);
				basvuru.getKisi().setePosta(basvuruListeDTO.getePosta());
				
				if (binaBean != null) {
					for (BinaDTO bina : ((LoginBean) Common
							.findBean("loginBean")).getTumBinalar()) {

						if (bina.getBinaId() == basvuruListeDTO.getBinaId()) {
							basvuru.setBina(bina);
							setBina(binaBean, bina,
									basvuruListeDTO);
							break;
						}
					}
				}

			}
		} else {
			if (viewId.equals("/taranmayanBinaListesi.xhtml")) {
				taramaYapilmayanBinaListe = new ArrayList<TaramaYapilmayanBinalar>();
				try {
					taramaYapilmayanBinaListe = new BasvuruBusiness()
							.getTaramaYapilmayanBinalar();
				} catch (CRUDException e) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(e.getMessage()));
				}
			} else {
				
				selectedBasvuru = new BasvuruListeDTO();
				basvuruListesi = new ArrayList<BasvuruListeDTO>();
				listt = new ArrayList<BasvuruListeDTO>();
				basvuruListe = new BasvuruListeModel(basvuruListesi);
				 BasvuruTarihBean basvuruTarihBean = (BasvuruTarihBean) FacesContext
							.getCurrentInstance()
							.getApplication()
							.evaluateExpressionGet(FacesContext.getCurrentInstance(),
									"#{basvuruTarihBean}", BasvuruTarihBean.class);
				try {
					
					listt = getBasvurular(basvuruTarihBean.getIlkTarih(),basvuruTarihBean.getSonTarih());
			
				double kontrolTutari = 0.00;
				for (BasvuruListeDTO basvuruListe : listt) {
					boolean odemeYapilacak = true;
					kontrolTutari = 0.00;
					for (BasvuruAsansorDTO b : basvuruListe
							.getAsansorKontrolFiyatlari()) {
						kontrolTutari += b.getKontrolTutari();
						if (b.getKontrolTutari() <= 0
								&& b.getKontrolTuru().equals("N")) {
							odemeYapilacak = false;
							break;
						}
					}
					
					
					if (viewId.equals("/basvuruListesi.xhtml")) {
						if (odemeYapilacak) {
							if (kontrolTutari > 0) {
								basvuruListe
										.setKontrolTutari(kontrolTutari);
								basvuruListesi.add(basvuruListe);
							}
						}
					} else {
						if (!odemeYapilacak) {

							basvuruListesi.add(basvuruListe);
						}
					}
					

				}

				
			
			setBasvuruListe(new BasvuruListeModel(basvuruListesi));
				
			
				} catch (CRUDException e) {
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage()));
				}
			}
		}

	}

	private void setBina(BinaBean binaBean, BinaDTO bina, BasvuruListeDTO basvuruListeDTO) {

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;
		((HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest()).getSession()
				.removeAttribute("binaid");
		((HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest()).getSession().setAttribute(
				"binaid", bina.getBinaId());

		try {

			ArrayList<IlIlceDTO> list = ApplicationDAOImpl.getINSTANCE()
					.getIlceler(bina.getIl());
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			for (IlIlceDTO i : list) {
				map.put(i.getIlceAdi(), i.getIlceKodu());
			}
			binaBean.setIlceler(new TreeMap<String, Integer>(
					new IlIlceComparator()));
			binaBean.getIlceler().putAll(map);

			List<BelediyeDTO> l = new ArrayList<BelediyeDTO>();
			l = new BelediyeBusiness().getBelediyelerByIl(bina.getIl(),
					bina.getSozlesmeBinaTipId());
			for (BelediyeDTO d : l) {
				binaBean.getBelediyeler().add(
						new SelectItem(d.getKod(), d.getAdi()));
			}
			List<Mahalle> mList = new ArrayList<Mahalle>();
			binaBean.getBucakKoyListesi(bina.getIlce());
			binaBean.getMahalleList(bina.getBucakKoyKod());
			for (Mahalle m : binaBean.getMahalleList()) {
				mList.add(m);
			}

			Collections.sort(mList, new MahalleComparator());
			binaBean.setMahalleList(mList);
			List<CaddeSokak> cList = new ArrayList<CaddeSokak>();
			int mahalleId = 0;
			for (Mahalle m : mList) {
				if (m.getAd().replace('i', 'İ').toUpperCase()
						.equals(bina.getMahalle())) {
					mahalleId = m.getKod();
				}
			}

			try {

				cList = ApplicationDAOImpl.getINSTANCE()
						.getCaddeSokakByMahalleId(mahalleId);
				Collections.sort(cList, new CaddeSokakComparator());
				binaBean.setCaddeSokakList(cList);
			} catch (NullPointerException e) {
				// TODO: handle exception
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (CRUDException e) {

			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, msg);
			e.printStackTrace();
		}
		try {
			binaBean.setYapiTipler(new BinaBusiness().getYapiTipler());
		} catch (CRUDException e) {
			e.printStackTrace();
		}

		CihazBean cihazBean = (CihazBean) context
				.getApplication()
				.evaluateExpressionGet(context, "#{cihazBean}", CihazBean.class);
		if (cihazBean != null) {
			List<CihazDTO> cihazList = new ArrayList<CihazDTO>();
			if (basvuruUpdate) {
				try {

					cihazList = new AsansorBusiness().getCihazlar(bina
							.getBinaId());
					binaBean.setCihazList(cihazList);
					cihazBean.setCihazlar(cihazList);
				} catch (ReadException e) {
					e.printStackTrace();
				}
			} else {
				try {
					binaBean.setCihazList(new AsansorBusiness()
							.getCihazlar(bina.getBinaId()));
				} catch (ReadException e) {
					e.printStackTrace();
				}
				cihazBean.setCihazlar(binaBean.getCihazList());
			}
			cihazBean.setCihazList(new CihazDataModel(cihazBean.getCihazlar()));
		}

		List<BasvuruAsansorDTO> baList = new ArrayList<BasvuruAsansorDTO>();
		for (CihazDTO c : binaBean.getCihazList()) {

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
				cihazDegerList = new AsansorBusiness().getCihazKapasite(c
						.getCihazId());
			} catch (ReadException e) {
				e.printStackTrace();
			}
			for (CihazTeknikDTO ct : cihazDegerList) {
				try {
					if (c.getTip() == 66) {
						if (ct.getDegerId() == 157) {
							bd.setKapasiteDurak(Integer.parseInt(ct.getCevap()));

						}
						if (ct.getDegerId() == 158) {
							bd.setKapasiteAgirlik(Integer.parseInt(ct
									.getCevap()));

						}

					}
					if (c.getTip() == 68) {
						if (ct.getDegerId() == 182) {
							bd.setKapasiteDurak(Integer.parseInt(ct.getCevap()));

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

		for (BasvuruAsansorDTO ba : baList) {
			for (CihazDTO b : binaBean.getCihazList()) {

				if (b.getCihazId() == ba.getCihazId()) {
					ba.setKontrolTuru(cihazKontrolTuru(ba.getCihazId()));
					if (!ba.getKontrolTuru().equals("E")) {
						ba.setKontrolTutari(cihazBakimFiyatHesapla(
								ba.getCihazTip(), ba.getKapasiteDurak(),
								ba.getKapasiteAgirlik(),ba.getKontrolTuru()));
					}
					for(BasvuruAsansorDTO bb:basvuruListeDTO.getAsansorKontrolFiyatlari()){
						if (ba.getCihazId()==bb.getCihazId()){
							ba.setKontrolTuru(bb.getKontrolTuru());
							ba.setKontrolTutari(bb.getKontrolTutari());
						}
					}
					
				}
			}
		}
		setAsansorList(baList);
		if (baList.size() > 0) {
			List<BasvuruAsansorDTO> basvuruAsansorler = new ArrayList<BasvuruAsansorDTO>();
			try {
				
				basvuruAsansorler = new BasvuruBusiness()
						.getBasvuruAsansor(basvuruListeDTO.getBasvuruId());
			} catch (ReadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (basvuruAsansorler.size() > 0) {
				selectedAsansor = new BasvuruAsansorDTO[basvuruAsansorler
						.size()];
				int i = 0;

				for (BasvuruAsansorDTO b : basvuruAsansorler) {
					for (BasvuruAsansorDTO ba : baList) {
						if (ba.getCihazId() == b.getCihazId()) {
							selectedAsansor[i] = ba;
							i++;
						}
					}
				}
			}
			try {
				asansorList = new FirmaBusiness().getAsansorBakimci(
						asansorList, basvuruAsansorler, basvuruListeDTO.getBasvuruId());
			} catch (ReadException e) {

				e.printStackTrace();
			}

			setCihazlarVisible(true);
		}

		/*
		 * CommandButton
		 * button=(CommandButton)FacesContext.getCurrentInstance().
		 * getViewRoot().findComponent(":formbasvuru:btnbinabul"); CommandButton
		 * button1
		 * =(CommandButton)FacesContext.getCurrentInstance().getViewRoot()
		 * .findComponent(":formbasvuru:animsatbtn");
		 * //button.setRendered(false); button1.setVi(false); NotificationBar
		 * n=(NotificationBar)FacesContext.getCurrentInstance().getViewRoot().
		 * findComponent(":formbasvuru:notificationbar"); n.setRendered(false);
		 */

	}

	public List<BasvuruAsansorDTO> getAsansorList() {
		return asansorList;
	}

	public void setAsansorList(List<BasvuruAsansorDTO> asansorList) {
		this.asansorList = asansorList;
		asansorData = new BasvuruAsansorDataModel(asansorList);
	}

	public Basvuru getBasvuru() {
		return basvuru;
	}

	public void setBasvuru(Basvuru basvuru) {
		this.basvuru = basvuru;
	}

	public List<BasvuruListeDTO> getBasvurular(Date ilkTarih,Date sonTarih) throws CRUDException{
		List<BasvuruListeDTO> list=new ArrayList<BasvuruListeDTO>();
		try {
			list= new BasvuruBusiness().basvuruListesi(ilkTarih,sonTarih);
		} catch (CRUDException e) {
			throw new CRUDException(e.getMessage(), null);
		}
		return list==null?new ArrayList<BasvuruListeDTO>():list;
	}

	public void islemTamam(ActionEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage("");

		try {

			msg = nedenInsert();

			if (basvuru.getBasvuru().getBasvuruYapanTCKimlikNo() > 0) {
				binaKisisiEkle();
			}

			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			context.addMessage(null, msg);

			// RequestContext.getCurrentInstance().showMessageInDialog(msg);

			valueTCCheck = false;
			binaKisi.setSorumlulukTuru((short) 0);
			basvuru = new Basvuru();
			asansorList = new ArrayList<BasvuruAsansorDTO>();
			asansorData = null;
			selectedAsansor = null;
			cihazlarVisible = false;
			binaKisi = new BinaKisiDTO();
			binaTip = "";
			islemiTamamla = false;
			kontrolYapilmamaNeden = "0";

			Panel p = (Panel) FacesContext.getCurrentInstance().getViewRoot()
					.findComponent(":formbasvuru:pnlBina");
			p.setVisible(false);
			Panel p1 = (Panel) FacesContext.getCurrentInstance().getViewRoot()
					.findComponent(":formbasvuru:binaara");
			p1.setVisible(false);
			Wizard wiz = (Wizard) FacesContext.getCurrentInstance()
					.getViewRoot().findComponent(":formbasvuru:basvuruvizard");
			wiz.setStep("basvuru");
		} catch (CRUDException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, msg);
		}

	}

	public void kaydet(ActionEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage("");
		if (selectedAsansor != null && selectedAsansor.length > 0) {
			boolean firmaGirilmedi = false;
			for (BasvuruAsansorDTO b : selectedAsansor) {
				if (b.getBakimciFirmaId() <= 0) {
					firmaGirilmedi = true;
					break;
				}

			}
			if (!firmaGirilmedi) {
				try {

					if (!basvuruUpdate) {
						basvuruInsert();
					} else {
						basvuruUpdate();
						basvuruUpdate = false;
					}
					if (basvuru.getBasvuru().getBasvuruYapanTCKimlikNo() > 0) {
						binaKisisiEkle();
					}

					msg = getBasvuruMesaj();
					double kontrolTutari = 0.00;
					for (BasvuruAsansorDTO b : basvuruYapilanAsansorler) {
						kontrolTutari += b.getKontrolTutari() <= 0.01 ? 0.00
								: b.getKontrolTutari();

					}
					if (basvuru.getBasvuru().getePosta() != null
							&& basvuru.getBasvuru().getePosta().trim().length() > 0) {
						new MailBusiness().basvuruBilgisiEPostaGonder(
								basvuru.getBasvuru(),
								basvuru.getBina().getIl(), kontrolTutari);
					}
					msg.setSeverity(FacesMessage.SEVERITY_INFO);
					context.addMessage(null, msg);
					valueTCCheck = false;
					binaKisi.setSorumlulukTuru((short) 0);
					basvuru = new Basvuru();
					this.binaBean.setBinalar(new BinaDTO());
					this.binaBean.setTcKimlikNo("");
					this.binaBean.setBinaId(0);
					this.binaBean.setTescilno(0);
					asansorList = new ArrayList<BasvuruAsansorDTO>();
					asansorData = new BasvuruAsansorDataModel(asansorList);
					
					selectedAsansor = new BasvuruAsansorDTO[]{new BasvuruAsansorDTO()};
					cihazlarVisible = false;
					binaKisi = new BinaKisiDTO();
					binaTip = "";

					Panel p = (Panel) FacesContext.getCurrentInstance()
							.getViewRoot()
							.findComponent(":formbasvuru:pnlBina");
					p.setVisible(false);
					Panel p1 = (Panel) FacesContext.getCurrentInstance()
							.getViewRoot()
							.findComponent(":formbasvuru:binaara");
					p1.setVisible(false);
					Wizard wiz = (Wizard) FacesContext.getCurrentInstance()
							.getViewRoot()
							.findComponent(":formbasvuru:basvuruvizard");
					wiz.setStep("basvuru");
				
				} catch (CRUDException e) {
					msg = new FacesMessage(e.getMessage());
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					context.addMessage(null, msg);
				}
			} else {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_WARN,

						Messages._BAKIMCIFIRMAGIRILMEDI_.getMesaj(), null));
			}
		}
		/*
		 * RequestContext requestContext = RequestContext.getCurrentInstance();
		 * requestContext.execute("PF('bar').hide()");
		 */
		
	}

	public String taramaKaydet() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage("");
		if (selectedAsansor != null && selectedAsansor.length > 0) {
			boolean firmaGirilmedi = false;
			for (BasvuruAsansorDTO b : selectedAsansor) {
				if (b.getBakimciFirmaId() <= 0) {
					firmaGirilmedi = true;
					break;
				}

			}
			if (!firmaGirilmedi) {
				try {
					if (!basvuruUpdate) {
						taramaInsert();
					} else {

						taramaUpdate();
						basvuruUpdate = false;
					}

					if (basvuru.getTarama().getBinaSorumlusuTCKimlikNo() > 0) {
						binaKisisiEkle();
					}
					msg = getBasvuruMesaj();

					msg.setSeverity(FacesMessage.SEVERITY_INFO);
					context.addMessage(null, msg);

					dialogGoster = true;
					for (BasvuruAsansorDTO b : basvuruYapilanAsansorler) {

						if (b.getKontrolTutari() <= 0
								&& !b.getKontrolTuru().equals("E")) {
							dialogGoster = false;
							break;
						}
					}

					Dialog d = (Dialog) context.getViewRoot().findComponent(
							":formbasvuru:dlgrandevu");
					d.setVisible(true);
					Growl growl = (Growl) context.getViewRoot().findComponent(
							":formbasvuru:growl");
					growl.setRendered(false);
					if (dialogGoster) {

						RandevuBean randevuBean = (RandevuBean) context
								.getApplication().evaluateExpressionGet(
										context, "#{randevuBean}",
										RandevuBean.class);
						randevuBean.getMuhendisler(basvuru.getBina().getIl());
						randevuBean.getFirmalar(basvuru.getBina().getIl());
						BasvuruListeDTO basvuruListe = new BasvuruListeDTO(
								basvuru);
						randevuBean.getRandevu().setRandevuTarih(new Date());
						randevuBean.getRandevu().setRandevuSaati(
								new Time(new Date().getTime()));
						randevuBean.getRandevu().setSecilenMuhendisler(
								new ArrayList<Kullanici>());
						randevuBean.getRandevu().setBasvuru(basvuruListe);

					}
				} catch (CRUDException e) {
					msg = new FacesMessage(e.getMessage());
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					context.addMessage(null, msg);
				}

			} else {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_WARN,

						Messages._BAKIMCIFIRMAGIRILMEDI_.getMesaj(), null));
			}
		}
		/*
		 * RequestContext requestContext = RequestContext.getCurrentInstance();
		 * requestContext.execute("PF('bar').hide()");
		 */
		return "";
	}

	private void basvuruUpdate() throws CRUDException {

		basvuruYapilanAsansorler = new BasvuruBusiness().basvuruGuncelle(
				basvuru, selectedAsansor);

	}

	private void taramaUpdate() throws CRUDException {
		TaramaDTO tarama = new TaramaDTO(basvuru);
		tarama.setDurum("C");
		basvuru.setTarama(tarama);
		basvuru.getBasvuru().setBasvuruDurum("C");
		basvuru.getBasvuru().setBinaId(basvuru.getBina().getBinaId());
		basvuru.getBasvuru().setBasvuruYapanSorumlulukTuru(
				binaKisi.getSorumlulukTuru());
		basvuru.getBasvuru().setePosta(basvuru.getKisi().getePosta());
		basvuruYapilanAsansorler = new BasvuruBusiness().basvuruGuncelle(
				basvuru, selectedAsansor);
		new BasvuruBusiness().taramaUpdate(basvuru.getTarama());

	}

	public BasvuruAsansorDTO[] getSelectedAsansor() {
		return selectedAsansor;
	}

	public void setSelectedAsansor(BasvuruAsansorDTO[] selectedAsansor) {
		this.selectedAsansor = selectedAsansor;
	}

	@PreDestroy
	public void preDestroy() {
		basvuru = null;
		asansorList = null;
		selectedAsansor = null;
		asansorData = null;
	}

	public BasvuruListeModel getBasvuruListe() {
		return basvuruListe;
	}

	public void setBasvuruListe(BasvuruListeModel basvuruListe) {
		this.basvuruListe = basvuruListe;
	}

	public BasvuruListeDTO getSelectedBasvuru() {
		return selectedBasvuru;
	}

	public void setSelectedBasvuru(BasvuruListeDTO selectedBasvuru) {
		this.selectedBasvuru = selectedBasvuru;
	}

	public void basvuruSilListener(BasvuruListeDTO basvuru) {

		FacesMessage msg;
		try {
			if (basvuru.getBasvuruDurum() != null
					&& basvuru.getBasvuruDurum().equals("R")) {
				msg = new FacesMessage(
						Messages._RANDEVUVERILENBASVURU_.getMesaj());
				msg.setSeverity(FacesMessage.SEVERITY_FATAL);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				new BasvuruBusiness().basvuruSil(basvuru.getBasvuruId(),
						basvuru.getBinaId());
				String viewId = FacesContext.getCurrentInstance().getViewRoot()
						.getViewId();

				if (viewId.equals("/randevuver.xhtml")) {
					RandevuBean randevuBean = (RandevuBean) FacesContext
							.getCurrentInstance()
							.getApplication()
							.evaluateExpressionGet(
									FacesContext.getCurrentInstance(),
									"#{randevuBean}", RandevuBean.class);
					try {

						for (BasvuruListeDTO b : randevuBean.getBasvuruList()) {
							if (b.getBasvuruId() == basvuru.getBasvuruId()) {
								randevuBean.getBasvuruList().remove(b);
								break;
							}
						}
					} catch (Exception e) {
						randevuBean
								.setBasvuruList(new ArrayList<BasvuruListeDTO>());
					}
					randevuBean.setBasvuruListeModel(new BasvuruListeModel(
							randevuBean.getBasvuruList()));
				} else {
					try {
						basvuruListesi.remove(basvuru);
						/*
						for (BasvuruListeDTO b : basvuruListesi) {
							if (b.getBasvuruId() == basvuru.getBasvuruId()) {
								basvuruListesi.remove(b);
								break;
							}
						}
						*/
					} catch (Exception e) {
						basvuruListesi = new ArrayList<BasvuruListeDTO>();
					}

					basvuruListe = new BasvuruListeModel(basvuruListesi);
				}
				msg = new FacesMessage(Messages._BASVURUSILINDI_.getMesaj());
				msg.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext.getCurrentInstance().addMessage(null, msg);

			}
		} catch (DeleteException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public void odemeListener(BasvuruListeDTO basvuru) {

		selectedBasvuru = new BasvuruListeDTO();
		try {
			selectedBasvuru = (BasvuruListeDTO) basvuru.clone();
			OdemeBean odemeBean = new OdemeBean();
			odemeBean.setOdeme(new OdemeDTO());
			odemeBean.setOdemeReq(true);
			OdemeDTO odeme = new OdemeDTO();
			odeme.setToplamTutar(selectedBasvuru.getKontrolTutari());

			for (BinaDTO b : tumBinalar) {
				if (selectedBasvuru.getBinaId() == b.getBinaId()) {
					odeme.setBinaAdi(b.getBinaAdi());
					odeme.setBinaId(b.getBinaId());
					break;
				}
			}
			odemeBean.setOdeme(odeme);
			FacesContext
					.getCurrentInstance()
					.getApplication()
					.getExpressionFactory()
					.createValueExpression(
							FacesContext.getCurrentInstance().getELContext(),
							"#{odemeBean}", OdemeBean.class)

					.setValue(FacesContext.getCurrentInstance().getELContext(),
							odemeBean);

		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void odemeTutarListener(AjaxBehaviorEvent event) {
		Object obj = ((UIInput) event.getComponent()).getValue();
		if (obj != null) {

			FacesMessage msg;
			String odemeTutar = obj.toString();
			if (!odemeTutar.trim().equals("")
					&& Double.parseDouble(odemeTutar.trim()) > 0.00) {
				double tutar = Double.parseDouble(odemeTutar.trim());
				if (tutar > selectedBasvuru.getKontrolTutari()) {
					msg = new FacesMessage(
							Messages._ODEMETUTARIFAZLA_.getMesaj());
					msg.setSeverity(FacesMessage.SEVERITY_FATAL);
					FacesContext.getCurrentInstance().addMessage(null, msg);
				}
			} else {
				msg = new FacesMessage(Messages._ODEMETUTARIGIRINIZ_.getMesaj());
				msg.setSeverity(FacesMessage.SEVERITY_FATAL);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

		}
	}

	public void odemeSekliDegistir() {
		FacesMessage msg;

		Panel odemePanel = (Panel) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(":formbasvuruliste:odeme");
		odemePanel.setVisible(true);

		try {
			new OdemeBusiness().odemeSekliGuncelle(selectedBasvuru);
			for (BasvuruListeDTO l : basvuruListesi) {
				if (l.getBasvuruId() == selectedBasvuru.getBasvuruId()) {
					basvuruListesi.remove(l);
					basvuruListesi = (basvuruListesi == null ? new ArrayList<BasvuruListeDTO>()
							: basvuruListesi);
					break;
				}
			}
			selectedBasvuru = new BasvuruListeDTO();
			msg = new FacesMessage(Messages._ODEMEKAYDEDILDI_.getMesaj());
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			odemePanel.setVisible(false);

		} catch (CRUDException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public void odemeAl() {
		FacesMessage msg;

		Panel odemePanel = (Panel) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(":formbasvuruliste:odeme");
		odemePanel.setVisible(true);

		try {
			OdemeBean odemeBean = (OdemeBean) FacesContext
					.getCurrentInstance()
					.getApplication()
					.evaluateExpressionGet(FacesContext.getCurrentInstance(),
							"#{odemeBean}", OdemeBean.class);
			new OdemeBusiness().odemeKaydet(selectedBasvuru,
					odemeBean.getOdeme());
			for (BasvuruListeDTO l : basvuruListesi) {
				if (l.getBasvuruId() == selectedBasvuru.getBasvuruId()) {
					basvuruListesi.remove(l);
					basvuruListesi = (basvuruListesi == null ? new ArrayList<BasvuruListeDTO>()
							: basvuruListesi);
					break;
				}
			}
			selectedBasvuru = new BasvuruListeDTO();

			odemeBean.setOdemeReq(false);
			FacesContext
					.getCurrentInstance()
					.getApplication()
					.getExpressionFactory()
					.createValueExpression(
							FacesContext.getCurrentInstance().getELContext(),
							"#{odemeBean}", OdemeBean.class)

					.setValue(FacesContext.getCurrentInstance().getELContext(),
							odemeBean);
			msg = new FacesMessage(Messages._ODEMEKAYDEDILDI_.getMesaj());
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			odemePanel.setVisible(false);

		} catch (CRUDException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} catch (Exception e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public void linkBina(BasvuruListeDTO basvuruListeDTO) {
		FacesContext context = FacesContext.getCurrentInstance();
	
		if (basvuruListeDTO != null){
			for (BinaDTO binaDTO : tumBinalar) {
				if (binaDTO.getBinaId() == basvuruListeDTO.getBinaId()) {
					binaBean.getBinaBilgileri(binaDTO);
					break;
				}
			}
		}
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("window.open('binakayit.jsf')");
	}

	public String linkBasvuru(BasvuruListeDTO basvuruListeDTO) {

		if (basvuruListeDTO != null) {
			((HttpServletRequest) FacesContext.getCurrentInstance()
					.getExternalContext().getRequest()).getSession()
					.setAttribute("basvurulisteparam", basvuruListeDTO);
			/*
			 * RequestContext
			 * requestContext=RequestContext.getCurrentInstance();
			 * requestContext.execute("window.open('basvuru.jsf')");
			 */
			if (basvuruListeDTO.getTaramaId() > 0)
				return "tarama.jsf?faces-redirect=true";
			else
				return "basvuru.jsf?faces-redirect=true";
		}
		return "";

	}

	private void taramaInsert() throws CRUDException {
		TaramaDTO tarama = new TaramaDTO(basvuru);
		tarama.setDurum("C");
		basvuru.setTarama(tarama);
		basvuru.getBasvuru().setBasvuruDurum("C");
		basvuru.getBasvuru().setBinaId(basvuru.getBina().getBinaId());
		basvuru.getBasvuru().setBasvuruYapanSorumlulukTuru(
				binaKisi.getSorumlulukTuru());
		basvuru.getBasvuru().setePosta(basvuru.getKisi().getePosta());

		basvuruYapilanAsansorler = new BasvuruBusiness().setBasvuru(basvuru,
				selectedAsansor);
		basvuru.getTarama().setId(
				basvuruYapilanAsansorler.get(0).getBasvuruId());
		new BasvuruBusiness().setTarama(basvuru.getTarama());
		basvuru.getBasvuru().setBasvuruId(
				basvuruYapilanAsansorler.get(0).getBasvuruId());

	}

	private void basvuruInsert() throws CRUDException {

		basvuru.getBasvuru().setBasvuruDurum("C");
		basvuru.getBasvuru().setBinaId(basvuru.getBina().getBinaId());
		basvuru.getBasvuru().setBasvuruYapanSorumlulukTuru(
				binaKisi.getSorumlulukTuru());
		basvuru.getBasvuru().setePosta(basvuru.getKisi().getePosta());
		basvuruYapilanAsansorler = new BasvuruBusiness().setBasvuru(basvuru,
				selectedAsansor);
		basvuru.getBasvuru().setBasvuruId(
				basvuruYapilanAsansorler.get(0).getBasvuruId());

	}

	private FacesMessage getBasvuruMesaj() {

		FacesMessage msg;
		double bakimFiyat = 0.00;
		StringBuffer sb = new StringBuffer();

		StringBuffer sbf = new StringBuffer();
		for (BasvuruAsansorDTO b : basvuruYapilanAsansorler) {
			bakimFiyat += b.getKontrolTutari() <= 0.01 ? 0.00 : b
					.getKontrolTutari();
			if (b.getKontrolTutari() <= 0 && b.getKontrolTuru().equals("N")) {
				sbf.append(b.getKimlikNo());
				sbf.append(" ");
			}
		}
		sb.append(Messages._MUAYENETUTARBILDIRIM_.getMesaj());
		sb.append(String.valueOf(bakimFiyat));
		sb.append(" ");
		sb.append(Messages._MUAYENETUTARBANKAYAYATIRINIZ_.getMesaj());
		if (sbf.toString().trim().length() > 0) {
			sb.append("Muayene Tutarı hesaplanamayan asansörler var.Kontrol ediniz. Hesaplanmayan asansörlere ait Kimlik No : ");
			sb.append(sbf.toString());
			sb.append("Gerekli düzeltmeler yapıldıktan sonra ödeme bilgisi verilecektir.");
		}
		sb.append("Ödeme tutarı yatırıldıktan sonra kontrol tarihi e-posta ve sms yoluyla"
				+ " tarafınıza bildirilecektir.");

		if (basvuru.getBasvuru().getBasvuruId() > 0) {
			msg = new FacesMessage(Messages._BASVURU_KAYDEDILDI_.getMesaj()
					+ Messages._GETBASVURUID_.getMesaj()
					+ basvuru.getBasvuru().getBasvuruId() + " " + sb.toString());

		} else {
			msg = new FacesMessage(Messages._BASVURU_KAYDEDILEMEDI_.getMesaj());

		}
		return msg;

	}

	private FacesMessage nedenInsert() throws CRUDException {

		FacesMessage msg = null;

		TaramaDTO tarama = new TaramaDTO(basvuru);
		tarama.setDurum("C");
		tarama.setTaramaYapilamamaNedenKod(Integer
				.parseInt(kontrolYapilmamaNeden));
		basvuru.setTarama(tarama);

		try {
			new BasvuruBusiness().setTaramaYapilmadi(basvuru.getTarama());
			msg = new FacesMessage(Messages._SQL_513_.getMesaj());
		} catch (CRUDException e) {
			msg = new FacesMessage(Messages._SQL_512_.getMesaj());

		}

		return msg;
	}

	private void binaKisisiEkle() throws CRUDException {

		binaKisi.setAdi(basvuru.getBasvuru().getBasvuruYapanAdi());
		binaKisi.setBinaId(basvuru.getBina().getBinaId());
		binaKisi.setePosta(basvuru.getKisi().getePosta());
		binaKisi.setSoyadi(basvuru.getBasvuru().getBasvuruYapanSoyadi());
		binaKisi.setTcKimlikNo(basvuru.getBasvuru().getBasvuruYapanTCKimlikNo());
		binaKisi.setTelefonNoDahili(basvuru.getBasvuru().getTelefonNoDahili());
		String telefonNo = basvuru.getBasvuru().getTelefonNoStr();
		telefonNo = telefonNo.equals("") ? "0" : telefonNo.replace("(", "")
				.replace(")", "").replace(" ", "");
		if (telefonNo.indexOf("5") == 0) {
			binaKisi.setTelefonNoGsm(Long.parseLong(telefonNo));
		} else {
			binaKisi.setTelefonNo(Long.parseLong(telefonNo));
		}

		new BinaBusiness().setBinaKisiBasvurudan(binaKisi);

	}

	public BinaKisiDTO getBinaKisi() {
		return binaKisi;
	}

	public void setBinaKisi(BinaKisiDTO binaKisi) {
		this.binaKisi = binaKisi;
	}

	public boolean isValueTCCheck() {
		return valueTCCheck;
	}

	public void setValueTCCheck(boolean valueTCCheck) {
		this.valueTCCheck = valueTCCheck;
	}

	public void checkTC() {

		basvuru.getBasvuru().setBasvuruYapanTCKimlikNo(
				valueTCCheck ? 99999999999l : 0l);

	}

	public String onFlowProcess(FlowEvent event) {
		/*
		 * Wizard
		 * w=(Wizard)FacesContext.getCurrentInstance().getViewRoot().findComponent
		 * (":formbasvuru:basvuruvizard"); try { if
		 * (event.getOldStep().equals("bina")){
		 * 
		 * if (binaTip.equals("Y"))
		 * 
		 * } } catch (Exception e) {
		 * 
		 * }
		 */

		if (event.getOldStep().equals("bina")
				&& event.getNewStep().equals("asansor") && !binaTipSelected) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(
							"İşlem yapacağınız bina bilgisini giriniz!"));
			return event.getOldStep();
		}
		if (event.getOldStep().equals("bina")
				&& event.getNewStep().equals("asansor")) {
			if (this.basvuru.getBina().getBinaId()<=0){
				binaBean.binaKaydetBasvuru();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(
								"İşlem yapacağınız bina bilgisini giriniz!"));
				return event.getOldStep();
			}else{
			try {
				binadanOdemeAlinacak = new BinaBusiness()
						.isOdemeAlinacakMi(basvuru.getBina().getBinaId());
			} catch (CRUDException e) {
				binadanOdemeAlinacak = true;
			}
			}
		}

		if (event.getOldStep().equals("asansor")) {
			binaTip = "K";
		}
		if (binaBilgisiDegisti && event.getNewStep().equals("asansor")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Değişiklikleri Kaydediniz"));
			return event.getOldStep();
		}
		if (!skip && binaTip != null && binaTip.equals("Y")
				&& event.getNewStep().equals("asansor")) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Değişiklikleri Kaydediniz"));
			return event.getOldStep();
		}
		if (event.getNewStep().equals("asansor")) {
			List<FirmaDTO> firmaList = new ArrayList<FirmaDTO>();
			firmaList = new RandevuBean()
					.getFirmalar(basvuru.getBina().getIl());
			firmalar = new TreeMap<String, Integer>();
			for (FirmaDTO d : firmaList) {
				firmalar.put(d.getUnvan(), d.getKod());
			}

			try {
				asansorList = asansorList == null ? new ArrayList<BasvuruAsansorDTO>()
						: asansorList;
				if (!basvuruUpdate) {
					asansorList = asansorList.size() > 0 ? new FirmaBusiness()
							.getAsansorBakimci(asansorList,
									new ArrayList<BasvuruAsansorDTO>(), 0)
							: asansorList;
				}

				asansorData = new BasvuruAsansorDataModel(asansorList);
			} catch (CRUDException e) {
				e.printStackTrace();
			}

		}
		skip = false;
		if (skip) {
			return event.getOldStep();
		} else {

			return event.getNewStep();
		}
	}

	public void binaSelect(AjaxBehaviorEvent event) {
		Panel panelBina = (Panel) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(":formbasvuru:pnlBina");
		Panel panelArama = (Panel) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(":formbasvuru:binaara");
		basvuru.setBina(new BinaDTO());
		if (binaTip.equals("Y")) {
			panelBina.setVisible(true);
			panelArama.setVisible(false);

		}
		if (binaTip.equals("K")) {

			panelArama.setVisible(true);
			panelBina.setVisible(false);

		}
		binaTipSelected = true;
		binaBean
				.setBinaDTO(new BinaDTO());
		binaBean
				.setBelediyeler(new ArrayList<SelectItem>());
		binaBean
				.setIlceler(new TreeMap<String, Integer>());
		binaBean
				.setMahalleList(new ArrayList<Mahalle>());
		binaBean
				.setCaddeSokakList(new ArrayList<CaddeSokak>());
	}

	public boolean isBinaBilgisiDegisti() {
		return binaBilgisiDegisti;
	}

	public void setBinaBilgisiDegisti(boolean binaBilgisiDegisti) {
		this.binaBilgisiDegisti = binaBilgisiDegisti;
	}

	public boolean isKontrolSekli() {
		return kontrolSekli;
	}

	public void setKontrolSekli(boolean kontrolSekli) {
		this.kontrolSekli = kontrolSekli;
	}

	public boolean isBinaTipSelected() {
		return binaTipSelected;
	}

	public void setBinaTipSelected(boolean binaTipSelected) {
		this.binaTipSelected = binaTipSelected;
	}

	public boolean isIslemiTamamla() {
		return islemiTamamla;
	}

	public void setIslemiTamamla(boolean islemiTamamla) {
		this.islemiTamamla = islemiTamamla;
	}

	public void kontrolYapilmamaNedenListener(AjaxBehaviorEvent event) {
		islemiTamamla = false;
		String str = ((UISelectOne) event.getComponent()).getValue().toString();
		if (str != null && !str.equals("0")) {
			islemiTamamla = true;
		}
	}

	public String getKontrolYapilmamaNeden() {
		return kontrolYapilmamaNeden;
	}

	public void setKontrolYapilmamaNeden(String kontrolYapilmamaNeden) {
		this.kontrolYapilmamaNeden = kontrolYapilmamaNeden;
	}

	public void taramaFirmaMuhendisPanelCloseListener(CloseEvent event) {

		valueTCCheck = false;
		binaKisi.setSorumlulukTuru((short) 0);
		basvuru = new Basvuru();
		asansorList = new ArrayList<BasvuruAsansorDTO>();
		asansorData = null;
		selectedAsansor = null;
		cihazlarVisible = false;
		binaKisi = new BinaKisiDTO();
		binaTip = "";

		Panel p = (Panel) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent(":formbasvuru:pnlBina");
		p.setVisible(false);
		Panel p1 = (Panel) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent(":formbasvuru:binaara");
		p1.setVisible(false);
		Wizard wiz = (Wizard) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent(":formbasvuru:basvuruvizard");
		wiz.setStep("basvuru");

	}

	public void firmaMuhendisAta() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage mesaj = new FacesMessage();
		RandevuBean randevuBean = (RandevuBean) context.getApplication()
				.evaluateExpressionGet(context, "#{randevuBean}",
						RandevuBean.class);
		int randevuId = 0;
		try {

			randevuId = randevuBean.randevuEkleByTarama(basvuru);
			// mesaj=new FacesMessage(Messages._RANDEVU_KAYDEDILDI_.getMesaj());
			// mesaj.setSeverity(FacesMessage.SEVERITY_INFO);
			try {
				this.binaBean.setRandevuId(randevuId);
				this.binaBean.setRandevuStr(String.valueOf(binaBean.getRandevuId()));

				this.binaBean.asansorTest();
				context.getExternalContext().redirect("kontrol.jsf");
			} catch (IOException e) {

				e.printStackTrace();
			}

		} catch (CRUDException e) {

			mesaj = new FacesMessage(e.getMessage());
			mesaj.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, mesaj);
		}

	}

	public void asansorEkleListener(ActionEvent event) {
		Panel panel = (Panel) FacesContext.getCurrentInstance().getViewRoot()
				.findComponent(":formbasvuru:panelasansorekle");
		panel.setVisible(true);
	}

	public List<TaramaYapilmayanBinalar> getTaramaYapilmayanBinaListe() {
		return taramaYapilmayanBinaListe;
	}

	public void setTaramaYapilmayanBinaListe(
			List<TaramaYapilmayanBinalar> taramaYapilmayanBinaListe) {
		this.taramaYapilmayanBinaListe = taramaYapilmayanBinaListe;
	}

	public List<TaramaYapilmayanBinalar> getFilteredTaramaVal() {
		return filteredTaramaVal;
	}

	public void setFilteredTaramaVal(
			List<TaramaYapilmayanBinalar> filteredTaramaVal) {
		this.filteredTaramaVal = filteredTaramaVal;
	}

	public boolean isDialogGoster() {
		return dialogGoster;
	}

	public void setDialogGoster(boolean dialogGoster) {
		this.dialogGoster = dialogGoster;
	}

	public boolean isBbVal() {
		return bbVal;
	}

	public void setBbVal(boolean bbVal) {
		this.bbVal = bbVal;
	}

	public void renderNotification(AjaxBehaviorEvent event) {
		RequestContext context = RequestContext.getCurrentInstance();
		if (bbVal)
			context.execute("PF('bb').show()");
		else
			context.execute("PF('bb').hide()");
	}

	public List<BasvuruAsansorDTO> getTutarHesaplanmayanAsansorler() {
		return tutarHesaplanmayanAsansorler;
	}

	public void setTutarHesaplanmayanAsansorler(
			List<BasvuruAsansorDTO> tutarHesaplanmayanAsansorler) {
		this.tutarHesaplanmayanAsansorler = tutarHesaplanmayanAsansorler;
	}

	public List<BinaDTO> getTumBinalar() {
		return tumBinalar;
	}

	public void setTumBinalar(List<BinaDTO> tumBinalar) {
		this.tumBinalar = tumBinalar;
	}

	public void closePanelListener(CloseEvent event) {
		OdemeBean odemeBean = (OdemeBean) FacesContext
				.getCurrentInstance()
				.getApplication()
				.evaluateExpressionGet(FacesContext.getCurrentInstance(),
						"#{odemeBean}", OdemeBean.class);

		odemeBean.setOdemeReq(false);
		FacesContext
				.getCurrentInstance()
				.getApplication()
				.getExpressionFactory()
				.createValueExpression(
						FacesContext.getCurrentInstance().getELContext(),
						"#{odemeBean}", OdemeBean.class)

				.setValue(FacesContext.getCurrentInstance().getELContext(),
						odemeBean);
	}

	public TreeMap<String, Integer> getFirmalar() {
		return firmalar;
	}

	public void setFirmalar(TreeMap<String, Integer> firmalar) {
		this.firmalar = firmalar;
	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	private double cihazBakimFiyatHesapla(int cihazTip, int kapasiteDurak,
			int kapasiteAgirlik,String kontrolTipi) {
		double fiyat = 0.00;

		try {
			fiyat = binadanOdemeAlinacak ? new AsansorBusiness()
					.getAsansorBakimFiyat(cihazTip, basvuru.getBina()
							.getBelediye(), kapasiteDurak, kapasiteAgirlik,
							basvuru.getBina().getSozlesmeBinaTipId(),kontrolTipi) : 0.00;
		} catch (CRUDException e) {
			fiyat = 0.00;
			e.printStackTrace();
		}
		return fiyat;

	}

	private String cihazKontrolTuru(int cihazId) {
		SoruTableBean soruTableBean = new SoruTableBean();
		soruTableBean.getCihazKontroller(cihazId);
	
		String kontrolTuru = "N";
		for (KontrolDTO k : soruTableBean.oncekiKontrollerList) {
			if (k.getKontrolTuru().equals("N")) {
				long oncekiKontrolGecenGunSayisi = DateUtils
						.differenceBetweenTwoDates(k.getKontrolBitisTarihi(),
								new Date());
				kontrolTuru = oncekiKontrolGecenGunSayisi < 350 ? "E" : "N";
				break;
			}
		}
		return kontrolTuru;
	}

	public void rowUnSelectListener(UnselectEvent event) {
		BasvuruAsansorDTO bas = ((BasvuruAsansorDTO) event.getObject());
		
		if (selectedAsansor != null) {
			if (bas != null) {
			
				for (BasvuruAsansorDTO b : asansorList) {
					
					if (b.getCihazId() == bas.getCihazId()) {
						b.setKontrolTuru("");
						b.setKontrolTutari(0.00);
						
						
					}
				}
			}
			if (selectedAsansor.length == 0) {
				for (BasvuruAsansorDTO b : asansorList) {
					b.setKontrolTuru("");
					b.setKontrolTutari(0.00);
				}
			}
			

		}else{
			for (BasvuruAsansorDTO b : asansorList) {
				b.setKontrolTuru("");
				b.setKontrolTutari(0.00);
			}
		}
		asansorData = new BasvuruAsansorDataModel(asansorList);
	
	}

	public void rowSelectListener(SelectEvent event) {
		
		if (selectedAsansor != null) {
			for (BasvuruAsansorDTO ba : selectedAsansor) {
				for (BasvuruAsansorDTO b : asansorList) {

					if (b.getCihazId() == ba.getCihazId()) {
						if (b.getKontrolTuru() == null
								|| b.getKontrolTuru().trim().equals("")) {
							b.setKontrolTuru(cihazKontrolTuru(ba.getCihazId()));
						}
						if (b.getKontrolTutari()<=0){
						if (!b.getKontrolTuru().equals("E")) {
							
							b.setKontrolTutari(cihazBakimFiyatHesapla(
									ba.getCihazTip(), ba.getKapasiteDurak(),
									ba.getKapasiteAgirlik(),b.getKontrolTuru()));
						} else {
							b.setKontrolTutari(0);
						}
						}
					}
				}
			}
			if (selectedAsansor.length == 0) {
				for (BasvuruAsansorDTO b : asansorList) {
					b.setKontrolTuru("");
					b.setKontrolTutari(0.00);
				}
			}
			asansorData = new BasvuruAsansorDataModel(asansorList);

		}
		
	}

	public void rowToggleSelectListener(ToggleSelectEvent event) {

		if (selectedAsansor != null) {
			for (BasvuruAsansorDTO ba : selectedAsansor) {
				for (BasvuruAsansorDTO b : asansorList) {

					if (b.getCihazId() == ba.getCihazId()) {
						if (b.getKontrolTuru() == null
								|| b.getKontrolTuru().trim().equals("")) {
							b.setKontrolTuru(cihazKontrolTuru(ba.getCihazId()));
						}
						if (b.getKontrolTutari()<=0){
						if (!b.getKontrolTuru().equals("E")) {
							b.setKontrolTutari(cihazBakimFiyatHesapla(
									ba.getCihazTip(), ba.getKapasiteDurak(),
									ba.getKapasiteAgirlik(),b.getKontrolTuru()));
						} else {
							b.setKontrolTutari(0);
						}
						}
					}
				}
			}
			if (selectedAsansor.length == 0) {
				for (BasvuruAsansorDTO b : asansorList) {
					b.setKontrolTuru("");
					b.setKontrolTutari(0.00);
				}
			}
			asansorData = new BasvuruAsansorDataModel(asansorList);

		}
	}

	public void rowEditListener(RowEditEvent event) {
		BasvuruAsansorDTO ba = ((BasvuruAsansorDTO) event.getObject());
		if (ba != null) {
			for (BasvuruAsansorDTO b : asansorList) {
				if (b.getCihazId() == ba.getCihazId()) {
					if (b.getKontrolTuru() == null
							|| b.getKontrolTuru().trim().equals("")) {
						b.setKontrolTuru(cihazKontrolTuru(ba.getCihazId()));
					}
					if (!b.getKontrolTuru().equals("E")) {
						b.setKontrolTutari(cihazBakimFiyatHesapla(
								ba.getCihazTip(), ba.getKapasiteDurak(),
								ba.getKapasiteAgirlik(),ba.getKontrolTuru()));
					} else {
						b.setKontrolTutari(0);
					}
				}
			}
			asansorData = new BasvuruAsansorDataModel(asansorList);

		}
	}

	public BinaBean getBinaBean() {
		return binaBean;
	}

	public void setBinaBean(BinaBean binaBean) {
		this.binaBean = binaBean;
	}

	public BasvuruAsansorDTO[] getSelectedFirmaAsansor() {
		return selectedFirmaAsansor;
	}

	public void setSelectedFirmaAsansor(BasvuruAsansorDTO[] selectedFirmaAsansor) {
		this.selectedFirmaAsansor = selectedFirmaAsansor;
	}
	
	private CihazFirmaDTO cihazFirma = new CihazFirmaDTO();
	public CihazFirmaDTO getCihazFirma() {
		return cihazFirma;
	}

	public void setCihazFirma(CihazFirmaDTO cihazFirma) {
		this.cihazFirma = cihazFirma;
	}
	
public void bakimciFirmaKaydet_() {
		
		FacesMessage msg;

		if (selectedFirmaAsansor != null && selectedFirmaAsansor.length>0) {
			if (this.cihazFirma.getSozlesmeTarih() != null && this.cihazFirma.getSozlesmeBitisTarih() != null
					&& this.cihazFirma.getSozlesmeBitisTarih().before(this.cihazFirma.getSozlesmeTarih()))
					 {
				

				 msg = new FacesMessage(
						Messages._KONTROLTARIHIHATALI_.getMesaj());
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}else{
		try {
			
			new FirmaBusiness().setAsansorBakimciFirmaBasvuru(cihazFirma,selectedFirmaAsansor);
			
			setAsansorList(new FirmaBusiness().getAsansorBakimci(asansorList,
					new ArrayList<BasvuruAsansorDTO>(), 0));
			cihazFirma = new CihazFirmaDTO();
			msg = new FacesMessage(Messages._SQL_513_.getMesaj());
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (Exception e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
			}
			
		} else {
			msg = new FacesMessage(Messages._CIHAZSECMEDINIZ_.getMesaj());
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}


private List<FirmaDTO> firmaList=new ArrayList<FirmaDTO>();
public List<FirmaDTO> getFirmaList() {
	return firmaList;
}

public void setFirmaList(List<FirmaDTO> firmaList) {
	this.firmaList = firmaList;
}
public void bakimciFirmaListener(ActionEvent event){
	selectedFirmaAsansor=new BasvuruAsansorDTO[]{};
	cihazFirma=new CihazFirmaDTO();
	
	
}

public List<BasvuruListeDTO> getBasvuruListesi() {
	return basvuruListesi;
}

public void setBasvuruListesi(List<BasvuruListeDTO> basvuruListesi) {
	this.basvuruListesi = basvuruListesi;
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
public void asansorGuncelle(BasvuruAsansorDTO cihaz) {
	FacesContext context = FacesContext.getCurrentInstance();

if (binaBean==null)
	binaBean=new BinaBean();
	
		if (binaBean.getBinaDTO()==null ||
				binaBean.getBinaDTO().getBinaId()!=this.basvuru.getBina().getBinaId()
				){
			binaBean.getBinaBilgileri(this.basvuru.getBina());
		}
		((HttpServletRequest) context.getExternalContext().getRequest())
				.getSession().setAttribute("bina", binaBean.getBinaDTO());
		for (CihazDTO c:binaBean.getCihazList()){
			((HttpServletRequest) context.getExternalContext().getRequest())
			.getSession().setAttribute("cihaz", c);
			if (cihaz.getCihazId()==c.getCihazId()){
				break;
			}
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


}
