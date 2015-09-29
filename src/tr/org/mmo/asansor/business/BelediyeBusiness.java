package tr.org.mmo.asansor.business;

import java.util.List;

import tr.org.mmo.asansor.dao.BelediyeDAOImpl;
import tr.org.mmo.asansor.dto.BelediyeDTO;
import tr.org.mmo.asansor.dto.BelediyeIletisimDTO;
import tr.org.mmo.asansor.dto.BelediyeSozlesmeDTO;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;

public class BelediyeBusiness {
public List<BelediyeDTO> getBelediyeler() throws ReadException{
	return new  BelediyeDAOImpl().getAllBelediye();
}

public List<BelediyeDTO> getBelediyelerByIl(int ilKodu,int binaTip) throws ReadException{
	return new  BelediyeDAOImpl().geBelediyeByIl(ilKodu,binaTip);
}

public int iletisimEkle(BelediyeIletisimDTO iletisim) throws CreateException{
	return new BelediyeDAOImpl().iletisimEkle(iletisim);
}
public void iletisimSil(int id) throws DeleteException{
	new BelediyeDAOImpl().iletisimSil(id);
}
public void sozlesmeSil(int id) throws DeleteException{
	new BelediyeDAOImpl().sozlesmeSil(id);
}
public void iletisimGuncelle(BelediyeIletisimDTO iletisim) throws UpdateException{
	new BelediyeDAOImpl().iletisimGuncelle(iletisim);
}
public void sozlesmeGuncelle(BelediyeSozlesmeDTO sozlesme,int sozlesmeBinaTipList) throws UpdateException{
	new BelediyeDAOImpl().sozlesmeGuncelle(sozlesme,sozlesmeBinaTipList);
}
public int belediyeEkle(BelediyeDTO belediye) throws CreateException{
	return new BelediyeDAOImpl().belediyeEkle(belediye);
}
public void belediyeKaydet(BelediyeDTO belediye) throws UpdateException{
	 new BelediyeDAOImpl().belediyeKaydet(belediye);
}
public List<BelediyeSozlesmeDTO> sozlesmeEkle(BelediyeSozlesmeDTO sozlesme,int[] sozlesmeBinaTipList) throws CreateException{
	 return new BelediyeDAOImpl().sozlesmeKaydet(sozlesme,sozlesmeBinaTipList);
}
public List<BelediyeIletisimDTO> getBelediyeIletisim(int kod) throws ReadException{
	 return new BelediyeDAOImpl().getBelediyeIletisim(kod);
}
public List<BelediyeSozlesmeDTO> getBelediyeSozlesme(int kod) throws ReadException{
	 return new BelediyeDAOImpl().getBelediyeSozlesme(kod);
}
public List<BelediyeDTO> getSozlesmeBitimiYaklasanBelediyeler() throws ReadException{
	return new BelediyeDAOImpl().getSozlesmesiBitecekBelediyeler();
}

public BelediyeDTO getBelediyeByKod(int belediye) throws ReadException{
	return new BelediyeDAOImpl().getBelediyeByKod(belediye);
}

public void belediyeUavtEkle(BelediyeDTO belediye) throws UpdateException {
	new BelediyeDAOImpl().setBelediyeUavtKod(belediye);
	
}
}
