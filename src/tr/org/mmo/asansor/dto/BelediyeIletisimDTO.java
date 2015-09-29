package tr.org.mmo.asansor.dto;

import java.io.Serializable;

import tr.org.mmo.asansor.util.Util;

public class BelediyeIletisimDTO implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 4236903743490196155L;
private int belediyeKod;
private String adi;
private String soyadi;
private String unvani;
private long telefonNo;
private String ePosta;
private String telefonNoStr;
private int id;
public int getBelediyeKod() {
	return belediyeKod;
}
public void setBelediyeKod(int belediyeKod) {
	this.belediyeKod = belediyeKod;
}
public String getAdi() {
	return adi;
}
public void setAdi(String adi) {
	this.adi = Util.toUpperCase(adi);
}
public String getSoyadi() {
	return soyadi;
}
public void setSoyadi(String soyadi) {
	this.soyadi = Util.toUpperCase(soyadi);
}
public String getUnvani() {
	return unvani;
}
public void setUnvani(String unvani) {
	this.unvani = Util.toUpperCase(unvani);
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
public String getTelefonNoStr() {
	return telefonNoStr;
}
public void setTelefonNoStr(String telefonNoStr) {
	this.telefonNoStr = telefonNoStr;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}



}
