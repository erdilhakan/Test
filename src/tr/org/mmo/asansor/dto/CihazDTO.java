package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tr.org.mmo.asansor.util.Util;

public class CihazDTO implements Serializable {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + binaId;
		result = prime * result + cihazId;
		result = prime * result + tip;
		result = prime * result
				+ ((tipAciklama == null) ? 0 : tipAciklama.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CihazDTO other = (CihazDTO) obj;
		if (binaId != other.binaId)
			return false;
		if (cihazId != other.cihazId)
			return false;
		if (tip != other.tip)
			return false;
		if (tipAciklama == null) {
			if (other.tipAciklama != null)
				return false;
		} else if (!tipAciklama.equals(other.tipAciklama))
			return false;
		return true;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1444754345637119782L;
	private int cihazId;
	private int tip;
	private int binaId;
	private String kimlikNo;
	private String asansorunYeri;
	private int kapsamId;
	private Date kontrolTarihi;
	private String sonKontrolEtiketi;
	private Date sonKontrolTarihi;
	private String durum="A";
	private long uavtKod;
	private String uavtEtiket; 
	private int uavtSiraNo;
	public String getKimlikNo() {
		return kimlikNo;
	}

	public void setKimlikNo(String kimlikNo) {
		this.kimlikNo = kimlikNo;
	}

	private String tipAciklama;

	private List<CihazTeknikDTO> cihaz = new ArrayList<CihazTeknikDTO>();

	public int getCihazId() {
		return cihazId;
	}

	public void setCihazId(int cihazId) {
		this.cihazId = cihazId;
	}

	public int getTip() {
		return tip;
	}

	public void setTip(int tip) {
		this.tip = tip;
	}

	public int getBinaId() {
		return binaId;
	}

	public void setBinaId(int binaId) {
		this.binaId = binaId;
	}

	public String getTipAciklama() {
		return tipAciklama;
	}

	public void setTipAciklama(String tipAciklama) {
		this.tipAciklama = Util.toUpperCase(tipAciklama);
	}

	public List<CihazTeknikDTO> getCihaz() {
		return cihaz;
	}

	public void setCihaz(List<CihazTeknikDTO> cihaz) {
		this.cihaz = cihaz;
	}

	public String getAsansorunYeri() {
		return asansorunYeri;
	}

	public void setAsansorunYeri(String asansorunYeri) {
		this.asansorunYeri = asansorunYeri;
	}

	public int getKapsamId() {
		return kapsamId;
	}

	public void setKapsamId(int kapsamId) {
		this.kapsamId = kapsamId;
	}

	public Date getKontrolTarihi() {
		return kontrolTarihi;
	}

	public void setKontrolTarihi(Date kontrolTarihi) {
		this.kontrolTarihi = kontrolTarihi;
	}

	public String getSonKontrolEtiketi() {
		return sonKontrolEtiketi;
	}

	public void setSonKontrolEtiketi(String sonKontrolEtiketi) {
		this.sonKontrolEtiketi = sonKontrolEtiketi;
	}

	public Date getSonKontrolTarihi() {
		return sonKontrolTarihi;
	}

	public void setSonKontrolTarihi(Date sonKontrolTarihi) {
		this.sonKontrolTarihi = sonKontrolTarihi;
	}

	public String getDurum() {
		return durum;
	}

	public void setDurum(String durum) {
		this.durum = durum;
	}

	

	

	

	

	public long getUavtKod() {
		return uavtKod;
	}

	public void setUavtKod(long uavtKod) {
		this.uavtKod = uavtKod;
	}

	public int getUavtSiraNo() {
		return uavtSiraNo;
	}

	public void setUavtSiraNo(int uavtSiraNo) {
		this.uavtSiraNo = uavtSiraNo;
	}

	public String getUavtEtiket() {
		return uavtEtiket;
	}

	public void setUavtEtiket(String uavtEtiket) {
		this.uavtEtiket = uavtEtiket;
	}

	

	
	
}
