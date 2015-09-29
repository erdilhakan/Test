package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class MenuItemDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5873193593974244027L;

	private String baslik;
	private String link;
	private int menuitemId;
	private int siraNo;
	private int ustmenuitemId;
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getMenuitemId() {
		return menuitemId;
	}
	public void setMenuitemId(int menuitemId) {
		this.menuitemId = menuitemId;
	}
	public void setMenuitem_Id(int menuitemId) {
		this.menuitemId = menuitemId;
	}
	public int getSiraNo() {
		return siraNo;
	}
	public void setSiraNo(int siraNo) {
		this.siraNo = siraNo;
	}
	public void setSira_No(int siraNo) {
		this.siraNo = siraNo;
	}
	public int getUstmenuitemId() {
		return ustmenuitemId;
	}
	public void setUstmenuitemId(int ustmenuitemId) {
		this.ustmenuitemId = ustmenuitemId;
	}
	public void setUstmenuitem_Id(int ustmenuitemId) {
		this.ustmenuitemId = ustmenuitemId;
	}
	public String getBaslik() {
		return baslik;
	}
	public void setBaslik(String baslik) {
		this.baslik = baslik;
	}

	
}
