package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tr.org.mmo.asansor.util.Util;

public class TemsilcilikDTO implements Serializable,Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3347782547597500680L;
	private String kodStr;
	private int kod;
	private String adi;
	private int subesi;
	private SubeDTO sube;
	private Date kurulusTarihi;
	private Date kapanisTarihi;
	private String aciklama;
	private String vizeYetkisi;
	private String durumu;
	private int sicilNo;
	private String ePosta;
	private String adres;
	private String smtp;
	private int port;
	private String kullaniciAdi;
	private String parola;
	private long telefonNo;
	private long faks;
	private String hostAdres;
	private List<TemsilcilikIlIlceDTO> temsilcilikIller = new ArrayList<TemsilcilikIlIlceDTO>();

	public int getKod() {
		return kod;
	}

	public void setKod(int kod) {
		this.kod = kod;
		this.kodStr = String.valueOf(kod).trim();
	}

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = Util.toUpperCase(adi);
	}

	public int getSubesi() {
		return subesi;
	}

	public void setSubesi(int subesi) {
		this.subesi = subesi;
	}

	public SubeDTO getSube() {
		return sube;
	}

	public void setSube(SubeDTO sube) {
		this.sube = sube;
	}

	public String getKodStr() {
		return kodStr;
	}

	public Date getKurulusTarihi() {
		return kurulusTarihi;
	}

	public void setKurulusTarihi(Date kurulusTarihi) {
		this.kurulusTarihi = kurulusTarihi;
	}

	public Date getKapanisTarihi() {
		return kapanisTarihi;
	}

	public void setKapanisTarihi(Date kapanisTarihi) {
		this.kapanisTarihi = kapanisTarihi;
	}

	public String getAciklama() {
		return aciklama;
	}

	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}

	public String getVizeYetkisi() {
		return vizeYetkisi;
	}

	public void setVizeYetkisi(String vizeYetkisi) {
		this.vizeYetkisi = vizeYetkisi;
	}

	public String getDurumu() {
		return durumu;
	}

	public void setDurumu(String durumu) {
		this.durumu = durumu;
	}

	public int getSicilNo() {
		return sicilNo;
	}

	public void setSicilNo(int sicilNo) {
		this.sicilNo = sicilNo;
	}

	public String getePosta() {
		return ePosta;
	}

	public void setePosta(String ePosta) {
		this.ePosta = ePosta;
	}

	public String getAdres() {
		return adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

	public String getSmtp() {
		return smtp;
	}

	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getKullaniciAdi() {
		return kullaniciAdi;
	}

	public void setKullaniciAdi(String kullaniciAdi) {
		this.kullaniciAdi = kullaniciAdi;
	}

	public String getParola() {
		return parola;
	}

	public void setParola(String parola) {
		this.parola = parola;
	}

	public long getTelefonNo() {
		return telefonNo;
	}

	public void setTelefonNo(long telefonNo) {
		this.telefonNo = telefonNo;
	}

	public long getFaks() {
		return faks;
	}

	public void setFaks(long faks) {
		this.faks = faks;
	}

	public String getHostAdres() {
		return hostAdres;
	}

	public void setHostAdres(String hostAdres) {
		this.hostAdres = hostAdres;
	}

	public void setKodStr(String kodStr) {
		this.kodStr = kodStr;
	}

	public List<TemsilcilikIlIlceDTO> getTemsilcilikIller() {
		return temsilcilikIller;
	}

	public void setTemsilcilikIller(List<TemsilcilikIlIlceDTO> temsilcilikIller) {
		this.temsilcilikIller = temsilcilikIller;
	}
	

	@Override
	public Object clone() throws CloneNotSupportedException {

		return super.clone();
	}

}
