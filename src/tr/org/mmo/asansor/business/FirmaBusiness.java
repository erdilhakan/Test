package tr.org.mmo.asansor.business;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;




import tr.org.mmo.asansor.dao.FirmaDAOImpl;
import tr.org.mmo.asansor.dto.BakimciFirmaIletisimDTO;
import tr.org.mmo.asansor.dto.BasvuruAsansorDTO;
import tr.org.mmo.asansor.dto.CihazDTO;
import tr.org.mmo.asansor.dto.CihazFirmaDTO;
import tr.org.mmo.asansor.dto.FirmaDTO;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;

public class FirmaBusiness {
	FirmaDAOImpl dao = new FirmaDAOImpl();

	public List<FirmaDTO> listeGetir() throws ReadException {
		return dao.firmaListesi();
	}

	public int firmaKaydet(FirmaDTO firma) throws UpdateException {
		return dao.firmaKaydet(firma);
	}

	public FirmaDTO getFirma(int kod) throws ReadException {
		return dao.getFirma(kod);
	}

	public boolean firmaVarMi(int kod) throws ReadException {
		return dao.firmaVarMi(kod);
	}

	public List<FirmaDTO> getBasvuruFirmalar(int ilKodu) throws ReadException {

		return new FirmaDAOImpl().firmaListesi(ilKodu);
	}

	public void iletisimKaydet(BakimciFirmaIletisimDTO firmaIletisim,
			int firmaId) throws CreateException {

		dao.firmaIletisimKaydet(firmaIletisim, firmaId);

	}

	public void iletisimSil(BakimciFirmaIletisimDTO iletisim)
			throws DeleteException {
		dao.firmaIletisimSil(iletisim);

	}

	public List<BakimciFirmaIletisimDTO> iletisimBilgisiGetir(int kod)
			throws ReadException {
		return dao.firmaIletisimBilgisiGetir(kod);
	}

	

	public List<BasvuruAsansorDTO> getAsansorBakimci(
			List<BasvuruAsansorDTO> asansorList,List<BasvuruAsansorDTO> basvuruAsansorler,  int basvuruId) throws ReadException {
		return dao.asansorBakimciFirmasi(asansorList,basvuruAsansorler, basvuruId);
	}

	public HashMap<Integer, FirmaDTO> getBasvuruFirmasi(Connection con,
			int basvuruId) throws ReadException {

		return dao.basvuruBakimciFirmalar(con, basvuruId);
	}

	public List<FirmaDTO> setAsansorBakimciFirmalar(Connection con,
			int basvuruId) throws ReadException {
		return dao.asansorBakimciFirmalar(con, basvuruId);
	}
	
	public void setAsansorBakimciFirma(
			CihazFirmaDTO cihazFirmaDTO) throws CreateException {
		dao.setAsansorBakimliFirma(cihazFirmaDTO);
	}
	
	public void setAsansorBakimciFirma(
			CihazFirmaDTO cihazFirmaDTO,List<CihazDTO> cihazList) throws CreateException {
		dao.setAsansorBakimliFirma(cihazFirmaDTO,cihazList);
	}
	public void setAsansorBakimciFirmaBasvuru(
			CihazFirmaDTO cihazFirmaDTO,BasvuruAsansorDTO[] cihazList) throws CreateException {
		dao.setAsansorBakimciFirma(cihazFirmaDTO,cihazList);
	}

	public List<FirmaDTO> getAsansorBakimciFirmalar(int cihazId)
			throws ReadException {
		return dao.asansorBakimciFirmalar(cihazId);
	}
	


	public FirmaDTO getCihazKontrolFirma(Connection con, int randevuId,
			int cihazId) throws ReadException {

		return dao.getCihazKontrolFirma(con, randevuId, cihazId);
	}
	public FirmaDTO getCihazKontrolFirma( int randevuId,
			int cihazId) throws ReadException {

		return dao.getCihazKontrolFirma(randevuId, cihazId);
	}
	public List<BasvuruAsansorDTO> getAsansorBakimciFirmalar(Connection con,
			int basvuruId) throws ReadException {
		return dao.getBasvuruKontrolFirma(con, basvuruId);
	}

	public List<FirmaDTO> getBasvuruAsansorBakimciFirmalar(int basvuruId)
			throws ReadException {
		return dao.getBasvuruKontrolFirma(basvuruId);
	}
	
	public CihazFirmaDTO getCihazAnlasmaliFirma(int cihazId,int firmaId) 
			throws ReadException {
		return dao.getCihazAnlasmaliFirma(firmaId, cihazId);
	}

	public void firmaSil(int kod)  throws DeleteException{
		dao.firmaSil(kod);
		
	}
	
public Date getCihazAnlasmaliFirmaTarih(int cihazId, int kod) throws ReadException{
		
		return dao.getCihazAnlasmaliFirmaTarih(cihazId,kod);
	}
	
}
