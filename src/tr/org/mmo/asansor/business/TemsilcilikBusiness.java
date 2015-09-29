package tr.org.mmo.asansor.business;

import java.util.List;

import tr.org.mmo.asansor.dao.TemsilcilikDAOImpl;
import tr.org.mmo.asansor.dto.MuayeneKurulusDTO;
import tr.org.mmo.asansor.dto.TemsilcilikDTO;
import tr.org.mmo.asansor.exception.db.ReadException;

public class TemsilcilikBusiness {
	public List<TemsilcilikDTO> listeGetir() throws ReadException{
		TemsilcilikDAOImpl dao= new TemsilcilikDAOImpl();
		return dao.temsilcilikListesi();
	}
	
	public TemsilcilikDTO temsilcilikGetir(int temsilcilikKodu) throws ReadException{
		TemsilcilikDAOImpl dao= new TemsilcilikDAOImpl();
		return dao.temsilcilikGetir(temsilcilikKodu);
	}
	
	public MuayeneKurulusDTO getTemsilcilik(int ilKodu,int ilceKodu) throws ReadException{
		return new TemsilcilikDAOImpl().getTemsilcilik(ilKodu,ilceKodu);
	}
	
	public MuayeneKurulusDTO getSube(int ilKodu) throws ReadException{
		return new TemsilcilikDAOImpl().getSube(ilKodu);
	}
	
}
