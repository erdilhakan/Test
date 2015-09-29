package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.util.Date;

import tr.org.mmo.asansor.util.Util;



public class BasvuruDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6239376350268325694L;
	
	private int basvuruId;
	private Date basvuruTarihi;
	private long basvuruYapanTCKimlikNo;
	private String basvuruYapanAdi;
	private String basvuruYapanSoyadi;
	private int  basvuruYapanSorumlulukTuru;
	private long binaId;
	private String basvuruDurum;
	private long telefonNo;
	private String telefonNoStr;
	private String ePosta;
	private int telefonNoDahili;
	private String telefonNoDahiliStr;
	
	
	public String getBasvuruYapanAdi() {
		return basvuruYapanAdi;
	}
	public void setBasvuruYapanAdi(String basvuruYapanAdi) {
		this.basvuruYapanAdi = Util.toUpperCase(basvuruYapanAdi);
	}
	public String getBasvuruYapanSoyadi() {
		return basvuruYapanSoyadi;
	}
	public void setBasvuruYapanSoyadi(String basvuruYapanSoyadi) {
		this.basvuruYapanSoyadi = Util.toUpperCase(basvuruYapanSoyadi);
	}
	public long getTelefonNo() {
		return telefonNo;
	}
	public void setTelefonNo(long telefonNo) {
		this.telefonNo = telefonNo;
	}
	public String getTelefonNoStr() {
		return telefonNoStr;
	}
	public void setTelefonNoStr(String telefonNoStr) {
		this.telefonNoStr = telefonNoStr;
	}
	public int getBasvuruId() {
		return basvuruId;
	}
	public void setBasvuruId(int basvuruId) {
		this.basvuruId = basvuruId;
	}
	public Date getBasvuruTarihi() {
		return basvuruTarihi;
	}
	public void setBasvuruTarihi(Date basvuruTarihi) {
		this.basvuruTarihi = basvuruTarihi;
	}
	public long getBasvuruYapanTCKimlikNo() {
		return basvuruYapanTCKimlikNo;
	}
	public void setBasvuruYapanTCKimlikNo(long basvuruYapanTCKimlikNo) {
		this.basvuruYapanTCKimlikNo = basvuruYapanTCKimlikNo;
	}
	
	
	public long getBinaId() {
		return binaId;
	}
	public void setBinaId(long binaId) {
		this.binaId = binaId;
	}
	public String getBasvuruDurum() {
		return basvuruDurum;
	}
	
	public void setBasvuruDurum(String basvuruDurum) {
		this.basvuruDurum = Util.toUpperCase(basvuruDurum);
	}
	public int getBasvuruYapanSorumlulukTuru() {
		return basvuruYapanSorumlulukTuru;
	}
	public void setBasvuruYapanSorumlulukTuru(int basvuruYapanSorumlulukTuru) {
		this.basvuruYapanSorumlulukTuru = basvuruYapanSorumlulukTuru;
	}
	public String getePosta() {
		return ePosta;
	}
	public void setePosta(String ePosta) {
		this.ePosta = ePosta;
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
