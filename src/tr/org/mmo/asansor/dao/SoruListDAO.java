package tr.org.mmo.asansor.dao;

import java.util.Date;
import java.util.List;





import tr.org.mmo.asansor.beans_.AnaSoru;
import tr.org.mmo.asansor.beans_.ReferansDenetimKayitKontrolSorularListesi;
import tr.org.mmo.asansor.beans_.Soru;
import tr.org.mmo.asansor.dto.BakanlikSoruDTO;
import tr.org.mmo.asansor.dto.KontrolDTO;
import tr.org.mmo.asansor.dto.KontrolTestDTO;
import tr.org.mmo.asansor.dto.OnTanimliTestDTO;
import tr.org.mmo.asansor.dto.ReferansDenetimSorularEslestirmeDTO;
import tr.org.mmo.asansor.dto.SoruDTO;
import tr.org.mmo.asansor.dto.SoruOnTanimliDTO;
import tr.org.mmo.asansor.dto.TestDTO;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;

public interface SoruListDAO {

	Soru soruGetir(Integer soruId);

	List<SoruDTO> listeGetir();

	List<SoruDTO> testSorulariAltListeGetir(int parent, int cihazTipi)
			throws ReadException;

	void testSorusuGuncelle(SoruDTO s) throws UpdateException;

	void soruEkle(SoruDTO s) throws CreateException;

	List<SoruOnTanimliDTO> getOnTanimliSorular(int soruNo) throws ReadException;

	void onTanimliSorusuGuncelle(SoruOnTanimliDTO s) throws UpdateException;

	int insertOnTanimli(SoruOnTanimliDTO onTanimliDTO) throws CreateException;

	void kontrolKaydet(KontrolDTO kontrolDTO, String etiket, List<AnaSoru> list)
			throws CreateException;

	

	List<OnTanimliTestDTO> onTanimliTestGetir(int kontrolId)
			throws ReadException;

	List<TestDTO> getTestYanitlari(int kontrolId) throws ReadException;

	

	

	

	List<KontrolTestDTO> getTestYanitlarForSanayiBakanlik(int kontrolId)
			throws ReadException;

	List<ReferansDenetimKayitKontrolSorularListesi> getReferansDenetimKayitKontrolSorulari()
			throws ReadException;

	List<AnaSoru> testSorulariGetir(int cihazTipi, boolean bakanlikSoru)
			throws ReadException;

	List<ReferansDenetimSorularEslestirmeDTO> getReferansDenetimKayitEslestirme()
			throws ReadException;

	int getMaxSiraNo(Integer cihazTipi, Integer parent, Date tarih)
			throws ReadException;

	List<AnaSoru> anaListeGetir(int cihazTipi, boolean asansorTest, Date tarih)
			throws ReadException;

	BakanlikSoruDTO getBakanlikSoruTarih() throws ReadException;

}
