package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class TestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1963903599588855060L;
	private int cevapId;

	private int soruId;
	private int parentId;
	private String parent;
	private String cevap;
	private String aciklama;
	private String soru;

	public int getCevapId() {
		return cevapId;
	}

	public void setCevapId(int cevapId) {
		this.cevapId = cevapId;
	}

	public int getSoruId() {
		return soruId;
	}

	public void setSoruId(int soruId) {
		this.soruId = soruId;
	}

	public String getCevap() {
		return cevap;
	}

	public void setCevap(String cevap) {
		this.cevap = cevap;
	}

	public String getAciklama() {
		return aciklama;
	}

	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}

	public String getSoru() {
		return soru;
	}

	public void setSoru(String soru) {
		this.soru = soru;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

}
