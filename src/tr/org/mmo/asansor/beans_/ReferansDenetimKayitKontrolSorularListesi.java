package tr.org.mmo.asansor.beans_;

import java.io.Serializable;
import java.util.Date;

import btest.ReferansKontrolSorular;

public class ReferansDenetimKayitKontrolSorularListesi implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id; 
	private int soruId;
	 private int kritikSeviyeId;
	 private String soruAciklama;
	 private Date gecerlilikTarihi;
	 private boolean aktif;
	 private int akmSoruId;
	 private String eslestirme;
	 private int cihazTipi;
	 private int parent;
	 private String grubu;
	
	public  ReferansDenetimKayitKontrolSorularListesi(){
		
	}
	public ReferansDenetimKayitKontrolSorularListesi(ReferansKontrolSorular r){
		this.aktif=r.getAktif()!=null?r.getAktif().getValue():false;
		this.cihazTipi=r.getAsansorTuru()!=null?r.getAsansorTuru().getValue():0;
		this.gecerlilikTarihi=r.getGecerlilikTarih()!=null?r.getGecerlilikTarih().getValue().toGregorianCalendar().getTime():new Date();
		this.grubu=r.getGrubu()!=null?r.getGrubu().getValue():"";
		
		this.id=r.getId()!=null?r.getId():0;
		this.soruAciklama=r.getSoruAciklama()!=null?r.getSoruAciklama().getValue():"";
		this.kritikSeviyeId=r.getKritiklikSeviyeId()!=null?r.getKritiklikSeviyeId().getValue().intValue():0;
	 }
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getKritikSeviyeId() {
		return kritikSeviyeId;
	}
	public void setKritikSeviyeId(int kritikSeviyeId) {
		this.kritikSeviyeId = kritikSeviyeId;
	}
	public String getSoruAciklama() {
		return soruAciklama;
	}
	public void setSoruAciklama(String soruAciklama) {
		this.soruAciklama = soruAciklama;
	}
	public Date getGecerlilikTarihi() {
		return gecerlilikTarihi;
	}
	public void setGecerlilikTarihi(Date gecerlilikTarihi) {
		this.gecerlilikTarihi = gecerlilikTarihi;
	}
	public boolean isAktif() {
		return aktif;
	}
	public void setAktif(boolean aktif) {
		this.aktif = aktif;
	}
	public int getAkmSoruId() {
		return akmSoruId;
	}
	public void setAkmSoruId(int akmSoruId) {
		this.akmSoruId = akmSoruId;
	}
	public String getEslestirme() {
		return eslestirme;
	}
	public void setEslestirme(String eslestirme) {
		this.eslestirme = eslestirme;
	}
	public int getCihazTipi() {
		return cihazTipi;
	}
	public void setCihazTipi(int cihazTipi) {
		this.cihazTipi = cihazTipi;
	}
	public int getSoruId() {
		return soruId;
	}
	public void setSoruId(int soruId) {
		this.soruId = soruId;
	}
	public int getParent() {
		return parent;
	}
	public void setParent(int parent) {
		this.parent = parent;
	}
	public String getGrubu() {
		return grubu;
	}
	public void setGrubu(String grubu) {
		this.grubu = grubu;
	}
	
	 
	 

}
