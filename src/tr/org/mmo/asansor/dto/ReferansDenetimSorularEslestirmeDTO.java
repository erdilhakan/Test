package tr.org.mmo.asansor.dto;

import java.io.Serializable;

import btest.ReferansDenetimSorularEslestirme;

public class ReferansDenetimSorularEslestirmeDTO implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	  private int asansorTipi;
	  private int id;
	 
	  private int soruId;
	  private int tseStandartId;
	  private byte[] versiyon;
	  public ReferansDenetimSorularEslestirmeDTO(){
		  
	  }
	 public ReferansDenetimSorularEslestirmeDTO(ReferansDenetimSorularEslestirme r){
		 this.asansorTipi=r.getAsansorTipi()!=null? r.getAsansorTipi().getValue().intValue():0;
		  
		 this.id=r.getId()!=null? r.getId().intValue():0;
		 this.soruId=r.getSoruId()!=null? r.getSoruId().getValue().intValue():0;
		  this.tseStandartId=r.getTseStandartId()!=null?r.getTseStandartId().getValue().intValue():0;
		  this.versiyon=r.getVersiyon()!=null?r.getVersiyon().getValue():new byte[]{0};
	  }
	  

	public int getAsansorTipi() {
		return asansorTipi;
	}


	public void setAsansorTipi(int asansorTipi) {
		this.asansorTipi = asansorTipi;
	}


	


	


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSoruId() {
		return soruId;
	}


	public void setSoruId(int soruId) {
		this.soruId = soruId;
	}


	public int getTseStandartId() {
		return tseStandartId;
	}


	public void setTseStandartId(int tseStandartId) {
		this.tseStandartId = tseStandartId;
	}


	public byte[] getVersiyon() {
		return versiyon;
	}


	public void setVersiyon(byte[] versiyon) {
		this.versiyon = versiyon;
	}


	

}
