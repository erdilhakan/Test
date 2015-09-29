package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class BelediyeKontrolDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int belediyeKod;
	private String belediyeAdi;
	private long normalKontrol;
	private long eksiklikKontrol;
	private long elektrikliKontrolAdet;
	private long hidrolikKontrolAdet;
	private long toplam;
	public int getBelediyeKod() {
		return belediyeKod;
	}
	public void setBelediyeKod(int belediyeKod) {
		this.belediyeKod = belediyeKod;
	}
	public String getBelediyeAdi() {
		return belediyeAdi;
	}
	public void setBelediyeAdi(String belediyeAdi) {
		this.belediyeAdi = belediyeAdi;
	}
	public long getElektrikliKontrolAdet() {
		return elektrikliKontrolAdet;
	}
	public void setElektrikliKontrolAdet(long elektrikliKontrolAdet) {
		this.elektrikliKontrolAdet = elektrikliKontrolAdet;
	}
	public long getHidrolikKontrolAdet() {
		return hidrolikKontrolAdet;
	}
	public void setHidrolikKontrolAdet(long hidrolikKontrolAdet) {
		this.hidrolikKontrolAdet = hidrolikKontrolAdet;
	}
	public long getToplam() {
		return toplam;
	}
	public void setToplam(long toplam) {
		this.toplam = toplam;
	}
	public long getNormalKontrol() {
		return normalKontrol;
	}
	public void setNormalKontrol(long normalKontrol) {
		this.normalKontrol = normalKontrol;
	}
	public long getEksiklikKontrol() {
		return eksiklikKontrol;
	}
	public void setEksiklikKontrol(long eksiklikKontrol) {
		this.eksiklikKontrol = eksiklikKontrol;
	}
	
	

}
