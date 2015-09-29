package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.util.Date;


public class OdemeKontrolOtuzGunDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int belediyeKod;
	private int binaId;
	private int basvuruId;
	private String binaAdi;
	private String belediyeAdi;
	private Date kontrolTarihi;
	private long elektrikliAsansor;
	private long hidrolikAsansor;
	private double toplamTutar;
	private double kontrolTutari;
	private double odenenTutar;
	private double odenenToplamTutar;
	private double odenmeyenTutar;
	private double odenmeyenToplamTutar;
	private String acikAdres;
	private String caddeSokak;
	private String mahalle;
	private String binaNo;
	private String binaKisi;
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
	public double getOdenenTutar() {
		return odenenTutar;
	}
	public void setOdenenTutar(double odenenTutar) {
		this.odenenTutar = odenenTutar;
	}
	public double getOdenenToplamTutar() {
		return odenenToplamTutar;
	}
	public void setOdenenToplamTutar(double odenenToplamTutar) {
		this.odenenToplamTutar = odenenToplamTutar;
	}
	public double getOdenmeyenTutar() {
		return odenmeyenTutar;
	}
	public void setOdenmeyenTutar(double odenmeyenTutar) {
		this.odenmeyenTutar = odenmeyenTutar;
	}
	public double getOdenmeyenToplamTutar() {
		return odenmeyenToplamTutar;
	}
	public void setOdenmeyenToplamTutar(double odenmeyenToplamTutar) {
		this.odenmeyenToplamTutar = odenmeyenToplamTutar;
	}
	public int getBelediyeKod() {
		return belediyeKod;
	}
	public void setBelediyeKod(int belediyeKod) {
		this.belediyeKod = belediyeKod;
	}
	public long getElektrikliAsansor() {
		return elektrikliAsansor;
	}
	public void setElektrikliAsansor(long elektrikliAsansor) {
		this.elektrikliAsansor = elektrikliAsansor;
	}
	public long getHidrolikAsansor() {
		return hidrolikAsansor;
	}
	public void setHidrolikAsansor(long hidrolikAsansor) {
		this.hidrolikAsansor = hidrolikAsansor;
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
	
	
}
