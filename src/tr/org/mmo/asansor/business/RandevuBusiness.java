package tr.org.mmo.asansor.business;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tr.org.mmo.asansor.beans_.Basvuru;
import tr.org.mmo.asansor.beans_.Istatistik;
import tr.org.mmo.asansor.beans_.Randevu;
import tr.org.mmo.asansor.dao.FirmaDAOImpl;
import tr.org.mmo.asansor.dao.RandevuDAOImpl;
import tr.org.mmo.asansor.dto.FirmaDTO;
import tr.org.mmo.asansor.dto.MuhendisCihazYetkiDTO;
import tr.org.mmo.asansor.dto.RandevuListeDTO;
import tr.org.mmo.asansor.dto.RandevuMuhendisDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;

public class RandevuBusiness {
	private RandevuDAOImpl dao = new RandevuDAOImpl();

	public int ekle(Randevu r) throws CreateException {

		return dao.randevuEkle(r);

	}

	public int ekle(Randevu r, Basvuru b) throws CreateException {

		return dao.randevuEkle(r, b);

	}

	public List<FirmaDTO> getFirmalar() throws ReadException {

		return new FirmaDAOImpl().firmaListesi();
	}

	public Randevu randevuGetir(Date tarih) throws ReadException {

		return dao.randevuGetir(tarih);

	}

	public Randevu randevuGetir(int randevuId) throws ReadException {

		return dao.randevuGetir(randevuId);

	}

	public void randevuGuncelle(Date tarih, int randevuId)
			throws UpdateException {

		dao.randevuGuncelle(tarih, randevuId);

	}

	public void randevuSil(Randevu r) throws DeleteException {

		dao.randevuSil(r);

	}

	public ArrayList<RandevuListeDTO> randevuListesiGetir()
			throws ReadException {
		return dao.listeGetir();

	}

	public ArrayList<RandevuListeDTO> randevuListesiGetirForMuhendis(int sicilNo)
			throws ReadException {
		return dao.listeGetirForMuhendis(sicilNo);

	}

	public boolean muhendisMuayeneAdetKontrol(Integer sicilNo,
			Date randevuTarih, int kontrolAdet,int basvuruId) throws ReadException {

		return dao.muhendisRandevuAdetKontrol(sicilNo, randevuTarih,
				kontrolAdet,basvuruId);
	}

	public void randevuDurumGuncelle(int randevuId, String durum)
			throws UpdateException {
		dao.randevuDurumUpdate(randevuId, durum);
	}

	public List<RandevuListeDTO> getRandevuByBinaId(int binaId)
			throws ReadException {

		return dao.getRandevularByBinaId(binaId);
	}

	public List<RandevuMuhendisDTO> getRandevuMuhendis(Connection con,
			int randevuId) throws ReadException {
		return dao.getRandevuMuhendis(con, randevuId);
	}

	public boolean isRandevuMuhendis(int randevuId) throws ReadException {
		return dao.isRandevuMuhendis(randevuId);
	}

	public void randevuSil(Connection con, int basvuruId) throws CRUDException {
		dao.randevuSil(con, basvuruId);

	}

	public List<Istatistik> getRandevuKontrol(int cihazId, int basvuruId) throws ReadException{
	 return	dao.getRandevuKontrol(cihazId,basvuruId);
		
	}
	
	public List<MuhendisCihazYetkiDTO> getMuhendisCihazYetki(int sicilNo) throws ReadException{
		return dao.getMuhendisCihazYetki(sicilNo);
	}
	
	public List<RandevuListeDTO> getEtiketListe(Date etiketRandevuTarihi,int muhendisSicilNo) throws ReadException{
		return dao.getEtiketListe(etiketRandevuTarihi,muhendisSicilNo);
	}
}
