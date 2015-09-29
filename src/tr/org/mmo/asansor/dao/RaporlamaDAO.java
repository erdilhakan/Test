package tr.org.mmo.asansor.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import tr.org.mmo.asansor.dto.BelediyeKontrolDTO;
import tr.org.mmo.asansor.dto.BinaKontrolVeOdemelerDTO;
import tr.org.mmo.asansor.dto.DenetciKontrolDTO;
import tr.org.mmo.asansor.dto.EtiketYapiTipKontrolDTO;
import tr.org.mmo.asansor.dto.OdemeKontrolOtuzGunDTO;
import tr.org.mmo.asansor.exception.db.ReadException;

public interface RaporlamaDAO {

	

	List<BelediyeKontrolDTO> getBelediyelereGoreKontroller(Date tarih1,
			Date tarih2, Map<Integer, Integer> subeIller,
			Map<Integer, ArrayList<Integer>> temsilcilikIllerIlceler,
			boolean temsilcilikSecildi) throws ReadException;


	List<DenetciKontrolDTO> getDenetciBazliKontroller(Date tarih1, Date tarih2,
			Map<Integer, Integer> subeIller,
			Map<Integer, ArrayList<Integer>> temsilcilikIllerIlceler,
			boolean temsilcilikSecildi) throws ReadException;


	List<EtiketYapiTipKontrolDTO> getEtiketVeYapiTiplerineGoreKontroller(
			Date tarih1, Date tarih2, Map<Integer, Integer> subeIller,
			Map<Integer, ArrayList<Integer>> temsilcilikIllerIlceler,
			boolean temsilcilikSecildi) throws ReadException;


	
	List<BinaKontrolVeOdemelerDTO> getBinaKontrolVeOdeme(Date tarih1,
			Date tarih2, List<Integer> belediyeler) throws ReadException;


	List<BinaKontrolVeOdemelerDTO> getBinaKontrol(Date tarih1, Date tarih2)
			throws ReadException;


	List<OdemeKontrolOtuzGunDTO> getBinaOdemelerOtuzGun(Date tarih1, Date tarih2)
			throws ReadException;

}
