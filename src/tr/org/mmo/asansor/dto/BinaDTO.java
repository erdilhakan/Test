package tr.org.mmo.asansor.dto;

import java.io.Serializable;

import tr.org.mmo.asansor.util.Util;

public class BinaDTO implements Serializable, Cloneable {

	/**
		 * 
		 */
	private static final long serialVersionUID = -7242306286547282803L;
	private int binaId;
	private short il;
	private short ilce;
	private String ilAdi;
	private String ilceAdi;
	private String vergiNo;
	private String belediyeAdi;
	private String yapiKonusuStr;
	private int sozlesmeBinaTipId;
	private int tescilNo;
	private String acikAdres;
	private double enlem;
	private double boylam;
	private int uavtKod = 0;
	private int bucakKoyKod = 0;
	private int firma=0;

	public String getBelediyeAdi() {
		return belediyeAdi;
	}

	public void setBelediyeAdi(String belediyeAdi) {
		this.belediyeAdi = belediyeAdi;
	}

	private String vergiDairesi;

	public String getIlAdi() {
		return ilAdi;
	}

	public void setIlAdi(String ilAdi) {
		this.ilAdi = Util.toUpperCase(ilAdi);
	}

	public String getIlceAdi() {
		return ilceAdi;
	}

	public void setIlceAdi(String ilceAdi) {
		this.ilceAdi = Util.toUpperCase(ilceAdi);
	}

	private String mahalle;
	private String caddeSokak;
	private String binaNo;
	private String binaAdi;
	private int belediye;
	private String ada;
	private String parsel;
	private String pafta;
	private int yapiTip;

	public int getYapiTip() {
		return yapiTip;
	}

	public void setYapiTip(int yapiTip) {
		this.yapiTip = yapiTip;
	}

	public int getBinaId() {

		return binaId;
	}

	public void setBinaId(int binaId) {
		this.binaId = binaId;

	}

	public void setBina_Id(int binaId) {
		this.binaId = binaId;
	}

	public short getIl() {
		return il;
	}

	public void setIl(short il) {
		this.il = il;
	}

	public short getIlce() {
		return ilce;
	}

	public void setIlce(short ilce) {
		this.ilce = ilce;
	}

	public String getMahalle() {
		return mahalle;
	}

	public void setMahalle(String mahalle) {
		this.mahalle = Util.toUpperCase(mahalle);
	}

	public String getCaddeSokak() {
		return caddeSokak;
	}

	public void setCaddeSokak(String caddeSokak) {
		this.caddeSokak = Util.toUpperCase(caddeSokak);
	}

	public void setCadde_Sokak(String caddeSokak) {
		this.caddeSokak = Util.toUpperCase(caddeSokak);
	}

	public String getBinaNo() {
		return binaNo;
	}

	public void setBinaNo(String binaNo) {
		this.binaNo = binaNo;
	}

	public void setBina_No(String binaNo) {
		this.binaNo = Util.toUpperCase(binaNo);
	}

	public String getBinaAdi() {
		return binaAdi;
	}

	public void setBinaAdi(String binaAdi) {
		this.binaAdi = Util.toUpperCase(binaAdi);
	}

	public void setBina_Adi(String binaAdi) {
		this.binaAdi = Util.toUpperCase(binaAdi);
	}

	public String getAda() {
		return ada;
	}

	public void setAda(String ada) {
		this.ada = Util.toUpperCase(ada);
	}

	public String getParsel() {
		return parsel;
	}

	public int getBelediye() {
		return belediye;
	}

	public void setBelediye(int belediye) {
		this.belediye = belediye;

	}

	public void setParsel(String parsel) {
		this.parsel = Util.toUpperCase(parsel);
	}

	public String getPafta() {
		return pafta;
	}

	public void setPafta(String pafta) {
		this.pafta = Util.toUpperCase(pafta);
	}

	public String getVergiNo() {
		return vergiNo;
	}

	public void setVergiNo(String vergiNo) {
		this.vergiNo = Util.toUpperCase(vergiNo);
	}

	public String getVergiDairesi() {
		return vergiDairesi;
	}

	public int getSozlesmeBinaTipId() {
		return sozlesmeBinaTipId;
	}

	public void setSozlesmeBinaTipId(int sozlesmeBinaTipId) {
		this.sozlesmeBinaTipId = sozlesmeBinaTipId;
	}

	public void setVergiDairesi(String vergiDairesi) {
		this.vergiDairesi = Util.toUpperCase(vergiDairesi);
	}

	public String getYapiKonusuStr() {
		return yapiKonusuStr;
	}

	public void setYapiKonusuStr(String yapiKonusuStr) {
		this.yapiKonusuStr = yapiKonusuStr;
	}

	public int getTescilNo() {
		return tescilNo;
	}

	public void setTescilNo(int tescilNo) {
		this.tescilNo = tescilNo;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public double getEnlem() {
		return enlem;
	}

	public void setEnlem(double enlem) {
		this.enlem = enlem;
	}

	public double getBoylam() {
		return boylam;
	}

	public void setBoylam(double boylam) {
		this.boylam = boylam;
	}

	public String getAcikAdres() {
		return acikAdres;
	}

	public void setAcikAdres(String acikAdres) {
		this.acikAdres = acikAdres;
	}

	public int getUavtKod() {
		return uavtKod;
	}

	public void setUavtKod(int uavtKod) {
		this.uavtKod = uavtKod;
	}

	public int getBucakKoyKod() {
		return bucakKoyKod;
	}

	public void setBucakKoyKod(int bucakKoyKod) {
		this.bucakKoyKod = bucakKoyKod;
	}

	public int getFirma() {
		return firma;
	}

	public void setFirma(int firma) {
		this.firma = firma;
	}

}
