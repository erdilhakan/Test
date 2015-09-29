package tr.org.mmo.asansor.business;

import java.sql.Connection;
import java.util.List;

import tr.org.mmo.asansor.dao.OdemeDAOImpl;
import tr.org.mmo.asansor.dto.BasvuruAsansorDTO;
import tr.org.mmo.asansor.dto.BasvuruListeDTO;
import tr.org.mmo.asansor.dto.OdemeDTO;
import tr.org.mmo.asansor.dto.OdemeSekliDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;

public class OdemeBusiness {
	public void basvuruOdemeKayit(Connection con, OdemeDTO odeme)
			throws CreateException {
		new OdemeDAOImpl().basvuruOdemeKayit(con, odeme);
	}

	public void basvuruOdemeSil(Connection con, int basvuruId)
			throws DeleteException {
		new OdemeDAOImpl().basvuruOdemeSil(con, basvuruId);
	}

	public OdemeDTO getBasvuruOdeme(Connection con, int basvuruId)
			throws ReadException {

		return new OdemeDAOImpl().basvuruOdeme(con, basvuruId);
	}

	public void odemeKaydet(BasvuruListeDTO selectedBasvuru, OdemeDTO odeme)
			throws CRUDException {
		new OdemeDAOImpl().odemeKaydet(selectedBasvuru, odeme);

	}

	public void odemeKaydet(OdemeDTO odeme) throws UpdateException {
		new OdemeDAOImpl().odemeKaydet(odeme);

	}

	public void odemeSekliGuncelle(BasvuruListeDTO selectedBasvuru)
			throws UpdateException {
		new OdemeDAOImpl().odemeSekliGuncelle(selectedBasvuru);

	}

	public OdemeDTO getOdemeByBasvuruId(int basvuruId) throws ReadException {
		return new OdemeDAOImpl().getOdemeByBasvuruId(basvuruId);

	}

	public List<OdemeDTO> getOdemeListe(int binaId) throws ReadException {

		return new OdemeDAOImpl().getOdemeListesiByBinaId(binaId);
	}

	public List<OdemeSekliDTO> getOdemeSekilleri() throws ReadException {

		return new OdemeDAOImpl().getOdemeSekilleri();
	}

	public List<OdemeDTO> getYapilanOdemeler() throws ReadException {

		return new OdemeDAOImpl().getYapilanOdemeler();

	}

	public List<BasvuruListeDTO> getOdemeListe() throws ReadException {

		return new OdemeDAOImpl().getOdemeListesi();

	}

	public void odemeIptal(Connection con, int basvuruId, int cihazId,int iptalKod,BasvuruAsansorDTO cihaz) throws UpdateException {
		new OdemeDAOImpl().odemeIptal(con,basvuruId,cihazId,iptalKod,cihaz);
		
	}
}
