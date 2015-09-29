package tr.org.mmo.asansor.dto;

import java.io.Serializable;

import tr.org.mmo.asansor.util.Util;

public class YapiKonusuDTO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 3879532642747515011L;
private int id;
private String tip;

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
	this.tip = Util.toUpperCase(tip);
}




}
