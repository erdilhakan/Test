package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tr.org.mmo.asansor.beans_.AnaSoru;
import tr.org.mmo.asansor.util.Util;

public class KontrolDTO implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8758775150565289658L;

	@Override
	public Object clone() throws CloneNotSupportedException {

		return super.clone();
	}

	private int raporId;
	private int kontrolId;
	private int randevuId;
	private int cihazId;
	private int kontrolMuhendisiSicilNo;
	private String kimlikNo;
	private Date kontrolBaslangicTarihi;
	private Date kontrolBitisTarihi;
	private String onayDurum;
	private String etiket;
	private Date onayTarihi;
	private int onaylayanSicilNo;
	private Date kontrolBaslangicSaati;
	private Date kontrolBitisSaati;
	private boolean raporYazildi = false;
	private KontrolFirmaKatilimDTO kontrolFirmaKatilimDTO = new KontrolFirmaKatilimDTO();
	private String onaylayanAdiSoyadi;
	private String kontrolTuru;
	private List<Date> oncekiKontroller;
	private String aciklamalar;
	private boolean revizyonRapor;
	private FirmaDTO firma = new FirmaDTO();
	private List<RandevuMuhendisDTO> kontrolMuhendisleri = new ArrayList<RandevuMuhendisDTO>();
	private boolean raporIptal=false;
	public List<Date> getOncekiKontroller() {
		return oncekiKontroller;
	}

	public void setOncekiKontroller(List<Date> oncekiKontroller) {
		this.oncekiKontroller = oncekiKontroller;
	}

	public String getKontrolTuru() {
		return kontrolTuru;
	}

	public void setKontrolTuru(String kontrolTuru) {
		this.kontrolTuru = Util.toUpperCase(kontrolTuru);
	}

	public String getOnaylayanAdiSoyadi() {
		return onaylayanAdiSoyadi;
	}

	public void setOnaylayanAdiSoyadi(String onaylayanAdiSoyadi) {
		this.onaylayanAdiSoyadi = Util.toUpperCase(onaylayanAdiSoyadi);
	}

	public boolean isRaporYazildi() {
		return raporYazildi;
	}

	public void setRaporYazildi(boolean raporYazildi) {
		this.raporYazildi = raporYazildi;
	}

	public String getKimlikNo() {
		return kimlikNo;
	}

	public void setKimlikNo(String kimlikNo) {
		this.kimlikNo = kimlikNo;
	}

	public String getOnayDurum() {
		return onayDurum;
	}

	public void setOnayDurum(String onayDurum) {
		this.onayDurum = Util.toUpperCase(onayDurum);
	}

	public Date getOnayTarihi() {
		return onayTarihi;
	}

	public void setOnayTarihi(Date onayTarihi) {
		this.onayTarihi = onayTarihi;
	}

	public int getOnaylayanSicilNo() {
		return onaylayanSicilNo;
	}

	public void setOnaylayanSicilNo(int onaylayanSicilNo) {
		this.onaylayanSicilNo = onaylayanSicilNo;
	}

	private int etiketInt;

	public int getEtiketInt() {
		return etiketInt;
	}

	public void setEtiketInt(int etiketInt) {
		this.etiketInt = etiketInt;
	}

	private CihazDTO cihaz = new CihazDTO();
	private List<CihazTeknikDTO> cihazTeknikBilgiler = new ArrayList<CihazTeknikDTO>();
	private BinaDTO bina = new BinaDTO();
	private List<BinaKisiDTO> binaKisiList = new ArrayList<BinaKisiDTO>();
	private List<AnaSoru> testSorular = new ArrayList<AnaSoru>();

	public List<AnaSoru> getTestSorular() {
		return testSorular;
	}

	public void setTestSorular(List<AnaSoru> testSorular) {
		this.testSorular = testSorular;
	}

	public int getKontrolId() {
		return kontrolId;
	}

	public void setKontrolId(int kontrolId) {
		this.kontrolId = kontrolId;
	}

	public int getRandevuId() {
		return randevuId;
	}

	public void setRandevuId(int randevuId) {
		this.randevuId = randevuId;
	}

	public int getCihazId() {
		return cihazId;
	}

	public void setCihazId(int cihazId) {
		this.cihazId = cihazId;
	}

	public String getEtiket() {
		return etiket;
	}

	public void setEtiket(String etiket) {
		this.etiket = Util.toUpperCase(etiket);
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

	public List<RandevuMuhendisDTO> getKontrolMuhendisleri() {
		return kontrolMuhendisleri;
	}

	public void setKontrolMuhendisleri(
			List<RandevuMuhendisDTO> kontrolMuhendisleri) {
		this.kontrolMuhendisleri = kontrolMuhendisleri;
	}

	public FirmaDTO getFirma() {
		return firma;
	}

	public void setFirma(FirmaDTO firma) {
		this.firma = firma;
	}

	public int getKontrolMuhendisiSicilNo() {
		return kontrolMuhendisiSicilNo;
	}

	public void setKontrolMuhendisiSicilNo(int kontrolMuhendisiSicilNo) {
		this.kontrolMuhendisiSicilNo = kontrolMuhendisiSicilNo;
	}

	public KontrolFirmaKatilimDTO getKontrolFirmaKatilimDTO() {
		return kontrolFirmaKatilimDTO;
	}

	public void setKontrolFirmaKatilimDTO(
			KontrolFirmaKatilimDTO kontrolFirmaKatilimDTO) {
		this.kontrolFirmaKatilimDTO = kontrolFirmaKatilimDTO;
	}

	public int getRaporId() {
		return raporId;
	}

	public void setRaporId(int raporId) {
		this.raporId = raporId;
	}

	public boolean isRaporIptal() {
		return raporIptal;
	}

	public void setRaporIptal(boolean raporIptal) {
		this.raporIptal = raporIptal;
	}

	
	
	

}
