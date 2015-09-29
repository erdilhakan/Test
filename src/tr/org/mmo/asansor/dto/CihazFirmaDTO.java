package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CihazFirmaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8725689394634221083L;
	
	private int id;
	private int cihazId;
	private int firmaId;
	private Date sozlesmeTarih;
	private Date sozlesmeBitisTarih;
	private String tarihStr;
	DateFormat df=new SimpleDateFormat("dd.MM.yyyy");
	private String sozlesmeSure;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCihazId() {
		return cihazId;
	}
	public void setCihazId(int cihazId) {
		this.cihazId = cihazId;
	}
	public int getFirmaId() {
		return firmaId;
	}
	public void setFirmaId(int firmaId) {
		this.firmaId = firmaId;
	}
	public Date getSozlesmeTarih() {
		return sozlesmeTarih;
	}
	public void setSozlesmeTarih(Date sozlesmeTarih) {
		
		this.sozlesmeTarih = sozlesmeTarih;
		this.tarihStr=df.format(sozlesmeTarih);
	}
	
	public String getTarihStr() {
		return tarihStr;
	}
	public void setTarihStr(String tarihStr) {
		
		this.tarihStr = tarihStr;
		try {
			sozlesmeTarih=df.parse(tarihStr);
		} catch (Exception e) {
			sozlesmeTarih=null;
		}
	}
	public Date getSozlesmeBitisTarih() {
		return sozlesmeBitisTarih;
	}
	public void setSozlesmeBitisTarih(Date sozlesmeBitisTarih) {
		this.sozlesmeBitisTarih = sozlesmeBitisTarih;
	}
	public String getSozlesmeSure() {
		return sozlesmeSure;
	}
	public void setSozlesmeSure(String sozlesmeSure) {
		this.sozlesmeSure = sozlesmeSure;
	}
	
	

}
