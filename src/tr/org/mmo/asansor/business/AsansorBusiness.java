package tr.org.mmo.asansor.business;




import java.sql.Connection;
import java.util.List;

import tr.org.mmo.asansor.dao.AsansorDAOImpl;
import tr.org.mmo.asansor.dto.AsansorKapsamDTO;
import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.dto.CihazDTO;
import tr.org.mmo.asansor.dto.CihazTeknikDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;



public class AsansorBusiness {
	public List<CihazTeknikDTO> getTeknikOzellikler(int cihazTipi,int cihazId) throws ReadException{
		return new AsansorDAOImpl().getTeknikBilgiler(cihazTipi,cihazId);
	}
	
	public void cihazGuncelle(CihazDTO cihaz,List<CihazTeknikDTO> list,boolean uavtAsansorKayit,int uavtBinaKod) throws UpdateException{
		new AsansorDAOImpl().CihazDegerGuncelle(cihaz,list,uavtAsansorKayit, uavtBinaKod);
	}
	public void cihazGuncelleKontrolde(CihazDTO cihaz,List<CihazTeknikDTO> list) throws UpdateException{
		new AsansorDAOImpl().CihazDegerGuncelleKontrolde(cihaz,list);
	}
	public void cihazEkle(int asansorAdet, CihazDTO cihaz,int id,int ilKodu,int ilceKodu,String ilceAdi,List<CihazTeknikDTO> list,int uavtBinaKod,int asansorSiraNo) throws CRUDException,Exception{
		new AsansorDAOImpl().cihazEkle(asansorAdet,cihaz,id,ilKodu,ilceKodu,ilceAdi,list,uavtBinaKod,asansorSiraNo);
	}
	public void cihazEkleBasvurudan(CihazDTO cihaz,int id,int ilKodu,int ilceKodu,String ilceAdi,List<CihazTeknikDTO> list,int uavtBinaKod,int asansorSiraNo) throws CreateException,Exception{
		new AsansorDAOImpl().cihazEkleBasvurudan(cihaz,id,ilKodu,ilceKodu,ilceAdi,list,uavtBinaKod,asansorSiraNo);
	}
	public List<CihazDTO> getCihazlar(int binaId) throws ReadException{
		return new AsansorDAOImpl().getCihazlar(binaId);
	}
	public List<BinaDTO> getCihazlar(List<BinaDTO> binaList,int firmaId) throws ReadException{
		return new AsansorDAOImpl().getCihazlar(binaList,firmaId);
	}
	public List<CihazDTO> getKontrolEdilecekCihazlar(int binaId,int randevuId) throws ReadException{
		return new AsansorDAOImpl().getKontrolEdilecekCihazlar(binaId, randevuId);
	}
	
	public List<CihazTeknikDTO> getCihaz(int cihazId,int cihazTuru) throws ReadException{
		return new AsansorDAOImpl().getCihaz(cihazId, cihazTuru);
	}
	
	
	
	public List<CihazTeknikDTO> getCihaz(Connection con,int cihazId,int cihazTuru) throws ReadException{
		return new AsansorDAOImpl().getCihaz(con,cihazId, cihazTuru);
	}
	
	public CihazDTO getCihaz(Connection con, int cihazId) throws ReadException{
		
		return new AsansorDAOImpl().getCihaz(con,cihazId);
	}
	
	public double getAsansorBakimFiyat(Connection con,int cihazTipi,int belediye,int kapasiteDurak,int kapasiteAgirlik,int sozlesmeBinaTipId,String kontrolTipi) throws ReadException{
		return new AsansorDAOImpl().getCihazFiyat(con, cihazTipi,  belediye, kapasiteDurak, kapasiteAgirlik,sozlesmeBinaTipId,kontrolTipi);
	}
	public double getAsansorBakimFiyat(int cihazTipi,int belediye,int kapasiteDurak,int kapasiteAgirlik,int sozlesmeBinaTipId,String kontrolTipi) throws CRUDException{
		return new AsansorDAOImpl().getCihazFiyat(cihazTipi,  belediye, kapasiteDurak, kapasiteAgirlik,sozlesmeBinaTipId,kontrolTipi);
	}

	public List<CihazTeknikDTO> getCihazKapasite(int cihazId) throws ReadException{
		return new AsansorDAOImpl().getCihazKapasite(cihazId);
	}
	
	public List<AsansorKapsamDTO> getKapsamTurleri() throws ReadException {
		
		return new AsansorDAOImpl().getKapsamTurleri();
	}

	public void cihazSil(int cihazId)  throws DeleteException{
		new AsansorDAOImpl().cihazSil(cihazId);
		
	}
	public int getCihazKapsamId(CihazDTO c) throws ReadException{
		return new AsansorDAOImpl().getKapsamId(c);
	}

	public void uavtKodEkle(CihazDTO cihaz) throws UpdateException{
		new AsansorDAOImpl().uavtCihazKaydet(cihaz);
		
	}
}
