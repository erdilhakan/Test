package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the kullanici database table.
 * 
 */
@Entity
@Table(name="kullanici",schema="test")
public class Kullanici implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	@Column(name="kullanici_id")
	private Integer kullaniciId;

	private String adi;

	private String durum;

	private String eposta;

	@Column(name="kullanici_adi")
	private String kullaniciAdi;

	private String parola;

	private Integer sicilno;

	private String soyadi;

	//bi-directional many-to-one association to KullaniciRol
	@OneToMany(mappedBy="kullanici")
	private List<KullaniciRol> kullaniciRols;

	//bi-directional many-to-one association to KullaniciYetkiliBirim
	@OneToMany(mappedBy="kullanici")
	private List<KullaniciYetkiliBirim> kullaniciYetkiliBirims;

	public Kullanici() {
	}

	public Integer getKullaniciId() {
		return this.kullaniciId;
	}

	public void setKullaniciId(Integer kullaniciId) {
		this.kullaniciId = kullaniciId;
	}

	public String getAdi() {
		return this.adi;
	}

	public void setAdi(String adi) {
		this.adi = adi;
	}

	public String getDurum() {
		return this.durum;
	}

	public void setDurum(String durum) {
		this.durum = durum;
	}

	public String getEposta() {
		return this.eposta;
	}

	public void setEposta(String eposta) {
		this.eposta = eposta;
	}

	public String getKullaniciAdi() {
		return this.kullaniciAdi;
	}

	public void setKullaniciAdi(String kullaniciAdi) {
		this.kullaniciAdi = kullaniciAdi;
	}

	public String getParola() {
		return this.parola;
	}

	public void setParola(String parola) {
		this.parola = parola;
	}

	public Integer getSicilno() {
		return this.sicilno;
	}

	public void setSicilno(Integer sicilno) {
		this.sicilno = sicilno;
	}

	public String getSoyadi() {
		return this.soyadi;
	}

	public void setSoyadi(String soyadi) {
		this.soyadi = soyadi;
	}

	public List<KullaniciRol> getKullaniciRols() {
		return this.kullaniciRols;
	}

	public void setKullaniciRols(List<KullaniciRol> kullaniciRols) {
		this.kullaniciRols = kullaniciRols;
	}

	public KullaniciRol addKullaniciRol(KullaniciRol kullaniciRol) {
		getKullaniciRols().add(kullaniciRol);
		kullaniciRol.setKullanici(this);

		return kullaniciRol;
	}

	public KullaniciRol removeKullaniciRol(KullaniciRol kullaniciRol) {
		getKullaniciRols().remove(kullaniciRol);
		kullaniciRol.setKullanici(null);

		return kullaniciRol;
	}

	public List<KullaniciYetkiliBirim> getKullaniciYetkiliBirims() {
		return this.kullaniciYetkiliBirims;
	}

	public void setKullaniciYetkiliBirims(List<KullaniciYetkiliBirim> kullaniciYetkiliBirims) {
		this.kullaniciYetkiliBirims = kullaniciYetkiliBirims;
	}

	public KullaniciYetkiliBirim addKullaniciYetkiliBirim(KullaniciYetkiliBirim kullaniciYetkiliBirim) {
		getKullaniciYetkiliBirims().add(kullaniciYetkiliBirim);
		kullaniciYetkiliBirim.setKullanici(this);

		return kullaniciYetkiliBirim;
	}

	public KullaniciYetkiliBirim removeKullaniciYetkiliBirim(KullaniciYetkiliBirim kullaniciYetkiliBirim) {
		getKullaniciYetkiliBirims().remove(kullaniciYetkiliBirim);
		kullaniciYetkiliBirim.setKullanici(null);

		return kullaniciYetkiliBirim;
	}

}