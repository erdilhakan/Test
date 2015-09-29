package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the soru_ontanimli database table.
 * 
 */
@Entity
@Table(name="soru_ontanimli",schema="test")
public class SoruOntanimli implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="soru_ontanimli_id")
	private Integer soruOntanimliId;

	private String aciklama;

	//bi-directional many-to-one association to Soru
	@ManyToOne
	@JoinColumn(name="soru_id")
	private Soru soru;

	public SoruOntanimli() {
	}

	public Integer getSoruOntanimliId() {
		return this.soruOntanimliId;
	}

	public void setSoruOntanimliId(Integer soruOntanimliId) {
		this.soruOntanimliId = soruOntanimliId;
	}

	public String getAciklama() {
		return this.aciklama;
	}

	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}

	public Soru getSoru() {
		return this.soru;
	}

	public void setSoru(Soru soru) {
		this.soru = soru;
	}

}