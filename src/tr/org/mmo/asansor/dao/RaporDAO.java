package tr.org.mmo.asansor.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import tr.org.mmo.asansor.beans_.Istatistik;
import tr.org.mmo.asansor.dto.BasvuruAsansorDTO;
import tr.org.mmo.asansor.dto.DenetimKayitDTO;
import tr.org.mmo.asansor.dto.EskiRaporDTO;
import tr.org.mmo.asansor.dto.RaporDTO;
import tr.org.mmo.asansor.dto.RaporKayitDTO;
import tr.org.mmo.asansor.dto.RaporTeslimDTO;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;

public interface RaporDAO {

	void raporSil(RaporDTO rapor) throws DeleteException;

	void raporOnayla(int kontrolId) throws UpdateException;

	int raporDosyasiOlustur(RaporDTO rapor) throws CreateException;

	void raporKaydet(int raporNo, FileInputStream fis, int len)
			throws UpdateException;

	List<EskiRaporDTO> getRaporlarByBinaId(int binaId) throws ReadException;

	void raporTeslimKaydet(RaporTeslimDTO rapor) throws CreateException,
			DeleteException;

	RaporTeslimDTO raporTeslimGetir(int raporId) throws ReadException;

	List<EskiRaporDTO> getEskiRaporlar(int binaId, int cihazId)
			throws ReadException;

	void raporSil(Connection con2, int kontrolId) throws DeleteException,
			SQLException;

	List<Istatistik> getRaporlar(Date tarih) throws ReadException;

	List<Istatistik> getYazdirilmamisRaporlar(Date tarih) throws ReadException;

	boolean isEskiRaporMu(int raporId) throws ReadException;

	void raporByKontrolId(int kontrolId) throws ReadException;

	void raporKayitKaydet(RaporKayitDTO raporKayit) throws CreateException,
			DeleteException;

	List<RaporDTO> getKayitliRaporlar(List<RaporDTO> onayliRaporlar)
			throws ReadException;

	void raporKayitSil(RaporKayitDTO raporKayit) throws DeleteException,
			CreateException;

	boolean onaylanmayanRaporVarMi() throws ReadException;

	List<RaporDTO> getRaporlarOnay(Date raporTarihi) throws ReadException;

	EskiRaporDTO getEskiRaporByKontrolId(int kontrolId)
			throws ReadException;

	boolean isRevizyonRaporVarMi(int kontrolId) throws ReadException;

	

	void onayliRaporIptal(RaporDTO rapor, int iptalKod, BasvuruAsansorDTO cihaz)
			throws UpdateException;

	RaporKayitDTO raporBakanliktaKayitliMi(Connection con2, int kontrolId)
			throws ReadException;

	void denetimKayitLogKaydet(DenetimKayitDTO denetimKayit)
			throws CreateException;

	boolean teslimEdilmeyenRaporVarmi() throws ReadException;

	List<EskiRaporDTO> teslimEdilmeyenRaporlarForOnaylayan()
			throws ReadException;

}
