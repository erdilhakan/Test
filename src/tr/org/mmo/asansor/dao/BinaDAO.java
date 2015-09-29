package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tr.org.mmo.asansor.beans_.Istatistik;
import tr.org.mmo.asansor.beans_.Randevu;
import tr.org.mmo.asansor.beans_.UavtBina;
import tr.org.mmo.asansor.dto.BinaDTO;
import tr.org.mmo.asansor.dto.BinaKisiDTO;
import tr.org.mmo.asansor.dto.BinaSorumlulukDTO;
import tr.org.mmo.asansor.dto.KontrolYapilamamaNedenDTO;
import tr.org.mmo.asansor.dto.OdemeAlinmayacakBinalarDTO;
import tr.org.mmo.asansor.dto.SozlesmeBinaTipleriDTO;
import tr.org.mmo.asansor.dto.YapiKonusuDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;
import tr.org.mmo.beans.CaddeSokak;
import tr.org.mmo.beans.Mahalle;

public interface BinaDAO {
	ArrayList<BinaDTO> binaByAdaParselPafta(String ada, String parsel,
			String pafta) throws ReadException;

	ArrayList<BinaDTO> binaByIlIlce(int il, int ilce) throws ReadException;

	BinaDTO binaById(int binaId) throws ReadException;

	BinaDTO binaByRandevuId(int randevuId) throws ReadException;

	ArrayList<BinaDTO> binaByTcKimlik(long tcKimlikNo) throws ReadException;

	int binaEkle(BinaDTO bina) throws CreateException;

	void binaGuncelle(BinaDTO bina) throws UpdateException, CreateException;

	ArrayList<BinaSorumlulukDTO> getBinaSorumlulukTur() throws ReadException;

	int binaKisiEkle(BinaKisiDTO binaKisi) throws CreateException;

	void binaKisiGuncelle(BinaKisiDTO binaKisi) throws UpdateException;

	List<BinaKisiDTO> getBinaKisiler(int binaId) throws ReadException;

	List<YapiKonusuDTO> getYapiTipleri() throws ReadException;

	int binaKontrol(BinaDTO bina) throws CRUDException;

	BinaDTO binaByRandevuId(Connection con, int randevuId) throws ReadException;

	List<BinaKisiDTO> getBinaKisiler(Connection con, int binaId)
			throws ReadException;

	ArrayList<BinaDTO> binaByIlIlce() throws ReadException;

	List<Randevu> getBasvuruRandevuByBinaId(int binaId) throws ReadException;

	void binaKisiEkleBasvurudan(BinaKisiDTO binaKisi) throws CreateException;

	List<SozlesmeBinaTipleriDTO> getSozlesmeBinaTipleri() throws ReadException;

	List<KontrolYapilamamaNedenDTO> getKontrolYapilmamaNedenKodlari()
			throws ReadException;

	void odemeAlinmamaDurumuEkle(int binaId, String odemeAlinmazAciklama)
			throws CreateException;

	void odemeAlinmamaDurumIptal(int id) throws UpdateException;

	List<OdemeAlinmayacakBinalarDTO> getOdemeAlinmamaDurumu(int binaId)
			throws ReadException;

	String getBinaAdiByBasvuruId(int basvuruId) throws ReadException;

	void setCoordinat(int binaId, double lat, double lon)
			throws UpdateException;

	boolean odemeAlinacakMi(int binaId) throws ReadException, CreateException;

	List<Istatistik> sonCalisilanBinalar(Date tarih) throws ReadException;

	


	void setCsbmFromUavt(List<CaddeSokak> list) throws CreateException,
			DeleteException;

	void setMahalleFromUavt(List<Mahalle> list) throws CreateException,
			DeleteException;

	ArrayList<UavtBina> getUavtBinaTableByCsbmKod(long csbmKod)
			throws ReadException;

	void setBinaFromUavt(List<UavtBina> uavtdenEklenecek)
			throws CreateException;

	ArrayList<BinaDTO> binaByBelediye(int belediyeKod) throws ReadException;

	BinaDTO binaForRaporGonder(Connection con, int randevuId)
			throws ReadException;

}
