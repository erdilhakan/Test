package tr.org.mmo.asansor.dto;

import java.io.Serializable;

import tr.org.mmo.asansor.util.Util;

public class IlIlceDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ilKodu;
	private int ilceKodu;
	private String ilAdi;
	private String ilceAdi;
	private double enlem = 0.00;
	private double boylam = 0.00;

	public int getIlKodu() {
		return ilKodu;
	}

	public void setIlKodu(int ilKodu) {
		this.ilKodu = ilKodu;
	}

	public void setIl_Kodu(int ilKodu) {
		this.ilKodu = ilKodu;
	}

	public int getIlceKodu() {
		return ilceKodu;
	}

	public void setIlceKodu(int ilceKodu) {
		this.ilceKodu = ilceKodu;
	}

	public void setIlce_Kodu(int ilceKodu) {
		this.ilceKodu = ilceKodu;
	}

	public String getIlAdi() {
		return ilAdi;
	}

	public void setIlAdi(String ilAdi) {
		this.ilAdi = Util.toUpperCase(ilAdi);
	}

	public void setIl_Adi(String ilAdi) {
		this.ilAdi = Util.toUpperCase(ilAdi);
	}

	public String getIlceAdi() {
		return ilceAdi;
	}

	public void setIlceAdi(String ilceAdi) {
		this.ilceAdi = Util.toUpperCase(ilceAdi);
	}

	public void setIlce_Adi(String ilceAdi) {
		this.ilceAdi = Util.toUpperCase(ilceAdi);
	}

	public double getEnlem() {
		return enlem;
	}

	public void setEnlem(double enlem) {
		this.enlem = enlem;
	}

	public double getBoylam() {
		return boylam;
	}

	public void setBoylam(double boylam) {
		this.boylam = boylam;
	}

}
