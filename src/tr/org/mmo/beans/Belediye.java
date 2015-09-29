package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the belediye database table.
 * 
 */
@Entity
@Table(name="belediye",schema="test")
public class Belediye implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer kod;

	private String adi;

	private Integer il;

	private Integer ilce;

	private Integer sube;

	private Integer temsilcilik;

	//bi-directional many-to-one association to BelediyeIletisim
	@OneToMany(mappedBy="belediye")
	private List<BelediyeIletisim> belediyeIletisims;

	//bi-directional many-to-one association to BelediyeSozlesme
	@OneToMany(mappedBy="belediye")
	private List<BelediyeSozlesme> belediyeSozlesmes;

	public Belediye() {
	}

	public Integer getKod() {
		return this.kod;
	}

	public void setKod(Integer kod) {
		this.kod = kod;
	}

	public String getAdi() {
		return this.adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
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

	public List<BelediyeIletisim> getBelediyeIletisims() {
		return this.belediyeIletisims;
	}

	public void setBelediyeIletisims(List<BelediyeIletisim> belediyeIletisims) {
		this.belediyeIletisims = belediyeIletisims;
	}

	public BelediyeIletisim addBelediyeIletisim(BelediyeIletisim belediyeIletisim) {
		getBelediyeIletisims().add(belediyeIletisim);
		belediyeIletisim.setBelediye(this);

		return belediyeIletisim;
	}

	public BelediyeIletisim removeBelediyeIletisim(BelediyeIletisim belediyeIletisim) {
		getBelediyeIletisims().remove(belediyeIletisim);
		belediyeIletisim.setBelediye(null);

		return belediyeIletisim;
	}

	public List<BelediyeSozlesme> getBelediyeSozlesmes() {
		return this.belediyeSozlesmes;
	}

	public void setBelediyeSozlesmes(List<BelediyeSozlesme> belediyeSozlesmes) {
		this.belediyeSozlesmes = belediyeSozlesmes;
	}

	public BelediyeSozlesme addBelediyeSozlesme(BelediyeSozlesme belediyeSozlesme) {
		getBelediyeSozlesmes().add(belediyeSozlesme);
		belediyeSozlesme.setBelediye(this);

		return belediyeSozlesme;
	}

	public BelediyeSozlesme removeBelediyeSozlesme(BelediyeSozlesme belediyeSozlesme) {
		getBelediyeSozlesmes().remove(belediyeSozlesme);
		belediyeSozlesme.setBelediye(null);

		return belediyeSozlesme;
	}

}