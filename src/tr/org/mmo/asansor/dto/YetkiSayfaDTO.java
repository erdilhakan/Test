package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class YetkiSayfaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private int yetkiId;
	private String sayfaAdi;
	private int sayfaId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getYetkiId() {
		return yetkiId;
	}

	public void setYetkiId(int yetkiId) {
		this.yetkiId = yetkiId;
	}

	public String getSayfaAdi() {
		return sayfaAdi;
	}

	public void setSayfaAdi(String sayfaAdi) {
		this.sayfaAdi = sayfaAdi;
	}

	public int getSayfaId() {
		return sayfaId;
	}

	public void setSayfaId(int sayfaId) {
		this.sayfaId = sayfaId;
	}

}
