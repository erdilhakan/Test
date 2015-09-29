package tr.org.mmo.beans;

import java.io.Serializable;




public class Mahalle implements Serializable{

	private static final long serialVersionUID = 1L;

	public Mahalle(){
		
	}
	public Mahalle(btest.Mahalle mahalle){
		this.kod=mahalle.getKod();
		this.ad=mahalle.getAd()==null?"":mahalle.getAd().getValue();
		this.koyKodu=mahalle.getKoyKodu()==null?0:mahalle.getKoyKodu().getValue().intValue();
		this.tanitimKodu=mahalle.getTanitimKodu()==null?0:mahalle.getTanitimKodu().getValue().intValue();
		this.tip=mahalle.getTip()==null?"":mahalle.getTip().getValue();
		this.yetkiliIdareKodu=mahalle.getYetkiliIdareKodu()==null?0:mahalle.getYetkiliIdareKodu().getValue();
	}

	
	private int kod;
	private String ad;
	private int tanitimKodu;
	private String tip;
	private int yetkiliIdareKodu;
	private int koyKodu;
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
	public int getTanitimKodu() {
		return tanitimKodu;
	}
	public void setTanitimKodu(int tanitimKodu) {
		this.tanitimKodu = tanitimKodu;
	}
	
	public int getYetkiliIdareKodu() {
		return yetkiliIdareKodu;
	}
	public void setYetkiliIdareKodu(int yetkiliIdareKodu) {
		this.yetkiliIdareKodu = yetkiliIdareKodu;
	}
	public int getKoyKodu() {
		return koyKodu;
	}
	public void setKoyKodu(int koyKodu) {
		this.koyKodu = koyKodu;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	

	


}
