package tr.org.mmo.asansor.business;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import tr.org.mmo.asansor.beans_.Basvuru;
import tr.org.mmo.asansor.beans_.Istatistik;
import tr.org.mmo.asansor.beans_.TaramaYapilmayanBinalar;
import tr.org.mmo.asansor.dao.BasvuruDAOImpl;
import tr.org.mmo.asansor.dto.BasvuruAsansorDTO;
import tr.org.mmo.asansor.dto.BasvuruListeDTO;
import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.dto.RaporDTO;
import tr.org.mmo.asansor.dto.TaramaDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;

public class BasvuruBusiness {
	private BasvuruDAOImpl instance;

	public BasvuruBusiness() {
		instance = instance == null ? new BasvuruDAOImpl() : instance;
	}

	@PostConstruct
	public void init() {
		instance = instance == null ? new BasvuruDAOImpl() : instance;
	}

	public List<BasvuruAsansorDTO> setBasvuru(Basvuru basvuru,
			BasvuruAsansorDTO[] selectedAsansor) throws CRUDException {
		return instance.basvuruKisiEkle(basvuru, selectedAsansor);
	}

	public void setTarama(TaramaDTO tarama) throws CRUDException {

		instance.taramaEkle(tarama);

	}

	public void setTaramaYapilmadi(TaramaDTO tarama) throws CRUDException {

		instance.taramaYapilmadiEkle(tarama);

	}

	public ArrayList<BasvuruListeDTO> randevuVerilecekBasvuruListesi()
			throws ReadException {
		ArrayList<BasvuruListeDTO> list = new ArrayList<BasvuruListeDTO>();
		list = instance.getOdemeYapilanBasvurular();
		return list;
	}

	public void basvuruDurumGuncelle(Connection con, int basvuruId, String durum)
			throws UpdateException {
		instance.basvuruDurumUpdate(con, basvuruId, durum);
	}
	
	public void basvuruBakimciFirmaUpdate( int randevuId, int firmaId,int cihazId)
			throws UpdateException {
		instance.basvuruBakimciFirmaUpdate( randevuId, firmaId,cihazId);
	}
	
	public void taramaDurumGuncelle(Connection con, int basvuruId, String durum)
			throws UpdateException {
		instance.taramaDurumUpdate(con, basvuruId, durum);
	}

	public List<BasvuruAsansorDTO> setBasvuruAsansor(int basvuruId,
			BinaDTO bina, BasvuruAsansorDTO[] asansorList)
			throws CreateException {
		return instance.basvuruAsansorEkle(basvuruId, bina, asansorList);
	}

	public List<BasvuruAsansorDTO> getBasvuruAsansor(int basvuruId)
			throws ReadException {
		return instance.getBasvuruAsansor(basvuruId);
	}
	
	public List<BasvuruAsansorDTO> getBasvuruAsansor(RaporDTO rapor)
			throws ReadException {
		return instance.getBasvuruAsansor(rapor);
	}
	
	public List<BasvuruAsansorDTO> getBasvuruAsansorTip(int basvuruId)
			throws ReadException {
		return instance.getBasvuruAsansorTip(basvuruId);
	}

	public List<BasvuruListeDTO> basvuruListesi(Date ilkTarih,Date sonTarih) throws ReadException {

		return instance.getBasvurular(ilkTarih,sonTarih);

	}

	public List<BasvuruAsansorDTO> basvuruGuncelle(Basvuru basvuru,
			BasvuruAsansorDTO[] selectedAsansor) throws CRUDException {
		return instance.basvuruGuncelle(basvuru, selectedAsansor);
	}

	public List<TaramaYapilmayanBinalar> getTaramaYapilmayanBinalar()
			throws ReadException {
		return instance.getTaramaYapilamayanBinalar();
	}

	public void basvuruAsansorKontrolUpdate(Connection con, int cihazId,
			int randevuId, Date kontrolTarihi) throws UpdateException {
		instance.basvuruAsansorKontrolGuncelle(con, cihazId, randevuId,
				kontrolTarihi);

	}

	public List<BasvuruListeDTO> getBasvuruByBinaId(int binaId)
			throws ReadException {
		return instance.getBasvurularByBinaId(binaId);
	}

	public void taramaUpdate(TaramaDTO tarama) throws UpdateException {
		instance.taramaGuncelle(tarama);

	}

	public List<Istatistik> getBasvurularByBinaIdAndTarih(int binaId, Date tarih) throws ReadException {
		
		return instance.getBasvurularByBinaIdAndTarih(binaId,tarih);
	}

	public void basvuruSil(int basvuruId,int binaId) throws DeleteException{
		instance.basvuruSil(basvuruId,binaId);
		
	}

	public BasvuruAsansorDTO basvuruVarmi(int cihazId) throws ReadException {
		return instance.basvuruVarmi(cihazId);
		
	}

	public BasvuruAsansorDTO getBasvuruAsansorFirma(Connection con2, int basvuruId,int cihazId) throws ReadException{
		return instance.getBasvuruAsansorFirma(con2, basvuruId,cihazId);
		
	}
}
