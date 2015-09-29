package tr.org.mmo.asansor.dao;

import java.util.Date;
import java.util.List;

import tr.org.mmo.asansor.dto.KontrolHaberDTO;
import tr.org.mmo.asansor.exception.db.ReadException;

public interface KontrolHaberDAO {
	List<KontrolHaberDTO> getKontroller(Date gununTarihi) throws ReadException ;
	
}
