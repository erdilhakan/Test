package tr.org.mmo.asansor.business;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import tr.org.mmo.asansor.beans_.Istatistik;
import tr.org.mmo.asansor.dao.RaporDAOImpl;
import tr.org.mmo.asansor.dto.BasvuruAsansorDTO;
import tr.org.mmo.asansor.dto.DenetimKayitDTO;
import tr.org.mmo.asansor.dto.EskiRaporDTO;
import tr.org.mmo.asansor.dto.RaporDTO;
import tr.org.mmo.asansor.dto.RaporKayitDTO;
import tr.org.mmo.asansor.dto.RaporTeslimDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;

public class RaporBusiness {

	public int raporDosyasiOlusturKaydet(RaporDTO rapor) throws CreateException {
		return new RaporDAOImpl().raporDosyasiOlustur(rapor);

	}

	public void raporKaydet(int raporNo, FileInputStream fis, int len)
			throws UpdateException {
		new RaporDAOImpl().raporKaydet(raporNo, fis, len);

	}

	public List<RaporDTO> getRaporlarOnay(Date raporTarihi)
			throws ReadException {
		return new RaporDAOImpl().getRaporlarOnay(raporTarihi);

	}

	public boolean onaylanmayanRaporVarmi() throws ReadException {
		return new RaporDAOImpl().onaylanmayanRaporVarMi();
	}
	public boolean teslimEdilmeyenRaporVarmi() throws ReadException {
		return new RaporDAOImpl().teslimEdilmeyenRaporVarmi();
	}
	
	public List<Istatistik> getRaporlar(Date raporTarihi) throws ReadException {
		return new RaporDAOImpl().getRaporlar(raporTarihi);

	}

	public List<Istatistik> getYazdirilmamisRaporlar(Date raporTarihi)
			throws ReadException {
		return new RaporDAOImpl().getYazdirilmamisRaporlar(raporTarihi);

	}

	public void raporSil(RaporDTO rapor) throws DeleteException {

		new RaporDAOImpl().raporSil(rapor);
	}

	public void raporSil(Connection con, int kontrolId) throws DeleteException,
			SQLException {
		new RaporDAOImpl().raporSil(con, kontrolId);
	}

	public void raporOnayla(int kontrolId) throws UpdateException {
		new RaporDAOImpl().raporOnayla(kontrolId);

	}

	public List<EskiRaporDTO> getRaporlarForBina(int binaId)
			throws ReadException {
		return new RaporDAOImpl().getRaporlarByBinaId(binaId);
	}

	public void raporTeslimKaydet(RaporTeslimDTO rapor) throws CRUDException {
		new RaporDAOImpl().raporTeslimKaydet(rapor);

	}

	public RaporTeslimDTO raporTeslimGetir(int raporId) throws ReadException {
		return new RaporDAOImpl().raporTeslimGetir(raporId);
	}

	public List<EskiRaporDTO> eskiRaporGetir(int binaId, int cihazId)
			throws ReadException {
		return new RaporDAOImpl().getEskiRaporlar(binaId, cihazId);
	}
	public EskiRaporDTO eskiRaporGetirByKontrolId(int kontrolId)
			throws ReadException {
		return new RaporDAOImpl().getEskiRaporByKontrolId(kontrolId);
	}
	public boolean isEskiRaporMu(int raporId) throws ReadException {
		return new RaporDAOImpl().isEskiRaporMu(raporId);

	}

	public void isRaporVarMi(int kontrolId) throws ReadException {
		new RaporDAOImpl().raporByKontrolId(kontrolId);

	}

	public void raporKayitKaydet(RaporKayitDTO raporKayit)
			throws CreateException {
		new RaporDAOImpl().raporKayitKaydet(raporKayit);
	}

	public List<RaporDTO> getRaporKayit(List<RaporDTO> onayliRaporlar)
			throws ReadException {

		return new RaporDAOImpl().getKayitliRaporlar(onayliRaporlar);
	}

	public void raporKayitSil(RaporKayitDTO raporKayit) throws CRUDException {
		new RaporDAOImpl().raporKayitSil(raporKayit);

	}

	public boolean isRevizyonRaporVarMi(int kontrolId) throws ReadException{
		
		return new RaporDAOImpl().isRevizyonRaporVarMi(kontrolId);
	}

	public void onayliRaporIptal(RaporDTO rapor,int iptalKod,BasvuruAsansorDTO cihaz) throws UpdateException{
		new RaporDAOImpl().onayliRaporIptal(rapor,iptalKod,cihaz);
		
	}

	public RaporKayitDTO raporBakanliktaKayitliMi(Connection con, int kontrolId) throws ReadException {
		
		return new RaporDAOImpl().raporBakanliktaKayitliMi(con,kontrolId);
	}
	
	public List<RaporDTO> getDenetimKayitRaporlar() throws ReadException{
		return new RaporDAOImpl().getDenetimKayitRaporlar();
	}

	public void setDenetimKayitLog(DenetimKayitDTO denetimKayit) throws CreateException{
		new RaporDAOImpl().denetimKayitLogKaydet(denetimKayit);
		
	}
	
	public int getBasvuruId(int raporId) throws ReadException {
		return new RaporDAOImpl().getBasvuruId(raporId);

	}

	public List<EskiRaporDTO> getRaporlarTeslimEdilmeyenRaporlar() throws ReadException{
	
		return new RaporDAOImpl().teslimEdilmeyenRaporlarForOnaylayan();
	}
	
	/*public boolean odemeAlindiMi(int basvuruId) throws ReadException {
		return new RaporDAOImpl().odemeAlindiMi(basvuruId);

	}*/

}
