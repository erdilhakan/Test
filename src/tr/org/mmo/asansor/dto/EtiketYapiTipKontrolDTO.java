package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class EtiketYapiTipKontrolDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long elektrikliKontrolAdet;
	private long hidrolikKontrolAdet;
	private long asansorTipToplam;
	private int belediyeKod;
	private String belediyeAdi;
	private int yapiTipi;
	private String yapiTipiAdi;
	private String etiket;
	private int cihazTipId;
	private long kirmiziEtiketAdet;
	private long yesilEtiketAdet;
	private long sariEtiketAdet;
	private long maviEtiketAdet;
	private long digerEtiketAdet;
	private long etiketToplamAdet;
	private long binaAdet;
	private long kamuBinasiAdet;
	private long konutAdet;
	private long isyeriAdet;
	private long digerBinaTipiAdet;
	private String kontrolTuru;
	private int normalKontrolAdet;
	private int eksiklikKontrolAdet;
	private double toplamTutar;
	private double kontrolTutari;
	private double odenenTutar;
	private double odenenToplamTutar;
	private double odenmeyenTutar;
	private double odenmeyenToplamTutar;
	
	public long getElektrikliKontrolAdet() {
		return elektrikliKontrolAdet;
	}
	public void setElektrikliKontrolAdet(long elektrikliKontrolAdet) {
		this.elektrikliKontrolAdet = elektrikliKontrolAdet;
	}
	public long getHidrolikKontrolAdet() {
		return hidrolikKontrolAdet;
	}
	public void setHidrolikKontrolAdet(long hidrolikKontrolAdet) {
		this.hidrolikKontrolAdet = hidrolikKontrolAdet;
	}
	public long getAsansorTipToplam() {
		return asansorTipToplam;
	}
	public void setAsansorTipToplam(long asansorTipToplam) {
		this.asansorTipToplam = asansorTipToplam;
	}
	public int getBelediyeKod() {
		return belediyeKod;
	}
	public void setBelediyeKod(int belediyeKod) {
		this.belediyeKod = belediyeKod;
	}
	public String getBelediyeAdi() {
		return belediyeAdi;
	}
	public void setBelediyeAdi(String belediyeAdi) {
		this.belediyeAdi = belediyeAdi;
	}
	public int getYapiTipi() {
		return yapiTipi;
	}
	public void setYapiTipi(int yapiTipi) {
		this.yapiTipi = yapiTipi;
	}
	public String getYapiTipiAdi() {
		return yapiTipiAdi;
	}
	public void setYapiTipiAdi(String yapiTipiAdi) {
		this.yapiTipiAdi = yapiTipiAdi;
	}
	public String getEtiket() {
		return etiket;
	}
	public void setEtiket(String etiket) {
		this.etiket = etiket;
	}
	public int getCihazTipId() {
		return cihazTipId;
	}
	public void setCihazTipId(int cihazTipId) {
		this.cihazTipId = cihazTipId;
	}
	public long getKirmiziEtiketAdet() {
		return kirmiziEtiketAdet;
	}
	public void setKirmiziEtiketAdet(long kirmiziEtiketAdet) {
		this.kirmiziEtiketAdet = kirmiziEtiketAdet;
	}
	public long getYesilEtiketAdet() {
		return yesilEtiketAdet;
	}
	public void setYesilEtiketAdet(long yesilEtiketAdet) {
		this.yesilEtiketAdet = yesilEtiketAdet;
	}
	public long getSariEtiketAdet() {
		return sariEtiketAdet;
	}
	public void setSariEtiketAdet(long sariEtiketAdet) {
		this.sariEtiketAdet = sariEtiketAdet;
	}
	public long getMaviEtiketAdet() {
		return maviEtiketAdet;
	}
	public void setMaviEtiketAdet(long maviEtiketAdet) {
		this.maviEtiketAdet = maviEtiketAdet;
	}
	public long getDigerEtiketAdet() {
		return digerEtiketAdet;
	}
	public void setDigerEtiketAdet(long digerEtiketAdet) {
		this.digerEtiketAdet = digerEtiketAdet;
	}
	public long getEtiketToplamAdet() {
		return etiketToplamAdet;
	}
	public void setEtiketToplamAdet(long etiketToplamAdet) {
		this.etiketToplamAdet = etiketToplamAdet;
	}
	public long getBinaAdet() {
		return binaAdet;
	}
	public void setBinaAdet(long binaAdet) {
		this.binaAdet = binaAdet;
	}
	public long getKamuBinasiAdet() {
		return kamuBinasiAdet;
	}
	public void setKamuBinasiAdet(long kamuBinasiAdet) {
		this.kamuBinasiAdet = kamuBinasiAdet;
	}
	public long getKonutAdet() {
		return konutAdet;
	}
	public void setKonutAdet(long konutAdet) {
		this.konutAdet = konutAdet;
	}
	public long getIsyeriAdet() {
		return isyeriAdet;
	}
	public void setIsyeriAdet(long isyeriAdet) {
		this.isyeriAdet = isyeriAdet;
	}
	public long getDigerBinaTipiAdet() {
		return digerBinaTipiAdet;
	}
	public void setDigerBinaTipiAdet(long digerBinaTipiAdet) {
		this.digerBinaTipiAdet = digerBinaTipiAdet;
	}
	public String getKontrolTuru() {
		return kontrolTuru;
	}
	public void setKontrolTuru(String kontrolTuru) {
		this.kontrolTuru = kontrolTuru;
	}
	public int getNormalKontrolAdet() {
		return normalKontrolAdet;
	}
	public void setNormalKontrolAdet(int normalKontrolAdet) {
		this.normalKontrolAdet = normalKontrolAdet;
	}
	public int getEksiklikKontrolAdet() {
		return eksiklikKontrolAdet;
	}
	public void setEksiklikKontrolAdet(int eksiklikKontrolAdet) {
		this.eksiklikKontrolAdet = eksiklikKontrolAdet;
	}
	public double getToplamTutar() {
		return toplamTutar;
	}
	public void setToplamTutar(double toplamTutar) {
		this.toplamTutar = toplamTutar;
	}
	public double getKontrolTutari() {
		return kontrolTutari;
	}
	public void setKontrolTutari(double kontrolTutari) {
		this.kontrolTutari = kontrolTutari;
	}
	public double getOdenenTutar() {
		return odenenTutar;
	}
	public void setOdenenTutar(double odenenTutar) {
		this.odenenTutar = odenenTutar;
	}
	public double getOdenenToplamTutar() {
		return odenenToplamTutar;
	}
	public void setOdenenToplamTutar(double odenenToplamTutar) {
		this.odenenToplamTutar = odenenToplamTutar;
	}
	public double getOdenmeyenTutar() {
		return odenmeyenTutar;
	}
	public void setOdenmeyenTutar(double odenmeyenTutar) {
		this.odenmeyenTutar = odenmeyenTutar;
	}
	public double getOdenmeyenToplamTutar() {
		return odenmeyenToplamTutar;
	}
	public void setOdenmeyenToplamTutar(double odenmeyenToplamTutar) {
		this.odenmeyenToplamTutar = odenmeyenToplamTutar;
	}

}
