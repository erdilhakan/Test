package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class RolYetkiRelationDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 878399928702656432L;

	private int id;
	private int rolId;
	private int yetkiId;
	private String yetkiAdi;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRolId() {
		return rolId;
	}

	public void setRolId(int rolId) {
		this.rolId = rolId;
	}

	public int getYetkiId() {
		return yetkiId;
	}

	public void setYetkiId(int yetkiId) {
		this.yetkiId = yetkiId;
	}

	public String getYetkiAdi() {
		return yetkiAdi;
	}

	public void setYetkiAdi(String yetkiAdi) {
		this.yetkiAdi = yetkiAdi;
	}

}
