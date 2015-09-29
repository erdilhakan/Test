package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.util.Date;

public class OdemeDTO implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4024275336755838844L;
	private int id;
	private int basvuruId;
	private double odemeTutari;
	private boolean odemeKontroldeAlinsin;
	private Date odemeTarihi;
	private double toplamTutar;
	private String aciklama;
	private int odemeSekli;
	private String odemeBelgeNo;
	private String binaAdi;
	private int binaId;
	private double kalan;
	private Date kontrolTarihi;

	public String getAciklama() {
		return aciklama;
	}

	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBasvuruId() {
		return basvuruId;
	}

	public void setBasvuruId(int basvuruId) {
		this.basvuruId = basvuruId;

	}

	public double getOdemeTutari() {
		return odemeTutari;
	}

	public void setOdemeTutari(double odemeTutari) {
		this.odemeTutari = odemeTutari;
	}

	public boolean isOdemeKontroldeAlinsin() {
		return odemeKontroldeAlinsin;
	}

	public void setOdemeKontroldeAlinsin(boolean odemeKontroldeAlinsin) {
		this.odemeKontroldeAlinsin = odemeKontroldeAlinsin;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public int getOdemeSekli() {
		return odemeSekli;
	}

	public void setOdemeSekli(int odemeSekli) {
		this.odemeSekli = odemeSekli;
	}

	public String getOdemeBelgeNo() {
		return odemeBelgeNo;
	}

	public void setOdemeBelgeNo(String odemeBelgeNo) {
		this.odemeBelgeNo = odemeBelgeNo;
	}

	public String getBinaAdi() {
		return binaAdi;
	}

	public void setBinaAdi(String binaAdi) {
		this.binaAdi = binaAdi;
	}

	public int getBinaId() {
		return binaId;
	}

	public void setBinaId(int binaId) {
		this.binaId = binaId;
	}

	public Date getOdemeTarihi() {
		return odemeTarihi;
	}

	public void setOdemeTarihi(Date odemeTarihi) {
		this.odemeTarihi = odemeTarihi;
	}

	public double getKalan() {
		return toplamTutar - odemeTutari;
	}

	public double getToplamTutar() {
		return toplamTutar;
	}

	public void setToplamTutar(double toplamTutar) {
		this.toplamTutar = toplamTutar;
	}

	public Date getKontrolTarihi() {
		return kontrolTarihi;
	}

	public void setKontrolTarihi(Date kontrolTarihi) {
		this.kontrolTarihi = kontrolTarihi;
	}

	
}
