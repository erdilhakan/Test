package tr.org.mmo.asansor.dto;

import java.io.Serializable;

import tr.org.mmo.asansor.util.Util;

public class SubeDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7057772632790252362L;

	private int kod;
	private String sube;
	private String eposta;
	private int sicilNo;
	private String adres="";
	private String smtp;
	private int port;
	private String kullaniciAdi;
	private String parola;
	private long telefonNo;
	private long faks;
	private int il;

	private String hostAdres;

	public int getKod() {
		return kod;
	}

	public void setKod(int kod) {
		this.kod = kod;

	}

	public String getSube() {
		return sube;
	}

	public void setSube(String sube) {
		this.sube = Util.toUpperCase(sube);
	}

	public String getEposta() {
		return eposta;
	}

	public void setEposta(String eposta) {
		this.eposta = eposta;
	}

	public int getSicilNo() {
		return sicilNo;
	}

	public void setSicilNo(int sicilNo) {
		this.sicilNo = sicilNo;
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

	public int getIl() {
		return il;
	}

	public void setIl(int il) {
		this.il = il;
	}

}
