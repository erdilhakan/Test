package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the cihaz_turu_teknik_ozellikleri database table.
 * 
 */
@Entity
@Table(name="cihaz_turu_teknik_ozellikleri",schema="test")
public class CihazTuruTeknikOzellikleri implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer kod;

	private String baslik;

	@Column(name="cihaz_turu_kod")
	private Integer cihazTuruKod;

	private Integer kullanici;

	private Integer sira;

	private Timestamp tarih;

	public CihazTuruTeknikOzellikleri() {
	}

	public Integer getKod() {
		return this.kod;
	}

	public void setKod(Integer kod) {
		this.kod = kod;
	}

	public String getBaslik() {
		return this.baslik;
	}

	public void setBaslik(String baslik) {
		this.baslik = baslik;
	}

	public Integer getCihazTuruKod() {
		return this.cihazTuruKod;
	}

	public void setCihazTuruKod(Integer cihazTuruKod) {
		this.cihazTuruKod = cihazTuruKod;
	}

	public Integer getKullanici() {
		return this.kullanici;
	}

	public void setKullanici(Integer kullanici) {
		this.kullanici = kullanici;
	}

	public Integer getSira() {
		return this.sira;
	}

	public void setSira(Integer sira) {
		this.sira = sira;
	}

	public Timestamp getTarih() {
		return this.tarih;
	}

	public void setTarih(Timestamp tarih) {
		this.tarih = tarih;
	}

}