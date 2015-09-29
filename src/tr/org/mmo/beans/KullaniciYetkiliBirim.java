package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the kullanici_yetkili_birim database table.
 * 
 */
@Entity
@Table(name="kullanici_yetkili_birim",schema="test")
public class KullaniciYetkiliBirim implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="birim_id")
	private Integer birimId;

	@Column(name="birim_kodu")
	private Integer birimKodu;

	@Column(name="birim_tip")
	private String birimTip;

	//bi-directional many-to-one association to Kullanici
	@ManyToOne
	@JoinColumn(name="kullanici_id")
	private Kullanici kullanici;

	public KullaniciYetkiliBirim() {
	}

	public Integer getBirimId() {
		return this.birimId;
	}

	public void setBirimId(Integer birimId) {
		this.birimId = birimId;
	}

	public Integer getBirimKodu() {
		return this.birimKodu;
	}

	public void setBirimKodu(Integer birimKodu) {
		this.birimKodu = birimKodu;
	}

	public String getBirimTip() {
		return this.birimTip;
	}

	public void setBirimTip(String birimTip) {
		this.birimTip = birimTip;
	}

	public Kullanici getKullanici() {
		return this.kullanici;
	}

	public void setKullanici(Kullanici kullanici) {
		this.kullanici = kullanici;
	}

}