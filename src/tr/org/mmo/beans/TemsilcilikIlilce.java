package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the temsilcilik_ililce database table.
 * 
 */
@Entity
@Table(name="temsilcilik_ililce",schema="test")
public class TemsilcilikIlilce implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer il;

	private Integer ilce;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer kod;

	private Integer temsilcilik;

	public TemsilcilikIlilce() {
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

	public Integer getKod() {
		return this.kod;
	}

	public void setKod(Integer kod) {
		this.kod = kod;
	}

	public Integer getTemsilcilik() {
		return this.temsilcilik;
	}

	public void setTemsilcilik(Integer temsilcilik) {
		this.temsilcilik = temsilcilik;
	}

}