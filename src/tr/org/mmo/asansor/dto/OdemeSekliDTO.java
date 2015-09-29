package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class OdemeSekliDTO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 5351934284428403802L;
private int id;
private String odemeSekli;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getOdemeSekli() {
	return odemeSekli;
}
public void setOdemeSekli(String odemeSekli) {
	this.odemeSekli = odemeSekli;
}

}
