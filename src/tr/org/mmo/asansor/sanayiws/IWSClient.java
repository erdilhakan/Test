package tr.org.mmo.asansor.sanayiws;



import java.util.List;

import btest.*;
import tr.org.mmo.asansor.beans_.ReferansDenetimKayitKontrolSorularListesi;
import tr.org.mmo.asansor.dto.ReferansDenetimSorularEslestirmeDTO;
import tr.org.mmo.asansor.exception.db.CRUDException;

public interface IWSClient {

	ServisSonucOfArrayOfIl8Zb117HL getIller() throws CRUDException;

	ServisSonucOfArrayOfReferansTseStandard8Zb117HL getReferansStandart() throws CRUDException;

	ServisSonucOfArrayOfMahalle8Zb117HL getMahalleOfIlceKod(int ilceKod) throws CRUDException;

	ServisSonucOfArrayOfCsbm8Zb117HL getCsbmKodOfMahalleKod(int mahalleKod) throws CRUDException;

	ServisSonucOfArrayOfBina8Zb117HL getBinaOfCsbmKod(int csbmKod) throws CRUDException;

	ServisSonucOfAsansorKimlikKodlar8Zb117HL getAsansorKimlikKodOfBinaKodAsansorSiraNo(int binaKod,
			int asansorSiraNo) throws CRUDException;

	ServisSonucOfDenetimKayitSonuc8Zb117HL asansorDenetimKayit(
			DenetimKayit denetimKayit) throws CRUDException;

	ServisSonucOfDenetimKayitSonuc8Zb117HL asansorDenetimKayitGuncelle(
			DenetimKayit denetimKayit) throws CRUDException;

	ServisSonuc asansorDenetimKayitSilme(int kayitNo) throws CRUDException;

	ServisSonucOfArrayOfDenetimKayit8Zb117HL asansorDenetimKayitSorgula(
			int kayitNo) throws CRUDException;

	ServisSonucOfBelediye8Zb117HL ilceBelediyeSorgulama(int ilceKod) throws CRUDException;

	ServisSonucOfArrayOfBina8Zb117HL csbmBagliBinaSorgulama(int csbmKod) throws CRUDException;

	

	ServisSonucOfAsansorKimlikKodlar8Zb117HL asansorKimlikKodSorgula(
			int uavtBinaKod, int asansorSiraNo) throws CRUDException;

	
	ServisSonucOfAsansorKimlikKodlar8Zb117HL binaAsansorKayit(int uavtBinaKod,
			int asansorSiraNo) throws CRUDException;

	ServisSonucOfArrayOfReferansKontrolSorular8Zb117HL referansDenetimKayitKontrolSorularListesiGetir()
			throws CRUDException;
	
	List<ReferansDenetimKayitKontrolSorularListesi> referansDenetimKayitKontrolSorularListeGetir()
			throws CRUDException;

	ServisSonucOfArrayOfReferansSoruSkala8Zb117HL referansSoruSkalaListesiGetir() throws CRUDException;

	ServisSonucOfArrayOfBelediye8Zb117HL ileGoreBelediyeSorgulama(int i) throws CRUDException;

	List<ReferansDenetimSorularEslestirmeDTO> referansDenetimSorularEslestirmeListesiGetir() throws CRUDException;

	ServisSonucOfArrayOfReferansKontrolSorular8Zb117HL referansDenetimKayitKontrolSorulariAsansorTuruneGore(
			int asansorTuru) throws CRUDException;

	

	

}
