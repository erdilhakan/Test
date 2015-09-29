package tr.org.mmo.asansor.dao;

import java.util.List;

import tr.org.mmo.asansor.beans_.Istatistik;
import tr.org.mmo.asansor.dto.BelediyeDTO;
import tr.org.mmo.asansor.dto.BelediyeIletisimDTO;
import tr.org.mmo.asansor.dto.BelediyeSozlesmeDTO;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;

public interface BelediyeDAO {
public List<BelediyeDTO> getAllBelediye() throws ReadException;



int iletisimEkle(BelediyeIletisimDTO iletisim)
		throws CreateException;



int belediyeEkle(BelediyeDTO belediye) throws CreateException;



int belediyeKaydet(BelediyeDTO belediye) throws UpdateException;



List<BelediyeIletisimDTO> getBelediyeIletisim(int kod) throws ReadException;



void iletisimSil(int id) throws CreateException, DeleteException;



void iletisimGuncelle(BelediyeIletisimDTO iletisim) throws UpdateException;







List<BelediyeSozlesmeDTO> getBelediyeSozlesme(int kod) throws ReadException;







void sozlesmeSil(int id) throws DeleteException;



List<BelediyeSozlesmeDTO> sozlesmeKaydet(BelediyeSozlesmeDTO sozlesme, int[] sozlesmeBinaTipList)
		throws CreateException;



void sozlesmeGuncelle(BelediyeSozlesmeDTO sozlesme, int sozlesmeBinaTip)
		throws UpdateException;



List<BelediyeDTO> geBelediyeByIl(int ilKodu, int binaTip) throws ReadException;



List<BelediyeDTO> getSozlesmesiBitecekBelediyeler()
		throws ReadException;



BelediyeDTO getBelediyeByKod(int belediye) throws ReadException;



void setBelediyeUavtKod(BelediyeDTO belediye) throws UpdateException;


}
