package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class KullaniciSorguBirimleriDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1142755065396453961L;
	
	private String kullaniciAdi;
	private int temsilcilik;
	private int il;
	private int ilce;
	public String getKullaniciAdi() {
		return kullaniciAdi;
	}
	public void setKullaniciAdi(String kullaniciAdi) {
		this.kullaniciAdi = kullaniciAdi;
	}
	public int getTemsilcilik() {
		return temsilcilik;
	}
	public void setTemsilcilik(int temsilcilik) {
		this.temsilcilik = temsilcilik;
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
