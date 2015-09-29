package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.faces.context.FacesContext;

import tr.org.mmo.asansor.managedbeans.BasvuruBean;
import tr.org.mmo.asansor.util.Util;

public class BasvuruAsansorDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7931080779899184702L;
	private int basvuruId;
	private int id;
	private int cihazId;
	private int cihazTip;
	private String tipAciklama;
	private String kimlikNo;
	private double kontrolTutari;
	private String kontrolTuru;
	private String durakKapasite;
	private String agirlikKapasite;
	private int kapasiteDurak;
	private int kapasiteAgirlik;
	private String asansorunYeri;
	private Date kontrolTarihi;
	private int bakimciFirmaId;
	private String bakimciFirmaAdi;
	private String sonKontrolEtiketi;
	private String bakimciFirmaHYBNo;
	private Date bakimciFirmaHYBGecerlilikTarihi;	
	private boolean kontrolIptal=false;

	public int getKapasiteDurak() {
		return kapasiteDurak;
	}

	public void setKapasiteDurak(int kapasiteDurak) {
		this.kapasiteDurak = kapasiteDurak;
	}

	public int getKapasiteAgirlik() {
		return kapasiteAgirlik;
	}

	public void setKapasiteAgirlik(int kapasiteAgirlik) {
		this.kapasiteAgirlik = kapasiteAgirlik;
	}

	public int getBasvuruId() {
		return basvuruId;
	}

	public void setBasvuruId(int basvuruId) {
		this.basvuruId = basvuruId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCihazId() {
		return cihazId;
	}

	public void setCihazId(int cihazId) {
		this.cihazId = cihazId;
	}

	public int getCihazTip() {
		return cihazTip;
	}

	public void setCihazTip(int cihazTip) {
		this.cihazTip = cihazTip;
	}

	public String getTipAciklama() {
		return tipAciklama;
	}

	public void setTipAciklama(String tipAciklama) {
		this.tipAciklama = Util.toUpperCase(tipAciklama);
	}

	public String getKimlikNo() {
		return kimlikNo;
	}

	public void setKimlikNo(String kimlikNo) {
		this.kimlikNo = kimlikNo;
	}

	public double getKontrolTutari() {
		return kontrolTutari;
	}

	public void setKontrolTutari(double kontrolTutari) {
		this.kontrolTutari = kontrolTutari;
	}

	public String getAsansorunYeri() {
		return asansorunYeri;
	}

	public void setAsansorunYeri(String asansorunYeri) {
		this.asansorunYeri = asansorunYeri;
	}

	public Date getKontrolTarihi() {
		return kontrolTarihi;
	}

	public void setKontrolTarihi(Date kontrolTarihi) {
		this.kontrolTarihi = kontrolTarihi;
	}

	public int getBakimciFirmaId() {
		return bakimciFirmaId;
	}

	public void setBakimciFirmaId(int bakimciFirmaId) {
		this.bakimciFirmaId = bakimciFirmaId;

		BasvuruBean basvuruBean = (BasvuruBean) FacesContext
				.getCurrentInstance()
				.getApplication()
				.evaluateExpressionGet(FacesContext.getCurrentInstance(),
						"#{basvuruBean}", BasvuruBean.class);
		if (basvuruBean != null && basvuruBean.getFirmalar() != null
				&& basvuruBean.getFirmalar().size() > 0) {
			this.bakimciFirmaAdi = "";
			if (bakimciFirmaId == 999999) {
				this.bakimciFirmaAdi = "Bina Yönetiminin yetki belgeli asansör bakım firmasıyla sözleşmesi bulunmamaktadır !";
			} else {
				Set<String> set = basvuruBean.getFirmalar().keySet();
				Iterator<String> iter = set.iterator();
				while (iter.hasNext()) {
					String s = iter.next();
					if (basvuruBean.getFirmalar().get(s) == bakimciFirmaId) {
						this.bakimciFirmaAdi = s;
						break;
					}
				}
			}

		}

	}

	public String getBakimciFirmaAdi() {
		return bakimciFirmaAdi;
	}

	public void setBakimciFirmaAdi(String bakimciFirmaAdi) {
		this.bakimciFirmaAdi = bakimciFirmaAdi;
	}

	public String getSonKontrolEtiketi() {
		return sonKontrolEtiketi;
	}

	public void setSonKontrolEtiketi(String sonKontrolEtiketi) {
		this.sonKontrolEtiketi = sonKontrolEtiketi;
	}

	public String getKontrolTuru() {
		return kontrolTuru;
	}

	public void setKontrolTuru(String kontrolTuru) {
		this.kontrolTuru = kontrolTuru;
	}

	public String getDurakKapasite() {
		return durakKapasite;
	}

	public void setDurakKapasite(String durakKapasite) {
		this.durakKapasite = durakKapasite;
	}

	public String getAgirlikKapasite() {
		return agirlikKapasite;
	}

	public void setAgirlikKapasite(String agirlikKapasite) {
		this.agirlikKapasite = agirlikKapasite;
	}

	public String getBakimciFirmaHYBNo() {
		return bakimciFirmaHYBNo;
	}

	public void setBakimciFirmaHYBNo(String bakimciFirmaHYBNo) {
		this.bakimciFirmaHYBNo = bakimciFirmaHYBNo;
	}

	public Date getBakimciFirmaHYBGecerlilikTarihi() {
		return bakimciFirmaHYBGecerlilikTarihi;
	}

	public void setBakimciFirmaHYBGecerlilikTarihi(
			Date bakimciFirmaHYBGecerlilikTarihi) {
		this.bakimciFirmaHYBGecerlilikTarihi = bakimciFirmaHYBGecerlilikTarihi;
	}

	public boolean isKontrolIptal() {
		return kontrolIptal;
	}

	public void setKontrolIptal(boolean kontrolIptal) {
		this.kontrolIptal = kontrolIptal;
	}
	
	

}
