package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the bina database table.
 * 
 */
@Entity
@Table(name="bina",schema="test")
public class Bina implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="bina_id")
	private Integer binaId;

	@Column(name="acik_adres")
	private String acikAdres;
	
	@Column(name="sozlesme_bina_tip_id")
	private int sozlesmeBinaTipId;
	private String ada;

	private Integer adreskod;

	private Integer belediye;

	@Column(name="bina_adi")
	private String binaAdi;

	@Column(name="bina_no")
	private String binaNo;

	@Column(name="cadde_sokak")
	private String caddeSokak;

	private Integer il;

	private Integer ilce;

	private String mahalle;

	private String pafta;

	private String parsel;

	private Integer tescilno;

	@Column(name="vergi_dairesi")
	private String vergiDairesi;

	@Column(name="vergi_no")
	private String vergiNo;

	private Integer yapitip;

	//bi-directional many-to-one association to Basvuru
	@OneToMany(mappedBy="bina")
	private List<Basvuru> basvurus;

	//bi-directional many-to-one association to BinaKisi
	@OneToMany(mappedBy="bina")
	private List<BinaKisi> binaKisis;

	//bi-directional many-to-one association to Cihaz
	@OneToMany(mappedBy="bina")
	private List<Cihaz> cihazs;

	public Bina() {
	}

	public Integer getBinaId() {
		return this.binaId;
	}

	public void setBinaId(Integer binaId) {
		this.binaId = binaId;
	}

	public String getAcikAdres() {
		return this.acikAdres;
	}

	public void setAcikAdres(String acikAdres) {
		this.acikAdres = acikAdres;
	}

	public String getAda() {
		return this.ada;
	}

	public void setAda(String ada) {
		this.ada = ada;
	}

	public Integer getAdreskod() {
		return this.adreskod;
	}

	public void setAdreskod(Integer adreskod) {
		this.adreskod = adreskod;
	}

	public Integer getBelediye() {
		return this.belediye;
	}

	public void setBelediye(Integer belediye) {
		this.belediye = belediye;
	}

	public String getBinaAdi() {
		return this.binaAdi;
	}

	public void setBinaAdi(String binaAdi) {
		this.binaAdi = binaAdi;
	}

	public String getBinaNo() {
		return this.binaNo;
	}

	public void setBinaNo(String binaNo) {
		this.binaNo = binaNo;
	}

	public String getCaddeSokak() {
		return this.caddeSokak;
	}

	public void setCaddeSokak(String caddeSokak) {
		this.caddeSokak = caddeSokak;
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

	public String getMahalle() {
		return this.mahalle;
	}

	public void setMahalle(String mahalle) {
		this.mahalle = mahalle;
	}

	public String getPafta() {
		return this.pafta;
	}

	public void setPafta(String pafta) {
		this.pafta = pafta;
	}

	public String getParsel() {
		return this.parsel;
	}

	public void setParsel(String parsel) {
		this.parsel = parsel;
	}

	public Integer getTescilno() {
		return this.tescilno;
	}

	public void setTescilno(Integer tescilno) {
		this.tescilno = tescilno;
	}

	public String getVergiDairesi() {
		return this.vergiDairesi;
	}

	public void setVergiDairesi(String vergiDairesi) {
		this.vergiDairesi = vergiDairesi;
	}

	public String getVergiNo() {
		return this.vergiNo;
	}

	public void setVergiNo(String vergiNo) {
		this.vergiNo = vergiNo;
	}

	public Integer getYapitip() {
		return this.yapitip;
	}

	public void setYapitip(Integer yapitip) {
		this.yapitip = yapitip;
	}

	public List<Basvuru> getBasvurus() {
		return this.basvurus;
	}

	public void setBasvurus(List<Basvuru> basvurus) {
		this.basvurus = basvurus;
	}

	public Basvuru addBasvurus(Basvuru basvurus) {
		getBasvurus().add(basvurus);
		basvurus.setBina(this);

		return basvurus;
	}

	public Basvuru removeBasvurus(Basvuru basvurus) {
		getBasvurus().remove(basvurus);
		basvurus.setBina(null);

		return basvurus;
	}

	public List<BinaKisi> getBinaKisis() {
		return this.binaKisis;
	}

	public void setBinaKisis(List<BinaKisi> binaKisis) {
		this.binaKisis = binaKisis;
	}

	public BinaKisi addBinaKisi(BinaKisi binaKisi) {
		getBinaKisis().add(binaKisi);
		binaKisi.setBina(this);

		return binaKisi;
	}

	public BinaKisi removeBinaKisi(BinaKisi binaKisi) {
		getBinaKisis().remove(binaKisi);
		binaKisi.setBina(null);

		return binaKisi;
	}

	public List<Cihaz> getCihazs() {
		return this.cihazs;
	}

	public void setCihazs(List<Cihaz> cihazs) {
		this.cihazs = cihazs;
	}

	public Cihaz addCihaz(Cihaz cihaz) {
		getCihazs().add(cihaz);
		cihaz.setBina(this);

		return cihaz;
	}

	public Cihaz removeCihaz(Cihaz cihaz) {
		getCihazs().remove(cihaz);
		cihaz.setBina(null);

		return cihaz;
	}

	public int getSozlesmeBinaTipId() {
		return sozlesmeBinaTipId;
	}

	public void setSozlesmeBinaTipId(int sozlesmeBinaTipId) {
		this.sozlesmeBinaTipId = sozlesmeBinaTipId;
	}
	
	

}