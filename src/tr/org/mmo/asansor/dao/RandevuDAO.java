package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tr.org.mmo.asansor.beans_.Istatistik;
import tr.org.mmo.asansor.beans_.Randevu;
import tr.org.mmo.asansor.dto.MuhendisCihazYetkiDTO;
import tr.org.mmo.asansor.dto.RandevuListeDTO;
import tr.org.mmo.asansor.dto.RandevuMuhendisDTO;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;

public interface RandevuDAO {
	public int randevuEkle(Randevu r) throws CreateException;

	public Randevu randevuGetir(Date tarih) throws ReadException;

	public Randevu randevuGetir(int randevuId) throws ReadException;

	public void randevuGuncelle(Date tarih, int randevuId)
			throws UpdateException;

	void randevuDurumUpdate(int randevuId, String durum) throws UpdateException;

	ArrayList<RandevuListeDTO> listeGetir() throws ReadException;

	int randevuEkle(Randevu r, tr.org.mmo.asansor.beans_.Basvuru b)
			throws CreateException;



	List<RandevuListeDTO> getRandevularByBinaId(int binaId)
			throws ReadException;

	ArrayList<RandevuListeDTO> listeGetirForMuhendis(int muhendisSicilNo)
			throws ReadException;

	ArrayList<RandevuMuhendisDTO> getRandevuMuhendis(Connection con,
			int randevuId) throws ReadException;

	void randevuSil(Randevu r) throws DeleteException;

	void randevuSil(Connection con2, int basvuruId) throws DeleteException,
			ReadException;

	boolean isRandevuMuhendis(int randevuId) throws ReadException;

	List<Istatistik> getRandevuKontrol(int cihazId, int basvuruId)
			throws ReadException;

	

	List<MuhendisCihazYetkiDTO> getMuhendisCihazYetki(Integer sicilNo)
			throws ReadException;

	boolean muhendisRandevuAdetKontrol(Integer sicilNo, Date randevuTarih,
			int kontrolAdet, int basvuruId) throws ReadException;

}
