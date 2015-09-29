package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cihaz_deger database table.
 * 
 */
@Entity
@Table(name="cihaz_deger",schema="test")
public class CihazDeger implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="cevap_id")
	private Integer cevapId;

	private String cevap;

	@Column(name="cihaz_id")
	private Integer cihazId;

	@Column(name="deger_id")
	private Integer degerId;

	public CihazDeger() {
	}

	public Integer getCevapId() {
		return this.cevapId;
	}

	public void setCevapId(Integer cevapId) {
		this.cevapId = cevapId;
	}

	public String getCevap() {
		return this.cevap;
	}

	public void setCevap(String cevap) {
		this.cevap = cevap;
	}

	public Integer getCihazId() {
		return this.cihazId;
	}

	public void setCihazId(Integer cihazId) {
		this.cihazId = cihazId;
	}

	public Integer getDegerId() {
		return this.degerId;
	}

	public void setDegerId(Integer degerId) {
		this.degerId = degerId;
	}

}