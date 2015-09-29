package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cihaz_turu_teknik_ozellik_deger database table.
 * 
 */
@Entity
@Table(name="cihaz_turu_teknik_ozellik_deger",schema="test")
public class CihazTuruTeknikOzellikDeger implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="deger_id")
	private Integer degerId;

	private String birim;

	private String deger;

	@Column(name="teknik_ozellik_kod")
	private Integer teknikOzellikKod;

	private String tipi;

	public CihazTuruTeknikOzellikDeger() {
	}

	public Integer getDegerId() {
		return this.degerId;
	}

	public void setDegerId(Integer degerId) {
		this.degerId = degerId;
	}

	public String getBirim() {
		return this.birim;
	}

	public void setBirim(String birim) {
		this.birim = birim;
	}

	public String getDeger() {
		return this.deger;
	}

	public void setDeger(String deger) {
		this.deger = deger;
	}

	public Integer getTeknikOzellikKod() {
		return this.teknikOzellikKod;
	}

	public void setTeknikOzellikKod(Integer teknikOzellikKod) {
		this.teknikOzellikKod = teknikOzellikKod;
	}

	public String getTipi() {
		return this.tipi;
	}

	public void setTipi(String tipi) {
		this.tipi = tipi;
	}

}