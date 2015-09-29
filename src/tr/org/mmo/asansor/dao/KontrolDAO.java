package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.util.List;

import tr.org.mmo.asansor.dto.KontrolDTO;
import tr.org.mmo.asansor.dto.KontrolFirmaKatilimDTO;
import tr.org.mmo.asansor.dto.RaporKayitDTO;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;

public interface KontrolDAO {

	List<KontrolDTO> kontrolListesi() throws ReadException;

	List<KontrolDTO> getCihazKontroller(int cihazId) throws ReadException;

	void kontrolDurumGuncelle(int kontrolId, String onayDurum)
			throws UpdateException;

	void kontrolDurumGuncelle(Connection con2, int kontrolId, String onayDurum)
			throws UpdateException;

	List<KontrolDTO> getCihazKontrollerForRapor(int cihazId, int kontrolId)
			throws ReadException;

	void kontrolSil(int kontrolId) throws DeleteException;

	KontrolDTO getKontrolByKontrolId(int kontrolId) throws ReadException;

	KontrolDTO kontrolByKontrolId(int kontrolId) throws ReadException;

	void onayliKontrolIptal(Connection con2, int kontrolId)
			throws UpdateException;

	


	RaporKayitDTO checkDenetimKayit(int kontrolId, int randevuId, int cihazId,
			boolean revizyonRapor) throws ReadException;

	KontrolFirmaKatilimDTO getFirmaKatilim(int kontrolId) throws ReadException;

}
