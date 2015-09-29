package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the soru database table.
 * 
 */
@Entity
@Table(name="soru",schema="test")
public class Soru implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="soru_id")
	private Integer soruId;

	private String aktif;

	@Column(name="cihaz_tipi")
	private Integer cihazTipi;

	private Integer parent;

	@Column(name="sira_no")
	private Integer siraNo;

	private String soru;

	private String yildiz;

	//bi-directional many-to-one association to SoruOntanimli
	@OneToMany(mappedBy="soru")
	private List<SoruOntanimli> soruOntanimlis;

	public Soru() {
	}

	public Integer getSoruId() {
		return this.soruId;
	}

	public void setSoruId(Integer soruId) {
		this.soruId = soruId;
	}

	public String getAktif() {
		return this.aktif;
	}

	public void setAktif(String aktif) {
		this.aktif = aktif;
	}

	public Integer getCihazTipi() {
		return this.cihazTipi;
	}

	public void setCihazTipi(Integer cihazTipi) {
		this.cihazTipi = cihazTipi;
	}

	public Integer getParent() {
		return this.parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public Integer getSiraNo() {
		return this.siraNo;
	}

	public void setSiraNo(Integer siraNo) {
		this.siraNo = siraNo;
	}

	public String getSoru() {
		return this.soru;
	}

	public void setSoru(String soru) {
		this.soru = soru;
	}

	public String getYildiz() {
		return this.yildiz;
	}

	public void setYildiz(String yildiz) {
		this.yildiz = yildiz;
	}

	public List<SoruOntanimli> getSoruOntanimlis() {
		return this.soruOntanimlis;
	}

	public void setSoruOntanimlis(List<SoruOntanimli> soruOntanimlis) {
		this.soruOntanimlis = soruOntanimlis;
	}

	public SoruOntanimli addSoruOntanimli(SoruOntanimli soruOntanimli) {
		getSoruOntanimlis().add(soruOntanimli);
		soruOntanimli.setSoru(this);

		return soruOntanimli;
	}

	public SoruOntanimli removeSoruOntanimli(SoruOntanimli soruOntanimli) {
		getSoruOntanimlis().remove(soruOntanimli);
		soruOntanimli.setSoru(null);

		return soruOntanimli;
	}

}