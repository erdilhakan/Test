package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.util.Date;


public class CihazOdemeKontrolDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int belediyeKod;
	private int binaId;
	private int basvuruId;
	private String binaAdi;
	private String belediyeAdi;
	private Date kontrolTarihi;
	private double toplamTutar;
	private double kontrolTutari;
	private String acikAdres;
	private String caddeSokak;
	private String mahalle;
	private String binaNo;
	private String kimlikNo;
	private String cihazTipi;
	private String durakSayisi;
	private String kontrolTuru;
	private String odendiMi;
	private String yapiTip;
	private String ada;
	private String parsel;
	private String pafta;
	private Date odemeTarihi;
	private String yonetici;
	private long telefonNo;
	private String bakimciFirmaAdi;
	private String tseBelgeNo;
	private String gecerlilikSuresi;
	private String etiket;
	
	public int getBinaId() {
		return binaId;
	}
	public void setBinaId(int binaId) {
		this.binaId = binaId;
	}
	public int getBasvuruId() {
		return basvuruId;
	}
	public void setBasvuruId(int basvuruId) {
		this.basvuruId = basvuruId;
	}
	public String getBinaAdi() {
		return binaAdi;
	}
	public void setBinaAdi(String binaAdi) {
		this.binaAdi = binaAdi;
	}
	public String getBelediyeAdi() {
		return belediyeAdi;
	}
	public void setBelediyeAdi(String belediyeAdi) {
		this.belediyeAdi = belediyeAdi;
	}
	
	public Date getKontrolTarihi() {
		return kontrolTarihi;
	}
	public void setKontrolTarihi(Date kontrolTarihi) {
		this.kontrolTarihi = kontrolTarihi;
	}
	public double getToplamTutar() {
		return toplamTutar;
	}
	public void setToplamTutar(double toplamTutar) {
		this.toplamTutar = toplamTutar;
	}
	public double getKontrolTutari() {
		return kontrolTutari;
	}
	public void setKontrolTutari(double kontrolTutari) {
		this.kontrolTutari = kontrolTutari;
	}
	public int getBelediyeKod() {
		return belediyeKod;
	}
	public void setBelediyeKod(int belediyeKod) {
		this.belediyeKod = belediyeKod;
	}
	public String getAcikAdres() {
		return acikAdres;
	}
	public void setAcikAdres(String acikAdres) {
		this.acikAdres = acikAdres;
	}
	public String getCaddeSokak() {
		return caddeSokak;
	}
	public void setCaddeSokak(String caddeSokak) {
		this.caddeSokak = caddeSokak;
	}
	public String getMahalle() {
		return mahalle;
	}
	public void setMahalle(String mahalle) {
		this.mahalle = mahalle;
	}
	public String getBinaNo() {
		return binaNo;
	}
	public void setBinaNo(String binaNo) {
		this.binaNo = binaNo;
	}
	public String getKimlikNo() {
		return kimlikNo;
	}
	public void setKimlikNo(String kimlikNo) {
		this.kimlikNo = kimlikNo;
	}
	public String getCihazTipi() {
		return cihazTipi;
	}
	public void setCihazTipi(String cihazTipi) {
		this.cihazTipi = cihazTipi;
	}
	
	public String getDurakSayisi() {
		return durakSayisi;
	}
	public void setDurakSayisi(String durakSayisi) {
		this.durakSayisi = durakSayisi;
	}
	public String getKontrolTuru() {
		return kontrolTuru;
	}
	public void setKontrolTuru(String kontrolTuru) {
		this.kontrolTuru = kontrolTuru;
	}
	public String getOdendiMi() {
		return odendiMi;
	}
	public void setOdendiMi(String odendiMi) {
		this.odendiMi = odendiMi;
	}
	public String getYapiTip() {
		return yapiTip;
	}
	public void setYapiTip(String yapiTip) {
		this.yapiTip = yapiTip;
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
	public Date getOdemeTarihi() {
		return odemeTarihi;
	}
	public void setOdemeTarihi(Date odemeTarihi) {
		this.odemeTarihi = odemeTarihi;
	}
	public String getYonetici() {
		return yonetici;
	}
	public void setYonetici(String yonetici) {
		this.yonetici = yonetici;
	}
	public long getTelefonNo() {
		return telefonNo;
	}
	public void setTelefonNo(long telefonNo) {
		this.telefonNo = telefonNo;
	}
	public String getBakimciFirmaAdi() {
		return bakimciFirmaAdi;
	}
	public void setBakimciFirmaAdi(String bakimciFirmaAdi) {
		this.bakimciFirmaAdi = bakimciFirmaAdi;
	}
	public String getTseBelgeNo() {
		return tseBelgeNo;
	}
	public void setTseBelgeNo(String tseBelgeNo) {
		this.tseBelgeNo = tseBelgeNo;
	}
	public String getGecerlilikSuresi() {
		return gecerlilikSuresi;
	}
	public void setGecerlilikSuresi(String gecerlilikSuresi) {
		this.gecerlilikSuresi = gecerlilikSuresi;
	}
	public String getEtiket() {
		return etiket;
	}
	public void setEtiket(String etiket) {
		this.etiket = etiket;
	}
	
	
}