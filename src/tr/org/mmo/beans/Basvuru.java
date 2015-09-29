package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the basvuru database table.
 * 
 */
@Entity
@Table(name="basvuru",schema="test")
public class Basvuru implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="basvuru_id")
	private Integer basvuruId;

	@Column(name="basvuru_durum")
	private String basvuruDurum;

	@Temporal(TemporalType.DATE)
	@Column(name="basvuru_tarihi")
	private Date basvuruTarihi;

	@Column(name="basvuru_yapan_adi")
	private String basvuruYapanAdi;

	@Column(name="basvuru_yapan_soyadi")
	private String basvuruYapanSoyadi;

	@Column(name="basvuru_yapan_tckimlikno")
	private Long basvuruYapanTckimlikno;

	@Column(name="telefon_no")
	private Long telefonNo;

	//bi-directional many-to-one association to Bina
	@ManyToOne
	@JoinColumn(name="bina_id")
	private Bina bina;

	//bi-directional many-to-one association to Odeme
	@OneToMany(mappedBy="basvuru")
	private List<Odeme> odemes;

	//bi-directional many-to-one association to Randevu
	@OneToMany(mappedBy="basvuru")
	private List<Randevu> randevus;

	public Basvuru() {
	}

	public Integer getBasvuruId() {
		return this.basvuruId;
	}

	public void setBasvuruId(Integer basvuruId) {
		this.basvuruId = basvuruId;
	}

	public String getBasvuruDurum() {
		return this.basvuruDurum;
	}

	public void setBasvuruDurum(String basvuruDurum) {
		this.basvuruDurum = basvuruDurum;
	}

	public Date getBasvuruTarihi() {
		return this.basvuruTarihi;
	}

	public void setBasvuruTarihi(Date basvuruTarihi) {
		this.basvuruTarihi = basvuruTarihi;
	}

	public String getBasvuruYapanAdi() {
		return this.basvuruYapanAdi;
	}

	public void setBasvuruYapanAdi(String basvuruYapanAdi) {
		this.basvuruYapanAdi = basvuruYapanAdi;
	}

	public String getBasvuruYapanSoyadi() {
		return this.basvuruYapanSoyadi;
	}

	public void setBasvuruYapanSoyadi(String basvuruYapanSoyadi) {
		this.basvuruYapanSoyadi = basvuruYapanSoyadi;
	}

	public Long getBasvuruYapanTckimlikno() {
		return this.basvuruYapanTckimlikno;
	}

	public void setBasvuruYapanTckimlikno(Long basvuruYapanTckimlikno) {
		this.basvuruYapanTckimlikno = basvuruYapanTckimlikno;
	}

	public Long getTelefonNo() {
		return this.telefonNo;
	}

	public void setTelefonNo(Long telefonNo) {
		this.telefonNo = telefonNo;
	}

	public Bina getBina() {
		return this.bina;
	}

	public void setBina(Bina bina) {
		this.bina = bina;
	}

	public List<Odeme> getOdemes() {
		return this.odemes;
	}

	public void setOdemes(List<Odeme> odemes) {
		this.odemes = odemes;
	}

	public Odeme addOdeme(Odeme odeme) {
		getOdemes().add(odeme);
		odeme.setBasvuru(this);

		return odeme;
	}

	public Odeme removeOdeme(Odeme odeme) {
		getOdemes().remove(odeme);
		odeme.setBasvuru(null);

		return odeme;
	}

	public List<Randevu> getRandevus() {
		return this.randevus;
	}

	public void setRandevus(List<Randevu> randevus) {
		this.randevus = randevus;
	}

	public Randevu addRandevus(Randevu randevus) {
		getRandevus().add(randevus);
		randevus.setBasvuru(this);

		return randevus;
	}

	public Randevu removeRandevus(Randevu randevus) {
		getRandevus().remove(randevus);
		randevus.setBasvuru(null);

		return randevus;
	}

}