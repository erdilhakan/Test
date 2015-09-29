package tr.org.mmo.asansor.business;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.dao.MuhendisDAOImpl;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;

public class MuhendisBusiness {

	public List<Kullanici> listeGetir() throws ReadException {
		MuhendisDAOImpl dao = new MuhendisDAOImpl();
		return dao.muhendisListesi();
	}

	public boolean muhendisVarMi(int sicilNo) throws ReadException {
		MuhendisDAOImpl dao = new MuhendisDAOImpl();
		return dao.muhendisVarMi(sicilNo);
	}

	public void muhendisUpdate(Kullanici muhendis) throws UpdateException {
		MuhendisDAOImpl dao = new MuhendisDAOImpl();
		dao.muhendisUpdate(muhendis);
	}

	public List<Kullanici> getOnaycilar() throws ReadException {
		return new MuhendisDAOImpl().getOnaycilar();

	}

	public Kullanici getMuhendis(Connection con, int sicilNo)
			throws ReadException {
		return new MuhendisDAOImpl().getMuhendis(con, sicilNo);

	}

	public List<Kullanici> getMuhendisler() throws ReadException {
		MuhendisDAOImpl dao = new MuhendisDAOImpl();
		return dao.getMuhendis();
	}

	public int getGunlukKontrolAdet(int sicilNo) throws ReadException {

		return new MuhendisDAOImpl().getGunlukKontrolAdet(sicilNo);
	}

	public void muhendisCihazYetkiKaydet(Connection con, int sicilNo, int[] i,
			String kullaniciAdi) throws SQLException {
		new MuhendisDAOImpl().muhendisCihazYetkiKaydet(con, sicilNo, i,
				kullaniciAdi);
	}

	
}
