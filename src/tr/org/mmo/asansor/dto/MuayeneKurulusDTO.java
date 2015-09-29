package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class MuayeneKurulusDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int kod;
	private String adi;
	private String telefonNo;
	private String ePosta;
	private String faks;
	private String adres;
	public int getKod() {
		return kod;
	}
	public void setKod(int kod) {
		this.kod = kod;
	}
	public String getAdi() {
		return adi;
	}
	public void setAdi(String adi) {
		this.adi = adi;
	}
	public String getTelefonNo() {
		return telefonNo;
	}
	public void setTelefonNo(String telefonNo) {
		this.telefonNo = telefonNo;
	}
	public String getePosta() {
		return ePosta;
	}
	public void setePosta(String ePosta) {
		this.ePosta = ePosta;
	}
	public String getFaks() {
		return faks;
	}
	public void setFaks(String faks) {
		this.faks = faks;
	}
	public String getAdres() {
		return adres;
	}
	public void setAdres(String adres) {
		this.adres = adres;
	}

}
