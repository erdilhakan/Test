package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class BakimciFirmaIletisimDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7865593907784580226L;
	private int id;
	private int bakimciFirmaKod;
	private String adi;
	private String soyadi;
	private long telefonNo;
	private long telefonNoGsm;
	private String telefonNoStr;
	private String telefonNoGsmStr;
	private String ePosta;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBakimciFirmaKod() {
		return bakimciFirmaKod;
	}
	public void setBakimciFirmaKod(int bakimciFirmaKod) {
		this.bakimciFirmaKod = bakimciFirmaKod;
	}
	public String getAdi() {
		return adi;
	}
	public void setAdi(String adi) {
		this.adi = adi;
	}
	public String getSoyadi() {
		return soyadi;
	}
	public void setSoyadi(String soyadi) {
		this.soyadi = soyadi;
	}
	public long getTelefonNo() {
		return telefonNo;
	}
	public void setTelefonNo(long telefonNo) {
		this.telefonNo = telefonNo;
		this.telefonNoStr=String.valueOf(telefonNo);
	}
	public long getTelefonNoGsm() {
		return telefonNoGsm;
	}
	public void setTelefonNoGsm(long telefonNoGsm) {
		this.telefonNoGsm = telefonNoGsm;
		this.telefonNoGsmStr=String.valueOf(telefonNoGsm);
	}
	public String getTelefonNoStr() {
		return telefonNoStr;
	}
	public void setTelefonNoStr(String telefonNoStr) {
		this.telefonNoStr = telefonNoStr;
		this.telefonNo=Long.parseLong(telefonNoStr==null?"0":telefonNoStr.trim()==""?"0":telefonNoStr.replace("(","").replace(")", "").replace(" ","").trim());
	}
	public String getTelefonNoGsmStr() {
		return telefonNoGsmStr;
	}
	public void setTelefonNoGsmStr(String telefonNoGsmStr) {
		this.telefonNoGsmStr = telefonNoGsmStr;
		this.telefonNoGsm=Long.parseLong(telefonNoGsmStr==null?"0":telefonNoGsmStr.trim()==""?"0":telefonNoGsmStr.replace("(","").replace(")", "").replace(" ","").trim());
	}
	public String getePosta() {
		return ePosta;
	}
	public void setePosta(String ePosta) {
		this.ePosta = ePosta;
	}
	
	
	

}
