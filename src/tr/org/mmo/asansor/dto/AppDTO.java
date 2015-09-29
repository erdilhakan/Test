package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class AppDTO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = -4803650779505719184L;
private int kod;
private String adi;
public int getKod() {
	return kod;
}
public void setKod(int kod) {
	this.kod = kod;
}
public String getAdi() {
	return adi;
}
public void setAdi(String adi) {
	this.adi = adi;
}

}
