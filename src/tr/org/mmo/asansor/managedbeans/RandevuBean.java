package tr.org.mmo.asansor.managedbeans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;

import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.beans_.Randevu;
import tr.org.mmo.asansor.business.BasvuruBusiness;
import tr.org.mmo.asansor.business.FirmaBusiness;
import tr.org.mmo.asansor.business.MailBusiness;
import tr.org.mmo.asansor.business.MuhendisBusiness;
import tr.org.mmo.asansor.business.RandevuBusiness;
import tr.org.mmo.asansor.dto.BakimciFirmaIletisimDTO;
import tr.org.mmo.asansor.dto.BasvuruAsansorDTO;
import tr.org.mmo.asansor.dto.BasvuruListeDTO;
import tr.org.mmo.asansor.dto.BilgilendirmeDTO;
import tr.org.mmo.asansor.dto.FirmaDTO;
import tr.org.mmo.asansor.dto.MuhendisCihazYetkiDTO;
import tr.org.mmo.asansor.dto.RandevuListeDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.models.BasvuruListeModel;
import tr.org.mmo.asansor.models.RandevuBilgiDataModel;
import tr.org.mmo.asansor.util.Messages;

@ManagedBean(name = "randevuBean")
@ViewScoped
public class RandevuBean implements Serializable {

	private static final long serialVersionUID = 1574769876197505161L;
	private DualListModel<Kullanici> muhendisModel;
	private DualListModel<Kullanici> oryantasyonMuhendisModel;
	private Randevu randevu;
	private List<BasvuruListeDTO> filterBasvuru;
	private ArrayList<BasvuruListeDTO> basvuruList;
	private BasvuruListeModel basvuruListeModel;
	private List<BasvuruAsansorDTO> asansorList;
	private RandevuBilgiDataModel randevuBilgiModel;
	private int gorevliMuhendis1 = 0;
	private boolean randevuReq = false;
	private boolean muhendisKontrolAdet = false;
	private boolean digerMuhendisKontrolAdet=false;
	private boolean oryantasyonMuhendisKontrolAdet=false;
	private List<FirmaDTO> bakimciFirmalar = new ArrayList<FirmaDTO>();
	@ManagedProperty(value = "#{randevuListBean.randevuListeDTO}")
	private RandevuListeDTO randevuListeDTO = new RandevuListeDTO();
	@ManagedProperty(value = "#{sessionBean}")
	private SessionBean sessionBean;
	@ManagedProperty(value = "#{loginBean.muhendisList}")
	List<Kullanici> muhendisList;
	private List<Kullanici> filteredVal;
	private List<BilgilendirmeDTO> bilgiList = new ArrayList<BilgilendirmeDTO>();
    private int etiketMuhendis = 0;
	
	public int getEtiketMuhendis() {
		return etiketMuhendis;
	}

	public void setEtiketMuhendis(int etiketMuhendis) {
		this.etiketMuhendis = etiketMuhendis;
	}

	List<BasvuruAsansorDTO> basvurulanAsansorler=new ArrayList<BasvuruAsansorDTO>();
	@ManagedProperty("#{applicationBean.iller}")
	private TreeMap<String, Integer> iller;

	public List<Kullanici> getFilteredVal() {
		return filteredVal;
	}

	public void setFilteredVal(List<Kullanici> filteredVal) {
		this.filteredVal = filteredVal;
	}

	@PostConstruct
	public void init() {
		
		randevuBilgiModel = new RandevuBilgiDataModel(
				new ArrayList<BilgilendirmeDTO>());
		asansorList = new ArrayList<BasvuruAsansorDTO>();
		basvuruList = new ArrayList<BasvuruListeDTO>();
		
		getBasvuruListesi();
		for (Kullanici m:muhendisList){
			try{
			List<MuhendisCihazYetkiDTO> yetkiList=new ArrayList<MuhendisCihazYetkiDTO>();
			yetkiList=new RandevuBusiness().getMuhendisCihazYetki(Integer.parseInt(m.getSicilNo()));
			m.setMuhendisCihazYetkiList(yetkiList);
			}catch(Exception e){
				
			}
		}
		randevu = new Randevu();

		List<Kullanici> pickSource = new ArrayList<Kullanici>();
		List<Kullanici> pickTarget = new ArrayList<Kullanici>();

		muhendisModel = new DualListModel<Kullanici>(pickSource, pickTarget);

		oryantasyonMuhendisModel = new DualListModel<Kullanici>(pickSource,
				pickTarget);
		
	}

	public int getGorevliMuhendis1() {
		return gorevliMuhendis1;
	}

	public void setGorevliMuhendis1(int gorevliMuhendis1) {
		this.gorevliMuhendis1 = gorevliMuhendis1;
	}

	public List<BasvuruAsansorDTO> getAsansorList() {
		return asansorList;
	}

	public void setAsansorList(List<BasvuruAsansorDTO> asansorList) {
		this.asansorList = asansorList;
	}

	public void getMuhendisler(int ilKod) {

		List<Kullanici> pickSource = new ArrayList<Kullanici>();
		List<Kullanici> pickTarget = new ArrayList<Kullanici>();

		try {

			randevu.setMuhendisler(new ArrayList<Kullanici>());
			for (Kullanici d : muhendisList) {
				// randevu.getMuhendisler().put(d.getAdi()+" "+d.getSoyadi(),
				// d.getSicilNo());
				if (d.getKullaniciTuru() == 1) {
					randevu.getMuhendisler().add(d);
				}

				if (d.getKullaniciTuru() == 5) {

					pickSource.add(d);
				}
				oryantasyonMuhendisModel = new DualListModel<Kullanici>(
						pickSource, pickTarget);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<FirmaDTO> getFirmalar(int ilKod) {
		bakimciFirmalar = new ArrayList<FirmaDTO>();
		try {
			bakimciFirmalar = new FirmaBusiness().getBasvuruFirmalar(ilKod);
			for (FirmaDTO f : bakimciFirmalar) {
				f.setFirmaIletisimList(new FirmaBusiness()
						.iletisimBilgisiGetir(f.getKod()));
			}

		} catch (CRUDException e) {
			e.printStackTrace();
		}
		return bakimciFirmalar;
	}

	public Randevu getRandevu() {
		return randevu;
	}

	public void setRandevu(Randevu randevu) {
		this.randevu = randevu;
	}

	public int randevuEkleByTarama(tr.org.mmo.asansor.beans_.Basvuru basvuru)
			throws CRUDException {
		int randevuId = 0;

		try {

			if (!muhendisKontrolAdet && !digerMuhendisKontrolAdet && !oryantasyonMuhendisKontrolAdet) {
				randevu.setSecilenMuhendisler(new ArrayList<Kullanici>());
				for (Kullanici k : muhendisList) {
					if (Integer.parseInt(k.getSicilNo()) == gorevliMuhendis1) {
						k.setSorumlu("E");
						randevu.getSecilenMuhendisler().add(k);

						break;
					}
				}

				for (Kullanici k : muhendisModel.getTarget()) {
					k.setSorumlu("H");
					randevu.getSecilenMuhendisler().add(k);

				}
				Map<Integer,Boolean> yetkiMap=new HashMap<Integer, Boolean>();
				
				for (Kullanici k:randevu.getSecilenMuhendisler()){
					for (MuhendisCihazYetkiDTO m:k.getMuhendisCihazYetkiList()){
						yetkiMap.put(m.getCihazTipi(),true);
					}
				
				}
				HashMap<Integer, Integer> asansorMap=new HashMap<Integer, Integer>();
				for (BasvuruAsansorDTO b:basvurulanAsansorler){
					asansorMap.put(b.getCihazTip(),b.getCihazId());
				}
				if (yetkiMap.size()>=asansorMap.size()){
				for (Kullanici k : oryantasyonMuhendisModel.getTarget()) {
					k.setSorumlu("O");
					randevu.getSecilenMuhendisler().add(k);

				}
				basvuru.getTarama().setId(basvuru.getBasvuru().getBasvuruId());
				randevuId = new RandevuBusiness().ekle(randevu, basvuru);

				gorevliMuhendis1 = 0;

				List<Kullanici> pickSource = new ArrayList<Kullanici>();
				List<Kullanici> pickTarget = new ArrayList<Kullanici>();

				muhendisModel = new DualListModel<Kullanici>(pickSource,
						pickTarget);
				oryantasyonMuhendisModel = new DualListModel<Kullanici>(
						pickSource, pickTarget);

				randevu.setSecilenMuhendisler(new ArrayList<Kullanici>());
				randevu.setRandevuTarih(null);
				randevu.setRandevuId(0);
				randevu.setBasvuru(null);
				muhendisKontrolAdet = false;
				digerMuhendisKontrolAdet=false;
				oryantasyonMuhendisKontrolAdet=false;
				}else{
					throw new CRUDException(
							Messages._MUHENDISCIHAZYETKIKONTROLUYARIT_.getMesaj(), null);

				
				}

			} else {
				throw new CRUDException(
						Messages._MUHENDISADETKONTROLUYARIGENEL_.getMesaj(), null);

			}

		} catch (CRUDException e) {
			throw new CRUDException(e.getMessage(), null);
		}
		return randevuId;

	}

	public void randevuEkle(ActionEvent event) {

		FacesMessage mesaj = new FacesMessage();
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		int randevuId = 0;

		try {

			if (!muhendisKontrolAdet && !digerMuhendisKontrolAdet && !oryantasyonMuhendisKontrolAdet) {

				randevu.setSecilenMuhendisler(new ArrayList<Kullanici>());
				for (Kullanici k : muhendisList) {
					if (Integer.parseInt(k.getSicilNo()) == gorevliMuhendis1) {
						k.setSorumlu("E");
						randevu.getSecilenMuhendisler().add(k);

						break;
					}
				}
				
				for (Kullanici k : muhendisModel.getTarget()) {
					k.setSorumlu("H");
					randevu.getSecilenMuhendisler().add(k);

				}
				Map<Integer,Boolean> yetkiMap=new HashMap<Integer, Boolean>();
				
				for (Kullanici k:randevu.getSecilenMuhendisler()){
					for (MuhendisCihazYetkiDTO m:k.getMuhendisCihazYetkiList()){
						yetkiMap.put(m.getCihazTipi(),true);
					}
				
				}
				HashMap<Integer, Integer> asansorMap=new HashMap<Integer, Integer>();
				for (BasvuruAsansorDTO b:basvurulanAsansorler){
					asansorMap.put(b.getCihazTip(),b.getCihazId());
				}
				if (yetkiMap.size()>=asansorMap.size()){
				for (Kullanici k : oryantasyonMuhendisModel.getTarget()) {
					k.setSorumlu("O");
					randevu.getSecilenMuhendisler().add(k);

				}
				randevuId = new RandevuBusiness().ekle(randevu);
				randevu.setRandevuId(randevuId);

				setRandevuBilgi();
				basvuruList.remove(randevu.getBasvuru());

				mesaj = new FacesMessage(
						Messages._RANDEVU_KAYDEDILDI_.getMesaj());
				mesaj.setSeverity(FacesMessage.SEVERITY_INFO);
				gorevliMuhendis1 = 0;

				List<Kullanici> pickSource = new ArrayList<Kullanici>();
				List<Kullanici> pickTarget = new ArrayList<Kullanici>();

				muhendisModel = new DualListModel<Kullanici>(pickSource,
						pickTarget);
				oryantasyonMuhendisModel = new DualListModel<Kullanici>(
						pickSource, pickTarget);

				randevu.setSecilenMuhendisler(new ArrayList<Kullanici>());
				randevu.setRandevuTarih(null);
				randevu.setRandevuId(0);
				randevu.setBasvuru(null);
				muhendisKontrolAdet = false;
				digerMuhendisKontrolAdet=false;
				oryantasyonMuhendisKontrolAdet=false;
				requestContext.execute("PF('randevuDialog').hide()");
				requestContext.execute("PF('confirmbilgi').show()");
				}else{
					mesaj = new FacesMessage(
							Messages._MUHENDISCIHAZYETKIKONTROLUYARIT_.getMesaj());
					mesaj.setSeverity(FacesMessage.SEVERITY_FATAL);
					requestContext.execute("PF('randevuDialog').show()");
				}
			}
			
			else {
				mesaj = new FacesMessage(
						Messages._MUHENDISADETKONTROLUYARIGENEL_.getMesaj());
				mesaj.setSeverity(FacesMessage.SEVERITY_FATAL);
				requestContext.execute("PF('randevuDialog').show()");
			}
			// component.setRendered(false);

			context.addMessage(null, mesaj);
		} catch (CRUDException e) {
			mesaj = new FacesMessage(e.getMessage());
			mesaj.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, mesaj);
			requestContext.execute("PF('randevuDialog').show()");
		}

	}

	public String handleTime(ValueChangeEvent event) {
		Date d = (Date) event.getNewValue();
		if (FacesContext.getCurrentInstance().getViewRoot().getViewId()
				.equals("/randevuListesi.xhtml")) {
			randevu = new Randevu();
			TreeNode n = randevuListeDTO.getMuhendis();

			for (TreeNode node : n.getChildren()) {
				Kullanici k = new Kullanici();

				k.setSicilNo(node.getData().toString());
				randevu.getSecilenMuhendisler().add(k);
				break;
			}
		}

		return "";

	}

	public void firmaChange(AjaxBehaviorEvent event) {
		// int ilceKodu=Integer.parseInt(((UIInput)
		// (event.getComponent())).getValue().toString());
		/*
		 * System.out.println(Integer.parseInt(((UIInput)
		 * (event.getComponent())).getValue().toString()));
		 * System.out.println(randevu.getSecilenFirma());
		 * System.out.println("deneme");
		 */

	}

	public void sec(AjaxBehaviorEvent event) {
		// int ilceKodu=Integer.parseInt(((UIInput)
		// (event.getComponent())).getValue().toString());

	}

	public ArrayList<BasvuruListeDTO> getBasvuruList() {
		return basvuruList;
	}

	public void setBasvuruList(ArrayList<BasvuruListeDTO> basvuruList) {
		this.basvuruList = basvuruList;
	}

	public void getBasvuruListesi() {
		try {
			basvuruList = new BasvuruBusiness()
					.randevuVerilecekBasvuruListesi();
			basvuruListeModel = new BasvuruListeModel(basvuruList);
		} catch (CRUDException e) {
			e.printStackTrace();
		}
	}

	public String yonlendir(ActionEvent event) {
		return "randevuListesi";
	}

	public String asansorListener(BasvuruListeDTO dto) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;
		if (dto != null) {
			try {
				asansorList = new BasvuruBusiness().getBasvuruAsansor(dto
						.getBasvuruId());
				if (asansorList.size() > 0) {

					StringBuffer sb = new StringBuffer();
					sb.append("Kontrol Edilecek Asansör Kimlik Numaraları ;");

					for (BasvuruAsansorDTO b : asansorList) {

						sb.append(b.getCihazId());
						sb.append(" ");
					}
					sb.append("Bakım Tutarı :");
					sb.append(dto.getKontrolTutari());
					sb.append(" TL ,");
					if (dto.getOdemeTutari() == 0
							&& dto.isOdemeKontroldeAlinsin()) {
						sb.append("Ödeme Kontrol Esnasında Alınacaktır");

					} else if (dto.getOdemeYapilmayacakBina().equals("E")) {
						sb.append("Bina kontrolünde ödeme alınmayacak şeklinde kodlanmış");
					} else {
						sb.append("Ödeme Tutarı :");
						sb.append(dto.getOdemeTutari());
						sb.append(" TL");
					}

					msg = new FacesMessage(sb.toString());
				} else {

					msg = new FacesMessage("Başvuruda asansör belirtilmemiş!");
				}
				msg.setSeverity(FacesMessage.SEVERITY_INFO);
			} catch (CRUDException e) {
				msg = new FacesMessage(e.getMessage());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}

			context.addMessage(null, msg);
		}
		return "";
	}

	private boolean checkMuhendisRandevu(int sicilNo, Randevu randevu)
			throws CRUDException {
		
		boolean baskaRandevuVarMi = false;
		boolean yetkili=false;
		List<MuhendisCihazYetkiDTO> yetkiList=new ArrayList<MuhendisCihazYetkiDTO>();
		for (Kullanici m : randevu.getMuhendisler()) {
			if (Integer.parseInt(m.getSicilNo()) == sicilNo) {
				yetkiList=m.getMuhendisCihazYetkiList();
				break;
			}
			}
		basvurulanAsansorler=new ArrayList<BasvuruAsansorDTO>();
		try {
			basvurulanAsansorler=new BasvuruBusiness().getBasvuruAsansorTip(randevu.getBasvuru().getBasvuruId());
		} catch (ReadException e) {
			
			e.printStackTrace();
		}
		
		for (MuhendisCihazYetkiDTO m:yetkiList){
			if (yetkili) break;
			for (BasvuruAsansorDTO b:basvurulanAsansorler){
				if (m.getCihazTipi()==b.getCihazTip()){
					yetkili=true;
					break;
				}
			}
		}if (!yetkili){
			FacesMessage msg = new FacesMessage(
					sicilNo+" "+Messages._MUHENDISCIHAZYETKIKONTROLUYARI_.getMesaj());
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else{
		int kontrolAdet = new MuhendisBusiness().getGunlukKontrolAdet(sicilNo);
		if (kontrolAdet > 0) {
			baskaRandevuVarMi = new RandevuBusiness()
					.muhendisMuayeneAdetKontrol(sicilNo,
							randevu.getRandevuTarih(), kontrolAdet,randevu.getBasvuru().getBasvuruId());

			if (baskaRandevuVarMi) {
				FacesMessage msg = new FacesMessage(
						Messages._MUHENDISKONTROLUYARI_.getMesaj());
				msg.setSeverity(FacesMessage.SEVERITY_INFO);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

		}
		}
		return baskaRandevuVarMi;
	}

	public void muhendisComboChange(ValueChangeEvent event) {

		UIComponent uic = (UIComponent) event.getComponent();
		muhendisKontrolAdet=false;
	
	
		if (event.getNewValue() != null) {
			Integer sicilNo = Integer.parseInt(event.getNewValue().toString());

			List<Kullanici> pickSource = new ArrayList<Kullanici>();
			List<Kullanici> pickTarget = new ArrayList<Kullanici>();
			if (sicilNo != null) {
				if (uic != null && uic.getId().equals("muhendis1")) {

					if (randevu.getRandevuTarih() != null
							&& randevu.getRandevuTarih().getTime() > 0) {
						try {
							checkMuhendisRandevu(sicilNo, randevu);

						} catch (CRUDException e) {
							if (e.getCause().toString()
									.equals("java.lang.Throwable: uyari")) {
								muhendisKontrolAdet = true;
							}
							FacesMessage msg = new FacesMessage(e.getMessage());
							msg.setSeverity(FacesMessage.SEVERITY_FATAL);
							FacesContext.getCurrentInstance().addMessage(null,
									msg);
						}
					}

				}

				for (Kullanici m : randevu.getMuhendisler()) {

					if (Integer.parseInt(m.getSicilNo()) != sicilNo) {
						if (uic != null && uic.getId().equals("muhendis1")) {

							pickSource.add(m);
						}
					}

				}

			}
			muhendisModel = new DualListModel<Kullanici>(pickSource, pickTarget);
		}
	}

	public void handleClose(CloseEvent event) {

		gorevliMuhendis1 = 0;

		List<Kullanici> pickSource = new ArrayList<Kullanici>();
		List<Kullanici> pickTarget = new ArrayList<Kullanici>();

		muhendisModel = new DualListModel<Kullanici>(pickSource, pickTarget);
		oryantasyonMuhendisModel = new DualListModel<Kullanici>(pickSource,
				pickTarget);

		randevu.setSecilenMuhendisler(new ArrayList<Kullanici>());
		randevu.setRandevuTarih(null);
		randevu.setRandevuId(0);
		randevuReq = false;

	}

	public void openRandevuDialog(BasvuruListeDTO basvuruListe) {
		
		
		
		getMuhendisler(basvuruListe.getIlKodu());
		getFirmalar(basvuruListe.getIlKodu());
		randevuReq = true;

		// bilgiList = new ArrayList<BilgilendirmeDTO>();

		// RequestContext.getCurrentInstance().execute("PF('randevuDialog').show()");
	}

	public BasvuruListeModel getBasvuruListeModel() {
		return basvuruListeModel;
	}

	public void setBasvuruListeModel(BasvuruListeModel basvuruListeModel) {
		this.basvuruListeModel = basvuruListeModel;
	}

	public List<BasvuruListeDTO> getFilterBasvuru() {
		return filterBasvuru;
	}

	public void setFilterBasvuru(List<BasvuruListeDTO> filterBasvuru) {
		this.filterBasvuru = filterBasvuru;
	}

	public RandevuListeDTO getRandevuListeDTO() {
		return randevuListeDTO;
	}

	public void setRandevuListeDTO(RandevuListeDTO randevuListeDTO) {
		this.randevuListeDTO = randevuListeDTO;
	}

	public void setRandevuBilgi() {
		BilgilendirmeDTO bilgi;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm a");

		for (FirmaDTO firma : randevu.getBasvuru().getBakimciFirma()) {

			bilgi = new BilgilendirmeDTO();
			bilgi.setAdiSoyadi(firma.getUnvan() == null ? "" : firma.getUnvan());
			bilgi.setePosta(firma.getEposta() == null ? "" : firma.getEposta());
			bilgi.setTelefonNo(String.valueOf(firma.getTelefonNo()));
			bilgi.setRol(new StringBuilder().append(firma.getUnvan())
					.append(" Merkez").toString());
			bilgi.setRandevuTarihi(df.format(randevu.getRandevuTarih()));
			bilgi.setBinaAdi(randevu.getBasvuru().getBinaAdi());
			bilgi.setBinaNo(randevu.getBasvuru().getBinaNo());
			bilgi.setCaddeSokak(randevu.getBasvuru().getCaddeSokak());
			bilgi.setMahalle(randevu.getBasvuru().getMahalle());
			bilgi.setIl(randevu.getBasvuru().getIl());
			bilgi.setIlce(randevu.getBasvuru().getIlce());

			bilgiList.add(bilgi);
			for (BakimciFirmaIletisimDTO bfi : firma.getFirmaIletisimList()) {
				bilgi = new BilgilendirmeDTO();
				bilgi.setAdiSoyadi(bfi.getAdi() == null ? "" : bfi.getAdi()
						.trim() + " " + bfi.getSoyadi() == null ? "" : bfi
						.getSoyadi().trim());
				bilgi.setePosta(bfi.getePosta() == null ? "" : bfi.getePosta());
				bilgi.setTelefonNo(String.valueOf(bfi.getTelefonNoGsm()));
				bilgi.setRol("Firma Çalışanı");

				bilgi.setRandevuTarihi(df.format(randevu.getRandevuTarih()));
				bilgi.setBinaAdi(randevu.getBasvuru().getBinaAdi());
				bilgi.setBinaNo(randevu.getBasvuru().getBinaNo());
				bilgi.setCaddeSokak(randevu.getBasvuru().getCaddeSokak());
				bilgi.setMahalle(randevu.getBasvuru().getMahalle());
				bilgi.setIl(randevu.getBasvuru().getIl());
				bilgi.setIlce(randevu.getBasvuru().getIlce());
				bilgiList.add(bilgi);
			}

		}

		bilgi = new BilgilendirmeDTO();
		bilgi.setAdiSoyadi(randevu.getBasvuru().getAdiSoyadi() == null ? ""
				: randevu.getBasvuru().getAdiSoyadi());
		bilgi.setePosta(randevu.getBasvuru().getePosta() == null ? "" : randevu
				.getBasvuru().getePosta());
		bilgi.setTelefonNo(randevu.getBasvuru().getTelefonNo() == null ? ""
				: randevu.getBasvuru().getTelefonNo());
		bilgi.setRol("Başvuru Yapan");
		bilgi.setRandevuTarihi(df.format(randevu.getRandevuTarih()));
		bilgi.setBinaAdi(randevu.getBasvuru().getBinaAdi());
		bilgi.setBinaNo(randevu.getBasvuru().getBinaNo());
		bilgi.setCaddeSokak(randevu.getBasvuru().getCaddeSokak());
		bilgi.setMahalle(randevu.getBasvuru().getMahalle());
		bilgi.setIl(randevu.getBasvuru().getIl());
		bilgi.setIlce(randevu.getBasvuru().getIlce());

		bilgiList.add(bilgi);
		randevuBilgiModel = new RandevuBilgiDataModel(bilgiList);
	}

	public void closeRandevuBildir(ActionEvent event) {

		gorevliMuhendis1 = 0;

		List<Kullanici> pickSource = new ArrayList<Kullanici>();
		List<Kullanici> pickTarget = new ArrayList<Kullanici>();

		muhendisModel = new DualListModel<Kullanici>(pickSource, pickTarget);
		oryantasyonMuhendisModel = new DualListModel<Kullanici>(pickSource,
				pickTarget);

		randevu.setSecilenMuhendisler(new ArrayList<Kullanici>());
		randevu.setRandevuTarih(null);
		randevu.setRandevuId(0);
		randevu.setBasvuru(null);
		muhendisKontrolAdet = false;
		digerMuhendisKontrolAdet=false;
		oryantasyonMuhendisKontrolAdet=false;

		RequestContext.getCurrentInstance().execute("PF('bilgilendir').hide()");
		FacesMessage mesaj = new FacesMessage(
				Messages._RANDEVU_KAYDEDILDI_.getMesaj());
		mesaj.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext.getCurrentInstance().addMessage(null, mesaj);

	}

	public void ePostaGonder(BilgilendirmeDTO bilgi) {

		if (bilgi != null) {
			int il = 0;
			Set<String> s = iller.keySet();
			Iterator<String> it = s.iterator();
			while (it.hasNext()) {
				String st = it.next();
				if (st.replaceAll("I", "ı")
						.replaceAll("İ", "i")
						.toLowerCase()
						.trim()
						.equals(bilgi.getIl().replaceAll("I", "ı")
								.replaceAll("İ", "i").toLowerCase().trim())) {
					il = iller.get(st);
					break;
				}
			}
			if (il > 0) {
				try {
					new MailBusiness().randevuBilgisiEPostaGonder(bilgi, il);
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "",
									"E-posta gönderildi"));
				} catch (CRUDException e) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e
									.getMessage()));
					e.printStackTrace();
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "",
								"E-posta gönderilemedi"));
			}

		}

	}

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public DualListModel<Kullanici> getMuhendisModel() {
		return muhendisModel;
	}

	public void setMuhendisModel(DualListModel<Kullanici> muhendisModel) {
		this.muhendisModel = muhendisModel;
	}

	public void onTransfer(TransferEvent event) {
		StringBuilder builder = new StringBuilder();

		int i = 0;
		for (Object item : event.getItems()) {
			Kullanici k = (Kullanici) item;
			if (k != null && k.getSicilNo() != null) {

				Integer sicilNo = Integer.parseInt(k.getSicilNo());

				if (randevu.getRandevuTarih() != null
						&& randevu.getRandevuTarih().getTime() > 0) {
					try {
						List<MuhendisCihazYetkiDTO> yetkiList=new ArrayList<MuhendisCihazYetkiDTO>();
						
					
						for (Kullanici m : randevu.getMuhendisler()) {
							if (Integer.parseInt(m.getSicilNo()) == sicilNo) {
								yetkiList=m.getMuhendisCihazYetkiList();
								break;
							}
							}
						boolean yetkili=false;
						for (MuhendisCihazYetkiDTO m:yetkiList){
							if (yetkili) break;
							for (BasvuruAsansorDTO b:basvurulanAsansorler){
								if (m.getCihazTipi()==b.getCihazTip()){
									yetkili=true;
									break;
								}
							}
						}if (!yetkili){
							FacesMessage msg = new FacesMessage(
									sicilNo+" "+Messages._MUHENDISCIHAZYETKIKONTROLUYARI_.getMesaj());
							msg.setSeverity(FacesMessage.SEVERITY_INFO);
							FacesContext.getCurrentInstance().addMessage(null, msg);
						}
						else{
						digerMuhendisKontrolAdet = false;
						int kontrolAdet = new MuhendisBusiness()
								.getGunlukKontrolAdet(sicilNo);
						if (kontrolAdet > 0) {
							boolean baskaRandevuVarMi = new RandevuBusiness()
									.muhendisMuayeneAdetKontrol(sicilNo,
											randevu.getRandevuTarih(),
											kontrolAdet,randevu.getBasvuru().getBasvuruId());

							if (baskaRandevuVarMi) {
								if (i == 0) {
									builder.append(
											"Görevlendirilen diğer mühendislerden ")
											.append(k.getAdi()).append(" ")
											.append(k.getSoyadi());
									i++;
								} else {
									builder.append(",").append(k.getAdi())
											.append(" ").append(k.getSoyadi());
								}

							}
						}
					}
					} catch (CRUDException e) {
						if (e.getCause().toString()
								.equals("java.lang.Throwable: uyari")) {
							digerMuhendisKontrolAdet = true;
						}
						FacesMessage msg = new FacesMessage(e.getMessage());
						msg.setSeverity(FacesMessage.SEVERITY_FATAL);
						FacesContext.getCurrentInstance().addMessage(null, msg);
					}
				}

				if (builder.toString().length() > 3) {

					builder.append(" nın aynı gün 1 saat içerisinde başka kontrol randevusu var");
					FacesMessage msg = new FacesMessage();
					msg.setSeverity(FacesMessage.SEVERITY_INFO);
					msg.setSummary(builder.toString());
					msg.setDetail("");

					FacesContext.getCurrentInstance().addMessage(null, msg);
				}

			}

		}

	}

	
	public void onTransferOryantasyon(TransferEvent event) {

		for (Object item : event.getItems()) {
			Kullanici k = (Kullanici) item;
			if (k != null && k.getSicilNo() != null) {

				Integer sicilNo = Integer.parseInt(k.getSicilNo());

				if (randevu.getRandevuTarih() != null
						&& randevu.getRandevuTarih().getTime() > 0) {
					try {
						
						
						oryantasyonMuhendisKontrolAdet = false;
						int kontrolAdet = new MuhendisBusiness()
								.getGunlukKontrolAdet(sicilNo);
						if (kontrolAdet > 0) {
							 new RandevuBusiness()
									.muhendisMuayeneAdetKontrol(sicilNo,
											randevu.getRandevuTarih(),
											kontrolAdet,randevu.getBasvuru().getBasvuruId());

							
						}
					
					} catch (CRUDException e) {
						if (e.getCause().toString()
								.equals("java.lang.Throwable: uyari")) {
							oryantasyonMuhendisKontrolAdet = true;
						}
						FacesMessage msg = new FacesMessage(e.getMessage());
						msg.setSeverity(FacesMessage.SEVERITY_FATAL);
						FacesContext.getCurrentInstance().addMessage(null, msg);
					}
				}

				

			}

		}

	}
	public DualListModel<Kullanici> getOryantasyonMuhendisModel() {
		return oryantasyonMuhendisModel;
	}

	public void setOryantasyonMuhendisModel(
			DualListModel<Kullanici> oryantasyonMuhendisModel) {
		this.oryantasyonMuhendisModel = oryantasyonMuhendisModel;
	}

	public List<Kullanici> getMuhendisList() {
		return muhendisList;
	}

	public void setMuhendisList(List<Kullanici> muhendisList) {
		this.muhendisList = muhendisList;
	}

	public List<BilgilendirmeDTO> getBilgiList() {
		return bilgiList;
	}

	public void setBilgiList(List<BilgilendirmeDTO> bilgiList) {
		this.bilgiList = bilgiList;
	}

	public RandevuBilgiDataModel getRandevuBilgiModel() {
		return randevuBilgiModel;
	}

	public void setRandevuBilgiModel(RandevuBilgiDataModel randevuBilgiModel) {
		this.randevuBilgiModel = randevuBilgiModel;
	}

	public boolean isRandevuReq() {
		return randevuReq;
	}

	public void setRandevuReq(boolean randevuReq) {
		this.randevuReq = randevuReq;
	}

	public TreeMap<String, Integer> getIller() {
		return iller;
	}

	public void setIller(TreeMap<String, Integer> iller) {
		this.iller = iller;
	}
	
	public void randevuTarihSelectListener(SelectEvent event){
        muhendisKontrolAdet=false;
        digerMuhendisKontrolAdet=false;
		oryantasyonMuhendisKontrolAdet=false;
        if (gorevliMuhendis1>0 || (muhendisModel.getTarget()!=null && muhendisModel.getTarget().size()>0)){
            
            if (randevu.getRandevuTarih() != null
                    && randevu.getRandevuTarih().getTime() > 0) {
                try {
                    if (gorevliMuhendis1>0){
               muhendisKontrolAdet=   checkMuhendisRandevu(gorevliMuhendis1, randevu);
                    }
                    if (muhendisModel.getTarget()!=null && muhendisModel.getTarget().size()>0){
                        for (Kullanici k:muhendisModel.getTarget()){
                          digerMuhendisKontrolAdet=  checkMuhendisRandevu(Integer.parseInt(k.getSicilNo()), randevu);
                            if (digerMuhendisKontrolAdet) break;
                        }
                    }
                    
                    if (oryantasyonMuhendisModel.getTarget()!=null && oryantasyonMuhendisModel.getTarget().size()>0){
                        for (Kullanici k:oryantasyonMuhendisModel.getTarget()){
                          oryantasyonMuhendisKontrolAdet=  checkMuhendisRandevu(Integer.parseInt(k.getSicilNo()), randevu);
                            if (oryantasyonMuhendisKontrolAdet) break;
                        }
                    }
                } catch (CRUDException e) {
                    if (e.getCause().toString()
                            .equals("java.lang.Throwable: uyari")) {
                        muhendisKontrolAdet = true;
                    
                    }
                    FacesMessage msg = new FacesMessage(e.getMessage());
                    msg.setSeverity(FacesMessage.SEVERITY_FATAL);
                    FacesContext.getCurrentInstance().addMessage(null,
                            msg);
                }
            }
        
        }
        
        
    
        
    }
	
}
