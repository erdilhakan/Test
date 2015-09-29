package tr.org.mmo.asansor.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import tr.org.mmo.asansor.beans_.Kullanici;
import tr.org.mmo.asansor.exception.db.ReadException;
import tr.org.mmo.asansor.exception.db.UpdateException;

public interface MuhendisDAO {

	void muhendisKaydet(Kullanici muhendis) throws UpdateException;

	Kullanici getMuhendis(Connection con2, int sicilNo) throws ReadException;

	void muhendisUpdate(Kullanici muhendis) throws UpdateException;

	List<Kullanici> muhendisListesi() throws ReadException;

	boolean muhendisVarMi(int sicilNo) throws ReadException;

	List<Kullanici> getOnaycilar() throws ReadException;

	List<Kullanici> getMuhendis() throws ReadException;

	int getGunlukKontrolAdet(int sicilNo) throws ReadException;

	void muhendisCihazYetkiKaydet(Connection con2, int sicilNo,
			int[] cihazTipiList, String kullanici) throws SQLException;
}
