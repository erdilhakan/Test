package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class TemsilcilikIlIlceDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int kod;
	private int temsilcilik;
	private int il;
	private int ilce;

	public int getKod() {
		return kod;
	}

	public void setKod(int kod) {
		this.kod = kod;
	}

	public int getTemsilcilik() {
		return temsilcilik;
	}

	public void setTemsilcilik(int temsilcilik) {
		this.temsilcilik = temsilcilik;
	}

	public int getIl() {
		return il;
	}

	public void setIl(int il) {
		this.il = il;
	}

	public int getIlce() {
		return ilce;
	}

	public void setIlce(int ilce) {
		this.ilce = ilce;
	}

}
