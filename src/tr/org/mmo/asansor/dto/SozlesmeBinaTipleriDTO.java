package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class SozlesmeBinaTipleriDTO implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = -8030759261072934561L;
private int id;
private String tip;
private String aciklama;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getTip() {
	return tip;
}
public void setTip(String tip) {
	this.tip = tip;
}
public String getAciklama() {
	return aciklama;
}
public void setAciklama(String aciklama) {
	this.aciklama = aciklama;
}

}
