package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the cihaz_turu database table.
 * 
 */
@Entity
@Table(name="cihaz_turu",schema="test")
public class CihazTuru implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="cihaz_tip_id")
	private Integer cihazTipId;

	@Column(name="cihaz_tip_aciklama")
	private String cihazTipAciklama;

	//bi-directional many-to-one association to Cihaz
	@OneToMany(mappedBy="cihazTuru")
	private List<Cihaz> cihazs;

	public CihazTuru() {
	}

	public Integer getCihazTipId() {
		return this.cihazTipId;
	}

	public void setCihazTipId(Integer cihazTipId) {
		this.cihazTipId = cihazTipId;
	}

	public String getCihazTipAciklama() {
		return this.cihazTipAciklama;
	}

	public void setCihazTipAciklama(String cihazTipAciklama) {
		this.cihazTipAciklama = cihazTipAciklama;
	}

	public List<Cihaz> getCihazs() {
		return this.cihazs;
	}

	public void setCihazs(List<Cihaz> cihazs) {
		this.cihazs = cihazs;
	}

	public Cihaz addCihaz(Cihaz cihaz) {
		getCihazs().add(cihaz);
		cihaz.setCihazTuru(this);

		return cihaz;
	}

	public Cihaz removeCihaz(Cihaz cihaz) {
		getCihazs().remove(cihaz);
		cihaz.setCihazTuru(null);

		return cihaz;
	}

}