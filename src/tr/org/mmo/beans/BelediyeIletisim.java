package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the belediye_iletisim database table.
 * 
 */
@Entity
@Table(name="belediye_iletisim",schema="test")
public class BelediyeIletisim implements Serializable {
	private static final long serialVersionUID = 1L;

	private String adi;

	private String eposta;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String soyadi;

	private Long telefonno;

	private String unvani;

	//bi-directional many-to-one association to Belediye
	@ManyToOne
	private Belediye belediye;

	public BelediyeIletisim() {
	}

	public String getAdi() {
		return this.adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

	public String getEposta() {
		return this.eposta;
	}

	public void setEposta(String eposta) {
		this.eposta = eposta;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSoyadi() {
		return this.soyadi;
	}

	public void setSoyadi(String soyadi) {
		this.soyadi = soyadi;
	}

	public Long getTelefonno() {
		return this.telefonno;
	}

	public void setTelefonno(Long telefonno) {
		this.telefonno = telefonno;
	}

	public String getUnvani() {
		return this.unvani;
	}

	public void setUnvani(String unvani) {
		this.unvani = unvani;
	}

	public Belediye getBelediye() {
		return this.belediye;
	}

	public void setBelediye(Belediye belediye) {
		this.belediye = belediye;
	}

}