package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.util.Date;


public class SariEtiketKontrolDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int binaId;
	private int basvuruId;
	private int randevuId;
	private int kontrolId;
	private int raporId;
	private int cihazId;
	private String binaAdi;
	private Date kontrolTarihi;
	private String acikAdres;
	private String caddeSokak;
	private String mahalle;
	private String binaNo;
	private String muhendisAdiSoyadi;
	private String cihazTuru;
	private String kontrolTuru;
	private String etiket;
	private String asansorUavtEtiket;
	private String bakimciFirmaAdi;
	private String binaKisi;
	private long telefonNo;
	private int belediyeKod;
	private String belediyeAdi;
    private int binaUavtKod;
    private Date kayitTarihi;
    private Date raporTeslimTarihi;
	
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
	public Date getKontrolTarihi() {
		return kontrolTarihi;
	}
	public void setKontrolTarihi(Date kontrolTarihi) {
		this.kontrolTarihi = kontrolTarihi;
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
	public String getMuhendisAdiSoyadi() {
		return muhendisAdiSoyadi;
	}
	public void setMuhendisAdiSoyadi(String muhendisAdiSoyadi) {
		this.muhendisAdiSoyadi = muhendisAdiSoyadi;
	}
	public String getCihazTuru() {
		return cihazTuru;
	}
	public void setCihazTuru(String cihazTuru) {
		this.cihazTuru = cihazTuru;
	}
	public String getKontrolTuru() {
		return kontrolTuru;
	}
	public void setKontrolTuru(String kontrolTuru) {
		this.kontrolTuru = kontrolTuru;
	}
	public String getEtiket() {
		return etiket;
	}
	public void setEtiket(String etiket) {
		this.etiket = etiket;
	}
	public String getAsansorUavtEtiket() {
		return asansorUavtEtiket;
	}
	public void setAsansorUavtEtiket(String asansorUavtEtiket) {
		this.asansorUavtEtiket = asansorUavtEtiket;
	}
	public String getBakimciFirmaAdi() {
		return bakimciFirmaAdi;
	}
	public void setBakimciFirmaAdi(String bakimciFirmaAdi) {
		this.bakimciFirmaAdi = bakimciFirmaAdi;
	}
	public int getRandevuId() {
		return randevuId;
	}
	public void setRandevuId(int randevuId) {
		this.randevuId = randevuId;
	}
	public int getKontrolId() {
		return kontrolId;
	}
	public void setKontrolId(int kontrolId) {
		this.kontrolId = kontrolId;
	}
	public int getRaporId() {
		return raporId;
	}
	public void setRaporId(int raporId) {
		this.raporId = raporId;
	}
	public int getCihazId() {
		return cihazId;
	}
	public void setCihazId(int cihazId) {
		this.cihazId = cihazId;
	}
	public String getBinaKisi() {
		return binaKisi;
	}
	public void setBinaKisi(String binaKisi) {
		this.binaKisi = binaKisi;
	}
	public long getTelefonNo() {
		return telefonNo;
	}
	public void setTelefonNo(long telefonNo) {
		this.telefonNo = telefonNo;
	}
	public int getBelediyeKod() {
		return belediyeKod;
	}
	public void setBelediyeKod(int belediyeKod) {
		this.belediyeKod = belediyeKod;
	}
	public String getBelediyeAdi() {
		return belediyeAdi;
	}
	public void setBelediyeAdi(String belediyeAdi) {
		this.belediyeAdi = belediyeAdi;
	}
	public int getBinaUavtKod() {
		return binaUavtKod;
	}
	public void setBinaUavtKod(int binaUavtKod) {
		this.binaUavtKod = binaUavtKod;
	}
	public Date getKayitTarihi() {
		return kayitTarihi;
	}
	public void setKayitTarihi(Date kayitTarihi) {
		this.kayitTarihi = kayitTarihi;
	}
	public Date getRaporTeslimTarihi() {
		return raporTeslimTarihi;
	}
	public void setRaporTeslimTarihi(Date raporTeslimTarihi) {
		this.raporTeslimTarihi = raporTeslimTarihi;
	}
	
	
}
