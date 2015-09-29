package tr.org.mmo.asansor.beans_;

import java.io.Serializable;

public class RandevuList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -766553501894221664L;

	private int randevuId;
	private String randevuTarihi;
	private String randevuSaati;
	private String unvan;
	private String firmaYetkilisi;
	private long yetkiliTelefon;
	private int basvuruId;
	private String basvuruYapan;
	private double kontrolTutari = 0.00;

	private long telefonNo;

	private String binaAdi;
	private String muhendisler;
	private String cihazlar;

	public int getRandevuId() {
		return randevuId;
	}

	public void setRandevuId(int randevuId) {
		this.randevuId = randevuId;
	}

	public String getUnvan() {
		return unvan;
	}

	public void setUnvan(String unvan) {
		this.unvan = unvan;
	}

	public String getFirmaYetkilisi() {
		return firmaYetkilisi;
	}

	public void setFirmaYetkilisi(String firmaYetkilisi) {
		this.firmaYetkilisi = firmaYetkilisi;
	}

	public long getYetkiliTelefon() {
		return yetkiliTelefon;
	}

	public void setYetkiliTelefon(long yetkiliTelefon) {
		this.yetkiliTelefon = yetkiliTelefon;
	}

	public int getBasvuruId() {
		return basvuruId;
	}

	public void setBasvuruId(int basvuruId) {
		this.basvuruId = basvuruId;
	}

	public String getBasvuruYapan() {
		return basvuruYapan;
	}

	public void setBasvuruYapan(String basvuruYapan) {
		this.basvuruYapan = basvuruYapan;
	}

	public double getKontrolTutari() {
		return kontrolTutari;
	}

	public void setKontrolTutari(double kontrolTutari) {
		this.kontrolTutari = kontrolTutari;
	}

	public long getTelefonNo() {
		return telefonNo;
	}

	public void setTelefonNo(long telefonNo) {
		this.telefonNo = telefonNo;
	}

	public String getBinaAdi() {
		return binaAdi;
	}

	public void setBinaAdi(String binaAdi) {
		this.binaAdi = binaAdi;
	}

	public String getCihazlar() {
		return cihazlar;
	}

	public String getMuhendisler() {
		return muhendisler;
	}

	public void setMuhendisler(String muhendisler) {
		this.muhendisler = muhendisler;
	}

	public void setCihazlar(String cihazlar) {
		this.cihazlar = cihazlar;
	}

	public String getRandevuTarihi() {
		return randevuTarihi;
	}

	public void setRandevuTarihi(String randevuTarihi) {
		this.randevuTarihi = randevuTarihi;
	}

	public String getRandevuSaati() {
		return randevuSaati;
	}

	public void setRandevuSaati(String randevuSaati) {
		this.randevuSaati = randevuSaati;
	}

}
