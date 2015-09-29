package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class BucakKoyDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int bucakKodu;
	private int koyKodu;
	private String koyAdi;
	private String bucakAdi;

	public int getBucakKodu() {
		return bucakKodu;
	}

	public void setBucakKodu(int bucakKodu) {
		this.bucakKodu = bucakKodu;
	}

	public int getKoyKodu() {
		return koyKodu;
	}

	public void setKoyKodu(int koyKodu) {
		this.koyKodu = koyKodu;
	}

	public String getKoyAdi() {
		return koyAdi;
	}

	public void setKoyAdi(String koyAdi) {
		this.koyAdi = koyAdi;
	}

	public String getBucakAdi() {
		return bucakAdi;
	}

	public void setBucakAdi(String bucakAdi) {
		this.bucakAdi = bucakAdi;
	}

}
