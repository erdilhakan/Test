package tr.org.mmo.asansor.dto;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import tr.org.mmo.asansor.beans_.Basvuru;
import tr.org.mmo.asansor.business.FirmaBusiness;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.util.Util;

public class BasvuruListeDTO implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 511206990233038554L;
	private int id; /* Ödeme ID */

	public BasvuruListeDTO(Basvuru basvuru) {
		super();

		this.basvuruId = basvuru.getBasvuru().getBasvuruId();
		this.basvuruTarihi = basvuru.getBasvuru().getBasvuruTarihi();
		this.adiSoyadi = new StringBuilder()
				.append(basvuru.getBasvuru().getBasvuruYapanAdi()).append(" ")
				.append(basvuru.getBasvuru().getBasvuruYapanSoyadi())
				.toString();
		this.tcKimlikNo = basvuru.getBasvuru().getBasvuruYapanTCKimlikNo();
		this.telefonNo = basvuru.getKisi().getTelefonNoGsmStr();
		this.mahalle = basvuru.getBina().getMahalle();
		this.caddeSokak = basvuru.getBina().getCaddeSokak();
		this.binaNo = basvuru.getBina().getBinaNo();
		this.il = basvuru.getBina().getIlAdi();
		this.ilce = basvuru.getBina().getIlceAdi();
		this.ilKodu = basvuru.getBina().getIl();
		this.ilceKodu = basvuru.getBina().getIlce();
		this.telefonNoDahili=basvuru.getBasvuru().getTelefonNoDahili();
		this.adi = "";
		this.odemeAciklama = "";
		this.odemeYapilmayacakBina = "";
		try {
			this.odemeTarihi = new SimpleDateFormat("dd.MM.yyyy")
					.parse("01.01.0001");
		} catch (ParseException e1) {
			this.odemeTarihi = new Date();
		}
		this.soyadi = "";
		this.telefonNo = "";
		this.binaId = basvuru.getBina().getBinaId();
		this.basvuruYapanSorumlulukTur = basvuru.getBasvuru()
				.getBasvuruYapanSorumlulukTuru();
		this.ePosta = "";

		this.taramaId = basvuru.getBasvuru().getBasvuruId();
		this.binaAdi = basvuru.getBina().getBinaAdi();

		try {
			List<FirmaDTO> l=new ArrayList<FirmaDTO>();
			l = new FirmaBusiness()
					.getBasvuruAsansorBakimciFirmalar(basvuruId);
			

			HashMap<Integer, FirmaDTO> firmaMap = new HashMap<Integer, FirmaDTO>();

			for (FirmaDTO bld : l) {
				firmaMap.put(bld.getKod(), bld);
			}
			this.bakimciFirma=new ArrayList<FirmaDTO>();
				Set<Integer> set = firmaMap.keySet();
				Iterator<Integer> iter = set.iterator();
				while (iter.hasNext()) {
					int i = iter.next();
					this.bakimciFirma.add(firmaMap.get(i));
				
				}
		} catch (CRUDException e) {
			this.bakimciFirma = new ArrayList<FirmaDTO>();
		}

	}

	public BasvuruListeDTO() {

	}
	private int telefonNoDahili;
	private int basvuruId;
	private Date basvuruTarihi;
	private String adiSoyadi;
	private long tcKimlikNo;
	private String telefonNo;
	private String mahalle;
	private String caddeSokak;
	private String binaNo;
	private String il;
	private String ilce;
	private int ilKodu;
	private int ilceKodu;
	private Date odemeTarihi;
	private double odemeTutari;
	private String basvuruDurum;
	private double kontrolTutari;
	
	private int binaId;
	private String adi;
	private String soyadi;
	private String odemeAciklama;
	private boolean odemeKontroldeAlinsin;
	private int basvuruYapanSorumlulukTur;
	private String ePosta;
	private String odemeYapilmayacakBina;
	private int taramaId;
	private String binaAdi;
	private List<FirmaDTO> bakimciFirma = new ArrayList<FirmaDTO>();
	private boolean firmaListedeBulunamadi = false;
	private List<BasvuruAsansorDTO> asansorKontrolFiyatlari = new ArrayList<BasvuruAsansorDTO>();

	public Date getBasvuruTarihi() {
		return basvuruTarihi;
	}

	public void setBasvuruTarihi(Date basvuruTarihi) {
		this.basvuruTarihi = basvuruTarihi;
	}

	public String getAdiSoyadi() {
		return adiSoyadi;
	}

	public void setAdiSoyadi(String adiSoyadi) {
		this.adiSoyadi = Util.toUpperCase(adiSoyadi);
	}

	public long getTcKimlikNo() {
		return tcKimlikNo;
	}

	public void setTcKimlikNo(long tcKimlikNo) {
		this.tcKimlikNo = tcKimlikNo;
	}

	public String getTelefonNo() {
		return telefonNo;
	}

	public void setTelefonNo(String telefonNo) {
		telefonNo = telefonNo == null ? "" : telefonNo;
		this.telefonNo = telefonNo.length() == 10 ? "("
				+ telefonNo.substring(0, 3) + ")" + telefonNo.substring(3)
				: telefonNo;
	}

	public int getBasvuruId() {
		return basvuruId;
	}

	public void setBasvuruId(int basvuruId) {
		this.basvuruId = basvuruId;
	}

	public String getMahalle() {
		return mahalle;
	}

	public void setMahalle(String mahalle) {
		this.mahalle = Util.toUpperCase(mahalle);
	}

	public String getCaddeSokak() {
		return caddeSokak;
	}

	public void setCaddeSokak(String caddeSokak) {
		this.caddeSokak = Util.toUpperCase(caddeSokak);
	}

	public String getIl() {
		return il;
	}

	public void setIl(String il) {
		this.il = Util.toUpperCase(il);
	}

	public String getIlce() {
		return ilce;
	}

	public void setIlce(String ilce) {
		this.ilce = Util.toUpperCase(ilce);
	}

	public String getBinaNo() {
		return binaNo;
	}

	public void setBinaNo(String binaNo) {
		this.binaNo = binaNo;
	}

	public int getIlKodu() {
		return ilKodu;
	}

	public void setIlKodu(int ilKodu) {
		this.ilKodu = ilKodu;
	}

	public int getIlceKodu() {
		return ilceKodu;
	}

	public void setIlceKodu(int ilceKodu) {
		this.ilceKodu = ilceKodu;
	}

	public Date getOdemeTarihi() {
		return odemeTarihi;
	}

	public void setOdemeTarihi(Date odemeTarihi) {
		this.odemeTarihi = odemeTarihi;
	}

	public double getOdemeTutari() {
		return odemeTutari;
	}

	public void setOdemeTutari(double odemeTutari) {
		this.odemeTutari = odemeTutari;
	}

	public double getKontrolTutari() {
		return kontrolTutari;
	}

	public void setKontrolTutari(double kontrolTutari) {
		this.kontrolTutari = kontrolTutari;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public int getBinaId() {
		return binaId;
	}

	public void setBinaId(int binaId) {
		this.binaId = binaId;
	}

	public String getAdi() {
		return adi;
	}

	public void setAdi(String adi) {
		this.adi = Util.toUpperCase(adi);
	}

	public String getSoyadi() {
		return soyadi;
	}

	public void setSoyadi(String soyadi) {
		this.soyadi = Util.toUpperCase(soyadi);
	}

	public boolean isOdemeKontroldeAlinsin() {
		return odemeKontroldeAlinsin;
	}

	public void setOdemeKontroldeAlinsin(boolean odemeKontroldeAlinsin) {
		this.odemeKontroldeAlinsin = odemeKontroldeAlinsin;
	}

	public int getBasvuruYapanSorumlulukTur() {
		return basvuruYapanSorumlulukTur;
	}

	public void setBasvuruYapanSorumlulukTur(int basvuruYapanSorumlulukTur) {
		this.basvuruYapanSorumlulukTur = basvuruYapanSorumlulukTur;
	}

	public String getePosta() {
		return ePosta;
	}

	public void setePosta(String ePosta) {
		this.ePosta = ePosta;
	}

	public String getOdemeYapilmayacakBina() {
		return odemeYapilmayacakBina;
	}

	public void setOdemeYapilmayacakBina(String odemeYapilmayacakBina) {
		this.odemeYapilmayacakBina = odemeYapilmayacakBina;
	}

	public List<BasvuruAsansorDTO> getAsansorKontrolFiyatlari() {
		return asansorKontrolFiyatlari;
	}

	public void setAsansorKontrolFiyatlari(
			List<BasvuruAsansorDTO> asansorKontrolFiyatlari) {
		this.asansorKontrolFiyatlari = asansorKontrolFiyatlari;
	}

	public String getOdemeAciklama() {
		return odemeAciklama;
	}

	public void setOdemeAciklama(String odemeAciklama) {
		this.odemeAciklama = odemeAciklama;
	}

	public int getTaramaId() {
		return taramaId;
	}

	public void setTaramaId(int taramaId) {
		this.taramaId = taramaId;
	}

	public String getBinaAdi() {
		return binaAdi;
	}

	public void setBinaAdi(String binaAdi) {
		this.binaAdi = binaAdi;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<FirmaDTO> getBakimciFirma() {
		return bakimciFirma;
	}

	public void setBakimciFirma(List<FirmaDTO> bakimciFirma) {
		this.bakimciFirma = bakimciFirma;
		for (FirmaDTO f : bakimciFirma) {
			if (f.getKod() == 999999) {
				this.firmaListedeBulunamadi = true;
				break;
			}
		}
	}

	public boolean isFirmaListedeBulunamadi() {
		return firmaListedeBulunamadi;
	}

	public void setFirmaListedeBulunamadi(boolean firmaListedeBulunamadi) {
		this.firmaListedeBulunamadi = firmaListedeBulunamadi;
	}

	public String getBasvuruDurum() {
		return basvuruDurum;
	}

	public void setBasvuruDurum(String basvuruDurum) {
		this.basvuruDurum = basvuruDurum;
	}

	public int getTelefonNoDahili() {
		return telefonNoDahili;
	}

	public void setTelefonNoDahili(int telefonNoDahili) {
		this.telefonNoDahili = telefonNoDahili;
	}

	

}
