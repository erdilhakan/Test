package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.util.Date;

import tr.org.mmo.asansor.beans_.Common;
import tr.org.mmo.asansor.managedbeans.ApplicationBean;

public class BelediyeSozlesmeDTO implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5160259426312110090L;
	private int belediyeKod = 0;
	private int yil = 0;
	private int cihazTipi;
	private int[] arrCihazTip;
	private Date sozlesmeBaslangicTarihi = null;
	private Date sozlesmeBitisTarihi = null;
	private double fiyat = 0.00;
	private String kapasiteOlcut = "";
	private int kapasite;
	private int id;
	private int sozlesmeBinaTipId;
	private String binaTipStr = "";
	private String kontrolTipi;
	private String[] kontrolTipiArr;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBelediyeKod() {
		return belediyeKod;
	}

	public void setBelediyeKod(int belediyeKod) {
		this.belediyeKod = belediyeKod;
	}

	public int getYil() {
		return yil;
	}

	public void setYil(int yil) {
		this.yil = yil;
	}

	public Date getSozlesmeBaslangicTarihi() {
		return sozlesmeBaslangicTarihi;
	}

	public void setSozlesmeBaslangicTarihi(Date sozlesmeBaslangicTarihi) {
		this.sozlesmeBaslangicTarihi = sozlesmeBaslangicTarihi;
	}

	public Date getSozlesmeBitisTarihi() {
		return sozlesmeBitisTarihi;
	}

	public void setSozlesmeBitisTarihi(Date sozlesmeBitisTarihi) {
		this.sozlesmeBitisTarihi = sozlesmeBitisTarihi;
	}

	public double getFiyat() {
		return fiyat;
	}

	public void setFiyat(double fiyat) {
		this.fiyat = fiyat;
	}

	public String getKapasiteOlcut() {
		return kapasiteOlcut;
	}

	public void setKapasiteOlcut(String kapasiteOlcut) {
		this.kapasiteOlcut = kapasiteOlcut;
	}

	public int getKapasite() {
		return kapasite;
	}

	public void setKapasite(int kapasite) {
		this.kapasite = kapasite;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public int getSozlesmeBinaTipId() {
		return sozlesmeBinaTipId;
	}

	public void setSozlesmeBinaTipId(int sozlesmeBinaTipId) {
		this.sozlesmeBinaTipId = sozlesmeBinaTipId;
		ApplicationBean applicationBean = (ApplicationBean) Common
				.findBean("applicationBean");
		for (SozlesmeBinaTipleriDTO s : applicationBean
				.getSozlesmeBinaTipList()) {
			if (s.getId() == sozlesmeBinaTipId) {
				this.binaTipStr = s.getAciklama();
			}
		}

	}

	public String getBinaTipStr() {
		return binaTipStr;
	}

	public void setBinaTipStr(String binaTipStr) {
		this.binaTipStr = binaTipStr;
	}

	public int getCihazTipi() {
		return cihazTipi;
	}

	public void setCihazTipi(int cihazTipi) {
		this.cihazTipi = cihazTipi;
	}

	public int[] getArrCihazTip() {
		return arrCihazTip;
	}

	public void setArrCihazTip(int[] arrCihazTip) {
		this.arrCihazTip = arrCihazTip;
	}

	public String getKontrolTipi() {
		return kontrolTipi;
	}

	public void setKontrolTipi(String kontrolTipi) {
		this.kontrolTipi = kontrolTipi;
	}

	public String[] getKontrolTipiArr() {
		return kontrolTipiArr;
	}

	public void setKontrolTipiArr(String[] kontrolTipiArr) {
		this.kontrolTipiArr = kontrolTipiArr;
	}
	
	

}
