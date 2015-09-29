package tr.org.mmo.asansor.managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.primefaces.component.menuitem.UIMenuItem;
import org.primefaces.event.TabChangeEvent;

import tr.org.mmo.asansor.beans_.Duyuru;
import tr.org.mmo.asansor.dto.YetkiSayfaDTO;

@ManagedBean
@SessionScoped
public class MenuStateBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3077266728208528780L;
	private int openTabIndex;
	private String shadow = "0px 0px 5px 0px #e7e1aa";
	private String fontWeight = "bold";
	private String color = "#e7e1aa";
	private String fontColor = "#000";
	private String[] style = new String[50];
	private static Boolean[] render = new Boolean[50];
	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;
	@ManagedProperty("#{binaBean}")
	private BinaBean binaBean;
	private static HashMap<String, Integer> viewMap = new HashMap<String, Integer>();

	static {
		if (viewMap.size() == 0) {
			viewMap.put("basvuru", 1);
			viewMap.put("basvuruListesi", 2);
			viewMap.put("odemesizBasvuruListesi", 3);
			viewMap.put("randevuver", 4);
			viewMap.put("randevuListesi", 5);
			viewMap.put("kontrol", 6);
			viewMap.put("asansor", 7);
			viewMap.put("bina", 8);
			viewMap.put("binakayit", 9);
			viewMap.put("kontrolListesi", 10);
			viewMap.put("raporlar", 11);
			viewMap.put("belediyeListe", 12);
			viewMap.put("belediyekayit", 13);
			viewMap.put("bakimciFirmaListe", 14);
			viewMap.put("bakimcifirmakayit", 15);
			viewMap.put("muhendisListe", 16);
			// viewMap.put("muhendiskayit", 17);
			viewMap.put("kullaniciListe", 18);
			viewMap.put("kullanicikayit", 19);
			viewMap.put("sifredegistir", 20);
			viewMap.put("index", 21);
			viewMap.put("tarama", 22);
			viewMap.put("raporteslim", 23);
			viewMap.put("testsorulari", 24);
			viewMap.put("taranmayanBinaListesi", 25);
			viewMap.put("muhendisIcinRandevuListesi", 26);
			viewMap.put("randevuListe", 27);
			viewMap.put("odemeGiris", 28);
			viewMap.put("odemeGorme", 29);
			viewMap.put("kontrolhaberliste", 30);
			viewMap.put("rolyetki", 31);
			viewMap.put("istatistik", 32);
			viewMap.put("belediyeleregorekontrolsayisidagilimi", 33);
			viewMap.put("denetcileregorekontrolsayisidagilimi", 34);
			viewMap.put("etiketveyapitiplerinegorekontrolsayisidagilimi", 35);
			viewMap.put("kontrolveodemelerlistesi", 36);
			viewMap.put("belediyebilgi", 37);
			viewMap.put("kontrolugelenbinalar", 38);
			viewMap.put("odemekontrol", 39);
			viewMap.put("duyurugiris", 40);
			viewMap.put("cihazOdemeKontrol", 41);
			viewMap.put("bakanlikraporlari", 42);
			viewMap.put("asansorEtiketBasma", 43);
			/*
			 * "Başvuru" "Tarama" "Randevu" "Ödeme" "Asansör Teknik Bilgiler"
			 * "Asansör Kontrol" "Rapor" "Belediye" "Mühendis" "Bakımcı Firma"
			 * "Test Soruları"
			 */
		}

		for (int i = 0; i < 50; i++) {
			render[i] = true;
		}

	}

	public String[] getStyle() {
		return style;
	}

	public void setStyle(String[] style) {
		this.style = style;
	}

	@PostConstruct
	public void init() {
		/*
		 * int i = 0; for (KullaniciRolYetkiDTO k :
		 * loginBean.getKullanici().getRoller()) { try {
		 * 
		 * i = viewMap.get(menuAdi(k.getYetkiId())); i--; render[i] = true; }
		 * catch (Exception e) {
		 * 
		 * } }
		 */

	}

	public MenuStateBean() {

		openTabIndex = -1;
		String viewId = FacesContext.getCurrentInstance().getViewRoot()
				.getViewId();
		if (viewId.equals("/basvuru.xhtml")
				|| viewId.equals("/basvuruListesi.xhtml")
				|| viewId.equals("/odemesizBasvuruListesi.xhtml")
				|| viewId.equals("/kontrolhaberliste.xhtml")

		) {
			openTabIndex = 0;
		}
		if (viewId.equals("/randevuver.xhtml")
				|| viewId.equals("/randevuListesi.xhtml")
				|| viewId.equals("/muhendisIcinRandevuListesi.xhtml")) {
			openTabIndex = 1;
		}
		if (viewId.equals("/kontrol.xhtml") 
				|| viewId.equals("/asansor.xhtml")
				|| viewId.equals("/asansorEtiketBasma.xhtml")) {
			openTabIndex = 2;
		}

		if (viewId.equals("/bina.xhtml") || viewId.equals("/binakayit.xhtml")) {
			openTabIndex = 3;
		}

		if (viewId.equals("/kontrolListesi.xhtml")
				|| viewId.equals("/raporlar.xhtml")
				|| viewId.equals("/raporteslim.xhtml")) {
			openTabIndex = 4;
		}
		if (viewId.equals("/belediyeListe.xhtml")
				|| viewId.equals("/belediyekayit.xhtml")
				|| viewId.equals("/bakimciFirmaListe.xhtml")
				|| viewId.equals("/bakimcifirmakayit.xhtml")
				|| viewId.equals("/muhendisListe.xhtml")
				|| viewId.equals("/muhendiskayit.xhtml")) {
			openTabIndex = 5;
		}
		if (viewId.equals("/kullaniciListe.xhtml")
				|| viewId.equals("/kullanicikayit.xhtml")
				|| viewId.equals("/testsorulari.xhtml")
				|| viewId.equals("/rolyetki.xhtml")
				|| viewId.equals("/istatistik.xhtml")
				|| viewId.equals("/randevuListe.xhtml")
				|| viewId.equals("/duyurugiris.xhtml")
				
				) {
			openTabIndex = 6;

		}
		if ( viewId.equals("/belediyeleregorekontrolsayisidagilimi.xhtml")
				|| viewId.equals("/denetcileregorekontrolsayisidagilimi.xhtml")
				|| viewId.equals("/etiketveyapitiplerinegorekontrolsayisidagilimi.xhtml")
				|| viewId.equals("/kontrolveodemelerlistesi.xhtml")
				|| viewId.equals("/belediyebilgi.xhtml")
				|| viewId.equals("/kontrolugelenbinalar.xhtml")
				|| viewId.equals("/odemekontrol.xhtml")
				|| viewId.equals("/cihazOdemeKontrol.xhtml")
				|| viewId.equals("/bakanlikraporlari.xhtml")
				
				) {
			openTabIndex = 9;

		}
		if (viewId.equals("/tarama.xhtml")
				|| viewId.equals("/taranmayanBinaListesi.xhtml")) {
			openTabIndex = 7;
		}
		if (viewId.equals("/odemeGiris.xhtml")
				|| viewId.equals("/odemeGorme.xhtml")) {
			openTabIndex = 8;
		}
		if (viewId.equals("/index.xhtml")) {
			openTabIndex = -1;
		}

	}

	public int getOpenTabIndex() {
		return openTabIndex;
	}

	public void setOpenTabIndex(int openTabIndex) {
		this.openTabIndex = openTabIndex;
	}

	public void onTabChange(TabChangeEvent event) {

		String activeTab = event.getTab().getId();
		int activeTabIndex = 0;

		for (UIComponent comp : event.getTab().getParent().getChildren()) {
			if (comp.getId().equals(activeTab)) {
				break;
			}
			activeTabIndex++;
		}
		openTabIndex = activeTabIndex;
	}

	public void listener(javax.faces.event.ActionEvent event) {

		style = new String[50];
		for (int i=0;i<50;i++){
			style[i]="";
		}
		UIMenuItem item = (UIMenuItem) event.getComponent();
		if (item!=null && item.getId().contains("binakayit") && binaBean!=null){
			binaBean.setYeniBina(false);
		}
		StringBuilder sb = new StringBuilder();
		sb.append(item.getId());
		sb.append(".jsf");
		if (item.getId().contains("duyurugiris")){
			loginBean.setDuyuru(new Duyuru());
		}
		// sb.append(".jsf?faces-redirect=true");
		style[viewMap.get(item.getId()) - 1] = String
				.format("background-color :%s;color:%s;font-weight:%s;-webkit-box-shadow:%s;-moz-box-shadow:%s;box-shadow:%s",
						color, fontColor, fontWeight, shadow, shadow, shadow);

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(sb.toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getFontWeight() {
		return fontWeight;
	}

	public void setFontWeight(String fontWeight) {
		this.fontWeight = fontWeight;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	private String menuAdi(int yetkiId) {
		String str = "";
		for (YetkiSayfaDTO y : loginBean.getSayfaYetkileri()) {
			if (y.getYetkiId() == yetkiId) {
				str = y.getSayfaAdi().replaceAll(".jsf", "");
				break;

			}
		}
		return str;
	}

	public Boolean[] getRender() {
		return render;
	}

	public void setRender(Boolean[] render) {
		this.render = render;
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
	
	

}
