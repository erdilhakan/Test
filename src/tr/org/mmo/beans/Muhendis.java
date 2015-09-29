package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the muhendis database table.
 * 
 */
@Entity
@Table(name="muhendis",schema="test")
public class Muhendis implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long sicilno;

	private String adi;

	private String akreditasyon;

	private String aktif;

	private String eposta;

	@Column(name="gsm_telefon")
	private Long gsmTelefon;

	private Integer il;

	@Column(name="onay_yetkisi")
	private String onayYetkisi;

	private String soyadi;

	//bi-directional many-to-one association to RandevuMuhendi
	@OneToMany(mappedBy="muhendis")
	private List<RandevuMuhendis> randevuMuhendis;

	public Muhendis() {
	}

	public Long getSicilno() {
		return this.sicilno;
	}

	public void setSicilno(Long sicilno) {
		this.sicilno = sicilno;
	}

	public String getAdi() {
		return this.adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

	public String getAkreditasyon() {
		return this.akreditasyon;
	}

	public void setAkreditasyon(String akreditasyon) {
		this.akreditasyon = akreditasyon;
	}

	public String getAktif() {
		return this.aktif;
	}

	public void setAktif(String aktif) {
		this.aktif = aktif;
	}

	public String getEposta() {
		return this.eposta;
	}

	public void setEposta(String eposta) {
		this.eposta = eposta;
	}

	public Long getGsmTelefon() {
		return this.gsmTelefon;
	}

	public void setGsmTelefon(Long gsmTelefon) {
		this.gsmTelefon = gsmTelefon;
	}

	public Integer getIl() {
		return this.il;
	}

	public void setIl(Integer il) {
		this.il = il;
	}

	public String getOnayYetkisi() {
		return this.onayYetkisi;
	}

	public void setOnayYetkisi(String onayYetkisi) {
		this.onayYetkisi = onayYetkisi;
	}

	public String getSoyadi() {
		return this.soyadi;
	}

	public void setSoyadi(String soyadi) {
		this.soyadi = soyadi;
	}

	public List<RandevuMuhendis> getRandevuMuhendis() {
		return this.randevuMuhendis;
	}

	public void setRandevuMuhendis(List<RandevuMuhendis> randevuMuhendis) {
		this.randevuMuhendis = randevuMuhendis;
	}

	public RandevuMuhendis addRandevuMuhendis(RandevuMuhendis randevuMuhendis) {
		getRandevuMuhendis().add(randevuMuhendis);
		randevuMuhendis.setMuhendis(this);

		return randevuMuhendis;
	}

	public RandevuMuhendis removeRandevuMuhendis(RandevuMuhendis randevuMuhendis) {
		getRandevuMuhendis().remove(randevuMuhendis);
		randevuMuhendis.setMuhendis(null);

		return randevuMuhendis;
	}

}