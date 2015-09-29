package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ililcetb database table.
 * 
 */
@Embeddable
public class IlilcetbPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="il_kodu")
	private Integer ilKodu;

	@Column(name="ilce_kodu")
	private Integer ilceKodu;

	public IlilcetbPK() {
	}
	public Integer getIlKodu() {
		return this.ilKodu;
	}
	public void setIlKodu(Integer ilKodu) {
		this.ilKodu = ilKodu;
	}
	public Integer getIlceKodu() {
		return this.ilceKodu;
	}
	public void setIlceKodu(Integer ilceKodu) {
		this.ilceKodu = ilceKodu;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof IlilcetbPK)) {
			return false;
		}
		IlilcetbPK castOther = (IlilcetbPK)other;
		return 
			this.ilKodu.equals(castOther.ilKodu)
			&& this.ilceKodu.equals(castOther.ilceKodu);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ilKodu.hashCode();
		hash = hash * prime + this.ilceKodu.hashCode();
		
		return hash;
	}
}