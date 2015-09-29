package tr.org.mmo.asansor.business;

import java.util.List;

import tr.org.mmo.asansor.beans_.Duyuru;
import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.dao.KullaniciDAOImpl;
import tr.org.mmo.asansor.dto.KullaniciRolYetkiDTO;
import tr.org.mmo.asansor.dto.KullaniciTurDTO;
import tr.org.mmo.asansor.exception.SystemUnavailableException;
import tr.org.mmo.asansor.exception.db.CRUDException;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;

public class KullaniciBusiness {

	private KullaniciDAOImpl dao = new KullaniciDAOImpl();

	public void ekle(Kullanici kullanici) throws CreateException {

		dao.kullaniciEkle(kullanici);

	}

	public Kullanici giris(Kullanici kullanici) throws ReadException,
			SystemUnavailableException {

		return dao.girisKontrol(kullanici);

	}

	public List<Kullanici> listeGetir() throws ReadException {

		return dao.kullaniciListesi();

	}

	public void yetkiInsert(Kullanici slctKullanici) throws CreateException {
		dao.yetkiInsert(slctKullanici);

	}

	public void setParola(String parola) throws UpdateException {
		dao.parolaDegistir(parola);

	}

	public void yeniParola(String kullaniciAdi, String parola)
			throws UpdateException {
		dao.yeniParola(kullaniciAdi, parola);

	}

	public Kullanici kullaniciByKullaniciAdi(String kullaniciAdi)
			throws ReadException {
		return dao.kullaniciGetirByKullaniciAdi(kullaniciAdi);
	}

	public void kullaniciDurumGuncelle(Kullanici kullanici)
			throws UpdateException {
		dao.kullaniciDurumGuncelle(kullanici);

	}

	public List<KullaniciTurDTO> kullaniciTurleri() throws ReadException {

		return dao.getKullaniciTurleri();
	}

	public void birimInsert(Kullanici slctKullanici) throws CRUDException {
		dao.birimInsert(slctKullanici);

	}

	public List<KullaniciRolYetkiDTO> getKullaniciRoller(int kullaniciId)
			throws ReadException {
		return dao.getKullaniciRoller(kullaniciId);

	}

	public void loginLogInsert() throws CRUDException {
		dao.loginLogInsert();

	}

	public void loginLogUpdate() throws UpdateException {
		dao.loginLogUpdate();

	}

	public List<Duyuru> getDuyurular() throws ReadException {
		
		return dao.getDuyurular();
	}

	public int duyuruKaydet(Duyuru duyuru) throws CRUDException{
	 return	dao.duyuruKaydet(duyuru);
		
	}

}
