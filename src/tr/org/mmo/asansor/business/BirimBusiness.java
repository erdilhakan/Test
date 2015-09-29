package tr.org.mmo.asansor.business;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.dao.BirimDAOImpl;
import tr.org.mmo.asansor.dto.BirimDTO;
import tr.org.mmo.asansor.dto.SubeDTO;
import tr.org.mmo.asansor.dto.SubeTemsilcilikHesapDTO;
import tr.org.mmo.asansor.dto.TemsilcilikDTO;
import tr.org.mmo.asansor.dto.YetkiSayfaDTO;
import tr.org.mmo.asansor.exception.db.ReadException;

public class BirimBusiness {

	public List<SubeDTO> subeler() throws ReadException {
		return new BirimDAOImpl().subeListesi();
	}

	public List<TemsilcilikDTO> temsilcilikler() throws ReadException {
		return new BirimDAOImpl().temsilcilikListesi();
	}

	public List<TemsilcilikDTO> subeTemsilcilikler(int subeKod)
			throws ReadException {
		return new BirimDAOImpl().subeTemsilcilikListesi(subeKod);
	}

	public List<BirimDTO> getKullaniciBirimler(Kullanici k)
			throws ReadException {
		return new BirimDAOImpl().getBirimler(k);
	}

	public List<BirimDTO> getKullaniciBirimler(Connection con, Kullanici k)
			throws ReadException {
		return new BirimDAOImpl().getBirimler(con, k);
	}

	public HashMap<Integer, List<SubeTemsilcilikHesapDTO>> getSubeTemsilcilikHesapNumaralari()
			throws ReadException {
		return new BirimDAOImpl().getSubeTemsilcilikHesaplar();

	}

	public List<YetkiSayfaDTO> sayfaYetkileri() throws ReadException {

		return new BirimDAOImpl().getSayfaYetkileri();
	}

}
