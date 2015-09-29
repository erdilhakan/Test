package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the asansor_kimlik_takip database table.
 * 
 */
@Embeddable
public class AsansorKimlikTakipPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer il;

	private Integer ilce;

	@Column(name="bina_id")
	private Integer binaId;

	public AsansorKimlikTakipPK() {
	}
	public Integer getIl() {
		return this.il;
	}
	public void setIl(Integer il) {
		this.il = il;
	}
	public Integer getIlce() {
		return this.ilce;
	}
	public void setIlce(Integer ilce) {
		this.ilce = ilce;
	}
	public Integer getBinaId() {
		return this.binaId;
	}
	public void setBinaId(Integer binaId) {
		this.binaId = binaId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AsansorKimlikTakipPK)) {
			return false;
		}
		AsansorKimlikTakipPK castOther = (AsansorKimlikTakipPK)other;
		return 
			this.il.equals(castOther.il)
			&& this.ilce.equals(castOther.ilce)
			&& this.binaId.equals(castOther.binaId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.il.hashCode();
		hash = hash * prime + this.ilce.hashCode();
		hash = hash * prime + this.binaId.hashCode();
		
		return hash;
	}
}