package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.dto.BirimDTO;
import tr.org.mmo.asansor.dto.SubeDTO;
import tr.org.mmo.asansor.dto.SubeTemsilcilikHesapDTO;
import tr.org.mmo.asansor.dto.TemsilcilikDTO;
import tr.org.mmo.asansor.dto.YetkiSayfaDTO;
import tr.org.mmo.asansor.exception.db.ReadException;

public interface BirimDAO {

	List<SubeDTO> subeListesi() throws ReadException;

	List<TemsilcilikDTO> temsilcilikListesi() throws ReadException;

	List<BirimDTO> getBirimler(Connection con, Kullanici k)
			throws ReadException;

	List<BirimDTO> getBirimler(Kullanici k) throws ReadException;

	List<TemsilcilikDTO> subeTemsilcilikListesi(int subeKod)
			throws ReadException;

	HashMap<Integer, List<SubeTemsilcilikHesapDTO>> getSubeTemsilcilikHesaplar()
			throws ReadException;

	List<YetkiSayfaDTO> getSayfaYetkileri() throws ReadException;

}
