package tr.org.mmo.asansor.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;




import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import flex2.compiler.mxml.analyzer.WebServiceAnalyzer;
import tr.org.mmo.asansor.beans_.ReferansDenetimKayitKontrolSorularListesi;
import tr.org.mmo.asansor.business.AsansorBusiness;
import tr.org.mmo.asansor.business.BinaBusiness;
import tr.org.mmo.asansor.business.BirimBusiness;
import tr.org.mmo.asansor.business.KullaniciBusiness;
import tr.org.mmo.asansor.business.OdemeBusiness;
import tr.org.mmo.asansor.business.SoruListBusiness;
import tr.org.mmo.asansor.business.WebServiceBusiness;
import tr.org.mmo.asansor.dto.AsansorKapsamDTO;
import tr.org.mmo.asansor.dto.BakanlikSoruDTO;
import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.dto.EskiRaporDTO;
import tr.org.mmo.asansor.dto.KullaniciTurDTO;
import tr.org.mmo.asansor.dto.OdemeSekliDTO;
import tr.org.mmo.asansor.dto.ReferansDenetimSorularEslestirmeDTO;
import tr.org.mmo.asansor.dto.SubeDTO;
import tr.org.mmo.asansor.dto.SubeTemsilcilikHesapDTO;
import tr.org.mmo.asansor.dto.TemsilcilikDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;

@ManagedBean(eager=true)
@SessionScoped
public class SessionBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2159661659520214439L;

	private List<KullaniciTurDTO> kullaniciTurleri = new ArrayList<KullaniciTurDTO>();
	private List<BinaDTO> tumBinalar = new ArrayList<BinaDTO>();
	private List<OdemeSekliDTO> odemeSekilleri = new ArrayList<OdemeSekliDTO>();
	private boolean notificationGoster = false;
	private boolean notificationGosterTeslim = false;
	private List<ReferansDenetimKayitKontrolSorularListesi> referansDenetimKayitKontrolSorularListesi=new ArrayList<ReferansDenetimKayitKontrolSorularListesi>();
	private List<ReferansDenetimSorularEslestirmeDTO> referansDenetimSorularEslestirmeListesi=new ArrayList<ReferansDenetimSorularEslestirmeDTO>();
	private List<ReferansDenetimKayitKontrolSorularListesi> referansDenetimKayitKontrolSorularListesi15092015Sonrasi=new ArrayList<ReferansDenetimKayitKontrolSorularListesi>();
	private List<ReferansDenetimSorularEslestirmeDTO> referansDenetimSorularEslestirmeListesi15092015Sonrasi=new ArrayList<ReferansDenetimSorularEslestirmeDTO>();
	private Map<String,String> referansSoruSkalaMap=new HashMap<String, String>();
	private List<SubeDTO> subeler = new ArrayList<SubeDTO>();
	private List<TemsilcilikDTO> temsilcilikler = new ArrayList<TemsilcilikDTO>();
	private HashMap<Integer, List<SubeTemsilcilikHesapDTO>> subeTemsilcilikHesapMap = new HashMap<Integer, List<SubeTemsilcilikHesapDTO>>();
	private BakanlikSoruDTO bakanlikSoru=new BakanlikSoruDTO();
	String[] styleColors = { "#FFFFCC", "#FFFF99", "#FFFF66", "#FFFF33",
			"#FFFF00", "#FFCCFF", "#FFCCCC", "#FFCC99", "#FFCC66", "#FFCC33",
			"#FFCC00", "#FF99FF", "#FF99CC", "#FF9999", "#FF9966", "#FF9933",
			"#FF9900", "#FF66FF", "#FF66CC", "#FF66CC", "#FF6666", "#FF6633",
			"#FF6600", "#FF33FF", "#FF33CC", "#FF3399", "#FF3399", "#FF3333",
			"#FF3300", "#FF00FF", "#FF00CC", "#FF0099", "#FF0066", "#FF0033",
			"#FF0000", "#CCFFFF", "#CCFFCC", "#CCFF99", "#CCFF66", "#CCFF33",
			"#CCFF00", "#CCCCFF", "#CCCCCC", "#CCCC99", "#CCCC66", "#CCCC33",
			"#CCCC00", "#CC99FF", "#CC99CC", "#CC9999", "#CC9966", "#CC9933",
			"#CC9900", "#CC66FF", "#CC66CC", "#CC6699", "#CC6666", "#CC6633",
			"#CC6600", "#CC33FF", "#CC33CC", "#CC3399", "#CC3366", "#CC3333",
			"#CC3300", "#CC00FF", "#CC00CC", "#CC0099", "#CC0066", "#CC0033",
			"#CC0000", "#99FFFF", "#99FFCC", "#99FF99", "#99FF66", "#99FF33",
			"#99FF00", "#99CCFF", "#99CCCC", "#99CC99", "#99CC66", "#99CC33",
			"#99CC00", "#9999FF", "#9999CC", "#999999", "#999966", "#999933",
			"#999900", "#9966FF", "#9966CC", "#996699", "#996666", "#996633",
			"#996600", "#9933FF", "#9933CC", "#993399", "#993366", "#993333",
			"#993300", "#9900FF", "#9900CC", "#990099", "#990066", "#990033",
			"#990000", "#66FFFF", "#66FFCC", "#66FF99", "#66FF66", "#66FF33",
			"#66FF00", "#66CCFF", "#66CCCC", "#66CC99", "#66CC66", "#66CC33",
			"#66CC00", "#6699FF", "#6699CC", "#669999", "#669966", "#669933",
			"#669900", "#6666FF", "#6666CC", "#666699", "#666666", "#666633",
			"#666600", "#6633FF", "#6633CC", "#663399", "#663366", "#663333",
			"#663300", "#6600FF", "#6600CC", "#660099", "#660066", "#660033",
			"#660000", "#33FFFF", "#33FFCC", "#33FF99", "#33FF66", "#33FF33",
			"#33FF00", "#33CCFF", "#33CCCC", "#33CC99", "#33CC66", "#33CC33",
			"#33CC00", "#3399FF", "#3399CC", "#339999", "#339966", "#339933",
			"#339900", "#3366FF", "#3366CC", "#336699", "#336666", "#336633",
			"#336600", "#3333FF", "#3333CC", "#333399", "#333366", "#333333",
			"#333300", "#3300FF", "#3300CC", "#330099", "#330066", "#330033",
			"#330000", "#00FFFF", "#00FFCC", "#00FF99", "#00FF66", "#00FF33",
			"#00FF00", "#00CCFF", "#00CCCC", "#00CC99", "#00CC66", "#00CC33",
			"#00CC00", "#0099FF", "#0099CC", "#009999", "#009966", "#009933",
			"#009900", "#0066FF", "#0066CC", "#006699", "#006666", "#006633",
			"#006600", "#0033FF", "#0033CC", "#003399", "#003366", "#003333",
			"#003300", "#0000FF", "#0000CC", "#000099", "#000066", "#000033",
			"#000000" };
	String[] colors = new String[28];
	private List<AsansorKapsamDTO> kapsamTurleri = new ArrayList<AsansorKapsamDTO>();

	public SessionBean() {
		for (int i = 0; i < 28; i++) {
			colors[i] = getRandomColor();
		}
		try {

			tumBinalar = tumBinalar.size() == 0 ? new BinaBusiness()
					.binaBulByIlIlce() : tumBinalar;

			kullaniciTurleri = kullaniciTurleri.size() == 0 ? new KullaniciBusiness()
					.kullaniciTurleri() : kullaniciTurleri;
			kapsamTurleri = new AsansorBusiness().getKapsamTurleri();
			odemeSekilleri = new OdemeBusiness().getOdemeSekilleri();
			getTumSubeler();
			getTumTemsilcilikler();
			getBakanlikSoruTarih();
			
			if (referansSoruSkalaMap.size()<=0){
				/*
				ServisSonucOfArrayOfReferansSoruSkala8Zb117HL servisSonucOfArrayOfReferansSoruSkala=new ServisSonucOfArrayOfReferansSoruSkala8Zb117HL();
						
				
					servisSonucOfArrayOfReferansSoruSkala=	new WebServiceBusiness().referansSoruSkalaListesiGetir();
					if (!servisSonucOfArrayOfReferansSoruSkala.isHata()){
					ArrayOfReferansSoruSkala 	arrayOfReferansSoruSkala= new ArrayOfReferansSoruSkala();
						arrayOfReferansSoruSkala=servisSonucOfArrayOfReferansSoruSkala.getSonuc().getValue();
						List<ReferansSoruSkala> list=new ArrayList<ReferansSoruSkala>();
						list=arrayOfReferansSoruSkala.getReferansSoruSkala();
				
						for (ReferansSoruSkala r:list){
							referansSoruSkalaMap.put(r.getDurumAciklama().getValue(), r.getId());
						}
						
					
						
					}
					*/
					
				referansSoruSkalaMap.put("Uygun","1");
				referansSoruSkalaMap.put("Uygun Degil", "2");
				referansSoruSkalaMap.put("Uygulanamaz", "3");
				
				
				
			}
			subeTemsilcilikHesapMap = new BirimBusiness()
					.getSubeTemsilcilikHesapNumaralari();
		
		} catch (CRUDException e) {
			e.printStackTrace();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	

	private void getBakanlikSoruTarih() {
		try {
			bakanlikSoru = new SoruListBusiness().getBakanlikSoruTarih();
		} catch (CRUDException e) {

			e.printStackTrace();
		}
		
	}



	public List<BinaDTO> getTumBinalar() {
		return tumBinalar;
	}

	public void setTumBinalar(List<BinaDTO> tumBinalar) {
		this.tumBinalar = tumBinalar;
	}

	public boolean isNotificationGoster() {
		return notificationGoster;
	}

	public void setNotificationGoster(boolean notificationGoster) {
		this.notificationGoster = notificationGoster;
	}

	public String raporlar() {
		notificationGoster = false;
		return "raporlar.xhtml?faces-redirect=true";
	}
	public String raporTeslim() {
		notificationGosterTeslim = false;
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		request.getSession().setAttribute("raporTeslimGor", "E");
		return "raporteslim.xhtml?faces-redirect=true";
	}
	public void notificationGizle() {
		notificationGoster = false;
		notificationGosterTeslim = false;
	}
	
	private List<EskiRaporDTO> raporTeslimList;
	
	

	public List<EskiRaporDTO> getRaporTeslimList() {
		return raporTeslimList;
	}



	public void setRaporTeslimList(List<EskiRaporDTO> raporTeslimList) {
		this.raporTeslimList = raporTeslimList;
	}



	public List<KullaniciTurDTO> getKullaniciTurleri() {
		return kullaniciTurleri;
	}

	public void setKullaniciTurleri(List<KullaniciTurDTO> kullaniciTurleri) {
		this.kullaniciTurleri = kullaniciTurleri;
	}

	public List<AsansorKapsamDTO> getKapsamTurleri() {
		return kapsamTurleri;
	}

	public void setKapsamTurleri(List<AsansorKapsamDTO> kapsamTurleri) {
		this.kapsamTurleri = kapsamTurleri;
	}

	public String[] getColors() {
		return colors;
	}

	public void setColors(String[] colors) {
		this.colors = colors;
	}

	private String getRandomColor() {

		return styleColors[new Random().nextInt(styleColors.length)];

	}

	public List<OdemeSekliDTO> getOdemeSekilleri() {
		return odemeSekilleri;
	}

	public void setOdemeSekilleri(List<OdemeSekliDTO> odemeSekilleri) {
		this.odemeSekilleri = odemeSekilleri;
	}

	public List<SubeDTO> getSubeler() {
		return subeler;
	}

	public void setSubeler(List<SubeDTO> subeler) {
		this.subeler = subeler;
	}

	public List<TemsilcilikDTO> getTemsilcilikler() {
		return temsilcilikler;
	}

	public void setTemsilcilikler(List<TemsilcilikDTO> temsilcilikler) {
		this.temsilcilikler = temsilcilikler;
	}

	public void getTumTemsilcilikler() {
		try {
			temsilcilikler = new BirimBusiness().temsilcilikler();
		} catch (CRUDException e) {

			e.printStackTrace();
		}
	}

	public void getTumSubeler() {
		try {
			subeler = new BirimBusiness().subeler();
		} catch (CRUDException e) {

			e.printStackTrace();
		}
	}

	public HashMap<Integer, List<SubeTemsilcilikHesapDTO>> getSubeTemsilcilikHesapMap() {
		return subeTemsilcilikHesapMap;
	}

	public void setSubeTemsilcilikHesapMap(
			HashMap<Integer, List<SubeTemsilcilikHesapDTO>> subeTemsilcilikHesapMap) {
		this.subeTemsilcilikHesapMap = subeTemsilcilikHesapMap;
	}

	public List<ReferansDenetimKayitKontrolSorularListesi> getReferansDenetimKayitKontrolSorularListesi() {
		return referansDenetimKayitKontrolSorularListesi;
	}

	public void setReferansDenetimKayitKontrolSorularListesi(
			List<ReferansDenetimKayitKontrolSorularListesi> referansDenetimKayitKontrolSorularListesi) {
		this.referansDenetimKayitKontrolSorularListesi = referansDenetimKayitKontrolSorularListesi;
	}

	public Map<String, String> getReferansSoruSkalaMap() {
		return referansSoruSkalaMap;
	}

	public void setReferansSoruSkalaMap(Map<String, String> referansSoruSkalaMap) {
		this.referansSoruSkalaMap = referansSoruSkalaMap;
	}



	public List<ReferansDenetimSorularEslestirmeDTO> getReferansDenetimSorularEslestirmeListesi() {
		return referansDenetimSorularEslestirmeListesi;
	}



	public void setReferansDenetimSorularEslestirmeListesi(
			List<ReferansDenetimSorularEslestirmeDTO> referansDenetimSorularEslestirmeListesi) {
		this.referansDenetimSorularEslestirmeListesi = referansDenetimSorularEslestirmeListesi;
	}



	public BakanlikSoruDTO getBakanlikSoru() {
		return bakanlikSoru;
	}



	public void setBakanlikSoru(BakanlikSoruDTO bakanlikSoru) {
		this.bakanlikSoru = bakanlikSoru;
	}



	public boolean isNotificationGosterTeslim() {
		return notificationGosterTeslim;
	}



	public void setNotificationGosterTeslim(boolean notificationGosterTeslim) {
		this.notificationGosterTeslim = notificationGosterTeslim;
	}



	public List<ReferansDenetimKayitKontrolSorularListesi> getReferansDenetimKayitKontrolSorularListesi15092015Sonrasi() {
		return referansDenetimKayitKontrolSorularListesi15092015Sonrasi;
	}



	public void setReferansDenetimKayitKontrolSorularListesi15092015Sonrasi(
			List<ReferansDenetimKayitKontrolSorularListesi> referansDenetimKayitKontrolSorularListesi15092015Sonrasi) {
		this.referansDenetimKayitKontrolSorularListesi15092015Sonrasi = referansDenetimKayitKontrolSorularListesi15092015Sonrasi;
	}



	public List<ReferansDenetimSorularEslestirmeDTO> getReferansDenetimSorularEslestirmeListesi15092015Sonrasi() {
		return referansDenetimSorularEslestirmeListesi15092015Sonrasi;
	}



	public void setReferansDenetimSorularEslestirmeListesi15092015Sonrasi(
			List<ReferansDenetimSorularEslestirmeDTO> referansDenetimSorularEslestirmeListesi15092015Sonrasi) {
		this.referansDenetimSorularEslestirmeListesi15092015Sonrasi = referansDenetimSorularEslestirmeListesi15092015Sonrasi;
	}



	
public void referansDenetimDoldur() throws Exception{
	if (referansDenetimKayitKontrolSorularListesi.size()<=0 || referansDenetimSorularEslestirmeListesi.size()<=0){
		referansDenetimKayitKontrolSorularListesi=new SoruListBusiness().getReferansDenetimKayitKontrolSorulari();
		referansDenetimSorularEslestirmeListesi=new SoruListBusiness().getReferansDenetimKayitEslestirme();
	}
	
	
	
	if (referansDenetimKayitKontrolSorularListesi15092015Sonrasi.size()<=0 || referansDenetimSorularEslestirmeListesi15092015Sonrasi.size()<=0){
		referansDenetimKayitKontrolSorularListesi15092015Sonrasi=new ArrayList<ReferansDenetimKayitKontrolSorularListesi>();
		referansDenetimSorularEslestirmeListesi15092015Sonrasi=new ArrayList<ReferansDenetimSorularEslestirmeDTO>();
	
			referansDenetimSorularEslestirmeListesi15092015Sonrasi=new WebServiceBusiness().referansDenetimSorularEslestirmeListesiGetir();
			referansDenetimKayitKontrolSorularListesi15092015Sonrasi=new WebServiceBusiness().referansDenetimKayitKontrolSorularListesiGetir();
			
			
		
		
		
	}
		
	}

	
	
	

}
