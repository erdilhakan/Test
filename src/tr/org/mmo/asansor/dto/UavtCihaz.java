package tr.org.mmo.asansor.dto;


import java.util.Date;

public class UavtCihaz {
	private boolean aktif;
	private String asansorEtiket;
	private long asansorNo;
	private int asansorSiraNo;
	private long binaKod;
	private int etiketYil;
	private Date gecerlilikTarih;
	public boolean isAktif() {
		return aktif;
	}
	public void setAktif(boolean aktif) {
		this.aktif = aktif;
	}
	public String getAsansorEtiket() {
		return asansorEtiket;
	}
	public void setAsansorEtiket(String asansorEtiket) {
		this.asansorEtiket = asansorEtiket;
	}
	
	public long getAsansorNo() {
		return asansorNo;
	}
	public void setAsansorNo(long asansorNo) {
		this.asansorNo = asansorNo;
	}
	public int getAsansorSiraNo() {
		return asansorSiraNo;
	}
	public void setAsansorSiraNo(int asansorSiraNo) {
		this.asansorSiraNo = asansorSiraNo;
	}
	public long getBinaKod() {
		return binaKod;
	}
	public void setBinaKod(long binaKod) {
		this.binaKod = binaKod;
	}
	public int getEtiketYil() {
		return etiketYil;
	}
	public void setEtiketYil(int etiketYil) {
		this.etiketYil = etiketYil;
	}
	public Date getGecerlilikTarih() {
		return gecerlilikTarih;
	}
	public void setGecerlilikTarih(Date gecerlilikTarih) {
		this.gecerlilikTarih = gecerlilikTarih;
	}
	
}
