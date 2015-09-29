package tr.org.mmo.asansor.beans_;

import java.io.Serializable;
import java.util.Date;

public class Duyuru implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Date tarih;
	private String konu;
	private String detay;
	
	private boolean yeniMi=false;
	
	public String getDetay() {
		return detay;
	}
	public void setDetay(String detay) {
		this.detay = detay;
	}
	public boolean isYeniMi() {
		return yeniMi;
	}
	public void setYeniMi(boolean yeniMi) {
		this.yeniMi = yeniMi;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getTarih() {
		return tarih;
	}
	public void setTarih(Date tarih) {
		this.tarih = tarih;
	}
	public String getKonu() {
		return konu;
	}
	public void setKonu(String konu) {
		this.konu = konu;
	}
	
	

}
