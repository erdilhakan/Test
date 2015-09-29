package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ililcetb database table.
 * 
 */
@Entity
@Table(name="ililcetb",schema="test")
public class Ililcetb implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@GeneratedValue(strategy=GenerationType.AUTO)
	private IlilcetbPK id;

	@Column(name="il_adi")
	private String ilAdi;

	@Column(name="ilce_adi")
	private String ilceAdi;

	public Ililcetb() {
	}

	public IlilcetbPK getId() {
		return this.id;
	}

	public void setId(IlilcetbPK id) {
		this.id = id;
	}

	public String getIlAdi() {
		return this.ilAdi;
	}

	public void setIlAdi(String ilAdi) {
		this.ilAdi = ilAdi;
	}

	public String getIlceAdi() {
		return this.ilceAdi;
	}

	public void setIlceAdi(String ilceAdi) {
		this.ilceAdi = ilceAdi;
	}

}