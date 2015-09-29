package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class RolYetkiDTO implements Serializable {

	/**
	 * ROL ve YETKİ tablosu için birlikte kullanılıyor
	 */
	private static final long serialVersionUID = -4690016876553366398L;

	private int id;
	private String adi;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

}
