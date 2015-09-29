package tr.org.mmo.asansor.dto;

import java.util.ArrayList;
import java.util.List;

public class KontrolTestDTO {

private List<OnTanimliTestDTO> altSorular=new ArrayList<OnTanimliTestDTO>();
private int cevapId;
private int kontrolId;
private int soruId;
private String cevap;
private String aciklama;
private String soru;
private String parent;
private int bakanlikSoruId;


public List<OnTanimliTestDTO> getAltSorular() {
	return altSorular;
}
public void setAltSorular(List<OnTanimliTestDTO> altSorular) {
	this.altSorular = altSorular;
}
public int getCevapId() {
	return cevapId;
}
public void setCevapId(int cevapId) {
	this.cevapId = cevapId;
}
public int getKontrolId() {
	return kontrolId;
}
public void setKontrolId(int kontrolId) {
	this.kontrolId = kontrolId;
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
public String getParent() {
	return parent;
}
public void setParent(String parent) {
	this.parent = parent;
}
public int getBakanlikSoruId() {
	return bakanlikSoruId;
}
public void setBakanlikSoruId(int bakanlikSoruId) {
	this.bakanlikSoruId = bakanlikSoruId;
}



}
