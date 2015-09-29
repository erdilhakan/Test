package tr.org.mmo.asansor.beans_;

import java.io.Serializable;

public class Soru implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2292103252869414613L;
	private Integer soruId;
	private String sorun;
	private String soru;
	private Integer parent;
	private Integer cihazTipi;
	private Integer siraNo;
	private String yildiz;

	
	public Integer getSoruId() {
		return soruId;
	}
	public String getSorun() {
		return sorun;
	}
	public void setSorun(String sorun) {
		this.sorun = sorun;
	}
	public void setSoruId(Integer soruId) {
		this.soruId = soruId;
	}
	public void setSoru_Id(Integer soruId) {
		this.soruId = soruId;
	}
	public String getSoru() {
		return soru;
	}
	public void setSoru(String soru) {
		this.soru = soru;
	}
	public Integer getParent() {
		return parent;
	}
	public void setParent(Integer parent) {
		this.parent = parent;
	}
	public Integer getCihazTipi() {
		return cihazTipi;
	}
	public void setCihazTipi(Integer cihazTipi) {
		this.cihazTipi = cihazTipi;
	}
	public void setCihaz_Tipi(Integer cihazTipi) {
		this.cihazTipi = cihazTipi;
	}
	public Integer getSiraNo() {
		return siraNo;
	}
	public void setSiraNo(Integer siraNo) {
		this.siraNo = siraNo;
	}
	public void setSira_No(Integer siraNo) {
		this.siraNo = siraNo;
	}
	public String getYildiz() {
		return yildiz;
	}
	public void setYildiz(String yildiz) {
		this.yildiz = yildiz;
	}
	public String getAktif() {
		return aktif;
	}
	public void setAktif(String aktif) {
		this.aktif = aktif;
	}
	private String aktif;
	
}
