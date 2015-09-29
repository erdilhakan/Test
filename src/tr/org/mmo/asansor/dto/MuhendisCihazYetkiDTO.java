package tr.org.mmo.asansor.dto;

import java.io.Serializable;

public class MuhendisCihazYetkiDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2861014739748266165L;
	private int id;
	private int muhendisSicilNo;
	private int cihazTipi;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMuhendisSicilNo() {
		return muhendisSicilNo;
	}

	public void setMuhendisSicilNo(int muhendisSicilNo) {
		this.muhendisSicilNo = muhendisSicilNo;
	}

	public int getCihazTipi() {
		return cihazTipi;
	}

	public void setCihazTipi(int cihazTipi) {
		this.cihazTipi = cihazTipi;
	}

}
