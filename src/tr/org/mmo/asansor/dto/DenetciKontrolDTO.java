package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class DenetciKontrolDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int muhendisSicilNo;
	private String muhendisAdiSoyadi;
	private long elektrikliKontrolAdet;
	private long hidrolikKontrolAdet;
	private long toplam;
	public int getMuhendisSicilNo() {
		return muhendisSicilNo;
	}
	public void setMuhendisSicilNo(int muhendisSicilNo) {
		this.muhendisSicilNo = muhendisSicilNo;
	}
	public String getMuhendisAdiSoyadi() {
		return muhendisAdiSoyadi;
	}
	public void setMuhendisAdiSoyadi(String muhendisAdiSoyadi) {
		this.muhendisAdiSoyadi = muhendisAdiSoyadi;
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
	
	

}
