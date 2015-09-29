package tr.org.mmo.asansor.business;

import java.util.Date;
import java.util.List;

import tr.org.mmo.asansor.dao.BakanlikRaporlariDAOImpl;
import tr.org.mmo.asansor.dto.KirmiziEtiketKontrolDTO;
import tr.org.mmo.asansor.dto.OdemeKontrolOtuzGunDTO;
import tr.org.mmo.asansor.dto.RaporTeslimKontrolDTO;
import tr.org.mmo.asansor.dto.SariEtiketKontrolDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.ReadException;

public class BakanlikRaporlariBusiness {
public static BakanlikRaporlariBusiness INSTANCE =new BakanlikRaporlariBusiness();

public List<OdemeKontrolOtuzGunDTO> getBinaOdemelerOtuzGun(Date tarih1,
		Date tarih2) throws CRUDException {
		return new BakanlikRaporlariDAOImpl().getBinaOdemelerOtuzGun(tarih1,tarih2);
}

public List<KirmiziEtiketKontrolDTO> getKirmiziEtiketKontrol(Date tarih1,
		Date tarih2) throws CRUDException {
	
	return new BakanlikRaporlariDAOImpl().getKirmiziEtiketKontrol(tarih1,tarih2);
}

public List<SariEtiketKontrolDTO> getSariEtiketKontrol(Date tarih1,
		Date tarih2) throws CRUDException {
	return new BakanlikRaporlariDAOImpl().getSariEtiketKontrol(tarih1,tarih2);
}

public List<RaporTeslimKontrolDTO> getRaporTeslimKontrol(Date tarih1,
		Date tarih2) throws ReadException {
	return new BakanlikRaporlariDAOImpl().getRaporTeslimKontrol(tarih1,tarih2);
}

public static void belediyeKirmiziRaporGonder(int raporId, int belediyeKod, String belediyeAdi,String etiket,
		int kontrolId,int randevuId,int basvuruId,int binaId) throws CreateException {
	new BakanlikRaporlariDAOImpl().insertKirmiziRaporGonderBelediye(raporId,belediyeKod,belediyeAdi,etiket,kontrolId,randevuId,basvuruId,binaId);
	
}

public List<KirmiziEtiketKontrolDTO> getBelediyeGonderilenlerKirmiziEtiketKontrol(
		Date tarih1, Date tarih2) throws ReadException {
	return new BakanlikRaporlariDAOImpl().getBelediyeGonderilenlerKirmiziEtiketKontrol(tarih1,tarih2);
}

public List<KirmiziEtiketKontrolDTO> getBelediyeGonderilmeyenlerKirmiziEtiketKontrol(
		Date tarih1, Date tarih2) throws ReadException {
	return new BakanlikRaporlariDAOImpl().getBelediyeGonderilmeyenlerKirmiziEtiketKontrol(tarih1,tarih2);
}

public List<SariEtiketKontrolDTO> getBelediyeGonderilenlerSariEtiketKontrol(
		Date tarih1, Date tarih2) throws ReadException {
	return new BakanlikRaporlariDAOImpl().getBelediyeGonderilenlerSariEtiketKontrol(tarih1,tarih2);
}

public List<SariEtiketKontrolDTO> getBelediyeGonderilmeyenlerSariEtiketKontrol(
		Date tarih1, Date tarih2) throws ReadException {
	return new BakanlikRaporlariDAOImpl().getBelediyeGonderilmeyenlerSariEtiketKontrol(tarih1,tarih2);
}

public static void belediyeSariRaporGonder(int raporId, int belediyeKod,
		String belediyeAdi, String etiket, int kontrolId, int randevuId,
		int basvuruId, int binaId) throws CreateException {
	new BakanlikRaporlariDAOImpl().insertSariRaporGonderBelediye(raporId,belediyeKod,belediyeAdi,etiket,kontrolId,randevuId,basvuruId,binaId);
	
}


}
