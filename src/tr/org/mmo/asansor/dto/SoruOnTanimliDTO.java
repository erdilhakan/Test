package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class SoruOnTanimliDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7564033424047239149L;

	private int id;

	private String aciklama;
	private int soruId;
	private int style;
	private String durum="A";

	public String getDurum() {
		return durum;
	}

	public void setDurum(String durum) {
		this.durum = durum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAciklama() {
		return aciklama;
	}

	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
		if (aciklama.contains("**")) {
			style = 2;
		} else if (aciklama.contains("*")) {
			style = 1;
		} else {
			style = 0;
		}
	}

	public int getSoruId() {
		return soruId;
	}

	public void setSoruId(int soruId) {
		this.soruId = soruId;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

}
