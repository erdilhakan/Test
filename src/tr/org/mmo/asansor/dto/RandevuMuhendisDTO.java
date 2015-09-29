package tr.org.mmo.asansor.dto;

import java.io.Serializable;

import tr.org.mmo.asansor.util.Util;

public class RandevuMuhendisDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5355385763460366587L;
	private int sicilNo;
	private String adiSoyadi;
	private long telefonNo;
	private String ePosta;
	private String sorumlu;
	private String unvan;

	public int getSicilNo() {
		return sicilNo;
	}

	public void setSicilNo(int sicilNo) {
		this.sicilNo = sicilNo;
	}

	public String getAdiSoyadi() {
		return adiSoyadi;
	}

	public void setAdiSoyadi(String adiSoyadi) {
		this.adiSoyadi = Util.toUpperCase(adiSoyadi);
	}

	public long getTelefonNo() {
		return telefonNo;
	}

	public void setTelefonNo(long telefonNo) {
		this.telefonNo = telefonNo;
	}

	public String getePosta() {
		return ePosta;
	}

	public void setePosta(String ePosta) {
		this.ePosta = ePosta;
	}

	public String getSorumlu() {
		return sorumlu;
	}

	public void setSorumlu(String sorumlu) {
		this.sorumlu = sorumlu;
	}

	public String getUnvan() {
		return unvan;
	}

	public void setUnvan(String unvan) {
		this.unvan = unvan;
	}
	
	

}
