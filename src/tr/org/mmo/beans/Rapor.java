package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the rapor database table.
 * 
 */
@Entity
@Table(name="rapor",schema="test")
public class Rapor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="rapor_id")
	private Integer raporId;

	@Column(name="dosya_adi")
	private String dosyaAdi;

	@Column(name="dosya_yeri")
	private String dosyaYeri;

	@Temporal(TemporalType.DATE)
	@Column(name="rapor_tarihi")
	private Date raporTarihi;

	//bi-directional many-to-one association to Kontrol
	@ManyToOne
	@JoinColumn(name="kontrol_id")
	private Kontrol kontrol;

	public Rapor() {
	}

	public Integer getRaporId() {
		return this.raporId;
	}

	public void setRaporId(Integer raporId) {
		this.raporId = raporId;
	}

	public String getDosyaAdi() {
		return this.dosyaAdi;
	}

	public void setDosyaAdi(String dosyaAdi) {
		this.dosyaAdi = dosyaAdi;
	}

	public String getDosyaYeri() {
		return this.dosyaYeri;
	}

	public void setDosyaYeri(String dosyaYeri) {
		this.dosyaYeri = dosyaYeri;
	}

	public Date getRaporTarihi() {
		return this.raporTarihi;
	}

	public void setRaporTarihi(Date raporTarihi) {
		this.raporTarihi = raporTarihi;
	}

	public Kontrol getKontrol() {
		return this.kontrol;
	}

	public void setKontrol(Kontrol kontrol) {
		this.kontrol = kontrol;
	}

}