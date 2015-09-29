package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.util.Date;

public class KontrolHaberDTO implements Serializable, Cloneable {

	/**
		 * 
		 */
	private static final long serialVersionUID = -7242306286547282803L;
	private int binaId;
	private int il;
	private String ilStr;
	private int ilce;
	private String ilceStr;
	private String mahalle;
	private String caddeSokak;
	private String binaAdi;
	private String binaKisi;
	private long telefonNo;
	private String telefonNoStr;
	private long telefonNoGsm;
	private String telefonNoGsmStr;
	private Date sonKontrolTarihi;
	private Integer cihazId;
	private String etiket;
	private String cihazKimlikNo;

	public String getCihazKimlikNo() {
		return cihazKimlikNo;
	}

	public void setCihazKimlikNo(String cihazKimlikNo) {
		this.cihazKimlikNo = cihazKimlikNo;
	}

	public String getEtiket() {
		return etiket;
	}

	public void setEtiket(String etiket) {
		this.etiket = etiket;
	}

	public Integer getCihazId() {
		return cihazId;
	}

	public void setCihazId(Integer cihazId) {
		this.cihazId = cihazId;
	}

	public Date getSonKontrolTarihi() {
		return sonKontrolTarihi;
	}

	public void setSonKontrolTarihi(Date sonKontrolTarihi) {
		this.sonKontrolTarihi = sonKontrolTarihi;
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

	public String getIlStr() {
		return ilStr;
	}

	public void setIlStr(String ilStr) {
		this.ilStr = ilStr;
	}

	public String getIlceStr() {
		return ilceStr;
	}

	public void setIlceStr(String ilceStr) {
		this.ilceStr = ilceStr;
	}

	public String getMahalle() {
		return mahalle;
	}

	public void setMahalle(String mahalle) {
		this.mahalle = mahalle;
	}

	public String getCaddeSokak() {
		return caddeSokak;
	}

	public void setCaddeSokak(String caddeSokak) {
		this.caddeSokak = caddeSokak;
	}

	public int getBinaId() {
		return binaId;
	}

	public void setBinaId(int binaId) {
		this.binaId = binaId;

	}

	public void setBina_Id(int binaId) {
		this.binaId = binaId;
	}

	public String getBinaAdi() {
		return binaAdi;
	}

	public void setBinaAdi(String binaAdi) {
		this.binaAdi = binaAdi;
	}

	public String getBinaKisi() {
		return binaKisi;
	}

	public void setBinaKisi(String binaKisi) {
		this.binaKisi = binaKisi;
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

	public long getTelefonNoGsm() {
		return telefonNoGsm;
	}

	public void setTelefonNoGsm(long telefonNoGsm) {
		this.telefonNoGsm = telefonNoGsm;
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

}
