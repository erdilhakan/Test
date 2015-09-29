package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the bakimci_firma_birim database table.
 * 
 */
@Entity
@Table(name="bakimci_firma_birim",schema="test")
public class BakimciFirmaBirim implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="firma_id")
	private int firmaId;
	
	private Integer sube;

	private Integer temsilcilik;

	//bi-directional many-to-one association to BakimciFirma
	@ManyToOne
	@JoinColumn(name="firma_id")
	private BakimciFirma bakimciFirma;

	public BakimciFirmaBirim() {
	}

	public Integer getSube() {
		return this.sube;
	}

	public void setSube(Integer sube) {
		this.sube = sube;
	}

	public Integer getTemsilcilik() {
		return this.temsilcilik;
	}

	public void setTemsilcilik(Integer temsilcilik) {
		this.temsilcilik = temsilcilik;
	}

	public BakimciFirma getBakimciFirma() {
		return this.bakimciFirma;
	}

	public void setBakimciFirma(BakimciFirma bakimciFirma) {
		this.bakimciFirma = bakimciFirma;
	}

	public int getFirmaId() {
		return firmaId;
	}

	public void setFirmaId(int firmaId) {
		this.firmaId = firmaId;
	}
	
	

}