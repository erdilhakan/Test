package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the basvuru_asansor database table.
 * 
 */
@Entity
@Table(name="basvuru_asansor",schema="test")
public class BasvuruAsansor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="basvuru_id")
	private Integer basvuruId;

	@Column(name="cihaz_id")
	private Integer cihazId;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;

	@Column(name="kontrol_tutari")
	private double kontrolTutari;

	public BasvuruAsansor() {
	}

	public Integer getBasvuruId() {
		return this.basvuruId;
	}

	public void setBasvuruId(Integer basvuruId) {
		this.basvuruId = basvuruId;
	}

	public Integer getCihazId() {
		return this.cihazId;
	}

	public void setCihazId(Integer cihazId) {
		this.cihazId = cihazId;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getKontrolTutari() {
		return this.kontrolTutari;
	}

	public void setKontrolTutari(double kontrolTutari) {
		this.kontrolTutari = kontrolTutari;
	}

}