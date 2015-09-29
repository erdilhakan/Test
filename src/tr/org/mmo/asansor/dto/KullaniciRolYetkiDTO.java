package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class KullaniciRolYetkiDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9165017500057104983L;

	private int id;
	private int kullaniciId;
	private int yetkiId;
	private int rolId;
	private String yetkiAdi;
	private String rolAdi;
	private boolean guncelle;
	private boolean sil;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getKullaniciId() {
		return kullaniciId;
	}

	public void setKullaniciId(int kullaniciId) {
		this.kullaniciId = kullaniciId;
	}

	public int getYetkiId() {
		return yetkiId;
	}

	public void setYetkiId(int yetkiId) {
		this.yetkiId = yetkiId;
	}

	public int getRolId() {
		return rolId;
	}

	public void setRolId(int rolId) {
		this.rolId = rolId;
	}

	public String getYetkiAdi() {
		return yetkiAdi;
	}

	public void setYetkiAdi(String yetkiAdi) {
		this.yetkiAdi = yetkiAdi;
	}

	public String getRolAdi() {
		return rolAdi;
	}

	public void setRolAdi(String rolAdi) {
		this.rolAdi = rolAdi;
	}

	public boolean isGuncelle() {
		return guncelle;
	}

	public void setGuncelle(boolean guncelle) {
		this.guncelle = guncelle;
	}

	public boolean isSil() {
		return sil;
	}

	public void setSil(boolean sil) {
		this.sil = sil;
	}

}
