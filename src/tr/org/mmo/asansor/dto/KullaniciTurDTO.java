package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class KullaniciTurDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3976315395102090990L;
	private int id;
	private String tur;
	private String aciklama;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTur() {
		return tur;
	}
	public void setTur(String tur) {
		this.tur = tur;
	}
	public String getAciklama() {
		return aciklama;
	}
	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}
	
	

}
