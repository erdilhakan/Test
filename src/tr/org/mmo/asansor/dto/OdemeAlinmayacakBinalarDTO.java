package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.util.Date;

public class OdemeAlinmayacakBinalarDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5037791529016952809L;
	private int id;
	private int binaId;
	private String aciklama;
	private Date islemTarihi;
	private Date iptalTarihi;
	private String iptalKodu;
	private String islemYapanKullanici;
	private String iptalEdenKullanici;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBinaId() {
		return binaId;
	}
	public void setBinaId(int binaId) {
		this.binaId = binaId;
	}
	public String getAciklama() {
		return aciklama;
	}
	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}
	public Date getIslemTarihi() {
		return islemTarihi;
	}
	public void setIslemTarihi(Date islemTarihi) {
		this.islemTarihi = islemTarihi;
	}
	public Date getIptalTarihi() {
		return iptalTarihi;
	}
	public void setIptalTarihi(Date iptalTarihi) {
		this.iptalTarihi = iptalTarihi;
	}
	public String getIptalKodu() {
		return iptalKodu;
	}
	public void setIptalKodu(String iptalKodu) {
		this.iptalKodu = iptalKodu;
	}
	public String getIslemYapanKullanici() {
		return islemYapanKullanici;
	}
	public void setIslemYapanKullanici(String islemYapanKullanici) {
		this.islemYapanKullanici = islemYapanKullanici;
	}
	public String getIptalEdenKullanici() {
		return iptalEdenKullanici;
	}
	public void setIptalEdenKullanici(String iptalEdenKullanici) {
		this.iptalEdenKullanici = iptalEdenKullanici;
	}
	

}
