package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the odeme database table.
 * 
 */
@Entity
@Table(name="odeme",schema="test")
public class Odeme implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Temporal(TemporalType.DATE)
	@Column(name="odeme_tarihi")
	private Date odemeTarihi;

	@Column(name="odeme_tutari")
	private double odemeTutari;

	private Boolean odemekontroldealinsin;

	@Column(name="toplam_tutar")
	private double toplamTutar;

	//bi-directional many-to-one association to Basvuru
	@ManyToOne
	@JoinColumn(name="basvuru_id")
	private Basvuru basvuru;

	public Odeme() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getOdemeTarihi() {
		return this.odemeTarihi;
	}

	public void setOdemeTarihi(Date odemeTarihi) {
		this.odemeTarihi = odemeTarihi;
	}

	public double getOdemeTutari() {
		return this.odemeTutari;
	}

	public void setOdemeTutari(double odemeTutari) {
		this.odemeTutari = odemeTutari;
	}

	public Boolean getOdemekontroldealinsin() {
		return this.odemekontroldealinsin;
	}

	public void setOdemekontroldealinsin(Boolean odemekontroldealinsin) {
		this.odemekontroldealinsin = odemekontroldealinsin;
	}

	public double getToplamTutar() {
		return this.toplamTutar;
	}

	public void setToplamTutar(double toplamTutar) {
		this.toplamTutar = toplamTutar;
	}

	public Basvuru getBasvuru() {
		return this.basvuru;
	}

	public void setBasvuru(Basvuru basvuru) {
		this.basvuru = basvuru;
	}

}