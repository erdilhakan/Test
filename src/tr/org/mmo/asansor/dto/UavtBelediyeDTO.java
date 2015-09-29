package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class UavtBelediyeDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int kod;
	private String belediyeAdi;
	private String prmBelediyeTurKod;
	private int ilKod;
	private int ilceKod;
	private int koyKod;
	private int ustBelediyeKod;
	public int getKod() {
		return kod;
	}
	public void setKod(int kod) {
		this.kod = kod;
	}
	public String getBelediyeAdi() {
		return belediyeAdi;
	}
	public void setBelediyeAdi(String belediyeAdi) {
		this.belediyeAdi = belediyeAdi;
	}
	public String getPrmBelediyeTurKod() {
		return prmBelediyeTurKod;
	}
	public void setPrmBelediyeTurKod(String prmBelediyeTurKod) {
		this.prmBelediyeTurKod = prmBelediyeTurKod;
	}
	public int getIlKod() {
		return ilKod;
	}
	public void setIlKod(int ilKod) {
		this.ilKod = ilKod;
	}
	public int getIlceKod() {
		return ilceKod;
	}
	public void setIlceKod(int ilceKod) {
		this.ilceKod = ilceKod;
	}
	public int getKoyKod() {
		return koyKod;
	}
	public void setKoyKod(int koyKod) {
		this.koyKod = koyKod;
	}
	public int getUstBelediyeKod() {
		return ustBelediyeKod;
	}
	public void setUstBelediyeKod(int ustBelediyeKod) {
		this.ustBelediyeKod = ustBelediyeKod;
	}
	

}
