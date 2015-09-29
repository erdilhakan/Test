package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.util.List;

import tr.org.mmo.asansor.dto.BasvuruListeDTO;
import tr.org.mmo.asansor.dto.OdemeDTO;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;

public interface OdemeDAO {

	OdemeDTO basvuruOdeme(Connection con2, int basvuruId) throws ReadException;

	void basvuruOdemeSil(Connection con2, int basvuruId) throws DeleteException;

	void odemeSekliGuncelle(BasvuruListeDTO selectedBasvuru)
			throws UpdateException;

	void basvuruOdemeKayit(Connection con, OdemeDTO odeme)
			throws CreateException;

	OdemeDTO getOdemeByBasvuruId(int basvuruId) throws ReadException;

	void odemeKaydet(OdemeDTO odeme) throws UpdateException;

	List<OdemeDTO> getOdemeListesiByBinaId(int binaId) throws ReadException;

	List<OdemeDTO> getYapilanOdemeler() throws ReadException;

	List<BasvuruListeDTO> getOdemeListesi() throws ReadException;

}
