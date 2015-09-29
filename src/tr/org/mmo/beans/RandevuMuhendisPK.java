package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the randevu_muhendis database table.
 * 
 */
@Embeddable
public class RandevuMuhendisPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="randevu_id")
	private Integer randevuId;

	@Column(name="muhendis_sicilno")
	private Integer muhendisSicilno;

	public RandevuMuhendisPK() {
	}
	public Integer getRandevuId() {
		return this.randevuId;
	}
	public void setRandevuId(Integer randevuId) {
		this.randevuId = randevuId;
	}
	public Integer getMuhendisSicilno() {
		return this.muhendisSicilno;
	}
	public void setMuhendisSicilno(Integer muhendisSicilno) {
		this.muhendisSicilno = muhendisSicilno;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RandevuMuhendisPK)) {
			return false;
		}
		RandevuMuhendisPK castOther = (RandevuMuhendisPK)other;
		return 
			this.randevuId.equals(castOther.randevuId)
			&& this.muhendisSicilno.equals(castOther.muhendisSicilno);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.randevuId.hashCode();
		hash = hash * prime + this.muhendisSicilno.hashCode();
		
		return hash;
	}
}