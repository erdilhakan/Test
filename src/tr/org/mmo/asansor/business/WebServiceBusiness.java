package tr.org.mmo.asansor.business;

import java.util.List;






 import btest.*;
import tr.org.mmo.asansor.beans_.ReferansDenetimKayitKontrolSorularListesi;
import tr.org.mmo.asansor.dao.SoruListDAOImpl;
import tr.org.mmo.asansor.dto.ReferansDenetimSorularEslestirmeDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.sanayiws.IWSClient;
import tr.org.mmo.asansor.sanayiws.WSClient;

public class WebServiceBusiness {
	private static WebServiceBusiness instance;
	
	public static WebServiceBusiness getInstance() throws Exception{
		return instance==null?new WebServiceBusiness():instance;
	}
	private IWSClient wSClient;
	public WebServiceBusiness() throws Exception{
		wSClient=WSClient.getInstance();
	}
	

	public  ServisSonucOfAsansorKimlikKodlar8Zb117HL getAsansorKimlikKodOfBinaKodAsansorSiraNo(int binaKod,int asansorSiraNo) throws CRUDException{
		
		return wSClient.getAsansorKimlikKodOfBinaKodAsansorSiraNo(binaKod, asansorSiraNo);
	}
	public  ServisSonucOfArrayOfBina8Zb117HL getBinaOfCsbmKod(int csbmKod) throws CRUDException{
		return wSClient.getBinaOfCsbmKod(csbmKod);
	}
	public  ServisSonucOfArrayOfCsbm8Zb117HL getCsbmKodOfMahalleKod(int mahalleKod) throws CRUDException{
		return wSClient.getCsbmKodOfMahalleKod(mahalleKod);
	}
	public ServisSonucOfArrayOfMahalle8Zb117HL getMahalleOfIlceKod(int ilceKod) throws CRUDException{
		return wSClient.getMahalleOfIlceKod(ilceKod);
	}
	public ServisSonucOfArrayOfIl8Zb117HL getIller() throws Exception{
		return wSClient.getIller();
	}
	public  ServisSonucOfArrayOfReferansTseStandard8Zb117HL getReferansStandart() throws CRUDException{
		return wSClient.getReferansStandart();
	}
	public ServisSonucOfDenetimKayitSonuc8Zb117HL asansorDenetimKayit(DenetimKayit denetimKayit) throws CRUDException{
		
		return wSClient.asansorDenetimKayit(denetimKayit);
	}
	public ServisSonucOfDenetimKayitSonuc8Zb117HL asansorDenetimKayitGuncelle(DenetimKayit denetimKayit) throws CRUDException{
		return wSClient.asansorDenetimKayitGuncelle(denetimKayit);
	}
	 public ServisSonuc asansorDenetimKayitSilme(int kayitNo) throws CRUDException{
		 return wSClient.asansorDenetimKayitSilme(kayitNo); 
	 }
	 public ServisSonucOfArrayOfDenetimKayit8Zb117HL asansorDenetimKayitSorgula(int kayitNo) throws CRUDException{
		 return wSClient.asansorDenetimKayitSorgula(kayitNo);
	 }
	 
	 public ServisSonucOfBelediye8Zb117HL ilceBelediyeSorgulama(int ilceKod) throws CRUDException{
		return wSClient.ilceBelediyeSorgulama(ilceKod);
	 }
	 public ServisSonucOfArrayOfBina8Zb117HL csbmBagliBinaSorgulama(int csbmKod) throws CRUDException{
			return wSClient.csbmBagliBinaSorgulama(csbmKod);
		 }


	public ServisSonucOfAsansorKimlikKodlar8Zb117HL asansorKimlikKodSorgula(
			int uavtBinaKod,int asansorSiraNo) throws CRUDException{
		return wSClient.asansorKimlikKodSorgula(uavtBinaKod,asansorSiraNo);
	}
	
	public ServisSonucOfAsansorKimlikKodlar8Zb117HL binaAsansorKayit(
			int uavtBinaKod,int asansorSiraNo) throws CRUDException{
		return wSClient.binaAsansorKayit(uavtBinaKod,asansorSiraNo);
	}
	
	public ServisSonucOfArrayOfReferansSoruSkala8Zb117HL referansSoruSkalaListesiGetir(
			) throws CRUDException{
		return wSClient.referansSoruSkalaListesiGetir();
	}


	public ServisSonucOfArrayOfBelediye8Zb117HL ileGoreBelediyeSorgulama(int i) throws CRUDException{
	return	wSClient.ileGoreBelediyeSorgulama(i);
		
	}


	public List<ReferansDenetimSorularEslestirmeDTO> referansDenetimSorularEslestirmeListesiGetir() throws CRUDException{
		// TODO Auto-generated method stub
		return wSClient.referansDenetimSorularEslestirmeListesiGetir();
	}


	public List<ReferansDenetimKayitKontrolSorularListesi> referansDenetimKayitKontrolSorularListesiGetir() throws CRUDException{
		// TODO Auto-generated method stub
		return wSClient.referansDenetimKayitKontrolSorularListeGetir();
	}
}
