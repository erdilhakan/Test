package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.util.Date;


public class RaporTeslimKontrolDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int binaId;
	private int basvuruId;
	private int randevuId;
	private int kontrolId;
	private int raporId;
	private int cihazId;
	private String binaAdi;
	private Date onayTarihi;
	private String acikAdres;
	private String caddeSokak;
	private String mahalle;
	private String binaNo;
	private String asansorUavtEtiket;
	private String binaKisi;
	private String bakimciFirma;
	private long telefonNo;
	
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
	

	public Date getOnayTarihi() {
		return onayTarihi;
	}
	public void setOnayTarihi(Date onayTarihi) {
		this.onayTarihi = onayTarihi;
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
	public String getAsansorUavtEtiket() {
		return asansorUavtEtiket;
	}
	public void setAsansorUavtEtiket(String asansorUavtEtiket) {
		this.asansorUavtEtiket = asansorUavtEtiket;
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
	public String getBakimciFirma() {
		return bakimciFirma;
	}
	public void setBakimciFirma(String bakimciFirma) {
		this.bakimciFirma = bakimciFirma;
	}
	public long getTelefonNo() {
		return telefonNo;
	}
	public void setTelefonNo(long telefonNo) {
		this.telefonNo = telefonNo;
	}
	
	
}
