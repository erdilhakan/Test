package tr.org.mmo.asansor.util;

import java.util.Date;

import btest.ReferansKontrolSorular;

public class TestSorulari {

		
		
		public TestSorulari(ReferansKontrolSorular r){
			this.durum=r.getAktif()!=null?r.getAktif().getValue():false;
			this.asansorTuru=r.getAsansorTuru()!=null?r.getAsansorTuru().getValue():0;
			this.gecerlilikTarihi=r.getGecerlilikTarih()!=null?r.getGecerlilikTarih().getValue().toGregorianCalendar().getTime():new Date();
			this.grubu=r.getGrubu()!=null?r.getGrubu().getValue():"";
			
			this.id=r.getId()!=null?r.getId():0;
			this.soruAciklama=r.getSoruAciklama()!=null?r.getSoruAciklama().getValue():"";
			this.kritikSeviyeId=r.getKritiklikSeviyeId()!=null?r.getKritiklikSeviyeId().getValue().intValue():0;
			
		}
		
		private int id;
		private String soruAciklama;
		private boolean durum;
		private int kritikSeviyeId;
		private String grubu;
		private Date gecerlilikTarihi;
		private int asansorTuru;
		
		
		public boolean isDurum() {
			return durum;
		}
		public void setDurum(boolean durum) {
			this.durum = durum;
		}
		public int getKritikSeviyeId() {
			return kritikSeviyeId;
		}
		public void setKritikSeviyeId(int kritikSeviyeId) {
			this.kritikSeviyeId = kritikSeviyeId;
		}
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getSoruAciklama() {
			return soruAciklama;
		}
		public void setSoruAciklama(String soruAciklama) {
			this.soruAciklama = soruAciklama;
		}
		public String getGrubu() {
			return grubu;
		}
		public void setGrubu(String grubu) {
			this.grubu = grubu;
		}
		public Date getGecerlilikTarihi() {
			return gecerlilikTarihi;
		}
		public void setGecerlilikTarihi(Date gecerlilikTarihi) {
			this.gecerlilikTarihi = gecerlilikTarihi;
		}
		public int getAsansorTuru() {
			return asansorTuru;
		}
		public void setAsansorTuru(int asansorTuru) {
			this.asansorTuru = asansorTuru;
		}
		
		
		
	
	
}
