package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the rol database table.
 * 
 */
@Entity
@Table(name="rol",schema="test")
public class Rol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="rol_id")
	private Integer rolId;

	@Column(name="rol_adi")
	private String rolAdi;

	//bi-directional many-to-one association to KullaniciRol
	@OneToMany(mappedBy="rol")
	private List<KullaniciRol> kullaniciRols;

	public Rol() {
	}

	public Integer getRolId() {
		return this.rolId;
	}

	public void setRolId(Integer rolId) {
		this.rolId = rolId;
	}

	public String getRolAdi() {
		return this.rolAdi;
	}

	public void setRolAdi(String rolAdi) {
		this.rolAdi = rolAdi;
	}

	public List<KullaniciRol> getKullaniciRols() {
		return this.kullaniciRols;
	}

	public void setKullaniciRols(List<KullaniciRol> kullaniciRols) {
		this.kullaniciRols = kullaniciRols;
	}

	public KullaniciRol addKullaniciRol(KullaniciRol kullaniciRol) {
		getKullaniciRols().add(kullaniciRol);
		kullaniciRol.setRol(this);

		return kullaniciRol;
	}

	public KullaniciRol removeKullaniciRol(KullaniciRol kullaniciRol) {
		getKullaniciRols().remove(kullaniciRol);
		kullaniciRol.setRol(null);

		return kullaniciRol;
	}

}