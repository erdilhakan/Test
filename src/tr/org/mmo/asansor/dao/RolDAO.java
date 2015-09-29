package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.util.List;

import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.dto.KullaniciRolYetkiDTO;
import tr.org.mmo.asansor.dto.RolYetkiDTO;
import tr.org.mmo.asansor.dto.RolYetkiRelationDTO;
import tr.org.mmo.asansor.exception.db.CreateException;
import tr.org.mmo.asansor.exception.db.DeleteException;
import tr.org.mmo.asansor.exception.db.ReadException;

public interface RolDAO {

	public List<RolYetkiDTO> rolListesi() throws ReadException;

	List<KullaniciRolYetkiDTO> getKullaniciRoller(Kullanici k)
			throws ReadException;

	List<KullaniciRolYetkiDTO> getKullaniciRoller(Connection con, Kullanici k)
			throws ReadException;

	List<RolYetkiDTO> yetkiListesi() throws ReadException;

	int rolEkle(RolYetkiDTO r) throws CreateException;

	void rolSil(int id) throws DeleteException;

	List<RolYetkiRelationDTO> getRolYetkiler(int rolId) throws ReadException;

	void rolYetkiSil(RolYetkiRelationDTO yetki) throws DeleteException;

	void yetkiTasi(List<RolYetkiDTO> secilenYetki, int rolId)
			throws CreateException;
}
