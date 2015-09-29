package tr.org.mmo.asansor.beans_;

import java.io.Serializable;

public class RaporSource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * değişken adları ireport field isimleri ile aynı olmalı ireport' a
	 * gönderilecek datasource list generic tipi bu class olmalı
	 */
	private String sira;
	private String fKriter;
	private String fSonuc;
	private String fAciklama;

	public String getSira() {
		return sira;
	}

	public void setSira(String sira) {
		this.sira = sira;
	}

	public String getfKriter() {
		return fKriter;
	}

	public void setfKriter(String fKriter) {
		this.fKriter = fKriter;
	}

	public String getfSonuc() {
		return fSonuc;
	}

	public void setfSonuc(String fSonuc) {
		this.fSonuc = fSonuc;
	}

	public String getfAciklama() {
		return fAciklama;
	}

	public void setfAciklama(String fAciklama) {
		this.fAciklama = fAciklama;
	}

}
