package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.util.TreeMap;

import tr.org.mmo.asansor.util.CihazTeknikOzellikleriComparator;
import tr.org.mmo.asansor.util.Util;

public class CihazTeknikDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8638153897810328106L;
	private int kod;
	private int sira;
	private String baslik;
	private int degerId;
	private String tipi;
	private String deger;
	private String birim;
	private String cevap;
	private int cevapId;
	private String[] ozelAsansorSartlar;
	private boolean zorunluAlan = false;

	public int getCevapId() {
		return cevapId;
	}

	public void setCevapId(int cevapId) {
		this.cevapId = cevapId;
	}

	private TreeMap<String, String> item = new TreeMap<String, String>(
			new CihazTeknikOzellikleriComparator());

	public TreeMap<String, String> getItem() {
		return item;
	}

	public void setItem(TreeMap<String, String> item) {
		this.item = item;
	}

	public String getCevap() {
		return cevap;
	}

	public void setCevap(String cevap) {
		this.cevap = Util.toUpperCase(cevap);
	}

	public int getKod() {
		return kod;
	}

	public void setKod(int kod) {
		this.kod = kod;
	}

	public int getSira() {
		return sira;
	}

	public void setSira(int sira) {
		this.sira = sira;
	}

	public String getBaslik() {
		return baslik;
	}

	public void setBaslik(String baslik) {
		this.baslik = Util.toUpperCase(baslik);
	}

	public int getDegerId() {
		return degerId;
	}

	public void setDegerId(int degerId) {
		this.degerId = degerId;
	}

	public String getTipi() {
		return tipi;
	}

	public void setTipi(String tipi) {
		this.tipi = tipi;
	}

	public String getDeger() {
		return deger;
	}

	public void setDeger(String deger) {
		this.deger = Util.toUpperCase(deger);
	}

	public String getBirim() {
		return birim;
	}

	public void setBirim(String birim) {
		this.birim = Util.toUpperCase(birim);
	}

	public boolean isZorunluAlan() {
		return zorunluAlan;
	}

	public void setZorunluAlan(boolean zorunluAlan) {
		this.zorunluAlan = zorunluAlan;
	}

	public String[] getOzelAsansorSartlar() {
		return ozelAsansorSartlar;
	}

	public void setOzelAsansorSartlar(String[] ozelAsansorSartlar) {
		this.ozelAsansorSartlar = ozelAsansorSartlar;
	}
	
	

}
