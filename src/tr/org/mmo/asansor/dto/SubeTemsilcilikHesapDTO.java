package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class SubeTemsilcilikHesapDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7119627676418576269L;
	private int id;
	private int subeKodu;
	private int temsilcilikKodu;
	private String hesapNo;
	private String iban;
	private String bankaAdi;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSubeKodu() {
		return subeKodu;
	}

	public void setSubeKodu(int subeKodu) {
		this.subeKodu = subeKodu;
	}

	public int getTemsilcilikKodu() {
		return temsilcilikKodu;
	}

	public void setTemsilcilikKodu(int temsilcilikKodu) {
		this.temsilcilikKodu = temsilcilikKodu;
	}

	public String getHesapNo() {
		return hesapNo;
	}

	public void setHesapNo(String hesapNo) {
		this.hesapNo = hesapNo;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getBankaAdi() {
		return bankaAdi;
	}

	public void setBankaAdi(String bankaAdi) {
		this.bankaAdi = bankaAdi;
	}

}
