package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class BilgilendirmeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String randevuTarihi;

	private long tcKimlikNo;
	private String binaAdi = "";
	private String adiSoyadi = "";
	private String ePosta = "";
	private String telefonNo = "";
	private String rol = "";
	private String il;
	private String ilce;
	private String mahalle;
	private String caddeSokak;
	private String binaNo;

	public String getAdiSoyadi() {
		return adiSoyadi;
	}

	public void setAdiSoyadi(String adiSoyadi) {
		this.adiSoyadi = adiSoyadi;
	}

	public String getePosta() {
		return ePosta;
	}

	public void setePosta(String ePosta) {
		this.ePosta = ePosta;
	}

	public String getTelefonNo() {
		return telefonNo;
	}

	public void setTelefonNo(String telefonNo) {
		this.telefonNo = telefonNo;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getBinaAdi() {
		return binaAdi;
	}

	public void setBinaAdi(String binaAdi) {
		this.binaAdi = binaAdi;
	}

	public String getRandevuTarihi() {
		return randevuTarihi;
	}

	public void setRandevuTarihi(String randevuTarihi) {
		this.randevuTarihi = randevuTarihi;
	}

	public long getTcKimlikNo() {
		return tcKimlikNo;
	}

	public void setTcKimlikNo(long tcKimlikNo) {
		this.tcKimlikNo = tcKimlikNo;
	}

	public String getIl() {
		return il;
	}

	public void setIl(String il) {
		this.il = il;
	}

	public String getIlce() {
		return ilce;
	}

	public void setIlce(String ilce) {
		this.ilce = ilce;
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

	public String getBinaNo() {
		return binaNo;
	}

	public void setBinaNo(String binaNo) {
		this.binaNo = binaNo;
	}

}
