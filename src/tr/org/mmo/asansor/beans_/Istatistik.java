package tr.org.mmo.asansor.beans_;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.dto.BinaKisiDTO;
import tr.org.mmo.asansor.dto.CihazDTO;
import tr.org.mmo.asansor.dto.CihazTeknikDTO;
import tr.org.mmo.asansor.dto.FirmaDTO;
import tr.org.mmo.asansor.dto.RandevuMuhendisDTO;

public class Istatistik implements Serializable,Cloneable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -6454914247142795101L;
	private int randevuAdet;
	private int eksiklikKontrolu;
	private int normalKontrol;
	private int asansorAdet;
	private int raporId;
	private int kontrolId;
	private byte[] dosya;
	private String dosyaAdi;
	private Date raporTarihi;
	@Override
	public Object clone() throws CloneNotSupportedException {
		
		return super.clone();
	}

	private Date onayTarihi;
	private String onayDurum;
	private String binaAdi;
	private int onaylayanSicilNo;
	private String etiket;
	private Date kontrolTarihi;
	private Date kontrolSaati;
	private boolean raporOnayDisabled = true;
	private String asansorunYeri;
	private String muhendis;
	private String onaylayan;
	private int basvuruId;
	private int randevuId;
	private Date randevuTarihi;
	private Date basvuruTarihi;
	private String asansorKimlikNo;
	private int binaId;
	private String mahalle;
	private String caddeSokak;
	private String ilce;
	private String il;
	private String binaNo;
	private String belediyeAdi;
	private String basvuruYapan;
	private double kontrolTutari;
	private double odemeTutari;
	private CihazDTO cihaz = new CihazDTO();
	private List<CihazTeknikDTO> cihazTeknikBilgiler = new ArrayList<CihazTeknikDTO>();
	private BinaDTO bina = new BinaDTO();
	private List<BinaKisiDTO> binaKisiList = new ArrayList<BinaKisiDTO>();
	private List<AnaSoru> testSorular = new ArrayList<AnaSoru>();
	
	private int cihazId;
	private int cihazTipi;
	private int kontrolMuhendisiSicilNo;
	
	private Date kontrolBaslangicTarihi;
	private Date kontrolBitisTarihi;
	
	private Date kontrolBaslangicSaati;
	private Date kontrolBitisSaati;
	private boolean raporYazildi = false;
	private String kontrolTuru;
	private String cihazTipAciklama;
	private String aciklamalar;
	private boolean revizyonRapor;
	private FirmaDTO firma = new FirmaDTO();
	private List<RandevuMuhendisDTO> kontrolMuhendisleri = new ArrayList<RandevuMuhendisDTO>();

	public Date getKontrolSaati() {
		return kontrolSaati;
	}

	public void setKontrolSaati(Date kontrolSaati) {
		this.kontrolSaati = kontrolSaati;
	}

	public int getBasvuruId() {
		return basvuruId;
	}

	public void setBasvuruId(int basvuruId) {
		this.basvuruId = basvuruId;
	}

	public int getRandevuId() {
		return randevuId;
	}

	public void setRandevuId(int randevuId) {
		this.randevuId = randevuId;
	}

	

	public Date getRandevuTarihi() {
		return randevuTarihi;
	}

	public void setRandevuTarihi(Date randevuTarihi) {
		this.randevuTarihi = randevuTarihi;
	}

	public Date getBasvuruTarihi() {
		return basvuruTarihi;
	}

	public void setBasvuruTarihi(Date basvuruTarihi) {
		this.basvuruTarihi = basvuruTarihi;
	}

	public String getAsansorKimlikNo() {
		return asansorKimlikNo;
	}

	public void setAsansorKimlikNo(String asansorKimlikNo) {
		this.asansorKimlikNo = asansorKimlikNo;
	}

	public int getRaporId() {
		return raporId;
	}

	public void setRaporId(int raporId) {
		this.raporId = raporId;
	}

	public int getKontrolId() {
		return kontrolId;
	}

	public void setKontrolId(int kontrolId) {
		this.kontrolId = kontrolId;
	}

	public String getDosyaAdi() {
		return dosyaAdi;
	}

	public void setDosyaAdi(String dosyaAdi) {
		this.dosyaAdi = dosyaAdi;
	}

	public Date getRaporTarihi() {
		return raporTarihi;
	}

	public void setRaporTarihi(Date raporTarihi) {
		this.raporTarihi = raporTarihi;
	}

	public Date getOnayTarihi() {
		return onayTarihi;
	}

	public void setOnayTarihi(Date onayTarihi) {
		this.onayTarihi = onayTarihi;
	}

	public String getOnayDurum() {
		return onayDurum;
	}

	public void setOnayDurum(String onayDurum) {
		this.onayDurum = onayDurum;
	}

	public byte[] getDosya() {
		return dosya;
	}

	public void setDosya(byte[] dosya) {
		this.dosya = dosya;
	}

	public boolean isRaporOnayDisabled() {
		return raporOnayDisabled;
	}

	public void setRaporOnayDisabled(boolean raporOnayDisabled) {
		this.raporOnayDisabled = raporOnayDisabled;
	}

	public String getBinaAdi() {
		return binaAdi;
	}

	public void setBinaAdi(String binaAdi) {
		this.binaAdi = binaAdi;
	}

	public int getOnaylayanSicilNo() {
		return onaylayanSicilNo;
	}

	public void setOnaylayanSicilNo(int onaylayanSicilNo) {
		this.onaylayanSicilNo = onaylayanSicilNo;
	}

	public String getEtiket() {
		return etiket;
	}

	public void setEtiket(String etiket) {
		this.etiket = etiket;
	}

	public Date getKontrolTarihi() {
		return kontrolTarihi;
	}

	public void setKontrolTarihi(Date kontrolTarihi) {
		this.kontrolTarihi = kontrolTarihi;
	}

	public String getAsansorunYeri() {
		return asansorunYeri;
	}

	public void setAsansorunYeri(String asansorunYeri) {
		this.asansorunYeri = asansorunYeri;
	}

	public String getMuhendis() {
		return muhendis;
	}

	public void setMuhendis(String muhendis) {
		this.muhendis = muhendis;
	}

	public String getOnaylayan() {
		return onaylayan;
	}

	public void setOnaylayan(String onaylayan) {
		this.onaylayan = onaylayan;
	}

	public int getCihazId() {
		return cihazId;
	}

	public void setCihazId(int cihazId) {
		this.cihazId = cihazId;
	}

	public int getKontrolMuhendisiSicilNo() {
		return kontrolMuhendisiSicilNo;
	}

	public void setKontrolMuhendisiSicilNo(int kontrolMuhendisiSicilNo) {
		this.kontrolMuhendisiSicilNo = kontrolMuhendisiSicilNo;
	}

	public Date getKontrolBaslangicTarihi() {
		return kontrolBaslangicTarihi;
	}

	public void setKontrolBaslangicTarihi(Date kontrolBaslangicTarihi) {
		this.kontrolBaslangicTarihi = kontrolBaslangicTarihi;
	}

	public Date getKontrolBitisTarihi() {
		return kontrolBitisTarihi;
	}

	public void setKontrolBitisTarihi(Date kontrolBitisTarihi) {
		this.kontrolBitisTarihi = kontrolBitisTarihi;
	}

	public Date getKontrolBaslangicSaati() {
		return kontrolBaslangicSaati;
	}

	public void setKontrolBaslangicSaati(Date kontrolBaslangicSaati) {
		this.kontrolBaslangicSaati = kontrolBaslangicSaati;
	}

	public Date getKontrolBitisSaati() {
		return kontrolBitisSaati;
	}

	public void setKontrolBitisSaati(Date kontrolBitisSaati) {
		this.kontrolBitisSaati = kontrolBitisSaati;
	}

	public boolean isRaporYazildi() {
		return raporYazildi;
	}

	public void setRaporYazildi(boolean raporYazildi) {
		this.raporYazildi = raporYazildi;
	}

	public String getKontrolTuru() {
		return kontrolTuru;
	}

	public void setKontrolTuru(String kontrolTuru) {
		this.kontrolTuru = kontrolTuru;
	}

	public String getAciklamalar() {
		return aciklamalar;
	}

	public void setAciklamalar(String aciklamalar) {
		this.aciklamalar = aciklamalar;
	}

	public boolean isRevizyonRapor() {
		return revizyonRapor;
	}

	public void setRevizyonRapor(boolean revizyonRapor) {
		this.revizyonRapor = revizyonRapor;
	}

	public FirmaDTO getFirma() {
		return firma;
	}

	public void setFirma(FirmaDTO firma) {
		this.firma = firma;
	}

	public List<RandevuMuhendisDTO> getKontrolMuhendisleri() {
		return kontrolMuhendisleri;
	}

	public void setKontrolMuhendisleri(List<RandevuMuhendisDTO> kontrolMuhendisleri) {
		this.kontrolMuhendisleri = kontrolMuhendisleri;
	}

	public CihazDTO getCihaz() {
		return cihaz;
	}

	public void setCihaz(CihazDTO cihaz) {
		this.cihaz = cihaz;
	}

	public List<CihazTeknikDTO> getCihazTeknikBilgiler() {
		return cihazTeknikBilgiler;
	}

	public void setCihazTeknikBilgiler(List<CihazTeknikDTO> cihazTeknikBilgiler) {
		this.cihazTeknikBilgiler = cihazTeknikBilgiler;
	}

	public BinaDTO getBina() {
		return bina;
	}

	public void setBina(BinaDTO bina) {
		this.bina = bina;
	}

	public List<BinaKisiDTO> getBinaKisiList() {
		return binaKisiList;
	}

	public void setBinaKisiList(List<BinaKisiDTO> binaKisiList) {
		this.binaKisiList = binaKisiList;
	}

	public List<AnaSoru> getTestSorular() {
		return testSorular;
	}

	public void setTestSorular(List<AnaSoru> testSorular) {
		this.testSorular = testSorular;
	}

	public int getCihazTipi() {
		return cihazTipi;
	}

	public void setCihazTipi(int cihazTipi) {
		this.cihazTipi = cihazTipi;
	}

	public int getBinaId() {
		return binaId;
	}

	public void setBinaId(int binaId) {
		this.binaId = binaId;
	}

	public String getMahalle() {
		return mahalle;
	}

	public void setMahalle(String mahalle) {
		this.mahalle = mahalle;
	}



	public String getCaddeSokak() {
		return caddeSokak;
	}

	public void setCaddeSokak(String caddeSokak) {
		this.caddeSokak = caddeSokak;
	}

	public String getIlce() {
		return ilce;
	}

	public void setIlce(String ilce) {
		this.ilce = ilce;
	}

	public String getIl() {
		return il;
	}

	public void setIl(String il) {
		this.il = il;
	}

	public String getBinaNo() {
		return binaNo;
	}

	public void setBinaNo(String binaNo) {
		this.binaNo = binaNo;
	}

	
	public String getBelediyeAdi() {
		return belediyeAdi;
	}

	public void setBelediyeAdi(String belediyeAdi) {
		this.belediyeAdi = belediyeAdi;
	}

	public String getBasvuruYapan() {
		return basvuruYapan;
	}

	public void setBasvuruYapan(String basvuruYapan) {
		this.basvuruYapan = basvuruYapan;
	}

	public int getAsansorAdet() {
		return asansorAdet;
	}

	public void setAsansorAdet(int asansorAdet) {
		this.asansorAdet = asansorAdet;
	}

	public int getEksiklikKontrolu() {
		return eksiklikKontrolu;
	}

	public void setEksiklikKontrolu(int eksiklikKontrolu) {
		this.eksiklikKontrolu = eksiklikKontrolu;
	}

	public int getNormalKontrol() {
		return normalKontrol;
	}

	public void setNormalKontrol(int normalKontrol) {
		this.normalKontrol = normalKontrol;
	}

	public int getRandevuAdet() {
		return randevuAdet;
	}

	public void setRandevuAdet(int randevuAdet) {
		this.randevuAdet = randevuAdet;
	}

	

	public String getCihazTipAciklama() {
		return cihazTipAciklama;
	}

	public void setCihazTipAciklama(String cihazTipAciklama) {
		this.cihazTipAciklama = cihazTipAciklama;
	}

	public double getKontrolTutari() {
		return kontrolTutari;
	}

	public void setKontrolTutari(double kontrolTutari) {
		this.kontrolTutari = kontrolTutari;
	}

	public double getOdemeTutari() {
		return odemeTutari;
	}

	public void setOdemeTutari(double odemeTutari) {
		this.odemeTutari = odemeTutari;
	}

	
	
	



}
