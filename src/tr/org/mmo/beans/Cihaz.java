package tr.org.mmo.beans;

import java.io.Serializable;
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

/**
 * The persistent class for the cihaz database table.
 * 
 */
@Entity
@Table(name = "cihaz", schema = "test")
public class Cihaz implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cihaz_id")
	private Integer cihazId;

	@Column(name = "kimlik_no")
	private String kimlikNo;

	private Integer kod;

	// bi-directional many-to-one association to Bina
	@ManyToOne
	@JoinColumn(name = "bina_id")
	private Bina bina;

	// bi-directional many-to-one association to CihazTuru
	@ManyToOne
	@JoinColumn(name = "cihaz_tip_id")
	private CihazTuru cihazTuru;

	// bi-directional many-to-one association to Kontrol
	@OneToMany(mappedBy = "cihaz")
	private List<Kontrol> kontrols;

	public Cihaz() {
	}

	public Integer getCihazId() {
		return this.cihazId;
	}

	public void setCihazId(Integer cihazId) {
		this.cihazId = cihazId;
	}

	public String getKimlikNo() {
		return this.kimlikNo;
	}

	public void setKimlikNo(String kimlikNo) {
		this.kimlikNo = kimlikNo;
	}

	public Integer getKod() {
		return this.kod;
	}

	public void setKod(Integer kod) {
		this.kod = kod;
	}

	public Bina getBina() {
		return this.bina;
	}

	public void setBina(Bina bina) {
		this.bina = bina;
	}

	public CihazTuru getCihazTuru() {
		return this.cihazTuru;
	}

	public void setCihazTuru(CihazTuru cihazTuru) {
		this.cihazTuru = cihazTuru;
	}

	public List<Kontrol> getKontrols() {
		return this.kontrols;
	}

	public void setKontrols(List<Kontrol> kontrols) {
		this.kontrols = kontrols;
	}

	public Kontrol addKontrol(Kontrol kontrol) {
		getKontrols().add(kontrol);
		kontrol.setCihaz(this);

		return kontrol;
	}

	public Kontrol removeKontrol(Kontrol kontrol) {
		getKontrols().remove(kontrol);
		kontrol.setCihaz(null);

		return kontrol;
	}

}