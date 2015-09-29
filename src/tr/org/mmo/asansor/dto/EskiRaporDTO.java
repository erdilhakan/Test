package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.util.Date;

public class EskiRaporDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String etiket;
	private byte[] dosya;
	private String dosyaAdi;
	private int cihazId;
	private int binaId;
	private int binaTescilNo;
	private int basvuruId;
	private int eskiKontrolKodu;
	private int randevuId;
	private int kontrolId;
	private int raporId;
	private boolean revizyonRapor;
	private Date raporTarih;
	private Date onayTarihi;
	private Date kontrolTarihi;
	private String asansorunYeri;
	private String kontrolTuru;
	private String onayDurumu;
	private boolean raporIptal=false;
	private String asansorUavtEtiket;
	private String binaAdi;
	public int getCihazId() {
		return cihazId;
	}

	public void setCihazId(int cihazId) {
		this.cihazId = cihazId;
	}

	public int getBinaId() {
		return binaId;
	}

	public void setBinaId(int binaId) {
		this.binaId = binaId;
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

	public int getEskiKontrolKodu() {
		return eskiKontrolKodu;
	}

	public void setEskiKontrolKodu(int eskiKontrolKodu) {
		this.eskiKontrolKodu = eskiKontrolKodu;
	}

	public int getRandevuId() {
		return randevuId;
	}

	public void setRandevuId(int randevuId) {
		this.randevuId = randevuId;
	}

	public int getKontrolId() {
		return kontrolId;
	}

	public void setKontrolId(int kontrolId) {
		this.kontrolId = kontrolId;
	}

	public int getRaporId() {
		return raporId;
	}

	public void setRaporId(int raporId) {
		this.raporId = raporId;
	}

	public Date getRaporTarih() {
		return raporTarih;
	}

	public void setRaporTarih(Date raporTarih) {
		this.raporTarih = raporTarih;
	}

	public byte[] getDosya() {
		return dosya;
	}

	public void setDosya(byte[] dosya) {
		this.dosya = dosya;
	}

	public String getDosyaAdi() {
		return dosyaAdi;
	}

	public void setDosyaAdi(String dosyaAdi) {
		this.dosyaAdi = dosyaAdi;
	}

	public String getEtiket() {
		return etiket;
	}

	public void setEtiket(String etiket) {
		this.etiket = etiket;
	}

	public boolean isRevizyonRapor() {
		return revizyonRapor;
	}

	public void setRevizyonRapor(boolean revizyonRapor) {
		this.revizyonRapor = revizyonRapor;
	}

	public Date getOnayTarihi() {
		return onayTarihi;
	}

	public void setOnayTarihi(Date onayTarihi) {
		this.onayTarihi = onayTarihi;
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

	public String getKontrolTuru() {
		return kontrolTuru;
	}

	public void setKontrolTuru(String kontrolTuru) {
		this.kontrolTuru = kontrolTuru;
	}

	public String getOnayDurumu() {
		return onayDurumu;
	}

	public void setOnayDurumu(String onayDurumu) {
		this.onayDurumu = onayDurumu;
	}

	public boolean isRaporIptal() {
		return raporIptal;
	}

	public void setRaporIptal(boolean raporIptal) {
		this.raporIptal = raporIptal;
	}

	public String getAsansorUavtEtiket() {
		return asansorUavtEtiket;
	}

	public void setAsansorUavtEtiket(String asansorUavtEtiket) {
		this.asansorUavtEtiket = asansorUavtEtiket;
	}

	public String getBinaAdi() {
		return binaAdi;
	}

	public void setBinaAdi(String binaAdi) {
		this.binaAdi = binaAdi;
	}
	
	

}
