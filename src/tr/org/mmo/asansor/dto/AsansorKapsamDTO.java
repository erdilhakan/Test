package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class AsansorKapsamDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8986217758107910524L;
	
	private int id;
	private String kapsamAdi;
	private int cihazTipi;
	private String durum;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getKapsamAdi() {
		return kapsamAdi;
	}
	public void setKapsamAdi(String kapsamAdi) {
		this.kapsamAdi = kapsamAdi;
	}
	public int getCihazTipi() {
		return cihazTipi;
	}
	public void setCihazTipi(int cihazTipi) {
		this.cihazTipi = cihazTipi;
	}
	public String getDurum() {
		return durum;
	}
	public void setDurum(String durum) {
		this.durum = durum;
	}
	

}
