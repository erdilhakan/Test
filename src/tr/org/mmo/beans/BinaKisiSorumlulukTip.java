package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the bina_kisi_sorumluluk_tip database table.
 * 
 */
@Entity
@Table(name="bina_kisi_sorumluluk_tip",schema="test")
public class BinaKisiSorumlulukTip implements Serializable {
	private static final long serialVersionUID = 1L;

	private String aciklama;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private Integer tip;

	public BinaKisiSorumlulukTip() {
	}

	public String getAciklama() {
		return this.aciklama;
	}

	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTip() {
		return this.tip;
	}

	public void setTip(Integer tip) {
		this.tip = tip;
	}

}