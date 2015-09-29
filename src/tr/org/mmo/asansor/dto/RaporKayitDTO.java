package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.util.Date;

public class RaporKayitDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int denetimDurumId;
	private int denetimKayitId;
	private int raporId;
	private int cihazId;
	private Date kayitTarihi;
	public int getDenetimDurumId() {
		return denetimDurumId;
	}
	public void setDenetimDurumId(int denetimDurumId) {
		this.denetimDurumId = denetimDurumId;
	}
	public int getDenetimKayitId() {
		return denetimKayitId;
	}
	public void setDenetimKayitId(int denetimKayitId) {
		this.denetimKayitId = denetimKayitId;
	}
	public int getRaporId() {
		return raporId;
	}
	public void setRaporId(int raporId) {
		this.raporId = raporId;
	}
	public int getCihazId() {
		return cihazId;
	}
	public void setCihazId(int cihazId) {
		this.cihazId = cihazId;
	}
	public Date getKayitTarihi() {
		return kayitTarihi;
	}
	public void setKayitTarihi(Date kayitTarihi) {
		this.kayitTarihi = kayitTarihi;
	}
	
	

}
