package tr.org.mmo.asansor.dao;

import java.util.ArrayList;
import java.util.List;

import tr.org.mmo.asansor.dto.BucakKoyDTO;
import tr.org.mmo.asansor.dto.IlIlceDTO;
import tr.org.mmo.asansor.dto.UavtBelediyeDTO;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.beans.CaddeSokak;
import tr.org.mmo.beans.Mahalle;

public interface ApplicationDAO {
	public ArrayList<IlIlceDTO> getIllerIlceler() throws ReadException;

	public ArrayList<IlIlceDTO> getIlceler(int ilKodu) throws ReadException;

	ArrayList<CaddeSokak> getCaddeSokakByMahalleId(int mahalleId)
			throws ReadException;

	IlIlceDTO getIlByIlKodu(int ilKodu) throws ReadException;

	ArrayList<Mahalle> getMahalleByKoyKodu(int koyKodu) throws ReadException;

	ArrayList<UavtBelediyeDTO> getUavtBelediye(int ilKodu, int ilceKodu)
			throws ReadException;

	List<BucakKoyDTO> getBucakKoyByIlceKod(short ilce) throws ReadException;
}
