package tr.org.mmo.asansor.dao;


import java.sql.Connection;
import java.util.List;

import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.dto.CihazDTO;
import tr.org.mmo.asansor.dto.CihazTeknikDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;

public interface AsansorDAO {


List<CihazDTO> getCihazlar(int binaId) throws ReadException;

List<CihazTeknikDTO> getCihaz(int cihazId, int cihazTuru) throws ReadException;

CihazDTO getCihaz(Connection con, int cihazId) throws ReadException;

List<CihazTeknikDTO> getCihaz(Connection con, int cihazId, int cihazTuru)
		throws ReadException;

List<CihazTeknikDTO> getTeknikBilgiler(int cihazTipi, int cihazId)
		throws ReadException;







List<CihazTeknikDTO> getCihazKapasite(int cihazId) throws ReadException;







List<CihazDTO> getKontrolEdilecekCihazlar(int binaId, int randevuId)
		throws ReadException;



void cihazSil(int cihazId) throws DeleteException;









void cihazEkle(int asansorAdet, CihazDTO cihaz, int id, int ilKodu,
		int ilceKodu, String ilceAdi, List<CihazTeknikDTO> list,
		int uavtBinaKod, int asansorSiraNo) throws CRUDException, Exception;



void CihazDegerGuncelle(CihazDTO cihaz, List<CihazTeknikDTO> list,
		boolean uavtAsansorKayit, int uavtBinaKod) throws UpdateException;

void cihazEkleBasvurudan(CihazDTO cihaz, int id, int ilKodu, int ilceKodu,
		String ilceAdi, List<CihazTeknikDTO> list, int uavtBinaKod,
		int asansorSiraNo) throws CreateException, Exception;

void CihazDegerGuncelleKontrolde(CihazDTO cihaz, List<CihazTeknikDTO> list)
		throws UpdateException;

List<BinaDTO> getCihazlar(List<BinaDTO> list, int firmaId)
		throws ReadException;

int getKapsamId(CihazDTO cihaz) throws ReadException;



void uavtCihazKaydet(CihazDTO cihaz) throws UpdateException;

double getCihazFiyat(int cihazTipi, int belediye, int kapasiteDurak,
		int kapasiteAgirlik, int sozlesmeBinaTipId, String kontrolTipi)
		throws CRUDException;

double getCihazFiyat(Connection con, int cihazTipi, int belediye,
		int kapasiteDurak, int kapasiteAgirlik, int sozlesmeBinaTipId,
		String kontrolTipi) throws ReadException;

}
