package tr.org.mmo.asansor.dto;

import java.io.Serializable;

import tr.org.mmo.asansor.util.Util;

public class BinaSorumlulukDTO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 5046773682285311627L;
private int id;
private int tip;
private String aciklama;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getTip() {
	return tip;
}
public void setTip(int tip) {
	this.tip = tip;
}
public String getAciklama() {
	return aciklama;
}
public void setAciklama(String aciklama) {
	this.aciklama = Util.toUpperCase(aciklama);
}


}
