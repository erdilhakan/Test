package tr.org.mmo.beans;

import java.io.Serializable;

import btest.*;




public class CaddeSokak implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public CaddeSokak() {
		// TODO Auto-generated constructor stub
	}
	
	public CaddeSokak(Csbm m) {
		this.kod=m.getKod();
				this.ad=m.getAd()==null?"":m.getAd().getValue();
				this.tanitimKodu=m.getTanitimKodu()==null?"":m.getTanitimKodu().getValue();
				this.tip=m.getTip()==null?"":m.getTip().getValue();
				this.mahalleKodu=m.getMahalleKodu()==null?0:m.getMahalleKodu().getValue().intValue();
	}
	
	private int kod;
	private String ad;
	private String tanitimKodu;
	private String tip;
	private int mahalleKodu;
	public int getKod() {
		return kod;
	}
	public void setKod(int kod) {
		this.kod = kod;
	}
	public String getAd() {
		return ad;
	}
	public void setAd(String ad) {
		this.ad = ad;
	}
	
	public String getTanitimKodu() {
		return tanitimKodu;
	}
	public void setTanitimKodu(String tanitimKodu) {
		this.tanitimKodu = tanitimKodu;
	}
	
	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public int getMahalleKodu() {
		return mahalleKodu;
	}
	public void setMahalleKodu(int mahalleKodu) {
		this.mahalleKodu = mahalleKodu;
	}
	


}
