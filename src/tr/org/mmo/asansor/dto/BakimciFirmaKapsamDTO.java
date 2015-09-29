package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class BakimciFirmaKapsamDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int firmaKod;
	private int il;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFirmaKod() {
		return firmaKod;
	}
	public void setFirmaKod(int firmaKod) {
		this.firmaKod = firmaKod;
	}
	public int getIl() {
		return il;
	}
	public void setIl(int il) {
		this.il = il;
	}
	

}
