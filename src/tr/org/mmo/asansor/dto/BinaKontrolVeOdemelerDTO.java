package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BinaKontrolVeOdemelerDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int randevuId;
	private int cihazId;
	private String etiket;
	private int binaKodu;
	private Date kontrolTarihi;
	private int tescilNo;
	private String binaAdi;
	private String acikAdres;
	private String mahalle;
	private String caddeSokak;
	private String ilAdi;
	private String ilceAdi;
	private String binaNo;
	private int belediyeKod;
	public String getEtiket() {
		return etiket;
	}
	public void setEtiket(String etiket) {
		this.etiket = etiket;
	}
	public int getBelediyeKod() {
		return belediyeKod;
	}
	public void setBelediyeKod(int belediyeKod) {
		this.belediyeKod = belediyeKod;
	}
	private String belediyeAdi;
	private String yapiTipiAdi;
	private int yapiTipi;
	private String yoneticiAdiSoyadi;
	private String yoneticiTelefon;
	private String cihazTipi;
	private int cihazTipId;
	private int basvuruId;
	private int kontrolId;
	private String odemeYapildiMi;
	
	private BasvuruAsansorDTO basvuruAsansor=new BasvuruAsansorDTO();
	private OdemeDTO odeme=new OdemeDTO();
	public Date getKontrolTarihi() {
		return kontrolTarihi;
	}
	public void setKontrolTarihi(Date kontrolTarihi) {
		this.kontrolTarihi = kontrolTarihi;
	}
	public int getBinaKodu() {
		return binaKodu;
	}
	public void setBinaKodu(int binaKodu) {
		this.binaKodu = binaKodu;
	}
	public int getTescilNo() {
		return tescilNo;
	}
	public void setTescilNo(int tescilNo) {
		this.tescilNo = tescilNo;
	}
	public String getBinaAdi() {
		return binaAdi;
	}
	public void setBinaAdi(String binaAdi) {
		this.binaAdi = binaAdi;
	}
	public String getAcikAdres() {
		return acikAdres;
	}
	public void setAcikAdres(String acikAdres) {
		this.acikAdres = acikAdres;
	}
	public String getMahalle() {
		return mahalle;
	}
	public void setMahalle(String mahalle) {
		this.mahalle = mahalle;
	}
	public String getCaddeSokak() {
		return caddeSokak;
	}
	public void setCaddeSokak(String caddeSokak) {
		this.caddeSokak = caddeSokak;
	}
	public String getIlAdi() {
		return ilAdi;
	}
	public void setIlAdi(String ilAdi) {
		this.ilAdi = ilAdi;
	}
	public String getIlceAdi() {
		return ilceAdi;
	}
	public void setIlceAdi(String ilceAdi) {
		this.ilceAdi = ilceAdi;
	}
	public String getBinaNo() {
		return binaNo;
	}
	public void setBinaNo(String binaNo) {
		this.binaNo = binaNo;
	}
	public String getBelediyeAdi() {
		return belediyeAdi;
	}
	public void setBelediyeAdi(String belediyeAdi) {
		this.belediyeAdi = belediyeAdi;
	}
	public String getYapiTipiAdi() {
		return yapiTipiAdi;
	}
	public void setYapiTipiAdi(String yapiTipiAdi) {
		this.yapiTipiAdi = yapiTipiAdi;
	}
	public int getYapiTipi() {
		return yapiTipi;
	}
	public void setYapiTipi(int yapiTipi) {
		this.yapiTipi = yapiTipi;
	}
	public String getYoneticiAdiSoyadi() {
		return yoneticiAdiSoyadi;
	}
	public void setYoneticiAdiSoyadi(String yoneticiAdiSoyadi) {
		this.yoneticiAdiSoyadi = yoneticiAdiSoyadi;
	}
	public String getYoneticiTelefon() {
		return yoneticiTelefon;
	}
	public void setYoneticiTelefon(String yoneticiTelefon) {
		this.yoneticiTelefon = yoneticiTelefon;
	}
	public String getCihazTipi() {
		return cihazTipi;
	}
	public void setCihazTipi(String cihazTipi) {
		this.cihazTipi = cihazTipi;
	}
	public int getBasvuruId() {
		return basvuruId;
	}
	public void setBasvuruId(int basvuruId) {
		this.basvuruId = basvuruId;
	}
	public int getKontrolId() {
		return kontrolId;
	}
	public void setKontrolId(int kontrolId) {
		this.kontrolId = kontrolId;
	}
	
	public BasvuruAsansorDTO getBasvuruAsansor() {
		return basvuruAsansor;
	}
	public void setBasvuruAsansor(BasvuruAsansorDTO basvuruAsansor) {
		this.basvuruAsansor = basvuruAsansor;
	}
	public OdemeDTO getOdeme() {
		return odeme;
	}
	public void setOdeme(OdemeDTO odeme) {
		this.odeme = odeme;
	}
	public int getCihazId() {
		return cihazId;
	}
	public void setCihazId(int cihazId) {
		this.cihazId = cihazId;
	}
	public int getRandevuId() {
		return randevuId;
	}
	public void setRandevuId(int randevuId) {
		this.randevuId = randevuId;
	}
	public int getCihazTipId() {
		return cihazTipId;
	}
	public void setCihazTipId(int cihazTipId) {
		this.cihazTipId = cihazTipId;
	}
	public String getOdemeYapildiMi() {
		return odemeYapildiMi;
	}
	public void setOdemeYapildiMi(String odemeYapildiMi) {
		this.odemeYapildiMi = odemeYapildiMi;
	}
	
	

}
