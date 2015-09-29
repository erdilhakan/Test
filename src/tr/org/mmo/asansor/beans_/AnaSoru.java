package tr.org.mmo.asansor.beans_;

import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tr.org.mmo.asansor.dto.SoruDTO;
import tr.org.mmo.asansor.dto.SoruOnTanimliDTO;
import tr.org.mmo.asansor.models.SoruDataModel;

public class AnaSoru implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8218791468865509385L;
	private Date tarih;
	private Integer soruId;
	private String soru;
	private Integer parent;
	private Integer cihazTipi;
	private String sorun;
	private String yildiz;
	private int siraNo;
	private Array kapsamArr;
	private short[] kapsam;
	private String durum;
	private boolean checked = false;
	private int sinif;
	private int bakanlikSoruId;
	

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

	public String getSorun() {
		return sorun;
	}

	public void setSorun(String sorun) {
		this.sorun = sorun;
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

	public List<SoruDTO> getAltSorular() {
		return altSorular;
	}

	public void setAltSorular(List<SoruDTO> altSorular) {
		this.altSorular = altSorular;
		// altSorularModel=new SoruDataModel(new ArrayList<SoruDTO>());

		if (altSorular.size() > 0){
			
			altSorularModel = new SoruDataModel(altSorular);
		}
	}

	public int getSiraNo() {
		return siraNo;
	}

	public void setSiraNo(int siraNo) {
		this.siraNo = siraNo;
	}

	private String aktif;
	private List<SoruDTO> altSorular = new ArrayList<SoruDTO>();
	private List<SoruDTO> optionalAltSorular = new ArrayList<SoruDTO>();
	private SoruDataModel altSorularModel = new SoruDataModel();

	public SoruDataModel getAltSorularModel() {
		return altSorularModel;
	}

	public void setAltSorularModel(SoruDataModel altSorularModel) {
		this.altSorularModel = altSorularModel;
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
		durum = durum == null ? "" : durum;
		durum = durum.replace("UYGUN DEĞİL", "Uygun Değil");
		durum = durum.replace("UYGUN", "Uygun");
		durum = durum.replace("UYGULAMASI YOK", "Uygulanmaz");
		durum = durum.replace("Uygulaması Yok", "Uygulanmaz");
		this.durum = durum;
		boolean bool = false;

		bool = durum.equals("Uygun Değil") ? true : false;
		checked = durum.contains("U") ? true : false;
		for (SoruDTO s : altSorular) {
			s.setEnableOnTanimli(bool);
			if (!bool) {
				s.setSlctdList(new ArrayList<SoruOnTanimliDTO>());
				s.setSelectedListId(new int[1]);
			}
		}

		for (SoruDTO s : optionalAltSorular) {
			s.setEnableOnTanimli(bool);
			if (!bool) {
				s.setSlctdList(new ArrayList<SoruOnTanimliDTO>());
				s.setSelectedListId(new int[1]);
			}
		}
	}

	
	public List<SoruDTO> getOptionalAltSorular() {
		return optionalAltSorular;
	}

	public void setOptionalAltSorular(List<SoruDTO> optionalAltSorular) {
		this.optionalAltSorular = optionalAltSorular;
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

	@Override
	public Object clone() throws CloneNotSupportedException {

		return super.clone();
	}
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Date getTarih() {
		return tarih;
	}

	public void setTarih(Date tarih) {
		this.tarih = tarih;
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
