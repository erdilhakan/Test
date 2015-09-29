package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tr.org.mmo.asansor.util.Util;

public class FirmaDTO implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 685293607157262332L;
	
	private int kod;
	private String unvan;
	private String adSoyad;
	private long gsmTelefon;
	private String gsmTelefonStr;
	private int il;
	private String ilAdi;
	private String ilceAdi;
	private int ilce;
	private String adres;
	private String durumu;
	private long tescilNo;
	private String uygunlukBelgesi;
	private Date gecerlilikSuresi;
	private String tseBelgesi;
	private String servisSozlesme;
	private Date sozlesmeTarihi;
	private String tseBelgeNo;
	private String eposta;
	private Date asansorBakimTarihi;
	private List<Integer> firmaIller = new ArrayList<Integer>();
	private String monteEden;
	private String yetkiliServis;
	private long telefonNo;
	private String telefonNoStr;
	private int dahili;
	private String ceBelgeTipi;
	private BakimciFirmaIletisimDTO firmaIletisim = new BakimciFirmaIletisimDTO();
	private List<BakimciFirmaIletisimDTO> firmaIletisimList = new ArrayList<BakimciFirmaIletisimDTO>();
	private long faks;
	private String faksStr;

	public Date getAsansorBakimTarihi() {
		return asansorBakimTarihi;
	}

	public void setAsansorBakimTarihi(Date asansorBakimTarihi) {
		this.asansorBakimTarihi = asansorBakimTarihi;
	}

	public long getGsmTelefon() {
		return gsmTelefon;
	}

	public void setGsmTelefon(long gsmTelefon) {
		this.gsmTelefon = gsmTelefon;
		this.gsmTelefonStr = String.valueOf(gsmTelefon);
	}

	public void setGsm_Telefon(long gsmTelefon) {
		this.gsmTelefon = gsmTelefon;
		this.gsmTelefonStr = String.valueOf(gsmTelefon);
	}

	public int getIl() {
		return il;
	}

	public void setIl(int il) {
		this.il = il;
	}

	public int getIlce() {
		return ilce;
	}

	public void setIlce(int ilce) {
		this.ilce = ilce;
	}

	public String getAdres() {
		return adres;
	}

	public void setAdres(String adres) {
		this.adres = Util.toUpperCase(adres);
	}

	public String getDurumu() {
		return durumu;
	}

	public void setDurumu(String durumu) {
		this.durumu = Util.toUpperCase(durumu);
	}

	public long getTescilNo() {
		return tescilNo;
	}

	public void setTescilNo(long tescilNo) {
		this.tescilNo = tescilNo;
	}

	public void setTescil_No(long tescilNo) {
		this.tescilNo = tescilNo;
	}

	public String getUygunlukBelgesi() {
		return uygunlukBelgesi;
	}

	public void setUygunlukBelgesi(String uygunlukBelgesi) {
		this.uygunlukBelgesi = Util.toUpperCase(uygunlukBelgesi);
	}

	public void setUygunluk_Belgesi(String uygunlukBelgesi) {
		this.uygunlukBelgesi = Util.toUpperCase(uygunlukBelgesi);
	}

	public Date getGecerlilikSuresi() {
		return gecerlilikSuresi;
	}

	public void setGecerlilikSuresi(Date gecerlilikSuresi) {
		this.gecerlilikSuresi = gecerlilikSuresi;
	}

	public void setGecerlilik_Suresi(Date gecerlilikSuresi) {
		this.gecerlilikSuresi = gecerlilikSuresi;
	}

	public String getServisSozlesme() {
		return servisSozlesme;
	}

	public void setServisSozlesme(String servisSozlesme) {
		this.servisSozlesme = Util.toUpperCase(servisSozlesme);
	}

	public void setServis_Sozlesme(String servisSozlesme) {
		this.servisSozlesme = Util.toUpperCase(servisSozlesme);
	}

	public Date getSozlesmeTarihi() {
		return sozlesmeTarihi;
	}

	public void setSozlesmeTarihi(Date sozlesmeTarihi) {
		this.sozlesmeTarihi = sozlesmeTarihi;
	}

	public void setSozlesme_Tarihi(Date sozlesmeTarihi) {
		this.sozlesmeTarihi = sozlesmeTarihi;
	}

	public String getTseBelgeNo() {
		return tseBelgeNo;
	}

	public void setTseBelgeNo(String tseBelgeNo) {
		this.tseBelgeNo = Util.toUpperCase(tseBelgeNo);
	}

	public void setTse_Belge_No(String tseBelgeNo) {
		this.tseBelgeNo = Util.toUpperCase(tseBelgeNo);
	}

	public String getEposta() {
		return eposta;
	}

	public void setEposta(String eposta) {
		this.eposta = eposta;
	}

	public int getKod() {
		return kod;
	}

	public void setKod(int kod) {
		this.kod = kod;
	}

	public String getUnvan() {
		return unvan;
	}

	public void setUnvan(String unvan) {
		this.unvan = Util.toUpperCase(unvan);
	}

	public String getAdSoyad() {
		return adSoyad;
	}

	public void setAdSoyad(String adSoyad) {
		this.adSoyad = Util.toUpperCase(adSoyad);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public String getIlAdi() {
		return ilAdi;
	}

	public void setIlAdi(String ilAdi) {
		this.ilAdi = Util.toUpperCase(ilAdi);
	}

	public String getIlceAdi() {
		return ilceAdi;
	}

	public void setIlceAdi(String ilceAdi) {
		this.ilceAdi = Util.toUpperCase(ilceAdi);
	}

	public String getMonteEden() {
		return monteEden;
	}

	public void setMonteEden(String monteEden) {
		this.monteEden = Util.toUpperCase(monteEden);
	}

	public String getYetkiliServis() {
		return yetkiliServis;
	}

	public void setYetkiliServis(String yetkiliServis) {
		this.yetkiliServis = Util.toUpperCase(yetkiliServis);
	}

	public long getTelefonNo() {
		return telefonNo;
	}

	public void setTelefonNo(long telefonNo) {
		this.telefonNo = telefonNo;
		this.telefonNoStr = String.valueOf(telefonNo);

	}

	public int getDahili() {
		return dahili;
	}

	public void setDahili(int dahili) {
		this.dahili = dahili;
	}

	public String getCeBelgeTipi() {
		return ceBelgeTipi;
	}

	public void setCeBelgeTipi(String ceBelgeTipi) {
		this.ceBelgeTipi = Util.toUpperCase(ceBelgeTipi);
	}

	public String getGsmTelefonStr() {
		return gsmTelefonStr;
	}

	public void setGsmTelefonStr(String gsmTelefonStr) {
		this.gsmTelefonStr = gsmTelefonStr;
		try {
			this.gsmTelefon = Long.parseLong(gsmTelefonStr.replace("(", "")
					.replace(")", "").replace(" ", "").trim());
		} catch (Exception e) {
			this.gsmTelefon = 0;
		}

	}

	public String getTelefonNoStr() {
		return telefonNoStr;
	}

	public void setTelefonNoStr(String telefonStr) {
		this.telefonNoStr = telefonStr;
		try {
			this.telefonNo = Long.parseLong(telefonStr.replace("(", "")
					.replace(")", "").replace(" ", "").trim());
		} catch (Exception e) {
			this.telefonNo = 0;
		}

	}

	public List<BakimciFirmaIletisimDTO> getFirmaIletisimList() {
		return firmaIletisimList;
	}

	public void setFirmaIletisimList(
			List<BakimciFirmaIletisimDTO> firmaIletisimList) {
		this.firmaIletisimList = firmaIletisimList;
	}

	public String getTseBelgesi() {
		return tseBelgesi;
	}

	public void setTseBelgesi(String tseBelgesi) {
		this.tseBelgesi = tseBelgesi;
	}

	public BakimciFirmaIletisimDTO getFirmaIletisim() {
		return firmaIletisim;
	}

	public void setFirmaIletisim(BakimciFirmaIletisimDTO firmaIletisim) {
		this.firmaIletisim = firmaIletisim;
	}

	public List<Integer> getFirmaIller() {
		return firmaIller;
	}

	public void setFirmaIller(List<Integer> firmaIller) {
		this.firmaIller = firmaIller;
	}

	public long getFaks() {
		return faks;
	}

	public void setFaks(long faks) {
		this.faks = faks;
		this.faksStr=String.valueOf(faks);
	}

	public String getFaksStr() {
		return faksStr;
	}

	public void setFaksStr(String faksStr) {
		this.faksStr = faksStr;
		try {
			this.faks = Long.parseLong(faksStr.replace("(", "")
					.replace(")", "").replace(" ", "").trim());
		} catch (Exception e) {
			this.faks = 0;
		}
	}

	
	
	

}
