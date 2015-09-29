package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.util.Date;

public class RaporDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7511512682108921659L;
	private int raporId;
	private int kontrolId;
	private byte[] dosya;
	private String dosyaAdi;
	private Date raporTarihi;
	private Date onayTarihi;
	private String onayDurum;
	private String binaAdi;
	private int onaylayanSicilNo;
	private String etiket;
	private int cihazId;
	private Date kontrolTarihi;
	private boolean raporOnayDisabled = true;
	private String asansorunYeri;
	private int binaTescilNo;
	private int eskiKontrolKodu;
	private int basvuruId;
	private boolean raporIptal=false;
	private RaporKayitDTO raporKayit=new RaporKayitDTO();
	public int getEskiKontrolKodu() {
		return eskiKontrolKodu;
	}

	public void setEskiKontrolKodu(int eskiKontrolKodu) {
		this.eskiKontrolKodu = eskiKontrolKodu;
	}

	public int getRaporId() {
		return raporId;
	}

	public void setRaporId(int raporId) {
		this.raporId = raporId;
	}

	public int getKontrolId() {
		return kontrolId;
	}

	public void setKontrolId(int kontrolId) {
		this.kontrolId = kontrolId;
	}

	public String getDosyaAdi() {
		return dosyaAdi;
	}

	public void setDosyaAdi(String dosyaAdi) {
		this.dosyaAdi = dosyaAdi;
	}

	public Date getRaporTarihi() {
		return raporTarihi;
	}

	public void setRaporTarihi(Date raporTarihi) {
		this.raporTarihi = raporTarihi;
	}

	public Date getOnayTarihi() {
		return onayTarihi;
	}

	public void setOnayTarihi(Date onayTarihi) {
		this.onayTarihi = onayTarihi;
	}

	public String getOnayDurum() {
		return onayDurum;
	}

	public void setOnayDurum(String onayDurum) {
		this.onayDurum = onayDurum;
	}

	public byte[] getDosya() {
		return dosya;
	}

	public void setDosya(byte[] dosya) {
		this.dosya = dosya;
	}

	public boolean isRaporOnayDisabled() {
		return raporOnayDisabled;
	}

	public void setRaporOnayDisabled(boolean raporOnayDisabled) {
		this.raporOnayDisabled = raporOnayDisabled;
	}

	public String getBinaAdi() {
		return binaAdi;
	}

	public void setBinaAdi(String binaAdi) {
		this.binaAdi = binaAdi;
	}

	public int getOnaylayanSicilNo() {
		return onaylayanSicilNo;
	}

	public void setOnaylayanSicilNo(int onaylayanSicilNo) {
		this.onaylayanSicilNo = onaylayanSicilNo;
	}

	public String getEtiket() {
		return etiket;
	}

	public void setEtiket(String etiket) {
		this.etiket = etiket;
	}

	public Date getKontrolTarihi() {
		return kontrolTarihi;
	}

	public void setKontrolTarihi(Date kontrolTarihi) {
		this.kontrolTarihi = kontrolTarihi;
	}

	public String getAsansorunYeri() {
		return asansorunYeri;
	}

	public void setAsansorunYeri(String asansorunYeri) {
		this.asansorunYeri = asansorunYeri;
	}

	public int getBinaTescilNo() {
		return binaTescilNo;
	}

	public void setBinaTescilNo(int binaTescilNo) {
		this.binaTescilNo = binaTescilNo;
	}

	public int getBasvuruId() {
		return basvuruId;
	}

	public void setBasvuruId(int basvuruId) {
		this.basvuruId = basvuruId;
	}

	public RaporKayitDTO getRaporKayit() {
		return raporKayit;
	}

	public void setRaporKayit(RaporKayitDTO raporKayit) {
		this.raporKayit = raporKayit;
	}

	public boolean isRaporIptal() {
		return raporIptal;
	}

	public void setRaporIptal(boolean raporIptal) {
		this.raporIptal = raporIptal;
	}

	public int getCihazId() {
		return cihazId;
	}

	public void setCihazId(int cihazId) {
		this.cihazId = cihazId;
	}

	
	
	

}
