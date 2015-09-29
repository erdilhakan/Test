package tr.org.mmo.asansor.dto;

import java.io.Serializable;

import tr.org.mmo.asansor.util.Util;

public class BelediyeDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8765660223138891349L;
	private int kod;
	private String adi;
	
	private int temsilcilik;
	private int sube;
	private int il;
	private int ilce;
	private String ilStr;
	private String ilceStr;
	private int uavtKod=0;
	
	public String getIlStr() {
		return ilStr;
	}
	public void setIlStr(String ilStr) {
		this.ilStr = Util.toUpperCase(ilStr);
	}
	public String getIlceStr() {
		return ilceStr;
	}
	public void setIlceStr(String ilceStr) {
		this.ilceStr = Util.toUpperCase(ilceStr);
	}
	public int getKod() {
		return kod;
	}
	public void setKod(int kod) {
		this.kod = kod;
	}
	public String getAdi() {
		return adi;
	}
	public void setAdi(String adi) {
		this.adi = Util.toUpperCase(adi);
	}
	
	public int getTemsilcilik() {
		return temsilcilik;
	}
	public void setTemsilcilik(int temsilcilik) {
		this.temsilcilik = temsilcilik;
	}
	public int getSube() {
		return sube;
	}
	public void setSube(int sube) {
		this.sube = sube;
	}
	public int getIl() {
		return il;
	}
	public void setIl(int il) {
		this.il = il;
		
		
	}
	public int getIlce() {
		return ilce;
	}
	public void setIlce(int ilce) {
		this.ilce = ilce;
	}
	public int getUavtKod() {
		return uavtKod;
	}
	public void setUavtKod(int uavtKod) {
		this.uavtKod = uavtKod;
	}
	
	
	
}
