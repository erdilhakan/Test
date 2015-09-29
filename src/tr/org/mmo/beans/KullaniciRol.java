package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the kullanici_rol database table.
 * 
 */
@Entity
@Table(name="kullanici_rol",schema="test")
public class KullaniciRol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="kullanici_rol_id")
	private Integer kullaniciRolId;

	//bi-directional many-to-one association to Kullanici
	@ManyToOne
	@JoinColumn(name="kullanici_id")
	private Kullanici kullanici;

	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name="rol_id")
	private Rol rol;

	public KullaniciRol() {
	}

	public Integer getKullaniciRolId() {
		return this.kullaniciRolId;
	}

	public void setKullaniciRolId(Integer kullaniciRolId) {
		this.kullaniciRolId = kullaniciRolId;
	}

	public Kullanici getKullanici() {
		return this.kullanici;
	}

	public void setKullanici(Kullanici kullanici) {
		this.kullanici = kullanici;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

}