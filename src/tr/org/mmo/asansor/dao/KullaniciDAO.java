package tr.org.mmo.asansor.dao;

import java.util.List;

import tr.org.mmo.asansor.beans_.Duyuru;
import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.dto.KullaniciRolYetkiDTO;
import tr.org.mmo.asansor.dto.KullaniciTurDTO;
import tr.org.mmo.asansor.exception.SystemUnavailableException;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;

public interface KullaniciDAO {

	void kullaniciEkle(Kullanici kullanici) throws CreateException;

	Kullanici girisKontrol(Kullanici kullanici) throws ReadException,
			SystemUnavailableException;

	List<Kullanici> kullaniciListesi() throws ReadException;

	void yetkiInsert(Kullanici kullanici) throws CreateException;

	void parolaDegistir(String parola) throws UpdateException;

	Kullanici kullaniciGetirByKullaniciAdi(String kullaniciAdi)
			throws ReadException;

	void kullaniciDurumGuncelle(Kullanici kullanici) throws UpdateException;

	List<KullaniciTurDTO> getKullaniciTurleri() throws ReadException;

	void yeniParola(String kullaniciAdi, String parola) throws UpdateException;

	void birimInsert(Kullanici kullanici) throws CreateException,
			DeleteException;

	List<KullaniciRolYetkiDTO> getKullaniciRoller(int kullaniciId)
			throws ReadException;

	void loginLogInsert() throws CreateException, UpdateException;

	void loginLogUpdate() throws UpdateException;

	List<Duyuru> getDuyurular() throws ReadException;

	int duyuruKaydet(Duyuru duyuru) throws CRUDException;

}
