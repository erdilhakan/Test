package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the temsilcilik database table.
 * 
 */
@Entity
@Table(name="temsilcilik",schema="test")
public class Temsilcilik implements Serializable {
	private static final long serialVersionUID = 1L;

	private String aciklama;

	private String adi;

	private Integer donem;

	private String durumu;

	private String eposta;

	private String hesapkod;

	private String kapanistarihi;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer kod;

	private String kurulustarihi;

	private Integer sicilno;

	private Integer subesi;

	private String vizeyetkisi;

	public Temsilcilik() {
	}

	public String getAciklama() {
		return this.aciklama;
	}

	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}

	public String getAdi() {
		return this.adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

	public Integer getDonem() {
		return this.donem;
	}

	public void setDonem(Integer donem) {
		this.donem = donem;
	}

	public String getDurumu() {
		return this.durumu;
	}

	public void setDurumu(String durumu) {
		this.durumu = durumu;
	}

	public String getEposta() {
		return this.eposta;
	}

	public void setEposta(String eposta) {
		this.eposta = eposta;
	}

	public String getHesapkod() {
		return this.hesapkod;
	}

	public void setHesapkod(String hesapkod) {
		this.hesapkod = hesapkod;
	}

	public String getKapanistarihi() {
		return this.kapanistarihi;
	}

	public void setKapanistarihi(String kapanistarihi) {
		this.kapanistarihi = kapanistarihi;
	}

	public Integer getKod() {
		return this.kod;
	}

	public void setKod(Integer kod) {
		this.kod = kod;
	}

	public String getKurulustarihi() {
		return this.kurulustarihi;
	}

	public void setKurulustarihi(String kurulustarihi) {
		this.kurulustarihi = kurulustarihi;
	}

	public Integer getSicilno() {
		return this.sicilno;
	}

	public void setSicilno(Integer sicilno) {
		this.sicilno = sicilno;
	}

	public Integer getSubesi() {
		return this.subesi;
	}

	public void setSubesi(Integer subesi) {
		this.subesi = subesi;
	}

	public String getVizeyetkisi() {
		return this.vizeyetkisi;
	}

	public void setVizeyetkisi(String vizeyetkisi) {
		this.vizeyetkisi = vizeyetkisi;
	}

}