package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the randevu_muhendis database table.
 * 
 */
@Entity
@Table(name="randevu_muhendis",schema="test")
public class RandevuMuhendis implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@GeneratedValue(strategy=GenerationType.AUTO)
	private RandevuMuhendisPK id;

	private String sorumlu;

	//bi-directional many-to-one association to Muhendi
	@ManyToOne
	@JoinColumn(name="muhendis_sicilno")
	private Muhendis muhendis;

	public RandevuMuhendis() {
	}

	public RandevuMuhendisPK getId() {
		return this.id;
	}

	public void setId(RandevuMuhendisPK id) {
		this.id = id;
	}

	public String getSorumlu() {
		return this.sorumlu;
	}

	public void setSorumlu(String sorumlu) {
		this.sorumlu = sorumlu;
	}

	public Muhendis getMuhendis() {
		return this.muhendis;
	}

	public void setMuhendis(Muhendis muhendis) {
		this.muhendis = muhendis;
	}

}