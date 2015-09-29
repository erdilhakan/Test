package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.util.Date;

import tr.org.mmo.asansor.util.Util;

public class BinaKisiDTO implements Serializable,Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -153059460228979471L;
	private int id;
	private Date kayitTarihi;
	private int binaId;
	private long tcKimlikNo;
	private String adi;
	private String soyadi;
	private short sorumlulukTuru;
	private long telefonNo;
	private int telefonNoDahili;
	private String telefonNoDahiliStr;
	private String telefonNoStr;
	
	private long telefonNoGsm;
	private String telefonNoGsmStr;
	private String ePosta;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getKayitTarihi() {
		return kayitTarihi;
	}
	public void setKayitTarihi(Date kayitTarihi) {
		this.kayitTarihi = kayitTarihi;
	}
	public int getBinaId() {
		return binaId;
	}
	public void setBinaId(int binaId) {
		this.binaId = binaId;
	}
	public long getTcKimlikNo() {
		return tcKimlikNo;
	}
	public void setTcKimlikNo(long tcKimlikNo) {
		this.tcKimlikNo = tcKimlikNo;
	}
	public String getAdi() {
		return adi;
	}
	public void setAdi(String adi) {
		this.adi = Util.toUpperCase(adi);
	}
	public String getSoyadi() {
		return soyadi;
	}
	public void setSoyadi(String soyadi) {
		this.soyadi = Util.toUpperCase(soyadi);
	}
	
	public short getSorumlulukTuru() {
		return sorumlulukTuru;
	}
	public void setSorumlulukTuru(short sorumlulukTuru) {
		this.sorumlulukTuru = sorumlulukTuru;
	}
	public long getTelefonNo() {
		return telefonNo;
	}
	public void setTelefonNo(long telefonNo) {
		this.telefonNo = telefonNo;
	}
	public long getTelefonNoGsm() {
		return telefonNoGsm;
	}
	public void setTelefonNoGsm(long telefonNoGsm) {
		this.telefonNoGsm = telefonNoGsm;
	}
	public String getePosta() {
		return ePosta;
	}
	public void setePosta(String ePosta) {
		this.ePosta = ePosta;
	}
	public String getTelefonNoStr() {
		return telefonNoStr;
	}
	public void setTelefonNoStr(String telefonNoStr) {
		this.telefonNoStr = telefonNoStr;
	}
	public String getTelefonNoGsmStr() {
		return telefonNoGsmStr;
	}
	public void setTelefonNoGsmStr(String telefonNoGsmStr) {
		this.telefonNoGsmStr = telefonNoGsmStr;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
	return super.clone();
	}
	public int getTelefonNoDahili() {
		return telefonNoDahili;
	}
	public void setTelefonNoDahili(int telefonNoDahili) {
		this.telefonNoDahili = telefonNoDahili;
		try{
			if (telefonNoDahili==0){
				this.telefonNoDahiliStr="";
			}else{
				this.telefonNoDahiliStr=String.valueOf(telefonNoDahili).trim();
			}
			
		}catch(Exception e){
			this.telefonNoDahiliStr="";
		}
	}
	public String getTelefonNoDahiliStr() {
		return telefonNoDahiliStr;
	}
	public void setTelefonNoDahiliStr(String telefonNoDahiliStr) {
		this.telefonNoDahiliStr = telefonNoDahiliStr;
		try{
			if (telefonNoDahiliStr==null){
				this.telefonNoDahili=0;
			}else{
				this.telefonNoDahili=Integer.parseInt(telefonNoDahiliStr.trim());
			}
			
		}catch(Exception e){
			this.telefonNoDahili=0;
		}
	}
	
	
}
