package tr.org.mmo.asansor.business;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tr.org.mmo.asansor.beans_.Istatistik;
import tr.org.mmo.asansor.beans_.Randevu;
import tr.org.mmo.asansor.beans_.UavtBina;
import tr.org.mmo.asansor.dao.BinaDAOImpl;
import tr.org.mmo.asansor.dao.KontrolHaberDAOImpl;
import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.dto.BinaKisiDTO;
import tr.org.mmo.asansor.dto.BinaSorumlulukDTO;
import tr.org.mmo.asansor.dto.KontrolHaberDTO;
import tr.org.mmo.asansor.dto.KontrolYapilamamaNedenDTO;
import tr.org.mmo.asansor.dto.OdemeAlinmayacakBinalarDTO;
import tr.org.mmo.asansor.dto.SozlesmeBinaTipleriDTO;
import tr.org.mmo.asansor.dto.YapiKonusuDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;
import tr.org.mmo.beans.CaddeSokak;
import tr.org.mmo.beans.Mahalle;

public class BinaBusiness {
	public List<YapiKonusuDTO> getYapiTipler() throws ReadException {
		return new BinaDAOImpl().getYapiTipleri();
	}

	public ArrayList<BinaDTO> binaBul(String ada, String parsel, String pafta)
			throws ReadException {
		return new BinaDAOImpl().binaByAdaParselPafta(ada, parsel, pafta);
	}

	public int setBinaKisi(BinaKisiDTO binaKisi) throws CreateException {
		return new BinaDAOImpl().binaKisiEkle(binaKisi);
	}

	public void setBinaKisiBasvurudan(BinaKisiDTO binaKisi)
			throws CreateException {
		new BinaDAOImpl().binaKisiEkleBasvurudan(binaKisi);
	}

	public void updateBinaKisi(BinaKisiDTO binaKisi) throws UpdateException {
		new BinaDAOImpl().binaKisiGuncelle(binaKisi);
	}

	public ArrayList<BinaSorumlulukDTO> getBinaSorumlulukTur()
			throws ReadException {
		return new BinaDAOImpl().getBinaSorumlulukTur();
	}

	public ArrayList<BinaDTO> binaBulByTcKimlik(long tcKimlikNo)
			throws ReadException {
		return new BinaDAOImpl().binaByTcKimlik(tcKimlikNo);
	}

	public ArrayList<BinaDTO> binaBul(int il, int ilce) throws ReadException {
		return new BinaDAOImpl().binaByIlIlce(il, ilce);
	}

	public ArrayList<BinaDTO> binaBulByIlIlce() throws ReadException {
		return new BinaDAOImpl().binaByIlIlce();
	}
	public ArrayList<BinaDTO> binaBulByBelediye(int belediyeKod) throws ReadException {
		return new BinaDAOImpl().binaByBelediye(belediyeKod);
	}
	public List<BinaKisiDTO> binaKisiler(int binaId) throws ReadException {
		return new BinaDAOImpl().getBinaKisiler(binaId);
	}

	public BinaDTO binaBul(int binaId) throws ReadException {
		return new BinaDAOImpl().binaById(binaId);
	}
	
	public BinaDTO binaBulTescilno(int tescilno) throws ReadException {
		return new BinaDAOImpl().binaByTescilno(tescilno);
	}

	public BinaDTO binaBulByRandevuId(int randevuId) throws ReadException {
		return new BinaDAOImpl().binaByRandevuId(randevuId);
	}

	public BinaDTO binaBulByRandevuId(Connection con, int randevuId)
			throws ReadException {
		return new BinaDAOImpl().binaByRandevuId(con, randevuId);
	}

	public int binaEkle(BinaDTO bina) throws CreateException {
		return new BinaDAOImpl().binaEkle(bina);
	}

	public void binaGuncelle(BinaDTO bina) throws UpdateException,
			CreateException {
		new BinaDAOImpl().binaGuncelle(bina);
	}

	public int binaKontrol(BinaDTO bina) throws CRUDException {
		return new BinaDAOImpl().binaKontrol(bina);
	}

	public List<BinaKisiDTO> binaKisiler(Connection con, int binaId)
			throws ReadException {
		return new BinaDAOImpl().getBinaKisiler(con, binaId);
	}

	public List<Randevu> getBasvuruRandevu(int binaId) throws ReadException {
		return new BinaDAOImpl().getBasvuruRandevuByBinaId(binaId);

	}

	public List<SozlesmeBinaTipleriDTO> getSozlesmeBinaTipleri()
			throws ReadException {
		return new BinaDAOImpl().getSozlesmeBinaTipleri();

	}

	public List<KontrolYapilamamaNedenDTO> getKontrolYapilmamaNedenKodlari()
			throws ReadException {

		return new BinaDAOImpl().getKontrolYapilmamaNedenKodlari();
	}

	public void odemeAlinmamaDurumuEkle(int binaId, String odemeAlinmazAciklama)
			throws CreateException {
		new BinaDAOImpl().odemeAlinmamaDurumuEkle(binaId, odemeAlinmazAciklama);

	}

	public void odemeAlinmamaDurumuIptal(int id) throws UpdateException {
		new BinaDAOImpl().odemeAlinmamaDurumIptal(id);

	}

	public List<OdemeAlinmayacakBinalarDTO> getOdemeAlinmamaDurumu(int binaId)
			throws ReadException {
		return new BinaDAOImpl().getOdemeAlinmamaDurumu(binaId);
	}

	public boolean isOdemeAlinacakMi(Connection con, int binaId)
			throws ReadException {

		return new BinaDAOImpl().odemeAlinacakMi(con, binaId);
	}
	
	public boolean isOdemeAlinacakMi(int binaId)
			throws CRUDException {

		return new BinaDAOImpl().odemeAlinacakMi(binaId);
	}

	public String binaBulByBasvuruId(String basvuruId) throws ReadException {

		return new BinaDAOImpl().getBinaAdiByBasvuruId(Integer
				.parseInt(basvuruId));
	}

	public List<KontrolHaberDTO> kontrolListesiGetir(Date gununTarihi)
			throws ReadException {

		return new KontrolHaberDAOImpl().getKontroller(gununTarihi);
	}

	public void setCoordinat(int binaId, double lat, double lon)
			throws UpdateException {
		new BinaDAOImpl().setCoordinat(binaId, lat, lon);

	}

	public List<Istatistik> sonCalisilanBinalar(Date tarih) throws ReadException {
		
		return new BinaDAOImpl().sonCalisilanBinalar(tarih);
	}

	public void setMahalleFromUavt (
			List<Mahalle> list) throws CRUDException{
		new BinaDAOImpl().setMahalleFromUavt(list);
		
	}

	public void setCsbmFromUavt (
			List<CaddeSokak> list) throws CRUDException{
		new BinaDAOImpl().setCsbmFromUavt(list);
		
	}
	
public List<UavtBina> getUavtBinaTableByCsbmKod(long csbmKod) throws ReadException {
		
		return new BinaDAOImpl().getUavtBinaTableByCsbmKod(csbmKod);
	}

public void setBinaFromUavt(List<UavtBina> uavtdenEklenecek) throws CreateException{
	new BinaDAOImpl().setBinaFromUavt(uavtdenEklenecek);
	
}

public BinaDTO binaBulForRaporGonder(Connection con, int randevuId) throws ReadException {
	
	return new BinaDAOImpl().binaForRaporGonder(con, randevuId);
}

}
