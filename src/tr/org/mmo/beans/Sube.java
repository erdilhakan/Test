package tr.org.mmo.beans;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sube database table.
 * 
 */
@Entity
@Table(name="sube",schema="test")
public class Sube implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer kod;

	private Integer donem;

	private String eposta;

	private Integer il;

	private String kisituru;

	private Integer sicilno;

	private String sube;

	public Sube() {
	}

	public Integer getKod() {
		return this.kod;
	}

	public void setKod(Integer kod) {
		this.kod = kod;
	}

	public Integer getDonem() {
		return this.donem;
	}

	public void setDonem(Integer donem) {
		this.donem = donem;
	}

	public String getEposta() {
		return this.eposta;
	}

	public void setEposta(String eposta) {
		this.eposta = eposta;
	}

	public Integer getIl() {
		return this.il;
	}

	public void setIl(Integer il) {
		this.il = il;
	}

	public String getKisituru() {
		return this.kisituru;
	}

	public void setKisituru(String kisituru) {
		this.kisituru = kisituru;
	}

	public Integer getSicilno() {
		return this.sicilno;
	}

	public void setSicilno(Integer sicilno) {
		this.sicilno = sicilno;
	}

	public String getSube() {
		return this.sube;
	}

	public void setSube(String sube) {
		this.sube = sube;
	}

}