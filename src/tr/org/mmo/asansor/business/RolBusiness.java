package tr.org.mmo.asansor.business;

import java.sql.Connection;
import java.util.List;

import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.dao.RolDAOImpl;
import tr.org.mmo.asansor.dto.KullaniciRolYetkiDTO;
import tr.org.mmo.asansor.dto.RolYetkiDTO;
import tr.org.mmo.asansor.dto.RolYetkiRelationDTO;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;

public class RolBusiness {

	public List<RolYetkiDTO> listeGetir() throws ReadException {
		RolDAOImpl dao = new RolDAOImpl();
		return dao.rolListesi();
	}

	public List<KullaniciRolYetkiDTO> kullaniciRolListesi(Kullanici kullanici)
			throws ReadException {
		RolDAOImpl dao = new RolDAOImpl();
		return dao.getKullaniciRoller(kullanici);
	}

	public List<KullaniciRolYetkiDTO> kullaniciRolListesi(Connection con,
			Kullanici kullanici) throws ReadException {
		RolDAOImpl dao = new RolDAOImpl();
		return dao.getKullaniciRoller(con, kullanici);
	}

	public List<RolYetkiDTO> yetkiListesiGetir() throws ReadException {
		RolDAOImpl dao = new RolDAOImpl();
		return dao.yetkiListesi();
	}

	public int rolEkle(RolYetkiDTO r) throws CreateException {
		return new RolDAOImpl().rolEkle(r);
	}

	public void rolSil(int id) throws DeleteException {
		new RolDAOImpl().rolSil(id);

	}

	public List<RolYetkiRelationDTO> getRolYetkiler(int id)
			throws ReadException {
		return new RolDAOImpl().getRolYetkiler(id);

	}

	public void rolYetkiSil(RolYetkiRelationDTO yetki) throws DeleteException {
		new RolDAOImpl().rolYetkiSil(yetki);
	}

	public void yetkiTasi(List<RolYetkiDTO> secilenYetki, int rolId)
			throws CreateException {
		new RolDAOImpl().yetkiTasi(secilenYetki, rolId);
	}
}
