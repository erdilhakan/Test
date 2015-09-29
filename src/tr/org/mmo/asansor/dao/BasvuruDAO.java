package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tr.org.mmo.asansor.beans_.Basvuru;
import tr.org.mmo.asansor.beans_.Istatistik;
import tr.org.mmo.asansor.beans_.TaramaYapilmayanBinalar;
import tr.org.mmo.asansor.dto.BasvuruAsansorDTO;
import tr.org.mmo.asansor.dto.BasvuruListeDTO;
import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.dto.RaporDTO;
import tr.org.mmo.asansor.dto.TaramaDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;

public interface BasvuruDAO {

	void basvuruDurumUpdate(Connection con, int basvuruId, String durum)
			throws UpdateException;

	List<BasvuruAsansorDTO> getBasvuruAsansor(int basvuruId)
			throws ReadException;

	List<BasvuruAsansorDTO> basvuruAsansorEkle(int basvuruId, BinaDTO bina,
			BasvuruAsansorDTO[] asansorList) throws CreateException;

	ArrayList<BasvuruListeDTO> getOdemeYapilanBasvurular() throws ReadException;

	

	List<BasvuruAsansorDTO> basvuruGuncelle(Basvuru basvuru,
			BasvuruAsansorDTO[] selectedAsansor) throws CRUDException;

	void taramaEkle(TaramaDTO tarama) throws CreateException;

	void taramaYapilmadiEkle(TaramaDTO tarama) throws CreateException;

	void taramaDurumUpdate(Connection con, int basvuruId, String durum)
			throws UpdateException;

	List<TaramaYapilmayanBinalar> getTaramaYapilamayanBinalar()
			throws ReadException;

	List<BasvuruAsansorDTO> basvuruKisiEkle(Basvuru basvuru,
			BasvuruAsansorDTO[] selectedAsansor) throws CreateException;

	void basvuruAsansorKontrolGuncelle(Connection con2, int cihazId,
			int randevuId, Date kontrolTarihi) throws UpdateException;

	List<BasvuruListeDTO> getBasvurularByBinaId(int binaId)
			throws ReadException;

	void taramaGuncelle(TaramaDTO tarama) throws UpdateException;

	List<Istatistik> getBasvurularByBinaIdAndTarih(int binaId, Date tarih)
			throws ReadException;

	List<BasvuruAsansorDTO> getBasvuruAsansorTip(int basvuruId)
			throws ReadException;

	BasvuruAsansorDTO basvuruVarmi(int cihazId) throws ReadException;

	BasvuruAsansorDTO getBasvuruAsansorFirma(Connection con2,
			int basvuruId, int cihazId) throws ReadException;

	List<BasvuruAsansorDTO> getBasvuruAsansor(RaporDTO rapor)
			throws ReadException;

	List<BasvuruListeDTO> getBasvurular(Date ilkTarih, Date sonTarih)
			throws ReadException;

	

	void basvuruBakimciFirmaUpdate(int basvuruId, int firmaId, int cihazId)
			throws UpdateException;

	

	
}
