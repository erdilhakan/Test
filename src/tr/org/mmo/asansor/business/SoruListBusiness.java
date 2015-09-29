package tr.org.mmo.asansor.business;

import java.util.Date;
import java.util.List;




import tr.org.mmo.asansor.beans_.AnaSoru;
import tr.org.mmo.asansor.beans_.ReferansDenetimKayitKontrolSorularListesi;
import tr.org.mmo.asansor.beans_.Soru;
import tr.org.mmo.asansor.dao.SoruListDAOImpl;
import tr.org.mmo.asansor.dto.BakanlikSoruDTO;
import tr.org.mmo.asansor.dto.KontrolDTO;
import tr.org.mmo.asansor.dto.KontrolTestDTO;
import tr.org.mmo.asansor.dto.OnTanimliTestDTO;
import tr.org.mmo.asansor.dto.ReferansDenetimSorularEslestirmeDTO;
import tr.org.mmo.asansor.dto.SoruDTO;
import tr.org.mmo.asansor.dto.SoruOnTanimliDTO;
import tr.org.mmo.asansor.dto.TestDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;

public class SoruListBusiness {

	private SoruListDAOImpl dao = new SoruListDAOImpl();

	public Soru soruGetir(Integer soruId) {

		return dao.soruGetir(soruId);

	}

	public List<SoruDTO> soruListesiGetir() {
		return dao.listeGetir();

	}

	public List<AnaSoru> anaSoruListesiGetir(int cihazTipi,boolean asansorTest,Date tarih)
			throws ReadException {
		return dao.anaListeGetir(cihazTipi,asansorTest,tarih);

	}

	public List<AnaSoru> testAnaSoruListesiGetir(int cihazTipi,boolean bakanlikSoru)
			throws ReadException {
		return dao.testSorulariGetir(cihazTipi,bakanlikSoru);

	}

	public List<SoruDTO> testAltSoruGetir(int parent, int cihazTipi)
			throws ReadException {
		return dao.testSorulariAltListeGetir(parent, cihazTipi);

	}

	public List<SoruOnTanimliDTO> getOnTanimliSorular(int soruId)
			throws ReadException {
		return dao.getOnTanimliSorular(soruId);

	}

	public void kontrolKaydet(KontrolDTO kontrolDTO, String etiket,
			List<AnaSoru> list) throws CreateException {
		dao.kontrolKaydet(kontrolDTO, etiket, list);

	}

	public List<TestDTO> getTestYanitlariByKontrolId(int kontrolId)
			throws ReadException {
		return dao.getTestYanitlari(kontrolId);

	}

	public List<OnTanimliTestDTO> getOnTanimliTestYanitlariByKontrolId(
			int kontrolId) throws ReadException {
		return dao.onTanimliTestGetir(kontrolId);

	}

	public void testSorusuGuncelle(SoruDTO s) throws UpdateException {
		dao.testSorusuGuncelle(s);

	}

	public void soruEkle(SoruDTO soruEkleDTO) throws CreateException {
		 dao.soruEkle(soruEkleDTO);

	}

	public int getMaxSiraNo(Integer cihazTipi, Integer parent,Date tarih)
			throws ReadException {
		return dao.getMaxSiraNo(cihazTipi, parent,tarih);
	}

	public void onTanimliGuncelle(SoruOnTanimliDTO s) throws UpdateException {
		dao.onTanimliSorusuGuncelle(s);

	}

	public int onTanimliEkle(SoruOnTanimliDTO onTanimliDTO)
			throws CreateException {

		return dao.insertOnTanimli(onTanimliDTO);
	}
	
	public List<KontrolTestDTO> getTestYanitlari(
			int kontrolId) throws CRUDException {
		return dao.getTestYanitlarForSanayiBakanlik(kontrolId);

	}

	public List<ReferansDenetimKayitKontrolSorularListesi> getReferansDenetimKayitKontrolSorulari() throws ReadException{
		return new SoruListDAOImpl().getReferansDenetimKayitKontrolSorulari();
				
	}
	
	public List<ReferansDenetimSorularEslestirmeDTO> getReferansDenetimKayitEslestirme() throws ReadException {
		return new SoruListDAOImpl().getReferansDenetimKayitEslestirme();
	}

	public BakanlikSoruDTO getBakanlikSoruTarih() throws ReadException{
		return new SoruListDAOImpl().getBakanlikSoruTarih();
	}

}
