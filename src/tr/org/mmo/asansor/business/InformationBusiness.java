package tr.org.mmo.asansor.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import tr.org.mmo.asansor.beans_.Istatistik;
import tr.org.mmo.asansor.dao.BelediyeDAOImpl;
import tr.org.mmo.asansor.dao.RaporlamaDAOImpl;
import tr.org.mmo.asansor.dto.BelediyeKontrolDTO;
import tr.org.mmo.asansor.dto.BinaKontrolVeOdemelerDTO;
import tr.org.mmo.asansor.dto.CihazOdemeKontrolDTO;
import tr.org.mmo.asansor.dto.DenetciKontrolDTO;
import tr.org.mmo.asansor.dto.EtiketYapiTipKontrolDTO;
import tr.org.mmo.asansor.dto.OdemeKontrolDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;

public class InformationBusiness {
public static InformationBusiness INSTANCE =new InformationBusiness();

public List<DenetciKontrolDTO> getDenetciBazliKontroller(Date tarih1,
		Date tarih2, Map<Integer, Integer> subeIller,
		Map<Integer, ArrayList<Integer>> temsilcilikIllerIlceler,
		boolean temsilcilikSecildi) throws CRUDException{
	 return new RaporlamaDAOImpl().getDenetciBazliKontroller(tarih1,tarih2,subeIller,temsilcilikIllerIlceler,temsilcilikSecildi);
	
}
public List<BelediyeKontrolDTO> getBelediyelereGoreKontroller(Date tarih1,
		Date tarih2, Map<Integer, Integer> subeIller,
		Map<Integer, ArrayList<Integer>> temsilcilikIllerIlceler,
		boolean temsilcilikSecildi) throws CRUDException{
	 return new RaporlamaDAOImpl().getBelediyelereGoreKontroller(tarih1,tarih2,subeIller,temsilcilikIllerIlceler,temsilcilikSecildi);
	
}
public List<EtiketYapiTipKontrolDTO> getEtiketVeYapiTiplerineGoreKontroller(Date tarih1,
		Date tarih2, Map<Integer, Integer> subeIller,
		Map<Integer, ArrayList<Integer>> temsilcilikIllerIlceler,
		boolean temsilcilikSecildi) throws CRUDException{
	 return new RaporlamaDAOImpl().getEtiketVeYapiTiplerineGoreKontroller(tarih1,tarih2,subeIller,temsilcilikIllerIlceler,temsilcilikSecildi);
	
}

public List<BinaKontrolVeOdemelerDTO> getBinaKontrolVeOdeme(Date tarih1,
		Date tarih2, List<Integer> belediyeler) throws CRUDException{
	 return new RaporlamaDAOImpl().getBinaKontrolVeOdeme(tarih1,tarih2,belediyeler);
	
}

public List<BinaKontrolVeOdemelerDTO> getBinaKontroller(Date tarih1,
		Date tarih2) throws CRUDException{
	 return new RaporlamaDAOImpl().getBinaKontrol(tarih1,tarih2);
	
}

public List<EtiketYapiTipKontrolDTO> getBelediyeOdemeler(Date tarih1,
		Date tarih2, Map<Integer, Integer> subeIller,
		Map<Integer, ArrayList<Integer>> temsilcilikIllerIlceler,
		boolean temsilcilikSecildi) throws CRUDException{
	 return new RaporlamaDAOImpl().getBelediyeOdemeler(tarih1,tarih2,subeIller,temsilcilikIllerIlceler,temsilcilikSecildi);
	
}
public List<OdemeKontrolDTO> getBinaOdemeler(Date tarih1,
		Date tarih2, Map<Integer, Integer> subeIller,
		Map<Integer, ArrayList<Integer>> temsilcilikIllerIlceler,
		boolean temsilcilikSecildi) throws CRUDException{
	 return new RaporlamaDAOImpl().getBinaOdemeler(tarih1,tarih2,subeIller,temsilcilikIllerIlceler,temsilcilikSecildi);
	
}

public List<BinaKontrolVeOdemelerDTO> getBelediyeBilgi(Date tarih1,
		Date tarih2, List<Integer> belediyeler) throws CRUDException{
	 return new RaporlamaDAOImpl().getBelediyeBilgi(tarih1,tarih2,belediyeler);
	
}

public List<CihazOdemeKontrolDTO> getCihazOdemeler(Date tarih1,	Date tarih2, Map<Integer, Integer> subeIller,
		Map<Integer, ArrayList<Integer>> temsilcilikIllerIlceler,boolean temsilcilikSecildi)
		throws CRUDException{
	 return new RaporlamaDAOImpl().getCihazOdemeler(tarih1,tarih2,subeIller,temsilcilikIllerIlceler,temsilcilikSecildi);
	
}
}
