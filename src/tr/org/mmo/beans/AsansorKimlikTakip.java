package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the asansor_kimlik_takip database table.
 * 
 */
@Entity
@Table(name="asansor_kimlik_takip",schema="test")
public class AsansorKimlikTakip implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@GeneratedValue(strategy=GenerationType.AUTO)
	private AsansorKimlikTakipPK id;

	private String deger;

	@Column(name="ilce_adi")
	private String ilceAdi;

	public AsansorKimlikTakip() {
	}

	public AsansorKimlikTakipPK getId() {
		return this.id;
	}

	public void setId(AsansorKimlikTakipPK id) {
		this.id = id;
	}

	public String getDeger() {
		return this.deger;
	}

	public void setDeger(String deger) {
		this.deger = deger;
	}

	public String getIlceAdi() {
		return this.ilceAdi;
	}

	public void setIlceAdi(String ilceAdi) {
		this.ilceAdi = ilceAdi;
	}

}