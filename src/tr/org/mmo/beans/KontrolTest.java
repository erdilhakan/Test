package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the kontrol_test database table.
 * 
 */
@Entity
@Table(name="kontrol_test",schema="test")
public class KontrolTest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="cevap_id")
	private Integer cevapId;

	private String aciklama;

	private String cevap;

	@Column(name="soru_id")
	private Integer soruId;

	//bi-directional many-to-one association to Kontrol
	@ManyToOne
	@JoinColumn(name="kontrol_id")
	private Kontrol kontrol;

	public KontrolTest() {
	}

	public Integer getCevapId() {
		return this.cevapId;
	}

	public void setCevapId(Integer cevapId) {
		this.cevapId = cevapId;
	}

	public String getAciklama() {
		return this.aciklama;
	}

	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}

	public String getCevap() {
		return this.cevap;
	}

	public void setCevap(String cevap) {
		this.cevap = cevap;
	}

	public Integer getSoruId() {
		return this.soruId;
	}

	public void setSoruId(Integer soruId) {
		this.soruId = soruId;
	}

	public Kontrol getKontrol() {
		return this.kontrol;
	}

	public void setKontrol(Kontrol kontrol) {
		this.kontrol = kontrol;
	}

}