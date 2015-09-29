package tr.org.mmo.asansor.dao;

import java.util.Date;
import java.util.List;

import tr.org.mmo.asansor.dto.KirmiziEtiketKontrolDTO;
import tr.org.mmo.asansor.dto.OdemeKontrolOtuzGunDTO;
import tr.org.mmo.asansor.dto.SariEtiketKontrolDTO;
import tr.org.mmo.asansor.exception.db.ReadException;

public interface BakanlikRaporlariDAO {

	List<OdemeKontrolOtuzGunDTO> getBinaOdemelerOtuzGun(Date tarih1, Date tarih2)
			throws ReadException;
	
	List<KirmiziEtiketKontrolDTO> getKirmiziEtiketKontrol(Date tarih1, Date tarih2)
			throws ReadException;
	
	List<SariEtiketKontrolDTO> getSariEtiketKontrol(Date tarih1, Date tarih2)
			throws ReadException;

}
