package tr.org.mmo.asansor.managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlBody;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.component.editor.Editor;
import org.primefaces.component.inplace.Inplace;
import org.primefaces.event.SelectEvent;
import org.primefaces.util.HTML;

import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.beans_.Duyuru;
import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.business.BelediyeBusiness;
import tr.org.mmo.asansor.business.BinaBusiness;
import tr.org.mmo.asansor.business.BirimBusiness;
import tr.org.mmo.asansor.business.KullaniciBusiness;
import tr.org.mmo.asansor.business.MuhendisBusiness;
import tr.org.mmo.asansor.business.RaporBusiness;
import tr.org.mmo.asansor.dto.BelediyeDTO;
import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.dto.BirimDTO;
import tr.org.mmo.asansor.dto.KontrolYapilamamaNedenDTO;
import tr.org.mmo.asansor.dto.KullaniciRolYetkiDTO;
import tr.org.mmo.asansor.dto.YetkiSayfaDTO;
import tr.org.mmo.asansor.exception.SystemUnavailableException;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.models.DuyuruModel;
import tr.org.mmo.asansor.util.BelediyeComparator;
import tr.org.mmo.asansor.util.DuyuruTarihComparator;
import tr.org.mmo.asansor.util.Messages;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	
	
	private Duyuru duyuru=new Duyuru();
	private String style;
	private DuyuruModel duyurular=new DuyuruModel(new ArrayList<Duyuru>());
	private static final long serialVersionUID = 1038061413659048809L;
	private Kullanici kullanici = new Kullanici();
	private List<BinaDTO> tumBinalar = new ArrayList<BinaDTO>();
	private List<Duyuru> duyuruList=new ArrayList<Duyuru>();
	private String user;
	private String pass;
	private List<BelediyeDTO> belediyeList = new ArrayList<BelediyeDTO>();
	private boolean loggedIn = false;
	private boolean belediyeloggedIn = false;
	private String styleLogin = "width:100%;margin-left:0px;";
	private String styleContent = "width:95%;";
	private List<KontrolYapilamamaNedenDTO> kontrolYapilmamaNedenList = new ArrayList<KontrolYapilamamaNedenDTO>();
	private boolean parolaDegistir = false;
	private boolean notificationSozlesmeGoster = false;
	private List<Kullanici> onayciList = new ArrayList<Kullanici>();
	private List<Kullanici> muhendisList = new ArrayList<Kullanici>();
	private List<BelediyeDTO> sozlesmesiBitecekBelediyeList = new ArrayList<BelediyeDTO>();
	private List<YetkiSayfaDTO> sayfaYetkileri = new ArrayList<YetkiSayfaDTO>();
	
	public LoginBean() {
		sayfaYetkileriGetir();
	}

	public Kullanici getKullanici() {
		return kullanici;
	}

	public void setKullanici(Kullanici kullanici) {
		this.kullanici = kullanici;
	}

	public String login() {

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = null;
		
		int i = 0;

		try {
			kullanici = new Kullanici();
			kullanici.setKullaniciAdi(user);
			kullanici.setParola(pass);
			kullanici = new KullaniciBusiness().giris(kullanici);
			if (kullanici.getKullaniciTuru()!=99){
			if (kullanici.getIller().size() > 0) {
				sozlesmesiBitecekBelediyeList = new BelediyeBusiness()
						.getSozlesmeBitimiYaklasanBelediyeler();
				if (sozlesmesiBitecekBelediyeList.size() > 0) {
					notificationSozlesmeGoster = true;
				}
				if (pass.equals(kullanici.getKullaniciAdi().trim())) {
					parolaDegistir = true;
				}

				onayciList = onayciList.size() == 0 ? new MuhendisBusiness()
						.getOnaycilar() : onayciList;
				muhendisList = muhendisList.size() == 0 ? new MuhendisBusiness()
						.getMuhendisler() : muhendisList;

				loggedIn = true;
				
				new KullaniciBusiness().loginLogInsert();
				tumBinalar = (tumBinalar == null ? new ArrayList<BinaDTO>()
						: tumBinalar).size() == 0 ? new BinaBusiness()
						.binaBulByIlIlce() : tumBinalar;
				styleLogin = "";
				styleContent = "";
				setKontrolYapilmamaNedenList();
				belediyeList = new BelediyeBusiness().getBelediyeler();
				Collections.sort(belediyeList, new BelediyeComparator());
				((SessionBean) Common.findBean("sessionBean"))
						.setNotificationGoster(false);
				((SessionBean) Common.findBean("sessionBean"))
				.setNotificationGosterTeslim(false);
				if (kullanici.isOnaylayan() && kullanici.getSicilNo() != null) {

					((SessionBean) Common.findBean("sessionBean"))
							.setNotificationGoster(new RaporBusiness()
									.onaylanmayanRaporVarmi());
					((SessionBean) Common.findBean("sessionBean"))
					.setNotificationGosterTeslim(new RaporBusiness()
							.teslimEdilmeyenRaporVarmi());

				}
				try{
				if (kullanici.getSicilNo() != null && Integer.parseInt(kullanici.getSicilNo())>0) {
					((SessionBean) Common.findBean("sessionBean"))
					.setNotificationGosterTeslim(new RaporBusiness()
							.teslimEdilmeyenRaporVarmi());
				}}
				catch(Exception e){
					((SessionBean) Common.findBean("sessionBean"))
					.setNotificationGosterTeslim(false);
				}
				
				
				style = String
						.format("visibility :%s;margin-top:%s",
								"hidden","-2em");
				
				duyuruList= new KullaniciBusiness().getDuyurular();
				duyurular=new DuyuruModel(duyuruList);
				if (duyuruList.size()>0) style = String
						.format("visibility :%s;",
								"visible");
				return "index.xhtml?faces-redirect=true";
			} else {
				msg = new FacesMessage(Messages._YETKIBELIRLE_.getMesaj());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);

				logout();

			}
			}else{
				HttpServletRequest request=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
				System.setProperty("java.net.preferIPv4Stack" , "true");
				String ipAddress=getClientIp(request);
				
				String ipHost=request.getRemoteHost();
				
				
				System.out.println("Remote Addr : " + ipAddress);
				System.out.println("Remote Host : " + ipHost);
				if ((ipAddress!=null && !ipAddress.trim().equals("") && ipAddress.trim().equals(kullanici.getBelediyeIp()))
				|| (ipHost!=null && !ipHost.trim().equals("") && ipHost.trim().equals(kullanici.getBelediyeIp()))){
					new KullaniciBusiness().loginLogInsert();
					tumBinalar = (tumBinalar == null ? new ArrayList<BinaDTO>()
							: tumBinalar).size() == 0 ? new BinaBusiness()
							.binaBulByBelediye(Integer.parseInt(kullanici.getSicilNo().trim())) : tumBinalar;
						belediyeloggedIn=true;
							return "belediyeindex?faces-redirect=true";
			}else{
					msg = new FacesMessage(Messages._YETKIBELIRLE_.getMesaj());
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);

					logout();

				}
				
				
				
			}

		} catch (CRUDException e) {

			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			if (e.getMessage().equals(Messages._YETKIBELIRLE_.getMesaj())) {
				logout();
			}

		} catch (SystemUnavailableException e) {

			msg = new FacesMessage(e.getMessage());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);

		}

		if (i == 0) {
			context.addMessage(null, msg);
		}

		return "";
	}

	private String getClientIp(HttpServletRequest request) {
		String ip=request.getHeader( "X-Real-IP" );
		  
		  if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("X-Forwarded-For");  
		    }  
		  if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("Proxy-Client-IP");  
		    }  
		  if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("WL-Proxy-Client-IP");  
		    }  
		  if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("HTTP_CLIENT_IP");  
		    }  
		  if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
		    }  
		  if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getRemoteAddr();  
		    } 
		    return ip;
	}

	public String logout() {
		try {
			new KullaniciBusiness().loginLogUpdate();
		} catch (CRUDException e) {

			e.printStackTrace();
		}
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		session.invalidate();
		styleLogin = "width:100%;margin-left:0px;";
		styleContent = "width:95%;";
		return "/login.xhtml?faces-redirect=true";
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getKullaniciBinaKapsam() {
		StringBuffer kosul = new StringBuffer();
		StringBuffer sbIl = new StringBuffer();
		StringBuffer sbIlce = new StringBuffer();

		int i = 0;
		int j = 0;
		boolean merkezKullanici = false;
		for (BirimDTO birim : getKullanici().getBirimler()) {
			if (birim.getBirimKodu() == 0) {

				kosul.append("a.il between 1 and 99");
				merkezKullanici = true;
				break;
			} else {
				if (!merkezKullanici) {
					if (birim.getBirimTipi().equals("S")) {
						if (i == 0) {
							sbIl.append("a.il in (");
							sbIl.append(birim.getIl());
							i++;
						} else {
							sbIl.append(",");
							sbIl.append(birim.getIl());

						}

					} else {
						if (j == 0) {
							sbIlce.append(" or a.ilce in (");
							sbIlce.append(birim.getIlce());
							j++;
						} else {
							sbIlce.append(",");
							sbIlce.append(birim.getIlce());
						}

					}
				}

			}
		}
		if (!merkezKullanici) {
			if (sbIl.toString().length() > 0) {
				sbIl.append(")");
			}
			if (sbIlce.toString().length() > 0) {
				sbIlce.append(")");
			}

			kosul.append(sbIl.toString());
			kosul.append(sbIlce.toString());
		}
		return "( " + kosul.toString() + " )";
	}

	public String getIlSorguKosul() {
		StringBuffer kosul = new StringBuffer();
		StringBuffer sbIl = new StringBuffer();
		int i = 0;
		boolean merkezKullanici = false;

		for (BirimDTO birim : getKullanici().getBirimler()) {
			if (birim.getBirimKodu() == 0) {

				kosul.append("a.il between 1 and 99");
				merkezKullanici = true;
				break;
			} else {
				if (i == 0) {
					sbIl.append("a.il in (");
					sbIl.append(birim.getIl());
					i++;
				} else {
					sbIl.append(",");
					sbIl.append(birim.getIl());
				}

			}
		}
		if (!merkezKullanici) {
			if (sbIl.toString().length() > 0) {
				sbIl.append(")");
			}

			kosul.append(sbIl.toString());
		}
		return "( " + kosul.toString() + " )";
	}

	public String parolaDegistir() {
		return "sifredegistir.jsf?faces-redirect=true";
		// RequestContext requestContext=RequestContext.getCurrentInstance();
		// //requestContext.execute("PF('paroladialog').hide()");
		// try {
		// FacesContext.getCurrentInstance().getExternalContext().redirect("sifredegistir.jsf");
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// requestContext.execute("PF('parola').show()");
	}

	public String parolaDegistirIptal() {
		return "index.jsf?faces-redirect=true";
		// RequestContext requestContext=RequestContext.getCurrentInstance();
		// requestContext.execute("PF('paroladialog').hide()");
		// try {
		// FacesContext.getCurrentInstance().getExternalContext().redirect("index.jsf");
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// requestContext.execute("PF('parola').show()");
	}

	public String sifremiUnuttum() {
		return "/sifre.xhtml?faces-redirect=true";
	}

	public List<KontrolYapilamamaNedenDTO> getKontrolYapilmamaNedenList() {
		return kontrolYapilmamaNedenList;
	}

	public void setKontrolYapilmamaNedenList(
			List<KontrolYapilamamaNedenDTO> kontrolYapilmamaNedenList) {
		this.kontrolYapilmamaNedenList = kontrolYapilmamaNedenList;
	}

	public void setKontrolYapilmamaNedenList() {
		try {
			this.kontrolYapilmamaNedenList = new BinaBusiness()
					.getKontrolYapilmamaNedenKodlari();
		} catch (CRUDException e) {

			e.printStackTrace();
		}
	}

	public String getStyleLogin() {
		return styleLogin;
	}

	public void setStyleLogin(String styleLogin) {
		this.styleLogin = styleLogin;
	}

	public boolean isParolaDegistir() {
		return parolaDegistir;
	}

	public void setParolaDegistir(boolean parolaDegistir) {
		this.parolaDegistir = parolaDegistir;
	}

	public void parolaDegistirListener(ActionEvent event) {
		parolaDegistir = false;
	}

	public void notificationSozlesmeGizle() {
		notificationSozlesmeGoster = false;
	}

	public boolean isNotificationSozlesmeGoster() {
		return notificationSozlesmeGoster;
	}

	public void setNotificationSozlesmeGoster(boolean notificationSozlesmeGoster) {
		this.notificationSozlesmeGoster = notificationSozlesmeGoster;
	}

	public List<BelediyeDTO> getSozlesmesiBitecekBelediyeList() {
		return sozlesmesiBitecekBelediyeList;
	}

	public void setSozlesmesiBitecekBelediyeList(
			List<BelediyeDTO> sozlesmesiBitecekBelediyeList) {
		this.sozlesmesiBitecekBelediyeList = sozlesmesiBitecekBelediyeList;
	}

	public List<Kullanici> getOnayciList() {
		return onayciList;
	}

	public void setOnayciList(List<Kullanici> onayciList) {
		this.onayciList = onayciList;
	}

	public List<Kullanici> getMuhendisList() {
		return muhendisList;
	}

	public void setMuhendisList(List<Kullanici> muhendisList) {
		this.muhendisList = muhendisList;
	}

	public String getStyleContent() {
		return styleContent;
	}

	public void setStyleContent(String styleContent) {
		this.styleContent = styleContent;
	}

	private int yetkiId(String viewId) {
		int yetkiId = 0;
		for (YetkiSayfaDTO y : sayfaYetkileri) {
			if (y.getSayfaAdi().equals(
					viewId.replaceAll("/", "").replaceAll("xhtml", "jsf"))) {
				yetkiId = y.getYetkiId();
				break;

			}
		}
		return yetkiId;
	}

	public boolean kaydedebilir() {
		String viewId = FacesContext.getCurrentInstance().getViewRoot()
				.getViewId();
		boolean bool = false;
		int yetkiId = yetkiId(viewId);
		for (KullaniciRolYetkiDTO k : kullanici.getRoller()) {
			if (yetkiId == k.getYetkiId()) {
				bool = k.isGuncelle();
			}
		}

		return bool;
	}

	public boolean silebilir() {
		String viewId = FacesContext.getCurrentInstance().getViewRoot()
				.getViewId();
		boolean bool = false;
		int yetkiId = yetkiId(viewId);
		for (KullaniciRolYetkiDTO k : kullanici.getRoller()) {
			if (yetkiId == k.getYetkiId()) {
				bool = k.isSil();
			}
		}

		return bool;
	}

	private void sayfaYetkileriGetir() {
		try {
			sayfaYetkileri = new BirimBusiness().sayfaYetkileri();
		} catch (CRUDException e) {

			e.printStackTrace();
		}
	}

	public void setSayfaYetkileri(List<YetkiSayfaDTO> sayfaYetkileri) {
		this.sayfaYetkileri = sayfaYetkileri;
	}

	public List<YetkiSayfaDTO> getSayfaYetkileri() {
		return sayfaYetkileri;
	}

	public List<BelediyeDTO> getBelediyeList() {
		return belediyeList;
	}

	public void setBelediyeList(List<BelediyeDTO> belediyeList) {
		this.belediyeList = belediyeList;
	}

	public List<BinaDTO> getTumBinalar() {
		return tumBinalar;
	}

	public void setTumBinalar(List<BinaDTO> tumBinalar) {
		this.tumBinalar = tumBinalar;
	}

	public boolean isBelediyeloggedIn() {
		return belediyeloggedIn;
	}

	public void setBelediyeloggedIn(boolean belediyeloggedIn) {
		this.belediyeloggedIn = belediyeloggedIn;
	}

	public List<Duyuru> getDuyuruList() {
		return duyuruList;
	}

	public void setDuyuruList(List<Duyuru> duyuruList) {
		this.duyuruList = duyuruList;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Duyuru getDuyuru() {
		return duyuru;
	}

	public void setDuyuru(Duyuru duyuru) {
		this.duyuru = duyuru;
	}

	public DuyuruModel getDuyurular() {
		return duyurular;
	}

	public void setDuyurular(DuyuruModel duyurular) {
		this.duyurular = duyurular;
	}


public void showDuyuru(SelectEvent event){
	try {
		
		FacesContext.getCurrentInstance().getExternalContext().redirect("duyuru.jsf");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void showDuyuru(Duyuru duyuru){
	try {
		this.duyuru=new Duyuru();
		this.duyuru.setId(duyuru.getId());
		this.duyuru.setDetay(duyuru.getDetay());
		this.duyuru.setKonu(duyuru.getKonu());
		this.duyuru.setTarih(duyuru.getTarih());
		this.duyuru.setYeniMi(duyuru.isYeniMi());
		FacesContext.getCurrentInstance().getExternalContext().redirect("duyuru.jsf");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


public void yeniDuyuru(ActionEvent event){
	
	try {
		this.duyuru=new Duyuru();
		FacesContext.getCurrentInstance().getExternalContext().redirect("duyurugiris.jsf");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void duyuruGuncelle(Duyuru duyuru){
	
	try {
		this.duyuru=new Duyuru();
		this.duyuru.setId(duyuru.getId());
		this.duyuru.setDetay(duyuru.getDetay());
		this.duyuru.setKonu(duyuru.getKonu());
		this.duyuru.setTarih(duyuru.getTarih());
		this.duyuru.setYeniMi(duyuru.isYeniMi());
		FacesContext.getCurrentInstance().getExternalContext().redirect("duyurugiris.jsf");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


public void duyuruKaydet(ActionEvent event){
	
	duyuru.setDetay(duyuru.getDetay().replace("<br>", "<br/>").trim());
	if (duyuru.getDetay().replaceAll("<br/>","").trim().length()<=0){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(Messages._DUYURUDETAYGIRINIZ_.getMesaj()));
	}else{
		
		try {
		int id=	new KullaniciBusiness().duyuruKaydet(duyuru);
			if (duyuru.getId()>0){
				for (Duyuru d:duyuruList){
					if (d.getId()==duyuru.getId()){
						d.setDetay(duyuru.getDetay());
						d.setKonu(duyuru.getKonu());
						
						break;
					}
				}
				
			}else{
				this.duyuru.setId(id);
				this.duyuru.setYeniMi(true);
				this.duyuru.setTarih(Calendar.getInstance().getTime());
				duyuruList.add(duyuru);
				
			}
			
				
				Collections.sort(duyuruList,new DuyuruTarihComparator());
				duyurular=new DuyuruModel(duyuruList);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(Messages._SQL_513_.getMesaj()));
		} catch (CRUDException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
		}
	}
	
}
	
	
	

}
