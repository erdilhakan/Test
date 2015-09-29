package tr.org.mmo.asansor.beans_;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tr.org.mmo.asansor.dto.MenuItemDTO;

public class AnaMenu implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2851123306733378124L;

	private Integer menuitemId;
	private String baslik;
	private Integer ustmenuitemId;
	private String link;
	private String siraNo;
	
	private  List<MenuItemDTO> altMenuler=new ArrayList<MenuItemDTO>();
	public Integer getMenuitemId() {
		return menuitemId;
	}
	public void setMenuitemId(Integer menuitemId) {
		this.menuitemId = menuitemId;
	}
	public void setMenuitem_Id(Integer menuitemId) {
		this.menuitemId = menuitemId;
	}
	public String getBaslik() {
		return baslik;
	}
	public void setBaslik(String baslik) {
		this.baslik = baslik;
	}
	public Integer getUstmenuitemId() {
		return ustmenuitemId;
	}
	public Integer getUstmenuitem_Id() {
		return ustmenuitemId;
	}
	public void setUstmenuitemId(Integer ustmenuitemId) {
		this.ustmenuitemId = ustmenuitemId;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getSiraNo() {
		return siraNo;
	
	}
	public void setSiraNo(String siraNo) {
		this.siraNo = siraNo;
	}
	public void setSira_No(String siraNo) {
		this.siraNo = siraNo;
	}
	public List<MenuItemDTO> getAltMenuler() {
		return altMenuler;
	}
	public void setAltMenuler(List<MenuItemDTO> altMenuler) {
		this.altMenuler = altMenuler;
	}
	
}
