package tr.org.mmo.asansor.beans_;

import java.io.Serializable;

import btest.*;




public class UavtBina implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8035880625157871396L;
	private String siteAdi;
	private int csbmKodu;
	private String blokAdi;
	private String diskapiNo1;
	private String diskapiNo12;
	private String diskapiNo2;
	private String esBinaKodu;
	private String nitelik;
	private String postaKodu;
	private long kod;
	
	private String disKapiNo;
	private String ada;
	private String pafta;
	private String parsel;
	private int binaNo;
	
	public UavtBina() {
		
	}
	
	public UavtBina(Bina bina) {
		
		this.siteAdi=bina.getSiteAdi()==null?"":bina.getSiteAdi().getValue();
		this.csbmKodu=bina.getCsbmKodu()==null?0:bina.getCsbmKodu().getValue();
		this.blokAdi=bina.getBlokAdi()==null?"":bina.getBlokAdi().getValue();
		this.diskapiNo1=bina.getDisKapiNo1()==null?"":bina.getDisKapiNo1().getValue();
		this.diskapiNo12=bina.getDisKapiNo12()==null?"":bina.getDisKapiNo12().getValue();
		this.diskapiNo2=bina.getDisKapiNo2()==null?"":bina.getDisKapiNo2().getValue();
		this.esBinaKodu=bina.getEsBinaKodu()==null?"":bina.getEsBinaKodu().getValue();
		this.nitelik=bina.getNitelik()==null?"":bina.getNitelik().getValue();
		this.postaKodu=bina.getPostaKodu()==null?"":bina.getPostaKodu().getValue();
		this.kod=bina.getKod();
		
		
		this.disKapiNo=bina.getDisKapiNo1()==null?(bina.getBlokAdi()==null?"":bina.getBlokAdi().getValue()):bina.getDisKapiNo1().getValue();
		
	}

	public String getSiteAdi() {
		return siteAdi;
	}

	public void setSiteAdi(String siteAdi) {
		this.siteAdi = siteAdi;
	}

	public int getCsbmKodu() {
		return csbmKodu;
	}

	public void setCsbmKodu(int csbmKodu) {
		this.csbmKodu = csbmKodu;
	}

	public String getBlokAdi() {
		return blokAdi;
	}

	public void setBlokAdi(String blokAdi) {
		this.blokAdi = blokAdi;
	}

	public String getDiskapiNo1() {
		return diskapiNo1;
	}

	public void setDiskapiNo1(String diskapiNo1) {
		this.diskapiNo1 = diskapiNo1;
	}

	public String getDiskapiNo12() {
		return diskapiNo12;
	}

	public void setDiskapiNo12(String diskapiNo12) {
		this.diskapiNo12 = diskapiNo12;
	}

	public String getDiskapiNo2() {
		return diskapiNo2;
	}

	public void setDiskapiNo2(String diskapiNo2) {
		this.diskapiNo2 = diskapiNo2;
	}

	public String getEsBinaKodu() {
		return esBinaKodu;
	}

	public void setEsBinaKodu(String esBinaKodu) {
		this.esBinaKodu = esBinaKodu;
	}

	public String getNitelik() {
		return nitelik;
	}

	public void setNitelik(String nitelik) {
		this.nitelik = nitelik;
	}

	public String getPostaKodu() {
		return postaKodu;
	}

	public void setPostaKodu(String postaKodu) {
		this.postaKodu = postaKodu;
	}

	public long getKod() {
		return kod;
	}

	public void setKod(long kod) {
		this.kod = kod;
	}

	

	public String getDisKapiNo() {
		return disKapiNo;
	}

	public void setDisKapiNo(String disKapiNo) {
		this.disKapiNo = disKapiNo;
	}

	public String getAda() {
		return ada;
	}

	public void setAda(String ada) {
		this.ada = ada;
	}

	public String getPafta() {
		return pafta;
	}

	public void setPafta(String pafta) {
		this.pafta = pafta;
	}

	public String getParsel() {
		return parsel;
	}

	public void setParsel(String parsel) {
		this.parsel = parsel;
	}

	public int getBinaNo() {
		return binaNo;
	}

	public void setBinaNo(int binaNo) {
		this.binaNo = binaNo;
	}
	
	

}
