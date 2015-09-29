package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the belediye_sozlesme database table.
 * 
 */
@Entity
@Table(name="belediye_sozlesme",schema="test")
public class BelediyeSozlesme implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="cihaz_tipi")
	private Integer cihazTipi;

	private Integer donem;

	private BigDecimal fiyat;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private Integer kapasite;

	@Column(name="kapasite_olcut")
	private String kapasiteOlcut;

	@Temporal(TemporalType.DATE)
	@Column(name="sozlesme_bitis_tarihi")
	private Date sozlesmeBitisTarihi;

	@Temporal(TemporalType.DATE)
	@Column(name="sozlesme_tarihi")
	private Date sozlesmeTarihi;

	private Integer yil;

	//bi-directional many-to-one association to Belediye
	@ManyToOne
	private Belediye belediye;

	public BelediyeSozlesme() {
	}

	public Integer getCihazTipi() {
		return this.cihazTipi;
	}

	public void setCihazTipi(Integer cihazTipi) {
		this.cihazTipi = cihazTipi;
	}

	public Integer getDonem() {
		return this.donem;
	}

	public void setDonem(Integer donem) {
		this.donem = donem;
	}

	public BigDecimal getFiyat() {
		return this.fiyat;
	}

	public void setFiyat(BigDecimal fiyat) {
		this.fiyat = fiyat;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getKapasite() {
		return this.kapasite;
	}

	public void setKapasite(Integer kapasite) {
		this.kapasite = kapasite;
	}

	public String getKapasiteOlcut() {
		return this.kapasiteOlcut;
	}

	public void setKapasiteOlcut(String kapasiteOlcut) {
		this.kapasiteOlcut = kapasiteOlcut;
	}

	public Date getSozlesmeBitisTarihi() {
		return this.sozlesmeBitisTarihi;
	}

	public void setSozlesmeBitisTarihi(Date sozlesmeBitisTarihi) {
		this.sozlesmeBitisTarihi = sozlesmeBitisTarihi;
	}

	public Date getSozlesmeTarihi() {
		return this.sozlesmeTarihi;
	}

	public void setSozlesmeTarihi(Date sozlesmeTarihi) {
		this.sozlesmeTarihi = sozlesmeTarihi;
	}

	public Integer getYil() {
		return this.yil;
	}

	public void setYil(Integer yil) {
		this.yil = yil;
	}

	public Belediye getBelediye() {
		return this.belediye;
	}

	public void setBelediye(Belediye belediye) {
		this.belediye = belediye;
	}

}