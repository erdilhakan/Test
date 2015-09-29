package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Time;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the randevu database table.
 * 
 */

@Entity
@Table(name="randevu",schema="test")
public class Randevu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="randevu_id")
	private Integer randevuId;

	@Column(name="firma_id")
	private Long firmaId;

	@Column(name="randevu_durum")
	private String randevuDurum;

	@Column(name="randevu_saati")
	private Time randevuSaati;

	@Temporal(TemporalType.DATE)
	@Column(name="randevu_tarihi")
	private Date randevuTarihi;

	//bi-directional many-to-one association to Kontrol
	@OneToMany(mappedBy="randevu")
	private List<Kontrol> kontrols;

	//bi-directional many-to-one association to Basvuru
	@ManyToOne
	@JoinColumn(name="basvuru_id")
	private Basvuru basvuru;

	//bi-directional one-to-one association to Randevu
	@OneToOne
	@JoinColumn(name="randevu_id")
	private Randevu randevu1;

	//bi-directional one-to-one association to Randevu
	@OneToOne(mappedBy="randevu1")
	private Randevu randevu2;

	public Randevu() {
	}

	public Integer getRandevuId() {
		return this.randevuId;
	}

	public void setRandevuId(Integer randevuId) {
		this.randevuId = randevuId;
	}

	public Long getFirmaId() {
		return this.firmaId;
	}

	public void setFirmaId(Long firmaId) {
		this.firmaId = firmaId;
	}

	public String getRandevuDurum() {
		return this.randevuDurum;
	}

	public void setRandevuDurum(String randevuDurum) {
		this.randevuDurum = randevuDurum;
	}

	public Time getRandevuSaati() {
		return this.randevuSaati;
	}

	public void setRandevuSaati(Time randevuSaati) {
		this.randevuSaati = randevuSaati;
	}

	public Date getRandevuTarihi() {
		return this.randevuTarihi;
	}

	public void setRandevuTarihi(Date randevuTarihi) {
		this.randevuTarihi = randevuTarihi;
	}

	public List<Kontrol> getKontrols() {
		return this.kontrols;
	}

	public void setKontrols(List<Kontrol> kontrols) {
		this.kontrols = kontrols;
	}

	public Kontrol addKontrol(Kontrol kontrol) {
		getKontrols().add(kontrol);
		kontrol.setRandevu(this);

		return kontrol;
	}

	public Kontrol removeKontrol(Kontrol kontrol) {
		getKontrols().remove(kontrol);
		kontrol.setRandevu(null);

		return kontrol;
	}

	public Basvuru getBasvuru() {
		return this.basvuru;
	}

	public void setBasvuru(Basvuru basvuru) {
		this.basvuru = basvuru;
	}

	public Randevu getRandevu1() {
		return this.randevu1;
	}

	public void setRandevu1(Randevu randevu1) {
		this.randevu1 = randevu1;
	}

	public Randevu getRandevu2() {
		return this.randevu2;
	}

	public void setRandevu2(Randevu randevu2) {
		this.randevu2 = randevu2;
	}

}