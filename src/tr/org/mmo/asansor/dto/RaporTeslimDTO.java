package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.util.Date;

public class RaporTeslimDTO implements Serializable,Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5506943130144787829L;
	
	private int id;
	private int raporId;
	private String teslimEdilenKisi;
	private Date teslimTarihi;
	private long telefonNo;
	private long tcKimlikNo;
	private String telefonNoStr;
	private String aciklama;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRaporId() {
		return raporId;
	}
	public void setRaporId(int raporId) {
		this.raporId = raporId;
	}
	public String getTeslimEdilenKisi() {
		return teslimEdilenKisi;
	}
	public void setTeslimEdilenKisi(String teslimEdilenKisi) {
		this.teslimEdilenKisi = teslimEdilenKisi;
	}
	public Date getTeslimTarihi() {
		return teslimTarihi;
	}
	public void setTeslimTarihi(Date teslimTarihi) {
		this.teslimTarihi = teslimTarihi;
	}
	public long getTelefonNo() {
		return telefonNo;
	}
	public void setTelefonNo(long telefonNo) {
		this.telefonNo = telefonNo;
	}
	public long getTcKimlikNo() {
		return tcKimlikNo;
	}
	public void setTcKimlikNo(long tcKimlikNo) {
		this.tcKimlikNo = tcKimlikNo;
	}
	public String getTelefonNoStr() {
		return telefonNoStr;
	}
	public void setTelefonNoStr(String telefonNoStr) {
		this.telefonNoStr = telefonNoStr;
		this.telefonNo=Long.parseLong(telefonNoStr==null?"0":(telefonNoStr.equals("")?"0":telefonNoStr.replace("(","").replace(")", "").replace(" ","").trim()));
	}
	public String getAciklama() {
		return aciklama;
	}
	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}
