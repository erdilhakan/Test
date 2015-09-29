package tr.org.mmo.asansor.dto;

import java.io.Serializable;

import tr.org.mmo.asansor.util.Util;

public class BirimDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2335144672347037031L;
	private int birimId;
	private int birimKodu;
	private int kullaniciId;
	private String birimTipi;
	private int il;
	private int ilce;
	
	public int getBirimId() {
		return birimId;
	}
	public void setBirimId(int birimId) {
		this.birimId = birimId;
	}
	public void setBirim_Id(int birimId) {
		this.birimId = birimId;
	}
	
	
	
	
	public int getKullaniciId() {
		return kullaniciId;
	}
	public void setKullaniciId(int kullaniciId) {
		this.kullaniciId = kullaniciId;
	}
	public void setKullanici_Id(int kullaniciId) {
		this.kullaniciId = kullaniciId;
	}
	public int getBirimKodu() {
		return birimKodu;
	}
	public void setBirimKodu(int birimKodu) {
		this.birimKodu = birimKodu;
	}
	public String getBirimTipi() {
		return birimTipi;
	}
	public void setBirimTipi(String birimTipi) {
		this.birimTipi = Util.toUpperCase(birimTipi);
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
	
	

}
