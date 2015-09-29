package tr.org.mmo.asansor.business;

import java.sql.Connection;
import java.util.List;

import tr.org.mmo.asansor.dao.KontrolDAOImpl;
import tr.org.mmo.asansor.dto.KontrolDTO;
import tr.org.mmo.asansor.dto.KontrolFirmaKatilimDTO;
import tr.org.mmo.asansor.dto.RaporKayitDTO;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;

public class KontrolBusiness {
	public List<KontrolDTO> getKontroller() throws ReadException {
		return new KontrolDAOImpl().kontrolListesi();
	}
	public KontrolFirmaKatilimDTO getFirmaKatilim(int kontrolId) throws ReadException {
		return new KontrolDAOImpl().getFirmaKatilim(kontrolId);
	}
	public KontrolDTO getKontrol(int kontrolId) throws ReadException {
		return new KontrolDAOImpl().kontrolByKontrolId(kontrolId);
	}

	public List<KontrolDTO> getCihazKontroller(int cihazId)
			throws ReadException {
		return new KontrolDAOImpl().getCihazKontroller(cihazId);
	}

	public List<KontrolDTO> getCihazKontrollerForRapor(int cihazId,
			int kontrolId) throws ReadException {
		return new KontrolDAOImpl().getCihazKontrollerForRapor(cihazId,
				kontrolId);
	}

	public KontrolDTO getCihazKontrolByKontrolId(int kontrolId)
			throws ReadException {
		return new KontrolDAOImpl().getKontrolByKontrolId(kontrolId);
	}

	public void kontrolDurumUpdate(Connection con, int kontrolId,
			String onayDurum) throws UpdateException {
		new KontrolDAOImpl().kontrolDurumGuncelle(con, kontrolId, onayDurum);
	}

	public void kontrolDurumUpdate(int kontrolId, String onayDurum)
			throws UpdateException {
		new KontrolDAOImpl().kontrolDurumGuncelle(kontrolId, onayDurum);
	}

	public void kontrolSil(int kontrolId) throws DeleteException {
		new KontrolDAOImpl().kontrolSil(kontrolId);

	}

	public int getBasvuruId(int randevuId) throws ReadException {
		return new KontrolDAOImpl().getBasvuruId(randevuId);
	}

	public void updateBasvuruAsansor(int basvuruId,int cihazId) throws UpdateException,
			CreateException {
		new KontrolDAOImpl().updateBasvuruAsansor(basvuruId,cihazId);

	}

	public String getBakimciFirmaPersonel(int kontrolId) throws ReadException {
		return new KontrolDAOImpl().getBakimciFirmaPersonel(kontrolId);
	}

	public void onayliKontrolIptal(Connection con, int kontrolId) throws UpdateException{
		// TODO Auto-generated method stub
		 new KontrolDAOImpl().onayliKontrolIptal(con,kontrolId);
	}

	public RaporKayitDTO checkDenetimKayit(int kontrolId,int randevuId,int cihazId,boolean revizyonRapor) throws ReadException {
		
		return new KontrolDAOImpl().checkDenetimKayit(kontrolId,randevuId,cihazId,revizyonRapor);
	}

}
