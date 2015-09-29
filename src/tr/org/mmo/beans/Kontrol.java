package tr.org.mmo.beans;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the kontrol database table.
 * 
 */
@Entity
@Table(name = "kontrol", schema = "test")
public class Kontrol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "kontrol_id")
	private Integer kontrolId;

	private String etiket;

	@Column(name = "kontrol_baslangic_saati")
	private Time kontrolBaslangicSaati;

	@Temporal(TemporalType.DATE)
	@Column(name = "kontrol_baslangic_tarihi")
	private Date kontrolBaslangicTarihi;

	@Column(name = "kontrol_bitis_saati")
	private Time kontrolBitisSaati;

	@Temporal(TemporalType.DATE)
	@Column(name = "kontrol_bitis_tarihi")
	private Date kontrolBitisTarihi;

	@Column(name = "kontrol_turu")
	private String kontrolTuru;

	@Column(name = "onay_durum")
	private String onayDurum;

	@Temporal(TemporalType.DATE)
	@Column(name = "onay_tarihi")
	private Date onayTarihi;

	@Column(name = "onaylayan_kullanici_id")
	private Integer onaylayanKullaniciId;

	// bi-directional many-to-one association to Cihaz
	@ManyToOne
	@JoinColumn(name = "cihaz_id")
	private Cihaz cihaz;

	// bi-directional many-to-one association to Randevu
	@ManyToOne
	@JoinColumn(name = "randevu_id")
	private Randevu randevu;

	// bi-directional many-to-one association to KontrolTest
	@OneToMany(mappedBy = "kontrol")
	private List<KontrolTest> kontrolTests;

	// bi-directional many-to-one association to Rapor
	@OneToMany(mappedBy = "kontrol")
	private List<Rapor> rapors;

	public Kontrol() {
	}

	public Integer getKontrolId() {
		return this.kontrolId;
	}

	public void setKontrolId(Integer kontrolId) {
		this.kontrolId = kontrolId;
	}

	public String getEtiket() {
		return this.etiket;
	}

	public void setEtiket(String etiket) {
		this.etiket = etiket;
	}

	public Time getKontrolBaslangicSaati() {
		return kontrolBaslangicSaati;
	}

	public void setKontrolBaslangicSaati(Time kontrolBaslangicSaati) {
		this.kontrolBaslangicSaati = kontrolBaslangicSaati;
	}

	public Date getKontrolBaslangicTarihi() {
		return kontrolBaslangicTarihi;
	}

	public void setKontrolBaslangicTarihi(Date kontrolBaslangicTarihi) {
		this.kontrolBaslangicTarihi = kontrolBaslangicTarihi;
	}

	public Time getKontrolBitisSaati() {
		return kontrolBitisSaati;
	}

	public void setKontrolBitisSaati(Time kontrolBitisSaati) {
		this.kontrolBitisSaati = kontrolBitisSaati;
	}

	public Date getKontrolBitisTarihi() {
		return kontrolBitisTarihi;
	}

	public void setKontrolBitisTarihi(Date kontrolBitisTarihi) {
		this.kontrolBitisTarihi = kontrolBitisTarihi;
	}

	public String getKontrolTuru() {
		return this.kontrolTuru;
	}

	public void setKontrolTuru(String kontrolTuru) {
		this.kontrolTuru = kontrolTuru;
	}

	public String getOnayDurum() {
		return this.onayDurum;
	}

	public void setOnayDurum(String onayDurum) {
		this.onayDurum = onayDurum;
	}

	public Date getOnayTarihi() {
		return this.onayTarihi;
	}

	public void setOnayTarihi(Date onayTarihi) {
		this.onayTarihi = onayTarihi;
	}

	public Integer getOnaylayanKullaniciId() {
		return this.onaylayanKullaniciId;
	}

	public void setOnaylayanKullaniciId(Integer onaylayanKullaniciId) {
		this.onaylayanKullaniciId = onaylayanKullaniciId;
	}

	public Cihaz getCihaz() {
		return this.cihaz;
	}

	public void setCihaz(Cihaz cihaz) {
		this.cihaz = cihaz;
	}

	public Randevu getRandevu() {
		return this.randevu;
	}

	public void setRandevu(Randevu randevu) {
		this.randevu = randevu;
	}

	public List<KontrolTest> getKontrolTests() {
		return this.kontrolTests;
	}

	public void setKontrolTests(List<KontrolTest> kontrolTests) {
		this.kontrolTests = kontrolTests;
	}

	public KontrolTest addKontrolTest(KontrolTest kontrolTest) {
		getKontrolTests().add(kontrolTest);
		kontrolTest.setKontrol(this);

		return kontrolTest;
	}

	public KontrolTest removeKontrolTest(KontrolTest kontrolTest) {
		getKontrolTests().remove(kontrolTest);
		kontrolTest.setKontrol(null);

		return kontrolTest;
	}

	public List<Rapor> getRapors() {
		return this.rapors;
	}

	public void setRapors(List<Rapor> rapors) {
		this.rapors = rapors;
	}

	public Rapor addRapor(Rapor rapor) {
		getRapors().add(rapor);
		rapor.setKontrol(this);

		return rapor;
	}

	public Rapor removeRapor(Rapor rapor) {
		getRapors().remove(rapor);
		rapor.setKontrol(null);

		return rapor;
	}

}