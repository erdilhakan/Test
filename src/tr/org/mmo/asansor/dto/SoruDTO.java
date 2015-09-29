package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SoruDTO implements Serializable,Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4485516137280260724L;
	private Date tarih;
	private Integer soruId;
	private String soru;
	private Integer parent;
	private Integer cihazTipi;
	private int siraNo;
	private String sorun;
	private String yildiz;
	private String aktif;
	private String durum;
	private String aciklama;
	private boolean check=false;
	private Array kapsamArr;
	private short[] kapsam;
	private String paddingLeft = "5px";
	private int[] selectedListId=null;
	private int sinif;
	private boolean enableOnTanimli = false;
	private List<SoruOnTanimliDTO> slctdList = new ArrayList<SoruOnTanimliDTO>();
	private List<SoruOnTanimliDTO> list = new ArrayList<SoruOnTanimliDTO>();
	private int bakanlikSoruId;

	public List<SoruOnTanimliDTO> getSlctdList() {
		return slctdList;
	}

	public void setSlctdList(List<SoruOnTanimliDTO> slctdList) {
		this.slctdList = slctdList;
	}

	public List<SoruOnTanimliDTO> getList() {
		return list;
	}

	public void setList(List<SoruOnTanimliDTO> list) {
		this.list = list;
	}

	public String getPaddingLeft() {
		return paddingLeft;
	}

	public void setPaddingLeft(String paddingLeft) {
		this.paddingLeft = paddingLeft;
	}

	public String getDurum() {
		durum = durum == null ? "" : durum;
		durum = durum.replace("UYGUN DEĞİL", "Uygun Değil");
		durum = durum.replace("UYGUN", "Uygun");
		durum = durum.replace("UYGULAMASI YOK", "Uygulanmaz");
		durum = durum.replace("Uygulaması Yok", "Uygulanmaz");
		return durum;
		
	}

	public void setDurum(String durum) {
		durum = durum==null ?"":durum;
		durum = durum.replace("UYGUN DEĞİL", "Uygun Değil");
		durum = durum.replace("UYGUN", "Uygun");
		durum = durum.replace("UYGULAMASI YOK", "Uygulanmaz");
		durum = durum.replace("Uygulaması Yok", "Uygulanmaz");
		this.durum = durum;
		boolean bool = false;

		bool = durum.equals("Uygun Değil") ? true : false;
		this.enableOnTanimli=bool;

	}

	public String getAciklama() {
		return aciklama;
	}

	public void setAciklama(String aciklama) {
		this.aciklama = aciklama;
	}

	public String getSorun() {
		return sorun;
	}

	public void setSorun(String sorun) {
		this.sorun = sorun;
	}

	public Integer getSoruId() {
		return soruId;
	}

	public void setSoruId(Integer soruId) {
		this.soruId = soruId;
	}

	public void setSoru_Id(Integer soruId) {
		this.soruId = soruId;
	}

	public String getSoru() {
		return soru;
	}

	public void setSoru(String soru) {
		this.soru = soru;
	}

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public Integer getCihazTipi() {
		return cihazTipi;
	}

	public void setCihazTipi(Integer cihazTipi) {
		this.cihazTipi = cihazTipi;
	}

	public void setCihaz_Tipi(Integer cihazTipi) {
		this.cihazTipi = cihazTipi;
	}

	public String getYildiz() {
		return yildiz;
	}

	public void setYildiz(String yildiz) {
		this.yildiz = yildiz;
	}

	public String getAktif() {
		return aktif;
	}

	public void setAktif(String aktif) {
		this.aktif = aktif;
	}

	public String getStyle() {
		String style = String.format("padding-left :%s;", paddingLeft);
		return style;
	}

	public int[] getSelectedListId() {
		return selectedListId;
	}

	public void setSelectedListId(int[] selectedListId) {
		this.selectedListId = selectedListId;
		
	}

	public int getSiraNo() {
		return siraNo;
	}

	public void setSiraNo(int siraNo) {
		this.siraNo = siraNo;
	}

	public boolean isEnableOnTanimli() {
		return enableOnTanimli;
	}

	public void setEnableOnTanimli(boolean enableOnTanimli) {
		this.enableOnTanimli = enableOnTanimli;
	}

	public short[] getKapsam() {
		return kapsam;
	}

	public void setKapsam(short[] kapsam) {
		this.kapsam = kapsam;
	}

	public Array getKapsamArr() {
		return kapsamArr;
	}

	public void setKapsamArr(Array kapsamArr) {
		this.kapsamArr = kapsamArr;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public Date getTarih() {
		return tarih;
	}

	public void setTarih(Date tarih) {
		this.tarih = tarih;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public int getSinif() {
		return sinif;
	}

	public void setSinif(int sinif) {
		this.sinif = sinif;
	}

	public int getBakanlikSoruId() {
		return bakanlikSoruId;
	}

	public void setBakanlikSoruId(int bakanlikSoruId) {
		this.bakanlikSoruId = bakanlikSoruId;
	}
	
	

}
