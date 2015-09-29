package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import tr.org.mmo.asansor.dto.BakimciFirmaIletisimDTO;
import tr.org.mmo.asansor.dto.BasvuruAsansorDTO;
import tr.org.mmo.asansor.dto.CihazDTO;
import tr.org.mmo.asansor.dto.CihazFirmaDTO;
import tr.org.mmo.asansor.dto.FirmaDTO;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;

public interface FirmaDAO {

	int firmaKaydet(FirmaDTO firma) throws UpdateException;

	FirmaDTO firmaDetay(Connection con, int firmaId) throws ReadException;

	FirmaDTO getFirma(int kod) throws ReadException;

	boolean firmaVarMi(int kod) throws ReadException;

	List<FirmaDTO> firmaListesi() throws ReadException;

	void firmaIletisimKaydet(BakimciFirmaIletisimDTO firmaIletisim, int firmaId)
			throws CreateException;

	void firmaIletisimSil(BakimciFirmaIletisimDTO iletisim)
			throws DeleteException;

	List<BakimciFirmaIletisimDTO> firmaIletisimBilgisiGetir(int kod)
			throws ReadException;

	

	HashMap<Integer, FirmaDTO> basvuruBakimciFirmalar(Connection con2,
			int basvuruId) throws ReadException;

	List<FirmaDTO> asansorBakimciFirmalar(Connection con2, int basvuruId)
			throws ReadException;

	List<FirmaDTO> asansorBakimciFirmalar(int cihazId) throws ReadException;

	List<BasvuruAsansorDTO> getBasvuruKontrolFirma(Connection con2,
			int basvuruId) throws ReadException;

	FirmaDTO getCihazKontrolFirma(Connection con2, int randevuId, int cihazId)
			throws ReadException;



	List<FirmaDTO> getBasvuruKontrolFirma(int basvuruId) throws ReadException;

	void setAsansorBakimliFirma(CihazFirmaDTO cihazFirmaDTO)
			throws CreateException;

	List<BasvuruAsansorDTO> asansorBakimciFirmasi(
			List<BasvuruAsansorDTO> asansorList,
			List<BasvuruAsansorDTO> basvuruAsansorler, int basvuruId)
			throws ReadException;

	CihazFirmaDTO getCihazAnlasmaliFirma(int firmaId, int cihazId)
			throws ReadException;

	void firmaSil(int kod) throws DeleteException;

	FirmaDTO getCihazKontrolFirma(int randevuId, int cihazId)
			throws ReadException;


	void setAsansorBakimliFirma(CihazFirmaDTO cihazFirmaDTO,
			List<CihazDTO> cihazList) throws CreateException;



	void setAsansorBakimciFirma(CihazFirmaDTO cihazFirmaDTO,
			BasvuruAsansorDTO[] cihazList) throws CreateException;

	Date getCihazAnlasmaliFirmaTarih(int cihazId, int kod) throws ReadException;

}
