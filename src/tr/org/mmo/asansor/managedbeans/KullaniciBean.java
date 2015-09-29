package tr.org.mmo.asansor.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;

import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.beans_.UavtBina;
import tr.org.mmo.asansor.business.BirimBusiness;
import tr.org.mmo.asansor.business.KullaniciBusiness;
import tr.org.mmo.asansor.business.MailBusiness;
import tr.org.mmo.asansor.business.RolBusiness;
import tr.org.mmo.asansor.dto.BelediyeDTO;
import tr.org.mmo.asansor.dto.BirimDTO;
import tr.org.mmo.asansor.dto.KullaniciRolYetkiDTO;
import tr.org.mmo.asansor.dto.RolYetkiDTO;
import tr.org.mmo.asansor.dto.RolYetkiRelationDTO;
import tr.org.mmo.asansor.dto.SubeDTO;
import tr.org.mmo.asansor.dto.TemsilcilikDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;
import tr.org.mmo.asansor.models.KullaniciDataModel;
import tr.org.mmo.asansor.models.RolYetkiModel;
import tr.org.mmo.asansor.util.KullaniciComparator;
import tr.org.mmo.asansor.util.Messages;
import tr.org.mmo.asansor.util.Util;

@ManagedBean
@ViewScoped
public class KullaniciBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5393442990836342874L;

	private List<Kullanici> kullaniciListSmall;
	private List<RolYetkiDTO> tumRoller;
	private List<RolYetkiDTO> tumYetkiler;
	private List<RolYetkiDTO> kullaniciRolleri;
	private List<RolYetkiDTO> kullaniciYetkileri;

	private RolYetkiModel yetkiModel;
	private Kullanici slctKullanici = new Kullanici();
	private List<Kullanici> filteredList;
	private KullaniciDataModel kullaniciListModel;

	private DualListModel<String> subeler;
	private List<SubeDTO> subeList = new ArrayList<SubeDTO>();
	private DualListModel<String> temsilcilikler;
	private List<TemsilcilikDTO> temsilcilikList = new ArrayList<TemsilcilikDTO>();
	private List<KullaniciRolYetkiDTO> kullaniciRolYetkiListesi = new ArrayList<KullaniciRolYetkiDTO>();
	private boolean kullaniciGuncelle = false;
	private int kullaniciRolId;
	private RolYetkiDTO secilenYetki;

	public List<Kullanici> getFilteredList() {
		return filteredList;
	}

	public void setFilteredList(List<Kullanici> filteredList) {
		this.filteredList = filteredList;
	}

	public KullaniciDataModel getKullaniciListModel() {
		return kullaniciListModel;
	}

	public void setKullaniciListModel(KullaniciDataModel kullaniciListModel) {
		this.kullaniciListModel = kullaniciListModel;
	}

	public Kullanici getSlctKullanici() {
		return slctKullanici;
	}

	public void setSlctKullanici(Kullanici slctKullanici) {
		this.slctKullanici = slctKullanici;
	}

	public void listeGetir() {

		kullaniciListSmall = new ArrayList<Kullanici>();
		kullaniciRolleri = new ArrayList<RolYetkiDTO>();
		kullaniciYetkileri = new ArrayList<RolYetkiDTO>();
		tumRoller = new ArrayList<RolYetkiDTO>();
		tumYetkiler = new ArrayList<RolYetkiDTO>();
		yetkiModel = new RolYetkiModel(tumYetkiler);

		try {
			kullaniciListSmall = new KullaniciBusiness().listeGetir();

			Collections.sort(kullaniciListSmall, new KullaniciComparator());

			setKullaniciListModel(new KullaniciDataModel(kullaniciListSmall));

		} catch (CRUDException e) {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));

			e.printStackTrace();
		}
	}

	@PostConstruct
	public void init() {

		String viewId = FacesContext.getCurrentInstance().getViewRoot()
				.getViewId();

		if (viewId.equals("/kullaniciListe.xhtml")) {
			listeGetir();

			if (subeList != null || subeList.size() == 0) {
				getTumSubeler();
			}
			if (temsilcilikList != null || temsilcilikList.size() == 0) {
				getTumTemsilcilikler();
			}

			List<String> subeSource = new ArrayList<String>();
			for (SubeDTO sube : subeList) {
				subeSource.add(sube.getSube());
			}
			List<String> subeTarget = new ArrayList<String>();
			subeler = new DualListModel<String>(subeSource, subeTarget);

			List<String> temsilcilikSource = new ArrayList<String>();
			for (TemsilcilikDTO temsilcilik : temsilcilikList) {
				temsilcilikSource.add(temsilcilik.getAdi());
			}
			List<String> temsilcilikTarget = new ArrayList<String>();
			temsilcilikler = new DualListModel<String>(temsilcilikSource,
					temsilcilikTarget);

			RolBean rolBean = new RolBean();
			try {
				tumRoller = rolBean.rolListesiGetir();
				tumYetkiler = rolBean.yetkiListesiGetir();
			} catch (CRUDException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public DualListModel<String> getSubeler() {
		return subeler;
	}

	public void setSubeler(DualListModel<String> subeler) {
		this.subeler = subeler;
	}

	public DualListModel<String> getTemsilcilikler() {
		return temsilcilikler;
	}

	public void setTemsilcilikler(DualListModel<String> temsilcilikler) {
		this.temsilcilikler = temsilcilikler;
	}

	public void onRowSelect(Kullanici kullanici) {

		try {
			kullaniciGuncelle = false;
			setSlctKullanici((Kullanici) kullanici.clone());

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		// return "kullanicikayit";
	}

	private void getKullaniciRoller(int kullaniciId) throws CRUDException {
		kullaniciRolId = 0;
		kullaniciRolleri = new ArrayList<RolYetkiDTO>();
		kullaniciYetkileri = new ArrayList<RolYetkiDTO>();

		for (RolYetkiDTO r : tumRoller) {
			kullaniciRolleri.add(r);
		}

		for (RolYetkiDTO y : tumYetkiler) {
			kullaniciYetkileri.add(y);
		}

		kullaniciRolYetkiListesi = new KullaniciBusiness()
				.getKullaniciRoller(kullaniciId);

		for (RolYetkiDTO r : kullaniciYetkileri) {
			for (KullaniciRolYetkiDTO ky : kullaniciRolYetkiListesi) {
				if (ky.getYetkiId() == r.getId()) {
					ky.setYetkiAdi(r.getAdi());
					break;
				}
			}
		}
		for (RolYetkiDTO r : kullaniciRolleri) {
			for (KullaniciRolYetkiDTO ky : kullaniciRolYetkiListesi) {
				if (ky.getRolId() == r.getId()) {
					kullaniciRolId = ky.getRolId();
					ky.setRolAdi(r.getAdi());
					break;
				}
			}
		}

		for (KullaniciRolYetkiDTO ry : kullaniciRolYetkiListesi) {

			// for (RolYetkiDTO r : tumRoller) {
			// if (r.getId() == ry.getRolId()) {
			// kullaniciRolleri.remove(r);
			// break;
			// }
			// }
			try {
				for (RolYetkiDTO y : tumYetkiler) {
					if (ry.getYetkiId() == y.getId()) {
						kullaniciYetkileri.remove(y);

					}
				}
			} catch (Exception e) {
				kullaniciYetkileri = new ArrayList<RolYetkiDTO>();
			}
		}

		yetkiModel = new RolYetkiModel(kullaniciYetkileri);

	}

	private void getKullaniciBirimler() throws ReadException {
		List<BirimDTO> birimler = new ArrayList<BirimDTO>();
		birimler = new BirimBusiness().getKullaniciBirimler(slctKullanici);
		List<String> sSource = new ArrayList<String>();
		List<String> sTarget = new ArrayList<String>();

		List<String> tSource = new ArrayList<String>();
		Map<Integer, String> tTargetMap = new HashMap<Integer, String>();
		List<String> tTarget = new ArrayList<String>();

		if (birimler != null) {
			for (BirimDTO birim : birimler) {
				if (birim.getBirimTipi().equals("S")) {

					for (SubeDTO s : subeList) {
						if (s.getKod() == birim.getBirimKodu()) {
							sTarget.add(s.getSube());
							break;
						}
					}

				} else {
					for (TemsilcilikDTO t : temsilcilikList) {
						if (t.getKod() == birim.getBirimKodu()) {
							// tTarget.add(t.getAdi());
							tTargetMap.put(t.getKod(), t.getAdi());
							break;
						}
					}

				}
			}
		}
		Set<Integer> keySet = tTargetMap.keySet();
		Iterator<Integer> it = keySet.iterator();
		while (it.hasNext()) {
			int i = it.next();
			tTarget.add(tTargetMap.get(i));
		}

		for (SubeDTO sube : subeList) {
			sSource.add(sube.getSube());
			for (String s : sTarget) {
				if (s.equals(sube.getSube())) {

					sSource.remove(s);
				}
			}
		}
		for (TemsilcilikDTO temsilcilik : temsilcilikList) {
			tSource.add(temsilcilik.getAdi());
			for (String t : tTarget) {
				if (t.equals(temsilcilik.getAdi())) {

					tSource.remove(t);
				}
			}
		}

		subeler = new DualListModel<String>(sSource, sTarget);
		temsilcilikler = new DualListModel<String>(tSource, tTarget);

	}

	private void getTumTemsilcilikler() {
		try {
			temsilcilikList = new BirimBusiness().temsilcilikler();
		} catch (ReadException e) {

			e.printStackTrace();
		}
	}

	private void getTumSubeler() {
		try {
			subeList = new BirimBusiness().subeler();
		} catch (ReadException e) {

			e.printStackTrace();
		}
	}

	public void rowSelectListener(SelectEvent event) {
		Kullanici k=((Kullanici) event.getObject());
		if (k!=null){
			if (k.getKullaniciTuru()!=99){
		try {
			
			getKullaniciRoller(k.getKullaniciId());
			getKullaniciBirimler();
			
			kullaniciGuncelle = false;
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
		}
			}else{
				kullaniciGuncelle = true;
			}
		}
	}

	public void yetkiKaydet(ActionEvent event) {

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		if (kullaniciRolYetkiListesi != null) {
			slctKullanici.setRoller(kullaniciRolYetkiListesi);
			try {
				new KullaniciBusiness().yetkiInsert(slctKullanici);
				msg = new FacesMessage(Messages._YETKIVERILDI_.getMesaj());
				msg.setSeverity(FacesMessage.SEVERITY_INFO);
			} catch (CreateException e) {
				msg = new FacesMessage(e.getMessage());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			}
			context.addMessage(null, msg);

		}
	}

	public void birimKaydet(ActionEvent event) {

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		List<BirimDTO> birimList = new ArrayList<BirimDTO>();
		for (String s : subeler.getTarget()) {
			BirimDTO b = new BirimDTO();
			for (SubeDTO s1 : subeList) {
				if (s1.getSube().trim().equals(s.trim())) {
					b.setBirimKodu(s1.getKod());
					b.setBirimTipi("S");
					birimList.add(b);
					break;
				}
			}

		}
		for (String t : temsilcilikler.getTarget()) {
			BirimDTO b = new BirimDTO();
			for (TemsilcilikDTO t1 : temsilcilikList) {
				if (t1.getAdi().trim().equals(t.trim())) {
					b.setBirimKodu(t1.getKod());
					b.setBirimTipi("T");
					birimList.add(b);
					break;
				}

			}

		}
		if (birimList.size() > 0) {
			try {
				slctKullanici.setBirimler(birimList);

				new KullaniciBusiness().birimInsert(slctKullanici);
				msg = new FacesMessage(Messages._YETKIVERILDI_.getMesaj());
				msg.setSeverity(FacesMessage.SEVERITY_INFO);

				kullaniciGuncelle = false;
			} catch (CRUDException e) {
				msg = new FacesMessage(e.getMessage());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			}
		} else {
			msg = new FacesMessage(Messages._BIRIMSECINIZ_.getMesaj());
			msg.setSeverity(FacesMessage.SEVERITY_WARN);
		}

		context.addMessage(null, msg);

	}

	public void parolaDegistir() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;

		try {
			if (slctKullanici != null && slctKullanici.getParola() != null
					&& !slctKullanici.getParola().trim().equals("")) {
				new KullaniciBusiness().setParola(slctKullanici.getParola());
				msg = new FacesMessage(Messages._PAROLADEGISTIR_.getMesaj());
				msg.setSeverity(FacesMessage.SEVERITY_INFO);
				LoginBean loginBean = (LoginBean) Common.findBean("loginBean");
				loginBean.setPass(slctKullanici.getParola());
				slctKullanici = new Kullanici();
				// requestContext.execute("document.forms.reset()");
				// requestContext.execute("PF('parola').hide()");
			} else {

				msg = new FacesMessage(Messages._PAROLAGIRINIZ_.getMesaj());
				msg.setSeverity(FacesMessage.SEVERITY_FATAL);
				// requestContext.execute("PF('parola').show()");
			}

		} catch (UpdateException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			// requestContext.execute("PF('parola').show()");
		}

		context.addMessage(null, msg);

	}

	public void parolaDegistir(ActionEvent event) {

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;

		try {
			slctKullanici = new KullaniciBusiness()
					.kullaniciByKullaniciAdi(slctKullanici.getKullaniciAdi());
			if (slctKullanici.getePosta() != null
					&& slctKullanici.getePosta().length() > 1) {
				String parola = Util.randomAlphanumeric();
				new KullaniciBusiness().yeniParola(
						slctKullanici.getKullaniciAdi(), parola);

				new MailBusiness().mailGonder(slctKullanici.getePosta(),
						parola, slctKullanici.getIl());
				msg = new FacesMessage(
						"Şifreniz değiştirilerek sistemde tanımlı e-posta adresinize gönderilmiştir.Kontrol ediniz...");
				msg.setSeverity(FacesMessage.SEVERITY_INFO);

			} else {
				msg = new FacesMessage(
						"Sistemde size  ait bir e-posta adresi bulunmamaktadır.");
				msg.setSeverity(FacesMessage.SEVERITY_FATAL);
			}

		} catch (CRUDException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);

		}
		context.addMessage(null, msg);

	}

	public void durumGuncelle(ActionEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;
		if (slctKullanici != null) {

			try {

				new KullaniciBusiness().kullaniciDurumGuncelle(slctKullanici);
				for (Kullanici k : kullaniciListSmall) {
					if (k.getKullaniciId() == slctKullanici.getKullaniciId()) {
						k.setDurum(slctKullanici.getDurum());
						break;
					}
				}
				StringBuilder sb = new StringBuilder();
				sb.append("Kullanıcı Güncellendi");
				// sb.append(slctKullanici.getDurum().equals("A")?"Aktif":"Pasif");
				// sb.append(" hale getirildi");
				kullaniciGuncelle = false;
				msg = new FacesMessage(sb.toString());
				msg.setSeverity(FacesMessage.SEVERITY_INFO);
				context.addMessage(null, msg);
			} catch (CRUDException e) {
				msg = new FacesMessage(e.getMessage());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				context.addMessage(null, msg);
			}

		}

	}

	public void kullaniciKaydet(ActionEvent event) {

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		try {
			new KullaniciBusiness().ekle(slctKullanici);
			msg = new FacesMessage(Messages._UYEKAYDEDILDI_.getMesaj() + " "
					+ Messages._PAROLABILGI_.getMesaj());
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			RequestContext requestContext = RequestContext.getCurrentInstance();
			requestContext.execute("document.getElementById('formb').reset()");
		} catch (CreateException e) {
			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);

		}
		context.addMessage(null, msg);

	}

	public boolean isKullaniciGuncelle() {
		return kullaniciGuncelle;
	}

	public void setKullaniciGuncelle(boolean kullaniciGuncelle) {
		this.kullaniciGuncelle = kullaniciGuncelle;
	}

	public void kullaniciTuruListener(AjaxBehaviorEvent event) {
		int kTur = Integer.parseInt(((UIInput) (event.getComponent()))
				.getValue().toString());
		if (kTur == 1) {
			slctKullanici.setOnayYetkisi("E");
			slctKullanici.setAkreditasyonDeger("E");
		} else {
			slctKullanici.setOnayYetkisi("H");
			slctKullanici.setAkreditasyonDeger("H");

		}
	}

	public void onayAkreditasyonListener(AjaxBehaviorEvent event) {
		String kod = ((UIInput) (event.getComponent())).getValue().toString();

		slctKullanici.setOnayYetkisi(kod);
		slctKullanici.setAkreditasyonDeger(kod);

	}

	public List<KullaniciRolYetkiDTO> getKullaniciRolYetkiListesi() {
		return kullaniciRolYetkiListesi;
	}

	public void setKullaniciRolYetkiListesi(
			List<KullaniciRolYetkiDTO> kullaniciRolYetkiListesi) {
		this.kullaniciRolYetkiListesi = kullaniciRolYetkiListesi;
	}

	public int getKullaniciRolId() {
		return kullaniciRolId;
	}

	public void setKullaniciRolId(int kullaniciRolId) {
		this.kullaniciRolId = kullaniciRolId;
	}

	public List<RolYetkiDTO> getKullaniciRolleri() {
		return kullaniciRolleri;
	}

	public void setKullaniciRolleri(List<RolYetkiDTO> kullaniciRolleri) {
		this.kullaniciRolleri = kullaniciRolleri;
	}

	public List<RolYetkiDTO> getKullaniciYetkileri() {
		return kullaniciYetkileri;
	}

	public void setKullaniciYetkileri(List<RolYetkiDTO> kullaniciYetkileri) {
		this.kullaniciYetkileri = kullaniciYetkileri;
	}

	public RolYetkiModel getYetkiModel() {
		return yetkiModel;
	}

	public void setYetkiModel(RolYetkiModel yetkiModel) {
		this.yetkiModel = yetkiModel;
	}

	public void rolSelectListener(AjaxBehaviorEvent event) {
		List<RolYetkiRelationDTO> secilenRolYetkileri = new ArrayList<RolYetkiRelationDTO>();
		try {
			secilenRolYetkileri = new RolBusiness()
					.getRolYetkiler(kullaniciRolId);
			List<KullaniciRolYetkiDTO> l = new ArrayList<KullaniciRolYetkiDTO>();
			for (KullaniciRolYetkiDTO k : kullaniciRolYetkiListesi) {
				l.add(k);
			}

			try {
				for (KullaniciRolYetkiDTO kry : l) {
					if (kry.getRolId() > 0)
						kullaniciRolYetkiListesi.remove(kry);
				}

				l = new ArrayList<KullaniciRolYetkiDTO>();
				for (KullaniciRolYetkiDTO k : kullaniciRolYetkiListesi) {
					l.add(k);
				}
				for (RolYetkiRelationDTO r : secilenRolYetkileri) {

					for (KullaniciRolYetkiDTO kry : l) {
						if (kry.getYetkiId() == r.getYetkiId())
							kullaniciRolYetkiListesi.remove(kry);
					}

				}

			} catch (Exception e) {
				kullaniciRolYetkiListesi = new ArrayList<KullaniciRolYetkiDTO>();
			}

			for (RolYetkiRelationDTO r : secilenRolYetkileri) {
				KullaniciRolYetkiDTO k = new KullaniciRolYetkiDTO();
				k.setKullaniciId(slctKullanici.getKullaniciId());
				k.setRolId(r.getRolId());
				k.setYetkiId(r.getYetkiId());
				k.setYetkiAdi(r.getYetkiAdi());
				kullaniciRolYetkiListesi.add(k);
			}

			kullaniciYetkileri = new ArrayList<RolYetkiDTO>();
			for (RolYetkiDTO y : tumYetkiler) {
				kullaniciYetkileri.add(y);
			}
			try {
				for (RolYetkiDTO y : tumYetkiler) {
					for (KullaniciRolYetkiDTO kry : kullaniciRolYetkiListesi) {
						if (kry.getYetkiId() == y.getId()) {
							kullaniciYetkileri.remove(y);
						}
					}
				}
			} catch (Exception e) {
				kullaniciYetkileri = new ArrayList<RolYetkiDTO>();
			}
			yetkiModel = new RolYetkiModel(kullaniciYetkileri);
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Hata", e
							.getMessage()));
		}
	}

	public RolYetkiDTO getSecilenYetki() {
		return secilenYetki;
	}

	public void setSecilenYetki(RolYetkiDTO secilenYetki) {
		this.secilenYetki = secilenYetki;
	}

	public void yetkiSelectListener(SelectEvent event) {
		RolYetkiDTO yetki = (RolYetkiDTO) event.getObject();
		if (yetki != null) {
			KullaniciRolYetkiDTO k = new KullaniciRolYetkiDTO();
			k.setKullaniciId(slctKullanici.getKullaniciId());
			k.setRolId(0);
			k.setYetkiId(yetki.getId());
			k.setYetkiAdi(yetki.getAdi());
			kullaniciRolYetkiListesi.add(k);
			kullaniciYetkileri.remove(yetki);

		}

	}

	public void kullaniciYetkisiSil(KullaniciRolYetkiDTO k) {
		if (k != null) {
			kullaniciRolYetkiListesi.remove(k);
			RolYetkiDTO r = new RolYetkiDTO();
			r.setAdi(k.getYetkiAdi());
			r.setId(k.getYetkiId());
			kullaniciYetkileri.add(r);
		}
	}
	
	public void kullaniciTuruChangeListener(AjaxBehaviorEvent event){
		slctKullanici.setAkreditasyon(false);
		slctKullanici.setOnaylayan(false);
		slctKullanici.setOnayYetkisi("H");
		slctKullanici.setAkreditasyonDeger("H");
		
		
	}
	public void belediyeSelectListener(SelectEvent event){
		BelediyeDTO belediye = (BelediyeDTO) event.getObject();
		if (belediye!=null){
			slctKullanici.setSicilNo(String.valueOf(belediye.getKod()));
			slctKullanici.setAdi(new StringBuffer(belediye.getAdi()).append(" BELEDİYESİ").toString());
			
		}
		
	}
	
	

}
