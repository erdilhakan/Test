package tr.org.mmo.asansor.beans_;

import java.io.Serializable;
import java.util.Date;

public class TaramaYapilmayanBinalar implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2422383909105914925L;
	
	private String ilAdi;
	private String ilceAdi;
	private int ilKodu;
	private int ilceKodu;
	private String mahalle;
	private String caddeSokak;
	private int binaId;
	private String binaNo;
	private String binaAdi;
	private int taramaId;
	private Date taramaTarih;
	private long binaSorumlusuTCKimlikNo;
	private String binaSorumlusuAdiSoyadi;
	private String binaSorumlusuTelefonNo;
	private String taramaYapilamamaNedeni;
	public String getIlAdi() {
		return ilAdi;
	}
	public void setIlAdi(String ilAdi) {
		this.ilAdi = ilAdi;
	}
	public String getIlceAdi() {
		return ilceAdi;
	}
	public void setIlceAdi(String ilceAdi) {
		this.ilceAdi = ilceAdi;
	}
	public int getIlKodu() {
		return ilKodu;
	}
	public void setIlKodu(int ilKodu) {
		this.ilKodu = ilKodu;
	}
	public int getIlceKodu() {
		return ilceKodu;
	}
	public void setIlceKodu(int ilceKodu) {
		this.ilceKodu = ilceKodu;
	}
	public String getMahalle() {
		return mahalle;
	}
	public void setMahalle(String mahalle) {
		this.mahalle = mahalle;
	}
	public String getCaddeSokak() {
		return caddeSokak;
	}
	public void setCaddeSokak(String caddeSokak) {
		this.caddeSokak = caddeSokak;
	}
	public int getBinaId() {
		return binaId;
	}
	public void setBinaId(int binaId) {
		this.binaId = binaId;
	}
	public String getBinaNo() {
		return binaNo;
	}
	public void setBinaNo(String binaNo) {
		this.binaNo = binaNo;
	}
	public String getBinaAdi() {
		return binaAdi;
	}
	public void setBinaAdi(String binaAdi) {
		this.binaAdi = binaAdi;
	}
	public int getTaramaId() {
		return taramaId;
	}
	public void setTaramaId(int taramaId) {
		this.taramaId = taramaId;
	}
	public Date getTaramaTarih() {
		return taramaTarih;
	}
	public void setTaramaTarih(Date taramaTarih) {
		this.taramaTarih = taramaTarih;
	}
	public long getBinaSorumlusuTCKimlikNo() {
		return binaSorumlusuTCKimlikNo;
	}
	public void setBinaSorumlusuTCKimlikNo(long binaSorumlusuTCKimlikNo) {
		this.binaSorumlusuTCKimlikNo = binaSorumlusuTCKimlikNo;
	}
	public String getBinaSorumlusuAdiSoyadi() {
		return binaSorumlusuAdiSoyadi;
	}
	public void setBinaSorumlusuAdiSoyadi(String binaSorumlusuAdiSoyadi) {
		this.binaSorumlusuAdiSoyadi = binaSorumlusuAdiSoyadi;
	}
	public String getBinaSorumlusuTelefonNo() {
		return binaSorumlusuTelefonNo;
	}
	public void setBinaSorumlusuTelefonNo(String binaSorumlusuTelefonNo) {
		this.binaSorumlusuTelefonNo = binaSorumlusuTelefonNo;
	}
	public String getTaramaYapilamamaNedeni() {
		return taramaYapilamamaNedeni;
	}
	public void setTaramaYapilamamaNedeni(String taramaYapilamamaNedeni) {
		this.taramaYapilamamaNedeni = taramaYapilamamaNedeni;
	}

}
